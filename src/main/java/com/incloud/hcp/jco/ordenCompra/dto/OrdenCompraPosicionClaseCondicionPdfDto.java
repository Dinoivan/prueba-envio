package com.incloud.hcp.jco.ordenCompra.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrdenCompraPosicionClaseCondicionPdfDto implements Serializable {
	private String numeroOrdenCompra;
    private String posicion;
    private String claseCondicionDescripcion;
    private BigDecimal claseCondicionPrecioUnitario;
    private BigDecimal claseCondicionImporte;


    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getClaseCondicionDescripcion() {
        return claseCondicionDescripcion;
    }

    public void setClaseCondicionDescripcion(String claseCondicionDescripcion) {
        this.claseCondicionDescripcion = claseCondicionDescripcion;
    }

    public BigDecimal getClaseCondicionPrecioUnitario() {
        return claseCondicionPrecioUnitario;
    }

    public void setClaseCondicionPrecioUnitario(BigDecimal claseCondicionPrecioUnitario) {
        this.claseCondicionPrecioUnitario = claseCondicionPrecioUnitario;
    }

    public BigDecimal getClaseCondicionImporte() {
        return claseCondicionImporte;
    }

    public void setClaseCondicionImporte(BigDecimal claseCondicionImporte) {
        this.claseCondicionImporte = claseCondicionImporte;
    }


    @Override
    public String toString() {
        return "OrdenCompraPosicionClaseCondicionPdfDto{" +
                "numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", posicion='" + posicion + '\'' +
                ", claseCondicionDescripcion='" + claseCondicionDescripcion + '\'' +
                ", claseCondicionPrecioUnitario=" + claseCondicionPrecioUnitario +
                ", claseCondicionImporte=" + claseCondicionImporte +
                '}';
    }
}