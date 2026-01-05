package com.incloud.hcp.jco.balanza.MotivosTraslado.dto;

public class MotivosTrasladoResponseDTO {
    private String motra;
    private String mandt;
    private String descripcion;

    public String getMotra() {
        return motra;
    }

    public void setMotra(String motra) {
        this.motra = motra;
    }

    public String getMandt() {
        return mandt;
    }

    public void setMandt(String mandt) {
        this.mandt = mandt;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "MotivosTrasladoResponseDTO{" +
                "motra='" + motra + '\'' +
                ", mandt='" + mandt + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
