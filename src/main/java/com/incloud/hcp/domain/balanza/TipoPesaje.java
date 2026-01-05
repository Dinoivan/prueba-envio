package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_TIPO_PESAJE")
public class TipoPesaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "PESAJE_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PESAJE_ID_SEQ", sequenceName = "PESAJE_ID_SEQ", allocationSize = 1)
    @Column(name="ID_TIPO_PESAJE", unique=true, nullable=false)
    private Integer id;

    @Column(name="CODIGO", length = 9, unique = true, nullable = false)
    private String codigo;

    @Column(name="DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name="FECHA_REGISTRO")
    private Timestamp fechaCreacion;
    @Column(name="ESTADO")
    private Boolean estado;
    @Column(name="ISDELETED")
    private Boolean isDeleted;
    @Column(name="FECHA_MODIFICACION")
    private Timestamp fechaModificacion;


    public TipoPesaje(){

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
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public TipoPesaje(Integer id, String codigo, String descripcion, Timestamp fechaCreacion, Boolean estado, Boolean isDeleted, Timestamp fechaModificacion) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.isDeleted = isDeleted;
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public String toString() {
        return "TipoPesaje{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
