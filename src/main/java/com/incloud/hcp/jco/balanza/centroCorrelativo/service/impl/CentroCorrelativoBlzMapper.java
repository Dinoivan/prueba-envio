package com.incloud.hcp.jco.balanza.centroCorrelativo.service.impl;

import com.incloud.hcp.jco.balanza.centroCorrelativo.dto.SapTableCentroCorrelativoBlz;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CentroCorrelativoBlzMapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JCoParameterList jCoParameterList;

    public CentroCorrelativoBlzMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public static CentroCorrelativoBlzMapper newMapper(JCoParameterList exportParameterList){
        return new CentroCorrelativoBlzMapper(exportParameterList);
    }



    public List<SapTableCentroCorrelativoBlz> getSapTableCentroAlmacenBlzList(){
        List<SapTableCentroCorrelativoBlz> sapTableCentroAlmacenBlzs = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_CENTRO_ALMACEN");
        logger.error("Extraccion CentroCorrelativoBlzMapper 01 table " + table);
        if(table != null && !table.isEmpty()){
            do {
                SapTableCentroCorrelativoBlz item = new SapTableCentroCorrelativoBlz();
                item.setCentro(table.getString("BUKRS"));
                item.setSerie(table.getString("WERKS"));
                item.setCorrelativo(table.getString("ZCORRELATIVO"));
                item.setSerie(table.getString("ZSERIE"));
                item.setDescripcion(table.getString("ZDESCR"));
                item.setNumeroGuiaRemision(table.getString("ULTIMO_ZGUIAR"));
                sapTableCentroAlmacenBlzs.add(item);
            } while (table.nextRow());
        }
        logger.error("Extraccion CentroCorrelativoBlzMapper 02 size " + sapTableCentroAlmacenBlzs.size());
        return sapTableCentroAlmacenBlzs;
    }
}
