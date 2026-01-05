package com.incloud.hcp.jco.EstadoDocumentoAceptacion.services.impl;

import com.incloud.hcp.jco.EstadoDocumentoAceptacion.dto.EstadoDocumentoAceptacionMapper;
import com.incloud.hcp.jco.EstadoDocumentoAceptacion.dto.EstadoDocumentoAceptacionRFCParameterBuilder;
import com.incloud.hcp.jco.EstadoDocumentoAceptacion.dto.EstadoDocumentoAceptacionResponse;
import com.incloud.hcp.jco.EstadoDocumentoAceptacion.dto.RangeSap;
import com.incloud.hcp.jco.EstadoDocumentoAceptacion.services.JCOEstadoDocumentoAceptacionService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
public class JCOEstadoDocumentoAceptacionImpl implements JCOEstadoDocumentoAceptacionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AtomicBoolean daProcessing = new AtomicBoolean(false);
    @Value("${destination.rfc.profit}")
    private String destinationProfit;
    private final String FUNCION_RFC = "ZPE_MM_ESTAT_DOC_ACEP";
    @Override
    public List<EstadoDocumentoAceptacionResponse> extraerEstadoDocumentoAceptacionRFC(List<RangeSap> rangeSap) throws Exception {
        logger.error("extraerEstadoDocumentoAceptacionRFC - INICIO");
        List<EstadoDocumentoAceptacionResponse> estadoDAResponseList = new ArrayList<>();
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("extraerEstadoDocumentoAceptacionRFC - destination" + destination);
        JCoRepository repo = destination.getRepository();
        logger.error("extraerEstadoDocumentoAceptacionRFC - repository" + repo);
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("extraerEstadoDocumentoAceptacionRFC - jcoFunction" + jCoFunction);
        EstadoDocumentoAceptacionRFCParameterBuilder.mapFilter(jCoFunction,
                rangeSap);
        jCoFunction.execute(destination);
        JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
        logger.error("extraerEstadoDocumentoAceptacionRFC - exportParameterList" + exportParameterList);
        EstadoDocumentoAceptacionMapper estadoDocumentoAceptacionMapper = EstadoDocumentoAceptacionMapper.newMapper(exportParameterList);
        logger.error("extraerEstadoDocumentoAceptacionRFC - estadoDocumentoAceptacionMapper" + estadoDocumentoAceptacionMapper);
        List<EstadoDocumentoAceptacionResponse> estadoDocumentoAceptacionResponseList = estadoDocumentoAceptacionMapper.getEstadoDireccionAlternaResponse();
        logger.error("extraerEstadoDocumentoAceptacionRFC - estadoDocumentoAceptacionResponseList" + estadoDocumentoAceptacionResponseList);
        return estadoDocumentoAceptacionResponseList;
    }
}
