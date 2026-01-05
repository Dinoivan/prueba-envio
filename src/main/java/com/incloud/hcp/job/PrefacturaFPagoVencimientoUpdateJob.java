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
public class PrefacturaFPagoVencimientoUpdateJob {
    private final AtomicBoolean enabled = new AtomicBoolean(true);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private PrefacturaService prefacturaService;

    @Autowired
    public PrefacturaFPagoVencimientoUpdateJob(PrefacturaService prefacturaService) {
        this.prefacturaService = prefacturaService;
    }


    // actualizacion de prefacturas anuladas en SAP
    @Scheduled(fixedRate = 30 * 60 * 1000 , initialDelay = 3 * 60 * 1000)
    public void runPrefacturaAnuladaUpdater() {
        if (enabled.get()) {
            try {
                long l = System.currentTimeMillis();
                logger.error("Inicio Ejecucion de Job Actualizacion de Prefacturas (Fecha Pago y Vencimiento). Fecha y hora: " + DateUtils.getCurrentTimestamp());
//                if(DateUtils.getCurrentHourOfDay() == 0) {
                    prefacturaService.actualizarPrefacturasFechaPagoVencimiento(DateUtils.getFechaInicioAsSapStringByDiasAtras(30), DateUtils.getFechaActualAsSapString(), false);
//                }
//                else {
//                    prefacturaService.actualizarPrefacturasFechaPagoVencimiento(DateUtils.getFechaActualAsSapString(), DateUtils.getFechaActualAsSapString(), false);
//                }
                logger.error("Fin Ejecucion de Actualizacion de Prefacturas (Fecha Pago y Vencimiento). Tiempo Total: " + (System.currentTimeMillis() - l) / 1000 + " segundos");
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