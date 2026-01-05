package com.incloud.hcp.dto;

/**
 * Created by Administrador on 07/11/2017.
 */
public class SolicitudBlackListAdjuntoDto {
    private String id;
    private String name;
    private String url;
    private String type;

    public SolicitudBlackListAdjuntoDto() {
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
}
