package com.incloud.hcp.service.notificacion;

/**
 * Created by Administrador on 13/11/2017.
 */
public class MailSetting {
    private String host;
    private String port;
    private String user;
    private String nameFrom;
    private String emailFrom;
    private String password;

    public MailSetting() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameFrom() {
        return nameFrom;
    }

    public void setNameFrom(String nameFrom) {
        this.nameFrom = nameFrom;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[MailSetting ");
        sb.append(this.host);
        sb.append(",");
        sb.append(this.port);
        sb.append(",");
        sb.append(this.user);
        sb.append("]");
        return sb.toString();
    }
}
