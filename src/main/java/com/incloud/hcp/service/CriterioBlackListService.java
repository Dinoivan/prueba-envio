package com.incloud.hcp.service;

import com.incloud.hcp.domain.CriteriosBlacklist;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by MARCELO on 13/09/2017.
 */
public interface CriterioBlackListService {

    public List<CriteriosBlacklist> getAllCriterioBlackList();

    public List<CriteriosBlacklist> getCriterioBlackListByTipoSolicitud(int codTipoSolicitud);

    public ResponseEntity<Map> save(CriteriosBlacklist criteriosBlacklist);

    public ResponseEntity<Map> update(CriteriosBlacklist criteriosBlacklist);

    public ResponseEntity<Map> delete(CriteriosBlacklist criteriosBlacklist);
}
