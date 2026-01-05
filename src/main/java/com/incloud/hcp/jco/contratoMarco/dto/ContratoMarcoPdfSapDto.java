package com.incloud.hcp.jco.contratoMarco.dto;

import java.io.Serializable;


public class ContratoMarcoPdfSapDto implements Serializable {
    private String clientePersonaContacto;
    private String clienteAutorizador;

    private String proveedorDireccion;
    private String proveedorContactoNombre;
    private String proveedorContactoTelefono;

    public String getClientePersonaContacto() {
        return clientePersonaContacto;
    }

    public void setClientePersonaContacto(String clientePersonaContacto) {
        this.clientePersonaContacto = clientePersonaContacto;
    }

    public String getClienteAutorizador() {
        return clienteAutorizador;
    }

    public void setClienteAutorizador(String clienteAutorizador) {
        this.clienteAutorizador = clienteAutorizador;
    }

    public String getProveedorDireccion() {
        return proveedorDireccion;
    }

    public void setProveedorDireccion(String proveedorDireccion) {
        this.proveedorDireccion = proveedorDireccion;
    }

    public String getProveedorContactoNombre() {
        return proveedorContactoNombre;
    }

    public void setProveedorContactoNombre(String proveedorContactoNombre) {
        this.proveedorContactoNombre = proveedorContactoNombre;
    }

    public String getProveedorContactoTelefono() {
        return proveedorContactoTelefono;
    }

    public void setProveedorContactoTelefono(String proveedorContactoTelefono) {
        this.proveedorContactoTelefono = proveedorContactoTelefono;
    }

    @Override
    public String toString() {
        return "ContratoMarcoPdfSapDto{" +
                "clientePersonaContacto='" + clientePersonaContacto + '\'' +
                ", clienteAutorizador='" + clienteAutorizador + '\'' +
                ", proveedorDireccion='" + proveedorDireccion + '\'' +
                ", proveedorContactoNombre='" + proveedorContactoNombre + '\'' +
                ", proveedorContactoTelefono='" + proveedorContactoTelefono + '\'' +
                '}';
    }
}