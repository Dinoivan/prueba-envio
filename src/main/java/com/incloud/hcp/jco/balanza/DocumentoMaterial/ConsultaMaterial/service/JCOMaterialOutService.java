package com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.service;

import com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.dto.MaterialOutDto;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.ConsultaMaterial.dto.MaterialIDto;

import java.util.List;

public interface JCOMaterialOutService {
    MaterialOutDto consultaMaterialRFC(MaterialIDto materialIResponse) throws Exception;
}
