package com.incloud.hcp.jco.balanza.PedidosTraslado.dto;

public class PedidosTrasladoResponse {
    private String pedido;
    private Integer posicion;
    private Double cantidad;
    private String meins;
    private String cenOri;
    private String almOri;
    private String cenDest;
    private String almDest;
    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getMeins() {
        return meins;
    }

    public void setMeins(String meins) {
        this.meins = meins;
    }

    public String getCenOri() {
        return cenOri;
    }

    public void setCenOri(String cenOri) {
        this.cenOri = cenOri;
    }

    public String getAlmOri() {
        return almOri;
    }

    public void setAlmOri(String almOri) {
        this.almOri = almOri;
    }

    public String getCenDest() {
        return cenDest;
    }

    public void setCenDest(String cenDest) {
        this.cenDest = cenDest;
    }

    public String getAlmDest() {
        return almDest;
    }

    public void setAlmDest(String almDest) {
        this.almDest = almDest;
    }

    @Override
    public String toString() {
        return "PedidosTrasladoResponse{" +
                "pedido='" + pedido + '\'' +
                ", posicion=" + posicion +
                ", cantidad=" + cantidad +
                ", meins='" + meins + '\'' +
                ", cenOri='" + cenOri + '\'' +
                ", almOri='" + almOri + '\'' +
                ", cenDest='" + cenDest + '\'' +
                ", almDest='" + almDest + '\'' +
                '}';
    }
}
