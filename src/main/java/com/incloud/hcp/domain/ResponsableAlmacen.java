package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name = "GA_RESPONSABLE_ALMACEN")
public class ResponsableAlmacen extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "GA_RESPONSABLE_ALMACEN_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "GA_RESPONSABLE_ALMACEN_ID_SEQ", sequenceName = "GA_RESPONSABLE_ALMACEN_ID_SEQ", allocationSize = 1)
    @Column(name = "ID_RESPONSABLE_ALMACEN", unique = true, nullable = false)
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @Column(name = "CENTRO", length = 4)
    private String centro;

    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo;

    @Column(name="CODIGO_SAP", length = 20)
    private String codigoSap;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getCodigoSap() {
        return codigoSap;
    }

    public void setCodigoSap(String codigoSap) {
        this.codigoSap = codigoSap;
    }

    @Override
    public String toString() {
        return "ResponsableAlmacen{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", centro='" + centro + '\'' +
                ", activo=" + activo +
                ", codigoSap='" + codigoSap + '\'' +
                '}';
    }
}