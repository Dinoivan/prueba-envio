package com.incloud.hcp.job;

import com.incloud.hcp.jco.balanza.Carreta.service.JCOCarretaService;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MaestroCarretaExtractionJob {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JCOCarretaService jcoCarretaService;

    @Autowired
    public MaestroCarretaExtractionJob(JCOCarretaService jcoCarretaService) {
        this.jcoCarretaService = jcoCarretaService;
    }

    //extracción de carretas
    @Scheduled(cron = "0 0 5 * * SUN")
    public void runCarretaExtractor() {
        try {
            logger.error("Inicio Ejecucion de Job Extraccion de Carretas");
            jcoCarretaService.extraerCarretaListRFC(false);
            logger.error("Fin Ejecucion de Job Extraccion de Carretas");
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
