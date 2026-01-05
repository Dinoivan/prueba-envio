package com.incloud.hcp.jco.grupoArticulo.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrupoArticuloRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(GrupoArticuloRFCParameterBuilder.class);


    private GrupoArticuloRFCParameterBuilder() {
    }

    public static void build(JCoFunction jCoFunction, String grupoArticulo) {
        logger.info("Mapeando tabla");
        JCoParameterList input = jCoFunction.getImportParameterList();
        logger.error("INPUT build GrupoArticuloRFCParameterBuilder: " + grupoArticulo);

        input.setValue("PI_MATKL", grupoArticulo);
        logger.error("INPUT build GrupoArticuloRFCParameterBuilder input " + input.toString());

    }


}
