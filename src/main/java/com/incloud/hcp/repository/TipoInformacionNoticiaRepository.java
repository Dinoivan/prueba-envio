package com.incloud.hcp.repository;

import com.incloud.hcp.domain.TipoInformacionNoticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by MARCELO on 26/09/2017.
 */
public interface TipoInformacionNoticiaRepository extends JpaRepository<TipoInformacionNoticia, Integer> {

    public List<TipoInformacionNoticia> findByDescripcionIgnoreCase(String descripcion);

    @Modifying
    @Query("Update TipoInformacionNoticia t SET t.carpetaId= ?2 WHERE t.idTipoInformacionNoticia= ?1")
    public void actualizarCarpetaIdById(Integer id, String carpetaId);

}
