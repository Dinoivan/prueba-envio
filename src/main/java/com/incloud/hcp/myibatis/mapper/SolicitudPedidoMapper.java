package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.bean.LicitacionResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by USER on 19/09/2017.
 */
@Mapper
@Repository
public interface SolicitudPedidoMapper {

    List<LicitacionResponse> getLicitacionBySolpedAndBien(
            @Param("codSolped") String codSolped,
            @Param("idBienServicio") int idBienServicio);

    List<LicitacionResponse> getLicitacionBySolpedPosicionAndBien(
            @Param("codSolped") String codSolped,
            @Param("posicion") String posicion,
            @Param("idBienServicio") int idBienServicio);
}
