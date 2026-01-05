package com.incloud.hcp.pdf.bean;

import java.util.Date;
import java.util.List;

public class ParameterEntradaMercaderiaPdfDTO{
    private String nroRuc;
    private String nroGuia;
    private String rucCliente;
    private String fechaEmision;
    private String razonSocialCliente;
    private String documentoMaterial;
    private String descripcionProveedor;
    private String ubicacionProveedor;
    private String telefonoProveedor;
    private List<FieldEntradaMercaderiaPdfDTO> fieldEntradaMercaderiaPdfDTOList;

    public String getNroRuc() {
        return nroRuc;
    }

    public void setNroRuc(String nroRuc) {
        this.nroRuc = nroRuc;
    }

    public String getNroGuia() {
        return nroGuia;
    }

    public void setNroGuia(String nroGuia) {
        this.nroGuia = nroGuia;
    }

    public String getRucCliente() {
        return rucCliente;
    }

    public void setRucCliente(String rucCliente) {
        this.rucCliente = rucCliente;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getRazonSocialCliente() {
        return razonSocialCliente;
    }

    public void setRazonSocialCliente(String razonSocialCliente) {
        this.razonSocialCliente = razonSocialCliente;
    }

    public String getDocumentoMaterial() {
        return documentoMaterial;
    }

    public void setDocumentoMaterial(String documentoMaterial) {
        this.documentoMaterial = documentoMaterial;
    }

    public String getDescripcionProveedor() {
        return descripcionProveedor;
    }

    public void setDescripcionProveedor(String descripcionProveedor) {
        this.descripcionProveedor = descripcionProveedor;
    }

    public String getUbicacionProveedor() {
        return ubicacionProveedor;
    }

    public void setUbicacionProveedor(String ubicacionProveedor) {
        this.ubicacionProveedor = ubicacionProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public List<FieldEntradaMercaderiaPdfDTO> getFieldEntradaMercaderiaPdfDTOList() {
        return fieldEntradaMercaderiaPdfDTOList;
    }

    public void setFieldEntradaMercaderiaPdfDTOList(List<FieldEntradaMercaderiaPdfDTO> fieldEntradaMercaderiaPdfDTOList) {
        this.fieldEntradaMercaderiaPdfDTOList = fieldEntradaMercaderiaPdfDTOList;
    }
}
