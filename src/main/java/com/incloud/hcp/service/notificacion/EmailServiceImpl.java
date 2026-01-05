package com.incloud.hcp.service.notificacion;


import com.google.common.html.HtmlEscapers;
import com.sap.cloud.account.TenantContext;
import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;
import okhttp3.*;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;


public class EmailServiceImpl{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    protected final String CORREO_FROM = "irequest@csticorp.biz";
    protected final String LOGO_COPEINCA = "com/incloud/hcp/image/cope_logo.jpg";
    protected final String TEMPLATE_INCIDENCA = "com/incloud/hcp/template/TmpIncidencia.html";
    protected final String TEMPLATE_MATERIAL = "com/incloud/hcp/template/TmpMaterial.html";

    @Value("${destination.email}")
    protected String destinationEmail;

    @Resource
    private TenantContext tenantContext;

    private static final String ON_PREMISE_PROXY = "OnPremise";
    private static final int COPY_CONTENT_BUFFER_SIZE = 1024;

    public EmailServiceImpl() {

    }

    private Proxy getProxy(String proxyType) {
        String proxyHost = null;
        int proxyPort;
        if (ON_PREMISE_PROXY.equals(proxyType)) {
            log.debug("Ingreso getProxy - " + ON_PREMISE_PROXY);
            // Get proxy for on-premise destinations
            proxyHost = System.getenv("HC_OP_HTTP_PROXY_HOST");
            proxyPort = Integer.parseInt(System.getenv("HC_OP_HTTP_PROXY_PORT"));
        } else {
            // Get proxy for internet destinations
            log.debug("Ingreso getProxy - INTERNET" );
            proxyHost = System.getProperty("http.proxyHost");
            proxyPort = Integer.parseInt(System.getProperty("http.proxyPort"));
        }
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
    }

    private void injectHeader(HttpURLConnection urlConnection, String proxyType) {
        if (ON_PREMISE_PROXY.equals(proxyType)) {
            log.debug("Ingreso injectHeader - " + ON_PREMISE_PROXY);
            // Insert header for on-premise connectivity with the consumer subaccount name
            urlConnection.setRequestProperty("SAP-Connectivity-ConsumerAccount", tenantContext.getAccountName());
        }
    }

    private void copyStream(InputStream inStream, OutputStream outStream) throws IOException {
        byte[] buffer = new byte[COPY_CONTENT_BUFFER_SIZE];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
    }

    protected void enviarCorreoSap(String paraCorreo, String asuntoCorreo, String bodyCorreo, String tipoCorreo) {
        HttpURLConnection urlConnection = null;
        try {
            log.error("tipoCorreo :: " + tipoCorreo);
            bodyCorreo = bodyCorreo.replaceAll("\"", "'");
            log.error("Ingresando enviarCorreoSap 01a - bodyCorreo: " + bodyCorreo);
            //bodyCorreo = bodyCorreo.replaceAll("\\r", "");
            //log.debug("Ingresando enviarCorreoSap 01b - bodyCorreo: " + bodyCorreo);
            //bodyCorreo = bodyCorreo.replaceAll("\r", "");
            //log.debug("Ingresando enviarCorreoSap 01c - bodyCorreo: " + bodyCorreo);
            bodyCorreo = bodyCorreo.replaceAll("\\r|\\n", "");
            log.error("Ingresando enviarCorreoSap 01c - bodyCorreo: " + bodyCorreo);

            //bodyCorreo = bodyCorreo.replace(System.getProperty("line.separator"), " ");
            //bodyCorreo = HtmlEscapers.htmlEscaper().escape(bodyCorreo);
            Context ctx = new InitialContext();
            ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx.lookup(
                    "java:comp/env/connectivityConfiguration");
            log.error("Ingresando enviarCorreoSap 02 - configuration: " + configuration);
            // Get destination configuration for "destinationName"
            DestinationConfiguration destConfiguration = configuration.getConfiguration(this.destinationEmail);
            if (destConfiguration == null) {
                return;
            }
            log.error("Ingresando enviarCorreoSap 03 - destConfiguration: " + destConfiguration);

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
            log.error("Ingresando enviarCorreoSap 09 - urlConnection: " + urlConnection);
            Map<String, String> mapProperties = destConfiguration.getAllProperties();
            for (Map.Entry<String, String> entry : mapProperties.entrySet())
            {
                log.error("Ingresando enviarCorreoSap 06b properties - " + entry.getKey() + "/" + entry.getValue());

            }
            //String cloudConnectorLocationId = destConfiguration.getProperty("CloudConnectorLocationId");
            log.error("Ingresando enviarCorreoSap 09");

            // Copy content from the incoming response to the outgoing response
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/xml");
            log.error("Ingresando enviarCorreoSap 09 correos " + paraCorreo);

            String variablePrueba = "";
            variablePrueba = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ";
            variablePrueba = variablePrueba + "xmlns:sen=\"http://www.example.org/sendMailOdm/\">";
            variablePrueba = variablePrueba + "<soapenv:Header/>";
            variablePrueba = variablePrueba + "<soapenv:Body>";
            variablePrueba = variablePrueba + "<sen:NewOperation>";
            variablePrueba = variablePrueba + "<auxiliar>auxi</auxiliar>";
            variablePrueba = variablePrueba + "<subject>" + asuntoCorreo + "</subject>";
            variablePrueba = variablePrueba + "<body><![CDATA[" + bodyCorreo + "]]></body>";
            int count = 0;
            if (paraCorreo != null && !paraCorreo.isEmpty()) {
                paraCorreo = paraCorreo.concat(",admin_iprovider@copeinca.com.pe");
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
            //variablePrueba = variablePrueba + "<to1>pavelprincipe@gmail.com</to1>";
            //variablePrueba = variablePrueba + "<to2>pprincipe@csticorp.biz</to2>";
            variablePrueba = variablePrueba + "</sen:NewOperation>";
            variablePrueba = variablePrueba + "</soapenv:Body>";
            variablePrueba = variablePrueba + "</soapenv:Envelope>";

            log.error("Ingresando enviarCorreoSap 09 bodyResponse - variablePrueba: " + variablePrueba);
            RequestBody body = RequestBody.create(mediaType, variablePrueba);


            Request request = new Request.Builder()
                    .url(valueURL)
                    .post(body)
                    .addHeader("content-type", "text/xml")
                    .addHeader("Authorization", "Basic c2p1YXJlekBjc3RpY29ycC5iaXo6UGEkJHcwcmQ=")
                    .build();
            log.error("Ingresando enviarCorreoSap 09");
            Response response = client.newCall(request).execute();
            log.error("Ingresando enviarCorreoSap 10");

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
            log.error("Connectivity operation failed", e);

        }
    }

    protected JSONObject devuelveJsonCorreo(String paraCorreo, String asuntoCorreo, String body) {
        try{
            //log.debug("Informacion - devuelveJsonCorreo body previo: " + body);
            body  = HtmlEscapers.htmlEscaper().escape(body);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("para", paraCorreo);
            jsonObject.put("asunto", asuntoCorreo);
            jsonObject.put("correo", body);
            log.debug("Informacion - devuelveJsonCorreo: " + jsonObject);
            return jsonObject;
        }
        catch(Exception e) {
            return null;
        }
    }


    public VelocityEngine getVelocityEngine() {
        VelocityEngine velocity = new VelocityEngine();
        velocity.setProperty(Velocity.RESOURCE_LOADER, "classpath");
        velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocity.setProperty("input.encoding", "UTF-8");
        velocity.init();
        return velocity;
    }

    public String getContentMail(VelocityContext context, String template) {
        VelocityEngine velocity = getVelocityEngine();
        Template t = velocity.getTemplate(template);
        StringWriter w = new StringWriter();
        t.merge(context, w);
        return w.toString();
    }






}

