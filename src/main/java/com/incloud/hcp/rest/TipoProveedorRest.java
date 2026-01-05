package com.incloud.hcp.rest;

import com.incloud.hcp.domain.TipoProveedor;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.repository.TipoProveedorRepository;
import com.incloud.hcp.rest._framework.AppRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value="/api/tipo-proveedor")
public class TipoProveedorRest extends AppRest {

    @Autowired
    private TipoProveedorRepository tipoProveedorRepository;


    @RequestMapping(value = "",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<TipoProveedor>> getListAll() throws PortalException {
        List listaTipo = this.tipoProveedorRepository.findAll();
        return ResponseEntity.ok().body(listaTipo);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getById(@PathVariable Integer id) throws PortalException {
        Optional<TipoProveedor> tipoProveedorOptional = this.tipoProveedorRepository.findById(id);
        Map response = new HashMap<>();
        response.put("total", tipoProveedorOptional.isPresent() ? 1 : 0);
        response.put("data", tipoProveedorOptional.isPresent() ? tipoProveedorOptional.get() : new TipoProveedor());
        return tipoProveedorOptional.isPresent() ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
