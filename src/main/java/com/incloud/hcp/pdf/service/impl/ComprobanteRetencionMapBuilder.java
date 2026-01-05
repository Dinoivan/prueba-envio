package com.incloud.hcp.pdf.service.impl;

import com.incloud.hcp.pdf.bean.FieldComprobanteRetencionPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterComprobanteRetencionPdfDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ComprobanteRetencionMapBuilder {

    private ParameterComprobanteRetencionPdfDTO parameterComprobanteRetencionPdfDTO;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String DASH = "-";
    private static final String rucCopeinca = "20224748711";
    private static final String rucCfgInvestment = "20512868046";

    @Value("${cfg.portal.url}")
    private String stringUrlPortal;

    static ComprobanteRetencionMapBuilder newComprobanteRetencionMapBuilder(ParameterComprobanteRetencionPdfDTO parameterComprobanteRetencionPdfDTO) {
        return new ComprobanteRetencionMapBuilder(parameterComprobanteRetencionPdfDTO);
    }

    public ComprobanteRetencionMapBuilder(ParameterComprobanteRetencionPdfDTO parameterComprobanteRetencionPdfDTO) {
        this.parameterComprobanteRetencionPdfDTO = parameterComprobanteRetencionPdfDTO;
    }

    Map<String, Object> buildParams() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("serieCorrelativo", Optional.ofNullable(parameterComprobanteRetencionPdfDTO.getSerieCorrelativo()).orElse(DASH));
        parameters.put("rucEmisor", Optional.ofNullable(parameterComprobanteRetencionPdfDTO.getRucEmisor()).orElse(DASH));
        parameters.put("nombreEmisor", Optional.ofNullable(parameterComprobanteRetencionPdfDTO.getNombreEmisor()).orElse(DASH));
        parameters.put("direccionEmisor", Optional.ofNullable(parameterComprobanteRetencionPdfDTO.getDireccionEmisor()).orElse(DASH));

        parameters.put("identificacionCliente", Optional.ofNullable(parameterComprobanteRetencionPdfDTO.getIdentificacionCliente()).orElse(DASH));
        parameters.put("nombreCliente", Optional.ofNullable(parameterComprobanteRetencionPdfDTO.getNombreCliente()).orElse(DASH));
        parameters.put("fechaEmision", Optional.ofNullable(parameterComprobanteRetencionPdfDTO.getFechaEmision()).orElse(DASH));
        parameters.put("tasaRetencion", Optional.ofNullable(parameterComprobanteRetencionPdfDTO.getTasaRetencion()).orElse(DASH));

        parameters.put("totalPagado", Optional.ofNullable(parameterComprobanteRetencionPdfDTO.getTotalPagado()).orElse(DASH));
        parameters.put("totalRetenido", Optional.ofNullable(parameterComprobanteRetencionPdfDTO.getTotalRetenido()).orElse(DASH));
        parameters.put("urlPortal", stringUrlPortal);

        if (Optional.ofNullable(parameterComprobanteRetencionPdfDTO.getRucEmisor()).isPresent()) {
            parameters.put("logo", rucCopeinca.equals(parameterComprobanteRetencionPdfDTO.getRucEmisor())  ? "logoCopeinca/COPEINCA.png" : "logoCopeinca/CFGInvestment.png");
        } else {
            parameters.put("logo", "logoCopeinca/CFGInvestment.png");
        }

        return parameters;
}

    List<Map<String, Object>> buildDetails() {
        List<Map<String, Object>> details = new ArrayList<>();

        List<FieldComprobanteRetencionPdfDTO> fieldConformidadServicioPdfDTOList = parameterComprobanteRetencionPdfDTO.getFieldComprobanteRetencionPdfDTOList();

        for (FieldComprobanteRetencionPdfDTO comprobanteRetencionPdfDTO : fieldConformidadServicioPdfDTOList) {
            Map<String, Object> map = new HashMap<>();
            map.put("tipoDocumento", Optional.ofNullable(comprobanteRetencionPdfDTO.getTipoDocumento()).orElse("").toUpperCase());
            map.put("numeroDocumento", Optional.ofNullable(comprobanteRetencionPdfDTO.getNumeroDocumento()).orElse(""));
            map.put("fechaEmision", Optional.ofNullable(comprobanteRetencionPdfDTO.getFechaEmision()).orElse(""));
            map.put("moneda", Optional.ofNullable(comprobanteRetencionPdfDTO.getMoneda()).orElse(""));
            map.put("importeOperacion", Optional.ofNullable(comprobanteRetencionPdfDTO.getImporteOperacion()).orElse(""));
            map.put("fechaPago", Optional.ofNullable(comprobanteRetencionPdfDTO.getFechaPago()).orElse(""));
            map.put("numeroPago", Optional.ofNullable(comprobanteRetencionPdfDTO.getNumeroPago()).orElse(""));
            map.put("importePago", Optional.ofNullable(comprobanteRetencionPdfDTO.getImportePago()).orElse(""));
            map.put("totalRetenido", Optional.ofNullable(comprobanteRetencionPdfDTO.getTotalRetenido()).orElse(""));
            map.put("totalPagado", Optional.ofNullable(comprobanteRetencionPdfDTO.getTotalPagado()).orElse(""));
            details.add(map);
        }
        return details;
    }


//    private String getContenidoQR(String retention) {
//        StringBuilder qrContent = new StringBuilder();
//        qrContent.append(retention.getAgentParty().getPartyIdentification().getId().getValue()).append("|");
//        qrContent.append(COD_RETENTION).append("|");
//        qrContent.append(retention.getId()).append("|").append("|");
//        qrContent.append(retention.getTotalInvoiceAmount().getValue()).append("|");
//        qrContent.append(retention.getIssueDate()).append("|");
//        qrContent.append(retention.getReceiverParty().getPartyIdentification().getId().getSchemeID()).append("|");
//        qrContent.append(retention.getReceiverParty().getPartyIdentification().getId().getValue()).append("|");
////        qrContent.append(digestValue).append("|");
////        qrContent.append(signatureValue).append("|");
//        return qrContent.toString();
//    }
//
//    private String generateQRCode(String contenido) {
//        try {
//            File temp = File.createTempFile("tmp", ".png");
//            FileOutputStream fos = new FileOutputStream(temp);
//            try {
//                qrServices.generateQRCode(contenido, fos);
//            } catch (Exception ex) {
//                logger.error(ex.getMessage());
//            }
//
//            return temp.getAbsolutePath();
//        } catch (IOException ex) {
//            logger.error(ex.getMessage());
//        }
//        return null;
//    }
}
