package com.incloud.hcp.jco.prefactura.dto;

import java.io.Serializable;
import java.util.Date;

public class PrefacturaAnuladaDto implements Serializable {

    private String codigoDocumentoContable;
    private String ejercicio;
    private Date fechaAnulacion;
    private String stringFechaAnulacion;
    private String referencia;
    private String sociedad;
    private String codigoDocumentoContableAnulador;
    private String ejercicioAnulador;


    public String getCodigoDocumentoContable() {
        return codigoDocumentoContable;
    }

    public void setCodigoDocumentoContable(String codigoDocumentoContable) {
        this.codigoDocumentoContable = codigoDocumentoContable;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Date getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Date fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    public String getStringFechaAnulacion() {
        return stringFechaAnulacion;
    }

    public void setStringFechaAnulacion(String stringFechaAnulacion) {
        this.stringFechaAnulacion = stringFechaAnulacion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getCodigoDocumentoContableAnulador() {
        return codigoDocumentoContableAnulador;
    }

    public void setCodigoDocumentoContableAnulador(String codigoDocumentoContableAnulador) {
        this.codigoDocumentoContableAnulador = codigoDocumentoContableAnulador;
    }

    public String getEjercicioAnulador() {
        return ejercicioAnulador;
    }

    public void setEjercicioAnulador(String ejercicioAnulador) {
        this.ejercicioAnulador = ejercicioAnulador;
    }


    @Override
    public String toString() {
        return "PrefacturaAnuladaDto{" +
                "codigoDocumentoContable='" + codigoDocumentoContable + '\'' +
                ", ejercicio='" + ejercicio + '\'' +
                ", stringFechaAnulacion='" + stringFechaAnulacion + '\'' +
                ", referencia='" + referencia + '\'' +
                ", sociedad='" + sociedad + '\'' +
                ", codigoDocumentoContableAnulador='" + codigoDocumentoContableAnulador + '\'' +
                ", ejercicioAnulador='" + ejercicioAnulador + '\'' +
                '}';
    }
}
