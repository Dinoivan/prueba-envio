package com.incloud.hcp.service;

import com.incloud.hcp.domain.CcomparativoProveedor;
import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.CComparativoAdjudicarDto;

import java.util.List;

/**
 * Created by USER on 18/09/2017.
 */
public interface CcomparativoService {

    CComparativoAdjudicarDto adjudicar(CComparativoAdjudicarDto dto) throws Exception;

    List<CcomparativoProveedor> adjudicarSAPErrores(Integer idLicitacion, CComparativoAdjudicarDto dto) throws Exception;

    List<CcomparativoProveedor> adjudicarSAPErrores(CComparativoAdjudicarDto dto) throws Exception;

    void enviarCuadroComparativo(Licitacion licitacion, List<Proveedor> listaProveedor);

    void enviarCorreoAgradecimiento(Licitacion licitacion) throws Exception;
}
