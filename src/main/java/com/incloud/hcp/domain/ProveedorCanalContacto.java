package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrador on 24/10/2017.
 */
@Entity
@Table(name="proveedor_canal_contacto")
public class ProveedorCanalContacto extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id_proveedor_canal", unique=true, nullable=false)
    @GeneratedValue(generator = "proveedor_canal_contacto_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator( name = "proveedor_canal_contacto_id_seq",
            sequenceName = "proveedor_canal_contacto_id_seq",
            allocationSize = 1)
    private Integer idProveedorCanal;

    @ManyToOne
    @JoinColumn(name="id_proveedor", nullable=false)
    private Proveedor proveedor;

    //uni-directional many-to-one association to Ubigeo
    @ManyToOne
    @JoinColumn(name="id_pais", nullable=false)
    private Ubigeo pais;

    //uni-directional many-to-one association to Ubigeo
    @ManyToOne
    @JoinColumn(name="id_region", nullable=false)
    private Ubigeo region;

    @ManyToOne
    @JoinColumn(name="id_provincia", nullable=false)
    private Ubigeo provincia;

    @Column(length=200)
    private String direccion;

    @Column(name="area_empresa", nullable=false, length=4)
    private String areaEmpresa;

    @Column(nullable=false, length=100)
    private String contacto;

    @Column(length=80)
    private String email;

    @Column(length=30)
    private String telefono;

    public ProveedorCanalContacto() {
    }

    public Integer getIdProveedorCanal() {
        return idProveedorCanal;
    }

    public void setIdProveedorCanal(Integer idProveedorCanal) {
        this.idProveedorCanal = idProveedorCanal;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Ubigeo getPais() {
        return pais;
    }

    public void setPais(Ubigeo pais) {
        this.pais = pais;
    }

    public Ubigeo getRegion() {
        return region;
    }

    public void setRegion(Ubigeo region) {
        this.region = region;
    }

    public Ubigeo getProvincia() {
        return provincia;
    }

    public void setProvincia(Ubigeo provincia) {
        this.provincia = provincia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getAreaEmpresa() {
        return areaEmpresa;
    }

    public void setAreaEmpresa(String areaEmpresa) {
        this.areaEmpresa = areaEmpresa;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "ProveedorCanalContacto{" +
                "idProveedorCanal=" + idProveedorCanal +
                ", proveedor=" + proveedor +
                ", pais=" + pais +
                ", region=" + region +
                ", provincia=" + provincia +
                ", direccion='" + direccion + '\'' +
                ", areaEmpresa='" + areaEmpresa + '\'' +
                ", contacto='" + contacto + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
