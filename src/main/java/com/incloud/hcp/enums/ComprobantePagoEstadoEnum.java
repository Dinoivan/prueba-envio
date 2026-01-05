package com.incloud.hcp.enums;

public enum ComprobantePagoEstadoEnum {

    PUBLICADO("PUB","Publicado"),
    PREREGISTRADO("PRG","Preregistrado"),
    PAGADO("PAG","Pagado"),
    ANULADO("ANU","Anulado");

    private final String codigo;
    private final String descripcion;

    ComprobantePagoEstadoEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
