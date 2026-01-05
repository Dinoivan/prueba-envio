package com.incloud.hcp.rest;

import com.incloud.hcp.domain.Ubigeo;
import com.incloud.hcp.repository.UbigeoRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.UbigeoService;
import com.incloud.hcp.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Administrador on 21/08/2017.
 */
@RestController
@RequestMapping(value = "/api/pais")
public class UbigeoRest extends AppRest {

    private static Logger logger = LoggerFactory.getLogger(UbigeoRest.class);

    @Autowired
    private UbigeoService ubigeoService;

    @Autowired
    private UbigeoRepository ubigeoRepository;



    @RequestMapping(value = "", method =
            RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getListPaisAll() {
        return this.procesar(this.ubigeoService.getListPaisAll());
    }

    @RequestMapping(value = "region/{idParent}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getListRegionAll(@PathVariable("idParent") String codigoPais) {
        if (codigoPais.equals(Constant.UNDEFINED))
            codigoPais = null;
        return this.procesar(this.ubigeoService.getListUbigeoByParent(Arrays.asList(codigoPais)));
    }

    @RequestMapping(value = "region/provincia/{idParent}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getListProvinciaAll(@PathVariable("idParent") String codigoRegion) {
        if (codigoRegion.equals(Constant.UNDEFINED))
            codigoRegion = null;
       return this.procesar(this.ubigeoService.getListUbigeoByParent(Arrays.asList(codigoRegion)));
    }

    @RequestMapping(value = "region/provincia/distrito/{idParent}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getListDistritoAll(@PathVariable("idParent") String codigoProvincia) {
        if (codigoProvincia.equals(Constant.UNDEFINED))
            codigoProvincia = null;
       return this.procesar(this.ubigeoService.getListUbigeoByParent(Arrays.asList(codigoProvincia)));
    }

    @RequestMapping(value = "auxiliar/{codigo}/{nivel}/{idParent}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Ubigeo> getAuxiliarUbigeo(@PathVariable("codigo") String codigo, @PathVariable("nivel") Integer nivel, @PathVariable("idParent") Integer idParent) {

        //return this.procesar();
        return ResponseEntity.ok().body(this.ubigeoRepository.findByCodigoUbigeoSapErpAndNivelAndIdPadre(codigo, nivel,idParent));
    }

    @RequestMapping(value = "region01/{idParent}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getListRegion01All(@PathVariable("idParent") String codigoPais) {
        if (codigoPais.equals(Constant.UNDEFINED))
            codigoPais = null;
        return this.procesar(this.ubigeoService.getListUbigeoByPadre(new Integer(codigoPais)));
    }

    @RequestMapping(value = "region01/provincia/{idParent}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getListProvincia01All(@PathVariable("idParent") String codigoRegion) {
        if (codigoRegion.equals(Constant.UNDEFINED))
            codigoRegion = null;
        return this.procesar(this.ubigeoService.getListUbigeoByPadre(new Integer(codigoRegion)));
    }

    @RequestMapping(value = "region01/provincia/distrito/{idParent}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getListDistrito01All(@PathVariable("idParent") String codigoProvincia) {
        if (codigoProvincia.equals(Constant.UNDEFINED))
            codigoProvincia = null;
        return this.procesar(this.ubigeoService.getListUbigeoByPadre(new Integer(codigoProvincia)));
    }

    @RequestMapping(value = "ubigeo/byparent", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Map> getListUbigeoByParent(@RequestBody ArrayList<String> ids)  {
        Optional<List<Ubigeo>> lista = Optional.of(this.ubigeoService.getListUbigeoByParent(ids));
        Map response = new HashMap<>();
        response.put("total", lista.isPresent() ? lista.get().size() : 0);
        response.put("data", lista.isPresent() ? lista.get() : new ArrayList<>());
        return lista.isPresent() ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    private ResponseEntity<Map> procesar(List lista) {
        return Optional.ofNullable(lista)
                .map(l -> {
                    Map response = new HashMap<>();
                    response.put("total", l.size());
                    response.put("data", l);
                    return response;
                })
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .get();
    }


    @RequestMapping(value = "/findByNivelOrderByDescripcion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Ubigeo>> findByNivelOrderByDescripcion(@RequestBody Integer nivel)  {
        List<Ubigeo> lista = this.ubigeoRepository.findByNivelOrderByDescripcion(nivel);
        return ResponseEntity.ok().body(lista);
    }




}
