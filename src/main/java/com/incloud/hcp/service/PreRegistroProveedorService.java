package com.incloud.hcp.service;

import com.incloud.hcp.domain.PreRegistroProveedor;

import java.util.List;

/**
 * Created by Administrador on 04/09/2017.
 */
public interface PreRegistroProveedorService {
    PreRegistroProveedor guardar(PreRegistroProveedor preRegistroProveedor) throws Exception;

    PreRegistroProveedor updateSearchSunat(PreRegistroProveedor preRegistroProveedor);

    PreRegistroProveedor getPreRegistroProveedorByIdHcp(String idHcp);

    PreRegistroProveedor getPreRegistroProveedorById(Integer idRegistro);

    List<PreRegistroProveedor> getListPreRegistroPendiente();

    List<PreRegistroProveedor> getListPreRegistroPendiente(String idHcp);

    PreRegistroProveedor aprobarSolicitud(Integer idPreRegistro);

    PreRegistroProveedor reprobarSolicitud(Integer idPreRegistro);

    PreRegistroProveedor getByRuc(String ruc);

}
