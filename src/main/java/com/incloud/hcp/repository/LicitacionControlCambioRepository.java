package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionControlCambio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by USER on 15/01/2018.
 */
public interface LicitacionControlCambioRepository extends JpaRepository<LicitacionControlCambio, Integer> {

    public List<LicitacionControlCambio> findByLicitacionOrderByFechaCreacionDesc (Licitacion licitacion);

}
