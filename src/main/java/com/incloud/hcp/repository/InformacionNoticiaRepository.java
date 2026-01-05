package com.incloud.hcp.repository;

import com.incloud.hcp.domain.InformacionNoticia;
import com.incloud.hcp.domain.TipoInformacionNoticia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by MARCELO on 26/09/2017.
 */
public interface InformacionNoticiaRepository extends JpaRepository<InformacionNoticia, Integer> {

    public List<InformacionNoticia> findByTipoInformacionNoticia(TipoInformacionNoticia tipoInformacionNoticia);

    @Modifying
    @Query("select i from InformacionNoticia i where LOWER(i.titulo) = LOWER(?1) and i.tipoInformacionNoticia.idTipoInformacionNoticia = ?2")
    public List<InformacionNoticia> findByTituloTipo(String titulo, Integer id);

    @Query("select i from InformacionNoticia i where i.idInformacionNoticia <> ?1 and LOWER(i.titulo) = LOWER(?2)")
    public List<InformacionNoticia> findByTituloById(Integer id, String titulo);

    @Transactional
    @Modifying
    @Query("UPDATE InformacionNoticia t SET t.archivoIdCf = ?2, t.archivoUrlCf = ?3 WHERE t.idInformacionNoticia=?1")
    public void updateMigraDoc(Integer id, String idArchivoCf, String archivoUrlCf);
}
