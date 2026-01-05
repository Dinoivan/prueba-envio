package com.incloud.hcp.jco.ordenCompra.service.impl;

import com.google.gson.Gson;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.dto.InfoMessage;
import com.incloud.hcp.dto.OrdenCompraSapDataDto;
import com.incloud.hcp.enums.OpcionGenericaEnum;
import com.incloud.hcp.enums.OrdenCompraEstadoEnum;
import com.incloud.hcp.enums.OrdenCompraEstadoSapEnum;
import com.incloud.hcp.enums.OrdenCompraTipoEnum;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenCompraPublicarOneService;
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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JCOOrdenCompraPublicarOneServiceImpl implements JCOOrdenCompraPublicarOneService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    private OrdenCompraRepository ordenCompraRepository;
    private OrdenCompraDetalleRepository ordenCompraDetalleRepository;
    private OrdenCompraDetalleTextoRepository ordenCompraDetalleTextoRepository;
    private OrdenCompraTextoCabeceraRepository ordenCompraTextoCabeceraRepository;
    private OrdenCompraDetalleTextoRegistroInfoRepository ordenCompraDetalleTextoRegistroInfoRepository;
    private OrdenCompraDetalleTextoMaterialAmpliadoRepository ordenCompraDetalleTextoMaterialAmpliadoRepository;
    private ContactoPublicadaOCNotificacion contactoPublicadaOCNotificacion;
    private UsuarioRepository usuarioRepository;
    private ProveedorService proveedorService;
    private ProveedorRepository proveedorRepository;
    private IUserIASService userIASService;

    @Autowired
    public JCOOrdenCompraPublicarOneServiceImpl(OrdenCompraRepository ordenCompraRepository,
                                                OrdenCompraDetalleRepository ordenCompraDetalleRepository,
                                                OrdenCompraDetalleTextoRepository ordenCompraDetalleTextoRepository,
                                                OrdenCompraTextoCabeceraRepository ordenCompraTextoCabeceraRepository,
                                                OrdenCompraDetalleTextoRegistroInfoRepository ordenCompraDetalleTextoRegistroInfoRepository,
                                                OrdenCompraDetalleTextoMaterialAmpliadoRepository ordenCompraDetalleTextoMaterialAmpliadoRepository,
                                                ContactoPublicadaOCNotificacion contactoPublicadaOCNotificacion,
                                                UsuarioRepository usuarioRepository,
                                                ProveedorService proveedorService,
                                                ProveedorRepository proveedorRepository,
                                                IUserIASService userIASService) {
        this.ordenCompraRepository = ordenCompraRepository;
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
        this.ordenCompraDetalleTextoRepository = ordenCompraDetalleTextoRepository;
        this.ordenCompraTextoCabeceraRepository = ordenCompraTextoCabeceraRepository;
        this.ordenCompraDetalleTextoRegistroInfoRepository = ordenCompraDetalleTextoRegistroInfoRepository;
        this.ordenCompraDetalleTextoMaterialAmpliadoRepository = ordenCompraDetalleTextoMaterialAmpliadoRepository;
        this.contactoPublicadaOCNotificacion = contactoPublicadaOCNotificacion;
        this.usuarioRepository = usuarioRepository;
        this.proveedorService = proveedorService;
        this.proveedorRepository = proveedorRepository;
        this.userIASService = userIASService;
    }


    @Override
    public InfoMessage extraerOneOrdenCompraRFC(String numeroOrdenCompra, boolean enviarCorreoPublicacion) throws Exception {
        try {
            String FUNCION_RFC = "ZPE_MM_COMPRAS_DETAIL";

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            this.mapFilters(jCoFunction, numeroOrdenCompra);
            jCoFunction.execute(destination);

            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            OrdenCompraExtractorMapper ordenCompraExtractorMapper = OrdenCompraExtractorMapper.newMapper(exportParameterList);

            List<OrdenCompra> ordenCompraSapList = ordenCompraExtractorMapper.getOrdenCompraList();
            logger.error("Cantidad de OC encontrada: " + ordenCompraSapList.size());
            logger.error("Cantidad de OC encontrada: " + ordenCompraSapList);
            List<OrdenCompraDetalle> ordenCompraDetalleSapList = ordenCompraExtractorMapper.getOrdenCompraDetalleList();
            logger.error("Cantidad de OC encontrada: " + ordenCompraDetalleSapList.size());
            logger.error("Cantidad de OC encontrada: " + ordenCompraDetalleSapList);
            List<OrdenCompraTextoCabecera> ordenCompraTextoCabeceraSapList = ordenCompraExtractorMapper.getOrdenCompraTextoCabeceraList();
            List<OrdenCompraDetalleTexto> ordenCompraDetalleTextoPosicionSapList = ordenCompraExtractorMapper.getOrdenCompraDetalleTextoList();
            List<OrdenCompraDetalleTextoRegistroInfo> ordenCompraDetalleTextoRegistroInfoSapList = ordenCompraExtractorMapper.getOrdenCompraDetalleTextoRegistroInfoList();
            List<OrdenCompraDetalleTextoMaterialAmpliado> ordenCompraDetalleTextoMaterialAmpliadoSapList = ordenCompraExtractorMapper.getOrdenCompraDetalleTextoMaterialAmpliadoList();

            InfoMessage infoMessage = new InfoMessage();

            String header1 = "INI: " + DateUtils.getCurrentTimestamp().toString() + " -- EXTR ONE OC: " + numeroOrdenCompra;
            logger.error(header1 + " // Extraccion ONE Orden de Compra");

            if (ordenCompraSapList.size() == 1) {
                infoMessage.setMessageCode("SUCCESS");
                infoMessage.setMessageText1("Se encontro 1 Orden de Compra con numero: " + numeroOrdenCompra);
                OrdenCompra nuevaOrdenCompra = ordenCompraSapList.get(0);

                logger.error(header1 + " // FOUND ONE OC: " + nuevaOrdenCompra.toString()); // MUESTRA SIEMPRE LOS DATOS DE LA OC QUE LLEGA DE SAP
                int[] counterArray = new int[]{0};
                ordenCompraDetalleSapList.forEach(ocd -> {
                    counterArray[0]++;
                    logger.error(header1 + " // FOUND OCD " + counterArray[0] + ": " + ocd.toString()); // MUESTRA SIEMPRE LOS DATOS DE LOS OCD QUE LLEGAN DE SAP
                });

                Optional<OrdenCompra> optionalOrdenCompra = ordenCompraRepository.getOrdenCompraActivaByNumero(numeroOrdenCompra);
                logger.error("Orden de Compra encontrada BD v1: " + optionalOrdenCompra);


                if (!optionalOrdenCompra.isPresent()) { // OC no existe en HANA
                    if (nuevaOrdenCompra.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.LIBERADA.getCodigo())) { // solo publicar OC si esta en estado liberado
                        nuevaOrdenCompra.setVersion(1); // porque OC es publicada por 1ra vez
                        nuevaOrdenCompra.setIsActive(OpcionGenericaEnum.SI.getCodigo()); // OC es activa porque es la 1ra y unica version
                        nuevaOrdenCompra.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.ACTIVA.getId()); // estado inicial publicada
                        nuevaOrdenCompra.setFechaPublicacion(DateUtils.getCurrentTimestamp());
                        logger.error("simple_nuevaOrdenCompra_nuevo_::: " + nuevaOrdenCompra.toString());

                        logger.error(header1 + " // WRITING NEW ONE OC: " + nuevaOrdenCompra.toString());
                        nuevaOrdenCompra = ordenCompraRepository.saveAndFlush(nuevaOrdenCompra);
                        Integer idOrdenCompra = nuevaOrdenCompra.getId();
                        Integer idTipoOrdenCompra = nuevaOrdenCompra.getIdTipoOrdenCompra();

                        ordenCompraTextoCabeceraSapList.stream()
                                .filter(octc -> octc.getNumeroOrdenCompra().equals(numeroOrdenCompra))
                                .forEach(octc -> {
                                    octc.setIdOrdenCompra(idOrdenCompra);

                                    logger.error(header1 + " // WRITING NEW OCTC: " + octc.toString());
                                    ordenCompraTextoCabeceraRepository.save(octc);
                                });

                        ordenCompraDetalleSapList.forEach(ocd -> {
                            ocd.setIdOrdenCompra(idOrdenCompra);
                            ocd.setTipoPosicion(idTipoOrdenCompra == OrdenCompraTipoEnum.MATERIAL.getId() ? "M" : "S");

                            BigDecimal cantidadBase = ocd.getPrecioTotal();
                            BigDecimal precioUnitarioBase = ocd.getPrecioUnitario();
                            BigDecimal precioUnitario = precioUnitarioBase.divide(cantidadBase, 4, RoundingMode.HALF_UP);

                            ocd.setPrecioUnitario(precioUnitario);
                            ocd.setPrecioTotal(ocd.getCantidad().multiply(precioUnitario).setScale(4, RoundingMode.HALF_UP));

                            logger.error(header1 + " // WRITING NEW OCD: " + ocd.toString());
                            ocd = ordenCompraDetalleRepository.save(ocd);
                            Integer idOrdenCompraDetalle = ocd.getId();
                            String posicion = ocd.getPosicion();

                            ordenCompraDetalleTextoPosicionSapList.stream()
                                    .filter(ocdt -> ocdt.getPosicion().equals(posicion))
                                    .forEach(ocdt -> {
                                        ocdt.setIdOrdenCompraDetalle(idOrdenCompraDetalle);
                                        logger.error(header1 + " // WRITING NEW OCDT: " + ocdt.toString());
                                        ordenCompraDetalleTextoRepository.save(ocdt);
                                    });

                            ordenCompraDetalleTextoRegistroInfoSapList.stream()
                                    .filter(ocdtri -> ocdtri.getPosicion().equals(posicion))
                                    .forEach(ocdtri -> {
                                        ocdtri.setIdOrdenCompraDetalle(idOrdenCompraDetalle);
                                        logger.error(header1 + " // WRITING NEW OCDTRI: " + ocdtri.toString());
                                        ordenCompraDetalleTextoRegistroInfoRepository.save(ocdtri);
                                    });

                            ordenCompraDetalleTextoMaterialAmpliadoSapList.stream()
                                    .filter(ocdtma -> ocdtma.getPosicion().equals(posicion))
                                    .forEach(ocdtma -> {
                                        ocdtma.setIdOrdenCompraDetalle(idOrdenCompraDetalle);
                                        logger.error(header1 + " // WRITING NEW OCDTMA: " + ocdtma.toString());
                                        ordenCompraDetalleTextoMaterialAmpliadoRepository.save(ocdtma);
                                    });
                        });

                        infoMessage.setMessageText2("Se publico la NUEVA Orden de Compra en estado LIBERADA. Cantidad de posiciones: " + ordenCompraDetalleSapList.size());

                        if (enviarCorreoPublicacion) {
                            /*Enviando Correo*/
                            Usuario comprador = usuarioRepository.findByCodigoSap(nuevaOrdenCompra.getCompradorUsuarioSap());
                            if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty())
                                contactoPublicadaOCNotificacion.enviar(nuevaOrdenCompra, null, comprador);

                            Usuario proveedorUsuario = null;
                            logger.error("oc.getProveedorRuc()+ " + nuevaOrdenCompra.getProveedorRuc());
                            if (!nuevaOrdenCompra.getProveedorRuc().equalsIgnoreCase("")) {
                                IASResponse response = userIASService.getUserByLoginName(nuevaOrdenCompra.getProveedorRuc());
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
                                contactoPublicadaOCNotificacion.enviar(nuevaOrdenCompra, proveedorUsuario, null);
                        }
                    } else {
                        infoMessage.setMessageText2("No se publico la Orden de Compra pues no esta en estado LIBERADA");
                    }
                } else { // OC ya existe en HANA
                    OrdenCompra ordenCompraAnterior = optionalOrdenCompra.get();
                    Date fechaModAnterior = ordenCompraAnterior.getFechaModificacion();
                    Time horaModAnterior = ordenCompraAnterior.getHoraModificacion();
                    Date fechaModNueva = nuevaOrdenCompra.getFechaModificacion();
                    Time horaModNueva = nuevaOrdenCompra.getHoraModificacion();

                    boolean procede;

                    if (ordenCompraAnterior.getIdEstadoOrdenCompra().compareTo(OrdenCompraEstadoEnum.APROBADA.getId()) == 0) {
                        procede = true;
                    } else {
                        // evalua si la fecha y hora de modificacion del nuevo registro es mayor a la del registro existente
                        procede = evaluarModificacionDeOrdenCompra(fechaModAnterior, horaModAnterior, fechaModNueva, horaModNueva);
                    }
                    logger.error("Procede OC: " + procede);

                    if (procede) {
                        if (nuevaOrdenCompra.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.LIBERADA.getCodigo())) { // llega registro OC liberada
                            /*if (ordenCompraAnterior.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.BLOQUEADA.getCodigo())
                                    || ordenCompraAnterior.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.LIBERADA.getCodigo())) {*/
                            ordenCompraAnterior.setIsActive(OpcionGenericaEnum.NO.getCodigo()); // la version anterior pasa a inactiva (no se visualizara)
                            ordenCompraAnterior = ordenCompraRepository.saveAndFlush(ordenCompraAnterior);
                            logger.error("simple_ordenCompraAnterior_modificacion_::: " + ordenCompraAnterior.toString());

                            nuevaOrdenCompra.setVersion(ordenCompraAnterior.getVersion() + 1); // numero de version sgte al actual
                            nuevaOrdenCompra.setIsActive(OpcionGenericaEnum.SI.getCodigo()); // la ultima version es la unica activa (que se va a visualizar)
                            nuevaOrdenCompra.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.ACTIVA.getId()); // estado inicial "Activa" (publicada)
                            nuevaOrdenCompra.setFechaPublicacion(DateUtils.getCurrentTimestamp());
                            nuevaOrdenCompra.setIdTipoOrdenCompra(ordenCompraAnterior.getIdTipoOrdenCompra());
                            logger.error("simple_nuevaOrdenCompra_modificacion_nuevo::: " + nuevaOrdenCompra.toString());
                            logger.error(header1 + " // WRITING NEW VERSION OC: " + nuevaOrdenCompra.toString());
                            nuevaOrdenCompra = ordenCompraRepository.saveAndFlush(nuevaOrdenCompra);
                            Integer idOrdenCompra = nuevaOrdenCompra.getId();
                            Integer idTipoOrdenCompra = nuevaOrdenCompra.getIdTipoOrdenCompra();

                            ordenCompraTextoCabeceraSapList.stream()
                                    .filter(octc -> octc.getNumeroOrdenCompra().equals(numeroOrdenCompra))
                                    .forEach(octc -> {
                                        octc.setIdOrdenCompra(idOrdenCompra);

                                        logger.error(header1 + " // WRITING NEW OCTC: " + octc.toString());
                                        ordenCompraTextoCabeceraRepository.save(octc);
                                    });

                            ordenCompraDetalleSapList.stream()
                                    .filter(ocd -> ocd.getNumeroOrdenCompra().equals(numeroOrdenCompra))
                                    .forEach(ocd -> {
                                        ocd.setIdOrdenCompra(idOrdenCompra);
                                        ocd.setTipoPosicion(idTipoOrdenCompra == OrdenCompraTipoEnum.MATERIAL.getId() ? "M" : "S");

                                        BigDecimal cantidadBase = ocd.getPrecioTotal();
                                        BigDecimal precioUnitarioBase = ocd.getPrecioUnitario();
                                        BigDecimal precioUnitario = precioUnitarioBase.divide(cantidadBase, 4, RoundingMode.HALF_UP);

                                        ocd.setPrecioUnitario(precioUnitario);
                                        ocd.setPrecioTotal(ocd.getCantidad().multiply(precioUnitario).setScale(4, RoundingMode.HALF_UP));

//                                            if(ocd.getCodigoSapBienServicio() != null && !ocd.getCodigoSapBienServicio().isEmpty()) {
//                                                ocd.setCodigoSapBienServicio(String.valueOf(Integer.parseInt(ocd.getCodigoSapBienServicio())));
//                                            }

                                        logger.error(header1 + " // WRITING NEW VERSION OCD: " + ocd.toString());
                                        ocd = ordenCompraDetalleRepository.save(ocd);
                                        Integer idOrdenCompraDetalle = ocd.getId();
                                        String posicion = ocd.getPosicion();

                                        ordenCompraDetalleTextoPosicionSapList.stream()
                                                .filter(ocdt -> ocdt.getPosicion().equals(posicion))
                                                .forEach(ocdt -> {
                                                    ocdt.setIdOrdenCompraDetalle(idOrdenCompraDetalle);

                                                    logger.error(header1 + " // WRITING NEW OCDT: " + ocdt.toString());
                                                    ordenCompraDetalleTextoRepository.save(ocdt);
                                                });

                                        ordenCompraDetalleTextoRegistroInfoSapList.stream()
                                                .filter(ocdtri -> ocdtri.getPosicion().equals(posicion))
                                                .forEach(ocdtri -> {
                                                    ocdtri.setIdOrdenCompraDetalle(idOrdenCompraDetalle);
                                                    logger.error(header1 + " // WRITING NEW OCDTRI: " + ocdtri.toString());
                                                    ordenCompraDetalleTextoRegistroInfoRepository.save(ocdtri);
                                                });

                                        ordenCompraDetalleTextoMaterialAmpliadoSapList.stream()
                                                .filter(ocdtma -> ocdtma.getPosicion().equals(posicion))
                                                .forEach(ocdtma -> {
                                                    ocdtma.setIdOrdenCompraDetalle(idOrdenCompraDetalle);
                                                    logger.error(header1 + " // WRITING NEW OCDTMA: " + ocdtma.toString());
                                                    ordenCompraDetalleTextoMaterialAmpliadoRepository.save(ocdtma);
                                                });
                                    });

                            infoMessage.setMessageText2("Se publico una nueva version (" + nuevaOrdenCompra.getVersion() + ") de la Orden de Compra al existir modificaciones. Cantidad de posiciones: " + ordenCompraDetalleSapList.size());

                            if (enviarCorreoPublicacion) {
                                /*Enviando Correo*/
                                Usuario comprador = usuarioRepository.findByCodigoSap(nuevaOrdenCompra.getCompradorUsuarioSap());
                                if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty())
                                    contactoPublicadaOCNotificacion.enviar(nuevaOrdenCompra, null, comprador);

                                Usuario proveedorUsuario = null;
                                logger.error("oc.getProveedorRuc()+ " + nuevaOrdenCompra.getProveedorRuc());
                                if (!nuevaOrdenCompra.getProveedorRuc().equalsIgnoreCase("")) {
                                    IASResponse response = userIASService.getUserByLoginName(nuevaOrdenCompra.getProveedorRuc());
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
                                    contactoPublicadaOCNotificacion.enviar(nuevaOrdenCompra, proveedorUsuario, null);
                            }
                            /*} else {
                                infoMessage.setMessageText2("La version actual (" + ordenCompraAnterior.getVersion() + ") de la Orden de Compra ya esta en estado ANULADA");
                            }*/
                        } else { // llega registro OC bloqueada o anulada
                            ordenCompraAnterior.setEstadoSap(nuevaOrdenCompra.getEstadoSap());
                            ordenCompraAnterior.setFechaModificacion(fechaModNueva);
                            ordenCompraAnterior.setHoraModificacion(horaModNueva);

                            if (nuevaOrdenCompra.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.ANULADA.getCodigo())) {
                                ordenCompraAnterior.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.ANULADA.getId());
                            }

                            logger.error(header1 + " // MOD CURRENT VERSION OC: " + nuevaOrdenCompra.toString());
                            ordenCompraRepository.saveAndFlush(ordenCompraAnterior);
                            infoMessage.setMessageText2("Se modifico la version actual (" + ordenCompraAnterior.getVersion() + ") de la Orden de Compra a un estado NO LIBERADA");
                        }
                    } else {
                        infoMessage.setMessageText2("Se mantuvo la version actual (" + ordenCompraAnterior.getVersion() + ") de la Orden de Compra al no existir modificaciones");
                    }
                }

            } else if (ordenCompraSapList.size() == 0) {
                infoMessage.setMessageCode("ERROR");
                infoMessage.setMessageText1("No se encontro una Orden de Compra valida con el numero :" + numeroOrdenCompra);
            } else {
                infoMessage.setMessageCode("ERROR");
                infoMessage.setMessageText1("Error al buscar la Orden de Compra con el numero :" + numeroOrdenCompra);
            }

            logger.error(header1 + " // FINISHED");
            return infoMessage;
        } catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }


    @Override
    public OrdenCompraSapDataDto extraerDataOneOrdenCompraRFC(String numeroOrdenCompra) throws Exception {
        try {
            String FUNCION_RFC = "ZPE_MM_COMPRAS_DETAIL";

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            this.mapFilters(jCoFunction, numeroOrdenCompra);
            jCoFunction.execute(destination);

            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            OrdenCompraExtractorMapper ordenCompraExtractorMapper = OrdenCompraExtractorMapper.newMapper(exportParameterList);

            List<OrdenCompra> ordenCompraSapList = ordenCompraExtractorMapper.getOrdenCompraList();
            List<OrdenCompra> ordenCompraSapListValidacion = ordenCompraExtractorMapper.getOrdenCompraListValidacionLiberada();
            List<OrdenCompraDetalle> ordenCompraDetalleSapList = ordenCompraExtractorMapper.getOrdenCompraDetalleList();
            List<OrdenCompraTextoCabecera> ordenCompraTextoCabeceraSapList = ordenCompraExtractorMapper.getOrdenCompraTextoCabeceraList();
            List<OrdenCompraDetalleTexto> ordenCompraDetalleTextoPosicionSapList = ordenCompraExtractorMapper.getOrdenCompraDetalleTextoList();
            List<OrdenCompraDetalleTextoRegistroInfo> ordenCompraDetalleTextoRegistroInfoSapList = ordenCompraExtractorMapper.getOrdenCompraDetalleTextoRegistroInfoList();
            List<OrdenCompraDetalleTextoMaterialAmpliado> ordenCompraDetalleTextoMaterialAmpliadoSapList = ordenCompraExtractorMapper.getOrdenCompraDetalleTextoMaterialAmpliadoList();

            OrdenCompraSapDataDto ordenCompraSapDataDto = new OrdenCompraSapDataDto();

            ordenCompraSapDataDto.setOrdenCompraSapList(ordenCompraSapList);
            ordenCompraSapDataDto.setOrdenCompraSapListValidacionLiberada(ordenCompraSapListValidacion);
            ordenCompraSapDataDto.setOrdenCompraDetalleSapList(ordenCompraDetalleSapList);
            ordenCompraSapDataDto.setOrdenCompraTextoCabeceraSapList(ordenCompraTextoCabeceraSapList);
            ordenCompraSapDataDto.setOrdenCompraDetalleTextoPosicionSapList(ordenCompraDetalleTextoPosicionSapList);
            ordenCompraSapDataDto.setOrdenCompraDetalleTextoRegistroInfoSapList(ordenCompraDetalleTextoRegistroInfoSapList);
            ordenCompraSapDataDto.setOrdenCompraDetalleTextoMaterialAmpliadoSapList(ordenCompraDetalleTextoMaterialAmpliadoSapList);

            String header1 = "INI: " + DateUtils.getCurrentTimestamp().toString() + " -- EXTR DATA ONE OC: " + numeroOrdenCompra + " // ";
            logger.error(header1 + "CANTIDAD DE OC ENCONTRADAS: " + ordenCompraSapList.size());
            logger.error(header1 + "CANTIDAD DE OC ENCONTRADAS validacion: " + ordenCompraSapListValidacion.size());
            logger.error(header1 + "ordenCompraSapList :" + ordenCompraSapList.toString());
            logger.error(header1 + "ordenCompraDetalleSapList :" + ordenCompraDetalleSapList.toString());
            logger.error(header1 + "ordenCompraTextoCabeceraSapList :" + ordenCompraTextoCabeceraSapList.toString());
            logger.error(header1 + "ordenCompraDetalleTextoPosicionSapList :" + ordenCompraDetalleTextoPosicionSapList.toString());
            logger.error(header1 + "ordenCompraDetalleTextoRegistroInfoSapList :" + ordenCompraDetalleTextoRegistroInfoSapList.toString());
            logger.error(header1 + "ordenCompraDetalleTextoMaterialAmpliadoSapList :" + ordenCompraDetalleTextoMaterialAmpliadoSapList.toString());

            logger.error(header1 + "FINISHED");
            return ordenCompraSapDataDto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }


    private void mapFilters(JCoFunction function, String numeroOrdenCompra) {
        JCoParameterList paramList = function.getImportParameterList();

        JCoTable jcoTableEBELN = paramList.getTable("I_EBELN");

        if (numeroOrdenCompra != null && !numeroOrdenCompra.isEmpty()) {
            jcoTableEBELN.appendRow();
            jcoTableEBELN.setRow(0);
            jcoTableEBELN.setValue("SIGN", "I");
            jcoTableEBELN.setValue("OPTION", "EQ");
            jcoTableEBELN.setValue("LOW", numeroOrdenCompra);
            jcoTableEBELN.setValue("HIGH", "");
        }
    }


    private boolean evaluarModificacionDeOrdenCompra(Date fechaModAnterior, Time horaModAnterior, Date fechaModNueva, Time horaModNueva) {
        logger.error("FECHA_MOD_ANTERIOR: " + DateUtils.utilDateToString(fechaModAnterior));
        logger.error("HORA_MOD_ANTERIOR: " + horaModAnterior);
        logger.error("FECHA_MOD_NUEVA: " + DateUtils.utilDateToString(fechaModNueva));
        logger.error("HORA_MOD_NUEVA: " + horaModNueva);

        if (fechaModAnterior == null || horaModAnterior == null) {
            if (fechaModNueva == null || horaModNueva == null)
                return false;

            return true;
        }


        LocalDateTime localDateTimeModAnterior = DateUtils.getLocalDateTimeFromDateAndTime(fechaModAnterior, horaModAnterior);
        LocalDateTime localDateTimeModNueva = DateUtils.getLocalDateTimeFromDateAndTime(fechaModNueva, horaModNueva);
        logger.error("LOCAL_DATE_TIME MOD_ANTERIOR: " + localDateTimeModAnterior.toString());
        logger.error("LOCAL_DATE_TIME MOD_NUEVA: " + horaModNueva.toString());

        Integer comparacionDateTime = localDateTimeModNueva.compareTo(localDateTimeModAnterior);
        logger.error("COMPARACION LOCAL_DATE_TIME: " + comparacionDateTime);

        if (comparacionDateTime > 0)
            return true;

        return false;
    }
}