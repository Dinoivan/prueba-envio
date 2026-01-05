package com.incloud.hcp.job;

import com.incloud.hcp.service.PrefacturaService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class PrefacturaAnuladaUpdateJob {
    private final AtomicBoolean enabled = new AtomicBoolean(true);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private PrefacturaService prefacturaService;

    @Autowired
    public PrefacturaAnuladaUpdateJob(PrefacturaService prefacturaService) {
        this.prefacturaService = prefacturaService;
    }


    // actualizacion de prefacturas anuladas en SAP
    @Scheduled(fixedRate = 2 * 60 * 1000 , initialDelay = 4 * 60 * 1000)
    public void runPrefacturaAnuladaUpdater() {
        if (enabled.get()) {
            try {
                long l = System.currentTimeMillis();
                logger.error("Inicio Ejecucion de Job Actualizacion de Prefacturas Anuladas. Fecha y hora: " + DateUtils.getCurrentTimestamp());
                if(DateUtils.getCurrentHourOfDay() == 0) {
                    prefacturaService.actualizarPrefacturasAnuladasPorRangoFechas(DateUtils.getFechaInicioAsSapStringByDiasAtras(1), DateUtils.getFechaActualAsSapString(), false,null);
                }
                else {
                    prefacturaService.actualizarPrefacturasAnuladasPorRangoFechas(DateUtils.getFechaActualAsSapString(), DateUtils.getFechaActualAsSapString(), false,null);
                }
                logger.error("Fin Ejecucion de Actualizacion de Prefacturas Anuladas. Tiempo Total: " + (System.currentTimeMillis() - l) / 1000 + " segundos");
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