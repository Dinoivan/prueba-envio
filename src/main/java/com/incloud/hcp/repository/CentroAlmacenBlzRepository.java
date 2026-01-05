package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.CentroAlmacenBlz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CentroAlmacenBlzRepository extends JpaRepository<CentroAlmacenBlz, Integer> {

    @Query("SELECT c FROM CentroAlmacenBlz c WHERE c.centro = ?1")
    List<CentroAlmacenBlz> getCentroAlmacenBlzByCentro(String centro);

    @Query("SELECT c FROM CentroAlmacenBlz c WHERE c.centro = ?1 and c.codigoAlmacen = ?2")
    List<CentroAlmacenBlz> getCentroAlmacenBlzByCentroAndCodigoAlmacen(String centro, String codigoAlmacen);

    @Query("SELECT c FROM CentroAlmacenBlz c")
    List<CentroAlmacenBlz> getAllCentroAlmacen();

    @Modifying
    @Query("DELETE FROM CentroAlmacenBlz")
    void deleteAlll();
}
