package com.incloud.hcp.rest;

import com.incloud.hcp.domain.LicitacionProveedorRenegociacion;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.repository.LicitacionProveedorRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.LicitacionProveedorService;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Created by USER on 21/09/2017.
 */

@RestController
@RequestMapping(value = "/api/licitacionProveedor")
public class LicitacionProveedorRest extends AppRest {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LicitacionProveedorRepository licitacionProveedorRepository;

    @Autowired
    private LicitacionProveedorService licitacionProveedorService;

    @RequestMapping(value = "/devuelveCountByEstadoLicitacionProveedor",  method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> devuelveCountByEstadoLicitacionProveedor(@RequestBody Map<String, Object> json)  {
        String estadoLicitacion = (String)json.get("estadoLicitacion");
        Integer idProveedor = (Integer)json.get("idProveedor");

        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(idProveedor);

        Integer contador = this.licitacionProveedorRepository.countByProveedorAndEstadoLicitacion(proveedor, estadoLicitacion);
        return ResponseEntity.ok().body(contador);
    }

    @RequestMapping(value = "/devuelveCountByNotEstadoLicitacionProveedor",  method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> devuelveCountByNotEstadoLicitacionProveedor(@RequestBody Map<String, Object> json)  {
        String estadoLicitacion = (String)json.get("estadoLicitacion");
        Integer idProveedor = (Integer)json.get("idProveedor");


        Integer idProvedor = new Integer(idProveedor);
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(idProvedor);

        Integer contador = this.licitacionProveedorRepository.countByProveedorAndNotEstadoLicitacion(proveedor, estadoLicitacion);
        return ResponseEntity.ok().body(contador);
    }

    @RequestMapping(value = "/validarNuevaRenegociacion/{idLicitacion}/{idProveedor}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<LicitacionProveedorRenegociacion> validarNuevaRenegociacion(
            @PathVariable Integer idLicitacion,
            @PathVariable Integer idProveedor)  {
        log.debug("Ingresando validarNuevaRenegociacion");
        try {
            return Optional.ofNullable(this.licitacionProveedorService.validarNuevaRenegociacion(idLicitacion, idProveedor))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }
        catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }

    }




}
