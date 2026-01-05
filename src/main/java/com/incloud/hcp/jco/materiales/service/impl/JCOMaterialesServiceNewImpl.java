package com.incloud.hcp.jco.materiales.service.impl;

import com.incloud.hcp.domain.TempBienServicio;
import com.incloud.hcp.jco.materiales.dto.MaterialesRFCParameterBuilder;
import com.incloud.hcp.jco.materiales.dto.MaterialesRFCResponseDto;
import com.incloud.hcp.jco.materiales.service.JCOMaterialesServiceNew;
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
public class JCOMaterialesServiceNewImpl implements JCOMaterialesServiceNew {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;
    private final int NIVEL = 1;
//    private final String FUNCION_RFC = "ZMMRFC_CONSULTA_MATERIALES";
    private final String FUNCION_RFC = "ZPE_MM_CONSULTA_MATERIALES";
    private final String NOMBRE_TABLA_RFC = "TO_MATNR";

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TempBienServicioRepository tempBienServicioRepository;

    @Override
    public MaterialesRFCResponseDto getListMaterialesRFC(String fechaInicio, String fechaFin) throws Exception {
        MaterialesRFCResponseDto materialesRFCResponseDto = new MaterialesRFCResponseDto();

        //logger.error("delete");
        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        //logger.error("01A - getListaMateriales");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        //logger.error("01tB - GetMateriales02");

        //logger.error("parametro ingresado");
        MaterialesRFCParameterBuilder.build(
                jCoFunction,
                fechaInicio,
                fechaFin
        );
        //logger.error("01C - GetMateriales02");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - GetMateriales02 - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        /* Obteniendo los valores obtenidos del RFC */
        //logger.error("02 - GetMateriales02 - FIN RFC");
        JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
        JCoParameterList result = jCoFunction.getExportParameterList();
        SapLog sapLog = new SapLog();
        String codigoSap = result.getString("PO_CODE");
        String message = result.getString("PO_MSJE");
        sapLog.setCode(codigoSap);
        sapLog.setMesaj(message);
        materialesRFCResponseDto.setSapLog(sapLog);
        //logger.error("02b - getGrupoArticulo - sapLog: " + sapLog.toString());

        /* Recorriendo valores obtenidos del RFC */

        List<TempBienServicio> listTempBienServicio = new ArrayList<TempBienServicio>();
        JCoTable table = tableParameterList.getTable(NOMBRE_TABLA_RFC);
        if (table != null && !table.isEmpty()) {

            do {
                //logger.error("A - getDevuelveValores TABLE JCO: " + table.toString());
                TempBienServicio tempBienServicio = new TempBienServicio();
                tempBienServicio.setCodigoSap(table.getString("MATNR"));
                tempBienServicio.setDescripcion(table.getString("MAKTX"));
                tempBienServicio.setDescripcionLarga(table.getString("MAKTX"));
                tempBienServicio.setTipoItem("M");
                tempBienServicio.setCodigoRubroSap(table.getString("MATKL"));
                tempBienServicio.setCodigoUnidadMedidaSap(table.getString("MEINS"));
                listTempBienServicio.add(tempBienServicio);
                //logger.error("bean" + tempRubroBien);
            } while (table.nextRow());
        }

        //logger.error("02c - getGrupoArticulo - sapLog: " + sapLog.toString());
        this.tempBienServicioRepository.deleteAll();
        //logger.error("02d - getGrupoArticulo - sapLog: " + sapLog.toString());
        this.tempBienServicioRepository.saveAll(listTempBienServicio);
        //logger.error("02e - getGrupoArticulo - sapLog: " + sapLog.toString());
        materialesRFCResponseDto.setListaBienServicio(listTempBienServicio);
        //logger.error("02f - getGrupoArticulo - sapLog: " + sapLog.toString());
        if (listTempBienServicio != null & listTempBienServicio.size() > 0) {
            //logger.error("Lista Materiales listTempBienServicio " +listTempBienServicio.size() );
            materialesRFCResponseDto.setContador(listTempBienServicio.size());
        }

        //logger.error("Lista Materiales" +listTempBienServicio.toString() );
        //logger.error("02f - getGrupoArticulo - FIN");
        return materialesRFCResponseDto;
    }
}
