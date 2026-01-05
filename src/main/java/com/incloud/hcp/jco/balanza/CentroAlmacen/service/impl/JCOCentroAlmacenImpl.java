package com.incloud.hcp.jco.balanza.CentroAlmacen.service.impl;

import com.incloud.hcp.domain.balanza.CentroAlmacenBlz;
import com.incloud.hcp.jco.balanza.CentroAlmacen.dto.SapTableCentroAlmacenBlz;
import com.incloud.hcp.jco.balanza.CentroAlmacen.service.JCOCentroAlmacenBlzService;
import com.incloud.hcp.repository.CentroAlmacenBlzRepository;
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

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOCentroAlmacenImpl implements JCOCentroAlmacenBlzService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private CentroAlmacenBlzRepository centroAlmacenBlzRepository;

    @Override
    public void extraerCentroAlmacenBlz() throws Exception {
        try {
            logger.error("Extraccion CentroAlmacenBlz 01");
            String FUNCION_RFC = "ZFPE_MM_LISTA_CENTRO_ALMACEN";
            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();
            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            logger.error("Extraccion CentroAlmacenBlz 02");
            this.mapFilter(jCoFunction);
            jCoFunction.execute(destination);
            logger.error("Extraccion CentroAlmacenBlz 03");
            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            CentroAlmacenBlzMapper centroAlmacenBlzMapper = CentroAlmacenBlzMapper.newMapper(exportParameterList);
            logger.error("Extraccion CentroAlmacenBlz 04");
            List<SapTableCentroAlmacenBlz> sapTableCentroAlmacenBlzs = centroAlmacenBlzMapper.getSapTableCentroAlmacenBlzList();
            logger.error("Extraccion CentroAlmacenBlz 05");
            List<CentroAlmacenBlz> centroAlmacenBlzs = new ArrayList<>();
            for(SapTableCentroAlmacenBlz item : sapTableCentroAlmacenBlzs){
                CentroAlmacenBlz i = new CentroAlmacenBlz();
                i.setCentro(item.getCentro());
                i.setPoblacion(item.getPoblacion());
                i.setDistrito(item.getDistrito());
                i.setCodigoAlmacen(item.getCodigoAlmacen());
                i.setDescripcionAlmacen(item.getDescripcionAlmacen());
                i.setDireccionCentro(item.getCenStras());
                i.setRegion(item.getRegion());
                i.setDenominacion(item.getDenominacion());
                i.setDireccion1(item.getDireccion1());
                i.setDireccion2(item.getDireccion2());
                i.setDireccion3(item.getDireccion3());
                i.setNombre(item.getNombre());
                centroAlmacenBlzs.add(i);
            }
            logger.error("Extraccion CentroAlmacenBlz 06 - size" + centroAlmacenBlzs.size());
            this.centroAlmacenBlzRepository.deleteAll();
            this.centroAlmacenBlzRepository.saveAll(centroAlmacenBlzs);
            logger.error("Extraccion CentroAlmacenBlz 07");
        } catch (Exception e){
            logger.error("Error en la extracción: " + e.getMessage());
            throw new Exception(e);
        }
    }

    @Override
    public List<CentroAlmacenBlz> getCentroAlmacenByCentro(String centro) {
        return this.centroAlmacenBlzRepository.getCentroAlmacenBlzByCentro(centro);
    }

    @Override
    public List<CentroAlmacenBlz> getAllCentroAlmacen() {
        return this.centroAlmacenBlzRepository.getAllCentroAlmacen();
    }

    private void mapFilter(JCoFunction function){
        JCoParameterList parameterList = function.getImportParameterList();
    }
}
