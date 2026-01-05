package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.domain.Cotizacion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by USER on 03/01/2018.
 */
@Mapper
@Repository
public interface CotizacionMapper {

    List<Cotizacion> getListCotizacionByLicitacion(@Param("idLicitacion") Integer idLicitacion);
}