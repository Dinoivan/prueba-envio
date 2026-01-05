package com.incloud.hcp.jco.balanza.Transportista.service.impl;

import com.incloud.hcp.domain.balanza.Transportista;
import com.incloud.hcp.jco.balanza.Transportista.dto.TransportistaBlzFiltroBusquedaDTO;
import com.incloud.hcp.jco.balanza.Transportista.dto.TransportistaBlzFiltroDTO;
import com.incloud.hcp.jco.balanza.Transportista.dto.TransportistaMapper;
import com.incloud.hcp.jco.balanza.Transportista.dto.TransportistaResponseDTO;
import com.incloud.hcp.jco.balanza.Transportista.service.TransportistaService;
import com.incloud.hcp.repository.TransportistaRepository;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TransportistaServiceImpl implements TransportistaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TransportistaRepository transportistaRepository;
    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;
    private final String FUNCION_CONSULTA_RFC = "ZFPE_MM_EXTRAE_DM_PROVEEDOR";

    @Override
    public List<Transportista> getAllTransportistas() {
        return transportistaRepository.findAll().stream().collect(Collectors.toList());
    }
    @Override
    public Transportista createTransportista(Transportista transportista) {
        if(transportistaRepository.findByRuc(transportista.getRuc()) != null){
            throw new RuntimeException("Ya existe un transportista con ese número de ruc");
        }
        transportista.setEstado(true);
        transportista.setOrigen("BTP");
        return transportistaRepository.save(transportista);
    }

    @Override
    public void extraerTransportistasListRFC(TransportistaBlzFiltroDTO dto) throws Exception {
        try{
            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            logger.error("EXTRACCION TRANSPORTISTAS - JCoDestination: " + destination);

            JCoRepository repository = destination.getRepository();
            logger.error("EXTRACCION TRANSPORTISTAS - JCoRepository: " + repository);

            JCoFunction jCoFunction = repository.getFunction(FUNCION_CONSULTA_RFC);
            logger.error("EXTRACCION TRANSPORTISTAS - JCoFunction: " + jCoFunction);


            Calendar calendar = Calendar.getInstance();
            Date fechaFin = new Date(calendar.getTimeInMillis());
            calendar.add(Calendar.DATE,-1);
            Date fechaIni = new Date(calendar.getTimeInMillis());
            logger.error("EXTRACCION TRANSPORTISTAS (A) - mapFilter START fechaIni: "+fechaIni+"   * fechaFin: "+fechaFin);
            //this.mapFilter(jCoFunction, dto.getRuc(),dto.getFechaInicio(),dto.getFechaFin());
            this.mapFilter(jCoFunction, "",fechaIni,fechaFin);
            logger.error("EXTRACCION TRANSPORTISTAS (A) - mapFilter END");

            jCoFunction.execute(destination);

            logger.error("EXTRACCION TRANSPORTISTAS - JCoParameterList START");
            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            logger.error("EXTRACCION TRANSPORTISTAS - JCoParameterList END");

            logger.error("EXTRACCION TRANSPORTISTAS - transportistaMapper START");
            TransportistaMapper transportistaMapper = TransportistaMapper.newMapper(exportParameterList);
            logger.error("EXTRACCION TRANSPORTISTAS - transportistaMapper END");

            logger.error("EXTRACCION TRANSPORTISTAS - getTransportistaList START");
            List<TransportistaResponseDTO> transportistaResponseDTOList = transportistaMapper.getTransportistaList();
            logger.error("EXTRACCION TRANSPORTISTAS - getTransportistaList END");

            logger.error("EXTRACCION TRANSPORTISTAS - NumTransportistas: " + transportistaResponseDTOList.size());

            Map<String, List<TransportistaResponseDTO>> transportistasMap = transportistaResponseDTOList.stream()
                    .distinct()
                    .collect(Collectors.groupingBy(TransportistaResponseDTO::getRuc,
                             Collectors.toList()));
            logger.error("EXTRACCION TRANSPORTISTAS - transportistasMap: " + transportistasMap.size());
            logger.error("EXTRACCION TRANSPORTISTAS - Inicio de guardado en bd");

            transportistasMap.forEach((ruc, itemList) -> {
                List<Integer> idTransportista = transportistaRepository.getIdTransportistaByRuc(ruc);
                if((!idTransportista.isEmpty() && idTransportista.get(0) == null) || idTransportista.isEmpty()){
                    if(idTransportista.isEmpty()) logger.error("Transportista nuevo con ruc: " + ruc);
                    TransportistaResponseDTO item = itemList.get(0);
                    Transportista transportista = new Transportista();
                    transportista.setDireccion(item.getDireccion());
                    transportista.setRuc(item.getRuc());
                    transportista.setRazonSocial(item.getRazonSocial());
                    transportista.setAcreedor(item.getAcreedor());
                    transportista.setGrupoCuentas(item.getGrupoCuentas());
                    transportista.setOrigen("SAP");
                    transportista.setEstado(true);
                    transportistaRepository.save(transportista);
                }
                List<Transportista> transportistaList = this.transportistaRepository.getTransportistaByRucList(ruc);

                Transportista transportistaBD = new Transportista();
                transportistaBD.setDireccion(transportistaList.get(0).getDireccion());
                transportistaBD.setRuc(transportistaList.get(0).getRuc());
                transportistaBD.setRazonSocial(transportistaList.get(0).getRazonSocial());
                transportistaBD.setGrupoCuentas(transportistaList.get(0).getGrupoCuentas());
                transportistaBD.setAcreedor(transportistaList.get(0).getAcreedor());

                TransportistaResponseDTO item = itemList.get(0);
                Transportista transportistaNEW = new Transportista();
                transportistaNEW.setDireccion(item.getDireccion());
                transportistaNEW.setRuc(item.getRuc());
                transportistaNEW.setRazonSocial(item.getRazonSocial());
                transportistaNEW.setAcreedor(item.getAcreedor());
                transportistaNEW.setGrupoCuentas(item.getGrupoCuentas());

                if(!transportistaBD.getRuc().equals(transportistaNEW.getRuc()) ||
                        !transportistaBD.getDireccion().equals(transportistaNEW.getDireccion()) ||
                        !transportistaBD.getRazonSocial().equals(transportistaNEW.getRazonSocial())){
                    logger.error("SE ACTUALIZO TRANSPORTIASTE CON RUC: " + ruc);
                    Transportista transportistaUP = new Transportista();
                    transportistaUP.setDireccion(item.getDireccion());
                    transportistaUP.setRuc(item.getRuc());
                    transportistaUP.setRazonSocial(item.getRazonSocial());
                    transportistaUP.setAcreedor(item.getAcreedor());
                    transportistaUP.setGrupoCuentas(item.getGrupoCuentas());
                    this.transportistaRepository.save(transportistaUP);
                }

            });

            logger.error("EXTRACCION TRANSPORTISTAS - Fin de guardado en bd");
        } catch (Exception e){
            logger.error("EXTRACCION TRANSPORTISTAS - ERROR AL REALIZAR EXTRACCION");
            logger.error("EXTRACCION TRANSPORTISTAS - Mensaje: " + e.getMessage());
            throw new Exception(e);
        }
    }

    @Override
    public Transportista cambiarEstado(Long transportistaId) throws Exception {
        Optional<Transportista> optionalTransportista = this.transportistaRepository.findById(transportistaId);
        if(!optionalTransportista.isPresent()){
            throw new Exception("No se encontró Transportista con ID: " + transportistaId);
        }
        Transportista transportista = optionalTransportista.get();
        transportista.setEstado(!transportista.getEstado());
        return this.transportistaRepository.save(transportista);
    }

    @Override
    public List<Transportista> getAllTransportistasBlzByFiltro(TransportistaBlzFiltroBusquedaDTO dto) {
        String ruc = dto.getRuc() != null ? dto.getRuc() : "";
        logger.error("getAllTransportistasBlzByFiltro::ruc " + ruc);
        String razonSocial = dto.getRazonSocial() != null ? dto.getRazonSocial() : "";
        logger.error("getAllTransportistasBlzByFiltro::razonSocial " + razonSocial);
        return this.transportistaRepository.findAllByRucAndRazonSocial(ruc, razonSocial);
    }

    public void mapFilter(JCoFunction jCoFunction, String ruc, Date fechaInicio, Date fechaFin){
        logger.error("EXTRACCION TRANSPORTISTAS (B) - mapFilter END");
        JCoParameterList parameterList = jCoFunction.getImportParameterList();
        parameterList.setValue("PI_RUC", ruc);
        parameterList.setValue("PI_FECINI", fechaInicio);
        parameterList.setValue("PI_FECFIN", fechaFin);
        logger.error("EXTRACCION TRANSPORTISTAS (B) - mapFilter END");
    }
}
