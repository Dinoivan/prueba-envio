package com.incloud.hcp.jco.contratoMarco.service;

import com.incloud.hcp.domain.CcomparativoAdjudicado;
import com.incloud.hcp.domain.CcomparativoProveedor;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.jco.contratoMarco.dto.ContratoMarcoResponseDto;

import java.util.List;

public interface JCOContratoMarcoService {

    ContratoMarcoResponseDto grabarContratoMarco(
            CcomparativoProveedor ccomparativoProveedor,
            Proveedor proveedor,
            Usuario usuario,
            List<CcomparativoAdjudicado> ccomparativoAdjudicadoList) throws Exception;
}
