package com.incloud.hcp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class GenericRequestDTO {

    private String stringParameter1;
    private Integer integerParameter1;

    private List<String> stringList1;
    private List<Integer> integerList1;


    @JsonProperty("StringParameter1")
    public String getStringParameter1() {
        return stringParameter1;
    }

    public void setStringParameter1(String stringParameter1) {
        this.stringParameter1 = stringParameter1;
    }

    @JsonProperty("IntegerParameter1")
    public Integer getIntegerParameter1() {
        return integerParameter1;
    }

    public void setIntegerParameter1(Integer integerParameter1) {
        this.integerParameter1 = integerParameter1;
    }


    public List<String> getStringList1() {
        return stringList1;
    }

    public void setStringList1(List<String> stringList1) {
        this.stringList1 = stringList1;
    }

    public List<Integer> getIntegerList1() {
        return integerList1;
    }

    public void setIntegerList1(List<Integer> integerList1) {
        this.integerList1 = integerList1;
    }


    @Override
    public String toString() {
        return "GenericRequestDTO{" +
                "stringParameter1='" + stringParameter1 + '\'' +
                ", integerParameter1=" + integerParameter1 +
                ", stringList1=" + stringList1 +
                ", integerList1=" + integerList1 +
                '}';
    }
}
