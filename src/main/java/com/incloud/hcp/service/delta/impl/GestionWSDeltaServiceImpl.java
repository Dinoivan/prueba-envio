package com.incloud.hcp.service.delta.impl;

import com.incloud.hcp.jco.comprobanteRetencion.dto.ComprobanteRetencionDto;
import com.incloud.hcp.service.delta.GestionWSDeltaService;
import com.incloud.hcp.service.impl.GestionWSServiceImpl;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpURLConnection;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GestionWSDeltaServiceImpl extends GestionWSServiceImpl implements GestionWSDeltaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final Logger log = LoggerFactory.getLogger(GestionWSDeltaServiceImpl.class);

    @Override
    public String getBase64ComprobanteRetencion(ComprobanteRetencionDto comprobanteRetencionDto) {
        HttpURLConnection urlConnection = null;
        String base64 = "";
        try {


            /*Context ctx = new InitialContext();
            ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx.lookup(
                    "java:comp/env/connectivityConfiguration");

            // Get destination configuration for "destinationName"
            DestinationConfiguration destConfiguration = configuration.getConfiguration("WS_COMPROBANTE_RETENCION");
            if (destConfiguration == null) {
                return "";
            }*/
            //log.error("Ingresando comprobante retencion - destConfiguration 01:" + destConfiguration);
            //logger.error("Ingresando comprobante retencion - destConfiguration 01:" + destConfiguration);

            // Get the destination URL
            //String valueURL = destConfiguration.getProperty("URL");
            String valueURL = "http://qas.incloud.la:9998/services/S3getFilesCopeinca?wsdl";

            log.error("Ingresando comprobante retencion - url 02_:" + valueURL);
            logger.error("Ingresando comprobante retencion - url 02_:" + valueURL);
            //Map<String, String> mapProperties = destConfiguration.getAllProperties();
            //for (Map.Entry<String, String> entry : mapProperties.entrySet())
            //{
              //  log.error("Ingresando comprobante retencion - url 03 properties - " + entry.getKey() + "/" + entry.getValue());
               // logger.error("Ingresando comprobante retencion - url 03 properties - " + entry.getKey() + "/" + entry.getValue());

           // }
            //String cloudConnectorLocationId = destConfiguration.getProperty("CloudConnectorLocationId");
            log.error("Ingresando comprobante retencion 04_");
            logger.error("Ingresando comprobante retencion 04_");

            // Copy content from the incoming response to the outgoing response
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/xml");
           // log.error("Ingresando enviarCorreoSap 09 correos " + paraCorreo);

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

            log.error("Ingresando comprobante retencion 05_:: " + variable);
            logger.error("Ingresando comprobante retencion 05_:: " + variable);
            RequestBody body = RequestBody.create(mediaType, variable);

            Request request = new Request.Builder()
                    .url(valueURL)
                    .post(body)
                    .addHeader("content-type", "text/xml")
                    .addHeader("Authorization", "Basic c2p1YXJlekBjc3RpY29ycC5iaXo6UGEkJHcwcmQ=")
                    .build();
            log.error("Ingresando comprobante retencion 06_");
            log.error("Ingresando comprobante retencion 06_");
            Response response = client.newCall(request).execute();
            log.error("Ingresando comprobante retencion 07:: " + response);
            logger.error("Ingresando comprobante retencion 08:: " + response.toString());
            base64 = response.body().string();

            String [] arrs = base64.split("<string_pdf>");
            if (arrs.length > 1) {
                arrs = arrs[1].split("</string_pdf>");
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
            log.error("Connectivityxz operation failed", e);
            logger.error("Connectivityxz operation failed", e);
            return e.toString();

        }

        return base64;

    }
}
