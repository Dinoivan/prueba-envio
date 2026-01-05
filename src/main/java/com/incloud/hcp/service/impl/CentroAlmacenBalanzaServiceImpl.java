package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.balanza.CentroAlmacenBalanza;
import com.incloud.hcp.repository.CentroAlmacenBalanzaRepository;
import com.incloud.hcp.service.CentroAlmacenBalanzaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CentroAlmacenBalanzaServiceImpl implements CentroAlmacenBalanzaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private CentroAlmacenBalanzaRepository centroAlmacenBalanzaRepository;


    @Override
    public CentroAlmacenBalanza guardarActualizarCentro(CentroAlmacenBalanza centroAlmacenBalanza){
        logger.error("[guardarActualizarCentro]:Inicio");
        logger.error("[guardarActualizarCentro]:centroAlmacenBalanza:{}", centroAlmacenBalanza);
        if(centroAlmacenBalanza.getId()== null){
            logger.error("[guardarActualizarCentro]:ingreso if");
            centroAlmacenBalanza.setEstado(false);
            return centroAlmacenBalanzaRepository.save(centroAlmacenBalanza);

        }else{
            logger.error("[guardarActualizarCentro]:ingreso else");
            Optional<CentroAlmacenBalanza> centroActualizado =centroAlmacenBalanzaRepository.findById(
                    centroAlmacenBalanza.getId());
            if(centroActualizado.isPresent()){
                CentroAlmacenBalanza centro = centroActualizado.get();
                centro.setCentro(centroAlmacenBalanza.getCentro());
                centro.setBalanza(centroAlmacenBalanza.getBalanza());
                centro.setModificar(centroAlmacenBalanza.getModificar());
                centro.setAlmacen(centroAlmacenBalanza.getAlmacen());
                centro.setImpresoras(centroAlmacenBalanza.getImpresoras());
                centro.setUsuario(centroAlmacenBalanza.getUsuario());
                centro.setSociedad(centroAlmacenBalanza.getSociedad());
                centro.setEstado(Optional.ofNullable(centroAlmacenBalanza.getEstado()).orElse(true));
                return centroAlmacenBalanzaRepository.save(centro);
            }else{
                Integer idcentro = centroAlmacenBalanza.getId();
                throw new RuntimeException("No existe el centro con id:" + idcentro);
            }

        }

    }

    @Override
    public CentroAlmacenBalanza cambiarEstado(Integer centroAlmacenId) throws Exception{
        Optional<CentroAlmacenBalanza> centroOptional = centroAlmacenBalanzaRepository.findById(centroAlmacenId);
        if(!centroOptional.isPresent()){
            throw new Exception("No se encontró el centro con ID: " + centroAlmacenId);
        }
        CentroAlmacenBalanza centro = centroOptional.get();
        centro.setEstado(!centro.getEstado());
        return centroAlmacenBalanzaRepository.save(centro);
    }
}
