package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrador on 18/09/2017.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByCodigoSap(String codigoSap);

    public Usuario findByEmail(String email);

    @Query("select u from Usuario u where u.codigoSap = ?2 and u.idUsuario <> ?1")
    List<Usuario> getUsuarioCodigoSapDistingbyId(Integer id, String codigoSap);

    @Query("select u from Usuario u where u.email = ?2 and u.idUsuario <> ?1")
    List<Usuario> getUsuarioCorreoDistingbyId(Integer id, String email);

    List<Usuario> findByCodigoUsuarioIdp(String idHcp);

    Usuario getByCodigoUsuarioIdp(String idHcp);

}
