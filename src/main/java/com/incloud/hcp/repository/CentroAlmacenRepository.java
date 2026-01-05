package com.incloud.hcp.repository;

import com.incloud.hcp.domain.CentroAlmacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 13/09/2017.
 */
public interface CentroAlmacenRepository extends JpaRepository<CentroAlmacen, Integer> {

    List<CentroAlmacen> findByNivelOrderByDenominacion(Integer nivel);

    List<CentroAlmacen> findByIdPadreOrderByDenominacion(Integer idPadre);

    List<CentroAlmacen> findByIdPadreInOrderByDenominacion(List<Integer> idPadre);

    CentroAlmacen findByCodigoSapAndNivel(String codigoSap, int nivel);

    CentroAlmacen findByIdPadreAndCodigoSapAndNivel(Integer idPadre, String codigoSap, int nivel);

    CentroAlmacen getByCodigoSap(String codigoSap);

    @Query("SELECT c FROM CentroAlmacen c WHERE c.nivel = 1 AND c.codigoSap = ?1")
    CentroAlmacen getByNivelCodigoSap(String codigoSap);

    @Query("SELECT c.idCentroAlmacen FROM CentroAlmacen c WHERE c.nivel = 1 AND c.codigoSap = ?1 group by c.codigoSap, c.id")
    List<Integer> getCentroxId(String codigoSap);
}
