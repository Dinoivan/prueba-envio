package com.incloud.hcp.bean;

public class OrdenCompraActivoCustom {

    private String numeroOrdenCompra;
    private Integer numeroAgrupado;

    public  OrdenCompraActivoCustom() {

    }
    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public Integer getNumeroAgrupado() {
        return numeroAgrupado;
    }

    public void setNumeroAgrupado(Integer numeroAgrupado) {
        this.numeroAgrupado = numeroAgrupado;
    }
}
