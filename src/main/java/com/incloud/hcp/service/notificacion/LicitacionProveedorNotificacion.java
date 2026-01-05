package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.bean.ProveedorCustom;
import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionSubetapa;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by USER on 15/11/2017.
 */

@Component
public class LicitacionProveedorNotificacion extends NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String LOGO_VENDOR = "com/incloud/hcp/image/vendor_logo.png";
    private final String LOGO_SAN_MARTIN = "com/incloud/hcp/image/sm_logo.png";
    private final String TEMPLATE = "com/incloud/hcp/templates/portal/TmpLicitacionProveedor.html";

//    @Value("${sm.portal.url}")
    @Value("${cfg.portal.url}")
    private  String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;


//    public void enviar(
//            MailSetting mailSetting,
//            ProveedorCustom proveedor,
//            Licitacion licitacion,
//            List<LicitacionSubetapa> licitacionSubetapaList) {
//
//        Mail mail = new Mail();
//        VelocityContext context = new VelocityContext();
//        Date fechaConfirmacion = licitacion.getFechaCierreConfirmacionParticipacion();
//        String sFechaConfirmacion = DateUtils.convertDateToString("dd/MM/yyyy H:mm:ss", fechaConfirmacion);
//
//        context.put("nombreProveedor", proveedor.getRazonSocial());
//        context.put("nroLicitacion", licitacion.getNroLicitacionString());
//        context.put("nombreUsuarioPublicacion", licitacion.getUsuarioPublicacionName());
//        context.put("emailUsuarioPublicacion", licitacion.getUsuarioPublicacionEmail());
//        context.put("fechaConfirmacion", sFechaConfirmacion);
//        context.put("licitacionSubetapaList", licitacionSubetapaList);
//        context.put("url",this.urlPortal);
//
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
//                String asunto = "Licitación Nro. " + licitacion.getNroLicitacionString()+"- SF";
//
//                mail.enviar(proveedor.getEmail(), null, asunto, content);
//
//
//        } catch (EmailException ex) {
//            logger.error("Error al enviar notificacion", ex);
//        }
//    }

    public void enviar(MailSetting mailSetting, ProveedorCustom proveedor, Licitacion licitacion, List<LicitacionSubetapa> licitacionSubetapaList) {
        String rucProveedor = proveedor.getRuc();
        String nombreProveedor = proveedor.getRazonSocial();
        String numeroLicitacion = licitacion.getNroLicitacionCero();
//        String nombreUsuarioPublicacion = licitacion.getUsuarioPublicacionName();
        String emailUsuarioPublicacion = licitacion.getUsuarioPublicacionEmail();
        String stringFechaConfirmacion = DateUtils.convertDateToString("dd/MM/yyyy H:mm:ss", licitacion.getFechaCierreConfirmacionParticipacion());
        String asunto = "Licitación Nro. " + licitacion.getNroLicitacionString() + " - Copeinca";


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
        body = body + "Estimado " + nombreProveedor + "&#44;</h1>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "Se le informa que ha sido invitado a participar en la Licitación Nro. " + numeroLicitacion + "&#44;<br/>";
        body = body + "para lo cual tiene hasta el " + stringFechaConfirmacion + " para poder confirmar su participación.<br/>";
        body = body + "<a href=\"" + urlPortal + "\">Click para ingresar a su cuenta</a>";
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";

        body = body + "<table cellspacing='0' cellpadding='0' border='1px #deb887'>";
        body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0'>";
        body = body + "<td bgcolor='#add8e6' align='center' ";
        body = body + "style='border-collapse: collapse;width: 300px;padding: 0;margin-left: 5px;padding-top: 36px;padding-bottom: 12px'>";
        body = body + "<span style='font-weight: bold'>Etapa de Licitación</span>";
        body = body + "</td>";
        body = body + "<td bgcolor='#add8e6' align='center' ";
        body = body + "style='border-collapse: collapse;width: 300px;padding: 0;margin-left: 5px;padding-top: 36px;padding-bottom: 12px'>";
        body = body + "<span style='font-weight: bold'>Fecha de Cierre</span>";
        body = body + "</td>";
        body = body + "</tr>";
        for(LicitacionSubetapa licSubetapa : licitacionSubetapaList) {
            body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0; border: 1px #2b3f7b '>";
            body = body + "<td align='center' ";
            body = body + "style='border-collapse: collapse;width: 300px;padding: 0;margin-left: 5px;padding-top: 36px;padding-bottom: 12px'>";
            body = body + licSubetapa.getIdSubetapa().getDescripcionSubetapa();
            body = body + "</td>";
            body = body + "<td align='center' ";
            body = body + "style='border-collapse: collapse;width: 300px;padding: 0;margin-left: 5px;padding-top: 36px;padding-bottom: 12px'>";
            body = body + licSubetapa.getFechaCierreSubetapaString();
            body = body + "</td>";
            body = body + "</tr>";
        }
        body = body + "</table>";

        body = body + "<table cellspacing='0' cellpadding='0' width='600px'>";
        body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0'>";
        body = body + "<td class='main_first' ";
        body = body + "style='border-collapse: collapse;width: 600px;padding: 0;margin: 0;padding-top: 12px;padding-bottom: 12px'>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "En caso tenga alguna duda&#44; puede contactarse a " + emailUsuarioPublicacion + "&#44;<br/>";
        body = body + "caso contrario sírvase remitir su cotización a través del portal o haciendo clic en el siguiente enlace para ingresar a su cuenta:<br/>";
        body = body + "<a href=\"" + urlPortal + "\">Click aquí para ingresar a su cuenta</a>";
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
            logger.error("Error al enviar el correo por LicitacionProveedorNotificacion al proveedor " + nombreProveedor + " con RUC " + rucProveedor + " por la licitacion " + numeroLicitacion, ex);
        }
    }


    public void reenviar(ProveedorCustom proveedor, Licitacion licitacion, String cuerpo) {
        String rucProveedor = proveedor.getRuc();
        String nombreProveedor = proveedor.getRazonSocial();
        String numeroLicitacion = licitacion.getNroLicitacionCero();
        String emailUsuarioPublicacion = licitacion.getUsuarioPublicacionEmail();
        String stringFechaConfirmacion = DateUtils.convertDateToString("dd/MM/yyyy H:mm:ss", licitacion.getFechaCierreConfirmacionParticipacion());
        String asunto = "Licitación Nro. " + licitacion.getNroLicitacionString() + " - Copeinca";


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
        body = body + "Estimados Señores " + nombreProveedor + "</h1>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "El equipo de Compras de CFG Investment – Copeinca&#44; en línea con el cronograma del proceso " + numeroLicitacion + "&#44;<br/>";
        body = body + "les recuerda lo siguiente;<br/>";
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";


        //------------------------------------------------------------------------------------------------------------------
        body = body + "<table cellspacing='0' cellpadding='0' width='600px'>";
        body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0'>";
        body = body + "<td class='main_first' ";
        body = body + "style='border-collapse: collapse;width: 600px;padding: 0;margin: 0;padding-top: 12px;padding-bottom: 12px'>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + StrUtils.escapeComma(cuerpo);
        body = body + "<br/>";
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";
        //------------------------------------------------------------------------------------------------------------------


        body = body + "<table cellspacing='0' cellpadding='0' width='600px'>";
        body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0'>";
        body = body + "<td class='main_first' ";
        body = body + "style='border-collapse: collapse;width: 600px;padding: 0;margin: 0;padding-top: 12px;padding-bottom: 12px'>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "Es importante mencionar que se deben respetar y cumplir las fechas indicadas en las bases&#44;<br/>";
        body = body + "para no poner en riesgo su participación en el proceso<br/>";
        body = body + "<br/>";
        body = body + "En caso tenga alguna duda&#44; puede contactarse a " + emailUsuarioPublicacion + "&#44;<br/>";
        body = body + "caso contrario sírvase remitir su cotización a través del portal o haciendo clic en el siguiente enlace para ingresar a su cuenta:<br/>";
        body = body + "<a href=\"" + urlPortal + "\">Click aquí para ingresar a su cuenta</a>";
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
            logger.error("Error al enviar el correo por LicitacionProveedorNotificacion al proveedor " + nombreProveedor + " con RUC " + rucProveedor + " por la licitacion " + numeroLicitacion, ex);
        }
    }
}
