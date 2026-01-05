package com.incloud.hcp.populate.impl;

import com.incloud.hcp.domain.Banco;
import com.incloud.hcp.dto.BancoDto;
import com.incloud.hcp.populate.Populater;
import org.springframework.stereotype.Component;

@Component
public class BancoPopulate implements Populater<Banco, BancoDto> {
    @Override
    public BancoDto toDto(Banco banco) {
        BancoDto dto = new BancoDto();
        dto.setIdBanco(banco.getIdBanco());
        dto.setDescripcion(banco.getDescripcion());
        return dto;
    }

    @Override
    public Banco toEntity(BancoDto bancoDto) {
        Banco entity = new Banco();
        entity.setIdBanco(bancoDto.getIdBanco());
        entity.setDescripcion(bancoDto.getDescripcion());

        return entity;
    }
}
