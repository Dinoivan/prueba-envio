package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Ubigeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 14/09/2017.
 */
public interface UbigeoRepository extends JpaRepository<Ubigeo, Integer> {

    public List<Ubigeo> findByNivelOrderByDescripcion(Integer idNivel);

    Ubigeo findByNivelAndDescripcionOrderByCodigoUbigeoSap(Integer idNivel,String descripcion);

    Ubigeo findByNivelAndDescripcionAndIdPadre(Integer idNivel, String descripcion, Integer idPadre);

    Ubigeo findByNivelAndDescripcion(Integer idNivel, String descripcion);

    Ubigeo findByCodigoUbigeoSapAndNivel(String codigo,Integer nivel);

    Ubigeo findByCodigoUbigeoSapErpAndNivel(String codigo,Integer nivel);

    Ubigeo findByCodigoUbigeoSapErpAndNivelAndIdPadre(String codigo, Integer nivel, Integer idPadre);

    @Modifying
    @Query("UPDATE Ubigeo u set u.codigoUbigeoSapErp=?1 where u.nivel=?2 and u.descripcion=?3")
    public void actualizarUbigeobyNivelandDescripcion(String codigoSap, Integer nivel, String descripcion);

    @Modifying
    @Query("UPDATE Ubigeo u set u.codigoUbigeoSapErp=?1 where u.nivel=?2 and u.descripcion=?3 and u.idPadre = ?4 ")
    public void actualizarUbigeobyNivelandDescripcionAndIdPadre(String codigoSap, Integer nivel, String descripcion, Integer idPadre);


}
