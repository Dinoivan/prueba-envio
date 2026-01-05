package com.incloud.hcp.repository;

import com.incloud.hcp.domain.OrdenCompraTextoCabecera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenCompraTextoCabeceraRepository extends JpaRepository<OrdenCompraTextoCabecera, Integer> {


}