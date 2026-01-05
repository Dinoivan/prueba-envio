package com.incloud.hcp.repository;

import com.incloud.hcp.domain.OrdenCompraDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenCompraDetalleRepository extends JpaRepository<OrdenCompraDetalle, Integer> {

    /*@Query("SELECT ocd FROM OrdenCompraDetalle ocd where ocd.idOrdenCompra = ?1 and ocd.ordenCompra.estadoSap = 'L'")
    List<OrdenCompraDetalle> getAllByIdOrdenCompraLiberada(Integer idOrdenCompra);*/
    @Query("SELECT ocd FROM OrdenCompraDetalle ocd where ocd.idOrdenCompra = ?1 and ocd.ordenCompra.proveedorRuc=?2 and ocd.ordenCompra.estadoSap = 'L' order by ocd.posicion asc")
    List<OrdenCompraDetalle> getAllByIdOrdenCompraLiberada(Integer idOrdenCompra,String ruc);
    @Query("SELECT ocd FROM OrdenCompraDetalle ocd where ocd.idOrdenCompra = ?1 and ocd.ordenCompra.estadoSap = 'L' order by ocd.posicion asc")
    List<OrdenCompraDetalle> getByIdOrdenCompraLiberada(Integer idOrdenCompra);
    //h
    @Query("SELECT ocd FROM OrdenCompraDetalle ocd where ocd.idOrdenCompra = ?1")
    List<OrdenCompraDetalle> getAllByIdOrdenCompra(Integer idOrdenCompra);

    Optional<OrdenCompraDetalle> findByNumeroOrdenCompraAndPosicionAndIdOrdenCompra(String numeroOrdenCompra, String posicion, Integer idOrdenCompra);
}