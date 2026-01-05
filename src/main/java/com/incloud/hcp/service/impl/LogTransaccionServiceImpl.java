package com.incloud.hcp.service.impl;

import com.incloud.hcp.config.SystemLoggedUser;
import com.incloud.hcp.domain.LogTransaccion;
import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.repository.LogTransaccionRepository;
import com.incloud.hcp.repository.UsuarioRepository;
import com.incloud.hcp.service.LogTransaccionService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.StrUtils;
import com.sap.cloud.security.xsuaa.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogTransaccionServiceImpl implements LogTransaccionService {

    private LogTransaccionRepository logTransaccionRepository;
    private SystemLoggedUser systemLoggedUser;
    private UsuarioRepository usuarioRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public LogTransaccionServiceImpl(LogTransaccionRepository logTransaccionRepository,
                                     SystemLoggedUser systemLoggedUser,
                                     UsuarioRepository usuarioRepository) {
        this.logTransaccionRepository = logTransaccionRepository;
        this.systemLoggedUser = systemLoggedUser;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void grabarNuevaLineaLogTransaccion(String tipoTransaccion, String tipoRegistro, String envioTrama, Integer idRegistro, String respuestaCodigo, String respuestaTexto, boolean isJob,Token token){
        logger.error("NUEVO LOG TRANSACCION: envioTramaOriginalSize = " + (envioTrama == null ? 0 : envioTrama.length()) + " // respuestaTextoOriginalSize = " + (respuestaTexto == null ? 0 : respuestaTexto.length()));
        String newEnvioTrama = StrUtils.truncarString(envioTrama);
        String newRespuestaTexto = StrUtils.truncarString(respuestaTexto);
        logger.error("NUEVO LOG TRANSACCION: newEnvioTramaSize = " + (newEnvioTrama == null ? 0 : newEnvioTrama.length()) + " // newRespuestaTextoSize = " + (newRespuestaTexto == null ? 0 : newRespuestaTexto.length()));

        LogTransaccion logTransaccion = new LogTransaccion();

        logTransaccion.setTipoTransaccion(tipoTransaccion);
        logTransaccion.setTipoRegistro(tipoRegistro);
        logTransaccion.setEnvioTrama(newEnvioTrama);
        logTransaccion.setIdRegistro(idRegistro);
        logTransaccion.setRespuestaCodigo(respuestaCodigo);
        logTransaccion.setRespuestaTexto(newRespuestaTexto);
        logTransaccion.setLogFecha(DateUtils.getCurrentTimestamp());

        if (isJob) {
            logTransaccion.setLogUsuario("[JOB]");
        }
        else {
            Usuario usuario = usuarioRepository.getByCodigoUsuarioIdp(systemLoggedUser.getUserSession(token).getId());
            if(usuario != null)
                logTransaccion.setLogUsuario(usuario.getCodigoSap());
        }

        logTransaccion = logTransaccionRepository.save(logTransaccion);
        logger.error("NUEVO LOG TRANSACCION: " + logTransaccion.toString());
    }

    @Override
    public String getUsuarioTransaccionBD(String transaccion, String tipoRegistro, Integer idRegistro){
        String username = logTransaccionRepository.getUsuarioTransaccionBD(transaccion, tipoRegistro, idRegistro);
        return username;
    }
}