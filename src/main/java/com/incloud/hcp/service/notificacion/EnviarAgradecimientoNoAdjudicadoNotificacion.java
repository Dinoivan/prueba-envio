package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.repository.ParametroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrador on 13/11/2017.
 */
@Component
public class EnviarAgradecimientoNoAdjudicadoNotificacion extends NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String LOGO_VENDOR = "com/incloud/hcp/image/vendor_logo.png";
    private final String LOGO_SAN_MARTIN = "com/incloud/hcp/image/sm_logo.png";
    private final String TEMPLATE = "com/incloud/hcp/templates/portal/TmpAgradecimientoNoAdjudicado.html";
    private final String MODULO_PORTAL = "PORTAL";
    private final String RUTA_PORTAL = "RUTA_PORTAL";
    private final String USUARIO_COPE_ADJUDICACION = "USUARIO_COPE_ADJUDICACION";

//    @Value("${sm.portal.url}")
    @Value("${cfg.portal.url}")
    private  String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;

    @Autowired
    private ParametroRepository parametroRepository;


//    public void enviar(MailSetting mailSetting, Licitacion licitacion, Proveedor proveedor) {
//
//        Mail mail = new Mail();
//        List<Parametro> listaParametroRutaPortal = this.parametroRepository.findByModuloAndTipo(MODULO_PORTAL, RUTA_PORTAL);
//         List<Parametro> listaParametroUsuarioSM = this.parametroRepository.findByModuloAndTipo(MODULO_PORTAL, USUARIO_COPE_ADJUDICACION);
//        String parametroRutaPortal = "";
//        String parametroPantallaCComparativo = "";
//        String parametroUsuarioSanMartinAdjudicacion = "";
//        String parametroEmailSanMartinAdjudicacion = "";
//
//        if (listaParametroRutaPortal != null && listaParametroRutaPortal.size() > 0) {
//            parametroRutaPortal = listaParametroRutaPortal.get(0).getValor();
//        }
//
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
//        String asunto = "Participación LICITACION Nro. " + licitacion.getNroLicitacionCero() +
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
        String rucProveedor = proveedor.getRuc();
        String nombreProveedor = proveedor.getRazonSocial();
        String numeroLicitacion = licitacion.getNroLicitacionCero();
        String asunto = "Participación LICITACION Nro. " + numeroLicitacion + " al PROVEEDOR: " + nombreProveedor + " (" + rucProveedor + ")";

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
        body = body + "Agradecemos su tiempo y el interés por parte de ustedes de participar en este proceso de Licitación Nro. " + numeroLicitacion + "&#44;<br/>";
        body = body + "sin embargo en esta oportunidad hemos optado por otra opción que se ajusta más a las necesidades de Copeinca.<br/>";
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
            logger.error("Error al enviar el correo por EnviarAgradecimientoNoAdjudicadoNotificacion al proveedor " + nombreProveedor + " con RUC " + rucProveedor + " por la licitacion " + numeroLicitacion, ex);
        }
    }
}
