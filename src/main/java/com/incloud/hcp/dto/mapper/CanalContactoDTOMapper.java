package com.incloud.hcp.dto.mapper;

import com.incloud.hcp.domain.ProveedorCanalContacto;
import com.incloud.hcp.domain.Ubigeo;
import com.incloud.hcp.dto.CanalContactoDto;
import com.incloud.hcp.myibatis.mapper.UbigeoMapper;

import java.util.Optional;

/**
 * Created by Administrador on 24/10/2017.
 */
public class CanalContactoDTOMapper implements EntityDTOMapper<ProveedorCanalContacto, CanalContactoDto> {
    private UbigeoMapper ubigeoMapper;

    public CanalContactoDTOMapper(UbigeoMapper ubigeoMapper) {
        this.ubigeoMapper = ubigeoMapper;
    }

    @Override
    public ProveedorCanalContacto toEntity(CanalContactoDto dto) {
        ProveedorCanalContacto entity = new ProveedorCanalContacto();
        entity.setAreaEmpresa(dto.getCodigoArea());
        entity.setContacto(dto.getContacto());
        entity.setDireccion(dto.getDireccion());
        entity.setEmail(dto.getEmail());
        entity.setTelefono(dto.getTelefono());
        Optional.ofNullable(ubigeoMapper)
                .map(rep -> rep.getUbigeoByIdUbigeo(dto.getCodigoPais()))
                .ifPresent(entity::setPais);
        Optional.ofNullable(ubigeoMapper)
                .map(rep -> rep.getUbigeoByIdUbigeo(dto.getCodigoRegion()))
                .ifPresent(entity::setRegion);
        Optional.ofNullable(ubigeoMapper)
                .map(rep -> rep.getUbigeoByIdUbigeo(dto.getCodigoProvincia()))
                .ifPresent(entity::setProvincia);
        return entity;
    }

    @Override
    public CanalContactoDto toDto(ProveedorCanalContacto entity) {
        CanalContactoDto dto = new CanalContactoDto();
        dto.setCodigoArea(entity.getAreaEmpresa());
        dto.setContacto(entity.getContacto());
        dto.setDireccion(entity.getDireccion());
        dto.setEmail(entity.getEmail());
        Optional.ofNullable(entity.getPais()).map(Ubigeo::getIdUbigeo).ifPresent(dto::setCodigoPais);
        Optional.ofNullable(entity.getRegion()).map(Ubigeo::getIdUbigeo).ifPresent(dto::setCodigoRegion);
        Optional.ofNullable(entity.getProvincia()).map(Ubigeo::getIdUbigeo).ifPresent(dto::setCodigoProvincia);
        dto.setTelefono(entity.getTelefono());
        return dto;
    }
}
