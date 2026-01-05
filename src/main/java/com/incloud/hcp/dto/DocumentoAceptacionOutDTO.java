package com.incloud.hcp.dto;

import com.incloud.hcp.domain.DocumentoAceptacion;

import java.util.Date;
import java.util.List;

public class DocumentoAceptacionOutDTO {
    private List<DocumentoAceptacionDto> lista;
    private Integer totalElementos;
    private Integer totalPaginas;

    public List<DocumentoAceptacionDto> getLista() {
        return lista;
    }

    public void setLista(List<DocumentoAceptacionDto> lista) {
        this.lista = lista;
    }

    public Integer getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(Integer totalElementos) {
        this.totalElementos = totalElementos;
    }

    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(Integer totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    @Override
    public String toString() {
        return "DocumentoAceptacionOutDTO{" +
                "lista=" + lista +
                ", totalElementos=" + totalElementos +
                ", totalPaginas=" + totalPaginas +
                '}';
    }
}
