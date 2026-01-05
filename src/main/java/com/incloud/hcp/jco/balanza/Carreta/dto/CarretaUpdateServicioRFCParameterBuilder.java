package com.incloud.hcp.jco.balanza.Carreta.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarretaUpdateServicioRFCParameterBuilder {
    private static final Logger logger = LoggerFactory.getLogger(CarretaUpdateServicioRFCParameterBuilder.class);
    public static void build(JCoFunction jCoFunction, CarretaResponseDTO carreta){
        logger.error("CarretaUpdateServicioRFCParameterBuilder START");
        JCoTable jCoTableInputPoItem = jCoFunction.getTableParameterList().getTable("TI_CARRETA");
        logger.error("CarretaUpdateServicioRFCParameterBuilder jCoTableInputPoItem: " + jCoTableInputPoItem);
        jCoTableInputPoItem.appendRow();
        jCoTableInputPoItem.setValue("PLACA", carreta.getPlaca());
        jCoTableInputPoItem.setValue("MODELO", carreta.getModelo());
        logger.error("CarretaServicioRFCParameterBuilder Mapeando tabla input ZPE_MM_CARRETA " + jCoTableInputPoItem);
    }
}
