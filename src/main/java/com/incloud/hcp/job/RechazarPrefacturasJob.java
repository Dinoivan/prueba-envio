package com.incloud.hcp.job;

import com.incloud.hcp.domain.LogTransaccion;
import com.incloud.hcp.repository.LogTransaccionRepository;
import com.incloud.hcp.repository.PrefacturaRepository;
import com.incloud.hcp.service.impl.PrefacturaServiceImpl;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.StrUtils;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class RechazarPrefacturasJob {
    private final PrefacturaServiceImpl prefacturaService;
    private final PrefacturaRepository prefacturaRepository;
    private final LogTransaccionRepository logTransaccionRepository;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RechazarPrefacturasJob(PrefacturaServiceImpl prefacturaService, PrefacturaRepository prefacturaRepository, LogTransaccionRepository logTransaccionRepository) {
        this.prefacturaService = prefacturaService;
        this.prefacturaRepository = prefacturaRepository;
        this.logTransaccionRepository = logTransaccionRepository;
    }


    @Async
    @Scheduled(cron = "0 0/20 * * * ?")
    public void scheduleRechazarPrefacturasSinAdjuntos() {
        logger.error("Cron Task scheduleRechazarPrefacturasSinAdjuntos :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {

            Date fechaInicio = DateUtils.obtenerFechaHoraActual();
            LogTransaccion logTransaccion = new LogTransaccion();
            logTransaccion.setLogFecha(DateUtils.getCurrentTimestamp());
            logTransaccion.setTipoRegistro("RechazarPrefacturasSinAdjuntos");
            try {
                logger.error("try jobRechazarFacturas" + fechaInicio);
                String rpta = this.prefacturaService.rechazarPrefacturasSinAdjuntos(fechaInicio,null);
                logTransaccion.setEnvioTrama(rpta);
                this.logTransaccionRepository.save(logTransaccion);
            } catch (Exception e){
                String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
                LogTransaccion logTransaccionError = new LogTransaccion();
                logTransaccionError.setLogFecha(DateUtils.getCurrentTimestamp());
                logTransaccionError.setEnvioTrama(fechaInicio + " - " + error);
                logTransaccionError.setTipoRegistro("Error - RechazarPrefacturasSinAdjuntos");
                this.logTransaccionRepository.save(logTransaccionError);
            }
        }
        catch (Exception e) {
            logger.error("Cron Task Fin JOB scheduleRechazarPrefacturasSinAdjuntos ERROR: " + Utils.obtieneMensajeErrorException(e));
        }
    }
}
