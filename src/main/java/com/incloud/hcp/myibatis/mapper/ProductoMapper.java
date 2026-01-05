package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.domain.BienServicio;
import com.incloud.hcp.dto.reporte.ProductoDtoExcel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by USER on 05/09/2017.
 */
@Mapper
@Repository
public interface ProductoMapper {
    List<ProductoDtoExcel> getListAllProductoProveedor();

    List<BienServicio> getBienServicioCodigoSap(@Param("codigoSap") String codigoSap);
}
