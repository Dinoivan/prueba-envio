package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_TIPO_PRODUCTO")
public class TipoProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "PRODUCTO_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRODUCTO_ID_SEQ", sequenceName = "PRODUCTO_ID_SEQ", allocationSize = 1)
    @Column(name="ID_TIPO_PRODUCTO", unique=true, nullable=false)
    private Integer id;

    @Column(name="CODIGO", length = 9, unique = true, nullable = false)
    private String codigo;

    @Column(name="DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name="FECHA_REGISTRO")
    private Timestamp fechaCreacion;

    @Column(name="FECHA_MODIFICACION")
    private Timestamp fechaModificacion;
    @Column(name="ESTADO")
    private Boolean estado;

    @Column(name="ISDELETED")
    private Boolean isDeleted;

    //Nuevo
    @Column(name = "UNIDAD_MEDIDA")
    private String unidadMedida;

    @Column(name="CORRELATIVO")
    private String correlativo;

    @Column(name="TIPO_MOVIMIENTO")
    private String tipoMovimiento;

    public TipoProducto(){

    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    //Nuevo
    public String getUnidadMedida(){return unidadMedida;}

    public void setUnidadMedida(String unidadMedida){this.unidadMedida = unidadMedida;}

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public TipoProducto(Integer id, String codigo, String descripcion, Timestamp fechaCreacion, Timestamp fechaModificacion, Boolean estado, Boolean isDeleted, String correlativo, String tipoMovimiento,String unidadMedida) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.estado = estado;
        this.isDeleted = isDeleted;
        this.correlativo = correlativo;
        this.tipoMovimiento = tipoMovimiento;
        this.unidadMedida = unidadMedida;
    }

    @Override
    public String toString() {
        return "TipoProducto{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                ", isDeleted=" + isDeleted +
                ", unidadMedida=' " + unidadMedida + '\'' +
                '}';
    }
}
