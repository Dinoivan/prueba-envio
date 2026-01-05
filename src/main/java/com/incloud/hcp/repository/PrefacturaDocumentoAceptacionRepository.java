package com.incloud.hcp.repository;

import com.incloud.hcp.domain.PrefacturaDocumentoAceptacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PrefacturaDocumentoAceptacionRepository extends JpaRepository<PrefacturaDocumentoAceptacion, Integer> {

    @Query("SELECT pd FROM PrefacturaDocumentoAceptacion pd where pd.idPrefactura = ?1")
    List<PrefacturaDocumentoAceptacion> getAllAssociatedByIdPrefactura(Integer idPrefactura);


    @Query("SELECT pd FROM PrefacturaDocumentoAceptacion pd where pd.idDocumentoAceptacion = ?1 and pd.isActive = '1'")
    List<PrefacturaDocumentoAceptacion> getAllByIdDocumentoAceptacionAndIsActive(Integer idPrefactura);

}