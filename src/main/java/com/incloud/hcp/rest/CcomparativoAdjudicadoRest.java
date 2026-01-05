package com.incloud.hcp.rest;

import com.incloud.hcp.domain.CcomparativoAdjudicado;
import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.repository.CcomparativoAdjudicadoRepository;
import com.incloud.hcp.rest._framework.AppRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by USER on 07/11/2017.
 */
@RestController
@RequestMapping(value = "/api/ccomparativoAdjudicado")
public class CcomparativoAdjudicadoRest extends AppRest {

    @Autowired
    private CcomparativoAdjudicadoRepository  ccomparativoAdjudicadoRepository;

    @RequestMapping(value = "/findByCcomparativoAdjudicado/{idLicitacion}", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<CcomparativoAdjudicado>> findByCcomparativoAdjudicado(@RequestBody Integer idLicitacion) {
        Licitacion licitacion = new Licitacion();
        licitacion.setIdLicitacion(idLicitacion);

        List<CcomparativoAdjudicado> lista = this.ccomparativoAdjudicadoRepository.findByLicitacionOrdenado(licitacion);
        return ResponseEntity.ok().body(lista);
    }




}
