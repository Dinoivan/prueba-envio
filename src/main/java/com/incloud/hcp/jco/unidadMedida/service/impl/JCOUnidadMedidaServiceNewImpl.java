package com.incloud.hcp.jco.unidadMedida.service.impl;

import com.incloud.hcp.domain.UnidadMedida;
import com.incloud.hcp.jco.unidadMedida.dto.UnidadMedidaRFCDTO;
import com.incloud.hcp.jco.unidadMedida.dto.UnidadMedidaRFCParameterBuilder;
import com.incloud.hcp.jco.unidadMedida.dto.UnidadMedidaRFCResponseDto;
import com.incloud.hcp.jco.unidadMedida.service.JCOUnidadMedidaServiceNew;
import com.incloud.hcp.repository.UnidadMedidaRepository;
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
public class JCOUnidadMedidaServiceNewImpl implements JCOUnidadMedidaServiceNew {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;

//    private final String FUNCION_RFC = "ZMMRFC_LISTA_UM";
    private final String FUNCION_RFC = "ZPE_MM_LISTA_UM";
    private final String NOMBRE_TABLA_RFC = "TO_LIST_UM";

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;



    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Override
    public UnidadMedidaRFCResponseDto actualizarUnidadMedida() throws Exception {
        UnidadMedidaRFCResponseDto unidadMedidaRFCResponseDto = new UnidadMedidaRFCResponseDto();


        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - getUnidadMedida");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01tB - getUnidadMedida");

        logger.error("parametro ingresado");
        UnidadMedidaRFCParameterBuilder.build(
                jCoFunction

        );
        logger.error("01C - getUnidadMedida");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - getUnidadMedida - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 - getUnidadMedida - FIN RFC");
        JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
        JCoParameterList result = jCoFunction.getExportParameterList();
        SapLog sapLog = new SapLog();
        String codigoSap = result.getString("PO_CODE");
        String message = result.getString("PO_MSJE");
        sapLog.setCode(codigoSap);
        sapLog.setMesaj(message);
        unidadMedidaRFCResponseDto.setSapLog(sapLog);
        logger.error("02b - getListaServicios - sapLog: " + sapLog.toString());

        /* Recorriendo valores obtenidos del RFC */

        List<UnidadMedidaRFCDTO> listaUnidadMedida = new ArrayList<UnidadMedidaRFCDTO>();
        //lista de monedas actual
        //List<Moneda> listMoneda = this.monedaRepository.findAll();

        JCoTable table = tableParameterList.getTable(NOMBRE_TABLA_RFC);
        if (table != null && !table.isEmpty()) {

            do {


                UnidadMedidaRFCDTO unidadMedidaRFC = new UnidadMedidaRFCDTO();
                unidadMedidaRFC.setCodigo(table.getString("MSEHI"));
                unidadMedidaRFC.setDescripcion(table.getString("MSEHL"));

                UnidadMedida unidadMedidaEncontrada=unidadMedidaRepository.getByCodigoSap(unidadMedidaRFC.getCodigo());
                if (!Optional.ofNullable(unidadMedidaEncontrada).isPresent()) {
                    UnidadMedida unidadMedidanew = new UnidadMedida();
                    unidadMedidanew.setCodigoSap(unidadMedidaRFC.getCodigo());
                    unidadMedidanew.setDescripcion(unidadMedidaRFC.getDescripcion());
                    unidadMedidanew.setTextoUm(unidadMedidaRFC.getCodigo());
                    this.unidadMedidaRepository.save(unidadMedidanew);
                }else{
                    unidadMedidaEncontrada.setTextoUm(unidadMedidaRFC.getCodigo());
                    unidadMedidaEncontrada.setDescripcion(unidadMedidaRFC.getDescripcion());
                    unidadMedidaEncontrada.setCodigoSap(unidadMedidaRFC.getCodigo());
                    this.unidadMedidaRepository.save(unidadMedidaEncontrada);
                }

                listaUnidadMedida.add(unidadMedidaRFC);


                //logger.error("bean" + tempRubroBien);
            } while (table.nextRow());
        }


        unidadMedidaRFCResponseDto.setListaUnidadMedida(listaUnidadMedida);
        logger.error("Lista Unidad Medida" +listaUnidadMedida.toString() );
        return unidadMedidaRFCResponseDto;
    }
}
