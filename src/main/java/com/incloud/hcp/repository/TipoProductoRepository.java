package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.TicketPesaje;
import com.incloud.hcp.domain.balanza.TipoPesaje;
import com.incloud.hcp.domain.balanza.TipoProducto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import java.util.List;

public interface TipoProductoRepository extends JpaRepository<TipoProducto,Integer> {
    @Query("SELECT t FROM TipoProducto t where t.codigo = ?1")
    TipoProducto findByCodigo(String codigo);

    @Query("SELECT t FROM TipoProducto t where t.codigo like %:codigo% and t.isDeleted != true")

    TipoProducto findByCodigoLike(@Param("codigo") String codigo );

    @Query("SELECT t FROM TipoProducto t where t.codigo = ?1 and t.isDeleted != true")
    List<TipoProducto> findByCodigoList(String codigo);

    @Transactional
    @Modifying
    @Query("DELETE FROM TipoProducto ca where ca.id=?1")
    void deleteById(Integer id);

    @Query("SELECT t FROM TipoProducto t where t.isDeleted != true")
    List<TipoProducto> listaTipoProductos();

    @Query("SELECT t FROM TipoProducto t where t.isDeleted != true and t.estado = true ")
    List<TipoProducto> listaTipoProductosActivos();
}
