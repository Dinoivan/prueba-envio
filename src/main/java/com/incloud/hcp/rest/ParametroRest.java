package com.incloud.hcp.rest;

import com.incloud.hcp.bean.Area;
import com.incloud.hcp.bean.EstadoLicitacion;
import com.incloud.hcp.bean.TipoCuenta;
import com.incloud.hcp.domain.Parametro;
import com.incloud.hcp.repository.ParametroRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.ParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Administrador on 23/08/2017.
 */
@RestController
@RequestMapping(value = "/api/parametro")
public class ParametroRest extends AppRest {
    @Autowired
    private ParametroService parametroService;

    @Autowired
    private ParametroRepository parametroRepository;

    @RequestMapping(value = "/area", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getListAreaAll() {

        Optional<List<Area>> lista = Optional.of(this.parametroService.getListAreaAll());
        Map response = new HashMap<>();
        response.put("total", lista.isPresent() ? lista.get().size() : 0);
        response.put("data", lista.isPresent() ? lista.get() : new ArrayList<>());
        return lista.isPresent() ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/moduloandtipo/{modulo}/{tipo}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Parametro>> getListParametroByModuloAndTipo(@PathVariable String modulo,@PathVariable String tipo) {

        List<Parametro> lista = this.parametroService.getByModuloandTipo(modulo,tipo);
        return ResponseEntity.ok().body(lista);
    }



    @RequestMapping(value = "/tipo-cuenta", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getListTipoCuentaAll() {
        Optional<List<TipoCuenta>> lista = Optional.of(this.parametroService.getListTipoCuentaAll());
        Map response = new HashMap<>();
        response.put("total", lista.isPresent() ? lista.get().size() : 0);
        response.put("data", lista.isPresent() ? lista.get() : new ArrayList<>());
        return lista.isPresent() ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/estado-licitacion", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<EstadoLicitacion>> getListEstadoLicitacionAll() {
        return Optional.of(this.parametroService.getListEstadoLicitacionAll())
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    @RequestMapping(value = "/findByModuloAndTipoAndCodigo", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Parametro>> findByModuloAndTipoAndCodigo(@RequestBody Map<String, String> json)  {
        String modulo = json.get("modulo");
        String tipo = json.get("tipo");
        String codigo = json.get("codigo");
        return Optional.of(this.parametroRepository.findByModuloAndTipoAndCodigo(modulo,tipo,codigo))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/findByModuloAndTipoAndValorAsociado", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Parametro>> findByModuloAndTipoAndValorAsociado(@RequestBody Map<String, String> json)  {
        String modulo = json.get("modulo");
        String tipo = json.get("tipo");
        String valorAsociado = json.get("valorAsociado");
        return Optional.of(this.parametroRepository.findByModuloAndTipoAndValorAsociado(modulo,tipo,valorAsociado))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/findByModuloAndTipo", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Parametro>> findByModuloAndTipo(@RequestBody Map<String, String> json)  {
        String modulo = json.get("modulo");
        String tipo = json.get("tipo");
        return Optional.of(this.parametroRepository.findByModuloAndTipoOrderByValor(modulo,tipo))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/findByModuloAndTipoBlank", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Parametro>> findByModuloAndTipoBlank(@RequestBody Map<String, String> json)  {
        String modulo = json.get("modulo");
        String tipo = json.get("tipo");
        List<Parametro> parametroList = this.parametroRepository.findByModuloAndTipoOrderByValor(modulo,tipo);
        Parametro parametroBlank = new Parametro();
        parametroBlank.setIdParametro(0);
        parametroBlank.setDescripcion("  ");
        List<Parametro> parametroListFinal = new ArrayList<Parametro>();
        parametroListFinal.add(parametroBlank);

        for(Parametro bean : parametroList) {
            parametroListFinal.add(bean);
        }
        return Optional.of(parametroListFinal)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }



}
