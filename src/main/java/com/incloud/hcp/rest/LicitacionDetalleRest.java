package com.incloud.hcp.rest;

import com.incloud.hcp.domain.*;
import com.incloud.hcp.repository.LicitacionDetalleRepository;
import com.incloud.hcp.repository.LicitacionProveedorDetalleEvaluacionRepository;
import com.incloud.hcp.repository.TipoDocumentoSapRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.util.constant.ClaseDocumentoConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by USER on 24/08/2017.
 */
@RestController
@RequestMapping(value = "/api/licitacionDetalle")
public class LicitacionDetalleRest extends AppRest {

    @Autowired
    private LicitacionDetalleRepository licitacionDetalleRepository;

    @Autowired
    private TipoDocumentoSapRepository tipoDocumentoSapRepository;

    @Autowired
    private LicitacionProveedorDetalleEvaluacionRepository licitacionProveedorDetalleEvaluacionRepository;

    @RequestMapping(value = "/findByLicitacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<LicitacionDetalle>> findByLicitacion(@RequestBody Integer idLicitacion)  {
        Licitacion licitacion = new Licitacion();
        licitacion.setIdLicitacion(idLicitacion);
        List<LicitacionDetalle> lista = this.licitacionDetalleRepository.findByLicitacionOrdenado(licitacion);
        String nombreTipoDocumentoSap = "";
        Integer idTipoDocumentoSap = null;
        if (lista != null && lista.size() > 0) {
            LicitacionDetalle licitacionDetalle = lista.get(0);
            if (StringUtils.isNotBlank(licitacionDetalle.getSolicitudPedido())) {
                nombreTipoDocumentoSap = ClaseDocumentoConstant.TIPO_DOCUMENTO_OC;
                TipoDocumentoSap tipoDocumentoSap = this.tipoDocumentoSapRepository.getBySiglaTipoDocumentoSap(nombreTipoDocumentoSap);
                if (Optional.ofNullable(tipoDocumentoSap).isPresent()) {
                    idTipoDocumentoSap = tipoDocumentoSap.getId();
                }
            }
            if (StringUtils.isBlank(licitacionDetalle.getSolicitudPedido())) {
                if (Optional.ofNullable(licitacionDetalle.getBienServicio()).isPresent()) {
//                    nombreTipoDocumentoSap = ClaseDocumentoConstant.TIPO_DOCUMENTO_CM;
                    nombreTipoDocumentoSap = ClaseDocumentoConstant.TIPO_DOCUMENTO_PO;
                    TipoDocumentoSap tipoDocumentoSap = this.tipoDocumentoSapRepository.getBySiglaTipoDocumentoSap(nombreTipoDocumentoSap);
                    if (Optional.ofNullable(tipoDocumentoSap).isPresent()) {
                        idTipoDocumentoSap = tipoDocumentoSap.getId();
                    }
                }
            }
        }

        for(LicitacionDetalle bean : lista) {
//            LicitacionProveedorDetalleEvaluacion detalleEvaluacion =
//                    this.licitacionProveedorDetalleEvaluacionRepository.getByMaximoPorcentajeLicitacionDetalle(
//                            idLicitacion,
//                            bean.getIdLicitacionDetalle()
//                        );
//            if (Optional.ofNullable(detalleEvaluacion).isPresent()) {
//                Proveedor proveedor = detalleEvaluacion.getIdProveedor();
//                if (Optional.ofNullable(proveedor).isPresent()) {
//                    bean.setNombreProveedorSugerido(proveedor.getRazonSocial());
//                }
//            }
            List<LicitacionProveedorDetalleEvaluacion> detalleEvaluacionList =
                    this.licitacionProveedorDetalleEvaluacionRepository.getListaByMaximoPorcentajeLicitacionDetalleOrdenadaByRazonSocial(
                            idLicitacion,
                            bean.getIdLicitacionDetalle()
                    );
            if (detalleEvaluacionList != null && !detalleEvaluacionList.isEmpty()) {
                Proveedor proveedor = detalleEvaluacionList.get(0).getIdProveedor();
                if (Optional.ofNullable(proveedor).isPresent()) {
                    bean.setNombreProveedorSugerido(proveedor.getRazonSocial());
                }
            }
            bean.setIdTipoDocumentoSap(idTipoDocumentoSap);
            bean.setNombreTipoDocumentoSap(nombreTipoDocumentoSap);
        }
        return ResponseEntity.ok().body(lista);
    }
}
