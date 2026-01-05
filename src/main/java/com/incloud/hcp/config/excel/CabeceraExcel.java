package com.incloud.hcp.config.excel;

public class CabeceraExcel {

    private String title;
    private Integer fondoColor01;
    private Integer fondoColor02;
    private Integer fondoColor03;
    private Integer letraColor01;
    private Integer letraColor02;
    private Integer letraColor03;
    private String comentario;

    private String id;
    private Integer ancho;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFondoColor01() {
        return fondoColor01;
    }

    public void setFondoColor01(Integer fondoColor01) {
        this.fondoColor01 = fondoColor01;
    }

    public Integer getFondoColor02() {
        return fondoColor02;
    }

    public void setFondoColor02(Integer fondoColor02) {
        this.fondoColor02 = fondoColor02;
    }

    public Integer getFondoColor03() {
        return fondoColor03;
    }

    public void setFondoColor03(Integer fondoColor03) {
        this.fondoColor03 = fondoColor03;
    }

    public Integer getLetraColor01() {
        return letraColor01;
    }

    public void setLetraColor01(Integer letraColor01) {
        this.letraColor01 = letraColor01;
    }

    public Integer getLetraColor02() {
        return letraColor02;
    }

    public void setLetraColor02(Integer letraColor02) {
        this.letraColor02 = letraColor02;
    }

    public Integer getLetraColor03() {
        return letraColor03;
    }

    public void setLetraColor03(Integer letraColor03) {
        this.letraColor03 = letraColor03;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAncho() {
        return ancho;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }

    @Override
    public String toString() {
        return "CabeceraExcel{" +
                "title='" + title + '\'' +
                ", fondoColor01=" + fondoColor01 +
                ", fondoColor02=" + fondoColor02 +
                ", fondoColor03=" + fondoColor03 +
                ", letraColor01=" + letraColor01 +
                ", letraColor02=" + letraColor02 +
                ", letraColor03=" + letraColor03 +
                ", comentario='" + comentario + '\'' +
                ", id='" + id + '\'' +
                ", ancho=" + ancho +
                '}';
    }
}