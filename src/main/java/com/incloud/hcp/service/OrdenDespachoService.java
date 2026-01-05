package com.incloud.hcp.service;

import com.incloud.hcp.bean.MensajeBean;
import com.incloud.hcp.domain.almacen.OrdenDespacho;
import com.incloud.hcp.dto.FiltroOrdenCompraDto;
import com.incloud.hcp.dto.OrdenDespachoRespuestaDto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface OrdenDespachoService {

    List<OrdenDespacho> getAllOrdenDespacho();

    OrdenDespacho getOrdenDespachoById(Integer idOrdenDespacho);

    MensajeBean reactivarOrdenDespacho(Integer idOrdenDespacho, Integer idEstado);

    List<OrdenDespacho> getOrdenDespachoListPorFechasAndRuc(Date fechaInicio, Date fechaFin, String ruc);
    List<OrdenDespacho> getOrdenDespachoList(FiltroOrdenCompraDto dto);

    List<OrdenDespacho> getOrdenDespachoListPorEstadoFechas(Integer idEstado, Date fechaInicio, Date fechaFin);
    List<OrdenDespacho> getOrdenDespachoListPorEstadoFechasOrdenDespacho(String ordenDespacho, Integer idEstado, Date fechaInicio, Date fechaFin);

    OrdenDespachoRespuestaDto updateOrdenDespachoFechaVisualizacion(Integer idOrdenDespacho);

    OrdenDespachoRespuestaDto aprobarRechazarOrdenDespacho(Integer idOrdenDespacho, int estado, String textoRechazo);

    void extraerOrdenDespachoMasivoByRangoFechas(Date fechaInicio, Date fechaFin, boolean enviarCorreoPublicacion);

    void extraerContratoMarcoMasivoByRangoFechas(Date fechaInicio, Date fechaFin, boolean enviarCorreoPublicacion);

    String getOrdenDespachoPdfContent(String numeroOrdenDespacho) throws Exception;

    String getContratoMarcoPdfContent(String numeroContratoMarco) throws Exception;

    OrdenDespacho getOrdenDespachoByNOCompra(String nOrdenCompra);

    List<HashMap<String,String>> cambioEstadoOCRechazada(List<String> numeroOrdenDespacho);
}
