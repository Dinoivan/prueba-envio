package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.balanza.Carreta;
import com.incloud.hcp.jco.balanza.Carreta.dto.CarretaResponseDTO;
import com.incloud.hcp.jco.balanza.Carreta.service.JCOCarretaService;
import com.incloud.hcp.repository.CarretaRepository;

import com.incloud.hcp.service.CarretaService;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CarretaServiceImpl implements CarretaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarretaRepository carretaRepository;
    @Autowired
    private JCOCarretaService jcoCarretaService;
    @Override
    public List<Carreta> getAllCarreta() {
        return carretaRepository.findAll().stream().collect(Collectors.toList());
    }

   @Override
    public Optional<Carreta> getCarretaById(Integer idCarreta) {
        return carretaRepository.findById(idCarreta);
    }

    @Override
    public Carreta getCarretaByDescripcion(String descripcion) {
        return carretaRepository.findByDescripcion(descripcion);
    }

    @Override
    public Carreta getCarretaByCodigo(String codigo) {
        return carretaRepository.findByCodigo(codigo);
    }


    @Override
    public ResponseEntity<Map> save(Carreta carreta) {
        ResponseEntity response = null;
        Map map = new HashMap<>();
        try{
            /*if(carretaRepository.findByDescripcion(carreta.getDescripcion()) != null){
                map.put("message", "La Descripcion " + carreta.getDescripcion() + " ya se encuentra resgistrado");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            }else if(carretaRepository.findByCodigo(carreta.getCodigo()) != null){
                map.put("message", "El codigo " + carreta.getCodigo() + " sé encuentra registrado");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            }*/
            if(carretaRepository.findByPlacaList(carreta.getPlaca()).size()>0){
                map.put("message", "La Placa " + carreta.getPlaca() + " ya se encuentra resgistrado");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            }else{
                try {
                    CarretaResponseDTO carretaResponseDTO = new CarretaResponseDTO();
                    carretaResponseDTO.setModelo(carreta.getModelo());
                    carretaResponseDTO.setPlaca(carreta.getPlaca());
                    logger.error("CarretaReponseDTO: " + carretaResponseDTO);
                    jcoCarretaService.actualizarCarreta(carretaResponseDTO);
                } catch (Exception e) {
                    logger.error("ERROR-CARRETA CREATE: " + e.getMessage());
                    throw new RuntimeException(e);
                }
                LocalDateTime fechaactual = LocalDateTime.now();
                carreta.setFechaCreacion(DateUtils.getCurrentTimestamp());
                carreta.setEstado(true);
                carreta.setOrigen("BTP");
                map.put("data", carretaRepository.save(carreta));
                try{
                    CarretaResponseDTO carretaResponseDTO = new CarretaResponseDTO();
                    carretaResponseDTO.setModelo(carreta.getModelo());
                    carretaResponseDTO.setPlaca(carreta.getPlaca());
                } catch (Exception e){
                    throw new RuntimeException(e);
                }
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        }catch (Exception e){
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public Carreta update(Integer carretaId, CarretaResponseDTO carretaResponse) {
        logger.error("INICIO CARRETA UPDATE");
        Optional<Carreta> carretaOptional = carretaRepository.findById(carretaId);
        if(!carretaOptional.isPresent()){
            logger.error("NO SE ENCONTRÓ UN TRANSPORTE CON EL ID: {}", carretaId);
        }
        Carreta carreta = carretaOptional.get();
        carreta.setModelo(carretaResponse.getModelo());
        carreta.setPlaca(carretaResponse.getPlaca());
        Carreta response = carretaRepository.save(carreta);
        try {
            CarretaResponseDTO carretaResponseDTO = new CarretaResponseDTO();
            carretaResponseDTO.setModelo(carretaResponse.getModelo());
            carretaResponseDTO.setPlaca(carretaResponse.getPlaca());
            logger.error("CarretaReponseDTO: " + carretaResponseDTO);
            jcoCarretaService.actualizarCarreta(carretaResponseDTO);
        } catch (Exception e) {
            logger.error("ERROR-CARRETA UPDATE: " + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.error("response {}", response);
        return response;
    }

    @Override
    public Carreta cambiarEstado(Integer carretaId) throws Exception{
        Optional<Carreta> carretaOptional = this.carretaRepository.findById(carretaId);
        if(!carretaOptional.isPresent()){
            throw new Exception("No se encontró Carreta con ID: " + carretaId);
        }
        Carreta carreta = carretaOptional.get();
        carreta.setEstado(!carreta.getEstado());
        return this.carretaRepository.save(carreta);
    }
}
