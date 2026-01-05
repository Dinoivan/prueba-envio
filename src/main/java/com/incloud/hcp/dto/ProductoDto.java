package com.incloud.hcp.dto;

/**
 * Created by Administrador on 30/08/2017.
 */
public class ProductoDto {
    private String marca;
    private String producto;
    private String descripcionAdicional;

    public ProductoDto() {
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescripcionAdicional() {
        return descripcionAdicional;
    }

    public void setDescripcionAdicional(String descripcionAdicional) {
        this.descripcionAdicional = descripcionAdicional;
    }

    @Override
    public String toString() {
        return "ProductoDto{" +
                "marca='" + marca + '\'' +
                ", producto='" + producto + '\'' +
                ", descripcionAdicional='" + descripcionAdicional + '\'' +
                '}';
    }
}
