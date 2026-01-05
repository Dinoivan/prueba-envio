package com.incloud.hcp.dto;

public class TicketPesajePDFdto {

    //id_ticket
    public String placa_transporte;
    //ruc_transportista

    //razon_social_transportar
    public String nombre_chofer;
    public String placa_carreta;

    public String denominacion;

    public String direccion;

    public String modelo_carreta;

    public String producto;

    public String proceso;

    //fecha_registro


    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getPlaca_transporte() {
        return placa_transporte;
    }

    public void setPlaca_transporte(String placa_transporte) {
        this.placa_transporte = placa_transporte;
    }

    public String getNombre_chofer() {
        return nombre_chofer;
    }

    public void setNombre_chofer(String nombre_chofer) {
        this.nombre_chofer = nombre_chofer;
    }

    public String getPlaca_carreta() {
        return placa_carreta;
    }

    public void setPlaca_carreta(String placa_carreta) {
        this.placa_carreta = placa_carreta;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getModelo_carreta() {
        return modelo_carreta;
    }

    public void setModelo_carreta(String modelo_carreta) {
        this.modelo_carreta = modelo_carreta;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
