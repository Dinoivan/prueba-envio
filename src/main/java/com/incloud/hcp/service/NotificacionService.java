package com.incloud.hcp.service;

import com.incloud.hcp.domain.PreRegistroProveedor;
import com.incloud.hcp.domain.SolicitudBlacklist;

/**
 * Created by Administrador on 05/09/2017.
 */
public interface NotificacionService {
    void proveedorRegistrado(PreRegistroProveedor preRegistroProveedor);

    void solicitudBlackList(SolicitudBlacklist solicitud);
}
