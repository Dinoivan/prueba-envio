package com.incloud.hcp.job;

import com.incloud.hcp.jco.balanza.CentroAlmacen.service.JCOCentroAlmacenBlzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CentroAlmacenExtractionJob {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JCOCentroAlmacenBlzService jcoCentroAlmacenBlzService;

    @Autowired
    public CentroAlmacenExtractionJob(JCOCentroAlmacenBlzService jcoCentroAlmacenBlzService){
        this.jcoCentroAlmacenBlzService = jcoCentroAlmacenBlzService;
    }

    @Scheduled(cron = "0 0 1 * * SUN")
    public void runCentroAlmacenExtractor(){
        logger.error("[CentroAlmacenExtractor] - INICIO");
        try {
            this.jcoCentroAlmacenBlzService.extraerCentroAlmacenBlz();
        } catch (Exception e){
            logger.error("[CentroAlmacenExtractor] - ERROR: " + e.getMessage());
        }
        logger.error("[CentroAlmacenExtractor] - FIN");
    }
}
