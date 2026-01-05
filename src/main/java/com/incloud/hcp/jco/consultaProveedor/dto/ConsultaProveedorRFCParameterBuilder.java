package com.incloud.hcp.jco.consultaProveedor.dto;

import com.incloud.hcp.jco.centro.dto.CentroRFCParameterBuilder;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsultaProveedorRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(CentroRFCParameterBuilder.class);


    private ConsultaProveedorRFCParameterBuilder() {
    }

    public static void build(JCoFunction jCoFunction, String nroAcreedor, String fechaInicio, String fechaFin) {
        logger.info("Mapeando tabla");
        JCoParameterList input = jCoFunction.getImportParameterList();
        logger.error("INPUT build ConsultaProveedor - nroAcreedor: " + nroAcreedor);
        logger.error("INPUT build ConsultaProveedor - fechaInicio / fechaFin : " + fechaInicio + " / " + fechaFin);

//        input.setValue("PI_LIFNR", nroAcreedor);
//        logger.error("INPUT build ConsultaProveedor input " + input.toString());
        if (nroAcreedor != null && !nroAcreedor.isEmpty()){
            if (nroAcreedor.trim().length() < 10)
                nroAcreedor = StringUtils.leftPad(nroAcreedor.trim(), 10, "0");

            input.setValue("PI_LIFNR", nroAcreedor);
        }

//        JCoStructure jcoEstructura = jCoFunction.getImportParameterList().getStructure("TI_ERDAT");
//        jcoEstructura.setValue("LOW", fechaInicio);
//        jcoEstructura.setValue("HIGH", fechaFin);
        if (fechaInicio != null && fechaFin != null && !fechaInicio.isEmpty() && !fechaFin.isEmpty()){
            JCoTable jcoTableTIERDAT = input.getTable("TI_ERDAT");
            jcoTableTIERDAT.appendRow();
            jcoTableTIERDAT.setRow(0);
            jcoTableTIERDAT.setValue("SIGN", "I");
            jcoTableTIERDAT.setValue("LOW", fechaInicio);

            if (fechaInicio.equals(fechaFin)) {
                jcoTableTIERDAT.setValue("OPTION", "EQ");
            } else {
                jcoTableTIERDAT.setValue("OPTION", "BT");
                jcoTableTIERDAT.setValue("HIGH", fechaFin);
            }
        }
        logger.error("INPUT build ConsultaProveedor input " + input.toString());
    }
}
