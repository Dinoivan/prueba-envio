package com.incloud.hcp.domain.almacen;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name= "GA_ESTADO_DESPACHO")
public class EstadoDespacho extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_ESTADO_DESPACHO", unique = true, nullable = false)
    private Integer id;

    @Column(name = "DESCRIPCION",nullable = false,length = 40)
    private String descripcion;

    @Column(name = "VALOR",nullable = false,length = 10)
    private String valor;

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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "EstadoDespacho{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
