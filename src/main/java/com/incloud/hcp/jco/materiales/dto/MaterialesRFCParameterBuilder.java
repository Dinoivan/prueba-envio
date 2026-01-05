package com.incloud.hcp.jco.materiales.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaterialesRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(MaterialesRFCParameterBuilder.class);


    private MaterialesRFCParameterBuilder() {
    }

    public static void build(JCoFunction jCoFunction, String fechaInicio, String fechaFin) {
        logger.info("Mapeando tabla");

        //entrada tipo tabla
        JCoStructure jcoEstructura = jCoFunction.getImportParameterList().getStructure("TI_ERSDA");
        jcoEstructura.setValue("LOW", fechaInicio);
        jcoEstructura.setValue("HIGH", fechaFin);
        logger.error("INPUT build GrupoArticuloRFCParameterBuilder input " + jcoEstructura.toString());

    }


}
