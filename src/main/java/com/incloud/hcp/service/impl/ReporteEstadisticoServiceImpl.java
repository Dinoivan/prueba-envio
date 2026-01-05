package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.estadistico.*;
import com.incloud.hcp.enums.EstadoLicitacionEnum;
import com.incloud.hcp.myibatis.mapper.ReporteEstadisticoMapper;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.service.ReporteEstadisticoService;
import com.incloud.hcp.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ReporteEstadisticoServiceImpl implements ReporteEstadisticoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProveedorRepository proveedorRepository;


    @Autowired
    private ReporteEstadisticoMapper reporteEstadisticoMapper;

    public ReporteEstadisticoAdjudicacionSalidaDto reporteAdjudicacion(String ruc) throws Exception {
        ReporteEstadisticoAdjudicacionSalidaDto result = new ReporteEstadisticoAdjudicacionSalidaDto();
        Proveedor proveedor = this.proveedorRepository.getProveedorByRuc(ruc);
        if (!Optional.ofNullable(proveedor).isPresent()) {
            throw new Exception("No se encontró Proveedor con RUC: " + ruc);
        }
        result.setProveedor(proveedor);

        Date fechaActual = DateUtils.obtenerFechaActual();
        Integer anno = DateUtils.getYear(fechaActual);
        ReporteEstadisticoAdjudicacionEntradaDto bean = new ReporteEstadisticoAdjudicacionEntradaDto();
        bean.setRuc(ruc);
        bean.setAnno(anno);
        List<ReporteEstadisticoAdjudicacionDto> data =this.reporteEstadisticoMapper.reporteEstadisticoAdjudicacion(bean);
        result.setData(data);
        return result;

    }

    public ReporteEstadisticoParticipacionSalidaDto reporteParticipacion(String ruc) throws Exception {
        ReporteEstadisticoParticipacionSalidaDto result = new ReporteEstadisticoParticipacionSalidaDto();
        Proveedor proveedor = this.proveedorRepository.getProveedorByRuc(ruc);
        if (!Optional.ofNullable(proveedor).isPresent()) {
            throw new Exception("No se encontró Proveedor con RUC: " + ruc);
        }
        result.setProveedor(proveedor);

        Date fechaActual = DateUtils.obtenerFechaActual();
        Integer anno = DateUtils.getYear(fechaActual);
        ReporteEstadisticoParticipacionEntradaDto bean = new ReporteEstadisticoParticipacionEntradaDto();
        bean.setRuc(ruc);
        bean.setAnno(anno);

        List<ReporteEstadisticoParticipacionDto> data =this.reporteEstadisticoMapper.reporteEstadisticoParticipacion(bean);
        result.setData(data);

        return result;
    }

    public ReporteStatusPeticionOfertaSalidaDto reporteStatusPeticionOferta(ReporteStatusPeticionOfertaEntradaDto bean) throws Exception {
        ReporteStatusPeticionOfertaSalidaDto result = new ReporteStatusPeticionOfertaSalidaDto();
        String ruc = bean.getRuc();
        if (StringUtils.isBlank(ruc)) {
            throw new Exception("Debe ingresar RUC");
        }
        Proveedor proveedor = this.proveedorRepository.getProveedorByRuc(ruc);
        if (!Optional.ofNullable(proveedor).isPresent()) {
            throw new Exception("No se encontró Proveedor con RUC: " + ruc);
        }
        if (!Optional.ofNullable(bean.getFechaInicio()).isPresent()) {
            throw new Exception("Debe ingresar Fecha de Inicio");
        }
        if (!Optional.ofNullable(bean.getFechaFin()).isPresent()) {
            throw new Exception("Debe ingresar Fecha Final");
        }
        if  (bean.getFechaFin().before(bean.getFechaInicio())) {
            throw new Exception("Fecha Final debe ser mayor igual a la Fecha de Inicio");
        }

//        Date fechaComprobacion = DateUtils.sumarRestarDias(bean.getFechaInicio(), 20);
        Date fechaFin = DateUtils.sumarRestarDias(bean.getFechaFin(), 1);
        bean.setFechaFin(fechaFin);
//        if  (fechaComprobacion.before(fechaFin)) {
//            throw new Exception("El intervalo máximo de dias entre ambas fechas debe ser de 20 dias");
//        }

        ReporteStatusPeticionOfertaIntermedioDto beanIntermedio = new ReporteStatusPeticionOfertaIntermedioDto();
        beanIntermedio.setRuc(ruc);
        beanIntermedio.setFechaInicio(bean.getFechaInicio());
        beanIntermedio.setFechaFin(bean.getFechaFin());
        beanIntermedio.setRazonSocial(null);
        beanIntermedio.setEstadoLicitacion(null);
        beanIntermedio.setEstadoNotLicitacion(null);

        logger.error("Ingresando reporteStatusPeticionOferta 01 todos:" + beanIntermedio.toString());
        List<Licitacion> licitacionListTodos = this.reporteEstadisticoMapper.reporteStatusPeticionOferta(beanIntermedio);
        logger.error("Ingresando reporteStatusPeticionOferta 01 todos size:" + licitacionListTodos.size());
        result.setLicitacionListTodos(licitacionListTodos);

        beanIntermedio.setEstadoLicitacion(EstadoLicitacionEnum.ADJUDICADA.getCodigo());
        logger.error("Ingresando reporteStatusPeticionOferta 02 adju:" + beanIntermedio.toString());
        List<Licitacion> licitacionListAdjudicadas = this.reporteEstadisticoMapper.reporteStatusPeticionOferta(beanIntermedio);
        logger.error("Ingresando reporteStatusPeticionOferta 02 adju size :" + licitacionListAdjudicadas.size());
        result.setLicitacionListAdjudicadas(licitacionListAdjudicadas);

        ReporteStatusPeticionOfertaIntermedioDto beanIntermedio02 = new ReporteStatusPeticionOfertaIntermedioDto();
        beanIntermedio02.setRuc(ruc);
        beanIntermedio02.setFechaInicio(bean.getFechaInicio());
        beanIntermedio02.setFechaFin(bean.getFechaFin());
        beanIntermedio02.setRazonSocial("");
        beanIntermedio02.setEstadoLicitacion("");
        beanIntermedio02.setEstadoNotLicitacion(EstadoLicitacionEnum.ADJUDICADA.getCodigo());
        logger.error("Ingresando reporteStatusPeticionOferta 03 en proc:" + beanIntermedio02.toString());
        List<Licitacion> licitacionListEnProceso = this.reporteEstadisticoMapper.reporteStatusPeticionOferta(beanIntermedio02);
        result.setLicitacionListEnProceso(licitacionListEnProceso);
        logger.error("Ingresando reporteStatusPeticionOferta 03 en proc size :" + licitacionListEnProceso.size());
        return result;
    }


    public ReporteStatusPeticionOfertaSalidaDto reporteStatusPeticionOfertaTodosPaginado(
            ReporteStatusPeticionOfertaEntradaPaginadoDto bean) throws Exception {
        ReporteStatusPeticionOfertaSalidaDto result = new ReporteStatusPeticionOfertaSalidaDto();
        String ruc = bean.getRuc();
        if (StringUtils.isBlank(ruc)) {
            throw new Exception("Debe ingresar RUC");
        }
        Proveedor proveedor = this.proveedorRepository.getProveedorByRuc(ruc);
        if (!Optional.ofNullable(proveedor).isPresent()) {
            throw new Exception("No se encontró Proveedor con RUC: " + ruc);
        }
        if (!Optional.ofNullable(bean.getFechaInicio()).isPresent()) {
            throw new Exception("Debe ingresar Fecha de Inicio");
        }
        if (!Optional.ofNullable(bean.getFechaFin()).isPresent()) {
            throw new Exception("Debe ingresar Fecha Final");
        }
        if  (bean.getFechaFin().before(bean.getFechaInicio())) {
            throw new Exception("Fecha Final debe ser mayor igual a la Fecha de Inicio");
        }

        Date fechaFin = DateUtils.sumarRestarDias(bean.getFechaFin(), 1);
        ReporteStatusPeticionOfertaIntermedioDto beanIntermedio = new ReporteStatusPeticionOfertaIntermedioDto();
        beanIntermedio.setRuc(ruc);
        beanIntermedio.setFechaInicio(bean.getFechaInicio());
        beanIntermedio.setFechaFin(fechaFin);
        beanIntermedio.setRazonSocial("");
        beanIntermedio.setEstadoLicitacion("");
        beanIntermedio.setEstadoNotLicitacion("");
        Integer nroRegistros = bean.getNroRegistros();
        Integer paginaMostrar = bean.getPaginaMostrar();
        paginaMostrar = new Integer((paginaMostrar.intValue() - 1) * nroRegistros);

        beanIntermedio.setNroRegistros(nroRegistros);
        beanIntermedio.setPaginaMostrar(paginaMostrar);
        logger.error("Ingresando reporteStatusPeticionOfertaPaginado 01 :" + beanIntermedio.toString());
        List<Licitacion> licitacionListTodos = this.reporteEstadisticoMapper.reporteStatusPeticionOfertaPaginado(beanIntermedio);
        result.setLicitacionListTodos(licitacionListTodos);
        return result;
    }

    public ReporteStatusPeticionOfertaSalidaDto reporteStatusPeticionOfertaAdjuPaginado(
            ReporteStatusPeticionOfertaEntradaPaginadoDto bean) throws Exception {
        ReporteStatusPeticionOfertaSalidaDto result = new ReporteStatusPeticionOfertaSalidaDto();
        String ruc = bean.getRuc();
        if (StringUtils.isBlank(ruc)) {
            throw new Exception("Debe ingresar RUC");
        }
        Proveedor proveedor = this.proveedorRepository.getProveedorByRuc(ruc);
        if (!Optional.ofNullable(proveedor).isPresent()) {
            throw new Exception("No se encontró Proveedor con RUC: " + ruc);
        }
        if (!Optional.ofNullable(bean.getFechaInicio()).isPresent()) {
            throw new Exception("Debe ingresar Fecha de Inicio");
        }
        if (!Optional.ofNullable(bean.getFechaFin()).isPresent()) {
            throw new Exception("Debe ingresar Fecha Final");
        }
        if  (bean.getFechaFin().before(bean.getFechaInicio())) {
            throw new Exception("Fecha Final debe ser mayor igual a la Fecha de Inicio");
        }

        Date fechaFin = DateUtils.sumarRestarDias(bean.getFechaFin(), 1);
        ReporteStatusPeticionOfertaIntermedioDto beanIntermedio = new ReporteStatusPeticionOfertaIntermedioDto();
        beanIntermedio.setRuc(ruc);
        beanIntermedio.setFechaInicio(bean.getFechaInicio());
        beanIntermedio.setFechaFin(fechaFin);
        beanIntermedio.setRazonSocial("");
        beanIntermedio.setEstadoLicitacion("");
        beanIntermedio.setEstadoNotLicitacion("");
        Integer nroRegistros = bean.getNroRegistros();
        Integer paginaMostrar = bean.getPaginaMostrar();
        paginaMostrar = new Integer((paginaMostrar.intValue() - 1) * nroRegistros);
        beanIntermedio.setEstadoLicitacion(EstadoLicitacionEnum.ADJUDICADA.getCodigo());

        beanIntermedio.setNroRegistros(nroRegistros);
        beanIntermedio.setPaginaMostrar(paginaMostrar);
        List<Licitacion> licitacionListAdju = this.reporteEstadisticoMapper.reporteStatusPeticionOfertaPaginado(beanIntermedio);
        result.setLicitacionListAdjudicadas(licitacionListAdju);
        return result;
    }

    public ReporteStatusPeticionOfertaSalidaDto reporteStatusPeticionOfertaEnProcPaginado(
            ReporteStatusPeticionOfertaEntradaPaginadoDto bean) throws Exception {
        ReporteStatusPeticionOfertaSalidaDto result = new ReporteStatusPeticionOfertaSalidaDto();
        String ruc = bean.getRuc();
        if (StringUtils.isBlank(ruc)) {
            throw new Exception("Debe ingresar RUC");
        }
        Proveedor proveedor = this.proveedorRepository.getProveedorByRuc(ruc);
        if (!Optional.ofNullable(proveedor).isPresent()) {
            throw new Exception("No se encontró Proveedor con RUC: " + ruc);
        }
        if (!Optional.ofNullable(bean.getFechaInicio()).isPresent()) {
            throw new Exception("Debe ingresar Fecha de Inicio");
        }
        if (!Optional.ofNullable(bean.getFechaFin()).isPresent()) {
            throw new Exception("Debe ingresar Fecha Final");
        }
        if  (bean.getFechaFin().before(bean.getFechaInicio())) {
            throw new Exception("Fecha Final debe ser mayor igual a la Fecha de Inicio");
        }

        Date fechaFin = DateUtils.sumarRestarDias(bean.getFechaFin(), 1);
        ReporteStatusPeticionOfertaIntermedioDto beanIntermedio = new ReporteStatusPeticionOfertaIntermedioDto();
        beanIntermedio.setRuc(ruc);
        beanIntermedio.setFechaInicio(bean.getFechaInicio());
        beanIntermedio.setFechaFin(fechaFin);
        beanIntermedio.setRazonSocial("");
        beanIntermedio.setEstadoLicitacion("");
        beanIntermedio.setEstadoNotLicitacion("");
        Integer nroRegistros = bean.getNroRegistros();
        Integer paginaMostrar = bean.getPaginaMostrar();
        paginaMostrar = new Integer((paginaMostrar.intValue() - 1) * nroRegistros);
        beanIntermedio.setEstadoNotLicitacion(EstadoLicitacionEnum.ADJUDICADA.getCodigo());

        beanIntermedio.setNroRegistros(nroRegistros);
        beanIntermedio.setPaginaMostrar(paginaMostrar);
        List<Licitacion> licitacionListEnProc = this.reporteEstadisticoMapper.reporteStatusPeticionOfertaPaginado(beanIntermedio);
        result.setLicitacionListEnProceso(licitacionListEnProc);
        return result;
    }




}
