package com.incloud.hcp.dto.homologacion;

import java.util.List;

/**
 * Created by Administrador on 16/10/2017.
 */
public class LineaComercialHomologacionDto {
    private Integer idProveedor;
    private Integer idLinea;
    private String linea;
    private List<HomologacionDto> preguntas;

    public Integer getIdLinea() {
        return idLinea;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public void setIdLinea(Integer idLinea) {
        this.idLinea = idLinea;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public List<HomologacionDto> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<HomologacionDto> preguntas) {
        this.preguntas = preguntas;
    }
}
