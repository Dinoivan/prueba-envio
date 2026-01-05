package com.incloud.hcp.repository;

import com.incloud.hcp.domain.BienServicio;
import com.incloud.hcp.domain.RubroBien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by USER on 17/09/2017.
 */
public interface BienServicioRepository extends JpaRepository<BienServicio, Integer>{

    BienServicio getByCodigoSap(String codigoSap);

    List<BienServicio> findByCodigoSap (String codigoSap);

    List<BienServicio> findAllByCodigoSap (String codigoSap);

    List<BienServicio> findAllByNumeroParte (String nroParte);

    List<BienServicio> findAllByRubroBien(RubroBien rubroBien);

}
