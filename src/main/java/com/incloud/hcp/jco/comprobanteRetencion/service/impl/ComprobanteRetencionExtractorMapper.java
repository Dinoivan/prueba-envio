package com.incloud.hcp.jco.comprobanteRetencion.service.impl;

import com.incloud.hcp.jco.comprobanteRetencion.dto.*;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComprobanteRetencionExtractorMapper {

    private JCoParameterList jCoParameterList;

    public static ComprobanteRetencionExtractorMapper newMapper(JCoParameterList exportParameterList) {
        return new ComprobanteRetencionExtractorMapper(exportParameterList);
    }

    private ComprobanteRetencionExtractorMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public List<SapTableCRHeaderDto> getHeaderDtoList() {
        List<SapTableCRHeaderDto> headerDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_ZWITHHEADER");

        if (table != null && !table.isEmpty()) {
            do {
                SapTableCRHeaderDto headerDto = new SapTableCRHeaderDto();

                headerDto.setNumeroDocumentoErp(table.getString("BELNR"));
                headerDto.setSociedad(table.getString("BUKRS"));
                headerDto.setEjercicio(table.getInt("GJAHR"));
                headerDto.setSerie(table.getString("ZWITHSERIE"));
                headerDto.setCorrelativo(table.getString("ZWITHNUMERO"));
                headerDto.setProveedorRuc(table.getString("STDC1"));
                headerDto.setProveedorRazonSocial(table.getString("RAZONSOCIAL"));
                headerDto.setFechaEmision(table.getDate("BLDAT"));
                headerDto.setEstado(table.getString("STATUS"));
                headerDto.setTasaRetencion(Optional.ofNullable(table.getBigDecimal("QSATZ")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
                headerDto.setMoneda(table.getString("WAERS"));

                headerDtoList.add(headerDto);
            } while (table.nextRow());
        }
        return headerDtoList;
    }

    public List<SapTableCRItemDto> getItemDtoList() {
        List<SapTableCRItemDto> itemDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_ZWITHDETAIL");

        if (table != null && !table.isEmpty()) {
            do {
                SapTableCRItemDto itemDto = new SapTableCRItemDto();

                itemDto.setNumeroDocumentoErp(table.getString("BELNR"));
                itemDto.setSociedad(table.getString("BUKRS"));
                itemDto.setEjercicio(table.getInt("GJAHR"));
                itemDto.setTipoComprobante(table.getString("BLART"));
                itemDto.setSerieFactura(table.getString("ZSERIEFACTURA"));
                itemDto.setCorrelativoFactura(table.getString("ZNUMEROFACTURA"));
                itemDto.setFechaEmision(table.getDate("BLDAT"));
                itemDto.setMoneda(table.getString("HWAER"));
                itemDto.setImporteTotalComprobante(Optional.ofNullable(table.getBigDecimal("WRBTR")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
                itemDto.setImportePago(Optional.ofNullable(table.getBigDecimal("WRBTR")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
                itemDto.setImporteRetencionSoles(Optional.ofNullable(table.getBigDecimal("WT_QBSHH")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
                itemDto.setImporteNetoSoles(Optional.ofNullable(table.getBigDecimal("DMBTR")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));

                itemDtoList.add(itemDto);
            } while (table.nextRow());
        }
        return itemDtoList;
    }

    public List<SapTableCRTotalDto> getTotalDtoList() {
        List<SapTableCRTotalDto> totalDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_ZWITHTOTAL");

        if (table != null && !table.isEmpty()) {
            do {
                SapTableCRTotalDto totalDto = new SapTableCRTotalDto();

                totalDto.setNumeroDocumentoErp(table.getString("BELNR"));
                totalDto.setSociedad(table.getString("BUKRS"));
                totalDto.setEjercicio(table.getInt("GJAHR"));
                totalDto.setImporteTotalRetencionMonedaLocal(Optional.ofNullable(table.getBigDecimal("WT_QBSHH")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));

                totalDtoList.add(totalDto);
            } while (table.nextRow());
        }
        return totalDtoList;
    }

    public List<SapTableCRSociedadDto> getSociedadDtoList() {
        List<SapTableCRSociedadDto> sociedadDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_T001");

        if (table != null && !table.isEmpty()) {
            do {
                SapTableCRSociedadDto sociedadDto = new SapTableCRSociedadDto();

                sociedadDto.setSociedad(table.getString("BUKRS"));
                sociedadDto.setRazonSocial(table.getString("NAME1"));
                sociedadDto.setCalle(table.getString("STREET"));
                sociedadDto.setNumeroEdificio(table.getString("HOUSE_NUM1"));
                sociedadDto.setPoblacion(table.getString("CITY1"));
                sociedadDto.setDistrito(table.getString("CITY2"));
                sociedadDto.setTelefono(table.getString("TEL_NUMBER"));
                sociedadDto.setRuc(table.getString("PAVAL"));

                sociedadDtoList.add(sociedadDto);
            } while (table.nextRow());
        }
        return sociedadDtoList;
    }
}