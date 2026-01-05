package com.incloud.hcp.jco.balanza.MotivosTraslado.dto;

public class UnidadMedidaResponseDTO {
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

    @Override
    public String toString() {
        return "TipoMovimientoResponseDTO{" +
                "valor='" + valor + '\'' +
                ", valorh='" + valorh + '\'' +
                '}';
    }
}
