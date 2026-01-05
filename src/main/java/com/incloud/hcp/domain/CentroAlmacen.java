package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.io.Serializable;


/**
 * The persistent class for the centro_almacen database table.
 *
 */
@Entity
@Table(name="centro_almacen")
public class CentroAlmacen extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id_centro_almacen", unique=true, nullable=false)
    @GeneratedValue(generator = "centro_almacen_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "centro_almacen_id_seq", sequenceName = "centro_almacen_id_seq", allocationSize = 1)
    private Integer idCentroAlmacen;

    @Column(name="codigo_sap", nullable=false, length=4)
    private String codigoSap;

    @Column(nullable=false, length=100)
    private String denominacion;

    @Column(name="id_padre")
    private Integer idPadre;

    @Column(nullable=false)
    private Integer nivel;

    @Size(max = 200)
    @Column(name = "poblacion", length = 200)
    private String poblacion;

    @Size(max = 200)
    @Column(name = "distrito", length = 200)
    private String distrito;

    @Size(max = 200)
    @Column(name = "direccion", length = 200)
    private String direccion;
    @Size(max = 200)
    @Column(name = "direccion2", length = 200)
    private String direccion2;

    @Size(max = 200)
    @Column(name = "direccion3", length = 200)
    private String direccion3;


    @Transient
    private String codigoSapPadre;

   /* @OneToMany(mappedBy = "centroAlmacen", targetEntity = TicketPesaje.class, fetch = FetchType.LAZY)
    private List<TicketPesaje> centroAlmacenticketPesajeList;
*/
    public CentroAlmacen() {
    }

    /*public List<TicketPesaje> getCentroAlmacenticketPesajeList() {
        return centroAlmacenticketPesajeList;
    }

    public void setCentroAlmacenticketPesajeList(List<TicketPesaje> centroAlmacenticketPesajeList) {
        this.centroAlmacenticketPesajeList = centroAlmacenticketPesajeList;
    }
*/
    public Integer getIdCentroAlmacen() {
        return this.idCentroAlmacen;
    }

    public void setIdCentroAlmacen(Integer idCentroAlmacen) {
        this.idCentroAlmacen = idCentroAlmacen;
    }

    public String getCodigoSap() {
        return this.codigoSap;
    }

    public void setCodigoSap(String codigoSap) {
        this.codigoSap = codigoSap;
    }

    public String getDenominacion() {
        return this.denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Integer getIdPadre() {
        return this.idPadre;
    }

    public void setIdPadre(Integer idPadre) {
        this.idPadre = idPadre;
    }

    public Integer getNivel() {
        return this.nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    // -- [direccion] ------------------------

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public CentroAlmacen direccion(String direccion) {
        setDireccion(direccion);
        return this;
    }
    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public CentroAlmacen direccion2(String direccion2) {
        setDireccion2(direccion2);
        return this;
    }
    public String getDireccion3() {
        return direccion3;
    }

    public void setDireccion3(String direccion3) {
        this.direccion3 = direccion3;
    }

    public CentroAlmacen direccion3(String direccion3) {
        setDireccion(direccion3);
        return this;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCodigoSapPadre() {
        return codigoSapPadre;
    }

    public void setCodigoSapPadre(String codigoSapPadre) {
        this.codigoSapPadre = codigoSapPadre;
    }

    @Override
    public String toString() {
        return "CentroAlmacen{" +
                "idCentroAlmacen=" + idCentroAlmacen +
                ", codigoSap='" + codigoSap + '\'' +
                ", denominacion='" + denominacion + '\'' +
                ", idPadre=" + idPadre +
                ", nivel=" + nivel +
                ", poblacion='" + poblacion + '\'' +
                ", distrito='" + distrito + '\'' +
                ", direccion='" + direccion + '\'' +
                ", direccion2='" + direccion2 + '\'' +
                ", direccion3='" + direccion3 + '\'' +
                ", codigoSapPadre='" + codigoSapPadre + '\'' +
                '}';
    }
}