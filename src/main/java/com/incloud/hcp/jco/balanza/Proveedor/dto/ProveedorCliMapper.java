package com.incloud.hcp.jco.balanza.Proveedor.dto;

import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProveedorCliMapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TABLE = "TO_DMPROV";
    private JCoParameterList jCoParameterList;

    public ProveedorCliMapper(JCoParameterList jCoParameterList){
        this.jCoParameterList = jCoParameterList;
    }

    public static ProveedorCliMapper newMapper(JCoParameterList exportParameterList){
        return new ProveedorCliMapper(exportParameterList);
    }
    public List<ProveedorResponseDTO> getProveedorCliList(){
        logger.error("ProveedorCliMapper - START");
        List<ProveedorResponseDTO> proveedorResponseList = new ArrayList<>();
        JCoTable jCoTable = jCoParameterList.getTable(TABLE);
        logger.error("ProveedorCliMapper - JCoTable: " + jCoTable);
        if(jCoTable != null && !jCoTable.isEmpty()){
            do {
                ProveedorResponseDTO proveedorResponseDTO = new ProveedorResponseDTO();
                proveedorResponseDTO.setDireccion(jCoTable.getString("DIRECCION").trim());
                proveedorResponseDTO.setAcreedor(jCoTable.getString("ACREEDOR").trim());
                proveedorResponseDTO.setRazonSocial(jCoTable.getString("RAZON_SOCIAL").trim());
                proveedorResponseDTO.setRuc(jCoTable.getString("RUC").trim());
                proveedorResponseDTO.setGrupoCuentas(jCoTable.getString("GRUPO_CUENTAS").trim());
                proveedorResponseDTO.setRegion(jCoTable.getString("REGION").trim());
                proveedorResponseDTO.setBezei(jCoTable.getString("BEZEI").trim());
                proveedorResponseDTO.setCity1(jCoTable.getString("CITY1").trim());
                proveedorResponseDTO.setCity2(jCoTable.getString("CITY2").trim());
                logger.error("ProveedorCliMapper - proveedorResponseDTO: " + proveedorResponseDTO);
                proveedorResponseList.add(proveedorResponseDTO);
            } while (jCoTable.nextRow());
        }
        logger.error("ProveedorCliMapper - proveedorResponseList: " + proveedorResponseList);
        logger.error("ProveedorCliMapper - END");
        return proveedorResponseList;
    }

}
