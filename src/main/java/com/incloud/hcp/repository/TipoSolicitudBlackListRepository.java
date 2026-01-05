package com.incloud.hcp.repository;

import com.incloud.hcp.domain.TipoSolicitudBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by MARCELO on 12/09/2017.
 */
public interface TipoSolicitudBlackListRepository extends JpaRepository<TipoSolicitudBlacklist, Integer> {

    public TipoSolicitudBlacklist findByIdTipoSolicitud(int idTipoSolicitud);
}
