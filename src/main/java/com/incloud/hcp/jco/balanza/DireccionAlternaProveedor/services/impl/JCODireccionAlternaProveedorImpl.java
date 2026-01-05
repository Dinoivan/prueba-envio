package com.incloud.hcp.jco.balanza.DireccionAlternaProveedor.services.impl;

import com.incloud.hcp.jco.balanza.DireccionAlternaProveedor.dto.DireccionAlternaCrearServicioRFCParameterBuilder;
import com.incloud.hcp.jco.balanza.DireccionAlternaProveedor.dto.DireccionAlternaProveedorMapper;
import com.incloud.hcp.jco.balanza.DireccionAlternaProveedor.dto.DireccionAlternaProveedorResponse;
import com.incloud.hcp.jco.balanza.DireccionAlternaProveedor.services.JCODireccionAlternaProveedorService;
import com.incloud.hcp.sap.SapLog;
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
public class JCODireccionAlternaProveedorImpl implements JCODireccionAlternaProveedorService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;
    private final int NRO_EJECUCIONES_RFC = 10;
    private final String FUNCION_CONSULTA_RFC = "ZFPE_MM_CONSULTA_DIRALT_PROVEE";
    private final String FUNCION_CREA_RFC = "ZFPE_MM_CREA_DIRALT_PROVEEDOR";
    @Override
    public List<DireccionAlternaProveedorResponse> consultaDireccionAlterna(String lifnr) throws Exception {
        logger.error("CONSULTA DIRECCION ALTERNA - START");
        logger.error("PARAMETRO LIFNR: " + lifnr);

        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("CONSULTA DIRECCION ALTERNA - JCoDestination: " + destination);

        JCoRepository repository = destination.getRepository();
        logger.error("CONSULTA DIRECCION ALTERNA - JCoRepository: " + repository);

        JCoFunction jCoFunction = repository.getFunction(FUNCION_CONSULTA_RFC);
        logger.error("CONSULTA DIRECCION ALTERNA - JCoFunction: " + jCoFunction);

        logger.error("CONSULTA DIRECCION ALTERNA - mapFilter START");
        this.mapFilter(jCoFunction, lifnr);
        logger.error("CONSULTA DIRECCION ALTERNA - mapFilter END");

        logger.error("CONSULTA DIRECCION ALTERNA - jCoFunction.execute START");
        jCoFunction.execute(destination);
        logger.error("CONSULTA DIRECCION ALTERNA - jCoFunction.execute END");

        logger.error("CONSULTA DIRECCION ALTERNA - JCoParameterList START");
        JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
        logger.error("CONSULTA DIRECCION ALTERNA - JCoParameterList END");

        logger.error("CONSULTA DIRECCION ALTERNA - direccionAlternaMapper START");
        DireccionAlternaProveedorMapper direccionAlternaProveedorMapper = DireccionAlternaProveedorMapper.newMapper(exportParameterList);
        logger.error("CONSULTA DIRECCION ALTERNA - direccionAlternaMapper END");

        logger.error("CONSULTA DIRECCION ALTERNA - Llenado de DireccionAlternaResponse START");
        List<DireccionAlternaProveedorResponse> direccionAlternaProveedorResponseList = direccionAlternaProveedorMapper.getDireccionAlternaResponseList();
        logger.error("CONSULTA DIRECCION ALTERNA - direccionAlternaResponseList: " + direccionAlternaProveedorResponseList);
        logger.error("CONSULTA DIRECCION ALTERNA - Llenado de DireccionAlternaResponse END");

        return direccionAlternaProveedorResponseList;
    }

    @Override
    public DireccionAlternaProveedorResponse crearDireccionAlternaProveedor(String lifnr, DireccionAlternaProveedorResponse direccionAlternaProveedorResponse) throws Exception {
        DireccionAlternaProveedorResponse direccionAlternaProveedorResponseDTO = new DireccionAlternaProveedorResponse();
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("DireccionAlternaProveedorCrearResponseDTO Destination: " + destination);
        JCoRepository repository = destination.getRepository();
        logger.error("01A - grabarDireccionAlternaProveedor repository: " + repository);
        JCoFunction jCoFunction = repository.getFunction(FUNCION_CREA_RFC);
        logger.error("01B - grabarDireccionAlternaProveedor jCoFunction: " + jCoFunction);

        /* Pasando los datos de parametria */
        DireccionAlternaCrearServicioRFCParameterBuilder.build_LIFNR(jCoFunction, lifnr);
        DireccionAlternaCrearServicioRFCParameterBuilder.build_ZSTPE_MM_ADRC(
                jCoFunction,
                direccionAlternaProveedorResponse
        );
        logger.error("01C - GET grabarDireccionAlternaProveedor");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - crearDireccionAlternaProveedor - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        JCoParameterList result = jCoFunction.getExportParameterList();
        List<SapLog> listSapLog = new ArrayList<>();
        logger.error("result: "+ result);

        logger.error("03 - GET crearDireccionAlternaProveedor - FIN direccionAlternaProveedorResponse: " + direccionAlternaProveedorResponseDTO.toString());

        logger.error("04 - GET grabarChofer - FIN choferGrabarResponseDTO: " + direccionAlternaProveedorResponseDTO.toString());
        return direccionAlternaProveedorResponseDTO;
    }

    public void mapFilter(JCoFunction jCoFunction, String lifnr){
        logger.error("DIRECCION ALTERNA - mapFilter START");
        logger.error("SEGUNDA VALIDACION DE PARAMETRO LIFNR: " + lifnr);

        JCoParameterList parameterList = jCoFunction.getImportParameterList();
        logger.error("DIRECCION ALTERNA - JCoParameterList: " + parameterList);
        parameterList.setValue("PI_LIFNR", lifnr);

        logger.error("DIRECCION ALTERNA - mapFilter END");
    }
}

