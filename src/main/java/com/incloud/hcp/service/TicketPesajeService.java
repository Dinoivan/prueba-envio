package com.incloud.hcp.service;


import com.incloud.hcp.domain.balanza.TicketPesaje;
import com.incloud.hcp.dto.FiltroTicketPesajeDTO;
import com.incloud.hcp.dto.TicketPesajeReporte;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface TicketPesajeService {

    List<TicketPesaje> getAllTicketPesaje();

    TicketPesaje getTicketPesajeById(Integer idTicketPesaje);


    TicketPesaje save(TicketPesaje ticketPesaje);
    TicketPesaje savev1(TicketPesaje ticketPesaje);

    Timestamp guardarFechaHoraEntradaTicket(String nSubTicket, Double pesoInicial) throws Exception;
    Timestamp guardarFechaHoraSalidaTicket(String nSubTicket, Double pesoFinal) throws Exception;
    String guardarFechaHoraTicket(String nSubTicket, Double pesoInicial, Double pesoFinal) throws Exception;

    /*ResponseEntity <Map> update (TicketPesaje ticketPesaje);*/
    List<TicketPesaje> getTicketPesajeListPorFechas(Date fechaInicio, Date fechaFin);

    List<TicketPesaje> findByIdAndFechaRegistroBetween(Integer id, Date fechaInicio, Date fechaFin);

    List<TicketPesaje> findByFiltro(Date fechaInicio, Date fechaFin, FiltroTicketPesajeDTO filtro);

    List<TicketPesaje> findByTransportistaRucAndFechaRegistroBetween(String transportistaRuc, Date fechaInicio, Date fechaFin);

    List<TicketPesaje> findByCentroAndFechaRegistroBetween(String centro, Date fechaInicio, Date fechaFin);
    List<TicketPesaje> findByCentroAndPlacaAndFechaRegistroBetween(String centro,String placa, Date fechaInicio, Date fechaFin);

    List<TicketPesaje> findByIdAndTransportistaRucAndFechaRegistroBetween(Integer id, String transportistaRuc, Date fechaInicio, Date fechaFin);

    List<TicketPesaje> findByIdAndCentroAndFechaRegistroBetween(Integer id, String centro, Date fechaInicio, Date fechaFin);
    List<TicketPesaje> findByIdAndCentroAndPlacaAndFechaRegistroBetween(Integer id, String centro,String placa, Date fechaInicio, Date fechaFin);

    List<TicketPesaje> findByTransportistaRucAndCentroAndFechaRegistroBetween(String transportistaRuc, String centro, Date fechaInicio, Date fechaFin);

    TicketPesaje actualizarEstado(Integer ticketId, Integer estadoId);
    List<TicketPesajeReporte> listarReporteByFiltro(Date fechaInicio, Date fechaFin, FiltroTicketPesajeDTO dto);
    List<TicketPesaje> listarByFiltro(Date fechaInicio, Date fechaFin, FiltroTicketPesajeDTO dto);

    byte[] getAllTicketPDF(Integer idTicketPesaje) throws FileNotFoundException, JRException;

    List<TicketPesaje> findByPlacaAndFechaRegistroBetween(String placa, Date fechaInicio, Date nuevaFechaFin);

    List<TicketPesaje> findByChoferAndFechaRegistroBetween(String dniChofer, Date fechaInicio, Date nuevaFechaFin);

    //List<TicketPesaje> findByTicketAndPlacaAndFechaRegistroBetween(Integer ticket, String placa, Date fechaInicio, Date nuevaFechaFin);
    //List<TicketPesaje> getByIdPDF(Integer ticketPesaje);
}
