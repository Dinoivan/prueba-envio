package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.domain.Prefactura;
import com.incloud.hcp.dto.PrefacturaAprobacionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface PrefacturaNeoMapper {
    List<PrefacturaAprobacionDTO> getPrefacturaListPg(@Param("fechaEmisionInicio") Date fechaEmisionInicio,
                                         @Param("fechaEmisionFin") Date fechaEmisionFin,
                                         @Param("fechaEntradaInicio") Date fechaEntradaInicio,
                                         @Param("fechaEntradaFin") Date fechaEntradaFin,
                                         @Param("ruc") String ruc,
                                         @Param("referencia") String referencia,
                                         @Param("comprador") String comprador,
                                         @Param("centro") String centro,
                                         @Param("idEstado") Integer idEstado,
                                         @Param("nroRegistros") Integer nroRegistros,
                                         @Param("paginaMostrar") Integer paginaMostrar,
                                         @Param("flagPaginador") String flagPaginador);
}
