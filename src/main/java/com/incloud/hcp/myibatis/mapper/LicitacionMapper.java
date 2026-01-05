package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.bean.LicitacionResponse;
import com.incloud.hcp.domain.Licitacion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by USER on 29/08/2017.
 */

@Mapper
@Repository
public interface LicitacionMapper {

    List<Licitacion> getListLicitacionByCierre(
            @Param("estadoLicitacion") String estadoLicitacion,
            @Param("fechaCierreRecepcionOferta") Date fechaCierreRecepcionOferta
    );

    List<LicitacionResponse> getListLicitacionByEstado(@Param("estados") ArrayList<String> estados);

    List<LicitacionResponse> getListLicitacionByNumero(@Param("annio") Integer annio,
                                                       @Param("num") Integer num);

    List<LicitacionResponse> getListLicitacionByRuc(String ruc);

    List<LicitacionResponse> getListLicitacionByDescripcion(String descripcion);

    List<LicitacionResponse> getListLicitacionByFiltroHistorico(@Param("claseDocumento") String claseDocumento,
                                                                @Param("estadoLicitacion") String estadoLicitacion,
                                                                @Param("tipoLicitacion") String tipoLicitacion,
                                                                @Param ("tipoCuestionario") String tipoCuestionario);

    List<Licitacion> getListLicitacionrByFiltroBusqueda(
            @Param("estadoLicitacion") String estadoLicitacion,
            @Param("estadoLicitacionLectura") String estadoLicitacionLectura,
            @Param("numeroLicitacion") String numeroLicitacion,
            @Param("puntoEntrega") String puntoEntrega,
            @Param("moneda") String moneda,
            @Param("listaCentroLogistico") ArrayList<String> listaCentroLogistico,
            @Param("listaAlmacen") ArrayList<String> listaAlmacen,
            @Param("solicitudPedido") String solicitudPedido,
            @Param("codigoSap") String codigoSap,
            @Param("listaTipoLicitacion") ArrayList<String> listaTipoLicitacion,
            @Param("listaTipoCuestionario") ArrayList<String> listaTipoCuestionario,
            @Param("idProveedor") Integer idProveedor,
            @Param("numeroRUC") String numeroRUC,
            @Param("razonSocial") String razonSocial,
            @Param("listaRegion") ArrayList<String> listaRegion,
            @Param("usuarioPublicacion") String usuarioPublicacion,
            @Param("emailUsuario") String emailUsuario
    );

    List<Licitacion> getListLicitacionrByFiltroBusquedaPaginado(
            @Param("estadoLicitacion") String estadoLicitacion,
            @Param("estadoLicitacionLectura") String estadoLicitacionLectura,
            @Param("numeroLicitacion") String numeroLicitacion,
            @Param("puntoEntrega") String puntoEntrega,
            @Param("moneda") String moneda,
            @Param("listaCentroLogistico") ArrayList<String> listaCentroLogistico,
            @Param("listaAlmacen") ArrayList<String> listaAlmacen,
            @Param("solicitudPedido") String solicitudPedido,
            @Param("codigoSap") String codigoSap,
            @Param("listaTipoLicitacion") ArrayList<String> listaTipoLicitacion,
            @Param("listaTipoCuestionario") ArrayList<String> listaTipoCuestionario,
            @Param("idProveedor") Integer idProveedor,
            @Param("numeroRUC") String numeroRUC,
            @Param("razonSocial") String razonSocial,
            @Param("listaRegion") ArrayList<String> listaRegion,
            @Param("usuarioPublicacion") String usuarioPublicacion,
            @Param("emailUsuario") String emailUsuario,
            @Param("nroRegistros") Integer nroRegistros,
            @Param("paginaMostrar") Integer paginaMostrar
    );

    List<Licitacion> getListLicitacionrByFiltroBusquedaEstadoTerminal(
            @Param("numeroLicitacion") String numeroLicitacion,
            @Param("puntoEntrega") String puntoEntrega,
            @Param("moneda") String moneda,
            @Param("listaCentroLogistico") ArrayList<String> listaCentroLogistico,
            @Param("listaAlmacen") ArrayList<String> listaAlmacen,
            @Param("solicitudPedido") String solicitudPedido,
            @Param("codigoSap") String codigoSap,
            @Param("listaTipoLicitacion") ArrayList<String> listaTipoLicitacion,
            @Param("listaTipoCuestionario") ArrayList<String> listaTipoCuestionario,
            @Param("idProveedor") Integer idProveedor,
            @Param("numeroRUC") String numeroRUC,
            @Param("razonSocial") String razonSocial,
            @Param("listaRegion") ArrayList<String> listaRegion,
            @Param("usuarioPublicacion") String usuarioPublicacion,
            @Param("emailUsuario") String emailUsuario

    );

    List<Licitacion> getListLicitacionrByFiltroBusquedaEstadoTerminalPaginado(
            @Param("numeroLicitacion") String numeroLicitacion,
            @Param("puntoEntrega") String puntoEntrega,
            @Param("moneda") String moneda,
            @Param("listaCentroLogistico") ArrayList<String> listaCentroLogistico,
            @Param("listaAlmacen") ArrayList<String> listaAlmacen,
            @Param("solicitudPedido") String solicitudPedido,
            @Param("codigoSap") String codigoSap,
            @Param("listaTipoLicitacion") ArrayList<String> listaTipoLicitacion,
            @Param("listaTipoCuestionario") ArrayList<String> listaTipoCuestionario,
            @Param("idProveedor") Integer idProveedor,
            @Param("numeroRUC") String numeroRUC,
            @Param("razonSocial") String razonSocial,
            @Param("listaRegion") ArrayList<String> listaRegion,
            @Param("usuarioPublicacion") String usuarioPublicacion,
            @Param("emailUsuario") String emailUsuario,
            @Param("nroRegistros") Integer nroRegistros,
            @Param("paginaMostrar") Integer paginaMostrar

    );

    List<Licitacion> getListLicitacionrByFiltroBusquedaAdjudicadoProveedor(
            @Param("numeroLicitacion") String numeroLicitacion,
            @Param("puntoEntrega") String puntoEntrega,
            @Param("moneda") String moneda,
            @Param("listaCentroLogistico") ArrayList<String> listaCentroLogistico,
            @Param("listaAlmacen") ArrayList<String> listaAlmacen,
            @Param("solicitudPedido") String solicitudPedido,
            @Param("codigoSap") String codigoSap,
            @Param("listaTipoLicitacion") ArrayList<String> listaTipoLicitacion,
            @Param("listaTipoCuestionario") ArrayList<String> listaTipoCuestionario,
            @Param("idProveedor") Integer idProveedor,
            @Param("numeroRUC") String numeroRUC,
            @Param("razonSocial") String razonSocial,
            @Param("listaRegion") ArrayList<String> listaRegion,
            @Param("emailUsuario") String emailUsuario
    );

    List<Licitacion> getListLicitacionrByFiltroBusquedaAdjudicadoProveedorPaginado(
            @Param("numeroLicitacion") String numeroLicitacion,
            @Param("puntoEntrega") String puntoEntrega,
            @Param("moneda") String moneda,
            @Param("listaCentroLogistico") ArrayList<String> listaCentroLogistico,
            @Param("listaAlmacen") ArrayList<String> listaAlmacen,
            @Param("solicitudPedido") String solicitudPedido,
            @Param("codigoSap") String codigoSap,
            @Param("listaTipoLicitacion") ArrayList<String> listaTipoLicitacion,
            @Param("listaTipoCuestionario") ArrayList<String> listaTipoCuestionario,
            @Param("idProveedor") Integer idProveedor,
            @Param("numeroRUC") String numeroRUC,
            @Param("razonSocial") String razonSocial,
            @Param("listaRegion") ArrayList<String> listaRegion,
            @Param("emailUsuario") String emailUsuario,
            @Param("nroRegistros") Integer nroRegistros,
            @Param("paginaMostrar") Integer paginaMostrar
    );


    List<Licitacion> getListLicitacionByFiltro(
            @Param("listaEstadoLicitacion") ArrayList<String> listaEstadoLicitacion,
            @Param("numeroLicitacion") Integer numeroLicitacion,
            @Param("annioLicitacion") Integer annioLicitacion,
            @Param("puntoEntrega") String puntoEntrega,
            @Param("moneda") String moneda,
            @Param("listaCentroLogistico") ArrayList<String> listaCentroLogistico,
            @Param("listaAlmacen") ArrayList<String> listaAlmacen,
            @Param("solicitudPedido") String solicitudPedido,
            @Param("codigoSap") String codigoSap,
            @Param("listaClaseDocumento") ArrayList<String> listaClaseDocumento,
            @Param("listaTipoLicitacion") ArrayList<String> listaTipoLicitacion,
            @Param("listaTipoCuestionario") ArrayList<String> listaTipoCuestionario,
            @Param("idProveedor") String idProveedor,
            @Param("numeroRUC") String numeroRUC,
            @Param("razonSocial") String razonSocial,
            @Param("descripcion") String descripcion,
            @Param("listaRegion") ArrayList<String> listaRegion,
            @Param("emailUsuario") String emailUsuario
    );

    List<Licitacion> getListLicitacionByFiltroPaginado(
            @Param("listaEstadoLicitacion") ArrayList<String> listaEstadoLicitacion,
            @Param("numeroLicitacion") Integer numeroLicitacion,
            @Param("annioLicitacion") Integer annioLicitacion,
            @Param("puntoEntrega") String puntoEntrega,
            @Param("moneda") String moneda,
            @Param("listaCentroLogistico") ArrayList<String> listaCentroLogistico,
            @Param("listaAlmacen") ArrayList<String> listaAlmacen,
            @Param("solicitudPedido") String solicitudPedido,
            @Param("codigoSap") String codigoSap,
            @Param("listaClaseDocumento") ArrayList<String> listaClaseDocumento,
            @Param("listaTipoLicitacion") ArrayList<String> listaTipoLicitacion,
            @Param("listaTipoCuestionario") ArrayList<String> listaTipoCuestionario,
            @Param("idProveedor") String idProveedor,
            @Param("numeroRUC") String numeroRUC,
            @Param("razonSocial") String razonSocial,
            @Param("descripcion") String descripcion,
            @Param("listaRegion") ArrayList<String> listaRegion,
            @Param("nroRegistros") Integer nroRegistros,
            @Param("indEjecucionSapOk") String indEjecucionSapOk,
            @Param("emailUsuario") String emailUsuario,
            @Param("paginaMostrar") Integer paginaMostrar
    );

    Integer countByFiltroBusquedaAdjudicadoProveedor(
            @Param("idProveedor") Integer idProveedor
    );

}
