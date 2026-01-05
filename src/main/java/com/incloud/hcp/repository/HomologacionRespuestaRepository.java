package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Homologacion;
import com.incloud.hcp.domain.HomologacionRespuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by MARCELO on 13/10/2017.
 */
public interface HomologacionRespuestaRepository extends JpaRepository<HomologacionRespuesta, Integer> {

    public List<HomologacionRespuesta> findByHomologacionOrderByIdHomologacionRespuesta(Homologacion homologacion);

    @Modifying
    @Query("DELETE FROM HomologacionRespuesta r WHERE r.homologacion.idHomologacion= ?1")
    public void deleteHomologacionRespuestaByHomologacion(Integer id);
}
