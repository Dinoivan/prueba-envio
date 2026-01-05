package com.incloud.hcp.dto;

/**
 * Created by Administrador on 18/09/2017.
 */
public class EvaluacionDesempenioDto {
    private String anio;
    private int evaluacion;

    public EvaluacionDesempenioDto() {
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public int getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(int evaluacion) {
        this.evaluacion = evaluacion;
    }

    @Override
    public String toString() {
        return "EvaluacionDesempenioDto{" +
                "anio='" + anio + '\'' +
                ", evaluacion=" + evaluacion +
                '}';
    }
}
