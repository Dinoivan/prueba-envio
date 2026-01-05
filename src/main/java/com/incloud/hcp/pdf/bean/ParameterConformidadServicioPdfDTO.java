package com.incloud.hcp.pdf.bean;

import java.util.List;

public class ParameterConformidadServicioPdfDTO {
   private String rucCliente;
   private String ubicacionCliente;
   private String descripcionCliente;
   private String telefonoCliente;
   private String nroConformidadServicio;
   private String rucProveedor;
   private String fechaEmision;
   private String razonSocialProveedor;
   private String tipoMoneda;
   private String recepcionPersona;
   private String autorPersona;
   private String fechaAcept;
   private List<FieldConformidadServicioPdfDTO> fieldConformidadServicioPdfDTOList;

    public ParameterConformidadServicioPdfDTO() {
    }

    public String getRucCliente() {
        return rucCliente;
    }

    public void setRucCliente(String rucCliente) {
        this.rucCliente = rucCliente;
    }

    public String getUbicacionCliente() {
        return ubicacionCliente;
    }

    public void setUbicacionCliente(String ubicacionCliente) {
        this.ubicacionCliente = ubicacionCliente;
    }

    public String getDescripcionCliente() {
        return descripcionCliente;
    }

    public void setDescripcionCliente(String descripcionCliente) {
        this.descripcionCliente = descripcionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getNroConformidadServicio() {
        return nroConformidadServicio;
    }

    public void setNroConformidadServicio(String nroConformidadServicio) {
        this.nroConformidadServicio = nroConformidadServicio;
    }

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getRazonSocialProveedor() {
        return razonSocialProveedor;
    }

    public void setRazonSocialProveedor(String razonSocialProveedor) {
        this.razonSocialProveedor = razonSocialProveedor;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getRecepcionPersona() {
        return recepcionPersona;
    }

    public void setRecepcionPersona(String recepcionPersona) {
        this.recepcionPersona = recepcionPersona;
    }

    public String getAutorPersona() {
        return autorPersona;
    }

    public void setAutorPersona(String autorPersona) {
        this.autorPersona = autorPersona;
    }

    public String getFechaAcept() {
        return fechaAcept;
    }

    public void setFechaAcept(String fechaAcept) {
        this.fechaAcept = fechaAcept;
    }

    public List<FieldConformidadServicioPdfDTO> getFieldConformidadServicioPdfDTOList() {
        return fieldConformidadServicioPdfDTOList;
    }

    public void setFieldConformidadServicioPdfDTOList(List<FieldConformidadServicioPdfDTO> fieldConformidadServicioPdfDTOList) {
        this.fieldConformidadServicioPdfDTOList = fieldConformidadServicioPdfDTOList;
    }
}
