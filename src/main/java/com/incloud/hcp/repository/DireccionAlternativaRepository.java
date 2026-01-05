package com.incloud.hcp.repository;


import com.incloud.hcp.domain.balanza.Chofer;
import com.incloud.hcp.domain.balanza.DireccionAlternativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionAlternativaRepository extends JpaRepository<DireccionAlternativa, Integer> {
    public List<DireccionAlternativa> findByRuc(String ruc);
}
