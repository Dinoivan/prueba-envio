package com.incloud.hcp.wsdl.inside;

/**
 * Created by Administrador on 09/11/2017.
 */
public interface InSiteService {
    InSiteResponse getConsultaRuc(String ruc) throws InSiteException;
}
