package com.incloud.hcp.repository;

import com.incloud.hcp.domain.almacen.OrdenDespachoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenDespachoDetalleRepository extends JpaRepository<OrdenDespachoDetalle, Integer> {

    @Query("SELECT ocd FROM OrdenDespachoDetalle ocd where ocd.idOrdenDespacho = ?1 and ocd.ordenDespacho.proveedorRuc=?2 and ocd.ordenDespacho.estadoSap = 'L' order by ocd.posicion asc")
    List<OrdenDespachoDetalle> getAllByIdOrdenDespachoLiberada(Integer idOrdenDespacho,String ruc);
    @Query("SELECT ocd FROM OrdenDespachoDetalle ocd where ocd.idOrdenDespacho = ?1 and ocd.ordenDespacho.estadoSap = 'L' order by ocd.posicion asc")
    List<OrdenDespachoDetalle> getByIdOrdenDespachoLiberada(Integer idOrdenDespacho);
    //h
    @Query("SELECT ocd FROM OrdenDespachoDetalle ocd where ocd.idOrdenDespacho = ?1")
    List<OrdenDespachoDetalle> getAllByIdOrdenDespacho(Integer idOrdenDespacho);

    @Query("SELECT ocd FROM OrdenDespachoDetalle ocd where ocd.numeroOrdenCompra = ?1 and ocd.posicion = ?2 and ocd.ordenDespacho.isActive = '1'")
    Optional<OrdenDespachoDetalle> getOrdenDespachoDetalleById(String numeroOrdenCompra, String posicion);

    @Query("SELECT ocd FROM OrdenDespachoDetalle ocd where ocd.numeroOrdenCompra = ?1 and ocd.posicion = ?2")
    OrdenDespachoDetalle getByODPos(String numeroOD, String pos);
}