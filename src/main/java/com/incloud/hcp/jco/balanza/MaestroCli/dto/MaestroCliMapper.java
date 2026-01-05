package com.incloud.hcp.jco.balanza.MaestroCli.dto;

import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MaestroCliMapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TABLE = "PO_MAESTRO_CLI";
    private JCoParameterList jCoParameterList;
    
    public MaestroCliMapper(JCoParameterList jCoParameterList){
        this.jCoParameterList = jCoParameterList;
    }
    public static MaestroCliMapper newMapper(JCoParameterList exportParameterList){
        return new MaestroCliMapper(exportParameterList);
    }
    public List<MaestroCliResponse>  getMaestroCliList(){
        logger.error("MAESTROCLIMAPPER - START");
        List<MaestroCliResponse> maestroCliResponseList = new ArrayList<>();
        JCoTable jCoTable = jCoParameterList.getTable(TABLE);
        logger.error("MAESTROCLIMAPPER - JCoTable: " + jCoTable);
        if(jCoTable != null && !jCoTable.isEmpty()){
            do {
                MaestroCliResponse maestroCliResponse = new MaestroCliResponse();
                maestroCliResponse.setKunnr(jCoTable.getString("KUNNR").trim());
                maestroCliResponse.setStreet(jCoTable.getString("STREET").trim());
                maestroCliResponse.setSmtpAddr(jCoTable.getString("SMTP_ADDR").trim());
                maestroCliResponse.setVkorg(jCoTable.getString("VKORG").trim());
                maestroCliResponse.setVtweg(jCoTable.getString("VTWEG").trim());
                maestroCliResponse.setSpart(jCoTable.getString("SPART").trim());
                maestroCliResponse.setName1(jCoTable.getString("NAME1").trim());
                maestroCliResponse.setStcd1(jCoTable.getString("STCD1").trim());
                maestroCliResponse.setRegion(jCoTable.getString("REGION").trim());
                maestroCliResponse.setBezei(jCoTable.getString("BEZEI").trim());
                maestroCliResponse.setCity1(jCoTable.getString("CITY1").trim());
                maestroCliResponse.setCity2(jCoTable.getString("CITY2").trim());
                logger.error("MAESTROCLIMAPPER - maestroCliResponse: " + maestroCliResponse);
                maestroCliResponseList.add(maestroCliResponse);
            } while (jCoTable.nextRow());
        }
        logger.error("MAESTROCLIMAPPER - maestroCliResponseList: " + maestroCliResponseList);
        logger.error("MAESTROCLIMAPPER - END");
        return maestroCliResponseList;
    }
}
