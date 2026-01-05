package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.bean.OrdenCompraActivoCustom;
import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.dto.FiltroOrdenCompraDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrador on 09/11/2017.
 */
@Mapper
@Repository
public interface OrdenCompraMapper {


    List<OrdenCompraActivoCustom> getListaOrdenCompraMultiplesActivos();
    List<OrdenCompra> getListaOrdenCompra(@Param("dto") FiltroOrdenCompraDto dto);


}
