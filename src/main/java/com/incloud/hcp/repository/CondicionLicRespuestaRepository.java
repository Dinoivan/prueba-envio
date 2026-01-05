package com.incloud.hcp.repository;

import com.incloud.hcp.domain.CondicionLicRespuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by USER on 25/09/2017.
 */
public interface CondicionLicRespuestaRepository extends JpaRepository<CondicionLicRespuesta, Integer> {

/*    @Query("select u from CondicionLicRespuesta u where u.condicionLic.tipoLicitacion1 = ?1 and u.condicionLic.tipoLicitacion2 = ?2")
    List<CondicionLicRespuesta> getRespuestas(
            TipoLicitacion objTipoLicitacion,
            TipoLicitacion objTipoCuestionario);

    List<CondicionLicRespuesta> findByCondicionLic (CondicionLic condicion);*/

    @Modifying
    @Query("DELETE FROM CondicionLicRespuesta t WHERE t.condicionLic.idCondicion= ?1")
    void deleteCondicionLicRespuestaByIdCondicion(Integer idCondicion);
}
