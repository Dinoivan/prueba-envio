package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.SolicitudBlacklist;
import org.apache.commons.mail.EmailException;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by USER on 15/11/2017.
 */
@Component
public class SolicitudPOFinalizacionNotificacion extends NotificarMail{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String LOGO_VENDOR = "com/incloud/hcp/image/vendor_logo.png";
    private final String LOGO_SAN_MARTIN = "com/incloud/hcp/image/sm_logo.png";
    private final String TEMPLATE = "com/incloud/hcp/templates/portal/TmpSolicitudPOFinalizacion.html";

//    @Value("${sm.portal.url}")
    @Value("${cfg.portal.url}")
    private  String urlPortal;

    public void enviar(MailSetting mailSetting, SolicitudBlacklist solicitud) {

        Mail mail = new Mail();
        VelocityContext context = new VelocityContext();

        String name = solicitud.getAdminNameRevision();

        Optional<Proveedor> oProveedor = Optional.ofNullable(solicitud)
                .map(SolicitudBlacklist::getProveedor);

        String ruc = oProveedor.map(Proveedor::getRuc).orElse("Desconocido");

        context.put("nombreAdmin", name);
        context.put("url", this.urlPortal);
        context.put("nombreProveedor", oProveedor.map(Proveedor::getRazonSocial).orElse("Desconocido"));
        context.put("rucProveedor", ruc);

        Optional.ofNullable(generateCid(mail.getHtmlMail(), LOGO_VENDOR))
                .ifPresent(cid -> context.put("vendor_logo", cid));

        Optional.ofNullable(generateCid(mail.getHtmlMail(), LOGO_SAN_MARTIN))
                .ifPresent(cid -> context.put("sm_logo", cid));

        String content = Optional.ofNullable(TEMPLATE)
                .map(url -> url + "")
                .map(template -> {
                    int i = 0;
                    return getContentMail(context, template);
                })
                .orElse("");
        mail.setMailSetting(mailSetting);
        try {

            String asunto = Optional.ofNullable(solicitud).map(SolicitudBlacklist::getCriteriosBlacklist)
                    .map(criterio -> criterio.getTipoSolicitudBlacklist())
                    .map(tipo -> tipo.getDescripcion())
                    .map(desc -> String.join(" ", "IProvider", "- Estado Final:", desc, ":", ruc))
                    .orElse("Sin asunto");

            mail.enviar("", null, asunto, content);


        } catch (EmailException ex) {
            logger.error("Error al enviar notificacion al usuario ", ex);
        }
    }
}
