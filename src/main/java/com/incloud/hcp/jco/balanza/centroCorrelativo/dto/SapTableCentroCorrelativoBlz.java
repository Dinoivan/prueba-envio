package com.incloud.hcp.jco.balanza.centroCorrelativo.dto;

import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SapTableCentroCorrelativoBlz {
    private String sociedad;
    private String centro;
    private String correlativo;
    private String serie;
    private String numeroGuiaRemision;
    private String descripcion;

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumeroGuiaRemision() {
        return numeroGuiaRemision;
    }

    public void setNumeroGuiaRemision(String numeroGuiaRemision) {
        this.numeroGuiaRemision = numeroGuiaRemision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static class CentroCorrelativoBlzMapper {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());
        private JCoParameterList jCoParameterList;

        public CentroCorrelativoBlzMapper(JCoParameterList exportParameterList) {
            this.jCoParameterList = exportParameterList;
        }

        public static CentroCorrelativoBlzMapper newMapper(JCoParameterList exportParameterList){
            return new CentroCorrelativoBlzMapper(exportParameterList);
        }

        public List<SapTableCentroCorrelativoBlz> getSapTableCentroCorrelativoBlzList(){
            List<SapTableCentroCorrelativoBlz> sapTableCentroAlmacenBlzs = new ArrayList<>();
            JCoTable table = jCoParameterList.getTable("TO_SERIE");
            logger.error("Extraccion TO_SERIE 01 table " + table);
            if(table != null && !table.isEmpty()){
                do {
                    SapTableCentroCorrelativoBlz item = new SapTableCentroCorrelativoBlz();
                    item.setSociedad(table.getString("BUKRS"));
                    item.setCentro(table.getString("WERKS"));
                    item.setCorrelativo(table.getString("ZCORRELATIVO"));
                    item.setSerie(table.getString("ZSERIE"));
                    item.setDescripcion(table.getString("ZDESCR"));
                    item.setNumeroGuiaRemision(table.getString("ULTIMO_ZGUIAR"));

                    sapTableCentroAlmacenBlzs.add(item);
                } while (table.nextRow());
            }
            logger.error("Extraccion CentroAlmacenBlzMapper 02 size " + sapTableCentroAlmacenBlzs.size());
            return sapTableCentroAlmacenBlzs;
        }
    }
}

