package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.GuiaRemisionDetalle;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GuiaRemisionDetalleRepository extends JpaRepository<GuiaRemisionDetalle, Integer> {

    @Query("SELECT t FROM GuiaRemisionDetalle t where t.idGuia = ?1")
    List<GuiaRemisionDetalle> findByIdGuiaRemision(Integer id);

    @Query("SELECT t FROM GuiaRemisionDetalle t where t.subticket like %:ticketPesajeId%")
    List<GuiaRemisionDetalle> getGuiaRemisionDetalleByTicket(@Param("ticketPesajeId") String ticketPesajeId);

    @Query("SELECT t FROM GuiaRemisionDetalle t where t.subticket = ?1")
    List<GuiaRemisionDetalle> findBySubTicket(String subticket);

    boolean existsBySubticket(String subticket);

    @Modifying
    @Query("DELETE FROM GuiaRemisionDetalle p WHERE p.subticket= ?1")
    void deleteGuiaRemisionDetalleSubticket(String subticket);
}
