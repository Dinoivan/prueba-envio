package com.incloud.hcp.jco.balanza.DocumentoMaterial.dto;

public class DocMaterialConsultaDto {

    private String docMaterial;
    private String ejercicio;
    private Integer tipoPesaje;

    public DocMaterialConsultaDto() {
        ;
    }

    public String getDocMaterial() {
        return docMaterial;
    }

    public void setDocMaterial(String docMaterial) {
        this.docMaterial = docMaterial;
    }

    public Integer getTipoPesaje() {
        return tipoPesaje;
    }

    public void setTipoPesaje(Integer tipoPesaje) {
        this.tipoPesaje = tipoPesaje;
    }

    public String getEjercicio() {
        return ejercicio;
    }
    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }
    @Override
    public String toString() {
        return "DocMaterialConsultaDto{" +
                "docMaterial='" + docMaterial + '\'' +
                ", ejercicio='" + ejercicio + '\'' +
                ", tipoPesaje='" + tipoPesaje + '\'' +
                '}';
    }
}
