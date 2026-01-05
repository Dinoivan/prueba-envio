package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.domain.Sociedad;
import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.domain.almacen.OrdenDespacho;
import com.incloud.hcp.repository.SociedadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ContactoVisualizadoODNotificacion extends NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String LOGO_VENDOR = "com/incloud/hcp/image/vendor_logo.png";
    private final String LOGO_CFG = "logoCopeinca/CFGInvestment.png";
    private final String LOGO_COPEINCA = "logoCopeinca/COPEINCA.png";
    private final String TEMPLATE = "com/incloud/hcp/templates/portal/TmpContactoVisualizadaOrdenCompra.html";
    private static final String ASUNTO = "Visualización de Orden de Compra N° %s";

    @Value("${cfg.portal.url}")
    private String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;


    @Autowired
    private SociedadRepository sociedadRepository;


    public void enviar(OrdenDespacho ordenDespacho, Usuario proveedor, Usuario comprador){
        Sociedad sociedad = sociedadRepository.getByCodigoSociedad(ordenDespacho.getSociedad());
        String emailContacto = "";

        String _vel_Contacto = "";
        String _vel_NombreDestinatario = "";
        String _vel_TextoEmpresa = "";
        String _vel_NroOrdenCompra = ordenDespacho.getNumeroOrdenCompra();

        if (proveedor == null && comprador != null) { // email a comprador (cliente)
            emailContacto = comprador.getEmail();
            _vel_Contacto = comprador.getNombre() + " " + comprador.getApellido();
            _vel_NombreDestinatario = sociedad.getRazonSocial();
            _vel_TextoEmpresa = "La empresa proveedora " + ordenDespacho.getProveedorRazonSocial();
        }
        else if(proveedor != null && comprador == null){ // email a responsable de compras (proveedor)
//            emailContacto = proveedor.getEmailPersonaCompra();
//            _vel_Contacto = proveedor.getNombrePersonaCompra();
            emailContacto = proveedor.getEmail();
            _vel_Contacto = proveedor.getApellido();
            _vel_NombreDestinatario = ordenDespacho.getProveedorRazonSocial();
            _vel_TextoEmpresa = "Se informa que personal de su empresa";
        }
        else{
            logger.error("Error en los datos de proveedor/comprador para el envio de correo de Visualizacion de OC: " + _vel_NroOrdenCompra);
            return;
        }

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
        body = body + _vel_TextoEmpresa + " ha visualizado la Orden de Compra N° " + _vel_NroOrdenCompra;
        body = body + " a traves del Portal de IProvider Copeinca.<br/>";
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


        try{
            this.enviarCorreoSap(emailContacto, String.format(ASUNTO,_vel_NroOrdenCompra), body);
        }catch (Exception ex){
            logger.error("Error al enviar Correo por Visualización OC a " + emailContacto, ex);
        }
    }
}
