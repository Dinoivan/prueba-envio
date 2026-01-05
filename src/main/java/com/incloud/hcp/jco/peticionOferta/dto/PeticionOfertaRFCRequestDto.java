package com.incloud.hcp.jco.peticionOferta.dto;

import java.io.Serializable;
import java.util.List;

public class PeticionOfertaRFCRequestDto implements Serializable {

    private String numeroLicitacion;
    private List<PeticionOfertaRFCDto> peticionOfertaRFCDtoList;

    public String getNumeroLicitacion() {
        return numeroLicitacion;
    }

    public void setNumeroLicitacion(String numeroLicitacion) {
        this.numeroLicitacion = numeroLicitacion;
    }

    public List<PeticionOfertaRFCDto> getPeticionOfertaRFCDtoList() {
        return peticionOfertaRFCDtoList;
    }

    public void setPeticionOfertaRFCDtoList(List<PeticionOfertaRFCDto> peticionOfertaRFCDtoList) {
        this.peticionOfertaRFCDtoList = peticionOfertaRFCDtoList;
    }

    @Override
    public String toString() {
        return "PeticionOfertaRFCRequestDto{" +
                "numeroLicitacion='" + numeroLicitacion + '\'' +
                ", peticionOfertaRFCDtoList=" + peticionOfertaRFCDtoList +
                '}';
    }
}
