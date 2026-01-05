package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.balanza.*;
import com.incloud.hcp.dto.DatosBLZProveedorDTO;
import com.incloud.hcp.dto.FiltroProveedorDTO;
import com.incloud.hcp.dto.GuiaRemisionDTO;
import com.incloud.hcp.dto.GuiaRemisionSapDTO;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.jco.balanza.GuiaRemision.dto.GuiaRemisionPosResponseDTO;
import com.incloud.hcp.jco.balanza.GuiaRemision.dto.GuiaRemisionResponseDTO;
import com.incloud.hcp.jco.balanza.GuiaRemision.service.JCOGuiaRemisionService;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.service.GuiaRemisionService;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.sap.conn.jco.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.awt.Color;
import java.io.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GuiaRemisionServiceImpl implements GuiaRemisionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final String NAME_SHEET = "ConstanciaPeso";
    protected final String CONFIG_TITLE = "com/incloud/hcp/excel/ConstanciaPesoExcel.xml";
    protected final String SOCIEDAD_CO = "CO02";
    protected final String SOCIEDAD_PE = "PE02";
    @Autowired
    private GuiaRemisionRepository guiaRemisionRepository;
    @Autowired
    private GuiaRemisionDetalleRepository guiaRemisionDetalleRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ChoferRepository choferRepository;

    @Autowired
    private JCOGuiaRemisionService guiaRemisionService;

    @Autowired
    private TicketPesajeRepository ticketPesajeRepository;
    @Autowired
    private ProveedorBalanzaRepository proveedorBalanzaRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private DetalleTicketRepository detalleTicketRepository;
    @Autowired
    private CarretaRepository carretaRepository;
    @Autowired
    private TransportistaRepository transportistaRepository;
    @Autowired
    private TransporteRepository transporteRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private ControlCambioCampoRepository controlCambioCampoRepository;
    @Autowired
    private SociedadRepository sociedadRepository;

    @Autowired
    private CentroAlmacenBlzRepository centroAlmacenBlzRepository;

    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;

    private final String FUNCION_RFC_REGISTRAR_GRE = "ZFPE_MM_REGISTRAR_GRE";
    private final String FUNCION_RFC_OBTENER_PDF = "ZFPE_MM_OBTENER_PDF";
    private final String FUNCION_RFC_ELIMINAR_GUIA = "ZFPE_MM_ELIMINA_GUIAREMISION";


@Autowired
private TipoProductoRepository tipoProductoRepository;
    @Autowired
    public GuiaRemisionServiceImpl(GuiaRemisionRepository guiaRemisionRepository,
                                   GuiaRemisionDetalleRepository guiaRemisionDetalleRepository,
                                   ProveedorRepository proveedorRepository,
                                   ChoferRepository choferRepository,
                                   JCOGuiaRemisionService guiaRemisionService,
                                   TicketPesajeRepository ticketPesajeRepository,
                                   DetalleTicketRepository detalleTicketRepository,
                                   EstadoRepository estadoRepository,
                                   TransporteRepository transporteRepository,
                                   CarretaRepository carretaRepository,
                                   SociedadRepository sociedadRepository,
                                   TipoProductoRepository tipoProductoRepository,
                                   CentroAlmacenBlzRepository centroAlmacenBlzRepository,
                                   ProveedorBalanzaRepository proveedorBalanzaRepository,
                                   ClienteRepository clienteRepository,
                                   TransportistaRepository transportistaRepository) {
        this.guiaRemisionRepository = guiaRemisionRepository;
        this.guiaRemisionDetalleRepository = guiaRemisionDetalleRepository;
        this.proveedorRepository = proveedorRepository;
        this.choferRepository = choferRepository;
        this.guiaRemisionService = guiaRemisionService;
        this.ticketPesajeRepository = ticketPesajeRepository;
        this.detalleTicketRepository = detalleTicketRepository;
        this.estadoRepository = estadoRepository;
        this.carretaRepository = carretaRepository;
        this.transporteRepository = transporteRepository;
        this.sociedadRepository = sociedadRepository;
        this.tipoProductoRepository = tipoProductoRepository;
        this.centroAlmacenBlzRepository = centroAlmacenBlzRepository;
        this.proveedorBalanzaRepository = proveedorBalanzaRepository;
        this.clienteRepository = clienteRepository;
        this.transportistaRepository = transportistaRepository;
    }
    @Override
    public List<GuiaRemision> getAllGuiaRemision() {
            return guiaRemisionRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    /**
     * @param idGuiaRemision
     * @return
     */
    @Override
    public GuiaRemision getGuiaRemisionById(Integer idGuiaRemision)throws PortalException {
        return guiaRemisionRepository.findById(idGuiaRemision).orElse(null);
    }


    @Override
    public GuiaRemisionSapDTO anularGuia(GuiaRemisionDTO guiaRemision) throws Exception {
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repository = destination.getRepository();

        JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC_ELIMINAR_GUIA);
        jCoFunction.getImportParameterList().setValue("SERIE", guiaRemision.getGuiaRemision().getSerieGuia());
        jCoFunction.getImportParameterList().setValue("NUMERO_GUIA", guiaRemision.getGuiaRemision().getNroGuia());

        jCoFunction.execute(destination);

        JCoTable table = jCoFunction.getTableParameterList().getTable("TO_RETURN");
        List<SapLog> listSapLog = new ArrayList<>();
        boolean anualado = false;
        if (table != null && !table.isEmpty()) {
            do {
                SapLog sapLog = new SapLog();
                sapLog.setTipo(table.getString("TYPE"));
                sapLog.setCode(table.getString("NUMBER"));
                sapLog.setMesaj(table.getString("MESSAGE"));
                sapLog.setParameter(table.getString("PARAMETER"));
                sapLog.setRow(table.getString("ROW"));
                sapLog.setField(table.getString("FIELD"));
                sapLog.setSystem(table.getString("SYSTEM"));
                logger.error("03A - grabarGuiaRemision sapLog" + sapLog.toString());
                anualado = table.getString("NUMBER").contains("005")?true:false;
                listSapLog.add(sapLog);

            } while (table.nextRow());
        }
        if(anualado){
            //guiaRemisionRepository.actualizarGuiaRemisionAnulado(guiaRemision.getGuiaRemision().getId(),"X");
            guiaRemisionRepository.deleteGuiaRemisionid(guiaRemision.getGuiaRemision().getSerieGuia(), guiaRemision.getGuiaRemision().getNroGuia());
            for (GuiaRemisionDetalle x: guiaRemision.getGuiaRemisionDetalleList()) {
                detalleTicketRepository.updateTieneGuia(x.getSubticket(),"");
                guiaRemisionDetalleRepository.deleteGuiaRemisionDetalleSubticket(x.getSubticket());
            }
        }
        GuiaRemisionSapDTO guiaRemisionResponseDTO = new GuiaRemisionSapDTO();
        guiaRemisionResponseDTO.setSapLogList(listSapLog);
        return guiaRemisionResponseDTO;
    }
    @Override
    public String obtenerPdf(GuiaRemisionDTO guiaRemision) throws Exception {
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repository = destination.getRepository();

        JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC_OBTENER_PDF);
        jCoFunction.getImportParameterList().setValue("SOCIEDAD", guiaRemision.getGuiaRemision().getSociedad());
        jCoFunction.getImportParameterList().setValue("SERIE", guiaRemision.getGuiaRemision().getSerieGuia());
        jCoFunction.getImportParameterList().setValue("NUMERO_GUIA", guiaRemision.getGuiaRemision().getNroGuia());
        jCoFunction.getImportParameterList().setValue("TIPO_GUIA", "GR");
        jCoFunction.execute(destination);

        return jCoFunction.getExportParameterList().getString("BASE64");



    }
    @Override
    public GuiaRemisionSapDTO RegistrarGRE(GuiaRemisionDTO guiaRemision) throws Exception {
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repository = destination.getRepository();

        JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC_REGISTRAR_GRE);



        jCoFunction.getImportParameterList().setValue("SOCIEDAD", guiaRemision.getGuiaRemision().getSociedad());
        jCoFunction.getImportParameterList().setValue("SERIE", guiaRemision.getGuiaRemision().getSerieGuia());
        jCoFunction.getImportParameterList().setValue("NUMERO_GUIA", guiaRemision.getGuiaRemision().getNroGuia());
        jCoFunction.getImportParameterList().setValue("FECHA_CREACION", guiaRemision.getGuiaRemision().getFechaCreacion());
        jCoFunction.execute(destination);


        //JCoStructure jCoTableInputHeader = jCoFunction.getTableParameterList().getTable("TI_GRE_MON");
        JCoTable table = jCoFunction.getTableParameterList().getTable("TI_GRE_MON");
        List<SapLog> listSapLog = new ArrayList<>();
        if (table != null && !table.isEmpty()) {
            do {
                SapLog sapLog = new SapLog();
                sapLog.setTipo(table.getString("ZSERIE"));
                sapLog.setCode(table.getString("ESTADO"));
                sapLog.setMesaj(table.getString("ZSERIE") +" - "+table.getString("ZGUIAR")+": "+table.getString("MENSAJE"));
                sapLog.setParameter(table.getString("ZGUIAR"));
                //sapLog.setRow(table.getString("ROW"));
                logger.error("03A - grabarGuiaRemision sapLog" + sapLog.toString());
                listSapLog.add(sapLog);
            } while (table.nextRow());
        }
        GuiaRemisionSapDTO guiaRemisionResponseDTO = new GuiaRemisionSapDTO();
        guiaRemisionResponseDTO.setSapLogList(listSapLog);
        for (SapLog x: listSapLog) {
            guiaRemisionRepository.actualizarGuiaRemisionMensajeAndEstado(guiaRemision.getGuiaRemision().getId(), x.getMesaj(), x.getCode());

        }

        return guiaRemisionResponseDTO;
        /*jCoTableInputHeader.setValue("BUKRS",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("ZSERIE",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("ZGUIAR",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("ZTIPO",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("ZANULA",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("CR_DATE",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("CR_TIME",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("CR_NAME",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("ZFETRAS",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("ESTADO",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("MENSAJE",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("RPTA_COMP",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("FECHA_COMP",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("RPTA_CDR",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("FECHA_CDR",guiaRemision.getGuiaRemision());
        jCoTableInputHeader.setValue("MOTIVO_BAJA",guiaRemision.getGuiaRemision());*/

        //String ticket = String.format("TK-%06d", Integer.parseInt(guiaRemision.getTicketPesaje()));
        //return null;
    }
    /**
     * @param guiaRemision
     * @return
     */
    @Override
    public GuiaRemisionSapDTO save(GuiaRemisionDTO guiaRemision) throws Exception {
        GuiaRemisionSapDTO guiaRemisionSapDTO = new GuiaRemisionSapDTO();
        logger.error("GUIA REMISION SAVE ");
        GuiaRemision gr = guiaRemision.getGuiaRemision();
        logger.error("SAVE " + gr);

        Integer ticketPesajeId = Integer.valueOf(gr.getIdTicketPesaje());
        logger.error("ticketPesajeId: " + ticketPesajeId);
        TicketPesaje ticketPesaje = this.ticketPesajeRepository.getOne(ticketPesajeId);
        logger.error("ticketPesaje: " + ticketPesaje.getId());
        List<DetalleTicket> detalleTicketList = this.detalleTicketRepository.findByTicketPesajeId(ticketPesajeId);
        logger.error("detalleTicketList: " + detalleTicketList.size());
        List<GuiaRemisionDetalle> guiaRemisionDetalleList = guiaRemision.getGuiaRemisionDetalleList();
        logger.error("GuiaRemisionDetalleList " + guiaRemisionDetalleList);
        String productoDescripcion = tipoProductoRepository.findByCodigoLike(guiaRemisionDetalleList.get(0).getMaterial()).getDescripcion();

        String zProceso;
        if (!guiaRemisionDetalleList.isEmpty()) {
            GuiaRemisionDetalle detalle = guiaRemisionDetalleList.get(0);
            switch (detalle.getTipoProducto()) {
                case "301":
                    zProceso = "02";
                    break;
                case "351":
                case "101":
                    zProceso = "01";
                    break;
                case "927":
                    zProceso = "03";
                    break;
                default:
                    zProceso = null;
            }
        } else {
            zProceso = null;
        }

        List<Transportista> transportistaList = this.transportistaRepository.getTransportistaByRucList(guiaRemision.getGuiaRemision().getTransport());
        String codigoAcreedor = transportistaList.get(0).getAcreedor();

        String calleOri , regionOri, denominacionOri, poblacionOri, distritoOri;
        calleOri = regionOri = denominacionOri = poblacionOri = distritoOri = "";
        String calleDes, regionDes, denominacionDes, poblacionDes, distritoDes;
        calleDes = regionDes = denominacionDes = poblacionDes = distritoDes = "";
        String centroOri = guiaRemision.getGuiaRemision().getIdCentroOrigen();
        String almacenOri = guiaRemision.getGuiaRemision().getIdAlmacenOrigen();
        String proveedorOri = guiaRemision.getGuiaRemision().getProveedorOrigen();
        String centroDes = guiaRemision.getGuiaRemision().getIdCentroDestino();
        String almacenDes = guiaRemision.getGuiaRemision().getIdAlmacenDestino();
        String proveedorDes = guiaRemision.getGuiaRemision().getProveedorDestino();
        String clienteDes = guiaRemision.getGuiaRemision().getClienteDestino();

        CentroAlmacenBlz centroAlmacenOri = new CentroAlmacenBlz();
        ProveedorBLZ proveedorBlzOri = new ProveedorBLZ();
        Optional<String> optionalCentroOri = Optional.ofNullable(centroOri);
        Optional<String> optionalAlmacenOri = Optional.ofNullable(almacenOri);
        Optional<String> optionalProveedorOri = Optional.ofNullable(proveedorOri);
        if(optionalCentroOri.isPresent() && !optionalCentroOri.get().isEmpty() && optionalAlmacenOri.isPresent() && !optionalAlmacenOri.get().isEmpty()){
            logger.error("centroOri seteo");
            centroAlmacenOri = this.centroAlmacenBlzRepository.getCentroAlmacenBlzByCentroAndCodigoAlmacen(centroOri, almacenOri).get(0);
            regionOri = centroAlmacenOri.getRegion();
            denominacionOri = centroAlmacenOri.getDenominacion();
            poblacionOri = centroAlmacenOri.getPoblacion();
            distritoOri = centroAlmacenOri.getDistrito();
        } else if (optionalProveedorOri.isPresent() && !optionalProveedorOri.get().isEmpty()){
            logger.error("proveedorOri seteo");
            proveedorBlzOri = this.proveedorBalanzaRepository.getProveedorBLZByAcreedor(proveedorOri);
            regionOri = proveedorBlzOri.getRegion();
            denominacionOri = proveedorBlzOri.getBezei();
            poblacionOri = proveedorBlzOri.getCity1();
            distritoOri = proveedorBlzOri.getCity2();
        }
        CentroAlmacenBlz centroAlmacenDes = new CentroAlmacenBlz();
        ProveedorBLZ proveedorBlzDes = new ProveedorBLZ();
        Cliente clienteBlzDes = new Cliente();
        Optional<String> optionalCentroDes = Optional.ofNullable(centroDes);
        Optional<String> optionalAlmacenDes = Optional.ofNullable(almacenDes);
        Optional<String> optionalProveedorDes = Optional.ofNullable(proveedorDes);
        Optional<String> optionalClienteDes = Optional.ofNullable(clienteDes);
        if(optionalCentroDes.isPresent() && !optionalCentroDes.get().isEmpty() && optionalAlmacenDes.isPresent() && !optionalAlmacenDes.get().isEmpty()){
            logger.error("centroDes seteo");
            centroAlmacenDes = this.centroAlmacenBlzRepository.getCentroAlmacenBlzByCentroAndCodigoAlmacen(centroDes, almacenDes).get(0);
            regionDes = centroAlmacenDes.getRegion();
            denominacionDes = centroAlmacenDes.getDenominacion();
            poblacionDes = centroAlmacenDes.getPoblacion();
            distritoDes = centroAlmacenDes.getDistrito();
        } else if (optionalProveedorDes.isPresent() && !optionalProveedorDes.get().isEmpty()){
            logger.error("proveedorDes seteo");
            /*proveedorBlzDes = this.proveedorBalanzaRepository.getProveedorBLZByAcreedor(proveedorDes);
            regionDes = proveedorBlzDes.getRegion();
            denominacionDes = proveedorBlzDes.getBezei();
            poblacionDes = proveedorBlzDes.getCity1();
            distritoDes = proveedorBlzDes.getCity2();*/
            regionDes =guiaRemision.getGuiaRemision().getRegion();
            denominacionDes = guiaRemision.getGuiaRemision().getBezei();
            poblacionDes = guiaRemision.getGuiaRemision().getCity1();
            distritoDes = guiaRemision.getGuiaRemision().getCity2();
        } else if(optionalClienteDes.isPresent() && !optionalClienteDes.get().isEmpty()){
            logger.error("clienteDes seteo"+clienteDes);
            //clienteBlzDes = this.clienteRepository.getClienteByRUC(clienteDes);
            /*clienteBlzDes = this.clienteRepository.getClienteByDeudor(clienteDes);
            regionDes = clienteBlzDes.getRegion();
            denominacionDes = clienteBlzDes.getBezei();
            poblacionDes = clienteBlzDes.getCity1();
            distritoDes = clienteBlzDes.getCity2();*/
            regionDes = guiaRemision.getGuiaRemision().getRegion();
            denominacionDes = guiaRemision.getGuiaRemision().getBezei();
            poblacionDes = guiaRemision.getGuiaRemision().getCity1();
            distritoDes = guiaRemision.getGuiaRemision().getCity2();
        }
        calleOri = guiaRemision.getGuiaRemision().getDireccionOrigen();
        calleDes = guiaRemision.getGuiaRemision().getDireccionDestino();

        logger.error("Datos de origen: "+ calleOri + regionOri+ denominacionOri, poblacionOri, distritoOri);
        logger.error("Datos de destino: ", calleDes, regionDes, denominacionDes, poblacionDes, distritoDes);

        logger.error("GuiaRemisionResponseDTO -  Entra al try");
        GuiaRemisionResponseDTO guiaRemisionResponse = new GuiaRemisionResponseDTO();
        guiaRemisionResponse.setSerieGuia(guiaRemision.getGuiaRemision().getSerieGuia());
        logger.error("guiaRemisionResponse - serieGuia: " + guiaRemisionResponse.getSerieGuia());
        guiaRemisionResponse.setNroGuia(guiaRemision.getGuiaRemision().getNroGuia());
        logger.error("guiaRemisionResponse - nroGuia: " + guiaRemisionResponse.getNroGuia());
        guiaRemisionResponse.setTipoGuia(guiaRemision.getGuiaRemision().getIdtipoGuia());
        logger.error("guiaRemisionResponse - tipoGuia: " + guiaRemisionResponse.getTipoGuia());
        guiaRemisionResponse.setTipoMovimiento(guiaRemisionResponse.getTipoMovimiento());
        logger.error("guiaRemisionResponse - tipoMovimiento: " + guiaRemisionResponse.getTipoMovimiento());
        guiaRemisionResponse.setMotTraslado(guiaRemision.getGuiaRemision().getIdmotTraslado());
        logger.error("guiaRemisionResponse - motTraslado: " + guiaRemisionResponse.getMotTraslado());
        guiaRemisionResponse.setTextoGuia(guiaRemision.getGuiaRemision().getTextoGuia());
        logger.error("guiaRemisionResponse - textoGuia: " + guiaRemisionResponse.getTextoGuia());
        guiaRemisionResponse.setTicketPesaje(String.valueOf(guiaRemision.getGuiaRemision().getIdTicketPesaje()));
        logger.error("guiaRemisionResponse - ticketPesaje: " + guiaRemisionResponse.getTicketPesaje());
        guiaRemisionResponse.setRazonSocial(guiaRemision.getGuiaRemision().getRazonSocial());
        logger.error("guiaRemisionResponse - razonSocial: " + guiaRemisionResponse.getRazonSocial());
        guiaRemisionResponse.setRuc(guiaRemision.getGuiaRemision().getRuc());
        logger.error("guiaRemisionResponse - ruc: " + guiaRemisionResponse.getRuc());
        guiaRemisionResponse.setOriCentro(guiaRemision.getGuiaRemision().getIdCentroOrigen());
        logger.error("guiaRemisionResponse - oriCentro: " + guiaRemisionResponse.getOriCentro());
        guiaRemisionResponse.setOriAlmacen(guiaRemision.getGuiaRemision().getIdAlmacenOrigen());
        logger.error("guiaRemisionResponse - oriAlmacen: " + guiaRemisionResponse.getOriAlmacen());
        guiaRemisionResponse.setOriProveedor(guiaRemision.getGuiaRemision().getProveedorOrigen());
        logger.error("guiaRemisionResponse - oriProveedor: " + guiaRemisionResponse.getOriProveedor());
        guiaRemisionResponse.setOriDirStreet(calleOri);
        //guiaRemisionResponse.setOriDirStreet(guiaRemisionResponse.getOriDirStreet());
        logger.error("guiaRemisionResponse - oriDirStreet: " + guiaRemisionResponse.getOriDirStreet());
        guiaRemisionResponse.setOriDirRegion(regionOri);
        logger.error("guiaRemisionResponse - OriDirRegion: " + guiaRemisionResponse.getOriDirRegion());
        guiaRemisionResponse.setOriDirBezei(denominacionOri);
        logger.error("guiaRemisionResponse - OriDirBezei: " + guiaRemisionResponse.getOriDirBezei());
        guiaRemisionResponse.setOriDirCity1(poblacionOri);
        logger.error("guiaRemisionResponse - OriDirCity1: " + guiaRemisionResponse.getOriDirCity1());
        guiaRemisionResponse.setOriDirCity2(distritoOri);
        logger.error("guiaRemisionResponse - OriDirCity2: " + guiaRemisionResponse.getOriDirCity2());
        guiaRemisionResponse.setOriDirHouseNum1("");
        guiaRemisionResponse.setOriDirHouseNum2("");
        guiaRemisionResponse.setOriDirStrSuppl2("");
        guiaRemisionResponse.setDesAlmacen(guiaRemision.getGuiaRemision().getIdAlmacenDestino());
        logger.error("guiaRemisionResponse - desAlmacen: " + guiaRemisionResponse.getDesAlmacen());
        guiaRemisionResponse.setDesCentro(guiaRemision.getGuiaRemision().getIdCentroDestino());
        logger.error("guiaRemisionResponse - desCentro: " + guiaRemisionResponse.getDesCentro());
        guiaRemisionResponse.setDesCliente(guiaRemision.getGuiaRemision().getClienteDestino());
        logger.error("guiaRemisionResponse - desCliente: " + guiaRemisionResponse.getDesCliente());
        guiaRemisionResponse.setDesProveedor(guiaRemision.getGuiaRemision().getProveedorDestino());
        logger.error("guiaRemisionResponse - desProveedor: " + guiaRemisionResponse.getDesProveedor());
        guiaRemisionResponse.setDesDirStreet(calleDes);
        logger.error("guiaRemisionResponse - derDirStreet: " + guiaRemisionResponse.getDesDirStreet());
        guiaRemisionResponse.setDesDirHouseNum1("");
        guiaRemisionResponse.setDesDirHouseNum2("");
        guiaRemisionResponse.setDesDirStrSuppl2("");
        guiaRemisionResponse.setDesDirRegion(regionDes);
        guiaRemisionResponse.setDesDirBezei(denominacionDes);
        guiaRemisionResponse.setDesDirCity1(poblacionDes);
        guiaRemisionResponse.setDesDirCity2(distritoDes);

        guiaRemisionResponse.setPlacaTransporte(guiaRemision.getGuiaRemision().getTransporte().getPlaca());
        logger.error("guiaRemisionResponse - placaTransporte: " + guiaRemisionResponse.getPlacaTransporte());
        if(guiaRemision.getGuiaRemision().getCarreta() != null){
            guiaRemisionResponse.setPlacaCarreta(guiaRemision.getGuiaRemision().getCarreta().getPlaca());
        } else {
            guiaRemisionResponse.setPlacaCarreta("");
        }

        logger.error("guiaRemisionResponse - placaCarreta: " + guiaRemisionResponse.getPlacaCarreta());
        guiaRemisionResponse.setTipoDestino(guiaRemision.getGuiaRemision().getzIndest());
        //guiaRemisionResponse.setTipoDestino(guiaRemision.getGuiaRemision().getTipoDestino());
        logger.error("guiaRemisionResponse - zIndest: " + guiaRemisionResponse.getTipoDestino());
        guiaRemisionResponse.setTipoOrigen(guiaRemision.getGuiaRemision().getzIndori());
        logger.error("guiaRemisionResponse - zIndori: " + guiaRemisionResponse.getTipoOrigen());
        guiaRemisionResponse.setTransport(codigoAcreedor);
        logger.error("guiaRemisionResponse - transport: " + guiaRemisionResponse.getTransport());
        guiaRemisionResponse.setzBalanza(guiaRemision.getGuiaRemision().getzBalanza());
        logger.error("guiaRemisionResponse - zBalanza: " + guiaRemisionResponse.getzBalanza());
        guiaRemisionResponse.setzProceso(zProceso);
        logger.error("guiaRemisionResponse - zProceso: " + zProceso);
        guiaRemisionResponse.setzProducto(productoDescripcion);
        logger.error("guiaRemisionResponse - zProducto: " + guiaRemisionResponse.getzProducto());
        guiaRemisionResponse.setzReferencia(guiaRemision.getGuiaRemision().getReferencia());
        logger.error("guiaRemisionResponse - zReferencia: " + guiaRemisionResponse.getzReferencia());
        guiaRemisionResponse.setUsuarioCreador(guiaRemision.getGuiaRemision().getUsuarioCreador());
        guiaRemisionResponse.setIdmotOtro(guiaRemision.getGuiaRemision().getIdmotOtro());
        guiaRemisionResponse.setIdmotTraslado(guiaRemision.getGuiaRemision().getIdmotTraslado());
        guiaRemisionResponse.setModoTransporte(guiaRemision.getGuiaRemision().getModoTransporte());
        guiaRemisionResponse.setPesoBruto(guiaRemision.getGuiaRemision().getPesoBruto());
        guiaRemisionResponse.setPesoTara(guiaRemision.getGuiaRemision().getPesoTara());
        guiaRemisionResponse.setPesoNeto(guiaRemision.getGuiaRemision().getPesoNeto());
        guiaRemisionResponse.setLfdnr(guiaRemision.getGuiaRemision().getLfdnr());
        guiaRemisionResponse.setLand1(guiaRemision.getGuiaRemision().getLand1());
        guiaRemisionResponse.setUnidadMedida(guiaRemision.getGuiaRemision().getUnidadMedida());
        guiaRemisionResponse.setNumeroBulto(guiaRemision.getGuiaRemision().getNumeroBulto());
        guiaRemisionResponse.setIndicadorServicio(guiaRemision.getGuiaRemision().getIndicadorServicio());
        guiaRemisionResponse.setTipoMovimiento(guiaRemision.getGuiaRemision().getTipoMovimiento());
        guiaRemisionResponse.setTipoTransaccion(guiaRemision.getGuiaRemision().getTipoTransaccion());
        guiaRemisionResponse.setFechaTraslado(guiaRemision.getGuiaRemision().getFechaTraslado());

        guiaRemisionResponse.setPresintoAduanero(guiaRemision.getGuiaRemision().getPresintoAduanero());
        guiaRemisionResponse.setNumContenedor(guiaRemision.getGuiaRemision().getNumContenedor());
        guiaRemisionResponse.setCodigoDam(guiaRemision.getGuiaRemision().getCodigoDam());
        guiaRemisionResponse.setPesoSeleccion(guiaRemision.getGuiaRemision().getPesoSeleccion());
        guiaRemisionResponse.setSustentoDiferencia(guiaRemision.getGuiaRemision().getSustentoDiferencia());
        guiaRemisionResponse.setTipoLocacion(guiaRemision.getGuiaRemision().getTipoLocacion());
        guiaRemisionResponse.setPuertoLlegada(guiaRemision.getGuiaRemision().getPuertoLlegada());
        guiaRemisionResponse.setTipoPuerto(guiaRemision.getGuiaRemision().getTipoPuerto());
        guiaRemisionResponse.setAeropuerto(guiaRemision.getGuiaRemision().getAeropuerto());
        guiaRemisionResponse.setConstanciaDetraccion(guiaRemision.getGuiaRemision().getConstanciaDetraccion());
        guiaRemisionResponse.setSociedad(guiaRemision.getGuiaRemision().getSociedad());


        logger.error("guiaRemisionResponse - usuarioCreador: " + guiaRemisionResponse.getUsuarioCreador());

        /*Date date = new Date();
        Date fechaProducion, fechaTraslado, fechaCreacion;
        fechaProducion = fechaTraslado = fechaCreacion = date;
        guiaRemisionResponse.setFechaTraslado(fechaTraslado);*/
        logger.error("guiaRemisionResponse - fechaTraslado: " + guiaRemisionResponse.getFechaTraslado());

        //guiaRemisionResponse.setFechaCreacion(fechaCreacion);

        /*Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(guiaRemision.getGuiaRemision().getFechaCreacion());
        calendar1.add(Calendar.HOUR_OF_DAY, -5);
        guiaRemisionResponse.setFechaCreacion(calendar1.getTime());*/
        guiaRemisionResponse.setFechaCreacion(guiaRemision.getGuiaRemision().getFechaCreacion());
        logger.error("guiaRemisionResponse - fechaCreacion: " + guiaRemisionResponse.getFechaCreacion());

        long now = System.currentTimeMillis();
        Time time = new Time(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.HOUR_OF_DAY, -5);
        Time newTime = new Time(calendar.getTimeInMillis());
        guiaRemisionResponse.setHoraCreacion(newTime);
        logger.error("guiaRemisionResponse - horaCreacion: " + guiaRemisionResponse.getHoraCreacion());

        logger.error("SETEO DEL CHOFER A GUIAREMISIONRESPONSE - START");
        guiaRemisionResponse.setChofer(guiaRemision.getGuiaRemision().getChofer());
        logger.error("SETEO DEL CHOFER A GUIAREMISIONRESPONSE - END");
        logger.error("guiaRemisionResponse - chofer: " + guiaRemisionResponse.getChofer());
        logger.error("guiaRemisionResponse: " + guiaRemisionResponse);

        List<GuiaRemisionPosResponseDTO> guiaRemisionPosResponseDTOS = new ArrayList<>();
        List<GuiaRemisionDetalle> grList = guiaRemision.getGuiaRemisionDetalleList();
        for (int i = 0; i < grList.size(); i++) {
            GuiaRemisionPosResponseDTO guiaRemisionPosResponseDTO = new GuiaRemisionPosResponseDTO();

            Integer contador = new Integer(10 * (i + 1));
            String scontador = StringUtils.leftPad(contador.toString().trim(), 5, '0');
            String codigoTipoProducto = tipoProductoRepository.findByCodigoLike(guiaRemisionDetalleList.get(i).getMaterial()).getDescripcion();
            guiaRemisionPosResponseDTO.setNumPos(Integer.valueOf(scontador));
            logger.error("guiaRemisionPosResponseDTO - numPos: " + guiaRemisionPosResponseDTO.getNumPos());
            guiaRemisionPosResponseDTO.setDescripcion(guiaRemisionDetalleList.get(i).getTipoProducto());
            logger.error("guiaRemisionPosResponseDTO - descripcion: " + guiaRemisionPosResponseDTO.getDescripcion());
            guiaRemisionPosResponseDTO.setDocMaterial(guiaRemisionDetalleList.get(i).getDocMaterial());
            logger.error("guiaRemisionPosResponseDTO - docMaterial: " + guiaRemisionPosResponseDTO.getDocMaterial());
            guiaRemisionPosResponseDTO.setEjercicio(guiaRemisionDetalleList.get(i).getEjercicio());
            logger.error("guiaRemisionPosResponseDTO - ejercicio: " + guiaRemisionPosResponseDTO.getEjercicio());
            guiaRemisionPosResponseDTO.setPosicionDocumento(guiaRemisionDetalleList.get(i).getPosicionDocumento());
            logger.error("guiaRemisionPosResponseDTO - posicion: " + guiaRemisionPosResponseDTO.getPosicionDocumento());

            guiaRemisionPosResponseDTO.setPosicion(guiaRemisionDetalleList.get(i).getPosicion());
            logger.error("guiaRemisionPosResponseDTO - posicion: " + guiaRemisionPosResponseDTO.getPosicion());
            guiaRemisionPosResponseDTO.setMaterial(guiaRemisionDetalleList.get(i).getMaterial());
            logger.error("guiaRemisionPosResponseDTO - material: " + guiaRemisionPosResponseDTO.getMaterial());
            guiaRemisionPosResponseDTO.setLote(guiaRemisionDetalleList.get(i).getLote());
            logger.error("guiaRemisionPosResponseDTO - lote: " + guiaRemisionPosResponseDTO.getLote());
            guiaRemisionPosResponseDTO.setCantDisponible(guiaRemisionDetalleList.get(i).getCantidad());
            logger.error("guiaRemisionPosResponseDTO - cantDisponible: " + guiaRemisionPosResponseDTO.getCantDisponible());
            guiaRemisionPosResponseDTO.setUm(guiaRemisionDetalleList.get(i).getUnidadMedida());
            logger.error("guiaRemisionPosResponseDTO - um: " + guiaRemisionPosResponseDTO.getUm());

            guiaRemisionPosResponseDTO.setSubticket(detalleTicketList.get(0).getSubticket());
            logger.error("guiaRemisionPosResponseDTO - subticket: " + guiaRemisionPosResponseDTO.getSubticket());
            guiaRemisionPosResponseDTO.setAlmacen(guiaRemisionDetalleList.get(i).getAlmacen());
            logger.error("guiaRemisionPosResponseDTO - almacen: " + guiaRemisionPosResponseDTO.getAlmacen());
            guiaRemisionPosResponseDTO.setUnidadMedidaNeto(guiaRemisionDetalleList.get(i).getUnidadMedidaNeto());
            logger.error("guiaRemisionPosResponseDTO - umNeto: " + guiaRemisionPosResponseDTO.getUnidadMedidaNeto());
            guiaRemisionPosResponseDTO.setPesoNeto(guiaRemisionDetalleList.get(i).getPesoNeto());
            logger.error("guiaRemisionPosResponseDTO - pesoNeto: " + guiaRemisionPosResponseDTO.getPesoNeto());
            guiaRemisionPosResponseDTO.setUnidadMedidaSap(guiaRemisionDetalleList.get(i).getUnidadMedidaSap());
            logger.error("guiaRemisionPosResponseDTO - umSap: " + guiaRemisionPosResponseDTO.getUnidadMedidaSap());
            guiaRemisionPosResponseDTO.setPesoSap(guiaRemisionDetalleList.get(i).getPesoSap());
            logger.error("guiaRemisionPosResponseDTO - pesoSap: " + guiaRemisionPosResponseDTO.getPesoSap());
            guiaRemisionPosResponseDTO.setPedidoVenta(guiaRemisionDetalleList.get(i).getPedidoVenta());
            guiaRemisionPosResponseDTO.setDocumentoTraslado(guiaRemisionDetalleList.get(i).getDocumentoTraslado());
            guiaRemisionPosResponseDTO.setDam(guiaRemisionDetalleList.get(i).getDam());
            logger.error("guiaRemisionPosResponseDTO - pedidoVenta: " + guiaRemisionPosResponseDTO.getPedidoVenta());
            guiaRemisionPosResponseDTO.setFechaProduccion(guiaRemisionDetalleList.get(i).getFechaProduccion());
            logger.error("guiaRemisionPosResponseDTO - fechaProduccion: " + guiaRemisionPosResponseDTO.getFechaProduccion());
            guiaRemisionPosResponseDTO.setCodigoTipoProducto(codigoTipoProducto);
            guiaRemisionPosResponseDTOS.add(guiaRemisionPosResponseDTO);
        }
        guiaRemisionResponse.setGuiaRemisionPosList(guiaRemisionPosResponseDTOS);
        logger.error("guiaRemisionPosResponseDTOS: " + guiaRemisionPosResponseDTOS);
        GuiaRemisionResponseDTO responseDTO = new GuiaRemisionResponseDTO();
        responseDTO = this.guiaRemisionService.grabarGuiaRemision(guiaRemisionResponse);
        List<SapLog> sapLogList = responseDTO.getSapLogList();
        logger.error("Grabar Guia Remisión sapLogList: " + sapLogList);
        guiaRemisionSapDTO.setSapLogList(sapLogList);
        logger.error("Grabar Guia Remisión responseDTO" + responseDTO);

        logger.error("SAPLOGLIST LENGTH:" + sapLogList.size());
        if(!sapLogList.isEmpty()){
            logger.error("GUIA REMISION CON ERRORES");
            guiaRemisionSapDTO.setSapLogList(sapLogList);
            return guiaRemisionSapDTO;
        }
        logger.error("Guia remision se guarda");
        GuiaRemision response = this.guiaRemisionRepository.save(gr);
        logger.error("Guia remision se guardo: " + response);
        guiaRemisionSapDTO.setGuiaRemision(response);

        logger.error("GUIAREMISIONSAPDTO guiaRemision: " + guiaRemisionSapDTO);
        Integer guiaRemisionId = response.getId();
        logger.error("guiaRemisionId " + guiaRemisionId);
        for (int i = 0; i < guiaRemisionDetalleList.size(); i++) {
            guiaRemisionDetalleList.get(i).setIdGuia(guiaRemisionId);
        }
        logger.error("GUIAREMISIONDETALLE LIST " + guiaRemisionDetalleList);
        List<GuiaRemisionDetalle> guiaRemisionDetalles =  this.guiaRemisionDetalleRepository.saveAll(guiaRemisionDetalleList);
        guiaRemisionSapDTO.setGuiaRemisionDetalleList(guiaRemisionDetalles);
        logger.error("GUIAREMISIONSAPDTO guiaRemisionDetalles: " + guiaRemisionSapDTO);

        logger.error("INICIO DE CAMBIO DE ESTADO");
        // ESTADO EN PROCESO
        Integer estadoId = 1;
        Estado estado = this.estadoRepository.getById(estadoId);
        logger.error("Estado: " + estado);
        ticketPesaje.setEstado(estado);

        logger.error("Actualizacion de ticketPesaje");
        //ticketPesajeRepository.updateEstado(ticketPesaje.getId(),estadoId);
        //TicketPesaje ticketPesajeSave= ticketPesajeRepository.save(ticketPesaje);
        //logger.error("Ticket pesaje saved: " + ticketPesajeSave.getId());


        for(DetalleTicket detalleTicket: detalleTicketList){
            logger.error("Jescudero detalleTicket.getSubticket(): " + detalleTicket.getSubticket()+"***"+detalleTicket.getId());
            List<GuiaRemisionDetalle> existsBySubticket = this.guiaRemisionDetalleRepository.findBySubTicket(detalleTicket.getSubticket());
            logger.error("Jescudero existsBySubticket.size()>: "+existsBySubticket.size());
            if(existsBySubticket.size()>0){
                //List<GuiaRemisionDetalle> aux = guiaRemisionDetalleList.stream().filter(x ->x.getSubticket() == detalleTicket.getSubticket()).collect(Collectors.toList());
                for (GuiaRemisionDetalle aux: guiaRemisionDetalleList) {
                    //if(aux.getSubticket().equalsIgnoreCase(detalleTicket.getSubticket()) && aux.getPosicion() ==detalleTicket.getPosicion()){
                    if(aux.getSubticket().equalsIgnoreCase(detalleTicket.getSubticket()) && aux.getPosicionDocumento() ==detalleTicket.getPosicionDocumento()){
                        detalleTicket.setDocMaterial(aux.getDocMaterial());
                        detalleTicket.setLote(aux.getLote());
                        detalleTicket.setMaterial(aux.getMaterial());
                        detalleTicket.setDam(aux.getDam());
                        if(aux.getPesoNeto()!=null)detalleTicket.setPeso_neto(aux.getPesoNeto()*1000);
                        detalleTicket.setUnidadMedidaSap(aux.getUnidadMedidaSap());
                        detalleTicket.setPesoSap(aux.getPesoSap());
                        detalleTicket.setEjercicio(aux.getEjercicio()+"");
                        detalleTicket.setPosicion(aux.getPosicion());
                        detalleTicket.setPosicionDocumento(aux.getPosicionDocumento());
                        detalleTicket.setDocumentoTraslado(aux.getDocumentoTraslado());
                        break;
                    }
                }


                detalleTicket.setTieneGuia("X");
            }
            logger.error("Actualizacion de detalleTicket: " + detalleTicket.getId());
            this.detalleTicketRepository.save(detalleTicket);
            logger.error("Detalleticket saved: " + detalleTicket);
        }
        logger.error("FIN DE CAMBIO DE ESTADO");

        logger.error("FINAL RESPONSE : " + guiaRemisionSapDTO);
        return guiaRemisionSapDTO;
    }

   @Override
    public List<DatosBLZProveedorDTO> getProveedorByProveedorandRucandSocialanddireccionandemail(FiltroProveedorDTO filtro) {
        List<DatosBLZProveedorDTO> lista = new ArrayList<>();
        proveedorRepository.getProveedorByProveedorandRucandSocialanddireccionandemail(filtro.getRuc(), filtro.getRazonSocial()).forEach( x -> {
            DatosBLZProveedorDTO dp = new DatosBLZProveedorDTO();
            dp.setRuc(String.valueOf(x[0]));
            dp.setRazonSocial(String.valueOf(x[1]));
            dp.setDireccionFiscal(String.valueOf(x[2]));
            dp.setEmail(String.valueOf(x[3]));
            dp.setAcreedorCodigoSap(String.valueOf(x[4]));
            lista.add(dp);
        });
        return lista;
    }
    @Override
    public List<DatosBLZProveedorDTO> getProveedorDtoByRuc(String ruc) {
        List<DatosBLZProveedorDTO> lista = new ArrayList<>();
        proveedorRepository.getProveedorDtoByRuc(ruc).forEach( x -> {
            DatosBLZProveedorDTO dp = new DatosBLZProveedorDTO();
            dp.setRuc(String.valueOf(x[0]));
            dp.setRazonSocial(String.valueOf(x[1]));
            dp.setDireccionFiscal(String.valueOf(x[2]));
            dp.setEmail(String.valueOf(x[3]));
            dp.setAcreedorCodigoSap(String.valueOf(x[4]));
            lista.add(dp);
        });
        return lista;
    }
    @Override
    public List<DatosBLZProveedorDTO> getProveedorDtoByRazonSocial(String razonSocial) {
        List<DatosBLZProveedorDTO> lista = new ArrayList<>();
        proveedorRepository.getProveedorDtoByRazonSocial(razonSocial).forEach( x -> {
            DatosBLZProveedorDTO dp = new DatosBLZProveedorDTO();
            dp.setRuc(String.valueOf(x[0]));
            dp.setRazonSocial(String.valueOf(x[1]));
            dp.setDireccionFiscal(String.valueOf(x[2]));
            dp.setEmail(String.valueOf(x[3]));
            dp.setAcreedorCodigoSap(String.valueOf(x[4]));
            lista.add(dp);
        });
        return lista;
    }

    @Override
    public List<DatosBLZProveedorDTO> getProveedorDtoByAcreedorCodigoSap(String acreedorCodigoSap) {
        List<DatosBLZProveedorDTO> lista = new ArrayList<>();
        proveedorRepository.getProveedorDtoByAcreedorCodigoSap(acreedorCodigoSap).forEach( x -> {
            DatosBLZProveedorDTO dp = new DatosBLZProveedorDTO();
            dp.setRuc(String.valueOf(x[0]));
            dp.setRazonSocial(String.valueOf(x[1]));
            dp.setDireccionFiscal(String.valueOf(x[2]));
            dp.setEmail(String.valueOf(x[3]));
            dp.setAcreedorCodigoSap(String.valueOf(x[4]));
            lista.add(dp);
        });
        return lista;
    }

    @Override
    public List<GuiaRemision> getAllGuiaRemisionByTicketPesajeId(String ticketPesajeId) {
        return this.guiaRemisionRepository.getAllGuiaRemisionByTicketPesajeId(ticketPesajeId);
    }



/*    @Override
    public String exportaConstanciaPeso(Integer id) throws FileNotFoundException, JRException {
        String fileName = "ConstanciaPeso.pdf";
        String sftpPath = "/";
        String sftpHost = "200.41.106.103";
        String sftpPort = "2222";
        String sftpUser = "sftpcope";
        String sftpPassword = "S3t%$&p30\"/";
        List<GuiaRemision> guiaRemision = guiaRemisionRepository.findGuiaRemisionById(id);


        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(sftpUser, sftpHost, Integer.valueOf(sftpPort));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(sftpPassword);
            logger.error("Connecting------");
            session.connect();
            logger.error("Established Session");

            Channel channel = session.openChannel("sftp");
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.connect();

            File file = ResourceUtils.getFile("classpath:reportes/jrxml/ConstanciaPeso.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(guiaRemision);
            logger.error("exportaConstanciaPeso: 1");
            Map<String, Object> map = new HashMap<>();
            map.put("createdBy","TITLE");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,map,dataSource);
            logger.error("exportaConstanciaPeso: 2");
            JasperExportManager.exportReportToPdfFile(jasperPrint,sftpPath+"/ConstanciaPeso.pdf");

            sftpChannel.put(file + "/" + fileName,sftpPath);
            sftpChannel.disconnect();
            session.disconnect();

            System.out.println("Disconnected from sftp");
        }catch (Exception e) {
            e.printStackTrace();
        }

        logger.error("exportaConstanciaPeso: inicio");



        return "reporte generado";
    }*/


    @Transactional(readOnly = true)
    public SXSSFWorkbook generaConstanciaPeso(Integer id){
        logger.error("[generaConstanciaPeso-Service]:Inicio");
        GuiaRemision guiaRemision = guiaRemisionRepository.downloadConstanciaExcel(id);
        logger.error("[generaConstanciaPeso-Service]:guiaRemision:{}", guiaRemision);
        String idTicket = guiaRemision.getIdTicketPesaje();
        List<DetalleTicket> detalleTicketList = detalleTicketRepository.findByTicketPesajeId(Integer.valueOf(idTicket));
        detalleTicketList = detalleTicketList.stream().sorted(Comparator.comparingInt(DetalleTicket::getId))
                .collect(Collectors.toList());
        Double pesoFinal = detalleTicketList.get(detalleTicketList.size() - 1).getPeso_final();
        String razonSocial= "";
        String ruc = "";
        String telf = "";
        String direccion = "";
        String distrito = "";
        String provincia = "";
        String departamento = "";
        String placaCarreta = "";
        String placaTransporte = "";

        if(!guiaRemision.getIdCentroOrigen().isEmpty()){
            if(guiaRemision.getIdCentroOrigen().contains("CO")){
                razonSocial = sociedadRepository.getByCodigoSociedad(SOCIEDAD_CO).getRazonSocial();
                ruc = sociedadRepository.getByCodigoSociedad(SOCIEDAD_CO).getRuc();
                direccion = sociedadRepository.getByCodigoSociedad(SOCIEDAD_CO).getDireccionFiscal();
                String [] parteDireccion = direccion.split("-");
                direccion = parteDireccion[0];
                distrito  = parteDireccion[1];
                telf = sociedadRepository.getByCodigoSociedad(SOCIEDAD_CO).getTelefono();
                String [] parteDistrito = distrito.split(",");
                departamento = parteDistrito[1];
                provincia = parteDistrito[1];

            }else if(guiaRemision.getIdCentroOrigen().contains("PE")){
                razonSocial = sociedadRepository.getByCodigoSociedad(SOCIEDAD_PE).getRazonSocial();
                ruc = sociedadRepository.getByCodigoSociedad(SOCIEDAD_PE).getRuc();
                telf = sociedadRepository.getByCodigoSociedad(SOCIEDAD_PE).getTelefono();
                direccion = sociedadRepository.getByCodigoSociedad(SOCIEDAD_PE).getDireccionFiscal();
                String [] parteDireccion = direccion.split("-");
                direccion = parteDireccion[0];
                distrito  = parteDireccion[1];
                String [] parteDistrito = distrito.split(",");
                departamento = parteDistrito[1];
                provincia = parteDistrito[1];

            }
        }

        if(guiaRemision.getTransporte()!=null && guiaRemision.getTransporte().getPlaca()!= null){
            placaTransporte = guiaRemision.getTransporte().getPlaca();
        }

        if(guiaRemision.getCarreta()!=null && guiaRemision.getCarreta().getPlaca()!= null){
            placaCarreta = guiaRemision.getCarreta().getPlaca();
        }

        SXSSFWorkbook book = new SXSSFWorkbook(100);
        XSSFWorkbook xbook = book.getXSSFWorkbook();
        SXSSFSheet sheet = book.createSheet();
        sheet.trackAllColumnsForAutoSizing();
        logger.debug("Ingresando downloadExcelSXLSX: 1");
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, NAME_SHEET);

        String fecha = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                .format(LocalDateTime.now());

        //Color
        Color colorGris = new Color(154, 159, 154);
        Color colorGrisClaro = new Color(255, 255, 255);

        //Letra
        XSSFFont fontTituloP = xbook.createFont();
        fontTituloP.setBold(true);

        XSSFFont font5 = xbook.createFont();
        font5.setFontHeightInPoints((short) 5);

        XSSFFont font6 = xbook.createFont();
        font6.setFontHeightInPoints((short) 6);

        XSSFFont font7 = xbook.createFont();
        font7.setFontHeightInPoints((short) 7);

        XSSFFont font7Color = xbook.createFont();
        font7Color.setFontHeightInPoints((short) 7);
        font7Color.setColor(new XSSFColor(colorGrisClaro));

        XSSFFont font8 = xbook.createFont();
        font8.setFontHeightInPoints((short) 8);

        XSSFFont font10 = xbook.createFont();
        font10.setFontHeightInPoints((short) 10);

        XSSFFont fontHeader = xbook.createFont();
        fontHeader.setFontHeightInPoints((short) 8);
        fontHeader.setBold(true);

        //Celdas
        XSSFCellStyle styleNormas = xbook.createCellStyle();
        styleNormas.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        XSSFCellStyle styleFecha = xbook.createCellStyle();
        styleFecha.setAlignment(HorizontalAlignment.LEFT);
        styleFecha.setFont(font7);

        XSSFCellStyle styleFechaRevista = xbook.createCellStyle();
        styleFechaRevista.setAlignment(HorizontalAlignment.LEFT);
        styleFechaRevista.setFont(font7Color);

        XSSFCellStyle styleTituloP = xbook.createCellStyle();
        styleTituloP.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        styleTituloP.setFont(fontTituloP);
        styleTituloP.setBorderTop(BorderStyle.MEDIUM);

        XSSFCellStyle styleTitulo = xbook.createCellStyle();
        styleTitulo.setFont(fontHeader);
        styleTitulo.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        XSSFCellStyle styleTextoS = xbook.createCellStyle();
        styleTextoS.setFont(font8);
        styleTextoS.setBorderBottom(BorderStyle.THIN);
        styleTextoS.setBorderTop(BorderStyle.THIN);
        styleTextoS.setBorderLeft(BorderStyle.THIN);
        styleTextoS.setBorderRight(BorderStyle.THIN);

        XSSFCellStyle styleTextoSB = xbook.createCellStyle();
        styleTextoSB.setFont(font8);

        XSSFCellStyle styleTextosM = xbook.createCellStyle();
        styleTextosM.setFont(font7);
        styleTextosM.setWrapText(true);

        XSSFCellStyle styleTextos7 = xbook.createCellStyle();
        styleTextos7.setFont(font7);
        styleTextos7.setAlignment(HorizontalAlignment.JUSTIFY);

        XSSFCellStyle styleTextosL = xbook.createCellStyle();
        styleTextosL.setFont(font5);
        styleTextosL.setWrapText(true);
        styleTextosL.setAlignment(HorizontalAlignment.JUSTIFY);

        XSSFCellStyle styleTextosL5 = xbook.createCellStyle();
        styleTextosL5.setFont(font5);

        XSSFCellStyle styleTextosColor = xbook.createCellStyle();
        styleTextosColor.setFont(font8);
        styleTextosColor.setFillForegroundColor(new XSSFColor( colorGris, new DefaultIndexedColorMap()));
        styleTextosColor.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleTextosColor.setBorderBottom(BorderStyle.THIN);
        styleTextosColor.setBorderTop(BorderStyle.THIN);
        styleTextosColor.setBorderLeft(BorderStyle.THIN);
        styleTextosColor.setBorderRight(BorderStyle.THIN);
        styleTextosColor.setAlignment(HorizontalAlignment.CENTER);

        XSSFCellStyle styleTextosColorC = xbook.createCellStyle();
        styleTextosColorC.setFont(font8);
        styleTextosColorC.setFillForegroundColor(new XSSFColor( colorGris, new DefaultIndexedColorMap()));
        styleTextosColorC.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleTextosColorC.setAlignment(HorizontalAlignment.CENTER);

        XSSFCellStyle styleTextosColorA = xbook.createCellStyle();
        styleTextosColorA.setFont(font10);

        XSSFCellStyle styleTextoPColor = xbook.createCellStyle();
        styleTextoPColor.setFillForegroundColor(new XSSFColor( colorGris, new DefaultIndexedColorMap()));
        styleTextoPColor.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleTextoPColor.setWrapText(true);
        styleTextoPColor.setFont(font6);
        styleTextoPColor.setAlignment(HorizontalAlignment.CENTER);
        styleTextoPColor.setBorderBottom(BorderStyle.THIN);
        styleTextoPColor.setBorderTop(BorderStyle.THIN);
        styleTextoPColor.setBorderLeft(BorderStyle.THIN);
        styleTextoPColor.setBorderRight(BorderStyle.THIN);

        XSSFCellStyle styleTextosBorder = xbook.createCellStyle();
        styleTextosBorder.setBorderBottom(BorderStyle.THIN);

        //Columnas
        Row row0 = sheet.createRow(0);
        Cell cell1 =row0.createCell(0);
        cell1.setCellValue("380998 ");
        Cell cell2 =row0.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(0,0, 1, 5));
        cell2.setCellValue("NORMAS LEGALES");
        cell2.setCellStyle(styleNormas);
        Cell cell3 =row0.createCell(6);
        sheet.addMergedRegion(new CellRangeAddress(0,0, 6, 8));
        cell3.setCellValue("El Peruano \nLima, lunes 06 de Octubre del 2008" );
        cell3.setCellStyle(styleFechaRevista);

        Row row1 = sheet.createRow(1);
        Cell cell4 =row1.createCell(0);
        cell4.setCellValue("CONSTANCIA DE VERIFICACION DE PESOS Y MEDIDAS");
        cell4.setCellStyle(styleTituloP);
        CellRangeAddress regionConstancia = new CellRangeAddress(1,1, 0, 8);
        sheet.addMergedRegion(regionConstancia);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionConstancia, sheet);

        Row row2 = sheet.createRow(2);
        Cell cell5 =row2.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(2,2, 0, 8));
        cell5.setCellValue("ALMACENES, TERMINALES DE ALMACENAMIENTO, TERMINALES PORTUARIOS O AEROPORTUARIOS, GENERADORES, ");
        cell5.setCellStyle(styleTitulo);

        Row row3 = sheet.createRow(3);
        Cell cell6 =row3.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(3,3, 0, 8));
        cell6.setCellValue("DADORES O REMITENTES DE LA MERCANCIA");
        cell6.setCellStyle(styleTitulo);

        Row row4 = sheet.createRow(4);
        Cell cell61 =row4.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(4,4, 0, 8));
        cell61.setCellValue("DECRETO SUPREMO N° 058-2003-MTC REGLAMENTO NACIONAL DE VEHICULOS Y SUS NORMAS " +
                "MODIFICATORIAS");
        cell61.setCellStyle(styleTitulo);

        Row row6 = sheet.createRow(6);
        Cell cell7 =row6.createCell(7);
        cell7.setCellValue("N° Registro");
        cell7.setCellStyle(styleTextosColor);
        Cell cell8 =row6.createCell(8);
        cell8.setCellValue(guiaRemision.getIdTicketPesaje());
        cell8.setCellStyle(styleTextoS);

        Row row7 = sheet.createRow(7);
        Cell cell9 =row7.createCell(0);
        cell9.setCellValue("Fecha :");
        cell9.setCellStyle(styleTextoSB);
        Cell cell10 =row7.createCell(1);
        cell10.setCellValue(fecha);
        cell10.setCellStyle(styleTextoSB);

        Row row8 = sheet.createRow(8);
        Cell cell11 =row8.createCell(0);
        cell11.setCellValue("I) DATOS DEL GENERADOR DE CARGA:");


        Row row9 = sheet.createRow(9);
        Cell cell12 =row9.createCell(0);
        cell12.setCellValue("NOMBRE DE LA EMPRESA");
        cell12.setCellStyle(styleTextosColor);
        CellRangeAddress regionNombre =new CellRangeAddress(9,9, 0, 1);
        sheet.addMergedRegion(regionNombre);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionNombre, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionNombre, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionNombre, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionNombre, sheet);

        Cell cell121 =row9.createCell(2);
        cell121.setCellValue(razonSocial);
        cell121.setCellStyle(styleTextosL5);
        CellRangeAddress regionEmpresa = new CellRangeAddress(9,9, 2, 3);
        sheet.addMergedRegion(regionEmpresa);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionEmpresa, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionEmpresa, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionEmpresa, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionEmpresa, sheet);

        Cell cell122 =row9.createCell(4);
        cell122.setCellValue( "N° RUC");
        cell122.setCellStyle(styleTextosColor);
        Cell cell123 =row9.createCell(5);
        cell123.setCellValue(ruc);
        cell123.setCellStyle(styleTextoS);
        CellRangeAddress regionRuc = new CellRangeAddress(9,9, 5, 6);
        sheet.addMergedRegion(regionRuc);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionRuc, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionRuc, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionRuc, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionRuc, sheet);

        Cell cell124 =row9.createCell(7);
        cell124.setCellValue("TELF.");
        cell124.setCellStyle(styleTextosColor);
        Cell cell125 =row9.createCell(8);
        cell125.setCellValue(telf);
        cell125.setCellStyle(styleTextoS);

        Row row10 = sheet.createRow(10);
        Cell cell13 =row10.createCell(0);
        cell13.setCellValue("Direccion");
        cell13.setCellStyle(styleTextosColor);
        Cell cell131 =row10.createCell(1);
        CellRangeAddress regionDireccion = new CellRangeAddress(10,10, 1, 8);
        sheet.addMergedRegion(regionDireccion);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionDireccion, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionDireccion, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionDireccion, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionDireccion, sheet);
        cell131.setCellValue(direccion);
        cell131.setCellStyle(styleTextoS);

        Row row11 = sheet.createRow(11);
        Cell cellNombre =row11.createCell(0);
        cellNombre.setCellValue("DISTRITO");
        cellNombre.setCellStyle(styleTextosColor);

        Cell cellEmpresa =row11.createCell(1);
        cellEmpresa.setCellValue(distrito);
        cellEmpresa.setCellStyle(styleTextoS);
        CellRangeAddress regionDistrito = new CellRangeAddress(11,11, 1, 2);
        sheet.addMergedRegion(regionDistrito);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionDistrito, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionDistrito, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionDistrito, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionDistrito, sheet);

        Cell cellNumero =row11.createCell(3);
        cellNumero.setCellValue("PROVINCIA");
        cellNumero.setCellStyle(styleTextosColor);
        CellRangeAddress regionProvincia = new CellRangeAddress(11,11, 3, 4);
        sheet.addMergedRegion(regionProvincia);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionProvincia, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionProvincia, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionProvincia, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionProvincia, sheet);

        Cell cellRuc =row11.createCell(5);
        cellRuc.setCellValue(provincia);
        cellRuc.setCellStyle(styleTextoS);

        Cell cellTelefono =row11.createCell(6);
        cellTelefono.setCellValue("DEPARTAMENTO");
        cellTelefono.setCellStyle(styleTextosColor);
        CellRangeAddress regionDepartamento = new CellRangeAddress(11,11, 6, 7);
        sheet.addMergedRegion(regionDepartamento);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionDepartamento, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionDepartamento, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionDepartamento, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionDepartamento, sheet);

        Cell cellDept =row11.createCell(8);
        cellDept.setCellValue(departamento);
        cellDept.setCellStyle(styleTextoS);

        Row row12 = sheet.createRow(12);
        Cell cell14 =row12.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(12,12, 0, 3));
        cell14.setCellValue( "II) TIPO DE MERCANCIA TRANSPORTADA: ");
        Cell cellProducto =row12.createCell(4);
        sheet.addMergedRegion(new CellRangeAddress(12,12, 4, 5));
        cellProducto.setCellValue(guiaRemision.getzProducto());
        cellProducto.setCellStyle(styleTextosColorA);

        Row row13 = sheet.createRow(13);
        Cell cell15 =row13.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(13,13, 0, 3));
        cell15.setCellValue("Según Guía de Remisión que se Adjunta: ");
        cell15.setCellStyle(styleTextoSB);
        Cell cellGuia =row13.createCell(4);
        sheet.addMergedRegion(new CellRangeAddress(13,13, 4, 5));
        cellGuia.setCellValue(guiaRemision.getSerieGuia() +" - "+ guiaRemision.getNroGuia());
        cellGuia.setCellStyle(styleTextoSB);
        cellGuia.setCellStyle(styleTextosColorA);

        Row row14 = sheet.createRow(14);
        Cell cell16 =row14.createCell(0);
        cell16.setCellValue("III) TIPO DE CONTROL EFECTUADO:");

        Row row15 = sheet.createRow(15);
        Cell cell17 =row15.createCell(0);
        cell17.setCellValue( "BALANZA");
        cell17.setCellStyle(styleTextosColor);
        Cell cell171 =row15.createCell(1);
        cell171.setCellValue( "X");
        cell171.setCellStyle(styleTextoS);
        Cell cell172 =row15.createCell(2);
        cell172.setCellValue( "SOFTWARE");
        cell172.setCellStyle(styleTextosColor);
        Cell cell173 =row15.createCell(3);
        cell173.setCellValue( "");
        cell173.setCellStyle(styleTextoS);
        cell172.setCellStyle(styleTextosColor);
        Cell cell174 =row15.createCell(4);
        cell174.setCellValue( "CUBICACION");
        cell174.setCellStyle(styleTextosColor);
        Cell cell175 =row15.createCell(5);
        cell175.setCellValue( "");
        cell175.setCellStyle(styleTextoS);
        Cell cell176 =row15.createCell(6);
        cell176.setCellValue( "OTROS");
        cell176.setCellStyle(styleTextosColor);
        Cell cell177 =row15.createCell(7);
        cell177.setCellValue( "");
        cell177.setCellStyle(styleTextoS);

        Row row16 = sheet.createRow(16);
        Cell cell18 =row16.createCell(0);
        cell18.setCellValue("IV) DATOS DEL VEHICULO:");

        Row row17 = sheet.createRow(17);
        Cell cell19 =row17.createCell(0);
        cell19.setCellValue("PLACAS (camión, tractor, remolque, Semiremolque, carreta)");
        cell19.setCellStyle(styleTextoPColor);
        CellRangeAddress regionPlaca = new CellRangeAddress(17,19, 0, 0);
        sheet.addMergedRegion(regionPlaca);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionPlaca, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionPlaca, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionPlaca, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionPlaca, sheet);

        Cell dimension =row17.createCell(1);
        dimension.setCellValue("DIMENSION TOTAL DEL VEHICULO (INCLUIDA LA MERCANCIA)");
        dimension.setCellStyle(styleTextoPColor);
        CellRangeAddress regionDimension = new CellRangeAddress(17,18, 1, 3);
        sheet.addMergedRegion(regionDimension);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionDimension, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionDimension, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionDimension, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionDimension, sheet);

        Cell config =row17.createCell(4);
        config.setCellValue("CONFIGURACION VEHICULAR");
        config.setCellStyle(styleTextoPColor);
        CellRangeAddress regionConfiguracion = new CellRangeAddress(17,19, 4, 4);
        sheet.addMergedRegion(regionConfiguracion);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionConfiguracion, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionConfiguracion, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionConfiguracion, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionConfiguracion, sheet);

        Cell pesoVehicular =row17.createCell(5);
        pesoVehicular.setCellValue("PESO BRUTO VEHICULAR MAX. PERMITIDO (Kg.) (1) ");
        CellRangeAddress regionPesoB = new CellRangeAddress(17,19, 5, 5);
        sheet.addMergedRegion(regionPesoB);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionPesoB, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionPesoB, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionPesoB, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionPesoB, sheet);

        pesoVehicular.setCellStyle(styleTextoPColor);
        Cell pesoTotal =row17.createCell(6);
        pesoTotal.setCellValue("PESO BRUTO TOTAL TRANSPORTADO (Kg) (2)");
        CellRangeAddress regionPesoBt = new CellRangeAddress(17,19, 6, 6);
        sheet.addMergedRegion(regionPesoBt);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionPesoBt, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionPesoBt, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionPesoBt, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionPesoBt, sheet);

        pesoTotal.setCellStyle(styleTextoPColor);
        Cell pesoEje =row17.createCell(7);
        pesoEje.setCellValue("PBMax. Para no control De pesos por ejes (2)");
        CellRangeAddress regionPb= new CellRangeAddress(17,19, 7, 7);
        sheet.addMergedRegion(regionPb);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionPb, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionPb, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionPb, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionPb, sheet);

        pesoEje.setCellStyle(styleTextoPColor);
        Cell bonificacion =row17.createCell(8);
        bonificacion.setCellValue("PBMax. Para no control de Pesos por ejes (DS 006-2008-MTC)(Kg) Con Bonificaciones" +
                " x Susp. Neu. y Neumas Extraanch (3)");
        bonificacion.setCellStyle(styleTextoPColor);
        CellRangeAddress regionPbm = new CellRangeAddress(17,19, 8, 8);
        sheet.addMergedRegion(regionPbm);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionPbm, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionPbm, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionPbm, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionPbm, sheet);

        Row row19 = sheet.createRow(19);
        Cell cellLargo =row19.createCell(1);
        cellLargo.setCellValue("LARGO              (mt)");
        cellLargo.setCellStyle(styleTextoPColor);
        Cell cellAncho =row19.createCell(2);
        cellAncho.setCellValue("ANCHO         (mt)");
        cellAncho.setCellStyle(styleTextoPColor);
        Cell cellAlto =row19.createCell(3);
        cellAlto.setCellValue("ALTURA             (mt)");
        cellAlto.setCellStyle(styleTextoPColor);


        Row row20 = sheet.createRow(20);
        Cell dato0 =row20.createCell(0);
        dato0.setCellStyle(styleTextoS);
        dato0.setCellValue(placaTransporte);
        Cell dato1 =row20.createCell(1);
        dato1.setCellStyle(styleTextoS);
        Cell dato2 =row20.createCell(2);
        dato2.setCellStyle(styleTextoS);
        Cell dato3 =row20.createCell(3);
        dato3.setCellStyle(styleTextoS);
        Cell dato6 =row20.createCell(6);
        dato6.setCellValue(pesoFinal);

        Row row21 = sheet.createRow(21);
        Cell dato210 =row21.createCell(0);
        dato210.setCellStyle(styleTextoS);
        dato210.setCellValue(placaCarreta);
        Cell dato211 =row21.createCell(1);
        dato211.setCellStyle(styleTextoS);
        Cell dato212 =row21.createCell(2);
        dato212.setCellStyle(styleTextoS);
        Cell dato213 =row21.createCell(3);
        dato213.setCellStyle(styleTextoS);

        Row row22 = sheet.createRow(22);
        Cell dato220 =row22.createCell(0);
        dato220.setCellStyle(styleTextoS);
        Cell dato221 =row22.createCell(1);
        dato221.setCellStyle(styleTextoS);
        Cell dato222 =row22.createCell(2);
        dato222.setCellStyle(styleTextoS);
        Cell dato223 =row22.createCell(3);
        dato223.setCellStyle(styleTextoS);

        CellRangeAddress region200 = new CellRangeAddress(20,22, 4, 4);
        sheet.addMergedRegion(region200);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region200, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, region200, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region200, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region200, sheet);

        CellRangeAddress region210 = new CellRangeAddress(20,22, 5, 5);
        sheet.addMergedRegion(region210);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region210, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, region210, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region210, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region210, sheet);

        CellRangeAddress region220 = new CellRangeAddress(20,22, 6, 6);
        sheet.addMergedRegion(region220);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region220, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, region220, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region220, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region220, sheet);

        CellRangeAddress region230 = new CellRangeAddress(20,22, 7, 7);
        sheet.addMergedRegion(region230);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region230, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, region230, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region230, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region230, sheet);

        CellRangeAddress region240 = new CellRangeAddress(20,22, 8, 8);
        sheet.addMergedRegion(region240);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region240, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, region240, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region240, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region240, sheet);


        Row row23 = sheet.createRow(23);
        Cell cell20 =row23.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(23,23, 0, 8));
        cell20.setCellValue("(1) Se obtiene del anexo IV del RNV D5 058-2003");
        cell20.setCellStyle(styleTextos7);

        Row row24 = sheet.createRow(24);
        Cell cell21 =row24.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(24,25, 0, 8));
        cell21.setCellValue("(2) El generador debera controlar que el peso bruto no sea mayor" +
                "que el 95% de la sumatoria de los pesos por eje o conjunto de ejes indicados en el anexo IV del RNV");
        cell21.setCellStyle(styleTextos7);

        Row row26 = sheet.createRow(26);
        Cell cell22 =row26.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(26,26, 0, 8));
        cell22.setCellValue("(3) PB MAX. Para no control p x ejes a vehiculos con bonificaciones" +
                "permitidas para susp. Neumatica y neumat. Extra anchos");
        cell22.setCellStyle(styleTextos7);

        Row row28 = sheet.createRow(28);
        Cell cell23 =row28.createCell(0);
        cell23.setCellValue("V) CONTROL DE PESOS POR EJE O CONJUNTO DE EJES:");

        Row row29 = sheet.createRow(29);
        Cell cell24 =row29.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(29,29, 0, 8));
        cell24.setCellValue("Para aquellos vehículos que exceden el 95% de la suma de los pesos por ejes");
        cell24.setCellStyle(styleTextos7);

        Row row30 = sheet.createRow(30);
        Cell cell25 =row30.createCell(0);
        cell25.setCellValue("DISTRIBUCION DE PESOS POR CONJUNTO DE EJES EN KG.");
        cell25.setCellStyle(styleTextosColor);
        CellRangeAddress regionDistribucion = new CellRangeAddress(30,30, 0, 8);
        sheet.addMergedRegion(regionDistribucion);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionDistribucion, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionDistribucion, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionDistribucion, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionDistribucion, sheet);


        Row row31 = sheet.createRow(31);
        Cell cell26 =row31.createCell(0);
        cell26.setCellValue("PESOS");
        cell26.setCellStyle(styleTextoPColor);
        CellRangeAddress regionPeso = new CellRangeAddress(31,31, 0, 2);
        sheet.addMergedRegion(regionPeso);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionPeso, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionPeso, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionPeso, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionPeso, sheet);

        Cell peso1 =row31.createCell(3);
        peso1.setCellValue("1er cjto");
        peso1.setCellStyle(styleTextoPColor);
        Cell peso2 =row31.createCell(4);
        peso2.setCellValue("2er cjto");
        peso2.setCellStyle(styleTextoPColor);
        Cell peso3 =row31.createCell(5);
        peso3.setCellValue("3to cjto");
        peso3.setCellStyle(styleTextoPColor);
        Cell peso4 =row31.createCell(6);
        peso4.setCellValue("4to cjto");
        peso4.setCellStyle(styleTextoPColor);
        Cell peso5 =row31.createCell(7);
        peso5.setCellValue("5to cjto");
        peso5.setCellStyle(styleTextoPColor);
        Cell peso6 =row31.createCell(8);
        peso6.setCellValue("6to cjto");
        peso6.setCellStyle(styleTextoPColor);

        Row row32 = sheet.createRow(32);
        CellRangeAddress regionDato0 = new CellRangeAddress(32,32, 0, 2);
        sheet.addMergedRegion(regionDato0);
        RegionUtil.setBorderBottom(BorderStyle.THIN, regionDato0, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionDato0, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, regionDato0, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, regionDato0, sheet);

/*        for (int i = 0; i < detalleTicketList.size(); i++){
            Cell pesoDato =row32.createCell(i+3);
            pesoDato.setCellValue(detalleTicketList.get(i).getPeso_neto());
            pesoDato.setCellStyle(styleTextoS);
        }*/
        Cell pesoDato3 =row32.createCell(3);
        pesoDato3.setCellValue("");
        pesoDato3.setCellStyle(styleTextoS);
        Cell pesoDato4 =row32.createCell(4);
        pesoDato4.setCellValue("");
        pesoDato4.setCellStyle(styleTextoS);
        Cell pesoDato5 =row32.createCell(5);
        pesoDato5.setCellValue("");
        pesoDato5.setCellStyle(styleTextoS);
        Cell pesoDato6 =row32.createCell(6);
        pesoDato6.setCellValue("");
        pesoDato6.setCellStyle(styleTextoS);

        Cell totalNombre =row32.createCell(7);
        totalNombre.setCellStyle(styleTextoS);
        totalNombre.setCellValue("");
        Cell pesoDato8 =row32.createCell(8);
        pesoDato8.setCellStyle(styleTextoS);

        Row row33 = sheet.createRow(33);
        Cell cell27 =row33.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(33,33, 0, 8));
        cell27.setCellValue("DECRETO SUPREMO N° 058-2003-MTC, modificado por D.S. N°006-2008-MTC," +
                " ANEXO IV: PESOS Y MEDIDAS");
        cell27.setCellStyle(styleTextosM);

        Row row34 = sheet.createRow(34);
        Cell cell28 =row34.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(34,35, 0, 8));
        cell28.setCellValue("Articulo 37°.- Pesos Max. Permitidos: (…) están exonerados del " +
                "control de pesos por eje o conjunto de ejes, los vehículos  o combinaciones vehiculares " +
                "que transiten con un peso bruto vehicular que no exceda el 95% de la sumatoria de pesos " +
                "por eje o conjunto de ejes, en tanto este valor no supere el peso bruto  vehicular máximo " +
                "permitido por el presente reglamento o sus normas complementarias");
        cell28.setCellStyle(styleTextosM);

        Row row36 = sheet.createRow(36);
        Cell cell29 =row36.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(36,36, 0, 8));
        cell29.setCellValue("OBSERVACIONES:.........................................................................." +
                "...................................................................................................." +
                "......................................................................");

        cell29.setCellStyle(styleTextos7);

        Row row38 = sheet.createRow(38);
        Cell cell400 =row38.createCell(0);
        cell400.setCellStyle(styleTextosBorder);
        Cell cell401 =row38.createCell(1);
        cell401.setCellStyle(styleTextosBorder);
        Cell cell402 =row38.createCell(2);
        cell402.setCellStyle(styleTextosBorder);

        Row row39 = sheet.createRow(39);
        Cell cell30 =row39.createCell(0);
        cell30.setCellValue("Representante del Generador de Carga");
        cell30.setCellStyle(styleTextos7);
        CellRangeAddress regionRepresentante = new CellRangeAddress(39,39, 0, 2);
        sheet.addMergedRegion(regionRepresentante);
        RegionUtil.setBorderTop(BorderStyle.THIN, regionRepresentante, sheet);


        Row row40 = sheet.createRow(40);
        Cell cell31 =row40.createCell(1);
        cell31.setCellValue("Firma y Sello");
        cell31.setCellStyle(styleTextos7);

        Row row41 = sheet.createRow(41);
        Cell cell32 =row41.createCell(0);
        cell32.setCellValue("NOTA:");
        cell32.setCellStyle(styleTextos7);

        Row row42 = sheet.createRow(42);
        Cell cell33 =row42.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(42,48, 0, 8));
        cell33.setCellValue("1.- LO CONSIGNADO EN EL PRESENTE FORMATO TIENE CARÁCTER DE DECLARACIÓN JURADA, " +
                "POR LO QUE ESTARÁ SUJETO A LO ESTABLECIDO EN EL ART. 32 NUMERAL 32.3 DE LA LEY N° 27444; SIN PERJUICIO " +
                "D E LA SANCIÓN ADMINISTRATIVA CORRESPONDIENTE. TENIENDO QUE CUMPLIR QUIEN GENERA LA CARGA EL LLENADO DE" +
                " LA PRESENTE CONSTANCIA\n" +
                "2.- Solo para Terminales Portuarios, Aeroportuarios, Almacenes Aduaneros y de carga de Hidrocarburos," +
                " LA GUÍA DE SALIDA , CONSTANCIA DE PESO O TICKET DE PESO DE SALIDA, reemplazará a la presente " +
                "constancia, la cual deberá contener lo indicado en el punto N° I y adicionalmente las Placas, Tipo " +
                "de Vehículo y Peso Bruto Total del Vehículo. Cuando el destino de la mercancía es local no se " +
                "requiere la emisión de esta constancia de control de pesos y medidas.\n" +
                "3.- Del punto IV - \"Dimensión Total del Vehículo y Carga\", será llenado cuando excedan las " +
                "dimensiones permitidas.\n" +
                "4- Para el transporte de contenedores vacios la presentación del EIR (Equipment Interchance" +
                " Reception) reemplaza al presente formato; Asimismo, los contenedores no están sujetos al control " +
                "de pesos por ejes.\n" +
                "5.- Para el control en las balanzas de las Estaciones de Pesaje; \"Peso Bruto Total Transportado\"," +
                " se consideraran las tolerancias del 3% vigente en el pesaje dinámico\n" +
                "6.- De no consignar los datos en el punto V, cuando corresponda, el generador declara que los pesos " +
                "por eje están dentro de lo permitido en el RNV.\n");
        cell33.setCellStyle(styleTextosL);


        Row row49 = sheet.createRow(49);
        Cell cell34 =row49.createCell(0);
        cell34.setCellValue( "260533-1");
        cell34.setCellStyle(styleTextos7);

        return book;
    }

    @Override
    public JSONObject findByIdGuiaRemision(Integer id) throws FileNotFoundException {


        GuiaRemision ListGuiaRemision =  guiaRemisionRepository.findByIdGuiaRemision(id);
        File localPath = ResourceUtils.getFile("classpath:reportes/");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh_mm_ss");
        String fileName = "reportesguiaremision-"+ListGuiaRemision.getId()+"-"+formatter.format(LocalDateTime.now())+".txt";
        String fullNamePath = localPath+"/"+fileName;
        //String filenamePrueba= "pruebaa.txt";
        String sftpPath = "/";
        String sftpHost = "200.41.106.103";
        String sftpPort = "2222";
        String sftpUser = "sftpcope";
        String sftpPassword = "S3t%$&p30\"/";

        JSONObject objerror = new JSONObject();
        objerror.put("message", "error al crear txt");
        objerror.put("status", new Integer(500));


        JSONObject objeSuccess = new JSONObject();
        objeSuccess.put("archivo", fileName);
        objeSuccess.put("status", new Integer(200));
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(sftpUser, sftpHost, Integer.valueOf(sftpPort));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(sftpPassword);
            logger.error("Connecting------");
            System.out.println("Connecting------");
            session.connect();
            System.out.println("Established Session");
            logger.error("Established Session");

            Channel channel = session.openChannel("sftp");
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.connect();

            try {

                //BufferedWriter parametrosEntrada = new BufferedWriter(new FileWriter(localPath+"/"+filenamePrueba, true));
                File file = new File(localPath+fileName);
                logger.error("file: {}",file);
                logger.error("localPath: {}",localPath);
                BufferedWriter parametrosEntrada = new BufferedWriter(new FileWriter(file, true));


                String contenido = ListGuiaRemision.getTransporte().getPlaca()+"|"+ListGuiaRemision.getSerieGuia()+"|"+ListGuiaRemision.getRuc()+"|"+ListGuiaRemision.getRazonSocial()+"|"+ListGuiaRemision.getChofer().getNombre()+"|"+ListGuiaRemision.getDireccionDestino()+"|"+ListGuiaRemision.getCarreta().getPlaca();
                parametrosEntrada.write(contenido);
                logger.error("contenido: {}",contenido);

                parametrosEntrada.close();
                sftpChannel.put(localPath+fileName,sftpPath);
                Thread.sleep(7000);
                file.delete();
            } catch (IOException ioe) {

                return objerror;
            }

            sftpChannel.disconnect();
            session.disconnect();

            logger.error("Disconnected from sftp");

        } catch (Exception e) {
            return objerror;
        }

        return objeSuccess;
    }

}
