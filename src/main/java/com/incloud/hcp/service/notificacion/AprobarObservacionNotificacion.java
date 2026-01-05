package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.AprobadorSolicitud;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.SolicitudBlacklist;
import com.incloud.hcp.domain.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by Administrador on 14/11/2017.
 */
@Component
public class AprobarObservacionNotificacion extends NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private final String LOGO_VENDOR = "com/incloud/hcp/image/vendor_logo.png";
//    private final String LOGO_SAN_MARTIN = "com/incloud/hcp/image/sm_logo.png";
//    private final String TEMPLATE = "com/incloud/hcp/templates/portal/TmpAprobarObservacion.html";

//    @Value("${sm.portal.url}")
    @Value("${cfg.portal.url}")
    private  String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;

//    public void enviar(MailSetting mailSetting, AprobadorSolicitud aprobador, SolicitudBlacklist solicitud) {
//
//        Mail mail = new Mail();
//        VelocityContext context = new VelocityContext();
//
//        Optional<Usuario> oUsuario = Optional.of(aprobador).map(AprobadorSolicitud::getUsuario);
//        String name = oUsuario.map(usuario -> String.join("", usuario.getNombre(), usuario.getApellido()))
//                .orElse("Desconocido");
//
//        Optional<Proveedor> oProveedor = Optional.ofNullable(solicitud)
//                .map(SolicitudBlacklist::getProveedor);
//
//        String ruc = oProveedor.map(Proveedor::getRuc).orElse("Desconocido");
//
//        context.put("aprobador", name);
//        context.put("nombreProveedor", oProveedor.map(Proveedor::getRazonSocial).orElse("Desconocido"));
//        context.put("rucProveedor", ruc);
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
//            if (oUsuario.isPresent()) {
//                String asunto = Optional.ofNullable(solicitud).map(SolicitudBlacklist::getCriteriosBlacklist)
//                        .map(criterio -> criterio.getTipoSolicitudBlacklist())
//                        .map(tipo -> tipo.getDescripcion())
//                        .map(desc -> String.join(" ", "IProvider", "-", desc, ":", ruc))
//                        .orElse("Sin asunto");
//
//                mail.enviar(oUsuario.get().getEmail(), null, asunto, content);
//            }
//
//        } catch (EmailException ex) {
//            logger.error("Error al enviar notificacion al usuario aprobador ", ex);
//        }
//    }


    public void enviar(MailSetting mailSetting, AprobadorSolicitud aprobador, SolicitudBlacklist solicitud){
        Optional<Usuario> oUsuario = Optional.of(aprobador).map(AprobadorSolicitud::getUsuario);
        String nombreAprobador = oUsuario.map(usuario -> String.join("", usuario.getNombre(), usuario.getApellido()))
                .orElse("Desconocido");

        Optional<Proveedor> oProveedor = Optional.ofNullable(solicitud)
                .map(SolicitudBlacklist::getProveedor);

        String rucProveedor = oProveedor.map(Proveedor::getRuc).orElse("Desconocido");
        String nombreProveedor = oProveedor.map(Proveedor::getRazonSocial).orElse("Desconocido");


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
        body = body + "Estimado " + nombreAprobador + "&#44;</h1>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "Se ha registrado una incidencia en el proveedor " + nombreProveedor + "<br/>";
        body = body + "con ruc: " +  rucProveedor + "&#44; sírvase validar la solicitud haciendo click en el siguiente enlace:<br/>";
        body = body + "<a href=\"" + urlPortal + "\">Click aquí para revisar la incidencia</a>";
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
            if (oUsuario.isPresent()) {
                String asunto = Optional.ofNullable(solicitud).map(SolicitudBlacklist::getCriteriosBlacklist)
                        .map(criterio -> criterio.getTipoSolicitudBlacklist())
                        .map(tipo -> tipo.getDescripcion())
                        .map(desc -> String.join(" ", "IProvider", "-", desc, ":", rucProveedor))
                        .orElse("Sin asunto");

                this.enviarCorreoSap(oUsuario.get().getEmail(), asunto, body);
            }
        }catch (Exception ex){
            logger.error("Error al enviar el correo por AprobarObservacionNotificacion del proveedor " + nombreProveedor + " con RUC " + rucProveedor, ex);
        }
    }
}
