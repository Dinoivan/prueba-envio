package com.incloud.hcp.jco.contratoMarco.service.impl;

import com.incloud.hcp.jco.contratoMarco.dto.ContratoMarcoPdfSapDto;
import com.incloud.hcp.jco.contratoMarco.service.JCOContratoMarcoPdfService;
import com.incloud.hcp.util.Utils;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JCOContratoMarcoPdfServiceImpl implements JCOContratoMarcoPdfService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Override
    public ContratoMarcoPdfSapDto extraerContratoMarcoPdfDtoRFC(String numeroContratoMarco) throws Exception {
        String header = "CONTRATO MARCO RFC // CM: " + numeroContratoMarco;

        try {
            String FUNCION_RFC = "ZPE_MM_CONTRATO_MARCO_01";

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();
            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);

            JCoParameterList paramList = jCoFunction.getImportParameterList();
            if (numeroContratoMarco != null && !numeroContratoMarco.isEmpty())
                paramList.setValue("I_CONTRATO", numeroContratoMarco);
            jCoFunction.execute(destination);

            JCoParameterList exportParameterList = jCoFunction.getExportParameterList();
            ContratoMarcoExtractorMapper contratoMarcoExtractorMapper = ContratoMarcoExtractorMapper.newMapper(exportParameterList);
            List<ContratoMarcoPdfSapDto> contratoMarcoPdfSapDtoList = contratoMarcoExtractorMapper.getContratoMarcoPdfDtoList();

            ContratoMarcoPdfSapDto contratoMarcoPdfSapDto = new ContratoMarcoPdfSapDto();

            if (contratoMarcoPdfSapDtoList != null && !contratoMarcoPdfSapDtoList.isEmpty()) {
                if(contratoMarcoPdfSapDtoList.size() == 1) {
                    contratoMarcoPdfSapDto = contratoMarcoPdfSapDtoList.get(0);
                    logger.error(header + " // Data encontrada: " + contratoMarcoPdfSapDto.toString());
                }
                else{
                    logger.error(header + " // Se encontro mas de un registro para el numero de contrato marco");
                }
            }
            else{
                logger.error(header + " // No se encontro data con el numero de contrato marco");
            }

            return contratoMarcoPdfSapDto;
        }
        catch (Exception e) {
            logger.error(header + " // " + Utils.obtieneMensajeErrorException(e));
            throw new Exception(e);
        }
    }
}