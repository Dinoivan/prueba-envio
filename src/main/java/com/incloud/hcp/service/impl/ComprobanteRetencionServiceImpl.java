package com.incloud.hcp.service.impl;

import com.incloud.hcp.enums.TipoArchivoEnum;
import com.incloud.hcp.jco.comprobanteRetencion.dto.ComprobanteRetencionDto;
import com.incloud.hcp.jco.comprobanteRetencion.dto.ComprobanteRetencionItemDto;
import com.incloud.hcp.jco.comprobanteRetencion.service.JCOComprobanteRetencionService;
import com.incloud.hcp.pdf.PdfGeneratorFactory;
import com.incloud.hcp.pdf.bean.FieldComprobanteRetencionPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterComprobanteRetencionPdfDTO;
import com.incloud.hcp.service.ComprobanteRetencionService;
import com.incloud.hcp.util.DateUtils;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.*;

@Service
public class ComprobanteRetencionServiceImpl implements ComprobanteRetencionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${cfg.retencion.ws.url}")
    private String valueURL;

    private JCOComprobanteRetencionService jcoComprobanteRetencionService;

    @Autowired
    public ComprobanteRetencionServiceImpl(JCOComprobanteRetencionService jcoComprobanteRetencionService) {
        this.jcoComprobanteRetencionService = jcoComprobanteRetencionService;
    }

    @Override
    public List<ComprobanteRetencionDto> getComprobanteRetencionListPorFechasAndSociedadAndRuc(Date fechaInicio, Date fechaFin, String sociedad, String ruc) throws Exception {
        List<ComprobanteRetencionDto> comprobanteRetencionDtoList = new ArrayList<>();
        sociedad = sociedad != null ? sociedad : "";
        ruc = ruc != null ? ruc : "";
        String fechaInicioSapString = fechaInicio != null ? DateUtils.utilDateToSapString(fechaInicio) : "";
        String fechaFinSapString = fechaFin != null ? DateUtils.utilDateToSapString(fechaFin) : "";

        logger.error("BUSQUEDA COMPROBANTES DE RETENCION EN SAP [FILTRO]: fechaInicio = " + fechaInicioSapString);
        logger.error("BUSQUEDA COMPROBANTES DE RETENCION EN SAP [FILTRO]: fechaFin = " + fechaFinSapString);
        logger.error("BUSQUEDA COMPROBANTES DE RETENCION EN SAP [FILTRO]: sociedad = " + sociedad);
        logger.error("BUSQUEDA COMPROBANTES DE RETENCION EN SAP [FILTRO]: ruc = " + ruc);

        if(!fechaInicioSapString.isEmpty() && !fechaFinSapString.isEmpty()) {
            comprobanteRetencionDtoList = jcoComprobanteRetencionService.extraerComprobanteRetencionListRFC(fechaInicioSapString, fechaFinSapString, sociedad, ruc);
        }

        return comprobanteRetencionDtoList;
    }


    @Override
    public String getComprobanteRetencionGenerateContent(ParameterComprobanteRetencionPdfDTO parameterComprobanteRetencionPdfDTO){
        byte[] generateComprobanteRetencion = PdfGeneratorFactory.getJasperGenerator().generateComprobanteRetencion(parameterComprobanteRetencionPdfDTO);
        return Base64.getEncoder().encodeToString(generateComprobanteRetencion);
    }

    @Override
    public String getComprobanteRetencionGeneratePdfContent(ComprobanteRetencionDto comprobanteRetencionDto){
        ParameterComprobanteRetencionPdfDTO parameterComprobanteRetencionPdfDTO = new ParameterComprobanteRetencionPdfDTO();
        List<FieldComprobanteRetencionPdfDTO> fieldComprobanteRetencionPdfDTOList = new ArrayList<>();
        List<ComprobanteRetencionItemDto> comprobanteRetencionItemDtoList = Optional.ofNullable(comprobanteRetencionDto.getComprobanteRetencionItemDtoList()).orElse(new ArrayList<>());

        parameterComprobanteRetencionPdfDTO.setSerieCorrelativo(comprobanteRetencionDto.getSerie() + "-" + comprobanteRetencionDto.getCorrelativo());
        parameterComprobanteRetencionPdfDTO.setRucEmisor(comprobanteRetencionDto.getSociedadRuc());
        parameterComprobanteRetencionPdfDTO.setNombreEmisor(comprobanteRetencionDto.getSociedadRazonSocial());
        parameterComprobanteRetencionPdfDTO.setDireccionEmisor(comprobanteRetencionDto.getSociedadDireccion1() + " <br> " + comprobanteRetencionDto.getSociedadDireccion2() + " <br> Telefono: " + comprobanteRetencionDto.getSociedadTelefono());
        parameterComprobanteRetencionPdfDTO.setIdentificacionCliente(comprobanteRetencionDto.getProveedorRuc());
        parameterComprobanteRetencionPdfDTO.setNombreCliente(comprobanteRetencionDto.getProveedorRazonSocial());
        parameterComprobanteRetencionPdfDTO.setFechaEmision(DateUtils.utilDateToString(comprobanteRetencionDto.getFechaEmision()));
        parameterComprobanteRetencionPdfDTO.setTasaRetencion(comprobanteRetencionDto.getTasaRetencion().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        parameterComprobanteRetencionPdfDTO.setTotalPagado(comprobanteRetencionDto.getImporteNetoPagadoTotalSoles().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        parameterComprobanteRetencionPdfDTO.setTotalRetenido(comprobanteRetencionDto.getImporteRetencionTotalSoles().setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        comprobanteRetencionItemDtoList.forEach(item -> {
            FieldComprobanteRetencionPdfDTO fieldComprobanteRetencionPdfDTO = new FieldComprobanteRetencionPdfDTO();

            fieldComprobanteRetencionPdfDTO.setTipoDocumento(item.getTipoComprobante());
            fieldComprobanteRetencionPdfDTO.setNumeroDocumento(item.getSerieFactura() + "-" + item.getCorrelativoFactura());
            fieldComprobanteRetencionPdfDTO.setFechaEmision(DateUtils.utilDateToString(item.getFechaEmision()));
            fieldComprobanteRetencionPdfDTO.setMoneda(item.getMoneda());
            fieldComprobanteRetencionPdfDTO.setImporteOperacion(item.getImporteTotalComprobante().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            fieldComprobanteRetencionPdfDTO.setFechaPago(DateUtils.utilDateToString(item.getFechaPago()));
            fieldComprobanteRetencionPdfDTO.setNumeroPago(item.getNumeroPago());
            fieldComprobanteRetencionPdfDTO.setImportePago(item.getImportePago().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            fieldComprobanteRetencionPdfDTO.setTotalRetenido(item.getImporteRetencionSoles().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            fieldComprobanteRetencionPdfDTO.setTotalPagado(item.getImporteNetoSoles().setScale(2, BigDecimal.ROUND_HALF_UP).toString());

            fieldComprobanteRetencionPdfDTOList.add(fieldComprobanteRetencionPdfDTO);
        });

        parameterComprobanteRetencionPdfDTO.setFieldComprobanteRetencionPdfDTOList(fieldComprobanteRetencionPdfDTOList);

        byte[] generateComprobanteRetencion = PdfGeneratorFactory.getJasperGenerator().generateComprobanteRetencion(parameterComprobanteRetencionPdfDTO);
        return Base64.getEncoder().encodeToString(generateComprobanteRetencion);
    }

    @Override
    public String getComprobanteRetencionBase64(ComprobanteRetencionDto comprobanteRetencionDto, String tipoArchivo) {
//        HttpURLConnection urlConnection = null;
        String openingTag = "";
        String closingTag = "";
        String base64 = "";

        if (tipoArchivo.equals(TipoArchivoEnum.PDF.toString())){ // tipoArchivo es PDF
            openingTag = "<string_pdf>";
            closingTag = "</string_pdf>";
        }
        else{ // tipoArchivo es XML
            openingTag = "<string_xml>";
            closingTag = "</string_xml>";
        }

        try {
//            logger.error("Ingresando comprobante retencion - destConfiguration 00:" );
            //Context ctx = new InitialContext();
            /*ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx.lookup(
                    "java:comp/env/connectivityConfiguration");*/

            // Get destination configuration for "destinationName"
            /*DestinationConfiguration destConfiguration = configuration.getConfiguration("WS_COMPROBANTE_RETENCION");
            if (destConfiguration == null) {
                return "";
            }*/
            //logger.error("Ingresando comprobante retencion - destConfiguration 01:" + destConfiguration);

            // Get the destination URL
            //String valueURL = destConfiguration.getProperty("URL");
//            String valueURL = "http://qas.incloud.la:9998/services/S3getFilesCopeinca";

            logger.error("Ingresando comprobante retencion 02 - ws url: " + valueURL);
            //Map<String, String> mapProperties = destConfiguration.getAllProperties();
           // for (Map.Entry<String, String> entry : mapProperties.entrySet())
           // {
            //    logger.error("Ingresando comprobante retencion - url 03 properties - " + entry.getKey() + "/" + entry.getValue());

          //  }
            //String cloudConnectorLocationId = destConfiguration.getProperty("CloudConnectorLocationId");
            logger.error("Ingresando comprobante retencion 04 - tipo Archivo: " + tipoArchivo);

            // Copy content from the incoming response to the outgoing response
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/xml");

            String variable = "";

            variable = variable + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.s3.ce.incloud.pe/\">";
            variable = variable + "<soapenv:Header/>";
            variable = variable + "<soapenv:Body>";
            variable = variable + "<ws:S3getFilesCopeinca>";
            variable = variable + "<document>";
            variable = variable + "<fecha>" + comprobanteRetencionDto.getEjercicio() +"</fecha>";
            variable = variable + "<ruc>" + comprobanteRetencionDto.getProveedorRuc() +"</ruc>";
            variable = variable + "<serie_numero>" + comprobanteRetencionDto.getSerie()+ "</serie_numero>";
            variable = variable + "<tipo_documento>" + comprobanteRetencionDto.getTipoComprobante() +"</tipo_documento>";
            variable = variable + "</document>";
            variable = variable + "</ws:S3getFilesCopeinca>";
            variable = variable + "</soapenv:Body>";
            variable = variable + "</soapenv:Envelope>";

            logger.error("Ingresando comprobante retencion 05 - xml request: " + variable);
            RequestBody body = RequestBody.create(mediaType, variable);

            Request request = new Request.Builder()
                    .url(valueURL)
                    .post(body)
                    .addHeader("content-type", "text/xml")
                    .addHeader("Authorization", "Basic c2p1YXJlekBjc3RpY29ycC5iaXo6UGEkJHcwcmQ=")
                    .build();
            logger.error("Ingresando comprobante retencion 06");
            Response response = client.newCall(request).execute();
            logger.error("Ingresando comprobante retencion 07 - response: " + response);
            logger.error("Ingresando comprobante retencion 08 - response string: " + response.toString());
            base64 = response.body().string();

            String [] arrs = base64.split(openingTag);
            if (arrs.length > 1) {
                arrs = arrs[1].split(closingTag);
                if (arrs.length > 0) {
                    base64 = arrs[0];
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();

            // Connectivity operation failed
            String errorMessage = "Connectivity operation failed with reason: "
                    + e.getMessage()
                    + ". See "
                    + "logs for details. Hint: Make sure to have an HTTP proxy configured in your "
                    + "local environment in case your environment uses "
                    + "an HTTP proxy for the outbound Internet "
                    + "communication.";
            logger.error("comprobante retencion Connectivity operation failed", e);
            return e.toString();
        }

        return base64;
    }
}