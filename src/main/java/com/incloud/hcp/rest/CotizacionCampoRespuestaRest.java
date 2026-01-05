package com.incloud.hcp.rest;

import com.incloud.hcp.config.util.AppConstants;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.rest.bean.CondicionCotizacionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by USER on 31/08/2017.
 */
@RestController
@RequestMapping(value = "/api/cotizacionCampoRespuesta")
public class CotizacionCampoRespuestaRest extends AppRest {

    private static Logger logger = LoggerFactory.getLogger(CotizacionCampoRespuestaRest.class);

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private GrupoCondicionLicRespuestaRepository grupoCondicionLicRespuestaRepository;

    @Autowired
    private CotizacionCampoRespuestaRepository cotizacionCampoRespuestaRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private LicitacionGrupoCondicionLicRepository licitacionGrupoCondicionLicRepository;


    @RequestMapping(value = "/findByCotizacionCampoRespuesta", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<CondicionCotizacionDTO>> findByCotizacionCampoRespuesta(@RequestBody Integer idLicitacion)  {
        final int limiteProveedor = 10;
        Licitacion licitacion = new Licitacion();
        licitacion.setIdLicitacion(idLicitacion);

        String[] listaProveedor = new String[limiteProveedor];
        Boolean[] listaVisible = new Boolean[limiteProveedor];

//        List<Cotizacion> listaCotizacion = this.cotizacionRepository.
//                findByLicitacionAndSort(licitacion, new Sort("proveedor.razonSocial"));
        List<Cotizacion> listaCotizacion = this.cotizacionRepository.
                findByLicitacionPorAdjudicarAndSort(licitacion, Sort.by("proveedor.razonSocial"));

        logger.error("findByCotizacionCampoRespuesta 0 listaCotizacion: " + listaCotizacion.size());

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

        List<CondicionCotizacionDTO> listaPrecioCotizacionDTO = new ArrayList<CondicionCotizacionDTO>();
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "licitacionGrupoCondicionLic.tipoLicitacion1.descripcion"),
                new Sort.Order(Sort.Direction.ASC, "licitacionGrupoCondicionLic.tipoLicitacion2.descripcion"),
                new Sort.Order(Sort.Direction.ASC, "licitacionGrupoCondicionLic.pregunta"));

        List<CotizacionCampoRespuesta> listaDetalle = this.cotizacionCampoRespuestaRepository.
                findByAndSort(
                        licitacion,sort);

        logger.error("findByCotizacionCampoRespuesta 1 listaDetalle: " + listaDetalle.size());

        String gtipoLicitacion = "-1";
        String gtipoCuestionario = "-1";
        String gpregunta = "-1";
        CondicionCotizacionDTO beanDTO = null;
        for(CotizacionCampoRespuesta item:listaDetalle) {

            String razonSocial = item.getCotizacion().getProveedor().getRazonSocial();
            String tipoLicitacion = item.getLicitacionGrupoCondicionLic().getTipoLicitacion1().getDescripcion();
            String tipoCuestionario = item.getLicitacionGrupoCondicionLic().getTipoLicitacion2().getDescripcion();
            String pregunta = item.getLicitacionGrupoCondicionLic().getPregunta();
            String bloqueadoProveedor = item.getLicitacionGrupoCondicionLic().getIndBloqueadoProveedor();

            GrupoCondicionLicRespuesta beanRespuesta = item.getGrupoCondicionLicRespuesta();
            String respuesta = "";
            Integer idCondicionRespuesta = null;
            String textoLibre = item.getTextoRespuestaLibre();
            if (beanRespuesta != null) {
                respuesta = beanRespuesta.getOpcion();
                idCondicionRespuesta = beanRespuesta.getIdCondicionRespuesta();
            }

            String tipoCampo = item.getLicitacionGrupoCondicionLic().getTipoCampo();
            BigDecimal pesoPregunta = item.getLicitacionGrupoCondicionLic().getPeso();
            List<GrupoCondicionLicRespuesta> listaGrupoCondicionLicRespuesta = null;
            if (bloqueadoProveedor != null && bloqueadoProveedor.equals(AppConstants.UNO)) {
                listaGrupoCondicionLicRespuesta =
                        this.grupoCondicionLicRespuestaRepository.findByLicitacionGrupoCondicionLicAndIndPredefinido(
                                item.getLicitacionGrupoCondicionLic(), AppConstants.UNO);
                if (listaGrupoCondicionLicRespuesta ==  null || listaGrupoCondicionLicRespuesta.size() <= 0) {
                    listaGrupoCondicionLicRespuesta =
                            this.grupoCondicionLicRespuestaRepository.findByLicitacionGrupoCondicionLic(
                                    item.getLicitacionGrupoCondicionLic());
                }

            }
            else {
                listaGrupoCondicionLicRespuesta =
                        this.grupoCondicionLicRespuestaRepository.findByLicitacionGrupoCondicionLic(
                                item.getLicitacionGrupoCondicionLic());
            }
            if (!(gtipoLicitacion.equals(tipoLicitacion) &&
                    gtipoCuestionario.equals(tipoCuestionario) &&
                    gpregunta.equals(pregunta))) {
                if (beanDTO != null) {
                    listaPrecioCotizacionDTO.add(beanDTO);
                }
                gtipoLicitacion = tipoLicitacion;
                gtipoCuestionario = tipoCuestionario;
                gpregunta = pregunta;

                beanDTO = new CondicionCotizacionDTO();
                beanDTO.setTipoLicitacion(tipoLicitacion);
                beanDTO.setTipoCuestionario(tipoCuestionario);
                beanDTO.setPregunta(pregunta);
                beanDTO.setBloqueadoProveedor(bloqueadoProveedor);
                beanDTO.setEstaNoBloqueadoProveedor(true);
                if (bloqueadoProveedor != null && bloqueadoProveedor.equals(AppConstants.UNO)) {
                    beanDTO.setEstaNoBloqueadoProveedor(false);
                }

                beanDTO.setListaRespuestas(listaGrupoCondicionLicRespuesta);
                beanDTO.setTipoCampo(tipoCampo);
                beanDTO.setPesoPregunta(pesoPregunta);

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
                beanDTO.setRespuesta01(respuesta);
                beanDTO.setTextoRespuestaLibre01(textoLibre);
                beanDTO.setIdCondicionRespuesta01(idCondicionRespuesta);
            }
            if (razonSocial.equals(listaProveedor[1])) {
                beanDTO.setRespuesta02(respuesta);
                beanDTO.setTextoRespuestaLibre02(textoLibre);
                beanDTO.setIdCondicionRespuesta02(idCondicionRespuesta);
            }
            if (razonSocial.equals(listaProveedor[2])) {
                beanDTO.setRespuesta03(respuesta);
                beanDTO.setTextoRespuestaLibre03(textoLibre);
                beanDTO.setIdCondicionRespuesta03(idCondicionRespuesta);
            }
            if (razonSocial.equals(listaProveedor[3])) {
                beanDTO.setRespuesta04(respuesta);
                beanDTO.setTextoRespuestaLibre04(textoLibre);
                beanDTO.setIdCondicionRespuesta04(idCondicionRespuesta);
            }
            if (razonSocial.equals(listaProveedor[4])) {
                beanDTO.setRespuesta05(respuesta);
                beanDTO.setTextoRespuestaLibre05(textoLibre);
                beanDTO.setIdCondicionRespuesta05(idCondicionRespuesta);
            }
            if (razonSocial.equals(listaProveedor[5])) {
                beanDTO.setRespuesta06(respuesta);
                beanDTO.setTextoRespuestaLibre06(textoLibre);
                beanDTO.setIdCondicionRespuesta06(idCondicionRespuesta);
            }
            if (razonSocial.equals(listaProveedor[6])) {
                beanDTO.setRespuesta07(respuesta);
                beanDTO.setTextoRespuestaLibre07(textoLibre);
                beanDTO.setIdCondicionRespuesta07(idCondicionRespuesta);
            }
            if (razonSocial.equals(listaProveedor[7])) {
                beanDTO.setRespuesta08(respuesta);
                beanDTO.setTextoRespuestaLibre08(textoLibre);
                beanDTO.setIdCondicionRespuesta08(idCondicionRespuesta);
            }
            if (razonSocial.equals(listaProveedor[8])) {
                beanDTO.setRespuesta09(respuesta);
                beanDTO.setTextoRespuestaLibre09(textoLibre);
                beanDTO.setIdCondicionRespuesta09(idCondicionRespuesta);
            }
            if (razonSocial.equals(listaProveedor[9])) {
                beanDTO.setRespuesta10(respuesta);
                beanDTO.setTextoRespuestaLibre10(textoLibre);
                beanDTO.setIdCondicionRespuesta10(idCondicionRespuesta);
            }
        }
        if (beanDTO != null) {
            listaPrecioCotizacionDTO.add(beanDTO);
        }
        return ResponseEntity.ok().body(listaPrecioCotizacionDTO);
    }


    @RequestMapping(value = "/findByCotizacionCampoRespuestaProveedor", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<CondicionCotizacionDTO>> findByCotizacionCampoRespuestaProveedor(
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


        final int limiteProveedor = 10;
        String[] listaProveedor = new String[limiteProveedor];
        Boolean[] listaVisible = new Boolean[limiteProveedor];

        boolean encontroCotizacion = false;
        List<CondicionCotizacionDTO> listaPrecioCotizacionDTO = new ArrayList<CondicionCotizacionDTO>();
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "proveedor.razonSocial"));
        List<Cotizacion> listaCotizacion = this.cotizacionRepository.
                findByLicitacionAndProveedorSort(licitacion, proveedor, sort);
        if (listaCotizacion != null && listaCotizacion.size()> 0) {
            encontroCotizacion = true;
        }

        if (!encontroCotizacion) {
            listaProveedor[0] = proveedor.getRazonSocial();
            listaVisible[0] = true;
            Sort sort1 = Sort.by(new Sort.Order(Sort.Direction.ASC, "tipoLicitacion1.descripcion"),
                    new Sort.Order(Sort.Direction.ASC, "tipoLicitacion2.descripcion"),
                    new Sort.Order(Sort.Direction.ASC, "pregunta"));
            List<LicitacionGrupoCondicionLic> listaDetalle = this.licitacionGrupoCondicionLicRepository.
                    findByLicitacionAndSort(
                            licitacion,sort1);

            String gtipoLicitacion = "-1";
            String gtipoCuestionario = "-1";
            String gpregunta = "-1";
            CondicionCotizacionDTO beanDTO = null;

            for(LicitacionGrupoCondicionLic item:listaDetalle) {

                String razonSocial = proveedor.getRazonSocial();
                String tipoLicitacion = item.getTipoLicitacion1().getDescripcion();
                String tipoCuestionario = item.getTipoLicitacion2().getDescripcion();
                String pregunta = item.getPregunta();
                String bloqueadoProveedor = item.getIndBloqueadoProveedor();

                Integer idLicitacionGrupo = item.getIdLicitacionGrupoCondicion();
                GrupoCondicionLicRespuesta beanRespuesta = new GrupoCondicionLicRespuesta();

                beanDTO = new CondicionCotizacionDTO();
                beanDTO.setIdLicitacionGrupoCondicion(idLicitacionGrupo);

                String tipoCampo = item.getTipoCampo();
                BigDecimal pesoPregunta = item.getPeso();


                /* Obteniendo Respuesta Predeterminada */
                List<GrupoCondicionLicRespuesta> listaGrupoCondicionLicRespuesta = null;
                GrupoCondicionLicRespuesta grupoCondicionLicRespuestaBloqueado = null;
                if (bloqueadoProveedor != null && bloqueadoProveedor.equals(AppConstants.UNO)) { /* Para casos de Indicador Bloqueado en TRUE */
                    listaGrupoCondicionLicRespuesta =  this.grupoCondicionLicRespuestaRepository.findByLicitacionGrupoCondicionLicAndIndPredefinido(item, AppConstants.UNO);
                    if (listaGrupoCondicionLicRespuesta ==  null || listaGrupoCondicionLicRespuesta.size() <= 0) {
                        listaGrupoCondicionLicRespuesta =  this.grupoCondicionLicRespuestaRepository.findByLicitacionGrupoCondicionLic(item);
                    }
                    if (listaGrupoCondicionLicRespuesta != null && listaGrupoCondicionLicRespuesta.size() > 0)
                        grupoCondicionLicRespuestaBloqueado = listaGrupoCondicionLicRespuesta.get(0);
                }
                else { /* Los demas casos */
                    listaGrupoCondicionLicRespuesta = this.grupoCondicionLicRespuestaRepository.findByLicitacionGrupoCondicionLic(item);
                    List<GrupoCondicionLicRespuesta> listaGrupoCondicionLicRespuestaPredeterminado = this.grupoCondicionLicRespuestaRepository.
                            findByLicitacionGrupoCondicionLicAndIndPredefinido(item, AppConstants.UNO);;
                    if (listaGrupoCondicionLicRespuestaPredeterminado != null && listaGrupoCondicionLicRespuestaPredeterminado.size() > 0)  {
                        grupoCondicionLicRespuestaBloqueado = listaGrupoCondicionLicRespuestaPredeterminado.get(0);
                    }
                }


                String respuesta = "";
                Integer idCondicionRespuesta = null;
                String textoLibre = "";

                beanDTO.setTipoLicitacion(tipoLicitacion);
                beanDTO.setTipoCuestionario(tipoCuestionario);
                beanDTO.setPregunta(pregunta);
                beanDTO.setIdLicitacionGrupoCondicion(idLicitacionGrupo);
                beanDTO.setBloqueadoProveedor(bloqueadoProveedor);
                beanDTO.setEstaNoBloqueadoProveedor(true);
                if (bloqueadoProveedor != null && bloqueadoProveedor.equals(AppConstants.UNO)) {
                    beanDTO.setEstaNoBloqueadoProveedor(false);
                }

                beanDTO.setListaRespuestas(listaGrupoCondicionLicRespuesta);
                beanDTO.setTipoCampo(tipoCampo);
                beanDTO.setPesoPregunta(pesoPregunta);

                beanDTO.setVisible01(listaVisible[0]);
                beanDTO.setNombreProveedor01(listaProveedor[0]);


                beanDTO.setRespuesta01(respuesta);
                beanDTO.setTextoRespuestaLibre01(textoLibre);
                beanDTO.setIdCondicionRespuesta01(idCondicionRespuesta);
                if (!beanDTO.isEstaNoBloqueadoProveedor()) {
                    if (grupoCondicionLicRespuestaBloqueado != null) {
                        beanDTO.setRespuesta01(grupoCondicionLicRespuestaBloqueado.getOpcion());
                        beanDTO.setIdCondicionRespuesta01(grupoCondicionLicRespuestaBloqueado.getIdCondicionRespuesta());
                    }

                }
                else  {
                    if (grupoCondicionLicRespuestaBloqueado != null) {
                        beanDTO.setIdCondicionRespuesta01(grupoCondicionLicRespuestaBloqueado.getIdCondicionRespuesta());
                    }
                }
                listaPrecioCotizacionDTO.add(beanDTO);
            }


        }
        else  {
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
            Sort sort1 = Sort.by(new Sort.Order(Sort.Direction.ASC, "licitacionGrupoCondicionLic.tipoLicitacion1.descripcion"),
                    new Sort.Order(Sort.Direction.ASC, "licitacionGrupoCondicionLic.tipoLicitacion2.descripcion"),
                    new Sort.Order(Sort.Direction.ASC, "licitacionGrupoCondicionLic.pregunta"));
            List<CotizacionCampoRespuesta> listaDetalle = this.cotizacionCampoRespuestaRepository.
                    findByAndSort2(
                            licitacion,
                            proveedor,sort1);


            String gtipoLicitacion = "-1";
            String gtipoCuestionario = "-1";
            String gpregunta = "-1";
            CondicionCotizacionDTO beanDTO = null;
            for(CotizacionCampoRespuesta item:listaDetalle) {

                String razonSocial = item.getCotizacion().getProveedor().getRazonSocial();
                String tipoLicitacion = item.getLicitacionGrupoCondicionLic().getTipoLicitacion1().getDescripcion();
                String tipoCuestionario = item.getLicitacionGrupoCondicionLic().getTipoLicitacion2().getDescripcion();
                String pregunta = item.getLicitacionGrupoCondicionLic().getPregunta();
                String bloqueadoProveedor = item.getLicitacionGrupoCondicionLic().getIndBloqueadoProveedor();
                GrupoCondicionLicRespuesta beanRespuesta = item.getGrupoCondicionLicRespuesta();

                String tipoCampo = item.getLicitacionGrupoCondicionLic().getTipoCampo();
                BigDecimal pesoPregunta = item.getLicitacionGrupoCondicionLic().getPeso();

                /* Obteniendo Respuesta Predeterminada */
                List<GrupoCondicionLicRespuesta> listaGrupoCondicionLicRespuesta = null;
                GrupoCondicionLicRespuesta grupoCondicionLicRespuestaBloqueado = null;
                if (bloqueadoProveedor != null && bloqueadoProveedor.equals(AppConstants.UNO)) {
                    listaGrupoCondicionLicRespuesta = this.grupoCondicionLicRespuestaRepository.
                            findByLicitacionGrupoCondicionLicAndIndPredefinido(item.getLicitacionGrupoCondicionLic(), AppConstants.UNO);
                    if (listaGrupoCondicionLicRespuesta ==  null || listaGrupoCondicionLicRespuesta.size() <= 0) {
                        listaGrupoCondicionLicRespuesta = this.grupoCondicionLicRespuestaRepository.
                                findByLicitacionGrupoCondicionLic(item.getLicitacionGrupoCondicionLic());
                    }
                    if (listaGrupoCondicionLicRespuesta != null && listaGrupoCondicionLicRespuesta.size() > 0)
                        grupoCondicionLicRespuestaBloqueado = listaGrupoCondicionLicRespuesta.get(0);
                }
                else {
                    listaGrupoCondicionLicRespuesta = this.grupoCondicionLicRespuestaRepository.findByLicitacionGrupoCondicionLic(
                            item.getLicitacionGrupoCondicionLic());
                }


                String respuesta = "";
                Integer idCondicionRespuesta = null;
                String textoLibre = item.getTextoRespuestaLibre();
                if (beanRespuesta != null) {
                    respuesta = beanRespuesta.getOpcion();
                    idCondicionRespuesta = beanRespuesta.getIdCondicionRespuesta();
                }

                if (!(gtipoLicitacion.equals(tipoLicitacion) &&
                        gtipoCuestionario.equals(tipoCuestionario) &&
                        gpregunta.equals(pregunta))) {
                    if (beanDTO != null) {
                        listaPrecioCotizacionDTO.add(beanDTO);
                    }
                    gtipoLicitacion = tipoLicitacion;
                    gtipoCuestionario = tipoCuestionario;
                    gpregunta = pregunta;

                    beanDTO = new CondicionCotizacionDTO();
                    beanDTO.setTipoLicitacion(tipoLicitacion);
                    beanDTO.setTipoCuestionario(tipoCuestionario);
                    beanDTO.setPregunta(pregunta);
                    beanDTO.setIdLicitacionGrupoCondicion(item.getLicitacionGrupoCondicionLic().getIdLicitacionGrupoCondicion());
                    beanDTO.setBloqueadoProveedor(bloqueadoProveedor);
                    beanDTO.setEstaNoBloqueadoProveedor(true);
                    if (bloqueadoProveedor != null && bloqueadoProveedor.equals(AppConstants.UNO)) {
                        beanDTO.setEstaNoBloqueadoProveedor(false);
                    }
                    beanDTO.setListaRespuestas(listaGrupoCondicionLicRespuesta);
                    beanDTO.setTipoCampo(tipoCampo);
                    beanDTO.setPesoPregunta(pesoPregunta);

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
                    beanDTO.setRespuesta01(respuesta);
                    beanDTO.setTextoRespuestaLibre01(textoLibre);
                    beanDTO.setIdCondicionRespuesta01(idCondicionRespuesta);

                    if (!beanDTO.isEstaNoBloqueadoProveedor()) {
                        if (grupoCondicionLicRespuestaBloqueado != null) {
                            beanDTO.setRespuesta01(grupoCondicionLicRespuestaBloqueado.getOpcion());
                            beanDTO.setIdCondicionRespuesta01(grupoCondicionLicRespuestaBloqueado.getIdCondicionRespuesta());
                        }
                    }
                }
                if (razonSocial.equals(listaProveedor[1])) {
                    beanDTO.setRespuesta02(respuesta);
                    beanDTO.setTextoRespuestaLibre02(textoLibre);
                    beanDTO.setIdCondicionRespuesta02(idCondicionRespuesta);

                }
                if (razonSocial.equals(listaProveedor[2])) {
                    beanDTO.setRespuesta03(respuesta);
                    beanDTO.setTextoRespuestaLibre03(textoLibre);
                    beanDTO.setIdCondicionRespuesta03(idCondicionRespuesta);

                }
                if (razonSocial.equals(listaProveedor[3])) {
                    beanDTO.setRespuesta04(respuesta);
                    beanDTO.setTextoRespuestaLibre04(textoLibre);
                    beanDTO.setIdCondicionRespuesta04(idCondicionRespuesta);
                }
                if (razonSocial.equals(listaProveedor[4])) {
                    beanDTO.setRespuesta05(respuesta);
                    beanDTO.setTextoRespuestaLibre05(textoLibre);
                    beanDTO.setIdCondicionRespuesta05(idCondicionRespuesta);
                }
                if (razonSocial.equals(listaProveedor[5])) {
                    beanDTO.setRespuesta06(respuesta);
                    beanDTO.setTextoRespuestaLibre06(textoLibre);
                    beanDTO.setIdCondicionRespuesta06(idCondicionRespuesta);
                }
                if (razonSocial.equals(listaProveedor[6])) {
                    beanDTO.setRespuesta07(respuesta);
                    beanDTO.setTextoRespuestaLibre07(textoLibre);
                    beanDTO.setIdCondicionRespuesta07(idCondicionRespuesta);
                }
                if (razonSocial.equals(listaProveedor[7])) {
                    beanDTO.setRespuesta08(respuesta);
                    beanDTO.setTextoRespuestaLibre08(textoLibre);
                    beanDTO.setIdCondicionRespuesta08(idCondicionRespuesta);
                }
                if (razonSocial.equals(listaProveedor[8])) {
                    beanDTO.setRespuesta09(respuesta);
                    beanDTO.setTextoRespuestaLibre09(textoLibre);
                    beanDTO.setIdCondicionRespuesta09(idCondicionRespuesta);
                }
                if (razonSocial.equals(listaProveedor[9])) {
                    beanDTO.setRespuesta10(respuesta);
                    beanDTO.setTextoRespuestaLibre10(textoLibre);
                    beanDTO.setIdCondicionRespuesta10(idCondicionRespuesta);
                }
            }
            if (beanDTO != null) {
                listaPrecioCotizacionDTO.add(beanDTO);
            }
        }
        return ResponseEntity.ok().body(listaPrecioCotizacionDTO);
    }


    @RequestMapping(value = "/findByListaCotizacionCampoRespuesta", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<CotizacionCampoRespuesta>> findByListaCotizacionCampoRespuesta(@RequestBody Integer idLicitacion)  {
        final int limiteProveedor = 10;
        Licitacion licitacion = new Licitacion();
        licitacion.setIdLicitacion(idLicitacion);

        String[] listaProveedor = new String[limiteProveedor];
        Boolean[] listaVisible = new Boolean[limiteProveedor];
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "proveedor.razonSocial"));
        List<Cotizacion> listaCtoizacion = this.cotizacionRepository.
                findByLicitacionAndSort(licitacion, sort);

        int contador = 0;
        for(Cotizacion item: listaCtoizacion) {
            listaProveedor[contador] = item.getProveedor().getRazonSocial();
            listaVisible[contador] = true;
            contador++;
        }
        for(int i= contador; i < limiteProveedor; i++) {
            listaProveedor[i] = "";
            listaVisible[i] = false;
        }

        List<CondicionCotizacionDTO> listaPrecioCotizacionDTO = new ArrayList<CondicionCotizacionDTO>();
        Sort sort1 = Sort.by(new Sort.Order(Sort.Direction.ASC, "licitacionGrupoCondicionLic.tipoLicitacion1.descripcion"),
                new Sort.Order(Sort.Direction.ASC, "licitacionGrupoCondicionLic.tipoLicitacion2.descripcion"),
                new Sort.Order(Sort.Direction.ASC, "licitacionGrupoCondicionLic.pregunta"));
        List<CotizacionCampoRespuesta> listaDetalle = this.cotizacionCampoRespuestaRepository.
                findByAndSort(
                        licitacion,sort1);

        return ResponseEntity.ok().body(listaDetalle);
    }



}