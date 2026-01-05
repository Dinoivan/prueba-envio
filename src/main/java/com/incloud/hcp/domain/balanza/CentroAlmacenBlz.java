package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;

@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_CENTRO_ALMACEN")
public class CentroAlmacenBlz {
    @Id
    @GeneratedValue(generator = "CENTRO_ALMACEN_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CENTRO_ALMACEN_ID_SEQ", sequenceName = "CENTRO_ALMACEN_ID_SEQ", allocationSize = 1)
    @Column(name="ID_CENTRO_ALMACEN", unique=true, nullable=false)
    private Integer id;
    @Column(name="CENTRO")
    private String centro;
    @Column(name="POBLACION")
    private String poblacion;
    @Column(name="DISTRITO")
    private String distrito;
    @Column(name="CODIGOALMACEN")
    private String codigoAlmacen;
    @Column(name="DESCRIPCIONALMACEN")
    private String descripcionAlmacen;
    @Column(name="REGION")
    private String region;
    @Column(name="DENOMINACION")
    private String denominacion;
    @Column(name="DIRECCIONCENTRO")
    private String direccionCentro;
    @Column(name="DIRECCION1")
    private String direccion1;
    @Column(name="DIRECCION2")
    private String direccion2;
    @Column(name="DIRECCION3")
    private String direccion3;
    @Column(name="NOMBRE")
    private String nombre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public void setCodigoAlmacen(String codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }

    public String getDescripcionAlmacen() {
        return descripcionAlmacen;
    }

    public void setDescripcionAlmacen(String descripcionAlmacen) {
        this.descripcionAlmacen = descripcionAlmacen;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getDireccion3() {
        return direccion3;
    }

    public void setDireccion3(String direccion3) {
        this.direccion3 = direccion3;
    }

    public String getDireccionCentro() {
        return direccionCentro;
    }

    public void setDireccionCentro(String direccionCentro) {
        this.direccionCentro = direccionCentro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "CentroAlmacenBlz{" +
                "id=" + id +
                ", centro='" + centro + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", distrito='" + distrito + '\'' +
                ", codigoAlmacen='" + codigoAlmacen + '\'' +
                ", descripcionAlmacen='" + descripcionAlmacen + '\'' +
                ", region='" + region + '\'' +
                ", denominacion='" + denominacion + '\'' +
                ", direccionCentro='" + direccionCentro + '\'' +
                ", direccion1='" + direccion1 + '\'' +
                ", direccion2='" + direccion2 + '\'' +
                ", direccion3='" + direccion3 + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
