package com.incloud.hcp.jco.centroAlmacen.service.impl;

import com.incloud.hcp.domain.CentroAlmacenBalanzaSap;
import com.incloud.hcp.domain.TempCentroAlmacen;
import com.incloud.hcp.domain.balanza.CentroAlmacenBlz;
import com.incloud.hcp.jco.centroAlmacen.dto.CentroAlmacenRFCParameterBuilder;
import com.incloud.hcp.jco.centroAlmacen.dto.CentroAlmacenRFCResponseDto;
import com.incloud.hcp.jco.centroAlmacen.dto.CentroRFCDto;
import com.incloud.hcp.jco.centroAlmacen.service.JCOCentroAlmacenServiceNew;
import com.incloud.hcp.repository.CentroAlmacenBalanzaSapRepository;
import com.incloud.hcp.repository.CentroAlmacenBlzRepository;
import com.incloud.hcp.repository.CentroAlmacenRepository;
import com.incloud.hcp.repository.TempCentroAlmacenRepository;
import com.incloud.hcp.sap.SapLog;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JCOCentroAlmacenServiceNewImpl implements JCOCentroAlmacenServiceNew {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;
    private final int NIVEL = 1;
//    private final String FUNCION_RFC = "ZMMRFC_LISTA_CNTRO_ALMCN";
    //private final String FUNCION_RFC = "ZPE_MM_LISTA_CENTRO_ALMACEN";
    private final String FUNCION_RFC = "ZFPE_MM_LISTA_CENTRO_ALMACEN";
    private final String NOMBRE_TABLA_RFC = "TO_CENTRO_ALMACEN";

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CentroAlmacenRepository centroAlmacenRepository;

    @Autowired
    private CentroAlmacenBalanzaSapRepository centroAlmacenBalanzaSapRepository;

    @Autowired
    private CentroAlmacenBlzRepository centroAlmacenBlzRepository;

    @Autowired
    private TempCentroAlmacenRepository tempCentroAlmacenRepository;



    @Override
    public CentroAlmacenRFCResponseDto getListaCentroAlmacen(String centro) throws Exception {

        CentroAlmacenRFCResponseDto centroAlmacenRFCResponseDto = new CentroAlmacenRFCResponseDto();

        SapLog sapLog = new SapLog();
        String codigoSap = "0";
        String message = "Consulta exitosa";
        sapLog.setCode(codigoSap);
        sapLog.setMesaj(message);
        centroAlmacenRFCResponseDto.setSapLog(sapLog);
        logger.error("02b 01 - getCentroAlmacen - fin recorrido list: " + sapLog.toString());
        //List<CentroAlmacenBalanzaSap> centroAlmacenRFCDtoList = this.centroAlmacenBalanzaSapRepository.findAll();
        List<CentroAlmacenBlz> centroAlmacenRFCDtoList = this.centroAlmacenBlzRepository.findAll();
        centroAlmacenRFCResponseDto.setListaCentroAlmacen(centroAlmacenRFCDtoList);
        if (centroAlmacenRFCDtoList != null && centroAlmacenRFCDtoList.size() > 0) {
            centroAlmacenRFCResponseDto.setContador(centroAlmacenRFCDtoList.size());
            logger.error("02 getDevuelveValores centroAlmacenRFCDtoList: " + centroAlmacenRFCDtoList.size());
        }
        return centroAlmacenRFCResponseDto;
    }

    @Override
    public CentroAlmacenRFCResponseDto getListaCentroAlmacenRFC(List<String> centros) throws JCoException {
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoFunction function = destination.getRepository().getFunction("ZFPE_MM_LISTADO_CENTROS");
        // Armar respuesta
        CentroAlmacenRFCResponseDto response = new CentroAlmacenRFCResponseDto();
        SapLog log = new SapLog();
        try {

            if (function == null) {
                throw new RuntimeException("No se encontró el RFC ZFPE_MM_LISTADO_CENTROS en SAP.");
            }
            logger.error("Ingresando: ",function);

            // Tabla de entrada
            JCoTable input = function.getTableParameterList().getTable("TI_LISTA");

            for (String centro : centros) {
                if (centro != null && !centro.trim().isEmpty()) {
                    input.appendRow();
                    input.setValue("WERKS", centro);
                    logger.error("Centro enviado a SAP RFC: {}", centro); //
                }else{
                    logger.error("Centro nulo o vacío ignorado");
                }
            }
            // Ejecutar RFC
            function.execute(destination);


            // Leer la misma tabla TI_LISTA que ahora contiene la salida
            JCoTable output = function.getTableParameterList().getTable("TI_LISTA");

            logger.error("Ouput: {}", output);

            List<CentroRFCDto> centrosExistentes = new ArrayList<>();
            for (int i = 0; i < output.getNumRows(); i++) {
                output.setRow(i);
                CentroRFCDto dto = new CentroRFCDto();
                String werks = output.getString("WERKS");
                dto.setWerks(werks);
                centrosExistentes.add(dto);

                // Log por consola
                logger.error("Centro encontrado en SAP: {}", werks);
            }

            response.setListaCentroAlmacenRFC(centrosExistentes);
            response.setContador(centrosExistentes.size());
            log.setCode("0");
            log.setMesaj("Centros procesados correctamente");
            response.setSapLog(log);
        }catch (JCoException e){
            logger.error("Error en ejecución del RFC SAP: {}", e.getMessage(), e);
            response.setContador(0); // ← importante
            log.setCode("1");
            log.setMesaj("Error en ejecución del RFC: " + e.getMessage());
            response.setSapLog(log);
        }
        return response;
    }

    private CentroAlmacenRFCResponseDto getListaCentroAlmacenBackup(String centro) throws Exception {

        CentroAlmacenRFCResponseDto centroAlmacenRFCResponseDto = new CentroAlmacenRFCResponseDto();

        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - getCentroAlmacen");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01B - getCentroAlmacen");

        logger.error("parametro ingresado" + centro);
        CentroAlmacenRFCParameterBuilder.build(
                jCoFunction,
                centro
        );
        logger.error("01C - getCentroAlmacen");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - getCentroAlmacen - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }
        /*try {
            jCoFunction.execute(destination);
        } catch (Exception e) {
            logger.error("01Ca - getCentroAlmacen - INI RFC ERROR: "+ e.toString());
            throw new Exception(e);

        }*/

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 - getCentroAlmacen - FIN RFC");
        JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
        JCoParameterList result = jCoFunction.getExportParameterList();
        SapLog sapLog = new SapLog();
        String codigoSap = result.getString("PO_CODE");
        String message = result.getString("PO_MSJE");
        sapLog.setCode(codigoSap);
        sapLog.setMesaj(message);
        centroAlmacenRFCResponseDto.setSapLog(sapLog);
        logger.error("02b - getCentroAlmacen - sapLog: " + sapLog.toString());

        /* Recorriendo valores obtenidos del RFC */

        List<TempCentroAlmacen> centroAlmacenRFCDtoList = new ArrayList<TempCentroAlmacen>();
        JCoTable table = tableParameterList.getTable(NOMBRE_TABLA_RFC);
        logger.error("02b 01 - getCentroAlmacen - sapLog: " + sapLog.toString());
        logger.error("TABLE CENTRO_ALMACEN: " + table);
        if (table != null && !table.isEmpty()) {

            do {
                //logger.error("02 bA - getDevuelveValores TABLE JCO: " + table.toString());
                TempCentroAlmacen centroAlmacenRFCDto = new TempCentroAlmacen();
                centroAlmacenRFCDto.setCentro(table.getString("WERKS"));
                centroAlmacenRFCDto.setPoblacion(table.getString("ORT01"));
                centroAlmacenRFCDto.setDistrito(table.getString("CITY2"));
                centroAlmacenRFCDto.setDireccion(table.getString("STRAS"));
                centroAlmacenRFCDto.setDireccion2(table.getString("STRAS2"));
                centroAlmacenRFCDto.setDireccion3(table.getString("STRAS3"));
                centroAlmacenRFCDto.setCodigoAlmacen(table.getString("LGORT"));
                centroAlmacenRFCDto.setDescripcionAlmacen(table.getString("LGOBE"));
                centroAlmacenRFCDto.setDireccionCentro(table.getString("CEN_STRAS"));
                centroAlmacenRFCDto.setNombre(table.getString("NAME1"));
                centroAlmacenRFCDtoList.add(centroAlmacenRFCDto);
            } while (table.nextRow());
        }
        logger.error("02b 01 - getCentroAlmacen - fin recorrido list: " + sapLog.toString());
        //centroAlmacenRFCResponseDto.setListaCentroAlmacen(centroAlmacenRFCDtoList);
        if (centroAlmacenRFCDtoList != null && centroAlmacenRFCDtoList.size() > 0) {
            centroAlmacenRFCResponseDto.setContador(centroAlmacenRFCDtoList.size());
            logger.error("02 getDevuelveValores centroAlmacenRFCDtoList: " + centroAlmacenRFCDtoList.size());
        }
        this.tempCentroAlmacenRepository.deleteAlll();
        this.tempCentroAlmacenRepository.saveAll(centroAlmacenRFCDtoList);
        return centroAlmacenRFCResponseDto;
    }

    @Override
    public CentroAlmacenRFCResponseDto getListaCentroAlmacen_v1(String centro) throws Exception {

        CentroAlmacenRFCResponseDto centroAlmacenRFCResponseDto = new CentroAlmacenRFCResponseDto();

        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - getCentroAlmacen");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01B - getCentroAlmacen");

        logger.error("parametro ingresado" + centro);
        CentroAlmacenRFCParameterBuilder.build(
                jCoFunction,
                centro
        );
        logger.error("01C - getCentroAlmacen");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - getCentroAlmacen - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }
        /*try {
            jCoFunction.execute(destination);
        } catch (Exception e) {
            logger.error("01Ca - getCentroAlmacen - INI RFC ERROR: "+ e.toString());
            throw new Exception(e);

        }*/

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 - getCentroAlmacen - FIN RFC");
        JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
        JCoParameterList result = jCoFunction.getExportParameterList();
        SapLog sapLog = new SapLog();
        String codigoSap = result.getString("PO_CODE");
        String message = result.getString("PO_MSJE");
        sapLog.setCode(codigoSap);
        sapLog.setMesaj(message);
        centroAlmacenRFCResponseDto.setSapLog(sapLog);
        logger.error("02b - getCentroAlmacen - sapLog: " + sapLog.toString());

        /* Recorriendo valores obtenidos del RFC */

        //List<CentroAlmacenBalanzaSap> centroAlmacenRFCDtoList = new ArrayList<CentroAlmacenBalanzaSap>();
        List<CentroAlmacenBlz> centroAlmacenRFCDtoList = new ArrayList<CentroAlmacenBlz>();
        JCoTable table = tableParameterList.getTable(NOMBRE_TABLA_RFC);
        logger.error("02b 01 - getCentroAlmacen - sapLog: " + sapLog.toString());
        logger.error("TABLE CENTRO_ALMACEN: " + table);
        if (table != null && !table.isEmpty()) {

            do {
                //logger.error("02 bA - getDevuelveValores TABLE JCO: " + table.toString());
                CentroAlmacenBlz centroAlmacenRFCDto = new CentroAlmacenBlz();
                centroAlmacenRFCDto.setCentro(table.getString("WERKS"));
                centroAlmacenRFCDto.setPoblacion(table.getString("ORT01"));
                centroAlmacenRFCDto.setDistrito(table.getString("CITY2"));
                //centroAlmacenRFCDto.setDireccion(table.getString("STRAS"));
                centroAlmacenRFCDto.setDireccionCentro(table.getString("STRAS"));
                centroAlmacenRFCDto.setDireccion2(table.getString("STRAS2"));
                centroAlmacenRFCDto.setDireccion3(table.getString("STRAS3"));
                centroAlmacenRFCDto.setCodigoAlmacen(table.getString("LGORT"));
                centroAlmacenRFCDto.setDescripcionAlmacen(table.getString("LGOBE"));
                centroAlmacenRFCDto.setDireccionCentro(table.getString("CEN_STRAS"));
                centroAlmacenRFCDto.setNombre(table.getString("NAME1"));
                centroAlmacenRFCDtoList.add(centroAlmacenRFCDto);
            } while (table.nextRow());
        }
        logger.error("02b 01 - getCentroAlmacen - fin recorrido list: " + sapLog.toString());
        centroAlmacenRFCResponseDto.setListaCentroAlmacen(centroAlmacenRFCDtoList);
        if (centroAlmacenRFCDtoList != null && centroAlmacenRFCDtoList.size() > 0) {
            centroAlmacenRFCResponseDto.setContador(centroAlmacenRFCDtoList.size());
            logger.error("02 getDevuelveValores centroAlmacenRFCDtoList: " + centroAlmacenRFCDtoList.size());
        }
        /*this.centroAlmacenBalanzaSapRepository.deleteAlll();
        this.centroAlmacenBalanzaSapRepository.saveAll(centroAlmacenRFCDtoList);*/
        this.centroAlmacenBlzRepository.deleteAlll();
        this.centroAlmacenBlzRepository.saveAll(centroAlmacenRFCDtoList);
        return centroAlmacenRFCResponseDto;
    }
}
