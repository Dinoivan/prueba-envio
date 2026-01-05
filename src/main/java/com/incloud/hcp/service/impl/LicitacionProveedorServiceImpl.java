package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.*;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.service.LicitacionProveedorService;
import com.incloud.hcp.util.Constant;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.constant.CotizacionConstant;
import com.incloud.hcp.util.constant.LicitacionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class LicitacionProveedorServiceImpl implements LicitacionProveedorService {


    @Autowired
    private LicitacionProveedorRepository licitacionProveedorRepository;

    @Autowired
    private LicitacionSubetapaRepository licitacionSubetapaRepository;

    @Autowired
    private LicitacionRepository licitacionRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private CotizacionRepository cotizacionRepository;


    public LicitacionProveedorRenegociacion validarNuevaRenegociacion(Integer idLicitacion, Integer idProveedor) {
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        Proveedor proveedor = this.proveedorRepository.getOne(idProveedor);
        if (licitacion.getEstadoLicitacion().equals(LicitacionConstant.ESTADO_POR_EVALUAR)) {
            throw new PortalException("La Licitación se encuentra en estado POR EVALUAR, ya no es posible realizar Renegociación");
        }

        LicitacionProveedor licitacionProveedor = this.licitacionProveedorRepository.
                getByLicitacionAndProveedor(licitacion, proveedor);
        if (!Optional.ofNullable(licitacionProveedor).isPresent()) {
            throw new PortalException("No se encontró registro en la tabla LicitacionProveedor");
        }
        Cotizacion cotizacion = this.cotizacionRepository.getByLicitacionAndProveedor(licitacion, proveedor);
        if (!Optional.ofNullable(cotizacion).isPresent()) {
            throw new PortalException("El Proveedor todavia no ha generado la Respuesta de Licitación respectiva");
        }
        if (!cotizacion.getEstadoCotizacion().equals(CotizacionConstant.ESTADO_ENVIADA)) {
            throw new PortalException("No se posible renegociar con dicho Proveedor, hasta que no ENVIE la Respuesta de Licitación");
        }

        String indSiParticipa = licitacionProveedor.getIndSiParticipa();
        if (Optional.ofNullable(indSiParticipa).isPresent()) {
            if (indSiParticipa.equals(Constant.N)) {
                throw new PortalException("No se posible renegociar con dicho Proveedor, porque NO PARTICIPA en la Licitación");
            }
        }
        Date fechaCierre = licitacionProveedor.getFechaCierreRecepcion();

        /* Devolviendo valores */
        LicitacionProveedorRenegociacion licitacionProveedorRenegociacion = new LicitacionProveedorRenegociacion();
        licitacionProveedorRenegociacion.setIdLicitacion(licitacionProveedor);
        licitacionProveedorRenegociacion.setFechaCierreRecepcion(fechaCierre);
        licitacionProveedorRenegociacion.setMotivoRenegociacion("");
        licitacionProveedorRenegociacion.setId(null);
        String fechaCierreString = DateUtils.convertDateToString("dd/MM/yyyy H:mm:ss", fechaCierre);
        licitacionProveedorRenegociacion.setFechaCierreRecepcionString(fechaCierreString);
        return licitacionProveedorRenegociacion;
    }
}
