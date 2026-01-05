package com.incloud.hcp.jco.balanza.MaterialLote.service.impl;

import com.incloud.hcp.jco.balanza.MaterialLote.dto.MaterialLoteParameterBuilder;
import com.incloud.hcp.jco.balanza.MaterialLote.dto.MaterialLoteResponse;
import com.incloud.hcp.jco.balanza.MaterialLote.service.JCOMaterialLoteService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOMaterialLoteServiceImpl implements JCOMaterialLoteService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;
    private final String FUNCION_CONSULTA_RFC = "ZFPE_MM_CONSULTA_MATERIAL_LOTE";


    @Override
    public List<MaterialLoteResponse> consultaMaterialLote(String piWerks, String piLgort) throws Exception {
        logger.error("CONSULTAMATERIALLOTE - START");
        logger.error("PARAMETRO PI_WERKS: " + piWerks);
        logger.error("PARAMETRO PI_LGORT: " + piLgort);

        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("CONSULTAMATERIALLOTE - JCoDestination: " + destination);

        JCoRepository repository = destination.getRepository();
        logger.error("CONSULTAMATERIALLOTE - JCoRepository: " + repository);

        JCoFunction jCoFunction = repository.getFunction(FUNCION_CONSULTA_RFC);
        logger.error("CONSULTAMATERIALLOTE - JCoFunction: " + jCoFunction);

        logger.error("CONSULTAMATERIALLOTE - mapFilter START");
        this.mapFilter(jCoFunction, piWerks, piLgort);
        logger.error("CONSULTAMATERIALLOTE - mapFilter END");

        logger.error("CONSULTAMATERIALLOTE - jCoFunction.execute START");
        jCoFunction.execute(destination);
        logger.error("CONSULTAMATERIALLOTE - jCoFunction.execute END");

        logger.error("CONSULTAMATERIALLOTE - JCoParameterList START");
        JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
        logger.error("CONSULTAMATERIALLOTE - JCoParameterList END");

        logger.error("CONSULTAMATERIALLOTE - materialLoteParameterBuilder START");
        MaterialLoteParameterBuilder materialLoteParameterBuilder = MaterialLoteParameterBuilder.newMapper(exportParameterList);
        logger.error("CONSULTAMATERIALLOTE - materialLoteParameterBuilder END");

        logger.error("CONSULTAMATERIALLOTE - Llenado de MaterialLoteResponse START");
        List<MaterialLoteResponse> materialLoteResponseList = materialLoteParameterBuilder.getMaterialLoteList();
        logger.error("CONSULTAMATERIALLOTE - materialLoteResponseList: " + materialLoteResponseList);
        logger.error("CONSULTAMATERIALLOTE - Llenado de MaterialLoteResponse END");

        return materialLoteResponseList;
    }

    public void mapFilter(JCoFunction jCoFunction, String piWerks, String piLgort){
        logger.error("CONSULTAMATERIALLOTE - mapFilter START");
        logger.error("SEGUNDA VALIDACION DE PARAMETRO piWerks: " + piWerks);
        logger.error("SEGUNDA VALIDACION DE PARAMETRO piLgort: " + piLgort);

        JCoParameterList parameterList = jCoFunction.getImportParameterList();
        logger.error("CONSULTAMATERIALLOTE - JCoParameterList: " + parameterList);
        parameterList.setValue("PI_WERKS", piWerks);
        parameterList.setValue("PI_LGORT", piLgort);

        logger.error("CONSULTAMATERIALLOTE - mapFiltert END");
    }
}
