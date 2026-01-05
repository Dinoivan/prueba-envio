package com.incloud.hcp.jco.servicios.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiciosRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ServiciosRFCParameterBuilder.class);


    private ServiciosRFCParameterBuilder() {
    }

    public static void build(JCoFunction jCoFunction,String fechaInicio,String fechaFin) {
        logger.info("Mapeando tabla");

        //entrada tipo tabla


        JCoStructure jcoEstructura = jCoFunction.getImportParameterList().getStructure("TI_ERDAT");
        jcoEstructura.setValue("LOW", fechaInicio);
        jcoEstructura.setValue("HIGH", fechaFin);


            //input.setValue("TI_ERSDA", grupoArticulo);
        logger.error("INPUT build Servicios input " + jcoEstructura.toString());





    }


}
