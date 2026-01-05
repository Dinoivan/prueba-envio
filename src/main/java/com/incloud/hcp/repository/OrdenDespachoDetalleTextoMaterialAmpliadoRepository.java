package com.incloud.hcp.repository;

import com.incloud.hcp.domain.OrdenCompraDetalleTextoMaterialAmpliado;
import com.incloud.hcp.domain.almacen.OrdenDespachoDetalleTextoMaterialAmpliado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenDespachoDetalleTextoMaterialAmpliadoRepository extends JpaRepository<OrdenDespachoDetalleTextoMaterialAmpliado, Integer> {


}