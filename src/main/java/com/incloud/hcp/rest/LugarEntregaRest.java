package com.incloud.hcp.rest;

import com.incloud.hcp.domain.almacen.LugarEntrega;
import com.incloud.hcp.repository.LugarEntregaRepository;
import com.incloud.hcp.service.LugarEntregaService;
import com.incloud.hcp.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/lugar-entrega")
public class LugarEntregaRest{

    private LugarEntregaService lugarEntregaService;

    private LugarEntregaRepository lugarEntregaRepository;

    @Autowired
    public LugarEntregaRest(LugarEntregaService lugarEntregaService,
                            LugarEntregaRepository lugarEntregaRepository) {
        this.lugarEntregaService = lugarEntregaService;
        this.lugarEntregaRepository = lugarEntregaRepository;
    }

    @PostMapping(value = "/extraerLugarEntregaOC")
    public ResponseEntity<List<LugarEntrega>> extraerLugarEntregaOC(){
        try {
            List<LugarEntrega> restLugarEntrega = this.lugarEntregaService.getLugarEntrega();
            return ResponseEntity.ok().body(restLugarEntrega);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/getAllLugaresEntrega")
    public List<LugarEntrega> getAllLugaresEntrega() {
        try{
            return this.lugarEntregaRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
