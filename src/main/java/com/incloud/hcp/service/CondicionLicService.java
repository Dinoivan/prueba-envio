package com.incloud.hcp.service;

import com.incloud.hcp.bean.Condicion;
import com.sap.cloud.security.xsuaa.token.Token;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by USER on 24/09/2017.
 */
public interface CondicionLicService {

    List<Condicion> getListCondicionLicByTipoLicitacionAndTipoCuestionario(int idTipoLicitacion,
                                                                              int idTipoCuestionario);

    BigDecimal getTotalPesoByTipoCuestionario(int idTipoLicitacion, int idTipoCuestionario);

    Map deleteCondicionLicByIdCondicion(ArrayList<Integer> idCondicion);

    String saveCondicionLicitacion(Condicion obj,Token token);
}
