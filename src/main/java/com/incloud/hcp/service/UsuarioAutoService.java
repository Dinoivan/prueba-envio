package com.incloud.hcp.service;

import com.incloud.hcp.domain.Usuario;


public interface UsuarioAutoService {

    Usuario save(Usuario user);

    Usuario update(Usuario user);

    void delete(Usuario user);
}
