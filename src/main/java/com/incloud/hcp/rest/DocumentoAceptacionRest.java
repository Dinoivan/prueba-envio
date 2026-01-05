package com.incloud.hcp.rest;

import com.incloud.hcp.domain.*;
import com.incloud.hcp.dto.DocumentoAceptacionDto;
import com.incloud.hcp.dto.DocumentoAceptacionOutDTO;
import com.incloud.hcp.dto.EstadoDocumentoAceptacionDto;
import com.incloud.hcp.dto.FiltroDocumentoDto;
import com.incloud.hcp.enums.OpcionGenericaEnum;
import com.incloud.hcp.exception.InvalidOptionException;
import com.incloud.hcp.pdf.bean.FieldConformidadServicioPdfDTO;
import com.incloud.hcp.pdf.bean.FieldEntradaMercaderiaPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterConformidadServicioPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterEntradaMercaderiaPdfDTO;
import com.incloud.hcp.repository.DocumentoAceptacionDetalleRepository;
import com.incloud.hcp.repository.EstadoDocumentoAceptacionRepository;
import com.incloud.hcp.service.*;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/DocumentoAceptacion")
public class DocumentoAceptacionRest {

    private static final String OPCION_INVALIDA = "'%s' no es una opción valida. Las opciones aceptadas son '%s' y '%s'.";

    private DocumentoAceptacionService documentoAceptacionService;
    private OrdenCompraService ordenCompraService;
    private SociedadService sociedadService;
    private ProveedorService proveedorService;
    private DocumentoAceptacionNeoService documentoAceptacionNeoService;
    private DocumentoAceptacionDetalleService documentoAceptacionDetalleService;
    private DocumentoAceptacionDetalleRepository documentoAceptacionDetalleRepository;
    private EstadoDocumentoAceptacionRepository estadoDocumentoAceptacionRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DocumentoAceptacionRest(DocumentoAceptacionService documentoAceptacionService,
                                   OrdenCompraService ordenCompraService,
                                   SociedadService sociedadService,
                                   ProveedorService proveedorService,
                                   DocumentoAceptacionNeoService documentoAceptacionNeoService,
                                   DocumentoAceptacionDetalleService documentoAceptacionDetalleService,
                                   DocumentoAceptacionDetalleRepository documentoAceptacionDetalleRepository,
                                   EstadoDocumentoAceptacionRepository estadoDocumentoAceptacionRepository) {
        this.documentoAceptacionService = documentoAceptacionService;
        this.ordenCompraService = ordenCompraService;
        this.sociedadService = sociedadService;
        this.proveedorService = proveedorService;
        this.documentoAceptacionNeoService = documentoAceptacionNeoService;
        this.documentoAceptacionDetalleService = documentoAceptacionDetalleService;
        this.documentoAceptacionDetalleRepository = documentoAceptacionDetalleRepository;
        this.estadoDocumentoAceptacionRepository = estadoDocumentoAceptacionRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<DocumentoAceptacion>> getAllDocumentoAceptacion() {
        List<DocumentoAceptacion> documentoAceptacionList = documentoAceptacionService.getAllDocumentoAceptacion();

        if (documentoAceptacionList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(documentoAceptacionList, HttpStatus.OK);
    }

    @GetMapping(value = "/getDocumentoAceptacionList")
    public ResponseEntity<List<DocumentoAceptacionDto>> getDocumentoAceptacionList(
            @RequestParam(value = "FechaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam(value = "FechaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
            @RequestParam(value = "ruc", required = false) String ruc,
            @RequestParam(value = "numeroDocumentoAceptacion", required = false) String numeroDocumentoAceptacion,
            @RequestParam(value = "numeroOrdenCompra", required = false) String numeroOrdenCompra,
            @RequestParam(value = "numeroGuiaProveedor", required = false) String numeroGuiaProveedor,
            @RequestParam(value = "fechaContabilizacionDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaContabilizacionDesde,
            @RequestParam(value = "fechaContabilizacionHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaContabilizacionHasta) {
        try {
            logger.error("fechaInicio " + fechaInicio +
                    " fechaFin " + fechaFin +
                    " fRuc " + ruc +
                    " fNumeroDocumentoAceptacion " + numeroDocumentoAceptacion +
                    " fNumeroOrdenCompra " + numeroOrdenCompra +
                    " fNumeroGuiaProveedor " + numeroGuiaProveedor +
                    " nfechaContabilizacionDesde " + fechaContabilizacionDesde +
                    " nfechaContabilizacionHasta " + fechaContabilizacionHasta);
            List<DocumentoAceptacionDto> documentoAceptacionList = new ArrayList<>();
            documentoAceptacionList = documentoAceptacionService.getDocumentoAceptacionFiltro(
                    fechaInicio,
                    fechaFin,
                    ruc,
                    numeroDocumentoAceptacion,
                    numeroOrdenCompra,
                    numeroGuiaProveedor,
                    fechaContabilizacionDesde,
                    fechaContabilizacionHasta);
            if (documentoAceptacionList != null && documentoAceptacionList.size() > 0) {
                for (DocumentoAceptacionDto da : documentoAceptacionList) {
                    logger.error("da__da " + da);
                    List<DocumentoAceptacionDetalle> listDad = this.documentoAceptacionDetalleRepository.getDocumentoAceptacionDetalleListByIdDocumentoAceptacion(da.getId());
                    if (!listDad.isEmpty()) {
                        da.setDocumentoAceptacionDetalleList(listDad);
                    }
                    logger.error("da.getIdEstadoDocumentoAceptacion()_ " + da.getIdEstadoDocumentoAceptacion());
                    Optional<EstadoDocumentoAceptacion> estadoDa = this.estadoDocumentoAceptacionRepository.getEstadoById(da.getIdEstadoDocumentoAceptacion());
                    logger.error("estado___no");
                    if(estadoDa.isPresent()){
                        logger.error("estadoDa_ " + estadoDa.get());
                        EstadoDocumentoAceptacion estado = estadoDa.get();
                        EstadoDocumentoAceptacionDto estadoDto = new EstadoDocumentoAceptacionDto();
                        estadoDto.setId(estado.getId());
                        estadoDto.setDescripcion(estado.getDescripcion());
                        da.setEstadoDocumentoAceptacion(estadoDto);
                    }
                }
            }

            if (documentoAceptacionList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(documentoAceptacionList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "extraerDocumentoAceptacionMasivo/{fechaInicio}/{fechaFin}/{aprobarOrdenCompraOpcion}/{enviarCorreoAprobacionOpcion}")
    public ResponseEntity<Void> extraerDocumentoAceptacionMasivo(@PathVariable(value = "fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                                                 @PathVariable(value = "fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                                                 @PathVariable(value = "aprobarOrdenCompraOpcion") OpcionGenericaEnum aprobarOrdenCompraOpcion,
                                                                 @PathVariable(value = "enviarCorreoAprobacionOpcion") OpcionGenericaEnum enviarCorreoAprobacionOpcion) {
        String opcionAprobarOC = aprobarOrdenCompraOpcion.toString().trim().toUpperCase();
        boolean aprobarOrdenCompra = OpcionGenericaEnum.NO.getValor();

        if (!opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()) && !opcionAprobarOC.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionAprobarOC, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else {
            if (opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()))
                aprobarOrdenCompra = OpcionGenericaEnum.SI.getValor();
        }

        String opcionEnviarCorreo = enviarCorreoAprobacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoAprobacion = OpcionGenericaEnum.NO.getValor();

        if (!opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()) && !opcionEnviarCorreo.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionEnviarCorreo, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else {
            if (opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()))
                enviarCorreoAprobacion = OpcionGenericaEnum.SI.getValor();
        }

        try {
            LocalDate localDateFechaInicio = DateUtils.utilDateToLocalDate(fechaInicio);
            LocalDate localDateFechaFin = DateUtils.utilDateToLocalDate(fechaFin);

            documentoAceptacionService.extraerDocumentoAceptacionMasivoByRangoFechas(localDateFechaInicio, localDateFechaFin, aprobarOrdenCompra, enviarCorreoAprobacion);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "extraerGuiasAnuladasDespacho/{fechaInicio}/{fechaFin}/{aprobarOrdenCompraOpcion}/{enviarCorreoAprobacionOpcion}")
    public ResponseEntity<Void> extraerGuiasAnuladasDespacho(@PathVariable(value = "fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                                                 @PathVariable(value = "fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                                                 @PathVariable(value = "aprobarOrdenCompraOpcion") OpcionGenericaEnum aprobarOrdenCompraOpcion,
                                                                 @PathVariable(value = "enviarCorreoAprobacionOpcion") OpcionGenericaEnum enviarCorreoAprobacionOpcion) {
        String opcionAprobarOC = aprobarOrdenCompraOpcion.toString().trim().toUpperCase();
        boolean aprobarOrdenCompra = OpcionGenericaEnum.NO.getValor();

        if (!opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()) && !opcionAprobarOC.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionAprobarOC, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else {
            if (opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()))
                aprobarOrdenCompra = OpcionGenericaEnum.SI.getValor();
        }

        String opcionEnviarCorreo = enviarCorreoAprobacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoAprobacion = OpcionGenericaEnum.NO.getValor();

        if (!opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()) && !opcionEnviarCorreo.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionEnviarCorreo, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else {
            if (opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()))
                enviarCorreoAprobacion = OpcionGenericaEnum.SI.getValor();
        }

        try {
            LocalDate localDateFechaInicio = DateUtils.utilDateToLocalDate(fechaInicio);
            LocalDate localDateFechaFin = DateUtils.utilDateToLocalDate(fechaFin);

            documentoAceptacionService.extraerGuiasAnuladasDespacho(localDateFechaInicio, localDateFechaFin, aprobarOrdenCompra, enviarCorreoAprobacion);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "extraerDocumentoAceptacionPorNumOrdenCompraYNumDocAceptacion/{numeroOrdenCompra}/{numeroDocumentoAceptacion}/{aprobarOrdenCompraOpcion}/{enviarCorreoAprobacionOpcion}")
    public ResponseEntity<String> extraerDocumentoAceptacionPorNumOrdenCompraYNumDocAceptacion(@PathVariable(value = "numeroOrdenCompra") String numeroOrdenCompra,
                                                                                     @PathVariable(value = "numeroDocumentoAceptacion") String numeroDocumentoAceptacion,
                                                                                     @PathVariable(value = "aprobarOrdenCompraOpcion") OpcionGenericaEnum aprobarOrdenCompraOpcion,
                                                                                     @PathVariable(value = "enviarCorreoAprobacionOpcion") OpcionGenericaEnum enviarCorreoAprobacionOpcion) {
        String opcionAprobarOC = aprobarOrdenCompraOpcion.toString().trim().toUpperCase();
        boolean aprobarOrdenCompra = OpcionGenericaEnum.NO.getValor();

        if (!opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()) && !opcionAprobarOC.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionAprobarOC, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else {
            if (opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()))
                aprobarOrdenCompra = OpcionGenericaEnum.SI.getValor();
        }

        String opcionEnviarCorreo = enviarCorreoAprobacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoAprobacion = OpcionGenericaEnum.NO.getValor();

        if (!opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()) && !opcionEnviarCorreo.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionEnviarCorreo, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else {
            if (opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()))
                enviarCorreoAprobacion = OpcionGenericaEnum.SI.getValor();
        }

        try {
            String respuesta = documentoAceptacionService.extraerDocumentoAceptacionByNumOrdenCompraAndNumDocAceptacion(numeroOrdenCompra, numeroDocumentoAceptacion, aprobarOrdenCompra, enviarCorreoAprobacion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "getExtraerDocumentoAceptacionPorNumOrdenCompraYNumDocAceptacion/{numeroOrdenCompra}/{numeroDocumentoAceptacion}/{aprobarOrdenCompraOpcion}/{enviarCorreoAprobacionOpcion}")
    public ResponseEntity<String> getExtraerDocumentoAceptacionPorNumOrdenCompraYNumDocAceptacion(@PathVariable(value = "numeroOrdenCompra") String numeroOrdenCompra,
                                                                                               @PathVariable(value = "numeroDocumentoAceptacion") String numeroDocumentoAceptacion,
                                                                                               @PathVariable(value = "aprobarOrdenCompraOpcion") OpcionGenericaEnum aprobarOrdenCompraOpcion,
                                                                                               @PathVariable(value = "enviarCorreoAprobacionOpcion") OpcionGenericaEnum enviarCorreoAprobacionOpcion) {
        String opcionAprobarOC = aprobarOrdenCompraOpcion.toString().trim().toUpperCase();
        boolean aprobarOrdenCompra = OpcionGenericaEnum.NO.getValor();

        if (!opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()) && !opcionAprobarOC.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionAprobarOC, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else {
            if (opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()))
                aprobarOrdenCompra = OpcionGenericaEnum.SI.getValor();
        }

        String opcionEnviarCorreo = enviarCorreoAprobacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoAprobacion = OpcionGenericaEnum.NO.getValor();

        if (!opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()) && !opcionEnviarCorreo.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionEnviarCorreo, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else {
            if (opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()))
                enviarCorreoAprobacion = OpcionGenericaEnum.SI.getValor();
        }

        try {
            String respuesta = documentoAceptacionService.extraerDocumentoAceptacionByNumOrdenCompraAndNumDocAceptacion(numeroOrdenCompra, numeroDocumentoAceptacion, aprobarOrdenCompra, enviarCorreoAprobacion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "entregaMercaderiaPdf/{idDocumentoAceptacion}")
    public String getEntregaMercaderiaPdf(@PathVariable(value = "idDocumentoAceptacion") Integer idEntregaMercaderia) {
        try {
            DocumentoAceptacion documentoAceptacion = documentoAceptacionService.getDocumentoAceptacionbyId(1, idEntregaMercaderia);
            OrdenCompra ordenCompra;
            if (documentoAceptacion.getIdOrdenCompra() != null) {
                ordenCompra = ordenCompraService.getOrdenCompraById(documentoAceptacion.getIdOrdenCompra());
            } else {
                throw new NullPointerException("El id del documento de aceptación no es válido.");
            }

            ParameterEntradaMercaderiaPdfDTO parameterEntradaMercaderiaPdfDTO = new ParameterEntradaMercaderiaPdfDTO();
            Proveedor proveedor = Optional
                    .ofNullable(proveedorService.getProveedorByRuc(documentoAceptacion.getProveedorRuc()))
                    .orElse(new Proveedor());

            parameterEntradaMercaderiaPdfDTO.setNroRuc(documentoAceptacion.getProveedorRuc());
            parameterEntradaMercaderiaPdfDTO.setNroGuia(documentoAceptacion.getNumeroGuiaProveedor());
            parameterEntradaMercaderiaPdfDTO.setRucCliente(ordenCompra.getInfoSociedad().getRuc());
            parameterEntradaMercaderiaPdfDTO.setFechaEmision(DateUtils.utilDateToString(documentoAceptacion.getFechaEmision()));
            parameterEntradaMercaderiaPdfDTO.setRazonSocialCliente(ordenCompra.getInfoSociedad().getRazonSocial());
            parameterEntradaMercaderiaPdfDTO.setDocumentoMaterial(documentoAceptacion.getNumeroDocumentoAceptacion());
            parameterEntradaMercaderiaPdfDTO.setDescripcionProveedor(documentoAceptacion.getProveedorRazonSocial());
            parameterEntradaMercaderiaPdfDTO.setUbicacionProveedor(proveedor.getDireccionFiscal() != null ? proveedor.getDireccionFiscal() : "");
            parameterEntradaMercaderiaPdfDTO.setTelefonoProveedor(proveedor.getTelefono() != null ? proveedor.getTelefono() : "");

            List<FieldEntradaMercaderiaPdfDTO> fieldEntradaMercaderiaPdfList = new ArrayList<>();

            if (documentoAceptacion.getDocumentoAceptacionDetalleList() != null && documentoAceptacion.getDocumentoAceptacionDetalleList().size() > 0) {
                documentoAceptacion.getDocumentoAceptacionDetalleList().forEach(documentoAceptacionDetalle -> {
                    FieldEntradaMercaderiaPdfDTO fieldEntradaMercaderiaPdf = new FieldEntradaMercaderiaPdfDTO();

                    fieldEntradaMercaderiaPdf.setNroItem(documentoAceptacionDetalle.getNumeroItem().toString());
                    fieldEntradaMercaderiaPdf.setNroOC(documentoAceptacionDetalle.getNumeroOrdenCompra());
                    fieldEntradaMercaderiaPdf.setNroItemOC(documentoAceptacionDetalle.getPosicionOrdenCompra());
                    fieldEntradaMercaderiaPdf.setCodigoProducto(documentoAceptacionDetalle.getCodigoSapBienServicio().replaceFirst("^0+(?!$)", ""));
                    fieldEntradaMercaderiaPdf.setDescripcionProducto(documentoAceptacionDetalle.getDescripcionBienServicio());
                    fieldEntradaMercaderiaPdf.setCantAceptableCliente(documentoAceptacionDetalle.getCantidadAceptadaCliente().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    fieldEntradaMercaderiaPdf.setUndMedida(documentoAceptacionDetalle.getUnidadMedida());
                    fieldEntradaMercaderiaPdf.setCantPedientePedido(documentoAceptacionDetalle.getCantidadPendiente().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    fieldEntradaMercaderiaPdf.setUndMedidaPedido(documentoAceptacionDetalle.getUnidadMedida());

                    fieldEntradaMercaderiaPdfList.add(fieldEntradaMercaderiaPdf);
                });
            }
            parameterEntradaMercaderiaPdfDTO.setFieldEntradaMercaderiaPdfDTOList(fieldEntradaMercaderiaPdfList);
            return documentoAceptacionService.getEntregaMercaderiaGenerateContent(parameterEntradaMercaderiaPdfDTO);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "conformidadServicioPdf/{idDocumentoAceptacion}")
    public String getConformidadServicioPdf(@PathVariable(value = "idDocumentoAceptacion") Integer idConformidadServicio) {
        try {
            DocumentoAceptacion documentoAceptacion = documentoAceptacionService.getDocumentoAceptacionbyId(2, idConformidadServicio);
            OrdenCompra ordenCompra;
            if (documentoAceptacion.getIdOrdenCompra() != null) {
                ordenCompra = ordenCompraService.getOrdenCompraById(documentoAceptacion.getIdOrdenCompra());
            } else {
                throw new NullPointerException("El id del documento de aceptación no es válido.");
            }

            ParameterConformidadServicioPdfDTO parameterConformidadServicioPdfDTO = new ParameterConformidadServicioPdfDTO();

            parameterConformidadServicioPdfDTO.setRucCliente(ordenCompra.getInfoSociedad().getRuc());
            parameterConformidadServicioPdfDTO.setUbicacionCliente(ordenCompra.getInfoSociedad().getDireccionFiscal());
            parameterConformidadServicioPdfDTO.setDescripcionCliente(ordenCompra.getInfoSociedad().getRazonSocial());
            parameterConformidadServicioPdfDTO.setTelefonoCliente(ordenCompra.getInfoSociedad().getTelefono());
            parameterConformidadServicioPdfDTO.setNroConformidadServicio(documentoAceptacion.getNumeroDocumentoAceptacion());
            parameterConformidadServicioPdfDTO.setRucProveedor(documentoAceptacion.getProveedorRuc());
            parameterConformidadServicioPdfDTO.setFechaEmision(DateUtils.utilDateToString(documentoAceptacion.getFechaEmision()));
            parameterConformidadServicioPdfDTO.setRazonSocialProveedor(documentoAceptacion.getProveedorRazonSocial());
            parameterConformidadServicioPdfDTO.setTipoMoneda(documentoAceptacion.getCodigoMoneda());
            parameterConformidadServicioPdfDTO.setRecepcionPersona(documentoAceptacion.getUsuarioSapRecepcion());
            parameterConformidadServicioPdfDTO.setAutorPersona(documentoAceptacion.getUsuarioSapAutoriza());
            parameterConformidadServicioPdfDTO.setFechaAcept(DateUtils.utilDateToString(documentoAceptacion.getFechaAceptacion()));

            List<FieldConformidadServicioPdfDTO> fieldConformidadServicioPdfList = new ArrayList<>();

            if (documentoAceptacion.getDocumentoAceptacionDetalleList() != null && documentoAceptacion.getDocumentoAceptacionDetalleList().size() > 0) {
                Integer[] nroItem = {0};
                documentoAceptacion.getDocumentoAceptacionDetalleList().forEach(documentoAceptacionDetalle -> {
                    nroItem[0]++;
                    FieldConformidadServicioPdfDTO fieldConformidadServicioPdf = new FieldConformidadServicioPdfDTO();

                    fieldConformidadServicioPdf.setNroItem(nroItem[0].toString());
                    fieldConformidadServicioPdf.setNroOrdenServicio(documentoAceptacionDetalle.getNumeroOrdenCompra());
                    fieldConformidadServicioPdf.setNroItemOrdenServicio(documentoAceptacion.getPosicionOrdenCompra());
                    fieldConformidadServicioPdf.setDescripcionServicio(documentoAceptacionDetalle.getDescripcionBienServicio());
                    fieldConformidadServicioPdf.setCantidad(documentoAceptacionDetalle.getCantidadAceptadaCliente().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    fieldConformidadServicioPdf.setUnidad(documentoAceptacionDetalle.getUnidadMedida());
                    fieldConformidadServicioPdf.setValorRecibido(documentoAceptacionDetalle.getValorRecibido().setScale(2, BigDecimal.ROUND_HALF_UP).toString());

                    fieldConformidadServicioPdfList.add(fieldConformidadServicioPdf);
                });
            }
            parameterConformidadServicioPdfDTO.setFieldConformidadServicioPdfDTOList(fieldConformidadServicioPdfList);
            return documentoAceptacionService.getConformidadServicioGenerateContent(parameterConformidadServicioPdfDTO);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "/getDocumentoAceptacionListPag")
    public ResponseEntity<DocumentoAceptacionOutDTO> getDocumentoAceptacionList(@RequestBody FiltroDocumentoDto filtroDocumentoDto,
                                                                                HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.error("<--MC_LOGGER--> :filtroDocumentoDto1:"+filtroDocumentoDto);
            DocumentoAceptacionOutDTO out = new DocumentoAceptacionOutDTO();
            logger.error("<--MC_LOGGER--> :filtroDocumentoDto2:");
            List<DocumentoAceptacionDto> listaAux = documentoAceptacionNeoService.getDocuAcepPorFecsRucPg(
                    filtroDocumentoDto.getFechaInicio()
                    , filtroDocumentoDto.getFechaFin()
                    , filtroDocumentoDto.getRuc()
                    , filtroDocumentoDto.getNumeroOc()
                    , filtroDocumentoDto.getNumeroGuia()
                    , filtroDocumentoDto.getNroRegistros()
                    , filtroDocumentoDto.getPaginaMostrar()
                    , filtroDocumentoDto.getTipoDocumento()
                    , null);

            logger.error("<--MC_LOGGER--> :filtroDocumentoDto3:"+listaAux.size());
            logger.error("<--MC_LOGGER--> :filtroDocumentoDto4:"+listaAux);
            Integer totalElementos = 0;
            Integer totalPaginas = 0;

            logger.error("<--MC_LOGGER--> :filtroDocumentoDto5:");
            if (listaAux != null && listaAux.size() > 0) {
                logger.error("<--MC_LOGGER--> :filtroDocumentoDto5:vacio");
                totalElementos = listaAux.size();
            }
            logger.error("<--MC_LOGGER--> :filtroDocumentoDto6:");
            if (totalElementos > 0) {
                logger.error("docacp_2 ");
                if (totalElementos > filtroDocumentoDto.getNroRegistros()) {
                    logger.error("docacp_3 ");
                    totalPaginas = totalElementos / filtroDocumentoDto.getNroRegistros();
                    if(totalElementos%filtroDocumentoDto.getNroRegistros()>0)
                        totalPaginas++;
                } else {
                    logger.error("docacp_4 ");
                    totalPaginas = 1;
                }


            }
            logger.error("<--MC_LOGGER--> :filtroDocumentoDto7:");
            Integer numeroPaginaMostrar = (filtroDocumentoDto.getPaginaMostrar() - 1) * filtroDocumentoDto.getNroRegistros();
            logger.error("docap__5 " + numeroPaginaMostrar);



            List<DocumentoAceptacionDto> lista = documentoAceptacionNeoService.getDocuAcepPorFecsRucPg(
                    filtroDocumentoDto.getFechaInicio()
                    , filtroDocumentoDto.getFechaFin()
                    , filtroDocumentoDto.getRuc()
                    , filtroDocumentoDto.getNumeroOc()
                    , filtroDocumentoDto.getNumeroGuia()
                    , filtroDocumentoDto.getNroRegistros()
                    , numeroPaginaMostrar
                    , filtroDocumentoDto.getTipoDocumento()
                    , "X");





            logger.error("listaFacturaPendiente__6 " + listaAux.size());
            logger.error("listaFacturaPendiente__7 " + lista.size());
            logger.error("listaFacturaPendiente__8 " + totalElementos);
            logger.error("listaFacturaPendiente__9 " + totalPaginas);

            for(DocumentoAceptacionDto itlst:lista){
                logger.error("listaFacturaPendiente__10 ");
                List<DocumentoAceptacionDetalle> itdet = documentoAceptacionDetalleRepository.getDocumentoAceptacionDetalleListByIdDocumentoAceptacion(itlst.getId());
//                logger.error("<--LOG_MC-->:A:"+itdet);
//                List<DocumentoAceptacionDetalle> documentoAceptacionDetalleList = documentoAceptacionDetalleService.getDocumentoAceptacionDetalleNoAnuladasListById(itlst.getId());
//                logger.error("<--LOG_MC-->:B:"+documentoAceptacionDetalleList);
                logger.error("listaFacturaPendiente__11 "+itdet);
                double sumatoria = itdet.stream()
                        .mapToDouble(e -> e.getValorRecibido().doubleValue())
                        .sum();
                itlst.setSumatoria(BigDecimal.valueOf(sumatoria));
                itlst.setDocumentoAceptacionDetalleList(itdet);
            }
            logger.error("listaFacturaPendiente__12 ");
            logger.error("listaFacturaPendiente__13 ");

            out.setTotalElementos(totalElementos);
            out.setTotalPaginas(totalPaginas);
            out.setLista(lista);






//            if (out.getLista().isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
            logger.error("<--MC_LOGGER--> :documentoAceptacionList:"+out);
            return new ResponseEntity<>(out, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
    @PostMapping(value = "/updateDocumentoAceptacionStatus/{numDocumentoAceptacion}/{numOrdenCompra}/{descripcionEstado}")
    public String updateDocumentoAceptacionStatus(@PathVariable String numDocumentoAceptacion,
                                                             @PathVariable String numOrdenCompra,
                                                  @PathVariable String descripcionEstado){
        return this.documentoAceptacionService.updateDocumentoAceptacionStatus(numDocumentoAceptacion, numOrdenCompra, descripcionEstado);
    }
}

