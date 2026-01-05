package com.incloud.hcp.service.impl;

import com.incloud.hcp.config.excel.ExcelDefault;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.dto.*;
import com.incloud.hcp.jco.tipoCambio.service.JCOTipoCambioService;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.service.LicitacionProveedorTipoCuestionarioService;
import com.incloud.hcp.util.Constant;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import com.incloud.hcp.util.constant.CotizacionConstant;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by USER on 22/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class LicitacionProveedorTipoCuestionarioServiceImpl implements LicitacionProveedorTipoCuestionarioService {

    private final Logger log = LoggerFactory.getLogger(LicitacionProveedorTipoCuestionarioServiceImpl.class.getSimpleName());
    protected final String CONFIG_TITLE_DETALLE_CUADRO_AGRUPADO = "com/incloud/hcp/excel/GanadorDetalleCuadroTituloExcel.xml";
    protected final String CONFIG_TITLE_DETALLE_CUADRO = "com/incloud/hcp/excel/GanadorDetalleCuadroExcel.xml";

    @Autowired
    private LicitacionRepository licitacionRepository;

    @Autowired
    private LicitacionDetalleRepository licitacionDetalleRepository;

    @Autowired
    private LicitacionGrupoCondicionLicRepository licitacionGrupoCondicionLicRepository;

    @Autowired
    private LicitacionProveedorRepository licitacionProveedorRepository;

    @Autowired
    private MonedaRepository monedaRepository;

    @Autowired
    private TasaCambioRepository tasaCambioRepository;

    @Autowired
    private CotizacionCampoRespuestaRepository cotizacionCampoRespuestaRepository;

    @Autowired
    private CotizacionDetalleRepository cotizacionDetalleRepository;

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private LicitacionProveedorTipoCuestionarioRepository licitacionProveedorTipoCuestionarioRepository;

    @Autowired
    private LicitacionProveedorDetalleEvaluacionRepository licitacionProveedorDetalleEvaluacionRepository;

    @Autowired
    private JCOTipoCambioService jcoTipoCambioService;


    @Transactional(readOnly = true)
    public List<GraficoMiniSalidaDto> graficaPrecioFinalMoneda(Integer idLicitacion) throws Exception {
        List<GraficoMiniSalidaDto> graficoMiniSalidaDtoList = new ArrayList<GraficoMiniSalidaDto>();
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        if (!Optional.ofNullable(licitacion).isPresent()) {
            return graficoMiniSalidaDtoList;
        }
        List<LicitacionProveedorTipoCuestionario> listaOrdenada =
                this.licitacionProveedorTipoCuestionarioRepository.findByLicitacionAndSort(
                        licitacion,
                        Sort.by(
                                new Sort.Order(Sort.Direction.DESC, "porcentajeTotalObtenido")));
        for (LicitacionProveedorTipoCuestionario bean : listaOrdenada) {
            GraficoMiniSalidaDto graficoMiniSalidaDto = new GraficoMiniSalidaDto();
            graficoMiniSalidaDto.setDescripcion(bean.getProveedor().getRazonSocial());
            graficoMiniSalidaDto.setValor(bean.getPrecioFinalMoneda());
            graficoMiniSalidaDtoList.add(graficoMiniSalidaDto);
        }
        return graficoMiniSalidaDtoList;
    }

    @Transactional(readOnly = true)
    public List<GraficoMiniSalidaDto> graficaPorcentajeObtenido(Integer idLicitacion) throws Exception {
        List<GraficoMiniSalidaDto> graficoMiniSalidaDtoList = new ArrayList<GraficoMiniSalidaDto>();
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        if (!Optional.ofNullable(licitacion).isPresent()) {
            return graficoMiniSalidaDtoList;
        }
        List<LicitacionProveedorTipoCuestionario> listaOrdenada =
                this.licitacionProveedorTipoCuestionarioRepository.findByLicitacionAndSort(
                        licitacion,
                        Sort.by(
                                new Sort.Order(Sort.Direction.DESC, "porcentajeTotalObtenido")));
        for (LicitacionProveedorTipoCuestionario bean : listaOrdenada) {
            GraficoMiniSalidaDto graficoMiniSalidaDto = new GraficoMiniSalidaDto();
            graficoMiniSalidaDto.setDescripcion(bean.getProveedor().getRazonSocial());
            graficoMiniSalidaDto.setValor(bean.getPorcentajeTotalObtenido());
            graficoMiniSalidaDtoList.add(graficoMiniSalidaDto);
        }
        return graficoMiniSalidaDtoList;
    }

    @Transactional(readOnly = true)
    public GanadorLicitacionEntradaDto obtenerEvaluacionTecnica(Integer idLicitacion) throws Exception {
        GanadorLicitacionEntradaDto beanResult = new GanadorLicitacionEntradaDto();
        List<GanadorLicitacionEvaluacionEntradaDto> listaEvaluacionTecnica = new ArrayList<GanadorLicitacionEvaluacionEntradaDto>();
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        if (!Optional.ofNullable(licitacion).isPresent()) {
            return beanResult;
        }

        beanResult.setIdLicitacion(licitacion.getIdLicitacion());
        List<LicitacionProveedor> licitacionProveedorList = this.licitacionProveedorRepository.
                findByLicitacionSiParticipa(licitacion, Constant.S);
        for (LicitacionProveedor bean: licitacionProveedorList) {
            GanadorLicitacionEvaluacionEntradaDto beanEvaluacion = new GanadorLicitacionEvaluacionEntradaDto();
            beanEvaluacion.setIdProveedor(bean.getProveedor().getIdProveedor());
            beanEvaluacion.setProveedor(bean.getProveedor());
            beanEvaluacion.setPuntajeEvaluacionTecnica(new BigDecimal(0.00));
            listaEvaluacionTecnica.add(beanEvaluacion);
        }
        beanResult.setListaEvaluacionTecnica(listaEvaluacionTecnica);
        return beanResult;
    }

    @Transactional(readOnly = true)
    public GanadorLicitacionSalidaTipoLicitacionDto obtenerTipoLicitacion(Integer idLicitacion) throws Exception {
        GanadorLicitacionSalidaTipoLicitacionDto beanResult = new GanadorLicitacionSalidaTipoLicitacionDto();
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        if (!Optional.ofNullable(licitacion).isPresent()) {
            return beanResult;
        }
        List<LicitacionGrupoCondicionLic> licitacionGrupoCondicionLicList =
                this.licitacionGrupoCondicionLicRepository.findByLicitacion(licitacion);

        TipoLicitacion tipoLicitacion = licitacionGrupoCondicionLicList.get(0).getTipoLicitacion1();
        TipoLicitacion tipoCuestionario = licitacionGrupoCondicionLicList.get(0).getTipoLicitacion2();
        beanResult.setTipoLicitacion(tipoLicitacion);
        beanResult.setTipoCuestionario(tipoCuestionario);
        beanResult.setMonedaLicitacion(licitacion.getMoneda());
        beanResult.setPesoCondicion(tipoCuestionario.getPesoCondicion());
        beanResult.setPesoMoneda(tipoCuestionario.getPesoMoneda());
        beanResult.setPesoEvaluacionTecnica(tipoCuestionario.getPesoEvaluacionTecnica());
        return beanResult;
    }

    @Transactional(readOnly = true)
    public List<LicitacionProveedorTipoCuestionario> obtenerLicitacionProveedorTipoCuestionario(Integer idLicitacion) throws Exception {
        List<LicitacionProveedorTipoCuestionario> licitacionProveedorTipoCuestionarioList =
                new ArrayList<LicitacionProveedorTipoCuestionario>();
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        if (!Optional.ofNullable(licitacion).isPresent()) {
            return licitacionProveedorTipoCuestionarioList;
        }
        List<LicitacionProveedorTipoCuestionario> listaOrdenada =
                this.licitacionProveedorTipoCuestionarioRepository.findByLicitacionAndSort(
                        licitacion,
                        Sort.by(
                                new Sort.Order(Sort.Direction.DESC, "porcentajeTotalObtenido")));
        return listaOrdenada;
    }

    @Transactional(readOnly = true)
    public List<LicitacionProveedorDetalleEvaluacionDto> obtenerLicitacionProveedorDetalleCuadroEvaluacion(Integer idLicitacion) throws Exception {
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        if (!Optional.ofNullable(licitacion).isPresent()) {
            return null;
        }
        List<LicitacionProveedorDetalleEvaluacion> listaDetalleFinal =
                this.licitacionProveedorDetalleEvaluacionRepository.findByIdLicitacionOrdenado(idLicitacion);
        if (!Optional.ofNullable(listaDetalleFinal).isPresent()) {
            return null;
        }
        List<LicitacionProveedorDetalleEvaluacionDto> listaDetalleCuadro = this.obtenerGanadorDetalleCuadro(
                listaDetalleFinal,
                licitacion);
        return listaDetalleCuadro;
    }

    /******************************/
    /* Obtencion Ganador Detalle  */
    /******************************/

    public GanadorLicitacionSalidaDto obtenerGanadorDetalleTipoCuestionario(GanadorLicitacionEntradaDto bean) throws Exception {

        GanadorLicitacionSalidaDto result = this.obtenerGanadorTipoCuestionario(bean);
        List<LicitacionProveedorTipoCuestionario> listaCabecera = result.getResultList();
        Licitacion licitacion = this.licitacionRepository.getOne(bean.getIdLicitacion());
        if (!Optional.ofNullable(licitacion).isPresent()) {
            result.setEjecucion(false);
            return result;
        }

        List<LicitacionDetalle> licitacionDetalleList = this.licitacionDetalleRepository.
                findByIdLicitacionOrdenado(licitacion.getIdLicitacion());
        if (licitacionDetalleList == null || licitacionDetalleList.size() <= 0) {
            result.setEjecucion(false);
            return result;
        }

        /* Obteniendo maximo dias de Condicion de Pago */
        List<LicitacionProveedor> licitacionProveedorList = this.licitacionProveedorRepository.
                findByLicitacionSiParticipa(licitacion, Constant.S);
        if (licitacionProveedorList == null || licitacionProveedorList.size() <= 0) {
            result.setEjecucion(false);
            return null;
        }

        Integer diasMaximoCondicionPago = new Integer(0);
        for (LicitacionProveedor beanLicitacionProveedor: licitacionProveedorList) {
            Integer diasCondicionPago = beanLicitacionProveedor.getProveedor().getCondicionPago().getDiasPago();
            if (diasCondicionPago.intValue() > diasMaximoCondicionPago.intValue()) {
                diasMaximoCondicionPago = diasCondicionPago;
            }
        }

        /* Recorriendo los proveedores */
        for (LicitacionProveedorTipoCuestionario beanCabecera : listaCabecera) {
            Proveedor proveedor = beanCabecera.getProveedor();
            List<LicitacionProveedorDetalleEvaluacion> listaDetalle = new ArrayList<LicitacionProveedorDetalleEvaluacion>();

            /* Obteniendo los detalles */
            for(LicitacionDetalle licitacionDetalle : licitacionDetalleList) {

                CotizacionDetalle cotizacionDetalle = this.cotizacionDetalleRepository.
                        getByCotizacionByProveedorLicitacionDetalle(
                                proveedor.getIdProveedor(),
                                licitacion.getIdLicitacion(),
                                licitacionDetalle.getIdLicitacionDetalle()
                        );
                if (!Optional.ofNullable(cotizacionDetalle).isPresent()) {
                    continue;
                }

                LicitacionProveedorDetalleEvaluacion beanDetalle = new LicitacionProveedorDetalleEvaluacion();
                BeanUtils.copyProperties(beanCabecera, beanDetalle);
                beanDetalle.setIdLicitacion(beanCabecera.getLicitacion());
                beanDetalle.setIdProveedor(beanCabecera.getProveedor());
                beanDetalle.setIdTipoLicitacion(beanCabecera.getTipoLicitacion());
                beanDetalle.setIdTipoCuestionario(beanCabecera.getTipoCuestionario());
                beanDetalle.setIdMoneda(beanCabecera.getMoneda());
                beanDetalle.setIdCondicionPago(beanCabecera.getCondicionPago());
                beanDetalle.setIdLicitacionDetalle(licitacionDetalle);
                beanDetalle.setIdCotizacionDetalle(cotizacionDetalle);
                beanDetalle.setCantidadTotalLicitacion(licitacionDetalle.getCantidadSolicitada());
                beanDetalle.setCantidadTotalCotizacion(cotizacionDetalle.getCantidadCotizada());

                LicitacionProveedorDetalleEvaluacion licitacionProveedorDetalleEvaluacionBD =
                        this.licitacionProveedorDetalleEvaluacionRepository.getByIdLicitacionAndIdProveedorAndIdLicitacionDetalle(
                                licitacion,
                                proveedor,
                                licitacionDetalle
                        );
                if (Optional.ofNullable(licitacionProveedorDetalleEvaluacionBD).isPresent()) {
                    beanDetalle.setId(licitacionProveedorDetalleEvaluacionBD.getId());
                }

                BigDecimal precioUnitario = new BigDecimal(
                        cotizacionDetalle.getPrecioUnitario().floatValue() *
                                beanCabecera.getTasaCambio().floatValue()
                ).setScale(2, BigDecimal.ROUND_HALF_UP);
//                log.error("CBAZALAR Evaluacion DETALLE precioUnitario 01: " + cotizacionDetalle.getPrecioUnitario());
//                log.error("CBAZALAR Evaluacion DETALLE Tasa Cambio: " + beanCabecera.getTasaCambio());
//                log.error("CBAZALAR Evaluacion DETALLE precioUnitario Final: " + precioUnitario);

                BigDecimal precioSuma = new BigDecimal(
                        cotizacionDetalle.getPrecioUnitario().floatValue() *
                                cotizacionDetalle.getCantidadCotizada().floatValue() *
                                beanCabecera.getTasaCambio().floatValue()
                ).setScale(2, BigDecimal.ROUND_HALF_UP);

                beanDetalle.setPrecioUnitario(precioUnitario);
                beanDetalle.setPrecioSuma(precioSuma);
                beanDetalle.setTasaInteresMoneda(new BigDecimal(0.00));
                beanDetalle.setCostoFinancieroMoneda(new BigDecimal(0.00));
                beanDetalle.setPrecioFinalMoneda(new BigDecimal(0.00));
                beanDetalle.setPrecioFinalMinimoMoneda(new BigDecimal(0.00));
                beanDetalle.setPorcentajePrecioObtenido(new BigDecimal(0.00));
                beanDetalle.setPorcentajeTotalObtenido(new BigDecimal(0.00));
                if(precioSuma.floatValue() > 0.00 && cotizacionDetalle.getCantidadCotizada().floatValue() > 0.00) {
                    BigDecimal tasaInteresMoneda = new BigDecimal(0.00);
                    BigDecimal baseExponencial = new BigDecimal( 1 + beanDetalle.getTasaMoneda().floatValue() / 100);
                    BigDecimal exponente = new BigDecimal( 1 / beanDetalle.getFactorDenominadorTasa().floatValue());
                    Double exponencial = Math.pow(baseExponencial.doubleValue(), exponente.doubleValue()) - 1;
                    if (beanDetalle.getIdCondicionPago().getDiasPago().intValue() != diasMaximoCondicionPago.intValue()) {
                        Integer intervalo = diasMaximoCondicionPago.intValue() - beanDetalle.getIdCondicionPago().getDiasPago().intValue();
                        tasaInteresMoneda = new BigDecimal(exponencial.floatValue() * intervalo.intValue() * 100)
                                .setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    beanDetalle.setTasaInteresMoneda(tasaInteresMoneda);
                    BigDecimal costoFinanciero = new BigDecimal(tasaInteresMoneda.floatValue() * precioSuma.floatValue() / 100)
                            .setScale(2, BigDecimal.ROUND_HALF_UP);
                    beanDetalle.setCostoFinancieroMoneda(costoFinanciero);
                    beanDetalle.setPrecioFinalMoneda(
                            new BigDecimal(costoFinanciero.floatValue() + precioSuma.floatValue())
                                    .setScale(2, BigDecimal.ROUND_HALF_UP));

                }
                listaDetalle.add(beanDetalle);
            }

            if (listaDetalle != null && listaDetalle.size() > 0) {
                //log.error("obtenerGanadorDetalleTipoCuestionario size: " + listaDetalle.size());
                this.licitacionProveedorDetalleEvaluacionRepository.saveAll(listaDetalle);
            }
        }


        /* Recorriendo ahora por el detalle para obtener precio minimo */
        for(LicitacionDetalle licitacionDetalle : licitacionDetalleList) {

            /* Obteniendo precio minimo */
            BigDecimal precioMinimoFinalIni = new BigDecimal(99999999999999.99);
            BigDecimal precioMinimoFinal = new BigDecimal(99999999999999.99);
            for (LicitacionProveedorTipoCuestionario beanCabecera : listaCabecera) {
                Proveedor proveedor = beanCabecera.getProveedor();
                LicitacionProveedorDetalleEvaluacion licitacionProveedorDetalleEvaluacion =
                        this.licitacionProveedorDetalleEvaluacionRepository.
                                getByIdLicitacionAndIdProveedorAndIdLicitacionDetalle(
                                        licitacion,
                                        proveedor,
                                        licitacionDetalle
                                );
                if (Optional.ofNullable(licitacionProveedorDetalleEvaluacion).isPresent()) {
                    BigDecimal precioFinal = licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda();
                    if (precioFinal.floatValue() < precioMinimoFinal.floatValue()) {
                        precioMinimoFinal = precioFinal;
                    }
                }
            }
            if (precioMinimoFinalIni.floatValue() == precioMinimoFinal.floatValue()) {
                precioMinimoFinal = new BigDecimal(0.00);
            }

            /* Grabando precio minimo */
            for (LicitacionProveedorTipoCuestionario beanCabecera : listaCabecera) {
                Proveedor proveedor = beanCabecera.getProveedor();
                LicitacionProveedorDetalleEvaluacion licitacionProveedorDetalleEvaluacion =
                        this.licitacionProveedorDetalleEvaluacionRepository.
                                getByIdLicitacionAndIdProveedorAndIdLicitacionDetalle(
                                        licitacion,
                                        proveedor,
                                        licitacionDetalle
                                );
                if (Optional.ofNullable(licitacionProveedorDetalleEvaluacion).isPresent()) {
                    licitacionProveedorDetalleEvaluacion.setPrecioFinalMinimoMoneda(precioMinimoFinal);
                    this.licitacionProveedorDetalleEvaluacionRepository.save(licitacionProveedorDetalleEvaluacion);
                }
            }
        }

        /* Recorriendo los proveedores */
        for (LicitacionProveedorTipoCuestionario beanCabecera : listaCabecera) {
            Proveedor proveedor = beanCabecera.getProveedor();
            List<LicitacionProveedorDetalleEvaluacion> listaDetalle  =
                    this.licitacionProveedorDetalleEvaluacionRepository.findByIdLicitacionAndIdProveedor(
                            licitacion,
                            proveedor
                    );

            /* Obteniendo valores secundarios del Detalle */
            if (listaDetalle != null && listaDetalle.size() > 0) {

                for (LicitacionProveedorDetalleEvaluacion beanEvaluacionDetalle : listaDetalle) {
                    BigDecimal precioMinimoFinal = beanEvaluacionDetalle.getPrecioFinalMinimoMoneda();
                    BigDecimal precioFinal = beanEvaluacionDetalle.getPrecioFinalMoneda();
                    BigDecimal porcentaje = new BigDecimal(0.00);

                    BigDecimal factorCantidad = new BigDecimal(0.00);
                    if (precioFinal.floatValue() != 0.00) {
                        porcentaje = new BigDecimal(precioMinimoFinal.floatValue() / precioFinal.floatValue());
                        porcentaje = new BigDecimal(porcentaje.floatValue() * beanEvaluacionDetalle.getPesoMoneda().floatValue());

                        factorCantidad = new BigDecimal(beanEvaluacionDetalle.getCantidadTotalCotizacion().floatValue() / beanEvaluacionDetalle.getCantidadTotalLicitacion().floatValue());
                        porcentaje = new BigDecimal(porcentaje.floatValue() * factorCantidad.floatValue())
                                .setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    beanEvaluacionDetalle.setPorcentajePrecioObtenido(porcentaje);
                    beanEvaluacionDetalle.setPorcentajeTotalObtenido(
                            new BigDecimal(
                                    beanEvaluacionDetalle.getPorcentajeCondicionObtenido().floatValue() +
                                            beanEvaluacionDetalle.getPorcentajePrecioObtenido().floatValue() +
                                            beanEvaluacionDetalle.getPorcentajeEvaluacionTecnicaObtenido().floatValue()
                            ).setScale(2, BigDecimal.ROUND_HALF_UP)
                    );
                    log.error("obtenerGanadorDetalleTipoCuestionario beanEvaluacionDetalle: " + beanEvaluacionDetalle.toString());
                    //this.licitacionProveedorDetalleEvaluacionRepository.save(beanEvaluacionDetalle);
                }

                //log.error("obtenerGanadorDetalleTipoCuestionario size: " + listaDetalle.size());
                this.licitacionProveedorDetalleEvaluacionRepository.saveAll(listaDetalle);

            }
        }


        /* Obteniendo data de la BD */
        List<LicitacionProveedorDetalleEvaluacion> listaDetalleFinal =
                this.licitacionProveedorDetalleEvaluacionRepository.findByIdLicitacionOrdenado(licitacion.getIdLicitacion());
        result.setResultDetalleList(listaDetalleFinal);
        if (listaDetalleFinal != null && listaDetalleFinal.size() > 0) {
            result.setContadorDetalleResult(listaDetalleFinal.size());
        }

        /* Obteniendo el cuadro Detalle */
        List<LicitacionProveedorDetalleEvaluacionDto> listaDetalleCuadro = this.obtenerGanadorDetalleCuadro(
                result.getResultDetalleList(),
                licitacion);
        result.setResultDetalleCuadroList(listaDetalleCuadro);
        return result;
    }

    private List<LicitacionProveedorDetalleEvaluacionDto> obtenerGanadorDetalleCuadro(
            List<LicitacionProveedorDetalleEvaluacion> resultDetalleList,
            Licitacion licitacion) throws Exception {
        List<LicitacionProveedorDetalleEvaluacionDto> listaDetalleCuadro = new ArrayList<LicitacionProveedorDetalleEvaluacionDto>();
        if (resultDetalleList == null || resultDetalleList.size() <= 0) {
            return listaDetalleCuadro;
        }

        /* Obteniendo proveedores participantes */
        List<Cotizacion> cotizacionList = this.cotizacionRepository.findByLicitacionAndSort(licitacion, Sort.by("proveedor.razonSocial"));
        List<Proveedor> proveedorList = new ArrayList<Proveedor>();
        for (Cotizacion cotizacion : cotizacionList) {
            String estadoCotizacion = cotizacion.getEstadoCotizacion();
            if (estadoCotizacion.equals(CotizacionConstant.ESTADO_NO_PARTICIPAR)) {
                continue;
            }
            Proveedor proveedor = cotizacion.getProveedor();
            proveedorList.add(proveedor);
        }


        /* Generando cuadro con los proveedores participantes */
        List<LicitacionDetalle> licitacionDetalleList = this.licitacionDetalleRepository.findByLicitacionOrderByDescripcion(licitacion);
        for (LicitacionDetalle beanDetalle : licitacionDetalleList) {
            LicitacionProveedorDetalleEvaluacionDto licitacionProveedorDetalleEvaluacionDto = new LicitacionProveedorDetalleEvaluacionDto();
            try {
                licitacionProveedorDetalleEvaluacionDto.setBienServicio(beanDetalle.getBienServicio());
                String codigoSap = beanDetalle.getBienServicio().getCodigoSap();
                codigoSap = codigoSap.substring(13, codigoSap.length());
                licitacionProveedorDetalleEvaluacionDto.setNombreBienServicio(
                        codigoSap + " - " + beanDetalle.getBienServicio().getDescripcion()
                );
                licitacionProveedorDetalleEvaluacionDto.setNombreBienServicioSap(
                        codigoSap + " - " + beanDetalle.getBienServicio().getDescripcion()
                );
            }
            catch(Exception e) {
                licitacionProveedorDetalleEvaluacionDto.setBienServicio(null);
                licitacionProveedorDetalleEvaluacionDto.setNombreBienServicio(beanDetalle.getDescripcion());
                licitacionProveedorDetalleEvaluacionDto.setNombreBienServicioSap(beanDetalle.getDescripcion());
            }
            licitacionProveedorDetalleEvaluacionDto.setUnidadMedida(beanDetalle.getUnidadMedida());
            licitacionProveedorDetalleEvaluacionDto.setLicitacionDetalle(beanDetalle);
            licitacionProveedorDetalleEvaluacionDto.setCantidadSolicitada(beanDetalle.getCantidadSolicitada());

            licitacionProveedorDetalleEvaluacionDto.setVisible01(false);
            licitacionProveedorDetalleEvaluacionDto.setVisible02(false);
            licitacionProveedorDetalleEvaluacionDto.setVisible03(false);
            licitacionProveedorDetalleEvaluacionDto.setVisible04(false);
            licitacionProveedorDetalleEvaluacionDto.setVisible05(false);
            licitacionProveedorDetalleEvaluacionDto.setVisible06(false);
            licitacionProveedorDetalleEvaluacionDto.setVisible07(false);
            licitacionProveedorDetalleEvaluacionDto.setVisible08(false);
            licitacionProveedorDetalleEvaluacionDto.setVisible09(false);
            licitacionProveedorDetalleEvaluacionDto.setVisible10(false);

            licitacionProveedorDetalleEvaluacionDto.setNombreProveedor01("");
            licitacionProveedorDetalleEvaluacionDto.setNombreProveedor02("");
            licitacionProveedorDetalleEvaluacionDto.setNombreProveedor03("");
            licitacionProveedorDetalleEvaluacionDto.setNombreProveedor04("");
            licitacionProveedorDetalleEvaluacionDto.setNombreProveedor05("");
            licitacionProveedorDetalleEvaluacionDto.setNombreProveedor06("");
            licitacionProveedorDetalleEvaluacionDto.setNombreProveedor07("");
            licitacionProveedorDetalleEvaluacionDto.setNombreProveedor08("");
            licitacionProveedorDetalleEvaluacionDto.setNombreProveedor09("");
            licitacionProveedorDetalleEvaluacionDto.setNombreProveedor10("");

            for (int i=0; i < proveedorList.size(); i++) {
                Proveedor proveedor = proveedorList.get(i);
                switch (i) {
                    case 0:
                        licitacionProveedorDetalleEvaluacionDto.setProveedor01(proveedor);
                        licitacionProveedorDetalleEvaluacionDto.setNombreProveedor01(proveedor.getRazonSocial());
                        licitacionProveedorDetalleEvaluacionDto.setCantidadCotizada01(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioUnitario01(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setSubTotal01(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioFinalMoneda01(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeCondicionObtenido01(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeEvaluacionTecnicaObtenido01(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajePrecioObtenido01(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeTotalObtenido01(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setVisible01(true);
                        break;
                    case 1:
                        licitacionProveedorDetalleEvaluacionDto.setProveedor02(proveedor);
                        licitacionProveedorDetalleEvaluacionDto.setNombreProveedor02(proveedor.getRazonSocial());

                        licitacionProveedorDetalleEvaluacionDto.setCantidadCotizada02(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioUnitario02(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setSubTotal02(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioFinalMoneda02(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeCondicionObtenido02(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeEvaluacionTecnicaObtenido02(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajePrecioObtenido02(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeTotalObtenido02(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setVisible02(true);
                        break;
                    case 2:
                        licitacionProveedorDetalleEvaluacionDto.setProveedor03(proveedor);
                        licitacionProveedorDetalleEvaluacionDto.setNombreProveedor03(proveedor.getRazonSocial());

                        licitacionProveedorDetalleEvaluacionDto.setCantidadCotizada03(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioUnitario03(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setSubTotal03(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioFinalMoneda03(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeCondicionObtenido03(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeEvaluacionTecnicaObtenido03(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajePrecioObtenido03(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeTotalObtenido03(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setVisible03(true);
                        break;
                    case 3:
                        licitacionProveedorDetalleEvaluacionDto.setProveedor04(proveedor);
                        licitacionProveedorDetalleEvaluacionDto.setNombreProveedor04(proveedor.getRazonSocial());

                        licitacionProveedorDetalleEvaluacionDto.setCantidadCotizada04(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioUnitario04(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setSubTotal04(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioFinalMoneda04(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeCondicionObtenido04(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeEvaluacionTecnicaObtenido04(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajePrecioObtenido04(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeTotalObtenido04(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setVisible04(true);
                        break;
                    case 4:
                        licitacionProveedorDetalleEvaluacionDto.setProveedor05(proveedor);
                        licitacionProveedorDetalleEvaluacionDto.setNombreProveedor05(proveedor.getRazonSocial());

                        licitacionProveedorDetalleEvaluacionDto.setCantidadCotizada05(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioUnitario05(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setSubTotal05(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioFinalMoneda05(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeCondicionObtenido05(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeEvaluacionTecnicaObtenido05(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajePrecioObtenido05(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeTotalObtenido05(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setVisible05(true);
                        break;
                    case 5:
                        licitacionProveedorDetalleEvaluacionDto.setProveedor06(proveedor);
                        licitacionProveedorDetalleEvaluacionDto.setNombreProveedor06(proveedor.getRazonSocial());

                        licitacionProveedorDetalleEvaluacionDto.setCantidadCotizada06(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioUnitario06(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setSubTotal06(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioFinalMoneda06(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeCondicionObtenido06(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeEvaluacionTecnicaObtenido06(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajePrecioObtenido06(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeTotalObtenido06(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setVisible06(true);
                        break;
                    case 6:
                        licitacionProveedorDetalleEvaluacionDto.setProveedor07(proveedor);
                        licitacionProveedorDetalleEvaluacionDto.setNombreProveedor07(proveedor.getRazonSocial());

                        licitacionProveedorDetalleEvaluacionDto.setCantidadCotizada07(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioUnitario07(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setSubTotal07(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioFinalMoneda07(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeCondicionObtenido07(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeEvaluacionTecnicaObtenido07(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajePrecioObtenido07(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeTotalObtenido07(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setVisible07(true);
                        break;
                    case 7:
                        licitacionProveedorDetalleEvaluacionDto.setProveedor08(proveedor);
                        licitacionProveedorDetalleEvaluacionDto.setNombreProveedor08(proveedor.getRazonSocial());

                        licitacionProveedorDetalleEvaluacionDto.setCantidadCotizada08(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioUnitario08(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setSubTotal08(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioFinalMoneda08(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeCondicionObtenido08(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeEvaluacionTecnicaObtenido08(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajePrecioObtenido08(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeTotalObtenido08(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setVisible08(true);
                        break;
                    case 8:
                        licitacionProveedorDetalleEvaluacionDto.setProveedor09(proveedor);
                        licitacionProveedorDetalleEvaluacionDto.setNombreProveedor09(proveedor.getRazonSocial());

                        licitacionProveedorDetalleEvaluacionDto.setCantidadCotizada09(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioUnitario09(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setSubTotal09(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioFinalMoneda09(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeCondicionObtenido09(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeEvaluacionTecnicaObtenido09(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajePrecioObtenido09(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeTotalObtenido09(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setVisible09(true);
                        break;
                    case 9:
                        licitacionProveedorDetalleEvaluacionDto.setProveedor10(proveedor);
                        licitacionProveedorDetalleEvaluacionDto.setNombreProveedor10(proveedor.getRazonSocial());

                        licitacionProveedorDetalleEvaluacionDto.setCantidadCotizada10(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioUnitario10(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setSubTotal10(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPrecioFinalMoneda10(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeCondicionObtenido10(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeEvaluacionTecnicaObtenido10(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajePrecioObtenido10(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setPorcentajeTotalObtenido10(new BigDecimal(0.00));
                        licitacionProveedorDetalleEvaluacionDto.setVisible10(true);
                        break;
                }
            }
            listaDetalleCuadro.add(licitacionProveedorDetalleEvaluacionDto);
        }

        /* Obteneniendo las cotizaciones para el Cuadro Detalle */
        for (LicitacionProveedorDetalleEvaluacionDto beanDetalle : listaDetalleCuadro) {
            if (beanDetalle.getVisible01()) {
                Proveedor proveedor = beanDetalle.getProveedor01();
                this.obtenerDatosDetalleEvaluacion(licitacion, proveedor, beanDetalle, 1);

            }
            if (beanDetalle.getVisible02()) {
                Proveedor proveedor = beanDetalle.getProveedor02();
                this.obtenerDatosDetalleEvaluacion(licitacion, proveedor, beanDetalle, 2);
            }
            if (beanDetalle.getVisible03()) {
                Proveedor proveedor = beanDetalle.getProveedor03();
                this.obtenerDatosDetalleEvaluacion(licitacion, proveedor, beanDetalle, 3);
            }
            if (beanDetalle.getVisible04()) {
                Proveedor proveedor = beanDetalle.getProveedor04();
                this.obtenerDatosDetalleEvaluacion(licitacion, proveedor, beanDetalle, 4);
            }
            if (beanDetalle.getVisible05()) {
                Proveedor proveedor = beanDetalle.getProveedor05();
                this.obtenerDatosDetalleEvaluacion(licitacion, proveedor, beanDetalle, 5);
            }
            if (beanDetalle.getVisible06()) {
                Proveedor proveedor = beanDetalle.getProveedor06();
                this.obtenerDatosDetalleEvaluacion(licitacion, proveedor, beanDetalle, 6);
            }
            if (beanDetalle.getVisible07()) {
                Proveedor proveedor = beanDetalle.getProveedor07();
                this.obtenerDatosDetalleEvaluacion(licitacion, proveedor, beanDetalle, 7);
            }
            if (beanDetalle.getVisible08()) {
                Proveedor proveedor = beanDetalle.getProveedor08();
                this.obtenerDatosDetalleEvaluacion(licitacion, proveedor, beanDetalle, 8);
            }
            if (beanDetalle.getVisible09()) {
                Proveedor proveedor = beanDetalle.getProveedor09();
                this.obtenerDatosDetalleEvaluacion(licitacion, proveedor, beanDetalle, 9);
            }
            if (beanDetalle.getVisible10()) {
                Proveedor proveedor = beanDetalle.getProveedor09();
                this.obtenerDatosDetalleEvaluacion(licitacion, proveedor, beanDetalle, 10);
            }

        }

        /* Obteniendo ganadores */
        for (LicitacionProveedorDetalleEvaluacionDto beanDetalle : listaDetalleCuadro) {
            BigDecimal porcentajeGanador = new BigDecimal(0.00);
            if (beanDetalle.getVisible01()) {
                if (beanDetalle.getPorcentajeTotalObtenido01().floatValue() > porcentajeGanador.floatValue()) {
                    porcentajeGanador = beanDetalle.getPorcentajeTotalObtenido01();
                    beanDetalle.setPorcentajeTotalObtenidoGanador(beanDetalle.getPorcentajeTotalObtenido01());
                    beanDetalle.setProveedorGanador(beanDetalle.getProveedor01());
                    beanDetalle.setNombreProveedorGanador(beanDetalle.getProveedor01().getRazonSocial());
                }
            }
            if (beanDetalle.getVisible02()) {
                if (beanDetalle.getPorcentajeTotalObtenido02().floatValue() > porcentajeGanador.floatValue()) {
                    porcentajeGanador = beanDetalle.getPorcentajeTotalObtenido02();
                    beanDetalle.setPorcentajeTotalObtenidoGanador(porcentajeGanador);
                    beanDetalle.setProveedorGanador(beanDetalle.getProveedor02());
                    beanDetalle.setNombreProveedorGanador(beanDetalle.getProveedor02().getRazonSocial());
                }
            }
            if (beanDetalle.getVisible03()) {
                if (beanDetalle.getPorcentajeTotalObtenido03().floatValue() > porcentajeGanador.floatValue()) {
                    porcentajeGanador = beanDetalle.getPorcentajeTotalObtenido03();
                    beanDetalle.setPorcentajeTotalObtenidoGanador(porcentajeGanador);
                    beanDetalle.setProveedorGanador(beanDetalle.getProveedor03());
                    beanDetalle.setNombreProveedorGanador(beanDetalle.getProveedor03().getRazonSocial());
                }
            }
            if (beanDetalle.getVisible04()) {
                if (beanDetalle.getPorcentajeTotalObtenido04().floatValue() > porcentajeGanador.floatValue()) {
                    porcentajeGanador = beanDetalle.getPorcentajeTotalObtenido04();
                    beanDetalle.setPorcentajeTotalObtenidoGanador(porcentajeGanador);
                    beanDetalle.setProveedorGanador(beanDetalle.getProveedor04());
                    beanDetalle.setNombreProveedorGanador(beanDetalle.getProveedor04().getRazonSocial());
                }
            }
            if (beanDetalle.getVisible05()) {
                if (beanDetalle.getPorcentajeTotalObtenido05().floatValue() > porcentajeGanador.floatValue()) {
                    porcentajeGanador = beanDetalle.getPorcentajeTotalObtenido05();
                    beanDetalle.setPorcentajeTotalObtenidoGanador(porcentajeGanador);
                    beanDetalle.setProveedorGanador(beanDetalle.getProveedor05());
                    beanDetalle.setNombreProveedorGanador(beanDetalle.getProveedor05().getRazonSocial());
                }
            }
            if (beanDetalle.getVisible06()) {
                if (beanDetalle.getPorcentajeTotalObtenido06().floatValue() > porcentajeGanador.floatValue()) {
                    porcentajeGanador = beanDetalle.getPorcentajeTotalObtenido06();
                    beanDetalle.setPorcentajeTotalObtenidoGanador(porcentajeGanador);
                    beanDetalle.setProveedorGanador(beanDetalle.getProveedor06());
                    beanDetalle.setNombreProveedorGanador(beanDetalle.getProveedor06().getRazonSocial());
                }
            }
            if (beanDetalle.getVisible07()) {
                if (beanDetalle.getPorcentajeTotalObtenido07().floatValue() > porcentajeGanador.floatValue()) {
                    porcentajeGanador = beanDetalle.getPorcentajeTotalObtenido07();
                    beanDetalle.setPorcentajeTotalObtenidoGanador(porcentajeGanador);
                    beanDetalle.setProveedorGanador(beanDetalle.getProveedor07());
                    beanDetalle.setNombreProveedorGanador(beanDetalle.getProveedor07().getRazonSocial());
                }
            }
            if (beanDetalle.getVisible08()) {
                if (beanDetalle.getPorcentajeTotalObtenido08().floatValue() > porcentajeGanador.floatValue()) {
                    porcentajeGanador = beanDetalle.getPorcentajeTotalObtenido08();
                    beanDetalle.setPorcentajeTotalObtenidoGanador(porcentajeGanador);
                    beanDetalle.setProveedorGanador(beanDetalle.getProveedor08());
                    beanDetalle.setNombreProveedorGanador(beanDetalle.getProveedor08().getRazonSocial());
                }
            }if (beanDetalle.getVisible09()) {
                if (beanDetalle.getPorcentajeTotalObtenido01().floatValue() > porcentajeGanador.floatValue()) {
                    porcentajeGanador = beanDetalle.getPorcentajeTotalObtenido09();
                    beanDetalle.setPorcentajeTotalObtenidoGanador(porcentajeGanador);
                    beanDetalle.setProveedorGanador(beanDetalle.getProveedor09());
                    beanDetalle.setNombreProveedorGanador(beanDetalle.getProveedor09().getRazonSocial());
                }
            }
            if (beanDetalle.getVisible10()) {
                if (beanDetalle.getPorcentajeTotalObtenido10().floatValue() > porcentajeGanador.floatValue()) {
                    porcentajeGanador = beanDetalle.getPorcentajeTotalObtenido10();
                    beanDetalle.setPorcentajeTotalObtenidoGanador(porcentajeGanador);
                    beanDetalle.setProveedorGanador(beanDetalle.getProveedor10());
                    beanDetalle.setNombreProveedorGanador(beanDetalle.getProveedor10().getRazonSocial());
                }
            }

        }
        return listaDetalleCuadro;
    }

    private void obtenerDatosDetalleEvaluacion(Licitacion licitacion,
                                                Proveedor proveedor,
                                                LicitacionProveedorDetalleEvaluacionDto beanDetalle,
                                                int contador) {
        LicitacionProveedorDetalleEvaluacion licitacionProveedorDetalleEvaluacion =
                this.licitacionProveedorDetalleEvaluacionRepository.getByIdLicitacionAndIdProveedorAndIdLicitacionDetalle(
                        licitacion,
                        proveedor,
                        beanDetalle.getLicitacionDetalle()
                );
        if (!Optional.ofNullable(licitacionProveedorDetalleEvaluacion).isPresent()) {
            licitacionProveedorDetalleEvaluacion = new LicitacionProveedorDetalleEvaluacion();
            licitacionProveedorDetalleEvaluacion.setCantidadTotalCotizacion(new BigDecimal(0.00));
            licitacionProveedorDetalleEvaluacion.setPrecioUnitario(new BigDecimal(0.00));
            licitacionProveedorDetalleEvaluacion.setPrecioSuma(new BigDecimal(0.00));
            licitacionProveedorDetalleEvaluacion.setPrecioFinalMoneda(new BigDecimal(0.00));
            licitacionProveedorDetalleEvaluacion.setPorcentajeCondicionObtenido(new BigDecimal(0.00));
            licitacionProveedorDetalleEvaluacion.setPorcentajeEvaluacionTecnicaObtenido(new BigDecimal(0.00));
            licitacionProveedorDetalleEvaluacion.setPorcentajePrecioObtenido(new BigDecimal(0.00));
            licitacionProveedorDetalleEvaluacion.setPorcentajeTotalObtenido(new BigDecimal(0.00));

        }
        switch (contador) {
            case 1:
                beanDetalle.setCantidadCotizada01(
                        licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion()
                );
                beanDetalle.setPrecioUnitario01(
                        licitacionProveedorDetalleEvaluacion.getPrecioUnitario()
                );
                beanDetalle.setSubTotal01(
                        licitacionProveedorDetalleEvaluacion.getPrecioSuma()
                );
                beanDetalle.setPrecioFinalMoneda01(
                        licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda()
                );
                beanDetalle.setPorcentajeCondicionObtenido01(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido()
                );
                beanDetalle.setPorcentajeEvaluacionTecnicaObtenido01(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido()
                );
                beanDetalle.setPorcentajePrecioObtenido01(
                        licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido()
                );
                beanDetalle.setPorcentajeTotalObtenido01(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido()
                );
                break;
            case 2:
                beanDetalle.setCantidadCotizada02(
                        licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion()
                );
                beanDetalle.setPrecioUnitario02(
                        licitacionProveedorDetalleEvaluacion.getPrecioUnitario()
                );
                beanDetalle.setSubTotal02(
                        licitacionProveedorDetalleEvaluacion.getPrecioSuma()
                );
                beanDetalle.setPrecioFinalMoneda02(
                        licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda()
                );
                beanDetalle.setPorcentajeCondicionObtenido02(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido()
                );
                beanDetalle.setPorcentajeEvaluacionTecnicaObtenido02(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido()
                );
                beanDetalle.setPorcentajePrecioObtenido02(
                        licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido()
                );
                beanDetalle.setPorcentajeTotalObtenido02(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido()
                );
                break;
            case 3:
                beanDetalle.setCantidadCotizada03(
                        licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion()
                );
                beanDetalle.setPrecioUnitario03(
                        licitacionProveedorDetalleEvaluacion.getPrecioUnitario()
                );
                beanDetalle.setSubTotal03(
                        licitacionProveedorDetalleEvaluacion.getPrecioSuma()
                );
                beanDetalle.setPrecioFinalMoneda03(
                        licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda()
                );
                beanDetalle.setPorcentajeCondicionObtenido03(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido()
                );
                beanDetalle.setPorcentajeEvaluacionTecnicaObtenido03(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido()
                );
                beanDetalle.setPorcentajePrecioObtenido03(
                        licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido()
                );
                beanDetalle.setPorcentajeTotalObtenido03(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido()
                );
                break;
            case 4:
                beanDetalle.setCantidadCotizada04(
                        licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion()
                );
                beanDetalle.setPrecioUnitario04(
                        licitacionProveedorDetalleEvaluacion.getPrecioUnitario()
                );
                beanDetalle.setSubTotal04(
                        licitacionProveedorDetalleEvaluacion.getPrecioSuma()
                );
                beanDetalle.setPrecioFinalMoneda04(
                        licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda()
                );
                beanDetalle.setPorcentajeCondicionObtenido04(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido()
                );
                beanDetalle.setPorcentajeEvaluacionTecnicaObtenido04(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido()
                );
                beanDetalle.setPorcentajePrecioObtenido04(
                        licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido()
                );
                beanDetalle.setPorcentajeTotalObtenido04(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido()
                );
                break;
            case 5:
                beanDetalle.setCantidadCotizada05(
                        licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion()
                );
                beanDetalle.setPrecioUnitario05(
                        licitacionProveedorDetalleEvaluacion.getPrecioUnitario()
                );
                beanDetalle.setSubTotal05(
                        licitacionProveedorDetalleEvaluacion.getPrecioSuma()
                );
                beanDetalle.setPrecioFinalMoneda05(
                        licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda()
                );
                beanDetalle.setPorcentajeCondicionObtenido05(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido()
                );
                beanDetalle.setPorcentajeEvaluacionTecnicaObtenido05(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido()
                );
                beanDetalle.setPorcentajePrecioObtenido05(
                        licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido()
                );
                beanDetalle.setPorcentajeTotalObtenido05(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido()
                );
                break;
            case 6:
                beanDetalle.setCantidadCotizada06(
                        licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion()
                );
                beanDetalle.setPrecioUnitario06(
                        licitacionProveedorDetalleEvaluacion.getPrecioUnitario()
                );
                beanDetalle.setSubTotal06(
                        licitacionProveedorDetalleEvaluacion.getPrecioSuma()
                );
                beanDetalle.setPrecioFinalMoneda06(
                        licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda()
                );
                beanDetalle.setPorcentajeCondicionObtenido06(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido()
                );
                beanDetalle.setPorcentajeEvaluacionTecnicaObtenido06(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido()
                );
                beanDetalle.setPorcentajePrecioObtenido06(
                        licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido()
                );
                beanDetalle.setPorcentajeTotalObtenido06(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido()
                );
                break;
            case 7:
                beanDetalle.setCantidadCotizada07(
                        licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion()
                );
                beanDetalle.setPrecioUnitario07(
                        licitacionProveedorDetalleEvaluacion.getPrecioUnitario()
                );
                beanDetalle.setSubTotal07(
                        licitacionProveedorDetalleEvaluacion.getPrecioSuma()
                );
                beanDetalle.setPrecioFinalMoneda07(
                        licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda()
                );
                beanDetalle.setPorcentajeCondicionObtenido07(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido()
                );
                beanDetalle.setPorcentajeEvaluacionTecnicaObtenido07(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido()
                );
                beanDetalle.setPorcentajePrecioObtenido07(
                        licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido()
                );
                beanDetalle.setPorcentajeTotalObtenido07(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido()
                );
                break;
            case 8:
                beanDetalle.setCantidadCotizada08(
                        licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion()
                );
                beanDetalle.setPrecioUnitario08(
                        licitacionProveedorDetalleEvaluacion.getPrecioUnitario()
                );
                beanDetalle.setSubTotal08(
                        licitacionProveedorDetalleEvaluacion.getPrecioSuma()
                );
                beanDetalle.setPrecioFinalMoneda08(
                        licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda()
                );
                beanDetalle.setPorcentajeCondicionObtenido08(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido()
                );
                beanDetalle.setPorcentajeEvaluacionTecnicaObtenido08(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido()
                );
                beanDetalle.setPorcentajePrecioObtenido08(
                        licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido()
                );
                beanDetalle.setPorcentajeTotalObtenido08(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido()
                );
                break;
            case 9:
                beanDetalle.setCantidadCotizada09(
                        licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion()
                );
                beanDetalle.setPrecioUnitario09(
                        licitacionProveedorDetalleEvaluacion.getPrecioUnitario()
                );
                beanDetalle.setSubTotal09(
                        licitacionProveedorDetalleEvaluacion.getPrecioSuma()
                );
                beanDetalle.setPrecioFinalMoneda09(
                        licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda()
                );
                beanDetalle.setPorcentajeCondicionObtenido09(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido()
                );
                beanDetalle.setPorcentajeEvaluacionTecnicaObtenido09(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido()
                );
                beanDetalle.setPorcentajePrecioObtenido09(
                        licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido()
                );
                beanDetalle.setPorcentajeTotalObtenido09(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido()
                );
                break;
            case 10:
                beanDetalle.setCantidadCotizada10(
                        licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion()
                );
                beanDetalle.setPrecioUnitario10(
                        licitacionProveedorDetalleEvaluacion.getPrecioUnitario()
                );
                beanDetalle.setSubTotal10(
                        licitacionProveedorDetalleEvaluacion.getPrecioSuma()
                );
                beanDetalle.setPrecioFinalMoneda10(
                        licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda()
                );
                beanDetalle.setPorcentajeCondicionObtenido10(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido()
                );
                beanDetalle.setPorcentajeEvaluacionTecnicaObtenido10(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido()
                );
                beanDetalle.setPorcentajePrecioObtenido10(
                        licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido()
                );
                beanDetalle.setPorcentajeTotalObtenido10(
                        licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido()
                );
                break;
        }
    }

    /******************************/
    /* Obtencion Ganador CABECERA */
    /******************************/

    public GanadorLicitacionSalidaDto obtenerGanadorTipoCuestionario(GanadorLicitacionEntradaDto bean) throws Exception {
        GanadorLicitacionSalidaDto ganadorLicitacionSalidaDto = new GanadorLicitacionSalidaDto();
        List<GanadorLicitacionEvaluacionEntradaDto> listaEvaluacionTecnica = bean.getListaEvaluacionTecnica();
        if (listaEvaluacionTecnica != null && listaEvaluacionTecnica.size() > 0) {
            for (GanadorLicitacionEvaluacionEntradaDto beanEvaluacion : listaEvaluacionTecnica) {
                BigDecimal puntaje = beanEvaluacion.getPuntajeEvaluacionTecnica();
                if (Optional.ofNullable(puntaje).isPresent()) {
                    if (puntaje.floatValue() < 0.00 || puntaje.floatValue() > 100.00) {
                        ganadorLicitacionSalidaDto.setEjecucion(false);
                        throw new Exception("Puntaje Evaluación Técnica debe ser mayor igual a Cero y menor igual a 100");
                    }
                }
            }
        }

        /* Datos Iniciales */
        List<LicitacionProveedorTipoCuestionario> licitacionProveedorTipoCuestionarioList =
                new ArrayList<LicitacionProveedorTipoCuestionario>();
        Licitacion licitacion = this.licitacionRepository.getOne(bean.getIdLicitacion());
        if (!Optional.ofNullable(licitacion).isPresent()) {
            ganadorLicitacionSalidaDto.setEjecucion(false);
            return ganadorLicitacionSalidaDto;
        }

        List<LicitacionGrupoCondicionLic> licitacionGrupoCondicionLicList =
                this.licitacionGrupoCondicionLicRepository.findByLicitacion(licitacion);
        if (licitacionGrupoCondicionLicList == null || licitacionGrupoCondicionLicList.size() <= 0) {
            ganadorLicitacionSalidaDto.setEjecucion(false);
            return ganadorLicitacionSalidaDto;
        }

        TipoLicitacion tipoLicitacion = licitacionGrupoCondicionLicList.get(0).getTipoLicitacion1();
        TipoLicitacion tipoCuestionario = licitacionGrupoCondicionLicList.get(0).getTipoLicitacion2();
        List<Moneda> monedaList = this.monedaRepository.findAll();

        List<LicitacionProveedor> licitacionProveedorList = this.licitacionProveedorRepository.
                findByLicitacionSiParticipa(licitacion, Constant.S);
        if (licitacionProveedorList == null || licitacionProveedorList.size() <= 0) {
            ganadorLicitacionSalidaDto.setEjecucion(false);
            return ganadorLicitacionSalidaDto;
        }


        Date fechaActual = DateUtils.obtenerFechaActual();
        Moneda monedaBase = this.monedaRepository.getByCodigoMoneda("PEN");
        Moneda monedaLicitacion = licitacion.getMoneda();
        Integer diasMaximoCondicionPago = new Integer(0);


        /* Obteniendo el detalle de la licitacion */
        List<LicitacionDetalle> licitacionDetalleList = this.licitacionDetalleRepository.findByLicitacion(licitacion);
        if (licitacionDetalleList == null || licitacionDetalleList.size() <= 0) {
            ganadorLicitacionSalidaDto.setEjecucion(false);
            return ganadorLicitacionSalidaDto;
        }

        BigDecimal cantidadSolicitada = new BigDecimal(0.00);
        for (LicitacionDetalle beanDetalle : licitacionDetalleList) {
            cantidadSolicitada = new BigDecimal(
                    cantidadSolicitada.floatValue() + beanDetalle.getCantidadSolicitada().floatValue()).
                    setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        /* Obteniendo maximo dias de Condicion de Pago */
        for (LicitacionProveedor beanLicitacionProveedor: licitacionProveedorList) {
            Integer diasCondicionPago = beanLicitacionProveedor.getProveedor().getCondicionPago().getDiasPago();
            if (diasCondicionPago.intValue() > diasMaximoCondicionPago.intValue()) {
                diasMaximoCondicionPago = diasCondicionPago;
            }
        }

        /* Recorriendo Proveedor / seteando valores  */
        for (LicitacionProveedor beanLicitacionProveedor: licitacionProveedorList) {
            LicitacionProveedorTipoCuestionario licitacionProveedorTipoCuestionario = this.setearValores(licitacion,
                    beanLicitacionProveedor.getProveedor(),
                    tipoLicitacion,
                    tipoCuestionario,
                    monedaLicitacion,
                    fechaActual,
                    cantidadSolicitada
            );

            this.obtenerPuntajeCondicion(licitacionProveedorTipoCuestionario);
            this.obtenerPrecioMoneda(licitacionProveedorTipoCuestionario,
                    fechaActual,
                    monedaBase,
                    monedaLicitacion,
                    diasMaximoCondicionPago);


            //log.error("Ingresando obtenerGanadorTipoCuestionario licitacionProveedorTipoCuestionario: " + licitacionProveedorTipoCuestionario.toString02());
            licitacionProveedorTipoCuestionarioList.add(licitacionProveedorTipoCuestionario);
        }


        /* Obteniendo precio menor de todas las cotizaciones */
        BigDecimal precioMinimoFinalIni = new BigDecimal(99999999999999.99);
        BigDecimal precioMinimoFinal = new BigDecimal(99999999999999.99);
        for (LicitacionProveedorTipoCuestionario beanTipo: licitacionProveedorTipoCuestionarioList) {
            if (beanTipo.getPrecioFinalMoneda().floatValue() > 0.00) {
                if (beanTipo.getPrecioFinalMoneda().floatValue() < precioMinimoFinal.floatValue())  {
                    precioMinimoFinal = new BigDecimal(beanTipo.getPrecioFinalMoneda().floatValue()).
                            setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            }

        }
        if (precioMinimoFinalIni.floatValue() == precioMinimoFinal.floatValue()) {
            precioMinimoFinal = new BigDecimal(0.00);
        }

        /* Obteniendo porcentaje precio */
        for (LicitacionProveedorTipoCuestionario beanTipo: licitacionProveedorTipoCuestionarioList) {
            beanTipo.setPrecioFinalMinimoMoneda(precioMinimoFinal);
            BigDecimal precioFinal = beanTipo.getPrecioFinalMoneda();
            BigDecimal porcentaje = new BigDecimal(0.00);

            BigDecimal factorCantidad = new BigDecimal(0.00);
            if (precioFinal.floatValue() != 0.00) {
                porcentaje = new BigDecimal(precioMinimoFinal.floatValue() / precioFinal.floatValue());
                porcentaje = new BigDecimal(porcentaje.floatValue() * beanTipo.getPesoMoneda().floatValue() );

                factorCantidad = new BigDecimal(beanTipo.getCantidadTotalCotizacion().floatValue() / beanTipo.getCantidadTotalLicitacion().floatValue());
                porcentaje = new BigDecimal(porcentaje.floatValue() * factorCantidad.floatValue())
                        .setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            beanTipo.setPorcentajePrecioObtenido(porcentaje);
        }

        /* Obteniendo Evaluacion Tecnica */
        if (listaEvaluacionTecnica != null && listaEvaluacionTecnica.size() > 0) {
            for (GanadorLicitacionEvaluacionEntradaDto beanEvaluacion : listaEvaluacionTecnica) {
                Integer idProveedor = beanEvaluacion.getIdProveedor();
                for (LicitacionProveedorTipoCuestionario beanTipo : licitacionProveedorTipoCuestionarioList) {
                    if (idProveedor == beanTipo.getProveedor().getIdProveedor()) {
                        BigDecimal puntajeEvaluacionTecnica = beanEvaluacion.getPuntajeEvaluacionTecnica();
                        beanTipo.setPuntajeEvaluacionTecnica(puntajeEvaluacionTecnica);
                        beanTipo.setPorcentajeEvaluacionTecnicaObtenido(
                                new BigDecimal(
                                        puntajeEvaluacionTecnica.floatValue() * beanTipo.getPesoEvaluacionTecnica().floatValue() / 100).setScale(2, BigDecimal.ROUND_HALF_UP)
                        );
                    }
                }
            }
        }

        /* Grabando */
        this.licitacionProveedorTipoCuestionarioRepository.deleteByLicitacion(licitacion);
        log.error("Ingresando obtenerGanadorTipoCuestionario grabando 0: ");
        if (licitacionProveedorTipoCuestionarioList != null && licitacionProveedorTipoCuestionarioList.size() > 0) {
            for (LicitacionProveedorTipoCuestionario beanTipo : licitacionProveedorTipoCuestionarioList) {
                beanTipo.setPorcentajeTotalObtenido(
                        new BigDecimal(
                                beanTipo.getPorcentajeCondicionObtenido().floatValue() +
                                        beanTipo.getPorcentajePrecioObtenido().floatValue() +
                                        beanTipo.getPorcentajeEvaluacionTecnicaObtenido().floatValue()
                        ).setScale(2, BigDecimal.ROUND_HALF_UP)
                );
                log.error("Ingresando obtenerGanadorTipoCuestionario grabando 1 - beanTipo: " + beanTipo.toString02());
                this.licitacionProveedorTipoCuestionarioRepository.save(beanTipo);
            }
        }
        //this.licitacionProveedorTipoCuestionarioRepository.saveAll(licitacionProveedorTipoCuestionarioList);

        /* Obteniendo lista ordenada de mayor a menor */
        log.error("Ingresando obtenerGanadorTipoCuestionario grabando 2: ");
        List<LicitacionProveedorTipoCuestionario> listaOrdenada =
                this.licitacionProveedorTipoCuestionarioRepository.findByLicitacionAndSort(
                        licitacion,
                        Sort.by(
                                new Sort.Order(Sort.Direction.DESC, "porcentajeTotalObtenido")));
        log.error("Ingresando obtenerGanadorTipoCuestionario grabando 3 - listaOrdenada: " + listaOrdenada.toString());
        ganadorLicitacionSalidaDto.setResultList(listaOrdenada);
        List<GraficoMiniSalidaDto> graficaPrecioFinalMonedaList = this.graficaPrecioFinalMoneda(licitacion.getIdLicitacion());
        List<GraficoMiniSalidaDto> graficaPorcentajeObtenidoList = this.graficaPorcentajeObtenido(licitacion.getIdLicitacion());
        ganadorLicitacionSalidaDto.setGraficaPrecioFinalMonedaList(graficaPrecioFinalMonedaList);
        ganadorLicitacionSalidaDto.setGraficaPorcentajeObtenidoList(graficaPorcentajeObtenidoList);

        if (listaOrdenada != null && listaOrdenada.size() > 0) {
            ganadorLicitacionSalidaDto.setContadorResult(listaOrdenada.size());
        }
        return ganadorLicitacionSalidaDto;

    }

    private BigDecimal obtenerTasaCambio(
            Date fechaActual,
            Moneda monedaBase,
            Moneda monedaLicitacion,
            Moneda monedaCotizacion) throws Exception {
        BigDecimal valorTasaCambio = new BigDecimal(0.00);
        log.error("CBAZALAR Ingresando obtenerTasaCambio 00");
        log.error("CBAZALAR Ingresando obtenerTasaCambio fechaActual: " + fechaActual);

        Moneda monedaOrigen = new Moneda();
        Moneda monedaDestino = new Moneda();
        boolean invertirTasa = false;

//        if(monedaLicitacion.getCodigoMoneda().equalsIgnoreCase("USD") && monedaCotizacion.getCodigoMoneda().equalsIgnoreCase("PEN")) { // invertimos las monedas para obtener la tasa invertida de la BD o SAP
//            monedaOrigen = monedaLicitacion;
//            monedaDestino = monedaCotizacion;
//            invertirTasa = true;
//            log.error("CBAZALAR Ingresando obtenerTasaCambio 00a - [INVERTIDO] monedaOrigen (monedaLicitacion): " + monedaOrigen.toString());
//            log.error("CBAZALAR Ingresando obtenerTasaCambio 00b - [INVERTIDO] monedaDestino (monedaCotizacion): " + monedaDestino.toString());
//        }
//        else{ // la moneda de Licitacion ES SIEMPRE la moneda real de destino
//            monedaOrigen = monedaCotizacion;
//            monedaDestino = monedaLicitacion;
//            log.error("CBAZALAR Ingresando obtenerTasaCambio 00a - monedaOrigen (monedaCotizacion): " + monedaOrigen.toString());
//            log.error("CBAZALAR Ingresando obtenerTasaCambio 00b - monedaDestino (monedaLicitacion): " + monedaDestino.toString());
//        }

        /* Licitacion con moneda base */
//        if (monedaBase.getCodigoMoneda().equals(monedaCotizacion.getCodigoMoneda())) {
        if (monedaCotizacion.getCodigoMoneda().equals(monedaLicitacion.getCodigoMoneda())) {
            log.error("CBAZALAR Ingresando obtenerTasaCambio 01 - sin conversion");
            valorTasaCambio = new BigDecimal(1.00);
        }
        else {
            log.error("CBAZALAR Ingresando obtenerTasaCambio 02 - con conversion");
            TasaCambio tasaCambio = this.tasaCambioRepository.getByFechaTasaAndIdMonedaOrigenAndIdMonedaDestino( // aparentemente se envian invertidas la moneda origen y la moneda destino
                    fechaActual, monedaLicitacion, monedaCotizacion);
//                    fechaActual, monedaDestino, monedaOrigen);
            if (Optional.ofNullable(tasaCambio).isPresent()) {
                log.error("CBAZALAR tasaCambio 02a: " + tasaCambio.toString());
                valorTasaCambio = tasaCambio.getValor().setScale(4, BigDecimal.ROUND_HALF_UP);
            }
            else {
                if(monedaLicitacion.getCodigoMoneda().equalsIgnoreCase("USD") && monedaCotizacion.getCodigoMoneda().equalsIgnoreCase("PEN")) { // invertimos las monedas para obtener la tasa invertida de la BD o SAP
                    tasaCambio = this.tasaCambioRepository.getByFechaTasaAndIdMonedaOrigenAndIdMonedaDestino( // aparentemente se envian invertidas la moneda origen y la moneda destino
                            fechaActual, monedaCotizacion, monedaLicitacion);
                    log.error("CBAZALAR Ingresando obtenerTasaCambio 02aa - [INVERTIDO] monedaOrigen (monedaLicitacion): " + monedaLicitacion.toString());
                    log.error("CBAZALAR Ingresando obtenerTasaCambio 02ab - [INVERTIDO] monedaDestino (monedaCotizacion): " + monedaCotizacion.toString());

                    if(Optional.ofNullable(tasaCambio).isPresent()){
                        TasaCambio newTasaCambio = Utils.obtenerTasaCambioInvertida(tasaCambio);
                        tasaCambioRepository.save(newTasaCambio);
                        valorTasaCambio = newTasaCambio.getValor().setScale(4, BigDecimal.ROUND_HALF_UP);
                        log.error("CBAZALAR Tasa de Cambio final: " + valorTasaCambio);
                        return valorTasaCambio;
                    }
                    else{
                        monedaOrigen = monedaLicitacion;
                        monedaDestino = monedaCotizacion;
                        invertirTasa = true;
                    }
                }
                else{
                    monedaOrigen = monedaCotizacion;
                    monedaDestino = monedaLicitacion;
                }

                log.error("CBAZALAR Ingresando obtenerTasaCambio 02b");
                String sFecha = DateUtils.convertDateToString("yyyyMMdd", fechaActual);
                this.jcoTipoCambioService.actualizarTipoCambio(sFecha);

                tasaCambio = this.tasaCambioRepository.getByFechaTasaAndIdMonedaOrigenAndIdMonedaDestino(
//                        fechaActual, monedaBase, monedaCotizacion);
                        fechaActual, monedaDestino, monedaOrigen);
                if (Optional.ofNullable(tasaCambio).isPresent()) {
                    if(invertirTasa){
                        TasaCambio newTasaCambio = Utils.obtenerTasaCambioInvertida(tasaCambio);
                        tasaCambioRepository.save(newTasaCambio);
                        log.error("CBAZALAR tasaCambio 02c: " + newTasaCambio.toString());
                        valorTasaCambio = newTasaCambio.getValor().setScale(4, BigDecimal.ROUND_HALF_UP);
                    }
                    else{
                        log.error("CBAZALAR tasaCambio 02c: " + tasaCambio.toString());
                        valorTasaCambio = tasaCambio.getValor().setScale(4, BigDecimal.ROUND_HALF_UP);
                    }
                }
                else {
                    fechaActual = DateUtils.sumarRestarDias(fechaActual, -1);
                    log.error("CBAZALAR Ingresando obtenerTasaCambio 02d - fechaActual: " + fechaActual);
                    tasaCambio = this.tasaCambioRepository.getByFechaTasaAndIdMonedaOrigenAndIdMonedaDestino(
//                            fechaActual, monedaBase, monedaCotizacion);
                            fechaActual, monedaDestino, monedaOrigen);
                    if (Optional.ofNullable(tasaCambio).isPresent()) {
                        if(invertirTasa){
                            TasaCambio newTasaCambio = Utils.obtenerTasaCambioInvertida(tasaCambio);
                            tasaCambioRepository.save(newTasaCambio);
                            log.error("CBAZALAR tasaCambio 02e: " + newTasaCambio.toString());
                            valorTasaCambio = newTasaCambio.getValor().setScale(4, BigDecimal.ROUND_HALF_UP);
                        }
                        else{
                            log.error("CBAZALAR tasaCambio 02e: " + tasaCambio.toString());
                            valorTasaCambio = tasaCambio.getValor().setScale(4, BigDecimal.ROUND_HALF_UP);
                        }
                    }
                    else {
                        throw new Exception("No se encontró Tasa de Cambio en la Fecha Actual " + sFecha +" para la moneda: " + monedaCotizacion.getSigla());
                    }
                }
            }
        }
        log.error("CBAZALAR Tasa de Cambio final: " + valorTasaCambio);
        return valorTasaCambio;
    }

    private void obtenerPrecioMoneda(
            LicitacionProveedorTipoCuestionario licitacionProveedorTipoCuestionario,
            Date fechaActual,
            Moneda monedaBase,
            Moneda monedaLicitacion,
            Integer diasMaximoCondicionPago) throws Exception {
        BigDecimal precioSuma = new BigDecimal(0.00);
        Cotizacion cotizacion = this.cotizacionRepository.getByLicitacionAndProveedor(
                licitacionProveedorTipoCuestionario.getLicitacion(),
                licitacionProveedorTipoCuestionario.getProveedor()
        );
        if (!Optional.ofNullable(cotizacion).isPresent()) {
            return;
        }

        /* Obteniendo tasa de cambio */
        Moneda monedaCotizacion = cotizacion.getMoneda();
        BigDecimal tasaCambio = this.obtenerTasaCambio(
                fechaActual,
                monedaBase,
                monedaLicitacion,
                monedaCotizacion);
        licitacionProveedorTipoCuestionario.setTasaCambio(tasaCambio);

        /* Obteniendo suma de precios de la cotizacion */
        List<CotizacionDetalle> listaDetalle = this.cotizacionDetalleRepository.findByCotizacion2(
                licitacionProveedorTipoCuestionario.getLicitacion(),
                licitacionProveedorTipoCuestionario.getProveedor()
        );
        BigDecimal cantidadCotizacion = new BigDecimal(0.00);
        for(CotizacionDetalle bean : listaDetalle) {
            precioSuma = new BigDecimal(
                    precioSuma.floatValue() +
                            bean.getPrecioUnitario().floatValue() * bean.getCantidadCotizada().floatValue() );
            cantidadCotizacion = new BigDecimal(
                    cantidadCotizacion.floatValue() + bean.getCantidadCotizada().floatValue()
            );
        }
        cantidadCotizacion = new BigDecimal(cantidadCotizacion.floatValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
        log.error("CBAZALAR Evaluacion cantidadCotizacion: " + cantidadCotizacion);
        precioSuma = new BigDecimal(
                precioSuma.floatValue() * tasaCambio.floatValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
        licitacionProveedorTipoCuestionario.setPrecioSuma(precioSuma);

        if(precioSuma.floatValue() <= 0.00) {
            return;
        }
        if(cantidadCotizacion.floatValue() <= 0.00) {
            return;
        }

        /* Obteniendo factores */
        BigDecimal baseExponencial = new BigDecimal( 1 + licitacionProveedorTipoCuestionario.getTasaMoneda().floatValue() / 100);
        BigDecimal exponente = new BigDecimal( 1 / licitacionProveedorTipoCuestionario.getFactorDenominadorTasa().floatValue());
        Double exponencial = Math.pow(baseExponencial.doubleValue(), exponente.doubleValue()) - 1;
        BigDecimal tasaInteresMoneda = new BigDecimal(0.00);
        if (licitacionProveedorTipoCuestionario.getCondicionPago().getDiasPago().intValue() != diasMaximoCondicionPago.intValue()) {
            Integer intervalo = diasMaximoCondicionPago.intValue() - licitacionProveedorTipoCuestionario.getCondicionPago().getDiasPago().intValue();
            tasaInteresMoneda = new BigDecimal(exponencial.floatValue() * intervalo.intValue() * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        licitacionProveedorTipoCuestionario.setTasaInteresMoneda(tasaInteresMoneda);
        BigDecimal costoFinanciero = new BigDecimal(tasaInteresMoneda.floatValue() * precioSuma.floatValue() / 100).setScale(2, BigDecimal.ROUND_HALF_UP);
        licitacionProveedorTipoCuestionario.setCostoFinancieroMoneda(costoFinanciero);
        licitacionProveedorTipoCuestionario.setPrecioFinalMoneda(
                new BigDecimal(costoFinanciero.floatValue() + precioSuma.floatValue()).setScale(2, BigDecimal.ROUND_HALF_UP));

        licitacionProveedorTipoCuestionario.setCantidadTotalCotizacion(cantidadCotizacion);
        return;
    }

    private void obtenerPuntajeCondicion(
            LicitacionProveedorTipoCuestionario licitacionProveedorTipoCuestionario) {
        BigDecimal puntajeCondicion = new BigDecimal(0.00);
        List<CotizacionCampoRespuesta> listaDetalle = this.cotizacionCampoRespuestaRepository.
                findLicitacionProveedor(
                        licitacionProveedorTipoCuestionario.getLicitacion(),
                        licitacionProveedorTipoCuestionario.getProveedor()
                );
        if (listaDetalle == null || listaDetalle.size() <= 0) {
            return;
        }

        for(CotizacionCampoRespuesta bean : listaDetalle) {
            puntajeCondicion = new BigDecimal(puntajeCondicion.floatValue() +  bean.getPeso().floatValue() * bean.getPuntaje().floatValue() / 100);
        }
        licitacionProveedorTipoCuestionario.setPuntajeCondicion(puntajeCondicion);
        BigDecimal porcentajeCondicion =
                new BigDecimal(puntajeCondicion.floatValue() * licitacionProveedorTipoCuestionario.getPesoCondicion().floatValue() / 100).setScale(2, BigDecimal.ROUND_HALF_UP);

        licitacionProveedorTipoCuestionario.setPorcentajeCondicionObtenido(porcentajeCondicion);
        return;
    }



    private LicitacionProveedorTipoCuestionario setearValores(
            Licitacion licitacion,
            Proveedor proveedor,
            TipoLicitacion tipoLicitacion,
            TipoLicitacion tipoCuestionario,
            Moneda moneda,
            Date fecha,
            BigDecimal cantidadSolicitada) {
        log.error("Ingresando setearValores: ");
        Licitacion nlicitacion = new Licitacion();
        nlicitacion.setIdLicitacion(licitacion.getIdLicitacion());

        Proveedor nproveedor = new Proveedor();
        nproveedor.setIdProveedor(proveedor.getIdProveedor());

        TipoLicitacion ntipoLicitacion = new TipoLicitacion();
        ntipoLicitacion.setIdTipoLicitacion(tipoLicitacion.getIdTipoLicitacion());

        TipoLicitacion ntipoCuestionario = new TipoLicitacion();
        ntipoCuestionario.setIdTipoLicitacion(tipoCuestionario.getIdTipoLicitacion());

        Moneda nmoneda = new Moneda();
        nmoneda.setIdMoneda(moneda.getIdMoneda());

        LicitacionProveedorTipoCuestionario licitacionProveedorTipoCuestionario = new LicitacionProveedorTipoCuestionario();
        licitacionProveedorTipoCuestionario.setLicitacion(licitacion);
        licitacionProveedorTipoCuestionario.setProveedor(proveedor);
        licitacionProveedorTipoCuestionario.setTipoLicitacion(tipoLicitacion);
        licitacionProveedorTipoCuestionario.setTipoCuestionario(tipoCuestionario);
        licitacionProveedorTipoCuestionario.setMoneda(moneda);

        CondicionPago condicionPago = proveedor.getCondicionPago();
        licitacionProveedorTipoCuestionario.setCondicionPago(condicionPago);
        licitacionProveedorTipoCuestionario.setFactorDenominadorTasa(condicionPago.getFactorDenominadorTasa());
        licitacionProveedorTipoCuestionario.setTasaMoneda(moneda.getTasaMoneda());

        BigDecimal valorCero = new BigDecimal(0.00);
        licitacionProveedorTipoCuestionario.setPesoCondicion(tipoCuestionario.getPesoCondicion());
        licitacionProveedorTipoCuestionario.setPesoMoneda(tipoCuestionario.getPesoMoneda());
        licitacionProveedorTipoCuestionario.setPesoEvaluacionTecnica(tipoCuestionario.getPesoEvaluacionTecnica());

        licitacionProveedorTipoCuestionario.setPrecioSuma(valorCero);
        licitacionProveedorTipoCuestionario.setTasaInteresMoneda(valorCero);
        licitacionProveedorTipoCuestionario.setCostoFinancieroMoneda(valorCero);
        licitacionProveedorTipoCuestionario.setPrecioFinalMoneda(valorCero);
        licitacionProveedorTipoCuestionario.setPrecioFinalMinimoMoneda(valorCero);

        licitacionProveedorTipoCuestionario.setPuntajeCondicion(valorCero);
        licitacionProveedorTipoCuestionario.setPuntajeMaximoCondicion(valorCero);

        licitacionProveedorTipoCuestionario.setPuntajeEvaluacionTecnica(valorCero);

        licitacionProveedorTipoCuestionario.setPorcentajeCondicionObtenido(valorCero);
        licitacionProveedorTipoCuestionario.setPorcentajeEvaluacionTecnicaObtenido(valorCero);
        licitacionProveedorTipoCuestionario.setPorcentajePrecioObtenido(valorCero);
        licitacionProveedorTipoCuestionario.setPorcentajeTotalObtenido(valorCero);

        //licitacionProveedorTipoCuestionario.setFechaTasaCambio(fecha);
        licitacionProveedorTipoCuestionario.setTasaCambio(valorCero);
        licitacionProveedorTipoCuestionario.setCantidadTotalCotizacion(valorCero);
        licitacionProveedorTipoCuestionario.setCantidadTotalLicitacion(cantidadSolicitada);

        log.error("Ingresando setearValores licitacionProveedorTipoCuestionario: " + licitacionProveedorTipoCuestionario.toString02());
        return licitacionProveedorTipoCuestionario;

    }


    @Transactional(readOnly = true)
    public SXSSFWorkbook downloadGanadorDetalleCuadro(Integer idLicitacion) throws Exception{
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        if (!Optional.ofNullable(licitacion).isPresent()) {
            return null;
        }
        List<LicitacionProveedorDetalleEvaluacion> listaDetalleFinal =
                this.licitacionProveedorDetalleEvaluacionRepository.findByIdLicitacionOrdenado(idLicitacion);
        if (!Optional.ofNullable(listaDetalleFinal).isPresent()) {
            return null;
        }
        List<LicitacionProveedorDetalleEvaluacionDto> listaDetalleCuadro = this.obtenerGanadorDetalleCuadro(
                listaDetalleFinal,
                licitacion);
        if (!Optional.ofNullable(listaDetalleCuadro).isPresent()) {
            return null;
        }


        SXSSFWorkbook book = new SXSSFWorkbook(100);
        XSSFWorkbook xbook = book.getXSSFWorkbook();
        SXSSFSheet sheet = book.createSheet();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, "Detallado");
        int nroColumnas = ExcelDefault.createAgrupadoTitleAndWidth(
                xbook,
                sheet,
                this.devuelveNombreSheetAgrupado(),
                this.devuelveNombreSheet(),
                "Resultados - Detalle",
                null);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 13));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 14, 22));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 23, 31));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 32, 40));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 41, 49));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 50, 58));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 59, 67));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 68, 76));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 77, 85));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 86, 94));


        XSSFCellStyle cellStyle01 = ExcelDefault.devuelveCellStyle(xbook, new Color(0, 0, 1), new Color(226, 239, 218), false, (short) 10);
        XSSFCellStyle cellStyle02 = ExcelDefault.devuelveCellStyle(xbook, new Color(0, 0, 192), new Color(255, 255, 255), false, (short) 10);
        List<CellStyle> cellStyleList = null;
        List<CellStyle> cellStyleList01 = ExcelDefault.generarCellStyle(xbook, cellStyle01);
        List<CellStyle> cellStyleList02 = ExcelDefault.generarCellStyle(xbook, cellStyle02);
        boolean filaImpar = true;
        for (LicitacionProveedorDetalleEvaluacionDto bean : listaDetalleCuadro) {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            Row dataRow = sheet.createRow(i + 1);
            int contador = 0;
            if (filaImpar) {
                cellStyleList = cellStyleList01;
            } else {
                cellStyleList = cellStyleList02;
            }
            filaImpar = !filaImpar;
            ExcelDefault.setValueCell(bean.getNombreBienServicioSap(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;

            ExcelDefault.setValueCell(bean.getCantidadSolicitada(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getUnidadMedida().getTextoUm(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getNombreProveedorGanador(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeTotalObtenidoGanador(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;

            ExcelDefault.setValueCell(bean.getNombreProveedor01(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioUnitario01(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getCantidadCotizada01(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getSubTotal01(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioFinalMoneda01(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajePrecioObtenido01(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeCondicionObtenido01(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeEvaluacionTecnicaObtenido01(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeTotalObtenido01(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;

            ExcelDefault.setValueCell(bean.getNombreProveedor02(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioUnitario02(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getCantidadCotizada02(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getSubTotal02(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioFinalMoneda02(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajePrecioObtenido02(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeCondicionObtenido02(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeEvaluacionTecnicaObtenido02(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeTotalObtenido02(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;

            ExcelDefault.setValueCell(bean.getNombreProveedor03(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioUnitario03(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getCantidadCotizada03(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getSubTotal03(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioFinalMoneda03(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajePrecioObtenido03(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeCondicionObtenido03(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeEvaluacionTecnicaObtenido03(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeTotalObtenido03(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;

            ExcelDefault.setValueCell(bean.getNombreProveedor04(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioUnitario04(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getCantidadCotizada04(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getSubTotal04(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioFinalMoneda04(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajePrecioObtenido04(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeCondicionObtenido04(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeEvaluacionTecnicaObtenido04(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeTotalObtenido04(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;

            ExcelDefault.setValueCell(bean.getNombreProveedor05(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioUnitario05(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getCantidadCotizada05(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getSubTotal05(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioFinalMoneda05(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajePrecioObtenido05(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeCondicionObtenido05(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeEvaluacionTecnicaObtenido05(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeTotalObtenido05(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;

            ExcelDefault.setValueCell(bean.getNombreProveedor06(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioUnitario06(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getCantidadCotizada06(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getSubTotal06(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioFinalMoneda06(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajePrecioObtenido06(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeCondicionObtenido06(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeEvaluacionTecnicaObtenido06(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeTotalObtenido06(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;

            ExcelDefault.setValueCell(bean.getNombreProveedor07(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioUnitario07(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getCantidadCotizada07(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getSubTotal07(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioFinalMoneda07(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajePrecioObtenido07(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeCondicionObtenido07(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeEvaluacionTecnicaObtenido07(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeTotalObtenido07(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;

            ExcelDefault.setValueCell(bean.getNombreProveedor08(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioUnitario08(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getCantidadCotizada08(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getSubTotal08(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioFinalMoneda08(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajePrecioObtenido08(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeCondicionObtenido08(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeEvaluacionTecnicaObtenido08(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeTotalObtenido08(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;

            ExcelDefault.setValueCell(bean.getNombreProveedor09(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioUnitario09(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getCantidadCotizada09(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getSubTotal09(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioFinalMoneda09(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajePrecioObtenido09(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeCondicionObtenido09(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeEvaluacionTecnicaObtenido09(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeTotalObtenido09(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;

            ExcelDefault.setValueCell(bean.getNombreProveedor10(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioUnitario10(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getCantidadCotizada10(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getSubTotal10(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPrecioFinalMoneda10(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajePrecioObtenido10(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeCondicionObtenido10(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeEvaluacionTecnicaObtenido10(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(bean.getPorcentajeTotalObtenido10(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;

        }

        int firstColumn = 5;
        int countGoupedColumns = 7;
        sheet.groupColumn(firstColumn, firstColumn + countGoupedColumns - 1);
        firstColumn = 14;
        countGoupedColumns = 7;
        sheet.groupColumn(firstColumn, firstColumn + countGoupedColumns - 1);
        firstColumn = 23;
        countGoupedColumns = 7;
        sheet.groupColumn(firstColumn, firstColumn + countGoupedColumns - 1);
        firstColumn = 32;
        countGoupedColumns = 7;
        sheet.groupColumn(firstColumn, firstColumn + countGoupedColumns - 1);
        firstColumn = 41;
        countGoupedColumns = 7;
        sheet.groupColumn(firstColumn, firstColumn + countGoupedColumns - 1);

        firstColumn = 50;
        countGoupedColumns = 7;
        sheet.groupColumn(firstColumn, firstColumn + countGoupedColumns - 1);
        firstColumn = 59;
        countGoupedColumns = 7;
        sheet.groupColumn(firstColumn, firstColumn + countGoupedColumns - 1);
        firstColumn = 68;
        countGoupedColumns = 7;
        sheet.groupColumn(firstColumn, firstColumn + countGoupedColumns - 1);
        firstColumn = 77;
        countGoupedColumns = 7;
        sheet.groupColumn(firstColumn, firstColumn + countGoupedColumns - 1);
        firstColumn = 86;
        countGoupedColumns = 7;
        sheet.groupColumn(firstColumn, firstColumn + countGoupedColumns - 1);

        return book;
    }


    private String devuelveNombreSheet() {
        return this.CONFIG_TITLE_DETALLE_CUADRO;
    }
    private String devuelveNombreSheetAgrupado() {
        return this.CONFIG_TITLE_DETALLE_CUADRO_AGRUPADO;
    }


}
