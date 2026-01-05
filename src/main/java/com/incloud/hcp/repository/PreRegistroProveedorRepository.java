package com.incloud.hcp.repository;

import com.incloud.hcp.domain.PreRegistroProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrador on 04/09/2017.
 */
public interface PreRegistroProveedorRepository extends JpaRepository<PreRegistroProveedor, Integer> {

    @Query("SELECT b FROM PreRegistroProveedor b WHERE b.estado=?1 order by b.ruc")
    List<PreRegistroProveedor> findByEstado(String estado);

    @Query("SELECT b FROM PreRegistroProveedor b WHERE b.idHcp= ?1")
    PreRegistroProveedor getPreRegistroByIdHcp(String idHcp);

    @Query("SELECT b FROM PreRegistroProveedor b WHERE b.email= ?1")
    PreRegistroProveedor getPreRegistroByEMail(String email);

    PreRegistroProveedor getByRuc(String ruc);
}
