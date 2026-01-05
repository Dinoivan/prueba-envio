package com.incloud.hcp.facade.impl;

import com.incloud.hcp.domain.*;
import com.incloud.hcp.dto.LineaComercialDto;
import com.incloud.hcp.dto.PreRegistroProveedorDto;
import com.incloud.hcp.enums.EstadoPreRegistroEnum;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.facade.PreRegistroFacade;
import com.incloud.hcp.populate.Populater;
import com.incloud.hcp.repository.LineaComercialRepository;
import com.incloud.hcp.repository.PreRegistroLineaComercialRepository;
import com.incloud.hcp.repository.PreRegistroProveedorRepository;
import com.incloud.hcp.repository.PreguntaInformacionRepository;
import com.incloud.hcp.service.PreRegistroProveedorService;
import com.incloud.hcp.service.delta.UsuarioLineaComercialDeltaService;
import com.incloud.hcp.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PreRegistroFacadeImpl implements PreRegistroFacade {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private PreRegistroProveedorService preRegistroProveedorService;
    private PreRegistroProveedorRepository preRegistroProveedorRepository;
    private PreRegistroLineaComercialRepository preRegistroLineaComercialRepository;
    private LineaComercialRepository lineaComercialRepository;
    private UsuarioLineaComercialDeltaService usuarioLineaComercialDeltaService;
    private PreguntaInformacionRepository preguntaInformacionRepository;

    private Populater<PreRegistroProveedor, PreRegistroProveedorDto> preRegistroPopulate;

    @Autowired
    public void setPreRegistroProveedorService(
            PreRegistroProveedorService preRegistroProveedorService,
            PreRegistroProveedorRepository preRegistroProveedorRepository,
            PreRegistroLineaComercialRepository preRegistroLineaComercialRepository,
            LineaComercialRepository lineaComercialRepository,
            UsuarioLineaComercialDeltaService usuarioLineaComercialDeltaService,
            PreguntaInformacionRepository preguntaInformacionRepository) {
        this.preRegistroProveedorService = preRegistroProveedorService;
        this.preRegistroProveedorRepository = preRegistroProveedorRepository;
        this.preRegistroLineaComercialRepository = preRegistroLineaComercialRepository;
        this.lineaComercialRepository = lineaComercialRepository;
        this.usuarioLineaComercialDeltaService = usuarioLineaComercialDeltaService;
        this.preguntaInformacionRepository = preguntaInformacionRepository;
    }

    @Autowired
    @Qualifier(value = "preRegistroPopulate")
    public void setPreRegistroPopulate(Populater preRegistroPopulate) {
        this.preRegistroPopulate = preRegistroPopulate;
    }

    @Override
    public PreRegistroProveedorDto save(PreRegistroProveedorDto preRegistroProveedorDto) throws Exception {
        logger.error("preRegistroProveedorDto del proveedor potencial : " + preRegistroProveedorDto.toString());
        Optional<PreRegistroProveedor> opt = Optional.ofNullable(preRegistroProveedorDto).map(preRegistroPopulate::toEntity);
        if (opt.isPresent()) {
            PreRegistroProveedor preRegistro = opt.get();
            preRegistro.setEstado(EstadoPreRegistroEnum.PENDIENTE.getCodigo());
            preRegistro = this.preRegistroProveedorService.guardar(preRegistro);

            /* Agregando detalle de Lineas Comerciales */
            List<LineaComercial> lineaComercialList = Optional.ofNullable(preRegistroProveedorDto.getLineaComercialList()).orElse(new ArrayList<>());
            logger.error("lineaComercialList del preregistro del proveedor potencial : " + lineaComercialList.toString());

            this.preRegistroLineaComercialRepository.deleteByIdRegistro(preRegistro);
            for (LineaComercial beanLinea : lineaComercialList) {
                PreRegistroLineaComercial beanDetalle = new PreRegistroLineaComercial();
                /* **************************************************************************** */
                beanDetalle.setIdNumberRegistro(preRegistro.getIdRegistro());
                beanDetalle.setIdNumberLineaComercial(beanLinea.getIdLinea());
                beanDetalle.setIdNumberFamilia(beanLinea.getIdFamilia());
                /* **************************************************************************** */
//                beanDetalle.setIdRegistro(preRegistro);
//                Optional.ofNullable(this.lineaComercialRepository.getLineaComercialById(beanLinea.getIdLinea())).ifPresent(beanDetalle::setIdLineaComercial);
//                Optional.ofNullable(this.lineaComercialRepository.getLineaComercialById(beanLinea.getIdFamilia())).ifPresent(beanDetalle::setIdFamilia);
                logger.error("preRegistroLineaComercial a guardar: " + beanDetalle.toString());
                beanDetalle = this.preRegistroLineaComercialRepository.save(beanDetalle);
                logger.error("preRegistroLineaComercial guardado: " + beanDetalle.toString());
            }
            return preRegistroPopulate.toDto(preRegistro);
        }
        throw new PortalException("Error al guardar el pre-registro ");
    }

    @Override
    public PreRegistroProveedorDto updateSearchSunat(PreRegistroProveedorDto dto) {
        logger.error("updateSearchSunat a01 dto " + dto.toString());
        PreRegistroProveedor preRegistroProveedor = this.preRegistroPopulate.toEntity(dto);
        logger.error("updateSearchSunat a02 preRegistroProveedor " + preRegistroProveedor.toString());
        preRegistroProveedor.setFechaInicioActiSunat(dto.getFechaInicioActiSunat());
        preRegistroProveedor.setCodigoSistemaEmisionElect(dto.getCodigoSistemaEmisionElect());
        preRegistroProveedor.setCodigoComprobantePago(dto.getCodigoComprobantePago());
        preRegistroProveedor.setCodigoPadron(dto.getCodigoPadron());
        logger.error("updateSearchSunat a03 preRegistroProveedor " + preRegistroProveedor.toString());
        return this.preRegistroPopulate.toDto(
                this.preRegistroProveedorService.updateSearchSunat(preRegistroProveedor));
    }

    @Override
    public PreRegistroProveedorDto getPreRegistroByIdHcp(String idHcp) {
        Optional<PreRegistroProveedor> oPreRegistro = Optional.ofNullable(this.preRegistroProveedorService)
                .map(service -> service.getPreRegistroProveedorByIdHcp(idHcp));

        if (!oPreRegistro.isPresent()) {
            return null;
            //throw new PortalException("No existe un Pre Registro con ID Hcp " + idHcp);
        }
        PreRegistroProveedor preRegistroProveedor = oPreRegistro.get();
        PreRegistroProveedorDto beanRetornoDTO = oPreRegistro.map(this.preRegistroPopulate::toDto).get();
        beanRetornoDTO.setCodigoSistemaEmisionElect(preRegistroProveedor.getCodigoSistemaEmisionElect());
        beanRetornoDTO.setCodigoComprobantePago(preRegistroProveedor.getCodigoComprobantePago());
        beanRetornoDTO.setCodigoPadron(preRegistroProveedor.getCodigoPadron());
        beanRetornoDTO.setFechaInicioActiSunat(preRegistroProveedor.getFechaInicioActiSunat());

        /* Obteniendo detalle de Lineas Comerciales */
        List<PreRegistroLineaComercial> preRegistroLineaComercialList = this.preRegistroLineaComercialRepository.
                findByIdRegistro(oPreRegistro.get());
        List<LineaComercial> lineaComercialList = new ArrayList<>();
        List<LineaComercialDto> lineaComercialListDTO = new ArrayList<>();
        for (PreRegistroLineaComercial bean: preRegistroLineaComercialList) {
            LineaComercial beanLinea = new LineaComercial();
            LineaComercialDto beanLineaDTO = new LineaComercialDto();

            beanLinea.setIdPadre(bean.getIdLineaComercial().getIdLineaComercial());
            beanLinea.setIdLineaComercial(bean.getIdFamilia().getIdLineaComercial());
            beanLinea.setIdLinea(bean.getIdLineaComercial().getIdLineaComercial());
            beanLinea.setIdFamilia(bean.getIdFamilia().getIdLineaComercial());
            beanLinea.setDescripcionPadre(bean.getIdLineaComercial().getDescripcion());
            beanLinea.setDescripcion(bean.getIdFamilia().getDescripcion());
            lineaComercialList.add(beanLinea);


            beanLineaDTO.setIdLinea(bean.getIdLineaComercial().getIdLineaComercial());
            beanLineaDTO.setLinea(bean.getIdLineaComercial().getDescripcion());
            beanLineaDTO.setIdFamilia(bean.getIdFamilia().getIdLineaComercial());
            beanLineaDTO.setFamilia(bean.getIdFamilia().getDescripcion());
            lineaComercialListDTO.add(beanLineaDTO);
        }
        beanRetornoDTO.setLineaComercialList(lineaComercialList);
        beanRetornoDTO.setLineasComercial(lineaComercialListDTO);


        /**
         * Pregunta Informacion
         */
        List<PreguntaInformacion> preguntaInformacionList =
                this.preguntaInformacionRepository.findAll(Sort.by("orden"));
        List<ProveedorPreguntaInformacion> proveedorPreguntaInformacionList = new ArrayList<ProveedorPreguntaInformacion>();
        for (PreguntaInformacion bean : preguntaInformacionList) {


            ProveedorPreguntaInformacion proveedorPreguntaInformacion = new ProveedorPreguntaInformacion();
            proveedorPreguntaInformacion.setRespuestaSiNo(false);

            if (Optional.ofNullable(proveedorPreguntaInformacion.getRespuesta()).isPresent()) {
                if (proveedorPreguntaInformacion.getRespuesta().equals(Constant.S)) {
                    proveedorPreguntaInformacion.setRespuestaSiNo(true);
                }
            }

            proveedorPreguntaInformacion.setIdPreguntaInformacion(bean);
            proveedorPreguntaInformacionList.add(proveedorPreguntaInformacion);
        }
        beanRetornoDTO.setPreguntaInformacion(proveedorPreguntaInformacionList);
        logger.error("toDto 06 Pregunta " + proveedorPreguntaInformacionList.toString());
        return beanRetornoDTO;
    }

    @Override
    public PreRegistroProveedorDto getPreRegistroById(Integer id) {
        Optional<PreRegistroProveedor> oPreRegistro = Optional.ofNullable(this.preRegistroProveedorService)
                .map(service -> service.getPreRegistroProveedorById(id));

        if (!oPreRegistro.isPresent()) {
            throw new PortalException("No existe un preregistro con el id " + id);
        }

        PreRegistroProveedorDto beanRetornoDTO = oPreRegistro.map(this.preRegistroPopulate::toDto).get();
        if (oPreRegistro.isPresent()) {
            PreRegistroProveedor preRegistroProveedor = oPreRegistro.get();
            beanRetornoDTO.setActivo(Optional.ofNullable(preRegistroProveedor.getActivo()).map(a -> a.equals("1")).orElse(false));
            beanRetornoDTO.setHabido(Optional.ofNullable(preRegistroProveedor.getHabido()).map(a -> a.equals("1")).orElse(false));
        }

        /* Obteniendo detalle de Lineas Comerciales */
        List<PreRegistroLineaComercial> preRegistroLineaComercialList = this.preRegistroLineaComercialRepository.
                findByIdRegistro(oPreRegistro.get());
        List<LineaComercial> lineaComercialList = new ArrayList<>();
        for (PreRegistroLineaComercial bean: preRegistroLineaComercialList) {
            LineaComercial beanLinea = new LineaComercial();
            beanLinea.setIdPadre(bean.getIdLineaComercial().getIdLineaComercial());
            beanLinea.setIdLineaComercial(bean.getIdFamilia().getIdLineaComercial());
            beanLinea.setIdLinea(bean.getIdLineaComercial().getIdLineaComercial());
            beanLinea.setIdFamilia(bean.getIdFamilia().getIdLineaComercial());
            beanLinea.setDescripcionPadre(bean.getIdLineaComercial().getDescripcion());
            beanLinea.setDescripcion(bean.getIdFamilia().getDescripcion());
            lineaComercialList.add(beanLinea);
        }
        beanRetornoDTO.setLineaComercialList(lineaComercialList);
        return beanRetornoDTO;
    }

    @Override
    public List<PreRegistroProveedorDto> getListSolicitudPendiente() {
        List<PreRegistroProveedor> list = this.preRegistroProveedorService.getListPreRegistroPendiente();
        return Optional.ofNullable(list)
                .filter(l -> !l.isEmpty())
                .map(l -> {
                    List<PreRegistroProveedorDto> listDto = new ArrayList<>();
                    l.stream().map(preRegistroPopulate::toDto).forEach(listDto::add);
                    return listDto;
                }).orElse(new ArrayList<>());
    }


    @Override
    public List<PreRegistroProveedorDto> getListSolicitudPendiente(String idHcp) {
        List<PreRegistroProveedor> preRegistroProveedorList =
                this.preRegistroProveedorService.getListPreRegistroPendiente(idHcp);

        List<PreRegistroProveedorDto> listDto = new ArrayList<PreRegistroProveedorDto>();
        for(PreRegistroProveedor bean: preRegistroProveedorList) {
            PreRegistroProveedorDto preRegistroProveedorDto = new PreRegistroProveedorDto();
            BeanUtils.copyProperties(bean, preRegistroProveedorDto);
            listDto.add(preRegistroProveedorDto);
        }
        return listDto;


//        List<PreRegistroProveedorDto> listDto = new ArrayList<PreRegistroProveedorDto>();
//        List<PreRegistroProveedor> list = this.preRegistroProveedorRepository.findByEstado(EstadoPreRegistroEnum.PENDIENTE.getCodigo());
//        for (PreRegistroProveedor bean : list) {
//            PreRegistroProveedorDto beanDto = this.preRegistroPopulate.toDto(bean);
//            List<PreRegistroLineaComercial> preRegistroLineaComercialList = this.preRegistroLineaComercialRepository.
//                    findByIdRegistro(bean);
//            List<LineaComercial> lineaComercialList = new ArrayList<>();
//            for (PreRegistroLineaComercial beanDetalle: preRegistroLineaComercialList) {
//                LineaComercial beanLinea = new LineaComercial();
//                beanLinea.setIdPadre(beanDetalle.getIdLineaComercial().getIdLineaComercial());
//                beanLinea.setIdLineaComercial(beanDetalle.getIdFamilia().getIdLineaComercial());
//                beanLinea.setIdLinea(beanDetalle.getIdLineaComercial().getIdLineaComercial());
//                beanLinea.setIdFamilia(beanDetalle.getIdFamilia().getIdLineaComercial());
//                beanLinea.setDescripcionPadre(beanDetalle.getIdLineaComercial().getDescripcion());
//                beanLinea.setDescripcion(beanDetalle.getIdFamilia().getDescripcion());
//                lineaComercialList.add(beanLinea);
//            }
//            beanDto.setLineaComercialList(lineaComercialList);
//            listDto.add(beanDto);
//        }
//        return listDto;
    }

    @Override
    public PreRegistroProveedorDto aprobarSolicitud(Integer idPreRegistro) {
        return this.preRegistroPopulate.toDto(this.preRegistroProveedorService.aprobarSolicitud(idPreRegistro));
    }

    @Override
    public PreRegistroProveedorDto reprobarSolicitud(Integer idPreRegistro) {
        return this.preRegistroPopulate.toDto(this.preRegistroProveedorService.reprobarSolicitud(idPreRegistro));
    }

}