package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_PROVEEDOR")
public class ProveedorBLZ {
    @Id
    @GeneratedValue(generator = "PROVEEDOR_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PROVEEDOR_ID_SEQ", sequenceName = "PROVEEDOR_ID_SEQ", allocationSize = 1)
    @Column(name="ID_PROVEEDOR", unique=true, nullable=false)
    private Long id;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;
    @Column(name = "GRUPO_CUENTAS")
    private String grupoCuentas;
    @Column(name = "RUC")
    private String ruc;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;

    @Column(name = "ESTADO")
    private Boolean estado;

    @Column(name = "ORIGEN")
    private String origen;

    @Column(name = "ACREEDOR")
    private String acreedor;

    @Column(name = "REGION")
    private String region;

    @Column(name = "BEZEI")
    private String bezei;

    @Column(name = "CITY1")
    private String city1;

    @Column(name = "CITY2")
    private String city2;
    public ProveedorBLZ() {
    }

    public ProveedorBLZ(Long id, Timestamp fechaCreacion, String grupoCuentas, String ruc, String direccion, String razonSocial, Boolean estado, String origen, String acreedor, String region, String bezei, String city1, String city2) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.grupoCuentas = grupoCuentas;
        this.ruc = ruc;
        this.direccion = direccion;
        this.razonSocial = razonSocial;
        this.estado = estado;
        this.origen = origen;
        this.acreedor = acreedor;
        this.region = region;
        this.bezei = bezei;
        this.city1 = city1;
        this.city2 = city2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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

    public String getGrupoCuentas() {
        return grupoCuentas;
    }

    public void setGrupoCuentas(String grupoCuentas) {
        this.grupoCuentas = grupoCuentas;
    }

    public String getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(String acreedor) {
        this.acreedor = acreedor;
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
        return "ProveedorBLZ{" +
                "id=" + id +
                ", fechaCreacion=" + fechaCreacion +
                ", grupoCuentas='" + grupoCuentas + '\'' +
                ", ruc='" + ruc + '\'' +
                ", direccion='" + direccion + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", estado=" + estado +
                ", origen='" + origen + '\'' +
                ", acreedor='" + acreedor + '\'' +
                ", region='" + region + '\'' +
                ", bezei='" + bezei + '\'' +
                ", city1='" + city1 + '\'' +
                ", city2='" + city2 + '\'' +
                '}';
    }
}
