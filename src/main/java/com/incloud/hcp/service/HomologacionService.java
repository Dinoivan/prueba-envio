package com.incloud.hcp.service;

import com.incloud.hcp.domain.Homologacion;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.ProveedorDto;
import com.incloud.hcp.dto.ProveedorVerNotaDto;
import com.incloud.hcp.dto.homologacion.LineaComercialHomologacionDto;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.jco.proveedor.dto.ProveedorRFCResponseDto;

import java.util.List;

/**
 * Created by Administrador on 28/09/2017.
 */
public interface HomologacionService {
    Proveedor evaluarProveedor(Integer idProveedor) throws Exception;

    ProveedorRFCResponseDto evaluarProveedorDto(Integer idProveedor, Boolean comunidad) throws Exception;
    ProveedorVerNotaDto verNota(Integer idProveedor) throws PortalException;

    List<LineaComercialHomologacionDto> getListHomologacionByIdProveedor(Integer idProveedor) throws PortalException;
    List<LineaComercialHomologacionDto> getListHomologacionByIdProveedorResponder(Integer idProveedor) throws PortalException;
    List<LineaComercialHomologacionDto> guardarHomologacion(Proveedor proveedor, List<LineaComercialHomologacionDto> lineasComercialHomologacion) throws PortalException;

    List<Homologacion> getAll() throws PortalException;

    Homologacion getHomologacionById(Integer id)throws PortalException;

    Homologacion guardar(Homologacion homologacion);
    void updateHomologacion(Integer idHomologacion) throws PortalException;
    void updateHomologacionActivar(Integer idHomologacion) throws PortalException;
}
