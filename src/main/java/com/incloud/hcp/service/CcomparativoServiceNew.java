package com.incloud.hcp.service;

import com.incloud.hcp.domain.CcomparativoProveedor;

import java.util.List;

public interface CcomparativoServiceNew {

    boolean verificarDataSunat(List<CcomparativoProveedor> ccomparativoProveedorList) throws Exception;

}
