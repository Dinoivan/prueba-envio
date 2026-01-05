package com.incloud.hcp.service;

import com.incloud.hcp.domain.RubroBien;

import java.util.List;

public interface RubroBienService {

    List<RubroBien> listarArticulosNuevos();
    List<RubroBien> listarArticulosActualizados();
}
