package com.incloud.hcp.repository;

import com.incloud.hcp.domain.ClaseDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by USER on 24/08/2017.
 */
public interface ClaseDocumentoRepository extends JpaRepository<ClaseDocumento, Integer> {

    List<ClaseDocumento> findByNivel(int nivel);

    List<ClaseDocumento> findByNivelAndIdPadre(int nivel, int padre);


    List<ClaseDocumento> findByNivelAndTipoClaseDocumentoOrderByDescripcion(int nivel, String tipo);

    ClaseDocumento findByCodigoClaseDocumentoAndNivel(String codigo, int nivel);

}
