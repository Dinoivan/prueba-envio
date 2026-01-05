package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_DIRECCION_ALTERNATIVA")
public class DireccionAlternativa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "DIRECCION_ALTERNATIVA_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "DIRECCION_ALTERNATIVA_ID_SEQ", sequenceName = "DIRECCION_ALTERNATIVA_ID_SEQ", allocationSize = 1)
    @Column(name = "ID_DIRECCION_ALTERNATIVA", unique = true, nullable = false)
    private Integer id;


    @Column(name = "RUC")
    private String ruc;

   /* @Column(name="ID_PROVEEDOR",  nullable=false, length=4)
    private String codigo;*/


    @Column(name = "DESCRIPCION",length = 250)
    private String descripcion;

    @Column(name = "ACREEDOR",length = 20)
    private String acreedor;

    public DireccionAlternativa() {

    }

    public String getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(String acreedor) {
        this.acreedor = acreedor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "DireccionAlternativa{" +
                "id=" + id +
                ", ruc='" + ruc + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", acreedor='" + acreedor + '\'' +
                '}';
    }
}
