package com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.dto;

public class MaterialIDto {
    private String matnr;
    private String werks;
    private String lgort;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getLgort() {
        return lgort;
    }

    public void setLgort(String lgort) {
        this.lgort = lgort;
    }

    @Override
    public String toString() {
        return "MaterialIResponse{" +
                "matnr='" + matnr + '\'' +
                ", werks='" + werks + '\'' +
                ", lgort='" + lgort + '\'' +
                '}';
    }
}
