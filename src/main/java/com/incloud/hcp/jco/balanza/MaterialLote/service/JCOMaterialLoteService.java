package com.incloud.hcp.jco.balanza.MaterialLote.service;
import com.incloud.hcp.jco.balanza.MaterialLote.dto.MaterialLoteResponse;

import java.util.List;

public interface JCOMaterialLoteService {
    List<MaterialLoteResponse> consultaMaterialLote(String piWerks, String piLgort) throws Exception;
}
