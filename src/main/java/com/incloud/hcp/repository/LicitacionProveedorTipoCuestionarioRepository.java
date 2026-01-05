package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionProveedorTipoCuestionario;
import com.incloud.hcp.domain.Proveedor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 21/09/2017.
 */
public interface LicitacionProveedorTipoCuestionarioRepository extends
        JpaRepository<LicitacionProveedorTipoCuestionario, Integer> {

    void deleteByLicitacion(Licitacion licitacion);

    @Modifying
    @Query("DELETE FROM LicitacionProveedorTipoCuestionario p WHERE p.licitacion.idLicitacion = ?1")
    void deleteByLicitacion02(Integer idLicitacion);

    void deleteByLicitacionAndProveedor(Licitacion licitacion, Proveedor proveedor);

    @Query("select u from LicitacionProveedorTipoCuestionario u where u.licitacion = ?1")
    List<LicitacionProveedorTipoCuestionario> findByLicitacionAndSort(
            Licitacion licitacion, Sort sort);

    @Query("select u from LicitacionProveedorTipoCuestionario u where u.tipoLicitacion.idTipoLicitacion = ?1 or u.tipoCuestionario.idTipoLicitacion = ?1")
    List<LicitacionProveedorTipoCuestionario> findByIdTipoLicitacionOrTipoCuestionario(Integer id);
}
