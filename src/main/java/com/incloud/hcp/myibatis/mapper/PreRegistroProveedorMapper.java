package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.domain.PreRegistroProveedor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrador on 23/11/2017.
 */
@Mapper
@Repository
public interface PreRegistroProveedorMapper {
    void guardarSolicitud(@Param("registro") PreRegistroProveedor registro);
}
