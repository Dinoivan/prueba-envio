package com.incloud.hcp.jco.peticionOferta.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeticionOfertaRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(PeticionOfertaRFCParameterBuilder.class);


    private PeticionOfertaRFCParameterBuilder() {
    }

    public static void build(JCoFunction jCoFunction, String numeroSolicitud, boolean esPeticionOferta) {
        logger.info("Mapeando tabla");
        JCoParameterList input = jCoFunction.getImportParameterList();
        logger.error("INPUT build PeticionOfertaRFCParameterBuilder: " + numeroSolicitud);

        if (numeroSolicitud != null && !numeroSolicitud.isEmpty()) {
            if (esPeticionOferta){ // se trata de un numero de Peticion de Oferta
                JCoTable jcoTableEBELN = input.getTable("PI_EBELN");
                jcoTableEBELN.appendRow();
                jcoTableEBELN.setRow(0);
                jcoTableEBELN.setValue("SIGN", "I");
                jcoTableEBELN.setValue("OPTION", "EQ");
                jcoTableEBELN.setValue("LOW", numeroSolicitud);
                jcoTableEBELN.setValue("HIGH", "");
            }
            else{ // se trata de un numero de Licitacion SAP
                JCoTable jcoTableSUBMI = input.getTable("PI_SUBMI");
                jcoTableSUBMI.appendRow();
                jcoTableSUBMI.setRow(0);
                jcoTableSUBMI.setValue("SIGN", "I");
                jcoTableSUBMI.setValue("OPTION", "EQ");
                jcoTableSUBMI.setValue("LOW", numeroSolicitud);
                jcoTableSUBMI.setValue("HIGH", "");
            }
        }
//        input.setValue("PI_EBELN", numeroSolicitud);
        logger.error("INPUT build PeticionOfertaRFCParameterBuilder input " + input.toString());
    }
}
