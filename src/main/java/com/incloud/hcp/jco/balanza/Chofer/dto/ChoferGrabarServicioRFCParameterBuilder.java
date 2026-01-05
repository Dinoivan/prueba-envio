package com.incloud.hcp.jco.balanza.Chofer.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChoferGrabarServicioRFCParameterBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ChoferGrabarServicioRFCParameterBuilder.class);
    public static void build(JCoFunction jCoFunction, ChoferResponseDTO chofer){
        logger.error("ChoferUpdateServicioRFCParameterBuilder - START");
        JCoTable jCoTableInputHeader = jCoFunction.getImportParameterList().getTable("I_DRIVERS");
        logger.error("chofer jCoTableInputHeader: " + jCoTableInputHeader);
        jCoTableInputHeader.appendRow();
        jCoTableInputHeader.setRow(1);
        jCoTableInputHeader.setValue("DR_DNI", chofer.getDni());
        jCoTableInputHeader.setValue("DR_LICENSE", chofer.getLicencia());
        jCoTableInputHeader.setValue("TIPODOCID", chofer.getTipoDocumento());
        jCoTableInputHeader.setValue("APEPATERNO", chofer.getApellidoPaterno());
        jCoTableInputHeader.setValue("APEMATERNO", chofer.getApellidoMaterno());
        jCoTableInputHeader.setValue("DR_NAME", chofer.getNombre());
        jCoTableInputHeader.setValue("TIPODOCID", chofer.getTipoDocumento());
        jCoTableInputHeader.setValue("DR_STATUS", chofer.getEstado());

        logger.error("ChoferServicioRFCParameterBuilder Mapeando tabla input ZPE_MM_CHOFER " + jCoTableInputHeader);
    }
}

/*
public class ChoferGrabarServicioRFCParameterBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ChoferGrabarServicioRFCParameterBuilder.class);
    public static void build(JCoFunction jCoFunction, ChoferResponseDTO chofer){
        logger.error("ChoferUpdateServicioRFCParameterBuilder - START");
        JCoStructure jCoTableInputHeader = jCoFunction.getImportParameterList().getStructure("PI_CABECERA");
        logger.error("chofer jCoTableInputHeader: " + jCoTableInputHeader);

        jCoTableInputHeader.setValue("LICENCIA", chofer.getLicencia());
        jCoTableInputHeader.setValue("NOMBRE", chofer.getNombre());
        jCoTableInputHeader.setValue("DNI", chofer.getDni());

        logger.error("ChoferServicioRFCParameterBuilder Mapeando tabla input ZPE_MM_CHOFER " + jCoTableInputHeader);
    }
}

 */
