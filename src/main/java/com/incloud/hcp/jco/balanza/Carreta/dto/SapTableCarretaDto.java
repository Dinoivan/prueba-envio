package com.incloud.hcp.jco.balanza.Carreta.dto;

public class SapTableCarretaDto {

    private String placa;
    private String modelo;
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public void setPlaca(String placa) { this.placa = placa;}
    public void setModelo(String modelo) { this.modelo = modelo;}
    @Override
    public String toString() {
        return "SapTableItemDto{" +
                "placa='" + placa + '\'' +
                ", modelo=" + modelo +
                '\'' +
                '}';
    }
}
