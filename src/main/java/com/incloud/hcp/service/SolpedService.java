package com.incloud.hcp.service;

import com.incloud.hcp.sap.solped.SolpedResponse;

/**
 * Created by USER on 11/09/2017.
 */
public interface SolpedService {

    SolpedResponse getSolpedResponseByCodigo(String codigo);

}
