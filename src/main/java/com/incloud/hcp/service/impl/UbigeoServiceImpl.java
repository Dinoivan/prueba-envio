package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.Distrito;
import com.incloud.hcp.bean.Pais;
import com.incloud.hcp.bean.Provincia;
import com.incloud.hcp.bean.Region;
import com.incloud.hcp.domain.Ubigeo;
import com.incloud.hcp.myibatis.mapper.UbigeoMapper;
import com.incloud.hcp.service.UbigeoService;
import com.incloud.hcp.util.constant.UbigeoConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Administrador on 22/08/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class UbigeoServiceImpl implements UbigeoService {
    @Autowired
    private UbigeoMapper ubigeoMapper;

    public List<Pais> getListPaisAll(){
        return ubigeoMapper.getListUbigeoByNivel(UbigeoConstant.PAIS)
                .stream().map(this::paisMapper).collect(Collectors.toList());
    }

    public List<Region> getListRegion(String codigoPais){
        return ubigeoMapper.getListUbigeoByCodigoParent(codigoPais)
                .stream().map(this::regionMapper).collect(Collectors.toList());
    }

    public List<Provincia> getListProvincia(String codigoRegion){
        return ubigeoMapper.getListUbigeoByCodigoParent(codigoRegion)
                .stream().map(this::provinciaMapper).collect(Collectors.toList());
    }

    public List<Distrito> getListDistrito(String codigoProvincia){
        return ubigeoMapper.getListUbigeoByCodigoParent(codigoProvincia)
                .stream().map(this::distritoMapper).collect(Collectors.toList());
    }

    @Override
    public List<Ubigeo> getListUbigeoByParent(List<String> ids) {
        return ubigeoMapper.getListUbigeoByParent(ids);
    }

    @Override
    public List<Ubigeo> getListUbigeoByPadre(Integer idPadre) {
        return ubigeoMapper.getListUbigeoByPadre(idPadre);
    }

    @Override
    public Ubigeo getUbigeoById(Integer idUbigeo) {
        return ubigeoMapper.getUbigeoByIdUbigeo(idUbigeo);
    }

    @Override
    public Ubigeo getUbigeoByCodigo(String codigo) {
        return ubigeoMapper.getUbigeoByCodigo(codigo);
    }

    private Distrito distritoMapper(Ubigeo ubigeo) {
        Distrito d = new Distrito();
        d.setIdDistrito(ubigeo.getIdUbigeo());
        d.setCodigo(ubigeo.getCodigoUbigeoSap());
        d.setDescripcion(ubigeo.getDescripcion());
        d.setIdProvincia(ubigeo.getIdPadre());
        return d;
    }

    private Provincia provinciaMapper(Ubigeo ubigeo) {
        Provincia p = new Provincia();
        p.setIdProvincia(ubigeo.getIdUbigeo());
        p.setCodigo(ubigeo.getCodigoUbigeoSap());
        p.setDescripcion(ubigeo.getDescripcion());
        p.setIdRegion(ubigeo.getIdPadre());
        return p;
    }

    private Region regionMapper(Ubigeo ubigeo){
        Region r = new Region();
        r.setIdRegion(ubigeo.getIdUbigeo());
        r.setCodigo(ubigeo.getCodigoUbigeoSap());
        r.setDescripcion(ubigeo.getDescripcion());
        Optional.ofNullable(ubigeo.getIdPadre()).ifPresent(r::setIdPais);
        return r;
    }

    private Pais paisMapper(Ubigeo ubigeo) {
        Pais p = new Pais();
        BeanUtils.copyProperties(ubigeo, p);

        p.setIdPais(ubigeo.getIdUbigeo());
        p.setCodigo(ubigeo.getCodigoUbigeoSap());
        p.setDescripcion(ubigeo.getDescripcion());

        return p;
    }
}
