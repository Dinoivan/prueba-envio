package com.incloud.hcp.rest;

import com.incloud.hcp.domain.Cotizacion;
import com.incloud.hcp.domain.CotizacionAdjunto;
import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.repository.CotizacionAdjuntoRepository;
import com.incloud.hcp.repository.CotizacionRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.rest.bean.CotizacionAdjuntoDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by USER on 01/09/2017.
 */
@RestController
@RequestMapping(value = "/api/cotizacionAdjunto")
public class CotizacionAdjuntoRest extends AppRest {


    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private CotizacionAdjuntoRepository cotizacionAdjuntoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @RequestMapping(value = "/countByCotizacionAdjunto", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> countByCotizacionAdjunto(@RequestBody Integer idCotizacion) {

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setIdCotizacion(idCotizacion);
        Integer contador = this.cotizacionAdjuntoRepository.countByCotizacion(cotizacion);
        return ResponseEntity.ok().body(contador);

    }


    @RequestMapping(value = "/countByCotizacionAdjuntoProveedor", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Integer> countByCotizacionAdjuntoProveedor(
            @RequestBody Map<String, Object> json)  {
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
        Integer contador = this.cotizacionAdjuntoRepository.countByCotizacionAdjuntoProveedor(licitacion, proveedor);
        return ResponseEntity.ok().body(contador);
    }

    @RequestMapping(value = "/findByCotizacionAdjunto", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<CotizacionAdjuntoDTO>> findByCotizacionAdjunto(@RequestBody Integer idLicitacion)  {
        final int limiteProveedor = 10;
        Licitacion licitacion = new Licitacion();
        licitacion.setIdLicitacion(idLicitacion);

        String[] listaProveedor = new String[limiteProveedor];
        Boolean[] listaVisible = new Boolean[limiteProveedor];

//        List<Cotizacion> listaCotizacion = this.cotizacionRepository.
//                findByLicitacionAndSort(licitacion, new Sort("proveedor.razonSocial"));
        List<Cotizacion> listaCotizacion = this.cotizacionRepository.
                findByLicitacionPorAdjudicarAndSort(licitacion, Sort.by("proveedor.razonSocial"));

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

        List<CotizacionAdjuntoDTO> listaPrecioCotizacionDTO = new ArrayList<CotizacionAdjuntoDTO>();
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "cotizacion.proveedor.razonSocial"));
        List<CotizacionAdjunto> listaDetalle = this.cotizacionAdjuntoRepository.
                findByAndSort(licitacion, sort);

        CotizacionAdjuntoDTO beanDTO = null;
        for(CotizacionAdjunto item:listaDetalle) {

            beanDTO = new CotizacionAdjuntoDTO();

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

            String razonSocial = item.getCotizacion().getProveedor().getRazonSocial();
            beanDTO.setNombreProveedor(razonSocial);

            if (razonSocial.equals(listaProveedor[0])) {
                beanDTO.setRutaAdjunto01(item.getRutaAdjunto());
                beanDTO.setArchivoNombre01(item.getArchivoNombre());
                beanDTO.setDescripcion01(item.getDescripcion());
            }
            if (razonSocial.equals(listaProveedor[1])) {
                beanDTO.setRutaAdjunto02(item.getRutaAdjunto());
                beanDTO.setArchivoNombre02(item.getArchivoNombre());
                beanDTO.setDescripcion02(item.getDescripcion());
            }
            if (razonSocial.equals(listaProveedor[2])) {
                beanDTO.setRutaAdjunto03(item.getRutaAdjunto());
                beanDTO.setArchivoNombre03(item.getArchivoNombre());
                beanDTO.setDescripcion03(item.getDescripcion());
            }
            if (razonSocial.equals(listaProveedor[3])) {
                beanDTO.setRutaAdjunto04(item.getRutaAdjunto());
                beanDTO.setArchivoNombre04(item.getArchivoNombre());
                beanDTO.setDescripcion04(item.getDescripcion());
            }
            if (razonSocial.equals(listaProveedor[4])) {
                beanDTO.setRutaAdjunto05(item.getRutaAdjunto());
                beanDTO.setArchivoNombre05(item.getArchivoNombre());
                beanDTO.setDescripcion05(item.getDescripcion());
            }
            if (razonSocial.equals(listaProveedor[5])) {
                beanDTO.setRutaAdjunto06(item.getRutaAdjunto());
                beanDTO.setArchivoNombre06(item.getArchivoNombre());
                beanDTO.setDescripcion06(item.getDescripcion());
            }
            if (razonSocial.equals(listaProveedor[6])) {
                beanDTO.setRutaAdjunto07(item.getRutaAdjunto());
                beanDTO.setArchivoNombre07(item.getArchivoNombre());
                beanDTO.setDescripcion07(item.getDescripcion());
            }
            if (razonSocial.equals(listaProveedor[7])) {
                beanDTO.setRutaAdjunto08(item.getRutaAdjunto());
                beanDTO.setArchivoNombre08(item.getArchivoNombre());
                beanDTO.setDescripcion08(item.getDescripcion());
            }
            if (razonSocial.equals(listaProveedor[8])) {
                beanDTO.setRutaAdjunto09(item.getRutaAdjunto());
                beanDTO.setArchivoNombre09(item.getArchivoNombre());
                beanDTO.setDescripcion09(item.getDescripcion());
            }
            if (razonSocial.equals(listaProveedor[9])) {
                beanDTO.setRutaAdjunto10(item.getRutaAdjunto());
                beanDTO.setArchivoNombre10(item.getArchivoNombre());
                beanDTO.setDescripcion10(item.getDescripcion());
            }
            listaPrecioCotizacionDTO.add(beanDTO);
        }


        String nombreProveedor = "-1";
        Integer numeroArchivos = 1;
        CotizacionAdjuntoDTO beanFinal = new CotizacionAdjuntoDTO();
        for(int i=0; i <listaPrecioCotizacionDTO.size(); i++ ) {
            CotizacionAdjuntoDTO item = listaPrecioCotizacionDTO.get(i);
            if (item.getNombreProveedor().equals(nombreProveedor)){
                numeroArchivos++;
            }
            else {
                numeroArchivos = 1;
                nombreProveedor = item.getNombreProveedor();
            }
            item.setNumeroArchivo(numeroArchivos);
            listaPrecioCotizacionDTO.set(i,item);
        }

        /* Ordenando Lista por Numero */
        Comparator<CotizacionAdjuntoDTO> comparatorLista = (CotizacionAdjuntoDTO a, CotizacionAdjuntoDTO b) -> {
            return (a.getNumeroArchivo().compareTo(b.getNumeroArchivo()));
        };

        Collections.sort(listaPrecioCotizacionDTO, comparatorLista);
        List<CotizacionAdjuntoDTO> listaFinal = new ArrayList<CotizacionAdjuntoDTO>();

        CotizacionAdjuntoDTO beanResult = new CotizacionAdjuntoDTO();
        Integer numeroArchivoActual = 1;

        for(int i=0; i <listaPrecioCotizacionDTO.size(); i++ ) {
            CotizacionAdjuntoDTO bean = listaPrecioCotizacionDTO.get(i);
            if (numeroArchivoActual.intValue() != bean.getNumeroArchivo().intValue()) {
                listaFinal.add(beanResult);
                numeroArchivoActual =  bean.getNumeroArchivo();
                beanResult = new CotizacionAdjuntoDTO();
            }

            if (StringUtils.isNotBlank(bean.getRutaAdjunto01())) {
                beanResult.setNombreProveedor(bean.getNombreProveedor01());
                beanResult.setRutaAdjunto01(bean.getRutaAdjunto01());
                beanResult.setArchivoNombre01(bean.getArchivoNombre01());
                beanResult.setDescripcion01(bean.getDescripcion01());
            }
            if (StringUtils.isNotBlank(bean.getRutaAdjunto02())) {
                beanResult.setNombreProveedor(bean.getNombreProveedor02());
                beanResult.setRutaAdjunto02(bean.getRutaAdjunto02());
                beanResult.setArchivoNombre02(bean.getArchivoNombre02());
                beanResult.setDescripcion02(bean.getDescripcion02());
            }
            if (StringUtils.isNotBlank(bean.getRutaAdjunto03())) {
                beanResult.setNombreProveedor(bean.getNombreProveedor03());
                beanResult.setRutaAdjunto03(bean.getRutaAdjunto03());
                beanResult.setArchivoNombre03(bean.getArchivoNombre03());
                beanResult.setDescripcion03(bean.getDescripcion03());
            }
            if (StringUtils.isNotBlank(bean.getRutaAdjunto04())) {
                beanResult.setNombreProveedor(bean.getNombreProveedor04());
                beanResult.setRutaAdjunto04(bean.getRutaAdjunto04());
                beanResult.setArchivoNombre04(bean.getArchivoNombre04());
                beanResult.setDescripcion04(bean.getDescripcion04());
            }
            if (StringUtils.isNotBlank(bean.getRutaAdjunto05())) {
                beanResult.setNombreProveedor(bean.getNombreProveedor05());
                beanResult.setRutaAdjunto05(bean.getRutaAdjunto05());
                beanResult.setArchivoNombre05(bean.getArchivoNombre05());
                beanResult.setDescripcion05(bean.getDescripcion05());
            }
            if (StringUtils.isNotBlank(bean.getRutaAdjunto06())) {
                beanResult.setNombreProveedor(bean.getNombreProveedor06());
                beanResult.setRutaAdjunto06(bean.getRutaAdjunto06());
                beanResult.setArchivoNombre06(bean.getArchivoNombre06());
                beanResult.setDescripcion06(bean.getDescripcion06());
            }
            if (StringUtils.isNotBlank(bean.getRutaAdjunto07())) {
                beanResult.setNombreProveedor(bean.getNombreProveedor07());
                beanResult.setRutaAdjunto07(bean.getRutaAdjunto07());
                beanResult.setArchivoNombre07(bean.getArchivoNombre07());
                beanResult.setDescripcion07(bean.getDescripcion07());
            }
            if (StringUtils.isNotBlank(bean.getRutaAdjunto08())) {
                beanResult.setNombreProveedor(bean.getNombreProveedor08());
                beanResult.setRutaAdjunto08(bean.getRutaAdjunto08());
                beanResult.setArchivoNombre08(bean.getArchivoNombre08());
                beanResult.setDescripcion08(bean.getDescripcion08());
            }
            if (StringUtils.isNotBlank(bean.getRutaAdjunto09())) {
                beanResult.setNombreProveedor(bean.getNombreProveedor09());
                beanResult.setRutaAdjunto09(bean.getRutaAdjunto09());
                beanResult.setArchivoNombre09(bean.getArchivoNombre09());
                beanResult.setDescripcion09(bean.getDescripcion09());
            }
            if (StringUtils.isNotBlank(bean.getRutaAdjunto10())) {
                beanResult.setNombreProveedor(bean.getNombreProveedor10());
                beanResult.setRutaAdjunto10(bean.getRutaAdjunto10());
                beanResult.setArchivoNombre10(bean.getArchivoNombre10());
                beanResult.setDescripcion10(bean.getDescripcion10());
            }


        }
        listaFinal.add(beanResult);
        return ResponseEntity.ok().body(listaFinal);
    }

    @RequestMapping(value = "/findByCotizacionAdjuntoProveedor", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<CotizacionAdjunto>> findByCotizacionAdjuntoProveedor(
            @RequestBody Map<String, Object> json)  {
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
        List<CotizacionAdjunto> lista = this.cotizacionAdjuntoRepository.findByLicitacionProveedor(licitacion, proveedor);
        return ResponseEntity.ok().body(lista);
    }
}
