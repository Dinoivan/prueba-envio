package com.incloud.hcp.rest;

import com.incloud.hcp.domain.balanza.CentroAlmacenBalanza;
import com.incloud.hcp.helpers.LecturaPeso;
import com.incloud.hcp.service.GuiaRemisionService;
import com.incloud.hcp.service.LecturaPesoService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/lecturaSFTP")
public class ImpresionSFTP {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    //Creartxt creartxt = new Creartxt();

    LecturaPeso lecturaPeso = new LecturaPeso();

    @Autowired
    private LecturaPesoService lectuaPesoService;

    @Autowired
    private GuiaRemisionService guiaRemisionService;


    @RequestMapping(value = "/crearTxt/{id}", method = RequestMethod.POST)
    public JSONObject Creartxt(@PathVariable("id") Integer id) throws FileNotFoundException {

        return guiaRemisionService.findByIdGuiaRemision(id);
    }


    @RequestMapping(value = "/leecturaSFTP/{nombretxt}", method = RequestMethod.GET)
    public String LecturaPeso(@PathVariable("nombretxt") String nombretxt) throws FileNotFoundException, JSchException, SftpException {
        logger.error("[lecturaPeso]:Inicio");
        return lecturaPeso.LecturaPesoSFTP(nombretxt);
    }


    @RequestMapping(value = "/listar-peso", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<CentroAlmacenBalanza>> gelAllTicketPesaje() {
        return Optional.ofNullable(lectuaPesoService.getAllCentroPesoBalanza()).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
