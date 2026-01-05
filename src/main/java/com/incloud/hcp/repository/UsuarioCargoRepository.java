package com.incloud.hcp.repository;

import com.incloud.hcp.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioCargoRepository extends JpaRepository<UsuarioCargo, Integer> {

    List<UsuarioCargo> findByIdUsuario(Usuario idUsuario);

    @Modifying
    @Query("DELETE FROM UsuarioCargo uc WHERE uc.idUsuario = ?1")
    void deleteByUsuario(Integer idUsuario);

    @Query("SELECT uc FROM UsuarioCargo uc WHERE uc.idUsuario = ?1")
    List<UsuarioCargo> findListaByIdUsuario(Integer idUsuario);

}