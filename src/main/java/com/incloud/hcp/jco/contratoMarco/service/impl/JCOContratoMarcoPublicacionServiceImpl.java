package com.incloud.hcp.jco.contratoMarco.service.impl;

import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.domain.OrdenCompraDetalle;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.enums.OpcionGenericaEnum;
import com.incloud.hcp.enums.OrdenCompraEstadoEnum;
import com.incloud.hcp.enums.OrdenCompraEstadoSapEnum;
import com.incloud.hcp.enums.OrdenCompraTipoEnum;
import com.incloud.hcp.jco.contratoMarco.service.JCOContratoMarcoPublicacionService;
import com.incloud.hcp.repository.OrdenCompraDetalleRepository;
import com.incloud.hcp.repository.OrdenCompraRepository;
import com.incloud.hcp.repository.UsuarioRepository;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.notificacion.ContactoPublicadaOCNotificacion;
import com.incloud.hcp.util.DateUtils;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOContratoMarcoPublicacionServiceImpl implements JCOContratoMarcoPublicacionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    private final AtomicBoolean ocProcessing = new AtomicBoolean(false);

    private UsuarioRepository usuarioRepository;
    private OrdenCompraRepository ordenCompraRepository;
    private OrdenCompraDetalleRepository ordenCompraDetalleRepository;
    private ContactoPublicadaOCNotificacion contactoPublicadaOCNotificacion;
    private ProveedorService proveedorService;


    @Autowired
    public JCOContratoMarcoPublicacionServiceImpl(UsuarioRepository usuarioRepository,
                                                  OrdenCompraRepository ordenCompraRepository,
                                                  OrdenCompraDetalleRepository ordenCompraDetalleRepository,
                                                  ContactoPublicadaOCNotificacion contactoPublicadaOCNotificacion,
                                                  ProveedorService proveedorService) {
        this.usuarioRepository = usuarioRepository;
        this.ordenCompraRepository = ordenCompraRepository;
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
        this.contactoPublicadaOCNotificacion = contactoPublicadaOCNotificacion;
        this.proveedorService = proveedorService;
    }


    @Override
    public void extraerContratoMarcoListRFC(String fechaInicio, String fechaFin, boolean enviarCorreoPublicacion) throws Exception {
        if(!ocProcessing.get()){
            ocProcessing.set(!ocProcessing.get());
            try {
                String FUNCION_RFC = "ZPE_MM_CONTRATO_MARCO";

                JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
                JCoRepository repository = destination.getRepository();

                JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
                this.mapFilters(jCoFunction, fechaInicio, fechaFin);
                jCoFunction.execute(destination);

                JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
                ContratoMarcoExtractorMapper contratoMarcoExtractorMapper = ContratoMarcoExtractorMapper.newMapper(tableParameterList);

                List<OrdenCompra> contratoMarcoSapList = contratoMarcoExtractorMapper.getContratoMarcoList();
                List<OrdenCompraDetalle> contratoMarcoDetalleSapList = contratoMarcoExtractorMapper.getContratoMarcoDetalleList();

                String header1 = "INI: " + DateUtils.getCurrentTimestamp().toString() + " -- EXTR CM -- RANGO: " + fechaInicio + " - " + fechaFin + " // ";
                logger.error(header1 + "CANTIDAD DE CM ENCONTRADOS: " + contratoMarcoSapList.size());
                logger.error(header1 + "CANTIDAD DE CMD ENCONTRADOS: " + contratoMarcoDetalleSapList.size());

                contratoMarcoSapList.forEach(cm -> {
                    String numContratoMarco = cm.getNumeroOrdenCompra();
                    Optional<OrdenCompra> optionalContratoMarco = ordenCompraRepository.getOrdenCompraActivaByNumero(numContratoMarco);
                    String header2 = header1.concat("CM: " + cm.getNumeroOrdenCompra());

                    if (!optionalContratoMarco.isPresent()){ // CM no existe en HANA
                        if(cm.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.LIBERADA.getCodigo())) { // solo publicar CM si esta en estado liberado
                            cm.setVersion(1); // porque CM es publicada por 1ra vez
                            cm.setIsActive(OpcionGenericaEnum.SI.getCodigo()); // CM es activa porque es la 1ra y unica version
                            cm.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.ACTIVA.getId()); // estado inicial publicada
                            cm.setFechaPublicacion(DateUtils.getCurrentTimestamp());

                            logger.error(header2 + " // WRITING NEW CM: " + cm.toString());
                            logger.error("contratomarco_cm_nuevo_::: " + cm.toString());
                            cm = ordenCompraRepository.save(cm);
                            Integer idContratoMarco = cm.getId();
                            Integer idTipoContratoMarco = cm.getIdTipoOrdenCompra();

                            contratoMarcoDetalleSapList.stream()
                                    .filter(cmd -> cmd.getNumeroOrdenCompra().equals(numContratoMarco))
                                    .forEach(cmd -> {
                                        cmd.setIdOrdenCompra(idContratoMarco);
                                        cmd.setTipoPosicion(idTipoContratoMarco == OrdenCompraTipoEnum.MATERIAL.getId() ? "M" : "S");

                                        BigDecimal cantidadBase = cmd.getPrecioTotal();
                                        BigDecimal precioUnitarioBase = cmd.getPrecioUnitario();
                                        BigDecimal precioUnitario = precioUnitarioBase.divide(cantidadBase, 4, RoundingMode.HALF_UP);

                                        cmd.setPrecioUnitario(precioUnitario);
                                        cmd.setPrecioTotal(cmd.getCantidad().multiply(precioUnitario).setScale(4, RoundingMode.HALF_UP));

                                        logger.error(header2 + " // WRITING NEW CMD: " + cmd.toString());
                                        cmd = ordenCompraDetalleRepository.save(cmd);
//                                        Integer idContratoMarcoDetalle = cmd.getId();
//                                        String posicion = cmd.getPosicion();
                                    });

                            if(enviarCorreoPublicacion) {
                                /*Enviando Correo*/
                                Usuario comprador = usuarioRepository.findByCodigoSap(cm.getCompradorUsuarioSap());
                                if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty())
                                    contactoPublicadaOCNotificacion.enviar(cm, null, comprador);

                                Proveedor proveedor = proveedorService.getProveedorByRuc(cm.getProveedorRuc());
                                Usuario proveedorUsuario = null;
                                if(proveedor != null && proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()){
                                    proveedorUsuario = new Usuario();
                                    logger.error("<--MC_LOG-->:JCOContratoMarcoPublicacionServiceImpl:");
                                    logger.error("<--MC_LOG-->:JCOContratoMarcoPublicacionServiceImpl:"+proveedor.getEmail());
                                    proveedorUsuario.setEmail(proveedor.getEmail());
                                    proveedorUsuario.setApellido(proveedor.getRazonSocial());
                                }
                                else{
                                    List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(cm.getProveedorRuc());
                                    if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                                        proveedorUsuario = posibleProveedorList.get(0);
                                }

                                if(proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                                    contactoPublicadaOCNotificacion.enviar(cm, proveedorUsuario, null);
                            }
                        }
                    }
                    else{ // CM ya existe en HANA
                        OrdenCompra cmAnterior = optionalContratoMarco.get();
                        Date fechaModAnterior = cmAnterior.getFechaModificacion();
                        Time horaModAnterior = cmAnterior.getHoraModificacion();
                        Date fechaModNueva = cm.getFechaModificacion();
                        Time horaModNueva = cm.getHoraModificacion();

                        boolean procede;

                        if(cmAnterior.getIdEstadoOrdenCompra().compareTo(OrdenCompraEstadoEnum.APROBADA.getId()) == 0) {
                            procede = false;
                        }
                        else {
                            // evalua si la fecha y hora de modificacion del nuevo registro es mayor a la del registro existente
                            procede = DateUtils.evaluarModificacionDeDocumento(fechaModAnterior, horaModAnterior, fechaModNueva, horaModNueva);
                        }

                        if (procede) {
                            if (cm.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.LIBERADA.getCodigo())) { // llega registro CM liberada
                                if (cmAnterior.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.BLOQUEADA.getCodigo())
                                        || cmAnterior.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.LIBERADA.getCodigo())) {
                                    cmAnterior.setIsActive(OpcionGenericaEnum.NO.getCodigo()); // la version anterior pasa a inactiva (no se visualizara)
                                    cmAnterior = ordenCompraRepository.save(cmAnterior);
                                    logger.error("contratomarco_cmAnterior_modificacion_::: " + cmAnterior.toString());

                                    cm.setVersion(cmAnterior.getVersion() + 1); // numero de version sgte al actual
                                    cm.setIsActive(OpcionGenericaEnum.SI.getCodigo()); // la ultima version es la unica activa (que se va a visualizar)
                                    cm.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.ACTIVA.getId()); // estado inicial "Activa" (publicada)
                                    cm.setFechaPublicacion(DateUtils.getCurrentTimestamp());
                                    cm.setIdTipoOrdenCompra(cmAnterior.getIdTipoOrdenCompra());
                                    logger.error(header2 + " // WRITING NEXT VERSION CM: " + cm.toString());
                                    logger.error("contratomarco_cm_modificacion_nuevo_::: " + cm.toString());
                                    cm = ordenCompraRepository.save(cm);
                                    Integer idContratoMarco = cm.getId();
                                    Integer idTipoContratoMarco = cm.getIdTipoOrdenCompra();

                                    contratoMarcoDetalleSapList.stream()
                                            .filter(cmd -> cmd.getNumeroOrdenCompra().equals(numContratoMarco))
                                            .forEach(cmd -> {
                                                cmd.setIdOrdenCompra(idContratoMarco);
                                                cmd.setTipoPosicion(idTipoContratoMarco == OrdenCompraTipoEnum.MATERIAL.getId() ? "M" : "S");

                                                BigDecimal cantidadBase = cmd.getPrecioTotal();
                                                BigDecimal precioUnitarioBase = cmd.getPrecioUnitario();
                                                BigDecimal precioUnitario = precioUnitarioBase.divide(cantidadBase, 4, RoundingMode.HALF_UP);

                                                cmd.setPrecioUnitario(precioUnitario);
                                                cmd.setPrecioTotal(cmd.getCantidad().multiply(precioUnitario).setScale(4, RoundingMode.HALF_UP));

//                                                if(cmd.getCodigoSapBienServicio() != null && !cmd.getCodigoSapBienServicio().isEmpty()) {
//                                                    cmd.setCodigoSapBienServicio(String.valueOf(Integer.parseInt(cmd.getCodigoSapBienServicio())));
//                                                }

                                                logger.error(header2 + " // WRITING NEXT VERSION OCD: " + cmd.toString());
                                                cmd = ordenCompraDetalleRepository.save(cmd);
                                                Integer idContratoMarcoDetalle = cmd.getId();
                                                String posicion = cmd.getPosicion();
                                            });

                                    if(enviarCorreoPublicacion) {
                                        /*Enviando Correo*/
                                        Usuario comprador = usuarioRepository.findByCodigoSap(cm.getCompradorUsuarioSap());
                                        if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty())
                                            contactoPublicadaOCNotificacion.enviar(cm, null, comprador);

                                        Proveedor proveedor = proveedorService.getProveedorByRuc(cm.getProveedorRuc());
                                        Usuario proveedorUsuario = null;
                                        if(proveedor != null && proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()){
                                            proveedorUsuario = new Usuario();
                                            logger.error("<--MC_LOG-->:JCOContratoMarcoPublicacionServiceImpl-extraerContratoMarcoListRFC:");
                                            logger.error("<--MC_LOG-->:JCOContratoMarcoPublicacionServiceImpl-extraerContratoMarcoListRFC:"+proveedor.getEmail());
                                            proveedorUsuario.setEmail(proveedor.getEmail());
                                            proveedorUsuario.setApellido(proveedor.getRazonSocial());
                                        }
                                        else{
                                            List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(cm.getProveedorRuc());
                                            if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                                                proveedorUsuario = posibleProveedorList.get(0);
                                        }

                                        if(proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                                            contactoPublicadaOCNotificacion.enviar(cm, proveedorUsuario, null);
                                    }
                                }
                            }
                            else{ // llega registro CM bloqueada o anulada
                                cmAnterior.setEstadoSap(cm.getEstadoSap());
                                cmAnterior.setFechaModificacion(fechaModNueva);
                                cmAnterior.setHoraModificacion(horaModNueva);

                                if(cm.getEstadoSap().equalsIgnoreCase(OrdenCompraEstadoSapEnum.ANULADA.getCodigo())){
                                    cmAnterior.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.ANULADA.getId());
                                }

                                logger.error(header2 + " // MOD CURRENT VERSION CM: " + cmAnterior.toString());
                                ordenCompraRepository.save(cmAnterior);
                            }
                        }
                    }
                });
                logger.error(header1 + "FINISHED");

                if(ocProcessing.get())
                    ocProcessing.set(!ocProcessing.get());
            }
            catch (Exception e) {
                if(ocProcessing.get())
                    ocProcessing.set(!ocProcessing.get());
                logger.error(e.getMessage(), e.getCause());
                throw new Exception(e);
            }
        }
        else{
            logger.error("INI: " + DateUtils.getCurrentTimestamp().toString() + " // ContratoMarco Extraction esta procesando");
        }
    }


    private void mapFilters(JCoFunction function, String fechaInicio, String fechaFin) {
        JCoParameterList paramList = function.getImportParameterList();

        if (fechaInicio != null && !fechaInicio.isEmpty() && fechaFin != null && !fechaFin.isEmpty()){
            JCoTable jcoTableAEDAT = paramList.getTable("IT_AEDAT");
            jcoTableAEDAT.appendRow();
            jcoTableAEDAT.setRow(0);
            jcoTableAEDAT.setValue("SIGN", "I");
            jcoTableAEDAT.setValue("LOW", fechaInicio);

            if (fechaInicio.equals(fechaFin)) {
                jcoTableAEDAT.setValue("OPTION", "EQ");
            } else {
                jcoTableAEDAT.setValue("OPTION", "BT");
                jcoTableAEDAT.setValue("HIGH", fechaFin);
            }
        }
    }


    public boolean toggleContratoMarcoExtractionProcessingState() {
        ocProcessing.set(!ocProcessing.get());
        return ocProcessing.get();
    }


    public boolean currentContratoMarcoExtractionProcessingState() {
        return ocProcessing.get();
    }

}
