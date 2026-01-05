package com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.service;

import com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.dto.MaterialIDto;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.dto.MaterialOutDto;

public interface JCOMaterialOutService {
    MaterialOutDto consultaMaterialRFC(MaterialIDto materialIResponse) throws Exception;
}
