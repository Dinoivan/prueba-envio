package com.incloud.hcp.jco.balanza.MaterialLote.dto;

import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MaterialLoteParameterBuilder {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TABLE = "TO_MATERIAL_LOTE";
    private JCoParameterList jCoParameterList;

    public MaterialLoteParameterBuilder(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public static MaterialLoteParameterBuilder newMapper(JCoParameterList exportParameterList){
        return new MaterialLoteParameterBuilder(exportParameterList);
    }

    public List<MaterialLoteResponse> getMaterialLoteList(){
        logger.error("MATERIALLOTEMAPPER - START");
        List<MaterialLoteResponse> materialLoteResponseList = new ArrayList<>();
        JCoTable jCoTable = jCoParameterList.getTable(TABLE);
        logger.error("MATERIALLOTEMAPPER - JCoTable: " + jCoTable);

        if(jCoTable != null && !jCoTable.isEmpty()){
            do {
                MaterialLoteResponse materialLoteResponse = new MaterialLoteResponse();
                materialLoteResponse.setMaterial(jCoTable.getString("MATERIAL").trim());
                materialLoteResponse.setLote(jCoTable.getString("LOTE").trim());
                materialLoteResponse.setDescripcion(jCoTable.getString("DESCRIPCION").trim());

                logger.error("MATERIALLOTEMAPPER - pedidosVentasResponse: " + materialLoteResponse);
                materialLoteResponseList.add(materialLoteResponse);
            } while (jCoTable.nextRow());
        }
        logger.error("MATERIALLOTEMAPPER - materialLoteResponseList: " + materialLoteResponseList);
        logger.error("MATERIALLOTEMAPPER - END");
        return materialLoteResponseList;
    }
}
