package com.incloud.hcp.repository;


import com.incloud.hcp.domain.EstadoOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EstadoOrdenCompraRepository extends JpaRepository<EstadoOrdenCompra, Integer> {
    @Query("SELECT p FROM EstadoOrdenCompra p  WHERE p.descripcion=:descripcion")
    EstadoOrdenCompra getEstadoOrdenCompraByDescripcion(@Param("descripcion") String descripcion);
}