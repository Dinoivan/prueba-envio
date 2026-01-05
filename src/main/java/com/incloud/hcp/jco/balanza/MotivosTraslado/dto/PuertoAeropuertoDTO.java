package com.incloud.hcp.jco.balanza.MotivosTraslado.dto;

public class PuertoAeropuertoDTO {
    private String codigo;
    private String text;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "PuertoAeropuertoDTO{" +
                "codigo='" + codigo + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
