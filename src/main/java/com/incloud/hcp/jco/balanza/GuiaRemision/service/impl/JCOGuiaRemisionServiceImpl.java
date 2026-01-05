package com.incloud.hcp.jco.balanza.GuiaRemision.service.impl;

import com.incloud.hcp.jco.balanza.GuiaRemision.dto.GuiaRemisionResponseDTO;
import com.incloud.hcp.jco.balanza.GuiaRemision.dto.GuiaRemisionServicioRFCParameterBuilder;
import com.incloud.hcp.jco.balanza.GuiaRemision.service.JCOGuiaRemisionService;
import com.incloud.hcp.sap.SapLog;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOGuiaRemisionServiceImpl implements JCOGuiaRemisionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;
    private final int NIVEL = 1;
    private final String FUNCION_RFC = "ZFPE_MM_CREA_GUIAREMISION";
    private final String NOMBRE_TABLA_RPTA_RFC = "TO_RETURN";

    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;



    @Override
    public GuiaRemisionResponseDTO grabarGuiaRemision(GuiaRemisionResponseDTO guiaRemisionResponse)
            throws Exception {
        logger.error("Inicio grabarGuiaRemision - RFC");
        GuiaRemisionResponseDTO guiaRemisionResponseDTO = new GuiaRemisionResponseDTO();
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("grabarGuiaRemision destination: " + destination);
        JCoRepository repo = destination.getRepository();
        logger.error("grabarGuiaRemision repository: " + repo);
        logger.error("01A - grabarGuiaRemision");
        JCoFunction jCoFunction = repo.getFunction("ZFPE_MM_CREA_GUIAREMISION");
        logger.error("grabarGuiaRemision jCoFunction: " + jCoFunction);
        logger.error("01B - grabarGuiaRemision");

        GuiaRemisionServicioRFCParameterBuilder.build(
                jCoFunction,
                guiaRemisionResponse,
                guiaRemisionResponse.getGuiaRemisionPosList(),
                guiaRemisionResponse.getChofer().getDni(),
                guiaRemisionResponse.getChofer().getLicencia()
        );

        logger.error("01C - GET grabarGuiaRemision");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - grabarGuiaRemision - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        JCoParameterList result = jCoFunction.getExportParameterList();
        List<SapLog> listSapLog = new ArrayList<>();

        logger.error("03 - GET grabarGuiaRemision - FIN guiaRemisionResponseDTO: " + guiaRemisionResponseDTO.toString());
        JCoTable table = jCoFunction.getTableParameterList().getTable(NOMBRE_TABLA_RPTA_RFC);
        if (table != null && !table.isEmpty()) {
            do {
                SapLog sapLog = new SapLog();
                sapLog.setTipo(table.getString("TYPE"));
                sapLog.setCode(table.getString("NUMBER"));
                sapLog.setMesaj(table.getString("MESSAGE"));
                sapLog.setParameter(table.getString("PARAMETER"));
                sapLog.setRow(table.getString("ROW"));
                sapLog.setField(table.getString("FIELD"));
                sapLog.setSystem(table.getString("SYSTEM"));
                logger.error("03A - grabarGuiaRemision sapLog" + sapLog.toString());
                listSapLog.add(sapLog);


            } while (table.nextRow());
        }


        logger.error("04 - GET grabarGuiaRemision - FIN guiaRemisionResponseDTO: " + guiaRemisionResponseDTO.toString());
        guiaRemisionResponseDTO.setSapLogList(listSapLog);
        logger.error("05 - GET grabarGuiaRemision - AFTER SET SAP LOG LIST: " + guiaRemisionResponseDTO.toString());
        return guiaRemisionResponseDTO;
    }
}
