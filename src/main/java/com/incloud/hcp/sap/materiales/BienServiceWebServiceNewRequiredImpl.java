package com.incloud.hcp.sap.materiales;

import com.incloud.hcp.domain.BienServicio;
import com.incloud.hcp.repository.BienServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class BienServiceWebServiceNewRequiredImpl implements BienServiceWebServiceNewRequired {

    @Autowired
    private BienServicioRepository bienServicioRepository;

    public BienServicio saveNewRequired(BienServicio bienServicio) {
        bienServicio = this.bienServicioRepository.save(bienServicio);
        return bienServicio;
    }
}
