package com.incloud.hcp.job;

import com.incloud.hcp.jco.balanza.Transporte.service.JCOTransporteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TransporteExtractionJob {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JCOTransporteService jcoTransporteService;

    @Autowired
    public TransporteExtractionJob(JCOTransporteService jcoTransporteService){
        this.jcoTransporteService = jcoTransporteService;
    }
    //@Scheduled(cron = "0 0 2 * * SUN")
    //@Scheduled(cron = "*/15 * * * * *")
    @Scheduled(fixedDelay = 15 * 1000 , initialDelay = 5 * 60 * 1000)
    public void runTransporteExtractor(){
        logger.error("[TransportesExtractor] - INICIO");
        try {
            this.jcoTransporteService.extraerTransporteListRFC(false);
        } catch (Exception e){
            logger.error("[TransportesExtractor] - ERROR: " + e.getMessage());
        }
        logger.error("[TransportesExtractor] - FIN");
    }
}
