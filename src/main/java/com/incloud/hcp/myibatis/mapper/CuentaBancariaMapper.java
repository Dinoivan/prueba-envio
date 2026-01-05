package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.bean.CuentaBancaria;
import com.incloud.hcp.dto.reporte.CuentaBancoDtoExcel;
import com.incloud.hcp.util.constant.ParametroConstant;
import com.incloud.hcp.util.constant.ParametroTipoConstant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrador on 04/09/2017.
 */
@Mapper
@Repository
public interface CuentaBancariaMapper {
    List<CuentaBancaria> getListCuentaByIdProveedor(@Param("parametro") ParametroConstant parametro,
                                                    @Param("tipo") ParametroTipoConstant tipo,
                                                    @Param("idProveedor") Integer idProveedor);

    List<CuentaBancoDtoExcel> getListAllCuentasBancariasProveedor();
}
