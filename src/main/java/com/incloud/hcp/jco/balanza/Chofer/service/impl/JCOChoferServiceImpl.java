package com.incloud.hcp.jco.balanza.Chofer.service.impl;

import com.incloud.hcp.domain.balanza.Chofer;
import com.incloud.hcp.jco.balanza.Chofer.dto.ChoferGrabarServicioRFCParameterBuilder;
import com.incloud.hcp.jco.balanza.Chofer.dto.ChoferResponseDTO;
import com.incloud.hcp.jco.balanza.Chofer.dto.ChoferUpdateServicioRFCParameterBuilder;
import com.incloud.hcp.jco.balanza.Chofer.dto.SapTableChoferDto;
import com.incloud.hcp.jco.balanza.Chofer.service.JCOChoferService;
import com.incloud.hcp.repository.ChoferRepository;
import com.incloud.hcp.util.DateUtils;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOChoferServiceImpl implements JCOChoferService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AtomicBoolean daProcessing = new AtomicBoolean(false);

    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;

    private final String NOMBRE_TABLA_RFC_CHOFER = "ZTPE_MM_CHOFER";

    @Autowired
    private ChoferRepository choferRepository;

    /*UPDATE CHOFER*/
    private final int NRO_EJECUCIONES_RFC = 10;
    private final int NIVEL = 1;
    private final String FUNCION_RFC = "ZFPE_MM_CHOFER";
    //private final String FUNCION_ACTUALIZA_RFC = "ZFPE_MM_ACTUALIZA_CHOFER";
    private final String FUNCION_ACTUALIZA_RFC = "ZFPE_MM_PROCESO_DRV_SAPBTP";
    private final String NOMBRE_TABLA_RPTA_RFC = "BAPIRET2";
    private final String FUNCION_CREA_RFC = "ZFPE_MM_CREA_CHOFER";

    @Override
    public void extraerChoferListRFC(boolean extraccionChofer) throws Exception {
        logger.error("ExtraerChoferList - start");
        if (!daProcessing.get() || extraccionChofer) {
            if (!daProcessing.get() && !extraccionChofer)
                daProcessing.set(!daProcessing.get());
            try {
                logger.error("extraerChoferListRFC - try start");
                //String FUNCION_RFC = "ZFPE_MM_EXTRAE_CHOFER";
                String FUNCION_RFC = "ZFPE_MM_EXTRAER_SAPBTP";

                JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
                JCoRepository repository = destination.getRepository();

                //JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
                JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
                logger.error("extraerChoferListRFC - JCo Destination: " + destination); // NUEVO
                logger.error("extraerChoferListRFC - JCo Repository: " + repository); // NUEVO
                logger.error("extraerChoferListRFC - JCo Function: " + jCoFunction); // NUEVO
                Calendar calendar = Calendar.getInstance();
                Date fechaFin = new Date(calendar.getTimeInMillis());
                calendar.add(Calendar.DATE,-1);
                Date fechaIni = new Date(calendar.getTimeInMillis());
                logger.error("extraerChoferListRFC mapFilters - START fechaIni: "+fechaIni+"   * fechaFin: "+fechaFin);

                this.mapFilters(jCoFunction, fechaIni,fechaFin);
                logger.error("extraerChoferListRFC mapFilters - END");

                logger.error("extraerChoferListRFC execute destination - START");
                jCoFunction.execute(destination);
                logger.error("extraerChoferListRFC execute destination - END");

                JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
                //logger.error("extraerChoferListRFC - export Parameter List: " + exportParameterList); // NUEVO

                ChoferExtractorMapper choferExtractorMapper = ChoferExtractorMapper.newMapper(exportParameterList);
                //logger.error("extraerChoferListRFC - Chofer Extractor: " +  choferExtractorMapper);
                List<SapTableChoferDto> sapTableChoferDtoList = choferExtractorMapper.getSapTableChoferDtoList();

                /*Map<String, List<SapTableChoferDto>> entradaChoferMap = sapTableChoferDtoList.stream()
                        .distinct()
                        .collect(Collectors.groupingBy(SapTableChoferDto::getDni,
                                Collectors.toList()));
                logger.error("CANTIDAD DE CHOFERES ENCONTRADOS: " + entradaChoferMap.size());
                entradaChoferMap.forEach((dni, itemList) -> {*/
                for(SapTableChoferDto primerItem : sapTableChoferDtoList) {
                    logger.error("DNI: " + primerItem.getDni() + "Licen " + primerItem.getLicencia());
                    List<Integer> idChofer = choferRepository.getIdChoferByDniLicenciaIntegers(primerItem.getDni(), primerItem.getLicencia());
                    logger.error("Pre insercion: " + idChofer);
                    //if ((!idChofer.isEmpty() && idChofer.get(0) == null) || idChofer.isEmpty()){
                    if (idChofer.size() == 0) {
                        //if(idChofer.isEmpty()) logger.error("Chofer nuevo con dni " + dni);
                        //SapTableChoferDto primerItem = itemList.get(0);
                        Chofer chofer = new Chofer();
                        chofer.setDni(primerItem.getDni().trim());
                        chofer.setLicencia(primerItem.getLicencia().trim());
                        chofer.setTipoDocumento(primerItem.getTipoDocumento().trim());
                        chofer.setApellidoPaterno(primerItem.getApellidoPaterno().trim());
                        chofer.setApellidoMaterno(primerItem.getApellidoMaterno().trim());
                        chofer.setNombre(primerItem.getNombre().trim());
                        chofer.setEstado(primerItem.getStatus());
                        //chofer.set(primerItem.getCorrelativo());
                        chofer.setOrigen("SAP");
                        chofer.setMigrado("X");
                        chofer.setFechaCreacion(DateUtils.getCurrentTimestamp());
                        choferRepository.save(chofer);
                        //logger.error("Datos del chofer" + chofer.toString());
                    } else {
                        //List<Chofer> choferV = this.choferRepository.getChoferByDniList(dni);
                        List<Chofer> choferV = this.choferRepository.getIdChoferByDniLicencia(primerItem.getDni(), primerItem.getLicencia());

                        /*Chofer cv = new Chofer();
                        cv.setDni(choferV.get(0).getDni().trim());
                        cv.setNombre(choferV.get(0).getNombre().trim());
                        cv.setLicencia(choferV.get(0).getLicencia().trim());
                        cv.setEstado(choferV.get(0).getEstado());
                        //logger.error("CHOFER EN BD: " + cv);

                        SapTableChoferDto primerItem1 = primerItem;
                        Chofer choferN = new Chofer();
                        choferN.setNombre(primerItem1.getNombre().trim());
                        choferN.setDni(primerItem1.getDni().trim());
                        choferN.setLicencia(primerItem1.getLicencia().trim());
                        choferN.setEstado(primerItem1.getStatus());*/
                        //logger.error("CHOFER EN SAP: " + choferN);
                        //if (!cv.getNombre().equals(choferN.getNombre()) || !cv.getLicencia().equals(choferN.getLicencia()) || !cv.getEstado().equals(choferN.getEstado())) {
                        if (choferV.size()>0) {
                            logger.error("SE ACTUALIZO CHOFER CON PLACA: " + primerItem.getDni());
                            Chofer up = choferV.get(0);
                            up.setNombre(primerItem.getNombre().trim());
                            up.setLicencia(primerItem.getLicencia().trim());
                            up.setEstado(primerItem.getStatus());
                            up.setApellidoPaterno(primerItem.getApellidoPaterno().trim());
                            up.setApellidoMaterno(primerItem.getApellidoMaterno().trim());
                            up.setTipoDocumento(primerItem.getTipoDocumento().trim());
                            up.setOrigen("SAP");
                            up.setMigrado("X");
                            up.setFechaCreacion(DateUtils.getCurrentTimestamp());
                            this.choferRepository.save(up);
                        }
                    }
                    //});
                }
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

    private void extraerChoferListRFCV1(boolean extraccionChofer) throws Exception {
        logger.error("ExtraerChoferList - start");
        if (!daProcessing.get() || extraccionChofer) {
            if (!daProcessing.get() && !extraccionChofer)
                daProcessing.set(!daProcessing.get());
            try {
                logger.error("extraerChoferListRFC - try start");
                //String FUNCION_RFC = "ZFPE_MM_EXTRAE_CHOFER";
                String FUNCION_RFC = "ZFPE_MM_EXTRAER_SAPBTP";

                JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
                JCoRepository repository = destination.getRepository();

                //JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
                JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
                logger.error("extraerChoferListRFC - JCo Destination: " + destination); // NUEVO
                logger.error("extraerChoferListRFC - JCo Repository: " + repository); // NUEVO
                logger.error("extraerChoferListRFC - JCo Function: " + jCoFunction); // NUEVO
                Calendar calendar = Calendar.getInstance();
                Date fechaFin = new Date(calendar.getTimeInMillis());
                calendar.add(Calendar.DATE,-1);
                Date fechaIni = new Date(calendar.getTimeInMillis());
                logger.error("extraerChoferListRFC mapFilters - START fechaIni: "+fechaIni+"   * fechaFin: "+fechaFin);

                this.mapFilters(jCoFunction, fechaIni,fechaFin);
                logger.error("extraerChoferListRFC mapFilters - END");

                logger.error("extraerChoferListRFC execute destination - START");
                jCoFunction.execute(destination);
                logger.error("extraerChoferListRFC execute destination - END");

                JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
                //logger.error("extraerChoferListRFC - export Parameter List: " + exportParameterList); // NUEVO

                ChoferExtractorMapper choferExtractorMapper = ChoferExtractorMapper.newMapper(exportParameterList);
                //logger.error("extraerChoferListRFC - Chofer Extractor: " +  choferExtractorMapper);
                List<SapTableChoferDto> sapTableChoferDtoList = choferExtractorMapper.getSapTableChoferDtoList();

                Map<String, List<SapTableChoferDto>> entradaChoferMap = sapTableChoferDtoList.stream()
                        .distinct()
                        .collect(Collectors.groupingBy(SapTableChoferDto::getDni,
                                Collectors.toList()));
                logger.error("CANTIDAD DE CHOFERES ENCONTRADOS: " + entradaChoferMap.size());
                entradaChoferMap.forEach((dni, itemList) -> {
                    logger.error("DNI: "  +  dni);
                    List<Integer> idChofer = choferRepository.getIdChoferByDniLicenciaIntegers(dni,itemList.get(0).getLicencia());
                    logger.error("Pre insercion: " + idChofer);
                    if ((!idChofer.isEmpty() && idChofer.get(0) == null) || idChofer.isEmpty()){
                        if(idChofer.isEmpty()) logger.error("Chofer nuevo con dni " + dni);
                        SapTableChoferDto primerItem = itemList.get(0);
                        Chofer chofer = new Chofer();
                        chofer.setDni(primerItem.getDni().trim());
                        chofer.setLicencia(primerItem.getLicencia().trim());
                        chofer.setTipoDocumento(primerItem.getTipoDocumento().trim());
                        chofer.setApellidoPaterno(primerItem.getApellidoPaterno().trim());
                        chofer.setApellidoMaterno(primerItem.getApellidoMaterno().trim());
                        chofer.setNombre(primerItem.getNombre().trim());
                        chofer.setEstado(primerItem.getStatus());
                        //chofer.set(primerItem.getCorrelativo());
                        chofer.setOrigen("SAP");
                        chofer.setMigrado("X");
                        chofer.setFechaCreacion(DateUtils.getCurrentTimestamp());
                        choferRepository.save(chofer);
                        //logger.error("Datos del chofer" + chofer.toString());
                    }
                    List<Chofer> choferV = this.choferRepository.getChoferByDniList(dni);

                    Chofer cv = new Chofer();
                    cv.setDni(choferV.get(0).getDni().trim());
                    cv.setNombre(choferV.get(0).getNombre().trim());
                    cv.setLicencia(choferV.get(0).getLicencia().trim());
                    cv.setEstado(choferV.get(0).getEstado());
                    //logger.error("CHOFER EN BD: " + cv);

                    SapTableChoferDto primerItem = itemList.get(0);
                    Chofer choferN = new Chofer();
                    choferN.setNombre(primerItem.getNombre().trim());
                    choferN.setDni(primerItem.getDni().trim());
                    choferN.setLicencia(primerItem.getLicencia().trim());
                    choferN.setEstado(primerItem.getStatus());
                    //logger.error("CHOFER EN SAP: " + choferN);
                    if(!cv.getNombre().equals(choferN.getNombre()) || !cv.getLicencia().equals(choferN.getLicencia()) || !cv.getEstado().equals(choferN.getEstado())){
                        logger.error("SE ACTUALIZO CHOFER CON PLACA: " + dni);
                        Chofer up = choferV.get(0);
                        up.setNombre(primerItem.getNombre().trim());
                        up.setLicencia(primerItem.getLicencia().trim());
                        up.setEstado(primerItem.getStatus());
                        this.choferRepository.save(up);
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
    public ChoferResponseDTO actualizarChofer(ChoferResponseDTO choferUpdateResponse)
            throws Exception {
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("00C - ChoferUpdateResponseDTO Destination: " + destination);
        JCoRepository repository = destination.getRepository();
        logger.error("01C - actualizarChofer repository: " + repository);
        JCoFunction jCoFunction = repository.getFunction(FUNCION_ACTUALIZA_RFC);
        logger.error("02C - actualizarChofer jCoFunction: " + jCoFunction);
        ChoferUpdateServicioRFCParameterBuilder.build(
                jCoFunction,
                choferUpdateResponse
        );
        logger.error("03C - GET actualizarChofer");
        jCoFunction.execute(destination);
        JCoParameterList result = jCoFunction.getExportParameterList();
        logger.error("04C - result: "+ result);
        logger.error("05C - GET actualizarChofer - FIN choferUpdateResponseDTO: " + choferUpdateResponse.toString());
        return choferUpdateResponse;
    }
/*
    @Override
    public ChoferResponseDTO actualizarChofer(ChoferResponseDTO choferUpdateResponse)
            throws Exception {
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("00C - ChoferUpdateResponseDTO Destination: " + destination);
        JCoRepository repository = destination.getRepository();
        logger.error("01C - actualizarChofer repository: " + repository);
        JCoFunction jCoFunction = repository.getFunction(FUNCION_ACTUALIZA_RFC);
        logger.error("02C - actualizarChofer jCoFunction: " + jCoFunction);
        ChoferUpdateServicioRFCParameterBuilder.build(
                jCoFunction,
                choferUpdateResponse
        );
        logger.error("03C - GET actualizarChofer");
        jCoFunction.execute(destination);
        JCoParameterList result = jCoFunction.getExportParameterList();
        logger.error("04C - result: "+ result);
        logger.error("05C - GET actualizarChofer - FIN choferUpdateResponseDTO: " + choferUpdateResponse.toString());
        return choferUpdateResponse;
    }*/

    @Override
    public ChoferResponseDTO grabarChofer(ChoferResponseDTO choferGrabarResponse) throws Exception {
        ChoferResponseDTO choferResponseDTO = new ChoferResponseDTO();
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("ChoferUpdateResponseDTO Destination: " + destination);
        JCoRepository repository = destination.getRepository();
        logger.error("01A - grabarChofer repository: " + repository);
        JCoFunction jCoFunction = repository.getFunction(FUNCION_ACTUALIZA_RFC);
        logger.error("01B - grabarChofer jCoFunction: " + jCoFunction);



        ChoferGrabarServicioRFCParameterBuilder.build(
                jCoFunction,
                choferGrabarResponse
        );
        logger.error("01C - GET grabarChofer");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - grabarChofer - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        JCoParameterList result = jCoFunction.getExportParameterList();
        logger.error("result: "+ result);

        logger.error("03 - GET grabarChofer - FIN choferGrabarResponseDTO: " + choferResponseDTO.toString());

        logger.error("04 - GET grabarChofer - FIN choferGrabarResponseDTO: " + choferResponseDTO.toString());
        return choferResponseDTO;
    }

    //private void mapFilters(JCoFunction function, boolean extraccionUnicoDocumento) {
    private void mapFilters(JCoFunction function, Date fechaIni, Date fechaFin) {
        JCoParameterList paramList = function.getImportParameterList();
        logger.error("extraerChoferListRFC - paramList: " + paramList);
        paramList.setValue("I_FECHA_INICIO", fechaIni);
        paramList.setValue("I_FECHA_FIN", fechaFin);

//        JCoTable jcoTableRANGO_DNI = paramList.getTable("PI_DNI"); //
//
//        logger.error("extraerChoferListRFC - jcoTableRANGO_DNI: " + jcoTableRANGO_DNI);
//        jcoTableRANGO_DNI.appendRow();
//        jcoTableRANGO_DNI.setRow(0);
//
//        logger.error("extraerChoferListRFC - JCO Parameter List: " + paramList); // NUEVO
//        logger.error("extraerChoferListRFC - jco Table Rango DNI: " + jcoTableRANGO_DNI); // NUEVO
//        jcoTableRANGO_DNI.setValue("SIGN", "");
//        jcoTableRANGO_DNI.setValue("OPTION", "");
//        jcoTableRANGO_DNI.setValue("LOW", "");
//        jcoTableRANGO_DNI.setValue("HIGH", "");

    }

}