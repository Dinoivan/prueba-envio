package com.incloud.hcp.jco.ordenCompra.service;


public interface JCOOrdenCompraPublicacionService {

    void extraerOrdenCompraListRFC(String fechaInicio, String fechaFin, boolean enviarCorreoPublicacion) throws Exception;

    boolean toggleOrdenCompraExtractionProcessingState();

    boolean currentOrdenCompraExtractionProcessingState();

}
