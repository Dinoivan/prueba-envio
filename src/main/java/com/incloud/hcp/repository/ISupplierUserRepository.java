package com.incloud.hcp.repository;

import com.incloud.hcp.domain.SupplierUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ISupplierUserRepository extends JpaRepository<SupplierUser, Integer> {

    @Transactional( readOnly = true )
    @Query( "select o from SupplierUser o where upper( o.email ) like upper(?1)" )
    Optional<SupplierUser> findByEmail( String email );

    @Transactional( readOnly = true )
    @Query( "select p.ruc from SupplierUser o " +
            "inner join Proveedor p on o.supplierId = p.idProveedor " +
            "where upper( o.email ) like upper(?1)" )
    Optional<String> findRucByEmail( String email );

    @Transactional( readOnly = true )
    @Query( "select count( o ) from SupplierUser o where upper( o.email ) like upper(?1)" )
    Integer countByEmail( String email );

}