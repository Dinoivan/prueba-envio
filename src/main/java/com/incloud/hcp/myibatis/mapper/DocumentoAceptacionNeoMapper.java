package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.domain.DocumentoAceptacion;
import com.incloud.hcp.dto.DocumentoAceptacionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface DocumentoAceptacionNeoMapper {
    List<DocumentoAceptacionDto> getDocuAcepPorFecsRucPg(@Param("fechaInicio") Date fechaInicio,
                                                         @Param("fechaFin") Date fechaFin,
                                                         @Param("ruc") String ruc,
                                                         @Param("nroOC") String nroOC,
                                                         @Param("nroGP") String nroGP,
                                                         @Param("nroRegistros") Integer nroRegistros,
                                                         @Param("paginaMostrar") Integer paginaMostrar,
                                                         @Param("tipoDocumento") Integer tipoDocumento,
                                                         @Param("flagPaginador") String flagPaginador);

    List<DocumentoAceptacionDto> getDocuAcepPorFecPrefactura(@Param("fechaInicio") Date fechaInicio,
                                                             @Param("fechaFin") Date fechaFin,
                                                             @Param("ruc") String ruc,
                                                             @Param("numeroDocumentoAceptacion") String numeroDocumentoAceptacion,
                                                             @Param("numeroOrdenCompra") String numeroOrdenCompra,
                                                             @Param("numeroGuiaProveedor") String numeroGuiaProveedor,
                                                             @Param("fechaContabilizacionDesde") Date fechaContabilizacionDesde,
                                                             @Param("fechaContabilizacionHasta") Date fechaContabilizacionHasta);
}
