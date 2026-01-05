package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.repository.UsuarioRepository;
import com.incloud.hcp.service.UsuarioAutoService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class UsuarioAutoServiceImpl implements UsuarioAutoService {

    private static Logger logger = LoggerFactory.getLogger(UsuarioAutoServiceImpl.class);

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioAutoServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario save(Usuario user) {
        try {
            user.setUsuarioCreacion(1);
            user.setUsuarioModificacion(1);
            user.setFechaCreacion(DateUtils.getCurrentTimestamp());
            user.setFechaModificacion(DateUtils.getCurrentTimestamp());
            user = usuarioRepository.save(user);

            return user;
        }
        catch (Exception e){
            String error = Utils.obtieneMensajeErrorException(e);
            logger.error("Excepción al guardar usuario: " + error);
            throw new RuntimeException(error);
        }
    }

    @Override
    public Usuario update(Usuario user) {
        try {
            user.setFechaModificacion(DateUtils.getCurrentTimestamp());
            user.setUsuarioModificacion(1);
            user = usuarioRepository.save(user);
            return user;
        }
        catch (Exception e){
            String error = Utils.obtieneMensajeErrorException(e);
            logger.error("Excepción al Editar usuario: " + error);
            throw new RuntimeException(error);
        }
    }

    @Override
    public void delete(Usuario user) {
        try {
            usuarioRepository.delete(user);
        }catch (Exception e){
            String error = Utils.obtieneMensajeErrorException(e);
            logger.error("Excepción al Eliminar usuario: " + error);
            throw new RuntimeException(error);
        }
    }
}
