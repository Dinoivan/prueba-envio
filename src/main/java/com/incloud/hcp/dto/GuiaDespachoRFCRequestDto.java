package com.incloud.hcp.dto;

import com.incloud.hcp.jco.prefactura.dto.PrefacturaRFCPosicionDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class GuiaDespachoRFCRequestDto implements Serializable {

    private String documentoMaterial;
    private String anio;

    public String getDocumentoMaterial() {
        return documentoMaterial;
    }

    public void setDocumentoMaterial(String documentoMaterial) {
        this.documentoMaterial = documentoMaterial;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    @Override
    public String toString() {
        return "GuiaDespachoRFCRequestDto{" +
                "documentoMaterial='" + documentoMaterial + '\'' +
                ", anio='" + anio + '\'' +
                '}';
    }

}
