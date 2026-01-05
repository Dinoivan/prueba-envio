package com.incloud.hcp.jco.documentoAceptacion.service.impl;

import com.incloud.hcp.jco.documentoAceptacion.dto.SapTableItemDto;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DocumentoAceptacionExtractorMapper {

    private JCoParameterList jCoParameterList;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static DocumentoAceptacionExtractorMapper newMapper(JCoParameterList exportParameterList) {
        return new DocumentoAceptacionExtractorMapper(exportParameterList);
    }

    private DocumentoAceptacionExtractorMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public List<SapTableItemDto> getSapTableItemDtoList() {
        List<SapTableItemDto> sapTableItemDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("T_HPEDIDO");

        if (table != null && !table.isEmpty()) {
            do {
                SapTableItemDto sapTableItemDto = new SapTableItemDto();

                sapTableItemDto.setNumeroDocumentoAceptacion(table.getString("BELNR"));
                sapTableItemDto.setNumeroItem(table.getInt("BUZEI"));
                sapTableItemDto.setMovimiento(table.getString("BWART"));
                sapTableItemDto.setNumeroOrdenCompra(table.getString("EBELN"));
                sapTableItemDto.setPosicionOrdenCompra(table.getString("EBELP"));
                sapTableItemDto.setNumeroGuiaProveedor(table.getString("XBLNR"));
                sapTableItemDto.setCodigoMaterial(table.getString("MATNR"));
                sapTableItemDto.setDescripcionMaterial(table.getString("MAKTX"));
                sapTableItemDto.setDescripcionServicio(table.getString("TXZ01"));
                sapTableItemDto.setUnidadMedidaMaterial(table.getString("ERFME"));
                sapTableItemDto.setUnidadMedidaServicio(table.getString("MEINS"));
                sapTableItemDto.setUsuarioSapRecepcion(table.getString("ERNAM"));
                sapTableItemDto.setCodigoMoneda(table.getString("WAERS"));
                sapTableItemDto.setCantidadAceptadaClienteMaterial(Optional.ofNullable(table.getBigDecimal("MENGE")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                sapTableItemDto.setCantidadAceptadaClienteServicio(Optional.ofNullable(table.getBigDecimal("ACT_MENGE")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                sapTableItemDto.setCantidadPendiente(Optional.ofNullable(table.getBigDecimal("MENGEP")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                sapTableItemDto.setPrecioUnitario(Optional.ofNullable(table.getBigDecimal("NETPR")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                sapTableItemDto.setValorRecibido(Optional.ofNullable(table.getBigDecimal("WRBTR")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                sapTableItemDto.setValorRecibidoMonedalocal(Optional.ofNullable(table.getBigDecimal("DMBTR")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                sapTableItemDto.setValorRecibidoServicio(Optional.ofNullable(table.getBigDecimal("ACT_WERT")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                sapTableItemDto.setFechaEmision(table.getDate("ERDAT"));
                sapTableItemDto.setFechaAceptacion(table.getDate("BLDAT"));
                logger.error("BUDAT_MKPF " + table.getDate("BUDAT_MKPF"));
                logger.error("BUDAT " + table.getDate("BUDAT"));
                sapTableItemDto.setFechaContabilizacion( table.getDate("BUDAT_MKPF") != null ? table.getDate("BUDAT_MKPF") : table.getDate("BUDAT") );
                sapTableItemDto.setIndicadorImpuesto(table.getString("TAX_CODE"));
                sapTableItemDto.setNumDocApectacionRelacionado(table.getString("LFBNR"));
                sapTableItemDto.setNumItemRelacionado(table.getInt("LFPOS"));
                sapTableItemDto.setStatus(table.getString("STATUS").trim());

                sapTableItemDtoList.add(sapTableItemDto);
            } while (table.nextRow());
        }
        return sapTableItemDtoList;
    }
}