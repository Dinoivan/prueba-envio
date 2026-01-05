package com.incloud.hcp.dto.mapper;

import com.incloud.hcp.bean.CuentaBancaria;
import com.incloud.hcp.bean.TipoCuenta;
import com.incloud.hcp.dto.CuentaBancariaDto;
import com.incloud.hcp.repository.BancoRepository;
import com.incloud.hcp.repository.MonedaRepository;

import java.util.Optional;

/**
 * Created by Joel on 02/09/2017.
 */

public class CuentaBancoDTOMapper implements EntityDTOMapper<CuentaBancaria, CuentaBancariaDto> {

    private BancoRepository bancoRepository;
    private MonedaRepository monedaRepository;

    public CuentaBancoDTOMapper(BancoRepository bancoRepository, MonedaRepository monedaRepository) {
        this.bancoRepository = bancoRepository;
        this.monedaRepository = monedaRepository;
    }

    @Override
    public CuentaBancaria toEntity(CuentaBancariaDto dto) {
        if(dto == null) {
            return null;
        }

        CuentaBancaria cuentaBanco = new CuentaBancaria();
        Optional.ofNullable(this.bancoRepository)
                .map(r -> r.getOne(dto.getIdBanco()))
                .ifPresent(cuentaBanco::setBanco);

        Optional.ofNullable(this.monedaRepository).map(r -> r.getOne(dto.getIdMoneda()))
                .ifPresent(cuentaBanco::setMoneda);

        TipoCuenta tipoCuenta = new TipoCuenta();
        tipoCuenta.setCodigo(dto.getCodigoTipoCuenta());
        Optional.ofNullable(dto.getIdTipoCuenta()).ifPresent(tipoCuenta::setIdTipoCuenta);
        tipoCuenta.setDescripcion(dto.getTipoCuenta());
        cuentaBanco.setTipoCuenta(tipoCuenta);
        cuentaBanco.setContacto(dto.getContacto());
        cuentaBanco.setNumeroCuenta(dto.getNumeroCuenta());
        cuentaBanco.setNumeroCuentaCci(dto.getNumeroCuentaCci());
        cuentaBanco.setArchivoId(dto.getArchivoId());
        cuentaBanco.setArchivoNombre(dto.getArchivoNombre());
        cuentaBanco.setArchivoTipo(dto.getArchivoTipo());
        cuentaBanco.setRutaAdjunto(dto.getRutaAdjunto());
        return cuentaBanco;
    }

    @Override
    public CuentaBancariaDto toDto(CuentaBancaria entity) {
        if (entity == null) {
            return null;
        }

        CuentaBancariaDto dto = new CuentaBancariaDto();
        Optional.ofNullable(entity.getBanco()).ifPresent(b -> {
            dto.setIdBanco(b.getIdBanco());
            dto.setBanco(b.getDescripcion());
        });

        Optional.ofNullable(entity.getMoneda()).ifPresent(m -> {
            dto.setIdMoneda(m.getIdMoneda());
            dto.setMoneda(m.getTextoBreve());
        });

        Optional.ofNullable(entity.getTipoCuenta()).ifPresent(t -> {
            dto.setIdTipoCuenta(t.getIdTipoCuenta());
            dto.setCodigoTipoCuenta(t.getCodigo());
            dto.setTipoCuenta(t.getDescripcion());
        });

        dto.setContacto(entity.getContacto());
        dto.setNumeroCuenta(entity.getNumeroCuenta());
        dto.setNumeroCuentaCci(entity.getNumeroCuentaCci());
        dto.setArchivoId(entity.getArchivoId());
        dto.setArchivoNombre(entity.getArchivoNombre());
        dto.setArchivoTipo(entity.getArchivoTipo());
        dto.setRutaAdjunto(entity.getRutaAdjunto());
        return dto;
    }

}
