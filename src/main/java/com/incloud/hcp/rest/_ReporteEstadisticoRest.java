package com.incloud.hcp.rest;

import com.incloud.hcp.dto.estadistico.*;
import com.incloud.hcp.service.ReporteEstadisticoService;
import com.incloud.hcp.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/_reporteEstadisticoRest")
public class _ReporteEstadisticoRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReporteEstadisticoService reporteEstadisticoService;


    @Operation(summary = "Genera Reporte Estadistico de Adjudicacion por RUC del Proveedor")
    @GetMapping(value = "/reporteAdjudicacion/{ruc}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReporteEstadisticoAdjudicacionSalidaDto> reporteAdjudicacion(@PathVariable String ruc) throws URISyntaxException {
        log.debug("Find by id reporteAdjudicacion : {}", ruc);
        try {
            return Optional.ofNullable(this.reporteEstadisticoService.reporteAdjudicacion(ruc))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Genera Reporte Estadistico de Participacion por RUC del Proveedor")
    @GetMapping(value = "/reporteParticipacion/{ruc}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReporteEstadisticoParticipacionSalidaDto> reporteParticipacion(@PathVariable String ruc) throws URISyntaxException {
        log.debug("Find by id reporteParticipacion : {}", ruc);
        try {
            return Optional.ofNullable(this.reporteEstadisticoService.reporteParticipacion(ruc))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Genera Reporte Estadistico de Status de Peticion de Oferta por RUC del Proveedor")
    @PostMapping(value = "/reporteStatusPeticionOferta", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReporteStatusPeticionOfertaSalidaDto> reporteStatusPeticionOferta(
            @RequestBody ReporteStatusPeticionOfertaEntradaDto bean) throws URISyntaxException {
        log.debug("Find by id reporteStatusPeticionOferta : {}", bean);
        try {
            return Optional.ofNullable(this.reporteEstadisticoService.reporteStatusPeticionOferta(bean))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Genera Reporte Estadistico de Status de Peticion de Oferta por RUC del Proveedor (Todos - Paginado)")
    @PostMapping(value = "/reporteStatusPeticionOfertaTodosPaginado", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReporteStatusPeticionOfertaSalidaDto> reporteStatusPeticionOfertaTodosPaginado(
            @RequestBody ReporteStatusPeticionOfertaEntradaPaginadoDto bean) throws URISyntaxException {
        log.debug("Find by id reporteStatusPeticionOfertaTodosPaginado : {}", bean);
        try {
            return Optional.ofNullable(this.reporteEstadisticoService.reporteStatusPeticionOfertaTodosPaginado(bean))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Genera Reporte Estadistico de Status de Peticion de Oferta por RUC del Proveedor (Adjudicados - Paginado)")
    @PostMapping(value = "/reporteStatusPeticionOfertaAdjuPaginado", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReporteStatusPeticionOfertaSalidaDto> reporteStatusPeticionOfertaAdjuPaginado(
            @RequestBody ReporteStatusPeticionOfertaEntradaPaginadoDto bean) throws URISyntaxException {
        log.debug("Find by id reporteStatusPeticionOfertaAdjuPaginado : {}", bean);
        try {
            return Optional.ofNullable(this.reporteEstadisticoService.reporteStatusPeticionOfertaAdjuPaginado(bean))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Genera Reporte Estadistico de Status de Peticion de Oferta por RUC del Proveedor (En Proceso - Paginado)")
    @PostMapping(value = "/reporteStatusPeticionOfertaEnProcPaginado", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReporteStatusPeticionOfertaSalidaDto> reporteStatusPeticionOfertaEnProcPaginado(
            @RequestBody ReporteStatusPeticionOfertaEntradaPaginadoDto bean) throws URISyntaxException {
        log.debug("Find by id reporteStatusPeticionOfertaEnProcPaginado : {}", bean);
        try {
            return Optional.ofNullable(this.reporteEstadisticoService.reporteStatusPeticionOfertaEnProcPaginado(bean))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }



}
