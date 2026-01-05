package com.incloud.hcp.service.impl;

import com.incloud.hcp.dto.DocumentoAceptacionDto;
import com.incloud.hcp.myibatis.mapper.DocumentoAceptacionNeoMapper;
import com.incloud.hcp.service.DocumentoAceptacionNeoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class DocumentoAceptacionNeoServiceImpl implements DocumentoAceptacionNeoService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DocumentoAceptacionNeoMapper documentoAceptacionNeoMapper;

    public List<DocumentoAceptacionDto> getDocuAcepPorFecsRucPg(Date fechaInicio, Date fechaFin, String ruc, String nroOC, String nroGP, Integer nroRegistros, Integer paginaMostrar, Integer tipoDocumento,String flagPaginador){
        List<DocumentoAceptacionDto> out = this.documentoAceptacionNeoMapper.getDocuAcepPorFecsRucPg(fechaInicio, fechaFin, ruc, nroOC, nroGP, nroRegistros, paginaMostrar, tipoDocumento,flagPaginador);
        return out;
    }

}
