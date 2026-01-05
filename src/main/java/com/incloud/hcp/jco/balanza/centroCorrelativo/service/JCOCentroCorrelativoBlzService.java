package com.incloud.hcp.jco.balanza.centroCorrelativo.service;

import com.incloud.hcp.jco.balanza.centroCorrelativo.dto.SapTableCentroCorrelativoBlz;

import java.util.List;

public interface JCOCentroCorrelativoBlzService {
   public List<SapTableCentroCorrelativoBlz> extraerCorrelativo(String centro) throws Exception;
}
