package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.PreRegistroProveedor;
import com.incloud.hcp.domain.SolicitudBlacklist;
import com.incloud.hcp.service.NotificacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrador on 05/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class NotificacionServiceImpl implements NotificacionService {
    @Override
    public void proveedorRegistrado(PreRegistroProveedor preRegistroProveedor){
        // NOTIFICACION ENVIADA AL ADMIN
    }

    @Override
    public void solicitudBlackList(SolicitudBlacklist solicitud) {
        // NOTIFICACION ENVIADA AL ADMIN
    }
}
