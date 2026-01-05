package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.Transportista;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface TransportistaRepository extends JpaRepository<Transportista,Long> {
    Transportista findByRuc(String ruc);

    @Query("SELECT t.id FROM Transportista t where t.ruc = ?1 group by t.ruc, t.id")
    List<Integer> getIdTransportistaByRuc(String ruc);

    @Query("SELECT t FROM Transportista t where t.ruc = ?1")
    List<Transportista> getTransportistaByRucList(String ruc);
    @Query("SELECT t FROM Transportista t where t.ruc like %:ruc% and t.razonSocial like %:razonSocial%")
    List<Transportista> findAllByRucAndRazonSocial(@Param("ruc") String ruc,
                                                   @Param("razonSocial") String razonSocial);
}
