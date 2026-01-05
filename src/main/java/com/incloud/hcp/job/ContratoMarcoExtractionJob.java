package com.incloud.hcp.job;

import com.incloud.hcp.jco.contratoMarco.service.JCOContratoMarcoPublicacionService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class ContratoMarcoExtractionJob {
    private final AtomicBoolean enabled = new AtomicBoolean(true);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JCOContratoMarcoPublicacionService jcoContratoMarcoPublicacionService;

    @Autowired
    public ContratoMarcoExtractionJob(JCOContratoMarcoPublicacionService jcoContratoMarcoPublicacionService) {
        this.jcoContratoMarcoPublicacionService = jcoContratoMarcoPublicacionService;
    }


    // extraccion de contratos marco
    @Scheduled(fixedRate = 4 * 60 * 1000 , initialDelay = 6 * 60 * 1000)
    public void runContratoMarcoExtractor() {
        if (enabled.get()) {
            try {
                long l = System.currentTimeMillis();
                logger.error("Inicio Ejecucion de Job Extraccion de Contratos Marco. Fecha y hora: " + DateUtils.getCurrentTimestamp());
                if(DateUtils.getCurrentHourOfDay() == 0) {
                    jcoContratoMarcoPublicacionService.extraerContratoMarcoListRFC(DateUtils.getFechaInicioAsSapStringByDiasAtras(1), DateUtils.getFechaActualAsSapString(), false);
                }
                else{
                    jcoContratoMarcoPublicacionService.extraerContratoMarcoListRFC(DateUtils.getFechaActualAsSapString(), DateUtils.getFechaActualAsSapString(), false);
                }
                logger.error("Fin Ejecucion de Job Extraccion de Contratos Marco. Tiempo Total: " + (System.currentTimeMillis() - l) / 1000 + " segundos");
            }
            catch(Exception e){
                String error = Utils.obtieneMensajeErrorException(e);
                throw new RuntimeException(error);
            }
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