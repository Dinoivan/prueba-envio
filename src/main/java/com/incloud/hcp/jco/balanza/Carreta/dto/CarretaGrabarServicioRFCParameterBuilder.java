package com.incloud.hcp.jco.balanza.Carreta.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarretaGrabarServicioRFCParameterBuilder {
    private static final Logger logger = LoggerFactory.getLogger(CarretaGrabarServicioRFCParameterBuilder.class);
    public static void build(JCoFunction jCoFunction, CarretaResponseDTO carreta){
        logger.error("CarretaGrabarServicioRFCParameterBuilder START");
        JCoStructure jCoTableInputHeader = jCoFunction.getImportParameterList().getStructure("PI_CABECERA");
        logger.error("carreta jCoTableInputHeader: " + jCoTableInputHeader);

        jCoTableInputHeader.setValue("PLACA", carreta.getPlaca());
        jCoTableInputHeader.setValue("MODELO", carreta.getModelo());

        logger.error("CarretaServicioRFCParameterBuilder Mapeando tabla input ZPE_MM_CARRETA " + jCoTableInputHeader);
    }
}
