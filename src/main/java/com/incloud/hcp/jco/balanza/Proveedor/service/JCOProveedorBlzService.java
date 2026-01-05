package com.incloud.hcp.jco.balanza.Proveedor.service;

import com.incloud.hcp.domain.balanza.ProveedorBLZ;
import com.incloud.hcp.jco.balanza.Proveedor.dto.ProveedorBlzFiltroBusquedaDTO;
import com.incloud.hcp.jco.balanza.Proveedor.dto.ProveedorBlzFiltroDTO;

import java.util.List;

public interface JCOProveedorBlzService {

    void extraerProveedorListRFC(ProveedorBlzFiltroDTO dto) throws Exception;

    List<ProveedorBLZ> getAllProveedoresBlz();
    List<ProveedorBLZ> getAllProveedoresBlzByFiltro(ProveedorBlzFiltroBusquedaDTO dto);
    List<ProveedorBLZ> getProveedorBLZByAcreedor(Integer idGuia);
    ProveedorBLZ cambiarEstado(Long proveedorblzId) throws Exception;
}
