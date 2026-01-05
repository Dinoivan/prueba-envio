package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Ccomparativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 18/09/2017.
 */
public interface CcomparativoRepository extends JpaRepository<Ccomparativo, Integer> {

    @Modifying
    @Query("DELETE FROM Ccomparativo d WHERE d.licitacion.idLicitacion= ?1")
    void deleteByLicitacion(Integer idLicitacion);

    @Query("SELECT d FROM Ccomparativo d WHERE d.licitacion.idLicitacion= ?1")
    List<Ccomparativo> findByLicitacion(Integer idLicitacion);




}
