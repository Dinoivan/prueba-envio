package com.incloud.hcp.rest;

import com.incloud.hcp.domain.Sociedad;
import com.incloud.hcp.repository.SociedadRepository;
import com.incloud.hcp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/Sociedad")
public class SociedadRest {

    @Autowired
    SociedadRepository sociedadRepository;

    @GetMapping(value = "/ObtenerTodo")
    public ResponseEntity<List<Sociedad>> getAllSociedad(){
        try {
            List<Sociedad> sociedadList = sociedadRepository.findAll();
            if(!sociedadList.isEmpty()){
                return new ResponseEntity<>(sociedadList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
