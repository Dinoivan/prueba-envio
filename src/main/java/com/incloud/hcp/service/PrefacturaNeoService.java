package com.incloud.hcp.service;

import com.incloud.hcp.dto.PrefacturaAprobacionDTO;

import java.util.Date;
import java.util.List;

public interface PrefacturaNeoService {
    List<PrefacturaAprobacionDTO> getPrefacturaListPg(Date fechaEmisionInicio, Date fechaEmisionFin,
                                                      Date fechaEntradaInicio, Date fechaEntradaFin,
                                                      String ruc,
                                                      String referencia,
                                                      String comprador,
                                                      String centro,
                                                      Integer idEstado,
                                                      Integer nroRegistros,
                                                      Integer paginaMostrar,
                                                      String flagPaginador) throws Exception;
}
