package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;


/**
 * The persistent class for the bien_servicio database table.
 *
 */
@Entity
@Table(name="bien_servicio")
public class BienServicio extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id_bien_servicio", unique=true, nullable=false)
    @GeneratedValue(generator = "bien_servicio_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "bien_servicio_id_seq", sequenceName = "bien_servicio_id_seq", allocationSize = 1)
    private Integer idBienServicio;

    @Column(name="codigo_sap", unique = true, nullable=false, length=18)
    private String codigoSap;

    @Column(nullable=false, length=80)
    private String descripcion;

    @Column(name="numero_parte", length=40)
    private String numeroParte;

    @Column(name="tipo_item", nullable=false, length=1)
    private String tipoItem;

    //uni-directional many-to-one association to RubroBien
    @ManyToOne
    @JoinColumn(name="id_rubro", nullable=false)
    private RubroBien rubroBien;

    //uni-directional many-to-one association to UnidadMedida
    @ManyToOne
    @JoinColumn(name="id_unidad_medida", nullable=false)
    private UnidadMedida unidadMedida;

    private String descripcionLarga;

    public BienServicio() {
    }

    public BienServicio(String codigoSap, String descripcion, String numeroParte, String tipoItem, RubroBien rubroBien, UnidadMedida unidadMedida) {
        this.codigoSap = codigoSap;
        this.descripcion = descripcion;
        this.numeroParte = numeroParte;
        this.tipoItem = tipoItem;
        this.rubroBien = rubroBien;
        this.unidadMedida = unidadMedida;
    }

    public Integer getIdBienServicio() {
        return this.idBienServicio;
    }

    public void setIdBienServicio(Integer idBienServicio) {
        this.idBienServicio = idBienServicio;
    }

    public String getCodigoSap() {
        return this.codigoSap;
    }

    public void setCodigoSap(String codigoSap) {
        this.codigoSap = codigoSap;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumeroParte() {
        return this.numeroParte;
    }

    public void setNumeroParte(String numeroParte) {
        this.numeroParte = numeroParte;
    }

    public RubroBien getRubroBien() {
        return this.rubroBien;
    }

    public void setRubroBien(RubroBien rubroBien) {
        this.rubroBien = rubroBien;
    }

    public UnidadMedida getUnidadMedida() {
        return this.unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getTipoItem() {
        String tipoBien = "Desconocido";
        if (("M").equals(tipoItem)){
            tipoBien =  "MATERIAL";
        }else if(("S").equals(tipoItem)){
            tipoBien = "SERVICIO";
        }
        return tipoBien;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    // -- [descripcionLarga] ------------------------

    @Size(max = 100)
    @Column(name = "descripcion_larga", length = 100)
    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public BienServicio descripcionLarga(String descripcionLarga) {
        setDescripcionLarga(descripcionLarga);
        return this;
    }


    @Override
    public String toString() {
        return "BienServicio{" +
                "idBienServicio=" + idBienServicio +
                ", codigoSap='" + codigoSap + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", numeroParte='" + numeroParte + '\'' +
                ", tipoItem='" + tipoItem + '\'' +
                ", rubroBien=" + rubroBien +
                ", unidadMedida=" + unidadMedida +
                ", descripcionLarga='" + descripcionLarga + '\'' +
                '}';
    }
}