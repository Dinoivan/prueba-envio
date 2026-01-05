package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.CcomparativoProveedor;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.TipoProveedor;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.service.CcomparativoServiceNew;
import com.incloud.hcp.service._framework.BaseServiceImpl;
import com.incloud.hcp.util.Constant;
import com.incloud.hcp.wsdl.inside.InSiteResponse;
import com.incloud.hcp.wsdl.inside.InSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class CcomparativoServiceNewImpl extends BaseServiceImpl implements CcomparativoServiceNew {

    private final int NRO_EJECUCIONES = 5;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private InSiteService inSiteService;


    public boolean verificarDataSunat(List<CcomparativoProveedor> ccomparativoProveedorList) throws Exception {
        boolean okSunat = true;
        for (CcomparativoProveedor bean : ccomparativoProveedorList) {
            Proveedor proveedor = this.proveedorRepository.getOne(bean.getIdProveedor().getIdProveedor());
            TipoProveedor tipoProveedor = proveedor.getTipoProveedor();
            if (tipoProveedor.getCodigoSap().equals(Constant.SAP_TIPO_PROVEEDOR_NACIONAL)) {

                InSiteResponse responseInSite = null;
                for (int contador = 0; contador < NRO_EJECUCIONES; contador++) {
                    try {
                        responseInSite = inSiteService.getConsultaRuc(proveedor.getRuc());
                        break;
                    } catch (Exception e) {
                        if (contador == NRO_EJECUCIONES - 1) {

                        }
                    }
                }
                if (responseInSite != null) {
                    proveedor.setIndHabidoSunat(Optional.ofNullable(responseInSite.getCondicion()).map(activo -> activo ? "1" : "0").orElse("0"));
                    proveedor.setIndActivoSunat(Optional.ofNullable(responseInSite.getEstado()).map(habido -> habido ? "1" : "0").orElse("0"));
                    this.proveedorRepository.save(proveedor);

                    if (!responseInSite.getCondicion()) {
                        okSunat = false;
                    }
                    if (!responseInSite.getEstado()) {
                        okSunat = false;
                    }
                    this.proveedorRepository.save(proveedor);
                }

            }

        }
        return okSunat;
    }

}
