package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    @Query("SELECT e FROM Estado e where e.id = ?1")
    Estado getById(Integer id);
}
