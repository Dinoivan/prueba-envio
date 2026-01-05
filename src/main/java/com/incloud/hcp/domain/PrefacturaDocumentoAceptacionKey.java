package com.incloud.hcp.domain;

import java.io.Serializable;

public class PrefacturaDocumentoAceptacionKey implements Serializable {

    private Integer idPrefactura;
    private Integer idDocumentoAceptacion;


    public Integer getIdPrefactura() {
        return idPrefactura;
    }

    public void setIdPrefactura(Integer idPrefactura) {
        this.idPrefactura = idPrefactura;
    }

    public Integer getIdDocumentoAceptacion() {
        return idDocumentoAceptacion;
    }

    public void setIdDocumentoAceptacion(Integer idDocumentoAceptacion) {
        this.idDocumentoAceptacion = idDocumentoAceptacion;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrefacturaDocumentoAceptacionKey)) return false;

        PrefacturaDocumentoAceptacionKey that = (PrefacturaDocumentoAceptacionKey) o;

        if (!idPrefactura.equals(that.idPrefactura)) return false;
        return idDocumentoAceptacion.equals(that.idDocumentoAceptacion);
    }

    @Override
    public int hashCode() {
        int result = idPrefactura.hashCode();
        result = 31 * result + idDocumentoAceptacion.hashCode();
        return result;
    }
}
