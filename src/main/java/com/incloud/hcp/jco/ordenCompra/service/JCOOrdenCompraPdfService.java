package com.incloud.hcp.jco.ordenCompra.service;


import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPdfDto;

public interface JCOOrdenCompraPdfService {

    OrdenCompraPdfDto extraerBloquesOrdenCompraDtoRFC(String numeroOrdenCompra) throws Exception;
    OrdenCompraPdfDto extraerOrdenCompraPdfDtoRFC(String numeroOrdenCompra) throws Exception;


}
