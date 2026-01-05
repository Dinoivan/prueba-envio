package com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.dto;

public class MaterialOutDto {
    private String matnr;
    private String meins;
    private String maktx;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMeins() {
        return meins;
    }

    public void setMeins(String meins) {
        this.meins = meins;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    @Override
    public String toString() {
        return "MaterialOutDto{" +
                "matnr='" + matnr + '\'' +
                ", meins=" + meins +
                ", maktx='" + maktx + '\'' +
                '}';
    }
}
