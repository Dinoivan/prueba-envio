package com.incloud.hcp.jco.balanza.Proveedor.service.impl;

import com.incloud.hcp.domain.balanza.GuiaRemision;
import com.incloud.hcp.domain.balanza.ProveedorBLZ;
import com.incloud.hcp.jco.balanza.Proveedor.dto.ProveedorBlzFiltroBusquedaDTO;
import com.incloud.hcp.jco.balanza.Proveedor.dto.ProveedorBlzFiltroDTO;
import com.incloud.hcp.jco.balanza.Proveedor.dto.ProveedorCliMapper;
import com.incloud.hcp.jco.balanza.Proveedor.dto.ProveedorResponseDTO;
import com.incloud.hcp.jco.balanza.Proveedor.service.JCOProveedorBlzService;
import com.incloud.hcp.repository.GuiaRemisionRepository;
import com.incloud.hcp.repository.ProveedorBalanzaRepository;
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
public class JCOProveedorBlzServiceImpl implements JCOProveedorBlzService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;
    private final String FUNCION_CONSULTA_RFC = "ZFPE_MM_EXTRAE_DM_PROVEEDOR";

    @Autowired
    private ProveedorBalanzaRepository proveedorBalanzaRepository;

    @Autowired
    private GuiaRemisionRepository guiaRemisionRepository;

    @Override
    public void extraerProveedorListRFC(ProveedorBlzFiltroDTO dto) throws Exception {
        try{
            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            logger.error("EXTRACCION PROVEEDORES - JCoDestination: " + destination);

            JCoRepository repository = destination.getRepository();
            logger.error("EXTRACCION PROVEEDORES - JCoRepository: " + repository);

            JCoFunction jCoFunction = repository.getFunction(FUNCION_CONSULTA_RFC);
            logger.error("EXTRACCION PROVEEDORES - JCoFunction: " + jCoFunction);

            logger.error("EXTRACCION PROVEEDORES (A) - mapFilter START");
            this.mapFilter(jCoFunction, dto.getRuc(),dto.getFechaInicio(),dto.getFechaFin());
            logger.error("EXTRACCION PROVEEDORES (A) - mapFilter END");

            jCoFunction.execute(destination);

            logger.error("EXTRACCION PROVEEDORES - JCoParameterList START");
            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            logger.error("EXTRACCION PROVEEDORES - JCoParameterList END");

            logger.error("EXTRACCION PROVEEDORES - proveedorCliMapper START");
            ProveedorCliMapper proveedorCliMapper = ProveedorCliMapper.newMapper(exportParameterList);
            logger.error("EXTRACCION PROVEEDORES - proveedorCliMapper END");

            logger.error("EXTRACCION PROVEEDORES - getProveedorCliList START");
            List<ProveedorResponseDTO> proveedorResponseDTOList = proveedorCliMapper.getProveedorCliList();
            logger.error("EXTRACCION PROVEEDORES - getProveedorCliList END");

            logger.error("EXTRACCION PROVEEDORES - NumProveedores: " + proveedorResponseDTOList.size());

            Map<String, List<ProveedorResponseDTO>> proveedorMap = proveedorResponseDTOList.stream()
                    .distinct()
                    .collect(Collectors.groupingBy(ProveedorResponseDTO::getRuc,
                            Collectors.toList()));
            logger.error("EXTRACCION TRANSPORTISTAS - proveedorMap: " + proveedorMap.size());

            logger.error("EXTRACCION PROVEEDORES - Inicio de guardado en bd");

            proveedorMap.forEach((ruc, itemList) -> {
                List<Integer> idProveedorBLZ = proveedorBalanzaRepository.getIdProveedorByRuc(ruc);
                if((!idProveedorBLZ.isEmpty() && idProveedorBLZ.get(0) == null) || idProveedorBLZ.isEmpty()){
                    if(idProveedorBLZ.isEmpty()) logger.error("ProveedorBLZ nuevo con ruc: " + ruc);
                    ProveedorResponseDTO item = itemList.get(0);
                    ProveedorBLZ proveedor = new ProveedorBLZ();
                    proveedor.setDireccion(item.getDireccion());
                    proveedor.setRuc(item.getRuc());
                    proveedor.setRazonSocial(item.getRazonSocial());
                    proveedor.setAcreedor(item.getAcreedor());
                    proveedor.setGrupoCuentas(item.getGrupoCuentas());
                    proveedor.setEstado(true);
                    proveedor.setRegion(item.getRegion());
                    proveedor.setBezei(item.getBezei());
                    proveedor.setCity1(item.getCity1());
                    proveedor.setCity2(item.getCity2());
                    proveedor.setOrigen("SAP");
                    proveedorBalanzaRepository.save(proveedor);
                }
                List<ProveedorBLZ> proveedorList = this.proveedorBalanzaRepository.getProveedorBLZByRucList(ruc);

                ProveedorBLZ proveedorBD = new ProveedorBLZ();
                proveedorBD.setDireccion(proveedorList.get(0).getDireccion());
                proveedorBD.setRuc(proveedorList.get(0).getRuc());
                proveedorBD.setRazonSocial(proveedorList.get(0).getRazonSocial());
                proveedorBD.setAcreedor(proveedorList.get(0).getAcreedor());
                proveedorBD.setGrupoCuentas(proveedorList.get(0).getGrupoCuentas());
                proveedorBD.setRegion(proveedorList.get(0).getRegion());
                proveedorBD.setBezei(proveedorList.get(0).getBezei());
                proveedorBD.setCity1(proveedorList.get(0).getCity1());
                proveedorBD.setCity2(proveedorList.get(0).getCity2());

                ProveedorResponseDTO item = itemList.get(0);
                ProveedorBLZ proveedorNEW = new ProveedorBLZ();
                proveedorNEW.setDireccion(item.getDireccion());
                proveedorNEW.setRuc(item.getRuc());
                proveedorNEW.setRazonSocial(item.getRazonSocial());
                proveedorNEW.setAcreedor(item.getAcreedor());
                proveedorNEW.setGrupoCuentas(item.getGrupoCuentas());
                proveedorNEW.setRegion(item.getRegion());
                proveedorNEW.setBezei(item.getBezei());
                proveedorNEW.setCity1(item.getCity1());
                proveedorNEW.setCity2(item.getCity2());
                if(!proveedorBD.getRuc().equals(proveedorNEW.getRuc()) ||
                        !proveedorBD.getDireccion().equals(proveedorNEW.getDireccion()) ||
                        !proveedorBD.getRazonSocial().equals(proveedorNEW.getRazonSocial())){
                    logger.error("SE ACTUALIZO PROVEEDOR CON RUC: " + ruc);
                    ProveedorBLZ proveedorUP = new ProveedorBLZ();
                    proveedorUP.setDireccion(item.getDireccion());
                    proveedorUP.setRuc(item.getRuc());
                    proveedorUP.setRazonSocial(item.getRazonSocial());
                    proveedorUP.setGrupoCuentas(item.getGrupoCuentas());
                    proveedorUP.setAcreedor(item.getAcreedor());
                    proveedorUP.setRegion(item.getRegion());
                    proveedorUP.setBezei(item.getBezei());
                    proveedorUP.setCity1(item.getCity1());
                    proveedorUP.setCity2(item.getCity2());
                    this.proveedorBalanzaRepository.save(proveedorUP);
                }

            });

            logger.error("EXTRACCION PROVEEDORES - Fin de guardado en bd");
        } catch (Exception e){
            logger.error("EXTRACCION PROVEEDORES - ERROR AL REALIZAR EXTRACCION");
            logger.error("EXTRACCION PROVEEDORES - Mensaje: " + e.getMessage());
            throw new Exception(e);
        }

    }

    @Override
    public List<ProveedorBLZ> getAllProveedoresBlz() {
        return this.proveedorBalanzaRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public List<ProveedorBLZ> getAllProveedoresBlzByFiltro(ProveedorBlzFiltroBusquedaDTO dto) {
        String ruc = dto.getRuc() != null ? dto.getRuc() : "";
        logger.error("getAllProveedoresBlzByFiltro::ruc " + ruc);
        String razonSocial = dto.getRazonSocial() != null ? dto.getRazonSocial() : "";
        logger.error("getAllProveedoresBlzByFiltro::razonSocial " + razonSocial);
        return this.proveedorBalanzaRepository.findAllByRucAndRazocial(ruc, razonSocial);
    }
    @Override
    public List<ProveedorBLZ> getProveedorBLZByAcreedor(Integer idGuia) {
        GuiaRemision guia = this.guiaRemisionRepository.findByIdGuiaRemision(idGuia);
        List<ProveedorBLZ> list = new ArrayList<>();
        list.add(this.proveedorBalanzaRepository.getProveedorBLZByAcreedor(guia.getProveedorDestino()));
        return list;
    }

    @Override
    public ProveedorBLZ cambiarEstado(Long proveedorblzId) throws Exception {
        Optional<ProveedorBLZ> optionalProveedorBLZ = this.proveedorBalanzaRepository.findById(proveedorblzId);
        if(!optionalProveedorBLZ.isPresent()){
            throw new Exception("No se encontró ProveedorBLZ con ID: " + proveedorblzId);
        }
        ProveedorBLZ proveedorBLZ = optionalProveedorBLZ.get();
        proveedorBLZ.setEstado(!proveedorBLZ.getEstado());
        return this.proveedorBalanzaRepository.save(proveedorBLZ);
    }

    public void mapFilter(JCoFunction jCoFunction, String ruc, Date fechaInicio, Date fechaFin){
        logger.error("EXTRACCION PROVEEDORES (B) - mapFilter END");
        JCoParameterList parameterList = jCoFunction.getImportParameterList();
        parameterList.setValue("PI_RUC", ruc);
        parameterList.setValue("PI_FECINI", fechaInicio);
        parameterList.setValue("PI_FECFIN", fechaFin);
        logger.error("EXTRACCION PROVEEDORES (B) - mapFilter END");
    }
}
