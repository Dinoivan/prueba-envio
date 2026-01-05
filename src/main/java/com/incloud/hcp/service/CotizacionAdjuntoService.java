package com.incloud.hcp.service;

import com.incloud.hcp.domain.Cotizacion;

/**
 * Created by USER on 06/11/2017.
 */
public interface CotizacionAdjuntoService {

    public void deleteCotizacionAdjuntoByCotizacionArchivoId(Cotizacion cotizacion, String archivoId);

}
