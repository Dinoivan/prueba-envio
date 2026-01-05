package com.incloud.hcp.jco.ordenCompra.service.impl;

import com.incloud.hcp.enums.OrdenCompraTipoEnum;
import com.incloud.hcp.jco.ordenCompra.dto.*;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdenCompraPdfExtractorMapper {

    private JCoParameterList jCoParameterList;

    public static OrdenCompraPdfExtractorMapper newMapper(JCoParameterList exportParameterList) {
        return new OrdenCompraPdfExtractorMapper(exportParameterList);
    }

    private OrdenCompraPdfExtractorMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public List<OrdenCompraPdfDto> getOrdenCompraPdfDtoList() {
        List<OrdenCompraPdfDto> ordenCompraPdfDtoList = new ArrayList<>();
        JCoTable headerTable = jCoParameterList.getTable("PO_HEADER");

        if (headerTable != null && !headerTable.isEmpty()) {
            do {
                OrdenCompraPdfDto ordenCompraPdfDto = new OrdenCompraPdfDto();

                ordenCompraPdfDto.setOrdenCompraNumero(headerTable.getString("PO_NUMBER"));
                //ordenCompraPdfDto.setOrdenCompraTipo(!(headerTable.getString("DOC_TYPE").equalsIgnoreCase("ZC03")) ? OrdenCompraTipoEnum.MATERIAL.getDescripcion() : OrdenCompraTipoEnum.SERVICIO.getDescripcion());
                ordenCompraPdfDto.setOrdenCompraTipo(!(headerTable.getString("DOC_TYPE").equalsIgnoreCase("ZC03")  || headerTable.getString("DOC_TYPE").equalsIgnoreCase("ZC18")) ? OrdenCompraTipoEnum.MATERIAL.getDescripcion() : OrdenCompraTipoEnum.SERVICIO.getDescripcion());
                ordenCompraPdfDto.setOrdenCompraFechaCreacion(headerTable.getDate("CREAT_DATE"));
                ordenCompraPdfDto.setOrdenCompraPersonaContacto(headerTable.getString("PERS_CONT"));
                ordenCompraPdfDto.setOrdenCompraFormaPago(headerTable.getString("TEXT1"));
                ordenCompraPdfDto.setOrdenCompraMoneda(headerTable.getString("CURRENCY"));
                ordenCompraPdfDto.setOrdenCompraAutorizador(headerTable.getString("AUTORIZADO_POR"));

                ordenCompraPdfDto.setProveedorRazonSocial(headerTable.getString("NAME1"));
                ordenCompraPdfDto.setProveedorRuc(headerTable.getString("STCD1"));
                ordenCompraPdfDto.setProveedorDireccion(headerTable.getString("LIFNRDIR1"));
                ordenCompraPdfDto.setProveedorContactoNombre(headerTable.getString("SALES_PERS"));
                ordenCompraPdfDto.setProveedorContactoTelefono(headerTable.getString("TELEPHONE"));
                ordenCompraPdfDto.setProveedorNumero(headerTable.getString("VENDOR"));

                ordenCompraPdfDto.setLugarEntrega(headerTable.getString("DELIVDIR1"));
                ordenCompraPdfDto.setAlmacenMaterialEmail(headerTable.getString("CORREO"));
                ordenCompraPdfDto.setAlmacenMaterialTelefono1(headerTable.getString("TELEF"));
                ordenCompraPdfDto.setAlmacenMaterialTelefono2(headerTable.getString("MOVIL"));

                ordenCompraPdfDtoList.add(ordenCompraPdfDto);
            } while (headerTable.nextRow());
        }
        return ordenCompraPdfDtoList;
    }


    public List<SociedadDto> getSociedadDtoList() {
        List<SociedadDto> sociedadDtoList = new ArrayList<>();
        JCoTable sociedadTable = jCoParameterList.getTable("PO_T001");

        if (sociedadTable != null && !sociedadTable.isEmpty()) {
            do {
                SociedadDto sociedadDto = new SociedadDto();

                sociedadDto.setRazonSocial(sociedadTable.getString("NAME1"));
                sociedadDto.setRuc(sociedadTable.getString("PAVAL"));
                sociedadDto.setCalle(sociedadTable.getString("STREET"));
                sociedadDto.setNumero(sociedadTable.getString("HOUSE_NUM1"));
                sociedadDto.setPoblacion(sociedadTable.getString("CITY1"));
                sociedadDto.setDistrito(sociedadTable.getString("CITY2"));
                sociedadDto.setTelefono(sociedadTable.getString("TEL_NUMBER"));

                sociedadDtoList.add(sociedadDto);
            } while (sociedadTable.nextRow());
        }
        return sociedadDtoList;
    }


    public List<OrdenCompraPosicionPdfDto> getOrdenCompraPosicionPdfDtoList() {
        List<OrdenCompraPosicionPdfDto> ordenCompraPosicionPdfDtoList = new ArrayList<>();
        JCoTable itemsTable = jCoParameterList.getTable("PO_ITEMS_AUX");

        if (itemsTable != null && !itemsTable.isEmpty()) {
            do {
                OrdenCompraPosicionPdfDto posicionPdfDto = new OrdenCompraPosicionPdfDto();

                posicionPdfDto.setNumeroOrdenCompra(itemsTable.getString("PO_NUMBER"));
                posicionPdfDto.setPosicion(itemsTable.getString("PO_ITEM"));
                posicionPdfDto.setCentro(itemsTable.getString("NAME1"));
                posicionPdfDto.setMaterial(itemsTable.getString("MATERIAL"));
                posicionPdfDto.setDescripcion(itemsTable.getString("SHORT_TEXT"));
                posicionPdfDto.setCantidad(Optional.ofNullable(itemsTable.getBigDecimal("QUANTITY")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
                posicionPdfDto.setUnidad(itemsTable.getString("PO_UNIT"));
                posicionPdfDto.setFechaEntrega(itemsTable.getDate("EINDT"));
                posicionPdfDto.setPrecioUnitario(Optional.ofNullable(itemsTable.getBigDecimal("NET_PRICE")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
//                posicionPdfDto.setCantidadBase(Optional.ofNullable(itemsTable.getBigDecimal("PRICE_UNIT")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
                BigDecimal cantidadBase = itemsTable.getBigDecimal("PRICE_UNIT");
                posicionPdfDto.setCantidadBase(((cantidadBase != null && cantidadBase.compareTo(BigDecimal.ZERO) != 0) ? cantidadBase : BigDecimal.ONE).setScale(2, RoundingMode.HALF_UP));
//                posicionPdfDto.setRecargoDescuentoDescripcion(itemsTable.getString("VTEXT"));
//                posicionPdfDto.setRecargoDescuentoPrecioUnitario(Optional.ofNullable(itemsTable.getBigDecimal("KBETR")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
//                posicionPdfDto.setRecargoDescuentoImporte(Optional.ofNullable(itemsTable.getBigDecimal("KWERT")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));

                ordenCompraPosicionPdfDtoList.add(posicionPdfDto);
            } while (itemsTable.nextRow());
        }
        return ordenCompraPosicionPdfDtoList;
    }


    public List<OrdenCompraPosicionClaseCondicionPdfDto> getClaseCondicionPdfDtoList() {
        List<OrdenCompraPosicionClaseCondicionPdfDto> claseCondicionPdfDtoList = new ArrayList<>();
        JCoTable claseCondicionTable = jCoParameterList.getTable("PO_DSCTORECARGO");

        if (claseCondicionTable != null && !claseCondicionTable.isEmpty()) {
            do {
                OrdenCompraPosicionClaseCondicionPdfDto claseCondicionPdfDto = new OrdenCompraPosicionClaseCondicionPdfDto();

                claseCondicionPdfDto.setNumeroOrdenCompra(claseCondicionTable.getString("PO_NUMBER"));
                claseCondicionPdfDto.setPosicion(claseCondicionTable.getString("PO_ITEM"));
                claseCondicionPdfDto.setClaseCondicionDescripcion(claseCondicionTable.getString("VTEXT"));
                claseCondicionPdfDto.setClaseCondicionPrecioUnitario(Optional.ofNullable(claseCondicionTable.getBigDecimal("KBETR")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
                claseCondicionPdfDto.setClaseCondicionImporte(Optional.ofNullable(claseCondicionTable.getBigDecimal("KWERT")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));

                claseCondicionPdfDtoList.add(claseCondicionPdfDto);
            } while (claseCondicionTable.nextRow());
        }
        return claseCondicionPdfDtoList;
    }


    public List<TextoPosicionDto> getTextoPosicionList(String nombreTablaSap) {
        List<TextoPosicionDto> textoPosicionList = new ArrayList<>();
        JCoTable textoTable = jCoParameterList.getTable(nombreTablaSap);

        if (textoTable != null && !textoTable.isEmpty()) {
            do {
                TextoPosicionDto textoPosicionDto = new TextoPosicionDto();

                textoPosicionDto.setNumeroOrdenCompra(textoTable.getString("PO_NUMBER"));
                textoPosicionDto.setPosicion(textoTable.getString("PO_ITEM"));
                textoPosicionDto.setLinea(textoTable.getString("TDLINE"));

                textoPosicionList.add(textoPosicionDto);
            } while (textoTable.nextRow());
        }
        return textoPosicionList;
    }


    public List<String> getTextoList(String nombreTablaSap) {
        List<String> textoList = new ArrayList<>();
        JCoTable textoTable = jCoParameterList.getTable(nombreTablaSap);

        if (textoTable != null && !textoTable.isEmpty()) {
            do {
                textoList.add(textoTable.getString("TDLINE"));
            }
            while (textoTable.nextRow());
        }
        return textoList;
    }
}
