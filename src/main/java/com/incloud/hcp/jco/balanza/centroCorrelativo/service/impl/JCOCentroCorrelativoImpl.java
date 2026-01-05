package com.incloud.hcp.jco.balanza.centroCorrelativo.service.impl;

import com.incloud.hcp.jco.balanza.centroCorrelativo.service.JCOCentroCorrelativoBlzService;
import com.incloud.hcp.jco.balanza.centroCorrelativo.dto.SapTableCentroCorrelativoBlz;
import com.incloud.hcp.repository.CentroAlmacenBlzRepository;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOCentroCorrelativoImpl implements JCOCentroCorrelativoBlzService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private CentroAlmacenBlzRepository centroAlmacenBlzRepository;


    @Override
    public List<SapTableCentroCorrelativoBlz> extraerCorrelativo(String centro) throws Exception {
        try {
            logger.error("Extraccion CentroAlmacenBlz 01");
            String FUNCION_RFC = "ZFPE_MM_CONSULTA_SERIES";
            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();
            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            logger.error("Extraccion CentroAlmacenBlz 02");
            this.mapFilter(jCoFunction,centro);
            jCoFunction.execute(destination);
            logger.error("Extraccion CentroAlmacenBlz 03");
            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            CentroCorrelativoBlzMapper centroCorrelativoBlzMapper = CentroCorrelativoBlzMapper.newMapper(exportParameterList);
            logger.error("Extraccion CentroAlmacenBlz 04");
            List<SapTableCentroCorrelativoBlz> sapTableCentroAlmacenBlzs = centroCorrelativoBlzMapper.getSapTableCentroAlmacenBlzList();

            logger.error("Extraccion CentroAlmacenBlz 05");
            List<SapTableCentroCorrelativoBlz> centroAlmacenBlzs = new ArrayList<>();

            logger.error("Extraccion CentroAlmacenBlz 06 - size" + centroAlmacenBlzs.size());
            logger.error("Extraccion CentroAlmacenBlz 07");
            return sapTableCentroAlmacenBlzs;
        } catch (Exception e){
            logger.error("Error en la extracción: " + e.getMessage());
            throw new Exception(e);
        }
    }


    private void mapFilter(JCoFunction function,String centro){
        //CoParameterList parameterList = function.getImportParameterList();
        JCoParameterList paramList = function.getImportParameterList();
        logger.error("extraerCentroCorrelativoListRFC - paramList: " + paramList);
        paramList.setValue("PI_WERKS", centro);
    }
}
