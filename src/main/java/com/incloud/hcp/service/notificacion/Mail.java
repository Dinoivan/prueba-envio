package com.incloud.hcp.service.notificacion;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * Created by Administrador on 13/11/2017.
 */

public class Mail {
    private MailSetting mailSetting;
    private final HtmlEmail mail;
    private final String CHARSET = "UTF-8";

    public Mail() {
        this.mail = new HtmlEmail();
    }

    public void setMailSetting(MailSetting mailSetting) {
        this.mailSetting = mailSetting;
    }

    public HtmlEmail getHtmlMail() {
        return this.mail;
    }

    public void enviar(String destino, String copia, String asunto, String mensaje, String... adjunto) throws EmailException {
//        this.mail.setHostName(this.mailSetting.getHost());
//        this.mail.setSmtpPort(Integer.parseInt(this.mailSetting.getPort()));
//        this.mail.setCharset(CHARSET);
//        this.mail.setAuthenticator(new DefaultAuthenticator(this.mailSetting.getUser(), this.mailSetting.getPassword()));
//
//        this.mail.setFrom(this.mailSetting.getEmailFrom(), this.mailSetting.getNameFrom());
//        this.mail.setStartTLSEnabled(true);
//
//        if (destino != null) {
//            String[] to = destino.split(",");
//            if (to != null && to.length > 0) {
//                for (String t : to) {
//                    if (!t.isEmpty()) {
//                        this.mail.addTo(t.trim());
//                    }
//                }
//            }
//        }
//
//        if (copia != null) {
//            String[] cc = copia.split(",");
//            if (cc != null && cc.length > 0) {
//                for (String c : cc) {
//                    if (!c.isEmpty()) {
//                        this.mail.addCc(c.trim());
//                    }
//                }
//            }
//        }
//
//        this.mail.setSubject(asunto);
//        this.mail.setHtmlMsg(mensaje);
//
//        Optional.ofNullable(adjunto)
//                .filter(val -> val.length>0)
//                .ifPresent(val -> {
//                    for(int i = 0; i < val.length; i++){
//                        if(val[i] != null ){
//                            File file = new File(val[i]);
//                            if(file.exists()) {
//                                try {
//                                    this.mail.attach(file);
//                                } catch (EmailException ex) {
//                                    ex.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//                });
//        this.mail.send();
    }
}
