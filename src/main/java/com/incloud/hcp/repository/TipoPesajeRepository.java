package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.TipoPesaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoPesajeRepository  extends JpaRepository<TipoPesaje,Integer> {

    @Query("SELECT t FROM TipoPesaje t where t.codigo = ?1")
    TipoPesaje  findByCodigo(String codigo);

    @Query("SELECT t FROM TipoPesaje t where t.codigo = ?1 and t.isDeleted != true")
    List<TipoPesaje>  findByCodigoList(String codigo);

    @Query("SELECT t FROM TipoPesaje t where t.isDeleted != true and t.estado = true")
    List<TipoPesaje>  listarPesajesActivos();

    @Query("SELECT t FROM TipoPesaje t where t.isDeleted != true")
    List<TipoPesaje>  listarPesajes();

}
