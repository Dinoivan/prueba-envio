package com.incloud.hcp.service.cmiscf.bean;

public class CmisBean {

    private String name;
    private String id;

    public CmisBean(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
