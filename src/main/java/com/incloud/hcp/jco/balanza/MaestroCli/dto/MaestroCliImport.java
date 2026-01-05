package com.incloud.hcp.jco.balanza.MaestroCli.dto;

public class MaestroCliImport {
    private String kunnr;
    private String name1;
    private String stcd1;

    public String getKunnr() {
        return kunnr;
    }

    public void setKunnr(String kunnr) {
        this.kunnr = kunnr;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getStcd1() {
        return stcd1;
    }

    public void setStcd1(String stcd1) {
        this.stcd1 = stcd1;
    }

    @Override
    public String toString() {
        return "MaestroCliImport{" +
                "kunnr='" + kunnr + '\'' +
                ", name1='" + name1 + '\'' +
                ", stcd1='" + stcd1 + '\'' +
                '}';
    }
}
