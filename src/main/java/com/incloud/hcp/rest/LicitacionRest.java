package com.incloud.hcp.rest;

import com.incloud.hcp.bean.LicitacionRequest;
import com.incloud.hcp.config.excel.ExcelType;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.dto.LicitacionAdjudicadoDTO;
import com.incloud.hcp.dto.mail.ReenvioProveedor;
import com.incloud.hcp.myibatis.mapper.LicitacionMapper;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.rest.bean.LicitacionCotizacionDTO;
import com.incloud.hcp.service.BienServicioService;
import com.incloud.hcp.service.LicitacionService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import com.sap.cloud.security.xsuaa.token.Token;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by USER on 23/08/2017.
 */
@RestController
@RequestMapping(value = "/api/licitacion")
public class LicitacionRest extends AppRest {

    private static final Logger logger = LoggerFactory.getLogger(LicitacionRest.class);

    @Autowired
    private LicitacionRepository licitacionRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private LicitacionService licitacionService;

    @Autowired
    private LicitacionMapper licitacionMapper;

    @Autowired
    private BienServicioService bienServicioService;

    @Autowired
    private LicitacionProveedorRepository licitacionProveedorRepository;

    @Autowired
    private ParametroRepository parametroRepository;



    @RequestMapping(value = "/actualizarLicitacionPorEvaluar", method = RequestMethod.GET, headers = "Accept=application/json")
    public String ActualizarLicitacionPorEvaluar()  {
        try {
            logger.error("Inicio ActualizarLicitacionPorEvaluar : ");
            this.licitacionService.updateLicitacionEstadoPorEvaluar();
            logger.error("Fin ActualizarLicitacionPorEvaluar :");
        }
        catch (Exception e) {
            return "ERROR: ActualizarLicitacionPorEvaluar";
        }

        return "success ActualizarLicitacionPorEvaluar";
    }

    @RequestMapping(value = "/sincronizarBienServicio", method = RequestMethod.GET, headers = "Accept=application/json")
    public String sincronizarBienServicio()  {
        try {
            logger.error("Inicio sincronizarBienServicio : ");
            this.bienServicioService.sincronizarBienServicioByLastDate();
            logger.error("Fin sincronizarBienServicio :");
        }
        catch (Exception e) {
            return "ERROR: sincronizarBienServicio: " + Utils.obtieneMensajeErrorException(e);
        }

        return "success sincronizarBienServicio";
    }


    @RequestMapping(value = "/devuelveCountByEstadoLicitacion/{estado}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Integer> devuelveCountByEstadoLicitacion2(@PathVariable String estado)  {
        Integer contador = this.licitacionRepository.countByEstadoLicitacion(estado);
        return ResponseEntity.ok().body(contador);
    }

    @RequestMapping(value = "/devuelveCountByEstadoLicitacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> devuelveCountByEstadoLicitacion(@RequestBody String estado)  {
        Integer contador = this.licitacionRepository.countByEstadoLicitacion(estado);
        return ResponseEntity.ok().body(contador);
    }

    @RequestMapping(value = "/devuelveCountByEstadoLicitacionNot", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Long> devuelveCountByEstadoLicitacionNot(@RequestBody String estado)  {
        Long contador = this.licitacionRepository.countByEstadoLicitacionNot(estado);
        return ResponseEntity.ok().body(contador);
    }

    @RequestMapping(value = "/filtro", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Licitacion>> devuelveListaLicitacionByFiltro(@RequestBody Map<String, Object> json)  {
        List<Licitacion> lista = licitacionService.getListaLicitacionByFiltro(json);
        //return ResponseEntity.ok().body(lista);
        return Optional.of(lista)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/filtroPaginado", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Licitacion>> devuelveListaLicitacionByFiltroPaginado(@RequestBody Map<String, Object> json)  {
        List<Licitacion> lista = licitacionService.getListaLicitacionByFiltroPaginado(json);
        //return ResponseEntity.ok().body(lista);
        return Optional.of(lista)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/findByEstadoLicitacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Licitacion>> findByEstadoLicitacion(@RequestBody String estado)  {
        Sort sort1 = Sort.by(new Sort.Order(Sort.Direction.DESC, "nroLicitacion"));
        List<Licitacion> lista= this.licitacionRepository.findByEstadoLicitacionAndSort(estado,sort1);
        return ResponseEntity.ok().body(lista);
    }


    @RequestMapping(value = "/findByFiltroBusqueda", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Licitacion>> findByFiltroBusqueda(@RequestBody Map<String, Object> json)   {
        List<Licitacion> lista = this.getLicitacions(json);
       return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByFiltroBusquedaPaginado", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Licitacion>> findByFiltroBusquedaPaginado(@RequestBody Map<String, Object> json)   {
        List<Licitacion> lista = this.getLicitacionsPaginado(json);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByFiltroBusquedaEstadoTerminal", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Licitacion>> findByFiltroBusquedaEstadoTerminal(@RequestBody Map<String, Object> json)   {
        List<Licitacion> lista = this.getLicitacionsEstadoTerminal(json);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByFiltroBusquedaEstadoTerminalPaginado", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Licitacion>> findByFiltroBusquedaEstadoTerminalPaginado(@RequestBody Map<String, Object> json)   {
        List<Licitacion> lista = this.getLicitacionsEstadoTerminalPaginado(json);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByFiltroBusquedaAdjudicadoProveedor", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<Licitacion>> getListLicitacionrByFiltroBusquedaAdjudicadoProveedor(@RequestBody Map<String, Object> json)   {
        List<Licitacion> lista = this.getLicitacionsAdjudicadoProveedor(json);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByFiltroBusquedaAdjudicadoProveedor02", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<LicitacionAdjudicadoDTO>> getListLicitacionrByFiltroBusquedaAdjudicadoProveedor02(@RequestBody Map<String, Object> json)   {
        List<LicitacionAdjudicadoDTO> lista = this.getLicitacionsAdjudicadoProveedor02(json);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByFiltroBusquedaAdjudicadoProveedorPaginado", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<LicitacionAdjudicadoDTO>> getListLicitacionrByFiltroBusquedaAdjudicadoProveedorPaginado(
            @RequestBody Map<String, Object> json)   {
        List<LicitacionAdjudicadoDTO> lista = this.getLicitacionsAdjudicadoProveedorPaginado(json);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByFiltroBusquedaAndCotizacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<LicitacionCotizacionDTO>> findByFiltroBusquedaAndCotizacion(@RequestBody Map<String, Object> json)   {
        Integer idProveedor = null;
        String sidProveedor = "";
        try {
            idProveedor = (Integer) json.get("idProveedor");
        }
        catch(Exception e) {
            sidProveedor = (String) json.get("idProveedor");
            sidProveedor = sidProveedor.trim();
            idProveedor =  new Integer(sidProveedor);
        }

        Proveedor proveedor = this.proveedorRepository.getOne(idProveedor);
        List<Licitacion> listaLicitacion = this.getLicitacions(json);
        List<LicitacionCotizacionDTO> listaDTO = new ArrayList<LicitacionCotizacionDTO>();
        for(Licitacion item : listaLicitacion ) {
            LicitacionCotizacionDTO bean = new LicitacionCotizacionDTO();
            bean.setLicitacion(item);

            Cotizacion cotizacion = new Cotizacion();
            cotizacion.setIdCotizacion(null);
            Sort sort1 = Sort.by(new Sort.Order(Sort.Direction.ASC, "proveedor.razonSocial"));
            List<Cotizacion> lista = this.cotizacionRepository.
                    findByLicitacionAndProveedorSort(item, proveedor, sort1);
            if (lista != null && lista.size()> 0) {
                cotizacion = lista.get(0);
                bean.setCotizacion(cotizacion);
            }
            LicitacionProveedor licitacionProveedor = this.licitacionProveedorRepository.getByLicitacionAndProveedor(item, proveedor);
            if (Optional.ofNullable(licitacionProveedor).isPresent()) {
                Date fechaCierreRecepcion = licitacionProveedor.getFechaCierreRecepcion();
                String sFechaCierreRecepcion = DateUtils.convertDateToString("dd/MM/yyyy HH:mm:ss", fechaCierreRecepcion);
                licitacionProveedor.setFechaCierreRecepcionProveedorString(sFechaCierreRecepcion);
                bean.setLicitacionProveedor(licitacionProveedor);
            }
            listaDTO.add(bean);
        }
        return ResponseEntity.ok().body(listaDTO);
    }


    private List<Licitacion> getLicitacions(@RequestBody Map<String, Object> json) {
        //String estadoLicitacion = LicitacionConstant.ESTADO_POR_EVALUAR;
        String estadoLicitacion = (String)json.get("estadoLicitacion");
        String estadoLicitacionLectura = (String)json.get("estadoLicitacionLectura");

        String puntoEntrega = (String)json.get("puntoEntrega");
        String numeroLicitacion = (String)json.get("numeroLicitacion");
        if(numeroLicitacion != null) {
            if (numeroLicitacion.length() > 8)
                numeroLicitacion = numeroLicitacion.substring(4);
        }
        String moneda = (String)json.get("moneda");

        String solicitudPedido = (String) json.get("solicitudPedido");
        String codigoSap = (String) json.get("codigoSap");
        ArrayList<String> listaCentroLogistico = (ArrayList<String>)json.get("listaCentroLogistico");
        ArrayList<String> listaAlmacen = (ArrayList<String>)json.get("listaAlmacen");

        ArrayList<String> listaTipoLicitacion = (ArrayList<String>)json.get("listaTipoLicitacion");
        ArrayList<String> listaTipoCuestionario = (ArrayList<String>)json.get("listaTipoCuestionario");

        Integer idProveedor = null;
        String sidProveedor = "";
        try {
            idProveedor = (Integer) json.get("idProveedor");
        }
        catch(Exception e) {
            sidProveedor = (String) json.get("idProveedor");
            sidProveedor = sidProveedor.trim();
            idProveedor =  new Integer(sidProveedor);
        }
        String numeroRUC = (String)json.get("numeroRUC");
        String razonSocial = (String)json.get("razonSocial");
        ArrayList<String> listaRegion = (ArrayList<String>)json.get("listaRegion");
        String usuarioPublicacion = (String)json.get("usuarioPublicacion"); //+@JEP
        String emailUsuario = (String)json.get("emailUsuario");
        return this.licitacionMapper.getListLicitacionrByFiltroBusqueda(
                estadoLicitacion,
                estadoLicitacionLectura,
                numeroLicitacion,
                puntoEntrega,
                moneda,
                listaCentroLogistico,
                listaAlmacen,
                solicitudPedido,
                codigoSap,
                listaTipoLicitacion,
                listaTipoCuestionario,
                idProveedor,
                numeroRUC,
                razonSocial,
                listaRegion,
                usuarioPublicacion,
                emailUsuario);
    }

    private List<Licitacion> getLicitacionsPaginado(@RequestBody Map<String, Object> json) {
        //String estadoLicitacion = LicitacionConstant.ESTADO_POR_EVALUAR;
        String estadoLicitacion = (String)json.get("estadoLicitacion");
        String estadoLicitacionLectura = (String)json.get("estadoLicitacionLectura");

        String puntoEntrega = (String)json.get("puntoEntrega");
        String numeroLicitacion = (String)json.get("numeroLicitacion");
        if (numeroLicitacion.length() > 8)
            numeroLicitacion = numeroLicitacion.substring(4);
        String moneda = (String)json.get("moneda");

        String solicitudPedido = (String) json.get("solicitudPedido");
        String codigoSap = (String) json.get("codigoSap");
        ArrayList<String> listaCentroLogistico = (ArrayList<String>)json.get("listaCentroLogistico");
        ArrayList<String> listaAlmacen = (ArrayList<String>)json.get("listaAlmacen");

        ArrayList<String> listaTipoLicitacion = (ArrayList<String>)json.get("listaTipoLicitacion");
        ArrayList<String> listaTipoCuestionario = (ArrayList<String>)json.get("listaTipoCuestionario");

        Integer idProveedor = null;
        String sidProveedor = "";
        try {
            idProveedor = (Integer) json.get("idProveedor");
        }
        catch(Exception e) {
            sidProveedor = (String) json.get("idProveedor");
            sidProveedor = sidProveedor.trim();
            idProveedor =  new Integer(sidProveedor);
        }
        String numeroRUC = (String)json.get("numeroRUC");
        String razonSocial = (String)json.get("razonSocial");
        ArrayList<String> listaRegion = (ArrayList<String>)json.get("listaRegion");
        String usuarioPublicacion = (String)json.get("usuarioPublicacion"); //+@JEP

        Integer nroRegistros = (Integer)json.get("nroRegistros");
        Integer paginaMostrar = (Integer)json.get("paginaMostrar");
        paginaMostrar = new Integer((paginaMostrar.intValue() - 1) * nroRegistros);
        String emailUsuario = (String)json.get("emailUsuario");

        return this.licitacionMapper.getListLicitacionrByFiltroBusquedaPaginado(
                estadoLicitacion,
                estadoLicitacionLectura,
                numeroLicitacion,
                puntoEntrega,
                moneda,
                listaCentroLogistico,
                listaAlmacen,
                solicitudPedido,
                codigoSap,
                listaTipoLicitacion,
                listaTipoCuestionario,
                idProveedor,
                numeroRUC,
                razonSocial,
                listaRegion,
                usuarioPublicacion,
                emailUsuario,
                nroRegistros,
                paginaMostrar);
    }


    private List<Licitacion> getLicitacionsEstadoTerminal(@RequestBody Map<String, Object> json) {
        String puntoEntrega = (String)json.get("puntoEntrega");
        String numeroLicitacion = (String)json.get("numeroLicitacion");
        if (numeroLicitacion.length() > 8)
            numeroLicitacion = numeroLicitacion.substring(4);
        String moneda = (String)json.get("moneda");

        String solicitudPedido = (String) json.get("solicitudPedido");
        String codigoSap = (String) json.get("codigoSap");
        ArrayList<String> listaCentroLogistico = (ArrayList<String>)json.get("listaCentroLogistico");
        ArrayList<String> listaAlmacen = (ArrayList<String>)json.get("listaAlmacen");

        ArrayList<String> listaTipoLicitacion = (ArrayList<String>)json.get("listaTipoLicitacion");
        ArrayList<String> listaTipoCuestionario = (ArrayList<String>)json.get("listaTipoCuestionario");

        Integer idProveedor = null;
        String sidProveedor = "";
        try {
            idProveedor = (Integer) json.get("idProveedor");
        }
        catch(Exception e) {
            sidProveedor = (String) json.get("idProveedor");
            sidProveedor = sidProveedor.trim();
            idProveedor =  new Integer(sidProveedor);
        }
        String numeroRUC = (String)json.get("numeroRUC");
        String razonSocial = (String)json.get("razonSocial");
        ArrayList<String> listaRegion = (ArrayList<String>)json.get("listaRegion");
        String usuarioPublicacion = (String)json.get("usuarioPublicacion"); //+@JEP
        String emailUsuario = (String)json.get("emailUsuario");
        return this.licitacionMapper.getListLicitacionrByFiltroBusquedaEstadoTerminal(
                numeroLicitacion,
                puntoEntrega,
                moneda,
                listaCentroLogistico,
                listaAlmacen,
                solicitudPedido,
                codigoSap,
                listaTipoLicitacion,
                listaTipoCuestionario,
                idProveedor,
                numeroRUC,
                razonSocial,
                listaRegion,
                usuarioPublicacion,
                emailUsuario);
    }

    private List<Licitacion> getLicitacionsEstadoTerminalPaginado(@RequestBody Map<String, Object> json) {
        String puntoEntrega = (String)json.get("puntoEntrega");
        String numeroLicitacion = (String)json.get("numeroLicitacion");
        if (numeroLicitacion.length() > 8)
            numeroLicitacion = numeroLicitacion.substring(4);
        String moneda = (String)json.get("moneda");

        String solicitudPedido = (String) json.get("solicitudPedido");
        String codigoSap = (String) json.get("codigoSap");
        ArrayList<String> listaCentroLogistico = (ArrayList<String>)json.get("listaCentroLogistico");
        ArrayList<String> listaAlmacen = (ArrayList<String>)json.get("listaAlmacen");

        ArrayList<String> listaTipoLicitacion = (ArrayList<String>)json.get("listaTipoLicitacion");
        ArrayList<String> listaTipoCuestionario = (ArrayList<String>)json.get("listaTipoCuestionario");

        Integer idProveedor = null;
        String sidProveedor = "";
        try {
            idProveedor = (Integer) json.get("idProveedor");
        }
        catch(Exception e) {
            sidProveedor = (String) json.get("idProveedor");
            sidProveedor = sidProveedor.trim();
            idProveedor =  new Integer(sidProveedor);
        }
        String numeroRUC = (String)json.get("numeroRUC");
        String razonSocial = (String)json.get("razonSocial");
        ArrayList<String> listaRegion = (ArrayList<String>)json.get("listaRegion");
        String usuarioPublicacion = (String)json.get("usuarioPublicacion"); //+@JEP
        Integer nroRegistros = (Integer)json.get("nroRegistros");
        Integer paginaMostrar = (Integer)json.get("paginaMostrar");
        paginaMostrar = new Integer((paginaMostrar.intValue() - 1) * nroRegistros);
        String emailUsuario = (String)json.get("emailUsuario");

        return this.licitacionMapper.getListLicitacionrByFiltroBusquedaEstadoTerminalPaginado(
                numeroLicitacion,
                puntoEntrega,
                moneda,
                listaCentroLogistico,
                listaAlmacen,
                solicitudPedido,
                codigoSap,
                listaTipoLicitacion,
                listaTipoCuestionario,
                idProveedor,
                numeroRUC,
                razonSocial,
                listaRegion,
                usuarioPublicacion,
                emailUsuario,
                nroRegistros,
                paginaMostrar
        );
    }

    private List<Licitacion> getLicitacionsAdjudicadoProveedor(@RequestBody Map<String, Object> json) {
        String puntoEntrega = (String)json.get("puntoEntrega");
        String numeroLicitacion = (String)json.get("numeroLicitacion");
        if (numeroLicitacion.length() > 8)
            numeroLicitacion = numeroLicitacion.substring(4);
        String moneda = (String)json.get("moneda");

        String solicitudPedido = (String) json.get("solicitudPedido");
        String codigoSap = (String) json.get("codigoSap");
        ArrayList<String> listaCentroLogistico = (ArrayList<String>)json.get("listaCentroLogistico");
        ArrayList<String> listaAlmacen = (ArrayList<String>)json.get("listaAlmacen");

        ArrayList<String> listaTipoLicitacion = (ArrayList<String>)json.get("listaTipoLicitacion");
        ArrayList<String> listaTipoCuestionario = (ArrayList<String>)json.get("listaTipoCuestionario");

        Integer idProveedor = null;
        String sidProveedor = "";
        try {
            idProveedor = (Integer) json.get("idProveedor");
        }
        catch(Exception e) {
            sidProveedor = (String) json.get("idProveedor");
            sidProveedor = sidProveedor.trim();
            idProveedor =  new Integer(sidProveedor);
        }
        String numeroRUC = (String)json.get("numeroRUC");
        String razonSocial = (String)json.get("razonSocial");
        ArrayList<String> listaRegion = (ArrayList<String>)json.get("listaRegion");
        String emailUsuario = (String)json.get("emailUsuario");
        return this.licitacionMapper.getListLicitacionrByFiltroBusquedaAdjudicadoProveedor(
                numeroLicitacion,
                puntoEntrega,
                moneda,
                listaCentroLogistico,
                listaAlmacen,
                solicitudPedido,
                codigoSap,
                listaTipoLicitacion,
                listaTipoCuestionario,
                idProveedor,
                numeroRUC,
                razonSocial,
                listaRegion,
                emailUsuario);

    }

    private List<LicitacionAdjudicadoDTO> getLicitacionsAdjudicadoProveedor02(@RequestBody Map<String, Object> json) {
        String puntoEntrega = (String)json.get("puntoEntrega");
        String numeroLicitacion = (String)json.get("numeroLicitacion");
        if (numeroLicitacion.length() > 8)
            numeroLicitacion = numeroLicitacion.substring(4);
        String moneda = (String)json.get("moneda");

        String solicitudPedido = (String) json.get("solicitudPedido");
        String codigoSap = (String) json.get("codigoSap");
        ArrayList<String> listaCentroLogistico = (ArrayList<String>)json.get("listaCentroLogistico");
        ArrayList<String> listaAlmacen = (ArrayList<String>)json.get("listaAlmacen");

        ArrayList<String> listaTipoLicitacion = (ArrayList<String>)json.get("listaTipoLicitacion");
        ArrayList<String> listaTipoCuestionario = (ArrayList<String>)json.get("listaTipoCuestionario");

        Integer idProveedor = null;
        String sidProveedor = "";
        try {
            idProveedor = (Integer) json.get("idProveedor");
        }
        catch(Exception e) {
            sidProveedor = (String) json.get("idProveedor");
            sidProveedor = sidProveedor.trim();
            idProveedor =  new Integer(sidProveedor);
        }
        String numeroRUC = (String)json.get("numeroRUC");
        String razonSocial = (String)json.get("razonSocial");
        ArrayList<String> listaRegion = (ArrayList<String>)json.get("listaRegion");
        String emailUsuario = (String)json.get("emailUsuario");

        List<Licitacion> licitacionList = this.licitacionMapper.getListLicitacionrByFiltroBusquedaAdjudicadoProveedor(
                numeroLicitacion,
                puntoEntrega,
                moneda,
                listaCentroLogistico,
                listaAlmacen,
                solicitudPedido,
                codigoSap,
                listaTipoLicitacion,
                listaTipoCuestionario,
                idProveedor,
                numeroRUC,
                razonSocial,
                listaRegion,
                emailUsuario);
        Proveedor proveedor = this.proveedorRepository.getOne(idProveedor);
        List<LicitacionAdjudicadoDTO> licitacionAdjudicadoDTOList = new ArrayList<LicitacionAdjudicadoDTO>();
        for(Licitacion item : licitacionList ) {
            LicitacionAdjudicadoDTO bean = new LicitacionAdjudicadoDTO();
            BeanUtils.copyProperties(item, bean);
            LicitacionProveedor licitacionProveedor = this.licitacionProveedorRepository.getByLicitacionAndProveedor(item, proveedor);
            if (Optional.ofNullable(licitacionProveedor).isPresent()) {
                Date fechaCierreRecepcion = licitacionProveedor.getFechaCierreRecepcion();
                String sFechaCierreRecepcion = DateUtils.convertDateToString("dd/MM/yyyy HH:mm:ss", fechaCierreRecepcion);
                licitacionProveedor.setFechaCierreRecepcionProveedorString(sFechaCierreRecepcion);
                bean.setLicitacionProveedor(licitacionProveedor);
            }
            licitacionAdjudicadoDTOList.add(bean);
        }
        return licitacionAdjudicadoDTOList;
    }

    private List<LicitacionAdjudicadoDTO> getLicitacionsAdjudicadoProveedorPaginado(
            @RequestBody Map<String, Object> json) {
        String puntoEntrega = (String)json.get("puntoEntrega");
        String numeroLicitacion = (String)json.get("numeroLicitacion");
        if (numeroLicitacion.length() > 8)
            numeroLicitacion = numeroLicitacion.substring(4);
        String moneda = (String)json.get("moneda");

        String solicitudPedido = (String) json.get("solicitudPedido");
        String codigoSap = (String) json.get("codigoSap");
        ArrayList<String> listaCentroLogistico = (ArrayList<String>)json.get("listaCentroLogistico");
        ArrayList<String> listaAlmacen = (ArrayList<String>)json.get("listaAlmacen");

        ArrayList<String> listaTipoLicitacion = (ArrayList<String>)json.get("listaTipoLicitacion");
        ArrayList<String> listaTipoCuestionario = (ArrayList<String>)json.get("listaTipoCuestionario");

        Integer idProveedor = null;
        String sidProveedor = "";
        try {
            idProveedor = (Integer) json.get("idProveedor");
        }
        catch(Exception e) {
            sidProveedor = (String) json.get("idProveedor");
            sidProveedor = sidProveedor.trim();
            idProveedor =  new Integer(sidProveedor);
        }
        String numeroRUC = (String)json.get("numeroRUC");
        String razonSocial = (String)json.get("razonSocial");
        ArrayList<String> listaRegion = (ArrayList<String>)json.get("listaRegion");
        Integer nroRegistros = (Integer)json.get("nroRegistros");
        Integer paginaMostrar = (Integer)json.get("paginaMostrar");
        paginaMostrar = new Integer((paginaMostrar.intValue() - 1) * nroRegistros);
        String emailUsuario = (String)json.get("emailUsuario");

        List<Licitacion> licitacionList = this.licitacionMapper.getListLicitacionrByFiltroBusquedaAdjudicadoProveedorPaginado(
                numeroLicitacion,
                puntoEntrega,
                moneda,
                listaCentroLogistico,
                listaAlmacen,
                solicitudPedido,
                codigoSap,
                listaTipoLicitacion,
                listaTipoCuestionario,
                idProveedor,
                numeroRUC,
                razonSocial,
                listaRegion,
                emailUsuario,
                nroRegistros,
                paginaMostrar
        );
        Proveedor proveedor = this.proveedorRepository.getOne(idProveedor);
        List<LicitacionAdjudicadoDTO> licitacionAdjudicadoDTOList = new ArrayList<LicitacionAdjudicadoDTO>();
        for(Licitacion item : licitacionList ) {
            LicitacionAdjudicadoDTO bean = new LicitacionAdjudicadoDTO();
            BeanUtils.copyProperties(item, bean);
            LicitacionProveedor licitacionProveedor = this.licitacionProveedorRepository.getByLicitacionAndProveedor(item, proveedor);
            if (Optional.ofNullable(licitacionProveedor).isPresent()) {
                Date fechaCierreRecepcion = licitacionProveedor.getFechaCierreRecepcion();
                String sFechaCierreRecepcion = DateUtils.convertDateToString("dd/MM/yyyy HH:mm:ss", fechaCierreRecepcion);
                licitacionProveedor.setFechaCierreRecepcionProveedorString(sFechaCierreRecepcion);
                bean.setLicitacionProveedor(licitacionProveedor);
            }
            licitacionAdjudicadoDTOList.add(bean);
        }
        return licitacionAdjudicadoDTOList;
    }


    @RequestMapping(value = "/guardarLicitacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<LicitacionRequest> saveLicitacion(@RequestBody LicitacionRequest objLicitacion,
                                                            @AuthenticationPrincipal Token token)   {
        LicitacionRequest licitacion = this.licitacionService.saveLicitacion(objLicitacion,token);
        return Optional.of(licitacion)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    @RequestMapping(value = "/get/{idLicitacion}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<LicitacionRequest> getLicitacionById(@PathVariable Integer idLicitacion)  {
        return Optional.of(this.licitacionService.getLicitacionById(idLicitacion))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/sendmail-solicitud-cotizacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> sendMail(@RequestBody Map<String, Object> json,
                                           @AuthenticationPrincipal Token token)   {

        String response = "";
        Integer idLicitacion = (Integer) json.get("idLicitacion");

        try{
            response = this.licitacionService.sendMail(idLicitacion,token);

        }catch(Exception e){
            response = e.getMessage();
        }
        return Optional.of(response)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }

    /** JRAMOS - UPDATE*/
    @PostMapping(value = "/mailForwarding-solicitud-cotizacion/{id_licitacion}", headers = "Accept=application/json")
    public ResponseEntity<String> mailForwarding(@PathVariable("id_licitacion") Integer idLicitacion,
                                                 @RequestBody ReenvioProveedor reenvioProveedor)   {
        String response = "";
        try{
            response = this.licitacionService.mailForwarding(reenvioProveedor.getProveedores(), idLicitacion, reenvioProveedor.getCuerpo());
        }catch(Exception e){
            logger.error("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<ERROR>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            logger.error("/mailForwarding-solicitud-cotizacion/" + idLicitacion);
            logger.error(""+e);
            logger.error("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            response = e.getMessage();
        }
        return Optional.of(response)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    @RequestMapping(value = "/findByLicitacionById/{idLicitacion}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Licitacion> findByLicitacionById(@PathVariable Integer idLicitacion)  {
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        if (licitacion.getFechaCierreRecepcionOferta() != null) {
            String fechaCierreRecepcionOfertaString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(licitacion.getFechaCierreRecepcionOferta());
            licitacion.setFechaCierreRecepcionOfertaString(fechaCierreRecepcionOfertaString);
        }
        if (licitacion.getFechaEntregaInicio() != null) {
            String fechaEntregaInicioString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(licitacion.getFechaEntregaInicio());
            licitacion.setFechaEntregaInicioString(fechaEntregaInicioString);
        }
        if (licitacion.getFechaUltimaRenegociacion() != null) {
            String fechaUltimaRenegociacionString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(licitacion.getFechaUltimaRenegociacion());
            licitacion.setFechaUltimaRenegociacionString(fechaUltimaRenegociacionString);
        }

        Parametro parametro = this.parametroRepository.getByModuloAndTipoAndCodigo(
                "LICITACION",
                "PUNTO_ENTREGA",
                licitacion.getPuntoEntrega());
        if (Optional.ofNullable(parametro).isPresent()) {
            String descripcionPuntoEntrega = parametro.getValor();
            licitacion.setDescripcionPuntoEntrega(descripcionPuntoEntrega);
        }
        Parametro parametroEstado = this.parametroRepository.getByModuloAndTipoAndCodigo(
                "LICITACION",
                "ESTADO",
                licitacion.getEstadoLicitacion());
        if (Optional.ofNullable(parametroEstado).isPresent()) {
            String descripcionEstado = parametroEstado.getValor();
            licitacion.setDescripcionEstado(descripcionEstado);
        }

        Parametro parametroUrgencia = this.parametroRepository.getByModuloAndTipoAndCodigo(
                "LICITACION",
                "URGENCIA",
                licitacion.getNecesidadUrgencia());
        if (Optional.ofNullable(parametroUrgencia).isPresent()) {
            String descripcionUrgencia = parametroUrgencia.getValor();
            licitacion.setDescripcionNecesidadUrgencia(descripcionUrgencia);
        }
        return ResponseEntity.ok().body(licitacion);
    }

    @RequestMapping(value = "/findByLicitacionByIdAndIdProveedor/{idLicitacion}/{idProveedor}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Licitacion> findByLicitacionByIdAndIdProveedor(
            @PathVariable Integer idLicitacion,
            @PathVariable Integer idProveedor)  {
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        Proveedor proveedor = this.proveedorRepository.getOne(idProveedor);
        LicitacionProveedor licitacionProveedor = this.licitacionProveedorRepository.getByLicitacionAndProveedor(
                licitacion,
                proveedor);

        if (Optional.ofNullable(licitacionProveedor).isPresent()) {
            String fechaCierreRecepcionOfertaString = DateUtils.convertDateToString(
                    "dd/MM/yyyy HH:mm:ss",
                    licitacionProveedor.getFechaCierreRecepcion());
            licitacion.setFechaCierreRecepcionOfertaString(fechaCierreRecepcionOfertaString);
        }
        if (licitacion.getFechaEntregaInicio() != null) {
            String fechaEntregaInicioString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(licitacion.getFechaEntregaInicio());
            licitacion.setFechaEntregaInicioString(fechaEntregaInicioString);
        }

        return ResponseEntity.ok().body(licitacion);
    }


    @RequestMapping(value = "/anular", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> anularLicitacion(@RequestBody Map<String, Object> json,@AuthenticationPrincipal Token token)   {

        String response = "";

        try{
            response = this.licitacionService.anularLicitacion(json,token);
        }catch(Exception e){
            response = e.getMessage();
        }
        return Optional.of(response)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }


    @RequestMapping(value = "/republicar", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> republicarLicitacion(@RequestBody Map<String, Object> json,@AuthenticationPrincipal Token token)   {

        String response = "";

        try{
            response = this.licitacionService.republicarLicitacion(json,token);
        }catch(Exception e){
            response = e.getMessage();
        }
        return Optional.of(response)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }

    @GetMapping(value = "/downloadExcelbyLicitacion/{idLicitacion}")
    public void getReporteLicitacion(@PathVariable Integer idLicitacion,HttpServletResponse response) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh_mm_ss");
        String fileName = "Licitacion" + idLicitacion +"_" + formatter.format(LocalDateTime.now()) + ".xlsx";

        String titulo = "";

        List<Map<String, Object>> dataCabecera = new ArrayList<>();

        try {
           // Map<String, Object> data = this.licitacionService.downloadExcel(idLicitacion);
            //Map<String, Object> data2 = this.licitacionProveedorPreguntaDeltaService.downloadExcelPrueba(idLicitacion);
           //
            Workbook workbook = this.licitacionService.reporteLicitacion(idLicitacion);
            //this.exportDataService.addSheet(workbook,data2);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);

            response.setContentLength(baos.size());
            response.setContentType(ExcelType.XLSX.getExtension());
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);

            out.flush();
            baos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
            throw new RuntimeException("Error al exportar");
        }
    }


}
