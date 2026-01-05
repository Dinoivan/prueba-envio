package com.incloud.hcp.repository;

import com.incloud.hcp.domain.ControlCambioCampo;
import com.incloud.hcp.domain.LicitacionControlCambio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Gadiel on 12/02/2018.
 */
public interface ControlCambioCampoRepository extends JpaRepository<ControlCambioCampo, Integer> {

    public List<ControlCambioCampo> findByLicitacionControlCambio(LicitacionControlCambio licitacionControlCambio);

}
