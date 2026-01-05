package com.incloud.hcp.dto;

public class RechazoDto {
  String motivo;

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	@Override
	public String toString() {
		return "RechazoDto{" +
				"motivo='" + motivo + '\'' +
				'}';
	}
}
