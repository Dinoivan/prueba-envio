package com.incloud.hcp.jco.comprobanteRetencion.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SapTableCRTotalDto implements Serializable {
    private String numeroDocumentoErp;
    private String sociedad;
    private Integer ejercicio;
    private BigDecimal importeTotalRetencionMonedaLocal;


    public String getNumeroDocumentoErp() {
        return numeroDocumentoErp;
    }

    public void setNumeroDocumentoErp(String numeroDocumentoErp) {
        this.numeroDocumentoErp = numeroDocumentoErp;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public BigDecimal getImporteTotalRetencionMonedaLocal() {
        return importeTotalRetencionMonedaLocal;
    }

    public void setImporteTotalRetencionMonedaLocal(BigDecimal importeTotalRetencionMonedaLocal) {
        this.importeTotalRetencionMonedaLocal = importeTotalRetencionMonedaLocal;
    }
}
