package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.InformacionNoticia;
import com.incloud.hcp.repository.InformacionNoticiaRepository;
import com.incloud.hcp.repository.TipoInformacionNoticiaRepository;
import com.incloud.hcp.service.InformacionNoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by MARCELO on 26/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class InformacionNoticiaServiceImpl implements InformacionNoticiaService {

    @Autowired
    InformacionNoticiaRepository informacionNoticiaRepository;

    @Autowired
    TipoInformacionNoticiaRepository tipoInformacionNoticiaRepository;

    @Override
    public InformacionNoticia getInformacionNoticiaById(Integer id) {
        return Optional.ofNullable(informacionNoticiaRepository)
                .map(i -> i.getOne(id))
                .orElse(null);
    }

    @Override
    public List<InformacionNoticia> getInfoNoticiaByTipoInfoNoticia(int idTipoInformacionNoticia) {
        return informacionNoticiaRepository
                .findByTipoInformacionNoticia(tipoInformacionNoticiaRepository
                        .getOne(idTipoInformacionNoticia));
    }

    @Override
    public ResponseEntity<Map> save(InformacionNoticia info) {
        ResponseEntity response;
        Map map = new HashMap<>();
        try {
            List<InformacionNoticia> informacion = informacionNoticiaRepository.findByTituloTipo(info.getTitulo(),
                    info.getTipoInformacionNoticia().getIdTipoInformacionNoticia());
            if(informacion != null && !informacion.isEmpty()){
                map.put("message", "El título a ingresar se encuentra registrado");
                response = new ResponseEntity(map, HttpStatus.OK);
            } else {
                info.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
                info.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
                info.setUsuarioCreacion(1);
                info.setUsuarioModificacion(1);
                info.setFechaCaducidad(new Timestamp(System.currentTimeMillis()));
                info.setFechaPublicacion(new Timestamp(System.currentTimeMillis()));
                info.setTextoInfo("Ver Documento");
                info.setIconText("");
                info.setRutaAdjunto("");
                info.setIndNoticiaNuevoProveedor("");
                info.setIndActivo("");
                map.put("data", informacionNoticiaRepository.save(info));
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        } catch (Exception e){
            map = new HashMap<>();
            map.put("message",e.getMessage());
            response = new ResponseEntity(map, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseEntity<Map> update(InformacionNoticia info) {
        ResponseEntity response;
        Map map = new HashMap<>();
        try {
            List<InformacionNoticia> informacion = informacionNoticiaRepository.findByTituloById
                    (info.getIdInformacionNoticia(), info.getTitulo());
            if(informacion != null && !informacion.isEmpty()){
                map.put("message", "El título a ingresar se encuentra registrado");
                response = new ResponseEntity(map, HttpStatus.OK);
            } else {
                map.put("data", informacionNoticiaRepository.save(info));
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        } catch (Exception e){
            map.put("message",e.getMessage());
            response = new ResponseEntity(map, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseEntity<Map> delete(InformacionNoticia info) {
        ResponseEntity<Map> response = null;
        Map map = null;
        try {
            informacionNoticiaRepository.delete(info);
            map = new HashMap<>();
            map.put("data", info);
            response = new ResponseEntity<Map>(map, HttpStatus.OK);
        } catch (Exception e){
            map = new HashMap<>();
            map.put("message", e.getMessage());
            response = new ResponseEntity<Map>(map, HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
