package com.incloud.hcp.rest;

import com.incloud.hcp.domain.balanza.DetalleTicket;
import com.incloud.hcp.domain.balanza.GuiaRemision;
import com.incloud.hcp.domain.balanza.TicketPesaje;
import com.incloud.hcp.dto.FiltroTicketPesajeDTO;
import com.incloud.hcp.dto.TicketPesajeReporte;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.dto.DocMaterialGenerarSAPDto;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.dto.DocumentoMaterialResponseDto;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.dto.TicketPesajeDto;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.service.JCODocumentoMaterialService;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.service.GuiaRemisionService;
import com.incloud.hcp.service.TicketPesajeService;
import com.incloud.hcp.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping(value = "/api/ticketPesaje")
public class TicketPesajeRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GuiaRemisionService guiaRemisionService;

    @Autowired
    private GuiaRemisionRepository guiaRemisionServiceRepository;

    @Autowired
    private GuiaRemisionDetalleRepository guiaRemisionDetalleRepository;

    @Autowired
    private TicketPesajeService ticketPesajeService;

    @Autowired
    private TicketPesajeRepository ticketPesajeRepository;

    @Autowired
    private JCODocumentoMaterialService jcoDocumentoMaterialService;

    @Autowired
    private DetalleTicketRepository detalleTicketRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @RequestMapping(value = "/id/{idTicketPesaje}",
                    method = RequestMethod.GET, produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getTicketPesajeById(@PathVariable("idTicketPesaje") Integer idTicketPesaje) throws PortalException {
        TicketPesaje ticketPesaje = this.ticketPesajeService.getTicketPesajeById(idTicketPesaje);
        if (ticketPesaje == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro TicketPesaje con ese id");
        }
        return new ResponseEntity<>(ticketPesaje, HttpStatus.OK);
    }
    @RequestMapping(value = "/TicketPesajeByIdAndGuia/id/{idTicketPesaje}/{idGuiaRemision}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getTicketPesajeByIdAndGuia(@PathVariable("idTicketPesaje") Integer idTicketPesaje,
                                                        @PathVariable("idGuiaRemision") Integer idGuiaRemision) throws PortalException {

        com.incloud.hcp.dto.TicketPesajeDto ticketPesajeDto = new com.incloud.hcp.dto.TicketPesajeDto();
        ticketPesajeDto.setTicketPesaje(this.ticketPesajeService.getTicketPesajeById(idTicketPesaje));
        ticketPesajeDto.setGuiaRemision(this.guiaRemisionService.getGuiaRemisionById(idGuiaRemision));
        ticketPesajeDto.setGuiaRemisionDetalle(this.guiaRemisionDetalleRepository.findByIdGuiaRemision(idGuiaRemision));
        if (ticketPesajeDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro TicketPesaje con ese id");
        }
        return new ResponseEntity<>(ticketPesajeDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/anularTicketPesaje/id/{idTicketPesaje}",
            method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public String anularTicketPesaje(@PathVariable("idTicketPesaje") String idTicketPesaje) throws PortalException {

        //TicketPesaje ticketPesaje = this.ticketPesajeService.getTicketPesajeById(Integer.parseInt(idTicketPesaje));
        List<GuiaRemision> listaGuiaRemision = this.guiaRemisionServiceRepository.getAllGuiaRemisionByTicketPesajeId(idTicketPesaje);
        //List<DetalleTicket> listDetalleTicket= this.detalleTicketRepository.findByTicketPesajeId(Integer.parseInt(idTicketPesaje));

       if(listaGuiaRemision.size()>0){
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se puede anular el Ticket de pesaje");

       }else{
            this.ticketPesajeRepository.updateEstadoTicketPesaje(3, Integer.parseInt(idTicketPesaje));
           return "Se anuló el ticket de pesaje";
       }


    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<java.util.List<TicketPesaje>> gelAllTicketPesaje() {
        return Optional.ofNullable(ticketPesajeService.getAllTicketPesaje()).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @RequestMapping(value = "/listarByFiltro/{FechaInicio}/{FechaFin}/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<TicketPesaje>> gelAllTicketPesaje(@PathVariable("FechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                                                        @PathVariable("FechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                                                        @RequestBody FiltroTicketPesajeDTO filtro) {

        List<TicketPesaje> ticketPesajeList = this.ticketPesajeService.listarByFiltro(fechaInicio, fechaFin, filtro);
        return new ResponseEntity<List<TicketPesaje>>(ticketPesajeList, HttpStatus.OK);
    }

    @RequestMapping(value = "/listarReporteByFiltro/{FechaInicio}/{FechaFin}/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<TicketPesajeReporte>> listarReporteByFiltro(@PathVariable("FechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                                                 @PathVariable("FechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                                                 @RequestBody FiltroTicketPesajeDTO filtro) {
        List<TicketPesajeReporte> ticketPesajeReporteList = this.ticketPesajeService.listarReporteByFiltro(fechaInicio,fechaFin,filtro);
        return new ResponseEntity<List<TicketPesajeReporte>>(ticketPesajeReporteList, HttpStatus.OK);
    }

    @RequestMapping(value = "/actualizarEstadoTicket/{ticketId}/{estadoId}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TicketPesaje> actualizarEstadoTicket(@PathVariable("ticketId") Integer ticketId,
                                                                 @PathVariable("estadoId") Integer estadoId) {
        logger.error("ActualizarEstadoTicket rest 0");
        TicketPesaje ticketPesaje = this.ticketPesajeService.actualizarEstado(ticketId, estadoId);
        return new ResponseEntity<TicketPesaje>(ticketPesaje, HttpStatus.OK);
    }
    @GetMapping(value = "/getTicketPesajeList/{FechaInicio}/{FechaFin}")
    public ResponseEntity<List<TicketPesaje>> getTicketPesajeList(
            @PathVariable("FechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @PathVariable("FechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin) {
        try {
            List<TicketPesaje> ticketPesajeList = ticketPesajeService.getTicketPesajeListPorFechas(fechaInicio, fechaFin);

            if (ticketPesajeList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ticketPesajeList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    /*@RequestMapping(value = "/actualizar", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TicketPesaje update(@RequestBody TicketPesaje ticketPesaje) {
        Optional<TicketPesaje> ticketPesajes =  ticketPesajeRepository.findById(ticketPesaje.getId());
        if(ticketPesajes.isPresent()) {
            List<DetalleTicket> detallesactual = ticketPesaje.getDetalleTicketList();
            ticketPesaje.setDetalleTicketList(null);
            ticketPesajeService.update(ticketPesaje);
            for(DetalleTicket det:detallesactual){
                det.setId(ticketPesaje.getId());

                det.setTipo_peso(det.getTipo_peso());
                det.setProducto(det.getProducto());
                det.setPosicion(det.getPosicion());
                det.setPeso_neto(det.getPeso_neto());
                det.setPeso_bruto(det.getPeso_bruto());
                LocalDateTime fechaActual = LocalDateTime.now();
                det.setFechaModificacion(fechaActual);
                det.setEstado(det.getEstado());
            }

            detalleTicketRepository.saveAll(detallesactual);
            ticketPesaje.setDetalleTicketList(detallesactual);
            }
        return ticketPesaje;
    }*/

    @RequestMapping(value = "SaveorUpdatePrueba", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TicketPesajeDto save(@RequestBody TicketPesaje ticketPesaje) {

        logger.error("SaveorUpdatePrueba - inicio");
        logger.error("SaveorUpdatePrueba - ticketPesaje:{}", ticketPesaje);
        TicketPesajeDto ticketDto = new TicketPesajeDto();
        List<DocumentoMaterialResponseDto> responseList = new ArrayList<DocumentoMaterialResponseDto>();
        TicketPesaje ticket = this.ticketPesajeService.save(ticketPesaje);
        List<DetalleTicket> listDetalles = ticket.getDetalleTicketList();
        List<DocMaterialGenerarSAPDto> listDto = new ArrayList<>();
        DocMaterialGenerarSAPDto dtoRFC= new DocMaterialGenerarSAPDto();
        dtoRFC.setCentro(ticket.getCodigoCentro());
        //dtoRFC.setExportaMoveplant(ticket.getCentroReceptor());
        //dtoRFC.setExportaMovestloc(ticket.getAlmacenReceptor());
        dtoRFC.setExportaPlant(ticket.getCodigoCentro());

        try{
            logger.error("generarDocumentosMaterialRfc - inicio");
            for(DetalleTicket item: listDetalles){
                dtoRFC.setDocumentoTraslado(item.getDocumentoTraslado());
                dtoRFC.setMaterial(item.getMaterial());
                dtoRFC.setExportaMaterial(item.getMaterial());
                dtoRFC.setUnidadMedida(item.getUnidadMedida());
                dtoRFC.setLote(item.getLote());
                dtoRFC.setExportaMovebatch(item.getLote());
                dtoRFC.setTipoPesaje(item.getTipoPesaje());
                dtoRFC.setCantidad(item.getPeso_neto().toString());
                dtoRFC.setPosicion(item.getPosicion().toString());
                dtoRFC.setNumeroPedido(item.getNumPedido());
                dtoRFC.setAlmacen(item.getCodigoAlmacen());
                listDto.add(dtoRFC);
            }
            List<DocumentoMaterialResponseDto> dtoDocMaterial =
                    this.jcoDocumentoMaterialService.generarDocumentoMaterialRfc(listDto);

            for(DetalleTicket item : listDetalles){

                logger.error("savePrueba:{}",dtoDocMaterial);

                for (int i = 0; i< dtoDocMaterial.size(); i++){
                    DocumentoMaterialResponseDto documentoMaterialResponseDto = dtoDocMaterial.get(i);
                    if(documentoMaterialResponseDto.getDocumentoMaterial().isEmpty() && !Objects.equals(item.getTipoPesaje(), "100")){
                        item.setEstado(this.estadoRepository.getById(3));
                    }
                    item.setDocMaterial(documentoMaterialResponseDto.getDocumentoMaterial());
                    item.setEjercicio(documentoMaterialResponseDto.getEjercio());
                    documentoMaterialResponseDto.setTipoPesaje(item.getTipoPesaje());
                    this.detalleTicketRepository.save(item);
                    logger.error("guardo");
                    responseList.add(documentoMaterialResponseDto);
                }

            }

            for(DetalleTicket i: listDetalles){
                if(i.getEstado().getId().equals(this.estadoRepository.getById(3).getId())){
                    ticket.setEstado(this.estadoRepository.getById(3));
                    break;
                }
            }

        } catch (Exception e){
            throw new RuntimeException(e);
        }
        ticketDto.setTicketPesaje(ticket);
        ticketDto.setResponseList(responseList);
        return ticketDto;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TicketPesaje saveTicket(@RequestBody TicketPesaje ticketPesaje) {

        logger.error("save - inicio");
        if(ticketPesaje!=null){
            logger.error("save - ticketPesaje:{}",ticketPesaje.toString() );
        }
        //try{
            logger.error("SAVE TICKETPESAJE: " + ticketPesaje);
            logger.error("SAVE TICKETPESAJE - Carreta: " + ticketPesaje.getCarreta());
            TicketPesaje ticket = this.ticketPesajeService.save(ticketPesaje);
            return ticket;
        /*}catch(Exception e){
            throw new RuntimeException(e);
        }*/

    }

    @Operation(summary = "Genera pdf")
    @RequestMapping(value = "/id/DescargarPDF/{idTicketPesaje}", method = RequestMethod.POST
            ,produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity<String> generarPdf(@PathVariable("idTicketPesaje") Integer idTicketPesaje){

        try {
            byte[] bytesx = null;
            bytesx = ticketPesajeService.getAllTicketPDF(idTicketPesaje);
//                    workOrder.setPathScpEcm(storageDocument.getPath());
            logger.error("<--LOG_MC-->:BASE64:"+ Base64.getEncoder().encodeToString(bytesx));
            String bsx = Base64.getEncoder().encodeToString(bytesx);
            return new ResponseEntity<>(bsx, HttpStatus.OK);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "saveFechaHoraEntradaTicket", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public  ResponseEntity<Timestamp> saveFechaHoraEntradaTicket(@RequestBody FiltroTicketPesajeDTO filtro) {
        logger.error("guardarFHI - inicio");
        try{
            logger.error("guardarFH idTICKETPESAJE: " + filtro);
            Timestamp fechaHoraEntrada = this.ticketPesajeService.guardarFechaHoraEntradaTicket(filtro.getnSubticket(), filtro.getPesoInicial());
            if(fechaHoraEntrada != null) {
                return new ResponseEntity<>(fechaHoraEntrada, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "saveFechaHoraSalidaTicket", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public  ResponseEntity<Timestamp> saveFechaHoraSalidaTicket(@RequestBody FiltroTicketPesajeDTO filtro) {
        logger.error("guardarFHS - inicio");
        try{
            logger.error("guardarFH idTICKETPESAJES: " + filtro);
            Timestamp fechaHoraSalida = this.ticketPesajeService.guardarFechaHoraSalidaTicket(filtro.getnSubticket(), filtro.getPesoFinal());
            if(fechaHoraSalida != null) {
                return new ResponseEntity<>(fechaHoraSalida, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "saveFechaHoraTicket", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public  ResponseEntity<String> saveFechaHoraTicket(@RequestBody FiltroTicketPesajeDTO filtro) {
        logger.error("guardarFH - inicio");
        try{
            logger.error("guardarFH idTICKETPESAJE: " + filtro);
            String message = this.ticketPesajeService.guardarFechaHoraTicket(filtro.getnSubticket(), filtro.getPesoInicial(), filtro.getPesoFinal());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
