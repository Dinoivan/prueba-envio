package com.incloud.hcp.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by USER on 05/09/2017.
 */
public class ProveedorFiltro implements Serializable{

    private static final long serialVersionUID = 1L;

    private ArrayList<String> idsPais;
    private ArrayList<String> idsRegion;
    private ArrayList<String> idsProvincia;
    private String nroRuc;
    private String tipoProveedor;
    private String tipoPersona;
    private String indHomologado;
    private String marca;
    private String producto;
    private String descripcionAdicional;
    private ArrayList<String> idsLinea;
    private ArrayList<String> idsFamilia;
    private ArrayList<String> idsSubFamilia;
    private String razonSocial;
    private String estadoProveedor;
    private Integer nroRegistros ;
    private Integer paginaMostrar ;


    public ProveedorFiltro() {
    }

    public String getNroRuc() {
        return nroRuc;
    }

    public void setNroRuc(String nroRuc) {
        this.nroRuc = nroRuc;
    }

    public ArrayList<String> getIdsPais() {
        return idsPais;
    }

    public void setIdsPais(ArrayList<String> idsPais) {
        this.idsPais = idsPais;
    }

    public ArrayList<String> getIdsRegion() {
        return idsRegion;
    }

    public void setIdsRegion(ArrayList<String> idsRegion) {
        this.idsRegion = idsRegion;
    }

    public ArrayList<String> getIdsProvincia() {
        return idsProvincia;
    }

    public void setIdsProvincia(ArrayList<String> idsProvincia) {
        this.idsProvincia = idsProvincia;
    }

    public String getTipoProveedor() {
        return tipoProveedor;
    }

    public void setTipoProveedor(String tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getIndHomologado() {
        return indHomologado;
    }

    public void setIndHomologado(String indHomologado) {
        this.indHomologado = indHomologado;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescripcionAdicional() {
        return descripcionAdicional;
    }

    public void setDescripcionAdicional(String descripcionAdicional) {
        this.descripcionAdicional = descripcionAdicional;
    }

    public ArrayList<String> getIdsLinea() {
        return idsLinea;
    }

    public void setIdsLinea(ArrayList<String> idsLinea) {
        this.idsLinea = idsLinea;
    }

    public ArrayList<String> getIdsFamilia() {
        return idsFamilia;
    }

    public void setIdsFamilia(ArrayList<String> idsFamilia) {
        this.idsFamilia = idsFamilia;
    }

    public ArrayList<String> getIdsSubFamilia() {
        return idsSubFamilia;
    }

    public void setIdsSubFamilia(ArrayList<String> idsSubFamilia) {
        this.idsSubFamilia = idsSubFamilia;
    }

    public String getEstadoProveedor() {
        return estadoProveedor;
    }

    public void setEstadoProveedor(String estadoProveedor) {
        this.estadoProveedor = estadoProveedor;
    }

    public Integer getNroRegistros() {
        return nroRegistros;
    }

    public void setNroRegistros(Integer nroRegistros) {
        this.nroRegistros = nroRegistros;
    }

    public Integer getPaginaMostrar() {
        return paginaMostrar;
    }

    public void setPaginaMostrar(Integer paginaMostrar) {
        this.paginaMostrar = paginaMostrar;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    @Override
    public String toString() {
        return "ProveedorFiltro{" +
                "idsPais=" + idsPais +
                ", idsRegion=" + idsRegion +
                ", idsProvincia=" + idsProvincia +
                ", nroRuc='" + nroRuc + '\'' +
                ", tipoProveedor='" + tipoProveedor + '\'' +
                ", tipoPersona='" + tipoPersona + '\'' +
                ", indHomologado='" + indHomologado + '\'' +
                ", marca='" + marca + '\'' +
                ", producto='" + producto + '\'' +
                ", descripcionAdicional='" + descripcionAdicional + '\'' +
                ", idsLinea=" + idsLinea +
                ", idsFamilia=" + idsFamilia +
                ", idsSubFamilia=" + idsSubFamilia +
                ", razonSocial='" + razonSocial + '\'' +
                ", estadoProveedor='" + estadoProveedor + '\'' +
                ", nroRegistros=" + nroRegistros +
                ", paginaMostrar=" + paginaMostrar +
                '}';
    }
}
