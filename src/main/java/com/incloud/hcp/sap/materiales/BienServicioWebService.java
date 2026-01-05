package com.incloud.hcp.sap.materiales;

/**
 * Created by USER on 22/09/2017.
 */
public interface BienServicioWebService {

    BienServicioResponse sincronizarMateriales(String fechaIni, String url, String user, String pass) throws Exception;

}
