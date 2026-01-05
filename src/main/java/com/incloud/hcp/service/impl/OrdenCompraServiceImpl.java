package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.MensajeBean;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.dto.FiltroOrdenCompraDto;
import com.incloud.hcp.dto.OrdenCompraRespuestaDto;
import com.incloud.hcp.enums.OrdenCompraEstadoEnum;
import com.incloud.hcp.jco.contratoMarco.dto.ContratoMarcoPdfSapDto;
import com.incloud.hcp.jco.contratoMarco.service.JCOContratoMarcoPdfService;
import com.incloud.hcp.jco.contratoMarco.service.JCOContratoMarcoPublicacionService;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPdfDto;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPosicionPdfDto;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenCompraPdfService;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenCompraPublicacionService;
import com.incloud.hcp.pdf.PdfGeneratorFactory;
import com.incloud.hcp.repository.EstadoOrdenCompraRepository;
import com.incloud.hcp.repository.OrdenCompraRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.repository.UsuarioRepository;
import com.incloud.hcp.service.OrdenCompraDetalleService;
import com.incloud.hcp.service.OrdenCompraService;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.notificacion.ContactoAprobadaRechazadaOCNotificacion;
import com.incloud.hcp.service.notificacion.ContactoVisualizadoOCNotificacion;
import com.incloud.hcp.service.notificacion.ReactivacionOrdenCompraNotificacion;
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
public class OrdenCompraServiceImpl implements OrdenCompraService {

    private OrdenCompraRepository ordenCompraRepository;
    private ContactoVisualizadoOCNotificacion contactoVisualizadoOCNotificacion;
    private ContactoAprobadaRechazadaOCNotificacion contactoAprobadaRechazadaOCNotificacion;
    private ProveedorRepository proveedorRepository;
    private UsuarioRepository usuarioRepository;
    private JCOOrdenCompraPublicacionService jcoOrdenCompraPublicacionService;
    private JCOContratoMarcoPublicacionService jcoContratoMarcoPublicacionService;
    private JCOOrdenCompraPdfService jcoOrdenCompraPdfService;
    private OrdenCompraDetalleService ordenCompraDetalleService;
    private JCOContratoMarcoPdfService jcoContratoMarcoPdfService;
    private ProveedorService proveedorService;
    private EstadoOrdenCompraRepository estadoOrdenCompraRepository;
    private ReactivacionOrdenCompraNotificacion reactivacionOrdenCompraNotificacion;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrdenCompraServiceImpl(OrdenCompraRepository ordenCompraRepository,
                                  ContactoVisualizadoOCNotificacion contactoVisualizadoOCNotificacion,
                                  ContactoAprobadaRechazadaOCNotificacion contactoAprobadaRechazadaOCNotificacion,
                                  ProveedorRepository proveedorRepository,
                                  UsuarioRepository usuarioRepository,
                                  JCOOrdenCompraPublicacionService jcoOrdenCompraPublicacionService,
                                  JCOContratoMarcoPublicacionService jcoContratoMarcoPublicacionService,
                                  JCOOrdenCompraPdfService jcoOrdenCompraPdfService,
                                  OrdenCompraDetalleService ordenCompraDetalleService,
                                  JCOContratoMarcoPdfService jcoContratoMarcoPdfService,
                                  ProveedorService proveedorService,
                                  EstadoOrdenCompraRepository estadoOrdenCompraRepository,
                                  ReactivacionOrdenCompraNotificacion reactivacionOrdenCompraNotificacion) {
        this.ordenCompraRepository = ordenCompraRepository;
        this.contactoVisualizadoOCNotificacion = contactoVisualizadoOCNotificacion;
        this.contactoAprobadaRechazadaOCNotificacion = contactoAprobadaRechazadaOCNotificacion;
        this.proveedorRepository = proveedorRepository;
        this.usuarioRepository = usuarioRepository;
        this.jcoOrdenCompraPublicacionService = jcoOrdenCompraPublicacionService;
        this.jcoContratoMarcoPublicacionService = jcoContratoMarcoPublicacionService;
        this.jcoOrdenCompraPdfService = jcoOrdenCompraPdfService;
        this.ordenCompraDetalleService = ordenCompraDetalleService;
        this.jcoContratoMarcoPdfService = jcoContratoMarcoPdfService;
        this.proveedorService = proveedorService;
        this.estadoOrdenCompraRepository = estadoOrdenCompraRepository;
        this.reactivacionOrdenCompraNotificacion = reactivacionOrdenCompraNotificacion;
    }

    @Override
    public List<OrdenCompra> getAllOrdenCompra() {
        return ordenCompraRepository.getAllActive();
    }

    @Override
    public OrdenCompra getOrdenCompraById(Integer idOrdenCompra) {
        return ordenCompraRepository.getOrdenCompraById(idOrdenCompra);
    }


    @Override
    public List<OrdenCompra> getOrdenCompraList(FiltroOrdenCompraDto dto) {
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
            return ordenCompraRepository.getOrdenCompraByFechaRegistroBetween2(fechaInicio, fechaFin, fechaInicioPublicacion, fechaFinPublicacion);
        } else {
            logger.error("else");
            return ordenCompraRepository.getOrdenCompraByFechaRegistroBetweenAndProveedorRuc2(fechaInicio, fechaFin, ruc, fechaInicioPublicacion, fechaFinPublicacion);
        }

        //return null;
    }

    @Override
    public List<OrdenCompra> getOrdenCompraListPorFechasAndRuc(Date fechaInicio, Date fechaFin, String ruc) {

        if (ruc == null || ruc.isEmpty()) {
            return ordenCompraRepository.getOrdenCompraByFechaRegistroBetween(fechaInicio, fechaFin);
        } else {
            return ordenCompraRepository.getOrdenCompraByFechaRegistroBetweenAndProveedorRuc(fechaInicio, fechaFin, ruc);
        }
    }
    @Override
    public List<OrdenCompra> getOrdenCompraListPorEstadoFechasOrdenCompra(String ordenCompra, Integer idEstado, Date fechaInicio, Date fechaFin) {
        return ordenCompraRepository.getOrdenCompraByFechaRegistroEstadoBetweenOrdenCompra(ordenCompra,idEstado, fechaInicio, fechaFin);
    }
    @Override
    public List<OrdenCompra> getOrdenCompraListPorEstadoFechas(Integer idEstado, Date fechaInicio, Date fechaFin) {


            return ordenCompraRepository.getOrdenCompraByFechaRegistroEstadoBetween(idEstado, fechaInicio, fechaFin);

    }

    public MensajeBean reactivarOrdenCompra(Integer idOrdenCompra, Integer idEstado) {
        MensajeBean msg = new MensajeBean();
        Optional<OrdenCompra> ordenCompraOpt = this.ordenCompraRepository.findById(idOrdenCompra);
        if(ordenCompraOpt.isPresent()) {
            Optional<EstadoOrdenCompra> estadoOrdenCompraOpt =this.estadoOrdenCompraRepository.findById(idEstado);
            if(estadoOrdenCompraOpt.isPresent()) {
                  OrdenCompra ordenCompra = ordenCompraOpt.get();
                  ordenCompra.setEstadoOrdenCompra(estadoOrdenCompraOpt.get());
                ordenCompra.setIdEstadoOrdenCompra(idEstado);
                OrdenCompra out = this.ordenCompraRepository.save(ordenCompra);
                //Enviar Correo
                Proveedor proveedorSap = this.proveedorRepository.getProveedorByAcreedorCodigoSap(ordenCompra.getProveedorCodigoSap());
                logger.error("OrdenCompraServiceImpl ::: proveedorSap ::: " + proveedorSap);
                if(proveedorSap != null) {
                    this.reactivacionOrdenCompraNotificacion.enviar(out, estadoOrdenCompraOpt.get(), proveedorSap);
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
    public OrdenCompraRespuestaDto updateOrdenCompraFechaVisualizacion(Integer idOrdenCompra) {
        OrdenCompraRespuestaDto ordenCompraRespuestaDto = new OrdenCompraRespuestaDto();
        List<String> mensajes = new ArrayList<>();
        Optional<OrdenCompra> ordenCompraOptional = ordenCompraRepository.findByIdAndIsActive(idOrdenCompra);
        OrdenCompra ordenCompra;

        if (ordenCompraOptional.isPresent()) {
            ordenCompra = ordenCompraOptional.get();

            if (ordenCompra.getFechaVisualizacion() == null) {
                ordenCompra.setFechaVisualizacion(DateUtils.getCurrentTimestamp());

                if (ordenCompra.getIdEstadoOrdenCompra().compareTo(OrdenCompraEstadoEnum.ACTIVA.getId()) == 0) {
                    ordenCompra.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.VISUALIZADA.getId());
                }
                ordenCompra = ordenCompraRepository.save(ordenCompra);
                ordenCompraRespuestaDto.setOrdenCompra(ordenCompra);

                /*Enviando Correo*/
                Usuario comprador = usuarioRepository.findByCodigoSap(ordenCompra.getCompradorUsuarioSap());
                if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty())
                    contactoVisualizadoOCNotificacion.enviar(ordenCompra, null, comprador);
                else
                    mensajes.add("Error al obtener los datos del comprador para envio de correo.");

                Proveedor proveedor = proveedorService.getProveedorByRuc(ordenCompra.getProveedorRuc());
                Usuario proveedorUsuario = null;
                if(proveedor != null && proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()){
                    proveedorUsuario = new Usuario();
                    logger.error("<--MC_LOG-->:OrdenCompraServiceImpl-updateOrdenCompraFechaVisualizacion:");
                    logger.error("<--MC_LOG-->:OrdenCompraServiceImpl-updateOrdenCompraFechaVisualizacion:"+proveedor.getEmail());
                    proveedorUsuario.setEmail(proveedor.getEmail());
                    proveedorUsuario.setApellido(proveedor.getRazonSocial());
                }
                else{
                    List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(ordenCompra.getProveedorRuc());
                    if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                        proveedorUsuario = posibleProveedorList.get(0);
                }

                if(proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                    contactoVisualizadoOCNotificacion.enviar(ordenCompra, proveedorUsuario, null);
                else
                    mensajes.add("Error al obtener los datos del proveedor para envio de correo.");

                ordenCompraRespuestaDto.setMensajes(mensajes);
            }
        }
        return ordenCompraRespuestaDto;
    }

    @Override
    public OrdenCompraRespuestaDto aprobarRechazarOrdenCompra(Integer idOrdenCompra, int estado, String textoRechazo) {
        OrdenCompraRespuestaDto ordenCompraRespuestaDto = new OrdenCompraRespuestaDto();
        Optional<OrdenCompra> ordenCompraOptional = ordenCompraRepository.findByIdAndIsActive(idOrdenCompra);
        OrdenCompra ordenCompra;
        List<String> mensajes = new ArrayList<>();

        if (ordenCompraOptional.isPresent()) {
            ordenCompra = ordenCompraOptional.get();

            ordenCompra.setIdEstadoOrdenCompra(estado);
            ordenCompra.setFechaAprobacion(DateUtils.getCurrentTimestamp());

            if (estado == OrdenCompraEstadoEnum.RECHAZADA.getId()) {
                ordenCompra.setMotivoRechazo(textoRechazo);
            }
            logger.error("OC " +  ordenCompra.getNumeroOrdenCompra() +  " APROB/RECHAZO estado (antes) :" + ordenCompra.getIdEstadoOrdenCompra());
            ordenCompra = ordenCompraRepository.save(ordenCompra);
            logger.error("OC " +  ordenCompra.getNumeroOrdenCompra() +  " APROB/RECHAZO estado (despues) :" + ordenCompra.getIdEstadoOrdenCompra());
            ordenCompraRespuestaDto.setOrdenCompra(ordenCompra);


            /*Enviando Correo*/
            Usuario comprador = usuarioRepository.findByCodigoSap(ordenCompra.getCompradorUsuarioSap());
            if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty()) {
                logger.error("<--MC_LOG-->:correo-comprador:"+ comprador.getEmail());
                contactoAprobadaRechazadaOCNotificacion.enviar(ordenCompra, null, comprador);
            }else {
                mensajes.add("Error al obtener los datos del comprador para envio de correo de aprobacion de orden de compra.");
            }

            Proveedor proveedor = proveedorService.getProveedorByRuc(ordenCompra.getProveedorRuc());
            Usuario proveedorUsuario = null;
            if(proveedor != null && proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()){
                proveedorUsuario = new Usuario();
                logger.error("<--MC_LOG-->:OrdenCompraServiceImpl-aprobarRechazarOrdenCompra:");
                logger.error("<--MC_LOG-->:OrdenCompraServiceImpl-aprobarRechazarOrdenCompra:"+proveedor.getEmail());
                proveedorUsuario.setEmail(proveedor.getEmail());
                proveedorUsuario.setApellido(proveedor.getRazonSocial());
            }
            else{
                List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(ordenCompra.getProveedorRuc());
                if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                    proveedorUsuario = posibleProveedorList.get(0);
            }

            if(proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                contactoAprobadaRechazadaOCNotificacion.enviar(ordenCompra, proveedorUsuario, null);
            else
                mensajes.add("Error al obtener los datos del proveedor para envio de correo de aprobacion de orden de compra.");

            ordenCompraRespuestaDto.setMensajes(mensajes);
        }
        return ordenCompraRespuestaDto;
    }


    @Override
    public void extraerOrdenCompraMasivoByRangoFechas(Date fechaInicio, Date fechaFin, boolean enviarCorreoPublicacion){
        LocalDate currentlyExtractLocalDate = DateUtils.utilDateToLocalDate(fechaInicio);
        LocalDate finalLocalDate = DateUtils.utilDateToLocalDate(fechaFin);
        logger.error("EXTRACCION ORDEN_COMPRA MASIVA - FECHA INICIO: " + currentlyExtractLocalDate.toString());
        logger.error("EXTRACCION ORDEN_COMPRA MASIVA - FECHA FIN: " + finalLocalDate.toString());

        while (currentlyExtractLocalDate.isBefore(finalLocalDate.plusDays(1))){
            try {
                String currentDateAsSapString = DateUtils.localDateToSapString(currentlyExtractLocalDate);
                jcoOrdenCompraPublicacionService.extraerOrdenCompraListRFC(currentDateAsSapString, currentDateAsSapString, enviarCorreoPublicacion);
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
    public String getOrdenCompraPdfContent(String numeroOrdenCompra) throws Exception{
        OrdenCompraPdfDto ordenCompraPdfDto = jcoOrdenCompraPdfService.extraerOrdenCompraPdfDtoRFC(numeroOrdenCompra);
        byte[] generateOrdenCompraBytes = PdfGeneratorFactory.getJasperGenerator().generateOrdenCompraPdfBytes(ordenCompraPdfDto);
        return Base64.getEncoder().encodeToString(generateOrdenCompraBytes);
    }


    @Override
    public String getContratoMarcoPdfContent(String numeroContratoMarco) throws Exception{
        OrdenCompraPdfDto contratoMarcoPdfDto = new OrdenCompraPdfDto();
        Optional<OrdenCompra> optionalContratoMarco = ordenCompraRepository.getOrdenCompraActivaByNumero(numeroContratoMarco);

        if(optionalContratoMarco.isPresent()){
            OrdenCompra contratoMarco = optionalContratoMarco.get();
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

            List<OrdenCompraDetalle> ordenCompraDetalleList = ordenCompraDetalleService.getOrdenCompraDetalleListByIdOc(contratoMarco.getId());
            List<OrdenCompraPosicionPdfDto> posicionPdfDtoList = new ArrayList<>();

            ordenCompraDetalleList.forEach(ocd -> {
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
    public OrdenCompra getOrdenCompraByNOCompra(String nOrdenCompra) {
        return ordenCompraRepository.getOrdenCompraByNOCompra(nOrdenCompra);
    }
    @Override
    public List<HashMap<String,String>> cambioEstadoOCRechazada(List<String> numeroOrdenCompra){
        logger.error("OC Rechazada: "+ numeroOrdenCompra);
        List<HashMap<String,String>> output = new ArrayList<>();

       for(String n : numeroOrdenCompra){
           HashMap<String,String> map = new HashMap<>();
           OrdenCompra oc= ordenCompraRepository.getOrdenCompraRechazada(n);
           if(oc!=null) {
               oc.setIdEstadoOrdenCompra(1);
               ordenCompraRepository.save(oc);
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