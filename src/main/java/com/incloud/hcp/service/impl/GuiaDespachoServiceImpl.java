package com.incloud.hcp.service.impl;

import com.google.gson.Gson;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.ResponsableAlmacen;
import com.incloud.hcp.domain.almacen.GuiaDespacho;
import com.incloud.hcp.domain.almacen.GuiaDespachoDetalle;
import com.incloud.hcp.domain.almacen.OrdenDespacho;
import com.incloud.hcp.domain.almacen.OrdenDespachoDetalle;
import com.incloud.hcp.dto.GuiaDespachoDto;
import com.incloud.hcp.dto.PosicionCantidadDto;
import com.incloud.hcp.enums.*;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.service.GuiaDespachoService;
import com.incloud.hcp.service.notificacion.GuiaDespachoEstadoNotificacion;
import com.incloud.hcp.util.DateUtils;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GuiaDespachoServiceImpl implements GuiaDespachoService {
    private GuiaDespachoRepository guiaDespachoRepository;
    private OrdenDespachoRepository ordenDespachoRepository;
    private ProveedorRepository proveedorRepository;
    private OrdenDespachoDetalleRepository ordenDespachoDetalleRepository;
    private GuiaDespachoDetalleRepository guiaDespachoDetalleRepository;
    private GuiaDespachoEstadoNotificacion guiaDespachoEstadoNotificacion;
    private ResponsableAlmacenRepository responsableAlmacenRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    public GuiaDespachoServiceImpl(GuiaDespachoRepository guiaDespachoRepository,
                                   OrdenDespachoRepository ordenDespachoRepository,
                                   ProveedorRepository proveedorRepository,
                                   OrdenDespachoDetalleRepository ordenDespachoDetalleRepository,
                                   GuiaDespachoDetalleRepository guiaDespachoDetalleRepository,
                                   GuiaDespachoEstadoNotificacion guiaDespachoEstadoNotificacion,
                                   ResponsableAlmacenRepository responsableAlmacenRepository) {
        this.guiaDespachoRepository = guiaDespachoRepository;
        this.ordenDespachoRepository = ordenDespachoRepository;
        this.proveedorRepository = proveedorRepository;
        this.ordenDespachoDetalleRepository = ordenDespachoDetalleRepository;
        this.guiaDespachoDetalleRepository = guiaDespachoDetalleRepository;
        this.guiaDespachoEstadoNotificacion = guiaDespachoEstadoNotificacion;
        this.responsableAlmacenRepository = responsableAlmacenRepository;
    }
    @Override
    public Integer ingresarNuevaGuiaDespacho(GuiaDespachoDto guiaDespachoDto, String ruc) {
        Gson gson = new Gson();
        try {
            logger.error("guiaDespachoDto_ " + guiaDespachoDto);
            GuiaDespacho guiaDespacho = new GuiaDespacho();
            guiaDespacho.setIdEstadoDespacho(GuiaDespachoEstadoEnum.REGISTRADA.getId());
            guiaDespacho.setFechaCreacion(guiaDespachoDto.getFechaCreacion());
            guiaDespacho.setFechaEntrega(guiaDespachoDto.getFechaEntrega());
            guiaDespacho.setCentro(guiaDespachoDto.getCentro());
            guiaDespacho.setDenominacionCentro(guiaDespachoDto.getDenominacionCentro());
            guiaDespacho.setLugarEntrega(guiaDespachoDto.getLugarEntrega());
            guiaDespacho.setNumeroGuiaRemision(guiaDespachoDto.getNumeroGuiaRemision());

            guiaDespacho.setNumeroOrdenDespacho(guiaDespachoDto.getNumeroOrdenDespacho());

            OrdenDespacho ordenDespacho = this.ordenDespachoRepository.getODActivaByNumero(guiaDespachoDto.getNumeroOrdenDespacho());
            guiaDespacho.setIdOrdenDespacho(ordenDespacho.getId());

            Proveedor proveedor = this.proveedorRepository.getProveedorByRuc(guiaDespachoDto.getRucProveedor());
            guiaDespacho.setProveedorRuc(proveedor.getRuc());
            guiaDespacho.setProveedorRazonSocial(proveedor.getRazonSocial());

            guiaDespacho.setUsuarioCreo(ruc);

            GuiaDespacho guiaDespachoSave = this.guiaDespachoRepository.save(guiaDespacho);
            Integer idGuiaDespacho = guiaDespacho.getId();

            List<PosicionCantidadDto> posicionCantidadGD = guiaDespachoDto.getListCantPos();

            for (int i = 0; i < posicionCantidadGD.size(); i++) {
                GuiaDespachoDetalle guiaDespachoDetalle = new GuiaDespachoDetalle();
                guiaDespachoDetalle.setIdGuiaDespacho(idGuiaDespacho);
                guiaDespachoDetalle.setCantidadEsteDespacho(posicionCantidadGD.get(i).getCantidad());
                guiaDespachoDetalle.setPosicion(posicionCantidadGD.get(i).getPosicion());

                OrdenDespachoDetalle ordenDespachoDetalle = this.ordenDespachoDetalleRepository.getByODPos(guiaDespachoDto.getNumeroOrdenDespacho(), posicionCantidadGD.get(i).getPosicion());
                guiaDespachoDetalle.setIdOrdenDespachoDetalle(ordenDespachoDetalle.getId());
                guiaDespachoDetalle.setNumeroOrdenDespacho(ordenDespachoDetalle.getNumeroOrdenCompra());
                guiaDespachoDetalle.setCantidadOriginal(ordenDespachoDetalle.getCantidad());

                guiaDespachoDetalle.setCodigoMaterial(ordenDespachoDetalle.getCodigoSapBienServicio());
                guiaDespachoDetalle.setUnidadMedida(ordenDespachoDetalle.getUnidadMedidaBienServicio());
                guiaDespachoDetalle.setDescripcionMaterial(ordenDespachoDetalle.getDescripcionBienServicio());

                guiaDespachoDetalle.setCentro(ordenDespachoDetalle.getCodigoSapCentro());
                guiaDespachoDetalle.setDenominacionCentro(ordenDespachoDetalle.getDenominacionCentro());

                guiaDespachoDetalle.setCodigoSapAlmacen(ordenDespachoDetalle.getCodigoSapAlmacen());
                guiaDespachoDetalle.setDenominacionAlmacen(ordenDespachoDetalle.getDenominacionAlmacen());

                this.guiaDespachoDetalleRepository.save(guiaDespachoDetalle);
            }

            List<ResponsableAlmacen> listResponsableAlmacen = this.responsableAlmacenRepository.findAll();
            String aprobador = null;
            String accion = "Registrada";
            // enviar correo
            guiaDespachoEstadoNotificacion.enviar(guiaDespachoSave, proveedor, listResponsableAlmacen, aprobador, accion);

            return idGuiaDespacho;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer descartarGuiaDespacho(Integer id, String ruc) {

        Optional<GuiaDespacho> guiaDespachoOpt = guiaDespachoRepository.findById(id);

        if (!guiaDespachoOpt.isPresent()) {
            return -422; // o el código que manejes
        }

        GuiaDespacho guia = guiaDespachoOpt.get();

        guia.setIdEstadoDespacho(GuiaDespachoEstadoEnum.DESCARTADA.getId());
        guia.setFechaDescarte(DateUtils.getCurrentTimestamp());

        guiaDespachoRepository.save(guia);

        return guia.getId(); // o el id que corresponda
    }

    @Override
    public Integer rechazarGuiaDespacho(Integer id, String motivo, String codigoUsuario) {

        Optional<GuiaDespacho> guiaDespachoOpt = guiaDespachoRepository.findById(id);

        if (!guiaDespachoOpt.isPresent()) {
            return -422; // o el código que manejes
        }

        GuiaDespacho guia = guiaDespachoOpt.get();

        guia.setIdEstadoDespacho(GuiaDespachoEstadoEnum.RECHAZADA.getId());
        guia.setFechaRechazo(DateUtils.getCurrentTimestamp());
        guia.setMotivoRechazo(motivo);

        List<ResponsableAlmacen> listResponsableAlmacen = new ArrayList<>();
        //List<ResponsableAlmacen> listResponsableAlmacen = this.responsableAlmacenRepository.findAll();
        Optional<ResponsableAlmacen> responsable = this.responsableAlmacenRepository.getResponsableCodigo(codigoUsuario);
        String aprobador = "";
        if(responsable.isPresent()){
            aprobador = responsable.get().getEmail();
        }

        String accion = "Rechazada";
        Proveedor proveedor = this.proveedorRepository.getProveedorByRuc(guia.getProveedorRuc());
        // enviar correo
        guiaDespachoEstadoNotificacion.enviar(guia, proveedor, listResponsableAlmacen, aprobador, accion);

        guiaDespachoRepository.save(guia);

        return guia.getId();
    }

    @Override
    public String aprobarGuiaDespacho(Integer id, GuiaDespachoDto guiaDespachoDto, String ruc) throws Exception{

        try{
            logger.error("GUIADESPACHO RFC /// REQUEST: ");

            GuiaDespacho guia = this.guiaDespachoRepository.findByIdGuiaDespacho(id);
            if (guia == null) {
                throw new Exception("Guía de despacho no encontrada");
            }

            guia.setIdEstadoDespacho(GuiaDespachoEstadoEnum.INGRESADA.getId());
            guia.setFechaEntrega(guiaDespachoDto.getFechaEntrega());
            guia.setCentro(guiaDespachoDto.getCentro());
            guia.setDenominacionCentro(guiaDespachoDto.getDenominacionCentro());
            guia.setLugarEntrega(guiaDespachoDto.getLugarEntrega());
            guia.setNumeroGuiaRemision(guiaDespachoDto.getNumeroGuiaRemision());
            guia.setFechaContabilizacion(guiaDespachoDto.getFechaContabilizacion());
            guia.setObservaciones(guiaDespachoDto.getObservacion());

            guia.setUsuarioModifico(ruc);

            this.guiaDespachoRepository.save(guia);

            List<PosicionCantidadDto> posicionCantidadGD = guiaDespachoDto.getListCantPos();
            for (int i = 0; i < posicionCantidadGD.size(); i++) {
                GuiaDespachoDetalle guiaDespachoDetalle = this.guiaDespachoDetalleRepository.findGuiaDetalleById(posicionCantidadGD.get(i).getIdDetalle());
                guiaDespachoDetalle.setCantidadEsteDespacho(posicionCantidadGD.get(i).getCantidad());
                this.guiaDespachoDetalleRepository.save(guiaDespachoDetalle);
            }

            List<GuiaDespachoDetalle> listGuiaDetalle = this.guiaDespachoDetalleRepository.listFindGuiaDetalleByIdGuia(guia.getId());

            String FUNCION_RFC = "ZPE_MM_ENTRADA_MOV";

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            this.mapFilters(jCoFunction, guia, listGuiaDetalle);
            jCoFunction.execute(destination);

            JCoParameterList exportParameterList = jCoFunction.getExportParameterList();
            String codigoDocumentoSap = exportParameterList.getString("ET_MAT_DOC");
            String ejercicio = exportParameterList.getString("ET_MAT_EJE");

            JCoTable table = jCoFunction.getTableParameterList().getTable("IT_RETURN");
            List<SapLog> listSapLog = new ArrayList<>();
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
                    listSapLog.add(sapLog);
                } while (table.nextRow());
            }
            if(codigoDocumentoSap == null || codigoDocumentoSap.isEmpty()){
                throw new PortalException("No se pudo crear la guia. " + listSapLog);
            }

            guia.setDocumentoMaterial(codigoDocumentoSap);
            guia.setEjercicio(ejercicio);

            guiaDespachoRepository.save(guia);

            List<ResponsableAlmacen> listResponsableAlmacen = this.responsableAlmacenRepository.findAll();
            String aprobador = ruc;
            String accion = "Ingresada";
            Proveedor proveedor = this.proveedorRepository.getProveedorByRuc(guia.getProveedorRuc());
            // enviar correo
            guiaDespachoEstadoNotificacion.enviar(guia, proveedor, listResponsableAlmacen, aprobador, accion);

            return guia.getDocumentoMaterial();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }

    private void mapFilters(JCoFunction function, GuiaDespacho guia, List<GuiaDespachoDetalle> guiaDetalleList) {

        JCoParameterList paramList = function.getImportParameterList();
        JCoStructure cabecera = paramList.getStructure("IT_INPUT_CAB");
        cabecera.setValue("PSTNG_DATE", guia.getFechaContabilizacion());
        cabecera.setValue("DOC_DATE", guia.getFechaEntrega());

        String guiaFormatoSap = getGuiaRemisionFormateada(guia.getNumeroGuiaRemision());
        cabecera.setValue("REF_DOC_NO", guiaFormatoSap);
        cabecera.setValue("HEADER_TXT", guia.getObservaciones());

        logger.error(
                "[SAP-JCO][CABECERA] PSTNG_DATE={}, DOC_DATE={}, REF_DOC_NO={}, HEADER_TXT={}",
                guia.getFechaContabilizacion(),
                guia.getFechaEntrega(),
                guiaFormatoSap,
                guia.getObservaciones()
        );

        JCoParameterList tableParameterList = function.getTableParameterList();
        JCoTable posDetalle = tableParameterList.getTable("IT_INPUT_DETA");
        for (int i = 0; i < guiaDetalleList.size(); i++) {
            GuiaDespachoDetalle posicionDto = guiaDetalleList.get(i);

            posDetalle.appendRow();
            posDetalle.setValue("MATERIAL", posicionDto.getCodigoMaterial());
            posDetalle.setValue("PLANT", posicionDto.getCentro());
            posDetalle.setValue("ENTRY_QNT", posicionDto.getCantidadEsteDespacho());
            posDetalle.setValue("ENTRY_UOM", posicionDto.getUnidadMedida());
            posDetalle.setValue("PO_NUMBER", posicionDto.getNumeroOrdenDespacho());
            posDetalle.setValue("PO_ITEM", posicionDto.getPosicion());
            posDetalle.setValue("MVT_IND", "B");

            if(guia.getLugarEntrega().equals("PLANTA")){
                posDetalle.setValue("STGE_LOC", posicionDto.getCodigoSapAlmacen());
                posDetalle.setValue("MOVE_TYPE", "101");
            }
            else{
                posDetalle.setValue("MOVE_TYPE", "103");
            }
        }
    }

    public String getGuiaRemisionFormateada(String guia) {

        if (guia == null || !guia.contains("-")) {
            return guia;
        }

        String[] partes = guia.split("-");
        String codigo = partes[0];      // T002
        String numero = partes[1];      // 10982

        String numeroFormateado = String.format("%07d", Integer.parseInt(numero));

        return "09-0" + codigo + "-" + numeroFormateado;
    }
}
