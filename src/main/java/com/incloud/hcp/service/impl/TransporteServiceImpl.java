package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.balanza.Transporte;
import com.incloud.hcp.jco.balanza.Transporte.dto.TransporteResponseDTO;
import com.incloud.hcp.jco.balanza.Transporte.service.JCOTransporteService;
import com.incloud.hcp.repository.TransporteRepository;
import com.incloud.hcp.service.TransporteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TransporteServiceImpl  implements TransporteService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private JCOTransporteService transporteService;
    @Override
    public List<Transporte> getAllTransporte() {
        return transporteRepository.findAll().stream().collect(Collectors.toList());
    }
    @Override
    public List<Transporte> getAllTransporteActivo() {
        return transporteRepository.findTransporteActivo();
    }
    @Override
    public List<Transporte> getAllRemolque() {
        return transporteRepository.findRemolque("X").stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Transporte> getTransporteById(Integer idTransporte) {
        return transporteRepository.findById(idTransporte);
    }

    @Override
    public ResponseEntity<Map> save(Transporte transporte) {
        ResponseEntity response = null;
        Map map = new HashMap<>();
        try{
            if(transporteRepository.findByPlaca(transporte.getPlaca()) != null){
                map.put("message", "La Placa " + transporte.getPlaca() + " sé encuentra registrado");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            }else{
                try{
                    TransporteResponseDTO transporteResponseDTO = new TransporteResponseDTO();
                    transporteResponseDTO.setMarca(transporte.getMarca());
                    transporteResponseDTO.setModelo(transporte.getModelo());
                    transporteResponseDTO.setPlaca(transporte.getPlaca());
                    transporteResponseDTO.setCiv(transporte.getCiv());
                    transporteResponseDTO.setRemolque(transporte.getRemolque());
                    transporteResponseDTO.setTipoVehiculo(transporte.getTipoVehiculo());
                    transporteResponseDTO.setNroAutorizacion(transporte.getNroAutorizacion());
                    transporteResponseDTO.setCodAutorizacion(transporte.getCodAutorizacion());
                    transporteResponseDTO.setZmtc(transporte.getZmtc());
                    transporteResponseDTO.setCreatedBy(transporte.getCreatedBy());
                    transporteResponseDTO.setModifiedBy(transporte.getModifiedBy());

                    transporteResponseDTO.setEstado("");
                    logger.error("transporteResponseDTO: " + transporteResponseDTO);
                    //transporteService.actualizaTransporte(transporteResponseDTO);
                    transporteService.grabarTransporte(transporteResponseDTO);
                } catch (Exception e){
                    logger.error("ERROR-TRANSPORTE CREATE: " + e.getMessage());
                    throw new RuntimeException(e);
                }
                LocalDateTime fechaactual = LocalDateTime.now();
                transporte.setFechaCreacion(Date.from(fechaactual.atZone(ZoneId.systemDefault()).toInstant()));
                transporte.setEstado("");
                transporte.setOrigen("BTP");
                map.put("data", transporteRepository.save(transporte));
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        }catch (Exception e){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return response;

    }

    @Override
    public Transporte update(Integer transporteId, TransporteResponseDTO transporteResponse) {
        logger.error("INICIO TRANSPORTE UPDATE");
        Optional<Transporte> transporteOptional = transporteRepository.findById(transporteId);
        if(!transporteOptional.isPresent()){
            logger.error("NO SE ENCONTRÓ UN TRANSPORTE CON EL ID: {}", transporteId);
        }
        Transporte transporte = transporteOptional.get();
        transporte.setMarca(transporteResponse.getMarca());
        transporte.setModelo(transporteResponse.getModelo());
        transporte.setPlaca(transporteResponse.getPlaca());
        transporte.setCiv(transporteResponse.getCiv());
        Transporte response = transporteRepository.save(transporte);
        try{
            TransporteResponseDTO transporteResponseDTO = new TransporteResponseDTO();
            transporteResponseDTO.setMarca(transporte.getMarca());
            transporteResponseDTO.setModelo(transporte.getModelo());
            transporteResponseDTO.setPlaca(transporte.getPlaca());
            transporteResponseDTO.setCiv(transporte.getCiv());
            transporteResponseDTO.setEstado(transporte.getEstado());
            logger.error("transporteResponseDTO: " + transporteResponseDTO);
            transporteService.actualizaTransporte(transporteResponseDTO);
        } catch (Exception e){
            logger.error("ERROR-TRANSPORTE UPDATE: " + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.error("response {}", response);
        return response;
    }

    @Override
    public Transporte cambiarEstado(Integer transporteId) throws Exception {
        Optional<Transporte> transporteOptional = transporteRepository.findById(transporteId);
        if(!transporteOptional.isPresent()){
            throw new Exception("No se encontró Transporte con ID: " + transporteId);
        }
        Transporte transporte = transporteOptional.get();
        transporte.setEstado(transporte.getEstado().equals("") ? "X" : "");

        TransporteResponseDTO transporteSAP = new TransporteResponseDTO();
        transporteSAP.setMarca(transporte.getMarca());
        transporteSAP.setModelo(transporte.getModelo());
        transporteSAP.setPlaca(transporte.getPlaca());
        transporteSAP.setCiv(transporte.getCiv());
        transporteSAP.setEstado(transporte.getEstado());
        transporteSAP.setRemolque(transporte.getRemolque());
        transporteSAP.setTipoVehiculo(transporte.getTipoVehiculo());
        transporteSAP.setNroAutorizacion(transporte.getNroAutorizacion());
        transporteSAP.setCodAutorizacion(transporte.getCodAutorizacion());
        transporteSAP.setZmtc(transporte.getZmtc());
        transporteSAP.setCreatedBy(transporte.getCreatedBy());
        transporteSAP.setModifiedBy(transporte.getModifiedBy());
        logger.error("transporteSAP: " + transporteSAP);
        //this.transporteService.actualizaTransporte(transporteSAP);
        this.transporteService.grabarTransporte(transporteSAP);
        return this.transporteRepository.save(transporte);
    }
}
