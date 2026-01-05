package com.incloud.hcp.service.notificacion;

import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;
import okhttp3.*;
import okio.Buffer;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.File;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrador on 13/11/2017.
 */

class NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${destination.email}")
    protected String destinationEmail;

    public NotificarMail() {
    }

    VelocityEngine getVelocityEngine() {
        logger.error("Inicialización de VelocityEngine");
        VelocityEngine velocity = new VelocityEngine();
        velocity.setProperty(Velocity.RESOURCE_LOADER, "classpath");
        velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocity.setProperty("input.encoding", "UTF-8");
        velocity.init();

        return velocity;
    }

    String getContentMail(VelocityContext context, String template) {
        logger.error("Generando el contenido de la notificación");
        VelocityEngine velocity = getVelocityEngine();
        Template t = velocity.getTemplate(template);
        StringWriter w = new StringWriter();
        t.merge(context, w);
        return w.toString();
    }

    String generateCid(HtmlEmail htmlMail, String imagen) {

        return Optional.ofNullable(NotificarMail.class.getClassLoader().getResource(imagen))
                .map(url -> {
                    logger.error("Obteniendo el path de la imagen ");
                    logger.error("url inicial : " + url);
                    String aux = url + "";
                    aux = aux.replace("file:", "");
                    logger.error("La ruta de la imagen es la siguiente " + aux);
                    return aux;
                })
                .map(p -> new File(p))
                .map(f -> {
                    try {
                        logger.error("Colocando la imagen en el contenido HTML");
                        String cid = htmlMail.embed(f);
                        return cid;
                    } catch (EmailException ex) {
                        logger.error("Error al colocar la imagen en el contenido HTML", ex);
                        return null;
                    }
                }).orElse(null);

    }

    protected void enviarCorreoSap(String paraCorreo, String asuntoCorreo, String bodyCorreo) {
        HttpURLConnection urlConnection = null;
        try {
            bodyCorreo = bodyCorreo.replaceAll("\"", "'");
            logger.error("Ingresando enviarCorreoSap 01a - bodyCorreo: " + bodyCorreo);
            bodyCorreo = bodyCorreo.replaceAll("\\r|\\n", "");
            logger.error("Ingresando enviarCorreoSap 01c - bodyCorreo: " + bodyCorreo);
            logger.error("Ingresando enviarCorreoSap 03 - destConfiguration: " + this.destinationEmail);

            String valueURL = this.destinationEmail;

            logger.error("Ingresando enviarCorreoSap 09 - urlConnection: " + urlConnection);

            logger.error("Ingresando enviarCorreoSap 09");

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/xml");
            logger.error("Ingresando enviarCorreoSap 09 correos " + paraCorreo);

            String variablePrueba = "";
            variablePrueba = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ";
            variablePrueba = variablePrueba + "xmlns:sen=\"http://www.example.org/sendMailIprovider/\">";
            variablePrueba = variablePrueba + "<soapenv:Header/>";
            variablePrueba = variablePrueba + "<soapenv:Body>";
            variablePrueba = variablePrueba + "<sen:NewOperation>";
            variablePrueba = variablePrueba + "<auxiliar>auxi</auxiliar>";
            variablePrueba = variablePrueba + "<subject>" + asuntoCorreo + "</subject>";
            variablePrueba = variablePrueba + "<body><![CDATA[" + bodyCorreo + "]]></body>";
            int count = 0;
            if (paraCorreo != null && !paraCorreo.isEmpty()) {
                paraCorreo = paraCorreo.concat(",admin_iprovider@copeinca.com.pe");
                paraCorreo = removeDuplicates(paraCorreo);
                logger.error("Ingresando enviarCorreoSap  paraCorreo " + paraCorreo);
                String[] to = paraCorreo.split(",");
                if (to != null && to.length > 0) {
                    for (String t : to) {
                        if (!t.isEmpty()) {
                            count++;
                            variablePrueba = variablePrueba + "<to" + count + ">" + t.trim()  +"</to" + count +">";
                        }
                    }
                }
            }
            variablePrueba = variablePrueba + "</sen:NewOperation>";
            variablePrueba = variablePrueba + "</soapenv:Body>";
            variablePrueba = variablePrueba + "</soapenv:Envelope>";

            logger.error("Ingresando enviarCorreoSap 09 bodyResponse - variablePrueba: " + variablePrueba);
            RequestBody body = RequestBody.create(mediaType, variablePrueba);
            logger.error("Body_rq " + body.toString());
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            logger.error("Body_buff " + buffer.readUtf8());

            Request request = new Request.Builder()
                    .url(valueURL)
                    .post(body)
                    .addHeader("content-type", "text/xml")
                    .addHeader("Authorization", "Basic c2ItNDczOWIwMTItZjBiNS00MTZlLWEzYTAtODliOWMxNzYyZDNmIWIzMTAxMzh8aXQtcnQtY3BpLXByZC1jZi1kZHM1N2Y0bSFiNTYxODY6ZTllMWM5NTAtMGJmMC00OGI2LWE1M2QtZGIyZGZmNTcxNDVmJHJ3aHlvaE5sbnJBLWk3U3RHU0hGaTVXZ1NURTRZRDIwcFNaYjZIY0l5bXc9") //pprincipe
                    .build();
            logger.error("Ingresando enviarCorreoSap 09_ " + request);
            Response response = client.newCall(request).execute();
            logger.error("Ingresando enviarCorreoSap 10 :response:" + response.body().string());
            logger.error("HTTP Status Code: " + response.code());
            logger.error("Response Headers: " + response.headers().toString());
        }
        catch(Exception e) {
            e.printStackTrace();

            String errorMessage = "Connectivity operation failed with reason: "
                    + e.getMessage()
                    + ". See "
                    + "logs for details. Hint: Make sure to have an HTTP proxy configured in your "
                    + "local environment in case your environment uses "
                    + "an HTTP proxy for the outbound Internet "
                    + "communication.";
            logger.error("Connectivity operation failed", e);

        }
    }
    public String removeDuplicates(String s) {
        return new LinkedHashSet<String>(Arrays.asList(s.split(","))).toString().replaceAll("(^\\[|\\]$)", "").replace(", ", ",");
    }
    protected void enviarCorreoSap2(String paraCorreo, String asuntoCorreo, String bodyCorreo) {
        HttpURLConnection urlConnection = null;
        try {
            bodyCorreo = bodyCorreo.replaceAll("\"", "'");
            logger.error("Ingresando enviarCorreoSap 01a - bodyCorreo: " + bodyCorreo);
            //bodyCorreo = bodyCorreo.replaceAll("\\r", "");
            //log.debug("Ingresando enviarCorreoSap 01b - bodyCorreo: " + bodyCorreo);
            //bodyCorreo = bodyCorreo.replaceAll("\r", "");
            //log.debug("Ingresando enviarCorreoSap 01c - bodyCorreo: " + bodyCorreo);
            bodyCorreo = bodyCorreo.replaceAll("\\r|\\n", "");
            logger.error("Ingresando enviarCorreoSap 01c - bodyCorreo: " + bodyCorreo);

            //bodyCorreo = bodyCorreo.replace(System.getProperty("line.separator"), " ");
            //bodyCorreo = HtmlEscapers.htmlEscaper().escape(bodyCorreo);
            Context ctx = new InitialContext();
            ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx.lookup(
                    "java:comp/env/connectivityConfiguration");
            logger.error("Ingresando enviarCorreoSap 02 - configuration: " + configuration);
            // Get destination configuration for "destinationName"
            DestinationConfiguration destConfiguration = configuration.getConfiguration(this.destinationEmail);
            if (destConfiguration == null) {
                return;
            }
            logger.error("Ingresando enviarCorreoSap 03 - destConfiguration: " + destConfiguration);

            // Get the destination URL
            String valueURL = destConfiguration.getProperty("URL");
            //URL url = new URL(valueURL);
            //log.debug("Ingresando enviarCorreoSap 04 - url: " + url);

            //String proxyType = destConfiguration.getProperty("ProxyType");
            //Proxy proxy = getProxy(proxyType);
            //log.debug("Ingresando enviarCorreoSap 05 - proxyType: " + proxyType);
            //log.debug("Ingresando enviarCorreoSap 05b - proxy: " + proxy);

            //urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection = (HttpURLConnection) url.openConnection(proxy);

            // Insert the required header in the request for on-premise destinations
            //injectHeader(urlConnection, proxyType);
//            log.debug("Ingresando enviarCorreoSap 06 - urlConnection: " + urlConnection);
            Map<String, String> mapProperties = destConfiguration.getAllProperties();
            for (Map.Entry<String, String> entry : mapProperties.entrySet())
            {
                logger.error("Ingresando enviarCorreoSap 06b properties - " + entry.getKey() + "/" + entry.getValue());

            }
            //String cloudConnectorLocationId = destConfiguration.getProperty("CloudConnectorLocationId");
            logger.error("Ingresando enviarCorreoSap 07");

            // Copy content from the incoming response to the outgoing response
//            OkHttpClient client = new OkHttpClient();
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            MediaType mediaType = MediaType.parse("text/xml");
            logger.error("Ingresando enviarCorreoSap 08");

            String variablePrueba = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cxf=\"http://cxf.component.camel.apache.org/\"><soapenv:Header/> <soapenv:Body> <cxf:invoke> <msg> <![CDATA[\t{\"para\":\"" +
                    paraCorreo +
                    "\",\"asunto\":\"" +
                    asuntoCorreo +
                    "\", \"cuerpo\":\""+ "" +
                    bodyCorreo +
                    "\"} ]]> </msg>" +
                    "\t</cxf:invoke>" +
                    "   </soapenv:Body> " +
                    "</soapenv:Envelope>";

            logger.error("Ingresando enviarCorreoSap 08 bodyResponse - variablePrueba: " + variablePrueba);
            RequestBody body = RequestBody.create(mediaType, variablePrueba);


            Request request = new Request.Builder()
                    .url(valueURL)
                    .post(body)
                    .addHeader("content-type", "text/xml")
                    //                    .addHeader("Authorization", "Basic YWRtaW5faXByb3ZpZGVyQGNvcGVpbmNhLmNvbS5wZTpQZXJ1MjAyMiQ=")
                    //.addHeader("Authorization", "Basic cHByaW5jaXBlLmNvbnNAZ21haWwuY29tOkluaWNpbzAxJCQ=") //pprincipe
                    .addHeader("Authorization", "Basic c2ItNDczOWIwMTItZjBiNS00MTZlLWEzYTAtODliOWMxNzYyZDNmIWIzMTAxMzh8aXQtcnQtY3BpLXByZC1jZi1kZHM1N2Y0bSFiNTYxODY6ZTllMWM5NTAtMGJmMC00OGI2LWE1M2QtZGIyZGZmNTcxNDVmJHJ3aHlvaE5sbnJBLWk3U3RHU0hGaTVXZ1NURTRZRDIwcFNaYjZIY0l5bXc9") //pprincipe
                    .build();
            logger.error("Ingresando enviarCorreoSap 09");
            Response response = client.newCall(request).execute();
            logger.error("Ingresando enviarCorreoSap 10: " + response.body().string());

            //InputStream instream = urlConnection.getInputStream();
            //OutputStream outstream = response.getOutputStream();
            //copyStream(instream, outstream);
            //ResponseBody response_=response.body();
            //response_.string();
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
            logger.error("Connectivity operation failed", e);

        }
    }
}
