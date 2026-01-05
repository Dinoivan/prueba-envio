package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_ESTADO")
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "ESTADO_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ESTADO_ID_SEQ", sequenceName = "ESTADO_ID_SEQ", allocationSize = 1)
    @Column(name="ID_ESTADO", unique=true, nullable=false)
    private Integer id;

    @Column(name="DESCRIPCION")
    private String descripcion;

    //@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//    @JsonIgnoreProperties(value={"", "hibernateLazyInitializer", "handler"}, allowSetters=true)
//    @JsonIgnore
//    @OneToOne( mappedBy="estado",fetch = FetchType.LAZY,cascade= CascadeType.ALL)
//    @OneToOne(mappedBy="estado")
//    private TicketPesaje ticketPesaje;
//////    //@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
////    @JsonIgnore
////    @OneToOne(mappedBy="estado",fetch = FetchType.LAZY, cascade= CascadeType.ALL)
//    @OneToOne(mappedBy="estadod")
//    private DetalleTicket detalleticket;



    public Estado() {

    }

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
        return "Estado{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
