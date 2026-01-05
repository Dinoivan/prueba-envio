package com.incloud.hcp.job;

import com.incloud.hcp.jco.documentoAceptacion.service.JCODocumentoAceptacionService;
import com.incloud.hcp.service.DocumentoAceptacionService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class DocumentoAceptacionExtractionJob {
    private final AtomicBoolean enabled = new AtomicBoolean(true);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JCODocumentoAceptacionService jcoDocumentoAceptacionService;
    private DocumentoAceptacionService documentoAceptacionService;

    @Autowired
    public DocumentoAceptacionExtractionJob(JCODocumentoAceptacionService jcoDocumentoAceptacionService,
                                            DocumentoAceptacionService documentoAceptacionService) {
        this.jcoDocumentoAceptacionService = jcoDocumentoAceptacionService;
        this.documentoAceptacionService = documentoAceptacionService;
    }


    // extraccion de documentos de aceptacion (EM y HES)
    @Scheduled(fixedRate = 8 * 60 * 1000 , initialDelay = 10 * 60 * 1000)
    public void runDocumentoAceptacionExtractor() {
        if (enabled.get()) {
            try {
                long l = System.currentTimeMillis();
                if(DateUtils.getCurrentHourOfDay() == 0) {
                    logger.error("Inicio Ejecucion (rango 2 dias) de Job Extraccion de Documentos de Aceptacion. Fecha y hora: " + DateUtils.getCurrentTimestamp());
                    jcoDocumentoAceptacionService.extraerDocumentoAceptacionListRFC(DateUtils.getFechaInicioAsSapStringByDiasAtras(1), DateUtils.getFechaActualAsSapString(), false, true, true);
                    logger.error("Fin Ejecucion (rango 2 dias) de Extraccion de Documentos de Aceptacion. Tiempo Total: " + (System.currentTimeMillis() - l) / 1000 + " segundos");
                }
                else if(DateUtils.getCurrentHourOfDay() == 2){
                    logger.error("Ejecucion de Job Extraccion de Documentos de Aceptacion en PAUSA. Fecha y hora: " + DateUtils.getCurrentTimestamp());
                }
                else{
                    logger.error("Inicio Ejecucion de Job Extraccion de Documentos de Aceptacion. Fecha y hora: " + DateUtils.getCurrentTimestamp());
                    jcoDocumentoAceptacionService.extraerDocumentoAceptacionListRFC(DateUtils.getFechaActualAsSapString(), DateUtils.getFechaActualAsSapString(), false, true, true);
                    logger.error("Fin Ejecucion de Extraccion de Documentos de Aceptacion. Tiempo Total: " + (System.currentTimeMillis() - l) / 1000 + " segundos");
                }

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