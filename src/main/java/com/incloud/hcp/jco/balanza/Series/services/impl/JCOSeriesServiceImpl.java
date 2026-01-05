package com.incloud.hcp.jco.balanza.Series.services.impl;

import com.incloud.hcp.jco.balanza.Series.dto.SerieConsultaMapper;
import com.incloud.hcp.jco.balanza.Series.dto.SerieConsultaResponse;
import com.incloud.hcp.jco.balanza.Series.services.JCOSeriesService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOSeriesServiceImpl implements JCOSeriesService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;
    private final String FUNCION_CONSULTA_RFC = "ZFPE_MM_CONSULTA_SERIES";

    @Override
    public List<SerieConsultaResponse> consultaSerie(String serieWerks) throws Exception {
        logger.error("CONSULTA SERIE - START");
        logger.error("PARAMETRO SERIEWERKS: " + serieWerks);

        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("CONSULTASERIE - JCoDestination: " + destination);

        JCoRepository repository = destination.getRepository();
        logger.error("CONSULTASERIE - JCoRepository: " + repository);

        JCoFunction jCoFunction = repository.getFunction(FUNCION_CONSULTA_RFC);
        logger.error("CONSULTASERIE - JCoFunction: " + jCoFunction);

        logger.error("CONSULTASERIE - mapFilter START");
        this.mapFilter(jCoFunction, serieWerks);
        logger.error("CONSULTASERIE - mapFilter END");

        logger.error("CONSULTASERIE - jCoFunction.execute START");
        jCoFunction.execute(destination);
        logger.error("CONSULTASERIE - jCoFunction.execute END");

        logger.error("CONSULTASERIE - JCoParameterList START");
        JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
        logger.error("CONSULTASERIE - JCoParameterList END");

        logger.error("CONSULTASERIE - serieConsultaMapper START");
//        SerieConsultaMapper serieConsultaMapper = SerieConsultaMapper.newMapper(exportParameterList);
        logger.error("SERIECONSULTAMAPPER - START");
        List<SerieConsultaResponse> serieConsultaResponsesList = new ArrayList<>();
        JCoTable jCoTable = jCoFunction.getTableParameterList().getTable("TO_SERIE");
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
        logger.error("CONSULTASERIE - serieConsultaMapper END");

//        logger.error("CONSULTASERIE - Llenado de SerieConstulaResponse START");
//        List<SerieConsultaResponse> serieConsultaResponseList = serieConsultaMapper.getSerieConsultaResponseList();
//        logger.error("CONSULTASERIE - serieConsultaResponseList: " + serieConsultaResponseList);
//        logger.error("CONSULTASERIE - Llenado de SerieConstulaResponse END");

        return serieConsultaResponsesList;
    }

    public void mapFilter(JCoFunction jCoFunction, String serieWerks){
        logger.error("CONSULTASERIE - mapFilter START");
        logger.error("SEGUNDA VALIDACION DE PARAMETRO SERIEWERKS: " + serieWerks);

        JCoParameterList parameterList = jCoFunction.getImportParameterList();
        logger.error("CONSULTASERIE - JCoParameterList: " + parameterList);
        parameterList.setValue("PI_WERKS", serieWerks);

        logger.error("CONSULTASERIE - mapFiltert END");
    }
}
