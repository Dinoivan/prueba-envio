package com.incloud.hcp.rest;

import com.incloud.hcp.domain.balanza.*;
import com.incloud.hcp.jco.EstadoDocumentoAceptacion.dto.EstadoDocumentoAceptacionResponse;
import com.incloud.hcp.jco.EstadoDocumentoAceptacion.dto.RangeSap;
import com.incloud.hcp.jco.EstadoDocumentoAceptacion.services.JCOEstadoDocumentoAceptacionService;
import com.incloud.hcp.jco.balanza.Carreta.dto.CarretaResponseDTO;
import com.incloud.hcp.jco.balanza.Carreta.service.JCOCarretaService;
import com.incloud.hcp.jco.balanza.CentroAlmacen.service.JCOCentroAlmacenBlzService;
import com.incloud.hcp.jco.balanza.Chofer.dto.ChoferResponseDTO;
import com.incloud.hcp.jco.balanza.Chofer.service.JCOChoferService;
import com.incloud.hcp.jco.balanza.DireccionAlternaCliente.dto.DireccionAlternaClienteArdc;
import com.incloud.hcp.jco.balanza.DireccionAlternaCliente.dto.DireccionAlternaClienteResponse;
import com.incloud.hcp.jco.balanza.DireccionAlternaCliente.services.JCODireccionAlternaClienteService;
import com.incloud.hcp.jco.balanza.DireccionAlternaProveedor.dto.DireccionAlternaProveedorResponse;
import com.incloud.hcp.jco.balanza.DireccionAlternaProveedor.services.JCODireccionAlternaProveedorService;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.dto.MaterialIDto;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.dto.MaterialOutDto;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.service.JCOMaterialOutService;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.dto.DocMaterialConsultaDto;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.dto.DocMaterialConsultaResponse;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.dto.DocMaterialGenerarSAPDto;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.dto.DocumentoMaterialResponseDto;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.service.JCODocumentoMaterialService;
import com.incloud.hcp.jco.balanza.GuiaRemision.dto.GuiaRemisionResponseDTO;
import com.incloud.hcp.jco.balanza.GuiaRemision.service.JCOGuiaRemisionService;
import com.incloud.hcp.jco.balanza.MaestroCli.dto.MaestroCliImport;
import com.incloud.hcp.jco.balanza.MaestroCli.dto.MaestroCliResponse;
import com.incloud.hcp.jco.balanza.MaestroCli.service.JCOMaestroCliService;
import com.incloud.hcp.jco.balanza.MaterialLote.dto.MaterialLoteResponse;
import com.incloud.hcp.jco.balanza.MaterialLote.service.JCOMaterialLoteService;
import com.incloud.hcp.jco.balanza.MotivosTraslado.dto.*;
import com.incloud.hcp.jco.balanza.MotivosTraslado.service.JCOMotivosTrasladoService;
import com.incloud.hcp.jco.balanza.PedidosTraslado.dto.PedidoTrasladoInput;
import com.incloud.hcp.jco.balanza.PedidosTraslado.dto.PedidosTrasladoResponse;
import com.incloud.hcp.jco.balanza.PedidosTraslado.service.JCOPedidosTrasladoService;
import com.incloud.hcp.jco.balanza.PedidosVentas.dto.PedidosVentasResponse;
import com.incloud.hcp.jco.balanza.PedidosVentas.service.JCOPedidoVentasService;
import com.incloud.hcp.jco.balanza.Proveedor.dto.ProveedorBlzFiltroBusquedaDTO;
import com.incloud.hcp.jco.balanza.Proveedor.dto.ProveedorBlzFiltroDTO;
import com.incloud.hcp.jco.balanza.Proveedor.service.JCOProveedorBlzService;
import com.incloud.hcp.jco.balanza.Series.dto.SerieConsultaResponse;
import com.incloud.hcp.jco.balanza.Series.services.JCOSeriesService;
import com.incloud.hcp.jco.balanza.Transporte.dto.TransporteResponseDTO;
import com.incloud.hcp.jco.balanza.Transporte.service.JCOTransporteService;
import com.incloud.hcp.jco.balanza.Transportista.dto.TransportistaBlzFiltroBusquedaDTO;
import com.incloud.hcp.jco.balanza.Transportista.dto.TransportistaBlzFiltroDTO;
import com.incloud.hcp.jco.balanza.Transportista.service.TransportistaService;
import com.incloud.hcp.jco.documentoAceptacion.service.JCODocumentoAceptacionService;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenCompraPublicacionService;
import com.incloud.hcp.job.*;
import com.incloud.hcp.repository.ClienteRepository;
import com.incloud.hcp.repository.DetalleTicketRepository;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.StrUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/api/util")
public class UtilRest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${war.build.datetime}")
    private String warBuildDatetime;

    private ContratoMarcoExtractionJob contratoMarcoExtractionJob;
    private DocumentoAceptacionExtractionJob documentoAceptacionExtractionJob;
    private DocumentoAceptacionHistoricExtractionJob documentoAceptacionHistoricExtractionJob;
    private OrdenCompraExtractionJob ordenCompraExtractionJob;
    private PrefacturaAnuladaUpdateJob prefacturaAnuladaUpdateJob;
    private JCOOrdenCompraPublicacionService jcoOrdenCompraPublicacionServiceService;
    private JCODocumentoAceptacionService jcoDocumentoAceptacionService;

    private JCOChoferService jcoChoferService;
    private JCOCentroAlmacenBlzService jcoCentroAlmacenBlzService;
    private JCOCarretaService jcoCarretaService;
    private JCOTransporteService jcoTransporteService;
    private JCOGuiaRemisionService jcoGuiaRemisionService;
    private JCODocumentoMaterialService jcoDocumentoMaterialService;
    private JCOSeriesService jcoSeriesService;
    private JCOMotivosTrasladoService jcoMotivosTrasladoService;
    private JCODireccionAlternaClienteService jcoDireccionAlternaClienteService;
    private JCODireccionAlternaProveedorService jcoDireccionAlternaProveedorService;
    private JCOPedidosTrasladoService jcoPedidosTrasladoService;
    private JCOPedidoVentasService jcoPedidoVentasService;
    private JCOMaterialLoteService jcoMaterialLoteService;
    private JCOMaterialOutService jcoMaterialOutService;
    private JCOMaestroCliService jcoMaestroCliService;
    private TransportistaService transportistaService;
    private JCOProveedorBlzService jcoProveedorBlzService;
    private ClienteRepository clienteRepository;
    private JCOEstadoDocumentoAceptacionService jcoEstadoDocumentoAceptacionService;
    private DetalleTicketRepository detalleTicketRepository;

    @Autowired
    public UtilRest(ContratoMarcoExtractionJob contratoMarcoExtractionJob,
                    DocumentoAceptacionExtractionJob documentoAceptacionExtractionJob,
                    DocumentoAceptacionHistoricExtractionJob documentoAceptacionHistoricExtractionJob,
                    OrdenCompraExtractionJob ordenCompraExtractionJob,
                    PrefacturaAnuladaUpdateJob prefacturaAnuladaUpdateJob,
                    JCOOrdenCompraPublicacionService jcoOrdenCompraPublicacionServiceService,
                    JCODocumentoAceptacionService jcoDocumentoAceptacionService,
                    JCOChoferService jcoChoferService,
                    JCOCarretaService jcoCarretaService,
                    JCOTransporteService jcoTransporteService,
                    JCOGuiaRemisionService jcoGuiaRemisionService,
                    JCODocumentoMaterialService jcoDocumentoMaterialService,
                    JCOSeriesService jcoSeriesService,
                    JCOMotivosTrasladoService jcoMotivosTrasladoService,
                    JCODireccionAlternaClienteService jcoDireccionAlternaClienteService,
                    JCODireccionAlternaProveedorService jcoDireccionAlternaProveedorService,
                    JCOPedidosTrasladoService jcoPedidosTrasladoService,
                    JCOPedidoVentasService jcoPedidoVentasService,
                    JCOMaterialLoteService jcoMaterialLoteService,
                    JCOMaterialOutService jcoMaterialOutService,
                    JCOMaestroCliService jcoMaestroCliService,
                    TransportistaService transportistaService,
                    JCOProveedorBlzService jcoProveedorBlzService,
                    ClienteRepository clienteRepository,
                    JCOEstadoDocumentoAceptacionService jcoEstadoDocumentoAceptacionService,
                    JCOCentroAlmacenBlzService jcoCentroAlmacenBlzService,
                    DetalleTicketRepository detalleTicketRepository) {
        this.contratoMarcoExtractionJob = contratoMarcoExtractionJob;
        this.documentoAceptacionHistoricExtractionJob = documentoAceptacionHistoricExtractionJob;
        this.documentoAceptacionExtractionJob = documentoAceptacionExtractionJob;
        this.ordenCompraExtractionJob = ordenCompraExtractionJob;
        this.prefacturaAnuladaUpdateJob = prefacturaAnuladaUpdateJob;
        this.jcoOrdenCompraPublicacionServiceService = jcoOrdenCompraPublicacionServiceService;
        this.jcoDocumentoAceptacionService = jcoDocumentoAceptacionService;
        this.jcoChoferService = jcoChoferService;
        this.jcoCarretaService = jcoCarretaService;
        this.jcoTransporteService = jcoTransporteService;
        this.jcoGuiaRemisionService = jcoGuiaRemisionService;
        this.jcoDocumentoMaterialService = jcoDocumentoMaterialService;
        this.jcoSeriesService = jcoSeriesService;
        this.jcoMotivosTrasladoService = jcoMotivosTrasladoService;
        this.jcoDireccionAlternaClienteService = jcoDireccionAlternaClienteService;
        this.jcoDireccionAlternaProveedorService = jcoDireccionAlternaProveedorService;
        this.jcoPedidosTrasladoService = jcoPedidosTrasladoService;
        this.jcoPedidoVentasService = jcoPedidoVentasService;
        this.jcoMaterialLoteService = jcoMaterialLoteService;
        this.jcoMaterialOutService = jcoMaterialOutService;
        this.jcoMaestroCliService = jcoMaestroCliService;
        this.transportistaService = transportistaService;
        this.jcoProveedorBlzService = jcoProveedorBlzService;
        this.clienteRepository = clienteRepository;
        this.jcoEstadoDocumentoAceptacionService = jcoEstadoDocumentoAceptacionService;
        this.jcoCentroAlmacenBlzService = jcoCentroAlmacenBlzService;
        this.detalleTicketRepository = detalleTicketRepository;

    }

    @GetMapping(value = "/jobs/contrato-marco-extractor-check-status")
    public ResponseEntity<String> contratoMarcoExtractorCurrentStatus() {
        return new ResponseEntity<>("ContratoMarcoExtractionJob is currently enabled: " + contratoMarcoExtractionJob.current(), HttpStatus.OK);
    }

    @GetMapping(value = "/jobs/contrato-marco-extractor-toggle-status")
    public ResponseEntity<String> contratoMarcoExtractorToggleStatus() {
        return new ResponseEntity<>("ContratoMarcoExtractionJob is now enabled: " + contratoMarcoExtractionJob.toggle(), HttpStatus.OK);
    }

    @GetMapping(value = "/jobs/documento-aceptacion-extractor-check-status")
    public ResponseEntity<String> documentoAceptacionExtractorCurrentStatus() {
        return new ResponseEntity<>("DocumentoAceptacionExtractionJob is currently enabled: " + documentoAceptacionExtractionJob.current(), HttpStatus.OK);
    }

    @GetMapping(value = "/jobs/documento-aceptacion-extractor-toggle-status")
    public ResponseEntity<String> documentoAceptacionExtractorToggleStatus() {
        return new ResponseEntity<>("DocumentoAceptacionExtractionJob is now enabled: " + documentoAceptacionExtractionJob.toggle(), HttpStatus.OK);
    }

    @GetMapping(value = "/jobs/documento-aceptacion-historic-extractor-check-status")
    public ResponseEntity<String> documentoAceptacionHistoricExtractorCurrentStatus() {
        return new ResponseEntity<>("DocumentoAceptacionHistoricExtractionJob is currently enabled: " + documentoAceptacionHistoricExtractionJob.current(), HttpStatus.OK);
    }

    @GetMapping(value = "/jobs/documento-aceptacion-historic-extractor-toggle-status")
    public ResponseEntity<String> documentoAceptacionHistoricExtractorToggleStatus() {
        return new ResponseEntity<>("DocumentoAceptacionHistoricExtractionJob is now enabled: " + documentoAceptacionHistoricExtractionJob.toggle(), HttpStatus.OK);
    }

    @GetMapping(value = "/jobs/orden-compra-extractor-check-status")
    public ResponseEntity<String> ordenCompraExtractorCurrentStatus() {
        return new ResponseEntity<>("OrdenCompraExtractionJob is currently enabled: " + ordenCompraExtractionJob.current(), HttpStatus.OK);
    }

    @GetMapping(value = "/jobs/orden-compra-extractor-toggle-status")
    public ResponseEntity<String> ordenCompraExtractorToggleStatus() {
        return new ResponseEntity<>("OrdenCompraExtractionExtractionJob is now enabled: " + ordenCompraExtractionJob.toggle(), HttpStatus.OK);
    }

    @GetMapping(value = "/jobs/prefactura-anulada-updater-check-status")
    public ResponseEntity<String> prefacturaAnuladaUpdaterCurrentStatus() {
        return new ResponseEntity<>("PrefacturaAnuladaUpdateJob is currently enabled: " + prefacturaAnuladaUpdateJob.current(), HttpStatus.OK);
    }

    @GetMapping(value = "/jobs/prefactura-anulada-updater-toggle-status")
    public ResponseEntity<String> prefacturaAnuladaUpdaterToggleStatus() {
        return new ResponseEntity<>("PrefacturaAnuladaUpdateJob is now enabled: " + prefacturaAnuladaUpdateJob.toggle(), HttpStatus.OK);
    }

    @GetMapping(value = "/process/documento-aceptacion-extraction-check-status")
    public ResponseEntity<String> currentDocumentoAceptacionExtractionProcessingStatus() {
        return new ResponseEntity<>("DocumentoAceptacion Extraction is currently processing: " + jcoDocumentoAceptacionService.currentDocumentoAceptacionExtractionProcessingState(), HttpStatus.OK);
    }

    @GetMapping(value = "/process/documento-aceptacion-extraction-force-toggle-status")
    public ResponseEntity<String> forceToggleDocumentoAceptacionExtractionProcessingStatus() {
        return new ResponseEntity<>("DocumentoAceptacion Extraction is now processing: " + jcoDocumentoAceptacionService.toggleDocumentoAceptacionExtractionProcessingState(), HttpStatus.OK);
    }

    @GetMapping(value = "process/orden-compra-extraction-check-status")
    public ResponseEntity<String> currentOrdenCompraExtractionProcessingStatus() {
        return new ResponseEntity<>("OrdenCompra Extraction is currently processing: " + jcoOrdenCompraPublicacionServiceService.currentOrdenCompraExtractionProcessingState(), HttpStatus.OK);
    }

    @GetMapping(value = "process/orden-compra-extraction-force-toggle-status")
    public ResponseEntity<String> forceToggleOrdenCompraExtractionProcessingStatus() {
        return new ResponseEntity<>("OrdenCompra Extraction is now processing: " + jcoOrdenCompraPublicacionServiceService.toggleOrdenCompraExtractionProcessingState(), HttpStatus.OK);
    }

    @GetMapping(value = "getWarBuildDatetime")
    public ResponseEntity<String> getWarBuildDatetime() {
        return new ResponseEntity<>(warBuildDatetime, HttpStatus.OK);
    }


    @PostMapping(value = "extraerChoferListManual")
    public ResponseEntity<Void> extraerChoferList(){
        try {
            logger.error("extraerChoferListManual - start");
            jcoChoferService.extraerChoferListRFC(false);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "extraerCarretaListManual")
    public ResponseEntity<Void> extraerCarretaList(){

        try {
            logger.error("extraerCarretaListManual - start");
            jcoCarretaService.extraerCarretaListRFC(false);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "extraerTransporteListManual")
    public ResponseEntity<Void> extraerTransporteList(){

        try {
            logger.error("extraerTransporteListManual - start");
            jcoTransporteService.extraerTransporteListRFC(false);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "grabarGuiaRemision")
    public ResponseEntity<GuiaRemisionResponseDTO> grabarGuiaRemision(@RequestBody GuiaRemisionResponseDTO guiaRemision){
        try {
            logger.error("extraerTransporteListManual - start");
            GuiaRemisionResponseDTO response = jcoGuiaRemisionService.grabarGuiaRemision(guiaRemision);
            logger.error("RESPONSE GRABAR GUIA REMISION: " + response);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "listar-documento-material")
    public ResponseEntity<List<DocMaterialConsultaResponse>> listarDocumentoMaterial(
            @RequestBody DocMaterialConsultaDto doc){

        try{
            logger.error("listar-documento-material Inicio");
            if(doc!=null){
                logger.error("listar-documento-material doc:{}-tipopesaje:{}",doc.getDocMaterial(), doc.getTipoPesaje());
            }
            List<DocMaterialConsultaResponse> listDocMaterial = this.jcoDocumentoMaterialService
                    .consultaDocumentoMaterialRFC(doc);
            return ResponseEntity.ok().body(listDocMaterial);
        }catch(Exception e){
            logger.error("listar-documento-material no inicio");
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }

//    @RequestMapping(value = "grabarCarreta", method = RequestMethod.POST, produces =
//            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public CarretaResponseDTO save(@RequestBody CarretaResponseDTO carretaResponseDTO){
//        try{
//            logger.error("grabarCarretaListManual - start");
//            return this.jcoCarretaService.grabarCarreta(carretaResponseDTO);
//        } catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    @PostMapping(value = "grabarTransporte")
//    public ResponseEntity<TransporteResponseDTO> grabarTransporte(@RequestBody TransporteResponseDTO transporte){
//        try {
//            logger.error("grabarTransporteListManual - start");
//            jcoTransporteService.grabarTransporte(transporte);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
//            throw new RuntimeException(error);
//        }
//    }
//
//    @RequestMapping(value = "grabarChofer", method = RequestMethod.POST, produces =
//            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public ChoferResponseDTO save(@RequestBody ChoferResponseDTO choferResponseDTO){
//        try{
//            logger.error("grabarChoferListManual - start");
//            return this.jcoChoferService.grabarChofer(choferResponseDTO);
//        } catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }

    @RequestMapping(value = "crearDireccionAlternaProveedor/{lifnr}", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public DireccionAlternaProveedorResponse save(@PathVariable String lifnr, @RequestBody DireccionAlternaProveedorResponse direccionAlternaProveedorResponse){
        try{
            logger.error("crearDireccionAlternaProveedorListManual - start");
            return this.jcoDireccionAlternaProveedorService.crearDireccionAlternaProveedor(lifnr, direccionAlternaProveedorResponse);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "crearDireccionAlternaCliente/{kna1Kunnr}", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public DireccionAlternaClienteResponse save(@PathVariable String kna1Kunnr, @RequestBody DireccionAlternaClienteArdc direccionAlternaClienteArdc){
        try{
            logger.error("crearDireccionAlternaCliente - start");
            return this.jcoDireccionAlternaClienteService.crearDireccionAlternarCliente(kna1Kunnr, direccionAlternaClienteArdc);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Se puede usar para crear o actualizar")
    @PostMapping(value = "actualizaTransporte")
    public ResponseEntity<TransporteResponseDTO> actualizaTransporte(@RequestBody TransporteResponseDTO transporte){
        try {
            logger.error("actualizaTransporte - start");
            jcoTransporteService.actualizaTransporte(transporte);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }
    @Operation(summary = "Se puede usar para crear o actualizar")
    @RequestMapping(value = "actualizaChofer", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ChoferResponseDTO actualizaChofer(@RequestBody ChoferResponseDTO choferResponseDTO){
        try{
            logger.error("actualizarChoferListManual - start");
            return this.jcoChoferService.actualizarChofer(choferResponseDTO);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @Operation(summary = "Se puede usar para crear o actualizar")
    @RequestMapping(value = "actualizaCarreta", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public CarretaResponseDTO actualizaCarreta(@RequestBody CarretaResponseDTO carretaResponseDTO){
        try{
            logger.error("actualizarChoferListManual - start");
            return this.jcoCarretaService.actualizarCarreta(carretaResponseDTO);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(value = "generar-documento-material", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<DocumentoMaterialResponseDto> generarDocMaterial(@RequestBody List<DocMaterialGenerarSAPDto> doc){
        //try{
            logger.error("generarDocMaterial - inicio");
            logger.error("DOCMATERIALGENERARSAPDTO doc: " + doc);
        List<DocumentoMaterialResponseDto> lista = new ArrayList<>();
        try {
            lista = this.jcoDocumentoMaterialService.generarDocumentoMaterialRfc(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < lista.size(); i++) {
                DocumentoMaterialResponseDto x = lista.get(i);
            //for (DocumentoMaterialResponseDto x:lista) {
                if(x.getMensajeError().equalsIgnoreCase("Documento Material creado")) {
                    //DocMaterialGenerarSAPDto obj = this.getTicket(x.getSubticket(),doc);
                    DocMaterialGenerarSAPDto obj = doc.get(i);
                    if (obj.getId() != 0) {
                        //detalleTicketRepository.updateLoToPosicionDocumentoMaterialEjercicio(x.getSubticket(), Integer.parseInt(x.getZeile()), x.getDocumentoMaterial(), x.getEjercio(), Double.parseDouble(x.getPoMenge()), obj.getLote(), Double.parseDouble(obj.getCantidad()), obj.getAlmacen(),obj.getDocumentoTraslado());
                        detalleTicketRepository.updateLoToPosicionDocumentoMaterialEjercicio(x.getSubticket(), obj.getPosicion(), x.getDocumentoMaterial(), x.getEjercio(), Double.parseDouble(x.getPoMenge()), obj.getLote(), Double.parseDouble(obj.getCantidad()), obj.getAlmacen(),obj.getDocumentoTraslado(),obj.getNumeroPedido(),Integer.parseInt(x.getZeile()));

                    } else {
                        Calendar calendarActual = Calendar.getInstance();
                        calendarActual.setTime(new Date());
                        calendarActual.add(Calendar.HOUR, -5);
                        Timestamp actual = new Timestamp(calendarActual.getTime().getTime());
                        LocalDateTime fechaActual = actual.toLocalDateTime();

                        DetalleTicket deta = new DetalleTicket();
                        deta.setCodigoAlmacen(obj.getAlmacen());
                        Estado estado = new Estado();
                        estado.setId(1);
                        deta.setEstado(estado);
                        deta.setSubticket(x.getSubticket());
                        TicketPesaje ticketPesaje = new TicketPesaje();
                        ticketPesaje.setId(obj.getIdTicketPesaje());
                        deta.setTicketPesaje(ticketPesaje);
                        deta.setEstadoDispoElimi(true);
                        deta.setTipoProducto(obj.getTipoProducto());
                        deta.setFechaModificacion(Timestamp.valueOf(fechaActual));
                        deta.setTipoPesaje(obj.getTipoPesaje());
                        deta.setUnidadMedida(obj.getUnidadMedida());
                        //deta.setPosicion(Integer.parseInt(x.getZeile()));
                        deta.setPosicion(obj.getPosicion());
                        deta.setPosicionDocumento(Integer.parseInt(x.getZeile()));
                        deta.setDocMaterial(x.getDocumentoMaterial());
                        deta.setEjercicio(x.getEjercio());
                        deta.setPesoSap(Double.parseDouble(x.getPoMenge()));
                        deta.setLote(obj.getLote());
                        deta.setCantidad(Double.parseDouble(obj.getCantidad()));
                        deta.setIsPosicion("1");
                        deta.setDocumentoTraslado(obj.getDocumentoTraslado());
                        detalleTicketRepository.save(deta);
                    }
                }
            }

            return lista;
        /*} catch (Exception e){
            throw new RuntimeException(e);
        }*/
    }
    /*private DocMaterialGenerarSAPDto getTicket(Integer subTicket, List<DocMaterialGenerarSAPDto> doc){
        for (DocMaterialGenerarSAPDto obj: doc) {
            if(obj.getTicketPesaje().equalsIgnoreCase(subTicket)){
                return obj;
            }
        }
        return null;
    }*/

//    @RequestMapping(value = "generar-documento-material-v2", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public DocumentoMaterialResponseDto generarDocMaterialV2(@RequestBody DocMaterialGenerarSAPDto doc){
//        try{
//            logger.error("generarDocMaterial - inicio");
//            logger.error("DOCMATERIALGENERARSAPDTO doc: " + doc);
//            return this.jcoDocumentoMaterialService.generarDocumentoMaterialRfc(doc);
//
//        } catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }

    @RequestMapping(value = "consulta-series/{serie_werks}", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<SerieConsultaResponse> consultaSerie(@PathVariable String serie_werks){
        try{
            logger.error("UTILREST - consultaSerie");
            return this.jcoSeriesService.consultaSerie(serie_werks);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value="extraerMotivosTrasladoListManual", method = RequestMethod.POST, produces =
    {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<MotivosTrasladoResponseDTO> extraerMotivosTrasladoList(){
    try{
        logger.error("UTILREST - extraer Motivos Traslado");
    return this.jcoMotivosTrasladoService.extraerMotivoTrasladoListRFC();
    } catch (Exception e){
        throw new RuntimeException(e);
    }
    }
    @RequestMapping(value="extraerMotivosOtrosListManual", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<MotivosOtroResponseDTO> extraerMotivosOtrosListManual(){
        try{
            logger.error("UTILREST - extraer Motivos Traslado");
            return this.jcoMotivosTrasladoService.extraerMotivoOtrosListRFC();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value="extraerModoTransporteListManual", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<ModoTransporteResponseDTO> extraerModoTransporteListManual(){
        try{
            logger.error("UTILREST - extraer Motivos Traslado");
            return this.jcoMotivosTrasladoService.extraerModoTransporteListRFC();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value="TipoMovimientoListManual", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<TipoMovimientoResponseDTO> extraerTipoMovimientoListRFC(){
        try{
            logger.error("UTILREST - extraer Motivos Traslado");
            return this.jcoMotivosTrasladoService.extraerTipoMovimientoListRFC();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value="UnidadMedidaListManual", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<UnidadMedidaResponseDTO> extraerUnidadMedidaListRFC(){
        try{
            logger.error("UTILREST - extraer Motivos Traslado");
            return this.jcoMotivosTrasladoService.extraerUnidadMedidaListRFC();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value="IndicadorServicioListManual", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<IndicadorServicioResponseDTO> extraerIndicadorServicioListRFC(){
        try{
            logger.error("UTILREST - extraer Motivos Traslado");
            return this.jcoMotivosTrasladoService.extraerIndicadorServicioListRFC();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value="tipoLocacion", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<TipoLocacionDTO> tipoLocacion(){
        try{
            logger.error("UTILREST - extraer Motivos Traslado");//ZFPE_MM_EXTRAE_EXPTOLLEG
            return this.jcoMotivosTrasladoService.tipoLocacion();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value="tipoPuertoList", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<TipoLocacionDTO> tipoPuertoList(){
        try{
            logger.error("UTILREST - extraer Motivos Traslado");//ZFPE_MM_EXTRAE_EXPTOLLEG
            return this.jcoMotivosTrasladoService.tipoPuertoList();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value="puertoLlegadaList", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<PuertoAeropuertoDTO> puertoLlegadaList(){
        try{
            logger.error("UTILREST - extraer Motivos Traslado");//ZFPE_MM_EXTRAE_EXPTOLLEG
            return this.jcoMotivosTrasladoService.puertoLlegadaList();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @RequestMapping(value="aeropuertoLlegadaList", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<PuertoAeropuertoDTO> aeropuertoLlegaList(){
        try{
            logger.error("UTILREST - extraer Motivos Traslado");//ZFPE_MM_EXTRAE_EXPTOLLEG
            return this.jcoMotivosTrasladoService.aeropuertoLlegadaList();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @RequestMapping(value = "consulta-diralt-cliente/{kna1Kunnr}", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<DireccionAlternaClienteResponse> consultaDiraltCliente(@PathVariable String kna1Kunnr){
        try{
            logger.error("UTILREST - consultaSerie");
            return this.jcoDireccionAlternaClienteService.consultaDirAltCliente(kna1Kunnr);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(value = "consulta-diralt-proveedor/{lifnr}", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<DireccionAlternaProveedorResponse> consultaDireccionAlterna(@PathVariable String lifnr){
        try{
            logger.error("UTILREST - consulta Direccion Alterna");
            return this.jcoDireccionAlternaProveedorService.consultaDireccionAlterna(lifnr);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(value = "consulta-pedidos-traslado", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<PedidosTrasladoResponse> consultaPedTraslado(@RequestBody PedidoTrasladoInput pedidoTrasladoInput){
        try{
            logger.error("UTILREST - consultaPedTraslado");
            return this.jcoPedidosTrasladoService.consultaPedTraslado(pedidoTrasladoInput);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(value = "consulta-pedidos-ventas/{vbapMatnr}/{piWerks}", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<PedidosVentasResponse> consultaPedVentas(@PathVariable String vbapMatnr,
                                                         @PathVariable String piWerks){
        try{
            logger.error("UTILREST - consultaPedVentas");
            return this.jcoPedidoVentasService.consultaPedVentas(vbapMatnr, piWerks);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(value = "consulta-material-lote/{piWerks}/{piLgort}", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<MaterialLoteResponse> consultaMaterialLote(@PathVariable String piWerks,
                                                           @PathVariable String piLgort){
        try{
            logger.error("UTILREST - consultaMaterialLote");
            return this.jcoMaterialLoteService.consultaMaterialLote(piWerks,piLgort);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(value = "consulta-material", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public MaterialOutDto consultaMaterialOut(@RequestBody MaterialIDto materialIDto){
        try{
            logger.error("UTILREST - material Out");
            return this.jcoMaterialOutService.consultaMaterialRFC(materialIDto);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(value = "consulta-maestro-cli", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<MaestroCliResponse> consultaMaestroCli(@RequestBody MaestroCliImport maestroCliImport){
        try{
            logger.error("UTILREST - consultaMaterialLote");
            return this.jcoMaestroCliService.consultaMaestro(maestroCliImport);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @GetMapping(value = "consulta-transportistas")
    public List<Transportista> consultaTransportistas() {
        try{
            return this.transportistaService.getAllTransportistas();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(value = "crear-transportista", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Transportista crearTransportista(@RequestBody Transportista transportista){
        try{
            return this.transportistaService.createTransportista(transportista);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "extraerTransportistasListManual")
    public ResponseEntity<Void> extraerTransportistas(@RequestBody TransportistaBlzFiltroDTO dto){
        try {
            logger.error("extraerTransportistasListManual - start");
            transportistaService.extraerTransportistasListRFC(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }
    @PostMapping(value = "extraerProveedoresListManual")
    public ResponseEntity<Void> extraerProveedores(@RequestBody ProveedorBlzFiltroDTO dto){
        try {
            logger.error("extraerProveedoresListManual - start");
            jcoProveedorBlzService.extraerProveedorListRFC(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }


    @RequestMapping(value = "cambiar-estado-proveedor/{proveedorblzId}",method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ProveedorBLZ cambiarEstadoProveedor(@PathVariable Long proveedorblzId) {
        try{
            return this.jcoProveedorBlzService.cambiarEstado(proveedorblzId);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(value = "cambiar-estado-transportista/{transportistaId}",method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Transportista cambiarEstadoTransportista(@PathVariable Long transportistaId) {
        try{
            return this.transportistaService.cambiarEstado(transportistaId);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(value = "extraerTransportistaSAP_BTP",method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String extraerTransportistaSAP_BTP() throws Exception {
        //try{
            this.transportistaService.extraerTransportistasListRFC(null);
            return "Se sincronizó correctamente";
        /*}catch (Exception e){
            throw new RuntimeException(e);
        }*/
    }
    @PostMapping(value = "consulta-transportistas-filtro-blz")
    public List<Transportista> getAllTransportistasBlzByFiltro(@RequestBody TransportistaBlzFiltroBusquedaDTO dto) {
        try{
            return this.transportistaService.getAllTransportistasBlzByFiltro(dto);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @GetMapping(value = "consulta-proveedores-blz")
    public List<ProveedorBLZ> consultaProveedoresBlz() {
        try{
            return this.jcoProveedorBlzService.getAllProveedoresBlz();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "consulta-proveedores-filtro-blz")
    public List<ProveedorBLZ> getAllProveedoresBlzByFiltro(@RequestBody ProveedorBlzFiltroBusquedaDTO dto) {
        try{
            return this.jcoProveedorBlzService.getAllProveedoresBlzByFiltro(dto);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "consulta-proveedores-filtro-blz/{idGuia}")
    public List<ProveedorBLZ> getAllProveedoresBlzByFiltroIdGuia(@PathVariable Integer idGuia, @RequestBody ProveedorBlzFiltroBusquedaDTO dto) {
        try{
            return this.jcoProveedorBlzService.getProveedorBLZByAcreedor(idGuia);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "extrae-maestro-cliente", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void extraeMaestroCliente(){
        try{
            MaestroCliImport maestroCliImport = new MaestroCliImport();
            logger.error("[extraeMaestroCliente]:Inicio");
            logger.error("[extraeMaestroCliente]:maestroCliImport:{}", maestroCliImport);
            List<MaestroCliResponse> list =this.jcoMaestroCliService.consultaMaestro(maestroCliImport);
            logger.error("[extraeMaestroCliente]:list:{}", list);
            list.forEach(item ->{
                String ruc = item.getStcd1();
                String deudor = item.getKunnr();
                logger.error("[extraeMaestroCliente]:ruc:{}-deudor:{}", ruc, deudor);
                List<Cliente> listResponse =this.clienteRepository.getClienteByByRucDeudor(ruc, deudor);
                logger.error("[extraeMaestroCliente]:listResponse:{}", listResponse);
                if(listResponse.isEmpty()){
                    Cliente cliente = new Cliente();
                    cliente.setDireccion(item.getStreet());
                    cliente.setCanal(item.getVtweg());
                    cliente.setEstado(true);
                    cliente.setCorreo(item.getSmtpAddr());
                    cliente.setRuc(item.getStcd1());
                    cliente.setOrigen("SAP");
                    cliente.setRazonSocial(item.getName1());
                    cliente.setNumDeudor(item.getKunnr());
                    cliente.setOrganizacionVenta(item.getVkorg());
                    cliente.setSector(item.getSpart());
                    cliente.setRegion(item.getRegion());
                    cliente.setBezei(item.getBezei());
                    cliente.setCity1(item.getCity1());
                    cliente.setCity2(item.getCity2());
                    logger.error("[extraeMaestroCliente]:cliente:{}", cliente);
                    this.clienteRepository.save(cliente);
                }
            });
        }catch (Exception e){
            logger.error("[extraeMaestroCliente]:No se realizo el proceso de extraccion");
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "cambiar-estado-cliente/{clienteId}",method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public Cliente cambiarEstadoCliente(@PathVariable Long clienteId) {
        try{
            logger.error("[cambiarEstadoCliente]:Inicio");
            logger.error("[cambiarEstadoCliente]:clienteId:{}", clienteId);
            return this.jcoMaestroCliService.cambiarEstado(clienteId);
        }catch (Exception e){
            logger.error("[cambiarEstadoCliente]:No se cambio el estado");
            throw new RuntimeException(e);
        }
    }


    @RequestMapping(value = "get-all-clientes",method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cliente> getAllClientes() {
        try{
            logger.error("[obtenerCliente]:Inicio");
            return this.jcoMaestroCliService.findAll();
        }catch (Exception e){
            logger.error("[obtenerCliente]: ERROR: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "crear-actualizar-cliente",method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public Cliente crearActualizarCliente(@RequestBody Cliente cliente) {
        try{
            logger.error("[crearActualizaCliente]:Inicio");
            logger.error("[crearActualizarCliente]:cliente:{}", cliente);
            if(cliente.getId()== null){
                cliente.setOrigen("BTP");
                cliente.setEstado(true);
                cliente.setFechaCreacion(DateUtils.getCurrentTimestamp());
                return this.clienteRepository.save(cliente);
            }else {
                Cliente clienteResponse = this.clienteRepository.getClienteByID(cliente.getId());
                clienteResponse.setDireccion(cliente.getDireccion());
                clienteResponse.setCanal(cliente.getCanal());
                clienteResponse.setCorreo(cliente.getCorreo());
                clienteResponse.setRuc(cliente.getRuc());
                clienteResponse.setRazonSocial(cliente.getRazonSocial());
                clienteResponse.setNumDeudor(cliente.getNumDeudor());
                clienteResponse.setOrganizacionVenta(cliente.getOrganizacionVenta());
                clienteResponse.setSector(cliente.getSector());
                return this.clienteRepository.save(clienteResponse);
            }
        }catch (Exception e){
            logger.error("[crearActualizarCliente]:No se cambio el estado");
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "consulta-estado-documento-aceptacion", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<EstadoDocumentoAceptacionResponse> consultarEstadoDocAceptacion(@RequestBody List<RangeSap> rangeSap){
        try{
            logger.error("UTILREST - consulta-estado-documento-aceptacion");
            return this.jcoEstadoDocumentoAceptacionService.extraerEstadoDocumentoAceptacionRFC(rangeSap);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "extraer-centro-almacen-blz")
    public ResponseEntity<Void> extraerCentroAlmacenBlz(){
        try {
            logger.error("extraerCentroAlmacenBlz - start");
            jcoCentroAlmacenBlzService.extraerCentroAlmacenBlz();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }
    @GetMapping(value = "consulta-centro-almacen/{centro}")
    public ResponseEntity<List<CentroAlmacenBlz>> consultaCentroAlmacenBlz(@PathVariable String centro){
        try {
            logger.error("consultaCentroAlmacenBlz - start");
            List<CentroAlmacenBlz> list = jcoCentroAlmacenBlzService.getCentroAlmacenByCentro(centro);
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }
    @PostMapping(value = "get-all-centro-almacen")
    public ResponseEntity<List<CentroAlmacenBlz>> getAllCentroAlmacen(){
        try {
            logger.error("getAllCentroAlmacen - start");
            List<CentroAlmacenBlz> list = jcoCentroAlmacenBlzService.getAllCentroAlmacen();
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }

    @RequestMapping(value = "getCentrosBlz", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<CentroAlmacenBlz>> getCentrosBlz() {
        List<CentroAlmacenBlz> lisCentrosBlz = this.jcoCentroAlmacenBlzService.getAllCentroAlmacen();
        return ResponseEntity.ok(lisCentrosBlz);
    }
}
