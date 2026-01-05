package com.incloud.hcp.service;

import com.incloud.hcp.domain.TipoLicitacion;
import com.incloud.hcp.dto.TipoLicitacionDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by USER on 24/08/2017.
 */
public interface TipoLicitacionService {

    List<TipoLicitacion> getListTipoLicitacion();

    List<TipoLicitacion> getListTipoCuestionario(int codTipoLicitacion);

    List<TipoLicitacionDto> getByNivel(int nivel);

    ResponseEntity<Map> save(TipoLicitacion tipoLicitacion);

    ResponseEntity<Map> update(TipoLicitacion tipoLicitacion);

    ResponseEntity<Map> delete(TipoLicitacion tipoLicitacion);

}
