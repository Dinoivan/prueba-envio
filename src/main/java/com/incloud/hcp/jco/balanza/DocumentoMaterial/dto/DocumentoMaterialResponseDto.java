package com.incloud.hcp.jco.balanza.DocumentoMaterial.dto;

public class DocumentoMaterialResponseDto {
    private String documentoMaterial;
    private String ejercio;
    private String numDelivery;
    private String mensajeError;
    private String tipoPesaje;
    private String zeile;
    private String poNumber;
    private String poItem;
    private String poMenge;
    private String poMeins;
    private String pedidoVenta;
    private String subticket;

    public String getZeile() {
        return zeile;
    }

    public void setZeile(String zeile) {
        this.zeile = zeile;
    }

    public String getTipoPesaje() {
        return tipoPesaje;
    }

    public void setTipoPesaje(String tipoPesaje) {
        this.tipoPesaje = tipoPesaje;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getNumDelivery() {
        return numDelivery;
    }

    public void setNumDelivery(String numDelivery) {
        this.numDelivery = numDelivery;
    }

    public String getEjercio() {
        return ejercio;
    }

    public void setEjercio(String ejercio) {
        this.ejercio = ejercio;
    }

    public DocumentoMaterialResponseDto() {
    }

    public String getDocumentoMaterial() {
        return documentoMaterial;
    }

    public void setDocumentoMaterial(String documentoMaterial) {
        this.documentoMaterial = documentoMaterial;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getPoItem() {
        return poItem;
    }

    public void setPoItem(String poItem) {
        this.poItem = poItem;
    }

    public String getPedidoVenta() {
        return pedidoVenta;
    }

    public void setPedidoVenta(String pedidoVenta) {
        this.pedidoVenta = pedidoVenta;
    }

    public String getSubticket() {
        return subticket;
    }

    public void setSubticket(String subticket) {
        this.subticket = subticket;
    }

    public String getPoMenge() {
        return poMenge;
    }

    public void setPoMenge(String poMenge) {
        this.poMenge = poMenge;
    }

    public String getPoMeins() {
        return poMeins;
    }

    public void setPoMeins(String poMeins) {
        this.poMeins = poMeins;
    }

    @Override
    public String toString() {
        return "DocumentoMaterialResponseDto{" +
                "documentoMaterial='" + documentoMaterial + '\'' +
                ", ejercio='" + ejercio + '\'' +
                ", numDelivery='" + numDelivery + '\'' +
                ", mensajeError='" + mensajeError + '\'' +
                ", tipoPesaje='" + tipoPesaje + '\'' +
                ", zeile='" + zeile + '\'' +
                ", poNumber='" + poNumber + '\'' +
                ", poItem='" + poItem + '\'' +
                ", poMenge='" + poMenge + '\'' +
                ", poMeins='" + poMeins + '\'' +
                ", pedidoVenta='" + pedidoVenta + '\'' +
                ", subticket='" + subticket + '\'' +
                '}';
    }
}
