package com.incloud.hcp.rest;

import com.incloud.hcp.domain.balanza.CentroAlmacenBalanza;
import com.incloud.hcp.jco.balanza.centroCorrelativo.dto.SapTableCentroCorrelativoBlz;
import com.incloud.hcp.jco.balanza.centroCorrelativo.service.JCOCentroCorrelativoBlzService;
import com.incloud.hcp.repository.CentroAlmacenBalanzaRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.CentroAlmacenBalanzaService;
import com.sap.cloud.security.xsuaa.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/centro-almacen-balanza")
public class CentroAlmacenBalanzaRest extends AppRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CentroAlmacenBalanzaRepository centroAlmacenBalanzaRepository;
    @Autowired
    private JCOCentroCorrelativoBlzService jcoCentroCorrelativoBlzService;

    @Autowired
    private CentroAlmacenBalanzaService centroAlmacenBalanzaService;

    @PostMapping(value = "/guardar-actualizar-centro", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> actualizaGuardaCentro(@RequestBody CentroAlmacenBalanza centroAlmacenBalanza){
        logger.error("[actualizaGuardaCentro]:Inicio");
        List<CentroAlmacenBalanza> listcentros = centroAlmacenBalanzaRepository.buscarUsuario(
                centroAlmacenBalanza.getUsuario());
        logger.error("[actualizaGuardaCentro]:listcentros:{}", listcentros);
        Map map = new HashMap<>();

        if(!listcentros.isEmpty() && centroAlmacenBalanza.getId()== null){
            map.put("mensaje", "Ya existe un centro con el usuario:" +centroAlmacenBalanza.getUsuario());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }else{
            try{
                CentroAlmacenBalanza centro= centroAlmacenBalanzaService.guardarActualizarCentro(centroAlmacenBalanza);
                map.put("mensaje", "Centro creado o actualizado");
                map.put("data", centro);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            catch( Exception error){
                map.put("mensaje","No pudo crearse el centro");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }
    }

    @PostMapping(value ="cambiar-estado-centro", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> cambiarEstadoCentro(Integer centroAlmacenBalanzaId){
        Map map = new HashMap<>();
        try{
            CentroAlmacenBalanza centro= centroAlmacenBalanzaService.cambiarEstado(centroAlmacenBalanzaId);

            map.put("data",centro);
            map.put("mensaje", "Se cambio el estado" );
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e){
            map.put("mensaje","Error al cambiar el estado");
            throw new RuntimeException(e);
        }
    }
    @PostMapping(value ="eliminar-usuario-Centro-estado-centro", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> eliminarUsuarioCentro(Integer centroAlmacenBalanzaId){
        Map map = new HashMap<>();
        try{
            centroAlmacenBalanzaRepository.deleteByUsuario(centroAlmacenBalanzaId);

            map.put("data","");
            map.put("mensaje", "Se eliminó el usuario del centro" );
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e){
            map.put("mensaje","Error al cambiar el estado");
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value ="listar-centros", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> listarCentros(){
        Map map = new HashMap<>();
        try{
            List<CentroAlmacenBalanza> lista = centroAlmacenBalanzaRepository.findAll();
            map.put("data",lista);
            map.put("mensaje", "Centros listados" );
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e){
            map.put("mensaje","Error al listar centros");
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value ="listar-centros-testRole", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> listarCentros(@AuthenticationPrincipal Token token){

        //this.validarPermisoEjecucionServicio(token,"listar-centros-testRole","GET");
        /*UserSession userSession = this.getUserSession(token);
        List<String> list = new ArrayList<>();
        userSession.getListaGrupos().forEach(x -> list.add(x.getValue()));*/
        /*
        for (GroupDto x: userSession.getListaGrupos() ) {
            list.add(x.getValue());
        }
         */
        /*logger.error("Error list: "+list.size()+"  ***:"+list.toString());
        if(rolServicioAutorizacionRepository.getAutorizacionByRol("listar-centros-testRole","GET",list).size()==0){
            throw new RuntimeException();
        }else {*/
            Map map = new HashMap<>();
            try {
                List<CentroAlmacenBalanza> lista = centroAlmacenBalanzaRepository.findAll();
                map.put("data", lista);
                map.put("mensaje", "Centros listados");
                return new ResponseEntity<>(map, HttpStatus.OK);
            } catch (Exception e) {
                map.put("mensaje", "Error al listar centros");
                throw new RuntimeException(e);
            }
        //}
    }


    @GetMapping(value ="listar-centro-correlativos/{centro}", produces = APPLICATION_JSON_VALUE)
    public List<SapTableCentroCorrelativoBlz> listarCentroCorrelativos(@PathVariable String centro) throws Exception{
        return jcoCentroCorrelativoBlzService.extraerCorrelativo(centro);

    }

}
