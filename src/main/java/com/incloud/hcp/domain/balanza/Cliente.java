package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Access(AccessType.FIELD)
@Table(name = "BLZ_CLIENTE")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "CLIENTE_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CLIENTE_ID_SEQ", sequenceName = "CLIENTE_ID_SEQ", allocationSize = 1)
    @Column(name="ID_CLIENTE", unique=true ,nullable=false)
    private Long id;

    @Column(name = "RUC")
    private String ruc;

    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "NUM_DEUDOR")
    private String numDeudor;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "ORGANIZACION_VENTA")
    private String organizacionVenta;

    @Column(name = "CANAL")
    private String canal;
    @Column(name = "SECTOR")
    private String sector;
    @Column(name = "ESTADO")
    private Boolean estado;
    @Column(name = "ORIGEN")
    private String origen;

    @Column(name = "REGION")
    private String region;

    @Column(name = "BEZEI")
    private String bezei;

    @Column(name = "CITY1")
    private String city1;

    @Column(name = "CITY2")
    private String city2;
    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumDeudor() {
        return numDeudor;
    }

    public void setNumDeudor(String numDeudor) {
        this.numDeudor = numDeudor;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getOrganizacionVenta() {
        return organizacionVenta;
    }

    public void setOrganizacionVenta(String organizacionVenta) {
        this.organizacionVenta = organizacionVenta;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBezei() {
        return bezei;
    }

    public void setBezei(String bezei) {
        this.bezei = bezei;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", ruc='" + ruc + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", direccion='" + direccion + '\'' +
                ", numDeudor='" + numDeudor + '\'' +
                ", correo='" + correo + '\'' +
                ", organizacionVenta='" + organizacionVenta + '\'' +
                ", canal='" + canal + '\'' +
                ", sector='" + sector + '\'' +
                ", estado=" + estado +
                ", origen='" + origen + '\'' +
                ", region='" + region + '\'' +
                ", bezei='" + bezei + '\'' +
                ", city1='" + city1 + '\'' +
                ", city2='" + city2 + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
