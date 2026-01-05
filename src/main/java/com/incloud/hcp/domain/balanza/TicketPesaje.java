package com.incloud.hcp.domain.balanza;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_TICKET_PESAJE")
public class TicketPesaje implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(generator = "TICKET_PESAJE_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "TICKET_PESAJE_ID_SEQ", sequenceName = "TICKET_PESAJE_ID_SEQ", allocationSize = 1)
    @Column(name="ID_TICKET_PESAJE", unique=true, nullable=false)
    private Integer id;

    @JsonIgnoreProperties(value={"choferticketPesajeList", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Chofer.class)
    @JoinColumn(name="ID_CHOFER", referencedColumnName = "ID_CHOFER")
    private Chofer chofer;
    /*@Column(name="ID_CHOFER", nullable=false)
    private Integer chofer;*/

    @JsonIgnoreProperties(value={"transporteticketPesajeList", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Transporte.class)
    @JoinColumn(name="ID_TRANSPORTE", referencedColumnName = "ID_TRANSPORTE")
    private Transporte transporte;
    /*@Column(name="ID_TRANSPORTE", nullable=false)
    private Integer transporte;*/

    @JsonIgnoreProperties(value={"carretaticketPesajeList", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Transporte.class)
    //@ManyToOne()
    @JoinColumn(name="ID_CARRETA", referencedColumnName = "ID_TRANSPORTE")
    private Transporte carreta;

    /* @Column(name="ID_CARRETA", nullable=false)
    private Integer carreta;*/
    @Column(name="CODIGO_CENTRO" )
    private String codigoCentro;

    @Column(name="RUC_TRANSPORTISTA" )
    private String rucTransportista;


    @Column(name="RAZ_SOCIAL_TRANSPORTISTA" )
    private String razSocialTransportista;

    @Column(name="OBSERVACION" )
    private String observacion;

    @Column(name="FECHA_REGISTRO" )
    private Timestamp fechaCreacion;

    @Column(name="FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name="USUARIO_CREADOR")
    private String usuarioCreador;

    @Column(name="USUARIO_MODIFICADOR")
    private String usuarioModificador;


    @Column(name="BALANZA")
    private String balanza;




    /*@ManyToOne(fetch = FetchType.LAZY, targetEntity = CentroAlmacen.class)
    @JoinColumn(name="id_centro_almacen", referencedColumnName = "id_centro_almacen", insertable = false, updatable = false)
    private CentroAlmacen centroAlmacen;*/

    @NotNull
    @OneToOne
    @JoinColumn( name = "ID_ESTADO")
    private Estado estado;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketPesaje", targetEntity = DetalleTicket.class, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DetalleTicket> detalleTicketList;





    public TicketPesaje() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }

    public Transporte getCarreta() {
        return carreta;
    }

    public void setCarreta(Transporte carreta) {
        this.carreta = carreta;
    }

    /*public Carreta getCarreta() {
        return carreta;
    }

    public void setCarreta(Carreta carreta) {
        this.carreta = carreta;
    }*/

    public String getCodigoCentro() {
        return codigoCentro;
    }

    public void setCodigoCentro(String codigoCentro) {
        this.codigoCentro = codigoCentro;
    }

    public String getRucTransportista() {
        return rucTransportista;
    }

    public void setRucTransportista(String rucTransportista) {
        this.rucTransportista = rucTransportista;
    }

    public String getRazSocialTransportista() {
        return razSocialTransportista;
    }

    public void setRazSocialTransportista(String razSocialTransportista) {
        this.razSocialTransportista = razSocialTransportista;
    }


    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }






    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<DetalleTicket> getDetalleTicketList() {
        return detalleTicketList;
    }

    public void setDetalleTicketList(List<DetalleTicket> detalleTicketList) {
        this.detalleTicketList = detalleTicketList;
    }


    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public String getUsuarioModificador() {
        return usuarioModificador;
    }

    public void setUsuarioModificador(String usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
    }

    public String getBalanza() {
        return balanza;
    }

    public void setBalanza(String balanza) {
        this.balanza = balanza;
    }




//    @Override
//    public String toString() {
//        return "TicketPesaje{" +
//                "id=" + id +
//                ", chofer=" + chofer +
//                ", transporte=" + transporte +
//                ", carreta=" + carreta +
//                ", codigoCentro='" + codigoCentro + '\'' +
//                ", codigoAlmacen='" + codigoAlmacen + '\'' +
//                ", rucTransportista='" + rucTransportista + '\'' +
//                ", razSocialTransportista='" + razSocialTransportista + '\'' +
//                ", unidadMedida='" + unidadMedida + '\'' +
//                ", observacion='" + observacion + '\'' +
//                ", fechaCreacion=" + fechaCreacion +
//                ", fechaModificacion=" + fechaModificacion +
//                ", loteReceptor='" + loteReceptor + '\'' +
//                ", estado=" + estado +
//                ", detalleTicketList=" + detalleTicketList +
//                '}';
//    }
}
