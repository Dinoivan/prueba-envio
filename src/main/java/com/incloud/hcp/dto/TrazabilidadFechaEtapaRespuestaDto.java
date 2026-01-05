package com.incloud.hcp.dto;

import java.util.Date;

public class TrazabilidadFechaEtapaRespuestaDto {

    private Date start;
    private Date end;
    private String type;
    private String title;
    private String info;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "TrazabilidadFechaEtapaRespuestaDto{" +
                "start=" + start +
                ", end=" + end +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
