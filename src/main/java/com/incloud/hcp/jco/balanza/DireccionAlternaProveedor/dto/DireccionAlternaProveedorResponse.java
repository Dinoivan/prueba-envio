package com.incloud.hcp.jco.balanza.DireccionAlternaProveedor.dto;

public class DireccionAlternaProveedorResponse {
    private String lfdnr;
    private String street;
    private String houseNum1;
    private String houseNum2;
    private String strSuppl2;
    private String land1;
    private String region;
    private String bezei;
    private String city1;
    private String city2;
    public String getLfdnr() {
        return lfdnr;
    }

    public void setLfdnr(String lfdnr) {
        this.lfdnr = lfdnr;
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNum1() {
        return houseNum1;
    }

    public void setHouseNum1(String houseNum1) {
        this.houseNum1 = houseNum1;
    }

    public String getHouseNum2() {
        return houseNum2;
    }

    public void setHouseNum2(String houseNum2) {
        this.houseNum2 = houseNum2;
    }

    public String getStrSuppl2() {
        return strSuppl2;
    }

    public void setStrSuppl2(String strSuppl2) {
        this.strSuppl2 = strSuppl2;
    }

    public String getLand1() {
        return land1;
    }

    public void setLand1(String land1) {
        this.land1 = land1;
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
        return "DireccionAlternaResponse{" +
                "lfdnr='" + lfdnr + '\'' +
                ", street='" + street + '\'' +
                ", houseNum1='" + houseNum1 + '\'' +
                ", houseNum2='" + houseNum2 + '\'' +
                ", strSuppl2='" + strSuppl2 + '\'' +
                ", land1='" + land1 + '\'' +
                ", region='" + region + '\'' +
                ", bezei='" + bezei + '\'' +
                ", city1='" + city1 + '\'' +
                ", city2='" + city2 + '\'' +
                '}';
    }
}
