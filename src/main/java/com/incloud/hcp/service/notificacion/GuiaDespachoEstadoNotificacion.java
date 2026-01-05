package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.*;
import com.incloud.hcp.domain.almacen.GuiaDespacho;
import com.incloud.hcp.enums.PrefacturaEstadoEnum;
import com.incloud.hcp.repository.OrdenCompraRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.repository.UsuarioRepository;
import com.incloud.hcp.util.StrUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GuiaDespachoEstadoNotificacion extends NotificarMail {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private static final String MENSAJE_RECHAZO = "Motivo: %s";
    private static final String ASUNTO = "Guia %s %s";

//    @Value("${cfg.portal.url}")
//    private String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;


    public String enviar(GuiaDespacho guiaDespacho, Proveedor proveedor, List<ResponsableAlmacen> listResponsableAlmacen, String aprobador, String accion){
//        Sociedad sociedad = prefactura.getSociedad();
        String mensajeEstado = "el registro";
        String textoAccion = "ha sido " + accion + " exitosamente.";
        String textoTile = "";

        String referencia = guiaDespacho.getNumeroGuiaRemision();

        String ruc = proveedor.getRuc();
        String nombreDestinatario = proveedor.getRazonSocial();

        String tipoDestinatario = "";
        String emailContacto = "";

        String lineaNombreAprobador = "";
        String lineaCorreoAprobador = "";

        logger.error("inicio Correo");


        ////////////////////// VARIABLES METODO VELPA //////////////////////
        logger.error("envio_factura_MOTIVO__" + guiaDespacho.getMotivoRechazo());
        if (aprobador != null && !aprobador.isEmpty()) {
            emailContacto = aprobador;
        }

        if ("Registrada".equals(accion)) {

            StringBuilder mailAprobador = new StringBuilder();
            textoTile = "Aprobar";

            for (ResponsableAlmacen item : listResponsableAlmacen) {
                if (item.getEmail() != null && !item.getEmail().isEmpty()) {
                    if (mailAprobador.length() > 0) {
                        mailAprobador.append(",");
                    }
                    mailAprobador.append(item.getEmail());
                }
            }

            if (!emailContacto.isEmpty() && mailAprobador.length() > 0) {
                emailContacto += ",";
            }
            emailContacto += mailAprobador.toString();

        } else if ("Anulada".equals(accion)) {

            StringBuilder mailAprobador = new StringBuilder();
            textoTile = "Visualizar";

            if (proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()) {
                mailAprobador.append(proveedor.getEmail());
            }

            for (ResponsableAlmacen item : listResponsableAlmacen) {
                if (item.getEmail() != null && !item.getEmail().isEmpty()) {
                    if (mailAprobador.length() > 0) {
                        mailAprobador.append(",");
                    }
                    mailAprobador.append(item.getEmail());
                }
            }

            if (!emailContacto.isEmpty() && mailAprobador.length() > 0) {
                emailContacto += ",";
            }
            emailContacto += mailAprobador.toString();

        } else if ("Rechazada".equals(accion) || "Ingresada".equals(accion)) {

            textoTile = "Visualizar";

            if (proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()) {
                if (!emailContacto.isEmpty()) {
                    emailContacto += ",";
                }
                emailContacto += proveedor.getEmail();
            }
        }

        String textoFinal = "Puede visualizar el estado del despacho en la ventana '" +  textoTile + " Despachos'del Portal iProvider";
//        if(StringUtils.isNotBlank(textoFinal))   {
//            textoFinal= StringEscapeUtils.escapeHtml4(textoFinal);
//            textoFinal = textoFinal.replaceAll(",", "&#44;");
//            textoFinal = textoFinal.replaceAll("%", "&#37;");
//        }

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
        body = body + "Estimado Proveedor: " + nombreDestinatario + "<br/>";
        body = body + "RUC " + ruc + "</h1>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "El despacho con guia° " + referencia + " " + textoAccion + "<br/>";
        body = body + textoFinal;
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
        body = body + lineaNombreAprobador;
        body = body + "Area Contable<br/>";
        body = body + "COPEINCA";
        body = body + lineaCorreoAprobador;
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";

        body = body + "</body>";
        body = body + "</html>";

        //////////////////////  END  METODO VELPA //////////////////////

        try{
            this.enviarCorreoSap(emailContacto,String.format(ASUNTO, referencia, accion), body);
        }catch (Exception ex){
            logger.error("Error al enviar el correo por " + mensajeEstado + " de la prefactura " + referencia + " al " + tipoDestinatario, ex);
        }
        return "Se envio correctamente el correo por " + mensajeEstado + " de la prefactura " + referencia + " al " + tipoDestinatario;
    }
}