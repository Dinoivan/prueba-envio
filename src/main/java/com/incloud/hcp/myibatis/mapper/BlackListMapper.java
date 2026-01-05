package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.domain.SolicitudAdjuntoBlacklist;
import com.incloud.hcp.domain.SolicitudBlacklist;
import com.incloud.hcp.dto.SolicitudBlackListAdjuntoDto;
import com.incloud.hcp.dto.SolicitudBlackListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrador on 09/11/2017.
 */
@Mapper
@Repository
public interface BlackListMapper {
    void registrarSolicitud(@Param("blacklist")SolicitudBlackListDto solicitud);

    void registrarSolicitudAdjunto(@Param("adjunto") SolicitudBlackListAdjuntoDto adjunto, @Param("idSolicitud") Integer idSolicitud);

    List<SolicitudBlacklist> getListSolicitudPendienteAprobacionByUser(@Param("email") String email,
                                                                       @Param("estadoPendiente") String estadoPendiente);

    List<SolicitudAdjuntoBlacklist> getListAdjuntosSolicitudBlackListByIdSolicitud(@Param("idSolicitud") Integer idSolicitud);

    Integer countByBlackListRegistrados(@Param("idProveedor") Integer idProveedor,
                                        @Param("codigoTipoSolicitud") String codigoTipoSolicitud);

}
