package com.incloud.hcp.jco.balanza.DireccionAlternaCliente.dto;

import com.incloud.hcp.jco.balanza.Series.dto.SerieConsultaResponse;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
public class DireccionAlternaClienteMapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TABLE = "TO_DIRALT";
    private JCoParameterList jCoParameterList;

    public DireccionAlternaClienteMapper(JCoParameterList jCoParameterList){
        this.jCoParameterList = jCoParameterList;
    }

    public static DireccionAlternaClienteMapper newMapper(JCoParameterList exportParameterList){
        return new DireccionAlternaClienteMapper(exportParameterList);
    }

    public List<DireccionAlternaClienteResponse> getDireccionAlternaClienteList(){
        logger.error("DIRECCIONALTERNACLIENTEMAPPER - START");
        List<DireccionAlternaClienteResponse> direccionAlternaClienteResponseList = new ArrayList<>();
        JCoTable jCoTable = jCoParameterList.getTable(TABLE);
        logger.error("DIRECCIONALTERNACLIENTEMAPPER - JCoTable: " + jCoTable);
        if(jCoTable != null && !jCoTable.isEmpty()){
            do {
                DireccionAlternaClienteResponse direccionAlternaClienteResponse = new DireccionAlternaClienteResponse();
                direccionAlternaClienteResponse.setLfdnr(jCoTable.getString("LFDNR").trim());
                direccionAlternaClienteResponse.setStreet(jCoTable.getString("STREET").trim());
                direccionAlternaClienteResponse.setHouseNum1(jCoTable.getString("HOUSE_NUM1").trim());
                direccionAlternaClienteResponse.setHouseNum2(jCoTable.getString("HOUSE_NUM2").trim());
                direccionAlternaClienteResponse.setStrSuppl2(jCoTable.getString("STR_SUPPL2").trim());
                direccionAlternaClienteResponse.setLand1(jCoTable.getString("LAND1").trim());
                direccionAlternaClienteResponse.setRegion(jCoTable.getString("REGION").trim());
                direccionAlternaClienteResponse.setBezei(jCoTable.getString("BEZEI").trim());
                direccionAlternaClienteResponse.setCity1(jCoTable.getString("CITY1").trim());
                direccionAlternaClienteResponse.setCity2(jCoTable.getString("CITY2").trim());
                logger.error("DIRECCIONALTERNACLIENTEMAPPER - direccionAlternaClienteResponse: " + direccionAlternaClienteResponse);
                direccionAlternaClienteResponseList.add(direccionAlternaClienteResponse);
            } while (jCoTable.nextRow());
        }
        logger.error("DIRECCIONALTERNACLIENTEMAPPER - serieConsultaResponsesList: " + direccionAlternaClienteResponseList);
        logger.error("DIRECCIONALTERNACLIENTEMAPPER - END");
        return direccionAlternaClienteResponseList;
    }
}
