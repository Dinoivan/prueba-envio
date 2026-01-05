package com.incloud.hcp.job;

import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenDespachoPublicacionService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class OrdenDespachoExtractionJob {
    private final AtomicBoolean enabled = new AtomicBoolean(true);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JCOOrdenDespachoPublicacionService jcoOrdenDespachoPublicacionService;

    @Autowired
    public OrdenDespachoExtractionJob(JCOOrdenDespachoPublicacionService jcoOrdenDespachoPublicacionService) {
        this.jcoOrdenDespachoPublicacionService = jcoOrdenDespachoPublicacionService;
    }

    // extraccion de ordenes de Despacho
    @Scheduled(fixedRate = 2 * 60 * 1000 , initialDelay = 3 * 60 * 1000)
    public void runOrdenDespachoExtractor() {
        try {
            long l = System.currentTimeMillis();
            logger.error("Inicio Ejecucion de Job Extraccion de Ordenes de Despacho. Fecha y hora: " + DateUtils.getCurrentTimestamp());
            if(DateUtils.getCurrentHourOfDay() == 0) {
                jcoOrdenDespachoPublicacionService.extraerOrdenDespachoListRFC(DateUtils.getFechaInicioAsSapStringByDiasAtras(1), DateUtils.getFechaActualAsSapString(), true);
            }
            else{
                jcoOrdenDespachoPublicacionService.extraerOrdenDespachoListRFC(DateUtils.getFechaActualAsSapString(), DateUtils.getFechaActualAsSapString(), true);
            }
            logger.error("Fin Ejecucion de Job Extraccion de Ordenes de Despacho. Tiempo Total: " + (System.currentTimeMillis() - l) / 1000 + " segundos");
        }
        catch(Exception e){
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    public boolean toggle() {
        enabled.set(!enabled.get());
        return enabled.get();
    }

    public boolean current() {
        return enabled.get();
    }
}