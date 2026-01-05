package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.InformacionNoticia;
import com.incloud.hcp.domain.TipoInformacionNoticia;
import com.incloud.hcp.repository.InformacionNoticiaRepository;
import com.incloud.hcp.repository.TipoInformacionNoticiaRepository;
import com.incloud.hcp.service.TipoInformacionNoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MARCELO on 26/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class TipoInformacioNoticiaServiceImpl implements TipoInformacionNoticiaService {

    @Autowired
    TipoInformacionNoticiaRepository tipoInformacionNoticiaRepository;

    @Autowired
    InformacionNoticiaRepository informacionNoticiaRepository;

    @Override
    public ResponseEntity<Map> save(TipoInformacionNoticia tipoInformacionNoticia) {
        ResponseEntity response;
        Map map = new HashMap<>();
        try {
            List<TipoInformacionNoticia> tipos = tipoInformacionNoticiaRepository.findByDescripcionIgnoreCase(tipoInformacionNoticia.getDescripcion());
            if(tipos != null && tipos.size() > 0){
                map.put("message", "La descripción se encuentra registrada");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            } else {
                map.put("data", tipoInformacionNoticiaRepository.save(tipoInformacionNoticia));
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        } catch (Exception e){
            map.put("message",e.getMessage());
            response = new ResponseEntity(map, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseEntity<Map> update(TipoInformacionNoticia tipoInformacionNoticia) {
        ResponseEntity response;
        Map map = new HashMap<>();
        try {
            List list = tipoInformacionNoticiaRepository.findAll();
            if(this.existe(list, tipoInformacionNoticia)){
                map.put("message", "La descripción se encuentra registrada");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            } else {
                map.put("data", tipoInformacionNoticiaRepository.save(tipoInformacionNoticia));
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        } catch (Exception e){
            map.put("message",e.getMessage());
            response = new ResponseEntity(map, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseEntity<Map> delete(TipoInformacionNoticia tipoInformacionNoticia) {
        ResponseEntity response;
        Map map = new HashMap<>();
        try {
            List<InformacionNoticia> infos = informacionNoticiaRepository.findByTipoInformacionNoticia(tipoInformacionNoticia);
            if(infos != null && infos.size() > 0){
                map.put("message", "El tipo de Información no se puede eliminar");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            } else {
                tipoInformacionNoticiaRepository.delete(tipoInformacionNoticia);
                map.put("data", tipoInformacionNoticia);
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        } catch (Exception e){
            map.put("message",e.getMessage());
            response = new ResponseEntity(map, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public List<TipoInformacionNoticia> getAll() {
        return tipoInformacionNoticiaRepository.findAll();
    }

    @Override
    public void actualizarCarpetaIdTipoInformacionById(Integer id, String carpetaId) {
        tipoInformacionNoticiaRepository.actualizarCarpetaIdById(id, carpetaId);
    }

    private boolean existe(List<TipoInformacionNoticia> list, TipoInformacionNoticia tipo){
        if (list != null && list.size()>0){
            for (TipoInformacionNoticia l: list){
                if (tipo.getIdTipoInformacionNoticia() != l.getIdTipoInformacionNoticia()
                        && tipo.getDescripcion().equalsIgnoreCase(l.getDescripcion())){
                    return true;
                }
            }
        }
        return false;
    }
}
