package com.incloud.hcp.service;

import com.incloud.hcp.domain.InformacionNoticia;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by MARCELO on 26/09/2017.
 */

public interface InformacionNoticiaService {

    public InformacionNoticia getInformacionNoticiaById(Integer id);

    public List<InformacionNoticia> getInfoNoticiaByTipoInfoNoticia(int idTipoInformacionNoticia);

    public ResponseEntity<Map> save(InformacionNoticia info);

    public ResponseEntity<Map> update(InformacionNoticia info);

    public ResponseEntity<Map> delete(InformacionNoticia info);
}
