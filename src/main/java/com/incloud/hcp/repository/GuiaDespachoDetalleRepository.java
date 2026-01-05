package com.incloud.hcp.repository;

import com.incloud.hcp.domain.almacen.GuiaDespachoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GuiaDespachoDetalleRepository extends JpaRepository<GuiaDespachoDetalle, Integer> {
    @Query("SELECT p FROM GuiaDespachoDetalle p WHERE p.id = ?1")
    GuiaDespachoDetalle findGuiaDetalleById(Integer id);

    @Query("SELECT p FROM GuiaDespachoDetalle p WHERE p.despacho.id = ?1")
    GuiaDespachoDetalle findGuiaDetalleByIdGuia(Integer id);

    @Query("SELECT p FROM GuiaDespachoDetalle p WHERE p.despacho.id = ?1")
    List<GuiaDespachoDetalle> listFindGuiaDetalleByIdGuia(Integer id);
}
