package com.incloud.hcp.jco.balanza.Carreta.service.impl;

import com.incloud.hcp.jco.balanza.Carreta.dto.SapTableCarretaDto;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

import java.util.ArrayList;
import java.util.List;

public class CarretaExtractorMapper {
    private JCoParameterList jCoParameterList;
    public static CarretaExtractorMapper newMapper(JCoParameterList exportParameterList) {
        return new CarretaExtractorMapper(exportParameterList);
    }

    public CarretaExtractorMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;}
    public List<SapTableCarretaDto> getSapTableCarretaDtoList(){
        List<SapTableCarretaDto> sapTableCarretaDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_CARRETAS");

        if(table != null && !table.isEmpty()){
            do{
                SapTableCarretaDto sapTableCarretaDto = new SapTableCarretaDto();
                sapTableCarretaDto.setPlaca(table.getString("PLACA").trim());
                sapTableCarretaDto.setModelo(table.getString("MODELO").trim());

                sapTableCarretaDtoList.add(sapTableCarretaDto);
            } while(table.nextRow());
        }
        return sapTableCarretaDtoList;
    }
}
