package com.incloud.hcp.bean;

import com.incloud.hcp.domain.Ubigeo;

/**
 * Created by Administrador on 29/08/2017.
 */
public class Contacto {
    private int idContacto;
    private Ubigeo region;
    private String nombre;
    private String email;
    private String telefono;
    private Area area;

    public Contacto() {
        this.region = new Ubigeo();
        this.area = new Area();
    }

    public int getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(int idContacto) {
        this.idContacto = idContacto;
    }

    public Ubigeo getRegion() {
        return region;
    }

    public void setRegion(Ubigeo region) {
        this.region = region;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "idContacto=" + idContacto +
                ", region=" + region +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", area=" + area +
                '}';
    }
}
