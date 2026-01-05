package com.incloud.hcp.jco.proveedor.dto;

import com.incloud.hcp.sap.SapLog;

import java.io.Serializable;
import java.util.List;

public class ProveedorResponseRFC implements Serializable {

        private static final long serialVersionUID = 1L;

        private SapLog sapLog;
        private List<ProveedorRFCResponseDto> listaProveedorSAPResult;
        private boolean tieneError;

        public ProveedorResponseRFC() {
        }

        public SapLog getSapLog() {
            return sapLog;
        }

        public void setSapLog(SapLog sapLog) {
            this.sapLog = sapLog;
        }

        public List<ProveedorRFCResponseDto> getListaProveedorSAPResult() {
            return listaProveedorSAPResult;
        }

        public void setListaProveedorSAPResult(List<ProveedorRFCResponseDto> listaProveedorSAPResult) {
            this.listaProveedorSAPResult = listaProveedorSAPResult;
        }

        public boolean isTieneError() {
            return tieneError;
        }

        public void setTieneError(boolean tieneError) {
            this.tieneError = tieneError;
        }
    }



