package com.incloud.hcp.jco.balanza.PedidosVentas.dto;

public class PedidosVentasResponse {
    private String pedido;
    private Integer posicion;
    private Double cantidad;
    private String lote;
    private String almacen;

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

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    @Override
    public String toString() {
        return "PedidosVentasResponse{" +
                "pedido='" + pedido + '\'' +
                ", posicion=" + posicion +
                ", cantidad=" + cantidad +
                ", lote='" + lote + '\'' +
                ", almacen='" + almacen + '\'' +
                '}';
    }
}
