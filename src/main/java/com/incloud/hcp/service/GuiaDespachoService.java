package com.incloud.hcp.service;

import com.incloud.hcp.dto.GuiaDespachoDto;

public interface GuiaDespachoService {
    Integer ingresarNuevaGuiaDespacho(GuiaDespachoDto guiaDespachoDto, String ruc);
    Integer descartarGuiaDespacho(Integer id, String ruc);
    Integer rechazarGuiaDespacho(Integer id, String motivo, String codigoUsuario);

    String aprobarGuiaDespacho(Integer id, GuiaDespachoDto guiaDespachoDto, String ruc) throws Exception;
}
