package com.incloud.hcp.repository;

import com.incloud.hcp.domain.CriteriosBlacklist;
import com.incloud.hcp.domain.SolicitudBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrador on 14/09/2017.
 */
public interface SolicitudBlackListRepository extends JpaRepository<SolicitudBlacklist, Integer> {

    @Query(" SELECT s FROM SolicitudBlacklist s WHERE s.proveedor.idProveedor = ?1 " +
           " AND s.criteriosBlacklist.tipoSolicitudBlacklist.idTipoSolicitud = ?2 " +
           " AND s.estadoSolicitud = ?3")
    List<SolicitudBlacklist> getListNoConformeByIdProveedorAndEstado(Integer idProveedor, Integer idTipoSolicitud, String estado);

    List<SolicitudBlacklist> findAllByOrderByFechaCreacionDesc();

    List<SolicitudBlacklist> findByEstadoSolicitudOrderByFechaCreacionDesc(String estado);

    List<SolicitudBlacklist> findByCriteriosBlacklist(CriteriosBlacklist criteriosBlacklist);

    List<SolicitudBlacklist> findAllByEstadoSolicitud(String estado);
}
