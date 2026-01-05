package com.incloud.hcp.jco.balanza.CentroAlmacen.service.impl;

import com.incloud.hcp.jco.balanza.CentroAlmacen.dto.SapTableCentroAlmacenBlz;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CentroAlmacenBlzMapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JCoParameterList jCoParameterList;

    public CentroAlmacenBlzMapper(JCoParameterList exportParameterList) {
        this.jCoParameterList = exportParameterList;
    }

    public static CentroAlmacenBlzMapper newMapper(JCoParameterList exportParameterList){
        return new CentroAlmacenBlzMapper(exportParameterList);
    }

    public List<SapTableCentroAlmacenBlz> getSapTableCentroAlmacenBlzList(){
        List<SapTableCentroAlmacenBlz> sapTableCentroAlmacenBlzs = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_CENTRO_ALMACEN");
        logger.error("Extraccion CentroAlmacenBlzMapper 01 table " + table);
        if(table != null && !table.isEmpty()){
            do {
                SapTableCentroAlmacenBlz item = new SapTableCentroAlmacenBlz();
                item.setCentro(table.getString("WERKS"));
                item.setPoblacion(table.getString("ORT01"));
                item.setDistrito(table.getString("CITY2"));
                item.setCodigoAlmacen(table.getString("LGORT"));
                item.setDescripcionAlmacen(table.getString("LGOBE"));
                item.setCenStras(table.getString("CEN_STRAS"));
                item.setRegion(table.getString("REGION"));
                item.setDenominacion(table.getString("BEZEI"));
                item.setDireccion1(table.getString("STRAS"));
                item.setDireccion2(table.getString("STRAS2"));
                item.setDireccion3(table.getString("STRAS3"));
                item.setNombre(table.getString("NAME1"));
                sapTableCentroAlmacenBlzs.add(item);
            } while (table.nextRow());
        }
        logger.error("Extraccion CentroAlmacenBlzMapper 02 size " + sapTableCentroAlmacenBlzs.size());
        return sapTableCentroAlmacenBlzs;
    }
}
