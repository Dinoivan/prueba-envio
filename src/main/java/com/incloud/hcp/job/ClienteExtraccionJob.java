package com.incloud.hcp.job;

import com.incloud.hcp.rest.UtilRest;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClienteExtraccionJob {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private UtilRest utilrest;

    @Autowired
    public ClienteExtraccionJob(UtilRest utilrest) {
        this.utilrest = utilrest;
    }

    @Scheduled(cron = "0 0 4 * * SUN")
    public void runClienteExtractor() {
        try {
            logger.error("Inicio Ejecucion de Job Extraccion de Cliente");
            utilrest.extraeMaestroCliente();
            logger.error("Fin Ejecucion de Job Extraccion de Cliente");
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}