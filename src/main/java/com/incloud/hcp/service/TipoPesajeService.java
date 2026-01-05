package com.incloud.hcp.service;
import com.incloud.hcp.domain.balanza.CentroAlmacenBalanza;
import com.incloud.hcp.domain.balanza.ProveedorBLZ;
import com.incloud.hcp.domain.balanza.TipoPesaje;

import java.util.List;

public interface TipoPesajeService {
    List<TipoPesaje> getAllTipoPesaje();
    TipoPesaje cambiarEstado(Integer tipoPesajeId) throws Exception;
    TipoPesaje eliminarEstado(Integer tipoPesajeId) throws Exception;
}
