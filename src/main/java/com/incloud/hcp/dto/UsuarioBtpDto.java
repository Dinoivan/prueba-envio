package com.incloud.hcp.dto;

public class UsuarioBtpDto {
    private String ruc;
    private String email;
    private String usuariop;

    public UsuarioBtpDto(){

    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuariop() {
        return usuariop;
    }

    public void setUsuariop(String usuariop) {
        this.usuariop = usuariop;
    }
}
