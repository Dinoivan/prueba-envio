package com.incloud.hcp.jco.ordenCompra.service.impl;

import com.incloud.hcp.domain.almacen.*;
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

public class OrdenDespachoExtractorMapper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JCoParameterList jCoParameterList;

    public static OrdenDespachoExtractorMapper newMapper(JCoParameterList exportParameterList) {
        return new OrdenDespachoExtractorMapper(exportParameterList);
    }

    private OrdenDespachoExtractorMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public List<OrdenDespacho> getOrdenDespachoList() {
        List<OrdenDespacho> ordenDespachoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_HEADER");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenDespacho ordenDespacho = new OrdenDespacho();

                String docType = table.getString("DOC_TYPE");


                if (docType.equalsIgnoreCase("ZC06")
                        || docType.equalsIgnoreCase("ZC07")
                        || docType.equalsIgnoreCase("ZC01")
                        || docType.equalsIgnoreCase("ZC09")) {
                    ordenDespacho.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                    ordenDespacho.setIdTipoOrdenCompra(OrdenCompraTipoEnum.MATERIAL.getId());
                    ordenDespacho.setIndicadorContratoMarco("0");
                    ordenDespacho.setEstadoSap((Optional.ofNullable(table.getString("DELETE_IND")).orElse("").
                            equalsIgnoreCase(OrdenCompraEstadoSapEnum.LIBERADA.getCodigo())) ?
                            OrdenCompraEstadoSapEnum.ANULADA.getCodigo() : (table.getString("PO_REL_IND").equalsIgnoreCase("") ?
                            OrdenCompraEstadoSapEnum.ANULADA.getCodigo() : table.getString("PO_REL_IND")));
                    ordenDespacho.setCodigoClaseOrdenCompra(table.getString("DOC_TYPE"));
                    ordenDespacho.setClaseOrdenCompra(table.getString("BATXT"));
                    ordenDespacho.setSociedad(table.getString("COMP_CODE"));
                    ordenDespacho.setCompradorUsuarioSap(table.getString("CREATED_BY"));
                    ordenDespacho.setCompradorNombre(table.getString("NAME_TEXT"));
                    ordenDespacho.setUltimoLiberadorUsuarioSap(table.getString("USERNAME"));
                    ordenDespacho.setProveedorCodigoSap(table.getString("VENDOR"));
                    ordenDespacho.setProveedorRuc(table.getString("STCD1"));
                    ordenDespacho.setProveedorRazonSocial(table.getString("NAME1"));
                    logger.error("Moneda de Orden de Compra: " + table.getString("CURRENCY"));
                    ordenDespacho.setCodigoMondeda(table.getString("CURRENCY"));
                    ordenDespacho.setTotal(Optional.ofNullable(table.getBigDecimal("RLWRT")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                    ordenDespacho.setCondicionPago(table.getString("PMNTTRMS"));
                    ordenDespacho.setCondicionPagoDescripcion(table.getString("TEXT1"));
                    ordenDespacho.setFechaEntrega(table.getDate("EINDT"));
                    ordenDespacho.setFechaRegistro(table.getDate("CREAT_DATE"));
                    ordenDespacho.setFechaModificacion(table.getDate("UDATE"));
                    ordenDespacho.setHoraModificacion(DateUtils.dateToTime(table.getDate("UTIME")));
                    ordenDespacho.setLugarEntrega(table.getString("DELIVDIR1"));
                    ordenDespacho.setAutorizadorFechaLiberacion(table.getString("AUTORIZADO_POR"));
                }

                ordenDespachoList.add(ordenDespacho);
            } while (table.nextRow());
        }
        return ordenDespachoList;
    }


    public List<OrdenDespacho> getOrdenCompraListValidacionLiberada() {
        List<OrdenDespacho> ordenDespachoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_HEADER");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenDespacho ordenDespacho = new OrdenDespacho();

                ordenDespacho.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                ordenDespacho.setIdTipoOrdenCompra(!(table.getString("DOC_TYPE").equalsIgnoreCase("ZC03") || table.getString("DOC_TYPE").equalsIgnoreCase("ZC18")) ? OrdenCompraTipoEnum.MATERIAL.getId() : OrdenCompraTipoEnum.SERVICIO.getId());
                ordenDespacho.setIndicadorContratoMarco("0");
                ordenDespacho.setEstadoSap(table.getString("PO_REL_IND"));
                ordenDespacho.setCodigoClaseOrdenCompra(table.getString("DOC_TYPE"));
                ordenDespacho.setClaseOrdenCompra(table.getString("BATXT"));
                ordenDespacho.setSociedad(table.getString("COMP_CODE"));
                ordenDespacho.setCompradorUsuarioSap(table.getString("CREATED_BY"));
                ordenDespacho.setCompradorNombre(table.getString("NAME_TEXT"));
                ordenDespacho.setUltimoLiberadorUsuarioSap(table.getString("USERNAME"));
                ordenDespacho.setProveedorCodigoSap(table.getString("VENDOR"));
                ordenDespacho.setProveedorRuc(table.getString("STCD1"));
                ordenDespacho.setProveedorRazonSocial(table.getString("NAME1"));
                ordenDespacho.setCodigoMondeda(table.getString("CURRENCY"));
                ordenDespacho.setTotal(Optional.ofNullable(table.getBigDecimal("RLWRT")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                ordenDespacho.setCondicionPago(table.getString("PMNTTRMS"));
                ordenDespacho.setCondicionPagoDescripcion(table.getString("TEXT1"));
                ordenDespacho.setFechaEntrega(table.getDate("EINDT"));
                ordenDespacho.setFechaRegistro(table.getDate("CREAT_DATE"));
                ordenDespacho.setFechaModificacion(table.getDate("UDATE"));
                ordenDespacho.setHoraModificacion(DateUtils.dateToTime(table.getDate("UTIME")));
                ordenDespacho.setLugarEntrega(table.getString("DELIVDIR1"));
                ordenDespacho.setAutorizadorFechaLiberacion(table.getString("AUTORIZADO_POR"));

                ordenDespachoList.add(ordenDespacho);
            } while (table.nextRow());
        }
        return ordenDespachoList;
    }


    public List<OrdenDespachoTextoCabecera> getOrdenCompraTextoCabeceraList() {
        List<OrdenDespachoTextoCabecera> ordenCompraTextoCabeceraList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_TEXTO_CABECERA");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenDespachoTextoCabecera ordenCompraTextoCabecera = new OrdenDespachoTextoCabecera();

                ordenCompraTextoCabecera.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                ordenCompraTextoCabecera.setLinea(table.getString("TDLINE"));

                ordenCompraTextoCabeceraList.add(ordenCompraTextoCabecera);
            } while (table.nextRow());
        }
        return ordenCompraTextoCabeceraList;
    }


    public List<OrdenDespachoDetalle> getOrdenDespachoDetalleList() {
        List<OrdenDespachoDetalle> ordenDespachoDetalleList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_ITEMS_AUX");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenDespachoDetalle ordenDespachoDetalle = new OrdenDespachoDetalle();

                ordenDespachoDetalle.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                logger.error("Posicion de OC: " + table.getString("PO_ITEM"));
                ordenDespachoDetalle.setPosicion(table.getString("PO_ITEM"));
                ordenDespachoDetalle.setCodigoSapBienServicio(table.getString("MATERIAL"));
                ordenDespachoDetalle.setDescripcionBienServicio(table.getString("SHORT_TEXT"));
                ordenDespachoDetalle.setUnidadMedidaBienServicio(table.getString("PO_UNIT"));
                ordenDespachoDetalle.setCodigoSapCentro(table.getString("PLANT"));
                ordenDespachoDetalle.setDenominacionCentro(table.getString("NAME1"));
                ordenDespachoDetalle.setDireccionCentro(table.getString("STRAS"));
                ordenDespachoDetalle.setCodigoSapAlmacen(table.getString("STGE_LOC"));
                ordenDespachoDetalle.setDenominacionAlmacen(table.getString("LGOBE"));
                logger.error("Cantidad de OC: " + table.getBigDecimal("QUANTITY"));
                ordenDespachoDetalle.setCantidad(Optional.ofNullable(table.getBigDecimal("QUANTITY")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                logger.error("Precio de OC: " + table.getBigDecimal("NET_PRICE"));
                ordenDespachoDetalle.setPrecioUnitario(Optional.ofNullable(table.getBigDecimal("NET_PRICE")).orElse(BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP));
                logger.error("Precio total OC: " + table.getBigDecimal("PRICE_UNIT"));
                BigDecimal cantidadBase = table.getBigDecimal("PRICE_UNIT");
                ordenDespachoDetalle.setPrecioTotal(((cantidadBase != null && cantidadBase.compareTo(BigDecimal.ZERO) != 0) ? cantidadBase : BigDecimal.ONE).setScale(4, RoundingMode.HALF_UP)); // GUARDA LA CANTIDAD BASE TEMPORALMENTE EN EL CAMPO "PRECIO TOTAL"
                logger.error("Indicador Imp de OC: " + table.getString("TAX_CODE"));
                ordenDespachoDetalle.setIndicadorImpuesto(table.getString("TAX_CODE"));
                ordenDespachoDetalle.setFechaEntrega(table.getDate("EINDT"));

                ordenDespachoDetalleList.add(ordenDespachoDetalle);
            } while (table.nextRow());
        }
        return ordenDespachoDetalleList;
    }


    public List<OrdenDespachoDetalleTexto> getOrdenCompraDetalleTextoList() {
        List<OrdenDespachoDetalleTexto> ordenCompraDetalleTextList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_TEXT");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenDespachoDetalleTexto ordenCompraDetalleTexto = new OrdenDespachoDetalleTexto();

                ordenCompraDetalleTexto.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                ordenCompraDetalleTexto.setPosicion(table.getString("PO_ITEM"));
                ordenCompraDetalleTexto.setLinea(table.getString("TDLINE"));

                ordenCompraDetalleTextList.add(ordenCompraDetalleTexto);
            } while (table.nextRow());
        }
        return ordenCompraDetalleTextList;
    }


    public List<OrdenDespachoDetalleTextoRegistroInfo> getOrdenCompraDetalleTextoRegistroInfoList() {
        List<OrdenDespachoDetalleTextoRegistroInfo> ordenCompraDetalleTextoRegistroInfoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_TEXTO_REG_POS");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenDespachoDetalleTextoRegistroInfo ordenCompraDetalleTextoRegistroInfo = new OrdenDespachoDetalleTextoRegistroInfo();

                ordenCompraDetalleTextoRegistroInfo.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                ordenCompraDetalleTextoRegistroInfo.setPosicion(table.getString("PO_ITEM"));
                ordenCompraDetalleTextoRegistroInfo.setLinea(table.getString("TDLINE"));

                ordenCompraDetalleTextoRegistroInfoList.add(ordenCompraDetalleTextoRegistroInfo);
            } while (table.nextRow());
        }
        return ordenCompraDetalleTextoRegistroInfoList;
    }

    public List<OrdenDespachoDetalleTextoMaterialAmpliado> getOrdenCompraDetalleTextoMaterialAmpliadoList() {
        List<OrdenDespachoDetalleTextoMaterialAmpliado> ordenCompraDetalleTextoMaterialAmpliadoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("PO_TEXTO_AMPL_MAT");

        if (table != null && !table.isEmpty()) {
            do {
                OrdenDespachoDetalleTextoMaterialAmpliado ordenCompraDetalleTextoMaterialAmpliado = new OrdenDespachoDetalleTextoMaterialAmpliado();

                ordenCompraDetalleTextoMaterialAmpliado.setNumeroOrdenCompra(table.getString("PO_NUMBER"));
                ordenCompraDetalleTextoMaterialAmpliado.setPosicion(table.getString("PO_ITEM"));
                ordenCompraDetalleTextoMaterialAmpliado.setLinea(table.getString("TDLINE"));

                ordenCompraDetalleTextoMaterialAmpliadoList.add(ordenCompraDetalleTextoMaterialAmpliado);
            } while (table.nextRow());
        }
        return ordenCompraDetalleTextoMaterialAmpliadoList;
    }
}
