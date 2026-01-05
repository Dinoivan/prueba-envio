package com.incloud.hcp.util;

import org.apache.commons.lang.StringUtils;


public class StrUtils {


    public static String obtieneMensajeErrorExceptionCustom(Exception e) {
        String retorno = null;
        String nombreException = e.getClass().getName();
        if (StringUtils.isNotBlank(e.getMessage())) {
            return nombreException + " -- " + e.getMessage();
        }
        if (StringUtils.isNotBlank(e.getLocalizedMessage())) {
            return nombreException + " -- " + e.getLocalizedMessage();
        }
        retorno = e.toString();
        return retorno;
    }

    public static String truncarString (String mensaje) {
        if (mensaje == null)
            return null;

        if (mensaje.length() > 4900)
            return mensaje.substring(0, 4900);

        return mensaje;
    }

    public static String escapeComma (String texto){
        String nuevoTexto = texto.replace(",","&#44;");
        nuevoTexto = escapeStringForXml(nuevoTexto);
        return nuevoTexto;
    }

    public static String escapeStringForXml(String original){
        return original.replace("&","&amp;")
                .replace("<","&lt;")
                .replace(">","&gt;")
                .replace("\"","&quot;")
                .replace("'","&apos;");
    }

    public static String unescapeStringFromXml(String original){
        return original.replace("&amp;","&")
                .replace("&lt;","<")
                .replace("&gt;",">")
                .replace("&quot;","\"")
                .replace("&apos;","'");
    }
}