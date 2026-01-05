package com.incloud.hcp.rest;

import com.incloud.hcp.bean.MensajeBean;
import com.incloud.hcp.bean.OrdenCompraActivoCustom;
import com.incloud.hcp.bean.OrdenCompraCustom;
import com.incloud.hcp.domain.DocumentoAceptacion;
import com.incloud.hcp.domain.EstadoOrdenCompra;
import com.incloud.hcp.domain.Parametro;
import com.incloud.hcp.domain.almacen.OrdenDespacho;
import com.incloud.hcp.dto.FiltroOrdenCompraDto;
import com.incloud.hcp.dto.OrdenDespachoRespuestaDto;
import com.incloud.hcp.dto.OrdenDespachoSapDataDto;
import com.incloud.hcp.enums.OpcionGenericaEnum;
import com.incloud.hcp.enums.OrdenCompraAprobacionEnum;
import com.incloud.hcp.exception.InvalidOptionException;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenDespachoPublicarOneService;
import com.incloud.hcp.myibatis.mapper.OrdenDespachoMapper;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.service.OrdenDespachoService;
import com.incloud.hcp.service.notificacion.TratarErroresNotificacion;
import com.incloud.hcp.util.StrUtils;
import com.incloud.hcp.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/OrdenDespacho")
public class OrdenDespachoRest {

    private static final String OPCION_INVALIDA = "'%s' no es una opción valida. Las opciones aceptadas son '%s' y '%s'.";
    private static final String RECHAZO_INVALIDO= "La opción '%s' es valida. Pero, el texto rechazo no puede ser vacio o nulo.";
    private OrdenDespachoService ordenDespachoService;
    private OrdenDespachoRepository ordenDespachoRepository;
    private ParametroRepository parametroRepository;
    private EstadoOrdenCompraRepository estadoOrdenCompraRepository;
    private DocumentoAceptacionRepository documentoAceptacionRepository;
    private JCOOrdenDespachoPublicarOneService jCOOrdenDespachoPublicarOneService;
    private OrdenDespachoMapper ordenDespachoMapper;
    private TratarErroresNotificacion tratarErroresNotificacion;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrdenDespachoRest(OrdenDespachoService ordenDespachoService,
                             OrdenDespachoRepository ordenDespachoRepository, ParametroRepository parametroRepository, EstadoOrdenCompraRepository estadoOrdenCompraRepository,
                             JCOOrdenDespachoPublicarOneService jCOOrdenDespachoPublicarOneService, DocumentoAceptacionRepository documentoAceptacionRepository,
                             OrdenDespachoMapper ordenDespachoMapper, TratarErroresNotificacion tratarErroresNotificacion) {
        this.ordenDespachoService = ordenDespachoService;
        this.ordenDespachoRepository = ordenDespachoRepository;
        this.parametroRepository = parametroRepository;
        this.estadoOrdenCompraRepository = estadoOrdenCompraRepository;
        this.jCOOrdenDespachoPublicarOneService = jCOOrdenDespachoPublicarOneService;
        this.documentoAceptacionRepository = documentoAceptacionRepository;
        this.ordenDespachoMapper = ordenDespachoMapper;
        this.tratarErroresNotificacion = tratarErroresNotificacion;
    }

    @GetMapping(value = "/getOrdenCompraList/{FechaInicio}/{FechaFin}")
    public ResponseEntity<List<OrdenDespacho>> getOrdenCompraList(
            @PathVariable("FechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @PathVariable("FechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
            @RequestParam(value = "ruc", required = false) String ruc){
        try{
            List<OrdenDespacho> ordenCompraList = ordenDespachoService.getOrdenDespachoListPorFechasAndRuc(fechaInicio, fechaFin, ruc);

            if(ordenCompraList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ordenCompraList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
    public  Date sumarDiasAFecha(Date fecha, int dias){
        if (dias==0) return fecha;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }
    @GetMapping(value = "/obtenerOrdenCompraLista/{fechaInicio}/{fechaFin}/{fechaInicioPublicacion}/{fechaFinPublicacion}")
    public ResponseEntity<List<OrdenDespacho>> obtenerOrdenCompraLista(
            @PathVariable("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @PathVariable("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
            @PathVariable("fechaInicioPublicacion") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicioPublicacion,
            @PathVariable("fechaFinPublicacion") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFinPublicacion,
            @RequestParam(value = "ruc", required = false) String ruc){
        FiltroOrdenCompraDto dto = new FiltroOrdenCompraDto();
//        if(fechaFinPublicacion.compareTo(fechaInicioPublicacion) == 0) {
//            fechaFinPublicacion = this.sumarDiasAFecha(fechaFinPublicacion,1);
//        }
        dto.setFechaInicio(fechaInicio);
        dto.setFechaFin(fechaFin);
        dto.setFechaInicioPublicacion(fechaInicioPublicacion);
        dto.setFechaFinPublicacion(fechaFinPublicacion);
        //DateUtils.obtenerFechaActualPlusDay()
        dto.setRuc(ruc);
        try{

            List<OrdenDespacho> ordenCompraList = ordenDespachoService.getOrdenDespachoList(dto);

            if(ordenCompraList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ordenCompraList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @GetMapping(value = "/sendMailErrorOrdenCompraMultiplesActivos")
    public ResponseEntity<List<OrdenCompraActivoCustom>> sendMailErrorOrdenCompraMultiplesActivos(
           ){
        try{
            List<OrdenCompraActivoCustom> ordenCompraList = ordenDespachoMapper.getListaOrdenCompraMultiplesActivos();

            Parametro parametro = parametroRepository.getByModuloAndTipoAndCodigo("TRATAR_ERRORES", "ORDEN_COMPRA", "TE");
            logger.error("sendMailErrorOrdenCompraMultiplesActivos_parametro :: " + parametro.toString());
            String textoCuerpo = "Las Ordenes de Compra siguientes tienen multiples versiones activas: ";
            if(ordenCompraList != null) {
                for(OrdenCompraActivoCustom ele: ordenCompraList)   {
                    textoCuerpo = textoCuerpo + "- " + ele.getNumeroOrdenCompra();
                }
            }
            if(parametro != null) {
                logger.error("sendMailErrorOrdenCompraMultiplesActivos_parametro :: parametro != null :: " + textoCuerpo );
                tratarErroresNotificacion.enviar(parametro.getValorAsociado(), parametro.getValor(), textoCuerpo, "Error Orden Compra Multiples versiones Áctivas");
            }

            if(ordenCompraList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ordenCompraList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
    @GetMapping(value = "/reactivarOrdenCompra/{idOrdenCompra}/{idEstado}")
    public ResponseEntity<MensajeBean> reactivarOrdenCompra(
            @PathVariable("idOrdenCompra") Integer idOrdenCompra,
            @PathVariable("idEstado") Integer idEstado
            ){
        MensajeBean msg = new MensajeBean();
        try{
            msg = ordenDespachoService.reactivarOrdenDespacho(idOrdenCompra, idEstado);
            logger.error("reactivarOrdenCompra ::: msg ::: " + msg);
            if(msg == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/getEstadoReversion/{numeroOrdenCompra}")
    public ResponseEntity<List<EstadoOrdenCompra>> getEstadoReversion(@PathVariable("numeroOrdenCompra") String numeroOrdenCompra){
        List<EstadoOrdenCompra> listaEstado = new ArrayList<EstadoOrdenCompra>();
        try{
            //Parametro paramAprobado = this.parametroRepository.getParametroByModuloAndTipoAndCodigo("ORDEN_COMPRA", "ESTADO", "AP");
            //Parametro paramActiva = this.parametroRepository.getParametroByModuloAndTipoAndCodigo("ORDEN_COMPRA", "ESTADO", "AC");
            List<DocumentoAceptacion> listaHes = this.documentoAceptacionRepository.getDocumentoAceptacionByOc(numeroOrdenCompra);
            String estadoReversion = "AC";
            if(listaHes != null && listaHes.size() > 0)
                estadoReversion = "AP";

            Parametro estadoReversible = this.parametroRepository.getParametroByModuloAndTipoAndCodigo("ORDEN_COMPRA", "ESTADO", estadoReversion);
            logger.error("getEstadoReversion ::: estadoReversible ::: " + estadoReversible + " ---- " + estadoReversion);
            //logger.error("getEstadoReversion ::: paramActiva ::: " + paramActiva);
            List<EstadoOrdenCompra> listaEstadoAux = this.estadoOrdenCompraRepository.findAll();
            logger.error("getEstadoReversion ::: listaEstadoAux ::: " + listaEstadoAux);

            for(EstadoOrdenCompra ele : listaEstadoAux) {
                if(ele.getDescripcion().equalsIgnoreCase(estadoReversible.getValor())) {
                    listaEstado.add(ele);
                }
            }
            logger.error("getEstadoReversion ::: listaEstado ::: " + listaEstado);
            if(listaEstado.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listaEstado, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Devuelve lista de registros de tipo Subetapa en base a los parámetros ingresados")
    @PostMapping(value = "/getOrdenCompraAnuladosList", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrdenDespacho>> getOrdenCompraAnuladosList(@RequestBody OrdenCompraCustom filtro)  {

        logger.error("getOrdenCompraAnuladosList ::: fechaInicio ::: " + filtro.getFechaInicio());
        logger.error("getOrdenCompraAnuladosList ::: fechaFin ::: " + filtro.getFechaFin());
        java.text.SimpleDateFormat formatDate = new java.text.SimpleDateFormat("dd/MM/yyyy");


        List<OrdenDespacho> ordenCompraList = new ArrayList<OrdenDespacho>();
        try{
            String [] fechaInicioStr = filtro.getFechaInicio().split("_");
            String [] fechaFinStr = filtro.getFechaFin().split("_");

            Date fechaInicio = formatDate.parse(fechaInicioStr[0] + "/" + fechaInicioStr[1] + "/" + fechaInicioStr[2]);
            Date fechaFin = formatDate.parse(fechaFinStr[0] + "/" + fechaFinStr[1] + "/" + fechaFinStr[2]);

            Parametro paramAnulado = this.parametroRepository.getParametroByModuloAndTipoAndCodigo("ORDEN_COMPRA", "ESTADO", "AN");
            logger.error("getOrdenCompraAnuladosList ::: paramAnulado ::: " + paramAnulado);
            EstadoOrdenCompra estadoOrden = this.estadoOrdenCompraRepository.getEstadoOrdenCompraByDescripcion(paramAnulado.getValor());
            logger.error("getOrdenCompraAnuladosList ::: estadoOrden ::: " + estadoOrden);
            if(filtro.getNumeroOrdenCompra() != null && !filtro.getNumeroOrdenCompra().equalsIgnoreCase("")) {
                ordenCompraList = ordenDespachoService.getOrdenDespachoListPorEstadoFechasOrdenDespacho(filtro.getNumeroOrdenCompra(), estadoOrden.getId(), fechaInicio, fechaFin);
            }else {
                ordenCompraList = ordenDespachoService.getOrdenDespachoListPorEstadoFechas(estadoOrden.getId(), fechaInicio, fechaFin);
            }


            logger.error("getOrdenCompraAnuladosList ::: ordenCompraList ::: " + ordenCompraList);

            if(ordenCompraList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ordenCompraList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }

    }
    //============================

    @GetMapping(value = "/validarOrdenCompraReversionSap/{ebeln}")
    public ResponseEntity<MensajeBean> validarOrdenCompraReversionSap(
            @PathVariable("ebeln") String ebeln
           ){
        logger.error("validarOrdenCompraReversionSap ::: ebeln ::: " + ebeln);
        MensajeBean msg = new MensajeBean();
        try{
            OrdenDespachoSapDataDto ordenCompraSap = this.jCOOrdenDespachoPublicarOneService.extraerDataOneOrdenDespachoRFC(ebeln);
            OrdenDespacho ordenCompra = null;
            if(ordenCompraSap != null) {
                if(ordenCompraSap.getOrdenDespachoSapListValidacionLiberada() != null && ordenCompraSap.getOrdenDespachoSapListValidacionLiberada().size() > 0) {
                    if(ordenCompraSap.getOrdenDespachoSapListValidacionLiberada().get(0).getEstadoSap() != null &&
                            ordenCompraSap.getOrdenDespachoSapListValidacionLiberada().get(0).getEstadoSap().equalsIgnoreCase("L")) {
                        msg.setType("S");
                        msg.setMensaje("La orden de compra " + ebeln + " está liberada");
                        logger.error("validarOrdenCompraReversionSap ::: ebeln ::: " + "La orden de compra " + ebeln + " está liberada");
                    }else {
                        msg.setType("E");
                        msg.setMensaje("La orden de compra " + ebeln + " no se encuentra liberada");
                        logger.error("validarOrdenCompraReversionSap ::: ebeln ::: " + "La orden de compra " + ebeln + " no se encuentra liberada");
                    }
                }else {
                    msg.setType("E");
                    msg.setMensaje("No hay Resultados para la orden de compra SAP " + ebeln);
                    logger.error("validarOrdenCompraReversionSap ::: ebeln ::: " + "No hay Resultados para la orden de compra SAP " + ebeln);
                }
            }else {
                msg.setType("E");
                msg.setMensaje("No hay Resultados de SAP");
                logger.error("validarOrdenCompraReversionSap ::: ebeln ::: " + "No hay Resultados de SAP");
            }

            logger.error("validarOrdenCompraReversionSap ::: msg ::: " + msg);

            if(msg == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PutMapping(value = "/ActualizarFechaVisualizacionById/{idOrdenCompra}")
    public ResponseEntity<OrdenDespachoRespuestaDto> actualizarFechaVisualizacionById(@PathVariable("idOrdenCompra") Integer idOrdenCompra){
        try{
            logger.error("<--MC_LOG-->:OrdenCompraRest/ActualizarFechaVisualizacionById:");
            logger.error("<--MC_LOG-->:OrdenCompraRest/ActualizarFechaVisualizacionById:"+idOrdenCompra);
            OrdenDespachoRespuestaDto ordenCompra = ordenDespachoService.updateOrdenDespachoFechaVisualizacion(idOrdenCompra);
            if(ordenCompra != null){
                return  new ResponseEntity<>(ordenCompra, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
       } catch (Exception e) {
               String error = Utils.obtieneMensajeErrorException(e);
               throw new RuntimeException(error);
        }
    }

    @PutMapping(value = "/AprobarRechazarOrdenCompra/{idOrdenCompra}/{aprobarRechazar}")
    public ResponseEntity<OrdenDespachoRespuestaDto> aprobarRechazarOrdenCompra(@PathVariable("idOrdenCompra") Integer idOrdenCompra,
                                                                              @PathVariable("aprobarRechazar") OrdenCompraAprobacionEnum ordenCompraAprobacionEnum,
                                                                              @RequestBody(required = false) String textoRechazo){
        String opcion = ordenCompraAprobacionEnum.toString().trim().toUpperCase();

        if (!opcion.equals(OrdenCompraAprobacionEnum.APROBAR.toString()) && !opcion.equals(OrdenCompraAprobacionEnum.RECHAZAR.toString()))
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcion, OrdenCompraAprobacionEnum.APROBAR.toString(), OrdenCompraAprobacionEnum.RECHAZAR.toString()));

        if(opcion.equals(OrdenCompraAprobacionEnum.RECHAZAR.toString()) && (textoRechazo == null || textoRechazo.isEmpty())){
            throw new InvalidOptionException(String.format(RECHAZO_INVALIDO, opcion));
        }

        int idAprobacionEnum = ordenCompraAprobacionEnum.getId();

       try {
           logger.error("<--MC_LOG-->:OrdenCompraRest/AprobarRechazarOrdenCompra:");
           logger.error("<--MC_LOG-->:OrdenCompraRest/AprobarRechazarOrdenCompra-idOrdenCompra:"+idOrdenCompra);
           OrdenDespachoRespuestaDto ordenCompraAprobacionRechazo = ordenDespachoService.aprobarRechazarOrdenDespacho(idOrdenCompra, idAprobacionEnum, textoRechazo);
            if(ordenCompraAprobacionRechazo != null){
                return new ResponseEntity<>(ordenCompraAprobacionRechazo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @GetMapping(value = "/getOrdenCompraActivaByNumero/{numOrdenCompra}")
    public ResponseEntity<OrdenDespacho> getOrdenCompraActivaByNumero(@PathVariable("numOrdenCompra") String numOrdenCompra){
        try{
            Optional<OrdenDespacho> opOrdenCompra = ordenDespachoRepository.getOrdenDespachoActivaByNumero(numOrdenCompra);
            if(opOrdenCompra.isPresent()){
                return  new ResponseEntity<>(opOrdenCompra.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/getOrdenCompraActivaById/{idOrdenCompra}")
    public ResponseEntity<OrdenDespacho> getOrdenCompraActivaById(@PathVariable("idOrdenCompra") Integer idOrdenCompra){
        try{
            Optional<OrdenDespacho> opOrdenCompra = ordenDespachoRepository.findByIdAndIsActive(idOrdenCompra);
            if(opOrdenCompra.isPresent()){
                return  new ResponseEntity<>(opOrdenCompra.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "extraerOrdenCompraMasivo/{fechaInicio}/{fechaFin}/{enviarCorreoPublicacionOpcion}")
    public ResponseEntity<Void> extraerOrdenCompraMasivo(@PathVariable(value = "fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                                         @PathVariable(value = "fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                                         @PathVariable(value = "enviarCorreoPublicacionOpcion") OpcionGenericaEnum enviarCorreoPublicacionOpcion){
        String opcion = enviarCorreoPublicacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoPublicacion = OpcionGenericaEnum.NO.getValor();

        if (!opcion.equals(OpcionGenericaEnum.SI.toString()) && !opcion.equals(OpcionGenericaEnum.NO.toString()))
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcion, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));

        if(opcion.equals(OpcionGenericaEnum.SI.toString()))
            enviarCorreoPublicacion = OpcionGenericaEnum.SI.getValor();

        try {
            ordenDespachoService.extraerOrdenDespachoMasivoByRangoFechas(fechaInicio, fechaFin, enviarCorreoPublicacion);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @GetMapping(value = "/getOrdenCompraPdfByNumero/{numeroOrdenCompra}")
    public ResponseEntity<String> getOrdenCompraPdfByNumero(@PathVariable("numeroOrdenCompra") String numeroOrdenCompra){
        if (numeroOrdenCompra == null || numeroOrdenCompra.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        try{
            Optional<OrdenDespacho> opOrdenCompra = ordenDespachoRepository.getOrdenDespachoActivaByNumero(numeroOrdenCompra);

            if(opOrdenCompra.isPresent()){
                return new ResponseEntity<>(ordenDespachoService.getOrdenDespachoPdfContent(numeroOrdenCompra),HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @GetMapping(value = "/getContratoMarcoPdfByNumero/{numeroContratoMarco}")
    public ResponseEntity<String> getContratoMarcoPdfByNumero(@PathVariable("numeroContratoMarco") String numeroContratoMarco){
        if (numeroContratoMarco == null || numeroContratoMarco.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        try{
            Optional<OrdenDespacho> opOrdenCompra = ordenDespachoRepository.getOrdenDespachoActivaByNumero(numeroContratoMarco);

            if(opOrdenCompra.isPresent()){
                return new ResponseEntity<>(ordenDespachoService.getContratoMarcoPdfContent(numeroContratoMarco),HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "extraerContratoMarcoMasivo/{fechaInicio}/{fechaFin}/{enviarCorreoPublicacionOpcion}")
    public ResponseEntity<Void> extraerContratoMarcoMasivo(@PathVariable(value = "fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                                           @PathVariable(value = "fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                                           @PathVariable(value = "enviarCorreoPublicacionOpcion") OpcionGenericaEnum enviarCorreoPublicacionOpcion){
        String opcion = enviarCorreoPublicacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoPublicacion = OpcionGenericaEnum.NO.getValor();

        if (!opcion.equals(OpcionGenericaEnum.SI.toString()) && !opcion.equals(OpcionGenericaEnum.NO.toString()))
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcion, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));

        if(opcion.equals(OpcionGenericaEnum.SI.toString()))
            enviarCorreoPublicacion = OpcionGenericaEnum.SI.getValor();

        try {
            ordenDespachoService.extraerContratoMarcoMasivoByRangoFechas(fechaInicio, fechaFin, enviarCorreoPublicacion);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
    @PostMapping(value = "/cambioEstadoOCRechazada", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashMap<String,String>>>  cambioEstadoOCRechazada(@RequestBody List<String> numeroOrdenCompra)
    {
        try {
            List<HashMap<String,String>> infoMessage = ordenDespachoService.cambioEstadoOCRechazada(numeroOrdenCompra);
            return new ResponseEntity<>(infoMessage, HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }
}
