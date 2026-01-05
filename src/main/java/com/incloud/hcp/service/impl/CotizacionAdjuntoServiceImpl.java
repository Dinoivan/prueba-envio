package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.Cotizacion;
import com.incloud.hcp.repository.CotizacionAdjuntoRepository;
import com.incloud.hcp.service.CotizacionAdjuntoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by USER on 06/11/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class CotizacionAdjuntoServiceImpl implements CotizacionAdjuntoService {

    @Autowired
    private CotizacionAdjuntoRepository cotizacionAdjuntoRepository;

    public void deleteCotizacionAdjuntoByCotizacionArchivoId(Cotizacion cotizacion, String archivoId) {
        this.cotizacionAdjuntoRepository.deleteCotizacionAdjuntoByCotizacionArchivoId(cotizacion, archivoId);
    }
}
