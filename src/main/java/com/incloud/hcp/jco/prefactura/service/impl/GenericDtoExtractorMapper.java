package com.incloud.hcp.jco.prefactura.service.impl;

import com.incloud.hcp.jco.prefactura.dto.HojaServicioSubPosicionDto;
import com.incloud.hcp.jco.prefactura.dto.PrefacturaAnuladaDto;
import com.incloud.hcp.jco.prefactura.dto.PrefacturaRegistradaSapDto;
import com.incloud.hcp.util.DateUtils;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenericDtoExtractorMapper {

    private JCoParameterList jCoParameterList;

    public static GenericDtoExtractorMapper newMapper(JCoParameterList exportParameterList) {
        return new GenericDtoExtractorMapper(exportParameterList);
    }

    private GenericDtoExtractorMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public List<HojaServicioSubPosicionDto> getSubPosicionDtoList() {
        List<HojaServicioSubPosicionDto> subPosicionDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("OT_ENTRYSHEET_SERVICES");

        if (table != null && !table.isEmpty()) {
            do {
                HojaServicioSubPosicionDto subPosicion = new HojaServicioSubPosicionDto();

                subPosicion.setCodigoServicio(table.getString("SERVICE"));
                subPosicion.setDescripcionServicio(table.getString("SHORT_TEXT"));
                subPosicion.setIndicadorDetraccion(table.getString("WT_WITHCD"));
                subPosicion.setCantidad(Optional.ofNullable(table.getBigDecimal("QUANTITY")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                subPosicion.setPrecioUnitario(Optional.ofNullable(table.getBigDecimal("GR_PRICE")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                subPosicion.setImporte(Optional.ofNullable(table.getBigDecimal("NET_VALUE")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));

                subPosicionDtoList.add(subPosicion);
            } while (table.nextRow());
        }
        return subPosicionDtoList;
    }


    public List<PrefacturaAnuladaDto> getPrefacturaAnuladaDtoList() {
        List<PrefacturaAnuladaDto> prefacturaAnuladaDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("OT_ANULADAS");

        if (table != null && !table.isEmpty()) {
            do {
                PrefacturaAnuladaDto prefacturaAnulada = new PrefacturaAnuladaDto();

                prefacturaAnulada.setCodigoDocumentoContable(table.getString("BELNR"));
                prefacturaAnulada.setEjercicio(table.getString("GJAHR"));
                prefacturaAnulada.setFechaAnulacion(table.getDate("CPUDT"));
                prefacturaAnulada.setStringFechaAnulacion(DateUtils.utilDateToString(prefacturaAnulada.getFechaAnulacion()));
                prefacturaAnulada.setReferencia(table.getString("XBLNR"));
                prefacturaAnulada.setSociedad(table.getString("BUKRS"));
                prefacturaAnulada.setCodigoDocumentoContableAnulador(table.getString("STBLG"));
                prefacturaAnulada.setEjercicioAnulador(table.getString("STJAH"));

                prefacturaAnuladaDtoList.add(prefacturaAnulada);
            } while (table.nextRow());
        }
        return prefacturaAnuladaDtoList;
    }


    public List<PrefacturaRegistradaSapDto> getPrefacturaRegistradaSapDtoList() {
        List<PrefacturaRegistradaSapDto> prefacturaRegistradaSapDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("OT_DETALLE_FACTURAS");

        if (table != null && !table.isEmpty()) {
            do {
                PrefacturaRegistradaSapDto prefacturaRegistrada = new PrefacturaRegistradaSapDto();

                prefacturaRegistrada.setReferencia(table.getString("XBLNR"));
                prefacturaRegistrada.setSociedad(table.getString("BUKRS"));
                prefacturaRegistrada.setRucProveedor(table.getString("STCD1"));
                prefacturaRegistrada.setNumeroDocumentoFacturaLogistica(table.getString("BELNR_L"));
                prefacturaRegistrada.setNumeroDocumentoContable(table.getString("BELNR_C"));
                prefacturaRegistrada.setEjercicio(table.getString("GJAHR"));
                prefacturaRegistrada.setUsuarioSapRegistrador(table.getString("ERFNAM"));
                prefacturaRegistrada.setFechaRegistro(table.getDate("CPUDT"));
                prefacturaRegistrada.setHoraRegistro(DateUtils.dateToTime(table.getDate("CPUTM")));
                prefacturaRegistrada.setFechaBase(table.getDate("ZFBDT"));
                prefacturaRegistrada.setFechaContabilizacion(table.getDate("BUDAT"));
                prefacturaRegistrada.setImporte(Optional.ofNullable(table.getBigDecimal("RMWWR")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
                prefacturaRegistrada.setIndicadorImpuesto(table.getString("MWSKZ1"));

                prefacturaRegistradaSapDtoList.add(prefacturaRegistrada);
            } while (table.nextRow());
        }
        return prefacturaRegistradaSapDtoList;
    }
}
