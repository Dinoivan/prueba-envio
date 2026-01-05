package com.incloud.hcp.rest;

import com.incloud.hcp.domain.DocumentoAceptacionDetalle;
import com.incloud.hcp.service.DocumentoAceptacionDetalleService;
import com.incloud.hcp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/DocumentoAceptacionDetalle")
public class DocumentoAceptacionDetalleRest {

    private DocumentoAceptacionDetalleService documentoAceptacionDetalleService;

    @Autowired
    public DocumentoAceptacionDetalleRest(DocumentoAceptacionDetalleService documentoAceptacionDetalleService) {
        this.documentoAceptacionDetalleService = documentoAceptacionDetalleService;
    }

    @GetMapping(value = "/findDocumentoAceptacionDetalleById/{idDocumentoAceptacion}")
    public ResponseEntity<List<DocumentoAceptacionDetalle>> getDocumentoAceptacionDetalleNoAnuladasListById(@PathVariable("idDocumentoAceptacion") Integer idDocumentoAceptacion){
        try {
            List<DocumentoAceptacionDetalle> documentoAceptacionDetalleList = documentoAceptacionDetalleService.getDocumentoAceptacionDetalleNoAnuladasListById(idDocumentoAceptacion);

            if(documentoAceptacionDetalleList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(documentoAceptacionDetalleList, HttpStatus.OK);
        }catch (Exception e) {
                String error = Utils.obtieneMensajeErrorException(e);
                throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/findDocumentoAceptacionDetalleConAnuladasById/{idDocumentoAceptacion}")
    public ResponseEntity<List<DocumentoAceptacionDetalle>> getDocumentoAceptacionDetalleConAnuladasListById(@PathVariable("idDocumentoAceptacion") Integer idDocumentoAceptacion){
        try {
            List<DocumentoAceptacionDetalle> documentoAceptacionDetalleList = documentoAceptacionDetalleService.getDocumentoAceptacionDetalleConAnuladasListById(idDocumentoAceptacion);

            if(documentoAceptacionDetalleList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(documentoAceptacionDetalleList, HttpStatus.OK);
        }catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
