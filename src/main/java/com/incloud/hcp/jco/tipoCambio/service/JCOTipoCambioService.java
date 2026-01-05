package com.incloud.hcp.jco.tipoCambio.service;



import com.incloud.hcp.jco.tipoCambio.dto.TipoCambioRFCResponseDto;

public interface JCOTipoCambioService {

    TipoCambioRFCResponseDto actualizarTipoCambio(String fecha) throws Exception;

}
