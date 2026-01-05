package com.incloud.hcp.rest._framework;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.config.BindingErrorsResponse;
import com.incloud.hcp.config.SystemLoggedUser;
import com.incloud.hcp.exception.PortalException;
import com.sap.cloud.security.xsuaa.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Administrador on 09/10/2017.
 */
public abstract class AppRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String CONSTANTE_SEPARADOR = " / ";

    @Autowired
    private SystemLoggedUser systemLoggedUser;



   /*public User getUserSCP(HttpServletRequest request) {
        return this.systemLoggedUser.getUserSCP(request);
    }*/

    public UserSession getUserSession(Token token) throws PortalException {
        return this.systemLoggedUser.getUserSession(token);

    }

    public UserSession getUserSessionEmail(String email) throws PortalException {
        return this.systemLoggedUser.getUserSessionByEmail(email);

    }

    protected ResponseEntity<?> processList(List lista) {
        return Optional.ofNullable(lista)
                .map(l -> {
                    Map response = new HashMap<>();
                    response.put("total", l.size());
                    response.put("data", l);
                    return response;
                })
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    protected ResponseEntity<Map> processObject(Object object) {
        return Optional.ofNullable(object)
                .map(oj -> {
                    Map response = new HashMap<>();
                    response.put("data", oj);
                    return response;
                })
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    protected ResponseEntity<?> processListEmpty() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected String devuelveErrorHeaders(BindingResult bindingResult, BindingErrorsResponse errors) {
        errors.addAllErrors(bindingResult);
        String errorDevuelve = "";
        int tam = bindingResult.getFieldErrors().size();
        int contador = 0;
        for (FieldError beanError: bindingResult.getFieldErrors()) {
            contador++;
            errorDevuelve += beanError.getDefaultMessage() ;
            if (contador < tam)
                errorDevuelve += CONSTANTE_SEPARADOR;
        }
        return errorDevuelve;
    }

    /*public boolean validarPermisoEjecucionServicio(@AuthenticationPrincipal Token token, String path, String tipo) throws PortalException{
        UserSession userSession = this.systemLoggedUser.getUserSession(token);
        List<String> list = new ArrayList<>();
        userSession.getListaGrupos().forEach(x -> list.add(x.getValue()));
        logger.error("Error list: "+list.size()+"  ***:"+list.toString());
        //return rolServicioAutorizacionRepository.getAutorizacionByRol("listar-centros-testRole",userSession.getGroupList()).size()>0?true:false;
        if(rolServicioAutorizacionRepository.getAutorizacionByRol(path,tipo,list).size()>0)
            //if(usuarioService.getRolServicioAutorizacion().stream().filter(x ->x.getServicio().contains(path) && x.getTipo()==tipo && x.getRol(). contains(userSession.getGroupList())).collect(Collectors.toList()).size()>0)
            return true;
        else throw new RuntimeException();

    }*/
}
