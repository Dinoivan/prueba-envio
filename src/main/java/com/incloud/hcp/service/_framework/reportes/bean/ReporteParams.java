package com.incloud.hcp.service._framework.reportes.bean;

import com.incloud.hcp.bean.UserSession;
import net.sf.jasperreports.engine.JasperReport;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ReporteParams {

	private String nombreReporte;
	private String tipoReporteJasper;
	private JasperReport jasperReport;
	private InputStream inputStreamReporte;

	private Map<String, Object> parameterMap;
	private List<?> listaDataReporte;

	private UserSession userDetails;
	private Date fechaActual;

	public String getNombreReporte() {
		return nombreReporte;
	}

	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}

	public String getTipoReporteJasper() {
		return tipoReporteJasper;
	}

	public void setTipoReporteJasper(String tipoReporteJasper) {
		this.tipoReporteJasper = tipoReporteJasper;
	}

	public JasperReport getJasperReport() {
		return jasperReport;
	}

	public void setJasperReport(JasperReport jasperReport) {
		this.jasperReport = jasperReport;
	}

	public InputStream getInputStreamReporte() {
		return inputStreamReporte;
	}

	public void setInputStreamReporte(InputStream inputStreamReporte) {
		this.inputStreamReporte = inputStreamReporte;
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public List<?> getListaDataReporte() {
		return listaDataReporte;
	}

	public void setListaDataReporte(List<?> listaDataReporte) {
		this.listaDataReporte = listaDataReporte;
	}

	public UserSession getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserSession userDetails) {
		this.userDetails = userDetails;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	@Override
	public String toString() {
		return "ReporteParams{" +
				"nombreReporte='" + nombreReporte + '\'' +
				", tipoReporteJasper='" + tipoReporteJasper + '\'' +
				", jasperReport=" + jasperReport +
				", inputStreamReporte=" + inputStreamReporte +
				", parameterMap=" + parameterMap +
				", listaDataReporte=" + listaDataReporte +
				", userDetails=" + userDetails +
				", fechaActual=" + fechaActual +
				'}';
	}
}
