package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.domain.Prefactura;
import com.incloud.hcp.dto.PrefacturaAprobacionDTO;
import com.incloud.hcp.jco.comprobantePago.dto.ComprobantePagoDto;
import com.incloud.hcp.jco.comprobantePago.service.JCOComprobantePagoService;
import com.incloud.hcp.myibatis.mapper.PrefacturaNeoMapper;
import com.incloud.hcp.service.LogTransaccionService;
import com.incloud.hcp.service.OrdenCompraService;
import com.incloud.hcp.service.PrefacturaNeoService;
import com.incloud.hcp.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PrefacturaNeoServiceImpl implements PrefacturaNeoService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PrefacturaNeoMapper prefacturaNeoMapper;

    @Autowired
    private JCOComprobantePagoService jcoComprobantePagoService;

    @Autowired
    private LogTransaccionService logTransaccionService;

    @Autowired
    private OrdenCompraService ordenCompraService;

    @Override
    public List<PrefacturaAprobacionDTO> getPrefacturaListPg(Date fechaEmisionInicio, Date fechaEmisionFin,
                                                             Date fechaEntradaInicio, Date fechaEntradaFin,
                                                             String ruc,
                                                             String referencia,
                                                             String comprador,
                                                             String centro,
                                                             Integer idEstado,
                                                             Integer nroRegistros,
                                                             Integer paginaMostrar,
                                                             String flagPaginador) throws Exception {
        if (comprador != null)
            comprador = "%" + comprador + "%";

        if (centro != null)
            centro = "%" + centro + "%";

        List<Prefactura> lstPrefactura = new ArrayList<>();
        List<PrefacturaAprobacionDTO> lstPrefacturaNeo = new ArrayList<>();
        List<PrefacturaAprobacionDTO> lstPrefacturaAprobacionDTO = new ArrayList<>();
        log.error("<--LOG_MC-->:fechaEmisionInicio:"+fechaEmisionInicio);
        log.error("<--LOG_MC-->:fechaEmisionFin:"+fechaEmisionFin);
        log.error("<--LOG_MC-->:fechaEntradaInicio:"+fechaEntradaInicio);
        log.error("<--LOG_MC-->:fechaEntradaFin:"+fechaEntradaFin);
        log.error("<--LOG_MC-->:ruc:"+ruc);
        log.error("<--LOG_MC-->:referencia:"+referencia);
        log.error("<--LOG_MC-->:comprador:"+comprador);
        log.error("<--LOG_MC-->:centro:"+centro);
        log.error("<--LOG_MC-->:idEstado:"+idEstado);
        log.error("<--LOG_MC-->:nroRegistros:"+nroRegistros);
        log.error("<--LOG_MC-->:paginaMostrar:"+paginaMostrar);
        log.error("<--LOG_MC-->:flagPaginador:"+flagPaginador);
//        lstPrefactura = prefacturaRepository.getPrefacturaByAllFilters(fechaEmisionInicio, fechaEmisionFin, fechaEntradaInicio, DateUtils.utilDateAtEndOfDay(fechaEntradaFin), ruc, referencia, comprador, centro, idEstado);
        lstPrefacturaNeo = prefacturaNeoMapper.getPrefacturaListPg(
                fechaEmisionInicio,
                fechaEmisionFin,
                fechaEntradaInicio,
//                DateUtils.utilDateAtEndOfDay(fechaEntradaFin),
                fechaEntradaFin,
                ruc,
                referencia,
                comprador,
                centro,
                idEstado,
                nroRegistros,
                paginaMostrar,
                flagPaginador
        );
        log.error("<--LOG_MC-->:lstPrefacturaNeo-Size:"+lstPrefacturaNeo.size());
        log.error("<--LOG_MC-->:lstPrefacturaNeo:"+lstPrefacturaNeo);
        if(flagPaginador != null) {
            log.error("<--LOG_MC-->lstPrefactura::" + lstPrefacturaNeo);
            String fechaInicioSapString = fechaEmisionInicio != null ? DateUtils.utilDateToSapString(fechaEmisionInicio) : "";
            String fechaFinSapString = fechaEmisionFin != null ? DateUtils.utilDateToSapString(fechaEmisionFin) : "";

            if (fechaInicioSapString.equals("")) {
                fechaInicioSapString = fechaEntradaInicio != null ? DateUtils.utilDateToSapString(fechaEntradaInicio) : "";
            }
            if (fechaFinSapString.equals("")) {
                fechaFinSapString = fechaEntradaFin != null ? DateUtils.utilDateToSapString(fechaEntradaFin) : "";
            }


//        logger.error("<--LOG_MC-->comprobantePagoDtoList::"+comprobantePagoDtoList);

            for (PrefacturaAprobacionDTO it : lstPrefacturaNeo) {
                PrefacturaAprobacionDTO prefacturaAprobacionDTO = new PrefacturaAprobacionDTO();
                prefacturaAprobacionDTO.setId(it.getId());
                prefacturaAprobacionDTO.setEstadoPrefactura(it.getEstadoPrefactura());
                prefacturaAprobacionDTO.setIdEstadoPrefactura(it.getIdEstadoPrefactura());
                prefacturaAprobacionDTO.setSociedad(it.getSociedad());
                prefacturaAprobacionDTO.setCodigoSociedad(it.getCodigoSociedad());
                prefacturaAprobacionDTO.setProveedorRuc(it.getProveedorRuc());
                prefacturaAprobacionDTO.setProveedorRazonSocial(it.getProveedorRazonSocial());
                prefacturaAprobacionDTO.setFechaEmision(it.getFechaEmision());
                prefacturaAprobacionDTO.setFechaContabilizacion(it.getFechaContabilizacion());
                prefacturaAprobacionDTO.setFechaBase(it.getFechaBase());
                prefacturaAprobacionDTO.setIndicadorImpuesto(it.getIndicadorImpuesto());
                prefacturaAprobacionDTO.setReferencia(it.getReferencia());
                prefacturaAprobacionDTO.setObservaciones(it.getObservaciones());
                prefacturaAprobacionDTO.setCadenaNumerosOrdenCompra(it.getCadenaNumerosOrdenCompra());
                prefacturaAprobacionDTO.setCadenaNumerosGuia(it.getCadenaNumerosGuia());
                prefacturaAprobacionDTO.setCodigoSap(it.getCodigoSap());
                prefacturaAprobacionDTO.setEjercicio(it.getEjercicio());
                prefacturaAprobacionDTO.setNumeroDocumentoContable(it.getNumeroDocumentoContable());
                prefacturaAprobacionDTO.setCodigoMondeda(it.getCodigoMondeda());
                prefacturaAprobacionDTO.setSubTotal(it.getSubTotal());
                prefacturaAprobacionDTO.setIgv(it.getIgv());
                prefacturaAprobacionDTO.setTotal(it.getTotal());
                prefacturaAprobacionDTO.setFechaRecepcion(it.getFechaRecepcion());
                prefacturaAprobacionDTO.setFechaDescarte(it.getFechaDescarte());
                prefacturaAprobacionDTO.setFechaRegistroSap(it.getFechaRegistroSap());
                prefacturaAprobacionDTO.setUsuarioRegistroSap(it.getUsuarioRegistroSap());
                prefacturaAprobacionDTO.setXmlEcmPath(it.getXmlEcmPath());
                prefacturaAprobacionDTO.setPdfEcmPath(it.getPdfEcmPath());
                prefacturaAprobacionDTO.setCentro(it.getCentro());
                prefacturaAprobacionDTO.setUsuarioComprador(it.getUsuarioComprador());
                prefacturaAprobacionDTO.setMotivoRechazo(it.getMotivoRechazo());
                prefacturaAprobacionDTO.setFechaPago(it.getFechaPago());
                prefacturaAprobacionDTO.setFechaVencimiento(it.getFechaVencimiento());

                prefacturaAprobacionDTO.setXmlEcmPathCf(it.getXmlEcmPathCf());
                prefacturaAprobacionDTO.setPdfEcmPathCf(it.getPdfEcmPathCf());
                prefacturaAprobacionDTO.setArchivoIdXmlCf(it.getArchivoIdXmlCf());
                prefacturaAprobacionDTO.setArchivoIdPdfCf(it.getArchivoIdPdfCf());

                lstPrefacturaAprobacionDTO.add(prefacturaAprobacionDTO);
            }


            for (PrefacturaAprobacionDTO prefacturaAprobacionDTOIt : lstPrefacturaAprobacionDTO) {
                prefacturaAprobacionDTOIt.setFechaPago(prefacturaAprobacionDTOIt.getFechaPago());
                prefacturaAprobacionDTOIt.setFechaVencimiento(prefacturaAprobacionDTOIt.getFechaVencimiento());
                //Mejora 3 :: Item 7
                /*
                List<ComprobantePagoDto> comprobantePagoDtoList = new ArrayList<>();
                comprobantePagoDtoList = jcoComprobantePagoService.extraerComprobantePagoListRFC(fechaInicioSapString, fechaFinSapString, "", prefacturaAprobacionDTOIt.getProveedorRuc());
                for (ComprobantePagoDto itx : comprobantePagoDtoList) {
                    if (itx.getNumeroDocumentoContable() != null) {
                        if (!itx.getNumeroDocumentoContable().equals("")) {
                            if (prefacturaAprobacionDTOIt.getNumeroDocumentoContable() != null && prefacturaAprobacionDTOIt.getNumeroDocumentoContable().equals(itx.getNumeroDocumentoContable())) {
                                prefacturaAprobacionDTOIt.setFechaPago(itx.getFechaRealPago());
                                prefacturaAprobacionDTOIt.setFechaVencimiento(itx.getFechaBase());
                            }
                        }
                    }
                }
                */

                //Mejora 3 :: Item 3-5
                log.error("<--LOG_MC-->:prefacturaAprobacionDTOIt.getIdEstadoPrefactura():"+prefacturaAprobacionDTOIt.getIdEstadoPrefactura());
                if (prefacturaAprobacionDTOIt.getIdEstadoPrefactura() == 2) {
                    log.error("<--LOG_MC-->:getIdEstadoPrefactura_getIdEstadoPrefactura 2:");
                    prefacturaAprobacionDTOIt.setUsuarioUltimoEstado("");
                    prefacturaAprobacionDTOIt.setFechaUltimoEstado(prefacturaAprobacionDTOIt.getFechaRegistroSap());
                    log.error("<--LOG_MC-->:prefacturaAprobacionDTOIt.getFechaRegistroSap():"+prefacturaAprobacionDTOIt.getFechaRegistroSap());
                    if (prefacturaAprobacionDTOIt.getFechaRegistroSap() != null)
                        prefacturaAprobacionDTOIt.setUsuarioUltimoEstado(prefacturaAprobacionDTOIt.getUsuarioRegistroSap());
                } else if (prefacturaAprobacionDTOIt.getIdEstadoPrefactura() == 3) {
                    log.error("<--LOG_MC-->:getIdEstadoPrefactura_getIdEstadoPrefactura 3:");
                    prefacturaAprobacionDTOIt.setUsuarioUltimoEstado("");
                    prefacturaAprobacionDTOIt.setFechaUltimoEstado(prefacturaAprobacionDTOIt.getFechaDescarte());
                    prefacturaAprobacionDTOIt.setUsuarioUltimoEstado(logTransaccionService.getUsuarioTransaccionBD("BD_MODIFICAR", "PREFACTURA", prefacturaAprobacionDTOIt.getId()));
                } else if (prefacturaAprobacionDTOIt.getIdEstadoPrefactura() == 5) {
                    log.error("<--LOG_MC-->:getIdEstadoPrefactura_getIdEstadoPrefactura 5:");
                    prefacturaAprobacionDTOIt.setUsuarioUltimoEstado("");
                    prefacturaAprobacionDTOIt.setUsuarioUltimoEstado(prefacturaAprobacionDTOIt.getUsuarioRegistroSap());
                    prefacturaAprobacionDTOIt.setFechaUltimoEstado(prefacturaAprobacionDTOIt.getFechaDescarte());
                } else {
                    log.error("<--LOG_MC-->:getIdEstadoPrefactura_getIdEstadoPrefactura otro:"+prefacturaAprobacionDTOIt.getIdEstadoPrefactura());
                    prefacturaAprobacionDTOIt.setUsuarioUltimoEstado("");
                    prefacturaAprobacionDTOIt.setUsuarioUltimoEstado(prefacturaAprobacionDTOIt.getUsuarioRegistroSap());
                    prefacturaAprobacionDTOIt.setFechaUltimoEstado(prefacturaAprobacionDTOIt.getFechaRegistroSap());
                }

                //Mejora 3 :: Item 6
                OrdenCompra oc = ordenCompraService.getOrdenCompraByNOCompra(prefacturaAprobacionDTOIt.getCadenaNumerosOrdenCompra());
                if (oc != null) {
                    prefacturaAprobacionDTOIt.setCondicionPago(oc.getCondicionPago() + "-" + oc.getCondicionPagoDescripcion());
                }

            }
            log.error("<--LOG_MC-->Con flagPaginator:lstPrefacturaNeo-Size:"+lstPrefacturaNeo.size());
            log.error("<--LOG_MC-->Con flagPaginator:lstPrefacturaNeo-Size:"+lstPrefacturaNeo.toString());
//                lstPrefacturaAprobacionDTO = lstPrefacturaNeo;
            log.error("<--LOG_MC-->Con flagPaginator:lstPrefacturaAprobacionDTO-Size:"+lstPrefacturaAprobacionDTO.size());
            log.error("<--LOG_MC-->Con flagPaginator:lstPrefacturaAprobacionDTO-Size:"+lstPrefacturaAprobacionDTO.toString());
        }else{
            log.error("<--LOG_MC-->Sin flagPaginator:lstPrefacturaNeo-Size:"+lstPrefacturaNeo.size());
            log.error("<--LOG_MC-->Sin flagPaginator:lstPrefacturaNeo-Size:"+lstPrefacturaNeo.toString());
            lstPrefacturaAprobacionDTO = lstPrefacturaNeo;
            log.error("<--LOG_MC-->Sin flagPaginator:lstPrefacturaAprobacionDTO-Size:"+lstPrefacturaAprobacionDTO.size());
            log.error("<--LOG_MC-->Sin flagPaginator:lstPrefacturaAprobacionDTO-Size:"+lstPrefacturaAprobacionDTO.toString());
        }
        log.error("<--LOG_MC-->Finally:lstPrefacturaAprobacionDTO-Size:"+lstPrefacturaAprobacionDTO.size());
        log.error("<--LOG_MC-->Finally:lstPrefacturaAprobacionDTO-Size:"+lstPrefacturaAprobacionDTO.toString());
        return lstPrefacturaAprobacionDTO;
    }
}
