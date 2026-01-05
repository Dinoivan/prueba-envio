package com.incloud.hcp.jco.balanza.MaterialLote.dto;

public class MaterialLoteResponse {
    private String material;
    private String lote;
    private String descripcion;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "MaterialLoteResponse{" +
                "material='" + material + '\'' +
                ", lote='" + lote + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
