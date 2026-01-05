package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.CriteriosBlacklist;
import com.incloud.hcp.domain.SolicitudBlacklist;
import com.incloud.hcp.repository.CriterioBlackListRepository;
import com.incloud.hcp.repository.SolicitudBlackListRepository;
import com.incloud.hcp.repository.TipoSolicitudBlackListRepository;
import com.incloud.hcp.service.CriterioBlackListService;
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

/**
 * Created by MARCELO on 13/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class CriterioBlackListServiceImpl implements CriterioBlackListService {

    @Autowired
    private CriterioBlackListRepository criterioBlackListRepository;

    @Autowired
    private TipoSolicitudBlackListRepository tipoSolicitudBlackListRepository;

    @Autowired
    SolicitudBlackListRepository solicitudBlackListRepository;

    @Override
    public List<CriteriosBlacklist> getAllCriterioBlackList() {
        return criterioBlackListRepository.findAll();
    }

    @Override
    public List<CriteriosBlacklist> getCriterioBlackListByTipoSolicitud(int codTipoSolicitud) {
        return criterioBlackListRepository.
                findByTipoSolicitudBlacklist(tipoSolicitudBlackListRepository.findByIdTipoSolicitud(codTipoSolicitud));
    }

    @Override
     public ResponseEntity<Map> save(CriteriosBlacklist criteriosBlacklist) {
        ResponseEntity<Map> response;
        Map map = new HashMap<>();
        try {
            List<CriteriosBlacklist> criterios = criterioBlackListRepository.findByDescripcion(criteriosBlacklist.getDescripcion());
            if(criterios != null && !criterios.isEmpty()){
                map.put("message", "Ya éxiste un criterio con descripción, por favor modificar la descripción");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            } else {
                criteriosBlacklist.setUsuarioCreacion(criteriosBlacklist.getUsuarioCreacion() );
                criteriosBlacklist.setUsuarioModificacion(criteriosBlacklist.getUsuarioCreacion() );
                criteriosBlacklist.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
                criteriosBlacklist.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
                CriteriosBlacklist criteriosBlacklistNew = criterioBlackListRepository.save(criteriosBlacklist);

                map.put("data",criteriosBlacklistNew);
                response =  new ResponseEntity<Map>(map, HttpStatus.OK);
            }

        } catch (Exception e){
            map.put("message", e.getMessage());
            response = new ResponseEntity<Map>(map, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseEntity<Map> update(CriteriosBlacklist criteriosBlacklist) {
        ResponseEntity<Map> response ;
        Map map = new HashMap<>();
        List<CriteriosBlacklist> criterios;
        try {
            criterios = criterioBlackListRepository.findByDescripcionAndByTipoSolicitudBlackList(
                    criteriosBlacklist.getDescripcion(),
                    criteriosBlacklist.getTipoSolicitudBlacklist());
            if (criterios != null && !criterios.isEmpty()){
                map.put("message", "Ya éxiste un criterio con descripción, por favor modificar la descripción");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            } else {
                CriteriosBlacklist criteriosBlacklistNew = criterioBlackListRepository.save(criteriosBlacklist);
                map.put("data",criteriosBlacklist);
                response =  new ResponseEntity<Map>(map, HttpStatus.OK);
            }
        } catch (Exception e){
            map.put("message", e.getMessage());
            response = new ResponseEntity<Map>(map, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseEntity<Map> delete(CriteriosBlacklist criteriosBlacklist) {
        ResponseEntity<Map> response ;
        Map map = new HashMap<>();
        List<SolicitudBlacklist> solicitudes;
        try {
            solicitudes = solicitudBlackListRepository.findByCriteriosBlacklist(criteriosBlacklist);
            if(solicitudes != null && !solicitudes.isEmpty()){
                map.put("message","Una Solicitud esta usando el Criterio");
                response =  new ResponseEntity<Map>(map, HttpStatus.OK);
            } else {
                criterioBlackListRepository.delete(criteriosBlacklist);
                map.put("data",criteriosBlacklist);
                response =  new ResponseEntity<Map>(map, HttpStatus.OK);
            }
        } catch (Exception e){
            map.put("message", e.getMessage());
            response = new ResponseEntity<Map>(map, HttpStatus.NOT_FOUND);
        }
        return response;
    }

}
