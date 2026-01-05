package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.TipoLicitacion;
import com.incloud.hcp.dto.TipoLicitacionDto;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.service.TipoLicitacionService;
import com.incloud.hcp.util.constant.TipoLicitacionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by USER on 24/08/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class TipoLicitacionServiceImpl implements TipoLicitacionService {

    @Autowired
    private TipoLicitacionRepository tipoLicitacionRepository;

    @Autowired
    private RubroBienRepository rubroBienRepository;

    @Autowired
    private LicitacionProveedorTipoCuestionarioRepository licitacionProveedorTipoCuestionarioRepository;

    @Autowired
    private LicitacionGrupoCondicionLicRepository licitacionGrupoCondicionLicRepository;

    @Autowired
    private CondicionLicRepository condicionLicRepository;

    @Override
    public List<TipoLicitacion> getListTipoLicitacion() {
        List<TipoLicitacion> listaTipoLicitacion = new ArrayList<TipoLicitacion>();
        listaTipoLicitacion = tipoLicitacionRepository.findByNivel(TipoLicitacionConstant.NIVEL_TIPOLICITACION);
        return listaTipoLicitacion;
    }

    @Override
    public List<TipoLicitacion> getListTipoCuestionario(int codTipoLicitacion) {
        List<TipoLicitacion> listaTipoLicitacion = new ArrayList<TipoLicitacion>();
        listaTipoLicitacion = tipoLicitacionRepository.findByNivelAndIdPadre(TipoLicitacionConstant.NIVEL_TIPOCUESTIONARIO, codTipoLicitacion);
        return listaTipoLicitacion;
    }

    @Override
    public List<TipoLicitacionDto> getByNivel(int nivel) {
        List<TipoLicitacion> lista = nivel > 0 ? this.tipoLicitacionRepository.findByNivel(nivel) : this.tipoLicitacionRepository.findAll();
        List<TipoLicitacionDto> dtos = new ArrayList<>();

        if (lista != null && lista.size() == 0){
            return null;
        }

        TipoLicitacionDto obj = null;
        for(TipoLicitacion tipo: lista){
            obj = new TipoLicitacionDto(tipo.getIdTipoLicitacion(), tipo.getDescripcion(), tipo.getNivel());
            if (tipo.getIdPadre() != null && tipo.getIdPadre() > 0) {
                TipoLicitacion padre = this.tipoLicitacionRepository.getOne(tipo.getIdPadre());
                obj.setIdPadre(tipo.getIdPadre());
                obj.setDescripcionPadre(padre.getDescripcion());
            }
            dtos.add(obj);
        }
        return dtos;
    }

    @Override
    public ResponseEntity<Map> save(TipoLicitacion tipoLicitacion) {
        ResponseEntity response = null;
        Map map = new HashMap<>();
        try{
            List tipos;
            if(tipoLicitacion.getNivel() == TipoLicitacionConstant.NIVEL_TIPOLICITACION){
                tipoLicitacion.setIdPadre(null);
                tipos = tipoLicitacionRepository.findByDescripcion(tipoLicitacion.getNivel(), tipoLicitacion.getDescripcion());
            } else {
                tipos = tipoLicitacionRepository.findByDescripcionByIdPadre(tipoLicitacion.getIdPadre(), tipoLicitacion.getDescripcion());
            }
            if(tipos != null && !tipos.isEmpty()){
                map.put("message", "La descripción no debe ser igual a otra descripción ya registrada");
                response = new ResponseEntity(map, HttpStatus.OK);
            } else {
                map.put("data", tipoLicitacionRepository.save(tipoLicitacion));
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        } catch (Exception e){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseEntity<Map> update(TipoLicitacion tipoLicitacion) {
        ResponseEntity response = null;
        Map map = new HashMap<>();
        try{
            List tipos;
            if(tipoLicitacion.getNivel() == 1){
                tipos = tipoLicitacionRepository.findByDescripcionByIdDistinto(tipoLicitacion.getIdTipoLicitacion(), tipoLicitacion.getNivel(), tipoLicitacion.getDescripcion());
            } else {
                tipos = tipoLicitacionRepository.findByDescripcionByIdDistintoByPadre(tipoLicitacion.getIdTipoLicitacion(), tipoLicitacion.getIdPadre(), tipoLicitacion.getDescripcion());
            }
            if(tipos != null && !tipos.isEmpty()){
                map.put("message", "La descripción no debe ser igual a otra descripción ya registrada");
                response = new ResponseEntity(map, HttpStatus.OK);
            } else {
                map.put("data", tipoLicitacionRepository.save(tipoLicitacion));
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        } catch (Exception e){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseEntity<Map> delete(TipoLicitacion tipoLicitacion) {
        ResponseEntity response = null;
        Map map = new HashMap<>();
        try {
            if(this.existeTipoLicitacionOrTipoCuestionario(tipoLicitacion)){
                map.put("message", "No puede eliminar la información seleccionada");
                response = new ResponseEntity(map, HttpStatus.OK);
            } else {
                tipoLicitacionRepository.deleteById(tipoLicitacion.getIdTipoLicitacion());
                map.put("data", tipoLicitacion);
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        } catch (Exception e){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return response;
    }



    private boolean existeTipoLicitacionOrTipoCuestionario(TipoLicitacion tipo){
        if(tipo.getNivel() == 1){
            List hijos =tipoLicitacionRepository.findByIdPadreOrderByDescripcion(tipo.getIdTipoLicitacion());
            if(hijos != null && !hijos.isEmpty()){
                return true;
            }
        }
        List rubros = rubroBienRepository.findByIdTipoLicitacionOrTipoCuestionario(tipo.getIdTipoLicitacion());
        if(rubros != null && !rubros.isEmpty()){
            return true;
        }
        List Licitaciones = licitacionProveedorTipoCuestionarioRepository.findByIdTipoLicitacionOrTipoCuestionario(tipo.getIdTipoLicitacion());
        if(Licitaciones != null && !Licitaciones.isEmpty()){
            return true;
        }
        List grupos = licitacionGrupoCondicionLicRepository.findByIdTipoLicitacionOrTipoCuestionario(tipo.getIdTipoLicitacion());
        if(grupos != null && !grupos.isEmpty()){
            return true;
        }
        List condiciones = condicionLicRepository.findByIdTipoLicitacionOrTipoCuestionario(tipo.getIdTipoLicitacion());
        if(condiciones != null && !condiciones.isEmpty()){
            return true;
        }
        return false;
    }
}
