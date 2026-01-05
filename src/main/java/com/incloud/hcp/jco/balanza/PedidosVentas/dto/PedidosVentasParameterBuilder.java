package com.incloud.hcp.jco.balanza.PedidosVentas.dto;

import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
public class PedidosVentasParameterBuilder {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TABLE = "TO_PEDIDOS_VENTA";
    private JCoParameterList jCoParameterList;

    public PedidosVentasParameterBuilder(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public static PedidosVentasParameterBuilder newMapper(JCoParameterList exportParameterList){
        return new PedidosVentasParameterBuilder(exportParameterList);
    }

    public List<PedidosVentasResponse> getPedidosVentasList(){
        logger.error("PEDIDOSVENTASMAPPER - START");
        List<PedidosVentasResponse> pedidosVentasResponseList = new ArrayList<>();
        JCoTable jCoTable = jCoParameterList.getTable(TABLE);
        logger.error("PEDIDOSVENTASMAPPER - JCoTable: " + jCoTable);

        if(jCoTable != null && !jCoTable.isEmpty()){
            do {
                PedidosVentasResponse pedidosVentasResponse = new PedidosVentasResponse();
                pedidosVentasResponse.setPedido(jCoTable.getString("PEDIDO").trim());
                pedidosVentasResponse.setPosicion(jCoTable.getInt("POSICION"));
                pedidosVentasResponse.setCantidad(jCoTable.getDouble("CANTIDAD"));
                pedidosVentasResponse.setLote(jCoTable.getString("LOTE"));
                pedidosVentasResponse.setAlmacen(jCoTable.getString("LGORT"));
                pedidosVentasResponseList.add(pedidosVentasResponse);
            } while (jCoTable.nextRow());
        }
        logger.error("PEDIDOSVENTASMAPPER - serieConsultaResponsesList: " + pedidosVentasResponseList);
        logger.error("PEDIDOSVENTASMAPPER - END");
        return pedidosVentasResponseList;
    }
}
