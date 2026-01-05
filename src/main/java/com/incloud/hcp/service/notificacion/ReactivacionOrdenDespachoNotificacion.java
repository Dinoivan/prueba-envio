package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.EstadoOrdenCompra;
import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.almacen.OrdenDespacho;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by USER on 11/12/2017.
 */
@Component
public class ReactivacionOrdenDespachoNotificacion extends NotificarMail{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String LOGO_VENDOR = "com/incloud/hcp/image/vendor_logo.png";
    private final String LOGO_SAN_MARTIN = "com/incloud/hcp/image/sm_logo.png";
    private final String TEMPLATE = "com/incloud/hcp/templates/portal/TmpAnularLicitacion.html";

//    @Value("${sm.portal.url}")
    @Value("${cfg.portal.url}")
    private  String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;


//    public void enviar(MailSetting mailSetting, ProveedorCustom proveedor, Licitacion licitacion, UserSession userSession) {
//
//        Mail mail = new Mail();
//        VelocityContext context = new VelocityContext();
//
//        String nroLicitacionString = this.getNroLicitacionString(licitacion.getNroLicitacion(), licitacion.getAnioLicitacion());
//        context.put("nombreProveedor", proveedor.getRazonSocial());
//        context.put("nroLicitacion", nroLicitacionString);
//        context.put("nombreUsuarioAnulacion",licitacion.getUsuarioAnulacionId());
//        context.put("emailUsuarioAnulacion", userSession.getMail());
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
//            String asunto = "Anulacion Licitacion Nro. " + nroLicitacionString;
//
//            mail.enviar(proveedor.getEmail(), null, asunto, content);
//
//        } catch (EmailException ex) {
//            logger.error("Error al enviar notificacion", ex);
//        }
//    }


    public String getNroLicitacionString(Integer numero, Integer annio){
        String texto = ("00000000" + numero);
        String nroLicitacionString = annio + texto.substring(texto.length() - 8, texto.length());

        return nroLicitacionString;
    }

    public void enviar(OrdenDespacho ordenDespacho, EstadoOrdenCompra estadoOrdenCompra, Proveedor proveedor) {
        //String rucProveedor = proveedor.getRuc();
        String nombreProveedor = proveedor.getRazonSocial();
        String numeroOrdenCompra = ordenDespacho.getNumeroOrdenCompra();
        String estadoDesc = estadoOrdenCompra.getDescripcion();
        String emailProveedor = proveedor.getEmail();
        String asunto = "Reactivación Orden Compra Nro. " + numeroOrdenCompra;

        logger.error("Reactivacion ::: nombreProveedor ::: " +  nombreProveedor);
        logger.error("Reactivacion ::: numeroOrdenCompra ::: " +  numeroOrdenCompra);
        logger.error("Reactivacion ::: estadoDesc ::: " +  estadoDesc);
        logger.error("Reactivacion ::: emailProveedor ::: " +  emailProveedor);
        logger.error("Reactivacion ::: asunto ::: " +  asunto);

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
        body = body + "Se le informa que la orden de compra Nro. " + numeroOrdenCompra + " ha sido " + estadoDesc +"&#44;<br/>";
        //body = body + "para mayor detalle por favor contactarse a " + emailProveedor + ".";
        body = body + "</p>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "Agradecemos su participación.";
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
            this.enviarCorreoSap(emailProveedor, asunto, body);
        } catch (Exception ex) {
            logger.error("Error al enviar el correo por LicitacionAnularNotificacion al proveedor " + nombreProveedor +  " por la oc " + numeroOrdenCompra, ex);
        }
    }
}
