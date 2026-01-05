package com.incloud.hcp.pdf.service.impl;

import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPdfDto;
import com.incloud.hcp.pdf.PdfGeneratorFactory;
import com.incloud.hcp.pdf.bean.*;
import com.incloud.hcp.pdf.exception.PdfException;
import com.incloud.hcp.pdf.service.PdfGeneratorService;
import com.incloud.hcp.pdf.util.ResourceUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class PdfGeneratorServiceJasperImpl extends GenericJasperGenerator implements PdfGeneratorService {

    private static PdfGeneratorService pdfService;
    private JasperReport jasperReport;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static PdfGeneratorService getInstance(){
        synchronized (PdfGeneratorServiceJasperImpl.class){
            if(pdfService == null){
                pdfService =new PdfGeneratorServiceJasperImpl();
            }
            return pdfService;
        }
    }

    @Override
    public byte[] generateEntradaMercaderia(ParameterEntradaMercaderiaPdfDTO parameterEntradaMercaderiaPdfDTO) {
        try {
            EntregaMercaderiaMapBuilder entregaMercaderiaMapBuilder = EntregaMercaderiaMapBuilder.newEntradaMercaderiaMapBuilder(parameterEntradaMercaderiaPdfDTO);
            Map<String, Object> parametersEntregaMercaderia = entregaMercaderiaMapBuilder.buildParams();
            List<Map<String, Object>> fieldEntregaMercaderia = entregaMercaderiaMapBuilder.buildDetails();
            JRBeanCollectionDataSource fieldEntregaMercaderiaCollectionDataSource =new JRBeanCollectionDataSource(fieldEntregaMercaderia);
            this.loadTemplate("reportes/jasper/EntradaMercaderiaCFG.jasper");

            return this.build(jasperReport, parametersEntregaMercaderia, fieldEntregaMercaderiaCollectionDataSource);
        }catch (Exception e){
            logger.error(e.getMessage(), e.getCause());
            throw new PdfException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public byte[] generateConformidadServicio(ParameterConformidadServicioPdfDTO parameterConformidadServicioPdfDTO) {
        try {
            ConformidadServicioMapBuilder conformidadServicioMapBuilder = ConformidadServicioMapBuilder.newConformidadServicioMapBuilder(parameterConformidadServicioPdfDTO);
            Map<String, Object> parametersConformidadServicio = conformidadServicioMapBuilder.buildParams();
            List<Map<String, Object>> fieldConformidadServicio = conformidadServicioMapBuilder.buildDetails();
            JRBeanCollectionDataSource fieldConformidadServicioCollectionDataSource = new JRBeanCollectionDataSource(fieldConformidadServicio);
            this.loadTemplate("reportes/jasper/ConformidadServicioCFG.jasper");

            return this.build(jasperReport, parametersConformidadServicio, fieldConformidadServicioCollectionDataSource);
        }catch (Exception e){
            logger.error(e.getMessage(), e.getCause());
            throw new PdfException(e.getMessage(),e.getCause());
        }
    }


    @Override
    public byte[] generateOrdenCompraPdfBytes(OrdenCompraPdfDto ordenCompraPdfDto) {
        try {
            OrdenCompraMapBuilder ordenCompraMapBuilder = OrdenCompraMapBuilder.newOrdenCompraMapBuilder(ordenCompraPdfDto);
            Map<String, Object> parametersOrdenCompra = ordenCompraMapBuilder.buildParams();
//            List<Map<String, Object>> fieldPosicionList = ordenCompraMapBuilder.buildDetails();
//            JRBeanCollectionDataSource fieldPosicionListCollectionDataSource = new JRBeanCollectionDataSource(fieldPosicionList);
            this.loadTemplate("reportes/jasper/OrdenCompraCFG.jasper");

            return this.build(jasperReport, parametersOrdenCompra, null);
        }catch (Exception e){
            logger.error(e.getMessage(), e.getCause());
            throw new PdfException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public byte[] generateContratoMarcoPdfBytes(OrdenCompraPdfDto ordenCompraPdfDto) {
        try {
            OrdenCompraMapBuilder ordenCompraMapBuilder = OrdenCompraMapBuilder.newOrdenCompraMapBuilder(ordenCompraPdfDto);
            Map<String, Object> parametersContratoMarco = ordenCompraMapBuilder.buildParams();
            this.loadTemplate("reportes/jasper/ContratoMarcoCFG.jasper");

            return this.build(jasperReport, parametersContratoMarco, null);
        }catch (Exception e){
            logger.error(e.getMessage(), e.getCause());
            throw new PdfException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public byte[] generatePrefacturaPdfBytes(PrefacturaPdfDto prefacturaPdfDto) {
        try {
            PrefacturaMapBuilder prefacturaMapBuilder = PrefacturaMapBuilder.newPrefacturaMapBuilder(prefacturaPdfDto);
            Map<String, Object> parametersPrefactura = prefacturaMapBuilder.buildParams();

            this.loadTemplate("reportes/jasper/PrefacturaCFG.jasper");

            return this.build(jasperReport, parametersPrefactura, null);
        }catch (Exception e){
            logger.error(e.getMessage(), e.getCause());
            throw new PdfException(e.getMessage(),e.getCause());
        }
    }


    @Override
    public byte[] generateComprobanteRetencion(ParameterComprobanteRetencionPdfDTO parameterComprobanteRetencionPdfDTO) {
        try {
            ComprobanteRetencionMapBuilder comprobanteRetencionMapBuilder = ComprobanteRetencionMapBuilder.newComprobanteRetencionMapBuilder(parameterComprobanteRetencionPdfDTO);
            Map<String, Object> parametersComprobanteRetencion = comprobanteRetencionMapBuilder.buildParams();
            List<Map<String, Object>> fieldsComprobanteRetencion = comprobanteRetencionMapBuilder.buildDetails();
            JRBeanCollectionDataSource fieldsComprobanteRetencionCollectionDataSource = new JRBeanCollectionDataSource(fieldsComprobanteRetencion);
            this.loadTemplate("reportes/jasper/CopeincaRetentionEs-20.jasper");

            return this.build(jasperReport, parametersComprobanteRetencion, fieldsComprobanteRetencionCollectionDataSource);
        }catch (Exception e){
            logger.error(e.getMessage(), e.getCause());
            throw new PdfException(e.getMessage(),e.getCause());
        }
    }

    private void loadTemplate(String templateFile){
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = ResourceUtil.getResourceStream(PdfGeneratorServiceJasperImpl.class, templateFile);
            jasperReport  = (JasperReport) JRLoader.loadObject(resourceAsStream);
        } catch (JRException | FileNotFoundException e){
            logger.error(e.getMessage(), e.getCause());
            throw new PdfException("Error when loading PDF template: " + e.getMessage(), e.getCause());
        } finally {
            if(resourceAsStream != null){
                try{
                    resourceAsStream.close();
                }catch (IOException e){
                    logger.error("Error when close Stream:" + e.getMessage(), e.getCause());
                }
            }
        }
    }


}
