package com.incloud.hcp.job;

import com.incloud.hcp.jco.balanza.Transportista.service.TransportistaService;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TransportistaExtractionJob {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private TransportistaService transportistaService;

    @Autowired
    public TransportistaExtractionJob(TransportistaService transportistaService) {
        this.transportistaService = transportistaService;
    }

    //extracción de transportistas
    //@Scheduled(cron = "0 0 6 * * SUN")
    //@Scheduled(cron = "*/15 * * * * *")
    @Scheduled(fixedDelay = 15 * 1000 , initialDelay = 5 * 60 * 1000)
    public void runTransportistaExtractor() {
        try {
            logger.error("Inicio Ejecucion de Job Extraccion de Transportistas");
            transportistaService.extraerTransportistasListRFC(null);
            logger.error("Fin Ejecucion de Job Extraccion de Transportistas");
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }

    }

}
