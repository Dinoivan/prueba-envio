package com.incloud.hcp.service;

import com.incloud.hcp.dto.DocumentoAceptacionDto;

import java.util.Date;
import java.util.List;

public interface DocumentoAceptacionNeoService {
    List<DocumentoAceptacionDto> getDocuAcepPorFecsRucPg(Date fechaInicio,
                                                         Date fechaFin,
                                                         String ruc,
                                                         String nroOC,
                                                         String nroGP,
                                                         Integer nroRegistros,
                                                         Integer paginaMostrar,
                                                         Integer tipoDocumento,
                                                         String flagPaginador
    );

}
