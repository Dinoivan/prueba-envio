package com.incloud.hcp.jco.grupoArticulo.service.impl;


import com.incloud.hcp.domain.RubroBien;
import com.incloud.hcp.jco.grupoArticulo.dto.GrupoArticuloRFCResponseDto;
import com.incloud.hcp.jco.grupoArticulo.service.JCOGrupoArticuloService;
import com.incloud.hcp.jco.grupoArticulo.service.JCOGrupoArticuloServiceNew;
import com.incloud.hcp.repository.RubroBienRepository;
import com.incloud.hcp.repository.TempRubroBienRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOGrupoArticuloServiceImpl implements JCOGrupoArticuloService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TempRubroBienRepository tempRubroBienRepository;


    @Autowired
    private RubroBienRepository rubroBienRepository;

    @Autowired
    private JCOGrupoArticuloServiceNew jcoGrupoArticuloServiceNew;



    @Override
    public List<RubroBien> actualizarGrupoArticulo() throws Exception{
        logger.error("Actualizando actualizarGrupoArticulo");
        GrupoArticuloRFCResponseDto grupoArticuloRFCResponseDto = this.jcoGrupoArticuloServiceNew.getGrupoArticulo(" ");
        logger.error("lista de Grupos Articulos size" + grupoArticuloRFCResponseDto.getListaGrupoArticulo().size());
        logger.error("lista de Grupos Articulos" + grupoArticuloRFCResponseDto.getListaGrupoArticulo().toString());

        tempRubroBienRepository.saveNeworUpdateActualizados();

        List<RubroBien> listaFinal = this.rubroBienRepository.findAll();
        logger.error("lista de Grupos Articulos final" + listaFinal.size());
        return listaFinal;

    }


}
