package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.Cargo;
import com.incloud.hcp.repository.CargoRepository;
import com.incloud.hcp.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by MARCELO on 07/11/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class CargoServiceImpl implements CargoService {

    @Autowired
    CargoRepository cargoRepository;

    @Override
    public List<Cargo> getAllCargo() {
        return cargoRepository.findAllByOrderByNombreCargoAsc();
    }
}
