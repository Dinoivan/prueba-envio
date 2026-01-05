package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionGrupoCondicionLic;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 28/09/2017.
 */
public interface LicitacionGrupoCondicionLicRepository extends JpaRepository<LicitacionGrupoCondicionLic, Integer> {

    @Modifying
    @Query("DELETE FROM LicitacionGrupoCondicionLic t WHERE t.licitacion.idLicitacion= ?1")
    void deleteGrupoCondicionByLicitacion(Integer idLicitacion);

    @Query("select u from LicitacionGrupoCondicionLic u where u.licitacion = ?1")
    List<LicitacionGrupoCondicionLic> findByLicitacionAndSort(Licitacion licitacion, Sort sort);

    @Query("select u from LicitacionGrupoCondicionLic u where u.tipoLicitacion1.idTipoLicitacion = ?1 or u.tipoLicitacion2.idTipoLicitacion = ?1")
    List<LicitacionGrupoCondicionLic> findByIdTipoLicitacionOrTipoCuestionario(Integer id);

    List<LicitacionGrupoCondicionLic> findByLicitacion(Licitacion licitacion);
}
