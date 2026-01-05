package com.incloud.hcp.service;

import com.incloud.hcp.bean.MensajeBean;
import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.dto.FiltroOrdenCompraDto;
import com.incloud.hcp.dto.OrdenCompraRespuestaDto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface OrdenCompraService {

    List<OrdenCompra> getAllOrdenCompra();

    OrdenCompra getOrdenCompraById(Integer idOrdenCompra);

    MensajeBean reactivarOrdenCompra(Integer idOrdenCompra, Integer idEstado);

    List<OrdenCompra> getOrdenCompraListPorFechasAndRuc(Date fechaInicio, Date fechaFin, String ruc);
    List<OrdenCompra> getOrdenCompraList(FiltroOrdenCompraDto dto);

    List<OrdenCompra> getOrdenCompraListPorEstadoFechas(Integer idEstado, Date fechaInicio, Date fechaFin);
    List<OrdenCompra> getOrdenCompraListPorEstadoFechasOrdenCompra(String ordenCompra, Integer idEstado, Date fechaInicio, Date fechaFin);

    OrdenCompraRespuestaDto updateOrdenCompraFechaVisualizacion(Integer idOrdenCompra);

    OrdenCompraRespuestaDto aprobarRechazarOrdenCompra(Integer idOrdenCompra, int estado, String textoRechazo);

    void extraerOrdenCompraMasivoByRangoFechas(Date fechaInicio, Date fechaFin, boolean enviarCorreoPublicacion);

    void extraerContratoMarcoMasivoByRangoFechas(Date fechaInicio, Date fechaFin, boolean enviarCorreoPublicacion);

    String getOrdenCompraPdfContent(String numeroOrdenCompra) throws Exception;

    String getContratoMarcoPdfContent(String numeroContratoMarco) throws Exception;

    OrdenCompra getOrdenCompraByNOCompra(String nOrdenCompra);

    List<HashMap<String,String>> cambioEstadoOCRechazada(List<String> numeroOrdenCompra);
}
