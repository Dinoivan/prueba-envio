package com.incloud.hcp.jco.ubigeo.service.impl;


import com.incloud.hcp.domain.Ubigeo;
import com.incloud.hcp.jco.ubigeo.dto.UbigeoRFCDTO;
import com.incloud.hcp.jco.ubigeo.dto.UbigeoRFCResponseDto;
import com.incloud.hcp.jco.ubigeo.service.JCOUbigeoService;
import com.incloud.hcp.repository.UbigeoRepository;
import com.incloud.hcp.sap.SapLog;
import com.sap.conn.jco.*;
import org.apache.commons.lang.StringUtils;
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
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOUbigeoServiceImpl implements JCOUbigeoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;

//    private final String FUNCION_RFC = "ZMMRFC_CONSULTA_UBIGEO";
    private final String FUNCION_RFC = "ZPE_MM_CONSULTA_UBIGEO";
    private final String NOMBRE_TABLA_RFC_PAISES = "T_T005";
    private final String NOMBRE_TABLA_RFC_REGION = "T_T005S";
    private final String NOMBRE_TABLA_RFC_POBLACION = "T_ADRCITY";
    private final String NOMBRE_TABLA_RFC_DISTRITO = "T_ADRCITYPRT";
    private final Integer NIVEL_PAIS = 0;
    private final Integer NIVEL_REGION = 1;
    private final Integer NIVEL_PROVINCIA = 2;
    private final Integer NIVEL_DISTRITO = 3;



    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UbigeoRepository ubigeoRepository;


    @Override
    public UbigeoRFCResponseDto listarandActualizarUbigeoRFC() throws Exception {
        UbigeoRFCResponseDto ubigeoRFCResponseDto = new UbigeoRFCResponseDto();

        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - getUBIGEORFC");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01tB - getUBIGEORFC");


        logger.error("01C - getUBIGEORFC");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - getUBIGEORFC - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 - getUBIGEORFC - FIN RFC");
        JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
        JCoParameterList result = jCoFunction.getExportParameterList();
        SapLog sapLog = new SapLog();
        String codigoSap = result.getString("PO_CODE");
        String message = result.getString("PO_MSJE");
        sapLog.setCode(codigoSap);
        sapLog.setMesaj(message);
        ubigeoRFCResponseDto.setSapLog(sapLog);
        logger.error("02b - getUBIGEORFC - sapLog: " + sapLog.toString());


        /* Recorriendo valores obtenidos del RFC */
        ///Paises RFC
        List<UbigeoRFCDTO> listaPaises=new ArrayList<>();
        JCoTable table = tableParameterList.getTable(NOMBRE_TABLA_RFC_PAISES);
        int contador = 0;
        int contadorTotal = 0;
        if (table != null && !table.isEmpty()) {
            do {
                UbigeoRFCDTO ubigeoRFCDTO=new UbigeoRFCDTO();
                ubigeoRFCDTO.setClavePais(table.getString("LAND1"));
                ubigeoRFCDTO.setDenominacion(table.getString("LANDX"));

                try {
                    Ubigeo ubigeoEncontrado = this.ubigeoRepository.findByCodigoUbigeoSapAndNivel(
                            ubigeoRFCDTO.getClavePais(),
                            NIVEL_PAIS);
                    if (Optional.ofNullable(ubigeoEncontrado).isPresent()) {
                        contador++;
                        ubigeoEncontrado.setCodigoUbigeoSapErp(ubigeoRFCDTO.getClavePais());
                        ubigeoEncontrado.setDescripcion(ubigeoRFCDTO.getDenominacion());
                        this.ubigeoRepository.save(ubigeoEncontrado);
                    } else {
                        logger.error("UBIGEO ERR PAIS : " + ubigeoRFCDTO.toString());
                    }
                }
                catch (Exception ex) {
                    logger.error("UBIGEO ERR PAIS EXCEPTION: " + ubigeoRFCDTO.toString());
                }
                contadorTotal++;
                listaPaises.add(ubigeoRFCDTO);
            } while (table.nextRow());
        }
        ubigeoRFCResponseDto.setContadorActualizadoPais(contador);
        ubigeoRFCResponseDto.setContadorTotalPais(contadorTotal);


        contador = 0;
        contadorTotal = 0;
        List<UbigeoRFCDTO> listaRegion=new ArrayList<>();
        JCoTable tableRegion = tableParameterList.getTable(NOMBRE_TABLA_RFC_REGION);
        if (tableRegion != null && !tableRegion.isEmpty()) {
            do {
                UbigeoRFCDTO ubigeoRFCDTO=new UbigeoRFCDTO();
                ubigeoRFCDTO.setClavePais(tableRegion.getString("LAND1"));
                ubigeoRFCDTO.setDenominacion(tableRegion.getString("BEZEI"));
                ubigeoRFCDTO.setClaveRegion(tableRegion.getString("BLAND"));
                ubigeoRFCDTO.setClavePoblacion(tableRegion.getString("FPRCD"));
                ubigeoRFCDTO.setClaveEquivalenciaSunat(tableRegion.getString("BLAND_S"));

                Ubigeo ubigeoEncontrado = null;
                if (StringUtils.isNotBlank(tableRegion.getString("BLAND_S"))) {
                    ubigeoEncontrado = this.ubigeoRepository.findByCodigoUbigeoSapAndNivel(
                            ubigeoRFCDTO.getClaveEquivalenciaSunat(),
                            NIVEL_REGION
                    );
                }
                if (Optional.ofNullable(ubigeoEncontrado).isPresent()) {
                    contador++;
                    ubigeoEncontrado.setCodigoUbigeoSapErp(ubigeoRFCDTO.getClaveRegion());
                    ubigeoEncontrado.setDescripcion(ubigeoRFCDTO.getDenominacion());
                    this.ubigeoRepository.save(ubigeoEncontrado);
                }
                else {
                    logger.error("UBIGEO ERR REGION : " + ubigeoRFCDTO.toString());
                }
                contadorTotal++;
                listaRegion.add(ubigeoRFCDTO);
            } while (tableRegion.nextRow());
        }
        ubigeoRFCResponseDto.setContadorActualizadoRegion(contador);
        ubigeoRFCResponseDto.setContadorTotalRegion(contadorTotal);


        contador = 0;
        contadorTotal = 0;
        List<UbigeoRFCDTO> listaPoblacion=new ArrayList<>();
        JCoTable tablePoblacion = tableParameterList.getTable(NOMBRE_TABLA_RFC_POBLACION);
        if (tablePoblacion != null && !tablePoblacion.isEmpty()) {
            do {
                UbigeoRFCDTO ubigeoRFCDTO=new UbigeoRFCDTO();
                ubigeoRFCDTO.setClavePais(tablePoblacion.getString("COUNTRY"));
                ubigeoRFCDTO.setDenominacion(tablePoblacion.getString("CITY_NAME"));
                ubigeoRFCDTO.setClaveRegion(tablePoblacion.getString("REGION"));
                ubigeoRFCDTO.setClavePoblacion(tablePoblacion.getString("CITY_CODE"));
                ubigeoRFCDTO.setClaveEquivalenciaSunat(tablePoblacion.getString("CITY_CODE_S"));
                logger.error("UBIGEO PROVINCIA 01: " + ubigeoRFCDTO.toString());

                try {
                    Ubigeo ubigeoEncontrado = this.ubigeoRepository.findByCodigoUbigeoSapAndNivel(
                            ubigeoRFCDTO.getClaveEquivalenciaSunat(),
                            NIVEL_PROVINCIA
                    );
                    if (Optional.ofNullable(ubigeoEncontrado).isPresent()) {
                        contador++;
                        ubigeoEncontrado.setCodigoUbigeoSapErp(ubigeoRFCDTO.getClavePoblacion());
                        ubigeoEncontrado.setDescripcion(ubigeoRFCDTO.getDenominacion());
                        this.ubigeoRepository.save(ubigeoEncontrado);
                    } else {
                        logger.error("UBIGEO ERR PROVINCIA 02: " + ubigeoRFCDTO.toString());
                    }
                }
                catch(Exception ex) {
                    logger.error("UBIGEO ERR PROVINCIA EXCEPTION: " + ubigeoRFCDTO.toString());
                }
                contadorTotal++;
                listaPoblacion.add(ubigeoRFCDTO);
            } while (tablePoblacion.nextRow());
        }
        ubigeoRFCResponseDto.setContadorActualizadoProvincia(contador);
        ubigeoRFCResponseDto.setContadorTotalProvincia(contadorTotal);


        contador = 0;
        contadorTotal=0;
        List<UbigeoRFCDTO> listaDistrito=new ArrayList<>();
        JCoTable tableDistrito = tableParameterList.getTable(NOMBRE_TABLA_RFC_DISTRITO);
        if (tableDistrito != null && !tableDistrito.isEmpty()) {
            do {
                UbigeoRFCDTO ubigeoRFCDTO=new UbigeoRFCDTO();
                ubigeoRFCDTO.setClavePais(tableDistrito.getString("COUNTRY"));
                ubigeoRFCDTO.setDenominacion(tableDistrito.getString("CITY_PART"));
                String distrito = tableDistrito.getString("CITYP_CODE");
                //distrito = distrito.substring(2,8);
                ubigeoRFCDTO.setClaveDistrito(distrito);
                ubigeoRFCDTO.setClavePoblacion(tableDistrito.getString("CITY_CODE"));

                try {
                    Ubigeo ubigeoEncontrado = this.ubigeoRepository.findByCodigoUbigeoSapAndNivel(
                            ubigeoRFCDTO.getClaveDistrito(),
                            NIVEL_DISTRITO
                    );
                    if (Optional.ofNullable(ubigeoEncontrado).isPresent()) {
                        contador++;
                        ubigeoEncontrado.setCodigoUbigeoSapErp(ubigeoRFCDTO.getClaveDistrito());
                        ubigeoEncontrado.setDescripcion(ubigeoRFCDTO.getDenominacion());
                        this.ubigeoRepository.save(ubigeoEncontrado);
                    } else {
                        logger.error("UBIGEO ERR DISTRITO : " + ubigeoRFCDTO.toString());
                    }
                }
                catch(Exception ex) {
                    logger.error("UBIGEO ERR DISTRITO exception: " + ubigeoRFCDTO.toString());
                }

                contadorTotal++;
                listaDistrito.add(ubigeoRFCDTO);
            } while (tableDistrito.nextRow());
        }
        ubigeoRFCResponseDto.setContadorActualizadoDistrito(contador);
        ubigeoRFCResponseDto.setContadorTotalDistrito(contadorTotal);


        ubigeoRFCResponseDto.setListaPaises(listaPaises);
        ubigeoRFCResponseDto.setListaRegion(listaRegion);
        ubigeoRFCResponseDto.setListaDistrito(listaDistrito);
        ubigeoRFCResponseDto.setListaPoblacion(listaPoblacion);
        logger.error("Cantidad Paises" +listaPaises.size() );
        logger.error("Cantidad Regiones" +listaRegion.size() );
        logger.error("Cantidad Distrito" +listaDistrito.size() );
        logger.error("Cantidad Provincia" +listaPoblacion.size() );
        return ubigeoRFCResponseDto;
    }




}
