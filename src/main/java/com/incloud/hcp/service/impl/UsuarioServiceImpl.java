package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.AprobadorSolicitud;
import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.repository.AprobadorSolicitudRepository;
import com.incloud.hcp.repository.UsuarioRepository;
import com.incloud.hcp.service.UsuarioService;
import com.incloud.hcp.util.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.stream.Collectors;

/**
 * Created by MARCELO on 22/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class UsuarioServiceImpl implements UsuarioService {

    private static Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AprobadorSolicitudRepository aprobadorSolicitudRepository;

    @Override
    public List<Usuario> getAllUsuario() {
        return usuarioRepository.findAll().stream()
                .filter(u-> !NumberUtils.stringIsLong(u.getCodigoUsuarioIdp()))
                .collect(Collectors.toList());
//        return usuarioRepository.findAll();
    }

    @Override
    public ResponseEntity<Map> save(Usuario user) {
        ResponseEntity response = null;
        Map map = new HashMap<>();
        try {
            if (usuarioRepository.findByCodigoSap(user.getCodigoSap()) != null){
                map.put("message", "El Código SAP " + user.getCodigoSap() + " sé encuentra asignado a un usuario registrado");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            } else if(usuarioRepository.findByEmail(user.getEmail()) != null) {
                map.put("message", "El correo "+user.getEmail()+" sé encuentra asignado a un usuario registrado" );
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            } else {
                user.setUsuarioCreacion(1);
                user.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
                user.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
                user.setUsuarioModificacion(1);
                map.put("data", usuarioRepository.save(user));
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        }catch (Exception e){
            logger.error("Excepción al guardar usuario: " + e.getMessage());
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseEntity<Map> update(Usuario user) {
        ResponseEntity response = null;
        Map map = new HashMap<>();
        try {
            List<Usuario> users = usuarioRepository.getUsuarioCodigoSapDistingbyId(user.getIdUsuario(), user.getCodigoSap());
            if (users != null && !users.isEmpty()){
                map.put("message", "El Código SAP " + user.getCodigoSap() + " sé encuentra asignado a un usuario registrado");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            } else {
                users = usuarioRepository.getUsuarioCorreoDistingbyId(user.getIdUsuario(), user.getEmail());
                if(users != null && !users.isEmpty()) {
                    map.put("message", "El correo "+user.getEmail() +" sé encuentra asignado a un usuario registrado" );
                    response = new ResponseEntity<Map>(map, HttpStatus.OK);
                } else {
                    user.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
                    user.setUsuarioModificacion(1);
                    map.put("data", usuarioRepository.save(user));
                    response = new ResponseEntity(map, HttpStatus.OK);
                }
            }
        }catch (Exception e){
            logger.error("Excepción al Editar usuario: " + e.getMessage());
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseEntity<Map> delete(Usuario user) {
        ResponseEntity response = null;
        Map map = new HashMap<>();
        try {
            List<AprobadorSolicitud> lista = aprobadorSolicitudRepository.findByUsuario(user);
            if( lista != null && !lista.isEmpty()){
                map.put("message", "El usuario a eliminar tiene solicitudes de aprobación");
                response = new ResponseEntity<Map>(map, HttpStatus.OK);
            } else {
                usuarioRepository.delete(user);
                map.put("data", user);
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        }catch (Exception e){
            logger.error("Excepción al Eliminar usuario: " + e.getMessage());
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
