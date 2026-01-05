package com.incloud.hcp.jco.ordenCompra.service;


import com.incloud.hcp.dto.InfoMessage;
import com.incloud.hcp.dto.OrdenDespachoSapDataDto;

public interface JCOOrdenDespachoPublicarOneService {

    InfoMessage extraerOneOrdenDespachoRFC(String numeroOrdenCompra, boolean enviarCorreoPublicacion) throws Exception;

    OrdenDespachoSapDataDto extraerDataOneOrdenDespachoRFC(String numeroOrdenCompra) throws Exception;

}
