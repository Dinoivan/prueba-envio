package com.incloud.hcp.jco.grupoArticulo.service.impl;

import com.incloud.hcp.domain.TempRubroBien;
import com.incloud.hcp.jco.grupoArticulo.dto.GrupoArticuloRFCParameterBuilder;
import com.incloud.hcp.jco.grupoArticulo.dto.GrupoArticuloRFCResponseDto;
import com.incloud.hcp.jco.grupoArticulo.service.JCOGrupoArticuloServiceNew;
import com.incloud.hcp.repository.TempRubroBienRepository;
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
public class JCOGrupoArticuloServiceNewImpl implements JCOGrupoArticuloServiceNew {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;
    private final int NIVEL = 1;
//    private final String FUNCION_RFC = "ZMMRFC_CONSULTA_GR_ARTICULOS";
    private final String FUNCION_RFC = "ZPE_MM_CONSULTA_GR_ARTICULOS";
    private final String NOMBRE_TABLA_RFC = "TO_GR_ARTD";

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TempRubroBienRepository tempRubroBienRepository;


    @Override
    public GrupoArticuloRFCResponseDto getGrupoArticulo(String codigo) throws Exception {

        GrupoArticuloRFCResponseDto grupoArticuloRFCResponseDto = new GrupoArticuloRFCResponseDto();
        tempRubroBienRepository.deleteALLL();
        logger.error("delete");
        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - getGrupoArticulo");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01B - getGrupoArticulo");

        logger.error("parametro ingresado" + codigo);
        GrupoArticuloRFCParameterBuilder.build(
                jCoFunction,
                codigo
        );
        logger.error("01C - getGrupoArticulo");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - getGrupoArticulo - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 - getGrupoArticulo - FIN RFC");
        JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
        JCoParameterList result = jCoFunction.getExportParameterList();
        SapLog sapLog = new SapLog();
        String codigoSap = result.getString("PO_CODE");
        String message = result.getString("PO_MSJE");
        sapLog.setCode(codigoSap);
        sapLog.setMesaj(message);
        grupoArticuloRFCResponseDto.setSapLog(sapLog);
        logger.error("02b - getGrupoArticulo - sapLog: " + sapLog.toString());

        /* Recorriendo valores obtenidos del RFC */

        List<TempRubroBien> rubroBienList = new ArrayList<TempRubroBien>();

        JCoTable table = tableParameterList.getTable(NOMBRE_TABLA_RFC);
        if (table != null && !table.isEmpty()) {

            do {
                //logger.error("A - getDevuelveValores TABLE JCO: " + table.toString());
                TempRubroBien tempRubroBien = new TempRubroBien();
                tempRubroBien.setCodigoSap(table.getString("MATKL"));
                tempRubroBien.setDescripcion(table.getString("WGBEZ"));
                tempRubroBien.setNivel(NIVEL);
                rubroBienList.add(tempRubroBien);
                //logger.error("bean" + tempRubroBien);
            } while (table.nextRow());
        }

        List<TempRubroBien> rubroBienListFinal = tempRubroBienRepository.saveAll(rubroBienList);
        logger.error("Lista Final " + rubroBienListFinal.toString());
        grupoArticuloRFCResponseDto.setListaGrupoArticulo(rubroBienList);

        //logger.error("04 - getSolpedResponseByCodigo solicitudPedidoRFCResponseDto: " + grupoArticuloRFCResponseDto.toString());
        return grupoArticuloRFCResponseDto;
    }
}
