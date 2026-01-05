package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.ProveedorBLZ;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProveedorBalanzaRepository extends JpaRepository<ProveedorBLZ, Long> {

    @Query("SELECT t.id FROM ProveedorBLZ t where t.ruc = ?1 group by t.ruc, t.id")
    List<Integer> getIdProveedorByRuc(String ruc);

    @Query("SELECT t FROM ProveedorBLZ t where t.acreedor = ?1")
    ProveedorBLZ getProveedorBLZByAcreedor(String acreedor);

    @Query("SELECT t FROM ProveedorBLZ t where t.ruc = ?1")
    List<ProveedorBLZ> getProveedorBLZByRucList(String ruc);

    @Query("SELECT t FROM ProveedorBLZ t where t.ruc like %:ruc% and t.razonSocial like %:razonSocial%")
    List<ProveedorBLZ> findAllByRucAndRazocial(@Param("ruc") String ruc,
                                               @Param("razonSocial") String razonSocial);
}
