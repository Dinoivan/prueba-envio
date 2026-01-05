package com.incloud.hcp.jco.balanza.MotivosTraslado.dto;

public class MotivosOtroResponseDTO {
    private String motro;
    private String correlativo;
    private String descripcion;

    public String getMotro() {
        return motro;
    }

    public void setMotro(String motro) {
        this.motro = motro;
    }

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "MotivosOtroResponseDTO{" +
                "motro='" + motro + '\'' +
                ", correlativo='" + correlativo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
