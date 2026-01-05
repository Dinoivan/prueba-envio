package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.LogTransaccion;
import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.domain.Sociedad;
import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.enums.OrdenCompraEstadoEnum;
import com.incloud.hcp.repository.SociedadRepository;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.repository.LogTransaccionRepository;
import com.incloud.hcp.util.StrUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContactoAprobadaRechazadaOCNotificacion extends NotificarMail {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String LOGO_VENDOR = "com/incloud/hcp/image/vendor_logo.png";
    private final String LOGO_CFG = "com/incloud/hcp/image/cfg_logo.png";
    private final String TEMPLATE = "com/incloud/hcp/templates/portal/TmpCompradorAprobadaRechazadaOrdenCompra.html";
    private static final String MENSAJE_RECHAZO = "Motivo: %s";
    private static final String ASUNTO = "%s de Orden de Compra N° %s";

    @Value("${cfg.portal.url}")
    private String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;

    @Autowired
    private SociedadRepository sociedadRepository;

    @Autowired
    private LogTransaccionRepository logTransaccionRepository;


    public String enviar(OrdenCompra ordenCompra, Usuario proveedor, Usuario comprador){
        Sociedad sociedad = sociedadRepository.getByCodigoSociedad(ordenCompra.getSociedad());
        String mensajeEstado = "la aprobación";
        String subAsunto = "Aprobación";
        String _vel_accion = "aprobada";
        String _vel_NroOrdenCompra = ordenCompra.getNumeroOrdenCompra();

        String tipoDestinatario = "";
        String emailContacto = "";
        String _vel_mensajeRechazo = "";
        String _vel_NombreDestinatario = "";
        String _vel_TextoEmpresa = "";

        ////////////////////// VARIABLES METODO VELPA //////////////////////

        if (proveedor == null && comprador != null) { // email a comprador (cliente)
            tipoDestinatario = "comprador";
            emailContacto = comprador.getEmail();
            _vel_NombreDestinatario = sociedad.getRazonSocial();
            _vel_TextoEmpresa = "para la empresa proveedora " + ordenCompra.getProveedorRazonSocial();
        }
        else if(proveedor != null && comprador == null){ // email a responsable de compras (proveedor)
            tipoDestinatario = "proveedor";
            emailContacto = proveedor.getEmail();
            _vel_NombreDestinatario = StrUtils.escapeComma(ordenCompra.getProveedorRazonSocial());
            _vel_TextoEmpresa = "de la empresa " + sociedad.getRazonSocial();
        }
        else {
            String mensaje = "Error en los datos de proveedor/comprador para el envio de correo de Publicacion de OC: " + ordenCompra.getNumeroOrdenCompra();
            logger.error(mensaje);
            return mensaje;
        }

        if(OrdenCompraEstadoEnum.RECHAZADA.getId() == ordenCompra.getIdEstadoOrdenCompra().intValue()){
            _vel_mensajeRechazo = String.format(MENSAJE_RECHAZO, StrUtils.escapeComma(ordenCompra.getMotivoRechazo()));
            _vel_accion = "rechazada";
            mensajeEstado = "el rechazo";
            subAsunto = "Rechazo";
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
        body = body + "Estimados Señores:<br/>";
        body = body + _vel_NombreDestinatario + "</h1>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "La Orden de Compra N° " + _vel_NroOrdenCompra + " " + _vel_TextoEmpresa;
        body = body + " ha sido " + _vel_accion + " exitosamente en el Portal de IProvider Copeinca.<br/>";
        body = body + _vel_mensajeRechazo;
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
        body = body + "IProvider COPEINCA";
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";

        body = body + "</body>";
        body = body + "</html>";

        //////////////////////  END  METODO VELPA //////////////////////

        try{
            LogTransaccion logTransaccion = new LogTransaccion();
            logTransaccion.setLogFecha(DateUtils.getCurrentTimestamp());
            logTransaccion.setTipoRegistro("ContactoAprobadaRechazadaOCNotificacion");
            logger.error(emailContacto + " - " + _vel_accion + " - " + _vel_NroOrdenCompra);
            logTransaccion.setEnvioTrama(emailContacto + " - " + _vel_accion + " - " + _vel_NroOrdenCompra);
            this.logTransaccionRepository.save(logTransaccion);
            this.enviarCorreoSap(emailContacto,String.format(ASUNTO,subAsunto,_vel_NroOrdenCompra),body);
        }catch (Exception ex){
            logger.error("Error al enviar el Correo de " + mensajeEstado + " de la orden de compra " + _vel_NroOrdenCompra, ex);
        }
        logger.error("<--KG_LOG-->:LOG_CORREO_10");
        return "Se envio correctamente el correo de " + mensajeEstado + " de la orden de compra " + _vel_NroOrdenCompra + " al " + tipoDestinatario;
    }
}