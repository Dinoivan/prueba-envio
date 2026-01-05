package com.incloud.hcp.ws.sunat.service.impl;

import com.incloud.hcp.ws.sunat.bean.*;
import com.incloud.hcp.ws.sunat.service.IStatusComprobante;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.time.Duration;

@Component
public class StatusComprobanteImpl implements IStatusComprobante {

    @Value("${cfg.sunat.user}")
    private String repositoryUser;

    @Value("${cfg.sunat.pass}")
    private String repositoryPass;

    private static final String SOAP_ENDPOINT = "https://ww1.sunat.gob.pe/ol-it-wsconscpegem/billConsultService?wsdl"; //"https://e-factura.sunat.gob.pe/ol-it-wsconscpegem/billConsultService";
    private static final MediaType XML_MEDIA_TYPE = MediaType.parse("text/xml; charset=utf-8");

    @Override
    public GetStatusOutput getStatusComprobante(GetStatusInput input) throws Exception {

        OkHttpClient client = new OkHttpClient.Builder()
                /*.authenticator((route, response) -> {
                    String credential = Credentials.basic(USER_NAME, PASSWORD);
                    return response.request().newBuilder().header("Authorization", credential).build();
                })*/
                .callTimeout(Duration.ofMinutes(5))
                .build();

        String soapRequest = buildSoapRequest(input);

        RequestBody body = RequestBody.create(soapRequest, XML_MEDIA_TYPE);
        Request request = new Request.Builder()
                .url(SOAP_ENDPOINT)
                .post(body)
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .addHeader("SOAPAction", "urn:getStatus")
                .build();

        Response response = client.newCall(request).execute();
        String responseXml = response.body().string();

        if (!response.isSuccessful()) {
            SoapFault errorResponse = handleSoapError(responseXml);
            throw new RuntimeException(errorResponse.getFaultString());
        }

        return handleSoapResponse(responseXml);
    }

    private String buildSoapRequest(GetStatusInput input) {
        StringBuilder str = new StringBuilder();

        str.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ")
                .append("xmlns:ser=\"http://service.sunat.gob.pe\">")
                .append("<soapenv:Header>")
                .append("<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/")
                .append("oasis-200401-wss-wssecurity-secext-1.0.xsd\">")
                .append("<wsse:UsernameToken>")
                .append("<wsse:Username>").append(input.getRucCliente()).append(repositoryUser).append("</wsse:Username>")
                .append("<wsse:Password>").append(repositoryPass).append("</wsse:Password>")
                .append("</wsse:UsernameToken>")
                .append("</wsse:Security>")
                .append("</soapenv:Header>")
                .append("<soapenv:Body>")
                .append("<ser:getStatus>")
                .append("<rucComprobante>").append(input.getRucComprobante()).append("</rucComprobante>")
                .append("<tipoComprobante>").append(input.getTipoComprobante()).append("</tipoComprobante>")
                .append("<serieComprobante>").append(input.getSerieComprobante()).append("</serieComprobante>")
                .append("<numeroComprobante>").append(input.getNumeroComprobante()).append("</numeroComprobante>")
                .append("</ser:getStatus>")
                .append("</soapenv:Body>")
                .append("</soapenv:Envelope>");

        return str.toString();
    }

    private GetStatusOutput handleSoapResponse(String responseText) throws RuntimeException, JAXBException {

        JAXBContext context = JAXBContext.newInstance(SoapEnvelope.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(responseText);

        SoapEnvelope response = (SoapEnvelope) unmarshaller.unmarshal(reader);

        GetStatusResponse statusResponse = response.getBody().getGetStatusResponse();

        return GetStatusOutput.builder()
                .codigo(statusResponse.getStatus().getStatusCode())
                .mensaje(statusResponse.getStatus().getStatusMessage())
                .build();

    }

    private SoapFault handleSoapError(String responseText) {
        try {
            JAXBContext context = JAXBContext.newInstance(SoapEnvelope.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(responseText);

            SoapEnvelope response = (SoapEnvelope) unmarshaller.unmarshal(reader);

            return response.getBody().getFault();

        } catch (JAXBException ex) {
            throw new RuntimeException("Ocurrió un problema al serializar el error. " + ex.getMessage());
        }
    }


}
