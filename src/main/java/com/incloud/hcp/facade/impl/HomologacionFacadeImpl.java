package com.incloud.hcp.facade.impl;

import com.incloud.hcp.domain.Homologacion;
import com.incloud.hcp.domain.HomologacionRespuesta;
import com.incloud.hcp.dto.HomologacionDto;
import com.incloud.hcp.dto.HomologacionRespuestaDto;
import com.incloud.hcp.facade.HomologacionFacade;
import com.incloud.hcp.populate.Populater;
import com.incloud.hcp.service.HomologacionRespuestaService;
import com.incloud.hcp.service.HomologacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class HomologacionFacadeImpl implements HomologacionFacade {

    private HomologacionService homologacionService;
    private HomologacionRespuestaService homologacionRespuestaService;
    private Populater<Homologacion, HomologacionDto> homologacionPopulate;
    private Populater<HomologacionRespuesta, HomologacionRespuestaDto> respuestaPopulate;

    @Autowired
    @Qualifier(value = "homologacionPopulate")
    public void setHomologacionPopulate(Populater<Homologacion, HomologacionDto> homologacionPopulate) {
        this.homologacionPopulate = homologacionPopulate;
    }

    @Autowired
    @Qualifier(value = "homologacionRespuestaPopulate")
    public void setRespuestaPopulate(Populater<HomologacionRespuesta, HomologacionRespuestaDto> respuestaPopulate) {
        this.respuestaPopulate = respuestaPopulate;
    }

    @Autowired
    public void setHomologacionService(HomologacionService homologacionService) {
        this.homologacionService = homologacionService;
    }

    @Autowired
    public void setHomologacionRespuestaService(HomologacionRespuestaService homologacionRespuestaService) {
        this.homologacionRespuestaService = homologacionRespuestaService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public HomologacionDto guardar(HomologacionDto dto) {

        Optional<Homologacion> oHomologacion = Optional.ofNullable(dto.getIdHomologacion())
                .filter(id -> id > 0)
                .map(this.homologacionService::getHomologacionById);
        Homologacion homologacion;
        if (oHomologacion.isPresent()) {
            homologacion = oHomologacion.get();
            homologacion.setPregunta(dto.getPregunta());
            homologacion.setEstado("1");
            homologacion.setPeso(dto.getPeso());
            homologacion.setFechaModificacion(new Date());
            homologacion.setUsuarioModificacion(1);

            homologacion = this.homologacionService.guardar(homologacion);
            dto = this.homologacionPopulate.toDto(homologacion);
            List<HomologacionRespuestaDto> listRespuestaDto = new ArrayList<>();
            Optional.ofNullable(this.homologacionRespuestaService.getHomologacionRespuestaByHomologacion(homologacion.getIdHomologacion()))
                    .ifPresent(list -> list.stream().map(this.respuestaPopulate::toDto).forEach(listRespuestaDto::add));
            dto.setRespuestas(listRespuestaDto);
            return dto;
        } else {
            homologacion = homologacionPopulate.toEntity(dto);
            homologacion.setEstado("1");
            homologacion.setFechaCreacion(new Date());
            homologacion.setUsuarioCreacion(1);
            homologacion = this.homologacionService.guardar(homologacion);
            List<HomologacionRespuestaDto> listDto = new ArrayList<>();
            if (dto.getRespuestas() != null && !dto.getRespuestas().isEmpty()) {
                List<HomologacionRespuesta> list = dto.getRespuestas().stream()
                        .map(this.respuestaPopulate::toEntity)
                        .collect(Collectors.toList());
                for (int i = 0; list.size() > i; i++) {
                    final HomologacionRespuesta respuesta = list.get(i);
                    respuesta.setFechaCreacion(new Date());
                    respuesta.setUsuarioCreacion(1);
                    respuesta.setNroOrden((i + 1) + "");
                    respuesta.setHomologacion(homologacion);
                    listDto.add(this.respuestaPopulate.toDto(this.homologacionRespuestaService.guardar(respuesta)));
                }
            }
            dto = this.homologacionPopulate.toDto(homologacion);
            dto.setRespuestas(listDto);
            return dto;
        }
    }

    @Override
    public List<HomologacionDto> getListAll() {
        List<HomologacionDto> listDto = new ArrayList<>();
        List<Homologacion> listaAux = homologacionService.getAll();
        Optional.ofNullable(listaAux)
                .ifPresent(list -> list.stream()
                        .map(homologacionPopulate::toDto)
                        .forEach(homologaciondto -> {

                            List<HomologacionRespuestaDto> listRptaDto = new ArrayList<>();
                            Optional.ofNullable(homologacionRespuestaService.getHomologacionRespuestaByHomologacion(homologaciondto.getIdHomologacion()))
                                    .ifPresent(listRpta -> listRpta.stream()
                                            .map(respuestaPopulate::toDto)
                                            .forEach(listRptaDto::add));

                            homologaciondto.setRespuestas(listRptaDto);
                            listDto.add(homologaciondto);
                        }));

        return listDto;
    }

    @Override
    public HomologacionDto getHomologacionDto(Integer idHomologacion) {
        return Optional.ofNullable(this.homologacionService.getHomologacionById(idHomologacion))
                .map(this.homologacionPopulate::toDto)
                .map(dto -> {

                    List<HomologacionRespuestaDto> listRptaDto = new ArrayList<>();
                    Optional.ofNullable(this.homologacionRespuestaService.getHomologacionRespuestaByHomologacion(dto.getIdHomologacion()))
                            .ifPresent(listRpta -> listRpta.stream()
                                    .map(this.respuestaPopulate::toDto)
                                    .forEach(listRptaDto::add));

                    dto.setRespuestas(listRptaDto);
                    return dto;
                }).orElse(this.homologacionPopulate.toDto(new Homologacion()));
    }
}
