package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.ClaseDocumento;
import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionDetalle;
import com.incloud.hcp.repository.ClaseDocumentoRepository;
import com.incloud.hcp.repository.LicitacionDetalleRepository;
import com.incloud.hcp.repository.LicitacionRepository;
import com.incloud.hcp.service.ClaseDocumentoService;
import com.incloud.hcp.util.constant.ClaseDocumentoConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by USER on 24/08/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class ClaseDocumentoServiceImpl implements ClaseDocumentoService {

    @Autowired
    private LicitacionRepository licitacionRepository;

    @Autowired
    private LicitacionDetalleRepository licitacionDetalleRepository;

    @Autowired
    private ClaseDocumentoRepository claseDocumentoRepository;

    @Override
    public List<ClaseDocumento> getListClaseDocumentoSolped() {
        List<ClaseDocumento> listaClaseDocSolped = new ArrayList<ClaseDocumento>();
        listaClaseDocSolped = claseDocumentoRepository.findByNivel(ClaseDocumentoConstant.NIVEL_SOLPED);
        return listaClaseDocSolped;
    }


    public List<ClaseDocumento> getListClaseDocumentoOrdenCompra(Integer idClaseDocumento) {
        List<ClaseDocumento> listaClaseDoc = new ArrayList<ClaseDocumento>();
        listaClaseDoc = claseDocumentoRepository.findByNivelAndIdPadre(ClaseDocumentoConstant.NIVEL_OC, idClaseDocumento);
        return listaClaseDoc;
    }


    public List<ClaseDocumento> getListClaseDocumentoLicitacion(Integer idLicitacion) throws Exception {
        List<ClaseDocumento> listaClaseDoc = new ArrayList<ClaseDocumento>();
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        if (!Optional.ofNullable(licitacion).isPresent()) {
            return listaClaseDoc;
        }
        List<LicitacionDetalle> licitacionDetalleList = this.licitacionDetalleRepository.findByLicitacion(licitacion);
        if (licitacionDetalleList == null || licitacionDetalleList.size() <= 0) {
            return listaClaseDoc;
        }
        LicitacionDetalle licitacionDetalle = licitacionDetalleList.get(0);
        if (StringUtils.isNotBlank(licitacionDetalle.getSolicitudPedido())) {
            listaClaseDoc = claseDocumentoRepository.
                    findByNivelAndTipoClaseDocumentoOrderByDescripcion(
                            ClaseDocumentoConstant.NIVEL_OC,
                            ClaseDocumentoConstant.TIPO_DOCUMENTO_OC);
            return listaClaseDoc;
        }
        if (StringUtils.isBlank(licitacionDetalle.getSolicitudPedido())) {
            if (Optional.ofNullable(licitacionDetalle.getBienServicio()).isPresent()) {
                listaClaseDoc = claseDocumentoRepository.
                        findByNivelAndTipoClaseDocumentoOrderByDescripcion(
                                ClaseDocumentoConstant.NIVEL_OC,
                                ClaseDocumentoConstant.TIPO_DOCUMENTO_CM);
                return listaClaseDoc;
            }
        }
        return listaClaseDoc;

    }


    @Override
    public List<ClaseDocumento> getListClaseDocumentoOC(int claseDocSolped) {
        List<ClaseDocumento> listaClaseDocOC = new ArrayList<ClaseDocumento>();
        listaClaseDocOC = claseDocumentoRepository.findByNivelAndIdPadre(ClaseDocumentoConstant.NIVEL_OC, claseDocSolped);
        return listaClaseDocOC;
    }

    @Override
    public List<ClaseDocumento> findByNivel(Integer nivel) {
        return claseDocumentoRepository.findByNivel(nivel);
    }

}
