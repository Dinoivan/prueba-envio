package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by USER on 15/01/2018.
 */
@Entity
@Table(name="licitacion_control_cambio")
public class LicitacionControlCambio extends BaseDomain implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id_licitacion_control_cambio", unique=true, nullable=false)
    @GeneratedValue(generator = "licitacion_control_cambio_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "licitacion_control_cambio_id_seq", sequenceName = "licitacion_control_cambio_id_seq", allocationSize = 1)
    private Integer idLicitacionControlCambio;

    @Column(name="comentario", length=500, nullable=false)
    private String comentario;

    @Column(name="usuario_creacion", length=30, nullable=false)
    private String usuarioCreacion;

    @Column(name="fecha_creacion", nullable=false)
    private Timestamp fechaCreacion;

    @Transient
    private String fechaCreacionString;

    //uni-directional many-to-one association to Licitacion
    @ManyToOne
    @JoinColumn(name="id_licitacion", nullable=false)
    private Licitacion licitacion;

    @Transient
    private List<ControlCambioCampo> controlCambioCamposList;



    public LicitacionControlCambio() {
    }

    public LicitacionControlCambio(String comentario, String usuarioCreacion, Timestamp fechaCreacion, Licitacion licitacion) {
        this.comentario = comentario;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.licitacion = licitacion;
    }

    public Integer getIdLicitacionControlCambio() {
        return idLicitacionControlCambio;
    }

    public void setIdLicitacionControlCambio(Integer idLicitacionControlCambio) {
        this.idLicitacionControlCambio = idLicitacionControlCambio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Licitacion getLicitacion() {
        return licitacion;
    }

    public void setLicitacion(Licitacion licitacion) {
        this.licitacion = licitacion;
    }

    public List<ControlCambioCampo> getControlCambioCamposList() {
        return controlCambioCamposList;
    }

    public void setControlCambioCamposList(List<ControlCambioCampo> controlCambioCamposList) {
        this.controlCambioCamposList = controlCambioCamposList;
    }

    public String getFechaCreacionString() {
        return fechaCreacionString;
    }

    public void setFechaCreacionString(String fechaCreacionString) {
        this.fechaCreacionString = fechaCreacionString;
    }

    @Override
    public String toString() {
        return "LicitacionControlCambio{" +
                "idLicitacionControlCambio=" + idLicitacionControlCambio +
                ", comentario='" + comentario + '\'' +
                ", usuarioCreacion='" + usuarioCreacion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaCreacionString='" + fechaCreacionString + '\'' +
                ", licitacion=" + licitacion +
                ", controlCambioCamposList=" + controlCambioCamposList +
                '}';
    }
}
