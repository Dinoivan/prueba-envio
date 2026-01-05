package com.incloud.hcp.jco.comprobanteRetencion.service.impl;

import com.incloud.hcp.enums.ComprobanteRetencionEstadoEnum;
import com.incloud.hcp.enums.ComprobanteTipoEnum;
import com.incloud.hcp.jco.comprobanteRetencion.dto.*;
import com.incloud.hcp.jco.comprobanteRetencion.service.JCOComprobanteRetencionService;
import com.incloud.hcp.repository.SociedadRepository;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class JCOComprobanteRetencionServiceImpl implements JCOComprobanteRetencionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${destination.rfc.profit}")
    private String destinationProfit;


    @Override
    public List<ComprobanteRetencionDto> extraerComprobanteRetencionListRFC(String fechaInicio, String fechaFin, String sociedad, String ruc) throws Exception {
        try {
            String FUNCION_RFC = "ZPE_MM_RETENCIONES";

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            this.mapFilters(jCoFunction, fechaInicio, fechaFin, sociedad, ruc);
            jCoFunction.execute(destination);

//            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            JCoParameterList exportParameterList = jCoFunction.getExportParameterList();
            logger.error("BUSQUEDA COMPROBANTES DE RETENCION EN SAP [DATA]: exportParameterList = " + (exportParameterList == null ? "NULL" : "OK"));
            ComprobanteRetencionExtractorMapper comprobanteRetencionExtractorMapper = ComprobanteRetencionExtractorMapper.newMapper(exportParameterList);

            List<SapTableCRHeaderDto> crHeaderDtoList = Optional.ofNullable(comprobanteRetencionExtractorMapper.getHeaderDtoList()).orElse(new ArrayList<>());
            List<SapTableCRItemDto> crItemDtoList = Optional.ofNullable(comprobanteRetencionExtractorMapper.getItemDtoList()).orElse(new ArrayList<>());
            List<SapTableCRTotalDto> crTotalDtoList = Optional.ofNullable(comprobanteRetencionExtractorMapper.getTotalDtoList()).orElse(new ArrayList<>());
            List<SapTableCRSociedadDto> crSociedadDtoList = Optional.ofNullable(comprobanteRetencionExtractorMapper.getSociedadDtoList()).orElse(new ArrayList<>());

            logger.error("BUSQUEDA COMPROBANTES DE RETENCION EN SAP [ENCONTRADOS]: comprobantes = " + crHeaderDtoList.size());
            logger.error("BUSQUEDA COMPROBANTES DE RETENCION EN SAP [ENCONTRADOS]: items = " + crItemDtoList.size());


            List <ComprobanteRetencionDto> comprobanteRetencionDtoList = new ArrayList<>();

            crHeaderDtoList.forEach(h -> {
                ComprobanteRetencionDto compRetencionDto = new ComprobanteRetencionDto();

                compRetencionDto.setNumeroDocumentoErp(h.getNumeroDocumentoErp());
                compRetencionDto.setSociedad(h.getSociedad());
                compRetencionDto.setEjercicio(h.getEjercicio());
                compRetencionDto.setTipoComprobante(ComprobanteTipoEnum.RETENCION.getDescripcion());
                compRetencionDto.setSerie(h.getSerie());
                compRetencionDto.setCorrelativo(h.getCorrelativo());
                compRetencionDto.setProveedorRuc(h.getProveedorRuc());
                compRetencionDto.setProveedorRazonSocial(h.getProveedorRazonSocial());
                compRetencionDto.setFechaEmision(h.getFechaEmision());
                compRetencionDto.setEstado(h.getEstado().equalsIgnoreCase(ComprobanteRetencionEstadoEnum.ANULADO.getCodigo()) ? ComprobanteRetencionEstadoEnum.ANULADO.getDescripcion() : ComprobanteRetencionEstadoEnum.ACEPTADO.getDescripcion() );
                compRetencionDto.setMoneda(h.getMoneda());
                compRetencionDto.setTasaRetencion(h.getTasaRetencion());

                crTotalDtoList.stream()
                        .filter (t -> t.getNumeroDocumentoErp().equals(h.getNumeroDocumentoErp()) && t.getSociedad().equals(h.getSociedad()) && (t.getEjercicio().compareTo(h.getEjercicio()) == 0))
                        .findFirst()
                        .ifPresent(t -> compRetencionDto.setImporteRetencionTotalSoles(t.getImporteTotalRetencionMonedaLocal()));

                crSociedadDtoList.stream()
                        .filter (s -> s.getSociedad().equals(h.getSociedad()))
                        .findFirst()
                        .ifPresent(s -> {
                            compRetencionDto.setSociedadRazonSocial(s.getRazonSocial());
                            compRetencionDto.setSociedadDireccion1(s.getCalle() + (!s.getNumeroEdificio().equals("") ? (" - Nro " + s.getNumeroEdificio()) : ""));
                            compRetencionDto.setSociedadDireccion2(s.getPoblacion() + " - " + s.getDistrito());
                            compRetencionDto.setSociedadTelefono(s.getTelefono());
                            compRetencionDto.setSociedadRuc(s.getRuc());
                        });

                List<SapTableCRItemDto> filteredItemDtoList = crItemDtoList.stream()
                        .filter (i -> i.getNumeroDocumentoErp().equals(h.getNumeroDocumentoErp()) && i.getSociedad().equals(h.getSociedad()) && (i.getEjercicio().compareTo(h.getEjercicio()) == 0))
                        .collect(Collectors.toList());

                BigDecimal importeNetoPagadoTotalSoles = filteredItemDtoList.stream()
                        .map(SapTableCRItemDto::getImporteNetoSoles)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .setScale(2, BigDecimal.ROUND_HALF_UP);

                compRetencionDto.setImporteNetoPagadoTotalSoles(importeNetoPagadoTotalSoles);

                List<ComprobanteRetencionItemDto> comprobanteRetencionItemDtoList = new ArrayList<>();

                filteredItemDtoList.forEach(i -> {
                    ComprobanteRetencionItemDto compRetencionItemDto = new ComprobanteRetencionItemDto();

                    compRetencionItemDto.setNumeroDocumentoErp(i.getNumeroDocumentoErp());
                    compRetencionItemDto.setSociedad(i.getSociedad());
                    compRetencionItemDto.setEjercicio(i.getEjercicio());
                    compRetencionItemDto.setTipoComprobante(i.getTipoComprobante().equals(ComprobanteTipoEnum.FACTURA.getCodigo()) ? ComprobanteTipoEnum.FACTURA.getDescripcion() : ""); // solo reconoce tipo FACTURA
                    compRetencionItemDto.setSerieFactura(i.getSerieFactura());
                    compRetencionItemDto.setCorrelativoFactura(i.getCorrelativoFactura());
                    compRetencionItemDto.setFechaEmision(i.getFechaEmision());
                    compRetencionItemDto.setMoneda(i.getMoneda());
                    compRetencionItemDto.setImporteTotalComprobante(i.getImporteTotalComprobante());
                    compRetencionItemDto.setFechaPago(h.getFechaEmision());
                    compRetencionItemDto.setNumeroPago("1"); // hardcode a la fuerza
                    compRetencionItemDto.setImportePago(i.getImportePago());
                    compRetencionItemDto.setImporteRetencionSoles(i.getImporteRetencionSoles());
                    compRetencionItemDto.setImporteNetoSoles(i.getImporteNetoSoles());

                    comprobanteRetencionItemDtoList.add(compRetencionItemDto);
                });

                compRetencionDto.setComprobanteRetencionItemDtoList(comprobanteRetencionItemDtoList);
                comprobanteRetencionDtoList.add(compRetencionDto);
            });

            return comprobanteRetencionDtoList;

        }
        catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }


    private void mapFilters(JCoFunction function, String fechaInicio, String fechaFin, String sociedad, String ruc) {
        JCoParameterList paramList = function.getImportParameterList();

        if (!fechaInicio.isEmpty() && !fechaFin.isEmpty()){
            paramList.setValue("I_DATE_INI", fechaInicio);
            paramList.setValue("I_DATE_FIN", fechaFin);
        }

        if (sociedad != null && !sociedad.isEmpty()){
            paramList.setValue("I_BUKRS", sociedad);
        }

        if (ruc != null && !ruc.isEmpty()){
            paramList.setValue("I_STCD1", ruc);
        }

        JCoTable jcoTableWITHT = paramList.getTable("IT_RGE_WITHT");
        jcoTableWITHT.appendRow();
        jcoTableWITHT.setRow(0);
        jcoTableWITHT.setValue("SIGN", "I");
        jcoTableWITHT.setValue("OPTION", "EQ");
        jcoTableWITHT.setValue("LOW", "RE");

        jcoTableWITHT.appendRow();
        jcoTableWITHT.setRow(1);
        jcoTableWITHT.setValue("SIGN", "I");
        jcoTableWITHT.setValue("OPTION", "EQ");
        jcoTableWITHT.setValue("LOW", "RM");


        JCoTable jcoTableWITHCD = paramList.getTable("IT_RGE_WITHCD");
        jcoTableWITHCD.appendRow();
        jcoTableWITHCD.setRow(0);
        jcoTableWITHCD.setValue("SIGN", "I");
        jcoTableWITHCD.setValue("OPTION", "EQ");
        jcoTableWITHCD.setValue("LOW", "R1");
    }
}