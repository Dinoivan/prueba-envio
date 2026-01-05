package com.incloud.hcp.jco.balanza.MotivosTraslado.dto;

public class TipoLocacionDTO {
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
        return "TipoLocacionDTO{" +
                "codigo='" + codigo + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
