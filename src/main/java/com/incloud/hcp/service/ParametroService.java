package com.incloud.hcp.service;

import com.incloud.hcp.bean.Area;
import com.incloud.hcp.bean.EstadoLicitacion;
import com.incloud.hcp.bean.TipoCuenta;
import com.incloud.hcp.domain.Parametro;
import com.incloud.hcp.sap.SapSetting;
import com.incloud.hcp.service.notificacion.MailSetting;

import java.util.List;

/**
 * Created by Administrador on 23/08/2017.
 */
public interface ParametroService {
    List<Area> getListAreaAll();
    List<TipoCuenta> getListTipoCuentaAll();
    List<EstadoLicitacion> getListEstadoLicitacionAll();
    MailSetting getMailSetting();
    SapSetting getSapSetting();
    List<Parametro> getByModuloandTipo(String modulo,String tipo);

}
