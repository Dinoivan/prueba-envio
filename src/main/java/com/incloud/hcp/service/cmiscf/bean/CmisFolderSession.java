package com.incloud.hcp.service.cmiscf.bean;

import org.apache.chemistry.opencmis.client.api.Session;

public class CmisFolderSession {

    private CmisFolder cmisFolder;
    private Session session;

    public CmisFolder getCmisFolder() {
        return cmisFolder;
    }

    public void setCmisFolder(CmisFolder cmisFolder) {
        this.cmisFolder = cmisFolder;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "CmisFolderSession{" +
                "cmisFolder=" + cmisFolder +
                ", session=" + session +
                '}';
    }
}
