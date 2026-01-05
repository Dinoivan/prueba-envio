package com.incloud.hcp.dto;

public class FiltroTicketPesajeDTO {


    private Integer nroTicket;
    private String transportistaRuc;
    private String centro;
    private String placa;
    private String chofer;
    private String nSubticket;
    private String balanza;
    private String carreta;
    //
    Double pesoInicial;
    Double pesoFinal;

    public Double getPesoInicial() {
        return pesoInicial;
    }

    public void setPesoInicial(Double pesoInicial) {
        this.pesoInicial = pesoInicial;
    }

    public Double getPesoFinal() {
        return pesoFinal;
    }

    public void setPesoFinal(Double pesoFinal) {
        this.pesoFinal = pesoFinal;
    }



    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public Integer getNroTicket() {
        return nroTicket;
    }

    public void setNroTicket(Integer nroTicket) {
        this.nroTicket = nroTicket;
    }

    public String getTransportistaRuc() {
        return transportistaRuc;
    }

    public void setTransportistaRuc(String transportistaRuc) {
        this.transportistaRuc = transportistaRuc;
    }

    public String getPlaca() { return placa; }

    public void setPlaca(String placa) { this.placa = placa;}

    public String getChofer() { return chofer;}

    public void setChofer(String chofer) { this.chofer = chofer; }

    public String getnSubticket() { return nSubticket; }

    public void setnSubticket(String nSubticket) { this.nSubticket = nSubticket; }

    public String getBalanza() { return balanza; }

    public void setBalanza(String balanza) { this.balanza = balanza; }

    public String getCarreta() { return carreta; }

    public void setCarreta(String carreta) { this.carreta = carreta; }

    @Override
    public String toString() {
        return "FiltroTicketPesajeDTO{" +
                "nroTicket=" + nroTicket +
                ", transportistaRuc='" + transportistaRuc + '\'' +
                ", centro='" + centro + '\'' +
                ", placa='" + placa + '\'' +
                ", chofer='" + chofer + '\'' +
                ", nSubticket='" + nSubticket + '\'' +
                ", balanza='" + balanza + '\'' +
                ", carreta='" + carreta + '\'' +
                ", pesoInicial='" + pesoInicial + '\'' +
                ", pesoFinal='" + pesoFinal + '\'' +
                '}';
    }
}
