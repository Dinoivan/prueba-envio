package com.incloud.hcp.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.incloud.hcp.domain.balanza.DetalleTicket;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DetalleTicketRepository extends JpaRepository<DetalleTicket, Integer> {
    public DetalleTicket findByFechaCreacion(LocalDateTime licencia);

    @Query("SELECT d FROM DetalleTicket d WHERE d.id=?1")
    public DetalleTicket findByIdDetalleTicket(Integer id);
/*    @Query(nativeQuery = true,value ="SELECT * FROM BLZ_DETALLE_TICKET WHERE ID_DETALLE_TICKET=?1")
    public DetalleTicket findByIdDetalleTicket(Integer id);*/

    @Query("SELECT d FROM DetalleTicket d WHERE d.docMaterial=?1")
    public DetalleTicket findByDocMaterial(String docMaterial);
    @Query("SELECT d FROM DetalleTicket d WHERE d.ticketPesaje.id=?1")
    List<DetalleTicket> findByTicketPesajeId(Integer id);

    @Query("SELECT d FROM DetalleTicket d WHERE d.ticketPesaje.id=?1")
    DetalleTicket traerIdTicketPesaje(Integer id);

    @Query("SELECT d FROM DetalleTicket d where d.id = ?1")
    DetalleTicket findDetalleTicketBy(Integer id);

    @Query("SELECT d.id FROM DetalleTicket d WHERE d.subticket like %:nroSubticket%")
    List<Integer> findByNroSubticket(@Param("nroSubticket") String nroSubticket);

    @Query("SELECT d FROM DetalleTicket d where d.subticket = ?1")
    DetalleTicket findxSubticket(String subticket);

    @Query("SELECT d FROM DetalleTicket d where d.subticket = ?1")
    List<DetalleTicket> findxSubticketList(String subticket);

    @Modifying
    @Query("UPDATE DetalleTicket t SET t.fechaEmisionPdf= ?1 WHERE t.id=?2")
    void updateFechaEmisionPdf(Timestamp fechaEmisionPdf, Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE DetalleTicket t SET t.posicion= ?2, t.docMaterial= ?3, t.ejercicio= ?4, t.pesoSap=?5, t.lote=?6, t.cantidad=?7, t.codigoAlmacen=?8,t.documentoTraslado=?9, t.numPedido=?10, t.posicionDocumento=?11 WHERE t.subticket=?1")
    void updateLoToPosicionDocumentoMaterialEjercicio(String id,String posicion, String documentoMaterial,String ejercicio, Double pesoSap, String lote,Double cantidad,String codigoAlmacen, String documentoTraslado, String numPedido,Integer posicionDocumento);

    @Transactional
    @Modifying
    @Query("UPDATE DetalleTicket t SET t.posicion= ?2, t.docMaterial= ?3, t.ejercicio= ?4, t.pesoSap=?5, t.lote=?6, t.cantidad=?7, t.codigoAlmacen=?8 WHERE t.id=?1")
    void updateDetalle(Integer id,String posicion, String documentoMaterial,String ejercicio, Double pesoSap, String lote,Double cantidad,String codigoAlmacen);

    @Modifying
    @Query("UPDATE DetalleTicket t SET t.tieneGuia= ?2 WHERE t.subticket=?1")
    void updateTieneGuia(String subticket,String tieneGuia);
}