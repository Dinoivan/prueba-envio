package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Created by USER on 15/01/2018.
 */
@Entity
@Table(name="control_cambio_campo")
public class ControlCambioCampo extends BaseDomain implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id_control_cambio_campo", unique=true, nullable=false)
    @GeneratedValue(generator = "control_cambio_campo_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "control_cambio_campo_id_seq", sequenceName = "control_cambio_campo_id_seq", allocationSize = 1)
    private Integer idControlCambioCampo;

    @Column(name="nombre_campo", length=250, nullable=false)
    private String nombreCampo;

    @Column(name="valor_inicial", length=250, nullable=false)
    private String valorInicial;

    @Column(name="valor_final", length=250, nullable=false)
    private String valorFinal;

    //uni-directional many-to-one association to Licitacion
    @ManyToOne
    @JoinColumn(name="id_licitacion_control_cambio", nullable=false)
    private LicitacionControlCambio licitacionControlCambio;


    public ControlCambioCampo() {
    }

    public ControlCambioCampo(String nombreCampo, String valorInicial, String valorFinal, LicitacionControlCambio licitacionControlCambio) {
        this.nombreCampo = nombreCampo;
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
        this.licitacionControlCambio = licitacionControlCambio;
    }

    public Integer getIdControlCambioCampo() {
        return idControlCambioCampo;
    }

    public void setIdControlCambioCampo(Integer idControlCambioCampo) {
        this.idControlCambioCampo = idControlCambioCampo;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public String getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(String valorInicial) {
        this.valorInicial = valorInicial;
    }

    public String getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(String valorFinal) {
        this.valorFinal = valorFinal;
    }

    public LicitacionControlCambio getLicitacionControlCambio() {
        return licitacionControlCambio;
    }

    public void setLicitacionControlCambio(LicitacionControlCambio licitacionControlCambio) {
        this.licitacionControlCambio = licitacionControlCambio;
    }

    @Override
    public String toString() {
        return "ControlCambioCampo{" +
                "idControlCambioCampo=" + idControlCambioCampo +
                ", nombreCampo='" + nombreCampo + '\'' +
                ", valorInicial='" + valorInicial + '\'' +
                ", valorFinal='" + valorFinal + '\'' +
                ", licitacionControlCambio=" + licitacionControlCambio +
                '}';
    }
}
