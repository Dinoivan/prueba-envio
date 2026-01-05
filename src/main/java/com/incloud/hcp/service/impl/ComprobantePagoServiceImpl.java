package com.incloud.hcp.service.impl;

import com.incloud.hcp.jco.comprobantePago.dto.ComprobantePagoDto;
import com.incloud.hcp.jco.comprobantePago.service.JCOComprobantePagoService;
import com.incloud.hcp.service.ComprobantePagoService;
import com.incloud.hcp.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ComprobantePagoServiceImpl implements ComprobantePagoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JCOComprobantePagoService jcoComprobantePagoService;

    @Autowired
    public ComprobantePagoServiceImpl(JCOComprobantePagoService jcoComprobantePagoService) {
        this.jcoComprobantePagoService = jcoComprobantePagoService;
    }

    @Override
    public List<ComprobantePagoDto> getComprobantePagoListPorFechasAndRuc(Date fechaInicio, Date fechaFin, String ruc, String numeroComprobantePago, String modulo) throws Exception {
        List<ComprobantePagoDto> comprobantePagoDtoList = new ArrayList<>();
        numeroComprobantePago = numeroComprobantePago != null ? numeroComprobantePago : "";

        if(modulo != "X") {
            ruc = ruc != null ? ruc : "";
        }

        String fechaInicioSapString = fechaInicio != null ? DateUtils.utilDateToSapString(fechaInicio) : "";
        String fechaFinSapString = fechaFin != null ? DateUtils.utilDateToSapString(fechaFin) : "";

        logger.error("BUSQUEDA COMPROBANTES DE PAGO EN SAP [FILTRO]: fechaInicio = " + fechaInicioSapString);
        logger.error("BUSQUEDA COMPROBANTES DE PAGO EN SAP [FILTRO]: fechaFin = " + fechaFinSapString);
        logger.error("BUSQUEDA COMPROBANTES DE PAGO EN SAP [FILTRO]: numeroComprobantePago = " + numeroComprobantePago);
        logger.error("BUSQUEDA COMPROBANTES DE PAGO EN SAP [FILTRO]: ruc = " + ruc);

        if(!numeroComprobantePago.isEmpty() || (!fechaInicioSapString.isEmpty() && !fechaFinSapString.isEmpty())) {
            comprobantePagoDtoList = jcoComprobantePagoService.extraerComprobantePagoListRFC(fechaInicioSapString, fechaFinSapString, numeroComprobantePago, ruc);
        }

        return comprobantePagoDtoList;
    }
}