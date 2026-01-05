package com.incloud.hcp.jco.balanza.Carreta.dto;

public class CarretaResponseDTO {
    private String placa;
    private String modelo;
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "CarretaUpdateResponseDTO{" +
                "placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                '}';
    }
}
