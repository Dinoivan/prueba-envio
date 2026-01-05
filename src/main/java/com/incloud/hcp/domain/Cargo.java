package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Created by MARCELO on 07/11/2017.
 */
@Entity
@Table(name="cargo")
public class Cargo extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id_cargo", unique=true, nullable=false)
    @GeneratedValue(generator = "cargo_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cargo_id_seq", sequenceName = "cargo_id_seq", allocationSize = 1)
    private Integer idCargo;

    @Column(name="cargo_nombre", nullable=false, length=15)
    private String nombreCargo;

    @Column(unique = true, nullable=false, length=80)
    private String descripcion;

    @Column(name="rol_idp", length=100)
    private String rolIdp;

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRolIdp() {
        return rolIdp;
    }

    public void setRolIdp(String rolIdp) {
        this.rolIdp = rolIdp;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "idCargo=" + idCargo +
                ", nombreCargo='" + nombreCargo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", rolIdp='" + rolIdp + '\'' +
                '}';
    }


}
