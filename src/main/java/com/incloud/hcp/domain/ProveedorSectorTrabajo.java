package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="proveedor_sector_trabajo")
public class ProveedorSectorTrabajo extends BaseDomain implements Serializable {
    @Id
    @Column(name="id_proveedor_sector_trabajo", unique=true, nullable=false)
    @GeneratedValue(generator = "proveedor_sector_trabajo_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "proveedor_sector_trabajo_id_seq", sequenceName = "proveedor_sector_trabajo_id_seq", allocationSize = 1)
    private Integer idProveedorSectorTrabjo;

    @ManyToOne
    @JoinColumn(name="id_proveedor", nullable=false)
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name="id_sector_trabajo", nullable=false)
    private SectorTrabajo sectorTrabajo;


    public ProveedorSectorTrabajo() {
    }

    public Integer getIdProveedorSectorTrabjo() {
        return idProveedorSectorTrabjo;
    }

    public void setIdProveedorSectorTrabjo(Integer idProveedorSectorTrabjo) {
        this.idProveedorSectorTrabjo = idProveedorSectorTrabjo;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public SectorTrabajo getSectorTrabajo() {
        return sectorTrabajo;
    }

    public void setSectorTrabajo(SectorTrabajo sectorTrabajo) {
        this.sectorTrabajo = sectorTrabajo;
    }

    @Override
    public String toString() {
        return "ProveedorSectorTrabajo{" +
                "idProveedorSectorTrabjo=" + idProveedorSectorTrabjo +
                ", proveedor=" + proveedor +
                ", sectorTrabajo=" + sectorTrabajo +
                '}';
    }
}
