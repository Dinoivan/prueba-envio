package com.incloud.hcp.dto.mapper;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.ProveedorDto;
import com.incloud.hcp.myibatis.mapper.UbigeoMapper;
import com.incloud.hcp.repository.CondicionPagoReposity;
import com.incloud.hcp.repository.MonedaRepository;
import com.incloud.hcp.repository.TipoComprobanteRepository;
import com.incloud.hcp.repository.TipoProveedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by Joel on 02/09/2017.
 */
@Component("proveedorDTOMapper")
public class ProveedorDTOMapper implements EntityDTOMapper<Proveedor, ProveedorDto> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private CondicionPagoReposity condicionPagoReposity;
    private UbigeoMapper ubigeoRepository;
    private MonedaRepository monedaRepository;
    private TipoComprobanteRepository tipoComprobanteRepository;
    private TipoProveedorRepository tipoProveedorRepository;

    public ProveedorDTOMapper(CondicionPagoReposity condicionPagoReposity,
                              UbigeoMapper ubigeoRepository,
                              MonedaRepository monedaRepository,
                              TipoComprobanteRepository tipoComprobanteRepository,
                              TipoProveedorRepository tipoProveedorRepository) {
        this.condicionPagoReposity = condicionPagoReposity;
        this.ubigeoRepository = ubigeoRepository;
        this.monedaRepository = monedaRepository;
        this.tipoComprobanteRepository = tipoComprobanteRepository;
        this.tipoProveedorRepository = tipoProveedorRepository;
    }

    @Override
    public Proveedor toEntity(ProveedorDto dto) {
        if (dto == null) {
            return null;
        }
        logger.error("Ingresando toEntity 0 dto: " + dto.toString());
        Proveedor proveedor = new Proveedor();
        BeanUtils.copyProperties(dto, proveedor);

        logger.error("Ingresando toEntity 1");
        proveedor.setEvaluacionDesempeno(Optional.ofNullable(dto.getEvaluacionDesempeno()).orElse(new BigDecimal(0)));
        proveedor.setEvaluacionHomologacion(Optional.ofNullable(dto.getEvaluacionHomologacion()).orElse(new BigDecimal(0)));
        Optional.ofNullable(dto.getIndBlackList()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndBlackList);
        Optional.ofNullable(dto.getIndBloqueadoSap()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndBloqueadoSap);
        Optional.ofNullable(dto.getIndHomologado()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndHomologado);
        Optional.ofNullable(dto.getIndProveedorComunidad()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndProveedorComunidad);
        Optional.ofNullable(dto.getIndMigradoSap()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndMigradoSap);
        proveedor.setIndSujetoRetencion("0");
        if (Optional.ofNullable(dto.getEmailRetencion()).isPresent()) {
            proveedor.setIndSujetoRetencion("1");
        }

        Optional.ofNullable(dto.isActivo()).map(v -> v ? "1" : "0").ifPresent(proveedor::setActivo);
        Optional.ofNullable(dto.isActivo()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndActivoSunat);
        Optional.ofNullable(dto.isHabido()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndHabidoSunat);
        Optional.ofNullable(dto.isCreacionUnicaVez()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndCreacionUnicaVez);
        Optional.ofNullable(dto.isProveedorExcepcion()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndProveedorExcepcion);
        Optional.ofNullable(dto.isTipoVentaBien()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndTipoVentaBien);
        Optional.ofNullable(dto.isTipoVentaServicio()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndTipoVentaServicio);
        Optional.ofNullable(dto.isTipoFacturacionElect()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndTipoFacturacionElect);
        Optional.ofNullable(dto.isTipoFacturacionManual()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndTipoFacturacionManual);
        Optional.ofNullable(dto.isAceptacion()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndAceptacion);
        Optional.ofNullable(dto.getIndAgenteRetencion()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndAgenteRetencion);


        logger.error("Ingresando toEntity 3");
        Optional.ofNullable(this.ubigeoRepository).map(u -> u.getUbigeoByIdUbigeo(dto.getCodigoPais()))
                .ifPresent(proveedor::setPais);

        Optional.ofNullable(this.ubigeoRepository).map(u -> u.getUbigeoByIdUbigeo(dto.getCodigoRegion()))
                .ifPresent(proveedor::setRegion);

        Optional.ofNullable(this.ubigeoRepository).map(u -> u.getUbigeoByIdUbigeo(dto.getCodigoProvincia()))
                .ifPresent(proveedor::setProvincia);

        Optional.ofNullable(this.ubigeoRepository).map(u -> u.getUbigeoByIdUbigeo(dto.getCodigoDistrito()))
                .ifPresent(proveedor::setDistrito);

        Optional.ofNullable(this.monedaRepository).map(m -> m.getOne(dto.getIdMoneda()))
                .ifPresent(proveedor::setMoneda);

        logger.error("Ingresando toEntity 4");
        Optional.ofNullable(this.tipoComprobanteRepository).map(t -> t.getOne(dto.getIdTipoComprobante()))
                .ifPresent(proveedor::setTipoComprobante);

        Optional.ofNullable(this.tipoProveedorRepository).map(t -> t.getOne(dto.getIdTipoProveedor()))
                .ifPresent(proveedor::setTipoProveedor);

        Optional.ofNullable(this.condicionPagoReposity).map(c -> c.getOne(dto.getIdCondicionPago()))
                .ifPresent(proveedor::setCondicionPago);
        logger.error("Ingresando toEntity 5");
        return proveedor;
    }

    @Override
    public ProveedorDto toDto(Proveedor entity) {
        if (entity == null) {
            return null;
        }

        ProveedorDto dto = new ProveedorDto();
        BeanUtils.copyProperties(entity, dto);

        dto.setIndBlackList("1".equals(entity.getIndBlackList()));
        dto.setIndBloqueadoSap("1".equals(entity.getIndBloqueadoSap()));
        dto.setIndHomologado("1".equals(entity.getIndHomologado()));
        dto.setIndProveedorComunidad("1".equals(entity.getIndProveedorComunidad()));
        dto.setIndMigradoSap(Optional.ofNullable(entity.getIndMigradoSap()).map(a -> a.equals("1")).orElse(false));
        dto.setActivo(Optional.ofNullable(entity.getActivo()).map(a -> a.equals("1")).orElse(false));
        dto.setHabido(Optional.ofNullable(entity.getIndHabidoSunat()).map(a -> a.equals("1")).orElse(false));

        dto.setCreacionUnicaVez(Optional.ofNullable(entity.getIndCreacionUnicaVez()).map(a -> a.equals("1")).orElse(false));
        dto.setProveedorExcepcion(Optional.ofNullable(entity.getIndProveedorExcepcion()).map(a -> a.equals("1")).orElse(false));
        dto.setTipoVentaBien(Optional.ofNullable(entity.getIndTipoVentaBien()).map(a -> a.equals("1")).orElse(false));
        dto.setTipoVentaServicio(Optional.ofNullable(entity.getIndTipoVentaServicio()).map(a -> a.equals("1")).orElse(false));
        dto.setTipoFacturacionElect(Optional.ofNullable(entity.getIndTipoFacturacionElect()).map(a -> a.equals("1")).orElse(false));
        dto.setTipoFacturacionManual(Optional.ofNullable(entity.getIndTipoFacturacionManual()).map(a -> a.equals("1")).orElse(false));
        dto.setAceptacion(Optional.ofNullable(entity.getIndAceptacion()).map(a -> a.equals("1")).orElse(false));
        dto.setIndAgenteRetencion(Optional.ofNullable(entity.getIndAgenteRetencion()).map(a -> a.equals("1")).orElse(false));


        Optional.ofNullable(entity.getPais()).map(p -> p.getIdUbigeo()).ifPresent(dto::setCodigoPais);
        Optional.ofNullable(entity.getRegion()).map(p -> p.getIdUbigeo()).ifPresent(dto::setCodigoRegion);
        Optional.ofNullable(entity.getProvincia()).map(p -> p.getIdUbigeo()).ifPresent(dto::setCodigoProvincia);
        Optional.ofNullable(entity.getDistrito()).map(p -> p.getIdUbigeo()).ifPresent(dto::setCodigoDistrito);
        Optional.ofNullable(entity.getMoneda()).map(m -> m.getIdMoneda()).ifPresent(dto::setIdMoneda);
        Optional.ofNullable(entity.getTipoComprobante()).map(t -> t.getIdTipoComprobante()).ifPresent(dto::setIdTipoComprobante);
        Optional.ofNullable(entity.getTipoProveedor()).map(t -> t.getIdTipoProveedor()).ifPresent(dto::setIdTipoProveedor);
        Optional.ofNullable(entity.getCondicionPago()).map(p -> p.getIdCondicionPago()).ifPresent(dto::setIdCondicionPago);

        return dto;
    }
}
