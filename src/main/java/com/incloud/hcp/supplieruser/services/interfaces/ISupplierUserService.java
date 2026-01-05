package com.incloud.hcp.supplieruser.services.interfaces;

import com.incloud.hcp.domain.SupplierUser;

import java.util.List;
import java.util.Optional;

public interface ISupplierUserService {

    List<SupplierUser> findBySupplierId( Integer supplierId );

    SupplierUser findById( Integer id );

    SupplierUser findByEmail( String email );

    Optional<String> findRucByEmail( String email );

    SupplierUser deleteById( Integer id );

    SupplierUser save( SupplierUser input );

}