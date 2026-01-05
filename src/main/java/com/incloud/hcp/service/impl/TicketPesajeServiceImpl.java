package com.incloud.hcp.service.impl;

import com.google.gson.Gson;
import com.incloud.hcp.domain.CentroAlmacen;
import com.incloud.hcp.domain.balanza.*;
import com.incloud.hcp.dto.FiltroTicketPesajeDTO;
import com.incloud.hcp.dto.GuiaRemisionReportDTO;
import com.incloud.hcp.dto.TicketPesajeReporte;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.myibatis.mapper.TicketPesajeMapper;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.service.GuiaRemisionDetalleService;
import com.incloud.hcp.service.GuiaRemisionService;
import com.incloud.hcp.util.DateUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.incloud.hcp.service.TicketPesajeService;
import org.springframework.util.ResourceUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TicketPesajeServiceImpl implements TicketPesajeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TicketPesajeRepository ticketPesajeRepository;
    @Autowired
    private GuiaRemisionService guiaRemisionService;
    @Autowired
    private GuiaRemisionDetalleService guiaRemisionDetalleService;
    //@Autowired
    private TicketPesajeService ticketPesajeService;

    @Autowired
    public void setTicketPesajeService(@Lazy TicketPesajeService ticketPesajeService) {
        this.ticketPesajeService = ticketPesajeService;
    }

    @Autowired
    private DetalleTicketRepository detalleTicketRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CarretaRepository carretaRepository;

    @Autowired
    private TransportistaRepository transportistaRepository;

    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private ChoferRepository choferRepository;

    @Autowired
    private CentroAlmacenRepository centroAlmacenRepository;

    @Autowired
    private TicketPesajeMapper ticketPesajeMapper;

    @Override
    public List<TicketPesaje> getAllTicketPesaje() {
        return ticketPesajeRepository.findAll();
    }

    @Override
    public TicketPesaje getTicketPesajeById(Integer idTicketPesaje) throws PortalException {
        Optional<TicketPesaje> optionalTicketPesaje = ticketPesajeRepository.findById(idTicketPesaje);
        TicketPesaje ticketPesaje = optionalTicketPesaje.get();

        List<DetalleTicket> detalleList = ticketPesaje.getDetalleTicketList()
                .stream()
                .collect(Collectors.toList());
        for (int i = 1; i < detalleList.size(); i++) {
            DetalleTicket anterior = detalleList.get(i - 1);
            DetalleTicket actual = detalleList.get(i);

            if (anterior.getPeso_final() != null) {
                actual.setPeso_inicial(anterior.getPeso_final());
            }
        }
        ticketPesaje.setDetalleTicketList(detalleList);

        return ticketPesaje;
    }

    @Override
    public TicketPesaje save(TicketPesaje ticketPesaje) {



        //LocalDateTime fechaActual = LocalDateTime.now();
        Calendar calendarActual = Calendar.getInstance();
        calendarActual.setTime(new Date());
        calendarActual.add(Calendar.HOUR, -5);
        Timestamp actual = new Timestamp(calendarActual.getTime().getTime());
        LocalDateTime fechaActual = actual.toLocalDateTime();
        logger.error("Jecudero logger fechaActual" + fechaActual +" actual "+actual);
        if(ticketPesaje.getId()==null){
            logger.error("paso 1s if" );

            ticketPesaje.setFechaCreacion(Timestamp.valueOf(fechaActual));
            ticketPesaje.setFechaModificacion(null);
        }else{
            logger.error("paso 1s els");
            LocalDateTime fechaCreacion = ticketPesajeRepository.findById(ticketPesaje.getId()).get().getFechaCreacion().toLocalDateTime();
            //ticketPesaje.setFechaModificacion(Timestamp.valueOf(fechaActual));
            ticketPesaje.setFechaModificacion(actual);
            ticketPesaje.setFechaCreacion(Timestamp.valueOf(fechaCreacion));

        };//);
        TicketPesaje newTicket = ticketPesaje;
        List<DetalleTicket> detalleTicketList =  ticketPesaje.getDetalleTicketList();
        //logger.error("jescudero 00"+ ticketPesaje.getDetalleTicketList());
        newTicket.setDetalleTicketList(new ArrayList<>());
        TicketPesaje ticketPesajeSave = ticketPesajeRepository.save(newTicket);
        Integer ticketPesajeSaveId = ticketPesajeSave.getId();
        //List<DetalleTicket> detalleList = ticketPesajeSave.getDetalleTicketList();
            //for (DetalleTicket detalleTicket : detalleList) {
        List<DetalleTicket> listaDetalle = new ArrayList<>();
        logger.error("jescudero 00.0"+ detalleTicketList);
        if(detalleTicketList!=null){
            logger.error("jescudero 01"+detalleTicketList.size());
            for (int i = 0; i <detalleTicketList.size(); i++) {

                //Integer i = detalleList.indexOf(detalleTicket);
                DetalleTicket detalleBD = detalleTicketList.get(i);
                if (detalleBD.getSubticket() == null) {
                    logger.error("jescudero 02");
                    String subticket = String.format("ST-%06d-%02d", ticketPesajeSaveId, i + 1);
                    detalleBD.setSubticket(subticket);
                    detalleBD.setTicketPesaje(ticketPesajeSave);
                    if (detalleBD.getFechaCreacion() != null && detalleBD.getPeso_inicial() != null) {
                        logger.error("jescudero 02.1");
                        detalleBD.setFechaCreacion(Timestamp.valueOf(fechaActual));

                    }
                    if (detalleBD.getFechaModificacion() == null && detalleBD.getPeso_final() != null) {
                        logger.error("paso 1_111f ");
                        detalleBD.setFechaModificacion(Timestamp.valueOf(fechaActual));
                        logger.error("jescudero 02.2");
                    }else if(detalleBD.getId() == null && detalleBD.getPeso_final() != null){
                        detalleBD.setFechaModificacion(Timestamp.valueOf(fechaActual));
                    }
                    listaDetalle.add(this.detalleTicketRepository.save(detalleBD));
                } else {
                    logger.error("jescudero 03");
                    List<DetalleTicket> listaAux = this.detalleTicketRepository.findxSubticketList(detalleBD.getSubticket());
                    if(listaAux != null && listaAux.size() > 0) {
                        for (DetalleTicket objAux : listaAux) {
                            //Nuevo

                            if (detalleBD.getFechaModificacion() != null && detalleBD.getPeso_final() != null) {
                                logger.error("jescudero 03.1");
                                objAux.setFechaModificacion(Timestamp.valueOf(fechaActual));
                                objAux.setPeso_final(detalleBD.getPeso_final());
                                objAux.setPeso_neto(detalleBD.getPeso_neto());
                            }

                            //validación y actualización del peso_inicial si realmente viene nuevo
                            if (detalleBD.getPeso_inicial() != null && detalleBD.getPeso_inicial() > 0) {
                                objAux.setPeso_inicial(detalleBD.getPeso_inicial());
                                if (detalleBD.getFechaCreacion() != null) {
                                    objAux.setFechaCreacion(Timestamp.valueOf(fechaActual));
                                }
                            }
                            listaDetalle.add(this.detalleTicketRepository.save(objAux));
                        }
                    }
                }
            }
            ticketPesajeSave.setDetalleTicketList(listaDetalle);

        }



        return ticketPesajeRepository.save(ticketPesajeSave);
    }

    @Override
    public TicketPesaje savev1(TicketPesaje ticketPesaje) {



        //LocalDateTime fechaActual = LocalDateTime.now();
        Calendar calendarActual = Calendar.getInstance();
        calendarActual.setTime(new Date());
        calendarActual.add(Calendar.HOUR, -5);
        Timestamp actual = new Timestamp(calendarActual.getTime().getTime());
        LocalDateTime fechaActual = actual.toLocalDateTime();
        logger.error("Jecudero logger fechaActual" + fechaActual);
        if(ticketPesaje.getId()==null){
            logger.error("paso 1s if" );

            ticketPesaje.setFechaCreacion(Timestamp.valueOf(fechaActual));
            ticketPesaje.setFechaModificacion(null);
        }else{
            logger.error("paso 1s els");
            LocalDateTime fechaCreacion = ticketPesajeRepository.findById(ticketPesaje.getId()).get().getFechaCreacion().toLocalDateTime();
            ticketPesaje.setFechaModificacion(Timestamp.valueOf(fechaActual));
            ticketPesaje.setFechaCreacion(Timestamp.valueOf(fechaCreacion));

        }
        //ticketPesaje.getDetalleTicketList().forEach(det->{
        for (int i = 0; i < ticketPesaje.getDetalleTicketList().size(); i++) {
            DetalleTicket det = ticketPesaje.getDetalleTicketList().get(i);

            if (ticketPesaje.getDetalleTicketList().get(i).getSubticket()==null && det.getFechaCreacion() != null) {
                Calendar calendarCrea = Calendar.getInstance();
                calendarActual.setTime(new Date());
                calendarCrea.setTime(new Date(det.getFechaCreacion().getTime()));
                calendarCrea.add(Calendar.HOUR, -5);
                Timestamp crea = new Timestamp(calendarCrea.getTime().getTime());
                det.setFechaCreacion(crea);
                ticketPesaje.getDetalleTicketList().get(i).setFechaCreacion(crea);
            }else if(det.getFechaCreacion() != null){
                det.setFechaCreacion(det.getFechaCreacion());
                ticketPesaje.getDetalleTicketList().get(i).setFechaCreacion(det.getFechaCreacion());
            }else{
                det.setFechaCreacion(Timestamp.valueOf(fechaActual));
                ticketPesaje.getDetalleTicketList().get(i).setFechaCreacion(Timestamp.valueOf(fechaActual));
            }
             if (ticketPesaje.getDetalleTicketList().get(i).getSubticket()==null && det.getFechaModificacion() != null && det.getPeso_final() != null) {
                 ticketPesaje.getDetalleTicketList().get(i).setFechaModificacion(Timestamp.valueOf(fechaActual));
            }
            /*if (det.getFechaModificacion() != null) {
                Calendar calendarModi = Calendar.getInstance();
                calendarModi.setTime(new Date(det.getFechaModificacion().getTime()));
                calendarModi.add(Calendar.HOUR, -5);
                Timestamp modi = new Timestamp(calendarModi.getTime().getTime());
                det.setFechaModificacion(modi);
                ticketPesaje.getDetalleTicketList().get(i).setFechaModificacion(modi);
            }*/

            det.setTicketPesaje(ticketPesaje);


        };//);
        TicketPesaje ticketPesajeSave = ticketPesajeRepository.save(ticketPesaje);
        Integer ticketPesajeSaveId = ticketPesajeSave.getId();
        List<DetalleTicket> detalleList = ticketPesajeSave.getDetalleTicketList();
        if(detalleList != null && detalleList.size() >0) {
            for (DetalleTicket detalleTicket : detalleList) {
                Integer i = detalleList.indexOf(detalleTicket);
                String subticket = String.format("ST-%06d-%02d", ticketPesajeSaveId, i + 1);
                detalleTicket.setSubticket(subticket);
            }
        }
        TicketPesaje ticketFinal = ticketPesajeRepository.save(ticketPesajeSave);

        List<DetalleTicket> detalleListF = ticketFinal.getDetalleTicketList();
        if(detalleListF != null && detalleListF.size() >0) {
            //for (DetalleTicket detalleListFinal : detalleListF) {
            for (int i = 0; i < detalleListF.size(); i++) {
                DetalleTicket detalleListFinal = detalleListF.get(i);
                DetalleTicket detalleBD = this.detalleTicketRepository.findxSubticket(detalleListF.get(i).getSubticket());
                if (detalleBD != null) {
                    if (detalleBD.getFechaCreacion() != null) {
                        logger.error("detalleBD.getFechaCreacion() != null"+detalleBD.getFechaCreacion());
                        detalleListFinal.setFechaCreacion(detalleBD.getFechaCreacion());
                    } else if (detalleListFinal.getFechaCreacion() == null && detalleListFinal.getPeso_inicial() != null) {
                        logger.error("else if (detalleListFinal.getFechaCreacion() == null &&" + Timestamp.valueOf(fechaActual));
                        detalleListFinal.setFechaCreacion(Timestamp.valueOf(fechaActual));
                    }
                    if (detalleBD.getFechaModificacion() != null) {
                        logger.error("paso 2_2f " + detalleBD.getFechaModificacion());
                        logger.error("paso 4");
                        detalleListFinal.setFechaModificacion(detalleBD.getFechaModificacion());

                    } else if (detalleListFinal.getSubticket()!=null && detalleListFinal.getFechaModificacion() == null && detalleListFinal.getPeso_final() != null) {
                        logger.error("paso 1_111f ");
                        detalleListFinal.setFechaModificacion(Timestamp.valueOf(fechaActual));
                    }
                    //this.detalleTicketRepository.save(detalleBD);
                }//else{

                    //ticketFinal.getDetalleTicketList().get(i).setFechaModificacion(Timestamp.valueOf(fechaActual));
                //}
            }
        }


        return ticketPesajeRepository.save(ticketFinal);
    }

    @Override
    public Timestamp guardarFechaHoraEntradaTicket(String noSubTicket, Double pesoInicial) throws Exception {
        try {
            //LocalDateTime fechaActual = LocalDateTime.now();
            Calendar calendarActual = Calendar.getInstance();
            calendarActual.setTime(new Date());
            calendarActual.add(Calendar.HOUR, -5);
            Timestamp actual = new Timestamp(calendarActual.getTime().getTime());
            LocalDateTime fechaActual = actual.toLocalDateTime();
            DetalleTicket detalleTicket = this.detalleTicketRepository.findxSubticket(noSubTicket);
            logger.error("saveFHI 1 " + pesoInicial);
            if (detalleTicket != null){
                logger.error("saveFHI 2 ");
                if(pesoInicial != null){
                    logger.error("saveFHI 2_1 ");
                    if(detalleTicket.getPeso_inicial() != null) {
                        if (Double.compare(detalleTicket.getPeso_inicial(), pesoInicial) != 0) {
                            logger.error("saveFHI 3 " + detalleTicket.getPeso_inicial());
                            detalleTicket.setFechaCreacion(Timestamp.valueOf(fechaActual));
                            logger.error("Se actualizo la fecha/hora de entrada." + detalleTicket);
                        } else {
                            logger.error("El nuevo peso y el actual inicial coinciden.");
                            detalleTicket.setFechaCreacion(detalleTicket.getFechaCreacion());
                        }
                    } else {
                        logger.error("else fhFI 4");
                        detalleTicket.setFechaCreacion(Timestamp.valueOf(fechaActual));
                        logger.error("Se registro la fecha/hora de entrada." + detalleTicket);
                    }
                }
            } else {
                logger.error("No hay detalle para registrar fecha y hora.");
            }
            this.detalleTicketRepository.save(detalleTicket);
            logger.error("getFechaCreacion() " + detalleTicket.getFechaCreacion());
            return detalleTicket.getFechaCreacion();
        } catch (Exception e){
            logger.error("no se pudo crear la fecha" + e.getMessage());
            throw new Exception(e);
        }
    }

    @Override
    public Timestamp guardarFechaHoraSalidaTicket(String noSubTicket, Double pesoFinal) throws Exception {
        try {
            //LocalDateTime fechaActual = LocalDateTime.now();
            Calendar calendarActual = Calendar.getInstance();
            calendarActual.setTime(new Date());
            calendarActual.add(Calendar.HOUR, -5);
            Timestamp actual = new Timestamp(calendarActual.getTime().getTime());
            LocalDateTime fechaActual = actual.toLocalDateTime();
            DetalleTicket detalleTicket = this.detalleTicketRepository.findxSubticket(noSubTicket);
            logger.error("saveFHS 1 " + pesoFinal);
            if (detalleTicket != null){
                logger.error("saveFHS 2 ");
                if(pesoFinal != null){
                    if(detalleTicket.getPeso_final() != null) {
                        if (Double.compare(detalleTicket.getPeso_final(), pesoFinal) != 0) {
                            logger.error("saveFHS 4 ");
                            detalleTicket.setFechaModificacion(Timestamp.valueOf(fechaActual));
                            logger.error("Se actualizo la fecha/hora de salida." + detalleTicket);
                        } else {
                            logger.error("El nuevo peso y el actual inicial coinciden.");
                            detalleTicket.setFechaModificacion(detalleTicket.getFechaCreacion());
                        }
                    } else {
                        logger.error("else fhS 4");
                        detalleTicket.setFechaModificacion(Timestamp.valueOf(fechaActual));
                        logger.error("saveFHS 5 " + detalleTicket);
                    }
                }
            } else {
                logger.error("No hay detalle para registrar fecha y hora.");
            }
            this.detalleTicketRepository.save(detalleTicket);
            return detalleTicket.getFechaModificacion();
        } catch (Exception e){
            logger.error("no se pudo crear la fecha" + e.getMessage());
            throw new Exception(e);
        }
    }

    @Override
    public String guardarFechaHoraTicket(String noSubTicket, Double pesoInicial, Double pesoFinal) throws Exception {
        try {
            Calendar calendarActual = Calendar.getInstance();
            calendarActual.setTime(new Date());
            calendarActual.add(Calendar.HOUR, -5);
            Timestamp actual = new Timestamp(calendarActual.getTime().getTime());
            LocalDateTime fechaActual = actual.toLocalDateTime();
            //LocalDateTime fechaActual = LocalDateTime.now();
            DetalleTicket detalleTicket = this.detalleTicketRepository.findxSubticket(noSubTicket);
            logger.error("saveFH 1 " + pesoInicial + " - " + pesoFinal);
            String mensajeFinal = "";
            if (detalleTicket != null){
                String mensaje1 = "";
                String mensaje2 = "";
                logger.error("saveFH 2 ");
                if(pesoInicial != null){
                    logger.error("saveFH 2_1 ");
                    if(detalleTicket.getPeso_inicial() != null) {
                        if (detalleTicket.getPeso_inicial() != pesoInicial) {
                            logger.error("saveFHI 3 ");
                            detalleTicket.setFechaCreacion(Timestamp.valueOf(fechaActual));
                            mensaje1 = "Se registro la fecha/hora de entrada.";
                            logger.error("saveFHI 3_1 ");
                        }
                    } else {
                        logger.error("else fhF 4");
                        detalleTicket.setFechaCreacion(Timestamp.valueOf(fechaActual));
                    }
                }
                if(pesoFinal != null){
                    if(detalleTicket.getPeso_final() != null) {
                        if (detalleTicket.getPeso_final() != pesoFinal) {
                            logger.error("saveFHF 4 ");
                            detalleTicket.setFechaModificacion(Timestamp.valueOf(fechaActual));
                            mensaje2 = "Se registro la fecha/hora de salida.";
                            logger.error("saveFHF 4_1 ");
                        }
                    } else {
                        logger.error("else fhF 4");
                        detalleTicket.setFechaModificacion(Timestamp.valueOf(fechaActual));
                    }
                }
                logger.error("saveFH 5 ");
                this.detalleTicketRepository.save(detalleTicket);
                if(mensaje1 != "" && mensaje2 != ""){
                    mensajeFinal = mensaje1 + " - " + mensaje2;
                } else if(mensaje1 != ""){
                    mensajeFinal = mensaje1;
                } else if(mensaje2 != ""){
                    mensajeFinal = mensaje2;
                }
            } else {
                String mensaje = "No hay detalle para registrar fecha y hora.";
                mensajeFinal = mensaje;
            }
            return mensajeFinal;
        } catch (Exception e){
            logger.error("no se pudo crear la fecha" + e.getMessage());
            throw new Exception(e);
        }
    }

    @Override
    public List<TicketPesaje> getTicketPesajeListPorFechas(Date fechaInicio, Date fechaFin) {
        return ticketPesajeRepository.getTicketPesajeByFechaRegistroBetween(fechaInicio, fechaFin);
    }

    @Override
    public List<TicketPesaje> findByIdAndFechaRegistroBetween(Integer id, Date fechaInicio, Date fechaFin) {
        return this.ticketPesajeRepository.findByIdAndFechaRegistroBetween(id, fechaInicio, fechaFin);
    }


    @Override
    public List<TicketPesaje> findByFiltro(Date fechaInicio, Date fechaFin, FiltroTicketPesajeDTO filtro) {
        return this.ticketPesajeRepository.findByIdAndTransportistaRucAndCentroAndFechaRegistroBetween(filtro.getNroTicket(), filtro.getTransportistaRuc(),filtro.getCentro(), fechaInicio, fechaFin);
    }

    @Override
    public List<TicketPesaje> findByTransportistaRucAndFechaRegistroBetween(String transportistaRuc, Date fechaInicio, Date fechaFin) {
        return this.ticketPesajeRepository.findByTransportistaRucAndFechaRegistroBetween(transportistaRuc, fechaInicio, fechaFin);
    }

    @Override
    public List<TicketPesaje> findByCentroAndFechaRegistroBetween(String centro, Date fechaInicio, Date fechaFin) {
        return this.ticketPesajeRepository.findByCentroAndFechaRegistroBetween(centro, fechaInicio, fechaFin);
    }

    @Override
    public List<TicketPesaje> findByCentroAndPlacaAndFechaRegistroBetween(String centro,String placa, Date fechaInicio, Date fechaFin) {
        return this.ticketPesajeRepository.findByCentroAndPlacaAndFechaRegistroBetween(centro,placa, fechaInicio, fechaFin);
    }

    @Override
    public List<TicketPesaje> findByIdAndTransportistaRucAndFechaRegistroBetween(Integer id, String transportistaRuc, Date fechaInicio, Date fechaFin) {
        return this.ticketPesajeRepository.findByIdAndTransportistaRucAndFechaRegistroBetween(id, transportistaRuc, fechaInicio, fechaFin);
    }

    @Override
    public List<TicketPesaje> findByIdAndCentroAndFechaRegistroBetween(Integer id, String centro, Date fechaInicio, Date fechaFin) {
        return this.ticketPesajeRepository.findByIdAndCentroAndFechaRegistroBetween(id, centro, fechaInicio, fechaFin);
    }

    @Override
    public List<TicketPesaje> findByIdAndCentroAndPlacaAndFechaRegistroBetween(Integer id, String centro,String placa, Date fechaInicio, Date fechaFin) {
        return this.ticketPesajeRepository.findByIdAndCentroAndPlacaAndFechaRegistroBetween(id, centro,placa, fechaInicio, fechaFin);
    }

    @Override
    public List<TicketPesaje> findByTransportistaRucAndCentroAndFechaRegistroBetween(String transportistaRuc, String centro, Date fechaInicio, Date fechaFin) {
        return this.ticketPesajeRepository.findByTransportistaRucAndCentroAndFechaRegistroBetween(transportistaRuc, centro, fechaInicio, fechaFin);
    }

    @Override
    public List<TicketPesaje> findByPlacaAndFechaRegistroBetween(String placa, Date fechaInicio, Date fechaFin) {
        return this.ticketPesajeRepository.findByPlacaAndFechaRegistroBetween(placa, fechaInicio, fechaFin);
    }

    @Override
    public List<TicketPesaje> findByChoferAndFechaRegistroBetween(String dniChofer, Date fechaInicio, Date fechaFin) {
        return this.ticketPesajeRepository.findByChoferAndFechaRegistroBetween(dniChofer, fechaInicio, fechaFin);
    }

    @Override
    public TicketPesaje actualizarEstado(Integer ticketId, Integer estadoId) {
        logger.error("ActualizarEstadoTicket start");
        logger.error("ActualizarEstadoTicket ticketid"  + ticketId);
        Optional<TicketPesaje> optionalTicketPesaje = this.ticketPesajeRepository.findById(ticketId);
        if(!optionalTicketPesaje.isPresent()){
            throw new RuntimeException("No existe un TicketPesaje con id: " + ticketId);
        }
        TicketPesaje ticketPesaje = optionalTicketPesaje.get();

        Optional<Estado> optionalEstado = this.estadoRepository.findById(estadoId);
        if(!optionalTicketPesaje.isPresent()){
            throw new RuntimeException("No existe un Estado con id: " + estadoId);
        }
        Estado estado = optionalEstado.get();

        List<DetalleTicket> detalleTicketList = this.detalleTicketRepository.findByTicketPesajeId(ticketId);

        logger.error("Comienzo de actualización de estados");
        ticketPesaje.setEstado(estado);
        this.ticketPesajeRepository.save(ticketPesaje);

        for(DetalleTicket detalleTicket : detalleTicketList){
            detalleTicket.setEstado(estado);
        }
        this.detalleTicketRepository.saveAll(detalleTicketList);
        return ticketPesaje;
    }

    @Override
    public List<TicketPesajeReporte> listarReporteByFiltro(Date fechaInicio, Date fechaFin, FiltroTicketPesajeDTO filtro) {
        logger.error("filtro " + filtro);
        List<TicketPesaje> ticketPesajeList = new ArrayList<>();
        LocalDate f = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaNueva = f.plusDays(1);
        Date nuevaFechaFin = Date.from(fechaNueva.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Integer> listIdTransporte = new ArrayList<>();
        List<Integer> listIdChofer = new ArrayList<>();
        List<Integer> listIdCarreta = new ArrayList<>();;
        Integer idTransporte = null;
        Integer idChofer = null;
        Integer idCarreta = null;
        if(filtro.getTransportistaRuc() != null) {
            List<Transportista> listTransportista = this.transportistaRepository.getTransportistaByRucList(filtro.getTransportistaRuc());
            if(listTransportista == null || listTransportista.isEmpty()) {
                throw new PortalException("El transportista no existe.");
            }
        }
        logger.error("getTransportistaRuc ");
        if(filtro.getPlaca() != null){
            listIdTransporte = this.transporteRepository.getIdTransporteByPlaca(filtro.getPlaca());
            idTransporte = listIdTransporte.get(0);
            if(listIdTransporte == null || listIdTransporte.isEmpty()) {
                throw new PortalException("El transporte no existe.");
            }
        }
        logger.error("getPlaca ");
        if(filtro.getChofer() != null){
            listIdChofer = this.choferRepository.getIdChoferByDni(filtro.getChofer());
            idChofer = listIdChofer.get(0);
            if(listIdChofer == null || listIdChofer.isEmpty()) {
                throw new PortalException("El chofer no existe.");
            }
        }
        logger.error("getChofer ");
        if(filtro.getCentro() != null){
            CentroAlmacen centro = this.centroAlmacenRepository.getByNivelCodigoSap(filtro.getCentro());
            if(centro == null) {
                throw new PortalException("El centro no existe.");
            }
        }
        logger.error("getCentro ");
        if(filtro.getnSubticket() != null){
            List<Integer> subticket = this.detalleTicketRepository.findByNroSubticket(filtro.getnSubticket());
            if(subticket == null || subticket.isEmpty()) {
                throw new PortalException("El subticket no existe.");
            }
        }
        logger.error("subticket ");
//        if(filtro.getBalanza() != null){
//            Balanza balanza = this.centroAlmacenRepository.getByNivelCodigoSap(filtro.getCentro());
//            if(balanza == null) {
//                throw new PortalException("La balanza no existe.");
//            }
//        }
        if(filtro.getCarreta() != null){
            listIdCarreta = this.carretaRepository.getIdCarretaByPlaca(filtro.getCarreta());
            idCarreta = listIdCarreta.get(0);
            if(listIdCarreta == null || listIdCarreta.isEmpty()) {
                throw new PortalException("La carreta no existe.");
            }
        }
        logger.error("getCarreta ");

        logger.error("idTransporte " + idTransporte + " - idChofer " + idChofer + " - idCarreta " + idCarreta);
        if(filtro != null){
            if(fechaInicio == null || fechaFin == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrese las fechas de inicio y fin");
            }
            logger.error("pre out");
            Date fechaFinal = DateUtils.sumarRestarDias(fechaFin, 1);
            List<TicketPesaje> outMap = this.ticketPesajeMapper.getTicketPesajeMap(fechaInicio, fechaFinal, filtro.getNroTicket(), filtro.getTransportistaRuc(),
                    idTransporte, idChofer, filtro.getCentro(), filtro.getnSubticket(), filtro.getBalanza(), idCarreta);
            Gson gson = new Gson();
            logger.error("out gson "+gson.toJson(outMap));
            if (outMap != null && outMap.size() > 0) {
                for (TicketPesaje tp : outMap) {
                    TicketPesaje ticketOut = this.ticketPesajeRepository.getById(tp.getId());
                    logger.error("ticketOut " + ticketOut);
                    ticketPesajeList.add(ticketOut);
                }
                logger.error("lista tickets " + ticketPesajeList);
            }
        }
        List<TicketPesajeReporte> ticketPesajeReporteList = new ArrayList<>();
        for (int i = 0; i < ticketPesajeList.size(); i++) {
            TicketPesajeReporte ticketPesajeReporte = new TicketPesajeReporte();
            GuiaRemisionReportDTO guiaRemisionDTO = new GuiaRemisionReportDTO();
            String ticketPesajeId = String.valueOf(ticketPesajeList.get(i).getId());
            logger.error("ticketPesajeId" + ticketPesajeId);
            ticketPesajeReporte.setTicketPesaje(ticketPesajeList.get(i));
            List<GuiaRemision> guiaRemisionList = this.guiaRemisionService.getAllGuiaRemisionByTicketPesajeId(ticketPesajeId);
            logger.error("guiaRemisionList" + guiaRemisionList.size());
            List<GuiaRemisionDetalle> guiaRemisionDetalleList = this.guiaRemisionDetalleService.getGuiaRemisionDetalleByTicket(ticketPesajeId);
            logger.error("guiaRemisionDetalleList" + guiaRemisionDetalleList.size());
            if (!guiaRemisionList.isEmpty()) {
                logger.error("llenado de guia");
                guiaRemisionDTO.setGuiaRemision(guiaRemisionList);
                guiaRemisionDTO.setGuiaRemisionDetalleList(guiaRemisionDetalleList);
            }
            ticketPesajeReporte.setGuiaRemision(guiaRemisionDTO);
            ticketPesajeReporteList.add(ticketPesajeReporte);
        }
        return ticketPesajeReporteList;
    }

    @Override
    public List<TicketPesaje> listarByFiltro(Date fechaInicio, Date fechaFin, FiltroTicketPesajeDTO filtro) {
        List<TicketPesaje> ticketPesajeList = new ArrayList<>();
        LocalDate f = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaNueva = f.plusDays(1);
        Date nuevaFechaFin = Date.from(fechaNueva.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if(filtro != null){
            if(fechaInicio == null || fechaFin == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrese las fechas de inicio y fin");
            } else if(filtro.getNroTicket() == null && filtro.getTransportistaRuc() == null && filtro.getCentro() == null){
                ticketPesajeList = this.ticketPesajeService.getTicketPesajeListPorFechas(fechaInicio, nuevaFechaFin);
            } else if(filtro.getNroTicket() != null && filtro.getTransportistaRuc() != null && filtro.getCentro() != null){
                ticketPesajeList = this.ticketPesajeService.findByFiltro(fechaInicio, nuevaFechaFin, filtro);
            } else if(filtro.getNroTicket() == null && filtro.getTransportistaRuc() != null && filtro.getCentro() == null){
                ticketPesajeList = this.ticketPesajeService.findByTransportistaRucAndFechaRegistroBetween(filtro.getTransportistaRuc(), fechaInicio, nuevaFechaFin);
            } else if(filtro.getNroTicket() != null && filtro.getTransportistaRuc() == null && filtro.getCentro() == null){
                ticketPesajeList = this.ticketPesajeService.findByIdAndFechaRegistroBetween(filtro.getNroTicket(), fechaInicio, nuevaFechaFin);
            } else if (filtro.getNroTicket() == null && filtro.getTransportistaRuc() == null && filtro.getCentro() != null) {
                if(filtro.getPlaca() != null)ticketPesajeList = this.ticketPesajeService.findByCentroAndPlacaAndFechaRegistroBetween(filtro.getCentro(),filtro.getPlaca(), fechaInicio, nuevaFechaFin);
                else ticketPesajeList = this.ticketPesajeService.findByCentroAndFechaRegistroBetween(filtro.getCentro(), fechaInicio, nuevaFechaFin);
            } else if (filtro.getNroTicket() != null && filtro.getTransportistaRuc() != null && filtro.getCentro() == null) {
                ticketPesajeList = this.ticketPesajeService.findByIdAndTransportistaRucAndFechaRegistroBetween(filtro.getNroTicket(), filtro.getTransportistaRuc(), fechaInicio, nuevaFechaFin);
            } else if (filtro.getNroTicket() != null && filtro.getTransportistaRuc() == null && filtro.getCentro() != null) {
                if(filtro.getPlaca()!=null) ticketPesajeList = this.ticketPesajeService.findByIdAndCentroAndPlacaAndFechaRegistroBetween(filtro.getNroTicket(), filtro.getCentro(),filtro.getPlaca(), fechaInicio, nuevaFechaFin);
                else ticketPesajeList = this.ticketPesajeService.findByIdAndCentroAndFechaRegistroBetween(filtro.getNroTicket(), filtro.getCentro(), fechaInicio, nuevaFechaFin);
            } else if (filtro.getNroTicket() == null && filtro.getTransportistaRuc() != null && filtro.getCentro() != null) {
                ticketPesajeList = this.ticketPesajeService.findByTransportistaRucAndCentroAndFechaRegistroBetween(filtro.getTransportistaRuc(), filtro.getCentro(), fechaInicio, nuevaFechaFin);
            } else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrese datos del filtro");
            }
        }
        return ticketPesajeList;
    }

    @Override
    public byte[]  getAllTicketPDF(Integer idTicketPesaje) throws FileNotFoundException, JRException {


        List<TicketPesaje> listData = (List<TicketPesaje>) ticketPesajeRepository.findById(idTicketPesaje).orElse(null);

        logger.error("Generando listData: {}",listData);

        File file = ResourceUtils.getFile("classpath:reportes/ticket.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listData);
        Map<String, Object> map = new HashMap<>();
        map.put("createdBy","TITLE");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,map,dataSource);
        logger.error("Generando pdf");
        //JasperExportManager.exportReportToPdfFile(jasperPrint,"C:\\Users\\Usuario\\Downloads"+"\\GuiaRemision.pdf");
        //JasperExportManager.exportReportToPdfStream(jasperPrint);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        logger.error("Generando outputStream: {}",outputStream);

        return outputStream.toByteArray();

    }




}







   /* @Override
    public ResponseEntity <Map> update(TicketPesaje ticketPesaje) {
        ResponseEntity response = null;
        Map map = new HashMap<>();
        try{
            TicketPesaje ticketPesajeActual = ticketPesajeRepository.findById(ticketPesaje.getId()).orElse(null);
            if(ticketPesajeActual != null) {
                ticketPesajeActual.setChofer(ticketPesaje.getChofer());
                ticketPesajeActual.setTransporte(ticketPesaje.getTransporte());
                ticketPesajeActual.setCarreta(ticketPesaje.getCarreta());
                ticketPesajeActual.setTipoProceso(ticketPesaje.getTipoProceso());
                ticketPesajeActual.setUnidadMedida(ticketPesaje.getUnidadMedida());
                ticketPesajeActual.setPesoInicial(ticketPesaje.getPesoInicial());
                ticketPesajeActual.setFechaModificacion(ticketPesaje.getFechaModificacion());
                ticketPesajeActual = ticketPesajeRepository.save(ticketPesajeActual);
            }
            ticketPesaje = ticketPesajeActual;
            LocalDateTime fechaActualmodi = LocalDateTime.now();
            ticketPesaje.setFechaModificacion(fechaActualmodi);
            map.put("data", ticketPesajeRepository.save(ticketPesaje));
            response = new ResponseEntity(map, HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }


        return response;
    }
*/


