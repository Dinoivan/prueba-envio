package com.incloud.hcp.sap.solped;

/**
 * Created by USER on 12/09/2017.
 */
public interface SolpedWebService {

    SolpedResponse getSolpedResponseByCodigo(String codigo, String url, String user, String pass);

}
