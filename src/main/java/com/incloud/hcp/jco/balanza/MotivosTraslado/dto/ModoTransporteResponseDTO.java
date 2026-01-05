package com.incloud.hcp.jco.balanza.MotivosTraslado.dto;

public class ModoTransporteResponseDTO {
    private String correlativo;
    private String valor;
    private String descripcion;

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ModoTransporteResponseDTO{" +
                "correlativo='" + correlativo + '\'' +
                ", valor='" + valor + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
