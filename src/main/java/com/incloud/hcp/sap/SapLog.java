package com.incloud.hcp.sap;

import java.io.Serializable;

/**
 * Created by USER on 12/09/2017.
 */
public class SapLog implements Serializable{

    private static final long serialVersionUID = 1L;

    private String code;
    private String mesaj;
    private String clase;
    private String tipo;

    private String parameter;
    private String row;
    private String field;
    private String system;

    public SapLog() {
    }

    public SapLog(String code, String mesaj) {
        this.code = code;
        this.mesaj = mesaj;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return "SapLog{" +
                "code='" + code + '\'' +
                ", mesaj='" + mesaj + '\'' +
                ", clase='" + clase + '\'' +
                ", tipo='" + tipo + '\'' +
                ", parameter='" + parameter + '\'' +
                ", row='" + row + '\'' +
                ", field='" + field + '\'' +
                ", system='" + system + '\'' +
                '}';
    }
}
