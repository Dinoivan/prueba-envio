package com.incloud.hcp.jco.balanza.DocumentoMaterial.dto;

public class DocMaterialGenerarDto {

    private String cantidad;
    private String tipoPesaje;
    private String numeroPedido;
    private String posicion;
    private String centro;
    private String material;
    private String almacen;
    private String lote;
    private String unidadMedida;


    public DocMaterialGenerarDto() {
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoPesaje() {
        return tipoPesaje;
    }

    public void setTipoPesaje(String tipoPesaje) {
        this.tipoPesaje = tipoPesaje;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return "DocMaterialGenerarDto{" +
                "cantidad='" + cantidad + '\'' +
                ", tipoPesaje='" + tipoPesaje + '\'' +
                ", numeroPedido='" + numeroPedido + '\'' +
                ", posicion='" + posicion + '\'' +
                ", centro='" + centro + '\'' +
                ", material='" + material + '\'' +
                ", almacen='" + almacen + '\'' +
                ", lote='" + lote + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                '}';
    }
}
