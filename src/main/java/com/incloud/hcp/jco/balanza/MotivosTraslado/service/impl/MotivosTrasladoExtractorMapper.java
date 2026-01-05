package com.incloud.hcp.jco.balanza.MotivosTraslado.service.impl;

import com.incloud.hcp.jco.balanza.Chofer.dto.SapTableChoferDto;
import com.incloud.hcp.jco.balanza.Chofer.service.impl.ChoferExtractorMapper;
import com.incloud.hcp.jco.balanza.MotivosTraslado.dto.*;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

import java.util.ArrayList;
import java.util.List;

public class MotivosTrasladoExtractorMapper {
    private JCoParameterList jCoParameterList;
    public static MotivosTrasladoExtractorMapper newMapper(JCoParameterList exportParameterList){
        return new MotivosTrasladoExtractorMapper(exportParameterList);
    }
    public MotivosTrasladoExtractorMapper(JCoParameterList jCoParameterList){ this.jCoParameterList = jCoParameterList;}
    public List<MotivosTrasladoResponseDTO> getSapTableMotivosTrasladoDtoList(){
        List<MotivosTrasladoResponseDTO> sapTableMotivosTrasladoDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_MOTIVO");

        if(table != null && !table.isEmpty()){
            do {
                MotivosTrasladoResponseDTO sapTableMotivosTrasladoDto = new MotivosTrasladoResponseDTO();
                sapTableMotivosTrasladoDto.setMandt(table.getString("MANDT"));
                sapTableMotivosTrasladoDto.setMotra(table.getString("ZMOTRAS"));
                sapTableMotivosTrasladoDto.setDescripcion(table.getString("DESCRIPCION"));

                sapTableMotivosTrasladoDtoList.add(sapTableMotivosTrasladoDto);
            } while (table.nextRow());
        }
        return sapTableMotivosTrasladoDtoList;
    }
    public List<MotivosOtroResponseDTO> getSapTableMotivosOtroladoDtoList(){
        List<MotivosOtroResponseDTO> sapTableMotivosTrasladoDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_MOTROS");

        if(table != null && !table.isEmpty()){
            do {
                MotivosOtroResponseDTO sapTableMotivosTrasladoDto = new MotivosOtroResponseDTO();
                sapTableMotivosTrasladoDto.setMotro(table.getString("ZMOTROS"));
                sapTableMotivosTrasladoDto.setCorrelativo(table.getString("ZCORRELATIVO"));
                sapTableMotivosTrasladoDto.setDescripcion(table.getString("DESCRIPCION"));

                sapTableMotivosTrasladoDtoList.add(sapTableMotivosTrasladoDto);
            } while (table.nextRow());
        }
        return sapTableMotivosTrasladoDtoList;
    }

    public List<ModoTransporteResponseDTO> getSapTableModoTransporteDtoList(){
        List<ModoTransporteResponseDTO> getSapTableModoTransporteDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_MODTRANSP");

        if(table != null && !table.isEmpty()){
            do {
                ModoTransporteResponseDTO sapTableMotivosTrasladoDto = new ModoTransporteResponseDTO();
                sapTableMotivosTrasladoDto.setCorrelativo(table.getString("ZCORRELATIVO"));
                sapTableMotivosTrasladoDto.setValor(table.getString("ZVALOR"));
                sapTableMotivosTrasladoDto.setDescripcion(table.getString("ZVALORH").replaceAll("TRANSPORTE",""));

                getSapTableModoTransporteDtoList.add(sapTableMotivosTrasladoDto);
            } while (table.nextRow());
        }
        return getSapTableModoTransporteDtoList;
    }

    public List<TipoMovimientoResponseDTO> getSapTipoMovimientoDtoList(){
        List<TipoMovimientoResponseDTO> getSapTableModoTransporteDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_TIPOMOV");

        if(table != null && !table.isEmpty()){
            do {
                TipoMovimientoResponseDTO sapTableMotivosTrasladoDto = new TipoMovimientoResponseDTO();
                sapTableMotivosTrasladoDto.setGrupo(table.getString("ZCGRUPO"));
                sapTableMotivosTrasladoDto.setValor(table.getString("ZVALOR"));
                sapTableMotivosTrasladoDto.setValorh(table.getString("ZVALORH"));

                getSapTableModoTransporteDtoList.add(sapTableMotivosTrasladoDto);
            } while (table.nextRow());
        }
        return getSapTableModoTransporteDtoList;
    }

    public List<UnidadMedidaResponseDTO> getSapUnidadMedidaDtoList(){
        List<UnidadMedidaResponseDTO> getSapTableModoTransporteDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_UM");

        if(table != null && !table.isEmpty()){
            do {
                UnidadMedidaResponseDTO sapTableMotivosTrasladoDto = new UnidadMedidaResponseDTO();
                sapTableMotivosTrasladoDto.setValor(table.getString("MSEH3"));
                sapTableMotivosTrasladoDto.setValorh(table.getString("MSEHT"));

                getSapTableModoTransporteDtoList.add(sapTableMotivosTrasladoDto);
            } while (table.nextRow());
        }
        return getSapTableModoTransporteDtoList;
    }

    public List<IndicadorServicioResponseDTO> getSapIndicadorServicoDtoList(){
        List<IndicadorServicioResponseDTO> getSapTableModoTransporteDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_INDSERV");

        if(table != null && !table.isEmpty()){
            do {
                IndicadorServicioResponseDTO sapTableMotivosTrasladoDto = new IndicadorServicioResponseDTO();
                sapTableMotivosTrasladoDto.setCodigo(table.getString("CODIGO"));
                sapTableMotivosTrasladoDto.setText(table.getString("TEXT"));

                getSapTableModoTransporteDtoList.add(sapTableMotivosTrasladoDto);
            } while (table.nextRow());
        }
        return getSapTableModoTransporteDtoList;
    }

    public List<TipoLocacionDTO> tipoPuertoList(){
        List<TipoLocacionDTO> getSapTableModoTransporteDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_EXPTOLLEG");

        if(table != null && !table.isEmpty()){
            do {
                TipoLocacionDTO sapTableMotivosTrasladoDto = new TipoLocacionDTO();
                sapTableMotivosTrasladoDto.setCodigo(table.getString("CODIGO"));
                sapTableMotivosTrasladoDto.setText(table.getString("TEXT"));

                getSapTableModoTransporteDtoList.add(sapTableMotivosTrasladoDto);
            } while (table.nextRow());
        }
        return getSapTableModoTransporteDtoList;
    }
    public List<TipoLocacionDTO> getTipoLocacionList(){
        List<TipoLocacionDTO> getSapTableModoTransporteDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_TIPOLOC");

        if(table != null && !table.isEmpty()){
            do {
                TipoLocacionDTO sapTableMotivosTrasladoDto = new TipoLocacionDTO();
                sapTableMotivosTrasladoDto.setCodigo(table.getString("CODIGO"));
                sapTableMotivosTrasladoDto.setText(table.getString("TEXT"));

                getSapTableModoTransporteDtoList.add(sapTableMotivosTrasladoDto);
            } while (table.nextRow());
        }
        return getSapTableModoTransporteDtoList;
    }

    public List<PuertoAeropuertoDTO> puertoLlegadaList(){
        List<PuertoAeropuertoDTO> getSapTableModoTransporteDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_PUERTOS");

        if(table != null && !table.isEmpty()){
            do {
                PuertoAeropuertoDTO sapTableMotivosTrasladoDto = new PuertoAeropuertoDTO();
                sapTableMotivosTrasladoDto.setCodigo(table.getString("COD_PUERTO"));
                sapTableMotivosTrasladoDto.setText(table.getString("NOM_PUERTO"));

                getSapTableModoTransporteDtoList.add(sapTableMotivosTrasladoDto);
            } while (table.nextRow());
        }
        return getSapTableModoTransporteDtoList;
    }

    public List<PuertoAeropuertoDTO> AeropuertoLlegadaList(){
        List<PuertoAeropuertoDTO> getSapTableModoTransporteDtoList = new ArrayList<>();
        JCoTable table = jCoParameterList.getTable("TO_AEROPU");

        if(table != null && !table.isEmpty()){
            do {
                PuertoAeropuertoDTO sapTableMotivosTrasladoDto = new PuertoAeropuertoDTO();
                sapTableMotivosTrasladoDto.setCodigo(table.getString("COD_AEROPUERTO"));
                sapTableMotivosTrasladoDto.setText(table.getString("NOM_AEROPUERTO"));

                getSapTableModoTransporteDtoList.add(sapTableMotivosTrasladoDto);
            } while (table.nextRow());
        }
        return getSapTableModoTransporteDtoList;
    }
}
