package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Access(AccessType.FIELD)
@Table(name="LOG_TRANSACCION")
public class LogTransaccion extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "LOG_TRANSACCION_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "LOG_TRANSACCION_ID_SEQ", sequenceName = "LOG_TRANSACCION_ID_SEQ", allocationSize = 1)
    @Column(name="ID_LOG_TRANSACCION", unique=true, nullable=false)
    private Integer id;

    @Column(name="TIPO_TRANSACCION", length=100)
    private String tipoTransaccion;

    @Column(name="TIPO_REGISTRO", length=100)
    private String tipoRegistro;

    @Column(name="ID_REGISTRO")
    private Integer idRegistro;

    @Column(name="ENVIO_TRAMA", length = 5000)
    private String envioTrama;

    @Column(name="RESPUESTA_CODIGO", length = 100)
    private String respuestaCodigo;

    @Column(name="RESPUESTA_TEXTO", length = 5000)
    private String respuestaTexto;

    @Column(name="LOG_USUARIO", length = 60)
    private String logUsuario;

    @Column(name="LOG_FECHA")
    private Timestamp logFecha;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getEnvioTrama() {
        return envioTrama;
    }

    public void setEnvioTrama(String envioTrama) {
        this.envioTrama = envioTrama;
    }

    public String getRespuestaCodigo() {
        return respuestaCodigo;
    }

    public void setRespuestaCodigo(String respuestaCodigo) {
        this.respuestaCodigo = respuestaCodigo;
    }

    public String getRespuestaTexto() {
        return respuestaTexto;
    }

    public void setRespuestaTexto(String respuestaTexto) {
        this.respuestaTexto = respuestaTexto;
    }

    public String getLogUsuario() {
        return logUsuario;
    }

    public void setLogUsuario(String logUsuario) {
        this.logUsuario = logUsuario;
    }

    public Timestamp getLogFecha() {
        return logFecha;
    }

    public void setLogFecha(Timestamp logFecha) {
        this.logFecha = logFecha;
    }


    @Override
    public String toString() {
        return "LogTransaccion{" +
                "id=" + id +
                ", tipoTransaccion='" + tipoTransaccion + '\'' +
                ", tipoRegistro='" + tipoRegistro + '\'' +
                ", idRegistro=" + idRegistro +
                ", envioTrama='" + envioTrama + '\'' +
                ", respuestaCodigo='" + respuestaCodigo + '\'' +
                ", respuestaTexto='" + respuestaTexto + '\'' +
                ", logUsuario='" + logUsuario + '\'' +
                ", logFecha=" + logFecha +
                '}';
    }
}