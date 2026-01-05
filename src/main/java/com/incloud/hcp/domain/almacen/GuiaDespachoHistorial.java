package com.incloud.hcp.domain.almacen;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Entidad que representa el historial de cambios de un despacho en el módulo de Almacenes
 * Registra todas las modificaciones, cambios de estado y acciones realizadas sobre los despachos
 * para trazabilidad y auditoría completa
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "GA_DESPACHO_HISTORIAL")
public class GuiaDespachoHistorial extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "GA_DESPACHO_HISTORIAL_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "GA_DESPACHO_HISTORIAL_ID_SEQ", sequenceName = "GA_DESPACHO_HISTORIAL_ID_SEQ", allocationSize = 1)
    @Column(name = "ID_DESPACHO_HISTORIAL", unique = true, nullable = false)
    private Integer id;

    @Column(name = "ID_GUIA_DESPACHO", nullable = false)
    private Integer idDespacho;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = GuiaDespacho.class)
    @JoinColumn(name = "ID_GUIA_DESPACHO", referencedColumnName = "ID_GUIA_DESPACHO", insertable = false, updatable = false)
    private GuiaDespacho despacho;

    @Column(name = "FECHA_HORA", nullable = false)
    private Timestamp fechaHora;

    @Column(name = "USUARIO", nullable = false, length = 50)
    private String usuario;

    @Column(name = "ACCION", nullable = false, length = 50)
    private String accion;

    @Column(name = "ESTADO_ANTERIOR", length = 20)
    private String estadoAnterior;

    @Column(name = "ESTADO_NUEVO", length = 20)
    private String estadoNuevo;

    @Column(name = "OBSERVACIONES", length = 1000)
    private String observaciones;

    @Column(name = "CAMPO_MODIFICADO", length = 100)
    private String campoModificado;

    @Column(name = "VALOR_ANTERIOR", length = 500)
    private String valorAnterior;

    @Column(name = "VALOR_NUEVO", length = 500)
    private String valorNuevo;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdDespacho() {
        return idDespacho;
    }

    public void setIdDespacho(Integer idDespacho) {
        this.idDespacho = idDespacho;
    }

    public GuiaDespacho getDespacho() {
        return despacho;
    }

    public void setDespacho(GuiaDespacho despacho) {
        this.despacho = despacho;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCampoModificado() {
        return campoModificado;
    }

    public void setCampoModificado(String campoModificado) {
        this.campoModificado = campoModificado;
    }

    public String getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public String getValorNuevo() {
        return valorNuevo;
    }

    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    @Override
    public String toString() {
        return "GuiaDespachoHistorial{" +
                "id=" + id +
                ", idDespacho=" + idDespacho +
                ", despacho=" + despacho +
                ", fechaHora=" + fechaHora +
                ", usuario='" + usuario + '\'' +
                ", accion='" + accion + '\'' +
                ", estadoAnterior='" + estadoAnterior + '\'' +
                ", estadoNuevo='" + estadoNuevo + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", campoModificado='" + campoModificado + '\'' +
                ", valorAnterior='" + valorAnterior + '\'' +
                ", valorNuevo='" + valorNuevo + '\'' +
                '}';
    }
}