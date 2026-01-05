package com.incloud.hcp.repository;

import com.incloud.hcp.domain.LineaComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrador on 28/08/2017.
 */
public interface LineaComercialRepository extends JpaRepository<LineaComercial, Integer> {

    @Query("SELECT l FROM LineaComercial l WHERE l.idPadre= ?1")
    List<LineaComercial> getListByIdParent(Integer idPadre);

    @Query("SELECT l FROM LineaComercial l WHERE l.nivel = ?1 order by l.descripcion")
    List<LineaComercial> getListByNivel(Integer nivel);

    @Query("SELECT l FROM LineaComercial l WHERE l.nivel = ?1 AND l.indGeneral <> 'G'")
    List<LineaComercial> getListByNivelAndWithoutIndGeneral(Integer nivel);

    @Query("SELECT l FROM LineaComercial l WHERE l.idLineaComercial = ?1")
    LineaComercial getLineaComercialById(Integer idLineaComercial);

    LineaComercial getByDescripcion(String descripcion);

    LineaComercial getByCodigoGrupoComercial(String codigoGrupoComercial);



}
