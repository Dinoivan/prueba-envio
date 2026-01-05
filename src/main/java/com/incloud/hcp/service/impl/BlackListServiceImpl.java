package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.dto.SolicitudBlackListAdjuntoDto;
import com.incloud.hcp.dto.SolicitudBlackListDto;
import com.incloud.hcp.dto.blacklist.SolicitudBlackListDTOMapper;
import com.incloud.hcp.dto.mapper.EntityDTOMapper;
import com.incloud.hcp.enums.CodigoReglaAprobacionEnum;
import com.incloud.hcp.enums.CodigoTipoSolicitudBlackListEnum;
import com.incloud.hcp.enums.EstadoBlackListEnum;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.myibatis.mapper.BlackListMapper;
import com.incloud.hcp.myibatis.mapper.ParametroMapper;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.sap.proveedor.ProveedorWebService;
import com.incloud.hcp.service.BlackListService;
import com.incloud.hcp.service.NotificacionService;
import com.incloud.hcp.service._framework.BaseServiceImpl;
import com.incloud.hcp.service.cmiscf.CmisBaseService;
import com.incloud.hcp.service.notificacion.SolicitudPOAprobadorNotificacion;
import com.incloud.hcp.service.notificacion.SolicitudPOFinalizacionNotificacion;
import com.incloud.hcp.util.constant.WebServiceConstant;
import com.sap.cloud.security.xsuaa.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrador on 12/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class BlackListServiceImpl extends BaseServiceImpl implements BlackListService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Timestamp fechaSistema = new Timestamp(new Date().getTime());

    private EntityDTOMapper<SolicitudBlacklist, SolicitudBlackListDto> mapper;
    private SolicitudBlackListRepository solicitudRepository;
    private TipoSolicitudBlackListRepository tipoSolicitudRepository;
    private CriterioBlackListRepository criterioBlackListRepository;
    private NotificacionService notificacionService;
    private ProveedorRepository proveedorRepository;
    @Autowired
    private CmisBaseService cmisService;

    @Autowired
    private SolicitudAdjuntoBlackListRepository solicitudAdjuntoBlackListRepository;

    @Autowired
    private SolicitudPOAprobadorNotificacion solicitudPOAprobadorNotificacion;

    @Autowired
    private SolicitudPOFinalizacionNotificacion solicitudPOFinalizacionNotificacion;

    @Autowired
    private ParametroMapper parametroMapper;

    @Autowired
    private ProveedorWebService proveedorWebService;


    @Autowired
    @Qualifier("solicitudBlackListDTOMapper")
    public void setSolicitudBlackListDTOMapper(SolicitudBlackListDTOMapper solicitudBlackListDTOMapper) {
        this.mapper = solicitudBlackListDTOMapper;
    }

    @Autowired
    public void setSolicitudBlackListRepository(SolicitudBlackListRepository solicitudBlackListRepository) {
        this.solicitudRepository = solicitudBlackListRepository;
    }

    @Autowired
    public void setNotificacionService(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @Autowired
    public void setProveedorRepository(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Autowired
    public void setTipoSolicitudRepository(TipoSolicitudBlackListRepository tipoSolicitudBlackListRepository) {
        this.tipoSolicitudRepository = tipoSolicitudBlackListRepository;
    }

    @Autowired
    public void setCriterioBlackListRepository(CriterioBlackListRepository criterioBlackListRepository) {
        this.criterioBlackListRepository = criterioBlackListRepository;
    }

    @Autowired
    private AprobadorSolicitudRepository aprobadorSolicitudRepository;



    @Autowired
    BlackListMapper blackListMapper;

    @Autowired
    UsuarioRepository usuarioRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SolicitudBlackListDto registrar(SolicitudBlackListDto solicitud)  {

        logger.debug("Registrar solicitud de blackList " + solicitud);

        solicitud.setEstado(EstadoBlackListEnum.GENERADA.getCodigo());
        blackListMapper.registrarSolicitud(solicitud);
        Optional.ofNullable(solicitud.getAdjuntos())
                .ifPresent(list -> list.stream().forEach(adjunto -> {
                    blackListMapper.registrarSolicitudAdjunto(adjunto, solicitud.getIdSolicitud());
                }));
        Optional.ofNullable(solicitud.getAdjuntos())
                .ifPresent(list -> {
                    logger.debug("Actualizando la version de los archivos adjuntos");
                    List<String> listIds = new ArrayList<>();
                    list.stream().map(SolicitudBlackListAdjuntoDto::getId).forEach(listIds::add);
                    cmisService.updateFileLastVersionTrue(listIds,solicitud.getRuc());
                });

        logger.debug("Notificar al administrador de Blacklist");

        return solicitud;
    }

    @Override
    public SolicitudBlackListDto getSociedadByRuc(String ruc)  {
        Optional<SolicitudBlackListDto> oProveedor =
                Optional.ofNullable(proveedorRepository.getProveedorByRuc(ruc))
                        .map(p -> {
                            SolicitudBlackListDto dto = new SolicitudBlackListDto();
                            dto.setRuc(p.getRuc());
                            dto.setRazonSocial(p.getRazonSocial());
                            String estado = Optional.ofNullable(p.getIndBlackList())
                                    .map(ind -> ind.equals("1"))
                                    .map(v -> v ? "En BlackList" : "Activo")
                                    .orElse("-");
                            dto.setEstado(estado);
                            return dto;
                        });
        return oProveedor.isPresent() ? oProveedor.get() : null;
    }

    @Override
    public List<TipoSolicitudBlacklist> getListSolicitudBlackList() {
        return this.tipoSolicitudRepository.findAll();
    }

    @Override
    public List<CriteriosBlacklist> getListCriteriosByTipoSolicitud(int idTipoSolicitud) {
        return this.criterioBlackListRepository.getListCriteriosByTipoSolicitud(idTipoSolicitud);
    }

    @Override
    public List<SolicitudBlacklist> getListSolicitudesGeneradas() {

        List<SolicitudBlacklist> listaSolicitud = solicitudRepository.findByEstadoSolicitudOrderByFechaCreacionDesc(EstadoBlackListEnum.GENERADA.getCodigo());

        for (int i=0; i < listaSolicitud.size(); i++) {
            List<SolicitudAdjuntoBlacklist> listAdjunto =
                    blackListMapper.getListAdjuntosSolicitudBlackListByIdSolicitud(listaSolicitud.get(i).getIdSolicitud());
            listaSolicitud.get(i).setListAdjunto(listAdjunto);
        }

        return listaSolicitud;
    }

    @Override
    public List<SolicitudBlacklist> getListSolicitudesPendientesApprobacionByUser(Token token) {
        UserSession userSession = new UserSession();
        userSession = this.getUserSession(token);

        logger.debug("USER_SESSION_EMAIL: " + userSession.getMail());

        List<SolicitudBlacklist> listaSolicitudPendiente =
                blackListMapper.getListSolicitudPendienteAprobacionByUser(userSession.getMail(),
                EstadoBlackListEnum.PENDIENTE_APROBACION.getCodigo());

        for (int i=0; i < listaSolicitudPendiente.size(); i++) {
            Integer idSolicitud = listaSolicitudPendiente.get(i).getIdSolicitud();
            List<AprobadorSolicitud> listAprobador =
                    aprobadorSolicitudRepository.getListAprobadorByIdSolicitud(idSolicitud);

            List<SolicitudAdjuntoBlacklist> listAdjunto =
                    blackListMapper.getListAdjuntosSolicitudBlackListByIdSolicitud(idSolicitud);

            listaSolicitudPendiente.get(i).setListAdjunto(listAdjunto);
            listaSolicitudPendiente.get(i).setListAprobador(listAprobador);
        }

        return listaSolicitudPendiente;
    }

    @Override
    public String setAprobadorBlackList(SolicitudBlacklist solicitudBlackList,Token token) {
        String rptaProceso="exito";
        UserSession userSession = new UserSession();
        userSession = this.getUserSession(token);

        SolicitudBlacklist objRemote = solicitudRepository.getOne(solicitudBlackList.getIdSolicitud());

        if ((EstadoBlackListEnum.GENERADA.getCodigo()).equals(objRemote.getEstadoSolicitud())) {
            logger.debug("Iniciando transacción ..");

            //Insertar en aprobador_solicitud
            for (AprobadorSolicitud aprobador : solicitudBlackList.getListAprobador()) {
                this.aprobadorSolicitudRepository.save(new AprobadorSolicitud(
                        solicitudBlackList,
                        aprobador.getUsuario(),
                        new AprobadorSolicitudPK(solicitudBlackList.getIdSolicitud(), aprobador.getUsuario().getIdUsuario()),
                        aprobador.getOrdenAprobacion(),
                        EstadoBlackListEnum.PENDIENTE_APROBACION.getCodigo(),
                        aprobador.getIndActivoAprobacion()
                ));
            }
            logger.debug("Se asignaron los aprobadores");

            //Actualizar solicitud_blacklist
            solicitudBlackList.setAdminIdRevision(userSession.getId());
            solicitudBlackList.setAdminNameRevision(userSession.getDisplayName());
            solicitudBlackList.setAdminFechaRevision(this.fechaSistema);
            solicitudBlackList.setEstadoSolicitud(EstadoBlackListEnum.PENDIENTE_APROBACION.getCodigo());
            solicitudBlackList.setUsuarioModificacion(userSession.getId());
            solicitudBlackList.setFechaModificacion(this.fechaSistema);

            solicitudRepository.save(solicitudBlackList);
            logger.debug("Se actualizó la solicitud");

            //Enviar correos
            logger.debug("INICIO: Envio email ..");
            if ((CodigoReglaAprobacionEnum.JERARQUICA.getCodigo()).equals(solicitudBlackList.getReglaAprobacion())) {
                for (AprobadorSolicitud aprobador : solicitudBlackList.getListAprobador()) {
                    if (("1").equals(aprobador.getOrdenAprobacion())) {
                        solicitudPOAprobadorNotificacion.enviar(this.parametroMapper.getMailSetting(), aprobador, solicitudBlackList);
                    }
                    break;
                }
            }else{
                for (AprobadorSolicitud aprobador : solicitudBlackList.getListAprobador()) {
                    solicitudPOAprobadorNotificacion.enviar(this.parametroMapper.getMailSetting(), aprobador, solicitudBlackList);
                }
            }
            logger.debug("FIN: Envio email ..");

            rptaProceso = "exito";
        }else{
            logger.error("Error al asignar aprobador");
            rptaProceso = "Error al asignar aprobador";
        }

        return rptaProceso;
    }

    @Override
    public String aprobarSolicitud(Integer idSolicitud,Token token) {
        String rptaProceso="";

        UserSession userSession = new UserSession();
        userSession = this.getUserSession(token);

        SolicitudBlacklist  solicitudBlackList = solicitudRepository.getOne(idSolicitud);
        Usuario usuario = usuarioRepository.findByEmail(userSession.getMail());
        AprobadorSolicitud aprobador = aprobadorSolicitudRepository.findBySolicitudBlacklistAndUsuario(solicitudBlackList, usuario);
        List<AprobadorSolicitud> listAprobador = aprobadorSolicitudRepository.getListAprobadorByIdSolicitud(idSolicitud);


        if (("1").equals(aprobador.getIndActivoAprobacion())){
            //update estado and fecha en aprobador_solicitud
            aprobador.setEstado(EstadoBlackListEnum.APROBADA.getCodigo());
            aprobador.setFechaRespuestaAprobador(this.fechaSistema);
            aprobadorSolicitudRepository.save(aprobador);

            Boolean isComplete=false;
            for(AprobadorSolicitud aprobadorSolicitud : listAprobador){
                if ((EstadoBlackListEnum.APROBADA.getCodigo()).equals(aprobadorSolicitud.getEstado())){
                    isComplete=true;
                }else{
                    isComplete=false;
                    break;
                }
            }

            if (isComplete){

                //actualizarProveedor
                Proveedor proveedor = proveedorRepository.getOne(solicitudBlackList.getProveedor().getIdProveedor());
                if((CodigoTipoSolicitudBlackListEnum.REGISTRO_PO.getCodigo()).equals
                        (solicitudBlackList.getCriteriosBlacklist().getTipoSolicitudBlacklist().getCodigoTipoSolicitud())){

                    proveedor.setIndBlackList("1");
                    proveedorRepository.save(proveedor);

                    //bloquear proveedor en sap
                    SapLog response = this.proveedorWebService.bloquearProveedorSAP(proveedor);
                    if (!(WebServiceConstant.RESPUESTA_OK).equals(response.getCode())){
                        throw new PortalException(response.getMesaj());
                    }else{
                        logger.debug("El proveedor ha sido bloqueado en Sap");
                    }

                }if((CodigoTipoSolicitudBlackListEnum.RETIRO_PO.getCodigo()).equals
                        (solicitudBlackList.getCriteriosBlacklist().getTipoSolicitudBlacklist().getCodigoTipoSolicitud())){

                    proveedor.setIndBlackList("0");
                    proveedorRepository.save(proveedor);
                }
                //actualizar Solicitud blacklist
                solicitudBlackList.setEstadoSolicitud(EstadoBlackListEnum.APROBADA.getCodigo());
                solicitudRepository.save(solicitudBlackList);

                //enviar correo al Administrador (usuario que asigno los aprobadores)
                solicitudPOFinalizacionNotificacion.enviar(this.parametroMapper.getMailSetting(), solicitudBlackList);

            }else{
                if ((CodigoReglaAprobacionEnum.JERARQUICA.getCodigo()).equals(solicitudBlackList.getReglaAprobacion())){
                    /*String subjectMail = solicitudBlackList.getCriteriosBlacklist().getTipoSolicitudBlacklist().getDescripcion() + ": " +
                            solicitudBlackList.getProveedor().getRuc();*/

                    logger.debug("La solicitud posee regla de Aprobacion: JERARQUICA");
                    Integer ordenActual = Integer.parseInt(aprobador.getOrdenAprobacion());
                    Integer ordenNext = ordenActual + 1;

                    if (ordenNext <= listAprobador.size()){
                        AprobadorSolicitud aprobadorSiguiente = aprobadorSolicitudRepository.findBySolicitudBlacklistAndOrdenAprobacion(solicitudBlackList, String.valueOf(ordenNext));

                        aprobador.setIndActivoAprobacion("");
                        aprobadorSolicitudRepository.save(aprobador);
                        aprobadorSiguiente.setIndActivoAprobacion("1");
                        aprobadorSolicitudRepository.save(aprobadorSiguiente);

                        //Enviar Correo al siguiente aprobador
                        solicitudPOAprobadorNotificacion.enviar(this.parametroMapper.getMailSetting(), aprobadorSiguiente, solicitudBlackList);

                        logger.debug("Se habilitó al siguiente aprobador: ");
                    }
                }
            }
            rptaProceso="exito";

        }else{
            logger.debug("La aprobación/ rechazo de la solicitud es de acuerdo a orden de aprobacion");
            rptaProceso="La aprobación/ rechazo de la solicitud es de acuerdo a orden de aprobacion";
        }
        return rptaProceso;
    }

    @Override
    public String rechazarSolicitud(SolicitudBlacklist obj,Token token) {
        String rptaProceso ="exito";

        UserSession userSession = new UserSession();
        userSession = this.getUserSession(token);

        SolicitudBlacklist  solicitudBlackList = solicitudRepository.getOne(obj.getIdSolicitud());
        Usuario usuario = usuarioRepository.findByEmail(userSession.getMail());
        AprobadorSolicitud aprobador = aprobadorSolicitudRepository.findBySolicitudBlacklistAndUsuario(solicitudBlackList, usuario);

        /*String subjectMail = "Estado Final: " + solicitudBlackList.getCriteriosBlacklist().getTipoSolicitudBlacklist().getDescripcion() + ": " +
                solicitudBlackList.getProveedor().getRuc();*/
        Map<String, Object> mapSmtp = new HashMap<String, Object>();
        mapSmtp = this.getConfigSmtp();
        Map<String, Object> mapTemplate;

        if (("1").equals(aprobador.getIndActivoAprobacion())){
            //update estado and fecha en aprobador_solicitud
            aprobador.setEstado(EstadoBlackListEnum.RECHAZADA.getCodigo());
            aprobador.setFechaRespuestaAprobador(this.fechaSistema);
            aprobadorSolicitudRepository.save(aprobador);

            solicitudBlackList.setEstadoSolicitud(EstadoBlackListEnum.RECHAZADA.getCodigo());
            solicitudRepository.save(solicitudBlackList);

            //enviar correo al usuarioCreacion
            mapTemplate = new HashMap<String, Object>();
            mapTemplate.put("nombreUsuarioCreacion", solicitudBlackList.getUsuarioCreacionName());
            mapTemplate.put("tipoSolicitud", solicitudBlackList.getCriteriosBlacklist().getTipoSolicitudBlacklist().getDescripcion());
            mapTemplate.put("rucProveedor", solicitudBlackList.getProveedor().getRuc());
            mapTemplate.put("nombreProveedor", solicitudBlackList.getProveedor().getRazonSocial());
            mapTemplate.put("estadoSolicitud", EstadoBlackListEnum.RECHAZADA.getCodigo());

            /*
            mailService.sendEmail(mapTemplate, mapSmtp,
                    solicitudBlackList.getUsuarioCreacionEmail(),
                    subjectMail, "TmpFinalizacionSolicitudBlacklist.htm");
            */
            rptaProceso="exito";

        }else{
            logger.debug("La aprobación/ rechazo de la solicitud es dea cuerdo a orden de aprobacion");
            rptaProceso="La aprobación/ rechazo de la solicitud es de acuerdo a orden de aprobacion";
        }
        return rptaProceso;
    }

    @Override
    public String rechazarSolicitudAdmin(SolicitudBlacklist obj,Token token) {
        String rptaProceso="exito";

        UserSession userSession = new UserSession();
        userSession =this.getUserSession(token);

        SolicitudBlacklist  solicitudBlackList = solicitudRepository.getOne(obj.getIdSolicitud());
        Usuario usuario = usuarioRepository.findByEmail(userSession.getMail());
        /*String subjectMail = "Estado Final: " + solicitudBlackList.getCriteriosBlacklist().getTipoSolicitudBlacklist().getDescripcion() + ": " +
                solicitudBlackList.getProveedor().getRuc();*/
        Map<String, Object> mapSmtp = new HashMap<String, Object>();
        mapSmtp = this.getConfigSmtp();
        Map<String, Object> mapTemplate;

        solicitudBlackList.setEstadoSolicitud(EstadoBlackListEnum.RECHAZADA.getCodigo());
        solicitudBlackList.setIndRechazoAdmin("1");
        solicitudBlackList.setMotivoRechazoAdmin(obj.getMotivoRechazoAdmin());
        solicitudRepository.save(solicitudBlackList);

        //enviar correo al usuarioCreacion
        mapTemplate = new HashMap<String, Object>();
        mapTemplate.put("nombreUsuarioCreacion", solicitudBlackList.getUsuarioCreacionName());
        mapTemplate.put("tipoSolicitud", solicitudBlackList.getCriteriosBlacklist().getTipoSolicitudBlacklist().getDescripcion());
        mapTemplate.put("rucProveedor", solicitudBlackList.getProveedor().getRuc());
        mapTemplate.put("nombreProveedor", solicitudBlackList.getProveedor().getRazonSocial());
        mapTemplate.put("estadoSolicitud", EstadoBlackListEnum.RECHAZADA.getCodigo());

        /*
        mailService.sendEmail(mapTemplate,
                mapSmtp, solicitudBlackList.getUsuarioCreacionEmail(),
                subjectMail, "TmpFinalizacionSolicitudBlacklist.htm");

        */
        rptaProceso="exito";

        return rptaProceso;
    }
}
