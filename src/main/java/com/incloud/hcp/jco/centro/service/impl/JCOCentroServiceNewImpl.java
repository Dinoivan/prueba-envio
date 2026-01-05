package com.incloud.hcp.jco.centro.service.impl;


import com.incloud.hcp.domain.CentroAlmacen;
import com.incloud.hcp.jco.centro.dto.CentroRFCDto;
import com.incloud.hcp.jco.centro.dto.CentroRFCParameterBuilder;
import com.incloud.hcp.jco.centro.dto.CentroRFCResponseDto;
import com.incloud.hcp.jco.centro.service.JCOCentroServiceNew;
import com.incloud.hcp.repository.CentroAlmacenRepository;
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
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JCOCentroServiceNewImpl implements JCOCentroServiceNew {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;
    private final int NIVEL = 1;
//    private final String FUNCION_RFC = "ZMMRFC_LISTA_CENTRO";
    private final String FUNCION_RFC = "ZPE_MM_LISTA_CENTRO";
    private final String NOMBRE_TABLA_RFC = "TO_CENTRO";

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CentroAlmacenRepository centroAlmacenRepository;

    @Override
    public CentroRFCResponseDto actualizarCentro(String sociedad) throws Exception {

        CentroRFCResponseDto centroRFCResponseDto = new CentroRFCResponseDto();

        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - getCentroAlmacen");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01B - getCentroAlmacen");

        logger.error("parametro ingresado" + sociedad);
        CentroRFCParameterBuilder.build(
                jCoFunction,
                sociedad
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

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 - getCentro - FIN RFC");
        JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
        JCoParameterList result = jCoFunction.getExportParameterList();
        SapLog sapLog = new SapLog();
        String codigoSap = result.getString("PO_CODE");
        String message = result.getString("PO_MSJE");
        sapLog.setCode(codigoSap);
        sapLog.setMesaj(message);
        centroRFCResponseDto.setSapLog(sapLog);
        logger.error("02b - getCentro - sapLog: " + sapLog.toString());

        /* Recorriendo valores obtenidos del RFC */

        List<CentroRFCDto> centroAlmacenRFCDtoList = new ArrayList<CentroRFCDto>();

        JCoTable table = tableParameterList.getTable(NOMBRE_TABLA_RFC);
        if (table != null && !table.isEmpty()) {

            do {
                CentroRFCDto centroAlmacenRFCDto = new CentroRFCDto();
                centroAlmacenRFCDto.setCentro(table.getString("WERKS"));
                centroAlmacenRFCDto.setPoblacion(table.getString("ORT01"));
                centroAlmacenRFCDto.setDistrito(table.getString("CITY2"));
                centroAlmacenRFCDto.setDireccion(table.getString("STRAS"));
                centroAlmacenRFCDto.setDescripcion(table.getString("NAME1"));

                CentroAlmacen centroAlmacen = this.centroAlmacenRepository.findByCodigoSapAndNivel(
                        centroAlmacenRFCDto.getCentro(), 1
                );
                if (!Optional.ofNullable(centroAlmacen).isPresent()) {
                    centroAlmacen = new CentroAlmacen();
                }
                centroAlmacen.setCodigoSap(centroAlmacenRFCDto.getCentro());
                centroAlmacen.setNivel(1);
                centroAlmacen.setDenominacion(centroAlmacenRFCDto.getDescripcion());
                centroAlmacen.setDireccion(centroAlmacenRFCDto.getDireccion());
                centroAlmacen.setPoblacion(centroAlmacenRFCDto.getPoblacion());
                centroAlmacen.setDistrito(centroAlmacenRFCDto.getDistrito());
                this.centroAlmacenRepository.saveAndFlush(centroAlmacen);

                centroAlmacenRFCDtoList.add(centroAlmacenRFCDto);
            } while (table.nextRow());
        }
        centroRFCResponseDto.setListaCentro(centroAlmacenRFCDtoList);
        if (centroAlmacenRFCDtoList != null && centroAlmacenRFCDtoList.size() > 0) {
            centroRFCResponseDto.setContador(centroAlmacenRFCDtoList.size());
        }

        //logger.error("04 - getSolpedResponseByCodigo solicitudPedidoRFCResponseDto: " + grupoArticuloRFCResponseDto.toString());
        return centroRFCResponseDto;
    }
}
