package com.incloud.hcp.jco.balanza.DireccionAlternaProveedor.dto;

import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DireccionAlternaProveedorMapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TABLE = "TO_DIRALT";
    private JCoParameterList jCoParameterList;
    public DireccionAlternaProveedorMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }
    public static DireccionAlternaProveedorMapper newMapper(JCoParameterList exportParameterList){
        return new DireccionAlternaProveedorMapper(exportParameterList);
    }
    public List<DireccionAlternaProveedorResponse> getDireccionAlternaResponseList(){
        logger.error("DIRECCIONALTERNAMAPPER - START");
        List<DireccionAlternaProveedorResponse> direccionAlternaResponseList = new ArrayList<>();
        JCoTable jCoTable = jCoParameterList.getTable(TABLE);
        logger.error("DIRECCIONALTERNAMAPPER - JCoTable: " + jCoTable);
        if(jCoTable != null && !jCoTable.isEmpty()){
            do {
                DireccionAlternaProveedorResponse direccionAlternaProveedorResponse = new DireccionAlternaProveedorResponse();
                direccionAlternaProveedorResponse.setLfdnr(jCoTable.getString("LFDNR").trim());
                direccionAlternaProveedorResponse.setStreet(jCoTable.getString("STREET").trim());
                direccionAlternaProveedorResponse.setHouseNum1(jCoTable.getString("HOUSE_NUM1").trim());
                direccionAlternaProveedorResponse.setHouseNum2(jCoTable.getString("HOUSE_NUM2").trim());
                direccionAlternaProveedorResponse.setLand1(jCoTable.getString("LAND1").trim());
                direccionAlternaProveedorResponse.setRegion(jCoTable.getString("REGION").trim());
                direccionAlternaProveedorResponse.setBezei(jCoTable.getString("BEZEI").trim());
                direccionAlternaProveedorResponse.setCity1(jCoTable.getString("CITY1").trim());
                direccionAlternaProveedorResponse.setCity2(jCoTable.getString("CITY2").trim());
                logger.error("DIRECCIONALTERNAMAPPER - direccionAlternaResponse: " + direccionAlternaProveedorResponse);
                direccionAlternaResponseList.add(direccionAlternaProveedorResponse);
            } while (jCoTable.nextRow());
        }
        logger.error("DIRECCIONALTERNAMAPPER - direccionAlternaResponseList: " + direccionAlternaResponseList);
        logger.error("DIRECCIONALTERNAMAPPER - END");
        return direccionAlternaResponseList;
    }

}
