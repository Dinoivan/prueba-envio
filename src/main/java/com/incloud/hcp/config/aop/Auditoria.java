package com.incloud.hcp.config.aop;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.config.SystemLoggedUser;
import com.incloud.hcp.domain.AppProcesoLog;
import com.incloud.hcp.exception.ServiceException;
import com.incloud.hcp.service.delta.AppProcesoLogDeltaService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class Auditoria {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String EXITO = "1";
    private final String ERROR = "0";
    private final int MAXIMO_CARACTERES_SALIDA = 3850;
    private String mensajeOK = "OK";


    @Value("${log.proceso.enabled}")
    protected boolean logProcesoEnabled;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SystemLoggedUser systemLoggedUser;

    @Autowired
    private AppProcesoLogDeltaService appProcesoLogDeltaService;


    protected Object logExecutionTime(ProceedingJoinPoint joinPoint, String nameLogAnotacion) throws Throwable {
        Object proceed;
        String errorMensaje = "";
        if (!logProcesoEnabled) {
            proceed = joinPoint.proceed();
            return proceed;
        }

        long start = System.currentTimeMillis();
        AppProcesoLog logSistema = new AppProcesoLog();
        logSistema.setFechaInicioEjecucion(DateUtils.obtenerFechaHoraActual());
        logSistema.setDescripcionEstadoEjecucion(this.mensajeOK);
        logSistema.setEstadoEjecucion(EXITO);

        /* Obteniendo usuario logueado */
        UserSession user = new UserSession();//this.systemLoggedUser.getUserSession(token);
        logSistema.setUsuario("Prueba");//user.getId());
        //logSistema.setUsuario("prueba");


        /* Obteniendo nombre de Clase  y Metodo */
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        logSistema.setClaseProgramacion(signature.getDeclaringType().getSimpleName());
        String claseHija = joinPoint.getTarget().getClass().getSimpleName();
        if (Optional.ofNullable(claseHija).isPresent()) {
            logSistema.setClaseProgramacion(claseHija);
        }
        logSistema.setMetodoProgramacion(signature.getName());

        /* Obteniendo Parametros de Entrada */
        //Object[] argsO = joinPoint.getArgs();
        List<Object> listaParametrosTmp = null;
        List<Object> listaParametros = new ArrayList<>();
        try {
            Object target = joinPoint.getTarget();
            listaParametrosTmp = Arrays.asList(joinPoint.getArgs());
            for (Object obj : listaParametrosTmp) {
                if (!(obj instanceof HttpServletRequest ||
                        obj instanceof BindingResult ||
                        obj instanceof UriComponentsBuilder )) {
                    listaParametros.add(obj);
                }
            }

            if (listaParametros != null && listaParametros.size() > 0) {
                ObjectMapper mapperEntrada = new ObjectMapper();
                mapperEntrada.setSerializationInclusion(Include.NON_NULL);
                String parametros = mapperEntrada.writerWithDefaultPrettyPrinter().writeValueAsString(listaParametros);
                if (parametros.length() >= MAXIMO_CARACTERES_SALIDA) {
                    parametros = parametros.substring(0, MAXIMO_CARACTERES_SALIDA);
                }
                logSistema.setParametroEntrada(parametros);
            }
        } catch (Exception e) {
            errorMensaje = Utils.obtieneMensajeErrorException(e);
            logger.error("Log logVentaExecutionTime - exception 01 - " + errorMensaje);
            logger.error("Log logVentaExecutionTime - exception 01 - listaParametros - listaParametros: " + listaParametros);
            logSistema.setParametroEntrada(null);
        }

        /* Ejecucion del Metodo */
        try {
            proceed = joinPoint.proceed();
            try {
                if (proceed != null) {
                    ObjectMapper mapperSalida = new ObjectMapper();
                    mapperSalida.setSerializationInclusion(Include.NON_NULL);
                    String resultadoSalida = mapperSalida.writerWithDefaultPrettyPrinter().writeValueAsString(proceed);
                    if (resultadoSalida.length() >= MAXIMO_CARACTERES_SALIDA) {
                        resultadoSalida = resultadoSalida.substring(0, MAXIMO_CARACTERES_SALIDA);
                    }
                    logSistema.setResultadoSalida(resultadoSalida);

                }
            } catch (Exception e) {
                errorMensaje = Utils.obtieneMensajeErrorException(e);
                logger.error("Log logVentaExecutionTime - exception 02 - " + errorMensaje);
                logSistema.setResultadoSalida(null);
            }

        } catch (ServiceException ex) {
            logger.error("Log logVentaExecutionTime - service exception 03");
            if(ex.getArgs() == null){
                errorMensaje = messageSource.getMessage(ex.getKeyMessage(), new Object[]{},null);
            }else{
                errorMensaje = messageSource.getMessage(ex.getKeyMessage(), ex.getArgs(),null);
            }

            errorMensaje = Utils.obtieneMensajeErrorException(ex);
            if (errorMensaje.length() >= MAXIMO_CARACTERES_SALIDA) {
                errorMensaje = errorMensaje.substring(0, MAXIMO_CARACTERES_SALIDA);
            }
            logSistema.setDescripcionEstadoEjecucion(errorMensaje);
            logSistema.setEstadoEjecucion(ERROR);
            throw ex;
        } catch (Exception e) {
            logger.error("Log logVentaExecutionTime - exception 04");
            errorMensaje = Utils.obtieneMensajeErrorException(e);
            if (errorMensaje.length() >= MAXIMO_CARACTERES_SALIDA) {
                errorMensaje = errorMensaje.substring(0, MAXIMO_CARACTERES_SALIDA);
            }
            logSistema.setDescripcionEstadoEjecucion(errorMensaje);
            logSistema.setEstadoEjecucion(ERROR);
            throw e;
        } finally {

            /* Insertando en tabla LOG */
            logSistema.setFechaFinEjecucion(DateUtils.obtenerFechaHoraActual());
            long executionTime = System.currentTimeMillis() - start;
            logSistema.setDuracionMs(executionTime);
            this.appProcesoLogDeltaService.create(logSistema);

        }
        return proceed;

    }


}
