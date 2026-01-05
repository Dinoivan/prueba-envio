package com.incloud.hcp.repository;

import com.incloud.hcp.domain.LogTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LogTransaccionRepository extends JpaRepository<LogTransaccion, Integer> {

    @Query(nativeQuery = true,
            value="SELECT top 1 lt.LOG_USUARIO FROM LOG_TRANSACCION lt where lt.TIPO_TRANSACCION =?1  and lt.TIPO_REGISTRO=?2  and lt.ID_REGISTRO=?3 ORDER BY LOG_FECHA desc ")
//    @Query("SELECT lt.logUsuario FROM LogTransaccion lt where lt.tipoTransaccion =?1 and lt.tipoRegistro=?2 and lt.idRegistro=?3 ")
    String getUsuarioTransaccionBD(String transaccion, String tipoRegistro, Integer idRegistro);

}