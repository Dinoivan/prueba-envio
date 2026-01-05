package com.incloud.hcp.rest;

import com.incloud.hcp.jco.comprobantePago.dto.ComprobantePagoDto;
import com.incloud.hcp.service.ComprobantePagoService;
import com.incloud.hcp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/ComprobantePago")
public class ComprobantePagoRest {

    private ComprobantePagoService comprobantePagoService;

    @Autowired
    public ComprobantePagoRest(ComprobantePagoService comprobantePagoService) {
        this.comprobantePagoService = comprobantePagoService;
    }

    @GetMapping(value = "/getComprobantePagoList")
    public ResponseEntity<List<ComprobantePagoDto>> getComprobantePagoList(
            @RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam(value = "fechaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
            @RequestParam(value = "ruc", required = false) String ruc,
            @RequestParam(value = "modulo", required = false) String modulo,
            @RequestParam(value = "numeroComprobante", required = false) String numeroComprobante){
        try{
            List<ComprobantePagoDto> comprobantePagoDtoList = comprobantePagoService.getComprobantePagoListPorFechasAndRuc(fechaInicio, fechaFin, ruc, numeroComprobante, modulo);

            if(comprobantePagoDtoList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(comprobantePagoDtoList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
