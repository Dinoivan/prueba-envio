package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.dto.estadistico.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReporteEstadisticoMapper {

    List<ReporteEstadisticoAdjudicacionDto> reporteEstadisticoAdjudicacion(ReporteEstadisticoAdjudicacionEntradaDto bean);

    List<ReporteEstadisticoParticipacionDto> reporteEstadisticoParticipacion(ReporteEstadisticoParticipacionEntradaDto bean);

    List<Licitacion> reporteStatusPeticionOferta(ReporteStatusPeticionOfertaIntermedioDto bean);

    List<Licitacion> reporteStatusPeticionOfertaPaginado(ReporteStatusPeticionOfertaIntermedioDto bean);

}
