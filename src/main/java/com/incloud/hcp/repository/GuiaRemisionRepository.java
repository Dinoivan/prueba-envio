package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.GuiaRemision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GuiaRemisionRepository extends JpaRepository<GuiaRemision, Integer> {


    @Query("SELECT g FROM GuiaRemision g where g.id = ?1")
    GuiaRemision downloadConstanciaExcel(Integer id);

    @Query("SELECT t FROM GuiaRemision t where t.id = ?1")
    GuiaRemision findByIdGuiaRemision(Integer id);

    @Query("SELECT g FROM GuiaRemision g where g.idTicketPesaje = ?1")
    List<GuiaRemision> getAllGuiaRemisionByTicketPesajeId(String ticketPesajeId);

    @Modifying
    @Query("UPDATE GuiaRemision p SET p.mensaje= ?2, p.estado = ?3 WHERE p.id=?1")
    public void actualizarGuiaRemisionMensajeAndEstado(Integer id, String mensaje, String estado);

    @Modifying
    @Query("UPDATE GuiaRemision p SET p.zanula = ?2 WHERE p.id=?1")
    public void actualizarGuiaRemisionAnulado(Integer id, String zanula);

    @Modifying
    @Query("DELETE FROM GuiaRemision p WHERE p.serieGuia= ?1 and p.nroGuia= ?2")
    void deleteGuiaRemisionid(String serieGuia, Integer nroGuia);
}
