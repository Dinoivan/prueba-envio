package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.CentroAlmacen;
import com.incloud.hcp.domain.balanza.*;
import com.incloud.hcp.dto.TicketPesajePDFdto;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.service.GenerarPDFservice;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GenerarPDFserviceImpl  implements GenerarPDFservice {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    @Autowired
    private DetalleTicketRepository detalleTicketRepository;
    @Autowired
    private TicketPesajeRepository ticketPesajeRepository;
    @Autowired
    private ChoferRepository choferRepository;
    @Autowired
    private CarretaRepository carretaRepository;
    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private TipoProductoRepository  tipoProductoRepository;

    @Autowired
    private CentroAlmacenRepository centroAlmacenRepository;
    @Autowired
    private TipoPesajeRepository tipoPesajeRepository;
    @Override
    public byte[]  generarPDFReporteGuia(Integer id){
        try {
            logger.error("[generarPDFReporteGuia]: Ingreso");
            DetalleTicket ListaSubTicket = detalleTicketRepository.findDetalleTicketBy(id);
            Integer idTicketTransporte = ListaSubTicket.getTicketPesaje().getTransporte().getId();
            Integer idTicketChofer = ListaSubTicket.getTicketPesaje().getChofer().getId();
            String tipoProducto = ListaSubTicket.getTipoProducto();

            DecimalFormat formatoPesos = new DecimalFormat("#,###");

            String pesoE = null;
            if(ListaSubTicket.getPeso_inicial() != null) {
                Integer pesoEntrada = ListaSubTicket.getPeso_inicial().intValue();
                pesoE = formatoPesos.format(pesoEntrada);
            }

            String pesoS = null;
            if(ListaSubTicket.getPeso_final() != null){
                Integer pesoSalida =ListaSubTicket.getPeso_final().intValue();
                pesoS = formatoPesos.format(pesoSalida);
            }

            String pesoN = null;
            if(ListaSubTicket.getPeso_neto() != null) {
                Integer pesoNeto = ListaSubTicket.getPeso_neto().intValue();
                pesoN = formatoPesos.format(pesoNeto);
            }

            String  operadorEntrada = ListaSubTicket.getTicketPesaje().getUsuarioCreador();
            String  operadorSalida= ListaSubTicket.getTicketPesaje().getUsuarioModificador();
            String  codTipoPesaje= ListaSubTicket.getTipoPesaje();
            String  unidadMedida= ListaSubTicket.getUnidadMedida();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat horaformatter = new SimpleDateFormat("hh:mm:s");
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

            //String fechaCreacion = formatter.format(ListaSubTicket.getTicketPesaje().getFechaCreacion());
            Calendar calendarActual = Calendar.getInstance();
            calendarActual.setTime(new Date());
            calendarActual.add(Calendar.HOUR, -5);
            Timestamp actual = new Timestamp(calendarActual.getTime().getTime());
            LocalDateTime fechaActual = actual.toLocalDateTime();
            String fechaCreacion = formatter.format(Timestamp.valueOf(fechaActual));
            Timestamp horaCreacionTk;
            logger.error(ListaSubTicket.getTicketPesaje().getId()+" getFechaEmisionPdf"+ListaSubTicket.getFechaEmisionPdf());
            if(ListaSubTicket.getFechaEmisionPdf() !=null) {
                horaCreacionTk = ListaSubTicket.getFechaEmisionPdf();
                fechaCreacion = formatter.format(ListaSubTicket.getFechaEmisionPdf());
            }else{
                horaCreacionTk = Timestamp.valueOf(fechaActual);//ListaSubTicket.getTicketPesaje().getFechaCreacion();
                detalleTicketRepository.updateFechaEmisionPdf(horaCreacionTk, ListaSubTicket.getId());

            }

            String fechaEntrada = null;
            Timestamp horaEntradaTk = null;
            if(ListaSubTicket.getFechaCreacion() != null) {
                fechaEntrada = formatter.format(ListaSubTicket.getFechaCreacion());
                horaEntradaTk = ListaSubTicket.getFechaCreacion();
            }

            //Optional<TicketPesaje> ticketPesaje = ticketPesajeRepository.findByIdTicket()
            String fechaSalida = null;
            Timestamp horaSalidaTk = null;
            if(ListaSubTicket.getFechaModificacion() != null) {
                fechaSalida = formatter.format(ListaSubTicket.getFechaModificacion());
                horaSalidaTk = ListaSubTicket.getFechaModificacion();
            }


            Optional<Transporte> transporte = transporteRepository.findById(idTicketTransporte);
            Optional<TicketPesaje> optionalTicketPesaje = ticketPesajeRepository.findById(ListaSubTicket.getTicketPesaje().getId());
            Optional<TipoProducto> producto = Optional.empty();
            if(tipoProducto != null && tipoProducto != "") {
                producto = Optional.ofNullable(tipoProductoRepository.findByCodigo(tipoProducto));
            }
            Optional<Chofer> chofer = choferRepository.findById(idTicketChofer);
            Optional<TipoPesaje> tipoPesaje = Optional.ofNullable(tipoPesajeRepository.findByCodigo(codTipoPesaje));

            TicketPesaje ticketPesaje = optionalTicketPesaje.get();
            String almacen = ListaSubTicket.getCodigoAlmacen();
            Optional<CentroAlmacen> centro = Optional.empty();
            centro = Optional.ofNullable(centroAlmacenRepository.getByNivelCodigoSap(ticketPesaje.getCodigoCentro()));
            String centroPDF = "";
            if(centro.isPresent()) {
                centroPDF = centro.get().getCodigoSap() + " - " + centro.get().getDenominacion();
            }

            TicketPesajePDFdto  ticketPesajePDFdto = new TicketPesajePDFdto();
            ticketPesajePDFdto.setPlaca_transporte(transporte.get().getPlaca());
            ticketPesajePDFdto.setNombre_chofer(chofer.get().getNombre() +" "+chofer.get().getApellidoPaterno()+" "+chofer.get().getApellidoMaterno());

            if(producto.isPresent()) {
                ticketPesajePDFdto.setProducto(producto.get().getDescripcion());
            } else {
                ticketPesajePDFdto.setProducto("");
            }
            ticketPesajePDFdto.setProceso(tipoPesaje.get().getDescripcion());
            logger.error("[generarPDFReporteGuia]: paso1");
            if (ListaSubTicket.getTicketPesaje().getCarreta()!=null){
                Integer idTicketCarreta = ListaSubTicket.getTicketPesaje().getCarreta().getId();
                //Optional<Carreta> carreta = carretaRepository.findById(idTicketCarreta);
                Optional<Transporte> carreta = transporteRepository.findById(idTicketCarreta);
                ticketPesajePDFdto.setPlaca_carreta(carreta.get().getPlaca());
                ticketPesajePDFdto.setModelo_carreta(carreta.get().getModelo());
            }else{
                ticketPesajePDFdto.setPlaca_carreta("");
                ticketPesajePDFdto.setModelo_carreta("");
            }
            logger.error("[generarPDFReporteGuia]: paso2");
            logger.error("ListaSubTicket: {}",ListaSubTicket);
            List<Map<String, Object>> reportListGuis = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("centro", Optional.ofNullable(centroPDF).orElse(" "));
            map.put("placa", Optional.ofNullable(ticketPesajePDFdto.getPlaca_transporte()).orElse(" "));
            map.put("subTicket", Optional.ofNullable(ListaSubTicket.getSubticket()).orElse(" "));
            map.put("ruc", Optional.ofNullable(ListaSubTicket.getTicketPesaje().getRucTransportista()).orElse(" "));
            map.put("cliente", Optional.ofNullable(ListaSubTicket.getTicketPesaje().getRazSocialTransportista()).orElse(" "));
            map.put("nombre_chofer", Optional.ofNullable(ticketPesajePDFdto.getNombre_chofer()).orElse(" "));
            map.put("placa_carreta", Optional.ofNullable(ticketPesajePDFdto.getPlaca_carreta()).orElse(" "));
            map.put("fecha_entrada", Optional.ofNullable(fechaEntrada).orElse(" "));
            map.put("hora_entrada", horaEntradaTk != null ? obtenerHoras(horaEntradaTk) : "");
            //map.put("hora_entrada", horaEntradaTk != null ? restarHoras(horaEntradaTk) : "");
            map.put("fecha_salida", Optional.ofNullable(fechaSalida).orElse(" "));
            //map.put("hora_salida", horaSalidaTk != null ? restarHoras(horaSalidaTk) : "");
            map.put("hora_salida", horaSalidaTk != null ? obtenerHoras(horaSalidaTk) : "");
            map.put("producto", Optional.ofNullable(ticketPesajePDFdto.getProducto()).orElse(" "));
            map.put("modelo_carreta",Optional.ofNullable(ticketPesajePDFdto.getModelo_carreta()).orElse(" "));
            map.put("peso_entrada", pesoE != null ? pesoE + "  " + unidadMedida : "");
            map.put("peso_salida", pesoS != null ? pesoS + "  " + unidadMedida : "");
            map.put("peso_neto", pesoN != null ? pesoN + "  " + unidadMedida : "");
            map.put("usu_entrada", Optional.ofNullable(operadorEntrada).orElse(Optional.ofNullable(operadorSalida).orElse(" ")));
            map.put("usu_salida", Optional.ofNullable(operadorSalida).orElse(Optional.ofNullable(operadorEntrada).orElse(" ")));
            map.put("proceso", Optional.ofNullable(ticketPesajePDFdto.getProceso()).orElse(" "));
            //map.put("unidad", Optional.ofNullable(unidadMedida).orElse(" "));
            map.put("observaciones", Optional.ofNullable(ticketPesaje.getObservacion()).orElse(" "));
            map.put("almacen", Optional.ofNullable(almacen).orElse(" "));
            //map.put("hora_creacion", restarHoras(horaCreacionTk));
            map.put("hora_creacion", obtenerHoras(horaCreacionTk));
            map.put("fecha_creacion", fechaCreacion);
            reportListGuis.add(map);
            logger.error("reportListGuis: {}",reportListGuis);

            File file = ResourceUtils.getFile("classpath:reportes/p.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            logger.error( "[GenerarReporte]: Ingreso" );
            JRBeanCollectionDataSource detail = new JRBeanCollectionDataSource(reportListGuis);

            logger.error( "[GenerarReporte]: detail:{}", detail);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy","Prueba");


            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,detail);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            return outputStream.toByteArray();

        }  catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());

        }
        return new byte[0];

    }

    public static String restarHoras(Timestamp fecha){
        long tiempoRestado = fecha.getTime() - (5 * 60 * 60 * 1000);
        Timestamp timestampRestado = new Timestamp(tiempoRestado);
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        String horaRestada = formatoHora.format(timestampRestado);
        return horaRestada;
    }

    public static String obtenerHoras(Timestamp fecha){
        long tiempoRestado = fecha.getTime();
        Timestamp timestampRestado = new Timestamp(tiempoRestado);
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        String horaRestada = formatoHora.format(timestampRestado);
        return horaRestada;
    }

}
