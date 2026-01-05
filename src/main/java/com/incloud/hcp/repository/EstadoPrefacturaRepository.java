package com.incloud.hcp.repository;

import com.incloud.hcp.domain.EstadoPrefactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoPrefacturaRepository extends JpaRepository<EstadoPrefactura, Integer> {
}
