package com.incloud.hcp.job;

import com.incloud.hcp.jco.balanza.Proveedor.service.JCOProveedorBlzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProveedoresExtractionJob {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JCOProveedorBlzService jcoProveedorBlzService;

    @Autowired
    public ProveedoresExtractionJob(JCOProveedorBlzService jcoProveedorBlzService){
        this.jcoProveedorBlzService = jcoProveedorBlzService;
    }
    @Scheduled(cron = "0 0 3 * * SUN")
    public void runProveedoresExtractor(){
        logger.error("[ProveedoresExtractor] - INICIO");
        try {
            this.jcoProveedorBlzService.extraerProveedorListRFC(null);
        } catch (Exception e){
            logger.error("[ProveedoresExtractor] - ERROR: " + e.getMessage());
        }
        logger.error("[ProveedoresExtractor] - FIN");
    }
}
