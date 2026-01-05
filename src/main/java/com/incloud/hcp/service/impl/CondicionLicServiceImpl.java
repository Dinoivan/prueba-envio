package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.Condicion;
import com.incloud.hcp.bean.RespuestaCondicion;
import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.CondicionLic;
import com.incloud.hcp.domain.CondicionLicRespuesta;
import com.incloud.hcp.domain.TipoLicitacion;
import com.incloud.hcp.myibatis.mapper.CondicionMapper;
import com.incloud.hcp.myibatis.mapper.RespuestaCondicionMapper;
import com.incloud.hcp.repository.CondicionLicRepository;
import com.incloud.hcp.repository.CondicionLicRespuestaRepository;
import com.incloud.hcp.service.CondicionLicService;
import com.incloud.hcp.service._framework.BaseServiceImpl;
import com.incloud.hcp.util.Log;
import com.sap.cloud.security.xsuaa.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by USER on 24/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class CondicionLicServiceImpl extends BaseServiceImpl implements CondicionLicService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CondicionMapper condicionMapper;

    @Autowired
    private RespuestaCondicionMapper respuestaCondicionMapper;

    @Autowired
    private CondicionLicRepository condicionLicRepository;

    @Autowired
    private CondicionLicRespuestaRepository condicionLicRespuestaRepository;


    @Override
    public List<Condicion> getListCondicionLicByTipoLicitacionAndTipoCuestionario(
            int idTipoLicitacion, int idTipoCuestionario) {

        List<Condicion> listaCondiciones = condicionMapper.getListCondicionByTipoLicitacionAndCuestionario(idTipoLicitacion,
                idTipoCuestionario);

        listaCondiciones.forEach(condicion -> {
            List<RespuestaCondicion> listaRespuestas = respuestaCondicionMapper.
                    getListRespuestaCondicion(condicion.getIdCondicion());
            condicion.setListaRespuestas(listaRespuestas);

            if (listaRespuestas.size() > 0) {
                listaRespuestas.forEach(respuesta -> {
                    if (("1").equals(respuesta.getIndPredefinido())) {
                        condicion.setRespuestaPredefinida(respuesta.getOpcion());
                    }
                });
            }
        });

        return listaCondiciones;
    }

    @Override
    public BigDecimal getTotalPesoByTipoCuestionario(int idTipoLicitacion, int idTipoCuestionario) {
        List<Condicion> listaCondiciones = condicionMapper.getListCondicionByTipoLicitacionAndCuestionario(idTipoLicitacion,
                idTipoCuestionario);

        BigDecimal totalPesoCuestionario = new BigDecimal(0);

        if (listaCondiciones.size() > 0) {
            for (Condicion condicion : listaCondiciones) {
                totalPesoCuestionario = totalPesoCuestionario.add(condicion.getPeso());
            }
        }

        return totalPesoCuestionario;
    }

    @Override
    public String saveCondicionLicitacion(Condicion obj, Token token) {
        UserSession userSession = this.getUserSession(token);
        Timestamp fechaSistema = new Timestamp(new Date().getTime());
        String response="exito";
        CondicionLic condicionLic;
        TipoLicitacion tipoLicitacion = new TipoLicitacion();
        TipoLicitacion tipoCuestionario = new TipoLicitacion();
        tipoLicitacion.setIdTipoLicitacion(obj.getIdTipoLicitacion());
        tipoCuestionario.setIdTipoLicitacion(obj.getIdTipoCuestionario());

        if (obj.getIdCondicion() != null){
            condicionLic = condicionLicRepository.getOne(obj.getIdCondicion());
            condicionLic.setUsuarioModificacion(userSession.getId());
            condicionLic.setFechaModificacion(fechaSistema);
        }else{
            condicionLic = new CondicionLic();
            condicionLic.setUsuarioCreacion(userSession.getId());
            condicionLic.setFechaCreacion(fechaSistema);
        }

        condicionLic.setPregunta(obj.getPregunta());
        condicionLic.setPeso(obj.getPeso());
        //String tipoCampo = ("Lista").equals(obj.getTipoCampo()) ? "L" : "T";
        condicionLic.setTipoCampo(obj.getTipoCampo());
        condicionLic.setIndBloqueadoProveedor(obj.getIndBloqueadoProveedor());
        condicionLic.setTipoLicitacion1(tipoLicitacion);
        condicionLic.setTipoLicitacion2(tipoCuestionario);
        CondicionLic condicionLicGuardada = condicionLicRepository.save(condicionLic);


        List<RespuestaCondicion> listaRespuestas = obj.getListaRespuestas();
        //condicionLicRespuestaRepository.deleteCondicionLicRespuestaByIdCondicion(condicionLicGuardada.getIdCondicion());

        if (listaRespuestas != null && listaRespuestas.size() > 0) {
            List<RespuestaCondicion> listaRespuestasExistentes = respuestaCondicionMapper.getListRespuestaCondicion(condicionLicGuardada.getIdCondicion());

            //Se eliminan registros antiguos que no se encuentran en la lista nueva
            listaRespuestasExistentes.forEach(rptaExist->{
                Boolean found = false;
                for (RespuestaCondicion rptaNew : listaRespuestas) {
                    if (rptaNew.getIdCondicionRespuesta() != null && rptaExist.getIdCondicionRespuesta().equals(rptaNew.getIdCondicionRespuesta())) {
                        found = true;
                    }
                }
                if (!found){
                    condicionLicRespuestaRepository.deleteById(rptaExist.getIdCondicionRespuesta());
                }
            });

            //Se actualizan o se insertan los registros de la lista nueva
            CondicionLicRespuesta condicionLicRespuesta;
            for (RespuestaCondicion rpta : listaRespuestas){

                if (rpta.getIdCondicionRespuesta() != null){
                    condicionLicRespuesta = condicionLicRespuestaRepository.getOne(rpta.getIdCondicionRespuesta());
                    condicionLicRespuesta.setUsuarioModificacion(userSession.getId());
                    condicionLicRespuesta.setFechaModificacion(fechaSistema);
                }else{
                    condicionLicRespuesta = new CondicionLicRespuesta();
                    condicionLicRespuesta.setUsuarioCreacion(userSession.getId());
                    condicionLicRespuesta.setFechaCreacion(fechaSistema);
                }
                condicionLicRespuesta.setOpcion(rpta.getOpcion());
                condicionLicRespuesta.setCondicionLic(condicionLicGuardada);
                condicionLicRespuesta.setPuntaje(rpta.getPuntaje());
                condicionLicRespuesta.setIndPredefinido(rpta.getIndPredefinido());
                condicionLicRespuestaRepository.save(condicionLicRespuesta);
            }
        }else{
            condicionLicRespuestaRepository.deleteCondicionLicRespuestaByIdCondicion(condicionLicGuardada.getIdCondicion());
        }

        return response;
    }



    @Override
    public Map deleteCondicionLicByIdCondicion(ArrayList<Integer> ids) {
        Map map = new HashMap<>();

        try{
            Integer count = 0;
            for (Integer id : ids) {
                CondicionLic condicionLic = condicionLicRepository.getOne(id);
                if (condicionLic != null) {
                    condicionLicRespuestaRepository.deleteCondicionLicRespuestaByIdCondicion(id);
                    condicionLicRepository.deleteCondicionLicByIdCondicion(id);
                    count++;
                }
            };
            logger.debug("Se eliminaron las condiciones");
            map.put("count", count);
            if (count > 0){
                map.put("log", new Log("1", "Los registros han sido eliminados"));
            }else{
                map.put("log", new Log("1", "No hay elementos para eliminar"));
            }

        }catch (Exception e){
            logger.error("Error al eliminar condicion: " + e.getMessage());
            map.put("log", new Log("0", e.toString()));
        }
        return map;

    }




}
