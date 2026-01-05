package com.incloud.hcp.jco.balanza.MotivosTraslado.dto;

public class TipoMovimientoResponseDTO {
    private String grupo;
    private String valor;
    private String valorh;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorh() {
        return valorh;
    }

    public void setValorh(String valorh) {
        this.valorh = valorh;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return "TipoMovimientoResponseDTO{" +
                "valor='" + valor + '\'' +
                ", valorh='" + valorh + '\'' +
                ", grupo='" + grupo + '\'' +
                '}';
    }
}
