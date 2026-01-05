package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.*;
import com.incloud.hcp.dto.CotizacionDto;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.myibatis.mapper.CotizacionMapper;
import com.incloud.hcp.myibatis.mapper.ParametroMapper;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.rest.bean.CotizacionEnviarCotizacionDTO;
import com.incloud.hcp.rest.bean.CotizacionGrabarDTO;
import com.incloud.hcp.rest.bean.PrecioCotizacionDTO;
import com.incloud.hcp.service.CotizacionService;
import com.incloud.hcp.service._framework.BaseServiceImpl;
import com.incloud.hcp.service.cmiscf.CmisBaseService;
import com.incloud.hcp.service.cmiscf.bean.CmisFile;
import com.incloud.hcp.service.notificacion.EnviarConfirmarParticipacionNotificacion;
import com.incloud.hcp.service.notificacion.EnviarCotizacionNotificacion;
import com.incloud.hcp.util.Constant;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.constant.CotizacionConstant;
import com.incloud.hcp.util.constant.LicitacionConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by USER on 13/10/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class CotizacionServiceImpl extends BaseServiceImpl implements CotizacionService {

    private static Logger logger = LoggerFactory.getLogger(CotizacionServiceImpl.class);

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private LicitacionRepository licitacionRepository;

    @Autowired
    private CotizacionDetalleRepository cotizacionDetalleRepository;

    @Autowired
    private CmisBaseService cmisService;

    @Autowired
    private CotizacionAdjuntoRepository cotizacionAdjuntoRepository;

    @Autowired
    private CotizacionCampoRespuestaRepository cotizacionCampoRespuestaRepository;

    @Autowired
    private GrupoCondicionLicRespuestaRepository grupoCondicionLicRespuestaRepository;

    @Autowired
    private LicitacionGrupoCondicionLicRepository licitacionGrupoCondicionLicRepository;

    @Autowired
    private LicitacionSubetapaRepository licitacionSubetapaRepository;


    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private EnviarCotizacionNotificacion enviarCotizacionNotificacion;

    @Autowired
    private EnviarConfirmarParticipacionNotificacion enviarConfirmarParticipacionNotificacion;

    @Autowired
    private LicitacionProveedorRepository licitacionProveedorRepository;

    @Autowired
    private CotizacionMapper cotizacionMapper;


    @Autowired
    private ParametroMapper parametroMapper;

    @Autowired
    private LicitacionProveedorPreguntaRepository licitacionProveedorPreguntaRepository;

    @Autowired
    private LicitacionDetalleRepository licitacionDetalleRepository;

    @Autowired
    private LicitacionProveedorTipoCuestionarioRepository licitacionProveedorTipoCuestionarioRepository;

    @Autowired
    private LicitacionProveedorDetalleEvaluacionRepository licitacionProveedorDetalleEvaluacionRepository;



    public CotizacionGrabarDTO save(CotizacionGrabarDTO dto) throws PortalException {
        if (dto == null) {
            throw new PortalException("CotizacionGrabarDTO ingresado es NULL");
        }
        if (dto.getCotizacion() == null) {
            throw new PortalException("Cotización ingresado es NULL");
        }
        if (dto.getCotizacion().getLicitacion() == null) {
            throw new PortalException("Licitación ingresado es NULL");
        }
        if (dto.getCotizacion().getProveedor() == null) {
            throw new PortalException("Proveedor ingresado es NULL");
        }
        if (dto.getCotizacion().getLicitacion().getIdLicitacion() == null) {
            throw new PortalException("Id Licitacion ingresado es NULL");
        }
        if (dto.getCotizacion().getProveedor().getIdProveedor() == null) {
            throw new PortalException("Id Proveedor ingresado es NULL");
        }

        logger.error("CotizacionGrabarDTO save 01");
        Integer idLicitacion = dto.getCotizacion().getLicitacion().getIdLicitacion();
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        logger.error("CotizacionGrabarDTO save 02 licitacion: " + licitacion);
        if (!licitacion.getEstadoLicitacion().equals(LicitacionConstant.ESTADO_PUBLICADA)) {
            throw new PortalException("Licitación se encuentra en Estado diferente a PENDIENTE DE EVALUAR");
        }
        Proveedor proveedor = dto.getCotizacion().getProveedor();
        proveedor = this.proveedorRepository.getOne(proveedor.getIdProveedor());
        LicitacionProveedor licitacionProveedor = this.licitacionProveedorRepository.
                getByLicitacionAndProveedor(licitacion, proveedor);

        String indicadorConfirmarParticipacion = licitacionProveedor.getIndSiParticipa();
        if (StringUtils.isNotBlank(indicadorConfirmarParticipacion)) {
            if (indicadorConfirmarParticipacion.equals(Constant.N)) {
                Date fechaActual = DateUtils.obtenerFechaHoraActual();
                Date fechaConfirmacionCierre = licitacion.getFechaCierreConfirmacionParticipacion();
                String sFechaConfirmacionCierre = DateUtils.convertDateToString("yyyy/MM/dd HH:mm:ss", fechaConfirmacionCierre);
                if (fechaConfirmacionCierre.before(fechaActual)) {
                    throw new PortalException("No puede Grabar Cotización respectiva debido que NO CONFIRMO SU PARTICIPACION y se excedió de la Fecha Límite: " + sFechaConfirmacionCierre);
                }
                throw new PortalException("No puede Grabar Cotización respectiva debido que NO HA CONFIRMADO SU PARTICIPACION, confirme primero su Confirmación. Fecha Límite de Confirmación: " + sFechaConfirmacionCierre);
            }
        }


        Date fechaRecepcion = licitacionProveedor.getFechaCierreRecepcion();
        Date fechaActual = DateUtils.obtenerFechaHoraActual();
        logger.error("CotizacionGrabarDTO save 03 fechaRecepcion: " + fechaRecepcion);
        logger.error("CotizacionGrabarDTO save 04 fechaActual: " + fechaActual);
        if (fechaRecepcion.before(fechaActual)) {
            throw new PortalException("No puede Grabar Cotización respectiva debido que se excedió de la Fecha Cierre Recepción Ofertas: " +
                    DateUtils.convertDateToString("dd/MM/yyyy HH:mm:ss", fechaRecepcion) );
        }

        List<CotizacionDetalle> listaCotizacionDetalle = dto.getListaCotizacionDetalle();

        Cotizacion cabecera = dto.getCotizacion();
        cabecera.setIdCotizacion(null);
        cabecera.setFechaCreacion( new Timestamp(System.currentTimeMillis()));
        cabecera.setIndGanador("0");
        cabecera.setEstadoCotizacion(CotizacionConstant.ESTADO_GENERADA);

        //---------------------------------------------------------------------
        BigDecimal suma = new BigDecimal(0);
//        BigDecimal descuento = new BigDecimal(0);
        for(CotizacionDetalle itemDetalle:listaCotizacionDetalle) {
            BigDecimal precioTotal = new BigDecimal(itemDetalle.getPrecioUnitario().floatValue() * itemDetalle.getCantidadCotizada().longValue());
            suma = new BigDecimal(suma.floatValue() + precioTotal.floatValue());
//            if (itemDetalle.getDescuento() != null)
//                descuento = new BigDecimal(descuento.floatValue() + itemDetalle.getDescuento().floatValue()); // ????? el descuento deberia ser aplicado como porcentaje al monto precioTotal !!!!!
        }
//        BigDecimal cotImporteTotal = new BigDecimal(suma.floatValue() - descuento.floatValue());
//        cabecera.setImporteTotal(cotImporteTotal);
        cabecera.setImporteTotal(suma);
        //---------------------------------------------------------------------

        /* Eliminando los registros hijos */

        List<Cotizacion> listaCotizacion = this.cotizacionRepository.
                findByLicitacionAndProveedorSort(
                        cabecera.getLicitacion(),
                        cabecera.getProveedor(),
                        Sort.by("proveedor.razonSocial"));

        this.licitacionProveedorDetalleEvaluacionRepository.deleteByLicitacion02(licitacion.getIdLicitacion());
        this.licitacionProveedorTipoCuestionarioRepository.deleteByLicitacion02(licitacion.getIdLicitacion());

        for(Cotizacion item:listaCotizacion) {
            this.cotizacionAdjuntoRepository.deleteCotizacionAdjuntoByCotizacion02(item.getIdCotizacion());
            this.cotizacionCampoRespuestaRepository.deleteCotizacionCampoRespuestaByCotizacion02(item.getIdCotizacion());
            this.cotizacionDetalleRepository.deleteCotizacionDetalleByCotizacion02(item.getIdCotizacion());
        }
        this.cotizacionRepository.deleteCotizacionByLicitacionAndProveedor(
                dto.getCotizacion().getLicitacion(),
                dto.getCotizacion().getProveedor());
        logger.error("CotizacionGrabarDTO save 04.5 Grabando nueva cotizacion: " + cabecera.toString());
        /* Insertando Cotizacion */
        final Cotizacion cabeceraResult = this.cotizacionRepository.save(cabecera);
        logger.error("CotizacionGrabarDTO save 04.6 Nueva cotizacion grabada: " + cabeceraResult.toString());
        logger.error("CotizacionGrabarDTO save 05 Grabando lista detalles: " + listaCotizacionDetalle.toString());
        /* Grabando detalles */
        for(CotizacionDetalle itemDetalle:listaCotizacionDetalle) {
            itemDetalle.setCotizacion(cabeceraResult);
            itemDetalle.setIndGanador("0");
            logger.debug("Antes de Ingresar cotizacion detalle");

            if (itemDetalle.getBienServicio() != null && itemDetalle.getBienServicio().getIdBienServicio() == null) {
                logger.debug("cotizacion detalle - Bien Servicio vacio 2");
                itemDetalle.setBienServicio(null);

            }
            logger.debug("Antes de SAVE cotizacion detalle");
            this.cotizacionDetalleRepository.save(itemDetalle);
        }

        /* Grabando detalles condicion */
        List<CotizacionCampoRespuesta> listaCotizacionCondicion = dto.getListaCotizacionCondicion();
        logger.error("CotizacionGrabarDTO save 06 Grabando lista detalles condicion: " + listaCotizacionCondicion.toString());
        for(CotizacionCampoRespuesta itemDetalle:listaCotizacionCondicion) {
             CotizacionCampoRespuestaPK beanPK = new CotizacionCampoRespuestaPK();
             beanPK.setIdCotizacion(cabeceraResult.getIdCotizacion());
             beanPK.setIdLicitacionGrupoCondicion(itemDetalle.getLicitacionGrupoCondicionLic().getIdLicitacionGrupoCondicion());
             LicitacionGrupoCondicionLic licitacionGrupoCondicionLicBean =
                     new LicitacionGrupoCondicionLic();
             licitacionGrupoCondicionLicBean.setIdLicitacionGrupoCondicion(itemDetalle.getLicitacionGrupoCondicionLic().getIdLicitacionGrupoCondicion());

            if (StringUtils.isNotBlank(itemDetalle.getTextoRespuestaLibre())) {
                itemDetalle.setGrupoCondicionLicRespuesta(null);
                itemDetalle.setPuntaje(new BigDecimal(0));
            }
            else {
                if (itemDetalle.getGrupoCondicionLicRespuesta() != null) {
                    GrupoCondicionLicRespuesta grupoCondicionLicRespuesta = this.grupoCondicionLicRespuestaRepository
                           .getOne(itemDetalle.getGrupoCondicionLicRespuesta().getIdCondicionRespuesta());
                    itemDetalle.setPuntaje(grupoCondicionLicRespuesta.getPuntaje());
                }
            }
            itemDetalle.setId(beanPK);
            itemDetalle.setCotizacion(cabeceraResult);
            itemDetalle.setLicitacionGrupoCondicionLic(licitacionGrupoCondicionLicBean);

            itemDetalle.setFechaRegistro( new Timestamp(System.currentTimeMillis()));
            this.cotizacionCampoRespuestaRepository.save(itemDetalle);
        }

        //insertando adjuntos
        logger.error("CotizacionGrabarDTO save 07 Grabando lista adjuntos: " + dto.getListaCotizacionAdjunto().toString());
        /* Grabando Adjuntos */
  /*      for(CotizacionAdjunto item:listaAdjuntos) {
            CotizacionAdjunto beanGrabar = new CotizacionAdjunto();
            beanGrabar.setCotizacion(cabeceraResult);
            beanGrabar.setRutaAdjunto(item.getRutaAdjunto());
            beanGrabar.setArchivoId(item.getArchivoId());
            beanGrabar.setArchivoNombre(item.getArchivoNombre());
            beanGrabar.setArchivoTipo(item.getArchivoTipo());
            beanGrabar.setDescripcion(item.getDescripcion());
            beanGrabar.setIndCotizacion(item.getIndCotizacion());

            this.cotizacionAdjuntoRepository.save(beanGrabar);
        }*/
        if (dto.getListaCotizacionAdjunto() != null && !dto.getListaCotizacionAdjunto().isEmpty()) {
            logger.error("ENTRANDO A GRABAR ADJUNTOS COTIZACION");
            try {
                this.guardarAdjuntoCotizacion(licitacion, cabeceraResult, dto.getListaCotizacionAdjunto(), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        dto.setCotizacionResult(cabeceraResult);
        logger.error("CotizacionGrabarDTO save 08 Insertando preguntas");
        //Insertando preguntas
        this.licitacionProveedorPreguntaRepository.deleteDetailByLicitacionProveedor(idLicitacion, dto.getCotizacion().getProveedor().getIdProveedor());
        this.licitacionProveedorPreguntaRepository.saveAll(dto.getLicitacionProveedorPregunta());

        return dto;
    }


    public CotizacionEnviarCotizacionDTO enviarCotizacion(CotizacionEnviarCotizacionDTO dto) throws PortalException  {

        Integer idCotizacion = dto.getCotizacion().getIdCotizacion();
        Integer idLicitacion = dto.getCotizacion().getLicitacion().getIdLicitacion();
        Integer idProveedor  = dto.getCotizacion().getProveedor().getIdProveedor();
        Integer idUsuario    = dto.getIdUsuario();

        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        Proveedor proveedor = this.proveedorRepository.getOne(idProveedor);

        /* Validaciones Previas */
        Date fechaActual = DateUtils.obtenerFechaHoraActual();
        Date fechaInicioRecepcion = licitacion.getFechaInicioRecepcionOferta();
        String fechaInicioRecepcionString = DateUtils.convertDateToString("dd/MM/yyyy HH:mm:ss", fechaInicioRecepcion);
        if (fechaActual.compareTo(fechaInicioRecepcion) < 0) {
            throw new PortalException("El Envio de la Respuesta de Licitación se habilitará a partir del " + fechaInicioRecepcionString);
        }


        this.cotizacionRepository.updateEstadoLicitacion(CotizacionConstant.ESTADO_ENVIADA,
                idUsuario, idCotizacion  );

        logger.debug("INICIO: Envio de email a Copeinca ..");
        this.enviarCotizacionNotificacion.enviar(this.parametroMapper.getMailSetting(), licitacion, proveedor);

        List<CotizacionAdjunto> listaAdjuntos = this.cotizacionAdjuntoRepository.findByLicitacionProveedor(
                dto.getCotizacion().getLicitacion(), dto.getCotizacion().getProveedor());
        logger.error("CotizacionGrabarDTO save 05 listaAdjuntos size: " + listaAdjuntos.size());
        logger.error("CotizacionGrabarDTO save 06 listaAdjuntos: " + listaAdjuntos.toString());
        if (listaAdjuntos == null  || listaAdjuntos.size() <= 0) {
            throw new PortalException("Es obligatorio adjuntar una cotización para poder continuar");
        }

        /* Verificando si ya se enviaron todas las cotizaciones */
        Integer contadorProveedor = this.licitacionProveedorRepository.countByLicitacionAndEstadoLicitacion(
                licitacion, LicitacionConstant.ESTADO_PUBLICADA);
        Integer contadorCotizacion = this.cotizacionRepository.countByLicitacionAndEstadoCotizacion(
                licitacion, CotizacionConstant.ESTADO_ENVIADA);
        if (contadorCotizacion.intValue() == contadorProveedor.intValue()) {

            String estadoLicitacion = LicitacionConstant.ESTADO_PUBLICADA;
            String estadoLicitacionPorEvaluar = LicitacionConstant.ESTADO_POR_EVALUAR;
            this.licitacionRepository.updateEstadoLicitacion02(
                    estadoLicitacionPorEvaluar, fechaActual, licitacion.getIdLicitacion());

        }

        dto.setCotizacionResult(dto.getCotizacion());
        return dto;
    }


    public LicitacionProveedor siParticiparCotizacion(Integer idLicitacion, Integer idProveedor) throws Exception {
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        if (!Optional.ofNullable(licitacion).isPresent()) {
            throw new Exception("No se encontró Licitación con ID: "+ idLicitacion);
        }
        Proveedor proveedor = this.proveedorRepository.getOne(idProveedor);
        if (!Optional.ofNullable(proveedor).isPresent()) {
            throw new Exception("No se encontró Proveedor con ID: "+ idProveedor);
        }

        LicitacionProveedor licitacionProveedor = this.licitacionProveedorRepository.
                getByLicitacionAndProveedor(
                        licitacion,
                        proveedor
                );
        if (!Optional.ofNullable(licitacionProveedor).isPresent()) {
            throw new Exception("No se encontró Licitacion Proveedor con ID Proveedor: "+ idProveedor);
        }
        String indicadorSiParticipa = licitacionProveedor.getIndSiParticipa();
        if (StringUtils.isNotBlank(indicadorSiParticipa)) {
            if (indicadorSiParticipa.equals(Constant.S)) {
                Date fechaConfirmacion = licitacionProveedor.getFechaConfirmacionParticipacion();
                String sfechaConfirmacion = DateUtils.convertDateToString("yyyy/MM/dd HH:mm:ss", fechaConfirmacion);
                throw new Exception("La Confirmación de Participación ya fue realizada en fecha: "+ sfechaConfirmacion);
            }
        }

        Date fechaActual = DateUtils.obtenerFechaHoraActual();
        LicitacionSubetapa licitacionSubetapa = this.licitacionSubetapaRepository.
                findByLicitacionConfirmacionParticipacion(idLicitacion);
        if (!Optional.ofNullable(licitacionSubetapa).isPresent()) {
            throw new Exception("No se encontró Etapa de Confirmación de Participación para dicha Licitación");
        }
        if (licitacionSubetapa.getFechaCierreSubetapa().before(fechaActual)) {
            String sfechaConfirmacion = DateUtils.convertDateToString("yyyy/MM/dd HH:mm:ss", licitacionSubetapa.getFechaCierreSubetapa());
            throw new Exception("La Fecha de Confirmación de Participación ha vencido : "+ sfechaConfirmacion + ". Ya no puede participar en la Licitación");
        }

        licitacionProveedor.setIndSiParticipa(Constant.S);
        licitacionProveedor.setFechaConfirmacionParticipacion(fechaActual);
        this.licitacionProveedorRepository.save(licitacionProveedor);

        this.enviarConfirmarParticipacionNotificacion.enviar(
                this.parametroMapper.getMailSetting(),
                licitacion,
                proveedor);
        return licitacionProveedor;
    }

    public CotizacionEnviarCotizacionDTO noParticiparCotizacion(CotizacionEnviarCotizacionDTO dto) throws PortalException {

        Integer idLicitacion = dto.getCotizacion().getLicitacion().getIdLicitacion();
        Integer idProveedor = dto.getCotizacion().getProveedor().getIdProveedor();
        Integer idUsuario = dto.getIdUsuario();

        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        Proveedor proveedor = this.proveedorRepository.getOne(idProveedor);

        /* Validaciones Previas */
        Date fechaConfirmacion = licitacion.getFechaCierreConfirmacionParticipacion();
        if (Optional.ofNullable(fechaConfirmacion).isPresent()) {
            Date fechaActual = DateUtils.obtenerFechaHoraActual();
            if (fechaConfirmacion.compareTo(fechaActual) < 0) {
                throw new PortalException("El plazo para NO PARTICIPAR en la Licitación ha vencido");
            }
        }

        /* Eliminando Cotizacion */
        List<Cotizacion> listaCotizacion = this.cotizacionRepository.
                findByLicitacionAndProveedorSort(licitacion, proveedor, Sort.by("proveedor.razonSocial"));
        for(Cotizacion item:listaCotizacion) {
            this.cotizacionAdjuntoRepository.deleteCotizacionAdjuntoByCotizacion(item);
            this.cotizacionCampoRespuestaRepository.deleteCotizacionCampoRespuestaByCotizacion(item);
            this.cotizacionDetalleRepository.deleteCotizacionDetalleByCotizacion(item);
        }
        this.cotizacionRepository.deleteCotizacionByLicitacionAndProveedor(licitacion, proveedor);

        /* Insertando Cotizacion */
        Date fechaActual = DateUtils.obtenerFechaHoraActual();
        Cotizacion cabecera = new Cotizacion();
        Moneda moneda = new Moneda();
        moneda.setIdMoneda(1);
        cabecera.setLicitacion(licitacion);
        cabecera.setProveedor(proveedor);
        cabecera.setMoneda(moneda);
        cabecera.setUsuarioCreacion(idUsuario);
        cabecera.setIdCotizacion(null);
        cabecera.setFechaCreacion( new Timestamp(fechaActual.getTime()));
        cabecera.setIndGanador("0");
        cabecera.setEstadoCotizacion(CotizacionConstant.ESTADO_NO_PARTICIPAR);

        BigDecimal suma = new BigDecimal(0);
        cabecera.setImporteTotal(suma);

        final Cotizacion cabeceraResult = this.cotizacionRepository.save(cabecera);
        dto.setCotizacionResult(cabeceraResult);


        /* Actualizando en Licitacion Proveedor */
        LicitacionProveedor licitacionProveedor = this.licitacionProveedorRepository.
                getByLicitacionAndProveedor(licitacion, proveedor);
        if (Optional.ofNullable(licitacionProveedor).isPresent()) {
            licitacionProveedor.setIndSiParticipa(Constant.N);
            this.licitacionProveedorRepository.save(licitacionProveedor);
        }

        return dto;

    }

    @Override
    public List<Cotizacion> getListCotizacionByLicitacion(Integer idLicitacion) {
        return cotizacionMapper.getListCotizacionByLicitacion(idLicitacion);
    }

    @Override
    public Cotizacion getCotizacionByProveedorandLicitacion(Integer idLicitacion,Integer idProveedor){
       return this.cotizacionRepository.
                findByLicitacionAndProveedor(idLicitacion,idProveedor);

    }

    @Override
    public CotizacionDto getCotizacionDtoByProveedorandLicitacion(Integer idLicitacion, Integer idProveedor){
//        String header = "getCotizacionByProveedorandLicitacion / idLicitacion = " + idLicitacion + " / idProveedor = " + idProveedor + " // ";
        String indSiParticipa = licitacionProveedorRepository.getIndSiParticipa(idLicitacion,idProveedor);
//        logger.error(header + "indSiParticipa :" + indSiParticipa);
        Cotizacion cotizacion = cotizacionRepository.findByLicitacionAndProveedor(idLicitacion,idProveedor);
        CotizacionDto cotizacionDto = new CotizacionDto();

        if(cotizacion != null){
//            logger.error(header + "cotizacion :" + cotizacion.toString());
            BeanUtils.copyProperties(cotizacion, cotizacionDto);
        }

        cotizacionDto.setIndSiParticipa(indSiParticipa);
//        logger.error(header + "cotizacionDto :" + cotizacionDto.toString());
        return cotizacionDto;
    }

    public void guardarAdjuntoCotizacion(Licitacion licitacion,Cotizacion cotizacionResult, List<CotizacionAdjunto> listAdjunto, String operacion) throws Exception {

        logger.error("iniciando GRABAR AdjuntoCotizacion");

        //Creo una segunda lista con los adjuntos no guardados
        List<com.incloud.hcp.service.cmiscf.bean.CmisFile> listAdjuntoNew = new ArrayList<com.incloud.hcp.service.cmiscf.bean.CmisFile>();
        if (listAdjunto.size() > 0) {
            listAdjunto.forEach(item -> {
                //item.setIdCotizacionAdjunto(null);
                if (item.getIdCotizacionAdjunto() == null) {
                    listAdjuntoNew.add(new com.incloud.hcp.service.cmiscf.bean.CmisFile(item.getArchivoId(), item.getArchivoNombre(), item.getRutaAdjunto(), item.getArchivoTipo(), null, null, null, null, null, null));
                    logger.error("Creo una segunda lista con los adjuntos no guardados");
                }else{
                    logger.error("Añadiendo adjuntos guardados a new Cotizacion");
                    CotizacionAdjunto obj = this.cotizacionAdjuntoRepository.save(new CotizacionAdjunto(cotizacionResult, item.getRutaAdjunto(), item.getArchivoId(), item.getArchivoNombre(), item.getArchivoTipo()));
                }
            });
        }

        String nameFolder = licitacion.getNroLicitacionCero() + "_" + cotizacionResult.getProveedor().getIdProveedor();
        logger.error("NOMBRE FOLDER " + nameFolder);
        //Se crea folder destino -> Nro de licitacion


        String folderId = cmisService.createFolder(nameFolder).getNameFolder();//Id();
        logger.debug("FOLDER_DESTINO: " + folderId);

        //Se mueven los adjuntos al folder destino y se obtiene la lista de los mismos con su nuevo URL
        Optional<List<com.incloud.hcp.service.cmiscf.bean.CmisFile>> listAdjuntoMove = Optional.ofNullable(listAdjuntoNew)
                .map(list -> {
                    logger.debug("Actualizando la version de los archivos adjuntos");
                    return cmisService.updateFileAndMoveVerificar(listAdjuntoNew, folderId);
                });

        logger.error(listAdjuntoMove.toString());
        //Se guardan en la base de datos los adjuntos movidos
        if(listAdjuntoMove.isPresent()){
            List<CmisFile> list = listAdjuntoMove.get();
            list.forEach(file->{
                CotizacionAdjunto obj = this.cotizacionAdjuntoRepository.save(new CotizacionAdjunto(cotizacionResult, file.getUrl(), file.getId(), file.getName(), file.getType()));
            });
            logger.error("Se guardan en la base de datos los adjuntos movidos");
        }

        //Eliminando adjuntos ausentes en el request
        List<CotizacionAdjunto> listaAdjuntosActual = this.cotizacionAdjuntoRepository.findByLicitacionProveedor(licitacion,cotizacionResult.getProveedor());
        logger.error(listaAdjuntosActual.toString());
        for (CotizacionAdjunto obj1 : listaAdjuntosActual){
            Boolean encontrado = false;
            for (CotizacionAdjunto obj2 : listAdjunto){
                if ((obj1.getArchivoId()).equals(obj2.getArchivoId())){
                    encontrado = true;
                    logger.error("Encontrados" + obj1.getArchivoId() + "  " + obj2.getArchivoId());
                    break;
                }
            }
            if (!encontrado){
                this.cotizacionAdjuntoRepository.deleteCotizacionAdjuntoByCotizacionArchivoId(cotizacionResult, obj1.getArchivoId());
                logger.error("Eliminando adjuntos ausentes en el request" + obj1.getArchivoId());
                continue;
            }
        }

        //Se obtiene lista de adjuntos final
      /*  List<ProveedorAdjuntoSunat> listaAdjuntosSunatResult = this.proveedorAdjuntoSunatRepository.getProveedorAdjuntoSunatByIdProveedor(proveedor.getIdProveedor());

        return listaAdjuntosSunatResult;*/

    }

    public List<PrecioCotizacionDTO> findByCotizacionDetalleLicitacionProveedor(Map<String, Object> json) throws Exception {
        Integer idLicitacion = 0;
        Integer idProveedor = 0;
        String sidLicitacion = "";
        String sidProveedor = "";
        Licitacion licitacion = new Licitacion();

        try {
            idProveedor = (Integer) json.get("idProveedor");

        } catch (Exception e) {
            sidProveedor = (String) json.get("idProveedor");
            sidProveedor = sidProveedor.trim();
            idProveedor = new Integer(sidProveedor);
        }
        Proveedor proveedor = this.proveedorRepository.getOne(idProveedor);

        try {
            idLicitacion = (Integer) json.get("idLicitacion");
            licitacion.setIdLicitacion(idLicitacion);
        } catch (Exception e) {
            sidLicitacion = (String) json.get("idLicitacion");
            sidLicitacion = sidLicitacion.trim();
            idLicitacion = new Integer(sidLicitacion);
            licitacion.setIdLicitacion(idLicitacion);
        }
        licitacion = this.licitacionRepository.getOne(idLicitacion);

        List<PrecioCotizacionDTO> listaPrecioCotizacionDTO = new ArrayList<PrecioCotizacionDTO>();
        List<LicitacionDetalle> licitacionDetalleList = this.licitacionDetalleRepository.findByLicitacionOrdenado(licitacion);
        if (licitacionDetalleList == null || licitacionDetalleList.size() <= 0) {
            return listaPrecioCotizacionDTO;
        }
        Cotizacion cotizacion = this.cotizacionRepository.findByLicitacionAndProveedor(
                idLicitacion,
                idProveedor
        );

        for (LicitacionDetalle beanLicitacionDetalle : licitacionDetalleList) {
            PrecioCotizacionDTO beanDTO = new PrecioCotizacionDTO();
            CotizacionDetalle cotizacionDetalle = this.cotizacionDetalleRepository.
                    getByCotizacionByProveedorLicitacionDetalle(
                            idProveedor,
                            idLicitacion,
                            beanLicitacionDetalle.getIdLicitacionDetalle()
                    );
            beanDTO.setLicitacionDetalle(beanLicitacionDetalle);
            if (Optional.ofNullable(cotizacionDetalle).isPresent()) {
                beanDTO.setNombreBienServicio(cotizacionDetalle.getDescripcion());
                beanDTO.setCantidadSolicitada(cotizacionDetalle.getCantidadSolicitada());
                beanDTO.setUnidadMedida(cotizacionDetalle.getUnidadMedida());
                beanDTO.setIdLicitacionDetalle(cotizacionDetalle.getLicitacionDetalle().getIdLicitacionDetalle());
                beanDTO.setSolicitudPedido(cotizacionDetalle.getLicitacionDetalle().getSolicitudPedido());
                beanDTO.setPosicionSolicitudPedido(cotizacionDetalle.getLicitacionDetalle().getPosicionSolicitudPedido());

                beanDTO.setVisible01(true);
                beanDTO.setNombreProveedor01(proveedor.getRazonSocial());
                BigDecimal subTotal01 = cotizacionDetalle.getCantidadCotizada().multiply(cotizacionDetalle.getPrecioUnitario());
                beanDTO.setMoneda01(cotizacion.getMoneda());
                beanDTO.setCantidadCotizada01(cotizacionDetalle.getCantidadCotizada());
                beanDTO.setPrecioUnitario01(cotizacionDetalle.getPrecioUnitario());
                beanDTO.setSubTotal01(subTotal01);
                beanDTO.setCotizacion01(cotizacionDetalle.getCotizacion().getIdCotizacion());
                beanDTO.setEstadoCotizacion01(cotizacionDetalle.getCotizacion().getEstadoCotizacion());
                beanDTO.setEspecificacion01(cotizacionDetalle.getEspecificacion());
            }
            else {
                beanDTO.setNombreBienServicio(beanLicitacionDetalle.getDescripcion());
                beanDTO.setCantidadSolicitada(beanLicitacionDetalle.getCantidadSolicitada());
                beanDTO.setUnidadMedida(beanLicitacionDetalle.getUnidadMedida());
                beanDTO.setIdLicitacionDetalle(beanLicitacionDetalle.getIdLicitacionDetalle());
                beanDTO.setSolicitudPedido(beanLicitacionDetalle.getSolicitudPedido());

                beanDTO.setVisible01(true);
                beanDTO.setNombreProveedor01(proveedor.getRazonSocial());
                beanDTO.setCotizacion01(null);
                beanDTO.setCantidadCotizada01(new BigDecimal(0));
                beanDTO.setPrecioUnitario01(new BigDecimal(0));
            }
            listaPrecioCotizacionDTO.add(beanDTO);
        }
        return listaPrecioCotizacionDTO;
    }

}
