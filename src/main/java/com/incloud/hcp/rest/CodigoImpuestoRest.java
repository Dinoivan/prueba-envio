package com.incloud.hcp.rest;

import com.incloud.hcp.domain.CodigoImpuesto;
import com.incloud.hcp.repository.CodigoImpuestoRepository;
import com.incloud.hcp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/CodigoImpuesto")
public class CodigoImpuestoRest {

    private CodigoImpuestoRepository codigoImpuestoRepository;

    @Autowired
    public CodigoImpuestoRest(CodigoImpuestoRepository codigoImpuestoRepository) {
        this.codigoImpuestoRepository = codigoImpuestoRepository;
    }

    @GetMapping(value = "/ObtenerTodo")
    public ResponseEntity<List<CodigoImpuesto>> getAllCodigoImpuesto(){
        try {
            Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "codigo"));
            List<CodigoImpuesto> codigoImpuestoList = codigoImpuestoRepository.findAll(sort);
            if(codigoImpuestoList != null && !codigoImpuestoList.isEmpty() ){
                return new ResponseEntity<>(codigoImpuestoList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
