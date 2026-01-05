package com.incloud.hcp.config.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditoriaAOP extends Auditoria{

    @Around(
            "execution(* com.incloud.hcp.rest._framework.AppRest+.*(..)) "
    )
    public Object logVentaExecutionTimeGeneric(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecutionTime(joinPoint, "LogAnotacion");
    }




}
