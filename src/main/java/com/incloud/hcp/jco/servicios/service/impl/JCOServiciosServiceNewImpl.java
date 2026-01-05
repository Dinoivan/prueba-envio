package com.incloud.hcp.jco.servicios.service.impl;

import com.incloud.hcp.domain.TempBienServicio;
import com.incloud.hcp.jco.servicios.dto.ServiciosRFCParameterBuilder;
import com.incloud.hcp.jco.servicios.dto.ServiciosRFCResponseDto;
import com.incloud.hcp.jco.servicios.service.JCOServiciosServiceNew;
import com.incloud.hcp.repository.TempBienServicioRepository;
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
public class JCOServiciosServiceNewImpl implements JCOServiciosServiceNew {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;

//    private final String FUNCION_RFC = "ZMMRFC_LISTA_SERVICIOS";
    private final String FUNCION_RFC = "ZPE_MM_LISTA_SERVICIOS";
    private final String NOMBRE_TABLA_RFC = "TO_SERVICIO";

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TempBienServicioRepository tempBienServicioRepository;

    @Override
    public ServiciosRFCResponseDto getListServicios(String fechaInicio,String fechaFin) throws Exception {
        ServiciosRFCResponseDto serviciosRFCResponseDto = new ServiciosRFCResponseDto();

        logger.error("delete");
        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - getListaServicios");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01tB - getListaServicios");

        logger.error("parametro ingresado");
        ServiciosRFCParameterBuilder.build(
                jCoFunction,
                fechaInicio,
                fechaFin
        );
        logger.error("01C - getListaServicios");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - getListaServicios - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 - getListaServicios - FIN RFC");
        JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
        JCoParameterList result = jCoFunction.getExportParameterList();
        SapLog sapLog = new SapLog();
        String codigoSap = result.getString("PO_CODE");
        String message = result.getString("PO_MSJE");
        sapLog.setCode(codigoSap);
        sapLog.setMesaj(message);
        serviciosRFCResponseDto.setSapLog(sapLog);
        logger.error("02b - getListaServicios - sapLog: " + sapLog.toString());

        /* Recorriendo valores obtenidos del RFC */

        List<TempBienServicio> listTempBienServicio = new ArrayList<TempBienServicio>();

        JCoTable table = tableParameterList.getTable(NOMBRE_TABLA_RFC);
        if (table != null && !table.isEmpty()) {

            do {
                //logger.error("A - getDevuelveValores TABLE JCO: " + table.toString());
                TempBienServicio tempBienServicio = new TempBienServicio();
                tempBienServicio.setCodigoSap(table.getString("ASNUM"));
                tempBienServicio.setDescripcion(table.getString("ASKTX"));
                tempBienServicio.setDescripcionLarga(table.getString("ASKTX"));
                tempBienServicio.setTipoItem("S");
                tempBienServicio.setCodigoRubroSap(table.getString("MATKL"));
                tempBienServicio.setCodigoUnidadMedidaSap(table.getString("MEINS"));


                listTempBienServicio.add(tempBienServicio);
                //logger.error("bean" + tempRubroBien);
            } while (table.nextRow());
        }

        this.tempBienServicioRepository.deleteAll();
        this.tempBienServicioRepository.saveAll(listTempBienServicio);
        serviciosRFCResponseDto.setListaBienServicio(listTempBienServicio);
        if (listTempBienServicio != null && listTempBienServicio.size() > 0) {
            serviciosRFCResponseDto.setContador(listTempBienServicio.size());
        }
        //logger.error("Lista Servicios" +listTempBienServicio.toString() );

        return serviciosRFCResponseDto;
    }
}
