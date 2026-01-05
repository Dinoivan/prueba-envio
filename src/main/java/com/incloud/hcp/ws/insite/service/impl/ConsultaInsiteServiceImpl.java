package com.incloud.hcp.ws.insite.service.impl;

import com.incloud.hcp.ws.insite.bean.ConsultaRUCResponse;
import com.incloud.hcp.ws.insite.bean.InsiteInput;
import com.incloud.hcp.ws.insite.bean.SoapEnvelope;
import com.incloud.hcp.ws.insite.bean.SoapFault;
import com.incloud.hcp.ws.insite.service.IConsultaInsiteService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.time.Duration;

@Component
public class ConsultaInsiteServiceImpl implements IConsultaInsiteService {

    private static final String SOAP_ENDPOINT = "http://ws.insite.pe/sunat/ruc.php?wsdl"; //http://ws.insite.pe/sunat/ruc.php/consultaRUC?wsdl
    private static final MediaType XML_MEDIA_TYPE = MediaType.parse("text/xml; charset=utf-8");

    @Override
    public String consultaRUC(InsiteInput input) throws Exception {
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
                .addHeader("SOAPAction", "ruc:consultaRUC")
                .build();

        Response response = client.newCall(request).execute();
        String responseXml = response.body().string();

        if (!response.isSuccessful()) {
            SoapFault errorResponse = handleSoapError(responseXml);
            throw new RuntimeException(errorResponse.getFaultString());
        }

        return handleSoapResponse(responseXml);
    }

    private String buildSoapRequest(InsiteInput input) {
        StringBuilder str = new StringBuilder();

        str.append("<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ruc=\"http://ws.insite.pe/sunat/ruc.php?wsdl\">")
                .append("<soapenv:Header/>")
                .append("<soapenv:Body>")
                .append("<ruc:consultaRUC soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">")
                .append("<ruc xsi:type=\"xsd:string\">").append(input.getRuc()).append("</ruc>")
                .append("<username xsi:type=\"xsd:string\">").append(input.getUsername()).append("</username>")
                .append("<hash xsi:type=\"xsd:string\">").append(input.getHash()).append("</hash>")
                .append("<tracking xsi:type=\"xsd:string\">").append(input.getTracking()).append("</tracking>")
                .append("</ruc:consultaRUC>")
                .append("</soapenv:Body>")
                .append("</soapenv:Envelope>");

        return str.toString();
    }

    private String handleSoapResponse(String responseText) throws RuntimeException, JAXBException {

        JAXBContext context = JAXBContext.newInstance(SoapEnvelope.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(responseText);

        SoapEnvelope response = (SoapEnvelope) unmarshaller.unmarshal(reader);

        ConsultaRUCResponse statusResponse = response.getBody().getConsultaRUCResponse();

        return statusResponse.getJsonResponse();

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
