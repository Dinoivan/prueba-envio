package com.incloud.hcp.sap;

/**
 * Created by Administrador on 27/12/2017.
 */
public class SapSetting {
    private String user;
    private String password;
    private String wsdlSolped;
    private String wsdlMaterial;
    private String wsdlProveedor;
    private String wsdlOrdenCompra;
    private String wsdlHomologacion;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWsdlSolped() {
        return wsdlSolped;
    }

    public void setWsdlSolped(String wsdlSolped) {
        this.wsdlSolped = wsdlSolped;
    }

    public String getWsdlMaterial() {
        return wsdlMaterial;
    }

    public void setWsdlMaterial(String wsdlMaterial) {
        this.wsdlMaterial = wsdlMaterial;
    }

    public String getWsdlProveedor() {
        return wsdlProveedor;
    }

    public void setWsdlProveedor(String wsdlProveedor) {
        this.wsdlProveedor = wsdlProveedor;
    }

    public String getWsdlOrdenCompra() {
        return wsdlOrdenCompra;
    }

    public void setWsdlOrdenCompra(String wsdlOrdenCompra) {
        this.wsdlOrdenCompra = wsdlOrdenCompra;
    }

    public String getWsdlHomologacion() {
        return wsdlHomologacion;
    }

    public void setWsdlHomologacion(String wsdlHomologacion) {
        this.wsdlHomologacion = wsdlHomologacion;
    }

    @Override
    public String toString() {
        return "SapSetting{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", wsdlSolped='" + wsdlSolped + '\'' +
                ", wsdlMaterial='" + wsdlMaterial + '\'' +
                ", wsdlProveedor='" + wsdlProveedor + '\'' +
                ", wsdlOrdenCompra='" + wsdlOrdenCompra + '\'' +
                ", wsdlHomologacion='" + wsdlHomologacion + '\'' +
                '}';
    }
}
