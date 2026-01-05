package com.incloud.hcp.jco.comprobantePago.service;

import com.incloud.hcp.jco.comprobantePago.dto.ComprobantePagoDto;
import java.util.List;


public interface JCOComprobantePagoService {

    List<ComprobantePagoDto> extraerComprobantePagoListRFC(String fechaInicio, String fechaFin, String numeroComprobantePago, String ruc) throws Exception;

}
