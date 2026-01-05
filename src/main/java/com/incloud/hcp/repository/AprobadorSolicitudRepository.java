package com.incloud.hcp.repository;

import com.incloud.hcp.domain.AprobadorSolicitud;
import com.incloud.hcp.domain.SolicitudBlacklist;
import com.incloud.hcp.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 07/11/2017.
 */
public interface AprobadorSolicitudRepository extends JpaRepository<AprobadorSolicitud, Integer> {

    @Query("select t from AprobadorSolicitud t where t.solicitudBlacklist.idSolicitud = ?1")
    public List<AprobadorSolicitud> getListAprobadorByIdSolicitud(Integer idSolicitud);

    public List<AprobadorSolicitud> findByUsuario(Usuario user);

    public AprobadorSolicitud findBySolicitudBlacklistAndUsuario(SolicitudBlacklist solicitud, Usuario usuario);

    public AprobadorSolicitud findBySolicitudBlacklistAndOrdenAprobacion(SolicitudBlacklist solicitud, String orden);

    /*@Modifying
    @Query("UPDATE AprobadorSolicitud t SET t.indActivoAprobacion= '1' WHERE t.solicitudBlacklist=?1 and t.ordenAprobacion=?2")
    public void activarIndActivoAprobador(SolicitudBlacklist solicitud, Integer orden);

    @Modifying
    @Query("UPDATE AprobadorSolicitud t SET t.indActivoAprobacion= '0' WHERE t.solicitudBlacklist=?1 and t.ordenAprobacion=?2")
    public void desactivarIndActivoAprobador(SolicitudBlacklist solicitud, Integer orden);*/

}
