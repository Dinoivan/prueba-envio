package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Access(AccessType.FIELD)
@Table(name = "SOCIEDAD")
public class Sociedad extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CODIGO_SOCIEDAD", unique = true, nullable=false, length=4)
    private String codigoSociedad;

    @Column(name = "RUC", length=16)
    private String ruc;

    @Column(name = "RAZON_SOCIAL", length=50)
    private String razonSocial;

    @Column(name = "DIRECCION_FISCAL", length=80)
    private String direccionFiscal;

    @Column(name = "TELEFONO", length=20)
    private String telefono;


    public String getCodigoSociedad() {
        return codigoSociedad;
    }

    public void setCodigoSociedad(String codigoSociedad) {
        this.codigoSociedad = codigoSociedad;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccionFiscal() {
        return direccionFiscal;
    }

    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    @Override
    public String toString() {
        return "Sociedad{" +
                "codigoSociedad='" + codigoSociedad + '\'' +
                ", ruc='" + ruc + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", direccionFiscal='" + direccionFiscal + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
