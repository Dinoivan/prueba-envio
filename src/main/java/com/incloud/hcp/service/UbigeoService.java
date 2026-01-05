package com.incloud.hcp.service;

import com.incloud.hcp.bean.Distrito;
import com.incloud.hcp.bean.Pais;
import com.incloud.hcp.bean.Provincia;
import com.incloud.hcp.bean.Region;
import com.incloud.hcp.domain.Ubigeo;

import java.util.List;

/**
 * Created by Administrador on 21/08/2017.
 */

public interface UbigeoService {

    List<Pais> getListPaisAll();

    List<Region> getListRegion(String codigoPais);

    List<Provincia> getListProvincia(String codigoRegion);

    List<Distrito> getListDistrito(String codigoProvincia);

    Ubigeo getUbigeoById(Integer idUbigeo);

    Ubigeo getUbigeoByCodigo(String codigo);

    List<Ubigeo> getListUbigeoByParent(List<String> ids);

    List<Ubigeo> getListUbigeoByPadre(Integer idPadre);
}
