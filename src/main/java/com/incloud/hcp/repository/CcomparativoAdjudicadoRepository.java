package com.incloud.hcp.repository;

import com.incloud.hcp.domain.CcomparativoAdjudicado;
import com.incloud.hcp.domain.CotizacionDetalle;
import com.incloud.hcp.domain.Licitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by USER on 18/09/2017.
 */
public interface CcomparativoAdjudicadoRepository extends JpaRepository<CcomparativoAdjudicado, Integer> {

    @Query("select u from CcomparativoAdjudicado u where u.ccomparativoProveedor.idCcomparativo.licitacion = ?1 " +
            "order by u.ccomparativoProveedor.idProveedor.razonSocial, u.cotizacionDetalle.descripcion ")
    List<CcomparativoAdjudicado> findByLicitacionOrdenado(Licitacion licitacion);

    @Query("select u from CcomparativoAdjudicado u where u.ccomparativoProveedor.idCcomparativo.licitacion = ?1 " )
    List<CcomparativoAdjudicado> findByLicitacion(Licitacion licitacion);


    @Modifying
    @Query("DELETE FROM CcomparativoAdjudicado d WHERE d.ccomparativoProveedor.idCcomparativo.licitacion.idLicitacion = ?1")
    void deleteByLicitacion(Integer idLicitacion);

    @Query(" select sum(u.cantidadReal * u.precioUnitario) from CcomparativoAdjudicado u " +
            "where u.ccomparativoProveedor.idCcomparativo.licitacion.idLicitacion = ?1 " +
            "and u.ccomparativoProveedor.idProveedor.idProveedor = ?2")
    BigDecimal getSumatoriaMontoByLicitacionAndProveedor(Integer idLicitacion, Integer idProveedor);


    @Query(" select u from CcomparativoAdjudicado u " +
            "where u.ccomparativoProveedor.idCcomparativo.licitacion.idLicitacion = ?1 " +
            "and u.ccomparativoProveedor.idProveedor.idProveedor = ?2")
    List<CcomparativoAdjudicado>  findByLicitacionProveedor(Integer idLicitacion, Integer idProveedor);



    @Query(" select u.cotizacionDetalle from CcomparativoAdjudicado u " +
            "where u.ccomparativoProveedor.idCcomparativo.licitacion.idLicitacion = ?1 " +
            "and u.ccomparativoProveedor.idProveedor.idProveedor = ?2")
    List<CotizacionDetalle>  findCotizacionDetalleByLicitacionProveedor(Integer idLicitacion, Integer idProveedor);

}
