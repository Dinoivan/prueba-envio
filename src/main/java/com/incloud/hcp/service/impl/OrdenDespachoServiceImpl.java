package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.MensajeBean;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.domain.almacen.OrdenDespacho;
import com.incloud.hcp.domain.almacen.OrdenDespachoDetalle;
import com.incloud.hcp.dto.FiltroOrdenCompraDto;
import com.incloud.hcp.dto.OrdenDespachoRespuestaDto;
import com.incloud.hcp.enums.OrdenCompraEstadoEnum;
import com.incloud.hcp.jco.contratoMarco.dto.ContratoMarcoPdfSapDto;
import com.incloud.hcp.jco.contratoMarco.service.JCOContratoMarcoPdfService;
import com.incloud.hcp.jco.contratoMarco.service.JCOContratoMarcoPublicacionService;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPdfDto;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPosicionPdfDto;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenCompraPdfService;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenDespachoPublicacionService;
import com.incloud.hcp.pdf.PdfGeneratorFactory;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.service.OrdenDespachoDetalleService;
import com.incloud.hcp.service.OrdenDespachoService;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.notificacion.ContactoAprobadaRechazadaODNotificacion;
import com.incloud.hcp.service.notificacion.ContactoVisualizadoODNotificacion;
import com.incloud.hcp.service.notificacion.ReactivacionOrdenDespachoNotificacion;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class OrdenDespachoServiceImpl implements OrdenDespachoService {

    private OrdenDespachoRepository ordenDespachoRepository;
    private ContactoVisualizadoODNotificacion contactoVisualizadoODNotificacion;
    private ContactoAprobadaRechazadaODNotificacion contactoAprobadaRechazadaODNotificacion;
    private ProveedorRepository proveedorRepository;
    private UsuarioRepository usuarioRepository;
    private JCOOrdenDespachoPublicacionService jcoOrdenDespachoPublicacionService;
    private JCOContratoMarcoPublicacionService jcoContratoMarcoPublicacionService;
    private JCOOrdenCompraPdfService jcoOrdenCompraPdfService;
    private OrdenDespachoDetalleService ordenDespachoDetalleService;
    private JCOContratoMarcoPdfService jcoContratoMarcoPdfService;
    private ProveedorService proveedorService;
    private EstadoOrdenCompraRepository estadoOrdenCompraRepository;
    private ReactivacionOrdenDespachoNotificacion reactivacionOrdenDespachoNotificacion;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrdenDespachoServiceImpl(OrdenDespachoRepository ordenDespachoRepository,
                                    ContactoVisualizadoODNotificacion contactoVisualizadoODNotificacion,
                                    ContactoAprobadaRechazadaODNotificacion contactoAprobadaRechazadaODNotificacion,
                                    ProveedorRepository proveedorRepository,
                                    UsuarioRepository usuarioRepository,
                                    JCOOrdenDespachoPublicacionService jcoOrdenDespachoPublicacionService,
                                    JCOContratoMarcoPublicacionService jcoContratoMarcoPublicacionService,
                                    JCOOrdenCompraPdfService jcoOrdenCompraPdfService,
                                    OrdenDespachoDetalleService ordenDespachoDetalleService,
                                    JCOContratoMarcoPdfService jcoContratoMarcoPdfService,
                                    ProveedorService proveedorService,
                                    EstadoOrdenCompraRepository estadoOrdenCompraRepository,
                                    ReactivacionOrdenDespachoNotificacion reactivacionOrdenDespachoNotificacion) {
        this.ordenDespachoRepository = ordenDespachoRepository;
        this.contactoVisualizadoODNotificacion = contactoVisualizadoODNotificacion;
        this.contactoAprobadaRechazadaODNotificacion = contactoAprobadaRechazadaODNotificacion;
        this.proveedorRepository = proveedorRepository;
        this.usuarioRepository = usuarioRepository;
        this.jcoOrdenDespachoPublicacionService = jcoOrdenDespachoPublicacionService;
        this.jcoContratoMarcoPublicacionService = jcoContratoMarcoPublicacionService;
        this.jcoOrdenCompraPdfService = jcoOrdenCompraPdfService;
        this.ordenDespachoDetalleService = ordenDespachoDetalleService;
        this.jcoContratoMarcoPdfService = jcoContratoMarcoPdfService;
        this.proveedorService = proveedorService;
        this.estadoOrdenCompraRepository = estadoOrdenCompraRepository;
        this.reactivacionOrdenDespachoNotificacion = reactivacionOrdenDespachoNotificacion;
    }

    @Override
    public List<OrdenDespacho> getAllOrdenDespacho() {
        return ordenDespachoRepository.getAllActive();
    }

    @Override
    public OrdenDespacho getOrdenDespachoById(Integer idOrdenDespacho) {
        return ordenDespachoRepository.getOrdenDespachoById(idOrdenDespacho);
    }


    @Override
    public List<OrdenDespacho> getOrdenDespachoList(FiltroOrdenCompraDto dto) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaInicio = dto.getFechaInicio();
        Date fechaFin = dto.getFechaFin();
        Date fechaInicioPublicacion = dto.getFechaInicioPublicacion();
        Date fechaFinPublicacion = dto.getFechaFinPublicacion();

        logger.error("_fechaInicio " + fechaInicio);
        logger.error("_fechaFin " + fechaFin);
        logger.error("_fechaInicioPublicacion " + fechaInicioPublicacion);
        logger.error("_fechaFinPublicacion " + fechaFinPublicacion);

       /* try {
            fechaInicio = df.parse(dto.getFechaInicio());
            fechaFin = df.parse(dto.getFechaInicio());

            if(StringUtils.isBlank(dto.getFechaInicioPublicacion())){
                fechaInicioPublicacion =df.parse("01/01/1970");
            }else {
                fechaInicioPublicacion = df.parse(dto.getFechaInicioPublicacion());
            }

            if(StringUtils.isBlank(dto.getFechaFinPublicacion())){
                fechaFinPublicacion = df.parse("01/01/9999");
            }else {
                fechaFinPublicacion = df.parse(dto.getFechaFinPublicacion());
            }



        }catch(Exception e) {

        }*/

        String ruc = dto.getRuc();
        if (ruc == null || ruc.isEmpty()) {
            logger.error("if1");
            return ordenDespachoRepository.getOrdenDespachoByFechaRegistroBetween2(fechaInicio, fechaFin, fechaInicioPublicacion, fechaFinPublicacion);
        } else {
            logger.error("else");
            return ordenDespachoRepository.getOrdenDespachoByFechaRegistroBetweenAndProveedorRuc2(fechaInicio, fechaFin, ruc, fechaInicioPublicacion, fechaFinPublicacion);
        }

        //return null;
    }

    @Override
    public List<OrdenDespacho> getOrdenDespachoListPorFechasAndRuc(Date fechaInicio, Date fechaFin, String ruc) {

        if (ruc == null || ruc.isEmpty()) {
            return ordenDespachoRepository.getOrdenDespachoByFechaRegistroBetween(fechaInicio, fechaFin);
        } else {
            return ordenDespachoRepository.getOrdenDespachoByFechaRegistroBetweenAndProveedorRuc(fechaInicio, fechaFin, ruc);
        }
    }
    @Override
    public List<OrdenDespacho> getOrdenDespachoListPorEstadoFechasOrdenDespacho(String ordenDespacho, Integer idEstado, Date fechaInicio, Date fechaFin) {
        return ordenDespachoRepository.getOrdenDespachoByFechaRegistroEstadoBetweenOrdenDespacho(ordenDespacho,idEstado, fechaInicio, fechaFin);
    }
    @Override
    public List<OrdenDespacho> getOrdenDespachoListPorEstadoFechas(Integer idEstado, Date fechaInicio, Date fechaFin) {


            return ordenDespachoRepository.getOrdenDespachoByFechaRegistroEstadoBetween(idEstado, fechaInicio, fechaFin);

    }

    public MensajeBean reactivarOrdenDespacho(Integer idOrdenDespacho, Integer idEstado) {
        MensajeBean msg = new MensajeBean();
        Optional<OrdenDespacho> ordenDespachoOpt = this.ordenDespachoRepository.findById(idOrdenDespacho);
        if(ordenDespachoOpt.isPresent()) {
            Optional<EstadoOrdenCompra> estadoOrdenCompraOpt =this.estadoOrdenCompraRepository.findById(idEstado);
            if(estadoOrdenCompraOpt.isPresent()) {
                  OrdenDespacho ordenDespacho = ordenDespachoOpt.get();
                  ordenDespacho.setEstadoOrdenCompra(estadoOrdenCompraOpt.get());
                ordenDespacho.setIdEstadoOrdenCompra(idEstado);
                OrdenDespacho out = this.ordenDespachoRepository.save(ordenDespacho);
                //Enviar Correo
                Proveedor proveedorSap = this.proveedorRepository.getProveedorByAcreedorCodigoSap(ordenDespacho.getProveedorCodigoSap());
                logger.error("OrdenCompraServiceImpl ::: proveedorSap ::: " + proveedorSap);
                if(proveedorSap != null) {
                    this.reactivacionOrdenDespachoNotificacion.enviar(out, estadoOrdenCompraOpt.get(), proveedorSap);
                }

                msg.setType("S");
                msg.setMensaje("Se actualizó correctamente la orden de Compra");

            }else {
                msg.setType("E");
                msg.setMensaje("No existe Estado Orden de Compra");
            }
        }else {
            msg.setType("E");
            msg.setMensaje("No existe Orden de Compra");
        }

        return msg;
    }

    @Override
    @Transactional
    /* En este metodo se guarda la fecha de actualización cuando se abre por primera vez la orden de compra
     * Además, se cambia de estado de orden de compra a '2: Visualiza'*/
    public OrdenDespachoRespuestaDto updateOrdenDespachoFechaVisualizacion(Integer idOrdenCompra) {
        OrdenDespachoRespuestaDto ordenDespachoRespuestaDto = new OrdenDespachoRespuestaDto();
        List<String> mensajes = new ArrayList<>();
        Optional<OrdenDespacho> ordenCompraOptional = ordenDespachoRepository.findByIdAndIsActive(idOrdenCompra);
        OrdenDespacho ordenDespacho;

        if (ordenCompraOptional.isPresent()) {
            ordenDespacho = ordenCompraOptional.get();

            if (ordenDespacho.getFechaVisualizacion() == null) {
                ordenDespacho.setFechaVisualizacion(DateUtils.getCurrentTimestamp());

                if (ordenDespacho.getIdEstadoOrdenCompra().compareTo(OrdenCompraEstadoEnum.ACTIVA.getId()) == 0) {
                    ordenDespacho.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.VISUALIZADA.getId());
                }
                ordenDespacho = ordenDespachoRepository.save(ordenDespacho);
                ordenDespachoRespuestaDto.setOrdenDespacho(ordenDespacho);

                /*Enviando Correo*/
                Usuario comprador = usuarioRepository.findByCodigoSap(ordenDespacho.getCompradorUsuarioSap());
                if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty())
                    contactoVisualizadoODNotificacion.enviar(ordenDespacho, null, comprador);
                else
                    mensajes.add("Error al obtener los datos del comprador para envio de correo.");

                Proveedor proveedor = proveedorService.getProveedorByRuc(ordenDespacho.getProveedorRuc());
                Usuario proveedorUsuario = null;
                if(proveedor != null && proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()){
                    proveedorUsuario = new Usuario();
                    logger.error("<--MC_LOG-->:OrdenCompraServiceImpl-updateOrdenCompraFechaVisualizacion:");
                    logger.error("<--MC_LOG-->:OrdenCompraServiceImpl-updateOrdenCompraFechaVisualizacion:"+proveedor.getEmail());
                    proveedorUsuario.setEmail(proveedor.getEmail());
                    proveedorUsuario.setApellido(proveedor.getRazonSocial());
                }
                else{
                    List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(ordenDespacho.getProveedorRuc());
                    if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                        proveedorUsuario = posibleProveedorList.get(0);
                }

                if(proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                    contactoVisualizadoODNotificacion.enviar(ordenDespacho, proveedorUsuario, null);
                else
                    mensajes.add("Error al obtener los datos del proveedor para envio de correo.");

                ordenDespachoRespuestaDto.setMensajes(mensajes);
            }
        }
        return ordenDespachoRespuestaDto;
    }

    @Override
    public OrdenDespachoRespuestaDto aprobarRechazarOrdenDespacho(Integer idOrdenCompra, int estado, String textoRechazo) {
        OrdenDespachoRespuestaDto ordenDespachoRespuestaDto = new OrdenDespachoRespuestaDto();
        Optional<OrdenDespacho> ordenDespachoOptional = ordenDespachoRepository.findByIdAndIsActive(idOrdenCompra);
        OrdenDespacho ordenDespacho;
        List<String> mensajes = new ArrayList<>();

        if (ordenDespachoOptional.isPresent()) {
            ordenDespacho = ordenDespachoOptional.get();

            ordenDespacho.setIdEstadoOrdenCompra(estado);
            ordenDespacho.setFechaAprobacion(DateUtils.getCurrentTimestamp());

            if (estado == OrdenCompraEstadoEnum.RECHAZADA.getId()) {
                ordenDespacho.setMotivoRechazo(textoRechazo);
            }
            logger.error("OC " +  ordenDespacho.getNumeroOrdenCompra() +  " APROB/RECHAZO estado (antes) :" + ordenDespacho.getIdEstadoOrdenCompra());
            ordenDespacho = ordenDespachoRepository.save(ordenDespacho);
            logger.error("OC " +  ordenDespacho.getNumeroOrdenCompra() +  " APROB/RECHAZO estado (despues) :" + ordenDespacho.getIdEstadoOrdenCompra());
            ordenDespachoRespuestaDto.setOrdenDespacho(ordenDespacho);


            /*Enviando Correo*/
            Usuario comprador = usuarioRepository.findByCodigoSap(ordenDespacho.getCompradorUsuarioSap());
            if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty()) {
                logger.error("<--MC_LOG-->:correo-comprador:"+ comprador.getEmail());
                contactoAprobadaRechazadaODNotificacion.enviar(ordenDespacho, null, comprador);
            }else {
                mensajes.add("Error al obtener los datos del comprador para envio de correo de aprobacion de orden de compra.");
            }

            Proveedor proveedor = proveedorService.getProveedorByRuc(ordenDespacho.getProveedorRuc());
            Usuario proveedorUsuario = null;
            if(proveedor != null && proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()){
                proveedorUsuario = new Usuario();
                logger.error("<--MC_LOG-->:OrdenCompraServiceImpl-aprobarRechazarOrdenCompra:");
                logger.error("<--MC_LOG-->:OrdenCompraServiceImpl-aprobarRechazarOrdenCompra:"+proveedor.getEmail());
                proveedorUsuario.setEmail(proveedor.getEmail());
                proveedorUsuario.setApellido(proveedor.getRazonSocial());
            }
            else{
                List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(ordenDespacho.getProveedorRuc());
                if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                    proveedorUsuario = posibleProveedorList.get(0);
            }

            if(proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                contactoAprobadaRechazadaODNotificacion.enviar(ordenDespacho, proveedorUsuario, null);
            else
                mensajes.add("Error al obtener los datos del proveedor para envio de correo de aprobacion de orden de compra.");

            ordenDespachoRespuestaDto.setMensajes(mensajes);
        }
        return ordenDespachoRespuestaDto;
    }


    @Override
    public void extraerOrdenDespachoMasivoByRangoFechas(Date fechaInicio, Date fechaFin, boolean enviarCorreoPublicacion){
        LocalDate currentlyExtractLocalDate = DateUtils.utilDateToLocalDate(fechaInicio);
        LocalDate finalLocalDate = DateUtils.utilDateToLocalDate(fechaFin);
        logger.error("EXTRACCION ORDEN_COMPRA MASIVA - FECHA INICIO: " + currentlyExtractLocalDate.toString());
        logger.error("EXTRACCION ORDEN_COMPRA MASIVA - FECHA FIN: " + finalLocalDate.toString());

        while (currentlyExtractLocalDate.isBefore(finalLocalDate.plusDays(1))){
            try {
                String currentDateAsSapString = DateUtils.localDateToSapString(currentlyExtractLocalDate);
                jcoOrdenDespachoPublicacionService.extraerOrdenDespachoListRFC(currentDateAsSapString, currentDateAsSapString, enviarCorreoPublicacion);
                currentlyExtractLocalDate = currentlyExtractLocalDate.plusDays(1);
            }
            catch(Exception e){
                String error = Utils.obtieneMensajeErrorException(e);
                logger.error("ERROR al extraer ordenes de compra de la fecha " + DateUtils.localDateToString(currentlyExtractLocalDate) + " : " + error);
                currentlyExtractLocalDate = currentlyExtractLocalDate.plusDays(1);
            }
        }
    }


    @Override
    public void extraerContratoMarcoMasivoByRangoFechas(Date fechaInicio, Date fechaFin, boolean enviarCorreoPublicacion){
        LocalDate currentlyExtractLocalDate = DateUtils.utilDateToLocalDate(fechaInicio);
        LocalDate finalLocalDate = DateUtils.utilDateToLocalDate(fechaFin);
        logger.error("EXTRACCION CONTRATO_MARCO MASIVA - FECHA INICIO: " + currentlyExtractLocalDate.toString());
        logger.error("EXTRACCION CONTRATO_MARCO MASIVA - FECHA FIN: " + finalLocalDate.toString());

        while (currentlyExtractLocalDate.isBefore(finalLocalDate.plusDays(1))){
            try {
                String currentDateAsSapString = DateUtils.localDateToSapString(currentlyExtractLocalDate);
                jcoContratoMarcoPublicacionService.extraerContratoMarcoListRFC(currentDateAsSapString, currentDateAsSapString, enviarCorreoPublicacion);
                currentlyExtractLocalDate = currentlyExtractLocalDate.plusDays(1);
            }
            catch(Exception e){
                String error = Utils.obtieneMensajeErrorException(e);
                logger.error("ERROR al extraer contratos marco de la fecha " + DateUtils.localDateToString(currentlyExtractLocalDate) + " : " + error);
                currentlyExtractLocalDate = currentlyExtractLocalDate.plusDays(1);
            }
        }
    }


    @Override
    public String getOrdenDespachoPdfContent(String numeroOrdenCompra) throws Exception{
        OrdenCompraPdfDto ordenCompraPdfDto = jcoOrdenCompraPdfService.extraerOrdenCompraPdfDtoRFC(numeroOrdenCompra);
        byte[] generateOrdenCompraBytes = PdfGeneratorFactory.getJasperGenerator().generateOrdenCompraPdfBytes(ordenCompraPdfDto);
        return Base64.getEncoder().encodeToString(generateOrdenCompraBytes);
    }


    @Override
    public String getContratoMarcoPdfContent(String numeroContratoMarco) throws Exception{
        OrdenCompraPdfDto contratoMarcoPdfDto = new OrdenCompraPdfDto();
        Optional<OrdenDespacho> optionalContratoMarco = ordenDespachoRepository.getOrdenDespachoActivaByNumero(numeroContratoMarco);

        if(optionalContratoMarco.isPresent()){
            OrdenDespacho contratoMarco = optionalContratoMarco.get();
            ContratoMarcoPdfSapDto contratoMarcoPdfSapDto = jcoContratoMarcoPdfService.extraerContratoMarcoPdfDtoRFC(numeroContratoMarco);


            contratoMarcoPdfDto.setOrdenCompraNumero(contratoMarco.getNumeroOrdenCompra());
            contratoMarcoPdfDto.setOrdenCompraTipo(contratoMarco.getTipoOrdenCompra().getDescripcion());
            contratoMarcoPdfDto.setOrdenCompraVersion(String.valueOf(contratoMarco.getVersion()));
            contratoMarcoPdfDto.setOrdenCompraFechaCreacion(contratoMarco.getFechaRegistro());
            contratoMarcoPdfDto.setOrdenCompraFormaPago(contratoMarco.getCondicionPagoDescripcion());
            contratoMarcoPdfDto.setOrdenCompraMoneda(contratoMarco.getCodigoMondeda());
//            contratoMarcoPdfDto.setOrdenCompraAutorizador(contratoMarco.getAutorizadorFechaLiberacion());
            contratoMarcoPdfDto.setOrdenCompraAutorizador(contratoMarcoPdfSapDto.getClienteAutorizador());
            contratoMarcoPdfDto.setOrdenCompraPersonaContacto(contratoMarcoPdfSapDto.getClientePersonaContacto());

            contratoMarcoPdfDto.setClienteRuc(contratoMarco.getInfoSociedad().getRuc());
            contratoMarcoPdfDto.setClienteTelefono(contratoMarco.getInfoSociedad().getTelefono());
            contratoMarcoPdfDto.setClienteDireccion(contratoMarco.getInfoSociedad().getDireccionFiscal());
            contratoMarcoPdfDto.setClienteRazonSocial(contratoMarco.getInfoSociedad().getRazonSocial());

            contratoMarcoPdfDto.setProveedorRazonSocial(contratoMarco.getProveedorRazonSocial());
            contratoMarcoPdfDto.setProveedorRuc(contratoMarco.getProveedorRuc());
            if(contratoMarco.getProveedorCodigoSap() != null && !contratoMarco.getProveedorCodigoSap().isEmpty())
                contratoMarcoPdfDto.setProveedorNumero(String.valueOf(Integer.parseInt(contratoMarco.getProveedorCodigoSap())));
            contratoMarcoPdfDto.setProveedorDireccion(contratoMarcoPdfSapDto.getProveedorDireccion());
            contratoMarcoPdfDto.setProveedorContactoNombre(contratoMarcoPdfSapDto.getProveedorContactoNombre());
            contratoMarcoPdfDto.setProveedorContactoTelefono(contratoMarcoPdfSapDto.getProveedorContactoTelefono());

//            contratoMarcoPdfDto.setMontoSubtotal();
//            contratoMarcoPdfDto.setMontoDescuento();
//            contratoMarcoPdfDto.setMontoIgv();
            contratoMarcoPdfDto.setMontoImporteTotal(contratoMarco.getTotal());

            List<OrdenDespachoDetalle> ordenDespachoetalleList = ordenDespachoDetalleService.getOrdenDespachoDetalleListByIdOc(contratoMarco.getId());
            List<OrdenCompraPosicionPdfDto> posicionPdfDtoList = new ArrayList<>();

            ordenDespachoetalleList.forEach(ocd -> {
                OrdenCompraPosicionPdfDto posicionPdfDto = new OrdenCompraPosicionPdfDto();

                if(ocd.getPosicion() != null && !ocd.getPosicion().isEmpty())
                    posicionPdfDto.setPosicion(String.valueOf(Integer.parseInt(ocd.getPosicion())));
                posicionPdfDto.setCentro(ocd.getDenominacionCentro());
                if(ocd.getCodigoSapBienServicio() != null && !ocd.getCodigoSapBienServicio().isEmpty())
                    posicionPdfDto.setMaterial(String.valueOf(Integer.parseInt(ocd.getCodigoSapBienServicio())));
                posicionPdfDto.setDescripcion(ocd.getDescripcionBienServicio());
                posicionPdfDto.setCantidad(ocd.getCantidad());
                posicionPdfDto.setUnidad(ocd.getUnidadMedidaBienServicio());
                posicionPdfDto.setFechaEntrega(ocd.getFechaEntrega());
                posicionPdfDto.setPrecioUnitario(ocd.getPrecioUnitario());
                posicionPdfDto.setImporte(ocd.getPrecioTotal());

                posicionPdfDtoList.add(posicionPdfDto);
            });

            contratoMarcoPdfDto.setOrdenCompraPosicionPdfDtoList(posicionPdfDtoList);
        }
        else{
            return null;
        }

        byte[] generateContratoMarcoBytes = PdfGeneratorFactory.getJasperGenerator().generateContratoMarcoPdfBytes(contratoMarcoPdfDto);
        return Base64.getEncoder().encodeToString(generateContratoMarcoBytes);
    }

    @Override
    public OrdenDespacho getOrdenDespachoByNOCompra(String nOrdenCompra) {
        return ordenDespachoRepository.getOrdenDespachoByNOCompra(nOrdenCompra);
    }
    @Override
    public List<HashMap<String,String>> cambioEstadoOCRechazada(List<String> numeroOrdenCompra){
        logger.error("OC Rechazada: "+ numeroOrdenCompra);
        List<HashMap<String,String>> output = new ArrayList<>();

       for(String n : numeroOrdenCompra){
           HashMap<String,String> map = new HashMap<>();
           OrdenDespacho od= ordenDespachoRepository.getOrdenDespachoRechazada(n);
           if(od!=null) {
               od.setIdEstadoOrdenCompra(1);
               ordenDespachoRepository.save(od);
              map.put("numeroOrdenCompra",n);
              map.put("mensaje","La Orden de compra "+ n +" ha sido actualizada.");


           }else{

               map.put("numeroOrdenCompra",n);
               map.put("mensaje","La Orden de compra "+ n +"no ha sido modificada al no estar Rechazada.");
           }
           output.add(map);

       }
        return output;

    }
}