package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.GuiaRemision;
import com.incloud.hcp.domain.balanza.TicketPesaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface TicketPesajeRepository extends JpaRepository<TicketPesaje, Integer> {
    @Query("SELECT t FROM TicketPesaje t where t.fechaCreacion between ?1 and ?2 ")
    List<TicketPesaje> getTicketPesajeByFechaRegistroBetween(Date fechaInicio, Date fechaFin);


    @Query("SELECT t FROM TicketPesaje t where t.id = ?1")
    TicketPesaje getById(Integer id);

    @Query("SELECT t FROM TicketPesaje t where t.id = ?1")
    List<TicketPesaje> findId(Integer id);


    @Query("SELECT t FROM TicketPesaje t where t.id = ?1")
    TicketPesaje findByIdTicket(Integer id);

    @Query("SELECT t FROM TicketPesaje t WHERE t.id = ?1 AND t.fechaCreacion BETWEEN ?2 AND ?3")
    List<TicketPesaje> findByIdAndFechaRegistroBetween(Integer id, Date fechaInicio, Date fechaFin);
    @Query("SELECT t FROM TicketPesaje t WHERE t.id = ?1 AND t.rucTransportista = ?2 AND t.codigoCentro = ?3 AND t.fechaCreacion BETWEEN ?4 AND ?5")
    List<TicketPesaje> findByIdAndTransportistaRucAndCentroAndFechaRegistroBetween(Integer id, String transportistaRuc, String centro, Date fechaInicio, Date fechaFin);
    @Query("SELECT t FROM TicketPesaje t WHERE t.rucTransportista = ?1 AND t.fechaCreacion BETWEEN ?2 AND ?3")
    List<TicketPesaje> findByTransportistaRucAndFechaRegistroBetween(String transportistaRuc, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.codigoCentro = ?1 AND t.fechaCreacion BETWEEN ?2 AND ?3")
    List<TicketPesaje> findByCentroAndFechaRegistroBetween(String centro, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.codigoCentro = ?1 and t.transporte.placa  like '%'||?2||'%'  AND t.fechaCreacion BETWEEN ?3 AND ?4")
    List<TicketPesaje> findByCentroAndPlacaAndFechaRegistroBetween(String centro,String placa, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.id = ?1 AND t.rucTransportista = ?2 AND t.fechaCreacion BETWEEN ?3 AND ?4")
    List<TicketPesaje> findByIdAndTransportistaRucAndFechaRegistroBetween(Integer id, String transportistaRuc, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.id = ?1 AND t.codigoCentro = ?2 AND t.fechaCreacion BETWEEN ?3 AND ?4")
    List<TicketPesaje> findByIdAndCentroAndFechaRegistroBetween(Integer id, String centro, Date fechaInicio, Date fechaFin);
    @Query("SELECT t FROM TicketPesaje t WHERE t.id = ?1 AND t.codigoCentro = ?2 and t.transporte.placa like '%'||?3||'%' AND t.fechaCreacion BETWEEN ?4 AND ?5")
    List<TicketPesaje> findByIdAndCentroAndPlacaAndFechaRegistroBetween(Integer id, String centro,String placa, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.rucTransportista = ?1 AND t.codigoCentro = ?2 AND t.fechaCreacion BETWEEN ?3 AND ?4")
    List<TicketPesaje> findByTransportistaRucAndCentroAndFechaRegistroBetween(String transportistaRuc, String centro, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.transporte.placa = ?1 AND t.fechaCreacion BETWEEN ?2 AND ?3")
    List<TicketPesaje> findByPlacaAndFechaRegistroBetween(String placa, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.chofer.dni = ?1 AND t.fechaCreacion BETWEEN ?2 AND ?3")
    List<TicketPesaje> findByChoferAndFechaRegistroBetween(String dniChofer, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.id = ?1 AND t.transporte.placa = ?2 AND t.fechaCreacion BETWEEN ?3 AND ?4")
    List<TicketPesaje> findByTicketAndPlacaAndFechaRegistroBetween(Integer id, String placa, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.id = ?1 AND t.chofer.dni = ?2 AND t.fechaCreacion BETWEEN ?3 AND ?4")
    List<TicketPesaje> findByTicketAndChoferAndFechaRegistroBetween(Integer id, String dniChofer, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.rucTransportista = ?1 AND t.transporte.placa = ?2 AND t.fechaCreacion BETWEEN ?3 AND ?4")
    List<TicketPesaje> findByRucAndPlacaAndFechaRegistroBetween(String transportistaRuc, String placa, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.rucTransportista = ?1 AND t.chofer.dni = ?2 AND t.fechaCreacion BETWEEN ?3 AND ?4")
    List<TicketPesaje> findByRucAndChoferAndFechaRegistroBetween(String transportistaRuc, String dniChofer, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.transporte.placa = ?1 AND t.chofer.dni = ?2 AND t.fechaCreacion BETWEEN ?3 AND ?4")
    List<TicketPesaje> findByPlacaAndChoferAndFechaRegistroBetween(String placa, String dniChofer, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.id = ?1 AND t.rucTransportista = ?2 AND t.transporte.placa = ?3 AND t.fechaCreacion BETWEEN ?4 AND ?5")
    List<TicketPesaje> findByTicketAndRucAndPlacaAndFechaRegistroBetween(Integer id, String transportistaRuc, String placa, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.id = ?1 AND t.rucTransportista = ?2 AND t.chofer.dni = ?3 AND t.fechaCreacion BETWEEN ?4 AND ?5")
    List<TicketPesaje> findByTicketAndRucAndChoferAndFechaRegistroBetween(Integer id, String transportistaRuc, String dniChofer, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.id = ?1 AND t.transporte.placa = ?2 AND t.chofer.dni = ?3 AND t.fechaCreacion BETWEEN ?4 AND ?5")
    List<TicketPesaje> findByTicketAndPlacaAndChoferAndFechaRegistroBetween(Integer id, String placa, String dniChofer, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.rucTransportista = ?1 AND t.transporte.placa = ?2 AND t.chofer.dni = ?3 AND t.fechaCreacion BETWEEN ?4 AND ?5")
    List<TicketPesaje> findByRucAndPlacaAndChoferAndFechaRegistroBetween(String transportistaRuc, String placa, String dniChofer, Date fechaInicio, Date fechaFin);

    @Query("SELECT t FROM TicketPesaje t WHERE t.id = ?1 AND t.rucTransportista = ?2 AND t.transporte.placa = ?3 AND t.chofer.dni = ?4 AND t.fechaCreacion BETWEEN ?5 AND ?6")
    List<TicketPesaje> findByTicketAndRucAndPlacaAndChoferAndFechaRegistroBetween(Integer id, String transportistaRuc, String placa, String dniChofer, Date fechaInicio, Date fechaFin);

/*
    @Modifying
    @Query("UPDATE TicketPesaje t SET t.id_estado= ?2 WHERE t.id=?1")
    void updateEstado(Integer id,Integer estado);*/

    //select modelo,placa,unidad_medida,peso_inicial from
    //blz_ticket_pesaje t inner join blz_carreta c on t.id_carreta= c.id_carreta;

    /*@Query("SELECT t.carreta FROM TicketPesaje t " +
            "INNER JOIN Carreta c on c.id = t.carreta   WHERE(t.id = ?1)" )
    List<TicketPesaje> getByIdPDF(Integer idTicketPesaje);
   **/

    @Modifying
    @Query("UPDATE TicketPesaje t SET t.estado.id= ?1 WHERE t.id=?2")
    void updateEstadoTicketPesaje(Integer estadoId, Integer id);


}
