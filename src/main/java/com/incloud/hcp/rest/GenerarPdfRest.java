package com.incloud.hcp.rest;

import com.incloud.hcp.service.GenerarPDFservice;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping(value = "/api/GenerarPDFTicket")
public class GenerarPdfRest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GenerarPDFservice generarPDFservice;
    @Operation(summary = "Generar pdf Ticket")
    @RequestMapping(value = "/gerarPDFTicket/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> generarPDFsubticket(@PathVariable("id") Integer id){

        //try {
            logger.error("[ReporteExtintoresPdf]: Ingreso");

            byte[] bytesReporte = null;
            bytesReporte =generarPDFservice.generarPDFReporteGuia(id);
            logger.error("[ReporteExtintoresPdf]: Base64:{}" + Base64.getEncoder().encodeToString(bytesReporte));
            String reporte = Base64.getEncoder().encodeToString(bytesReporte);
            return new ResponseEntity<>(reporte, HttpStatus.OK);

        /*} catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }*/
    }
}
