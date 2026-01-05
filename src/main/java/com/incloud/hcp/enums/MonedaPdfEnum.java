package com.incloud.hcp.enums;

public enum MonedaPdfEnum {

    USD("USD","Dólar USA","Dólares"),
    PEN("PEN","Sol Peruano","Soles");

    String codigo;
    String descripcion;
    String nombrePlural;

    MonedaPdfEnum(String codigo, String descripcion, String nombrePlural){
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.nombrePlural = nombrePlural;
    }

    public String getCodigo(){
        return this.codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombrePlural() {
        return nombrePlural;
    }
}
