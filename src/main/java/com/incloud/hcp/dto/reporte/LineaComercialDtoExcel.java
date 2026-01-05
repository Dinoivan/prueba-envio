package com.incloud.hcp.dto.reporte;

/**
 * Created by Administrador on 30/11/2017.
 */
public class LineaComercialDtoExcel {
    private String codigoSap;
    private String ruc;
    private String razonSocial;
    private String lineaComercial;
    private String familia;
    private String subFamilia;
    private String otraFamilia;

    public String getCodigoSap() {
        return codigoSap;
    }

    public void setCodigoSap(String codigoSap) {
        this.codigoSap = codigoSap;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getLineaComercial() {
        return lineaComercial;
    }

    public void setLineaComercial(String lineaComercial) {
        this.lineaComercial = lineaComercial;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getSubFamilia() {
        return subFamilia;
    }

    public void setSubFamilia(String subFamilia) {
        this.subFamilia = subFamilia;
    }

    public String getOtraFamilia() {
        return otraFamilia;
    }

    public void setOtraFamilia(String otraFamilia) {
        this.otraFamilia = otraFamilia;
    }
}
