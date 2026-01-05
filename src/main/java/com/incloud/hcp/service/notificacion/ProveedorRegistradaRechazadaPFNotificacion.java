package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.domain.Prefactura;
import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.enums.PrefacturaEstadoEnum;
import com.incloud.hcp.repository.OrdenCompraRepository;
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
public class ProveedorRegistradaRechazadaPFNotificacion extends NotificarMail {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private static final String MENSAJE_RECHAZO = "Motivo: %s";
    private static final String ASUNTO = "Factura %s %s";

//    @Value("${cfg.portal.url}")
//    private String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public String enviar(Prefactura prefactura, Usuario proveedor, Usuario aprobador){
//        Sociedad sociedad = prefactura.getSociedad();
        String accion = "Registrada";
        String mensajeEstado = "el registro";
        String textoAccion = "ha sido Registrada exitosamente.";
        String textoFinal = "Puede visualizar el estado de la factura en la ventana 'Publicación de Comprobantes de Pago' del Portal iProvider";
        String referencia = prefactura.getReferencia();
        String ruc = prefactura.getProveedorRuc();

        String tipoDestinatario = "";
        String emailContacto = "";
        String nombreDestinatario = "";
        String lineaNombreAprobador = "";
        String lineaCorreoAprobador = "";

        logger.error("inicio Correo");
        String ordenes = prefactura.getCadenaNumerosOrdenCompra();
        logger.error("ordenes: " + ordenes);
        String[] parts = ordenes.split("[-,]");
        List<String> ocs = Arrays.stream(ordenes.split("[-,]"))
                .map(String::trim)
                .filter(oc -> !oc.isEmpty())
                .distinct()
                .collect(Collectors.toList());
        logger.error("ocs: " + ocs);

        List<String> correosComprador = new ArrayList<>();
        if(!ocs.isEmpty()){
            for (String oc : ocs) {
                logger.error("inicio for {}", oc);
                try {
                    Optional<OrdenCompra> ordenCompra = this.ordenCompraRepository.getOrdenCompraActivaByNumero(oc);
                    logger.error("opOcOrden ");
                    if (ordenCompra.isPresent()) {
                        logger.error("ordenCompraIFCorreo: ");
                        OrdenCompra orden = ordenCompra.get();
                        Usuario user = this.usuarioRepository.findByCodigoSap(orden.getCompradorUsuarioSap());
                        logger.error("user: {}", user != null ? user.getCodigoSap() : "null");
                        if (user != null && user.getEmail() != null) {
                            correosComprador.add(user.getEmail());
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error al obtener orden de compra activa para OC: " + oc, e);
                }
            }
            logger.error("correosComprador: " + correosComprador);
        }
        List<String> correosUnicos = correosComprador.stream()
                .distinct()
                .collect(Collectors.toList());
        logger.error("correosUnicos: " + correosUnicos);

        ////////////////////// VARIABLES METODO VELPA //////////////////////
        logger.error("envio_factura_MOTIVO__" + prefactura.getMotivoRechazo());
        if (PrefacturaEstadoEnum.REGISTRADA.getId() != prefactura.getIdEstadoPrefactura().intValue()) {
            if (aprobador != null) {
                lineaNombreAprobador = aprobador.getNombre() + " " + aprobador.getApellido() + "<br/>";
                lineaCorreoAprobador = "<br/>" + aprobador.getEmail();
            } else {
                lineaNombreAprobador = " " + "<br/>";
                lineaCorreoAprobador = "<br/>" + "iprovider@copeinca.com.pe";
            }

            if (PrefacturaEstadoEnum.RECHAZADA.getId() == prefactura.getIdEstadoPrefactura().intValue()) {
                accion = "Rechazada";
                mensajeEstado = "el rechazo";
                textoAccion = "ha sido Rechazada por el siguiente motivo:";
                //textoFinal = StrUtils.escapeComma(prefactura.getMotivoRechazo());;
                textoFinal = prefactura.getMotivoRechazo();
                logger.error("envio_factura_rechazada__" + textoFinal);
            }
            else if (PrefacturaEstadoEnum.ANULADA.getId() == prefactura.getIdEstadoPrefactura().intValue()){
                accion = "Anulada";
                mensajeEstado = "la anulacion";
                textoAccion = "ha sido Anulada por el siguiente motivo:";
               // textoFinal = StrUtils.escapeComma(prefactura.getMotivoRechazo());
                textoFinal = prefactura.getMotivoRechazo();
                logger.error("envio_factura_anulada__" + textoFinal);
            }
        }
        if(StringUtils.isNotBlank(textoFinal))   {
            textoFinal= StringEscapeUtils.escapeHtml4(textoFinal);
            textoFinal = textoFinal.replaceAll(",", "&#44;");
            textoFinal = textoFinal.replaceAll("%", "&#37;");
        }
        if(proveedor != null){
            tipoDestinatario = "proveedor";
            String mailAprobador = "";
            if(!accion.equals("Registrada")){
                if(aprobador != null){
                    if(aprobador.getEmail() != null){
                        mailAprobador = ","+aprobador.getEmail();
                    }
                }
            }
            emailContacto = proveedor.getEmail()+mailAprobador;
            nombreDestinatario = StrUtils.escapeComma(prefactura.getProveedorRazonSocial());
        } else {
            String mensaje = "Error en los datos de " + tipoDestinatario + " RUC: " + prefactura.getProveedorRuc() + " para el envio de correo por " + mensajeEstado + " de la prefactura: " + referencia;
            logger.error(mensaje);
            return mensaje;
        }

        if (PrefacturaEstadoEnum.RECHAZADA.getId() == prefactura.getIdEstadoPrefactura().intValue() || PrefacturaEstadoEnum.ANULADA.getId() == prefactura.getIdEstadoPrefactura().intValue()) {
            if (correosUnicos != null && !correosUnicos.isEmpty()) {
                StringBuilder correosConcatenados = new StringBuilder(emailContacto != null ? emailContacto : "");
                for (String correoComprador : correosUnicos) {
                    if (!correoComprador.isEmpty()) {
                        correosConcatenados.append(",").append(correoComprador);
                    }
                }
                logger.error("correosUnicos_: " + correosConcatenados);
                emailContacto = correosConcatenados.toString();
            }
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
        body = body + "Estimado Proveedor: " + nombreDestinatario + "<br/>";
        body = body + "RUC " + ruc + "</h1>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "La factura N° " + referencia + " con orden(es) " + ordenes + " " + textoAccion + "<br/>";
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