package com.incloud.hcp.service;

import com.incloud.hcp.domain.almacen.LugarEntrega;

import java.util.List;

public interface LugarEntregaService {

    List<LugarEntrega> getLugarEntrega() throws Exception;
}
