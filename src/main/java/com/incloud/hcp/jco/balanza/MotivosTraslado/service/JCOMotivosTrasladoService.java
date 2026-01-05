package com.incloud.hcp.jco.balanza.MotivosTraslado.service;

import com.incloud.hcp.jco.balanza.MotivosTraslado.dto.*;

import java.util.List;

public interface JCOMotivosTrasladoService {
    List<MotivosTrasladoResponseDTO> extraerMotivoTrasladoListRFC() throws Exception;
    List<MotivosOtroResponseDTO> extraerMotivoOtrosListRFC() throws Exception;
    List<ModoTransporteResponseDTO> extraerModoTransporteListRFC() throws Exception;
    List<TipoMovimientoResponseDTO> extraerTipoMovimientoListRFC() throws Exception;
    List<UnidadMedidaResponseDTO> extraerUnidadMedidaListRFC() throws Exception;
    List<IndicadorServicioResponseDTO> extraerIndicadorServicioListRFC() throws Exception;
    List<TipoLocacionDTO> tipoLocacion() throws Exception;
    List<TipoLocacionDTO> tipoPuertoList() throws Exception;
    List<PuertoAeropuertoDTO> puertoLlegadaList() throws Exception;
    List<PuertoAeropuertoDTO> aeropuertoLlegadaList() throws Exception;

}
