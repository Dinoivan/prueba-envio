package com.incloud.hcp.service;

import com.incloud.hcp.domain.LicitacionControlCambio;

import java.util.List;

/**
 * Created by Gadiel on 14/02/2018.
 */
public interface LicitacionControlCambioService {

    public List<LicitacionControlCambio> getLicitacionControlCambio(Integer idLicitacion);

}
