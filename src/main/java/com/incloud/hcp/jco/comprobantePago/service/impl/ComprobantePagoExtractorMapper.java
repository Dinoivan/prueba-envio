package com.incloud.hcp.jco.comprobantePago.service.impl;

import com.incloud.hcp.jco.comprobantePago.dto.ComprobantePagoDto;
import com.incloud.hcp.jco.comprobantePago.dto.ComprobantePagoItemDto;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComprobantePagoExtractorMapper {

    private JCoParameterList jCoParameterList;

    public static ComprobantePagoExtractorMapper newMapper(JCoParameterList exportParameterList) {
        return new ComprobantePagoExtractorMapper(exportParameterList);
    }

    private ComprobantePagoExtractorMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public List<ComprobantePagoDto> getComprobantePagoDtoList() {
        List<ComprobantePagoDto> comprobantePagoDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("OT_DH_INV_REC");

        if (table != null && !table.isEmpty()) {
            do {
                ComprobantePagoDto comprobantePago = new ComprobantePagoDto();

                comprobantePago.setCodigoDocumentoSap(table.getString("BELNR"));
                comprobantePago.setEjercicio(table.getString("GJAHR"));
                comprobantePago.setNumeroDocumentoContable(table.getString("BELNR_REF"));
                comprobantePago.setNumeroComprobantePago(table.getString("XBLNR"));
                comprobantePago.setCodigoSociedad(table.getString("BUKRS_REF"));
                comprobantePago.setProveedorRuc(table.getString("STCD1"));
                comprobantePago.setCodigoMoneda(table.getString("WAERS"));
                comprobantePago.setSubTotal(Optional.ofNullable(table.getBigDecimal("WMWST1")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
                comprobantePago.setIgv(Optional.ofNullable(table.getBigDecimal("WSKTO")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
                comprobantePago.setTotal(Optional.ofNullable(table.getBigDecimal("RMWWR")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
                comprobantePago.setEstado(table.getString("STATUS"));
                comprobantePago.setFormaPago(table.getInt("DZBD1T"));
                comprobantePago.setFechaEmision(table.getDate("BLDAT"));
                comprobantePago.setFechaRegistroDocContable(table.getDate("CPUDT"));
                comprobantePago.setFechaContabilizacion(table.getDate("BUDAT"));
                comprobantePago.setFechaBase(table.getDate("ZFBDT"));
                comprobantePago.setFechaVencimiento(table.getDate("FDTAG"));
//                comprobantePago.setFechaPosiblePago(table.getDate("XXXXX"));f
                comprobantePago.setFechaRealPago(table.getDate("AUGDT"));
                comprobantePago.setFechaPagoNuevo(table.getDate("FDPAGO"));
                comprobantePago.setObservaciones(table.getString("BKTXT"));
                comprobantePago.setTipoPago(table.getString("TEXT1"));
                comprobantePago.setNumeroDocumentoCompensacion(table.getString("AUGBL"));
                comprobantePago.setBanco(table.getString("BANKA"));
                comprobantePago.setProveedorRuc(table.getString("STCD1"));

                comprobantePagoDtoList.add(comprobantePago);
            } while (table.nextRow());
        }
        return comprobantePagoDtoList;
    }

    public List<ComprobantePagoItemDto> getComprobantePagoItemDtoList() {
        List<ComprobantePagoItemDto> comprobantePagoItemDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("OT_DD_INV_REC");

        if (table != null && !table.isEmpty()) {
            do {
                ComprobantePagoItemDto comprobantePagoItem = new ComprobantePagoItemDto();

                comprobantePagoItem.setCodigoDocumentoSap(table.getString("BELNR"));
                comprobantePagoItem.setEjercicio(table.getString("GJAHR"));
                comprobantePagoItem.setNumeroItem(table.getInt("BUZEI"));
                comprobantePagoItem.setNumeroOrdenCompra(table.getString("EBELN"));
                comprobantePagoItem.setNumeroPosicion(String.format("%05d", table.getInt("EBELP")));
                comprobantePagoItem.setDescripcionProducto(table.getString("TXZ01"));
                comprobantePagoItem.setCantidad(Optional.ofNullable(table.getBigDecimal("MENGE")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                comprobantePagoItem.setPrecioUnitario(Optional.ofNullable(table.getBigDecimal("NETPR")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                comprobantePagoItem.setImporteItem(Optional.ofNullable(table.getBigDecimal("WRBTR")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));

                comprobantePagoItemDtoList.add(comprobantePagoItem);
            } while (table.nextRow());
        }
        return comprobantePagoItemDtoList;
    }
}