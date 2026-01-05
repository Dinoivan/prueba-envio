package com.incloud.hcp.jco.balanza.Series.dto;

import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
public class SerieConsultaMapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TABLE = "TO_SERIE";
    private JCoParameterList jCoParameterList;

    public SerieConsultaMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public static SerieConsultaMapper newMapper(JCoParameterList exportParameterList){
        return new SerieConsultaMapper(exportParameterList);
    }

    public List<SerieConsultaResponse> getSerieConsultaResponseList() {
        logger.error("SERIECONSULTAMAPPER - START");
        List<SerieConsultaResponse> serieConsultaResponsesList = new ArrayList<>();
        JCoTable jCoTable = jCoParameterList.getTable(TABLE);
        logger.error("SERIECONSULTAMAPPER - JCoTable: " + jCoTable);
        if(jCoTable != null && !jCoTable.isEmpty()){
            do {
                SerieConsultaResponse serieConsultaResponse = new SerieConsultaResponse();
                serieConsultaResponse.setBukrs(jCoTable.getString("BUKRS").trim());
                serieConsultaResponse.setWerks(jCoTable.getString("WERKS").trim());
                serieConsultaResponse.setzCorrelativo(jCoTable.getString("ZCORRELATIVO").trim());
                serieConsultaResponse.setzSerie(jCoTable.getString("ZSERIE").trim());
                serieConsultaResponse.setzDescr(jCoTable.getString("ZDESCR").trim());
                serieConsultaResponse.setUltimoZGuiar(jCoTable.getString("ULTIMO_ZGUIAR"));
                logger.error("SERIECONSULTAMPPER - serieConsultaResponse: " + serieConsultaResponse);
                serieConsultaResponsesList.add(serieConsultaResponse);
            } while (jCoTable.nextRow());
        }
        logger.error("SERIECONSULTAMPPER - serieConsultaResponsesList: " + serieConsultaResponsesList);
        logger.error("SERIECONSULTAMAPPER - END");
        return serieConsultaResponsesList;
    }


}
