package com.incloud.hcp.repository;

import com.incloud.hcp.domain.almacen.LugarEntrega;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LugarEntregaRepository extends JpaRepository<LugarEntrega, Integer> {
    @Query("SELECT p FROM LugarEntrega p WHERE p.lugarEntrega = ?1")
    Optional<LugarEntrega> findByLugarEntrega(String lugar);

}
