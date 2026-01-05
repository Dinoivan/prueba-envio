package com.incloud.hcp.jco.balanza.MotivosTraslado.service.impl;

import com.incloud.hcp.jco.balanza.MotivosTraslado.dto.*;
import com.incloud.hcp.jco.balanza.MotivosTraslado.service.JCOMotivosTrasladoService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOMotivosTrasladoServiceImpl implements JCOMotivosTrasladoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AtomicBoolean daProcessing = new AtomicBoolean(false);
    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;
    private final String FUNCION_RFC = "ZFPE_MM_EXTRAE_MOTIVOSTRASLADO";
    private final String FUNCION_RFC_OTRO = "ZFPE_MM_EXTRAE_MOTROS";
    private final String FUNCION_RFC_MODO_TRANSPORTE = "ZFPE_MM_EXTRAE_MODTRANSP";
    private final String FUNCION_RFC_TIPO_MOVIMIENTO = "ZFPE_MM_EXTRAE_TIPOMOV";
    private final String FUNCION_RFC_EXTRAE_UM = "ZFPE_MM_EXTRAE_UM";
    private final String FUNCION_RFC_INDICADOR_SERVICIO = "ZFPE_MM_EXTRAE_INDSERV";
    private final String FUNCION_RFC_AEROPUESTO = "ZFPE_MM_EXTRAE_AEROPU";
    private final String FUNCION_RFC_EXPTOLLEG = "ZFPE_MM_EXTRAE_EXPTOLLEG";
    private final String FUNCION_RFC_TIPO_TIPOLOC = "ZFPE_MM_EXTRAE_TIPOLOC";
    private final String FUNCION_RFC_PUERTOS = "ZFPE_MM_EXTRAE_PUERTOS";


    @Override
    public List<MotivosTrasladoResponseDTO> extraerMotivoTrasladoListRFC() throws Exception {
        logger.error("ExtraerMotivosTrasladoList - start");
            try {
                logger.error("extraerMotivosTrasladoListRFC - try start");

                JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
                JCoRepository repository = destination.getRepository();

                JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
                logger.error("extraerMotivosTrasladoListRFC - JCo Destination: " + destination); // NUEVO
                logger.error("extraerMotivosTrasladoListRFC - JCo Repository: " + repository); // NUEVO
                logger.error("extraerMotivosTrasladoListRFC - JCo Function: " + jCoFunction); // NUEVO

                logger.error("extraerMotivosTrasladoListRFC execute destination - START");
                jCoFunction.execute(destination);
                logger.error("extraerMotivosTrasladoListRFC execute destination - END");

                JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
                logger.error("extraerMotivosTrasladoListRFC - export Parameter List: " + exportParameterList); // NUEVO

                MotivosTrasladoExtractorMapper motivosTrasladoExtractorMapper = MotivosTrasladoExtractorMapper.newMapper(exportParameterList);
                logger.error("extraerMotivosTrasladoListRFC - Motivo Traslado Extractor: " +  motivosTrasladoExtractorMapper);
                List<MotivosTrasladoResponseDTO> motivosTrasladoResponseDTOList = motivosTrasladoExtractorMapper.getSapTableMotivosTrasladoDtoList();
                return motivosTrasladoResponseDTOList;
            } catch (Exception e) {
                logger.error("extraerMotivosTrasladoListRFC - ERROR: ");
                logger.error(e.getMessage(), e.getCause());
                throw new Exception(e);
            }
    }

    @Override
    public List<MotivosOtroResponseDTO> extraerMotivoOtrosListRFC() throws Exception {
        logger.error("ExtraerMotivosTrasladoList - start");
        try {
            logger.error("extraerMotivosTrasladoListRFC - try start");

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC_OTRO);
            logger.error("extraerMotivosTrasladoListRFC - JCo Destination: " + destination); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Repository: " + repository); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Function: " + jCoFunction); // NUEVO

            logger.error("extraerMotivosTrasladoListRFC execute destination - START");
            jCoFunction.execute(destination);
            logger.error("extraerMotivosTrasladoListRFC execute destination - END");

            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            logger.error("extraerMotivosTrasladoListRFC - export Parameter List: " + exportParameterList); // NUEVO

            MotivosTrasladoExtractorMapper motivosTrasladoExtractorMapper = MotivosTrasladoExtractorMapper.newMapper(exportParameterList);
            logger.error("extraerMotivosTrasladoListRFC - Motivo Traslado Extractor: " +  motivosTrasladoExtractorMapper);
            List<MotivosOtroResponseDTO> motivosTrasladoResponseDTOList = motivosTrasladoExtractorMapper.getSapTableMotivosOtroladoDtoList();
            return motivosTrasladoResponseDTOList;
        } catch (Exception e) {
            logger.error("extraerMotivosTrasladoListRFC - ERROR: ");
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }


    @Override
    public List<ModoTransporteResponseDTO> extraerModoTransporteListRFC() throws Exception {
        logger.error("ExtraerMotivosTrasladoList - start");
        try {
            logger.error("extraerMotivosTrasladoListRFC - try start");

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC_MODO_TRANSPORTE);
            logger.error("extraerMotivosTrasladoListRFC - JCo Destination: " + destination); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Repository: " + repository); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Function: " + jCoFunction); // NUEVO

            logger.error("extraerMotivosTrasladoListRFC execute destination - START");
            jCoFunction.execute(destination);
            logger.error("extraerMotivosTrasladoListRFC execute destination - END");

            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            logger.error("extraerMotivosTrasladoListRFC - export Parameter List: " + exportParameterList); // NUEVO

            MotivosTrasladoExtractorMapper motivosTrasladoExtractorMapper = MotivosTrasladoExtractorMapper.newMapper(exportParameterList);
            logger.error("extraerMotivosTrasladoListRFC - Motivo Traslado Extractor: " +  motivosTrasladoExtractorMapper);
            List<ModoTransporteResponseDTO> motivosTrasladoResponseDTOList = motivosTrasladoExtractorMapper.getSapTableModoTransporteDtoList();
            return motivosTrasladoResponseDTOList;
        } catch (Exception e) {
            logger.error("extraerMotivosTrasladoListRFC - ERROR: ");
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }

    @Override
    public List<TipoMovimientoResponseDTO> extraerTipoMovimientoListRFC() throws Exception {
        logger.error("ExtraerMotivosTrasladoList - start");
        try {
            logger.error("extraerMotivosTrasladoListRFC - try start");

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC_TIPO_MOVIMIENTO);
            logger.error("extraerMotivosTrasladoListRFC - JCo Destination: " + destination); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Repository: " + repository); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Function: " + jCoFunction); // NUEVO

            logger.error("extraerMotivosTrasladoListRFC execute destination - START");
            jCoFunction.execute(destination);
            logger.error("extraerMotivosTrasladoListRFC execute destination - END");

            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            logger.error("extraerMotivosTrasladoListRFC - export Parameter List: " + exportParameterList); // NUEVO

            MotivosTrasladoExtractorMapper motivosTrasladoExtractorMapper = MotivosTrasladoExtractorMapper.newMapper(exportParameterList);
            logger.error("extraerMotivosTrasladoListRFC - Motivo Traslado Extractor: " +  motivosTrasladoExtractorMapper);
            List<TipoMovimientoResponseDTO> motivosTrasladoResponseDTOList = motivosTrasladoExtractorMapper.getSapTipoMovimientoDtoList();
            return motivosTrasladoResponseDTOList;
        } catch (Exception e) {
            logger.error("extraerMotivosTrasladoListRFC - ERROR: ");
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }
    @Override
    public List<UnidadMedidaResponseDTO> extraerUnidadMedidaListRFC() throws Exception {
        logger.error("ExtraerMotivosTrasladoList - start");
        try {
            logger.error("extraerMotivosTrasladoListRFC - try start");

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC_EXTRAE_UM);
            logger.error("extraerMotivosTrasladoListRFC - JCo Destination: " + destination); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Repository: " + repository); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Function: " + jCoFunction); // NUEVO

            logger.error("extraerMotivosTrasladoListRFC execute destination - START");
            jCoFunction.execute(destination);
            logger.error("extraerMotivosTrasladoListRFC execute destination - END");

            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            logger.error("extraerMotivosTrasladoListRFC - export Parameter List: " + exportParameterList); // NUEVO

            MotivosTrasladoExtractorMapper motivosTrasladoExtractorMapper = MotivosTrasladoExtractorMapper.newMapper(exportParameterList);
            logger.error("extraerMotivosTrasladoListRFC - Motivo Traslado Extractor: " +  motivosTrasladoExtractorMapper);
            List<UnidadMedidaResponseDTO> motivosTrasladoResponseDTOList = motivosTrasladoExtractorMapper.getSapUnidadMedidaDtoList();
            return motivosTrasladoResponseDTOList;
        } catch (Exception e) {
            logger.error("extraerMotivosTrasladoListRFC - ERROR: ");
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }
    @Override
    public List<IndicadorServicioResponseDTO> extraerIndicadorServicioListRFC() throws Exception {
        logger.error("ExtraerMotivosTrasladoList - start");
        try {
            logger.error("extraerMotivosTrasladoListRFC - try start");

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC_INDICADOR_SERVICIO);
            logger.error("extraerMotivosTrasladoListRFC - JCo Destination: " + destination); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Repository: " + repository); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Function: " + jCoFunction); // NUEVO

            logger.error("extraerMotivosTrasladoListRFC execute destination - START");
            jCoFunction.execute(destination);
            logger.error("extraerMotivosTrasladoListRFC execute destination - END");

            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            logger.error("extraerMotivosTrasladoListRFC - export Parameter List: " + exportParameterList); // NUEVO

            MotivosTrasladoExtractorMapper motivosTrasladoExtractorMapper = MotivosTrasladoExtractorMapper.newMapper(exportParameterList);
            logger.error("extraerMotivosTrasladoListRFC - Motivo Traslado Extractor: " +  motivosTrasladoExtractorMapper);
            List<IndicadorServicioResponseDTO> motivosTrasladoResponseDTOList = motivosTrasladoExtractorMapper.getSapIndicadorServicoDtoList();
            return motivosTrasladoResponseDTOList;
        } catch (Exception e) {
            logger.error("extraerMotivosTrasladoListRFC - ERROR: ");
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }

    @Override
    public List<TipoLocacionDTO> tipoLocacion() throws Exception {
        logger.error("ExtraerMotivosTrasladoList - start");
        try {
            logger.error("extraerMotivosTrasladoListRFC - try start");

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC_TIPO_TIPOLOC);
            logger.error("extraerMotivosTrasladoListRFC - JCo Destination: " + destination); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Repository: " + repository); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Function: " + jCoFunction); // NUEVO

            logger.error("extraerMotivosTrasladoListRFC execute destination - START");
            jCoFunction.execute(destination);
            logger.error("extraerMotivosTrasladoListRFC execute destination - END");

            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            logger.error("extraerMotivosTrasladoListRFC - export Parameter List: " + exportParameterList); // NUEVO

            MotivosTrasladoExtractorMapper motivosTrasladoExtractorMapper = MotivosTrasladoExtractorMapper.newMapper(exportParameterList);
            logger.error("extraerMotivosTrasladoListRFC - Motivo Traslado Extractor: " +  motivosTrasladoExtractorMapper);
            List<TipoLocacionDTO> motivosTrasladoResponseDTOList = motivosTrasladoExtractorMapper.getTipoLocacionList();
            return motivosTrasladoResponseDTOList;
        } catch (Exception e) {
            logger.error("extraerMotivosTrasladoListRFC - ERROR: ");
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }
    @Override
    public List<TipoLocacionDTO> tipoPuertoList() throws Exception {
        logger.error("ExtraerMotivosTrasladoList - start");
        try {
            logger.error("extraerMotivosTrasladoListRFC - try start");

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC_EXPTOLLEG);
            logger.error("extraerMotivosTrasladoListRFC - JCo Destination: " + destination); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Repository: " + repository); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Function: " + jCoFunction); // NUEVO

            logger.error("extraerMotivosTrasladoListRFC execute destination - START");
            jCoFunction.execute(destination);
            logger.error("extraerMotivosTrasladoListRFC execute destination - END");

            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            logger.error("extraerMotivosTrasladoListRFC - export Parameter List: " + exportParameterList); // NUEVO

            MotivosTrasladoExtractorMapper motivosTrasladoExtractorMapper = MotivosTrasladoExtractorMapper.newMapper(exportParameterList);
            logger.error("extraerMotivosTrasladoListRFC - Motivo Traslado Extractor: " +  motivosTrasladoExtractorMapper);
            List<TipoLocacionDTO> motivosTrasladoResponseDTOList = motivosTrasladoExtractorMapper.tipoPuertoList();
            return motivosTrasladoResponseDTOList;
        } catch (Exception e) {
            logger.error("extraerMotivosTrasladoListRFC - ERROR: ");
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }

    @Override
    public List<PuertoAeropuertoDTO> puertoLlegadaList() throws Exception {
        logger.error("ExtraerMotivosTrasladoList - start");
        try {
            logger.error("extraerMotivosTrasladoListRFC - try start");

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC_PUERTOS);
            logger.error("extraerMotivosTrasladoListRFC - JCo Destination: " + destination); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Repository: " + repository); // NUEVO
            logger.error("extraerMotivosTrasladoListRFC - JCo Function: " + jCoFunction); // NUEVO

            logger.error("extraerMotivosTrasladoListRFC execute destination - START");
            jCoFunction.execute(destination);
            logger.error("extraerMotivosTrasladoListRFC execute destination - END");

            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            logger.error("extraerMotivosTrasladoListRFC - export Parameter List: " + exportParameterList); // NUEVO

            MotivosTrasladoExtractorMapper motivosTrasladoExtractorMapper = MotivosTrasladoExtractorMapper.newMapper(exportParameterList);
            logger.error("extraerMotivosTrasladoListRFC - Motivo Traslado Extractor: " +  motivosTrasladoExtractorMapper);
            List<PuertoAeropuertoDTO> motivosTrasladoResponseDTOList = motivosTrasladoExtractorMapper.puertoLlegadaList();
            return motivosTrasladoResponseDTOList;
        } catch (Exception e) {
            logger.error("extraerMotivosTrasladoListRFC - ERROR: ");
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }
    @Override
    public List<PuertoAeropuertoDTO> aeropuertoLlegadaList() throws Exception {
        logger.error("ExtraerMotivosTrasladoList - start");
        try {
            logger.error("extraerMotivosTrasladoListRFC - try start");

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC_AEROPUESTO);
            jCoFunction.execute(destination);
            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            MotivosTrasladoExtractorMapper motivosTrasladoExtractorMapper = MotivosTrasladoExtractorMapper.newMapper(exportParameterList);
            List<PuertoAeropuertoDTO> motivosTrasladoResponseDTOList = motivosTrasladoExtractorMapper.AeropuertoLlegadaList();
            return motivosTrasladoResponseDTOList;
        } catch (Exception e) {
            logger.error("extraerMotivosTrasladoListRFC - ERROR: ");
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }


}
