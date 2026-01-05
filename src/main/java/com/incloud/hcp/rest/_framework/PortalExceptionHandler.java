package com.incloud.hcp.rest._framework;

import com.incloud.hcp.exception.PortalException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 05/09/2017.
 */
@ControllerAdvice
public class PortalExceptionHandler  {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PortalException.class)
    public @ResponseBody Map<String, Object> handleDataStoreException(PortalException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("errors", ex.getDetails());
        error.put("message", ex.getMessage());
        return error;
    }

}
