package com.incloud.hcp.dto;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionProveedor;
import com.incloud.hcp.domain.Moneda;
import com.incloud.hcp.domain.Proveedor;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CotizacionDto {
	private Integer idCotizacion;
	private String estadoCotizacion;
	private Timestamp fechaCreacion;
	private Timestamp fechaModificacion;
	private BigDecimal importeTotal;
	private String indGanador;
	private Integer usuarioCreacion;
	private Integer usuarioModificacion;
	private Proveedor proveedor;
	private Licitacion licitacion;
	private Moneda moneda;
	private String descripcionEstadoCotizacion;
	private LicitacionProveedor licitacionProveedor;
	private String indSiParticipa;


	public Integer getIdCotizacion() {
		return this.idCotizacion;
	}

	public void setIdCotizacion(Integer idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	public String getEstadoCotizacion() {
		return this.estadoCotizacion;
	}

	public void setEstadoCotizacion(String estadoCotizacion) {
		this.estadoCotizacion = estadoCotizacion;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public BigDecimal getImporteTotal() {
		return this.importeTotal;
	}

	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}

	public String getIndGanador() {
		return this.indGanador;
	}

	public void setIndGanador(String indGanador) {
		this.indGanador = indGanador;
	}

	public Integer getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(Integer usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Integer getUsuarioModificacion() {
		return this.usuarioModificacion;
	}

	public void setUsuarioModificacion(Integer usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Licitacion getLicitacion() {
		return licitacion;
	}

	public void setLicitacion(Licitacion licitacion) {
		this.licitacion = licitacion;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

    public String getDescripcionEstadoCotizacion() {
        return descripcionEstadoCotizacion;
    }

    public void setDescripcionEstadoCotizacion(String descripcionEstadoCotizacion) {
		this.descripcionEstadoCotizacion = descripcionEstadoCotizacion;
	}

	public LicitacionProveedor getLicitacionProveedor() {
		return licitacionProveedor;
	}

	public void setLicitacionProveedor(LicitacionProveedor licitacionProveedor) {
		this.licitacionProveedor = licitacionProveedor;
	}

    public String getIndSiParticipa() {
        return indSiParticipa;
    }

    public void setIndSiParticipa(String indSiParticipa) {
        this.indSiParticipa = indSiParticipa;
    }

    //	@Override
//	public String toString() {
//		return "Cotizacion{" +
//				"idCotizacion=" + idCotizacion +
//				", estadoCotizacion='" + estadoCotizacion + '\'' +
//				", fechaCreacion=" + fechaCreacion +
//				", fechaModificacion=" + fechaModificacion +
//				", importeTotal=" + importeTotal +
//				", indGanador='" + indGanador + '\'' +
//				", usuarioCreacion=" + usuarioCreacion +
//				", usuarioModificacion=" + usuarioModificacion +
//				", proveedor=" + proveedor +
//				", licitacion=" + licitacion +
//				", moneda=" + moneda +
//				", descripcionEstadoCotizacion='" + descripcionEstadoCotizacion + '\'' +
//				", licitacionProveedor=" + licitacionProveedor +
//				'}';
//	}


    @Override
    public String toString() {
        return "CotizacionDto{" +
                "idCotizacion=" + idCotizacion +
                ", estadoCotizacion='" + estadoCotizacion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                ", importeTotal=" + importeTotal +
                ", indGanador='" + indGanador + '\'' +
                ", usuarioCreacion=" + usuarioCreacion +
                ", usuarioModificacion=" + usuarioModificacion +
                ", proveedor=" + proveedor +
                ", licitacion=" + licitacion +
                ", moneda=" + moneda +
                ", descripcionEstadoCotizacion='" + descripcionEstadoCotizacion + '\'' +
                ", licitacionProveedor=" + licitacionProveedor +
                ", indSiParticipa='" + indSiParticipa + '\'' +
                '}';
    }
}