package com.incloud.hcp.dto.mapper;

import com.incloud.hcp.domain.ProveedorProducto;
import com.incloud.hcp.dto.ProductoDto;
import org.springframework.stereotype.Component;

/**
 * Created by Joel on 02/09/2017.
 */
@Component("productoDTOMapper")
public class ProveedorProductoDTOMapper implements EntityDTOMapper<ProveedorProducto, ProductoDto> {
    @Override
    public ProveedorProducto toEntity(ProductoDto dto) {
        if(dto == null) {
            return null;
        }
        ProveedorProducto pp = new ProveedorProducto();
        pp.setProducto(dto.getProducto());
        pp.setMarca(dto.getMarca());
        pp.setDescripcionAdicional(dto.getDescripcionAdicional());
        return pp;
    }

    @Override
    public ProductoDto toDto(ProveedorProducto entity) {
        if(entity == null) {
            return null;
        }
        ProductoDto dto = new ProductoDto();
        dto.setProducto(entity.getProducto());
        dto.setMarca(entity.getMarca());
        dto.setDescripcionAdicional(entity.getDescripcionAdicional());
        return dto;
    }
}
