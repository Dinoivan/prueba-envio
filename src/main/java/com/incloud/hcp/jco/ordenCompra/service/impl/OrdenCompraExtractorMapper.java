package com.incloud.hcp.jco.ordenCompra.service.impl;

import com.incloud.hcp.domain.*;
import com.incloud.hcp.enums.OrdenCompraEstadoSapEnum;
import com.incloud.hcp.enums.OrdenCompraTipoEnum;
import com.incloud.hcp.util.DateUtils;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdenCompraExtractorMapper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JCoParameterList jCoParameterList;

    public static OrdenCompraExtractorMapper newMapper(JCoParameterList exportParameterList) {
        return new OrdenCompraExtractorMapper(exportParameterList);
    }

    private OrdenCompraExtractorMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public List<OrdenCompra> getOrdenCompraList() {
        List<OrdenCompra> ordenCompraList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_HEADER");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenCompra ordenCompra = new OrdenCompra();

                ordenCompra.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                //ordenCompra.setIdTipoOrdenCompra(!(table.getString("DOC_TYPE").equalsIgnoreCase("ZC03")) ? OrdenCompraTipoEnum.MATERIAL.getId() : OrdenCompraTipoEnum.SERVICIO.getId());
                ordenCompra.setIdTipoOrdenCompra(!(table.getString("DOC_TYPE").equalsIgnoreCase("ZC03") || table.getString("DOC_TYPE").equalsIgnoreCase("ZC18")) ? OrdenCompraTipoEnum.MATERIAL.getId() : OrdenCompraTipoEnum.SERVICIO.getId());
                ordenCompra.setIndicadorContratoMarco("0");
                ordenCompra.setEstadoSap((Optional.ofNullable(table.getString("DELETE_IND")).orElse("").
                        equalsIgnoreCase(OrdenCompraEstadoSapEnum.LIBERADA.getCodigo())) ?
                        OrdenCompraEstadoSapEnum.ANULADA.getCodigo() : (table.getString("PO_REL_IND").equalsIgnoreCase("") ?
                        OrdenCompraEstadoSapEnum.ANULADA.getCodigo() : table.getString("PO_REL_IND") ));
                ordenCompra.setCodigoClaseOrdenCompra(table.getString("DOC_TYPE"));
                ordenCompra.setClaseOrdenCompra(table.getString("BATXT"));
                ordenCompra.setSociedad(table.getString("COMP_CODE"));
                ordenCompra.setCompradorUsuarioSap(table.getString("CREATED_BY"));
                ordenCompra.setCompradorNombre(table.getString("NAME_TEXT"));
                ordenCompra.setUltimoLiberadorUsuarioSap(table.getString("USERNAME"));
                ordenCompra.setProveedorCodigoSap(table.getString("VENDOR"));
                ordenCompra.setProveedorRuc(table.getString("STCD1"));
                ordenCompra.setProveedorRazonSocial(table.getString("NAME1"));
                logger.error("Moneda de Orden de Compra: " + table.getString("CURRENCY"));
                ordenCompra.setCodigoMondeda(table.getString("CURRENCY"));
                ordenCompra.setTotal(Optional.ofNullable(table.getBigDecimal("RLWRT")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                ordenCompra.setCondicionPago(table.getString("PMNTTRMS"));
                ordenCompra.setCondicionPagoDescripcion(table.getString("TEXT1"));
                ordenCompra.setFechaEntrega(table.getDate("EINDT"));
                ordenCompra.setFechaRegistro(table.getDate("CREAT_DATE"));
                ordenCompra.setFechaModificacion(table.getDate("UDATE"));
                ordenCompra.setHoraModificacion(DateUtils.dateToTime(table.getDate("UTIME")));
                ordenCompra.setLugarEntrega(table.getString("DELIVDIR1"));
                ordenCompra.setAutorizadorFechaLiberacion(table.getString("AUTORIZADO_POR"));

                ordenCompraList.add(ordenCompra);
            } while (table.nextRow());
        }
        return ordenCompraList;
    }


    public List<OrdenCompra> getOrdenCompraListValidacionLiberada() {
        List<OrdenCompra> ordenCompraList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_HEADER");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenCompra ordenCompra = new OrdenCompra();

                ordenCompra.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                //ordenCompra.setIdTipoOrdenCompra(!(table.getString("DOC_TYPE").equalsIgnoreCase("ZC03")) ? OrdenCompraTipoEnum.MATERIAL.getId() : OrdenCompraTipoEnum.SERVICIO.getId());
                ordenCompra.setIdTipoOrdenCompra(!(table.getString("DOC_TYPE").equalsIgnoreCase("ZC03") || table.getString("DOC_TYPE").equalsIgnoreCase("ZC18")) ? OrdenCompraTipoEnum.MATERIAL.getId() : OrdenCompraTipoEnum.SERVICIO.getId());
                ordenCompra.setIndicadorContratoMarco("0");
                ordenCompra.setEstadoSap(table.getString("PO_REL_IND"));
                ordenCompra.setCodigoClaseOrdenCompra(table.getString("DOC_TYPE"));
                ordenCompra.setClaseOrdenCompra(table.getString("BATXT"));
                ordenCompra.setSociedad(table.getString("COMP_CODE"));
                ordenCompra.setCompradorUsuarioSap(table.getString("CREATED_BY"));
                ordenCompra.setCompradorNombre(table.getString("NAME_TEXT"));
                ordenCompra.setUltimoLiberadorUsuarioSap(table.getString("USERNAME"));
                ordenCompra.setProveedorCodigoSap(table.getString("VENDOR"));
                ordenCompra.setProveedorRuc(table.getString("STCD1"));
                ordenCompra.setProveedorRazonSocial(table.getString("NAME1"));
                ordenCompra.setCodigoMondeda(table.getString("CURRENCY"));
                ordenCompra.setTotal(Optional.ofNullable(table.getBigDecimal("RLWRT")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                ordenCompra.setCondicionPago(table.getString("PMNTTRMS"));
                ordenCompra.setCondicionPagoDescripcion(table.getString("TEXT1"));
                ordenCompra.setFechaEntrega(table.getDate("EINDT"));
                ordenCompra.setFechaRegistro(table.getDate("CREAT_DATE"));
                ordenCompra.setFechaModificacion(table.getDate("UDATE"));
                ordenCompra.setHoraModificacion(DateUtils.dateToTime(table.getDate("UTIME")));
                ordenCompra.setLugarEntrega(table.getString("DELIVDIR1"));
                ordenCompra.setAutorizadorFechaLiberacion(table.getString("AUTORIZADO_POR"));

                ordenCompraList.add(ordenCompra);
            } while (table.nextRow());
        }
        return ordenCompraList;
    }


    public List<OrdenCompraTextoCabecera> getOrdenCompraTextoCabeceraList() {
        List<OrdenCompraTextoCabecera> ordenCompraTextoCabeceraList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_TEXTO_CABECERA");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenCompraTextoCabecera ordenCompraTextoCabecera = new OrdenCompraTextoCabecera();

                ordenCompraTextoCabecera.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                ordenCompraTextoCabecera.setLinea(table.getString("TDLINE"));

                ordenCompraTextoCabeceraList.add(ordenCompraTextoCabecera);
            } while (table.nextRow());
        }
        return ordenCompraTextoCabeceraList;
    }


    public List<OrdenCompraDetalle> getOrdenCompraDetalleList() {
        List<OrdenCompraDetalle> ordenCompraDetalleList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_ITEMS_AUX");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenCompraDetalle ordenCompraDetalle = new OrdenCompraDetalle();

                ordenCompraDetalle.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                logger.error("Posicion de OC: " + table.getString("PO_ITEM"));
                ordenCompraDetalle.setPosicion(table.getString("PO_ITEM"));
                ordenCompraDetalle.setCodigoSapBienServicio(table.getString("MATERIAL"));
                ordenCompraDetalle.setDescripcionBienServicio(table.getString("SHORT_TEXT"));
                ordenCompraDetalle.setUnidadMedidaBienServicio(table.getString("PO_UNIT"));
                ordenCompraDetalle.setCodigoSapCentro(table.getString("PLANT"));
                ordenCompraDetalle.setDenominacionCentro(table.getString("NAME1"));
                ordenCompraDetalle.setDireccionCentro(table.getString("STRAS"));
                ordenCompraDetalle.setCodigoSapAlmacen(table.getString("STGE_LOC"));
                ordenCompraDetalle.setDenominacionAlmacen(table.getString("LGOBE"));
                logger.error("Cantidad de OC: " + table.getBigDecimal("QUANTITY"));
                ordenCompraDetalle.setCantidad(Optional.ofNullable(table.getBigDecimal("QUANTITY")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                logger.error("Precio de OC: " + table.getBigDecimal("NET_PRICE"));
                ordenCompraDetalle.setPrecioUnitario(Optional.ofNullable(table.getBigDecimal("NET_PRICE")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
//                ordenCompraDetalle.setPrecioTotal(Optional.ofNullable(table.getBigDecimal("PRICE_UNIT")).orElse(new BigDecimal("1.00")).setScale(4, RoundingMode.HALF_UP)); // GUARDA LA CANTIDAD BASE TEMPORALMENTE EN EL CAMPO "PRECIO TOTAL"
                logger.error("Precio total OC: " + table.getBigDecimal("PRICE_UNIT"));
                BigDecimal cantidadBase = table.getBigDecimal("PRICE_UNIT");
                ordenCompraDetalle.setPrecioTotal(((cantidadBase != null && cantidadBase.compareTo(BigDecimal.ZERO) != 0) ? cantidadBase : BigDecimal.ONE).setScale(4, RoundingMode.HALF_UP)); // GUARDA LA CANTIDAD BASE TEMPORALMENTE EN EL CAMPO "PRECIO TOTAL"
                logger.error("Indicador Imp de OC: " + table.getString("TAX_CODE"));
                ordenCompraDetalle.setIndicadorImpuesto(table.getString("TAX_CODE"));
                ordenCompraDetalle.setFechaEntrega(table.getDate("EINDT"));

                ordenCompraDetalleList.add(ordenCompraDetalle);
            } while (table.nextRow());
        }
        return ordenCompraDetalleList;
    }


    public List<OrdenCompraDetalleTexto> getOrdenCompraDetalleTextoList() {
        List<OrdenCompraDetalleTexto> ordenCompraDetalleTextList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_TEXT");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenCompraDetalleTexto ordenCompraDetalleTexto = new OrdenCompraDetalleTexto();

                ordenCompraDetalleTexto.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                ordenCompraDetalleTexto.setPosicion(table.getString("PO_ITEM"));
                ordenCompraDetalleTexto.setLinea(table.getString("TDLINE"));

                ordenCompraDetalleTextList.add(ordenCompraDetalleTexto);
            } while (table.nextRow());
        }
        return ordenCompraDetalleTextList;
    }


    public List<OrdenCompraDetalleTextoRegistroInfo> getOrdenCompraDetalleTextoRegistroInfoList() {
        List<OrdenCompraDetalleTextoRegistroInfo> ordenCompraDetalleTextoRegistroInfoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_TEXTO_REG_POS");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenCompraDetalleTextoRegistroInfo ordenCompraDetalleTextoRegistroInfo = new OrdenCompraDetalleTextoRegistroInfo();

                ordenCompraDetalleTextoRegistroInfo.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                ordenCompraDetalleTextoRegistroInfo.setPosicion(table.getString("PO_ITEM"));
                ordenCompraDetalleTextoRegistroInfo.setLinea(table.getString("TDLINE"));

                ordenCompraDetalleTextoRegistroInfoList.add(ordenCompraDetalleTextoRegistroInfo);
            } while (table.nextRow());
        }
        return ordenCompraDetalleTextoRegistroInfoList;
    }

    public List<OrdenCompraDetalleTextoMaterialAmpliado> getOrdenCompraDetalleTextoMaterialAmpliadoList() {
        List<OrdenCompraDetalleTextoMaterialAmpliado> ordenCompraDetalleTextoMaterialAmpliadoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_TEXTO_AMPL_MAT");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenCompraDetalleTextoMaterialAmpliado ordenCompraDetalleTextoMaterialAmpliado = new OrdenCompraDetalleTextoMaterialAmpliado();

                ordenCompraDetalleTextoMaterialAmpliado.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                ordenCompraDetalleTextoMaterialAmpliado.setPosicion(table.getString("PO_ITEM"));
                ordenCompraDetalleTextoMaterialAmpliado.setLinea(table.getString("TDLINE"));

                ordenCompraDetalleTextoMaterialAmpliadoList.add(ordenCompraDetalleTextoMaterialAmpliado);
            } while (table.nextRow());
        }
        return ordenCompraDetalleTextoMaterialAmpliadoList;
    }
}
