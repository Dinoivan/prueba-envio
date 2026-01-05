package com.incloud.hcp.service;

import com.incloud.hcp.domain.TipoInformacionNoticia;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by MARCELO on 26/09/2017.
 */
public interface TipoInformacionNoticiaService {

    public List<TipoInformacionNoticia> getAll();

    public ResponseEntity<Map> save(TipoInformacionNoticia tipoInformacionNoticia);

    public ResponseEntity<Map> update(TipoInformacionNoticia tipoInformacionNoticia);

    public ResponseEntity<Map> delete(TipoInformacionNoticia tipoInformacionNoticia);

    public void actualizarCarpetaIdTipoInformacionById(Integer id,String carpetaId);
}
