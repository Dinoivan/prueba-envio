package com.incloud.hcp.jco.contratoMarco.service.impl;

import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.domain.OrdenCompraDetalle;
import com.incloud.hcp.enums.OrdenCompraEstadoSapEnum;
import com.incloud.hcp.enums.OrdenCompraTipoEnum;
import com.incloud.hcp.jco.contratoMarco.dto.ContratoMarcoPdfSapDto;
import com.incloud.hcp.util.DateUtils;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContratoMarcoExtractorMapper {

    private JCoParameterList jCoParameterList;

    public static ContratoMarcoExtractorMapper newMapper(JCoParameterList exportParameterList) {
        return new ContratoMarcoExtractorMapper(exportParameterList);
    }

    private ContratoMarcoExtractorMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public List<OrdenCompra> getContratoMarcoList() {
        List<OrdenCompra> contratoMarcoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("T_HEADER");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenCompra contratoMarco = new OrdenCompra();

                contratoMarco.setNumeroOrdenCompra(table.getString("EBELN"));
//                contratoMarco.setIdTipoOrdenCompra(!(table.getString("BSART").equalsIgnoreCase("ZC03")) ? OrdenCompraTipoEnum.MATERIAL.getId() : OrdenCompraTipoEnum.SERVICIO.getId());
                contratoMarco.setIdTipoOrdenCompra(OrdenCompraTipoEnum.MATERIAL.getId()); // todas la clases d documento (ZCMK,ZCWK,ZCP1) serian de tipo material
                contratoMarco.setIndicadorContratoMarco("1");
                contratoMarco.setEstadoSap((table.getString("LOEKZ").equalsIgnoreCase(OrdenCompraEstadoSapEnum.LIBERADA.getCodigo())) ?
                        OrdenCompraEstadoSapEnum.ANULADA.getCodigo() : (table.getString("FRGKE").equalsIgnoreCase("") ?
                        OrdenCompraEstadoSapEnum.ANULADA.getCodigo() : table.getString("FRGKE")));
                contratoMarco.setCodigoClaseOrdenCompra(table.getString("BSART"));
                contratoMarco.setClaseOrdenCompra(table.getString("BATXT"));
                contratoMarco.setSociedad(table.getString("BUKRS"));
                contratoMarco.setCompradorUsuarioSap(table.getString("ERNAM"));
                contratoMarco.setCompradorNombre(table.getString("NAME_TEXT"));
                contratoMarco.setUltimoLiberadorUsuarioSap(table.getString("USERNAME"));
                contratoMarco.setProveedorCodigoSap(table.getString("LIFNR"));
                contratoMarco.setProveedorRuc(table.getString("STCD1"));
                contratoMarco.setProveedorRazonSocial(table.getString("NAME1"));
                contratoMarco.setCodigoMondeda(table.getString("WAERS"));
                contratoMarco.setTotal(Optional.ofNullable(table.getBigDecimal("RLWRT")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                contratoMarco.setCondicionPago(table.getString("ZTERM"));
                contratoMarco.setCondicionPagoDescripcion(table.getString("TEXT1"));
                contratoMarco.setFechaEntrega(table.getDate("KDATB"));
                contratoMarco.setFechaRegistro(table.getDate("AEDAT"));
                contratoMarco.setFechaModificacion(table.getDate("UDATE"));
                contratoMarco.setHoraModificacion(DateUtils.dateToTime(table.getDate("UTIME")));
                contratoMarco.setAutorizadorFechaLiberacion(table.getString("AUTORIZADO_POR"));

                contratoMarcoList.add(contratoMarco);
            } while (table.nextRow());
        }
        return contratoMarcoList;
    }


    public List<OrdenCompraDetalle> getContratoMarcoDetalleList() {
        List<OrdenCompraDetalle> contratoMarcoDetalleList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("T_DETALLE");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenCompraDetalle contratoMarcoDetalle = new OrdenCompraDetalle();

                contratoMarcoDetalle.setNumeroOrdenCompra(table.getString("EBELN"));
                contratoMarcoDetalle.setPosicion(table.getString("EBELP"));
                contratoMarcoDetalle.setCodigoSapBienServicio(table.getString("MATNR"));
                contratoMarcoDetalle.setDescripcionBienServicio(table.getString("TXZ01"));
                contratoMarcoDetalle.setUnidadMedidaBienServicio(table.getString("MEINS"));
                contratoMarcoDetalle.setCodigoSapCentro(table.getString("WERKS"));
                contratoMarcoDetalle.setDenominacionCentro(table.getString("NAME1"));
                contratoMarcoDetalle.setDireccionCentro(table.getString("STRAS"));
                contratoMarcoDetalle.setCodigoSapAlmacen(table.getString("LGORT"));
                contratoMarcoDetalle.setDenominacionAlmacen(table.getString("LGOBE"));
                contratoMarcoDetalle.setCantidad(Optional.ofNullable(table.getBigDecimal("MENGE")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                contratoMarcoDetalle.setPrecioUnitario(Optional.ofNullable(table.getBigDecimal("NETPR")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                BigDecimal cantidadBase = table.getBigDecimal("PEINH");
                contratoMarcoDetalle.setPrecioTotal(((cantidadBase != null && cantidadBase.compareTo(BigDecimal.ZERO) != 0) ? cantidadBase : BigDecimal.ONE).setScale(4, RoundingMode.HALF_UP)); // GUARDA LA CANTIDAD BASE TEMPORALMENTE EN EL CAMPO "PRECIO TOTAL"
                contratoMarcoDetalle.setIndicadorImpuesto(table.getString("MWSKZ"));
                contratoMarcoDetalle.setFechaEntrega(table.getDate("EINDT"));

                contratoMarcoDetalleList.add(contratoMarcoDetalle);
            } while (table.nextRow());
        }
        return contratoMarcoDetalleList;
    }


    public List<ContratoMarcoPdfSapDto> getContratoMarcoPdfDtoList() {
        List<ContratoMarcoPdfSapDto> contratoMarcoPdfSapDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("O_CM_DETALLE");

        if (table != null && !table.isEmpty()) {
            do {
                ContratoMarcoPdfSapDto contratoMarcoPdf = new ContratoMarcoPdfSapDto();

                contratoMarcoPdf.setClientePersonaContacto(table.getString("COMPRADOR"));
                contratoMarcoPdf.setClienteAutorizador(table.getString("LIBERADOR"));
                contratoMarcoPdf.setProveedorDireccion(table.getString("DIRECCION"));
                contratoMarcoPdf.setProveedorContactoNombre(table.getString("CONTACTO"));
                contratoMarcoPdf.setProveedorContactoTelefono(table.getString("TELEFONO"));

                contratoMarcoPdfSapDtoList.add(contratoMarcoPdf);
            } while (table.nextRow());
        }
        return contratoMarcoPdfSapDtoList;
    }
}
