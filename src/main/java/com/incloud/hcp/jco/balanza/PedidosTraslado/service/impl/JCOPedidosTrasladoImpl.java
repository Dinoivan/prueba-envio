package com.incloud.hcp.jco.balanza.PedidosTraslado.service.impl;

import com.incloud.hcp.jco.balanza.PedidosTraslado.dto.PedidoTrasladoInput;
import com.incloud.hcp.jco.balanza.PedidosTraslado.dto.PedidosTrasladoMapper;
import com.incloud.hcp.jco.balanza.PedidosTraslado.dto.PedidosTrasladoResponse;
import com.incloud.hcp.jco.balanza.PedidosTraslado.service.JCOPedidosTrasladoService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOPedidosTrasladoImpl implements JCOPedidosTrasladoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;
    private final String FUNCION_CONSULTA_RFC = "ZFPE_MM_CONSULTA_PED_TRASLADO";
    @Override
    public List<PedidosTrasladoResponse> consultaPedTraslado(PedidoTrasladoInput pedidoTrasladoInput) throws Exception {
        logger.error("CONSULTAPEDTRASLADO - START");
        String ekpoMatnr = pedidoTrasladoInput.getEkpoMatnr();
        logger.error("PARAMETRO PI_MATNR: " + ekpoMatnr);
        String piReswk = pedidoTrasladoInput.getPiReswk();
        logger.error("PARAMETRO PI_RESWK: " + piReswk);
        String flagTraslado = pedidoTrasladoInput.getFlagTraslado();
        logger.error("PARAMETRO FLAG_TRASLADO: " + flagTraslado);

        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("CONSULTAPEDTRASLADO - JCoDestination: " + destination);

        JCoRepository repository = destination.getRepository();
        logger.error("CONSULTAPEDTRASLADO - JCoRepository: " + repository);

        JCoFunction jCoFunction = repository.getFunction(FUNCION_CONSULTA_RFC);
        logger.error("CONSULTAPEDTRASLADO - JCoFunction: " + jCoFunction);

        logger.error("CONSULTAPEDTRASLADO - mapFilter START");
        this.mapFilter(jCoFunction, ekpoMatnr, piReswk, flagTraslado);
        logger.error("CONSULTAPEDTRASLADO - mapFilter END");

        logger.error("CONSULTAPEDTRASLADO - jCoFunction.execute START");
        jCoFunction.execute(destination);
        logger.error("CONSULTAPEDTRASLADO - jCoFunction.execute END");

        logger.error("CONSULTAPEDTRASLADO - JCoParameterList START");
        JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
        logger.error("CONSULTAPEDTRASLADO - JCoParameterList END");

        logger.error("CONSULTAPEDTRASLADO - pedTrasladoMapper START");
        PedidosTrasladoMapper pedidosTrasladoMapper = PedidosTrasladoMapper.newMapper(exportParameterList);
        logger.error("CONSULTAPEDTRASLADO - pedTrasladoMapper END");

        logger.error("CONSULTAPEDTRASLADO - Llenado de PedidosTrasladoResponse START");
        List<PedidosTrasladoResponse> pedidosTrasladoResponseList = pedidosTrasladoMapper.getPedidosTrasladoList();
        logger.error("CONSULTAPEDTRASLADO - pedidosTrasladoResponseList: " + pedidosTrasladoResponseList);
        logger.error("CONSULTAPEDTRASLADO - Llenado de PedidosTrasladoResponse END");
        return pedidosTrasladoResponseList;
    }
    public void mapFilter(JCoFunction jCoFunction, String ekpoMatnr, String piReswk, String flagTraslado){
        logger.error("CONSULTAPEDTRASLADO - mapFilter START");
        logger.error("SEGUNDA VALIDACION DE PARAMETRO ekpoMatnr: " + ekpoMatnr);
        logger.error("SEGUNDA VALIDACION DE PARAMETRO piReswk: " + piReswk);
        logger.error("SEGUNDA VALIDACION DE PARAMETRO flagTraslado: " + flagTraslado);

        JCoParameterList parameterList = jCoFunction.getImportParameterList();
        logger.error("CONSULTAPEDTRASLADO - JCoParameterList: " + parameterList);
        parameterList.setValue("PI_MATNR", ekpoMatnr);
        parameterList.setValue("PI_RESWK", piReswk);
        parameterList.setValue("PI_INOUT", flagTraslado);

        logger.error("CONSULTAPEDTRASLADO - mapFiltert END");
    }
}
