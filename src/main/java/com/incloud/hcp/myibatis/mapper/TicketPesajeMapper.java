package com.incloud.hcp.myibatis.mapper;
import com.incloud.hcp.domain.balanza.TicketPesaje;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface TicketPesajeMapper {
    List<TicketPesaje> getTicketPesajeMap(@Param("fechaInicio") Date fechaInicio,
                                               @Param("fechaFin") Date fechaFin,
                                               @Param("nroTicket") Integer nroTicket,
                                               @Param("transportistaRuc") String transportistaRuc,
                                               @Param("placaVehiculo") Integer placaVehiculo,
                                               @Param("chofer") Integer chofer,
                                               @Param("centro") String centro,
                                               @Param("nSubticket") String nSubticket,
                                               @Param("balanza") String balanza,
                                               @Param("carreta") Integer carreta);
}
