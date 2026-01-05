package com.incloud.hcp.jco.peticionOferta.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PeticionOfertaRFCDto {

    private String numeroPeticionOferta;
    private String posicionPeticionOferta;
    private String codigoMaterial;
    private BigDecimal precioUnitario;
    private String codigoMoneda;

    public String getNumeroPeticionOferta() {
        return numeroPeticionOferta;
    }

    public void setNumeroPeticionOferta(String numeroPeticionOferta) {
        this.numeroPeticionOferta = numeroPeticionOferta;
    }

    public String getPosicionPeticionOferta() {
        return posicionPeticionOferta;
    }

    public void setPosicionPeticionOferta(String posicionPeticionOferta) {
        this.posicionPeticionOferta = posicionPeticionOferta;
    }

    public String getCodigoMaterial() {
        return codigoMaterial;
    }

    public void setCodigoMaterial(String codigoMaterial) {
        this.codigoMaterial = codigoMaterial;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    @Override
    public String toString() {
        return "PeticionOfertaRFCDto{" +
                "numeroPeticionOferta='" + numeroPeticionOferta + '\'' +
                ", posicionPeticionOferta='" + posicionPeticionOferta + '\'' +
                ", codigoMaterial='" + codigoMaterial + '\'' +
                ", precioUnitario=" + precioUnitario +
                ", codigoMoneda='" + codigoMoneda + '\'' +
                '}';
    }
}
