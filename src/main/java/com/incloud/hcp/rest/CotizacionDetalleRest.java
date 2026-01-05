package com.incloud.hcp.rest;

import com.incloud.hcp.domain.*;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.rest.bean.PrecioCotizacionDTO;
import com.incloud.hcp.rest.bean.ProveedorAdjudicacionDTO;
import com.incloud.hcp.service.CotizacionService;
import com.incloud.hcp.util.Utils;
import com.incloud.hcp.util.constant.CotizacionConstant;
import com.incloud.hcp.util.constant.LicitacionConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by USER on 25/08/2017.
 */
@RestController
@RequestMapping(value = "/api/cotizacionDetalle")
public class CotizacionDetalleRest extends AppRest {

    private static Logger logger = LoggerFactory.getLogger(CotizacionDetalleRest.class);

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private CotizacionDetalleRepository cotizacionDetalleRepository;

    @Autowired
    private LicitacionDetalleRepository licitacionDetalleRepository;

    @Autowired
    private LicitacionRepository licitacionRepository;


    @Autowired
    private CotizacionService cotizacionService;

    @RequestMapping(value = "/countByLicitacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> countByLicitacion(
            @RequestBody Integer idLicitacion) {
       Licitacion licitacion = new Licitacion();
       licitacion.setIdLicitacion(idLicitacion);

       Integer contador = this.cotizacionRepository.countByLicitacion(licitacion);
       return ResponseEntity.ok().body(contador);
    }

    @RequestMapping(value = "/countByLicitacionEnviada", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> countByLicitacionEnviada(
            @RequestBody Integer idLicitacion) {
        Licitacion licitacion = new Licitacion();
        licitacion.setIdLicitacion(idLicitacion);

        Integer contador = this.cotizacionRepository.countByLicitacionAndEstadoCotizacion(licitacion, CotizacionConstant.ESTADO_ENVIADA);
        return ResponseEntity.ok().body(contador);
    }


    @RequestMapping(value = "/findByCotizacionDetallePrecioCotizacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<PrecioCotizacionDTO>> findByCotizacionDetallePrecioCotizacion(
            @RequestBody Integer idLicitacion)  {
        final int limiteProveedor = 10;
        Licitacion licitacion = new Licitacion();
        licitacion.setIdLicitacion(idLicitacion);
        String[] listaProveedor = new String[limiteProveedor];
        BigDecimal[] listaTotales = new BigDecimal[limiteProveedor];
        Boolean[] listaVisible = new Boolean[limiteProveedor];

        List<Cotizacion> listaCotizacion = this.cotizacionRepository.
                findByLicitacionPorAdjudicarAndSort(licitacion, Sort.by("proveedor.razonSocial"));
        int contador = 0;
        for(Cotizacion item: listaCotizacion) {
            listaProveedor[contador] = item.getProveedor().getRazonSocial();
            listaTotales[contador] = item.getImporteTotal();
            listaVisible[contador] = true;
            contador++;
        }
        for(int i= contador; i < limiteProveedor; i++) {
            listaProveedor[i] = "";
            listaVisible[i] = false;
        }

        List<PrecioCotizacionDTO> listaPrecioCotizacionDTO = new ArrayList<PrecioCotizacionDTO>();
        List<CotizacionDetalle> listaDetalle = this.cotizacionDetalleRepository.findByCotizacionInOrderByDescripcionAndProveedor(listaCotizacion);

        PrecioCotizacionDTO beanDTO = this.obtenerListaPreciosCotizacion(listaProveedor, listaVisible, listaPrecioCotizacionDTO, listaDetalle, listaTotales);

        /* Ordenando Lista por Proveedor */
        Comparator<PrecioCotizacionDTO> comparatorProveedor = (PrecioCotizacionDTO a, PrecioCotizacionDTO b) -> {
            try {
                String valorA = a.getSolicitudPedido() + a.getPosicionSolicitudPedido();
                String valorB = b.getSolicitudPedido() + b.getPosicionSolicitudPedido();
                return ((valorA.compareTo(valorB)) );
            }
            catch(Exception e) {
                return 0;
            }
        };
        Collections.sort(listaPrecioCotizacionDTO, comparatorProveedor);
        for(PrecioCotizacionDTO bean : listaPrecioCotizacionDTO) {
            logger.error("findByCotizacionDetallePrecioCotizacion  listaPrecioCotizacionDTO: " + bean.toString());
        }
        return ResponseEntity.ok().body(listaPrecioCotizacionDTO);
    }


    @RequestMapping(value = "/findByCotizacionDetallePrecioCotizacionPorAdjudicar", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<PrecioCotizacionDTO>> findByCotizacionDetallePrecioCotizacionPorAdjudicar(
            @RequestBody Integer idLicitacion)  {
        final int limiteProveedor = 10;
        Licitacion licitacion = new Licitacion();
        licitacion.setIdLicitacion(idLicitacion);
        String[] listaProveedor = new String[limiteProveedor];
        Boolean[] listaVisible = new Boolean[limiteProveedor];
        Sort sort1 = Sort.by(new Sort.Order(Sort.Direction.ASC, "proveedor.razonSocial"));
        List<Cotizacion> listaCotizacion = this.cotizacionRepository.
                findByLicitacionPorAdjudicarAndSort(licitacion, sort1);

        int contador = 0;
        for(Cotizacion item: listaCotizacion) {
            listaProveedor[contador] = item.getProveedor().getRazonSocial();
            listaVisible[contador] = true;
            contador++;
        }
        for(int i= contador; i < limiteProveedor; i++) {
            listaProveedor[i] = "";
            listaVisible[i] = false;
        }

        List<PrecioCotizacionDTO> listaPrecioCotizacionDTO = new ArrayList<PrecioCotizacionDTO>();
        List<CotizacionDetalle> listaDetalle = this.cotizacionDetalleRepository.findByCotizacionInOrderByDescripcionAndProveedor(listaCotizacion);

        PrecioCotizacionDTO beanDTO = obtenerListaPreciosCotizacion(listaProveedor, listaVisible, listaPrecioCotizacionDTO, listaDetalle, null);

        /* Ordenando Lista por Proveedor */
        Comparator<PrecioCotizacionDTO> comparatorProveedor = (PrecioCotizacionDTO a, PrecioCotizacionDTO b) -> {
            try {
                String valorA = a.getSolicitudPedido() + a.getPosicionSolicitudPedido();
                String valorB = b.getSolicitudPedido() + b.getPosicionSolicitudPedido();
                return ((valorA.compareTo(valorB)) );
            }
            catch(Exception e) {
                return 0;
            }
        };
        Collections.sort(listaPrecioCotizacionDTO, comparatorProveedor);
        return ResponseEntity.ok().body(listaPrecioCotizacionDTO);
    }

    @RequestMapping(value = "/findByCotizacionDetalleLicitacionProveedor", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<PrecioCotizacionDTO>> findByCotizacionDetalleLicitacionProveedor(
            @RequestBody Map<String, Object> json) {
        try {
            return Optional.ofNullable(this.cotizacionService.findByCotizacionDetalleLicitacionProveedor(json))
                    .map(ccomparativoProveedor -> new ResponseEntity<>(ccomparativoProveedor, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }

    }



    @RequestMapping(value = "/findByCotizacionDetalleProveedorPrecioCotizacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<PrecioCotizacionDTO>> findByCotizacionDetalleProveedorPrecioCotizacion(
            @RequestBody Map<String, Object> json) {

        Integer idLicitacion = 0;
        Integer idProveedor = 0;
        String sidLicitacion = "";
        String sidProveedor = "";
        Proveedor proveedor = new Proveedor();
        Licitacion licitacion = new Licitacion();

        try {
            idProveedor = (Integer) json.get("idProveedor");
            proveedor = this.proveedorRepository.getOne(idProveedor);
        }
        catch(Exception e) {
            sidProveedor = (String) json.get("idProveedor");
            sidProveedor = sidProveedor.trim();
            idProveedor = new Integer(sidProveedor);
            proveedor = this.proveedorRepository.getOne(idProveedor);
        }
        try {
            idLicitacion = (Integer) json.get("idLicitacion");
            licitacion.setIdLicitacion(idLicitacion);
        }
        catch(Exception e) {
            sidLicitacion = (String) json.get("idLicitacion");
            sidLicitacion = sidLicitacion.trim();
            idLicitacion = new Integer(sidLicitacion);
            licitacion.setIdLicitacion(idLicitacion);
        }

        List<PrecioCotizacionDTO> listaPrecioCotizacionDTO = new ArrayList<PrecioCotizacionDTO>();

        final int limiteProveedor = 10;
        String[] listaProveedor = new String[limiteProveedor];
        Boolean[] listaVisible = new Boolean[limiteProveedor];
        Sort sort1 = Sort.by(new Sort.Order(Sort.Direction.ASC, "proveedor.razonSocial"));

        List<Cotizacion> listaCotizacion = this.cotizacionRepository.
                findByLicitacionAndProveedorSort(licitacion, proveedor, sort1);

        boolean existeCotizaciones = false;
        if (listaCotizacion != null && listaCotizacion.size() > 0) {
            existeCotizaciones = true;
        }

        if (existeCotizaciones) {
            int contador = 0;

            for (Cotizacion item : listaCotizacion) {
                listaProveedor[contador] = item.getProveedor().getRazonSocial();
                listaVisible[contador] = true;
                contador++;
            }
            for (int i = contador; i < limiteProveedor; i++) {
                listaProveedor[i] = "";
                listaVisible[i] = false;
            }

            Cotizacion cotizacion = listaCotizacion.get(0);
            boolean existeCotizacionNoParticipar = false;
            if (cotizacion.getEstadoCotizacion().equals(CotizacionConstant.ESTADO_NO_PARTICIPAR)) {
                existeCotizacionNoParticipar = true;
            }

            if (!existeCotizacionNoParticipar) {
                List<CotizacionDetalle> listaDetalle = this.cotizacionDetalleRepository.findByCotizacionInOrderByDescripcionAndProveedor(listaCotizacion);
                PrecioCotizacionDTO beanDTO = new PrecioCotizacionDTO();

                beanDTO = this.obtenerListaPreciosCotizacion(listaProveedor, listaVisible, listaPrecioCotizacionDTO, listaDetalle, null);

            }
            else {
                listaProveedor[0] = proveedor.getRazonSocial();
                listaVisible[0] = true;
                List<LicitacionDetalle> listaDetalle = this.licitacionDetalleRepository.findByLicitacionOrdenado(licitacion);
                PrecioCotizacionDTO beanDTO = new PrecioCotizacionDTO();

                beanDTO = this.obtenerListaPreciosCotizacionLicitacionDetalle(listaProveedor, listaVisible, listaPrecioCotizacionDTO, listaDetalle, true);
             }

        }
        else {
            listaProveedor[0] = proveedor.getRazonSocial();
            listaVisible[0] = true;
            List<LicitacionDetalle> listaDetalle = this.licitacionDetalleRepository.findByLicitacionOrdenado(licitacion);
            PrecioCotizacionDTO beanDTO = new PrecioCotizacionDTO();

            beanDTO = this.obtenerListaPreciosCotizacionLicitacionDetalle02(listaProveedor, listaVisible, listaPrecioCotizacionDTO, listaDetalle, false);
         }

        /* Ordenando Lista por Proveedor */
        Comparator<PrecioCotizacionDTO> comparatorProveedor = (PrecioCotizacionDTO a, PrecioCotizacionDTO b) -> {
            try {
                String valorA = a.getSolicitudPedido() + a.getPosicionSolicitudPedido();
                String valorB = b.getSolicitudPedido() + b.getPosicionSolicitudPedido();
                return ((valorA.compareTo(valorB)) );
            }
            catch(Exception e) {
                return 0;
            }
        };
        Collections.sort(listaPrecioCotizacionDTO, comparatorProveedor);

        return ResponseEntity.ok().body(listaPrecioCotizacionDTO);
    }

    private PrecioCotizacionDTO obtenerListaPreciosCotizacion(String[] listaProveedor,
                                                              Boolean[] listaVisible,
                                                              List<PrecioCotizacionDTO> listaPrecioCotizacionDTO,
                                                              List<CotizacionDetalle> listaDetalle,
                                                              BigDecimal[] listaTotales) {
        String nombreBien = "-1";
        String posicionSolicitudBuscar = "-1";
        String solpedBuscar = "-1";
        Integer idLicitacionDetalleBuscar = -1;
        PrecioCotizacionDTO beanDTO = null;
        logger.error("obtenerListaPreciosCotizacion 0 listaDetalle: " + listaDetalle.size());

        for(CotizacionDetalle item:listaDetalle) {

            String razonSocial = item.getCotizacion().getProveedor().getRazonSocial();
            Moneda monedaCotizacion = item.getCotizacion().getMoneda();
            String solped = item.getLicitacionDetalle().getSolicitudPedido();
            String posicionSolicitud = item.getLicitacionDetalle().getPosicionSolicitudPedido();
            Integer idLicitacionDetalle = item.getLicitacionDetalle().getIdLicitacionDetalle();
            boolean cambiarRegistro = false;

            if (StringUtils.isNotBlank(posicionSolicitud) && StringUtils.isNotBlank(solped)) {
                if ((item.getDescripcion().equals(nombreBien))) {
                    if (!solped.equals(solpedBuscar)) {
                        cambiarRegistro = true;
                    }
                    if (solped.equals(solpedBuscar) && !posicionSolicitud.equals(posicionSolicitudBuscar)) {
                        cambiarRegistro = true;
                    }
                }

            }
            else {
                if (idLicitacionDetalle.intValue() != idLicitacionDetalleBuscar) {
                    cambiarRegistro = true;
                }
            }

            if (!(item.getDescripcion().equals(nombreBien)) || cambiarRegistro) {

                if (beanDTO != null) {
                    listaPrecioCotizacionDTO.add(beanDTO);
                }
                nombreBien = item.getDescripcion();
                posicionSolicitudBuscar = posicionSolicitud;
                solpedBuscar = solped;
                idLicitacionDetalleBuscar = idLicitacionDetalle;

                beanDTO = new PrecioCotizacionDTO();
                beanDTO.setLicitacionDetalle(item.getLicitacionDetalle());
                beanDTO.setNombreBienServicio(item.getDescripcion());
                beanDTO.setNombreBienServicioCentroAlmacen(
                        item.getDescripcion() +
                                " - (Centro: " +
                                (item.getLicitacionDetalle().getIdCentro() != null ? item.getLicitacionDetalle().getIdCentro().getCodigoSap() : "--") +
//                                item.getLicitacionDetalle().getIdCentro().getCodigoSap() +
//                                " / Almacen: " +
//                                item.getLicitacionDetalle().getIdAlmacen().getCodigoSap() +
                                ")"
                        );

                beanDTO.setCantidadSolicitada(item.getCantidadSolicitada());
                beanDTO.setUnidadMedida(item.getUnidadMedida());
                beanDTO.setIdLicitacionDetalle(item.getLicitacionDetalle().getIdLicitacionDetalle());
                beanDTO.setSolicitudPedido(item.getLicitacionDetalle().getSolicitudPedido());
                beanDTO.setPosicionSolicitudPedido(item.getLicitacionDetalle().getPosicionSolicitudPedido());

                beanDTO.setVisible01(listaVisible[0]);
                beanDTO.setNombreProveedor01(listaProveedor[0]);
                beanDTO.setVisible02(listaVisible[1]);
                beanDTO.setNombreProveedor02(listaProveedor[1]);
                beanDTO.setVisible03(listaVisible[2]);
                beanDTO.setNombreProveedor03(listaProveedor[2]);
                beanDTO.setVisible04(listaVisible[3]);
                beanDTO.setNombreProveedor04(listaProveedor[3]);
                beanDTO.setVisible05(listaVisible[4]);
                beanDTO.setNombreProveedor05(listaProveedor[4]);
                beanDTO.setVisible06(listaVisible[5]);
                beanDTO.setNombreProveedor06(listaProveedor[5]);
                beanDTO.setVisible07(listaVisible[6]);
                beanDTO.setNombreProveedor07(listaProveedor[6]);
                beanDTO.setVisible08(listaVisible[7]);
                beanDTO.setNombreProveedor08(listaProveedor[7]);
                beanDTO.setVisible09(listaVisible[8]);
                beanDTO.setNombreProveedor09(listaProveedor[8]);
                beanDTO.setVisible10(listaVisible[9]);
                beanDTO.setNombreProveedor10(listaProveedor[9]);
            }

            if (razonSocial.equals(listaProveedor[0])) {
                BigDecimal subTotal01 = item.getCantidadCotizada().multiply(item.getPrecioUnitario());
                beanDTO.setMoneda01(monedaCotizacion);
                beanDTO.setCantidadCotizada01(item.getCantidadCotizada());
                beanDTO.setPrecioUnitario01(item.getPrecioUnitario());
                beanDTO.setSubTotal01(subTotal01);
                beanDTO.setCotizacion01(item.getCotizacion().getIdCotizacion());
                beanDTO.setEstadoCotizacion01(item.getCotizacion().getEstadoCotizacion());
                beanDTO.setEspecificacion01(item.getEspecificacion());
                beanDTO.setDescripcionAlternativa01(item.getDescripcionAlternativa());
                beanDTO.setDescuento01(item.getDescuento());
                beanDTO.setPrecio01(item.getPrecio());
                beanDTO.setTiempoEntrega01(item.getTiempoEntrega());
            }
            if (razonSocial.equals(listaProveedor[1])) {
                BigDecimal subTotal02 = item.getCantidadCotizada().multiply(item.getPrecioUnitario());

                beanDTO.setMoneda02(monedaCotizacion);
                beanDTO.setCantidadCotizada02(item.getCantidadCotizada());
                beanDTO.setPrecioUnitario02(item.getPrecioUnitario());
                beanDTO.setSubTotal02(subTotal02);
                beanDTO.setCotizacion02(item.getCotizacion().getIdCotizacion());
                beanDTO.setEstadoCotizacion02(item.getCotizacion().getEstadoCotizacion());
                beanDTO.setEspecificacion02(item.getEspecificacion());
                beanDTO.setDescripcionAlternativa02(item.getDescripcionAlternativa());
                beanDTO.setDescuento02(item.getDescuento());
                beanDTO.setPrecio02(item.getPrecio());
                beanDTO.setTiempoEntrega02(item.getTiempoEntrega());
            }
            if (razonSocial.equals(listaProveedor[2])) {
                BigDecimal subTotal03 = item.getCantidadCotizada().multiply(item.getPrecioUnitario());

                beanDTO.setMoneda03(monedaCotizacion);
                beanDTO.setCantidadCotizada03(item.getCantidadCotizada());
                beanDTO.setPrecioUnitario03(item.getPrecioUnitario());
                beanDTO.setSubTotal03(subTotal03);
                beanDTO.setCotizacion03(item.getCotizacion().getIdCotizacion());
                beanDTO.setEstadoCotizacion03(item.getCotizacion().getEstadoCotizacion());
                beanDTO.setEspecificacion03(item.getEspecificacion());
                beanDTO.setDescripcionAlternativa03(item.getDescripcionAlternativa());
                beanDTO.setDescuento03(item.getDescuento());
                beanDTO.setPrecio03(item.getPrecio());
                beanDTO.setTiempoEntrega03(item.getTiempoEntrega());
            }
            if (razonSocial.equals(listaProveedor[3])) {
                BigDecimal subTotal04 = item.getCantidadCotizada().multiply(item.getPrecioUnitario());

                beanDTO.setMoneda04(monedaCotizacion);
                beanDTO.setCantidadCotizada04(item.getCantidadCotizada());
                beanDTO.setPrecioUnitario04(item.getPrecioUnitario());
                beanDTO.setSubTotal04(subTotal04);
                beanDTO.setCotizacion04(item.getCotizacion().getIdCotizacion());
                beanDTO.setEstadoCotizacion04(item.getCotizacion().getEstadoCotizacion());
                beanDTO.setEspecificacion04(item.getEspecificacion());
                beanDTO.setDescripcionAlternativa04(item.getDescripcionAlternativa());
                beanDTO.setDescuento04(item.getDescuento());
                beanDTO.setPrecio04(item.getPrecio());
                beanDTO.setTiempoEntrega04(item.getTiempoEntrega());
            }
            if (razonSocial.equals(listaProveedor[4])) {
                BigDecimal subTotal05 = item.getCantidadCotizada().multiply(item.getPrecioUnitario());

                beanDTO.setMoneda05(monedaCotizacion);
                beanDTO.setCantidadCotizada05(item.getCantidadCotizada());
                beanDTO.setPrecioUnitario05(item.getPrecioUnitario());
                beanDTO.setSubTotal05(subTotal05);
                beanDTO.setCotizacion05(item.getCotizacion().getIdCotizacion());
                beanDTO.setEstadoCotizacion05(item.getCotizacion().getEstadoCotizacion());
                beanDTO.setEspecificacion05(item.getEspecificacion());
                beanDTO.setDescripcionAlternativa05(item.getDescripcionAlternativa());
                beanDTO.setDescuento05(item.getDescuento());
                beanDTO.setPrecio05(item.getPrecio());
                beanDTO.setTiempoEntrega05(item.getTiempoEntrega());
            }
            if (razonSocial.equals(listaProveedor[5])) {
                BigDecimal subTotal06 = item.getCantidadCotizada().multiply(item.getPrecioUnitario());

                beanDTO.setMoneda06(monedaCotizacion);
                beanDTO.setCantidadCotizada06(item.getCantidadCotizada());
                beanDTO.setPrecioUnitario06(item.getPrecioUnitario());
                beanDTO.setSubTotal06(subTotal06);
                beanDTO.setCotizacion06(item.getCotizacion().getIdCotizacion());
                beanDTO.setEstadoCotizacion06(item.getCotizacion().getEstadoCotizacion());
                beanDTO.setEspecificacion06(item.getEspecificacion());
                beanDTO.setDescripcionAlternativa06(item.getDescripcionAlternativa());
                beanDTO.setDescuento06(item.getDescuento());
                beanDTO.setPrecio06(item.getPrecio());
                beanDTO.setTiempoEntrega06(item.getTiempoEntrega());
            }
            if (razonSocial.equals(listaProveedor[6])) {
                BigDecimal subTotal07 = item.getCantidadCotizada().multiply(item.getPrecioUnitario());

                beanDTO.setMoneda07(monedaCotizacion);
                beanDTO.setCantidadCotizada07(item.getCantidadCotizada());
                beanDTO.setPrecioUnitario07(item.getPrecioUnitario());
                beanDTO.setSubTotal07(subTotal07);
                beanDTO.setCotizacion07(item.getCotizacion().getIdCotizacion());
                beanDTO.setEstadoCotizacion07(item.getCotizacion().getEstadoCotizacion());
                beanDTO.setEspecificacion07(item.getEspecificacion());
                beanDTO.setDescripcionAlternativa07(item.getDescripcionAlternativa());
                beanDTO.setDescuento07(item.getDescuento());
                beanDTO.setPrecio07(item.getPrecio());
                beanDTO.setTiempoEntrega07(item.getTiempoEntrega());
            }
            if (razonSocial.equals(listaProveedor[7])) {
                BigDecimal subTotal08 = item.getCantidadCotizada().multiply(item.getPrecioUnitario());

                beanDTO.setMoneda08(monedaCotizacion);
                beanDTO.setCantidadCotizada08(item.getCantidadCotizada());
                beanDTO.setPrecioUnitario08(item.getPrecioUnitario());
                beanDTO.setSubTotal08(subTotal08);
                beanDTO.setCotizacion08(item.getCotizacion().getIdCotizacion());
                beanDTO.setEstadoCotizacion08(item.getCotizacion().getEstadoCotizacion());
                beanDTO.setEspecificacion08(item.getEspecificacion());
                beanDTO.setDescripcionAlternativa08(item.getDescripcionAlternativa());
                beanDTO.setDescuento08(item.getDescuento());
                beanDTO.setPrecio08(item.getPrecio());
                beanDTO.setTiempoEntrega08(item.getTiempoEntrega());
            }
            if (razonSocial.equals(listaProveedor[8])) {
                BigDecimal subTotal09 = item.getCantidadCotizada().multiply(item.getPrecioUnitario());

                beanDTO.setMoneda09(monedaCotizacion);
                beanDTO.setCantidadCotizada09(item.getCantidadCotizada());
                beanDTO.setPrecioUnitario09(item.getPrecioUnitario());
                beanDTO.setSubTotal09(subTotal09);
                beanDTO.setCotizacion09(item.getCotizacion().getIdCotizacion());
                beanDTO.setEstadoCotizacion09(item.getCotizacion().getEstadoCotizacion());
                beanDTO.setEspecificacion09(item.getEspecificacion());
                beanDTO.setDescripcionAlternativa09(item.getDescripcionAlternativa());
                beanDTO.setDescuento09(item.getDescuento());
                beanDTO.setPrecio09(item.getPrecio());
                beanDTO.setTiempoEntrega09(item.getTiempoEntrega());
            }
            if (razonSocial.equals(listaProveedor[9])) {
                BigDecimal subTotal10 = item.getCantidadCotizada().multiply(item.getPrecioUnitario());

                beanDTO.setMoneda10(monedaCotizacion);
                beanDTO.setCantidadCotizada10(item.getCantidadCotizada());
                beanDTO.setPrecioUnitario10(item.getPrecioUnitario());
                beanDTO.setSubTotal10(subTotal10);
                beanDTO.setCotizacion10(item.getCotizacion().getIdCotizacion());
                beanDTO.setEstadoCotizacion10(item.getCotizacion().getEstadoCotizacion());
                beanDTO.setEspecificacion10(item.getEspecificacion());
                beanDTO.setDescripcionAlternativa10(item.getDescripcionAlternativa());
                beanDTO.setDescuento10(item.getDescuento());
                beanDTO.setPrecio10(item.getPrecio());
                beanDTO.setTiempoEntrega10(item.getTiempoEntrega());
            }


            BienServicio producto = item.getBienServicio();
            if (producto != null) {
                Integer idProducto = producto.getIdBienServicio();
                if (idProducto != null) {

                    beanDTO.setIdBienServicio(idProducto);
                    try {
                        beanDTO.setTipoLicitacion(producto.getRubroBien().getTipoLicitacion().getDescripcion());
                    }
                    catch (Exception e) {
                    }

                    try {
                        beanDTO.setTipoCuestionario(producto.getRubroBien().getTipoCuestionario().getDescripcion());
                    }
                    catch (Exception e) {
                    }
                }
            }
        }

        if (beanDTO != null) {
            listaPrecioCotizacionDTO.add(beanDTO);
        }
        if (listaTotales != null && listaTotales.length > 0){
            PrecioCotizacionDTO beanDtoTotales = new PrecioCotizacionDTO();
            beanDtoTotales = this.obtenerBeanTotales(listaTotales);
            listaPrecioCotizacionDTO.add(beanDtoTotales);
        }
        return beanDTO;
    }


    private PrecioCotizacionDTO obtenerBeanTotales(BigDecimal[] listaTotales){

        PrecioCotizacionDTO beanDto = new PrecioCotizacionDTO();
        beanDto.setNombreBienServicio("TOTALES");
        beanDto.setNombreBienServicioCentroAlmacen("TOTALES");
        beanDto.setSubTotal01(listaTotales[0]);
        beanDto.setSubTotal02(listaTotales[1]);
        beanDto.setSubTotal03(listaTotales[2]);
        beanDto.setSubTotal04(listaTotales[3]);
        beanDto.setSubTotal05(listaTotales[4]);
        beanDto.setSubTotal06(listaTotales[5]);
        beanDto.setSubTotal07(listaTotales[6]);
        beanDto.setSubTotal08(listaTotales[7]);
        beanDto.setSubTotal09(listaTotales[8]);
        beanDto.setSubTotal10(listaTotales[9]);

        return beanDto;
    }


    private PrecioCotizacionDTO obtenerListaPreciosCotizacionLicitacionDetalle02(String[] listaProveedor,
                                                                               Boolean[] listaVisible,
                                                                               List<PrecioCotizacionDTO> listaPrecioCotizacionDTO,
                                                                               List<LicitacionDetalle> listaDetalle,
                                                                               boolean esCotizacionNoParticipar) {
        String nombreBien = "-1";
        String posicionSolicitudBuscar = "-1";
        String solpedBuscar = "-1";
        Integer idLicitacionDetalleBuscar = -1;
        PrecioCotizacionDTO beanDTO = null;
        for(LicitacionDetalle item:listaDetalle) {
            String solped = item.getSolicitudPedido();
            String posicionSolicitud = item.getPosicionSolicitudPedido();
            Integer idLicitacionDetalle = item.getIdLicitacionDetalle();
            boolean cambiarRegistro = false;

            if (StringUtils.isNotBlank(posicionSolicitud) && StringUtils.isNotBlank(solped)) {
                if ((item.getDescripcion().equals(nombreBien))) {
                    if (!solped.equals(solpedBuscar)) {
                        cambiarRegistro = true;
                    }
                    if (solped.equals(solpedBuscar) && !posicionSolicitud.equals(posicionSolicitudBuscar)) {
                        cambiarRegistro = true;
                    }
                }
            }
            else {
                if (idLicitacionDetalle.intValue() != idLicitacionDetalleBuscar) {
                    cambiarRegistro = true;
                }
            }

            if (!(item.getDescripcion().equals(nombreBien)) || cambiarRegistro) {

                if (beanDTO != null) {
                    listaPrecioCotizacionDTO.add(beanDTO);
                }
                nombreBien = item.getDescripcion();
                posicionSolicitudBuscar = posicionSolicitud;
                solpedBuscar = solped;
                idLicitacionDetalleBuscar = idLicitacionDetalle;

                beanDTO = new PrecioCotizacionDTO();
                beanDTO.setLicitacionDetalle(item);
                beanDTO.setNombreBienServicio(item.getDescripcion());
                beanDTO.setNombreBienServicioCentroAlmacen(
                        item.getDescripcion() +
                                " - (Centro: " +
                                (item.getIdCentro() != null ? item.getIdCentro().getCodigoSap() : "--") +
//                                item.getIdCentro().getCodigoSap() +
//                                " / Almacen: " +
//                                item.getIdAlmacen().getCodigoSap() +
                                ")"
                );

                beanDTO.setCantidadSolicitada(item.getCantidadSolicitada());
                beanDTO.setUnidadMedida(item.getUnidadMedida());
                beanDTO.setIdLicitacionDetalle(item.getIdLicitacionDetalle());
                beanDTO.setSolicitudPedido(item.getSolicitudPedido());

                beanDTO.setVisible01(listaVisible[0]);
                beanDTO.setNombreProveedor01(listaProveedor[0]);
                beanDTO.setCotizacion01(null);
                if (esCotizacionNoParticipar)
                    beanDTO.setEstadoCotizacion01(CotizacionConstant.ESTADO_NO_PARTICIPAR);

            }

            beanDTO.setCantidadCotizada01(item.getCantidadSolicitada());
            beanDTO.setPrecioUnitario01(new BigDecimal(0));

            BienServicio producto = item.getBienServicio();
            if (producto != null) {
                Integer idProducto = producto.getIdBienServicio();
                if (idProducto != null) {
                    beanDTO.setIdBienServicio(idProducto);
                    try {
                        beanDTO.setTipoLicitacion(producto.getRubroBien().getTipoLicitacion().getDescripcion());
                    }
                    catch (Exception e) {
                    }

                    try {
                        beanDTO.setTipoCuestionario(producto.getRubroBien().getTipoCuestionario().getDescripcion());
                    }
                    catch (Exception e) {
                    }
                }
            }
        }
        if (beanDTO != null) {
            listaPrecioCotizacionDTO.add(beanDTO);
        }
        return beanDTO;
    }

    private PrecioCotizacionDTO obtenerListaPreciosCotizacionLicitacionDetalle(String[] listaProveedor,
                                                              Boolean[] listaVisible,
                                                              List<PrecioCotizacionDTO> listaPrecioCotizacionDTO,
                                                              List<LicitacionDetalle> listaDetalle,
                                                              boolean esCotizacionNoParticipar) {
        String nombreBien = "-1";
        String posicionSolicitudBuscar = "-1";
        String solpedBuscar = "-1";
        Integer idLicitacionDetalleBuscar = -1;
        PrecioCotizacionDTO beanDTO = null;

        for(LicitacionDetalle item:listaDetalle) {
            String solped = item.getSolicitudPedido();
            String posicionSolicitud = item.getPosicionSolicitudPedido();
            Integer idLicitacionDetalle = item.getIdLicitacionDetalle();
            boolean cambiarRegistro = false;

            if (StringUtils.isNotBlank(posicionSolicitud) && StringUtils.isNotBlank(solped)) {
                if ((item.getDescripcion().equals(nombreBien))) {
                    if (!solped.equals(solpedBuscar)) {
                        cambiarRegistro = true;
                    }
                    if (solped.equals(solpedBuscar) && !posicionSolicitud.equals(posicionSolicitudBuscar)) {
                        cambiarRegistro = true;
                    }
                }
            }
            else {
                if (idLicitacionDetalle.intValue() != idLicitacionDetalleBuscar) {
                    cambiarRegistro = true;
                }
            }

            if (!(item.getDescripcion().equals(nombreBien)) || cambiarRegistro) {

                if (beanDTO != null) {
                    listaPrecioCotizacionDTO.add(beanDTO);
                }
                nombreBien = item.getDescripcion();
                posicionSolicitudBuscar = posicionSolicitud;
                solpedBuscar = solped;
                idLicitacionDetalleBuscar = idLicitacionDetalle;

                beanDTO = new PrecioCotizacionDTO();
                beanDTO.setLicitacionDetalle(item);
                beanDTO.setNombreBienServicio(item.getDescripcion());
                beanDTO.setNombreBienServicioCentroAlmacen(
                        item.getDescripcion() +
                                " - (Centro: " +
                                (item.getIdCentro() != null ? item.getIdCentro().getCodigoSap() : "--") +
//                                item.getIdCentro().getCodigoSap() +
//                                " / Almacen: " +
//                                item.getIdAlmacen().getCodigoSap() +
                                ")"
                );

                beanDTO.setCantidadSolicitada(item.getCantidadSolicitada());
                beanDTO.setUnidadMedida(item.getUnidadMedida());
                beanDTO.setIdLicitacionDetalle(item.getIdLicitacionDetalle());
                beanDTO.setSolicitudPedido(item.getSolicitudPedido());

                beanDTO.setVisible01(listaVisible[0]);
                beanDTO.setNombreProveedor01(listaProveedor[0]);
                beanDTO.setCotizacion01(null);
                if (esCotizacionNoParticipar)
                    beanDTO.setEstadoCotizacion01(CotizacionConstant.ESTADO_NO_PARTICIPAR);

            }

            beanDTO.setCantidadCotizada01(new BigDecimal(0));
            beanDTO.setPrecioUnitario01(new BigDecimal(0));

            BienServicio producto = item.getBienServicio();
            if (producto != null) {
                Integer idProducto = producto.getIdBienServicio();
                if (idProducto != null) {
                    beanDTO.setIdBienServicio(idProducto);
                    try {
                        beanDTO.setTipoLicitacion(producto.getRubroBien().getTipoLicitacion().getDescripcion());
                    }
                    catch (Exception e) {
                    }

                    try {
                        beanDTO.setTipoCuestionario(producto.getRubroBien().getTipoCuestionario().getDescripcion());
                    }
                    catch (Exception e) {
                    }
                }
            }
        }
        if (beanDTO != null) {
            listaPrecioCotizacionDTO.add(beanDTO);
        }
       return beanDTO;
    }


    @RequestMapping(value = "/findByProveedorCotizacionDetalle", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<ProveedorAdjudicacionDTO>> findByProveedorCotizacionDetalle(@RequestBody Integer idLicitacion)  {
        final int limiteProveedor = 10;
        Licitacion licitacion = new Licitacion();
        licitacion.setIdLicitacion(idLicitacion);

        licitacion = this.licitacionRepository.getOne(idLicitacion);
        List<CotizacionDetalle> listaDetalle = null;
        if (licitacion.getEstadoLicitacion().equals(LicitacionConstant.ESTADO_PUBLICADA)) {
            Sort sort1 = Sort.by(new Sort.Order(Sort.Direction.ASC, "cotizacion.proveedor.razonSocial"),
                    new Sort.Order(Sort.Direction.ASC, "descripcion"));

            listaDetalle = this.cotizacionDetalleRepository.
                    findByCotizacionInAndSort(licitacion,sort1);
        }
        else {
            Sort sort1 = Sort.by(new Sort.Order(Sort.Direction.ASC, "cotizacion.proveedor.razonSocial"),
                    new Sort.Order(Sort.Direction.ASC, "descripcion"));
            listaDetalle = this.cotizacionDetalleRepository.
                    findByCotizacionInAndSortAdjudicado(licitacion,sort1);
        }

        List<ProveedorAdjudicacionDTO> listaProveedor = new ArrayList<ProveedorAdjudicacionDTO>();
        String grazonSocial = "-1";
        ProveedorAdjudicacionDTO beanDTO = null;
        List<CotizacionDetalle> listaCotizacionDetalle = new ArrayList<CotizacionDetalle>();

        for(CotizacionDetalle item:listaDetalle) {
            String razonSocial = item.getCotizacion().getProveedor().getRazonSocial();
            if (!(grazonSocial.equals(razonSocial))) {
                if (beanDTO != null) {
                    beanDTO.setListaCotizacionDetalle(listaCotizacionDetalle);
                    listaProveedor.add(beanDTO);
                }
                grazonSocial = razonSocial;
                beanDTO = new ProveedorAdjudicacionDTO();
                beanDTO.setIdProveedor(item.getCotizacion().getProveedor().getIdProveedor());
                beanDTO.setRazonSocial(item.getCotizacion().getProveedor().getRazonSocial());
                listaCotizacionDetalle = new ArrayList<CotizacionDetalle>();
            }
            listaCotizacionDetalle.add(item);
        }
        if (beanDTO != null) {
            beanDTO.setListaCotizacionDetalle(listaCotizacionDetalle);
            listaProveedor.add(beanDTO);
        }
        return ResponseEntity.ok().body(listaProveedor);
    }


}
