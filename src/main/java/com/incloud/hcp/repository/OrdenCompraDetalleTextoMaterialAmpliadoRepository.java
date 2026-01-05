package com.incloud.hcp.repository;

import com.incloud.hcp.domain.OrdenCompraDetalleTexto;
import com.incloud.hcp.domain.OrdenCompraDetalleTextoMaterialAmpliado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenCompraDetalleTextoMaterialAmpliadoRepository extends JpaRepository<OrdenCompraDetalleTextoMaterialAmpliado, Integer> {


}