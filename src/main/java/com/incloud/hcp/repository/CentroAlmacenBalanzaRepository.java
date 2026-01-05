package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.CentroAlmacenBalanza;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CentroAlmacenBalanzaRepository extends JpaRepository<CentroAlmacenBalanza, Integer> {

    @Query("SELECT ca FROM CentroAlmacenBalanza ca where ca.usuario=?1")
    List<CentroAlmacenBalanza> buscarUsuario(String usuario);

    @Transactional
    @Modifying
    @Query("DELETE FROM CentroAlmacenBalanza ca where ca.id=?1")
    void deleteByUsuario(Integer usuario);

}
