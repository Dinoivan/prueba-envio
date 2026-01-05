package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.CentroAlmacenBalanza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturaPesoRepository extends JpaRepository<CentroAlmacenBalanza, Integer> {
}
