package com.incloud.hcp.dto;

import com.incloud.hcp.bean.ProveedorCustom;

import java.util.List;

public class ListProveedorHCP {

    List<ProveedorCustom> listaProveedorsinIDHCP;

    public List<ProveedorCustom> getListaProveedorsinIDHCP() {
        return listaProveedorsinIDHCP;
    }

    public void setListaProveedorsinIDHCP(List<ProveedorCustom> listaProveedorsinIDHCP) {
        this.listaProveedorsinIDHCP = listaProveedorsinIDHCP;
    }
}
