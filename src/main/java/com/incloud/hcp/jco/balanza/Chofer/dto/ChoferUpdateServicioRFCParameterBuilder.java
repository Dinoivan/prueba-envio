package com.incloud.hcp.jco.balanza.Chofer.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChoferUpdateServicioRFCParameterBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ChoferUpdateServicioRFCParameterBuilder.class);
    public static void build(JCoFunction jCoFunction, ChoferResponseDTO chofer){
        logger.error("ChoferUpdateServicioRFCParameterBuilder - START");
        JCoTable jCoTableInputPoItem = jCoFunction.getTableParameterList().getTable("TI_CHOFERES");
        logger.error("chofer jCoTableInputHeader: " + jCoTableInputPoItem);

        jCoTableInputPoItem.appendRow();
        jCoTableInputPoItem.setValue("LICENCIA", chofer.getLicencia());
        //jCoTableInputPoItem.setValue("NOMBRE", chofer.getNombre());
        jCoTableInputPoItem.setValue("DR_NAME", chofer.getNombre());
        jCoTableInputPoItem.setValue("TIPODOCID", chofer.getTipoDocumento());
        jCoTableInputPoItem.setValue("APEPATERNO", chofer.getApellidoPaterno());
        jCoTableInputPoItem.setValue("APEMATERNO", chofer.getApellidoMaterno());
        jCoTableInputPoItem.setValue("DNI", chofer.getDni());
        jCoTableInputPoItem.setValue("STATUS", chofer.getEstado());

        logger.error("ChoferServicioRFCParameterBuilder Mapeando tabla input ZPE_MM_CHOFER " + jCoTableInputPoItem);
    }
}
