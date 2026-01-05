package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.SupplierUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProveedorAfiliadoNotificacion extends NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String ASUNTO = "IProvider - Usuario afiliado a proveedor";

    //    @Value("${sm.portal.url}")
    @Value("${cfg.portal.url}")
    private String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;


    public void enviar(SupplierUser supplierUser, Proveedor proveedor) {
        String contactoProveedor = supplierUser.getDisplayName();
        String rucProveedor = proveedor.getRuc();
        String nombreProveedor = proveedor.getRazonSocial();

        String asunto = "";
        String mensaje = "";
        String accion = "";

        asunto = ASUNTO;
        mensaje = "Se ha creado su usuario afiliado al proveedor " + nombreProveedor + "- " + rucProveedor + ". Le llegará un correo de activación de ias@notifications.sap.com";
        accion = "";

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
        body = body + mensaje + "<br/>";
        body = body + "Por favor ingrese al siguiente enlace después de haber activado su cuenta. " + accion + "<br/>";
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
            this.enviarCorreoSap(supplierUser.getEmail(), asunto, body);
        } catch (Exception ex) {
            logger.error("Error al enviar el correo por ProveedorDataMaestraNotificacion al proveedor " + nombreProveedor + " con RUC " + rucProveedor, ex);
        }
    }
}
