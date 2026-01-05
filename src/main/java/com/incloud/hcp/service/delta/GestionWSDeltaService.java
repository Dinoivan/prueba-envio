package com.incloud.hcp.service.delta;

import com.incloud.hcp.jco.comprobanteRetencion.dto.ComprobanteRetencionDto;
import com.incloud.hcp.service.GestionWSService;

public interface GestionWSDeltaService extends GestionWSService {

    String getBase64ComprobanteRetencion (ComprobanteRetencionDto comprobanteRetencionDto);
}
