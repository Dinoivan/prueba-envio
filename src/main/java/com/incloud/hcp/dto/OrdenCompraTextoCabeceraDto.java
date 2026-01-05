package com.incloud.hcp.dto;

public class OrdenCompraTextoCabeceraDto {
    private Integer id;
    private OrdenCompraDto ordenCompra;
    private Integer idOrdenCompra;
    private String numeroOrdenCompra;
    private String linea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdenCompraDto getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompraDto ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    @Override
    public String toString() {
        return "OrdenCompraTextoCabeceraDto{" +
                "id=" + id +
                ", ordenCompra=" + ordenCompra +
                ", idOrdenCompra=" + idOrdenCompra +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", linea='" + linea + '\'' +
                '}';
    }
}
