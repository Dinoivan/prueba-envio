package com.incloud.hcp.dto;

import com.incloud.hcp.domain.Prefactura;

import java.math.BigDecimal;

public class PrefacturaActualizarDto {
    private Integer idPrefactura;
    private String referencia;
    private String referenciaSap;
    private String sociedad;
    private String rucProveedor;
    private BigDecimal importe; // bigdecimal (2 decimales)
    private String mensaje;

    private Prefactura prefactura;


    public Integer getIdPrefactura() {
        return idPrefactura;
    }

    public void setIdPrefactura(Integer idPrefactura) {
        this.idPrefactura = idPrefactura;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getReferenciaSap() {
        return referenciaSap;
    }

    public void setReferenciaSap(String referenciaSap) {
        this.referenciaSap = referenciaSap;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Prefactura getPrefactura() {
        return prefactura;
    }

    public void setPrefactura(Prefactura prefactura) {
        this.prefactura = prefactura;
    }

    @Override
    public String toString() {
        return "PrefacturaActualizarDto{" +
                "idPrefactura=" + idPrefactura +
                ", referencia='" + referencia + '\'' +
                ", referenciaSap='" + referenciaSap + '\'' +
                ", sociedad='" + sociedad + '\'' +
                ", rucProveedor='" + rucProveedor + '\'' +
                ", importe=" + importe +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
