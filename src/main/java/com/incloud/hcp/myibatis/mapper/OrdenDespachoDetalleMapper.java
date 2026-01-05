package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.dto.OrdenDespachoDetalleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrador on 09/11/2017.
 */
@Mapper
@Repository
public interface OrdenDespachoDetalleMapper {
    List<OrdenDespachoDetalleDto> getAllByIdOrdenDespachoLiberada(@Param("numeroOrdenCompra") String numeroOrdenCompra,
                                                                  @Param("ruc") String ruc);

}
