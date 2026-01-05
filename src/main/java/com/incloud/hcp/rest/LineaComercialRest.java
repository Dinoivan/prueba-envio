package com.incloud.hcp.rest;

import com.incloud.hcp.domain.LineaComercial;
import com.incloud.hcp.facade.LineaComercialFacade;
import com.incloud.hcp.repository.LineaComercialRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.LineaComercialService;
import com.incloud.hcp.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Administrador on 28/08/2017.
 */
@RestController
@RequestMapping(value = "/api/linea-comercial")
public class LineaComercialRest extends AppRest {

    @Autowired
    private LineaComercialService lineaComercialService;

    @Autowired
    private LineaComercialFacade lineaComercialFacade;

    @Autowired
    private LineaComercialRepository lineaComercialRepository;

    @Operation(summary = "Busca registro de Linea Comercial en base al nivel")
    @GetMapping(value = "/findByNivel/{nivel}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LineaComercial>> findByIdUsuario(@PathVariable Integer nivel) throws URISyntaxException {
        try {
            return Optional.ofNullable(this.lineaComercialRepository.getListByNivel(nivel))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getListLineaComercial(@QueryParam("query") String query) {
        return this.processList(Optional.ofNullable(query)
                .filter(q -> q.equals("all"))
                .map(q -> this.lineaComercialFacade.getListLinea())
                .orElse(this.lineaComercialFacade.getListLineaWithoutIndGeneral()));
    }

    @RequestMapping(value = "/{id_linea}/familia", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getListFamiliaLineaComercial(@PathVariable("id_linea") int idLinea) {
        return this.processObject(this.lineaComercialFacade.getLineaFamilia(idLinea));
    }

    @RequestMapping(value = "/idLinea/{id_linea}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getLineaComercialById(@PathVariable("id_linea") int idLinea) {
        return this.processObject(this.lineaComercialService.getByIdLineaComercial(idLinea));
    }

    @RequestMapping(value = "{id_linea}/familia/{id_familia}/sub-familia", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getListSubFamiliaLineaComercial(@PathVariable("id_linea") int idLinea,
                                                             @PathVariable("id_familia") int idFamilia) {
        return this.processList(this.lineaComercialFacade.getLineaSubFamilia(idFamilia));
    }

    @RequestMapping(value = "ids/familia", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> getListFamiliaByIDs(@RequestBody ArrayList<String> ids) {
        return this.processList(this.lineaComercialService.getListFamiliaByIDs(ids));
    }

    @RequestMapping(value = "/all",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAll() {
        return this.processList(lineaComercialService.getListAll());
    }
}
