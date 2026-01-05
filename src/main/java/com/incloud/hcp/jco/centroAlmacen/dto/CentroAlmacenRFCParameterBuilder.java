package com.incloud.hcp.jco.centroAlmacen.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CentroAlmacenRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(CentroAlmacenRFCParameterBuilder.class);


    private CentroAlmacenRFCParameterBuilder() {
    }

    public static void build(JCoFunction jCoFunction, String centro) {
        logger.info("Mapeando tabla");
        JCoParameterList input = jCoFunction.getImportParameterList();
        logger.error("INPUT build GrupoArticuloRFCParameterBuilder: " + centro);

        input.setValue("PI_WERKS", centro);
        logger.error("INPUT build GrupoArticuloRFCParameterBuilder input " + input.toString());

    }


}
