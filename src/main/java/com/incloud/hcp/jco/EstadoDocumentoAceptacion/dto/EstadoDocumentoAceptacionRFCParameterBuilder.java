package com.incloud.hcp.jco.EstadoDocumentoAceptacion.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EstadoDocumentoAceptacionRFCParameterBuilder {
    private static final Logger logger = LoggerFactory.getLogger(EstadoDocumentoAceptacionRFCParameterBuilder.class);
    public static void mapFilter(JCoFunction jCoFunction, List<RangeSap> rangeSap){
        JCoTable jCoTableInputItem = jCoFunction.getImportParameterList().getTable("EBELN");
        logger.error("estado doc aceptacion jCoTableInputHeader BUILD EBELN: " + jCoTableInputItem);
        for(int i= 0; i <rangeSap.size(); i++){
            RangeSap itemSap = rangeSap.get(i);
            logger.error("estadoDocumentoAceptacionRFCParameterBuilder ITEMSAP: " + itemSap);
            jCoTableInputItem.appendRow();
            jCoTableInputItem.setRow(i);
            jCoTableInputItem.setValue("SIGN", itemSap.getSign());
            jCoTableInputItem.setValue("OPTION", itemSap.getOption());
            jCoTableInputItem.setValue("LOW", itemSap.getLow());
            jCoTableInputItem.setValue("HIGH", itemSap.getHigh());

            logger.error("estadoDocumentoAceptacionRFCParameterBuilder tabla input: " + jCoTableInputItem);
        }

    }
}
