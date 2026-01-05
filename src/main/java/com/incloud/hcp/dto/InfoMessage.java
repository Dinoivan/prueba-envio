package com.incloud.hcp.dto;

public class InfoMessage {
    private String messageCode;
    private String messageText1;
    private String messageText2;
    private Integer messageInteger;

    public InfoMessage(String messageCode, String messageText1) {
        this.messageCode = messageCode;
        this.messageText1 = messageText1;
    }

    public InfoMessage() {
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageText1() {
        return messageText1;
    }

    public void setMessageText1(String messageText1) {
        this.messageText1 = messageText1;
    }

    public String getMessageText2() {
        return messageText2;
    }

    public void setMessageText2(String messageText2) {
        this.messageText2 = messageText2;
    }

    public Integer getMessageInteger() {
        return messageInteger;
    }

    public void setMessageInteger(Integer messageInteger) {
        this.messageInteger = messageInteger;
    }

}