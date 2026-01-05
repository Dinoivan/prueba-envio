package com.incloud.hcp.jco.balanza.DireccionAlternaProveedor.dto;

import com.incloud.hcp.jco.balanza.Chofer.dto.ChoferGrabarServicioRFCParameterBuilder;
import com.incloud.hcp.jco.balanza.Chofer.dto.ChoferResponseDTO;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DireccionAlternaCrearServicioRFCParameterBuilder {
    private static final Logger logger = LoggerFactory.getLogger(DireccionAlternaCrearServicioRFCParameterBuilder.class);
    public static void build_LIFNR(JCoFunction jCoFunction, String lifnr){
        logger.error("DireccionAlternaCrearServicioRFCParameterBuilder LFA1-LIFNR - START");
        JCoParameterList parameterList = jCoFunction.getImportParameterList();
        logger.error("direccion alterna jCoTableInputHeader LFA1-LIFNR: " + parameterList);

        parameterList.setValue("PI_LIFNR", lifnr);
    }
    public static void build_ZSTPE_MM_ADRC(JCoFunction jCoFunction, DireccionAlternaProveedorResponse direccionAlternaProveedorResponse){
        logger.error("DireccionAlternaCrearServicioRFCParameterBuilder ZSTPE_MM_ADRC - START");
        JCoStructure jCoTableInputHeader = jCoFunction.getImportParameterList().getStructure("PI_DIRALT");
        logger.error("direccion alterna jCoTableInputHeader ZSTPE_MM_ADRC: " + jCoTableInputHeader);

        jCoTableInputHeader.setValue("STREET", direccionAlternaProveedorResponse.getStreet());
        jCoTableInputHeader.setValue("HOUSE_NUM1", direccionAlternaProveedorResponse.getHouseNum1());
        jCoTableInputHeader.setValue("HOUSE_NUM2", direccionAlternaProveedorResponse.getHouseNum2());
        jCoTableInputHeader.setValue("STR_SUPPL2", direccionAlternaProveedorResponse.getStrSuppl2());
        jCoTableInputHeader.setValue("LAND1", direccionAlternaProveedorResponse.getLand1());
        jCoTableInputHeader.setValue("REGION", direccionAlternaProveedorResponse.getRegion());
        jCoTableInputHeader.setValue("BEZEI", direccionAlternaProveedorResponse.getBezei());
        jCoTableInputHeader.setValue("CITY1", direccionAlternaProveedorResponse.getCity1());
        jCoTableInputHeader.setValue("CITY2", direccionAlternaProveedorResponse.getCity2());

        logger.error("DireccionAlternativaCrearRFCParameterBuilder Mapeando tabla input ZSTPE_MM_ADRC " + jCoTableInputHeader);
    }
}
