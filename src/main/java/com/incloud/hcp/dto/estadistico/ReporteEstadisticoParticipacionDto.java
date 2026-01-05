package com.incloud.hcp.dto.estadistico;

public class ReporteEstadisticoParticipacionDto {

    private Integer anno;
    private Integer mes;
    private String descripcionMes;

    private Integer totalInvitacion;
    private Integer totalParticipacion;


    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getDescripcionMes() {
        return descripcionMes;
    }

    public void setDescripcionMes(String descripcionMes) {
        this.descripcionMes = descripcionMes;
    }

    public Integer getTotalInvitacion() {
        return totalInvitacion;
    }

    public void setTotalInvitacion(Integer totalInvitacion) {
        this.totalInvitacion = totalInvitacion;
    }

    public Integer getTotalParticipacion() {
        return totalParticipacion;
    }

    public void setTotalParticipacion(Integer totalParticipacion) {
        this.totalParticipacion = totalParticipacion;
    }

    @Override
    public String toString() {
        return "ReporteEstadisticoParticipacionDto{" +
                "anno=" + anno +
                ", mes=" + mes +
                ", descripcionMes='" + descripcionMes + '\'' +
                ", totalInvitacion=" + totalInvitacion +
                ", totalParticipacion=" + totalParticipacion +
                '}';
    }
}
