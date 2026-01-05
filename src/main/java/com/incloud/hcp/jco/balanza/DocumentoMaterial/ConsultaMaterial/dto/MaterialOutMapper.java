package com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.dto;

import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MaterialOutMapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TABLE = "PO_MATERIAL";
    private JCoParameterList jCoParameterList;
    public MaterialOutMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }
    public static MaterialOutMapper newMapper(JCoParameterList exportParameterList){
        return new MaterialOutMapper(exportParameterList);
    }
    public MaterialOutDto getMaterialOutResponse(){
        logger.error("MATERIALOUTMAPPER - START");
        JCoTable jCoTable = jCoParameterList.getTable(TABLE);
        logger.error("MATERIALOUTMAPPER - JCoTable: " + jCoTable);
        MaterialOutDto r = new MaterialOutDto();
        if(jCoTable != null && !jCoTable.isEmpty()){
            MaterialOutDto materialOutDto = new MaterialOutDto();
            materialOutDto.setMaktx(jCoTable.getString("MAKTX").trim());
            materialOutDto.setMatnr(jCoTable.getString("MATNR").trim());
            materialOutDto.setMeins(jCoTable.getString("MEINS").trim());
            logger.error("MATERIALOUTMAPPER - materialOutResponse: " + materialOutDto);
            logger.error("MATERIALOUTMAPPER - END");
            return materialOutDto;
        }
        logger.error("NO ENCONTRO NINGUN MATERIAL");
        return r;
    }
}
