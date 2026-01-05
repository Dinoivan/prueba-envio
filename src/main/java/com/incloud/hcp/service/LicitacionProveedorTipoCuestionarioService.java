package com.incloud.hcp.service;

import com.incloud.hcp.domain.LicitacionProveedorTipoCuestionario;
import com.incloud.hcp.dto.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;

/**
 * Created by USER on 22/09/2017.
 */
public interface LicitacionProveedorTipoCuestionarioService {

    GanadorLicitacionSalidaDto obtenerGanadorDetalleTipoCuestionario(
            GanadorLicitacionEntradaDto bean) throws Exception;

    GanadorLicitacionSalidaDto obtenerGanadorTipoCuestionario(
            GanadorLicitacionEntradaDto bean) throws Exception;

    List<LicitacionProveedorTipoCuestionario> obtenerLicitacionProveedorTipoCuestionario(
            Integer idLicitacion) throws Exception;


    GanadorLicitacionSalidaTipoLicitacionDto obtenerTipoLicitacion(Integer idLicitacion) throws Exception;

    GanadorLicitacionEntradaDto obtenerEvaluacionTecnica(Integer idLicitacion) throws Exception;

    List<GraficoMiniSalidaDto> graficaPrecioFinalMoneda(Integer idLicitacion) throws Exception;

    List<GraficoMiniSalidaDto> graficaPorcentajeObtenido(Integer idLicitacion) throws Exception;

    List<LicitacionProveedorDetalleEvaluacionDto> obtenerLicitacionProveedorDetalleCuadroEvaluacion(Integer idLicitacion) throws Exception;

    SXSSFWorkbook downloadGanadorDetalleCuadro(Integer idLicitacion) throws Exception;

}
