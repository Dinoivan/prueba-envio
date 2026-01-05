package com.incloud.hcp.rest;

import com.incloud.hcp.enums.TipoArchivoEnum;
import com.incloud.hcp.jco.comprobanteRetencion.dto.ComprobanteRetencionDto;
import com.incloud.hcp.service.ComprobanteRetencionService;
import com.incloud.hcp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/ComprobanteRetencion")
public class ComprobanteRetencionRest {

    private ComprobanteRetencionService comprobanteRetencionService;

    @Autowired
    public ComprobanteRetencionRest(ComprobanteRetencionService comprobanteRetencionService) {
        this.comprobanteRetencionService = comprobanteRetencionService;
    }

    @GetMapping(value = "/getComprobanteRetencionList")
    public ResponseEntity<List<ComprobanteRetencionDto>> getComprobanteRetencionList(
            @RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam(value = "fechaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
            @RequestParam(value = "sociedad", required = false) String sociedad,
            @RequestParam(value = "ruc", required = false) String ruc){
        try{
            List<ComprobanteRetencionDto> comprobanteRetencionDtoList = comprobanteRetencionService.getComprobanteRetencionListPorFechasAndSociedadAndRuc(fechaInicio, fechaFin, sociedad, ruc);

            if(comprobanteRetencionDtoList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(comprobanteRetencionDtoList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "/comprobanteRetencionPdf")
    public String getComprobanteRetencionPdf(@RequestBody ComprobanteRetencionDto comprobanteRetencionDto) {

        try {
            return comprobanteRetencionService.getComprobanteRetencionGeneratePdfContent(comprobanteRetencionDto);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "/comprobanteRetencionBase64")
    public String getComprobanteRetencionBase64(@RequestBody ComprobanteRetencionDto comprobanteRetencionDto) {
        try {
            return comprobanteRetencionService.getComprobanteRetencionBase64(comprobanteRetencionDto, TipoArchivoEnum.PDF.toString());
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "/comprobanteRetencionXmlBase64")
    public String getComprobanteRetencionXmlBase64(@RequestBody ComprobanteRetencionDto comprobanteRetencionDto) {
        try {
            return comprobanteRetencionService.getComprobanteRetencionBase64(comprobanteRetencionDto, TipoArchivoEnum.XML.toString());
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
