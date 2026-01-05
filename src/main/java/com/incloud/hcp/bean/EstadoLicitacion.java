package com.incloud.hcp.bean;

/**
 * Created by USER on 23/08/2017.
 */
public class EstadoLicitacion {

    private int idEstado;
    private String codigo;
    private String valor;

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "EstadoLicitacion{" +
                "idEstado=" + idEstado +
                ", codigo='" + codigo + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
