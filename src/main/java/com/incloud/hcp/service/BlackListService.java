package com.incloud.hcp.service;

import com.incloud.hcp.domain.CriteriosBlacklist;
import com.incloud.hcp.domain.SolicitudBlacklist;
import com.incloud.hcp.domain.TipoSolicitudBlacklist;
import com.incloud.hcp.dto.SolicitudBlackListDto;
import com.incloud.hcp.exception.PortalException;
import com.sap.cloud.security.xsuaa.token.Token;

import java.util.List;

/**
 * Created by Administrador on 12/09/2017.
 */
public interface BlackListService {
    SolicitudBlackListDto registrar(SolicitudBlackListDto solicitud) throws PortalException;

    SolicitudBlackListDto getSociedadByRuc(String ruc) throws PortalException;

    List<TipoSolicitudBlacklist> getListSolicitudBlackList();

    List<CriteriosBlacklist> getListCriteriosByTipoSolicitud(int idTipoSolicitud);

    List<SolicitudBlacklist> getListSolicitudesGeneradas();

    List<SolicitudBlacklist> getListSolicitudesPendientesApprobacionByUser(Token token);

    String setAprobadorBlackList(SolicitudBlacklist solicitud, Token token);

    String aprobarSolicitud(Integer idSolicitud, Token token);

    String rechazarSolicitud(SolicitudBlacklist solicitud,Token token);

    String rechazarSolicitudAdmin(SolicitudBlacklist solicitud,Token token);
}
