package com.incloud.hcp.jco.ubigeo.dto;

import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class UbigeoRFCResponseDto implements Serializable {

    private SapLog sapLog;

    private int contadorActualizadoPais;
    private int contadorActualizadoRegion;
    private int contadorActualizadoProvincia;
    private int contadorActualizadoDistrito;

    private int contadorTotalPais;
    private int contadorTotalRegion;
    private int contadorTotalProvincia;
    private int contadorTotalDistrito;

    private List<UbigeoRFCDTO> listaPaises;
    private List<UbigeoRFCDTO> listaRegion;
    private List<UbigeoRFCDTO> listaPoblacion;
    private List<UbigeoRFCDTO> listaDistrito;


    public SapLog getSapLog() {
        return sapLog;
    }

    public void setSapLog(SapLog sapLog) {
        this.sapLog = sapLog;
    }

    public List<UbigeoRFCDTO> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<UbigeoRFCDTO> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public List<UbigeoRFCDTO> getListaRegion() {
        return listaRegion;
    }

    public void setListaRegion(List<UbigeoRFCDTO> listaRegion) {
        this.listaRegion = listaRegion;
    }

    public List<UbigeoRFCDTO> getListaPoblacion() {
        return listaPoblacion;
    }

    public void setListaPoblacion(List<UbigeoRFCDTO> listaPoblacion) {
        this.listaPoblacion = listaPoblacion;
    }

    public List<UbigeoRFCDTO> getListaDistrito() {
        return listaDistrito;
    }

    public void setListaDistrito(List<UbigeoRFCDTO> listaDistrito) {
        this.listaDistrito = listaDistrito;
    }

    public int getContadorActualizadoPais() {
        return contadorActualizadoPais;
    }

    public void setContadorActualizadoPais(int contadorActualizadoPais) {
        this.contadorActualizadoPais = contadorActualizadoPais;
    }

    public int getContadorActualizadoRegion() {
        return contadorActualizadoRegion;
    }

    public void setContadorActualizadoRegion(int contadorActualizadoRegion) {
        this.contadorActualizadoRegion = contadorActualizadoRegion;
    }

    public int getContadorActualizadoProvincia() {
        return contadorActualizadoProvincia;
    }

    public void setContadorActualizadoProvincia(int contadorActualizadoProvincia) {
        this.contadorActualizadoProvincia = contadorActualizadoProvincia;
    }

    public int getContadorActualizadoDistrito() {
        return contadorActualizadoDistrito;
    }

    public void setContadorActualizadoDistrito(int contadorActualizadoDistrito) {
        this.contadorActualizadoDistrito = contadorActualizadoDistrito;
    }

    public int getContadorTotalPais() {
        return contadorTotalPais;
    }

    public void setContadorTotalPais(int contadorTotalPais) {
        this.contadorTotalPais = contadorTotalPais;
    }

    public int getContadorTotalRegion() {
        return contadorTotalRegion;
    }

    public void setContadorTotalRegion(int contadorTotalRegion) {
        this.contadorTotalRegion = contadorTotalRegion;
    }

    public int getContadorTotalProvincia() {
        return contadorTotalProvincia;
    }

    public void setContadorTotalProvincia(int contadorTotalProvincia) {
        this.contadorTotalProvincia = contadorTotalProvincia;
    }

    public int getContadorTotalDistrito() {
        return contadorTotalDistrito;
    }

    public void setContadorTotalDistrito(int contadorTotalDistrito) {
        this.contadorTotalDistrito = contadorTotalDistrito;
    }

    @Override
    public String toString() {
        return "UbigeoRFCResponseDto{" +
                "sapLog=" + sapLog +
                ", contadorActualizadoPais=" + contadorActualizadoPais +
                ", contadorActualizadoRegion=" + contadorActualizadoRegion +
                ", contadorActualizadoProvincia=" + contadorActualizadoProvincia +
                ", contadorActualizadoDistrito=" + contadorActualizadoDistrito +
                ", contadorTotalPais=" + contadorTotalPais +
                ", contadorTotalRegion=" + contadorTotalRegion +
                ", contadorTotalProvincia=" + contadorTotalProvincia +
                ", contadorTotalDistrito=" + contadorTotalDistrito +
                ", listaPaises=" + listaPaises +
                ", listaRegion=" + listaRegion +
                ", listaPoblacion=" + listaPoblacion +
                ", listaDistrito=" + listaDistrito +
                '}';
    }
}
