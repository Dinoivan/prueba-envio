package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.bean.ProveedorCustom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by USER on 30/09/2017.
 */
@Mapper
@Repository
public interface LicitacionProveedorMapper {

    List<ProveedorCustom> getListProveedorByIdLicitacion(@Param("idLicitacion") Integer idLicitacion);

    /** JRAMOS - UPDATE */
    ProveedorCustom getProveedorCustomByIdProveedor(@Param("idProveedor") Integer idProveedor, @Param("idLicitacion") Integer idLicitacion);

    Integer countByLicitacionesGanadas(@Param("idProveedor") Integer idProveedor);

}
