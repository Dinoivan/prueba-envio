package com.incloud.hcp.dto.blacklist;

import com.incloud.hcp.domain.SolicitudAdjuntoBlacklist;
import com.incloud.hcp.domain.SolicitudBlacklist;
import com.incloud.hcp.dto.SolicitudBlackListAdjuntoDto;
import com.incloud.hcp.dto.SolicitudBlackListDto;
import com.incloud.hcp.dto.mapper.EntityDTOMapper;
import com.incloud.hcp.repository.CriterioBlackListRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Administrador on 12/09/2017.
 */
@Component("solicitudBlackListDTOMapper")
public class SolicitudBlackListDTOMapper implements EntityDTOMapper<SolicitudBlacklist, SolicitudBlackListDto> {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private CriterioBlackListRepository criterioBlackListRepository;

    @Override
    public SolicitudBlacklist toEntity(SolicitudBlackListDto dto) {
        SolicitudBlacklist solicitud = new SolicitudBlacklist();
        solicitud.setMotivo(dto.getMotivo());
        solicitud.setEstadoSolicitud(dto.getEstado());
        solicitud.setSede(dto.getSede());
        solicitud.setUsuarioCreacion(dto.getId());
        solicitud.setUsuarioCreacionEmail(dto.getCorreo());
        solicitud.setUsuarioCreacionName(dto.getUsuario());

        Optional.ofNullable(criterioBlackListRepository)
                .map(r -> r.getOne(dto.getIdCriterio()))
                .ifPresent(solicitud::setCriteriosBlacklist);
        Optional.ofNullable(proveedorRepository)
                .map(r -> r.getProveedorByRuc(dto.getRuc()))
                .ifPresent(solicitud::setProveedor);

        Optional.ofNullable(dto.getAdjuntos())
                .ifPresent(list -> {
                    List<SolicitudAdjuntoBlacklist> listAdjunto = new ArrayList<>();
                    list.stream().map(temp -> {
                        SolicitudAdjuntoBlacklist  adjunto = new SolicitudAdjuntoBlacklist();
                        adjunto.setAdjuntoId(temp.getId());
                        adjunto.setAdjuntoNombre(temp.getName());
                        adjunto.setAdjuntoTipo(temp.getType());
                        adjunto.setAdjuntoUrl(temp.getUrl());
                        adjunto.setSolicitudBlacklist(solicitud);
                        return adjunto;
                    }).forEach(listAdjunto::add);
                    solicitud.setListAdjunto(listAdjunto);
                });

        return solicitud;
    }

    @Override
    public SolicitudBlackListDto toDto(SolicitudBlacklist solicitud) {
        SolicitudBlackListDto dto = new SolicitudBlackListDto();
        dto.setIdSolicitud(solicitud.getIdSolicitud());
        dto.setSede(solicitud.getSede());
        dto.setMotivo(solicitud.getMotivo());
        dto.setEstado(solicitud.getEstadoSolicitud());
        dto.setId(solicitud.getUsuarioCreacion());
        dto.setUsuario(solicitud.getUsuarioCreacionName());
        dto.setCorreo(solicitud.getUsuarioCreacionEmail());

        dto.setFechaCreacion(DateFormatUtils.format(solicitud.getFechaCreacion(), "yyyy-MM-dd"));
        Optional.ofNullable(solicitud.getCriteriosBlacklist())
                .ifPresent(c -> {
                    dto.setIdCriterio(c.getIdCriterio());
                    dto.setCriterio(c.getDescripcion());
                });
        Optional.ofNullable(solicitud.getProveedor()).ifPresent(p -> {
            dto.setRuc(p.getRuc());
            dto.setRazonSocial(p.getRazonSocial());
        });

        Optional.ofNullable(solicitud.getListAdjunto())
                .ifPresent(list -> {
                    List<SolicitudBlackListAdjuntoDto> listAdjuntoDtos = new ArrayList<>();
                    list.stream().map(temp -> {
                        SolicitudBlackListAdjuntoDto adjuntoDto = new SolicitudBlackListAdjuntoDto();
                        adjuntoDto.setId(temp.getAdjuntoId());
                        adjuntoDto.setName(temp.getAdjuntoNombre());
                        adjuntoDto.setType(temp.getAdjuntoTipo());
                        adjuntoDto.setUrl(temp.getAdjuntoUrl());
                        return adjuntoDto;
                    }).forEach(listAdjuntoDtos::add);
                    dto.setAdjuntos(listAdjuntoDtos);
                });

        return dto;
    }
}
