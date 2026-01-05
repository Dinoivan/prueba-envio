package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.Area;
import com.incloud.hcp.bean.EstadoLicitacion;
import com.incloud.hcp.bean.TipoCuenta;
import com.incloud.hcp.domain.Parametro;
import com.incloud.hcp.myibatis.mapper.ParametroMapper;
import com.incloud.hcp.sap.SapSetting;
import com.incloud.hcp.service.ParametroService;
import com.incloud.hcp.service.notificacion.MailSetting;
import com.incloud.hcp.util.constant.ParametroConstant;
import com.incloud.hcp.util.constant.ParametroTipoConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Administrador on 23/08/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class ParametroServiceImpl implements ParametroService {
    @Autowired
    private ParametroMapper parametroMapper;

    @Override
    public List<Area> getListAreaAll() {
        return parametroMapper.getListParametro(ParametroConstant.CONTACTO, ParametroTipoConstant.AREA)
                .stream().map(this::areaMapper).collect(Collectors.toList());
    }

    @Override
    public List<TipoCuenta> getListTipoCuentaAll() {
        return parametroMapper.getListParametro(ParametroConstant.CUENTA_BANCO, ParametroTipoConstant.TIPO)
                .stream().map(this::tipoCuentaMapper).collect(Collectors.toList());
    }

    @Override
    public List<EstadoLicitacion> getListEstadoLicitacionAll() {
        return parametroMapper.getListParametro(ParametroConstant.LICITACION, ParametroTipoConstant.ESTADO)
                .stream().map(this::estadoLicitacionMapper).collect(Collectors.toList());
    }



    private Area areaMapper(Parametro parametro) {
        Area a = new Area();
        a.setCodigoArea(parametro.getCodigo());
        a.setDescripcion(parametro.getValor());
        return a;
    }

    private TipoCuenta tipoCuentaMapper(Parametro parametro) {
        TipoCuenta t = new TipoCuenta();
        Optional.ofNullable(parametro.getIdParametro()).ifPresent(t::setIdTipoCuenta);
        t.setDescripcion(parametro.getValor());
        t.setCodigo(parametro.getCodigo());
        return t;
    }

    private EstadoLicitacion estadoLicitacionMapper(Parametro parametro) {
        EstadoLicitacion t = new EstadoLicitacion();
        Optional.ofNullable(parametro.getIdParametro()).ifPresent(t::setIdEstado);
        t.setValor(parametro.getValor());
        t.setCodigo(parametro.getCodigo());
        return t;
    }

    @Override
    public MailSetting getMailSetting() {
        return parametroMapper.getMailSetting();
    }

    @Override
    public SapSetting getSapSetting() {
        return parametroMapper.getSapSetting();
    }

    @Override
    public List<Parametro> getByModuloandTipo(String modulo, String tipo) {
        return parametroMapper.getListParametrobyModuloandTipo(modulo,tipo);
    }
}
