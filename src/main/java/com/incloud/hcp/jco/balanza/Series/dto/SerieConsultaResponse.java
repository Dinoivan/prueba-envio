package com.incloud.hcp.jco.balanza.Series.dto;

public class SerieConsultaResponse {
    private String bukrs;
    private String werks;
    private String zCorrelativo;
    private String zSerie;
    private String zDescr;
    private String ultimoZGuiar;

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getzCorrelativo() {
        return zCorrelativo;
    }

    public void setzCorrelativo(String zCorrelativo) {
        this.zCorrelativo = zCorrelativo;
    }

    public String getzSerie() {
        return zSerie;
    }

    public void setzSerie(String zSerie) {
        this.zSerie = zSerie;
    }

    public String getzDescr() {
        return zDescr;
    }

    public void setzDescr(String zDescr) {
        this.zDescr = zDescr;
    }

    public String getUltimoZGuiar() {
        return ultimoZGuiar;
    }

    public void setUltimoZGuiar(String ultimoZGuiar) {
        this.ultimoZGuiar = ultimoZGuiar;
    }


    @Override
    public String toString() {
        return "SerieConsultaResponse{" +
                "bukrs='" + bukrs + '\'' +
                ", werks='" + werks + '\'' +
                ", zCorrelativo='" + zCorrelativo + '\'' +
                ", zSerie='" + zSerie + '\'' +
                ", zDescr='" + zDescr + '\'' +
                ", ultimoZGuiar=" + ultimoZGuiar +
                '}';
    }
}
