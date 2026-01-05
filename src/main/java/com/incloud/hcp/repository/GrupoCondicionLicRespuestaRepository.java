package com.incloud.hcp.repository;

import com.incloud.hcp.domain.GrupoCondicionLicRespuesta;
import com.incloud.hcp.domain.LicitacionGrupoCondicionLic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 28/09/2017.
 */
public interface GrupoCondicionLicRespuestaRepository extends JpaRepository<GrupoCondicionLicRespuesta, Integer> {

    public List<GrupoCondicionLicRespuesta> findByLicitacionGrupoCondicionLic(
            LicitacionGrupoCondicionLic licitacionGrupoCondicionLic);

    public List<GrupoCondicionLicRespuesta> findByLicitacionGrupoCondicionLicAndIndPredefinido(
            LicitacionGrupoCondicionLic licitacionGrupoCondicionLic, String indPredefinido);


    @Modifying
    @Query("DELETE FROM GrupoCondicionLicRespuesta t WHERE t.licitacionGrupoCondicionLic.idLicitacionGrupoCondicion= ?1")
    void deleteGrupoRespuestaByGrupoCondicion(Integer idLicitacionGrupoCondicion);

}
