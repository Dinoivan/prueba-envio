package com.incloud.hcp.service.cmiscf.bean;

import org.apache.chemistry.opencmis.client.api.Session;

import java.util.Map;

public class CmisSession {

    private Session session;
    private Map parameters;
    private String mensajeError;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    @Override
    public String toString() {
        return "CmisSession{" +
                "session=" + session +
                ", parameters=" + parameters +
                ", mensajeError='" + mensajeError + '\'' +
                '}';
    }
}
