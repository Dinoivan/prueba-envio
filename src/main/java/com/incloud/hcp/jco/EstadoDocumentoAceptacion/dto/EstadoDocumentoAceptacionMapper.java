package com.incloud.hcp.jco.EstadoDocumentoAceptacion.dto;

import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EstadoDocumentoAceptacionMapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TABLE = "T_SPEDIDO";
    private JCoParameterList jCoParameterList;
    public EstadoDocumentoAceptacionMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }
    public static EstadoDocumentoAceptacionMapper newMapper(JCoParameterList exportParameterList){
        return new EstadoDocumentoAceptacionMapper(exportParameterList);
    }
    public List<EstadoDocumentoAceptacionResponse> getEstadoDireccionAlternaResponse(){
        logger.error("ESTADO DOCUMENTO ACEPTACIÓN MAPPER - START");
        List<EstadoDocumentoAceptacionResponse> estadoDocumentoAceptacionResponseList = new ArrayList<>();
        JCoTable jCoTable = jCoParameterList.getTable(TABLE);
        logger.error("ESTADO DOCUMENTO ACEPTACIÓN MAPPER - JCoTable" + jCoTable);
        if(jCoTable != null && !jCoTable.isEmpty()){
            do{
                EstadoDocumentoAceptacionResponse estadoDocumentoAceptacionResponse = new EstadoDocumentoAceptacionResponse();
                estadoDocumentoAceptacionResponse.setEbeln(jCoTable.getString("EBELN").trim());
                estadoDocumentoAceptacionResponse.setEbelp(jCoTable.getInt("EBELP"));
                estadoDocumentoAceptacionResponse.setLfbja(jCoTable.getString("LFBJA").trim());
                estadoDocumentoAceptacionResponse.setBelnr(jCoTable.getString("BELNR").trim());
                estadoDocumentoAceptacionResponse.setMjahr(jCoTable.getString("MJAHR").trim());
                estadoDocumentoAceptacionResponse.setEstatu(jCoTable.getString("ESTATU").trim());
                estadoDocumentoAceptacionResponse.setIndBorrado(jCoTable.getString("LOEKZ").trim());
                estadoDocumentoAceptacionResponse.setFacturaFinal(jCoTable.getString("EREKZ").trim());
                logger.error("ESTADO DOCUMENTO ACEPTACION MAPPER - RESPONSE : "+ estadoDocumentoAceptacionResponse);
                estadoDocumentoAceptacionResponseList.add((estadoDocumentoAceptacionResponse));
            } while(jCoTable.nextRow());
        }
        logger.error("ESTADO DOCUMENTO ACEPTACIÓN MAPPER - estadoDocumentoAceptacionResponseList: "+ estadoDocumentoAceptacionResponseList);
        logger.error("ESTADO DOCUMENTO ACEPTACION MAPPER - END");
        return estadoDocumentoAceptacionResponseList;

    }
}
