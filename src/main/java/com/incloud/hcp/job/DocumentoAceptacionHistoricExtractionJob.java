package com.incloud.hcp.job;

import com.incloud.hcp.service.DocumentoAceptacionService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class DocumentoAceptacionHistoricExtractionJob {
    private final AtomicBoolean enabled = new AtomicBoolean(true);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DocumentoAceptacionService documentoAceptacionService;

    @Autowired
    public DocumentoAceptacionHistoricExtractionJob(DocumentoAceptacionService documentoAceptacionService) {
        this.documentoAceptacionService = documentoAceptacionService;
    }


    // extraccion de documentos de aceptacion (EM y HES) una vez al dia de 15 dias atras
    @Scheduled(cron = "0 4 2 * * ?", zone = DateUtils.DEFAULT_TIMEZONE)
    public void runDocumentoAceptacionHistoricExtractor() {
        if (enabled.get()) {
            try {
                LocalDate localDateFechaFin = LocalDate.now(ZoneId.of(DateUtils.DEFAULT_TIMEZONE)).minusDays(2); // obtiene fecha de anteayer
                LocalDate localDateFechaInicio = localDateFechaFin.minusDays(14); // obtiene fecha de 14 dias atras (metodo de extraccion es inclusivo => total 15 dias)
                long l = System.currentTimeMillis();
                logger.error("Inicio Ejecucion de Job Extraccion (historica) de Documentos de Aceptacion. Fecha y hora: " + DateUtils.getCurrentTimestamp());
                documentoAceptacionService.extraerDocumentoAceptacionMasivoByRangoFechas(localDateFechaInicio, localDateFechaFin, true, false);
                logger.error("Fin Ejecucion de Job Extraccion (historica) de Documentos de Aceptacion. Tiempo Total: " + (System.currentTimeMillis() - l) / 1000 + " segundos");
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