package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Joel on 02/09/2017.
 */
public interface MonedaRepository extends JpaRepository<Moneda, Integer> {

    Moneda getByCodigoMoneda(String codigoMoneda);






}
