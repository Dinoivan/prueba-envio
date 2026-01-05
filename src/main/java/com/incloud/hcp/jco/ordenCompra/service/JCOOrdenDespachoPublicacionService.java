package com.incloud.hcp.jco.ordenCompra.service;


public interface JCOOrdenDespachoPublicacionService {

    void extraerOrdenDespachoListRFC(String fechaInicio, String fechaFin, boolean enviarCorreoPublicacion) throws Exception;

    boolean toggleOrdenDespachoExtractionProcessingState();

    boolean currentOrdenDespachoExtractionProcessingState();

}
