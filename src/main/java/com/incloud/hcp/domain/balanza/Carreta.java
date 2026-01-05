package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_CARRETA")
public class Carreta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "CARRETA_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CARRETA_ID_SEQ", sequenceName = "CARRETA_ID_SEQ", allocationSize = 1)
    @Column(name="ID_CARRETA", unique=true, nullable=false)
    private Integer id;
    @Column(name="PLACA", length = 100, nullable= false)
    private String placa;
    @Column(name="MODELO", length = 200, nullable= false)

    private String modelo;
    @Column(name="CODIGO")
    private String codigo;
    @Column(name="DESCRIPCION")
    private String descripcion;

    @Column(name="FECHA_REGISTRO")
    private Timestamp fechaCreacion;

    @Column(name="FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "ESTADO")
    private Boolean estado;

    @Column(name = "ORIGEN")
    private String origen;


    public Carreta() {}

    public Carreta(Integer id, String placa, String modelo, String codigo, String descripcion, Timestamp fechaCreacion, Timestamp fechaModificacion, Boolean estado, String origen) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.estado = estado;
        this.origen = origen;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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

    @Override
    public String toString() {
        return "Carreta{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                ", estado=" + estado +
                ", origen='" + origen + '\'' +
                '}';
    }
}
