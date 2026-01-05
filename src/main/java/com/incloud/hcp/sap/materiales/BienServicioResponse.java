package com.incloud.hcp.sap.materiales;

import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

/**
 * Created by USER on 17/09/2017.
 */
public class BienServicioResponse implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer countItemRead;
    private Integer countItemInsert;
    private Integer countItemUpdate;
    private Integer countSaveFail;
    private List<String> listSaveFail;
    private SapLog sapLog;

    public BienServicioResponse() {

    }

    public Integer getCountItemRead() {
        return countItemRead;
    }

    public void setCountItemRead(Integer countItemRead) {
        this.countItemRead = countItemRead;
    }

    public Integer getCountItemInsert() {
        return countItemInsert;
    }

    public void setCountItemInsert(Integer countItemInsert) {
        this.countItemInsert = countItemInsert;
    }

    public Integer getCountItemUpdate() {
        return countItemUpdate;
    }

    public void setCountItemUpdate(Integer countItemUpdate) {
        this.countItemUpdate = countItemUpdate;
    }

    public Integer getCountSaveFail() {
        return countSaveFail;
    }

    public void setCountSaveFail(Integer countSaveFail) {
        this.countSaveFail = countSaveFail;
    }

    public List<String> getListSaveFail() {
        return listSaveFail;
    }

    public void setListSaveFail(List<String> listSaveFail) {
        this.listSaveFail = listSaveFail;
    }

    public SapLog getSapLog() {
        return sapLog;
    }

    public void setSapLog(SapLog sapLog) {
        this.sapLog = sapLog;
    }
}
