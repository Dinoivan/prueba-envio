package com.incloud.hcp.jco.balanza.Transportista.dto;

import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TransportistaMapper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TABLE = "TO_DMPROV";
    private JCoParameterList jCoParameterList;

    public TransportistaMapper(JCoParameterList jCoParameterList){
        this.jCoParameterList = jCoParameterList;
    }

    public static TransportistaMapper newMapper(JCoParameterList exportParameterList){
        return new TransportistaMapper(exportParameterList);
    }
    public List<TransportistaResponseDTO> getTransportistaList(){
        logger.error("TransportistaMapper - START");
        List<TransportistaResponseDTO> transportistaResponseList = new ArrayList<>();
        JCoTable jCoTable = jCoParameterList.getTable(TABLE);
        logger.error("TransportistaMapper - JCoTable: " + jCoTable);
        if(jCoTable != null && !jCoTable.isEmpty()){
            do {
                TransportistaResponseDTO transportistaResponseDTO = new TransportistaResponseDTO();
                transportistaResponseDTO.setDireccion(jCoTable.getString("DIRECCION").trim());
                transportistaResponseDTO.setRazonSocial(jCoTable.getString("RAZON_SOCIAL").trim());
                transportistaResponseDTO.setRuc(jCoTable.getString("RUC").trim());
                transportistaResponseDTO.setAcreedor(jCoTable.getString("ACREEDOR").trim());
                transportistaResponseDTO.setGrupoCuentas(jCoTable.getString("GRUPO_CUENTAS").trim());
                logger.error("ProveedorCliMapper - transportistaResponseDTO: " + transportistaResponseDTO);
                transportistaResponseList.add(transportistaResponseDTO);
            } while (jCoTable.nextRow());
        }
        logger.error("ProveedorCliMapper - transportistaResponseList: " + transportistaResponseList);
        logger.error("ProveedorCliMapper - END");
        return transportistaResponseList;
    }

}
