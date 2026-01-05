package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Access(AccessType.FIELD)
@Table(name = "PREFACTURA_DOCUMENTO_ACEPTACION")
@IdClass(PrefacturaDocumentoAceptacionKey.class)
public class PrefacturaDocumentoAceptacion extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_PREFACTURA", nullable = false)
    private Integer idPrefactura;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = DocumentoAceptacion.class)
    @JoinColumn(name="ID_DOCUMENTO_ACEPTACION", referencedColumnName = "ID_DOCUMENTO_ACEPTACION", insertable = false, updatable = false)
    private DocumentoAceptacion documentoAceptacion;

    @Id
    @Column(name = "ID_DOCUMENTO_ACEPTACION", nullable=false)
    private Integer idDocumentoAceptacion;

    @Column(name = "IS_ACTIVE", length=1)
    private String isActive;


    public Integer getIdPrefactura() {
        return idPrefactura;
    }

    public void setIdPrefactura(Integer idPrefactura) {
        this.idPrefactura = idPrefactura;
    }

    public DocumentoAceptacion getDocumentoAceptacion() {
        return documentoAceptacion;
    }

    public void setDocumentoAceptacion(DocumentoAceptacion documentoAceptacion) {
        this.documentoAceptacion = documentoAceptacion;
    }

    public Integer getIdDocumentoAceptacion() {
        return idDocumentoAceptacion;
    }

    public void setIdDocumentoAceptacion(Integer idDocumentoAceptacion) {
        this.idDocumentoAceptacion = idDocumentoAceptacion;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "PrefacturaDocumentoAceptacion{" +
                "idPrefactura=" + idPrefactura +
                ", documentoAceptacion=" + documentoAceptacion +
                ", idDocumentoAceptacion=" + idDocumentoAceptacion +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
