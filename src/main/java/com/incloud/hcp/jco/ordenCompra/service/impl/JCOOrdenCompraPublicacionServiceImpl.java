package com.incloud.hcp.jco.ordenCompra.service.impl;

import com.google.gson.Gson;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.enums.OpcionGenericaEnum;
import com.incloud.hcp.enums.OrdenCompraEstadoEnum;
import com.incloud.hcp.enums.OrdenCompraEstadoSapEnum;
import com.incloud.hcp.enums.OrdenCompraTipoEnum;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenCompraPublicacionService;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.notificacion.ContactoPublicadaOCNotificacion;
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
import java.math.RoundingMode;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOOrdenCompraPublicacionServiceImpl implements JCOOrdenCompraPublicacionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    private final AtomicBoolean ocProcessing = new AtomicBoolean(false);

    private UsuarioRepository usuarioRepository;
    private OrdenCompraRepository ordenCompraRepository;
    private OrdenCompraDetalleRepository ordenCompraDetalleRepository;
    private OrdenCompraDetalleTextoRepository ordenCompraDetalleTextoRepository;
    private ContactoPublicadaOCNotificacion contactoPublicadaOCNotificacion;
    private OrdenCompraTextoCabeceraRepository ordenCompraTextoCabeceraRepository;
    private OrdenCompraDetalleTextoRegistroInfoRepository ordenCompraDetalleTextoRegistroInfoRepository;
    private OrdenCompraDetalleTextoMaterialAmpliadoRepository ordenCompraDetalleTextoMaterialAmpliadoRepository;
    private LogTransaccionRepository logTransaccionRepository;
    private ProveedorRepository proveedorRepository;
    private ProveedorService proveedorService;
    private IUserIASService userIASService;


    @Autowired
    public JCOOrdenCompraPublicacionServiceImpl(UsuarioRepository usuarioRepository,
                                                OrdenCompraRepository ordenCompraRepository,
                                                OrdenCompraDetalleRepository ordenCompraDetalleRepository,
                                                OrdenCompraDetalleTextoRepository ordenCompraDetalleTextoRepository,
                                                ContactoPublicadaOCNotificacion contactoPublicadaOCNotificacion,
                                                OrdenCompraTextoCabeceraRepository ordenCompraTextoCabeceraRepository,
                                                OrdenCompraDetalleTextoRegistroInfoRepository ordenCompraDetalleTextoRegistroInfoRepository,
                                                OrdenCompraDetalleTextoMaterialAmpliadoRepository ordenCompraDetalleTextoMaterialAmpliadoRepository,
                                                ProveedorService proveedorService,
                                                LogTransaccionRepository logTransaccionRepository,
                                                ProveedorRepository proveedorRepository,
                                                IUserIASService userIASService) {
        this.usuarioRepository = usuarioRepository;
        this.ordenCompraRepository = ordenCompraRepository;
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
        this.ordenCompraDetalleTextoRepository = ordenCompraDetalleTextoRepository;
        this.contactoPublicadaOCNotificacion = contactoPublicadaOCNotificacion;
        this.ordenCompraTextoCabeceraRepository = ordenCompraTextoCabeceraRepository;
        this.ordenCompraDetalleTextoRegistroInfoRepository = ordenCompraDetalleTextoRegistroInfoRepository;
        this.ordenCompraDetalleTextoMaterialAmpliadoRepository = ordenCompraDetalleTextoMaterialAmpliadoRepository;
        this.logTransaccionRepository = logTransaccionRepository;
        this.proveedorRepository = proveedorRepository;
        this.proveedorService = proveedorService;
        this.userIASService = userIASService;
    }


    @Override
    public void extraerOrdenCompraListRFC(String fechaInicio, String fechaFin, boolean enviarCorreoPublicacion) throws Exception {
        try {
            String FUNCION_RFC = "ZPE_MM_COMPRAS_DETAIL";

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            this.mapFilters(jCoFunction, fechaInicio, fechaFin);
            jCoFunction.execute(destination);

            //JCoParameterList exportParameterList = jCoFunction.getExportParameterList();
            JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
            OrdenCompraExtractorMapper ordenCompraExtractorMapper = OrdenCompraExtractorMapper.newMapper(tableParameterList);

            List<OrdenCompra> ordenCompraSapList = ordenCompraExtractorMapper.getOrdenCompraList();
            List<OrdenCompraTextoCabecera> ordenCompraTextoCabeceraSapList = ordenCompraExtractorMapper.getOrdenCompraTextoCabeceraList();
            List<OrdenCompraDetalle> ordenCompraDetalleSapList = ordenCompraExtractorMapper.getOrdenCompraDetalleList();
            List<OrdenCompraDetalleTexto> ordenCompraDetalleTextoPosicionSapList = ordenCompraExtractorMapper.getOrdenCompraDetalleTextoList();
            List<OrdenCompraDetalleTextoRegistroInfo> ordenCompraDetalleTextoRegistroInfoSapList = ordenCompraExtractorMapper.getOrdenCompraDetalleTextoRegistroInfoList();
            List<OrdenCompraDetalleTextoMaterialAmpliado> ordenCompraDetalleTextoMaterialAmpliadoSapList = ordenCompraExtractorMapper.getOrdenCompraDetalleTextoMaterialAmpliadoList();

            String header1 = "INI: " + DateUtils.getCurrentTimestamp().toString() + " -- EXTR OC -- RANGO: " + fechaInicio + " - " + fechaFin + " // ";
            //logger.error(header1 + "Rango de Fechas : " + fechaInicio + " - " + fechaFin);
            logger.error(header1 + "CANTIDAD DE OC ENCONTRADOS: " + ordenCompraSapList.size());
            logger.error(header1 + "CANTIDAD DE OCD ENCONTRADOS: " + ordenCompraDetalleSapList.size());

            ordenCompraSapList.forEach(oc -> {
                String numOrdenCompra = oc.getNumeroOrdenCompra();
                logger.error("numOrdenCompra - " + numOrdenCompra);
                List<OrdenCompra> listOrdenCompra = ordenCompraRepository.listOrdenCompraActivaByNumero(numOrdenCompra);
                logger.error("listOrdenCompra size " + listOrdenCompra.size());
                String header2 = header1.concat("OC: " + oc.getNumeroOrdenCompra());

                Boolean continuarOC = true;
                if (listOrdenCompra.size() > 1) {
                    continuarOC = false;
                    logger.error("cotinuarOc false");
                    //String sLogValidarOC = ;
                    LogTransaccion logValidarOC = new LogTransaccion();
                    logValidarOC.setLogFecha(DateUtils.getCurrentTimestamp());
                    logValidarOC.setTipoRegistro("runOrdenCompraExtractor - ocDuplicada");
                    logValidarOC.setEnvioTrama("Existe OC duplicada " + numOrdenCompra);
                    this.logTransaccionRepository.save(logValidarOC);
                }

                logger.error("continuarOC true");
                if (continuarOC) {
                    if (listOrdenCompra.size() == 0 || listOrdenCompra.isEmpty()) { // OC no existe en HANA
                        //if (ocOptional.isPresent()) {
                        if (oc.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.LIBERADA.getCodigo())) { // solo publicar OC si esta en estado liberado
                            logger.error("masivo_oc_nuevo_::: " + oc.toString());
                            oc.setVersion(1); // porque OC es publicada por 1ra vez
                            oc.setIsActive(OpcionGenericaEnum.SI.getCodigo()); // OC es activa porque es la 1ra y unica version
                            oc.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.ACTIVA.getId()); // estado inicial publicada
                            oc.setFechaPublicacion(DateUtils.getCurrentTimestamp());

                            logger.error(header2 + " // WRITING NEW OC: " + oc.toString());
                            oc = ordenCompraRepository.saveAndFlush(oc);
                            //Optional<OrdenCompra> ocOptional = ordenCompraRepository.findById(oc.getId());

                            LogTransaccion logCrearOcNueva = new LogTransaccion();
                            logCrearOcNueva.setLogFecha(DateUtils.getCurrentTimestamp());
                            logCrearOcNueva.setTipoRegistro("runOrdenCompraExtractor - guardarOCnoExiste");
                            logCrearOcNueva.setEnvioTrama("se guardó primera version OC" + numOrdenCompra + " estado: " + oc.getEstadoSap());
                            this.logTransaccionRepository.save(logCrearOcNueva);

                            //oc = ocOptional.get();
                            Integer idOrdenCompra = oc.getId();
                            Integer idTipoOrdenCompra = oc.getIdTipoOrdenCompra();

                            ordenCompraTextoCabeceraSapList.stream()
                                    .filter(octc -> octc.getNumeroOrdenCompra().equals(numOrdenCompra))
                                    .forEach(octc -> {
                                        octc.setIdOrdenCompra(idOrdenCompra);

                                        logger.error(header2 + " // WRITING NEW OCTC: " + octc.toString());
                                        ordenCompraTextoCabeceraRepository.save(octc);
                                    });

                            ordenCompraDetalleSapList.stream()
                                    .filter(ocd -> ocd.getNumeroOrdenCompra().equals(numOrdenCompra))
                                    .forEach(ocd -> {
                                        ocd.setIdOrdenCompra(idOrdenCompra);
                                        ocd.setTipoPosicion(idTipoOrdenCompra == OrdenCompraTipoEnum.MATERIAL.getId() ? "M" : "S");

                                        BigDecimal cantidadBase = ocd.getPrecioTotal();
                                        BigDecimal precioUnitarioBase = ocd.getPrecioUnitario();
                                        BigDecimal precioUnitario = precioUnitarioBase.divide(cantidadBase, 4, RoundingMode.HALF_UP);

                                        ocd.setPrecioUnitario(precioUnitario);
                                        ocd.setPrecioTotal(ocd.getCantidad().multiply(precioUnitario).setScale(4, RoundingMode.HALF_UP));

                                        logger.error(header2 + " // WRITING NEW OCD: " + ocd.toString());
                                        ocd = ordenCompraDetalleRepository.save(ocd);
                                        Integer idOrdenCompraDetalle = ocd.getId();
                                        String posicion = ocd.getPosicion();

                                        ordenCompraDetalleTextoPosicionSapList.stream()
                                                .filter(ocdt -> ocdt.getNumeroOrdenCompra().equals(numOrdenCompra) && ocdt.getPosicion().equals(posicion))
                                                .forEach(ocdt -> {
                                                    ocdt.setIdOrdenCompraDetalle(idOrdenCompraDetalle);
                                                    logger.error(header2 + " // WRITING NEW OCDT: " + ocdt.toString());
                                                    ordenCompraDetalleTextoRepository.save(ocdt);
                                                });

                                        ordenCompraDetalleTextoRegistroInfoSapList.stream()
                                                .filter(ocdtri -> ocdtri.getNumeroOrdenCompra().equals(numOrdenCompra) && ocdtri.getPosicion().equals(posicion))
                                                .forEach(ocdtri -> {
                                                    ocdtri.setIdOrdenCompraDetalle(idOrdenCompraDetalle);
                                                    logger.error(header2 + " // WRITING NEW OCDTRI: " + ocdtri.toString());
                                                    ordenCompraDetalleTextoRegistroInfoRepository.save(ocdtri);
                                                });

                                        ordenCompraDetalleTextoMaterialAmpliadoSapList.stream()
                                                .filter(ocdtma -> ocdtma.getNumeroOrdenCompra().equals(numOrdenCompra) && ocdtma.getPosicion().equals(posicion))
                                                .forEach(ocdtma -> {
                                                    ocdtma.setIdOrdenCompraDetalle(idOrdenCompraDetalle);
                                                    logger.error(header2 + " // WRITING NEW OCDTMA: " + ocdtma.toString());
                                                    ordenCompraDetalleTextoMaterialAmpliadoRepository.save(ocdtma);
                                                });
                                    });

                            if (enviarCorreoPublicacion) {
                                /*Enviando Correo*/
                                Usuario comprador = usuarioRepository.findByCodigoSap(oc.getCompradorUsuarioSap());
                                if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty())
                                    contactoPublicadaOCNotificacion.enviar(oc, null, comprador);

                                Boolean enviarCorreoProveedor = true;
                                if (enviarCorreoProveedor) {
                                    Usuario proveedorUsuario = null;
                                    logger.error("oc.getProveedorRuc()+ " + oc.getProveedorRuc());
                                    if (!oc.getProveedorRuc().equalsIgnoreCase("")) {
                                        IASResponse response = userIASService.getUserByLoginName(oc.getProveedorRuc());
                                        if (response.getStatus().equals("200")) {
                                            Gson gson = new Gson();
                                            logger.error("emails+" + gson.toJson(response.getResult().getResources().get(0).getEmails()));
                                            proveedorUsuario = new Usuario();
                                            IASUserInfoResponse.Resource resource = response.getResult().getResources().get(0);
                                            String givenName = resource.getName().getGivenName() != null ? resource.getName().getGivenName() + " " : "";
                                            String familyName = resource.getName().getFamilyName() == null ? "" : resource.getName().getFamilyName();
                                            proveedorUsuario.setApellido(givenName + familyName);
                                            proveedorUsuario.setEmail(resource.getEmails().get(0).getValue());
                                            logger.error("proveedorUsuario+ " + proveedorUsuario);
                                        }
                                    }

                                    if (proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                                        contactoPublicadaOCNotificacion.enviar(oc, proveedorUsuario, null);

                                }
                            }
                        } else {
                            LogTransaccion logNoSeGuardaOCNueva = new LogTransaccion();
                            logNoSeGuardaOCNueva.setLogFecha(DateUtils.getCurrentTimestamp());
                            logNoSeGuardaOCNueva.setTipoRegistro("runOrdenCompraExtractor - noGuardarOCNuevaBloqueada");
                            logNoSeGuardaOCNueva.setEnvioTrama("no se guardó OC nueva bloqueada o anulada " + numOrdenCompra + " estado: " + oc.getEstadoSap());
                            this.logTransaccionRepository.save(logNoSeGuardaOCNueva);
                        }
                    } else { // OC ya existe en HANA
                        OrdenCompra ocAnterior = listOrdenCompra.get(0);
                        Date fechaModAnterior = ocAnterior.getFechaModificacion();
                        Time horaModAnterior = ocAnterior.getHoraModificacion();
                        Date fechaModNueva = oc.getFechaModificacion();
                        Time horaModNueva = oc.getHoraModificacion();

                        boolean procede;

                        if (ocAnterior.getIdEstadoOrdenCompra().compareTo(OrdenCompraEstadoEnum.APROBADA.getId()) == 0) {
                            procede = false;
                        } else {
                            // evalua si la fecha y hora de modificacion del nuevo registro es mayor a la del registro existente
                            procede = DateUtils.evaluarModificacionDeDocumento(fechaModAnterior, horaModAnterior, fechaModNueva, horaModNueva);
                        }

                        if (procede) {
                            if (oc.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.LIBERADA.getCodigo())) { // llega registro OC liberada
                                /*if (ocAnterior.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.BLOQUEADA.getCodigo())
                                        || ocAnterior.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.LIBERADA.getCodigo())) {*/
                                ocAnterior.setIsActive(OpcionGenericaEnum.NO.getCodigo()); // la version anterior pasa a inactiva (no se visualizara)
                                ocAnterior = ordenCompraRepository.saveAndFlush(ocAnterior);
                                logger.error("masivo_ocAnterior_modificacion_::: " + ocAnterior.toString());

                                oc.setVersion(ocAnterior.getVersion() + 1); // numero de version sgte al actual
                                oc.setIsActive(OpcionGenericaEnum.SI.getCodigo()); // la ultima version es la unica activa (que se va a visualizar)
                                oc.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.ACTIVA.getId()); // estado inicial "Activa" (publicada)
                                oc.setFechaPublicacion(DateUtils.getCurrentTimestamp());
                                oc.setIdTipoOrdenCompra(ocAnterior.getIdTipoOrdenCompra());
                                logger.error(header2 + " // WRITING NEXT VERSION OC: " + oc.toString());
                                oc = ordenCompraRepository.saveAndFlush(oc);
                                logger.error("masivo_oc_modificacion_nuevo::: " + oc.toString());

                                LogTransaccion logCrearOcNueva = new LogTransaccion();
                                logCrearOcNueva.setLogFecha(DateUtils.getCurrentTimestamp());
                                logCrearOcNueva.setTipoRegistro("runOrdenCompraExtractor - guardarOCExiste");
                                logCrearOcNueva.setEnvioTrama("se guardó nueva version OC" + numOrdenCompra);
                                this.logTransaccionRepository.save(logCrearOcNueva);

                                Integer idOrdenCompra = oc.getId();
                                Integer idTipoOrdenCompra = oc.getIdTipoOrdenCompra();

                                ordenCompraTextoCabeceraSapList.stream()
                                        .filter(octc -> octc.getNumeroOrdenCompra().equals(numOrdenCompra))
                                        .forEach(octc -> {
                                            octc.setIdOrdenCompra(idOrdenCompra);

                                            logger.error(header2 + " // WRITING NEW OCTC: " + octc.toString());
                                            ordenCompraTextoCabeceraRepository.save(octc);
                                        });

                                ordenCompraDetalleSapList.stream()
                                        .filter(ocd -> ocd.getNumeroOrdenCompra().equals(numOrdenCompra))
                                        .forEach(ocd -> {
                                            ocd.setIdOrdenCompra(idOrdenCompra);
                                            ocd.setTipoPosicion(idTipoOrdenCompra == OrdenCompraTipoEnum.MATERIAL.getId() ? "M" : "S");

                                            BigDecimal cantidadBase = ocd.getPrecioTotal();
                                            BigDecimal precioUnitarioBase = ocd.getPrecioUnitario();
                                            BigDecimal precioUnitario = precioUnitarioBase.divide(cantidadBase, 4, RoundingMode.HALF_UP);

                                            ocd.setPrecioUnitario(precioUnitario);
                                            ocd.setPrecioTotal(ocd.getCantidad().multiply(precioUnitario).setScale(4, RoundingMode.HALF_UP));

                                            logger.error(header2 + " // WRITING NEXT VERSION OCD: " + ocd.toString());
                                            ocd = ordenCompraDetalleRepository.save(ocd);
                                            Integer idOrdenCompraDetalle = ocd.getId();
                                            String posicion = ocd.getPosicion();

                                            ordenCompraDetalleTextoPosicionSapList.stream()
                                                    .filter(ocdt -> ocdt.getNumeroOrdenCompra().equals(numOrdenCompra) && ocdt.getPosicion().equals(posicion))
                                                    .forEach(ocdt -> {
                                                        ocdt.setIdOrdenCompraDetalle(idOrdenCompraDetalle);
                                                        logger.error(header2 + " // WRITING NEW OCDT: " + ocdt.toString());
                                                        ordenCompraDetalleTextoRepository.save(ocdt);
                                                    });

                                            ordenCompraDetalleTextoRegistroInfoSapList.stream()
                                                    .filter(ocdtri -> ocdtri.getNumeroOrdenCompra().equals(numOrdenCompra) && ocdtri.getPosicion().equals(posicion))
                                                    .forEach(ocdtri -> {
                                                        ocdtri.setIdOrdenCompraDetalle(idOrdenCompraDetalle);
                                                        logger.error(header2 + " // WRITING NEW OCDTRI: " + ocdtri.toString());
                                                        ordenCompraDetalleTextoRegistroInfoRepository.save(ocdtri);
                                                    });

                                            ordenCompraDetalleTextoMaterialAmpliadoSapList.stream()
                                                    .filter(ocdtma -> ocdtma.getNumeroOrdenCompra().equals(numOrdenCompra) && ocdtma.getPosicion().equals(posicion))
                                                    .forEach(ocdtma -> {
                                                        ocdtma.setIdOrdenCompraDetalle(idOrdenCompraDetalle);
                                                        logger.error(header2 + " // WRITING NEW OCDTMA: " + ocdtma.toString());
                                                        ordenCompraDetalleTextoMaterialAmpliadoRepository.save(ocdtma);
                                                    });
                                        });

                                if (enviarCorreoPublicacion) {
                                    /*Enviando Correo*/
                                    Usuario comprador = usuarioRepository.findByCodigoSap(oc.getCompradorUsuarioSap());
                                    if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty())
                                        contactoPublicadaOCNotificacion.enviar(oc, null, comprador);

                                    Boolean enviarCorreoProveedor = true;
                                    Usuario proveedorUsuario = null;
                                    logger.error("oc.getProveedorRuc()- " + oc.getProveedorRuc());
                                    if (enviarCorreoProveedor) {
                                        if (!oc.getProveedorRuc().equalsIgnoreCase("")) {
                                            IASResponse response = userIASService.getUserByLoginName(oc.getProveedorRuc());
                                            if (response.getStatus().equals("200")) {
                                                Gson gson = new Gson();
                                                logger.error("emails-" + gson.toJson(response.getResult().getResources().get(0).getEmails()));
                                                proveedorUsuario = new Usuario();
                                                IASUserInfoResponse.Resource resource = response.getResult().getResources().get(0);
                                                String givenName = resource.getName().getGivenName() != null ? resource.getName().getGivenName() + " " : "";
                                                String familyName = resource.getName().getFamilyName() == null ? "" : resource.getName().getFamilyName();
                                                proveedorUsuario.setApellido(givenName + familyName);
                                                proveedorUsuario.setEmail(resource.getEmails().get(0).getValue());
                                                logger.error("proveedorUsuario- " + proveedorUsuario);
                                            }
                                        }

                                        if (proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                                            contactoPublicadaOCNotificacion.enviar(oc, proveedorUsuario, null);
                                    }
                                }
                                //}
                                /*} else {
                                    LogTransaccion logNoSeGuardaOCNueva = new LogTransaccion();
                                    logNoSeGuardaOCNueva.setLogFecha(DateUtils.getCurrentTimestamp());
                                    logNoSeGuardaOCNueva.setTipoRegistro("runOrdenCompraExtractor - guardarOCExisteAnulada");
                                    logNoSeGuardaOCNueva.setEnvioTrama("no se guardó OC existe " + numOrdenCompra);
                                    this.logTransaccionRepository.save(logNoSeGuardaOCNueva);
                                }*/
                            } else { // llega registro OC bloqueada o anulada
                                ocAnterior.setIsActive(OpcionGenericaEnum.NO.getCodigo()); // la version anterior pasa a inactiva (no se visualizara)
                                ocAnterior = ordenCompraRepository.saveAndFlush(ocAnterior);
                                logger.error("anulada_ocAnterior_modificacion_::: " + ocAnterior.toString());

                                oc.setVersion(ocAnterior.getVersion() + 1); // numero de version sgte al actual
                                oc.setIsActive(OpcionGenericaEnum.SI.getCodigo()); // la ultima version es la unica activa (que se va a visualizar)
                                oc.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.ANULADA.getId()); // estado inicial "Activa" (publicada)
                                oc.setFechaPublicacion(DateUtils.getCurrentTimestamp());
                                oc.setIdTipoOrdenCompra(ocAnterior.getIdTipoOrdenCompra());
                                logger.error(header2 + " // WRITING NEXT VERSION OC: " + oc.toString());
                                oc = ordenCompraRepository.saveAndFlush(oc);

                                LogTransaccion logCrearOcNuevaA = new LogTransaccion();
                                logCrearOcNuevaA.setLogFecha(DateUtils.getCurrentTimestamp());
                                logCrearOcNuevaA.setTipoRegistro("runOrdenCompraExtractor - guardarOCExisteAnul");
                                logCrearOcNuevaA.setEnvioTrama("se guardó nueva version OC Anulada" + numOrdenCompra);
                                this.logTransaccionRepository.save(logCrearOcNuevaA);
                            }
                        }
                    }
                }

            });
            logger.error(header1 + "FINISHED");

            if (ocProcessing.get())
                ocProcessing.set(!ocProcessing.get());
        } catch (Exception e) {
            if (ocProcessing.get())
                ocProcessing.set(!ocProcessing.get());
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }


    private void mapFilters(JCoFunction function, String fechaInicio, String fechaFin) {
        JCoParameterList paramList = function.getImportParameterList();

        if (fechaInicio != null && !fechaInicio.isEmpty())
            paramList.setValue("I_AEDATI", fechaInicio); // parametro fecha desde la cual se extraera las OC

        if (fechaFin != null && !fechaFin.isEmpty())
            paramList.setValue("I_AEDATF", fechaFin); // parametro fecha hasta la cual se extraera las OC
    }


    public boolean toggleOrdenCompraExtractionProcessingState() {
        ocProcessing.set(!ocProcessing.get());
        return ocProcessing.get();
    }


    public boolean currentOrdenCompraExtractionProcessingState() {
        return ocProcessing.get();
    }
}
