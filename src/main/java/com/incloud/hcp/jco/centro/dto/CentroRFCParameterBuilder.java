package com.incloud.hcp.jco.centro.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CentroRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(CentroRFCParameterBuilder.class);


    private CentroRFCParameterBuilder() {
    }

    public static void build(JCoFunction jCoFunction, String sociedad) {
        logger.info("Mapeando tabla");
        JCoParameterList input = jCoFunction.getImportParameterList();
        logger.error("INPUT build CentroRFCParameterBuilder: " + sociedad);

        input.setValue("PI_BUKRS", sociedad);
        logger.error("INPUT build CentroRFCParameterBuilder input " + input.toString());

    }


}
