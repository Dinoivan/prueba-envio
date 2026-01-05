package com.incloud.hcp.jco.balanza.PedidosVentas.service.impl;

import com.incloud.hcp.jco.balanza.PedidosVentas.dto.PedidosVentasParameterBuilder;
import com.incloud.hcp.jco.balanza.PedidosVentas.dto.PedidosVentasResponse;
import com.incloud.hcp.jco.balanza.PedidosVentas.service.JCOPedidoVentasService;
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
public class JCOPedidosVentasServiceImpl implements JCOPedidoVentasService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;
    private final String FUNCION_CONSULTA_RFC = "ZFPE_MM_CONSULTA_PED_VENTAS";
    @Override
    public List<PedidosVentasResponse> consultaPedVentas(String vbapMatnr,String piWerks) throws Exception {
        logger.error("CONSULTAPEDVENTAS - START");
        logger.error("PARAMETRO PI_MATNR: " + vbapMatnr);
        logger.error("PARAMETRO PI_WERKS: " + piWerks);

        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("CONSULTAPEDVENTAS - JCoDestination: " + destination);

        JCoRepository repository = destination.getRepository();
        logger.error("CONSULTAPEDVENTAS - JCoRepository: " + repository);

        JCoFunction jCoFunction = repository.getFunction(FUNCION_CONSULTA_RFC);
        logger.error("CONSULTAPEDVENTAS - JCoFunction: " + jCoFunction);

        logger.error("CONSULTAPEDVENTAS - mapFilter START");
        this.mapFilter(jCoFunction, vbapMatnr,piWerks);
        logger.error("CONSULTAPEDVENTAS - mapFilter END");

        logger.error("CONSULTAPEDVENTAS - jCoFunction.execute START");
        jCoFunction.execute(destination);
        logger.error("CONSULTAPEDVENTAS - jCoFunction.execute END");

        logger.error("CONSULTAPEDVENTAS - JCoParameterList START");
        JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
        logger.error("CONSULTAPEDVENTAS - JCoParameterList END");

        logger.error("CONSULTAPEDVENTAS - pedVentasMapper START");
        PedidosVentasParameterBuilder pedidosVentasParameterBuilder = PedidosVentasParameterBuilder.newMapper(exportParameterList);
        logger.error("CONSULTAPEDVENTAS - pedVentasMapper END");

        logger.error("CONSULTAPEDVENTAS - Llenado de PedidosVentasResponse START");
        List<PedidosVentasResponse> pedidosVentasResponseList = pedidosVentasParameterBuilder.getPedidosVentasList();
        logger.error("CONSULTAPEDVENTAS - pedidosVentasResponseList: " + pedidosVentasResponseList);
        logger.error("CONSULTAPEDVENTAS - Llenado de PedidosVentasResponse END");

        return pedidosVentasResponseList;
    }

    public void mapFilter(JCoFunction jCoFunction, String vbapMatnr, String piWerks){
        logger.error("CONSULTAPEDVENTAS - mapFilter START");
        logger.error("SEGUNDA VALIDACION DE PARAMETRO vbapMatnr: " + vbapMatnr);
        logger.error("SEGUNDA VALIDACION DE PARAMETRO piWerks: " + piWerks);

        JCoParameterList parameterList = jCoFunction.getImportParameterList();
        logger.error("CONSULTAPEDVENTAS - JCoParameterList: " + parameterList);
        parameterList.setValue("PI_MATNR", vbapMatnr);
        parameterList.setValue("PI_WERKS", piWerks);

        logger.error("CONSULTAPEDVENTAS - mapFiltert END");
    }
}
