package com.incloud.hcp.rest;

import com.incloud.hcp.domain.Cotizacion;
import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionProveedor;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.CotizacionDto;
import com.incloud.hcp.repository.CotizacionRepository;
import com.incloud.hcp.repository.LicitacionProveedorRepository;
import com.incloud.hcp.repository.LicitacionRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.rest.bean.CotizacionEnviarCotizacionDTO;
import com.incloud.hcp.rest.bean.CotizacionGrabarDTO;
import com.incloud.hcp.service.CotizacionService;
import com.incloud.hcp.service.delta.LicitacionSubetapaDeltaService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by USER on 29/08/2017.
 */
@RestController
@RequestMapping(value = "/api/cotizacion")
public class CotizacionRest extends AppRest {

    private static Logger logger = LoggerFactory.getLogger(CotizacionRest.class);

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private CotizacionService cotizacionService;

    @Autowired
    private LicitacionProveedorRepository licitacionProveedorRepository;

    @Autowired
    private LicitacionRepository licitacionRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private LicitacionSubetapaDeltaService licitacionSubetapaDeltaService;

    @RequestMapping(value = "/findByLicitacionAndSort", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Cotizacion>> findByLicitacionAndSort(@RequestBody Integer idLicitacion)  {
        Licitacion licitacion = new Licitacion();
        licitacion.setIdLicitacion(idLicitacion);
        Sort sort1 = Sort.by(new Sort.Order(Sort.Direction.ASC, "proveedor.razonSocial"));
        List<Cotizacion> lista = this.cotizacionRepository.
                findByLicitacionAndSort(licitacion, sort1);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByLicitacionAndProveedorSort", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Cotizacion> findByLicitacionAndProveedorSort(@RequestBody Cotizacion cotizacion)  {
        Cotizacion cotizacionResult = new Cotizacion();
        Licitacion licitacion = new Licitacion();
        licitacion.setIdLicitacion(cotizacion.getLicitacion().getIdLicitacion());

        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(cotizacion.getProveedor().getIdProveedor());
        Sort sort1 = Sort.by(new Sort.Order(Sort.Direction.ASC, "proveedor.razonSocial"));
        List<Cotizacion> lista = this.cotizacionRepository.
                findByLicitacionAndProveedorSort(licitacion, proveedor, sort1);
        if (lista != null && lista.size()> 0) {
            cotizacionResult = lista.get(0);
        }
        return ResponseEntity.ok().body(cotizacionResult);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> save(@RequestBody CotizacionGrabarDTO dto)  {
        CotizacionGrabarDTO beanGrabar = this.cotizacionService.save(dto);
        Integer idCotizacion = beanGrabar.getCotizacionResult().getIdCotizacion();
        return ResponseEntity.ok().body(idCotizacion);

    }

    @RequestMapping(value = "/enviarCotizacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> enviarCotizacion(@RequestBody CotizacionEnviarCotizacionDTO dto)  {
        CotizacionEnviarCotizacionDTO beanGrabar = this.cotizacionService.enviarCotizacion(dto);
        Integer idCotizacion = beanGrabar.getCotizacionResult().getIdCotizacion();
        return ResponseEntity.ok().body(idCotizacion);

    }

    @RequestMapping(value = "/noParticiparCotizacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> noParticiparCotizacion(@RequestBody CotizacionEnviarCotizacionDTO dto)  {
        CotizacionEnviarCotizacionDTO beanGrabar = this.cotizacionService.noParticiparCotizacion(dto);
        Integer idCotizacion = beanGrabar.getCotizacionResult().getIdCotizacion();
        return ResponseEntity.ok().body(idCotizacion);

    }

    @RequestMapping(value = "/siParticiparCotizacion/{idLicitacion}/{idProveedor}", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<LicitacionProveedor> siParticiparCotizacion(
            @PathVariable Integer idLicitacion,
            @PathVariable Integer idProveedor
    )  {
        try {
            LicitacionProveedor beanGrabar = this.cotizacionService.siParticiparCotizacion(
                    idLicitacion,
                    idProveedor);
            this.licitacionSubetapaDeltaService.enviarCorreoRecordatorio();

            return ResponseEntity.ok().body(beanGrabar);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }


    }


    @RequestMapping(value = "/lista-cotizacion/licitacion/{idLicitacion}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Cotizacion>> getListCotizacionByLicitacion(@PathVariable Integer idLicitacion)  {
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        List<Cotizacion> lista = this.cotizacionService.getListCotizacionByLicitacion(idLicitacion);
        List<Cotizacion> listaFinal = new ArrayList<Cotizacion>();
        logger.error("INI getListCotizacionByLicitacion licitacion: " + licitacion.toString());
        for(Cotizacion cotizacion : lista) {
            Proveedor proveedor = cotizacion.getProveedor();
            proveedor = this.proveedorRepository.getOne(proveedor.getIdProveedor());
            Cotizacion beanCotizacionFinal = this.cotizacionRepository.
                   getByLicitacionAndProveedor(licitacion, proveedor);


            logger.error("getListCotizacionByLicitacion proveedor: " + proveedor.toString());
            LicitacionProveedor licitacionProveedor = this.licitacionProveedorRepository.
                    getByLicitacionAndProveedor(licitacion, proveedor);
            logger.error("getListCotizacionByLicitacion licitacionProveedor: " + licitacionProveedor.toString());
            if (Optional.ofNullable(licitacionProveedor).isPresent()) {
                Date fechaCierreRecepcion = licitacionProveedor.getFechaCierreRecepcion();
                String sFechaCierreRecepcion = DateUtils.convertDateToString("dd/MM/yyyy HH:mm:ss", fechaCierreRecepcion);
                licitacionProveedor.setFechaCierreRecepcionProveedorString(sFechaCierreRecepcion);
            }
            if (Optional.ofNullable(beanCotizacionFinal).isPresent()) {
                beanCotizacionFinal.setLicitacionProveedor(licitacionProveedor);
                listaFinal.add(beanCotizacionFinal);
            }
            else {
                cotizacion.setLicitacionProveedor(licitacionProveedor);
                listaFinal.add(cotizacion);
            }
        }

        logger.error("FIN getListCotizacionByLicitacion listaFinal: " + listaFinal.toString());
        return Optional.of(listaFinal)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

//    @RequestMapping(value = "/cotizacion/proveedor/{idProveedor}/licitacion/{idLicitacion}", method = RequestMethod.GET, produces = {
//            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public ResponseEntity<Cotizacion> getListCotizacionByLicitacion(@PathVariable Integer idProveedor,@PathVariable Integer idLicitacion)  {
//        return Optional.of(this.cotizacionService.getCotizacionByProveedorandLicitacion(idLicitacion,idProveedor))
//                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
//    }

    @RequestMapping(value = "/cotizacion/proveedor/{idProveedor}/licitacion/{idLicitacion}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CotizacionDto> getListCotizacionByLicitacion(@PathVariable Integer idProveedor, @PathVariable Integer idLicitacion)  {
        return Optional.of(this.cotizacionService.getCotizacionDtoByProveedorandLicitacion(idLicitacion,idProveedor))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}

