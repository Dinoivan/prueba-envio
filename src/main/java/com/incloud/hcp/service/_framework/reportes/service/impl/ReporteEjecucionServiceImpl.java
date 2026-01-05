package com.incloud.hcp.service._framework.reportes.service.impl;

import com.incloud.hcp.config.SystemLoggedUser;
import com.incloud.hcp.service._framework.reportes.bean.ReporteParams;
import com.incloud.hcp.service._framework.reportes.enums.TipoReporteJasperEnum;
import com.incloud.hcp.service._framework.reportes.service.ReporteEjecucionService;
import com.incloud.hcp.util.DateUtils;
import com.sap.cloud.security.xsuaa.token.Token;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class ReporteEjecucionServiceImpl implements ReporteEjecucionService {

    private final String RUTA_JASPER = "reportes/jasper/";
    private final String RUTA_JRXML = "reportes/jrxml/";
    private final String SUFIJO_JASPER = ".jasper";
    private final String SUFIJO_JRXML = ".jrxml";

    @Autowired
    private SystemLoggedUser systemLoggedUser;

    @Override
    public ReporteParams inicializaReporte(
            String jrxmlReporte,
            TipoReporteJasperEnum tipoReporteJasperEnum,
            Map parameterMap,
            List<?> listaData,
            Token token
            ) throws Exception {
        ReporteParams reporteParams = new ReporteParams();
        reporteParams.setNombreReporte(jrxmlReporte);
        reporteParams.setListaDataReporte(listaData);
        reporteParams.setParameterMap(parameterMap);
        reporteParams.setTipoReporteJasper(tipoReporteJasperEnum.getEstado());

        reporteParams.setUserDetails(this.systemLoggedUser.getUserSession(token));
        reporteParams.setFechaActual(DateUtils.obtenerFechaHoraActual());

        String jasperNombreReporte = RUTA_JASPER + jrxmlReporte + SUFIJO_JASPER;
        ClassPathResource reportResourceReporte = new ClassPathResource(jasperNombreReporte);
        if (!reportResourceReporte.exists()) {
            boolean generoJasper = this.generateJasper(jrxmlReporte);
            if (generoJasper) {
                reportResourceReporte = new ClassPathResource(jasperNombreReporte);
            }
            else  {
                throw new Exception("No se encontró Reporte: " + jasperNombreReporte);
            }
        }
        reporteParams.setInputStreamReporte(reportResourceReporte.getInputStream());
        return reporteParams;
    }

    @Override
    public ByteArrayResource executeReporte(ReporteParams reportParams) throws Exception {
        String tipoReporteJasper = reportParams.getTipoReporteJasper();
        switch (tipoReporteJasper)
        {
            case "PDF":
                return this.exportReportToPDF(
                        reportParams.getInputStreamReporte(),
                        reportParams.getParameterMap(),
                        reportParams.getListaDataReporte());

            case "PDF_DESCARGAR":
                return this.exportReportToPDFDescargar(
                        reportParams.getInputStreamReporte(),
                        reportParams.getParameterMap(),
                        reportParams.getListaDataReporte());

            case "DOCX":
                return this.exportReportToDOCX(
                        reportParams.getInputStreamReporte(),
                        reportParams.getParameterMap(),
                        reportParams.getListaDataReporte());

            default:
                return null;
        }

    }

    @Override
    public ByteArrayResource executeReporteVirtualizado(ReporteParams reportParams) throws Exception {
        return null;
    }

    private boolean generateJasper(String reporteJrxml) throws Exception {
        String jrxmlNombrePlantilla = RUTA_JRXML + reporteJrxml + SUFIJO_JRXML;
        String jasperNombrePlantilla = RUTA_JASPER + reporteJrxml + SUFIJO_JASPER;
        ClassPathResource reportResourceReporte = new ClassPathResource(jrxmlNombrePlantilla);

        if (reportResourceReporte.exists()) {
            JasperReport jasperReport = JasperCompileManager.compileReport(reportResourceReporte.getInputStream());
            JRSaver.saveObject(jasperReport, jasperNombrePlantilla);
            return true;
        }
        return false;
    }

    /**
     * Method for exporting report to PDF.
     *
     * @param targetStream - target report stream
     * @param parameters - generated parameters
     * @return byte array resource (file content)
     */
    private ByteArrayResource exportReportToPDF(InputStream targetStream, Map<String, Object> parameters, List<?> detail) throws Exception
    {

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(detail);
        JasperPrint jasperPrint = JasperFillManager.fillReport(targetStream, parameters, dataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        byte[] reportContent = outputStream.toByteArray();
        return new ByteArrayResource(reportContent);

    }

    /**
     * Method for exporting report to PDF.
     *
     * @param targetStream - target report stream
     * @param parameters - generated parameters
     * @return byte array resource (file content)
     */
    private ByteArrayResource exportReportToPDFDescargar(InputStream targetStream, Map<String, Object> parameters, List<?> detail) throws Exception
    {

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(detail);
        JasperPrint jasperPrint = JasperFillManager.fillReport(targetStream, parameters, dataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();

        byte[] reportContent = outputStream.toByteArray();
        return new ByteArrayResource(reportContent);

    }

    /**
     * Method for exporting report to DOCx format
     *
     * @param targetStream - target report stream
     * @param parameters - generated parameters
     * @return byte array resource (generated report file).
     */
    private ByteArrayResource exportReportToDOCX(InputStream targetStream, Map<String, Object> parameters, List<?> detail) throws Exception
    {
        JRDataSource dataSource = new JRBeanCollectionDataSource(detail);
        JasperPrint jasperPrint = JasperFillManager.fillReport(targetStream, parameters, dataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();

        byte[] reportContent = outputStream.toByteArray();
        return new ByteArrayResource(reportContent);
    }
}
