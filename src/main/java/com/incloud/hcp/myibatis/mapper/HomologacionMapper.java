package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.domain.HomologacionRespuesta;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.ProveedorVerNotaDto;
import com.incloud.hcp.dto.homologacion.LineaComercialHomologacionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrador on 28/09/2017.
 */
@Mapper
@Repository
public interface HomologacionMapper {

    void evaluarProveedorVerNota(@Param("proveedorVerNota") ProveedorVerNotaDto proveedorVerNota);

    void evaluarProveedor(@Param("proveedor") Proveedor proveedor);

    void getAvanceHomologacionByIdProveedor(@Param("proveedor") Proveedor proveedor);

    List<LineaComercialHomologacionDto> getListHomologacionByIdProveedor(@Param("idProveedor") Integer idProveedor);
    List<LineaComercialHomologacionDto> getListHomologacionByIdProveedorResponder(@Param("idProveedor") Integer idProveedor);

    List<HomologacionRespuesta> getListHomologacionExcel();
}
