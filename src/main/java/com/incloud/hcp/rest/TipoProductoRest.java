package com.incloud.hcp.rest;

import com.incloud.hcp.domain.balanza.TipoProducto;
import com.incloud.hcp.repository.TipoProductoRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.TipoProductoService;
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
@RequestMapping(value = "/api/TipoProducto")
public class TipoProductoRest extends AppRest {

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Autowired
    private TipoProductoService tipoProductoService;
    private final Logger log = LoggerFactory.getLogger(TipoProductoRest.class);
    @GetMapping(value = "/findAll", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TipoProducto>> findAll() throws URISyntaxException {
        List <TipoProducto> list = new ArrayList<>();
        try{
            list = tipoProductoRepository.listaTipoProductosActivos();
            return Optional.ofNullable(new ResponseEntity<>(list, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch (Exception e){
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }

    }
    @PostMapping(value = "/save", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> save(@RequestBody TipoProducto tipoProducto)
            throws URISyntaxException {

        Map map = new HashMap<>();

        try{
            if(tipoProducto.getId() == null){
                List<TipoProducto> producto = this.tipoProductoRepository.findByCodigoList(tipoProducto.getCodigo());

                if (!producto.isEmpty()) {
                    map.put("mensaje:", "Ya existe un producto con el código:" + tipoProducto.getCodigo());
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }

                tipoProducto.setFechaCreacion(DateUtils.getCurrentTimestamp());
                tipoProducto.setFechaModificacion(null);
                tipoProducto.setEstado(true);
                tipoProducto.setDeleted(false);
            }else{
                TipoProducto productoBD = tipoProductoRepository.findById(tipoProducto.getId())
                        .orElseThrow(()-> new RuntimeException("Producto no encontrado"));

                if(!productoBD.getCodigo().equals(tipoProducto.getCodigo())){
                    List<TipoProducto> otrosProductos = this.tipoProductoRepository.findByCodigoList(tipoProducto.getCodigo());
                    boolean existeEnOtro = otrosProductos.stream()
                            .anyMatch(p -> !p.getId().equals(tipoProducto.getId()));
                    if (existeEnOtro) {
                        map.put("mensaje:", "No se puede actualizar. Ya existe otro producto con el código: " + tipoProducto.getCodigo());
                        return new ResponseEntity<>(map, HttpStatus.OK);
                    }
                }
                Timestamp fechaCreacion = productoBD.getFechaCreacion();
                tipoProducto.setFechaModificacion(DateUtils.getCurrentTimestamp());
                tipoProducto.setFechaCreacion(fechaCreacion);
                tipoProducto.setDeleted(false);
            }

            this.tipoProductoRepository.save(tipoProducto);
            map.put("mensaje:", tipoProducto.getId() == null ? "Producto creado" : "Producto actualizado");
            map.put("data:", tipoProducto);
            return new ResponseEntity<>(map, HttpStatus.OK);

        }catch(Exception e){
            map.put("mensaje:", "No pudo procesarse el producto: " + e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
    @PostMapping(value ="cambiar-estado-tipo-producto", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> cambiarEstadoTipoProducto(Integer tipoProductoId){
        Map map = new HashMap<>();
        try{
            TipoProducto tipoProducto= tipoProductoService.cambiarEstado(tipoProductoId);

            map.put("data",tipoProducto);
            map.put("mensaje", "Se cambio el estado" );
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e){
            map.put("mensaje","Error al cambiar el estado");
            throw new RuntimeException(e);
        }
    }
    @PostMapping(value ="eliminar-tipo-producto", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> eliminarTipoProducto(Integer tipoProductoId){
        Map map = new HashMap<>();
        try{
            tipoProductoService.eliminarTipo(tipoProductoId);

            map.put("data","");
            map.put("mensaje", "Se eliminó el tipo de producto" );
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e){
            map.put("mensaje","Error al cambiar el estado");
            throw new RuntimeException(e);
        }
    }
    @GetMapping(value ="listar-tipo-producto", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> listarTipoProducto(){
        Map map = new HashMap<>();
        try{
            List<TipoProducto> tipoProducto = tipoProductoService.getAllTipoProducto();
            map.put("data",tipoProducto);
            map.put("mensaje", "Tipo Producto listados" );
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e){
            map.put("mensaje","Error al listar Tipo Producto");
            throw new RuntimeException(e);
        }
    }
}
