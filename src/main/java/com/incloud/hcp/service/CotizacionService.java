package com.incloud.hcp.service;

import com.incloud.hcp.domain.Cotizacion;
import com.incloud.hcp.domain.LicitacionProveedor;
import com.incloud.hcp.dto.CotizacionDto;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.rest.bean.CotizacionEnviarCotizacionDTO;
import com.incloud.hcp.rest.bean.CotizacionGrabarDTO;
import com.incloud.hcp.rest.bean.PrecioCotizacionDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by USER on 13/10/2017.
 */
public interface CotizacionService {

    CotizacionGrabarDTO save(CotizacionGrabarDTO dto) throws PortalException;

    CotizacionEnviarCotizacionDTO enviarCotizacion(CotizacionEnviarCotizacionDTO dto) throws PortalException;

    CotizacionEnviarCotizacionDTO noParticiparCotizacion(CotizacionEnviarCotizacionDTO dto) throws PortalException;

    List<Cotizacion> getListCotizacionByLicitacion(Integer idLicitacion);

    Cotizacion getCotizacionByProveedorandLicitacion(Integer idLicitacion,Integer idProveedor);

    CotizacionDto getCotizacionDtoByProveedorandLicitacion(Integer idLicitacion, Integer idProveedor);

    LicitacionProveedor siParticiparCotizacion(Integer idLicitacion, Integer idProveedor) throws Exception;

    List<PrecioCotizacionDTO> findByCotizacionDetalleLicitacionProveedor(Map<String, Object> json) throws Exception;

}
