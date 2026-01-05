package com.incloud.hcp.jco.balanza.Transporte.dto;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransporteUpdateServicioRFCParameterBuilder {
    private static final Logger logger = LoggerFactory.getLogger(TransporteUpdateServicioRFCParameterBuilder.class);

    public static void buildActualiza(JCoFunction jCoFunction, TransporteResponseDTO transporte) {
        logger.error("TransporteServicioRFCParameterBuilder-ACTUALIZA START");
        JCoTable jCoTableInputPoItem = jCoFunction.getTableParameterList().getTable("TI_TRANSPORTE");

        logger.error("transpote jCoTableInputPoItem-ACTUALIZA: " + jCoTableInputPoItem);
        jCoTableInputPoItem.appendRow();
        jCoTableInputPoItem.setValue("PLACA", transporte.getPlaca());
        jCoTableInputPoItem.setValue("MARCA", transporte.getMarca());
        jCoTableInputPoItem.setValue("MODELO", transporte.getModelo());
        jCoTableInputPoItem.setValue("CIV", transporte.getCiv());
        jCoTableInputPoItem.setValue("STATUS", transporte.getEstado());

        logger.error("TransporteServicioRFCParameterBuilder Mapeando tabla input ZTPE_MM_TRANSPORTE-ACTUALIZA: " + jCoTableInputPoItem);

    }

    public static void buildCrea(JCoFunction jCoFunction, TransporteResponseDTO transporte) {
        logger.error("TransporteServicioRFCParameterBuilder-CREA START");
        JCoTable jCoTableInputHeader = jCoFunction.getImportParameterList().getTable("I_TRUCK");

        logger.error("transpote jCoTableInputPoItem-CREA: " + jCoTableInputHeader);
        jCoTableInputHeader.appendRow();
        jCoTableInputHeader.setRow(1);
        jCoTableInputHeader.setValue("TR_PLACA", transporte.getPlaca());
        jCoTableInputHeader.setValue("REMOLQUE", transporte.getRemolque());
        jCoTableInputHeader.setValue("TR_TYPE", transporte.getTipoVehiculo());
        jCoTableInputHeader.setValue("TR_MARCA", transporte.getMarca());
        jCoTableInputHeader.setValue("TR_MODEL", transporte.getModelo());
        jCoTableInputHeader.setValue("TR_CIV", transporte.getCiv());
        jCoTableInputHeader.setValue("TR_STATUS", transporte.getEstado());
        jCoTableInputHeader.setValue("NROAUTO", transporte.getNroAutorizacion());
        jCoTableInputHeader.setValue("CODAUTO", transporte.getCodAutorizacion());
        jCoTableInputHeader.setValue("ZMTC", transporte.getZmtc());
        jCoTableInputHeader.setValue("CREATED_BY", transporte.getCreatedBy());
        jCoTableInputHeader.setValue("MODIFIED_BY", transporte.getModifiedBy());

        logger.error("TransporteServicioRFCParameterBuilder Mapeando tabla input ZTPE_MM_TRANSPORTE-CREA: " + jCoTableInputHeader);

    }
}
