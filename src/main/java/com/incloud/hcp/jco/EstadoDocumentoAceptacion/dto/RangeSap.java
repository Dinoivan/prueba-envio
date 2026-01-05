package com.incloud.hcp.jco.EstadoDocumentoAceptacion.dto;

import java.io.Serializable;

public class RangeSap implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sign;
    private String option;
    private String low;
    private String high;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    @Override
    public String toString() {
        return "RangeSap{" +
                "sign='" + sign + '\'' +
                ", option='" + option + '\'' +
                ", low='" + low + '\'' +
                ", high='" + high + '\'' +
                '}';
    }
}
