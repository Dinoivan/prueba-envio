package com.incloud.hcp.jco.ordenCompra.dto;

import java.io.Serializable;
import java.util.List;

public class OrdenCompraPosicionDataAdicionalPdfDto implements Serializable {
    private String textoRegistroInfo;
    private String textoPosicion;

    private List<OrdenCompraPosicionClaseCondicionPdfDto> ordenCompraPosicionClaseCondicionPdfDtoList;


    public String getTextoRegistroInfo() {
        return textoRegistroInfo;
    }

    public void setTextoRegistroInfo(String textoRegistroInfo) {
        this.textoRegistroInfo = textoRegistroInfo;
    }

    public String getTextoPosicion() {
        return textoPosicion;
    }

    public void setTextoPosicion(String textoPosicion) {
        this.textoPosicion = textoPosicion;
    }

    public List<OrdenCompraPosicionClaseCondicionPdfDto> getOrdenCompraPosicionClaseCondicionPdfDtoList() {
        return ordenCompraPosicionClaseCondicionPdfDtoList;
    }

    public void setOrdenCompraPosicionClaseCondicionPdfDtoList(List<OrdenCompraPosicionClaseCondicionPdfDto> ordenCompraPosicionClaseCondicionPdfDtoList) {
        this.ordenCompraPosicionClaseCondicionPdfDtoList = ordenCompraPosicionClaseCondicionPdfDtoList;
    }


    @Override
    public String toString() {
        return "OrdenCompraPosicionDataAdicionalPdfDto{" +
                "textoRegistroInfo='" + textoRegistroInfo + '\'' +
                ", textoPosicion='" + textoPosicion + '\'' +
                ", ordenCompraPosicionClaseCondicionPdfDtoList=" + ordenCompraPosicionClaseCondicionPdfDtoList +
                '}';
    }
}