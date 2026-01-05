package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.domain.almacen.GuiaDespachoDetalle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrador on 09/11/2017.
 */
@Mapper
@Repository
public interface GuiaDespachoDetalleMapper {
    List<GuiaDespachoDetalle> getAllGuiaDepachoDetalleByIdMap(@Param("id") Integer id);

}
