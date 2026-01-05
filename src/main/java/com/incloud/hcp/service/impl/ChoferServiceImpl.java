package com.incloud.hcp.service.impl;


import com.incloud.hcp.domain.balanza.Chofer;
import com.incloud.hcp.jco.balanza.Chofer.dto.ChoferResponseDTO;
import com.incloud.hcp.jco.balanza.Chofer.service.JCOChoferService;
import com.incloud.hcp.repository.ChoferRepository;
import com.incloud.hcp.service.ChoferService;
import com.incloud.hcp.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ChoferServiceImpl implements ChoferService {

    private static Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    ChoferRepository choferRepository;

    @Autowired
    JCOChoferService jcoChoferService;

    @Override
    public List<Chofer> getAllChofer() {
        return choferRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    @Override
    public Chofer getChoferByDni(String dni) {
        return choferRepository.findByDni(dni);
    }

    @Override
    public Chofer getChoferByLicencia(String licencia) {
        return choferRepository.findByLicencia(licencia);
    }

    @Override
    public ResponseEntity<Map> save(Chofer chofer) {
        ResponseEntity response = null;
        Map map = new HashMap<>();
        try{
            /*if(choferRepository.findByLicencia(chofer.getLicencia()) != null){
                map.put("message", "La Licencia " + chofer.getLicencia() + " sé encuentra asignado a otro Chofer");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            }else if(choferRepository.findByDni(chofer.getDni()) != null){
                map.put("message", "El DNI " + chofer.getDni() + " sé encuentra registrado a otro Chofer");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            }*/
            if(choferRepository.getChoferByDniAndLicencia(chofer.getDni(),chofer.getLicencia()).size()>0){
                map.put("message", "La Licencia " + chofer.getLicencia() +" y documento " +chofer.getDni()+ " se encuentra asignado al Chofer");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            }else{
                //try{
                    ChoferResponseDTO choferResponseDTO = new ChoferResponseDTO();
                    choferResponseDTO.setLicencia(chofer.getLicencia());
                    choferResponseDTO.setNombre(chofer.getNombre());
                    choferResponseDTO.setApellidoPaterno(chofer.getApellidoPaterno());
                    choferResponseDTO.setApellidoMaterno(chofer.getApellidoMaterno());
                    choferResponseDTO.setDni(chofer.getDni());
                    choferResponseDTO.setEstado(chofer.getEstado());
                    choferResponseDTO.setTipoDocumento(chofer.getTipoDocumento());
                    logger.error("ChoferResponseDTO: " + choferResponseDTO);
                    jcoChoferService.grabarChofer(choferResponseDTO);
                /*} catch (Exception e){
                    logger.error("ERROR-CHOFER CREATE: " + e.getMessage());
                    throw new RuntimeException(e);
                }*/
                LocalDateTime fechaactual = LocalDateTime.now();
                chofer.setFechaCreacion(DateUtils.getCurrentTimestamp());
                chofer.setOrigen("BTP");
                chofer.setEstado("");
                chofer.setMigrado("X");
                map.put("data", choferRepository.save(chofer));
                response = new ResponseEntity(map, HttpStatus.OK);
            }

        }catch (Exception e){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public Chofer update(Integer choferId, ChoferResponseDTO choferResponse) {
        logger.error("INICIO CHOFER UPDATE");
        Optional<Chofer> choferOptional = choferRepository.findById(choferId);
        if(!choferOptional.isPresent()){
            logger.error("NO SE ENCONTRÓ UN CHOFER CON EL ID: {}", choferId);
        }
        Chofer chofer = choferOptional.get();
        chofer.setLicencia(choferResponse.getLicencia());
        chofer.setNombre(choferResponse.getNombre());
        chofer.setDni(choferResponse.getDni());
        chofer.setEstado("");
        Chofer response = choferRepository.save(chofer);
        try{
            ChoferResponseDTO choferResponseDTO = new ChoferResponseDTO();
            choferResponseDTO.setLicencia(choferResponse.getLicencia());
            choferResponseDTO.setNombre(choferResponse.getNombre());
            choferResponseDTO.setDni(choferResponse.getDni());
            choferResponseDTO.setEstado(choferResponse.getEstado());
            logger.error("ChoferResponseDTO: " + choferResponseDTO);
            jcoChoferService.actualizarChofer(choferResponseDTO);
        } catch (Exception e){
            logger.error("ERROR-CHOFER UPDATE: " + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.error("response {}", response);
        return response;
    }

    @Override
    public Chofer cambiarEstado(Integer choferId) throws Exception {
        Optional<Chofer> choferOptional = this.choferRepository.findById(choferId);
        if(!choferOptional.isPresent()){
            throw new Exception("No se encontró Chofer con ID: " + choferId);
        }
        Chofer chofer = choferOptional.get();
        chofer.setEstado(chofer.getEstado().equals("") ? "X" : "");
        ChoferResponseDTO choferResponseDTO = new ChoferResponseDTO();
        choferResponseDTO.setLicencia(chofer.getLicencia());
        choferResponseDTO.setNombre(chofer.getNombre());
        choferResponseDTO.setDni(chofer.getDni());
        choferResponseDTO.setEstado(chofer.getEstado());
        choferResponseDTO.setApellidoPaterno(chofer.getApellidoPaterno());
        choferResponseDTO.setApellidoMaterno(chofer.getApellidoMaterno());
        choferResponseDTO.setTipoDocumento(chofer.getTipoDocumento());
        //jcoChoferService.actualizarChofer(choferResponseDTO);
        jcoChoferService.grabarChofer(choferResponseDTO);
        return this.choferRepository.save(chofer);
    }
}
