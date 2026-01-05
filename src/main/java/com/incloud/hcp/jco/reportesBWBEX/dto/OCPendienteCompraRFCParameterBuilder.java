package com.incloud.hcp.jco.reportesBWBEX.dto;

import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraRFCParameterBuilder;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OCPendienteCompraRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(OrdenCompraRFCParameterBuilder.class);


    private OCPendienteCompraRFCParameterBuilder() {
    }

    public static void build(JCoFunction jCoFunction,
                             CalidadParameterEntradaDto benaEntrada) {

        JCoParameterList input01 = jCoFunction.getImportParameterList();
        input01.setValue("I_INFOPROVIDER", "ZPUR_C001");
        JCoParameterList input02 = jCoFunction.getImportParameterList();
        input02.setValue("I_QUERY", "ZPUR_C001_Q001");
        JCoParameterList input03 = jCoFunction.getImportParameterList();
        input03.setValue("I_VIEW_ID", "");

        JCoTable jCoTableInputPoItem = jCoFunction.getTableParameterList().getTable("I_T_PARAMETER");
        logger.error("CalidadRFCParameterBuilder INICIO PRUEBA");
        jCoTableInputPoItem.appendRow();
        jCoTableInputPoItem.setRow(0);
        jCoTableInputPoItem.setValue("NAME", "ZVAR_CALYEAR");
        jCoTableInputPoItem.setValue("VALUE", benaEntrada.getAnho());
        logger.error("OrdenCompraRFCParameterBuilder Mapeando tabla input T_POITEM " + jCoTableInputPoItem.toString());

    }
}
