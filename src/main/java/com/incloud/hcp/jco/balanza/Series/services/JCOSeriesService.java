package com.incloud.hcp.jco.balanza.Series.services;

import com.incloud.hcp.jco.balanza.Series.dto.SerieConsultaResponse;

import java.util.List;
public interface JCOSeriesService {
    List<SerieConsultaResponse> consultaSerie(String serieWerks) throws Exception;
}
