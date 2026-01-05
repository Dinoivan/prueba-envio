package com.incloud.hcp.jco.balanza.PedidosTraslado.dto;

import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PedidosTrasladoMapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TABLE = "TO_PEDIDOS_TRASLADO";
    private JCoParameterList jCoParameterList;

    public PedidosTrasladoMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }
    public static PedidosTrasladoMapper newMapper(JCoParameterList exportParameterList) {
        return new PedidosTrasladoMapper(exportParameterList);
    }
    public List<PedidosTrasladoResponse> getPedidosTrasladoList(){
        logger.error("PEDIDOSTRASLADOMAPPER - START");
        List<PedidosTrasladoResponse> pedidosTrasladoResponseList = new ArrayList<>();
        JCoTable jCoTable = jCoParameterList.getTable(TABLE);
        logger.error("PEDIDOSTRASLADOMAPPER - JCoTable: " + jCoTable);

        if(jCoTable != null && !jCoTable.isEmpty()){
            do {
                PedidosTrasladoResponse pedidosTrasladoResponse = new PedidosTrasladoResponse();
                pedidosTrasladoResponse.setPedido(jCoTable.getString("PEDIDO").trim());
                pedidosTrasladoResponse.setPosicion(jCoTable.getInt("POSICION"));
                pedidosTrasladoResponse.setCantidad(jCoTable.getDouble("CANTIDAD"));
                pedidosTrasladoResponse.setMeins(jCoTable.getString("MEINS"));
                pedidosTrasladoResponse.setCenOri(jCoTable.getString("CEN_ORI"));
                pedidosTrasladoResponse.setAlmOri(jCoTable.getString("ALM_ORI"));
                pedidosTrasladoResponse.setCenDest(jCoTable.getString("CEN_DEST"));
                pedidosTrasladoResponse.setAlmDest(jCoTable.getString("ALM_DEST"));

                logger.error("PEDIDOSTRASLADOMAPPER - pedidosTrasladoResponse: " + pedidosTrasladoResponse);
                pedidosTrasladoResponseList.add(pedidosTrasladoResponse);
            } while (jCoTable.nextRow());
        }
        logger.error("PEDIDOSTRASLADOMAPPER - serieConsultaResponsesList: " + pedidosTrasladoResponseList);
        logger.error("PEDIDOSTRASLADOMAPPER - END");
        return pedidosTrasladoResponseList;
    }
}
