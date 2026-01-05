package com.incloud.hcp.pdf.service.impl;

import com.incloud.hcp.pdf.exception.PdfException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GenericJasperGenerator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public byte[] build(JasperReport jasperReport, Map<String, Object> params, JRBeanCollectionDataSource jrDataSource) {
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrDataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex.getCause());
            throw new PdfException(ex.getMessage(), ex.getCause());
        }
    }

}
