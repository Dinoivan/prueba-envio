package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.domain.Parametro;
import com.incloud.hcp.sap.SapSetting;
import com.incloud.hcp.service.notificacion.MailSetting;
import com.incloud.hcp.util.constant.ParametroConstant;
import com.incloud.hcp.util.constant.ParametroTipoConstant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrador on 23/08/2017.
 */
@Mapper
@Repository
public interface ParametroMapper {
    List<Parametro> getListParametro(@Param("parametro") ParametroConstant parametro,
                                     @Param("tipo") ParametroTipoConstant tipo);
    List<Parametro> getListParametrobyModuloandTipo(@Param("modulo") String modulo,
                                     @Param("tipo") String tipo);

    MailSetting getMailSetting();

    SapSetting getSapSetting();
}
