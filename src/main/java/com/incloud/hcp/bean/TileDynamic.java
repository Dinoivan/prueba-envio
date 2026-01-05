package com.incloud.hcp.bean;

import java.math.BigDecimal;

/**
 * Created by Administrador on 24/10/2017.
 */
public class TileDynamic {
    private String icon;
    private String info;
    private String infoState;
    private BigDecimal number;
    private Integer numberDigits;
    private String numberFactor;
    private String numberState;
    private String numberUnit;
    private String stateArrow;
    private String subtitle;
    private String title;

    public TileDynamic() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfoState() {
        return infoState;
    }

    public void setInfoState(String infoState) {
        this.infoState = infoState;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public Integer getNumberDigits() {
        return numberDigits;
    }

    public void setNumberDigits(Integer numberDigits) {
        this.numberDigits = numberDigits;
    }

    public String getNumberFactor() {
        return numberFactor;
    }

    public void setNumberFactor(String numberFactor) {
        this.numberFactor = numberFactor;
    }

    public String getNumberState() {
        return numberState;
    }

    public void setNumberState(String numberState) {
        this.numberState = numberState;
    }

    public String getNumberUnit() {
        return numberUnit;
    }

    public void setNumberUnit(String numberUnit) {
        this.numberUnit = numberUnit;
    }

    public String getStateArrow() {
        return stateArrow;
    }

    public void setStateArrow(String stateArrow) {
        this.stateArrow = stateArrow;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TileDynamic{" +
                "icon='" + icon + '\'' +
                ", info='" + info + '\'' +
                ", infoState='" + infoState + '\'' +
                ", number=" + number +
                ", numberDigits=" + numberDigits +
                ", numberFactor='" + numberFactor + '\'' +
                ", numberState='" + numberState + '\'' +
                ", numberUnit='" + numberUnit + '\'' +
                ", stateArrow='" + stateArrow + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
