package com.incloud.hcp.jco.documentoAceptacion.service.impl;

import com.google.gson.Gson;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.enums.*;
import com.incloud.hcp.jco.documentoAceptacion.dto.SapTableItemDto;
import com.incloud.hcp.jco.documentoAceptacion.service.JCODocumentoAceptacionService;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenCompraPublicarOneService;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.notificacion.ContactoAprobadaRechazadaOCNotificacion;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.ws.ias.bean.IASResponse;
import com.incloud.hcp.ws.ias.bean.IASUserInfoResponse;
import com.incloud.hcp.ws.ias.service.IUserIASService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JCODocumentoAceptacionServiceImpl implements JCODocumentoAceptacionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AtomicBoolean daProcessing = new AtomicBoolean(false);

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    private DocumentoAceptacionRepository documentoAceptacionRepository;
    private DocumentoAceptacionDetalleRepository documentoAceptacionDetalleRepository;
    private OrdenCompraRepository ordenCompraRepository;
    private JCOOrdenCompraPublicarOneService jcoOrdenCompraPublicarOneService;
    private ContactoAprobadaRechazadaOCNotificacion contactoAprobadaRechazadaOCNotificacion;
    private UsuarioRepository usuarioRepository;
    private ProveedorService proveedorService;
    private IUserIASService userIASService;
    private LogTransaccionRepository logTransaccionRepository;

    @Autowired
    public JCODocumentoAceptacionServiceImpl(DocumentoAceptacionRepository documentoAceptacionRepository,
                                             DocumentoAceptacionDetalleRepository documentoAceptacionDetalleRepository,
                                             OrdenCompraRepository ordenCompraRepository,
                                             JCOOrdenCompraPublicarOneService jcoOrdenCompraPublicarOneService,
                                             ContactoAprobadaRechazadaOCNotificacion contactoAprobadaRechazadaOCNotificacion,
                                             UsuarioRepository usuarioRepository,
                                             ProveedorService proveedorService,
                                             IUserIASService userIASService,
                                             LogTransaccionRepository logTransaccionRepository) {
        this.documentoAceptacionRepository = documentoAceptacionRepository;
        this.documentoAceptacionDetalleRepository = documentoAceptacionDetalleRepository;
        this.ordenCompraRepository = ordenCompraRepository;
        this.jcoOrdenCompraPublicarOneService = jcoOrdenCompraPublicarOneService;
        this.contactoAprobadaRechazadaOCNotificacion = contactoAprobadaRechazadaOCNotificacion;
        this.usuarioRepository = usuarioRepository;
        this.proveedorService = proveedorService;
        this.userIASService = userIASService;
        this.logTransaccionRepository = logTransaccionRepository;
    }

    @Override
    public void extraerDocumentoAceptacionListRFC(String parametro1, String parametro2, boolean extraccionUnicoDocumento, boolean aprobarOrdenCompra, boolean enviarCorreoAprobacion) throws Exception {
        if(!daProcessing.get() || extraccionUnicoDocumento) {

            if(!daProcessing.get() && !extraccionUnicoDocumento)
                daProcessing.set(!daProcessing.get());

            try{
                String FUNCION_RFC = "ZPE_MM_ENTRADA_MERCADERIAS";

                JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
                JCoRepository repository = destination.getRepository();

                JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
                this.mapFilters(jCoFunction, parametro1, parametro2, extraccionUnicoDocumento);
                jCoFunction.execute(destination);

                JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
                DocumentoAceptacionExtractorMapper documentoAceptacionExtractorMapper = DocumentoAceptacionExtractorMapper.newMapper(exportParameterList);

                List<SapTableItemDto> sapTableItemDtoList = documentoAceptacionExtractorMapper.getSapTableItemDtoList();
                List<String> movimientosIncluidos = Arrays.asList(MovimientoEntregaMercaderiaTipoEnum.LIBERACION_EM.getCodigo(),MovimientoEntregaMercaderiaTipoEnum.LIBERACION_2_PASO.getCodigo(),MovimientoEntregaMercaderiaTipoEnum.ANULACION_EM.getCodigo(),MovimientoEntregaMercaderiaTipoEnum.ANULACION_2_PASO.getCodigo());
                List<String> movimientosDeAnulacion = Arrays.asList(MovimientoEntregaMercaderiaTipoEnum.ANULACION_EM.getCodigo(),MovimientoEntregaMercaderiaTipoEnum.ANULACION_2_PASO.getCodigo());

                if (extraccionUnicoDocumento){
                    sapTableItemDtoList = sapTableItemDtoList.stream()
                            .filter(item -> item.getNumeroDocumentoAceptacion().equals(parametro2))
                            .collect(Collectors.toList());
                }

                Map<String,List<SapTableItemDto>> entradaMercaderiaItemMap = sapTableItemDtoList.stream()
                        .filter(item -> movimientosIncluidos.contains(item.getMovimiento())) // solo items de EM con movimiento 101,102,105,106
                        .collect(Collectors.groupingBy(SapTableItemDto::getNumeroDocumentoAceptacion,Collectors.toList()));

                Map<String,List<SapTableItemDto>> hojaServicioItemMap = sapTableItemDtoList.stream()
                        .filter(item -> item.getMovimiento().equals("")) // solo items de HES (sin movimiento)
                        .collect(Collectors.groupingBy(SapTableItemDto::getNumeroDocumentoAceptacion,Collectors.toList()));

                Long cantidadEntregaMercaderiaExcluida = sapTableItemDtoList.stream()
                        .filter(item -> !movimientosIncluidos.contains(item.getMovimiento()) && !item.getMovimiento().equals("")) // solo items de EM con movimientos que no son 101,102,105,106
                        .count();

                List<String> numEntradaMercaderiaPosibleAnuladaList = new ArrayList<>();

                String header1 = "INI: " + DateUtils.getCurrentTimestamp().toString() + " -- EXTR DA -- RANGO: " + parametro1 + " - " + parametro2 + " // ";
//                logger.error(header1 + "Rango de Fechas : " + fechaInicio + " - " + fechaFin);
                logger.error(header1 + "CANTIDAD DE EM ENCONTRADAS: " + entradaMercaderiaItemMap.size());
                logger.error(header1 + "CANTIDAD DE EM EXCLUIDAS: " + cantidadEntregaMercaderiaExcluida);
                logger.error(header1 + "CANTIDAD DE HES ENCONTRADAS: " + hojaServicioItemMap.size());

                if (extraccionUnicoDocumento){
                    for(int i=0 ; i < sapTableItemDtoList.size() ; i++){
                        logger.error(header1 + "SAP ITEM " + i + ": " + sapTableItemDtoList.get(i).toString());
                    }
                }

                entradaMercaderiaItemMap.forEach((numeroEntradaMercaderia, itemList) -> {
                    Integer idEntradaMercaderiaExistente = documentoAceptacionRepository.getIdDocumentoAceptacionByNumero(numeroEntradaMercaderia);
                    if (idEntradaMercaderiaExistente == null) { // si no existe previamente la entrada de mercaderia
                        String header2 = header1.concat("EM: " + numeroEntradaMercaderia);
                        SapTableItemDto primerItem = itemList.get(0); // trae primer item para obtener datos comunes de la EM
                        boolean esEmDeAnulacion = movimientosDeAnulacion.contains(primerItem.getMovimiento());
                        boolean procedePublicar = true;
                        String numeroOrdenCompra = primerItem.getNumeroOrdenCompra();
                        DocumentoAceptacion entradaMercaderia = new DocumentoAceptacion();

                        if(esEmDeAnulacion){ // si es una EM de Anulacion, busca si ya este publicada la EM cuyas posiciones apunta a anular y que se encuentre en estado ACTIVO
                            Optional<DocumentoAceptacion> opDocAceptacionRelacionado = documentoAceptacionRepository.findByNumeroDocumentoAceptacion(primerItem.getNumDocApectacionRelacionado());
                            if(!opDocAceptacionRelacionado.isPresent() || opDocAceptacionRelacionado.get().getIdEstadoDocumentoAceptacion().compareTo(DocumentoAceptacionEstadoEnum.ACTIVO.getId()) != 0)
                                procedePublicar = false;
                        }

                        if(procedePublicar) {
                            entradaMercaderia.setNumeroDocumentoAceptacion(numeroEntradaMercaderia);
                            entradaMercaderia.setIdTipoDocumentoAceptacion(DocumentoAceptacionTipoEnum.ENTRADA_MERCADERIA.getId());
                            entradaMercaderia.setIdEstadoDocumentoAceptacion(this.asignarIdEstadoDocumentoAceptacion(primerItem.getMovimiento()));
                            entradaMercaderia.setNumeroOrdenCompra(numeroOrdenCompra);
                            entradaMercaderia.setNumeroGuiaProveedor(primerItem.getNumeroGuiaProveedor());
                            entradaMercaderia.setUsuarioSapRecepcion(primerItem.getUsuarioSapRecepcion());
                            entradaMercaderia.setCodigoMoneda(primerItem.getCodigoMoneda());
                            entradaMercaderia.setFechaEmision(primerItem.getFechaEmision());
                            entradaMercaderia.setFechaContabilizacion(primerItem.getFechaContabilizacion());

                            List<OrdenCompra> optionalOrdenCompra = ordenCompraRepository.getListOrdenCompraLiberadaActivaValidaByNumero(numeroOrdenCompra);
                            OrdenCompra ordenCompra = new OrdenCompra();
                            Boolean continuarOC = true;
                            if (optionalOrdenCompra.size() > 1) {
                                continuarOC = false;
                                logger.error("cotinuarOc false");
                                //String sLogValidarOC = ;
                                LogTransaccion logValidarOC = new LogTransaccion();
                                logValidarOC.setLogFecha(DateUtils.getCurrentTimestamp());
                                logValidarOC.setTipoRegistro("runDocumentoAceptacionExtractor - ocDuplicada procedePublicar");
                                logValidarOC.setEnvioTrama("Existe OC duplicada " + numeroOrdenCompra);
                                this.logTransaccionRepository.save(logValidarOC);
                            }

                            if (continuarOC) {
                                if (optionalOrdenCompra.size() == 1) {
                                    ordenCompra = optionalOrdenCompra.get(0);
                                    logger.error(header2 + " // OC ENCONTRADA: " + ordenCompra.toString());

                                    // AQUI MECANICA QUE MODIFICA OC ENCONTRADA A ESTADO "APROBADA" (por proveedor) (SOLO SI EM tiene Mov 101,105)
                                    entradaMercaderia = this.actualizarDocumentoAceptacionAndOrdenCompra("EM", entradaMercaderia, ordenCompra, aprobarOrdenCompra, enviarCorreoAprobacion);
                                } else {
                                    if (!esEmDeAnulacion) { // trata de publicar su OC solo si no es una EM de anulacion
                                        try {
                                            jcoOrdenCompraPublicarOneService.extraerOneOrdenCompraRFC(numeroOrdenCompra, false);
                                            List<OrdenCompra> optionalOrdenCompraExtraida = ordenCompraRepository.getListOrdenCompraLiberadaActivaValidaByNumero(numeroOrdenCompra);
                                            Boolean continuarOCd = true;
                                            if (optionalOrdenCompra.size() > 1) {
                                                continuarOCd = false;
                                                logger.error("continuarOCd false");
                                                //String sLogValidarOC = ;
                                                LogTransaccion logValidarOC = new LogTransaccion();
                                                logValidarOC.setLogFecha(DateUtils.getCurrentTimestamp());
                                                logValidarOC.setTipoRegistro("runDocumentoAceptacionExtractor - ocDuplicada esEmDeAnulacion");
                                                logValidarOC.setEnvioTrama("Existe OC duplicada " + numeroOrdenCompra);
                                                this.logTransaccionRepository.save(logValidarOC);
                                            }

                                            if (continuarOCd) {
                                                if (optionalOrdenCompraExtraida.size() == 1) {
                                                    ordenCompra = optionalOrdenCompraExtraida.get(0);
                                                    logger.error(header2 + " // OC PUBLICADA: " + ordenCompra.toString());

                                                    // AQUI MECANICA QUE MODIFICA OC PUBLICADA A ESTADO "APROBADA" (por proveedor) (SOLO SI EM tiene Mov 101,105)
                                                    entradaMercaderia = this.actualizarDocumentoAceptacionAndOrdenCompra("EM", entradaMercaderia, ordenCompra, aprobarOrdenCompra, enviarCorreoAprobacion);
                                                } else {
                                                    logger.error(header2 + " // NO SE PUBLICO OC: " + numeroOrdenCompra);
                                                }
                                            }
                                        } catch (Exception e) {
                                            logger.error(header2 + " // ERROR AL EXTRAER OC: " + numeroOrdenCompra);
                                        }
                                    }
                                }
                            }

                            if (entradaMercaderia.getIdOrdenCompra() != null) { // si es que la OC no ha sido publicada, no se publica la EM
                                entradaMercaderia.setFechaPublicacion(DateUtils.getCurrentTimestamp());
                                logger.error(header2 + " // WRITING NEW EM: " + entradaMercaderia.toString());
                                entradaMercaderia = documentoAceptacionRepository.save(entradaMercaderia);
                                Integer idEntradaMercaderia = entradaMercaderia.getId();

                                itemList.forEach(item -> {
                                    DocumentoAceptacionDetalle entradaMercaderiaDetalle = new DocumentoAceptacionDetalle();
                                    String movimiento = item.getMovimiento();

                                    entradaMercaderiaDetalle.setIdDocumentoAceptacion(idEntradaMercaderia);
                                    entradaMercaderiaDetalle.setIdEstadoDocumentoAceptacionDetalle(this.asignarIdEstadoDocumentoAceptacion(movimiento));
                                    entradaMercaderiaDetalle.setNumeroDocumentoAceptacion(numeroEntradaMercaderia);
                                    entradaMercaderiaDetalle.setNumeroItem(item.getNumeroItem());
                                    entradaMercaderiaDetalle.setNumeroOrdenCompra(item.getNumeroOrdenCompra());
                                    entradaMercaderiaDetalle.setPosicionOrdenCompra(item.getPosicionOrdenCompra());
                                    entradaMercaderiaDetalle.setCodigoSapBienServicio(item.getCodigoMaterial());
                                    entradaMercaderiaDetalle.setDescripcionBienServicio(!item.getDescripcionMaterial().isEmpty() ? item.getDescripcionMaterial() : item.getDescripcionServicio());
                                    entradaMercaderiaDetalle.setUnidadMedida(item.getUnidadMedidaMaterial());
                                    entradaMercaderiaDetalle.setCantidadAceptadaCliente(item.getCantidadAceptadaClienteMaterial());
                                    entradaMercaderiaDetalle.setCantidadPendiente(item.getCantidadPendiente());
                                    entradaMercaderiaDetalle.setValorRecibidoMonedalocal(item.getValorRecibidoMonedalocal());
                                    entradaMercaderiaDetalle.setPrecioUnitario(item.getPrecioUnitario());
                                    entradaMercaderiaDetalle.setValorRecibido(item.getValorRecibido());
                                    entradaMercaderiaDetalle.setMovimiento(movimiento);
                                    entradaMercaderiaDetalle.setIndicadorImpuesto(item.getIndicadorImpuesto());

                                    if (movimientosDeAnulacion.contains(primerItem.getMovimiento())) {
                                        String numEmPorAnular = item.getNumDocApectacionRelacionado();
                                        Integer numItemPorAnular = item.getNumItemRelacionado();
                                        String posicionOrdenCompra = item.getPosicionOrdenCompra();
                                        BigDecimal cantidadAceptadaCliente = item.getCantidadAceptadaClienteMaterial();

                                        entradaMercaderiaDetalle.setNumDocApectacionRelacionado(numEmPorAnular);
                                        entradaMercaderiaDetalle.setNumItemRelacionado(numItemPorAnular);

                                        logger.error(header2 + " // WRITING NEW EM POS (ANUL): " + entradaMercaderiaDetalle.toString());
                                        documentoAceptacionDetalleRepository.save(entradaMercaderiaDetalle);
//                                    Optional<DocumentoAceptacionDetalle> optionalEntradaMercaderiaDetalleExistente = documentoAceptacionDetalleRepository.findByNumeroDocumentoAceptacionAndNumeroItem(numEmPorAnular, numItemPorAnular);
                                        Optional<DocumentoAceptacionDetalle> optionalEntradaMercaderiaDetalleExistente = documentoAceptacionDetalleRepository.findByNumeroDocumentoAceptacionAndPosicionOrdenCompraAndCantidadAceptadaCliente(numEmPorAnular, posicionOrdenCompra, cantidadAceptadaCliente);
                                        DocumentoAceptacionDetalle entradaMercaderiaDetalleExistente = new DocumentoAceptacionDetalle();

                                        if (optionalEntradaMercaderiaDetalleExistente.isPresent()) {
                                            entradaMercaderiaDetalleExistente = optionalEntradaMercaderiaDetalleExistente.get();
                                            logger.error(header2 + " // FOUND EM POS (POR ANULAR): " + entradaMercaderiaDetalleExistente.toString());
                                            Integer idEstadoItem = entradaMercaderiaDetalleExistente.getIdEstadoDocumentoAceptacionDetalle();

                                            if (idEstadoItem.compareTo(DocumentoAceptacionEstadoEnum.ACTIVO.getId()) == 0
                                                    || idEstadoItem.compareTo(DocumentoAceptacionEstadoEnum.TRANSITO.getId()) == 0) {
                                                entradaMercaderiaDetalleExistente.setIdEstadoDocumentoAceptacionDetalle(DocumentoAceptacionEstadoEnum.ANULADO.getId());
                                                logger.error(header2 + " // ANULANDO EM POS: " + entradaMercaderiaDetalleExistente.toString());
                                                documentoAceptacionDetalleRepository.save(entradaMercaderiaDetalleExistente);
                                                String numEntradaMercaderiaExistente = entradaMercaderiaDetalleExistente.getNumeroDocumentoAceptacion();

                                                if (!numEntradaMercaderiaPosibleAnuladaList.contains(numEntradaMercaderiaExistente))
                                                    numEntradaMercaderiaPosibleAnuladaList.add(numEntradaMercaderiaExistente);
                                            }
                                        } else {
                                            logger.error(header2 + " // FOUND EM POS (POR ANULAR): " + optionalEntradaMercaderiaDetalleExistente.toString());
                                        }
                                    } else {
                                        logger.error(header2 + " // WRITING NEW EM POS: " + entradaMercaderiaDetalle.toString());
                                        documentoAceptacionDetalleRepository.save(entradaMercaderiaDetalle);
                                    }
                                });
                            }
                        }
                    }
                });

                numEntradaMercaderiaPosibleAnuladaList.forEach(num -> {
                    Optional<DocumentoAceptacion> optionalEntradaMercaderiaPosibleAnulada = documentoAceptacionRepository.findByNumeroDocumentoAceptacion(num);
                    String header2 = header1.concat("EM: " + num);
                    if(optionalEntradaMercaderiaPosibleAnulada.isPresent()) {
                        boolean emAnulada = true;
                        DocumentoAceptacion entradaMercaderiaPosibleAnulada = optionalEntradaMercaderiaPosibleAnulada.get();
                        logger.error(header2 + " // EM POSIBLE A ANULAR: " + entradaMercaderiaPosibleAnulada.toString());
                        List<DocumentoAceptacionDetalle> emPosibleAnuladaPosicionList = Optional.ofNullable(entradaMercaderiaPosibleAnulada.getDocumentoAceptacionDetalleList()).orElse(new ArrayList<>());

                        if(emPosibleAnuladaPosicionList.isEmpty()) {
                            emPosibleAnuladaPosicionList.addAll(documentoAceptacionDetalleRepository.getDocumentoAceptacionDetalleListByIdDocumentoAceptacion(entradaMercaderiaPosibleAnulada.getId()));
                            logger.error(header2 + " // LISTA POSICIONES DE EM POSIBLE A ANULAR: " + emPosibleAnuladaPosicionList.toString());
                        }

                        for (DocumentoAceptacionDetalle posicion : emPosibleAnuladaPosicionList) {
                            if (posicion.getIdEstadoDocumentoAceptacionDetalle().compareTo(DocumentoAceptacionEstadoEnum.ANULADO.getId()) != 0) {
                                emAnulada = false;
                                break;
                            }
                        }

                        if (emAnulada && !emPosibleAnuladaPosicionList.isEmpty()) {
                            entradaMercaderiaPosibleAnulada.setIdEstadoDocumentoAceptacion(DocumentoAceptacionEstadoEnum.ANULADO.getId());
                            logger.error(header2 + " // ANULANDO EM: " + entradaMercaderiaPosibleAnulada.toString());
                            documentoAceptacionRepository.save(entradaMercaderiaPosibleAnulada);
                        }
                        else{
                            logger.error(header2 + " // NO SE ANULO EM: " + entradaMercaderiaPosibleAnulada.toString());
                        }
                    }
                });

                hojaServicioItemMap.forEach((numeroHojaServicio, itemList) -> {
                    Integer idHojaServicioExistente = documentoAceptacionRepository.getIdDocumentoAceptacionByNumero(numeroHojaServicio);

                    if(idHojaServicioExistente == null){ // si no existe previamente la hoja de entrada de servicio
                        SapTableItemDto unicoItem = itemList.get(0); // HES solo tiene un item (posicion)
                        String statusSap = unicoItem.getStatus();
                        if (statusSap != null && !statusSap.isEmpty()
                                && (statusSap.equalsIgnoreCase(DocumentoAceptacionStatusSapEnum.ACEPTADO.getDescripcion())
                                || statusSap.equalsIgnoreCase(DocumentoAceptacionStatusSapEnum.BORRADO.getDescripcion()))) { // solo publica si statusSap existe
                            String header2 = header1.concat("HES: " + numeroHojaServicio);
                            String numeroOrdenCompra = unicoItem.getNumeroOrdenCompra();
                            String posicionOrdencompra = unicoItem.getPosicionOrdenCompra();
                            DocumentoAceptacion hojaServicio = new DocumentoAceptacion();

                            hojaServicio.setNumeroDocumentoAceptacion(numeroHojaServicio);
                            hojaServicio.setIdTipoDocumentoAceptacion(DocumentoAceptacionTipoEnum.HOJA_ENTRADA_SERVICIO.getId());
                            hojaServicio.setNumeroOrdenCompra(numeroOrdenCompra);
                            hojaServicio.setPosicionOrdenCompra(posicionOrdencompra);
                            hojaServicio.setUsuarioSapRecepcion(unicoItem.getUsuarioSapRecepcion());
                            hojaServicio.setCodigoMoneda(unicoItem.getCodigoMoneda());
                            hojaServicio.setFechaEmision(unicoItem.getFechaEmision());
                            hojaServicio.setFechaAceptacion(unicoItem.getFechaAceptacion());
                            hojaServicio.setFechaContabilizacion(unicoItem.getFechaContabilizacion());

                            hojaServicio.setStatusSap(statusSap);
                            if (statusSap.equalsIgnoreCase(DocumentoAceptacionStatusSapEnum.ACEPTADO.getDescripcion()))
                                hojaServicio.setIdEstadoDocumentoAceptacion(DocumentoAceptacionEstadoEnum.ACTIVO.getId());
                            else // statusSap == "Borrado"
                                hojaServicio.setIdEstadoDocumentoAceptacion(DocumentoAceptacionEstadoEnum.ANULADO.getId());

                            OrdenCompra ordenCompra = new OrdenCompra();
                            List<OrdenCompra> optionalOrdenCompra = ordenCompraRepository.getListOrdenCompraLiberadaActivaValidaByNumero(numeroOrdenCompra);
                            Boolean continuarOCa = true;
                            if (optionalOrdenCompra.size() > 1) {
                                continuarOCa = false;
                                logger.error("cotinuarOc false");
                                //String sLogValidarOC = ;
                                LogTransaccion logValidarOC = new LogTransaccion();
                                logValidarOC.setLogFecha(DateUtils.getCurrentTimestamp());
                                logValidarOC.setTipoRegistro("runDocumentoAceptacionExtractor - ocDuplicada hojaServicioa");
                                logValidarOC.setEnvioTrama("Existe OC duplicada " + numeroOrdenCompra);
                                this.logTransaccionRepository.save(logValidarOC);
                            }

                            if(continuarOCa) {
                                if (optionalOrdenCompra.size() == 1) {
                                    ordenCompra = optionalOrdenCompra.get(0);
                                    logger.error(header2 + " // OC ENCONTRADA: " + ordenCompra.toString());

                                    // AQUI MECANICA QUE MODIFICA OC ENCONTRADA A ESTADO "APROBADA" (por proveedor) (SOLO SI HES SUBE ACEPTADA)
                                    hojaServicio = this.actualizarDocumentoAceptacionAndOrdenCompra("HES", hojaServicio, ordenCompra, aprobarOrdenCompra, enviarCorreoAprobacion);
                                } else {
                                    try {
                                        jcoOrdenCompraPublicarOneService.extraerOneOrdenCompraRFC(numeroOrdenCompra, false);
                                        List<OrdenCompra> optionalOrdenCompraExtraida = ordenCompraRepository.getListOrdenCompraLiberadaActivaValidaByNumero(numeroOrdenCompra);
                                        Boolean continuarOCb = true;
                                        if (optionalOrdenCompra.size() > 1) {
                                            continuarOCb = false;
                                            logger.error("cotinuarOc false");
                                            //String sLogValidarOC = ;
                                            LogTransaccion logValidarOC = new LogTransaccion();
                                            logValidarOC.setLogFecha(DateUtils.getCurrentTimestamp());
                                            logValidarOC.setTipoRegistro("runDocumentoAceptacionExtractor - ocDuplicada hojaServiciob");
                                            logValidarOC.setEnvioTrama("Existe OC duplicada " + numeroOrdenCompra);
                                            this.logTransaccionRepository.save(logValidarOC);
                                        }

                                        if(continuarOCb) {
                                            if (optionalOrdenCompraExtraida.size() == 1) {
                                                ordenCompra = optionalOrdenCompraExtraida.get(0);
                                                logger.error(header2 + " // OC PUBLICADA: " + ordenCompra.toString());

                                                // AQUI MECANICA QUE MODIFICA OC PUBLICADA A ESTADO "APROBADA" (por proveedor) (SOLO SI HES SUBE ACEPTADA)
                                                hojaServicio = this.actualizarDocumentoAceptacionAndOrdenCompra("HES", hojaServicio, ordenCompra, aprobarOrdenCompra, enviarCorreoAprobacion);
                                            } else {
                                                logger.error(header2 + " // NO SE PUBLICO OC: " + numeroOrdenCompra);
                                            }
                                        }
                                    } catch (Exception e) {
                                        logger.error(header2 + " // ERROR AL EXTRAER OC: " + numeroOrdenCompra + " // " + e.getClass().getName() + " -- " + e.getMessage());
                                    }
                                }
                            }

                            if(hojaServicio.getIdOrdenCompra() != null) { // si es que la OC no ha sido publicada, no se publica la nueva HES
                                hojaServicio.setFechaPublicacion(DateUtils.getCurrentTimestamp());
                                logger.error(header2 + " // WRITING NEW HES: " + hojaServicio.toString());
                                hojaServicio = documentoAceptacionRepository.save(hojaServicio);
                                DocumentoAceptacionDetalle hojaServicioDetalle = new DocumentoAceptacionDetalle();

                                hojaServicioDetalle.setIdDocumentoAceptacion(hojaServicio.getId());
                                hojaServicioDetalle.setNumeroDocumentoAceptacion(numeroHojaServicio);
                                hojaServicioDetalle.setNumeroOrdenCompra(numeroOrdenCompra);
                                hojaServicioDetalle.setPosicionOrdenCompra(posicionOrdencompra);
                                hojaServicioDetalle.setDescripcionBienServicio(unicoItem.getDescripcionServicio());
                                hojaServicioDetalle.setUnidadMedida(unicoItem.getUnidadMedidaServicio());
                                hojaServicioDetalle.setCantidadAceptadaCliente(unicoItem.getCantidadAceptadaClienteServicio());
                                hojaServicioDetalle.setPrecioUnitario(unicoItem.getPrecioUnitario());
                                hojaServicioDetalle.setValorRecibido(unicoItem.getValorRecibidoServicio());
                                hojaServicioDetalle.setValorRecibidoMonedalocal(unicoItem.getValorRecibidoMonedalocal());
                                hojaServicioDetalle.setIndicadorImpuesto(unicoItem.getIndicadorImpuesto());

                                Integer idEstadoHojaServicio = hojaServicio.getIdEstadoDocumentoAceptacion();
                                if (idEstadoHojaServicio.compareTo(DocumentoAceptacionEstadoEnum.ACTIVO.getId()) == 0)
                                    hojaServicioDetalle.setIdEstadoDocumentoAceptacionDetalle(DocumentoAceptacionEstadoEnum.ACTIVO.getId());
                                else // idEstadoHojaServicio.compareTo(DocumentoAceptacionEstadoEnum.ANULADO.getId()) == 0
                                    hojaServicioDetalle.setIdEstadoDocumentoAceptacionDetalle(DocumentoAceptacionEstadoEnum.ANULADO.getId());

                                logger.error(header2 + " // WRITING NEW HES POS: " + hojaServicioDetalle.toString());
                                documentoAceptacionDetalleRepository.save(hojaServicioDetalle);
                            }
                        }
                    }
                    else{ // (idHojaServicioExistente != null) si ya existe previamente la hoja de entrada de servicio
                        String header2 = header1.concat("HES EXISTENTE: " + numeroHojaServicio);
                        SapTableItemDto unicoItem = itemList.get(0); // HES solo tiene un item (posicion)
                        DocumentoAceptacion hojaServicioExistente = documentoAceptacionRepository.getDocumentoAceptacionById(DocumentoAceptacionTipoEnum.HOJA_ENTRADA_SERVICIO.getId(),idHojaServicioExistente);

                        String hesExistenteStatusSap = Optional.ofNullable(hojaServicioExistente.getStatusSap()).orElse("");
                        String hesExtraidaStatusSap = Optional.ofNullable(unicoItem.getStatus()).orElse("");
                        Integer IdEstadoHesExistente  = Optional.ofNullable(hojaServicioExistente.getIdEstadoDocumentoAceptacion()).orElse(-1);

                        if(extraccionUnicoDocumento){
                            logger.error(header2 + " // HES STATUS_SAP ACTUAL: " + hesExistenteStatusSap);
                            logger.error(header2 + " // HES STATUS_SAP EXTRAIDO: " + hesExtraidaStatusSap);
                            logger.error(header2 + " // HES ID_ESTADO ACTUAL: " + IdEstadoHesExistente);
                        }

                        if(hesExistenteStatusSap.equalsIgnoreCase(DocumentoAceptacionStatusSapEnum.ACEPTADO.getDescripcion())
                                && hesExtraidaStatusSap.equalsIgnoreCase(DocumentoAceptacionStatusSapEnum.BORRADO.getDescripcion())){
                            hojaServicioExistente.setStatusSap(hesExtraidaStatusSap);

                            if(IdEstadoHesExistente.compareTo(DocumentoAceptacionEstadoEnum.ACTIVO.getId())==0
                                    || IdEstadoHesExistente.compareTo(DocumentoAceptacionEstadoEnum.PREFACTURADO.getId())==0) {
                                hojaServicioExistente.setIdEstadoDocumentoAceptacion(DocumentoAceptacionEstadoEnum.ANULADO.getId());

                                DocumentoAceptacionDetalle hojaServicioDetalleExistente = hojaServicioExistente.getDocumentoAceptacionDetalleList().get(0);
                                hojaServicioDetalleExistente.setIdEstadoDocumentoAceptacionDetalle(DocumentoAceptacionEstadoEnum.ANULADO.getId());
                                documentoAceptacionDetalleRepository.save(hojaServicioDetalleExistente);
                            }

                            logger.error(header2 + " // REGISTRO HES MODIFICADO: " + hojaServicioExistente.toString());
                            documentoAceptacionRepository.save(hojaServicioExistente);
                        }
                    }
                });
                logger.error(header1 + "FINISHED");

                if(daProcessing.get())
                    daProcessing.set(!daProcessing.get());
            }
            catch (Exception e){
                if(daProcessing.get())
                    daProcessing.set(!daProcessing.get());
                logger.error(e.getMessage(), e.getCause());
                throw new Exception(e);
            }
        }
        else{
            logger.error("INI: " + DateUtils.getCurrentTimestamp().toString() + " // DocumentoAceptacion Extraction esta procesando");
        }
    }


    @Override
    public List<SapTableItemDto> extraerDataDocumentoAceptacionRFC(String parametro1, String parametro2, boolean unicoDocumentoAceptacion) throws Exception {
        try{
            String FUNCION_RFC = "ZPE_MM_ENTRADA_MERCADERIAS";

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            this.mapFilters(jCoFunction, parametro1, parametro2, true);
            jCoFunction.execute(destination);

            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            DocumentoAceptacionExtractorMapper documentoAceptacionExtractorMapper = DocumentoAceptacionExtractorMapper.newMapper(exportParameterList);

            List<SapTableItemDto> sapTableItemDtoList = documentoAceptacionExtractorMapper.getSapTableItemDtoList();
            List<String> movimientosIncluidos = Arrays.asList("101","102","105","106");

            if (unicoDocumentoAceptacion){
                sapTableItemDtoList = sapTableItemDtoList.stream()
                        .filter(item -> item.getNumeroDocumentoAceptacion().equals(parametro2))
                        .collect(Collectors.toList());
            }

            Map<String,List<SapTableItemDto>> entradaMercaderiaItemMap = sapTableItemDtoList.stream()
                    .filter(item -> movimientosIncluidos.contains(item.getMovimiento())) // solo items de EM con movimiento 101,102,105,106
                    .collect(Collectors.groupingBy(SapTableItemDto::getNumeroDocumentoAceptacion,Collectors.toList()));

            Map<String,List<SapTableItemDto>> hojaServicioItemMap = sapTableItemDtoList.stream()
                    .filter(item -> item.getMovimiento().equals("")) // solo items de HES (sin movimiento)
                    .collect(Collectors.groupingBy(SapTableItemDto::getNumeroDocumentoAceptacion,Collectors.toList()));

            Long cantidadEntregaMercaderiaExcluida = sapTableItemDtoList.stream()
                    .filter(item -> !movimientosIncluidos.contains(item.getMovimiento()) && !item.getMovimiento().equals("")) // solo items de EM con movimientos que no son 101,102,103,104,105,106
                    .count();

            String header1 = "";
            if (unicoDocumentoAceptacion)
                header1 = "INI: " + DateUtils.getCurrentTimestamp().toString() + " -- EXTR DATA DA -- RANGO: " + parametro1 + " - " + parametro2 + " // ";
            else
                header1 = "INI: " + DateUtils.getCurrentTimestamp().toString() + " -- EXTR DATA DA -- RANGO: " + parametro1 + " - ALL // ";

            logger.error(header1 + "CANTIDAD DE EM ENCONTRADAS: " + entradaMercaderiaItemMap.size());
            logger.error(header1 + "CANTIDAD DE EM EXCLUIDAS: " + cantidadEntregaMercaderiaExcluida);
            logger.error(header1 + "CANTIDAD DE HES ENCONTRADAS: " + hojaServicioItemMap.size());

            for(int i=0 ; i < sapTableItemDtoList.size() ; i++){
                logger.error(header1 + "SAP ITEM " + i + ": " + sapTableItemDtoList.get(i).toString());
            }

            logger.error(header1 + "FINISHED");

            return sapTableItemDtoList;
        }
        catch (Exception e){
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }


    private DocumentoAceptacion actualizarDocumentoAceptacionAndOrdenCompra(String tipoDocAceptacion, DocumentoAceptacion documentoAceptacion, OrdenCompra ordenCompra, boolean aprobarOrdenCompra, boolean enviarCorreo){
        if(aprobarOrdenCompra
                && documentoAceptacion.getIdEstadoDocumentoAceptacion().compareTo(DocumentoAceptacionEstadoEnum.ACTIVO.getId())==0
                && (ordenCompra.getIdEstadoOrdenCompra().compareTo(OrdenCompraEstadoEnum.ACTIVA.getId())==0 || ordenCompra.getIdEstadoOrdenCompra().compareTo(OrdenCompraEstadoEnum.VISUALIZADA.getId())==0))
        {
            ordenCompra.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.APROBADA.getId());
            ordenCompra.setFechaAprobacion(DateUtils.getCurrentTimestamp());
            ordenCompraRepository.save(ordenCompra);

            if (enviarCorreo) {
            /*Enviando correo*/
                Usuario comprador = usuarioRepository.findByCodigoSap(ordenCompra.getCompradorUsuarioSap());
                if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty())
                    contactoAprobadaRechazadaOCNotificacion.enviar(ordenCompra,null, comprador);

                //Proveedor proveedor = proveedorService.getProveedorByRuc(ordenCompra.getProveedorRuc());
                Usuario proveedorUsuario = null;
                if (!ordenCompra.getProveedorRuc().equalsIgnoreCase("")) {
                    IASResponse response = userIASService.getUserByLoginName(ordenCompra.getProveedorRuc());
                    if (response.getStatus().equals("200")) {
                        proveedorUsuario = new Usuario();
                        Gson gson = new Gson();
                        logger.error("<--MC_LOG-->:JCODocumentoAceptacionServiceImpl-actualizarDocumentoAceptacionAndOrdenCompra:" + gson.toJson(response.getResult().getResources().get(0).getEmails()));
                        IASUserInfoResponse.Resource resource = response.getResult().getResources().get(0);
                        String givenName = resource.getName().getGivenName() != null ? resource.getName().getGivenName() + " " : "";
                        String familyName = resource.getName().getFamilyName() == null ? "" : resource.getName().getFamilyName();
                        proveedorUsuario.setApellido(givenName + familyName);
                        proveedorUsuario.setEmail(resource.getEmails().get(0).getValue());
                        logger.error("proveedorUsuario- " + proveedorUsuario);
                    } else {
                        List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(ordenCompra.getProveedorRuc());
                        if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                            proveedorUsuario = posibleProveedorList.get(0);
                    }

                }

                if(proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                    contactoAprobadaRechazadaOCNotificacion.enviar(ordenCompra, proveedorUsuario, null);
            }
        }

        documentoAceptacion.setIdOrdenCompra(ordenCompra.getId());
        documentoAceptacion.setProveedorRuc(ordenCompra.getProveedorRuc());
        documentoAceptacion.setProveedorRazonSocial(ordenCompra.getProveedorRazonSocial());

        if(tipoDocAceptacion.equals("HES")){
            documentoAceptacion.setUsuarioSapAutoriza(ordenCompra.getUltimoLiberadorUsuarioSap());
        }

        return documentoAceptacion;
    }


    private int asignarIdEstadoDocumentoAceptacion(String movimiento){
        switch (movimiento) {
            case "101": // Liberación EM
                return DocumentoAceptacionEstadoEnum.ACTIVO.getId();
            case "103": // Liberación 1er Paso
                return DocumentoAceptacionEstadoEnum.TRANSITO.getId();
            case "105": // Liberación 2d Paso
                return DocumentoAceptacionEstadoEnum.ACTIVO.getId();
            default: // Anulacion EM, Anulacion 1er Paso, Anulacion 2do Paso ("102","104","106")
                return DocumentoAceptacionEstadoEnum.ANULACION.getId();
        }
    }


    private void mapFilters(JCoFunction function, String parametro1, String parametro2, boolean extraccionUnicoDocumento) {
        JCoParameterList paramList = function.getImportParameterList();

        if(extraccionUnicoDocumento){
            JCoTable jcoTableEBELN = paramList.getTable("I_EBELN");

            if (parametro1 != null && !parametro1.isEmpty()) {
                jcoTableEBELN.appendRow();
                jcoTableEBELN.setRow(0);
                jcoTableEBELN.setValue("SIGN", "I");
                jcoTableEBELN.setValue("OPTION", "EQ");
                jcoTableEBELN.setValue("LOW", parametro1); // parametro numero orden compra a la cual se extraera sus doc aceptacion
                jcoTableEBELN.setValue("HIGH", "");
            }
        }
        else {
            if (parametro1 != null && !parametro1.isEmpty())
                paramList.setValue("I_CPUDTI", parametro1); // parametro fecha inicio desde la cual se extraera los doc aceptacion

            if (parametro2 != null && !parametro2.isEmpty())
                paramList.setValue("I_CPUDTF", parametro2); // parametro fecha fin hasta la cual se extraera los doc aceptacion
        }
    }

    public boolean toggleDocumentoAceptacionExtractionProcessingState() {
        daProcessing.set(!daProcessing.get());
        return daProcessing.get();
    }

    public boolean currentDocumentoAceptacionExtractionProcessingState() {
        return daProcessing.get();
    }
}