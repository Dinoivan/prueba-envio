package com.incloud.hcp.jco.balanza.Transporte.service;

import com.incloud.hcp.jco.balanza.Transporte.dto.TransporteResponseDTO;

public interface JCOTransporteService {
    void extraerTransporteListRFC(boolean extraerTransporte) throws Exception;
    TransporteResponseDTO actualizaTransporte(TransporteResponseDTO grabarTransporte) throws Exception;

    TransporteResponseDTO grabarTransporte(TransporteResponseDTO creaTransporte) throws Exception;


}
