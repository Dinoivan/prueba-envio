package com.incloud.hcp.ws.sunat.service;

import com.incloud.hcp.ws.sunat.bean.GetStatusInput;
import com.incloud.hcp.ws.sunat.bean.GetStatusOutput;

public interface IStatusComprobante {

    GetStatusOutput getStatusComprobante(GetStatusInput input) throws Exception;

}
