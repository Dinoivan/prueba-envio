package com.incloud.hcp.jco.balanza.DireccionAlternaCliente.services.impl;

import com.incloud.hcp.jco.balanza.DireccionAlternaCliente.dto.DireccionAlternaClienteArdc;
import com.incloud.hcp.jco.balanza.DireccionAlternaCliente.dto.DireccionAlternaClienteMapper;
import com.incloud.hcp.jco.balanza.DireccionAlternaCliente.dto.DireccionAlternaClienteParameterBuilder;
import com.incloud.hcp.jco.balanza.DireccionAlternaCliente.dto.DireccionAlternaClienteResponse;
import com.incloud.hcp.jco.balanza.DireccionAlternaCliente.services.JCODireccionAlternaClienteService;
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
public class JCODireccionAlternaClienteServiceImpl implements JCODireccionAlternaClienteService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;
    private final String FUNCION_CONSULTA_RFC = "ZFPE_MM_CONSULTA_DIRALT_CLIENT";
    private final String FUNCION_CREA_RFC = "ZFPE_MM_CREA_DIRALT_CLIENTE";

    private final int NRO_EJECUCIONES_RFC = 10;

    @Override
    public List<DireccionAlternaClienteResponse> consultaDirAltCliente(String kna1Kunnr) throws Exception {
        logger.error("CONSULTADIRALTCLIENTE - START");
        logger.error("PARAMETRO SERIEWERKS: " + kna1Kunnr);

        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("CONSULTADIRALTCLIENTE - JCoDestination: " + destination);

        JCoRepository repository = destination.getRepository();
        logger.error("CONSULTADIRALTCLIENTE - JCoRepository: " + repository);

        JCoFunction jCoFunction = repository.getFunction(FUNCION_CONSULTA_RFC);
        logger.error("CONSULTADIRALTCLIENTE - JCoFunction: " + jCoFunction);

        logger.error("CONSULTADIRALTCLIENTE - mapFilter START");
        this.mapFilter(jCoFunction, kna1Kunnr);
        logger.error("CONSULTADIRALTCLIENTE - mapFilter END");

        logger.error("CONSULTADIRALTCLIENTE - jCoFunction.execute START");
        jCoFunction.execute(destination);
        logger.error("CONSULTADIRALTCLIENTE - jCoFunction.execute END");

        logger.error("CONSULTADIRALTCLIENTE - JCoParameterList START");
        JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
        logger.error("CONSULTADIRALTCLIENTE - JCoParameterList END");

        logger.error("CONSULTADIRALTCLIENTE - serieConsultaMapper START");
        DireccionAlternaClienteMapper direccionAlternaClienteMapper = DireccionAlternaClienteMapper.newMapper(exportParameterList);
        logger.error("CONSULTADIRALTCLIENTE - serieConsultaMapper END");

        logger.error("CONSULTADIRALTCLIENTE - Llenado de SerieConstulaResponse START");
        List<DireccionAlternaClienteResponse> direccionAlternaClienteResponseList = direccionAlternaClienteMapper.getDireccionAlternaClienteList();
        logger.error("CONSULTADIRALTCLIENTE - serieConsultaResponseList: " + direccionAlternaClienteResponseList);
        logger.error("CONSULTADIRALTCLIENTE - Llenado de SerieConstulaResponse END");

        return direccionAlternaClienteResponseList;
    }

    @Override
    public DireccionAlternaClienteResponse crearDireccionAlternarCliente(String kna1Kunnr, DireccionAlternaClienteArdc response) throws Exception {
        DireccionAlternaClienteResponse direccionAlternaClienteResponse = new DireccionAlternaClienteResponse();
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("DireccionAlternaClienteResponse Destination: " + destination);
        JCoRepository repository = destination.getRepository();
        logger.error("01A - crearDireccionAlternarCliente repository: " + repository);
        JCoFunction jCoFunction = repository.getFunction(FUNCION_CREA_RFC);
        logger.error("01B - crearDireccionAlternarCliente jCoFunction: " + jCoFunction);

        logger.error("DireccionAlternaClienteParameterBuilder INICIO");
        DireccionAlternaClienteParameterBuilder.build(jCoFunction, kna1Kunnr, response);
        logger.error("DireccionAlternaClienteParameterBuilder FIN");

        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - crearDireccionAlternarCliente - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }
        JCoParameterList result = jCoFunction.getExportParameterList();
        logger.error("crearDireccionAlternarCliente result: " + result);

        return direccionAlternaClienteResponse;
    }

    public void mapFilter(JCoFunction jCoFunction, String kna1Kunnr){
        logger.error("CONSULTADIRALTCLIENTE - mapFilter START");
        logger.error("SEGUNDA VALIDACION DE PARAMETRO kna1Kunnr: " + kna1Kunnr);

        JCoParameterList parameterList = jCoFunction.getImportParameterList();
        logger.error("CONSULTADIRALTCLIENTE - JCoParameterList: " + parameterList);
        parameterList.setValue("PI_KUNNR", kna1Kunnr);

        logger.error("CONSULTADIRALTCLIENTE - mapFiltert END");
    }
}
