package com.incloud.hcp.jco.EstadoDocumentoAceptacion.dto;

public class EstadoDocumentoAceptacionResponse {
    private String ebeln;
    private Integer ebelp;
    private String lfbja;
    private String belnr;
    private String mjahr;
    private String estatu;
    private String indBorrado;
    private String facturaFinal;

    public String getEbeln() {
        return ebeln;
    }

    public void setEbeln(String ebeln) {
        this.ebeln = ebeln;
    }

    public Integer getEbelp() {
        return ebelp;
    }

    public void setEbelp(Integer ebelp) {
        this.ebelp = ebelp;
    }

    public String getLfbja() {
        return lfbja;
    }

    public void setLfbja(String lfbja) {
        this.lfbja = lfbja;
    }

    public String getBelnr() {
        return belnr;
    }

    public void setBelnr(String belnr) {
        this.belnr = belnr;
    }

    public String getMjahr() {
        return mjahr;
    }

    public void setMjahr(String mjahr) {
        this.mjahr = mjahr;
    }

    public String getEstatu() {
        return estatu;
    }

    public void setEstatu(String estatu) {
        this.estatu = estatu;
    }

    public String getIndBorrado() {
        return indBorrado;
    }

    public void setIndBorrado(String indBorrado) {
        this.indBorrado = indBorrado;
    }

    public String getFacturaFinal() {
        return facturaFinal;
    }

    public void setFacturaFinal(String facturaFinal) {
        this.facturaFinal = facturaFinal;
    }

    @Override
    public String toString() {
        return "EstadoDocumentoAceptacionResponse{" +
                "ebeln='" + ebeln + '\'' +
                ", ebelp=" + ebelp +
                ", lfbja='" + lfbja + '\'' +
                ", belnr='" + belnr + '\'' +
                ", mjahr='" + mjahr + '\'' +
                ", estatu='" + estatu + '\'' +
                '}';
    }
}
