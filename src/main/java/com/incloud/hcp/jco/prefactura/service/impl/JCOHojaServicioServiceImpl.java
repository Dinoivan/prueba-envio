package com.incloud.hcp.jco.prefactura.service.impl;

import com.incloud.hcp.jco.prefactura.dto.HojaServicioSubPosicionDto;
import com.incloud.hcp.jco.prefactura.service.JCOHojaServicioService;
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
public class JCOHojaServicioServiceImpl implements JCOHojaServicioService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Override
    public List<HojaServicioSubPosicionDto> extraerHojaServicioSubPosicionListRFC(String numeroHojaServicio) throws Exception {
        try {
            String header = "HOJA SERVICIO RFC // HES: " + numeroHojaServicio;
            String FUNCION_RFC = "ZPE_MM_INDICADOR_RET";

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();
            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);

            JCoParameterList paramList = jCoFunction.getImportParameterList();
            if (numeroHojaServicio != null && !numeroHojaServicio.isEmpty())
                paramList.setValue("I_ENTRYSHEET", numeroHojaServicio);
            jCoFunction.execute(destination);

            JCoParameterList exportTableList = jCoFunction.getTableParameterList();
            GenericDtoExtractorMapper genericDtoExtractorMapper = GenericDtoExtractorMapper.newMapper(exportTableList);
            List<HojaServicioSubPosicionDto> hojaServicioSubPosicionDtoList = genericDtoExtractorMapper.getSubPosicionDtoList();

            if (hojaServicioSubPosicionDtoList != null && !hojaServicioSubPosicionDtoList.isEmpty()) {
                for(int i = 0 ; i < hojaServicioSubPosicionDtoList.size() ; i++){
                    logger.error(header + " // Subposicion " + String.valueOf(i+1) + ": " + hojaServicioSubPosicionDtoList.get(i).toString());
                }
            }
            else{
                logger.error(header + " // No tiene subposiciones");
            }

            return hojaServicioSubPosicionDtoList;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }
}