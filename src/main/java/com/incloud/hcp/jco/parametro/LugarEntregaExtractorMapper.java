package com.incloud.hcp.jco.parametro;



import com.incloud.hcp.jco.parametro.dto.SapTableLugarEntrega;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LugarEntregaExtractorMapper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JCoParameterList jCoParameterList;

    public LugarEntregaExtractorMapper(JCoParameterList exportParameterList) {
        this.jCoParameterList = exportParameterList;
    }

    public static LugarEntregaExtractorMapper newMapper(JCoParameterList exportParameterList){
        return new LugarEntregaExtractorMapper(exportParameterList);
    }

    public List<SapTableLugarEntrega> getSapTableLugarEntrega(){
        List<SapTableLugarEntrega> sapTableParametroLugarEntrega= new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("IT_LISTA");
        logger.error("Extraccion LugarEntrega 01 table " + table);
        if(table != null && !table.isEmpty()){
            do {
                SapTableLugarEntrega item = new SapTableLugarEntrega();
                item.setLugarEntrega(table.getString("UNSEZ"));
                item.setCentro(table.getString("WERKS"));
                item.setNumeroCuenta(table.getString("LIFNR"));
                item.setCalle(table.getString("STREET"));
                item.setCalleCuatro(table.getString("STR_SUPPL3"));
                item.setPoblacion(table.getString("CITY1"));
                item.setDistrito(table.getString("CITY2"));
                item.setzRegion(table.getString("REGIO"));
                sapTableParametroLugarEntrega.add(item);
            } while (table.nextRow());
        }
        logger.error("Extraccion LugarEntrega 02 size " + sapTableParametroLugarEntrega.size());
        return sapTableParametroLugarEntrega;
    }
}
