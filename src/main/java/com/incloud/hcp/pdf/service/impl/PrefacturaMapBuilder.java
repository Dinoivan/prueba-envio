package com.incloud.hcp.pdf.service.impl;

import com.incloud.hcp.enums.MonedaPdfEnum;
import com.incloud.hcp.pdf.bean.PrefacturaItemPdfDto;
import com.incloud.hcp.pdf.bean.PrefacturaPdfDto;
import com.incloud.hcp.util.NumberUtils;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PrefacturaMapBuilder {

    private PrefacturaPdfDto prefacturaPdfDto;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String DASH = "-";
    private static final String rucCopeinca = "20224748711";
    private static final String rucCfgInvestment = "20512868046";

    static PrefacturaMapBuilder newPrefacturaMapBuilder(PrefacturaPdfDto prefacturaPdfDto) {
        return new PrefacturaMapBuilder(prefacturaPdfDto);
    }

    public PrefacturaMapBuilder(PrefacturaPdfDto prefacturaPdfDto) {
        this.prefacturaPdfDto = prefacturaPdfDto;
    }

    Map<String, Object> buildParams() {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("clienteRuc", Optional.ofNullable(prefacturaPdfDto.getClienteRuc()).orElse(DASH));
        parameters.put("clienteRazonSocial", Optional.ofNullable(prefacturaPdfDto.getClienteRazonSocial()).orElse(DASH));
        parameters.put("proveedorRuc", Optional.ofNullable(prefacturaPdfDto.getProveedorRuc()).orElse(DASH));
        parameters.put("proveedorRazonSocial", Optional.ofNullable(prefacturaPdfDto.getProveedorRazonSocial()).orElse(DASH));

        parameters.put("prefacturaReferencia", Optional.ofNullable(prefacturaPdfDto.getPrefacturaReferencia()).orElse(DASH));
        parameters.put("prefacturaEstado", Optional.ofNullable(prefacturaPdfDto.getPrefacturaEstado()).orElse(DASH));
        parameters.put("prefacturaOrdenes", Optional.ofNullable(prefacturaPdfDto.getPrefacturaOrdenes()).orElse(DASH));
        parameters.put("prefacturaGuias", Optional.ofNullable(prefacturaPdfDto.getPrefacturaGuias()).orElse(DASH));
        parameters.put("prefacturaFechaEmision", Optional.ofNullable(prefacturaPdfDto.getPrefacturaFechaEmision()).orElse(DASH));
        parameters.put("prefacturaFechaRegistro", Optional.ofNullable(prefacturaPdfDto.getPrefacturaFechaRegistro()).orElse(DASH));
        boolean enSoles = prefacturaPdfDto.getPrefacturaMoneda().equalsIgnoreCase(MonedaPdfEnum.PEN.getCodigo());
        parameters.put("prefacturaMoneda", enSoles ? MonedaPdfEnum.PEN.getDescripcion() : MonedaPdfEnum.USD.getDescripcion());
        parameters.put("prefacturaObservaciones", Optional.ofNullable(prefacturaPdfDto.getPrefacturaObservaciones()).orElse(DASH));

        parameters.put("montoSubtotal", NumberUtils.ponerSeparadorMillares((Optional.ofNullable(prefacturaPdfDto.getMontoSubtotal()).orElse(BigDecimal.ZERO)).setScale(2, RoundingMode.HALF_UP).toString(),",",2));
        BigDecimal montoIgv = Optional.ofNullable(prefacturaPdfDto.getMontoIgv()).orElse(BigDecimal.ZERO);
        parameters.put("montoIgv", NumberUtils.ponerSeparadorMillares(montoIgv.setScale(2, RoundingMode.HALF_UP).toString(),",",2));
        String montoTotal = (Optional.ofNullable(prefacturaPdfDto.getMontoTotal()).orElse(BigDecimal.ZERO)).setScale(2, RoundingMode.HALF_UP).toString();
        parameters.put("montoTotal", NumberUtils.ponerSeparadorMillares(montoTotal,",",2));
        parameters.put("montoTotalLiteral", NumberUtils.convertirNumeroALetras(montoTotal, (enSoles ? MonedaPdfEnum.PEN.getNombrePlural() : MonedaPdfEnum.USD.getNombrePlural()), true));

        String prefacturaIndicadorImpuesto = Optional.ofNullable(prefacturaPdfDto.getPrefacturaIndicadorImpuesto()).orElse("");
        if (prefacturaIndicadorImpuesto.isEmpty()){
            if(montoIgv.compareTo(BigDecimal.ZERO) == 0)
                parameters.put("prefacturaIndicadorImpuesto", "C0");
            else
                parameters.put("prefacturaIndicadorImpuesto", "C1");
        }
        else{
            parameters.put("prefacturaIndicadorImpuesto", prefacturaIndicadorImpuesto);
        }

        parameters.put("logo", "logoCopeinca/CFG.png");
        /* if (Optional.ofNullable(prefacturaPdfDto.getClienteRuc()).isPresent()) {
            parameters.put("logo", rucCopeinca.equals(prefacturaPdfDto.getClienteRuc()) ? "logoCopeinca/COPEINCA.png" : "logoCopeinca/CFGInvestment.png");
        } else {
            parameters.put("logo", "logoCopeinca/CFGInvestment.png");
        }*/

        parameters.put("posicionList", this.getPosicionList(prefacturaPdfDto));

        return parameters;
    }


    private JRBeanCollectionDataSource getPosicionList(PrefacturaPdfDto prefacturaPdfDto) {
        List<PrefacturaItemPdfDto> itemPdfDtoList = prefacturaPdfDto.getPrefacturaItemPdfDtoList();
        List<Map<String, Object>> itemMaps = new ArrayList<>();

        if (itemPdfDtoList != null && !itemPdfDtoList.isEmpty()) {
            for (PrefacturaItemPdfDto itemPdfDTO : itemPdfDtoList) {
                Map<String, Object> map = new HashMap<>();

                map.put("item", Optional.ofNullable(itemPdfDTO.getItem()).orElse(DASH));
                map.put("numeroDocumento", Optional.ofNullable(itemPdfDTO.getNumeroDocumento()).orElse(DASH));
                map.put("numeroOrden", Optional.ofNullable(itemPdfDTO.getNumeroOrden()).orElse(DASH));
                map.put("posicion", Optional.ofNullable(itemPdfDTO.getPosicion()).orElse(DASH));
                map.put("codigo", Optional.ofNullable(itemPdfDTO.getCodigo()).orElse(DASH));
                map.put("descripcion", Optional.ofNullable(itemPdfDTO.getDescripcion()).orElse("                    " + DASH));
                map.put("codigoServicio", Optional.ofNullable(itemPdfDTO.getCodigoServicio()).orElse(DASH));
                map.put("descripcionServicio", Optional.ofNullable(itemPdfDTO.getDescripcionServicio()).orElse("                    " + DASH));
                map.put("indicadorDetraccion", Optional.ofNullable(itemPdfDTO.getIndicadorDetraccion()).orElse(DASH));
                map.put("cantidad", NumberUtils.ponerSeparadorMillares((Optional.ofNullable(itemPdfDTO.getCantidad()).orElse(BigDecimal.ZERO)).setScale(2, RoundingMode.HALF_UP).toString(),",",2));
                map.put("precioUnitario", NumberUtils.ponerSeparadorMillares((Optional.ofNullable(itemPdfDTO.getPrecioUnitario()).orElse(BigDecimal.ZERO)).setScale(2, RoundingMode.HALF_UP).toString(),",",2));
                map.put("importe", NumberUtils.ponerSeparadorMillares((Optional.ofNullable(itemPdfDTO.getImporte()).orElse(BigDecimal.ZERO)).setScale(2, RoundingMode.HALF_UP).toString(),",",2));

                itemMaps.add(map);
            }
        }
        return new JRBeanCollectionDataSource(itemMaps);
    }
}
