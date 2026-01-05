package com.incloud.hcp.jco.tipoCambio.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TipoCambioRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(TipoCambioRFCParameterBuilder.class);


    private TipoCambioRFCParameterBuilder() {
    }

    public static void build(JCoFunction jCoFunction,String fecha) {
        logger.info("Mapeando tabla");
        JCoParameterList input = jCoFunction.getImportParameterList();
        logger.error("INPUT build TipoCambioRFC: ");

        input.setValue("PI_DATE", fecha);
        logger.error("INPUT build TipoCambioRFC input " + input.toString());


    }


}
