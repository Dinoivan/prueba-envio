package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Access(AccessType.FIELD)
@Table(name = "ESTADO_PREFACTURA")
public class EstadoPrefactura extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_ESTADO_PREFACTURA", unique = true, nullable = false)
    private Integer id;

    @Column(name = "DESCRIPCION", nullable=false, length=40)
    private String descripcion;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public String toString() {
        return "EstadoPrefactura{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
