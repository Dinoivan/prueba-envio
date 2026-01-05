package com.incloud.hcp.service.cmiscf.bean;


public class CmisDocument {

    private CmisFile cmisFile;

    public CmisFile getCmisFile() {
        return cmisFile;
    }

    public void setCmisFile(CmisFile cmisFile) {
        this.cmisFile = cmisFile;
    }

    @Override
    public String toString() {
        return "CmisDocument{" +
                "cmisFile=" + cmisFile +
                '}';
    }
}
