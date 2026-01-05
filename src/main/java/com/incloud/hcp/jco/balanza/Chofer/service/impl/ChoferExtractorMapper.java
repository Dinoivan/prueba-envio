package com.incloud.hcp.jco.balanza.Chofer.service.impl;

import com.incloud.hcp.jco.balanza.Chofer.dto.SapTableChoferDto;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

import java.util.ArrayList;
import java.util.List;
public class ChoferExtractorMapper {
    private JCoParameterList jCoParameterList;

    public static ChoferExtractorMapper newMapper(JCoParameterList exportParameterList){
        return new ChoferExtractorMapper(exportParameterList);
    }

    public ChoferExtractorMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public List<SapTableChoferDto> getSapTableChoferDtoList(){
        List<SapTableChoferDto> sapTableChoferDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("T_DRIVERS");

        if(table != null && !table.isEmpty()){
            do {
                SapTableChoferDto sapTableChoferDto = new SapTableChoferDto();
                sapTableChoferDto.setDni(table.getString("DR_DNI").trim());
                sapTableChoferDto.setLicencia(table.getString("DR_LICENSE").trim());
                sapTableChoferDto.setTipoDocumento(table.getString("TIPODOCID").trim());
                sapTableChoferDto.setApellidoPaterno(table.getString("APEPATERNO").trim());
                sapTableChoferDto.setApellidoMaterno(table.getString("APEMATERNO").trim());
                sapTableChoferDto.setNombre(table.getString("DR_NAME").trim());
                sapTableChoferDto.setStatus(table.getString("DR_STATUS").trim());
                sapTableChoferDto.setCorrelativo(table.getString("CORRELATIVO").trim());

                sapTableChoferDtoList.add(sapTableChoferDto);
            } while (table.nextRow());
        }
        return sapTableChoferDtoList;
    }
}
