package com.incloud.hcp.service;

import com.incloud.hcp.bean.TileDynamic;
import com.incloud.hcp.domain.Proveedor;

/**
 * Created by Administrador on 24/10/2017.
 */
public interface TileDynamicService {
    TileDynamic getTileToAvanceHomologacion(Proveedor proveedor);

    public TileDynamic getTileLicitacionesPorResponder(Proveedor proveedor);

    public TileDynamic getTileHistoricoLicitacionesPorResponder(Proveedor proveedor);

    public TileDynamic getTileCuadroComparativo();

    public TileDynamic getTileHistoricoCuadroComparativo();
}
