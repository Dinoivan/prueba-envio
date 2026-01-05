package com.incloud.hcp.rest;

import com.incloud.hcp.domain.CentroAlmacen;
import com.incloud.hcp.repository.CentroAlmacenRepository;
import com.incloud.hcp.rest._framework.AppRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by USER on 13/09/2017.
 */
@RestController
@RequestMapping(value = "/api/centroAlmacen")
public class CentroAlmacenRest extends AppRest {

    @Autowired
    private CentroAlmacenRepository centroAlmacenRepository;


    @RequestMapping(value = "/findByNivelOrderByDenominacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<CentroAlmacen>> findByNivelOrderByDenominacion(@RequestBody Integer nivel)  {
        List<CentroAlmacen> lista = this.centroAlmacenRepository.findByNivelOrderByDenominacion(nivel);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByIdPadreOrderByDenominacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<CentroAlmacen>> findByIdPadreOrderByDenominacion(@RequestBody Integer idPadre)  {
        List<CentroAlmacen> lista = this.centroAlmacenRepository.findByIdPadreOrderByDenominacion(idPadre);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByIdPadreOrderByDenominacion/{sCentro}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<CentroAlmacen>> getAlmacenByIdPadreOrderByDenominacion(@PathVariable Integer sCentro)  {
        List<CentroAlmacen> lista = this.centroAlmacenRepository.findByIdPadreOrderByDenominacion(sCentro);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByNivel/{nivel}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<CentroAlmacen>> getAlmacenByNivel(@PathVariable Integer nivel)  {
        List<CentroAlmacen> lista = this.centroAlmacenRepository.findByNivelOrderByDenominacion(nivel);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByIdPadreInOrderByDenominacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<CentroAlmacen>> findByIdPadreInOrderByDenominacion(@RequestBody List<Integer> idPadre)  {
        List<CentroAlmacen> lista = this.centroAlmacenRepository.findByIdPadreInOrderByDenominacion(idPadre);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findById/{idCentro}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<CentroAlmacen> findById(@PathVariable Integer idCentro)  {
        CentroAlmacen centroAlmacen= this.centroAlmacenRepository.getOne(idCentro);
        return ResponseEntity.ok().body(centroAlmacen);
    }

    @RequestMapping(value = "/CodigoSap/{codigo_sap}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<CentroAlmacen> findById(@PathVariable String codigo_sap)  {
        CentroAlmacen centroAlmacen= this.centroAlmacenRepository.getByNivelCodigoSap(codigo_sap);
        return ResponseEntity.ok().body(centroAlmacen);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<CentroAlmacen>> gelAllCentros() {
        List<CentroAlmacen> lisCentroAlmacen = this.centroAlmacenRepository.findAll();
        return ResponseEntity.ok(lisCentroAlmacen);
    }
}
