package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;

@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_CENTRO_ALMACEN_BALANZA")
public class CentroAlmacenBalanza {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "CENTRO_ALMACEN_BALANZA_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CENTRO_ALMACEN_BALANZA_ID_SEQ", sequenceName = "CENTRO_ALMACEN_BALANZA_ID_SEQ", allocationSize = 1)
    @Column(name="ID_CENTRO_ALMACEN_BALANZA", unique=true, nullable=false)
    private Integer id;

    @Column(name="CENTRO", length = 30)
    private String centro;

    @Column(name="ALMACEN", length = 30)
    private String almacen;

    @Column(name="BALANZA", length = 30)
    private String balanza;

    @Column(name="IMPRESORAS", length = 300)
    private String impresoras;

    @Column(name="USUARIO", length = 300)
    private String usuario;

    @Column(name="ESTADO")
    private Boolean estado;

    @Column(name="SOCIEDAD", length = 4)
    private String sociedad;

    @Column(name="MODIFICAR", length = 15)
    private String modificar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getBalanza() {
        return balanza;
    }

    public void setBalanza(String balanza) {
        this.balanza = balanza;
    }

    public String getImpresoras() {
        return impresoras;
    }

    public void setImpresoras(String impresoras) {
        this.impresoras = impresoras;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getModificar() {
        return modificar;
    }

    public void setModificar(String modificar) {
        this.modificar = modificar;
    }

    @Override
    public String toString() {
        return "CentroAlmacenBalanza{" +
                "id=" + id +
                ", centro='" + centro + '\'' +
                ", almacen='" + almacen + '\'' +
                ", balanza='" + balanza + '\'' +
                ", impresoras='" + impresoras + '\'' +
                ", usuario='" + usuario + '\'' +
                ", estado=" + estado +
                ", sociedad=" + sociedad +
                ", modificar=" + modificar +
                '}';
    }
}
