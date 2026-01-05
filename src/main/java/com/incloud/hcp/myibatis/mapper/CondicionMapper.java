package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.bean.Condicion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by USER on 25/09/2017.
 */
@Mapper
@Repository
public interface CondicionMapper {

    List<Condicion> getListCondicionByTipoLicitacionAndCuestionario(@Param("tipoLicitacion") int tipoLicitacion,
                                                                    @Param("tipoCuestionario") int tipoCuestionario);

    List<Condicion> getListCondicionByIdLicitacion(@Param("idLicitacion") int idLicitacion);

}
