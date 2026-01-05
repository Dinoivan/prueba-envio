package com.incloud.hcp.jco.balanza.Transporte.service.impl;

import com.incloud.hcp.jco.balanza.Transporte.dto.SapTableTransporteDto;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TransporteExtractorMapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JCoParameterList jCoParameterList;

    public static TransporteExtractorMapper newMapper(JCoParameterList exportParameterList){
        return new TransporteExtractorMapper(exportParameterList);
    }

    public TransporteExtractorMapper(JCoParameterList jCoParameterList) {
        this.jCoParameterList = jCoParameterList;
    }

    public List<SapTableTransporteDto> getSapTableTransporteDtoList(){
        List<SapTableTransporteDto> sapTableTransporteDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("T_TRUCK");

        if(table != null && !table.isEmpty()){
            do {
                SapTableTransporteDto sapTableTransporteDto = new SapTableTransporteDto();
                sapTableTransporteDto.setPlaca(table.getString("TR_PLACA").trim());
                sapTableTransporteDto.setRemolque(table.getString("REMOLQUE").trim());
                sapTableTransporteDto.setTipoVehiculo(table.getString("TR_TYPE").trim());
                sapTableTransporteDto.setMarca(table.getString("TR_MARCA").trim());
                sapTableTransporteDto.setModelo(table.getString("TR_MODEL").trim());
                sapTableTransporteDto.setCiv(table.getString("TR_CIV").trim());
                sapTableTransporteDto.setStatus(table.getString("TR_STATUS").trim());
                sapTableTransporteDto.setNroAutorizacion(table.getString("NROAUTO").trim());
                sapTableTransporteDto.setCodAutorizacion(table.getString("CODAUTO").trim());
                sapTableTransporteDto.setZmtc(table.getString("ZMTC").trim());
                sapTableTransporteDto.setCreatedBy(table.getString("CREATED_BY").trim());
                sapTableTransporteDto.setModifiedBy(table.getString("MODIFIED_BY").trim());
                //sapTableTransporteDto.setFechaRegistro(table.getString("FECHA_REGISTRO").trim());

                sapTableTransporteDtoList.add(sapTableTransporteDto);
            } while (table.nextRow());
        }
        logger.error("001 - sapTableTransporteDtoList: " + sapTableTransporteDtoList);
        return sapTableTransporteDtoList;
    }
}
