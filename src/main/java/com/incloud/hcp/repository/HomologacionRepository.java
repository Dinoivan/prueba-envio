package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Homologacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by MARCELO on 11/10/2017.
 */
public interface HomologacionRepository extends JpaRepository<Homologacion, Integer> {
    @Modifying
    @Query("UPDATE Homologacion t SET t.estado = ?1 WHERE t.idHomologacion=?2")
    public void updateHomologacion(String estado, Integer idHomologacion);
}
