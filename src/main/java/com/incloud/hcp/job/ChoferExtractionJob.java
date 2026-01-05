package com.incloud.hcp.job;

import com.incloud.hcp.jco.balanza.Chofer.service.JCOChoferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ChoferExtractionJob {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JCOChoferService jcoChoferService;

    @Autowired
    public ChoferExtractionJob(JCOChoferService jcoChoferService){
        this.jcoChoferService = jcoChoferService;
    }

    //@Scheduled(cron = "*/15 * * * * *")
    @Scheduled(fixedDelay = 15 * 1000 , initialDelay = 5 * 60 * 1000)
    public void runChoferExtractor(){
        logger.error("[ChoferExtractor] - INICIO");
        try {
            this.jcoChoferService.extraerChoferListRFC(false);
        } catch (Exception e){
            logger.error("[ChoferExtractor] - ERROR: " + e.getMessage());
        }
        logger.error("[ChoferExtractor] - FIN");
    }
}
