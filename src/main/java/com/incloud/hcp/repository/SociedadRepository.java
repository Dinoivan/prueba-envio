package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Sociedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SociedadRepository extends JpaRepository<Sociedad, Integer> {

    @Query("SELECT s FROM Sociedad s where s.ruc = ?1")
    Sociedad getOneByRuc(String rucCliente);

    @Query("SELECT s FROM Sociedad s where s.codigoSociedad = ?1")
    Sociedad getByCodigoSociedad(String codigoSociedad);
}
