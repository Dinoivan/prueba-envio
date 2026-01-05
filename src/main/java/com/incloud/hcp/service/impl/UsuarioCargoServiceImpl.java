package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.domain.UsuarioCargo;
import com.incloud.hcp.dto.UsuarioCargoDto;
import com.incloud.hcp.repository.UsuarioCargoRepository;
import com.incloud.hcp.repository.UsuarioRepository;
import com.incloud.hcp.service.UsuarioCargoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UsuarioCargoServiceImpl implements UsuarioCargoService {

    private final Logger log = LoggerFactory.getLogger(UsuarioCargoServiceImpl.class);

    private UsuarioRepository usuarioRepository;
    private UsuarioCargoRepository usuarioCargoRepository;

    @Autowired
    public UsuarioCargoServiceImpl(UsuarioRepository usuarioRepository,
                                   UsuarioCargoRepository usuarioCargoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioCargoRepository = usuarioCargoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioCargo> findbyIdUsuario(Integer idUsuario) {
        log.debug("Ingresando find: ", idUsuario);
        return this.usuarioCargoRepository.findListaByIdUsuario(idUsuario);
    }

    @Override
    public List<UsuarioCargo> grabarLista(UsuarioCargoDto usuarioCargoDto) throws Exception {
        Integer idUsuario = usuarioCargoDto.getIdUsuario();
        Usuario usuario = this.usuarioRepository.getOne(idUsuario);
        if (!Optional.ofNullable(usuario).isPresent()) {
            throw new Exception("No se encontró Usuario con ID: " + idUsuario);
        }
        this.usuarioCargoRepository.deleteByUsuario(idUsuario);
        List<UsuarioCargo> usuarioCargoList = usuarioCargoDto.getUsuarioCargoList();
        for (int i=0; i < usuarioCargoList.size(); i++) {
            UsuarioCargo bean = usuarioCargoList.get(i);
            bean.setId(null);
            bean.setIdUsuario(idUsuario);
            bean = this.usuarioCargoRepository.save(bean);
            usuarioCargoList.set(i, bean);
        }
        return usuarioCargoList;
    }

}