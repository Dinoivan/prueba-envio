package com.incloud.hcp.jco.balanza.MaestroCli.dto;

public class MaestroCliResponse {
    private String kunnr;
    private String street;
    private String smtpAddr;
    private String vkorg;
    private String vtweg;
    private String spart;
    private String name1;
    private String stcd1;
    private String region;
    private String bezei;
    private String city1;
    private String city2;

    public String getKunnr() {
        return kunnr;
    }

    public void setKunnr(String kunnr) {
        this.kunnr = kunnr;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSmtpAddr() {
        return smtpAddr;
    }

    public void setSmtpAddr(String smtpAddr) {
        this.smtpAddr = smtpAddr;
    }

    public String getVkorg() {
        return vkorg;
    }

    public void setVkorg(String vkorg) {
        this.vkorg = vkorg;
    }

    public String getVtweg() {
        return vtweg;
    }

    public void setVtweg(String vtweg) {
        this.vtweg = vtweg;
    }

    public String getSpart() {
        return spart;
    }

    public void setSpart(String spart) {
        this.spart = spart;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBezei() {
        return bezei;
    }

    public void setBezei(String bezei) {
        this.bezei = bezei;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    @Override
    public String toString() {
        return "MaestroCliResponse{" +
                "kunnr='" + kunnr + '\'' +
                ", street='" + street + '\'' +
                ", smtpAddr='" + smtpAddr + '\'' +
                ", vkorg='" + vkorg + '\'' +
                ", vtweg='" + vtweg + '\'' +
                ", spart='" + spart + '\'' +
                ", name1='" + name1 + '\'' +
                ", stcd1='" + stcd1 + '\'' +
                ", region='" + region + '\'' +
                ", bezei='" + bezei + '\'' +
                ", city1='" + city1 + '\'' +
                ", city2='" + city2 + '\'' +
                '}';
    }
}
