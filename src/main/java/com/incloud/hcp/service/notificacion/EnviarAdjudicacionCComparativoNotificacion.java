package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.Parametro;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.repository.ParametroRepository;
import org.apache.commons.mail.EmailException;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Created by Administrador on 13/11/2017.
 */
@Component
public class EnviarAdjudicacionCComparativoNotificacion extends NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String LOGO_VENDOR = "com/incloud/hcp/image/vendor_logo.png";
    private final String LOGO_SAN_MARTIN = "com/incloud/hcp/image/sm_logo.png";
    private final String TEMPLATE = "com/incloud/hcp/templates/portal/TmpAdjudicarCComparativo.html";
    private final String MODULO_PORTAL = "PORTAL";
    private final String RUTA_PORTAL = "RUTA_PORTAL";
    private final String RUTA_PANTALLA_SEGUIMIENTO_OC = "RUTA_PANTALLA_SEGUIMIENTO_OC";
    private final String USUARIO_COPE_ADJUDICACION = "USUARIO_COPE_ADJUDICACION";

//    @Value("${sm.portal.url}")
    @Value("${cfg.portal.url}")
    private  String urlPortal;

    @Value("${cfg.portal.url.public.oc}")
    private String urlPortalPublicacionOC;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;

    @Autowired
    private ParametroRepository parametroRepository;


//    public void enviar(MailSetting mailSetting, Licitacion licitacion, Proveedor proveedor) {
//
//        Mail mail = new Mail();
//        List<Parametro> listaParametroRutaPortal = this.parametroRepository.findByModuloAndTipo(MODULO_PORTAL, RUTA_PORTAL);
//        List<Parametro> listaParametroPantallaCComparativo = this.parametroRepository.findByModuloAndTipo(MODULO_PORTAL, RUTA_PANTALLA_SEGUIMIENTO_OC);
//        List<Parametro> listaParametroUsuarioSM = this.parametroRepository.findByModuloAndTipo(MODULO_PORTAL, USUARIO_COPE_ADJUDICACION);
//        String parametroRutaPortal = "";
//        String parametroPantallaCComparativo = "";
//        String parametroUsuarioSanMartinAdjudicacion = "";
//        String parametroEmailSanMartinAdjudicacion = "";
//
//        if (listaParametroRutaPortal != null && listaParametroRutaPortal.size() > 0) {
//            parametroRutaPortal = listaParametroRutaPortal.get(0).getValor();
//        }
//        if (listaParametroPantallaCComparativo != null && listaParametroPantallaCComparativo.size() > 0) {
//            parametroPantallaCComparativo = listaParametroPantallaCComparativo.get(0).getValor();
//        }
//        if (listaParametroUsuarioSM != null && listaParametroUsuarioSM.size() > 0) {
//            parametroUsuarioSanMartinAdjudicacion = listaParametroUsuarioSM.get(0).getValor();
//            parametroEmailSanMartinAdjudicacion = listaParametroUsuarioSM.get(0).getDescripcion();
//        }
//
//
//        VelocityContext context = new VelocityContext();
//        context.put("nombreProveedor", proveedor.getRazonSocial());
//        context.put("ruc", proveedor.getRuc());
//        context.put("nroLicitacion", licitacion.getNroLicitacionCero());
//        context.put("usuarioSanMartin", parametroUsuarioSanMartinAdjudicacion);
//        context.put("emailSanMartin", parametroEmailSanMartinAdjudicacion);
//        context.put("rutaPortal", parametroRutaPortal);
//        context.put("rutaPantalla", parametroPantallaCComparativo);
//        context.put("url",urlPortal);
//
//        String asunto = "Adjudicación LICITACION Nro. " + licitacion.getNroLicitacionCero() +
//                " al PROVEEDOR: " + proveedor.getRazonSocial() + " (" + proveedor.getRuc() + ")";
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
//            mail.enviar(proveedor.getEmail(), null, asunto, content);
//        } catch (EmailException ex) {
//            logger.error("Error al enviar notificacion del proveedor ", ex);
//        }
//    }

    public void enviar(MailSetting mailSetting, Licitacion licitacion, Proveedor proveedor) {
        List<Parametro> listaParametroUsuarioCope = this.parametroRepository.findByModuloAndTipo(MODULO_PORTAL, USUARIO_COPE_ADJUDICACION);
        String usuarioCopeincaAdjudicacion = "";
        String emailCopeincaAdjudicacion = "";
        if (listaParametroUsuarioCope != null && listaParametroUsuarioCope.size() > 0) {
            usuarioCopeincaAdjudicacion = listaParametroUsuarioCope.get(0).getValor();
            emailCopeincaAdjudicacion = listaParametroUsuarioCope.get(0).getDescripcion();
        }

        String rucProveedor = proveedor.getRuc();
        String nombreProveedor = proveedor.getRazonSocial();
        String numeroLicitacion = licitacion.getNroLicitacionCero();
        String asunto = "Adjudicación LICITACION Nro. " + numeroLicitacion + " al PROVEEDOR: " + nombreProveedor + " (" + rucProveedor + ")";

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
        body = body + "Se le informa que ha resultado ganador de la Licitación Nro. " + numeroLicitacion + "&#44;<br/>";
        body = body + "en breve se enviará la Orden de compra / servicio para su atención.<br/>";
        body = body + "Para visualizar la Orden una vez que haya sido publicada<br/>";
        body = body + "puede ingresar a la siguiente enlace:<br/>";
        body = body + "<a href=\"" + urlPortalPublicacionOC + "\">Publicación de Ordenes de Compra</a>";
        body = body + "</p>";
        if(usuarioCopeincaAdjudicacion != null && !usuarioCopeincaAdjudicacion.isEmpty() && emailCopeincaAdjudicacion != null && !emailCopeincaAdjudicacion.isEmpty()) {
            body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
            body = body + "Para mayor detalle por favor contactarse con:<br/>";
            body = body + usuarioCopeincaAdjudicacion + "<br/>";
            body = body + emailCopeincaAdjudicacion + "<br/>";
            body = body + "</p>";
        }
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
            logger.error("Error al enviar el correo por EnviarAdjudicacionCComparativoNotificacion al proveedor " + nombreProveedor + " con RUC " + rucProveedor + " por la licitacion " + numeroLicitacion, ex);
        }
    }
}
