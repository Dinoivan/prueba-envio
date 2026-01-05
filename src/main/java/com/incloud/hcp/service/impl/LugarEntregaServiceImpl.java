package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.almacen.LugarEntrega;
import com.incloud.hcp.jco.parametro.LugarEntregaExtractorMapper;
import com.incloud.hcp.jco.parametro.dto.SapTableLugarEntrega;
import com.incloud.hcp.repository.LugarEntregaRepository;
import com.incloud.hcp.service.LugarEntregaService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Administrador on 28/08/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class LugarEntregaServiceImpl implements LugarEntregaService {

    @Autowired
    private LugarEntregaRepository lugarEntregaRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${destination.rfc.profit}")
    private String destinationProfit;


    @Override
    public List<LugarEntrega> getLugarEntrega() throws Exception {
        try {
            logger.error("Extraccion LugarEntrega 01");
            String FUNCION_RFC = "ZPE_MM_LISTA_LUGAR_ENTREGA";
            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();
            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            logger.error("Extraccion LugarEntrega 02");
            this.mapFilter(jCoFunction);
            jCoFunction.execute(destination);
            logger.error("Extraccion LugarEntrega 03");
            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            LugarEntregaExtractorMapper lugarEntregaExtractorMapper = LugarEntregaExtractorMapper.newMapper(exportParameterList);
            logger.error("Extraccion LugarEntrega 04");
            List<SapTableLugarEntrega> sapTableCentroAlmacenBlzs = lugarEntregaExtractorMapper.getSapTableLugarEntrega();
            logger.error("Extraccion LugarEntrega 05");
            List<LugarEntrega> LugarEntregaList = new ArrayList<>();
            for(SapTableLugarEntrega item : sapTableCentroAlmacenBlzs){
                LugarEntrega i = new LugarEntrega();
                Optional<LugarEntrega> lEntrega = this.lugarEntregaRepository.findByLugarEntrega(i.getLugarEntrega());
                if(lEntrega.isPresent()){
                    i = lEntrega.get();
                }
                i.setLugarEntrega(item.getLugarEntrega());
                i.setCentro(item.getCentro());
                i.setNumeroCuenta(item.getNumeroCuenta());
                i.setCalle(item.getCalle());
                i.setCalleCuatro(item.getCalleCuatro());
                i.setPoblacion(item.getPoblacion());
                i.setDistrito(item.getDistrito());
                i.setzRegion(item.getzRegion());
                LugarEntregaList.add(i);
                this.lugarEntregaRepository.save(i);
            }
            logger.error("Extraccion LugarEntrega 06 - size" );
            return LugarEntregaList;
        } catch (Exception e){
            logger.error("Error en la extracción: " + e.getMessage());
            throw new Exception(e);
        }
    }

    private void mapFilter(JCoFunction function){
        JCoParameterList parameterList = function.getImportParameterList();
    }
}
