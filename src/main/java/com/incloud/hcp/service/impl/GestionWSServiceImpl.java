package com.incloud.hcp.service.impl;

import com.incloud.hcp.service.GestionWSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GestionWSServiceImpl implements GestionWSService {
    protected final Logger log = LoggerFactory.getLogger(GestionWSServiceImpl.class);
}
