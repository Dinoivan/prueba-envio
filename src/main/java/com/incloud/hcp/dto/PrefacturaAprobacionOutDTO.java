package com.incloud.hcp.dto;

import java.util.List;

public class PrefacturaAprobacionOutDTO {
    private List<PrefacturaAprobacionDTO> lista;
    private Integer totalElementos;
    private Integer totalPaginas;

    public List<PrefacturaAprobacionDTO> getLista() {
        return lista;
    }

    public void setLista(List<PrefacturaAprobacionDTO> lista) {
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
        return "PrefacturaAprobacionOutDTO{" +
                "lista=" + lista +
                ", totalElementos=" + totalElementos +
                ", totalPaginas=" + totalPaginas +
                '}';
    }
}
