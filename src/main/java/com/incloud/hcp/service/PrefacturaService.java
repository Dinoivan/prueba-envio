package com.incloud.hcp.service;

import com.incloud.hcp.domain.DocumentoAceptacion;
import com.incloud.hcp.domain.Prefactura;
import com.incloud.hcp.dto.*;
import com.incloud.hcp.jco.prefactura.dto.PrefacturaRFCResponseDto;
import com.sap.cloud.security.xsuaa.token.Token;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.Date;
import java.util.List;


public interface PrefacturaService {

    List<Prefactura> getPrefacturaListPorFechasAndRuc(Date fechaInicio, Date fechaFin, String ruc);

    List<Prefactura> getPrefacturaListByFechasEmisionAndFechaEntradaAndRuc(Date fechaEmisionInicio, Date fechaEmisionFin,
                                                                           Date fechaEntradaInicio, Date fechaEntradaFin,
                                                                           String ruc,
                                                                           String referencia);

    List<PrefacturaAprobacionDTO> getPrefacturaList(Date fechaEmisionInicio, Date fechaEmisionFin,
                                                    Date fechaEntradaInicio, Date fechaEntradaFin,
                                                    String ruc,
                                                    String referencia,
                                                    String comprador,
                                                    String centro,
                                                    Integer idEstado) throws Exception;

    Integer ingresarNuevaPrefactura(PrefacturaDto prefacturaDto, Token token);

    List<DocumentoAceptacion> obtenerDocumentoAceptacionListByIdPrefactura(Integer idPrefactura);

    String descartarPrefactura(Integer idPrefactura, Token token);

    PrefacturaRFCResponseDto rechazarPrefactura(Integer idPrefactura, String textoRechazo,Token token);

    String enviarCorreoAnulacionPrefactura(Integer idPrefactura, String textoAnulacion,@AuthenticationPrincipal Token token);

    PrefacturaRFCResponseDto registrarPrefacturaEnSap(Integer idPrefactura, Date fechaContabilizacion, Date fechaBase, String indicadorImpuesto, Token token) throws Exception;

    String updatePathAdjuntoPrefactura(Integer idPrefactura, String cmisFileUrl, String type);
    String updatePathAdjuntoPrefacturaCf(Integer idPrefactura, String cmisFileUrl, String idCmis, String type);

    String getFileEcmPath(Integer idPrefactura, String fileType);

    String getPrefacturaPdfContent(Prefactura prefactura) throws Exception;

    PrefacturaAnuladaRespuestaDto actualizarPrefacturasAnuladasPorRangoFechas(String fechaInicio, String fechaFin, boolean actualizacionManual,Token token);

    PrefacturaAnuladaRespuestaDto actualizarMasivoPrefacturasAnuladasPorRangoFechas(Date fechaInicio, Date fechaFin, Token token);

    List<PrefacturaActualizarDto> actualizarPrefacturasRegistradasEnSap (List<Integer> idPrefacturaActualizarList, Token token);

    SXSSFWorkbook descargarListaPrefacturaExcelSXLSX(Date fechaEmisionInicio, Date fechaEmisionFin,
                                                     Date fechaEntradaInicio, Date fechaEntradaFin,
                                                     String ruc,
                                                     String referencia,
                                                     String comprador,
                                                     String centro,
                                                     Integer idEstado);

    SXSSFWorkbook descargarListaPrefacturaExcelSXLSXconFiltros(PrefacturaExcelRequestDto prefacturaExcelRequestDto);

    String actualizarPrefacturasFechaPagoVencimiento(String fechaInicioSapString, String fechaFinSapString, boolean actualizacionManual) throws Exception;
    String rechazarPrefacturasSinAdjuntos(Date fechaRecepcion, Token token);
    Boolean validarAdjuntosPrefactura(Integer idPrefactura);
}
