package com.incloud.hcp.enums;

public enum TipoArchivoEnum {
    PDF("pdf"), XML("xml");

    private String fileType;

    TipoArchivoEnum(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }
}
