package com.incloud.hcp.service;

import com.incloud.hcp.domain.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by MARCELO on 22/09/2017.
 */
public interface UsuarioService {

    public List<Usuario> getAllUsuario();

    public ResponseEntity<Map> save(Usuario user);

    public ResponseEntity<Map> update(Usuario user);

    public ResponseEntity<Map> delete(Usuario user);
}
