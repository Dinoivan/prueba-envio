package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.Proveedor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ActualizacionDeDatosNotificacion extends NotificarMail {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private final String LOGO_VENDOR = "com/incloud/hcp/image/vendor_logo.png";
//    private final String LOGO_SAN_MARTIN = "com/incloud/hcp/image/sm_logo.png";
//    private final String TEMPLATE = "com/incloud/hcp/templates/portal/TmpActualizacionDeDatos.html";
    private final String ASUNTO = "IProvider - Actualización de datos del proveedor";

//    @Value("${sm.portal.url}")
    @Value("${cfg.portal.url}")
    private  String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;

//    public void enviar(MailSetting mailSetting, Proveedor proveedor, String mailAdministrador){
//        Mail mail = new Mail();
//        VelocityContext context = new VelocityContext();
//        context.put("nombreProveedor", proveedor.getRazonSocial());
//        context.put("ruc", proveedor.getRuc());
//        context.put("url",this.urlPortal);
//        Optional.ofNullable(generateCid(mail.getHtmlMail(), LOGO_VENDOR))
//                .ifPresent(cid -> context.put("vendor_logo", cid));
//
//        Optional.ofNullable(generateCid(mail.getHtmlMail(), LOGO_SAN_MARTIN))
//                .ifPresent(cid -> context.put("sm_logo", cid));
//
//        String content = Optional.ofNullable(TEMPLATE)
//                .map(url -> url + "")
//                .map(template -> {
//                    int i = 0;
//                    return getContentMail(context, template);
//                })
//                .orElse("");
//        mail.setMailSetting(mailSetting);
//        try {
//            mail.enviar(mailAdministrador, null, ASUNTO, content);
//        } catch (EmailException ex) {
//            logger.error("Error al enviar notificación al administrador ", ex);
//        }
//    }


    public void enviar(MailSetting mailSetting, Proveedor proveedor, String mailAdministrador){
        String rucProveedor = proveedor.getRuc();
        String nombreProveedor = proveedor.getRazonSocial();

        ////////////////////// BEGIN METODO VELPA //////////////////////

        String body = "<!DOCTYPE html>";
        body = body + "<html lang='en'>";
        body = body + "<head>";
        body = body + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>";
        body = body + "</head>";
        body = body + "<body style='width: 600px;margin: auto;padding: 0;font-family: Arial;font-size: 14px;color: #333'>";

        body = body + "<table cellspacing='0' cellpadding='0' width='600px'>";
        body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0'>";
        body = body + "<td class='main_first' ";
        body = body + "style='border-collapse: collapse;width: 600px;padding: 0;margin: 0;padding-top: 12px;padding-bottom: 12px'>";
        body = body + "<h1 style='font-family: Arial;font-weight: bold;font-size: 16px;color: #555;margin-bottom: 24px'>";
        body = body + "Estimado administrador&#44;</h1>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "El proveedor " + nombreProveedor + " con ruc: " + rucProveedor + " ha modificado sus respuestas de homologación&#44; por favor sírvase a validar su información en el siguiente enlace:<br/>";
        body = body + "<a href=\"" + urlPortal + "\">Click aquí para revisar los datos del proveedor</a>";
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";

        body = body + "<table cellpadding='0' cellspacing='0' width='600px'>";
        body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0'>";
        body = body + "<td class='colophon' ";
        body = body + "style='border-collapse: collapse;width: 600px;padding: 0;margin: 0;font-size: 11px;color: #888;line-height: 13px'>";
        body = body + "<p style='font-family: Arial;font-size: 12px;line-height: 13px;color: #888'>";
        body = body + "AGRADECEREMOS NO RESPONDER ESTE CORREO. SI LO DESEA ENVIE SU CONSULTA A:<br/>";
        body = body + urlConsulta;
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";

        body = body + "<table cellspacing='0' cellpadding='0' width='600px'>";
        body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0'>";
        body = body + "<td class='main_last' ";
        body = body + "style='border-collapse: collapse;width: 600px;padding: 0;margin: 0;padding-top: 12px;padding-bottom: 36px'>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "Atentamente&#44;<br/>";
        body = body + "Equipo IProvider<br/>";
        body = body + "COPEINCA";
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";

        body = body + "</body>";
        body = body + "</html>";

        //////////////////////  END  METODO VELPA //////////////////////

        try{
            this.enviarCorreoSap(mailAdministrador, ASUNTO, body);
        }catch (Exception ex){
            logger.error("Error al enviar el correo por ActualizacionDeDatosNotificacion del proveedor " + nombreProveedor + " con RUC " + rucProveedor, ex);
        }
    }


    public void enviarPrueba(MailSetting mailSetting, Proveedor proveedor, String mailAdministrador){
        //String rucProveedor = proveedor.getRuc();
        //String nombreProveedor = proveedor.getRazonSocial();

        ////////////////////// BEGIN METODO VELPA //////////////////////

        String body = "Prueba";

        try{
            this.enviarCorreoSap("jescudero@csticorp.biz", ASUNTO, body);
        }catch (Exception ex){
            logger.error("Error al enviar el correo por ActualizacionDeDatosNotificacion del proveedor " );//+ nombreProveedor + " con RUC " + rucProveedor, ex);
        }
    }

}
