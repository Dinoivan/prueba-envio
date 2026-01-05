package com.incloud.hcp.rest;

import com.incloud.hcp.domain.balanza.TipoPesaje;
import com.incloud.hcp.repository.TipoPesajeRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.TipoPesajeService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/TipoPesaje")
public class TipoPesajeRest extends AppRest {

    @Autowired
    private TipoPesajeRepository tipoPesajeRepository;
    @Autowired
    private TipoPesajeService tipoPesajeService;
    private final Logger log = LoggerFactory.getLogger(TipoPesajeRest.class);
    @GetMapping(value = "/findAll", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TipoPesaje>> findAll() throws URISyntaxException {
        List <TipoPesaje> list = new ArrayList<>();
        try{
            list = tipoPesajeRepository.listarPesajesActivos();
            return Optional.ofNullable(new ResponseEntity<>(list, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch (Exception e){
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }

    }
    @PostMapping(value = "/save", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> save(@RequestBody TipoPesaje tipoPesaje)
            throws URISyntaxException {
        Map map = new HashMap<>();

        try{

            if(tipoPesaje.getId() == null){
                List<TipoPesaje> pesaje= this.tipoPesajeRepository.findByCodigoList(tipoPesaje.getCodigo());
                if(!pesaje.isEmpty()){
                    map.put("mensaje:", "Ya existe un pesaje con el código:" +tipoPesaje.getCodigo());
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }
                tipoPesaje.setFechaCreacion(DateUtils.getCurrentTimestamp());
                tipoPesaje.setFechaModificacion(null);
                tipoPesaje.setEstado(true);
                tipoPesaje.setDeleted(false);

            }else{
                TipoPesaje pesajeBD = tipoPesajeRepository.findById(tipoPesaje.getId())
                        .orElseThrow(()-> new RuntimeException("Pesaje no encontrado"));

                if (!pesajeBD.getCodigo().equals(tipoPesaje.getCodigo())) {
                    List<TipoPesaje> otrosPesajes = this.tipoPesajeRepository.findByCodigoList(tipoPesaje.getCodigo());
                    boolean existeEnOtro = otrosPesajes.stream()
                            .anyMatch(p -> !p.getId().equals(tipoPesaje.getId()));
                    if (existeEnOtro) {
                        map.put("mensaje:", "No se puede actualizar. Ya existe otro pesaje con el código: " + tipoPesaje.getCodigo());
                        return new ResponseEntity<>(map, HttpStatus.OK);
                    }
                }

                Timestamp fechaCreacion = pesajeBD.getFechaCreacion();
                tipoPesaje.setFechaModificacion(DateUtils.getCurrentTimestamp());
                tipoPesaje.setFechaCreacion(fechaCreacion);
                tipoPesaje.setDeleted(false);
            }

            this.tipoPesajeRepository.save(tipoPesaje);
            map.put("mensaje:", tipoPesaje.getId() == null ? "Pesaje creado" : "Pesaje actualizado");
            map.put("data:", tipoPesaje);
            return new ResponseEntity<>(map, HttpStatus.OK);

        }catch(Exception e){
            map.put("mensaje:", "No pudo procesarse el pesaje: " + e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
    @PostMapping(value ="cambiar-estado-tipo-pesaje", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> cambiarEstadoTipoPesaje(Integer tipoPesajeId){
        Map map = new HashMap<>();
        try{
            TipoPesaje tipoPesaje= tipoPesajeService.cambiarEstado(tipoPesajeId);

            map.put("data",tipoPesaje);
            map.put("mensaje", "Se cambio el estado" );
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e){
            map.put("mensaje","Error al cambiar el estado");
            throw new RuntimeException(e);
        }
    }
    @GetMapping(value ="listar-tipo-pesaje", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> listarTipoPesaje(){
        Map map = new HashMap<>();
        try{
            List<TipoPesaje> tipoPesaje = tipoPesajeRepository.listarPesajes();
            map.put("data",tipoPesaje);
            map.put("mensaje", "Tipo Pesaje listados" );
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e){
            map.put("mensaje","Error al listar Tipo Pesaje");
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value ="eliminar-tipo-pesaje", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> eliminarTipoPesaje(Integer tipoPesajeId){
        Map map = new HashMap<>();
        try{
            TipoPesaje tipoPesaje= tipoPesajeService.eliminarEstado(tipoPesajeId);

            map.put("data",tipoPesaje);
            map.put("mensaje", "Se eliminó correctamente" );
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e){
            map.put("mensaje","Error al cambiar el estado");
            throw new RuntimeException(e);
        }
    }
}
