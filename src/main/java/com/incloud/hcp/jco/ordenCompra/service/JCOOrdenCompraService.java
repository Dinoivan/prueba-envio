package com.incloud.hcp.jco.ordenCompra.service;

import com.incloud.hcp.domain.CcomparativoAdjudicado;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraResponseDto;

import java.util.List;

public interface JCOOrdenCompraService {

    OrdenCompraResponseDto grabarOrdenCompra(
            String claseDocumento,
            Proveedor proveedor,
            Usuario usuario,
            List<CcomparativoAdjudicado> ccomparativoAdjudicadoList) throws Exception;

}
