package com.incloud.hcp.jco.ordenCompra.service;


import com.incloud.hcp.dto.InfoMessage;
import com.incloud.hcp.dto.OrdenCompraSapDataDto;

public interface JCOOrdenCompraPublicarOneService {

    InfoMessage extraerOneOrdenCompraRFC(String numeroOrdenCompra, boolean enviarCorreoPublicacion) throws Exception;

    OrdenCompraSapDataDto extraerDataOneOrdenCompraRFC(String numeroOrdenCompra) throws Exception;

}
