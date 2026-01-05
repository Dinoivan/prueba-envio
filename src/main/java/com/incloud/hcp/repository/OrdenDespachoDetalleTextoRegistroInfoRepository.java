package com.incloud.hcp.repository;

import com.incloud.hcp.domain.OrdenCompraDetalleTextoRegistroInfo;
import com.incloud.hcp.domain.almacen.OrdenDespachoDetalleTextoRegistroInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenDespachoDetalleTextoRegistroInfoRepository extends JpaRepository<OrdenDespachoDetalleTextoRegistroInfo, Integer> {


}