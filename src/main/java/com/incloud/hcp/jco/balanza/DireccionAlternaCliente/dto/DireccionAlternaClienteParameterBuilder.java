package com.incloud.hcp.jco.balanza.DireccionAlternaCliente.dto;

import com.incloud.hcp.jco.balanza.Chofer.dto.ChoferGrabarServicioRFCParameterBuilder;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DireccionAlternaClienteParameterBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ChoferGrabarServicioRFCParameterBuilder.class);
    public static void build(JCoFunction jCoFunction, String Kna1Kunnr, DireccionAlternaClienteArdc response){
        logger.error("build START");
        JCoParameterList parameterList = jCoFunction.getImportParameterList();
        logger.error("build jCoTableInputHeader A: "+ parameterList);
        parameterList.setValue("PI_KUNNR", Kna1Kunnr);
        logger.error("build jCoTableInputHeader B: "+ parameterList);
        JCoStructure jCoTableInputHeader = jCoFunction.getImportParameterList().getStructure("PI_DIRALT");
        logger.error("build jCoTableInputPoItem A: " + jCoTableInputHeader);
        jCoTableInputHeader.setValue("STREET", response.getStreet());
        jCoTableInputHeader.setValue("HOUSE_NUM1", response.getHouseNum1());
        jCoTableInputHeader.setValue("HOUSE_NUM2", response.getHouseNum2());
        jCoTableInputHeader.setValue("STR_SUPPL2", response.getStrSuppl2());
        jCoTableInputHeader.setValue("LAND1", response.getLand1());
        jCoTableInputHeader.setValue("REGION", response.getRegion());
        jCoTableInputHeader.setValue("BEZEI", response.getBezei());
        jCoTableInputHeader.setValue("CITY1", response.getCity1());
        jCoTableInputHeader.setValue("CITY2", response.getCity2());
        logger.error("build jCoTableInputPoItem B: " + jCoTableInputHeader);
        logger.error("build END");
    }

}
