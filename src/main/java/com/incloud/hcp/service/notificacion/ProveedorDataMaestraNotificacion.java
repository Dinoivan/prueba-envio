package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.Proveedor;
import org.apache.commons.mail.EmailException;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProveedorDataMaestraNotificacion extends NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String LOGO_VENDOR = "com/incloud/hcp/image/vendor_logo.png";
    private final String LOGO_SAN_MARTIN = "com/incloud/hcp/image/sm_logo.png";
    private final String TEMPLATE = "com/incloud/hcp/templates/portal/TmpProveedorDataMaestraAprobado.html";
    private final String TEMPLATERECHAZO = "com/incloud/hcp/templates/portal/TmpProveedorDataMaestraRechazado.html";
    private final String ASUNTOAPROBADO = "IProvider - Proveedor validado por Data Maestra";
    private final String ASUNTORECHAZO = "IProvider - Proveedor rechazado Data Maestra";

//    @Value("${sm.portal.url}")
    @Value("${cfg.portal.url}")
    private  String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;


//    public void enviar(MailSetting mailSetting, Proveedor proveedor, String estado) {
//
//        Mail mail = new Mail();
//        VelocityContext context = new VelocityContext();
//        context.put("contacto", proveedor.getContacto());
//        context.put("url", this.urlPortal);
//        String asunto="",plantilla="";
//
//        if(estado.equals("APR")){
//            asunto=ASUNTOAPROBADO;
//            plantilla=TEMPLATE;
//        }else{
//            asunto=ASUNTORECHAZO;
//            plantilla=TEMPLATERECHAZO;
//        }
//
//
//        Optional.ofNullable(generateCid(mail.getHtmlMail(), LOGO_VENDOR))
//                .ifPresent(cid -> context.put("vendor_logo", cid));
//
//        Optional.ofNullable(generateCid(mail.getHtmlMail(), LOGO_SAN_MARTIN))
//                .ifPresent(cid -> context.put("sm_logo", cid));
//
//        String content = Optional.ofNullable(plantilla)
//                .map(url -> url + "")
//                .map(template -> {
//                    int i = 0;
//                    return getContentMail(context, template);
//                })
//                .orElse("");
//        mail.setMailSetting(mailSetting);
//        try {
//            mail.enviar(proveedor.getEmail(), null, asunto, content);
//        } catch (EmailException ex) {
//            logger.error("Error al enviar notificacion al proveedor ", ex);
//        }
//    }


    public void enviar(MailSetting mailSetting, Proveedor proveedor, String estado){
        String contactoProveedor = proveedor.getContacto();
        String rucProveedor = proveedor.getRuc();
        String nombreProveedor = proveedor.getRazonSocial();

        String asunto = "";
        String mensaje = "";
        String accion = "";

        if(estado.equals("APR")){
            asunto = ASUNTOAPROBADO;
            mensaje = "La información registrada es correcta y continuará en el proceso de evaluación como proveedor.";
            accion = "y asegúrese de completar el cuestionario.";
        }else{
            asunto = ASUNTORECHAZO;
            mensaje = "Usted no ha sido validado por el área de Data Maestra."; //  ...por los siguientes motivos:
            accion = "para completar su información en 'Mis Datos'.";
        }

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
        body = body + "Estimado " + contactoProveedor + "&#44;</h1>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body +  mensaje + "<br/>";
        body = body + "Por favor ingrese al siguiente enlace " + accion + "<br/>";
        body = body + "<a href=\"" + urlPortal + "\">Click para ingresar a su cuenta</a>";
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


        try {
            this.enviarCorreoSap(proveedor.getEmail(), asunto, body);
        } catch (Exception ex) {
            logger.error("Error al enviar el correo por ProveedorDataMaestraNotificacion al proveedor " + nombreProveedor + " con RUC " + rucProveedor, ex);
        }
    }
}
