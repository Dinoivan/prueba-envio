package com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.service.Impl;

import com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.dto.MaterialIDto;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.dto.MaterialOutDto;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.dto.MaterialOutMapper;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.service.JCOMaterialOutService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOMaterialOutServiceImpl implements JCOMaterialOutService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String FUNCION_CONSULTA_RFC = "ZFPE_MM_CONSULTA_MATERIAL";

    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;

    @Override
    public MaterialOutDto consultaMaterialRFC(MaterialIDto materialIResponse) throws Exception {
        logger.error("CONSULTA MATERIAL OUT - START");
        logger.error("PARAMETRO materialIResponse: " + materialIResponse);

        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("CONSULTA MATERIAL OUT - JCoDestination: " + destination);

        JCoRepository repository = destination.getRepository();
        logger.error("CONSULTA MATERIAL OUT - JCoRepository: " + repository);

        JCoFunction jCoFunction = repository.getFunction(FUNCION_CONSULTA_RFC);
        logger.error("CONSULTA MATERIAL OUT - JCoFunction: " + jCoFunction);

        logger.error("CONSULTA MATERIAL OUT - mapFilter START");
        this.mapFilter(jCoFunction, materialIResponse);
        logger.error("CONSULTA MATERIAL OUT - mapFilter END");

        logger.error("CONSULTA MATERIAL OUT - jCoFunction.execute START");
        jCoFunction.execute(destination);
        logger.error("CONSULTA MATERIAL OUT - jCoFunction.execute END");

        logger.error("CONSULTA MATERIAL OUT - JCoParameterList START");
        JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
        logger.error("CONSULTA MATERIAL OUT - JCoParameterList END");

        logger.error("CONSULTA MATERIAL OUT - materialOutMapper START");
        MaterialOutMapper materialOutMapper = MaterialOutMapper.newMapper(exportParameterList);
        logger.error("CONSULTA MATERIAL OUT - materialOutMapper END");

        logger.error("CONSULTA MATERIAL OUT - Llenado de materialOutResponse START");
        MaterialOutDto materialOutDto = materialOutMapper.getMaterialOutResponse();
        logger.error("CONSULTA MATERIAL OUT - materialOutDto: " + materialOutDto);
        logger.error("CONSULTA MATERIAL OUT - Llenado de materialOutResponse END");
        return materialOutDto;
    }
    public void mapFilter(JCoFunction jCoFunction, MaterialIDto response){
        logger.error("CONSULTA MATERIAL OUT - mapFilter START");
        logger.error("SEGUNDA VALIDACION DE PARAMETRO lgort: " + response.getLgort());
        logger.error("SEGUNDA VALIDACION DE PARAMETRO werks: " + response.getWerks());
        logger.error("SEGUNDA VALIDACION DE PARAMETRO matnr: " + response.getMatnr());

        JCoStructure parameterList = jCoFunction.getImportParameterList().getStructure("PI_MATERIAL");
        logger.error("CONSULTA MATERIAL OUT - JCoParameterList: " + parameterList);
        parameterList.setValue("LGORT", response.getLgort());
        parameterList.setValue("WERKS", response.getWerks());
        parameterList.setValue("MATNR", response.getMatnr());

        logger.error("CONSULTA MATERIAL OUT- mapFilter END");
    }
}
