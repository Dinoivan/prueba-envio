package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.LineaComercial;
import com.incloud.hcp.domain.PreRegistroProveedor;
import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.enums.EstadoPreRegistroEnum;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.myibatis.mapper.ParametroMapper;
import com.incloud.hcp.myibatis.mapper.PreRegistroProveedorMapper;
import com.incloud.hcp.repository.PreRegistroLineaComercialRepository;
import com.incloud.hcp.repository.PreRegistroProveedorRepository;
import com.incloud.hcp.repository.UsuarioLineaComercialRepository;
import com.incloud.hcp.repository.UsuarioRepository;
import com.incloud.hcp.service.PreRegistroProveedorService;
import com.incloud.hcp.service.notificacion.ProveedorPotencialAprobadoNotificacion;
import com.incloud.hcp.service.notificacion.ProveedorPotencialRechazadoNotificacion;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Administrador on 04/09/2017.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PreRegistroProveedorServiceImpl implements PreRegistroProveedorService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private PreRegistroProveedorMapper preRegistroProveedorMapper;
    private PreRegistroProveedorRepository preRegistroProveedorRepository;
    private ParametroMapper parametroMapper;
    private ProveedorPotencialAprobadoNotificacion proveedorPotencialAprobadoNotificacion;
    private ProveedorPotencialRechazadoNotificacion proveedorPotencialRechazadoNotificacion;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioLineaComercialRepository usuarioLineaComercialRepository;

    @Autowired
    private PreRegistroLineaComercialRepository preRegistroLineaComercialRepository;


    @Autowired
    public void setParametroMapper(ParametroMapper parametroMapper) {
        this.parametroMapper = parametroMapper;
    }

    @Autowired
    public void setPreRegistroProveedorRepository(PreRegistroProveedorRepository preRegistroProveedorRepository) {
        this.preRegistroProveedorRepository = preRegistroProveedorRepository;
    }

    @Autowired
    public void setPreRegistroProveedorMapper(PreRegistroProveedorMapper preRegistroProveedorMapper) {
        this.preRegistroProveedorMapper = preRegistroProveedorMapper;
    }

    @Autowired
    public void setProveedorPotencialAprobadoNotificacion(ProveedorPotencialAprobadoNotificacion proveedorPotencialAprobadoNotificacion) {
        this.proveedorPotencialAprobadoNotificacion = proveedorPotencialAprobadoNotificacion;
    }
    @Autowired
    public void setProveedorPotencialRechazadoNotificacion(ProveedorPotencialRechazadoNotificacion proveedorPotencialRechazadoNotificacion) {
        this.proveedorPotencialRechazadoNotificacion = proveedorPotencialRechazadoNotificacion;
    }

    @Override
    public PreRegistroProveedor aprobarSolicitud(Integer idPreRegistro)  {
        PreRegistroProveedor pre = preRegistroProveedorRepository.getOne(idPreRegistro);
        String estado = pre.getEstado();
        if (Optional.ofNullable(estado).isPresent()) {
            if (estado.equals(EstadoPreRegistroEnum.RECHAZADA.getCodigo())) {
                throw new PortalException("No se puede APROBAR porque ya fue RECHAZADO dicho Proveedor");
            }
        }
        pre.setEstado(EstadoPreRegistroEnum.APROBADA.getCodigo());

        preRegistroProveedorRepository.save(pre);
        proveedorPotencialAprobadoNotificacion.enviar(this.parametroMapper.getMailSetting(), pre);
        return pre;
    }

    @Override
    public PreRegistroProveedor reprobarSolicitud(Integer idPreRegistro) {
        PreRegistroProveedor pre = preRegistroProveedorRepository.getOne(idPreRegistro);
        String estado = pre.getEstado();
        if (Optional.ofNullable(estado).isPresent()) {
            if (estado.equals(EstadoPreRegistroEnum.APROBADA.getCodigo())) {
                throw new PortalException("No se puede RECHAZAR porque ya fue APROBADO dicho Proveedor");
            }
        }
        pre.setEstado(EstadoPreRegistroEnum.RECHAZADA.getCodigo());
        //return preRegistroProveedorRepository.save(pre);
        preRegistroProveedorRepository.save(pre);
        proveedorPotencialRechazadoNotificacion.enviar(this.parametroMapper.getMailSetting(), pre);
        return pre;
    }

    @Override
    public PreRegistroProveedor getByRuc(String ruc) {
        return preRegistroProveedorRepository.getByRuc(ruc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreRegistroProveedor> getListPreRegistroPendiente() {
        return Optional.ofNullable(preRegistroProveedorRepository
                .findByEstado(EstadoPreRegistroEnum.PENDIENTE.getCodigo()))
                .orElse(new ArrayList<>());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreRegistroProveedor> getListPreRegistroPendiente(String idHcp) {
        List<PreRegistroProveedor> listaPendiente = new ArrayList<PreRegistroProveedor>();
        if (StringUtils.isBlank(idHcp)) {
            return listaPendiente;
        }
        List<Usuario> usuarioList = this.usuarioRepository.findByCodigoUsuarioIdp(idHcp);
        if (usuarioList == null || usuarioList.size() <= 0) {
            return listaPendiente;
        }
        Usuario usuario = usuarioList.get(0);
        logger.error("getListPreRegistroPendiente usuario: " + usuario.toString());
        List<LineaComercial> lineaComercialList =
                this.usuarioLineaComercialRepository.findLineaComercialByIdUsuario(usuario);
        if (lineaComercialList == null || lineaComercialList.size() <= 0) {
            return listaPendiente;
        }
        logger.error("getListPreRegistroPendiente lineaComercialList size: " + lineaComercialList.size());
        logger.error("getListPreRegistroPendiente lineaComercialList : " + lineaComercialList.toString());
        List<PreRegistroProveedor> preRegistroProveedorList = this.preRegistroLineaComercialRepository.
                findProveedorByLineaAndPendiente(lineaComercialList, EstadoPreRegistroEnum.PENDIENTE.getCodigo());

        return Optional.ofNullable(preRegistroProveedorList)
                .orElse(new ArrayList<>());
    }

    @Override
    public PreRegistroProveedor guardar(PreRegistroProveedor preRegistroProveedor) throws Exception {
        logger.debug("Guardar preregistro del proveedor potencial");
        PreRegistroProveedor preRegistroProveedorBuscar = this.preRegistroProveedorRepository.getByRuc(preRegistroProveedor.getRuc());
        if (Optional.ofNullable(preRegistroProveedorBuscar).isPresent()) {
            preRegistroProveedor.setIdRegistro(preRegistroProveedorBuscar.getIdRegistro());
        }
        preRegistroProveedor.setEstado(EstadoPreRegistroEnum.PENDIENTE.getCodigo());
        logger.error("Guardar preregistro del proveedor potencial dto a guardar: " + preRegistroProveedor);
        preRegistroProveedor = this.preRegistroProveedorRepository.save(preRegistroProveedor);
        logger.error("Guardar preregistro del proveedor potencial guardado: " + preRegistroProveedor);
        //preRegistroProveedorMapper.guardarSolicitud(preRegistroProveedor);
        return preRegistroProveedor;
    }

    @Override
    public PreRegistroProveedor getPreRegistroProveedorByIdHcp(String idHcp) {
        return Optional.ofNullable(this.preRegistroProveedorRepository)
                .map(r -> r.getPreRegistroByIdHcp(idHcp))
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public PreRegistroProveedor getPreRegistroProveedorById(Integer idRegistro) {
        logger.error("Obtener el pre - registro por id " + idRegistro);
        return Optional.ofNullable(this.preRegistroProveedorRepository)
                .map(r -> r.getOne(idRegistro))
                .orElse(null);
    }

    @Override
    public PreRegistroProveedor updateSearchSunat(PreRegistroProveedor temp) {
        logger.error("updateSearchSunat 00 temp " + temp.toString());
        return Optional.ofNullable(this.preRegistroProveedorRepository)
                .map(r -> r.getOne(temp.getIdRegistro()))
                .map(registro -> {
                    logger.error("updateSearchSunat 01 temp: " + temp.toString());

                    BeanUtils.copyProperties(temp, registro);

                    registro.setSunat(temp.getSunat());
                    registro.setRazonSocial(temp.getRazonSocial());
                    registro.setHabido(temp.getHabido());
                    registro.setActivo(temp.getActivo());
                    registro.setRegion(temp.getRegion());
                    registro.setProvincia(temp.getProvincia());
                    registro.setDistrito(temp.getDistrito());
                    registro.setDireccion(temp.getDireccion());
                    if (temp.getUbigeo().length() > 6) {
                        Integer nUbigeo = new Integer(temp.getUbigeo());
                        String sUbigeo = nUbigeo.toString();
                        temp.setUbigeo(sUbigeo);
                    }

                    registro.setUbigeo(temp.getUbigeo());
                    logger.error("updateSearchSunat 02 registro: " + registro.toString());

                    return this.preRegistroProveedorRepository.save(registro);
                })
                .orElse(null);
    }

    /*
    @Override
    @Transactional(readOnly = true)
    public List<PreRegistroProveedorDto> getListPreRegistroProveedorDto() {
        return Optional.ofNullable(preRegistroProveedorRepository)
                .map(PreRegistroProveedorRepository::findAll)
                .map(l -> {
                    List<PreRegistroProveedorDto> list = new ArrayList<>();
                    PreRegistroDtoMapper mapper = new PreRegistroDtoMapper(tipoProveedorRepository);
                    l.stream().map(mapper::toDto).forEach(list::add);
                    return list;
                })
                .orElse(new ArrayList<>());
    }
    */

    /*
    @Override
    public PreRegistroProveedorDto getPreRegistroProveedorDtoByEmail(String email) {
        logger.debug("Consultando pre - registro del proveedor con el email " + email);
        PreRegistroDtoMapper mapper = new PreRegistroDtoMapper(tipoProveedorRepository);
        PreRegistroProveedor preRegistro = Optional.ofNullable(this.preRegistroProveedorRepository)
                .map(r -> r.getPreRegistroByEMail(email))
                .orElse(new PreRegistroProveedor());
        return mapper.toDto(preRegistro);
    }
    */
}