package com.incloud.hcp.service.cmiscf.bean;

public class CmisFolder {

    private String id;
    private String nameFolder;
    private String mensaje;
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNameFolder() {
        return nameFolder;
    }

    public void setNameFolder(String nameFolder) {
        this.nameFolder = nameFolder;
    }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    @Override
    public String toString() {
        return "CmisFolder{" +
                "id='" + id + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", nameFolder='" + nameFolder + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
