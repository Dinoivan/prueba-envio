package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.bean.RespuestaCondicion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by USER on 25/09/2017.
 */
@Mapper
@Repository
public interface RespuestaCondicionMapper {

    List<RespuestaCondicion> getListRespuestaCondicion(@Param("idCondicion") int idCondicion);

    List<RespuestaCondicion> getListRespuestaCondicionLic(@Param("idGrupoCondicion") int idGrupoCondicion);
}
