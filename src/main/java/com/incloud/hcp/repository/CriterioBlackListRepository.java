package com.incloud.hcp.repository;

import com.incloud.hcp.domain.CriteriosBlacklist;
import com.incloud.hcp.domain.TipoSolicitudBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by MARCELO on 13/09/2017.
 */
public interface CriterioBlackListRepository extends JpaRepository<CriteriosBlacklist, Integer> {

    public List<CriteriosBlacklist> findByTipoSolicitudBlacklist(TipoSolicitudBlacklist tipoSolicitudBlacklist);

    public List<CriteriosBlacklist> findByDescripcion(String descripcion);

    @Modifying
    @Query("select c from CriteriosBlacklist c where c.descripcion = ?1 and c.tipoSolicitudBlacklist = ?2")
    public List<CriteriosBlacklist> findByDescripcionAndByTipoSolicitudBlackList(String descripcion, TipoSolicitudBlacklist tipoSolicitudBlacklist);

    @Query("SELECT c FROM CriteriosBlacklist c  WHERE c.tipoSolicitudBlacklist.idTipoSolicitud=?1")
    List<CriteriosBlacklist> getListCriteriosByTipoSolicitud(int idTipoSolicitud);

}
