package com.incloud.hcp.repository;

import com.incloud.hcp.domain.TipoLicitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 24/08/2017.
 */
public interface TipoLicitacionRepository extends JpaRepository<TipoLicitacion, Integer> {

    public List<TipoLicitacion> findByNivel(int nivel);

    public List<TipoLicitacion> findByNivelAndIdPadre(int nivel, int padre);

    public List<TipoLicitacion> findByNivelOrderByDescripcion(Integer nivel);

    public List<TipoLicitacion> findByIdPadreOrderByDescripcion(Integer idPadre);

    public List<TipoLicitacion> findByIdPadreInOrderByDescripcion(List<Integer> idPadre);

    @Query("select t from TipoLicitacion t where t.nivel = ?1 and LOWER(t.descripcion) = LOWER(?2)")
    public List<TipoLicitacion> findByDescripcion(Integer nivel, String descripcion);

    @Modifying
    @Query("select t from TipoLicitacion t where t.idPadre = ?1 and LOWER(t.descripcion) = LOWER(?2)")
    public List<TipoLicitacion> findByDescripcionByIdPadre(Integer idPadre, String descripcion);

    @Query("select t from TipoLicitacion t where t.idTipoLicitacion<> ?1 and t.nivel = ?2 and LOWER(t.descripcion) = LOWER(?3)")
    public List<TipoLicitacion> findByDescripcionByIdDistinto(Integer id, Integer nivel,String descripcion);

    @Query("select t from TipoLicitacion t where t.idTipoLicitacion<> ?1 and t.idPadre = ?2 and LOWER(t.descripcion) = LOWER(?3)")
    public List<TipoLicitacion> findByDescripcionByIdDistintoByPadre(Integer id, Integer idPadre, String descripcion);

    TipoLicitacion getByDescripcion(String descripcion);

    TipoLicitacion getByDescripcionAndIdPadre(String descripcion, Integer idPadre);

}
