package com.incloud.hcp.service.cmiscf.bean;

/**
 * Created by Administrador on 26/09/2017.
 */
public class CmisFile {
    private String id;
    private String name;
    private String url;
    private String type;
    private Long size;
    private String nameFinal;
    private String extension;
    private String carpetaId;
    private String nombreFolder;
    private String other; // campo adicional

    private CmisFile parent;

    public CmisFile() {
        this.parent = null;
    }

    public CmisFile(String id, String name, String url, String type) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
    }

    public CmisFile(String id, String name, String url, String type, Long size) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }

    public CmisFile(String id, String name, String url, String type, String other) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.other = other;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CmisFile getParent() {
        return parent;
    }

    public void setParent(CmisFile parent) {
        this.parent = parent;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getNameFinal() {
        return nameFinal;
    }

    public void setNameFinal(String nameFinal) {
        this.nameFinal = nameFinal;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCarpetaId() {
        return carpetaId;
    }

    public void setCarpetaId(String carpetaId) {
        this.carpetaId = carpetaId;
    }

    public String getNombreFolder() {
        return nombreFolder;
    }

    public void setNombreFolder(String nombreFolder) {
        this.nombreFolder = nombreFolder;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "CmisFile{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", nameFinal='" + nameFinal + '\'' +
                ", extension='" + extension + '\'' +
                ", carpetaId='" + carpetaId + '\'' +
                ", nombreFolder='" + nombreFolder + '\'' +
                ", parent=" + parent +
                '}';
    }
}
