package com.incloud.hcp.service;

import com.incloud.hcp.bean.File64;
import com.incloud.hcp.domain.BienServicio;
import com.incloud.hcp.sap.materiales.BienServicioResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by USER on 21/09/2017.
 */
public interface BienServicioService {

    List<BienServicio> getListBienServicioByCodigoSap (String codigoSap);

    List<BienServicio> getListBienServicioByNroParte (String nroParte);

    List<BienServicio> getListBienServicioByGrupoArticulo (int idGrupoArt);

    BienServicioResponse sincronizarBienServicioByLastDate() throws Exception;

    Map<String, Object> getListBienServicioByExcel (File64 file);

}
