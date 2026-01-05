package com.incloud.hcp.pdf.bean;

public class FieldEntradaMercaderiaPdfDTO {
    private String nroItem;
    private String nroOC;
    private String nroItemOC;
    private String codigoProducto;
    private String descripcionProducto;
    private String cantAceptableCliente;
    private String undMedida;
    private String cantPedientePedido;
    private String undMedidaPedido;

    public FieldEntradaMercaderiaPdfDTO() {
    }

    public FieldEntradaMercaderiaPdfDTO(String nroItem, String nroOC, String nroItemOC, String codigoProducto, String descripcionProducto, String cantAceptableCliente, String undMedida, String cantPedientePedido, String undMedidaPedido) {
        this.nroItem = nroItem;
        this.nroOC = nroOC;
        this.nroItemOC = nroItemOC;
        this.codigoProducto = codigoProducto;
        this.descripcionProducto = descripcionProducto;
        this.cantAceptableCliente = cantAceptableCliente;
        this.undMedida = undMedida;
        this.cantPedientePedido = cantPedientePedido;
        this.undMedidaPedido = undMedidaPedido;
    }

    public String getNroItem() {
        return nroItem;
    }

    public void setNroItem(String nroItem) {
        this.nroItem = nroItem;
    }

    public String getNroOC() {
        return nroOC;
    }

    public void setNroOC(String nroOC) {
        this.nroOC = nroOC;
    }

    public String getNroItemOC() {
        return nroItemOC;
    }

    public void setNroItemOC(String nroItemOC) {
        this.nroItemOC = nroItemOC;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getCantAceptableCliente() {
        return cantAceptableCliente;
    }

    public void setCantAceptableCliente(String cantAceptableCliente) {
        this.cantAceptableCliente = cantAceptableCliente;
    }

    public String getUndMedida() {
        return undMedida;
    }

    public void setUndMedida(String undMedida) {
        this.undMedida = undMedida;
    }

    public String getCantPedientePedido() {
        return cantPedientePedido;
    }

    public void setCantPedientePedido(String cantPedientePedido) {
        this.cantPedientePedido = cantPedientePedido;
    }

    public String getUndMedidaPedido() {
        return undMedidaPedido;
    }

    public void setUndMedidaPedido(String undMedidaPedido) {
        this.undMedidaPedido = undMedidaPedido;
    }
}
