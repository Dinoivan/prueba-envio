package com.incloud.hcp.repository;

import com.incloud.hcp.domain.CondicionLic;
import com.incloud.hcp.domain.TipoLicitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 24/09/2017.
 */
public interface CondicionLicRepository extends JpaRepository<CondicionLic, Integer> {

    List<CondicionLic> findByTipoLicitacion1AndTipoLicitacion2(TipoLicitacion objTipoLicitacion,
                                                               TipoLicitacion objTipoCuestionario);

    @Modifying
    @Query("DELETE FROM CondicionLic t WHERE t.idCondicion= ?1")
    void deleteCondicionLicByIdCondicion(Integer idCondicion);

    @Query("SELECT t FROM CondicionLic t WHERE t.tipoLicitacion1.idTipoLicitacion= ?1 or t.tipoLicitacion2.idTipoLicitacion= ?1")
    List<CondicionLic> findByIdTipoLicitacionOrTipoCuestionario(Integer id);


}
