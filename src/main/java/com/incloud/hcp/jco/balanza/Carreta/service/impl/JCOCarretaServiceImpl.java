package com.incloud.hcp.jco.balanza.Carreta.service.impl;

import com.incloud.hcp.domain.balanza.Carreta;
import com.incloud.hcp.jco.balanza.Carreta.dto.CarretaGrabarServicioRFCParameterBuilder;
import com.incloud.hcp.jco.balanza.Carreta.dto.CarretaResponseDTO;
import com.incloud.hcp.jco.balanza.Carreta.dto.CarretaUpdateServicioRFCParameterBuilder;
import com.incloud.hcp.jco.balanza.Carreta.dto.SapTableCarretaDto;
import com.incloud.hcp.jco.balanza.Carreta.service.JCOCarretaService;
import com.incloud.hcp.repository.CarretaRepository;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.util.DateUtils;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOCarretaServiceImpl implements JCOCarretaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AtomicBoolean daProcessing = new AtomicBoolean(false);
    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;

    private final String NOMBRE_TABLA_RFC_CARRETA = "ZTPE_MM_CARRETA";
    @Autowired
    private CarretaRepository carretaRepository;

    /*UPDATE CARRETA*/
    private final int NRO_EJECUCIONES_RFC = 10;
    private final int NIVEL = 1;
    private final String FUNCION_RFC = "ZFPE_MM_ACTUALIZA_CARRETA";
    private final String NOMBRE_TABLA_RPTA_RFC = "BAPIRET2";
    @Override
    public void extraerCarretaListRFC(boolean extraccionCarreta) throws Exception {
        logger.error("extraerCarretaListRFC - start");
                if (!daProcessing.get() || extraccionCarreta) {
            if (!daProcessing.get() && !extraccionCarreta)
                daProcessing.set(!daProcessing.get());
            try {
                logger.error("extraerCarretaListRFC - try start");
                String FUNCION_RFC = "ZFPE_MM_EXTRAE_CARRETA";

                JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
                JCoRepository repository = destination.getRepository();

                JCoFunction jCoFunction = repository.getFunction("ZFPE_MM_EXTRAE_CARRETA");
                logger.error("extraerCarretaListRFC - JCo Destination: " + destination); // NUEVO
                logger.error("extraerCarretaListRFC - JCo Repository: " + repository); // NUEVO
                logger.error("extraerCarretaListRFC - JCo Function: " + jCoFunction); // NUEVO

                logger.error("extraerCarretaListRFC mapFilters - START");
                this.mapFilters(jCoFunction, extraccionCarreta);
                logger.error("extraerCarretaListRFC mapFilters - END");

                logger.error("extraerCarretaListRFC execute destination - START");
                jCoFunction.execute(destination);
                logger.error("extraerCarretaListRFC execute destination - END");

                JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
                //logger.error("extraerCarretaListRFC - export Parameter List: " + exportParameterList); // NUEVO

                CarretaExtractorMapper carretaExtractorMapper = CarretaExtractorMapper.newMapper(exportParameterList);
                //logger.error("extraerCarretaListRFC - Carreta Extractor: " +  carretaExtractorMapper);
                List<SapTableCarretaDto> sapTableCarretaDtoList = carretaExtractorMapper.getSapTableCarretaDtoList();

                Map<String, List<SapTableCarretaDto>> entradaCarretaMap = sapTableCarretaDtoList.stream()
                        .collect(Collectors.groupingBy(SapTableCarretaDto::getPlaca, Collectors.toList()));

                logger.error("CANTIDAD DE CARRETAS ENCONTRADAS: " + entradaCarretaMap.size());


               entradaCarretaMap.forEach((placa, itemList) -> {
                   //   logger.error("PLACA: " + placa);
                    List<Integer> idCarreta = carretaRepository.getIdCarretaByPlaca(placa);
                    logger.error("Pre insercion");
                   if ((!idCarreta.isEmpty() && idCarreta.get(0) == null) || idCarreta.isEmpty()){
                       if(idCarreta.isEmpty()) logger.error("Carreta nuevo con placa " + placa);
                        SapTableCarretaDto primerItem = itemList.get(0);
                        Carreta carreta = new Carreta();
                        carreta.setPlaca(primerItem.getPlaca().trim());
                        carreta.setModelo(primerItem.getModelo().trim());
                        carreta.setOrigen("SAP");
                        carreta.setEstado(true);
                        carretaRepository.save(carreta);
                       //logger.error("Datos de la carreta " + carreta.toString());
                    }
                   List<Carreta> carretaV = carretaRepository.findByPlacaList(placa);

                   Carreta cv = new Carreta();
                   cv.setPlaca(carretaV.get(0).getPlaca().trim());
                   cv.setModelo(carretaV.get(0).getModelo().trim());
                   //logger.error("CARRETA CV: " + cv);

                   SapTableCarretaDto primerItem = itemList.get(0);
                   Carreta carretaN = new Carreta();
                   carretaN.setPlaca(primerItem.getPlaca().trim());
                   carretaN.setModelo(primerItem.getModelo().trim());
                   // logger.error("CARRETA EN SAP: " + carretaN);
                   if(!cv.getModelo().equals(carretaN.getModelo())){
                       Carreta up = carretaV.get(0);
                       up.setModelo(primerItem.getModelo().trim());
                       //    logger.error("SE ACTUALIZA CARRETA CON PLACA: " + placa);
                       carretaRepository.save(up);
                   }

                });


                if (daProcessing.get())
                    daProcessing.set(!daProcessing.get());
            } catch (Exception e) {
                if (daProcessing.get())
                    daProcessing.set(!daProcessing.get());
                logger.error("extraerTransporteListRFC - ERROR: ");
                logger.error(e.getMessage(), e.getCause());
                throw new Exception(e);
            }
        } else {
            logger.error("Extracción en proceso");
        }
    }

    @Override
    public CarretaResponseDTO actualizarCarreta(CarretaResponseDTO carretaUpdateResponse)
            throws Exception {
        CarretaResponseDTO carretaResponseDTO = new CarretaResponseDTO();
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("grabarCarreta destination: "+ destination);
        JCoRepository repository = destination.getRepository();
        logger.error("grabarCarreta repository: " + repository);

        logger.error("01A - actualizarCarreta");
        JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
        logger.error("grabarCarreta jCoFunction: " + jCoFunction);
        logger.error("01B - actualizarCarreta");


        CarretaUpdateServicioRFCParameterBuilder.build(
                jCoFunction,
                carretaUpdateResponse
        );
        logger.error("01C - GET actualizarCarreta");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - actualizarCarreta - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        JCoParameterList result = jCoFunction.getExportParameterList();
        logger.error("grabarCarreta RESULT: " + result);

        logger.error("03 - GET actualizarCarreta - FIN carretaUpdateResponseDTO: " + carretaResponseDTO.toString());

        return carretaResponseDTO;
    }

    @Override
    public CarretaResponseDTO grabarCarreta(CarretaResponseDTO carretaGrabarResponse) throws Exception {
        CarretaResponseDTO carretaResponseDTO = new CarretaResponseDTO();
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("grabarCarreta destination: "+ destination);
        JCoRepository repository = destination.getRepository();
        logger.error("grabarCarreta repository: " + repository);

        logger.error("01A - actualizarCarreta");
        JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
        logger.error("grabarCarreta jCoFunction: " + jCoFunction);
        logger.error("01B - actualizarCarreta");

        CarretaGrabarServicioRFCParameterBuilder.build(
                jCoFunction,
                carretaGrabarResponse
        );
        logger.error("01C - GET actualizarCarreta");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - actualizarCarreta - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        JCoParameterList result = jCoFunction.getExportParameterList();
        logger.error("grabarCarreta RESULT: " + result);

        logger.error("03 - GET actualizarCarreta - FIN carretaUpdateResponseDTO: " + carretaResponseDTO.toString());

        logger.error("04 - GET actualizarCarreta - FIN carretaUpdateResponseDTO: " + carretaResponseDTO.toString());
        return carretaResponseDTO;
    }

    private void mapFilters(JCoFunction function, boolean extraccionUnicoPlaca) {
        JCoParameterList paramList = function.getImportParameterList();
        logger.error("extraerCarretaListRFC - paramList: " + paramList);
        //JCoTable jcoTableRANGO_PLACA = paramList.getTable("PI_PLACA"); //

        //jcoTableRANGO_PLACA.appendRow();
        //jcoTableRANGO_PLACA.setRow(0);

        //logger.error("extraerCarretaListRFC - JCO Parameter List: " + paramList); // NUEVO
        //logger.error("extraerCarretaListRFC - jco Table Rango Placa: " + jcoTableRANGO_PLACA); // NUEVO

//      jcoTableRANGO_PLACA.setValue("SIGN", "I");
//      jcoTableRANGO_PLACA.setValue("OPTION", "EQ");
//      jcoTableRANGO_PLACA.setValue("LOW", parametro1); // parametro numero orden compra a la cual se extraera sus doc aceptacion
//      jcoTableRANGO_PLACA.setValue("HIGH", "");


    }

}