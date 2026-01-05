package com.incloud.hcp.jco.balanza.Transporte.service.impl;

import com.incloud.hcp.domain.balanza.Transporte;
import com.incloud.hcp.jco.balanza.Transporte.dto.SapTableTransporteDto;
import com.incloud.hcp.jco.balanza.Transporte.dto.TransporteResponseDTO;
import com.incloud.hcp.jco.balanza.Transporte.dto.TransporteUpdateServicioRFCParameterBuilder;
import com.incloud.hcp.jco.balanza.Transporte.service.JCOTransporteService;
import com.incloud.hcp.repository.TransporteRepository;
import com.incloud.hcp.util.DateUtils;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
public class JCOTransporteServiceImpl implements JCOTransporteService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AtomicBoolean daProcessing = new AtomicBoolean(false);

    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;

    private final int NRO_EJECUCIONES_RFC = 10;

    //private final String FUNCION_EXTRAE_RFC = "ZFPE_MM_EXTRAE_TRANSPORTE";
    private final String FUNCION_EXTRAE_TRANSPORTE_CHOFER_RFC = "ZFPE_MM_EXTRAER_SAPBTP";
    //private final String FUNCION_ACTUALIZA_RFC = "ZFPE_MM_ACTUALIZA_TRANSPORTE";
    private final String FUNCION_PROCESO_TRANSPORTE_RFC = "ZFPE_MM_PROCESO_TRUCK_SAPBTP";
    private final String FUNCION_CREA_RFC = "ZTPE_MM_TRANSPORTE";
    @Autowired
    private TransporteRepository transporteRepository;

    @Override
    public void extraerTransporteListRFC(boolean extraccionTransporte) throws Exception {
        logger.error("extraerTransporteListRFC - start");
        if(!daProcessing.get() || extraccionTransporte){
            if(!daProcessing.get() && !extraccionTransporte)
                daProcessing.set(!daProcessing.get());
            try {
                logger.error("extraerTransporteListRFC - try start");
                JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
                logger.error("extraerTransporte - destination: " + destination);
                JCoRepository repository = destination.getRepository();
                logger.error("extraerTransporte - repository: " + repository);
                JCoFunction jCoFunction = repository.getFunction(FUNCION_EXTRAE_TRANSPORTE_CHOFER_RFC);
                logger.error("extraerTransporte - jCoFunction: " + jCoFunction);

                Calendar calendar = Calendar.getInstance();
                Date fechaFin = new Date(calendar.getTimeInMillis());
                calendar.add(Calendar.DATE,-1);
                Date fechaIni = new Date(calendar.getTimeInMillis());
                logger.error("extraerTransporte mapFilters - START fechaIni: "+fechaIni+"   * fechaFin: "+fechaFin);

                this.mapFilters(jCoFunction, fechaIni,fechaFin);
                logger.error("extraerTransporte mapFilters - END");

                logger.error("extraerTransporte execute destination - START");
                jCoFunction.execute(destination);
                logger.error("extraerTransporte execute destination - END");

                JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
                logger.error("extraerTransporte - exportParameterList: " + exportParameterList);
                TransporteExtractorMapper transporteExtractorMapper = TransporteExtractorMapper.newMapper(exportParameterList);

                List<SapTableTransporteDto> sapTableTransporteDtoList = transporteExtractorMapper.getSapTableTransporteDtoList();
                ///logger.error("sapTableTransporteDtoList: " + sapTableTransporteDtoList);

                Map<String, List<SapTableTransporteDto>> entradaTransporteMap = sapTableTransporteDtoList.stream()
                        .collect(Collectors.groupingBy(SapTableTransporteDto::getPlaca, Collectors.toList()));

                // logger.error("sapTableTransporteDtoList: " + entradaTransporteMap);
                logger.error("CANTIDAD DE TRANSPORTES ENCONTRADOS: " + entradaTransporteMap.size());
                entradaTransporteMap.forEach((placa, itemList) -> {
                    //  logger.error("Pre insercion foreach trasnporte - placa : " + placa.toString());
                    //  logger.error("Pre insercion foreach trasnporte - itemList : " + itemList.toString());
                    List<Integer> idTransporte = transporteRepository.getIdTransporteByPlaca(placa);
                    //    logger.error("Pre insercion foreach trasnporte - List<integer> idTransporte : " + idTransporte.toString());
                    logger.error("Pre insercion");
                    // SOLO PARA NUEVOS TRANSPORTES
                    SapTableTransporteDto primerItem = itemList.get(0);
                    if((!idTransporte.isEmpty() && idTransporte.get(0) == null) || idTransporte.isEmpty()){
                        if(idTransporte.isEmpty()) logger.error("Transporte nuevo con placa " + placa);

                        //  logger.error("PRIMER ITEM: " + primerItem);
                        Transporte transporte = new Transporte();
                        transporte.setPlaca(primerItem.getPlaca().trim());
                        transporte.setRemolque(primerItem.getRemolque().trim());
                        transporte.setTipoVehiculo(primerItem.getTipoVehiculo().trim());
                        transporte.setMarca(primerItem.getMarca().trim());
                        transporte.setModelo(primerItem.getModelo().trim());
                        transporte.setCiv(primerItem.getCiv());
                        transporte.setEstado(primerItem.getStatus());
                        transporte.setNroAutorizacion(primerItem.getNroAutorizacion());
                        transporte.setCodAutorizacion(primerItem.getCodAutorizacion());
                        transporte.setZmtc(primerItem.getZmtc());
                        transporte.setCreatedBy(primerItem.getCreatedBy());
                        transporte.setModifiedBy(primerItem.getModifiedBy());
                        transporte.setFechaCreacion(new Date());
                        transporte.setOrigen("SAP");
                        //   logger.error("transporte save: " + transporte.toString());
                        transporteRepository.save(transporte);
                        //   logger.error("Datos del transporte" + transporte.toString());
                    }
                    // VERIFICAR SI TRANSPORTE SE HA ACTUALIZADO
                    List<Transporte> transporteV = transporteRepository.findByPlacaList(placa);
                    /*Transporte tv = new Transporte();
                    tv.setModelo(transporteV.get(0).getModelo().trim());
                    tv.setMarca(transporteV.get(0).getMarca().trim());
                    tv.setPlaca(transporteV.get(0).getPlaca().trim());
                    tv.setCiv(transporteV.get(0).getCiv());
                    tv.setEstado(Optional.ofNullable(transporteV.get(0).getEstado()).orElse(""));
                    //logger.error("TRANSPOTE V: " + tv);

                    SapTableTransporteDto primerItem = itemList.get(0);
                    Transporte transporteN = new Transporte();
                    transporteN.setModelo(primerItem.getModelo().trim());
                    transporteN.setMarca(primerItem.getMarca().trim());
                    transporteN.setPlaca(primerItem.getPlaca().trim());
                    transporteN.setCiv(primerItem.getCiv());
                    transporteN.setEstado(Optional.ofNullable(primerItem.getStatus()).orElse(""));*/
                    //logger.error("TRANSPORTE EN SAP: " + transporteN);
                    //if(!tv.getModelo().equals(transporteN.getModelo()) || !tv.getMarca().equals(transporteN.getMarca()) || !tv.getCiv().equals(transporteN.getCiv()) || !tv.getEstado().equals(transporteN.getEstado())){
                    if(transporteV.size()>0){
                        Transporte up = transporteV.get(0);
                        up.setModelo(primerItem.getModelo().trim());
                        up.setMarca(primerItem.getMarca().trim());
                        up.setCiv(primerItem.getCiv());
                        up.setEstado(primerItem.getStatus());
                        up.setTipoVehiculo(primerItem.getTipoVehiculo());
                        up.setRemolque(primerItem.getRemolque().trim());
                        up.setNroAutorizacion(primerItem.getNroAutorizacion());
                        up.setCodAutorizacion(primerItem.getCodAutorizacion());
                        up.setZmtc(primerItem.getZmtc());
                        up.setFechaCreacion(new Date());
                        // logger.error("SE ACTUALIZO TRANSPORTE CON PLACA: " + placa);
                        transporteRepository.save(up);
                    }
                });


                if(daProcessing.get())
                    daProcessing.set(!daProcessing.get());
            }catch (Exception e){
                if(daProcessing.get())
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
    public TransporteResponseDTO actualizaTransporte(TransporteResponseDTO actualizaTransporte) throws Exception {
        TransporteResponseDTO sapTableTransporteActualizar = new TransporteResponseDTO();
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("actualizaTransporte DESTINATION: " + destination);
        JCoRepository repository = destination.getRepository();
        logger.error("01A - actualiza Transporte REPOSITORY: " + repository);
        JCoFunction jCoFunction = repository.getFunction(FUNCION_PROCESO_TRANSPORTE_RFC);
        logger.error("01B - actualiza Transporte jCoFunction: " + jCoFunction);

        TransporteUpdateServicioRFCParameterBuilder.buildActualiza(
                jCoFunction,
                actualizaTransporte
        );
        logger.error("01C - GET actualiza Transporte");
        jCoFunction.execute(destination);
        JCoParameterList result = jCoFunction.getExportParameterList();
        logger.error("actualizaTransporte RESULT: " + result);

        logger.error("02 - GET actualizaTransporte - FIN guiaRemisionResponseDTO: " + sapTableTransporteActualizar);
        return actualizaTransporte;
    }

    @Override
    public TransporteResponseDTO grabarTransporte(TransporteResponseDTO creaTransporte) throws Exception {
        TransporteResponseDTO sapTableTransporteActualizar = new TransporteResponseDTO();
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("grabarTransporte DESTINATION: " + destination);
        JCoRepository repository = destination.getRepository();
        logger.error("01A - actualiza/grabar Transporte REPOSITORY: " + repository);
        JCoFunction jCoFunction = repository.getFunction(FUNCION_PROCESO_TRANSPORTE_RFC);
        logger.error("01B - actualiza/grabar Transporte jCoFunction: " + jCoFunction);

        TransporteUpdateServicioRFCParameterBuilder.buildCrea(
                jCoFunction,
                creaTransporte
        );

        logger.error("01C - GET actualiza/grabar Transporte");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - actualizaTransporte - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }
        JCoParameterList result = jCoFunction.getExportParameterList();
        logger.error("grabarTransporte RESULT: " + result);

        logger.error("02 - GET actualizaTransporte - FIN guiaRemisionResponseDTO: " + sapTableTransporteActualizar);
        return creaTransporte;
    }

   /* @Override
    public TransporteResponseDTO grabarTransporte(TransporteResponseDTO creaTransporte) throws Exception {
        TransporteResponseDTO sapTableTransporteActualizar = new TransporteResponseDTO();
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("grabarTransporte DESTINATION: " + destination);
        JCoRepository repository = destination.getRepository();
        logger.error("01A - actualiza/grabar Transporte REPOSITORY: " + repository);
        JCoFunction jCoFunction = repository.getFunction(FUNCION_ACTUALIZA_RFC);
        logger.error("01B - actualiza/grabar Transporte jCoFunction: " + jCoFunction);

        TransporteUpdateServicioRFCParameterBuilder.buildCrea(
                jCoFunction,
                creaTransporte
        );

        logger.error("01C - GET actualiza/grabar Transporte");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - actualizaTransporte - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }
        JCoParameterList result = jCoFunction.getExportParameterList();
        logger.error("grabarTransporte RESULT: " + result);

        logger.error("02 - GET actualizaTransporte - FIN guiaRemisionResponseDTO: " + sapTableTransporteActualizar);
        return creaTransporte;
    }*/

    private void mapFilters(JCoFunction function, Date fechaIni, Date fechaFin) {
        JCoParameterList paramList = function.getImportParameterList();
        paramList.setValue("I_FECHA_INICIO", fechaIni);
        paramList.setValue("I_FECHA_FIN", fechaFin);
        logger.error("extraerTransporteListRFC - paramList: " + paramList);

        //JCoTable jcoTableRANGO_PLACA = paramList.getTable("PI_PLACA"); //
        //logger.error("extraerTransporte - jcoTableRANGO_PLACA: " + jcoTableRANGO_PLACA);
        //    jcoTableRANGO_PLACA.appendRow();
        //    jcoTableRANGO_PLACA.setRow(0);
//            jcoTableRANGO_PLACA.setValue("SIGN", "I");
//            jcoTableRANGO_PLACA.setValue("OPTION", "EQ");
//            jcoTableRANGO_PLACA.setValue("LOW", parametro1);
//            jcoTableRANGO_PLACA.setValue("HIGH", "");

    }
}
