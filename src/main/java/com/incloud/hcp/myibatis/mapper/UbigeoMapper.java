package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.domain.Ubigeo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrador on 21/08/2017.
 */
@Mapper
@Repository
public interface UbigeoMapper {

    List<Ubigeo> getListUbigeoByCodigoParent(String codigo);

    List<Ubigeo> getListUbigeoByNivel(int nivel);

    Ubigeo getUbigeoByCodigo(String codigo);

    Ubigeo getUbigeoByIdUbigeo(Integer idUbigeo);

    List<Ubigeo> getListUbigeoByParent(@Param("ids") List<String> ids);

    List<Ubigeo> getListUbigeoByPadre(@Param("idPadre") Integer idPadre);
}
