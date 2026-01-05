package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.dto.CanalContactoDto;
import com.incloud.hcp.dto.reporte.SucursalDtoExcel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrador on 23/08/2017.
 */
@Mapper
@Repository
public interface ContactoMapper {
    List<CanalContactoDto> getListContactoByIdProveedor(@Param("idProveedor") Integer idProveedor);

    List<SucursalDtoExcel> getListAllSucursalContactoProveedor();
}
