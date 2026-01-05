package com.incloud.hcp.rest;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionAdjunto;
import com.incloud.hcp.repository.LicitacionAdjuntoRepository;
import com.incloud.hcp.rest._framework.AppRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by USER on 04/09/2017.
 */
@RestController
@RequestMapping(value = "/api/licitacionAdjunto")
public class LicitacionAdjuntoRest extends AppRest {

    @Autowired
    private LicitacionAdjuntoRepository licitacionAdjuntoRepository;

    @RequestMapping(value = "/findByLicitacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<LicitacionAdjunto>> findByLicitacion(@RequestBody Integer idLicitacion)  {
        Licitacion licitacion = new Licitacion();
        licitacion.setIdLicitacion(idLicitacion);
        List<LicitacionAdjunto> lista = this.licitacionAdjuntoRepository.findByLicitacionOrderByArchivoNombre(licitacion);
        return ResponseEntity.ok().body(lista);
    }


}
