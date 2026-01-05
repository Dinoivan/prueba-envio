package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_CHOFER")
public class Chofer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "CHOFER_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CHOFER_ID_SEQ", sequenceName = "CHOFER_ID_SEQ", allocationSize = 1)
    @Column(name="ID_CHOFER", unique=true, nullable=false)
    private Integer id;

    @Column(name="LICENCIA", length = 25, nullable = false)
    private String licencia;

    @Column(name="NOMBRE", nullable = false)
    private String nombre;

    @Column(name="DNI", length = 25, nullable = false)
    private String dni;

    @Column(name="APELLIDO_PATERNO", length = 50)
    private String apellidoPaterno;

    @Column(name="APELLIDO_MATERNO", length = 50)
    private String apellidoMaterno;

    @Column(name="TIPO_DOCUMENTO", length = 1)
    private String tipoDocumento;

    @Column(name="FECHA_REGISTRO")
    private Timestamp fechaCreacion;

    @Column(name="FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "ORIGEN")
    private String origen;

    @Column(name = "MIGRADO")
    private String migrado;


    public Chofer(){

    }

    public Chofer(Integer id, String licencia, String nombre, String dni, String apellidoPaterno, String apellidoMaterno, String tipoDocumento, Timestamp fechaCreacion, Timestamp fechaModificacion, String estado, String origen, String migrado) {
        this.id = id;
        this.licencia = licencia;
        this.nombre = nombre;
        this.dni = dni;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.tipoDocumento = tipoDocumento;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.estado = estado;
        this.origen = origen;
        this.migrado = migrado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getMigrado() {
        return migrado;
    }

    public void setMigrado(String migrado) {
        this.migrado = migrado;
    }


}
