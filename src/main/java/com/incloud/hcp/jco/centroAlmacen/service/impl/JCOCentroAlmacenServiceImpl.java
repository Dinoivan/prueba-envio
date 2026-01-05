package com.incloud.hcp.jco.centroAlmacen.service.impl;


import com.incloud.hcp.jco.centroAlmacen.dto.CentroAlmacenRFCResponseDto;
import com.incloud.hcp.jco.centroAlmacen.service.JCOCentroAlmacenService;
import com.incloud.hcp.jco.centroAlmacen.service.JCOCentroAlmacenServiceNew;
import com.incloud.hcp.repository.TempCentroAlmacenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOCentroAlmacenServiceImpl implements JCOCentroAlmacenService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TempCentroAlmacenRepository tempCentroAlmacenRepository;

    @Autowired
    private JCOCentroAlmacenServiceNew jcoCentroAlmacenServiceNew;


    @Override
    public CentroAlmacenRFCResponseDto actualizaCentroAlmacen(String centro) throws Exception {

        CentroAlmacenRFCResponseDto result = this.jcoCentroAlmacenServiceNew.getListaCentroAlmacen(centro);
        this.tempCentroAlmacenRepository.saveNeworUpdateActualizados();
        return result;
    }

}
