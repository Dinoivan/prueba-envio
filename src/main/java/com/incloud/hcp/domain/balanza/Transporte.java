package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)

@Table(name="BLZ_TRANSPORTE")
public class Transporte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "TRANSPORTE_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "TRANSPORTE_ID_SEQ", sequenceName = "TRANSPORTE_ID_SEQ", allocationSize = 1)
    @Column(name="ID_TRANSPORTE", unique=true, nullable=false)
    private Integer id;

    @Column(name="PLACA", nullable=false, length=25)
    private String placa;

    @Column(name="REMOLQUE",  nullable=false, length=20)
    private String remolque;

    @Column(name="MARCA", nullable=false, length=50)
    private String marca;

    @Column(name="MODELO", length = 50,  nullable = false)
    private String modelo;

    @Column(name="CIV", length = 25,  nullable = false)
    private String civ;

    @Column(name="FECHA_REGISTRO")
    private Date fechaCreacion;

    @Column(name="FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "ORIGEN")
    private String origen;

    @Column(name = "TIPO_VEHICULO")
    private String tipoVehiculo;

    @Column(name = "NRO_AUTORIZACION")
    private String nroAutorizacion;

    @Column(name = "COD_AUTORIZACION")
    private String codAutorizacion;

    @Column(name = "ZMTC")
    private String zmtc;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    public Transporte() {}

    public Transporte(Integer id, String placa, String remolque, String marca, String modelo, String civ, Date fechaCreacion, Timestamp fechaModificacion, String estado, String origen, String tipoVehiculo, String nroAutorizacion, String codAutorizacion, String zmtc, String createdBy, String modifiedBy) {
        this.id = id;
        this.placa = placa;
        this.remolque = remolque;
        this.marca = marca;
        this.modelo = modelo;
        this.civ = civ;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.estado = estado;
        this.origen = origen;
        this.tipoVehiculo = tipoVehiculo;
        this.nroAutorizacion = nroAutorizacion;
        this.codAutorizacion = codAutorizacion;
        this.zmtc = zmtc;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRemolque() {
        return remolque;
    }

    public void setRemolque(String remolque) {
        this.remolque = remolque;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getCiv() {
        return civ;
    }

    public void setCiv(String civ) {
        this.civ = civ;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getNroAutorizacion() {
        return nroAutorizacion;
    }

    public void setNroAutorizacion(String nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    public String getCodAutorizacion() {
        return codAutorizacion;
    }

    public void setCodAutorizacion(String codAutorizacion) {
        this.codAutorizacion = codAutorizacion;
    }

    public String getZmtc() {
        return zmtc;
    }

    public void setZmtc(String zmtc) {
        this.zmtc = zmtc;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
        return "Transporte{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", remolque='" + remolque + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", civ='" + civ + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                ", estado='" + estado + '\'' +
                ", origen='" + origen + '\'' +
                ", tipoVehiculo='" + tipoVehiculo + '\'' +
                ", nroAutorizacion='" + nroAutorizacion + '\'' +
                ", codAutorizacion='" + codAutorizacion + '\'' +
                ", zmtc='" + zmtc + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
