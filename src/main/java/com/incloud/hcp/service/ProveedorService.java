package com.incloud.hcp.service;

import com.incloud.hcp.bean.ProveedorCustom;
import com.incloud.hcp.bean.ProveedorFiltro;
import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.*;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.rest.bean.ProveedorDatosGeneralesDTO;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrador on 29/08/2017.
 */
public interface ProveedorService {

    void eliminarDatosProveedor(Integer idProveedor);

    Proveedor getProveedorById(Integer idProveedor);

    Proveedor getProveedorByIdHcp(String idHcp) throws PortalException;

    Proveedor getProveedorByRuc(String ruc);
    ProveedorDto getProveedorDtoByRuc(String ruc);
    ProveedorDto getProveedorDtoByRucResponder(String ruc);

    Proveedor save(Proveedor documento);

    ProveedorDto getProveedorDtoByIdHcp(String idHcp) throws PortalException;

    ProveedorDto getProveedorDtoById(Integer idProveedor) throws PortalException;

    ProveedorDto saveDto(ProveedorDto dto) throws Exception;

    ProveedorDto toDto(Proveedor proveedor) throws PortalException;

    List<LineaComercialDto> getListLineaComercialByIdProveedor(Integer idProveedor);

    List<ProveedorCatalogoDto> getListCatalogosByIdProveedor(Integer idProveedor);

    List<Proveedor> getListProveedor();

    List<ProveedorCustom> getListProveedorByFiltro(ProveedorFiltro filtro);
    List<ProveedorCustom> getListProveedorByFiltroPaginado(ProveedorFiltro filtro);
    List<ProveedorCustom> getListProveedorByFiltroValidacion(UserSession userSession, ProveedorFiltro filtro);
    List<ProveedorCustom> getListProveedorByFiltroLicitacion(ProveedorFiltro filtro);
    List<ProveedorCustom> getListProveedorByFiltroLicitacionPaginado(ProveedorFiltro filtro);

    List<ProveedorCustom> getListProveedorByRuc(String ruc);

    List<ProveedorCustom> getListProveedorSinHcpID();

    public List<ProveedorDatosGeneralesDTO> getProveedorDatosGenerales(
            String fechaCreacionIni, String fechaCreacionFin) throws PortalException;

    ProveedorDto sendToSap(Integer idProveedor) throws PortalException;

    void evaluarDataMaestra(Integer idProveedor) throws PortalException ;
    void rechazarDataMaestra(Integer idProveedor) throws PortalException ;
    Integer updateProveedorIDHCP(ListProveedorHCP listProveedorHCP);
    //carga Excel para la creacion en la BD
    public List<ProveedorXLSXDTO> uploadExcel(InputStream in);

    String saveEmail(String ruc, String email);

}
