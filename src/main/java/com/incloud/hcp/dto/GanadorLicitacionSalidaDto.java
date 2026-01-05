package com.incloud.hcp.dto;

import com.incloud.hcp.domain.LicitacionProveedorDetalleEvaluacion;
import com.incloud.hcp.domain.LicitacionProveedorTipoCuestionario;

import java.io.Serializable;
import java.util.List;

public class GanadorLicitacionSalidaDto implements Serializable {

    private boolean ejecucion = true;
    private Integer contadorResult = 0;
    private Integer contadorDetalleResult = 0;
    private List<LicitacionProveedorTipoCuestionario> resultList;
    private List<LicitacionProveedorDetalleEvaluacion> resultDetalleList;
    private List<LicitacionProveedorDetalleEvaluacionDto> resultDetalleCuadroList;

    private List<GraficoMiniSalidaDto> graficaPrecioFinalMonedaList;
    private List<GraficoMiniSalidaDto> graficaPorcentajeObtenidoList;



    public List<LicitacionProveedorTipoCuestionario> getResultList() {
        return resultList;
    }

    public void setResultList(List<LicitacionProveedorTipoCuestionario> resultList) {
        this.resultList = resultList;
    }

    public List<GraficoMiniSalidaDto> getGraficaPrecioFinalMonedaList() {
        return graficaPrecioFinalMonedaList;
    }

    public void setGraficaPrecioFinalMonedaList(List<GraficoMiniSalidaDto> graficaPrecioFinalMonedaList) {
        this.graficaPrecioFinalMonedaList = graficaPrecioFinalMonedaList;
    }

    public List<GraficoMiniSalidaDto> getGraficaPorcentajeObtenidoList() {
        return graficaPorcentajeObtenidoList;
    }

    public void setGraficaPorcentajeObtenidoList(List<GraficoMiniSalidaDto> graficaPorcentajeObtenidoList) {
        this.graficaPorcentajeObtenidoList = graficaPorcentajeObtenidoList;
    }

    public boolean isEjecucion() {
        return ejecucion;
    }

    public void setEjecucion(boolean ejecucion) {
        this.ejecucion = ejecucion;
    }

    public List<LicitacionProveedorDetalleEvaluacion> getResultDetalleList() {
        return resultDetalleList;
    }

    public void setResultDetalleList(List<LicitacionProveedorDetalleEvaluacion> resultDetalleList) {
        this.resultDetalleList = resultDetalleList;
    }


    public Integer getContadorResult() {
        return contadorResult;
    }

    public void setContadorResult(Integer contadorResult) {
        this.contadorResult = contadorResult;
    }

    public Integer getContadorDetalleResult() {
        return contadorDetalleResult;
    }

    public void setContadorDetalleResult(Integer contadorDetalleResult) {
        this.contadorDetalleResult = contadorDetalleResult;
    }


    public List<LicitacionProveedorDetalleEvaluacionDto> getResultDetalleCuadroList() {
        return resultDetalleCuadroList;
    }

    public void setResultDetalleCuadroList(List<LicitacionProveedorDetalleEvaluacionDto> resultDetalleCuadroList) {
        this.resultDetalleCuadroList = resultDetalleCuadroList;
    }

    @Override
    public String toString() {
        return "GanadorLicitacionSalidaDto{" +
                "ejecucion=" + ejecucion +
                ", contadorResult=" + contadorResult +
                ", contadorDetalleResult=" + contadorDetalleResult +
                ", resultList=" + resultList +
                ", resultDetalleList=" + resultDetalleList +
                ", resultDetalleCuadroList=" + resultDetalleCuadroList +
                ", graficaPrecioFinalMonedaList=" + graficaPrecioFinalMonedaList +
                ", graficaPorcentajeObtenidoList=" + graficaPorcentajeObtenidoList +
                '}';
    }
}
