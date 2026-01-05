package com.incloud.hcp.jco.ordenCompra.dto;

import java.io.Serializable;

public class TextoPosicionDto implements Serializable {
	private String numeroOrdenCompra;
    private String posicion;
    private String linea;


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

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }


    @Override
    public String toString() {
        return "TextoPosicionDto{" +
                "numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", posicion='" + posicion + '\'' +
                ", linea='" + linea + '\'' +
                '}';
    }
}