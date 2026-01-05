package com.incloud.hcp.populate.impl;

import com.incloud.hcp.domain.PreRegistroProveedor;
import com.incloud.hcp.domain.TipoProveedor;
import com.incloud.hcp.dto.PreRegistroProveedorDto;
import com.incloud.hcp.populate.Populater;
import com.incloud.hcp.repository.TipoProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PreRegistroPopulate implements Populater<PreRegistroProveedor, PreRegistroProveedorDto> {
    private TipoProveedorRepository tipoProveedorRepository;

    @Autowired
    public void setTipoProveedorRepository(TipoProveedorRepository tipoProveedorRepository) {
        this.tipoProveedorRepository = tipoProveedorRepository;
    }

    @Override
    public PreRegistroProveedorDto toDto(PreRegistroProveedor entity) {
        PreRegistroProveedorDto dto = new PreRegistroProveedorDto();
        dto.setContacto(entity.getContacto());
        dto.setEmail(entity.getEmail());
        dto.setEstado(entity.getEstado());
        dto.setIdRegistro(entity.getIdRegistro());
        dto.setIdHcp(entity.getIdHcp());
        Optional.ofNullable(entity).map(PreRegistroProveedor::getTipoProveedor)
                .map(TipoProveedor::getIdTipoProveedor)
                .ifPresent(dto::setIdTipoProveedor);
        dto.setRazonSocial(entity.getRazonSocial());
        dto.setComentario(entity.getComentario());
        dto.setRuc(entity.getRuc());
        dto.setTelefono(entity.getTelefono());
        dto.setActivo(Optional.ofNullable(entity.getActivo()).map(activo -> activo.equals("1")).orElse(Boolean.FALSE));
        dto.setHabido(Optional.ofNullable(entity.getHabido()).map(habido -> habido.equals("1")).orElse(Boolean.FALSE));
        dto.setRegion(entity.getRegion());
        dto.setProvincia(entity.getProvincia());
        dto.setDistrito(entity.getDistrito());
        dto.setUbigeo(entity.getUbigeo());
        dto.setDireccion(entity.getDireccion());
        dto.setSunat(Optional.ofNullable(entity.getSunat()).map(sunat -> sunat.equals("1")).orElse(Boolean.FALSE));
        dto.setDatospersonales(entity.getDatospersonales());
        return dto;
    }

    @Override
    public PreRegistroProveedor toEntity(PreRegistroProveedorDto dto) {
        PreRegistroProveedor registro = new PreRegistroProveedor();
        registro.setContacto(dto.getContacto());
        registro.setEmail(dto.getEmail());
        registro.setEstado(dto.getEstado());
        registro.setIdRegistro(dto.getIdRegistro());
        registro.setRazonSocial(dto.getRazonSocial());
        registro.setComentario(dto.getComentario());
        registro.setRuc(dto.getRuc());
        registro.setTelefono(dto.getTelefono());
        registro.setIdHcp(dto.getIdHcp());
        registro.setActivo(Optional.ofNullable(dto.getActivo()).map(activo -> activo ? "1" : "0").orElse("0"));
        registro.setHabido(Optional.ofNullable(dto.getHabido()).map(habido -> habido ? "1" : "0").orElse("0"));
        registro.setRegion(dto.getRegion());
        registro.setProvincia(dto.getProvincia());
        registro.setDistrito(dto.getDistrito());
        registro.setUbigeo(dto.getUbigeo());
        registro.setDireccion(dto.getDireccion());
        registro.setSunat(Optional.ofNullable(dto.getSunat()).map(sunat -> sunat ? "1" : "0").orElse("0"));
        registro.setDatospersonales(dto.getDatospersonales());
        Optional.ofNullable(tipoProveedorRepository)
                .map(rep -> rep.getOne(dto.getIdTipoProveedor()))
                .ifPresent(registro::setTipoProveedor);
        return registro;
    }
}
