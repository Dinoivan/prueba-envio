package com.incloud.hcp.jco.unidadMedida.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnidadMedidaRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(UnidadMedidaRFCParameterBuilder.class);


    private UnidadMedidaRFCParameterBuilder() {
    }

    public static void build(JCoFunction jCoFunction) {
        logger.info("Mapeando tabla");
        JCoParameterList input = jCoFunction.getImportParameterList();
        logger.error("INPUT build UnidadMedidaRFC: ");




    }


}
