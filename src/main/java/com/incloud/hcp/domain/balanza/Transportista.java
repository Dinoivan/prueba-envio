package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Access(AccessType.FIELD)

@Table(name="BLZ_TRANSPORTISTA")
public class Transportista {
    @Id
    @GeneratedValue(generator = "TRANSPORTISTA_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "TRANSPORTISTA_ID_SEQ", sequenceName = "TRANSPORTISTA_ID_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;
    @Column(name = "RUC", unique=true)
    private String ruc;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;

    @Column(name = "ESTADO")
    private Boolean estado;
    @Column(name = "ACREEDOR")
    private String acreedor;
    @Column(name = "GRUPO_CUENTAS")
    private String grupoCuentas;
    @Column(name = "ORIGEN")
    private String origen;


    public Transportista(Long id, Timestamp fechaCreacion, String ruc, String direccion, String razonSocial, Boolean estado, String acreedor, String grupoCuentas, String origen) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.ruc = ruc;
        this.direccion = direccion;
        this.razonSocial = razonSocial;
        this.estado = estado;
        this.acreedor = acreedor;
        this.grupoCuentas = grupoCuentas;
        this.origen = origen;
    }

    public Transportista() {

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

    public String getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(String acreedor) {
        this.acreedor = acreedor;
    }

    public String getGrupoCuentas() {
        return grupoCuentas;
    }

    public void setGrupoCuentas(String grupoCuentas) {
        this.grupoCuentas = grupoCuentas;
    }

    @Override
    public String toString() {
        return "Transportista{" +
                "id=" + id +
                ", fechaCreacion=" + fechaCreacion +
                ", ruc='" + ruc + '\'' +
                ", direccion='" + direccion + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", estado=" + estado +
                ", acreedor='" + acreedor + '\'' +
                ", grupoCuentas='" + grupoCuentas + '\'' +
                ", origen='" + origen + '\'' +
                '}';
    }
}
