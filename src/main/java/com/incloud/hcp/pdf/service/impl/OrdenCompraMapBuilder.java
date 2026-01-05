package com.incloud.hcp.pdf.service.impl;

import com.incloud.hcp.enums.MonedaPdfEnum;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPdfDto;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPosicionClaseCondicionPdfDto;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPosicionDataAdicionalPdfDto;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPosicionPdfDto;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.NumberUtils;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class OrdenCompraMapBuilder {

    private OrdenCompraPdfDto ordenCompraPdfDto;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String DASH = "-";
    private static final String rucCopeinca = "20224748711";
    private static final String rucCfgInvestment = "20512868046";

    static OrdenCompraMapBuilder newOrdenCompraMapBuilder(OrdenCompraPdfDto ordenCompraPdfDto) {
        return new OrdenCompraMapBuilder(ordenCompraPdfDto);
    }

    public OrdenCompraMapBuilder(OrdenCompraPdfDto ordenCompraPdfDto) {
        this.ordenCompraPdfDto = ordenCompraPdfDto;
    }

    Map<String, Object> buildParams() {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("ordenCompraNumero", Optional.ofNullable(ordenCompraPdfDto.getOrdenCompraNumero()).orElse(DASH));
        parameters.put("ordenCompraTipo", Optional.ofNullable(ordenCompraPdfDto.getOrdenCompraTipo()).orElse(DASH));
        parameters.put("ordenCompraVersion", Optional.ofNullable(ordenCompraPdfDto.getOrdenCompraVersion()).orElse(DASH));
        parameters.put("ordenCompraFechaCreacion", Optional.ofNullable(DateUtils.utilDateToStringPattern(ordenCompraPdfDto.getOrdenCompraFechaCreacion(),DateUtils.STANDARD_DATE_FORMAT)).orElse(DASH));
        parameters.put("ordenCompraPersonaContacto", Optional.ofNullable(ordenCompraPdfDto.getOrdenCompraPersonaContacto()).orElse(DASH));
        parameters.put("ordenCompraFormaPago", Optional.ofNullable(ordenCompraPdfDto.getOrdenCompraFormaPago()).orElse(DASH));
        parameters.put("ordenCompraMoneda", ordenCompraPdfDto.getOrdenCompraMoneda() != null ? (ordenCompraPdfDto.getOrdenCompraMoneda().equalsIgnoreCase(MonedaPdfEnum.USD.getCodigo()) ? MonedaPdfEnum.USD.getDescripcion() : MonedaPdfEnum.PEN.getDescripcion()) : ordenCompraPdfDto.getOrdenCompraMoneda());
        parameters.put("ordenCompraAutorizador", Optional.ofNullable(ordenCompraPdfDto.getOrdenCompraAutorizador()).orElse(DASH));

        parameters.put("clienteRuc", Optional.ofNullable(ordenCompraPdfDto.getClienteRuc()).orElse(DASH));
        parameters.put("clienteTelefono", Optional.ofNullable(ordenCompraPdfDto.getClienteTelefono()).orElse(DASH));
        parameters.put("clienteDireccion", Optional.ofNullable(ordenCompraPdfDto.getClienteDireccion()).orElse(DASH));
        parameters.put("clienteRazonSocial", Optional.ofNullable(ordenCompraPdfDto.getClienteRazonSocial()).orElse(DASH));

        parameters.put("proveedorRazonSocial", Optional.ofNullable(ordenCompraPdfDto.getProveedorRazonSocial()).orElse(DASH));
        parameters.put("proveedorRuc", Optional.ofNullable(ordenCompraPdfDto.getProveedorRuc()).orElse(DASH));
        parameters.put("proveedorDireccion", Optional.ofNullable(ordenCompraPdfDto.getProveedorDireccion()).orElse(""));
        parameters.put("proveedorContactoNombre", Optional.ofNullable(ordenCompraPdfDto.getProveedorContactoNombre()).orElse(DASH));
        parameters.put("proveedorContactoTelefono", Optional.ofNullable(ordenCompraPdfDto.getProveedorContactoTelefono()).orElse(""));
        parameters.put("proveedorNumero", Optional.ofNullable(ordenCompraPdfDto.getProveedorNumero()).orElse(DASH));

        parameters.put("lugarEntrega", Optional.ofNullable(ordenCompraPdfDto.getLugarEntrega()).orElse(DASH));
        parameters.put("almacenMaterialEmail", Optional.ofNullable(ordenCompraPdfDto.getAlmacenMaterialEmail()).orElse(""));
        parameters.put("almacenMaterialTelefono1", Optional.ofNullable(ordenCompraPdfDto.getAlmacenMaterialTelefono1()).orElse(""));
        parameters.put("almacenMaterialTelefono2", Optional.ofNullable(ordenCompraPdfDto.getAlmacenMaterialTelefono2()).orElse(""));

        parameters.put("montoSubtotal", NumberUtils.ponerSeparadorMillares(Optional.ofNullable(ordenCompraPdfDto.getMontoSubtotal()).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP).toString(),",",2));
        parameters.put("montoDescuento", NumberUtils.ponerSeparadorMillares(Optional.ofNullable(ordenCompraPdfDto.getMontoDescuento()).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP).toString(),",",2));
        parameters.put("montoIgv", NumberUtils.ponerSeparadorMillares(Optional.ofNullable(ordenCompraPdfDto.getMontoIgv()).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP).toString(),",",2));
        parameters.put("montoImporteTotal", NumberUtils.ponerSeparadorMillares(Optional.ofNullable(ordenCompraPdfDto.getMontoImporteTotal()).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP).toString(),",",2));

        parameters.put("textoCabecera", ordenCompraPdfDto.getTextoCabecera());
        parameters.put("textoCabeceraObservaciones", ordenCompraPdfDto.getTextoCabeceraObservaciones());
        parameters.put("textoFecha", ordenCompraPdfDto.getTextoFecha());
        parameters.put("textoNotasImportantes", ordenCompraPdfDto.getTextoNotasImportantes());
        parameters.put("textoNotasProveedor", ordenCompraPdfDto.getTextoNotasProveedor());
        parameters.put("textoNotasClausula", ordenCompraPdfDto.getTextoNotasClausula());

        /*if (Optional.ofNullable(ordenCompraPdfDto.getClienteRuc()).isPresent()) {
            parameters.put("logo", rucCopeinca.equals(ordenCompraPdfDto.getClienteRuc())  ? "logoCopeinca/COPEINCA.png" : "logoCopeinca/CFGInvestment.png");
        } else {
            parameters.put("logo", "logoCopeinca/CFGInvestment.png");
        }*/
        parameters.put("logo", "logoCopeinca/CFG.png");
        parameters.put("posicionList", this.getPosicionList(ordenCompraPdfDto));

        return parameters;
    }


    private JRBeanCollectionDataSource getPosicionList(OrdenCompraPdfDto ordenCompraPdfDto) {
        List<OrdenCompraPosicionPdfDto> posicionPdfDtoList = ordenCompraPdfDto.getOrdenCompraPosicionPdfDtoList();
        List<Map<String, Object>> posicionMaps = new ArrayList<>();

        if (posicionPdfDtoList != null && !posicionPdfDtoList.isEmpty()) {
            for (OrdenCompraPosicionPdfDto posicionPdfDTO : posicionPdfDtoList) {
                Map<String, Object> map = new HashMap<>();

                map.put("posicion", Optional.ofNullable(posicionPdfDTO.getPosicion()).orElse(""));
                map.put("centro", Optional.ofNullable(posicionPdfDTO.getCentro()).orElse(""));
                map.put("material", Optional.ofNullable(posicionPdfDTO.getMaterial()).orElse(""));
                map.put("codigoProv", Optional.ofNullable(posicionPdfDTO.getCodigoProv()).orElse(""));
                map.put("descripcion", Optional.ofNullable(posicionPdfDTO.getDescripcion()).orElse(""));
                map.put("cantidad", NumberUtils.ponerSeparadorMillares(Optional.ofNullable(posicionPdfDTO.getCantidad()).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP).toString(),",",4));
                map.put("unidad", Optional.ofNullable(posicionPdfDTO.getUnidad()).orElse(""));
                map.put("fechaEntrega", posicionPdfDTO.getFechaEntrega() != null ? DateUtils.utilDateToStringPattern(posicionPdfDTO.getFechaEntrega(),DateUtils.STANDARD_DATE_FORMAT) : "");
                map.put("precioUnitario", NumberUtils.ponerSeparadorMillares(Optional.ofNullable(posicionPdfDTO.getPrecioUnitario()).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP).toString(),",",4));
                map.put("importe", NumberUtils.ponerSeparadorMillares(Optional.ofNullable(posicionPdfDTO.getImporte()).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP).toString(),",",2));
                if (posicionPdfDTO.getOrdenCompraPosicionDataAdicionalPdfDto() != null)
                    map.put("dataAdicionalList", this.getPosicionDataAdicionalList(posicionPdfDTO));

                posicionMaps.add(map);
            }
        }
        return new JRBeanCollectionDataSource(posicionMaps);
    }

    private JRBeanCollectionDataSource getPosicionDataAdicionalList(OrdenCompraPosicionPdfDto posicionPdfDto) {
        OrdenCompraPosicionDataAdicionalPdfDto posicionDataAdicionalPdfDto = posicionPdfDto.getOrdenCompraPosicionDataAdicionalPdfDto();
        List<Map<String, Object>> dataAdicionalMaps = new ArrayList<>();

        if (posicionDataAdicionalPdfDto != null) {
            Map<String, Object> map = new HashMap<>();

            map.put("textoPosicionRegistroInfo", posicionDataAdicionalPdfDto.getTextoPosicion() + "<br>"+ posicionDataAdicionalPdfDto.getTextoRegistroInfo());
            map.put("claseCondicionList", this.getClaseCondicionList(posicionDataAdicionalPdfDto));

            dataAdicionalMaps.add(map);
         }
        return new JRBeanCollectionDataSource(dataAdicionalMaps);
    }


    private JRBeanCollectionDataSource getClaseCondicionList(OrdenCompraPosicionDataAdicionalPdfDto posicionDataAdicionalPdfDto) {
        List<OrdenCompraPosicionClaseCondicionPdfDto> claseCondicionPdfDtoList = posicionDataAdicionalPdfDto.getOrdenCompraPosicionClaseCondicionPdfDtoList();
        List<Map<String, Object>> claseCondicionMaps = new ArrayList<>();

        if (claseCondicionPdfDtoList != null && !claseCondicionPdfDtoList.isEmpty()) {
            for (OrdenCompraPosicionClaseCondicionPdfDto claseCondicionPdfDto : claseCondicionPdfDtoList) {
                Map<String, Object> map = new HashMap<>();

                map.put("claseCondicionDescripcion", claseCondicionPdfDto.getClaseCondicionDescripcion());
                map.put("claseCondicionPrecioUnitario", claseCondicionPdfDto.getClaseCondicionPrecioUnitario().compareTo(BigDecimal.ZERO) != 0 ? claseCondicionPdfDto.getClaseCondicionPrecioUnitario().toString() : null);
                map.put("claseCondicionImporte", claseCondicionPdfDto.getClaseCondicionImporte().compareTo(BigDecimal.ZERO) != 0 ? claseCondicionPdfDto.getClaseCondicionImporte().toString() : null);

                claseCondicionMaps.add(map);
            }
        }
        return new JRBeanCollectionDataSource(claseCondicionMaps);
    }
}
