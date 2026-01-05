package com.incloud.hcp.service.impl;

import com.google.gson.Gson;
import com.incloud.hcp.config.SystemLoggedUser;
import com.incloud.hcp.config.excel.ExcelDefault;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.dto.*;
import com.incloud.hcp.enums.*;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.jco.comprobantePago.dto.ComprobantePagoDto;
import com.incloud.hcp.jco.comprobantePago.service.JCOComprobantePagoService;
import com.incloud.hcp.jco.prefactura.dto.*;
import com.incloud.hcp.jco.prefactura.service.JCOHojaServicioService;
import com.incloud.hcp.jco.prefactura.service.JCOPrefacturaService;
import com.incloud.hcp.pdf.PdfGeneratorFactory;
import com.incloud.hcp.pdf.bean.PrefacturaItemPdfDto;
import com.incloud.hcp.pdf.bean.PrefacturaPdfDto;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.service.LogTransaccionService;
import com.incloud.hcp.service.OrdenCompraService;
import com.incloud.hcp.service.PrefacturaService;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.notificacion.ProveedorRegistradaRechazadaPFNotificacion;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.StrUtils;
import com.incloud.hcp.util.Utils;
import com.sap.cloud.security.xsuaa.token.Token;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PrefacturaServiceImpl implements PrefacturaService {

    private PrefacturaRepository prefacturaRepository;
    private PrefacturaDocumentoAceptacionRepository prefacturaDocumentoAceptacionRepository;
    private DocumentoAceptacionRepository documentoAceptacionRepository;
    private JCOPrefacturaService jcoPrefacturaService;
    private JCOHojaServicioService jcoHojaServicioService;
    private LogTransaccionService logTransaccionService;
    private SystemLoggedUser systemLoggedUser;
    private UsuarioRepository usuarioRepository;
    private ProveedorRegistradaRechazadaPFNotificacion proveedorRegistradaRechazadaPFNotificacion;
    private ProveedorService proveedorService;
    private JCOComprobantePagoService jcoComprobantePagoService;
    private OrdenCompraService ordenCompraService;
    private LogTransaccionRepository logTransaccionRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String PREFACTURA_MENSAJE_GENERICO = "La prefactura no puede ser %s porque %s.";

    @Value("${cfg.folder.ecm}")
    private String folderName;

    @Autowired
    public PrefacturaServiceImpl(PrefacturaRepository prefacturaRepository,
                                 PrefacturaDocumentoAceptacionRepository prefacturaDocumentoAceptacionRepository,
                                 DocumentoAceptacionRepository documentoAceptacionRepository,
                                 JCOPrefacturaService jcoPrefacturaService,
                                 JCOHojaServicioService jcoHojaServicioService,
                                 LogTransaccionService logTransaccionService,
                                 SystemLoggedUser systemLoggedUser,
                                 UsuarioRepository usuarioRepository,
                                 ProveedorRegistradaRechazadaPFNotificacion proveedorRegistradaRechazadaPFNotificacion,
                                 ProveedorService proveedorService,
                                 JCOComprobantePagoService jcoComprobantePagoService,
                                 OrdenCompraService ordenCompraService,
                                 LogTransaccionRepository logTransaccionRepository) {
        this.prefacturaRepository = prefacturaRepository;
        this.prefacturaDocumentoAceptacionRepository = prefacturaDocumentoAceptacionRepository;
        this.documentoAceptacionRepository = documentoAceptacionRepository;
        this.jcoPrefacturaService = jcoPrefacturaService;
        this.jcoHojaServicioService = jcoHojaServicioService;
        this.logTransaccionService = logTransaccionService;
        this.systemLoggedUser = systemLoggedUser;
        this.usuarioRepository = usuarioRepository;
        this.proveedorRegistradaRechazadaPFNotificacion = proveedorRegistradaRechazadaPFNotificacion;
        this.proveedorService = proveedorService;
        this.jcoComprobantePagoService = jcoComprobantePagoService;
        this.ordenCompraService = ordenCompraService;
        this.logTransaccionRepository = logTransaccionRepository;
    }


    @Override
    public List<Prefactura> getPrefacturaListPorFechasAndRuc(Date fechaInicio, Date fechaFin, String ruc) {
        if (ruc == null || ruc.isEmpty()) {
            return prefacturaRepository.getPrefacturaByFechaRegistroBetween(fechaInicio, fechaFin);
        } else {
            return prefacturaRepository.getPrefacturaByFechaRegistroBetweenAndProveedorRuc(fechaInicio, fechaFin, ruc);
        }
    }

    @Override
    public List<Prefactura> getPrefacturaListByFechasEmisionAndFechaEntradaAndRuc(Date fechaEmisionInicio, Date fechaEmisionFin,
                                                                                  Date fechaEntradaInicio, Date fechaEntradaFin,
                                                                                  String ruc,
                                                                                  String referencia) {
        return prefacturaRepository.getPrefacturaByFechaEmisionBetweenFechaEntradaBetweenAndProveedorRuc(fechaEmisionInicio, fechaEmisionFin, fechaEntradaInicio, DateUtils.utilDateAtEndOfDay(fechaEntradaFin), ruc, referencia);
    }

    @Override
    public List<PrefacturaAprobacionDTO> getPrefacturaList(Date fechaEmisionInicio, Date fechaEmisionFin,
                                                           Date fechaEntradaInicio, Date fechaEntradaFin,
                                                           String ruc,
                                                           String referencia,
                                                           String comprador,
                                                           String centro,
                                                           Integer idEstado) throws Exception {
        if (comprador != null)
            comprador = "%" + comprador + "%";

        if (centro != null)
            centro = "%" + centro + "%";

        List<Prefactura> lstPrefactura = new ArrayList<>();
        List<PrefacturaAprobacionDTO> lstPrefacturaAprobacionDTO = new ArrayList<>();
        lstPrefactura = prefacturaRepository.getPrefacturaByAllFilters(fechaEmisionInicio, fechaEmisionFin, fechaEntradaInicio, DateUtils.utilDateAtEndOfDay(fechaEntradaFin), ruc, referencia, comprador, centro, idEstado);
//        logger.error("<--LOG_MC-->lstPrefactura::"+lstPrefactura);
        String fechaInicioSapString = fechaEmisionInicio != null ? DateUtils.utilDateToSapString(fechaEmisionInicio) : "";
        String fechaFinSapString = fechaEmisionFin != null ? DateUtils.utilDateToSapString(fechaEmisionFin) : "";

        if(fechaInicioSapString.equals("")){
            fechaInicioSapString = fechaEntradaInicio != null ? DateUtils.utilDateToSapString(fechaEntradaInicio) : "";
        }
        if(fechaFinSapString.equals("")){
            fechaFinSapString = fechaEntradaFin != null ? DateUtils.utilDateToSapString(fechaEntradaFin) : "";
        }

        List<ComprobantePagoDto> comprobantePagoDtoList = new ArrayList<>();
        comprobantePagoDtoList = jcoComprobantePagoService.extraerComprobantePagoListRFC(fechaInicioSapString, fechaFinSapString, "", "");
//        logger.error("<--LOG_MC-->comprobantePagoDtoList::"+comprobantePagoDtoList);

        for(Prefactura it:lstPrefactura){
            PrefacturaAprobacionDTO prefacturaAprobacionDTO = new PrefacturaAprobacionDTO();
            prefacturaAprobacionDTO.setId(it.getId());
            prefacturaAprobacionDTO.setEstadoPrefactura(it.getEstadoPrefactura());
            prefacturaAprobacionDTO.setIdEstadoPrefactura(it.getIdEstadoPrefactura());
            prefacturaAprobacionDTO.setSociedad(it.getSociedad());
            prefacturaAprobacionDTO.setCodigoSociedad(it.getCodigoSociedad());
            prefacturaAprobacionDTO.setProveedorRuc(it.getProveedorRuc());
            prefacturaAprobacionDTO.setProveedorRazonSocial(it.getProveedorRazonSocial());
            prefacturaAprobacionDTO.setFechaEmision(it.getFechaEmision());
            prefacturaAprobacionDTO.setFechaContabilizacion(it.getFechaContabilizacion());
            prefacturaAprobacionDTO.setFechaBase(it.getFechaBase());
            prefacturaAprobacionDTO.setIndicadorImpuesto(it.getIndicadorImpuesto());
            prefacturaAprobacionDTO.setReferencia(it.getReferencia());
            prefacturaAprobacionDTO.setObservaciones(it.getObservaciones());
            prefacturaAprobacionDTO.setCadenaNumerosOrdenCompra(it.getCadenaNumerosOrdenCompra());
            prefacturaAprobacionDTO.setCadenaNumerosGuia(it.getCadenaNumerosGuia());
            prefacturaAprobacionDTO.setCodigoSap(it.getCodigoSap());
            prefacturaAprobacionDTO.setEjercicio(it.getEjercicio());
            prefacturaAprobacionDTO.setNumeroDocumentoContable(it.getNumeroDocumentoContable());
            prefacturaAprobacionDTO.setCodigoMondeda(it.getCodigoMondeda());
            prefacturaAprobacionDTO.setSubTotal(it.getSubTotal());
            prefacturaAprobacionDTO.setIgv(it.getIgv());
            prefacturaAprobacionDTO.setTotal(it.getTotal());
            prefacturaAprobacionDTO.setFechaRecepcion(it.getFechaRecepcion());
            prefacturaAprobacionDTO.setFechaDescarte(it.getFechaDescarte());
            prefacturaAprobacionDTO.setFechaRegistroSap(it.getFechaRegistroSap());
            prefacturaAprobacionDTO.setUsuarioRegistroSap(it.getUsuarioRegistroSap());
            prefacturaAprobacionDTO.setXmlEcmPath(it.getXmlEcmPath());
            prefacturaAprobacionDTO.setXmlEcmPathCf(it.getXmlEcmPathCf());
            prefacturaAprobacionDTO.setArchivoIdXmlCf(it.getArchivoIdXMLCf());
            prefacturaAprobacionDTO.setPdfEcmPath(it.getPdfEcmPath());
            prefacturaAprobacionDTO.setPdfEcmPathCf(it.getPdfEcmPathCf());
            prefacturaAprobacionDTO.setArchivoIdPdfCf(it.getArchivoIdPdfCf());
            prefacturaAprobacionDTO.setCentro(it.getCentro());
            prefacturaAprobacionDTO.setUsuarioComprador(it.getUsuarioComprador());
            prefacturaAprobacionDTO.setMotivoRechazo(it.getMotivoRechazo());
/*
//        logger.error("<--LOG_MC-->::4");
            for(ComprobantePagoDto itx:comprobantePagoDtoList){
//        logger.error("<--LOG_MC-->::5");
                if(itx.getNumeroDocumentoContable() != null) {
//        logger.error("<--LOG_MC-->itx.getNumeroDocumentoContable()::6"+itx.getNumeroDocumentoContable());
                    if (!itx.getNumeroDocumentoContable().equals("")) {
//        logger.error("<--LOG_MC-->prefacturaAprobacionDTO.getNumeroDocumentoContable().equals(itx.getNumeroDocumentoContable())::7"+prefacturaAprobacionDTO.getNumeroDocumentoContable()+"__"+itx.getNumeroDocumentoContable());
                        if (prefacturaAprobacionDTO.getNumeroDocumentoContable() != null && prefacturaAprobacionDTO.getNumeroDocumentoContable().equals(itx.getNumeroDocumentoContable())) {
//        logger.error("<--LOG_MC-->::8");
                            prefacturaAprobacionDTO.setFechaPago(itx.getFechaRealPago());
//        logger.error("<--LOG_MC-->::9");
                            prefacturaAprobacionDTO.setFechaVencimiento(itx.getFechaBase());
//        logger.error("<--LOG_MC-->::10");
                        }
                    }
                }
            }*/
            lstPrefacturaAprobacionDTO.add(prefacturaAprobacionDTO);
        }


        for(PrefacturaAprobacionDTO prefacturaAprobacionDTOIt:lstPrefacturaAprobacionDTO){
            //Mejora 3 :: Item 7
//            logger.error("<--MC_LOG-->:prefacturaAprobacionDTOIt:"+prefacturaAprobacionDTOIt.getId());
            for(ComprobantePagoDto itx:comprobantePagoDtoList){
//            logger.error("<--MC_LOG-->:getNumeroComprobantePago:"+itx.getNumeroComprobantePago());
//            logger.error("<--MC_LOG-->:setFechaPago-getFechaRealPago:"+itx.getFechaRealPago());
//            logger.error("<--MC_LOG-->:setFechaVencimiento-getFechaBase:"+itx.getFechaBase());
                if(itx.getNumeroDocumentoContable() != null) {
                    if (!itx.getNumeroDocumentoContable().equals("")) {
                        if (prefacturaAprobacionDTOIt.getNumeroDocumentoContable() != null && prefacturaAprobacionDTOIt.getNumeroDocumentoContable().equals(itx.getNumeroDocumentoContable())) {
                            prefacturaAprobacionDTOIt.setFechaPago(itx.getFechaRealPago());
                            prefacturaAprobacionDTOIt.setFechaVencimiento(itx.getFechaBase());
                        }
                    }
                }
            }

            //Mejora 3 :: Item 3-5
            if(prefacturaAprobacionDTOIt.getIdEstadoPrefactura() == 2){
                prefacturaAprobacionDTOIt.setUsuarioUltimoEstado("");
                prefacturaAprobacionDTOIt.setFechaUltimoEstado(prefacturaAprobacionDTOIt.getFechaRegistroSap());
                if(prefacturaAprobacionDTOIt.getFechaRegistroSap()!=null)
                    prefacturaAprobacionDTOIt.setUsuarioUltimoEstado(prefacturaAprobacionDTOIt.getUsuarioRegistroSap());
            }else if(prefacturaAprobacionDTOIt.getIdEstadoPrefactura() == 3) {
                prefacturaAprobacionDTOIt.setUsuarioUltimoEstado("");
                prefacturaAprobacionDTOIt.setFechaUltimoEstado(prefacturaAprobacionDTOIt.getFechaDescarte());
                prefacturaAprobacionDTOIt.setUsuarioUltimoEstado(logTransaccionService.getUsuarioTransaccionBD("BD_MODIFICAR","PREFACTURA",prefacturaAprobacionDTOIt.getId()));
            }else if(prefacturaAprobacionDTOIt.getIdEstadoPrefactura() == 5) {
                prefacturaAprobacionDTOIt.setUsuarioUltimoEstado("");
                prefacturaAprobacionDTOIt.setUsuarioUltimoEstado(prefacturaAprobacionDTOIt.getUsuarioRegistroSap());
                prefacturaAprobacionDTOIt.setFechaUltimoEstado(prefacturaAprobacionDTOIt.getFechaDescarte());
            }else{
                prefacturaAprobacionDTOIt.setUsuarioUltimoEstado("");
                prefacturaAprobacionDTOIt.setUsuarioUltimoEstado(prefacturaAprobacionDTOIt.getUsuarioRegistroSap());
                prefacturaAprobacionDTOIt.setFechaUltimoEstado(prefacturaAprobacionDTOIt.getFechaRegistroSap());
            }

            //Mejora 3 :: Item 6
            OrdenCompra oc = ordenCompraService.getOrdenCompraByNOCompra(prefacturaAprobacionDTOIt.getCadenaNumerosOrdenCompra());
            if(oc != null){
                prefacturaAprobacionDTOIt.setCondicionPago(oc.getCondicionPago()+"-"+oc.getCondicionPagoDescripcion());
            }

        }

        return lstPrefacturaAprobacionDTO;
    }

    @Override
    public Integer ingresarNuevaPrefactura(PrefacturaDto prefacturaDto,Token token) {
        Gson gson = new Gson();
        List<Prefactura> prefacturaList = new ArrayList<>();

        try {
            String referencia = this.completarReferenciaNotacionSunat(prefacturaDto.getReferencia());
            List<Integer> idEstadoPrefacturaList = Arrays.asList(PrefacturaEstadoEnum.ENVIADA.getId(), PrefacturaEstadoEnum.REGISTRADA.getId(), PrefacturaEstadoEnum.REGISTRO_MIRO.getId());
            prefacturaList = prefacturaRepository.getPrefacturaListByRucAndReferenciaAndIdEstadoList(prefacturaDto.getProveedorRuc(), referencia, idEstadoPrefacturaList);

            if (prefacturaList.size() > 0) {
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_CREAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefacturaDto), null,
                        "ERROR", "Ya existe actualmente por lo menos una prefactura en estado ENVIADA o REGISTRADA con la referencia " + referencia + " con el RUC " + prefacturaDto.getProveedorRuc() + ". [IdPrefactura: " + prefacturaList.get(0).getId() + "]", false,token);
                return -422;
            }

            Prefactura prefactura = new Prefactura();
            prefactura.setIdEstadoPrefactura(PrefacturaEstadoEnum.ENVIADA.getId());
            prefactura.setCodigoSociedad(prefacturaDto.getSociedad());
            prefactura.setProveedorRuc(prefacturaDto.getProveedorRuc());
            prefactura.setProveedorRazonSocial(prefacturaDto.getProveedorRazonSocial());
            prefactura.setFechaEmision(prefacturaDto.getFechaEmision());
//            prefactura.setFechaContabilizacion(prefacturaDto.getFechaContabilizacion());
//            prefactura.setFechaBase(prefacturaDto.getFechaBase());
            prefactura.setIndicadorImpuesto(prefacturaDto.getIndicadorImpuesto());
            prefactura.setReferencia(referencia);
            prefactura.setObservaciones(prefacturaDto.getObservaciones());
            prefactura.setCadenaNumerosOrdenCompra(prefacturaDto.getCadenaNumerosOrdenCompra());
            prefactura.setCadenaNumerosGuia(prefacturaDto.getCadenaNumerosGuia());
            prefactura.setCodigoMondeda(prefacturaDto.getCodigoMoneda());
            prefactura.setSubTotal(new BigDecimal(prefacturaDto.getSubTotal()));
            prefactura.setIgv(new BigDecimal(prefacturaDto.getIgv()));
            prefactura.setTotal(new BigDecimal(prefacturaDto.getTotal()));
            prefactura.setFechaRecepcion(DateUtils.getCurrentTimestamp());
            prefactura.setCentro(prefacturaDto.getCentro());
            prefactura.setUsuarioComprador(prefacturaDto.getUsuarioComprador());

            List<Integer> idDocumentoAceptacionList = prefacturaDto.getIdDocumentoAceptacionList();
            List<DocumentoAceptacion> docAceptacionNoActivoList = new ArrayList<>();

            for (Integer idDoc : idDocumentoAceptacionList) {
                DocumentoAceptacion documentoAceptacion = documentoAceptacionRepository.getOne(idDoc);

                if (documentoAceptacion.getIdEstadoDocumentoAceptacion().compareTo(DocumentoAceptacionEstadoEnum.ACTIVO.getId()) != 0) {
                    docAceptacionNoActivoList.add(documentoAceptacion);
                }
            }

            if (docAceptacionNoActivoList.size() > 0) {
                String respuestaBasica = "La EM/HES con numero %s asociada a la prefactura " + referencia + " esta en estado %s";
                StringBuilder stringListaNumerosDocNoActivos = new StringBuilder("");

                for (int i = 0; i < docAceptacionNoActivoList.size(); i++) {
                    DocumentoAceptacion docNoActivo = docAceptacionNoActivoList.get(0);
                    stringListaNumerosDocNoActivos.append((i > 0 ? " - " : "").concat(docNoActivo.getNumeroDocumentoAceptacion()));

                    logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.SAP_ENVIAR.toString(),
                            RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), 0,
                            "ERROR", String.format(respuestaBasica, docNoActivo.getNumeroDocumentoAceptacion(), docNoActivo.getEstadoDocumentoAceptacion().getDescripcion()), false,token);
                }

                throw new PortalException("La(s) EM/HES con numero(s) " + stringListaNumerosDocNoActivos.toString() + " asociada(s) a la prefactura " + referencia + " no esta(n) en estado ACTIVO");
            }

            logger.error("NUEVA PREFACTURA A GRABAR: " + prefacturaDto.toString());
            prefactura = prefacturaRepository.save(prefactura);
            Integer idPrefactura = prefactura.getId();
            logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_CREAR.toString(),
                    RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefacturaDto), idPrefactura,
                    "EXITO", "Id Nueva Prefactura: " + idPrefactura, false,token);

            idDocumentoAceptacionList.forEach(id -> {
                PrefacturaDocumentoAceptacion prefDocAceptacion = new PrefacturaDocumentoAceptacion();
                prefDocAceptacion.setIdPrefactura(idPrefactura);
                prefDocAceptacion.setIdDocumentoAceptacion(id);
                prefDocAceptacion.setIsActive(OpcionGenericaEnum.SI.getCodigo());

                logger.error("NUEVO PREFACTURA X DOCACEPTACION A GRABAR: " + prefDocAceptacion.toString());
                prefacturaDocumentoAceptacionRepository.save(prefDocAceptacion);
                documentoAceptacionRepository.updateEstadoDocumentoAceptacionById(DocumentoAceptacionEstadoEnum.PREFACTURADO.getId(), id);
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                        RegistroLogTipoEnum.DOC_ACEPTACION.toString(), null, id,
                        "EXITO", "El Documento Aceptacion con Id: " + id + " paso a estado PREFACTURADO", false,token);
            });

            return idPrefactura;

        } catch (Exception e) {
            logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_CREAR.toString(),
                    RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefacturaDto), null,
                    "ERROR", "EXCEPCION: " + e.getClass().getName() + " -- " + e.getMessage(), false,token);
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<DocumentoAceptacion> obtenerDocumentoAceptacionListByIdPrefactura(Integer idPrefactura) {
        List<PrefacturaDocumentoAceptacion> prefacturaDocumentoAceptacionList = prefacturaDocumentoAceptacionRepository.getAllAssociatedByIdPrefactura(idPrefactura);
        List<DocumentoAceptacion> documentoAceptacionList;

        documentoAceptacionList = prefacturaDocumentoAceptacionList.stream()
                .map(PrefacturaDocumentoAceptacion::getDocumentoAceptacion).collect(Collectors.toList());

        return documentoAceptacionList;
    }


    @Override
    public String descartarPrefactura(Integer idPrefactura, Token token) {
        Optional<Prefactura> optionalPrefactura = prefacturaRepository.getOneById(idPrefactura);
        String respuesta;

        if (optionalPrefactura.isPresent()) {
            Prefactura prefactura = optionalPrefactura.get();
            Gson gson = new Gson();
            respuesta = "La prefactura ha sido descartada exitosamente";

            if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.ENVIADA.getId())) {
                prefactura.setIdEstadoPrefactura(PrefacturaEstadoEnum.DESCARTADA.getId());
                prefactura.setFechaDescarte(DateUtils.getCurrentTimestamp());
                prefacturaRepository.save(prefactura);
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                        "EXITO", "La Prefactura con Id: " + idPrefactura + " paso a estado DESCARTADA", false,token);

                List<PrefacturaDocumentoAceptacion> prefacturaDocumentoAceptacionList = prefacturaDocumentoAceptacionRepository.getAllAssociatedByIdPrefactura(idPrefactura);
                if(prefacturaDocumentoAceptacionList.isEmpty()){
                    prefacturaDocumentoAceptacionEmpty(idPrefactura, prefactura,token);
                }
                prefacturaDocumentoAceptacionList.forEach(pda -> {
                    pda.setIsActive(OpcionGenericaEnum.NO.getCodigo());
                    prefacturaDocumentoAceptacionRepository.save(pda);
                    documentoAceptacionRepository.updateEstadoDocumentoAceptacionById(DocumentoAceptacionEstadoEnum.ACTIVO.getId(), pda.getIdDocumentoAceptacion());
                    logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                            RegistroLogTipoEnum.DOC_ACEPTACION.toString(), null, pda.getIdDocumentoAceptacion(),
                            "EXITO", "El Documento Aceptacion con Id: " + pda.getIdDocumentoAceptacion() + " paso a estado ACTIVO", false,token);
                });
            } else if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.REGISTRADA.getId())) {
                respuesta = String.format(PREFACTURA_MENSAJE_GENERICO, "descartada", "ya esta registrada");
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                        "ERROR", respuesta, false,token);
            } else if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.RECHAZADA.getId())) {
                respuesta = String.format(PREFACTURA_MENSAJE_GENERICO, "descartada", "ha sido rechazada");
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                        "ERROR", respuesta, false,token);
            } else {
                respuesta = String.format(PREFACTURA_MENSAJE_GENERICO, "descartada", "ya ha sido descartada");
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                        "ERROR", respuesta, false,token);
            }
        } else {
            respuesta = String.format(PREFACTURA_MENSAJE_GENERICO, "descartada", "no existe");
            logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                    RegistroLogTipoEnum.PREFACTURA.toString(), null, idPrefactura,
                    "ERROR", respuesta, false,token);
        }

        return respuesta;
    }

    private void savePrefacturaDocumentoAceptacion(Integer idPrefactura, DocumentoAceptacion documentoAceptacionAux) {
        PrefacturaDocumentoAceptacion prefDocAceptacion = new PrefacturaDocumentoAceptacion();
        prefDocAceptacion.setIdPrefactura(idPrefactura);
        prefDocAceptacion.setIdDocumentoAceptacion(documentoAceptacionAux.getId());
        prefDocAceptacion.setIsActive(OpcionGenericaEnum.SI.getCodigo());
        this.prefacturaDocumentoAceptacionRepository.save(prefDocAceptacion);
    }


    @Override
    public PrefacturaRFCResponseDto rechazarPrefactura(Integer idPrefactura, String textoRechazo,Token token) {
        logger.error("rechazarPrefactura: inicio");
        Boolean isJob;
        isJob = textoRechazo.contains("[AUTOMÁTICO]");
        Optional<Prefactura> optionalPrefactura = prefacturaRepository.getOneById(idPrefactura);
        String respuestaBasica = "La prefactura ha sido rechazada exitosamente";
        PrefacturaRFCResponseDto responseDto = new PrefacturaRFCResponseDto();
        Gson gson = new Gson();

        if (optionalPrefactura.isPresent()) {
            Prefactura prefactura = optionalPrefactura.get();
            logger.error("rechazarPrefactura: 1.0");
            if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.ENVIADA.getId())) {
                prefactura.setIdEstadoPrefactura(PrefacturaEstadoEnum.RECHAZADA.getId());
                prefactura.setMotivoRechazo(textoRechazo);
                prefactura.setFechaDescarte(DateUtils.getCurrentTimestamp());
                prefactura = prefacturaRepository.save(prefactura);
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                        "EXITO", "La Prefactura con Id: " + idPrefactura + " paso a estado RECHAZADA", isJob,token);

                List<PrefacturaDocumentoAceptacion> prefacturaDocumentoAceptacionList = prefacturaDocumentoAceptacionRepository.getAllAssociatedByIdPrefactura(idPrefactura);
                if(prefacturaDocumentoAceptacionList.isEmpty()){
                    prefacturaDocumentoAceptacionEmpty(idPrefactura, prefactura,token);
                }
                logger.error("rechazarPrefactura: 2.0");
                prefacturaDocumentoAceptacionList.forEach(pda -> {
                    pda.setIsActive(OpcionGenericaEnum.NO.getCodigo());
                    prefacturaDocumentoAceptacionRepository.save(pda);
                    documentoAceptacionRepository.updateEstadoDocumentoAceptacionById(DocumentoAceptacionEstadoEnum.ACTIVO.getId(), pda.getIdDocumentoAceptacion());
                    logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                            RegistroLogTipoEnum.DOC_ACEPTACION.toString(), null, pda.getIdDocumentoAceptacion(),
                            "EXITO", "El Documento Aceptacion con Id: " + pda.getIdDocumentoAceptacion() + " paso a estado ACTIVO", isJob,token);
                });
                logger.error("rechazarPrefactura: 3.0");
                /*Enviando correo*/
                Proveedor proveedor = proveedorService.getProveedorByRuc(prefactura.getProveedorRuc());
                Usuario aprobador = null;
                if(!isJob) aprobador = usuarioRepository.getByCodigoUsuarioIdp(systemLoggedUser.getUserSession(token).getId());

                Usuario proveedorUsuario = null;
                SapLog correoLog = new SapLog();
                correoLog.setCode("CORREO");
                logger.error("rechazarPrefactura: 4.0");
                if (proveedor != null && proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()) {
                    proveedorUsuario = new Usuario();
                    logger.error("<--MC_LOG-->:PrefacturaServiceImpl-rechazarPrefactura:");
                    logger.error("<--MC_LOG-->:PrefacturaServiceImpl-rechazarPrefactura:"+proveedor.getEmail());
                    proveedorUsuario.setEmail(proveedor.getEmail());
                } else {
                    List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(prefactura.getProveedorRuc());
                    if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                        proveedorUsuario = posibleProveedorList.get(0);
                }
                logger.error("rechazarPrefactura: 5.0");
                /*List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(prefactura.getProveedorRuc());
                if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                    proveedorUsuario = posibleProveedorList.get(0);*/

                if (proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                    correoLog.setMesaj(proveedorRegistradaRechazadaPFNotificacion.enviar(prefactura, proveedorUsuario, aprobador));
                else
                    correoLog.setMesaj("Error al obtener los datos del proveedor para envio de correo de rechazo de prefactura.");

                logger.error("rechazarPrefactura: 6.0");
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.CORREO_ENVIAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), null, idPrefactura,
                        correoLog.getCode(), correoLog.getMesaj(), isJob,token);
                logger.error("rechazarPrefactura: 7.0");
                List<SapLog> sapMessageList = new ArrayList<>();
                sapMessageList.add(correoLog);
                responseDto.setSapMessageList(sapMessageList);

            } else if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.REGISTRADA.getId())) {
                respuestaBasica = String.format(PREFACTURA_MENSAJE_GENERICO, "rechazada", "ya esta registrada");
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                        "ERROR", respuestaBasica, isJob,token);
            } else if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.DESCARTADA.getId())) {
                respuestaBasica = String.format(PREFACTURA_MENSAJE_GENERICO, "rechazada", "ha sido descartada");
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                        "ERROR", respuestaBasica, isJob,token);
            } else {
                respuestaBasica = String.format(PREFACTURA_MENSAJE_GENERICO, "rechazada", "ya ha sido rechazada");
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                        "ERROR", respuestaBasica, isJob,token);
            }
        } else {
            respuestaBasica = String.format(PREFACTURA_MENSAJE_GENERICO, "rechazada", "no existe");
            logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                    RegistroLogTipoEnum.PREFACTURA.toString(), null, idPrefactura,
                    "ERROR", respuestaBasica, isJob,token);
        }

        logger.error("retornarrechazarPrefactura 8.0");
        responseDto.setRespuestaBasica(respuestaBasica);
        return responseDto;
    }

    private void prefacturaDocumentoAceptacionEmpty(Integer idPrefactura, Prefactura prefactura, Token token) {
        String nroDocumento = prefactura.getCadenaNumerosGuia();
        if(nroDocumento.contains("-")) {
            String[] parts = nroDocumento.split("-");
            for (int i = 0; i < parts.length; i++) {
                DocumentoAceptacion documentoAceptacionAux = this.documentoAceptacionRepository.findByNumeroDocumentoAceptacion(parts[i].trim()).get();
                documentoAceptacionRepository.updateEstadoDocumentoAceptacionById(DocumentoAceptacionEstadoEnum.ACTIVO.getId(), documentoAceptacionAux.getId());
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                        RegistroLogTipoEnum.DOC_ACEPTACION.toString(), null, documentoAceptacionAux.getId(),
                        "EXITO", "El Documento Aceptacion con Id: " + documentoAceptacionAux.getId() + " paso a estado ACTIVO", false,token);
                this.savePrefacturaDocumentoAceptacion(idPrefactura, documentoAceptacionAux);
            }
        } else {
            DocumentoAceptacion documentoAceptacionAux = this.documentoAceptacionRepository.findByNumeroDocumentoAceptacion(nroDocumento).get();
            documentoAceptacionRepository.updateEstadoDocumentoAceptacionById(DocumentoAceptacionEstadoEnum.ACTIVO.getId(), documentoAceptacionAux.getId());
            logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                    RegistroLogTipoEnum.DOC_ACEPTACION.toString(), null, documentoAceptacionAux.getId(),
                    "EXITO", "El Documento Aceptacion con Id: " + documentoAceptacionAux.getId() + " paso a estado ACTIVO", false,token);
            this.savePrefacturaDocumentoAceptacion(idPrefactura, documentoAceptacionAux);
        }
    }


    @Override
    public String enviarCorreoAnulacionPrefactura(Integer idPrefactura, String textoAnulacion,Token token) {
        Optional<Prefactura> optionalPrefactura = prefacturaRepository.getOneById(idPrefactura);
        String respuestaBasica = "";
        String respuestaErrorBase = "No se puede enviar correo de anulacion porque la prefactura %s.";
        Gson gson = new Gson();

        if (optionalPrefactura.isPresent()) {
            Prefactura prefactura = optionalPrefactura.get();

            if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.ANULADA.getId())) {
                Usuario aprobador = usuarioRepository.getByCodigoUsuarioIdp(systemLoggedUser.getUserSession(token).getId());
                prefactura.setMotivoRechazo(textoAnulacion);
                prefactura.setUsuarioRegistroSap(aprobador.getCodigoSap());
                prefactura.setFechaDescarte(DateUtils.getCurrentTimestamp());
                prefactura = prefacturaRepository.save(prefactura);

                /*Enviando correo*/
                Proveedor proveedor = proveedorService.getProveedorByRuc(prefactura.getProveedorRuc());
                Usuario proveedorUsuario = null;
                SapLog correoLog = new SapLog();
                correoLog.setCode("CORREO");

                if (proveedor != null && proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()) {
                    proveedorUsuario = new Usuario();
                    logger.error("<--MC_LOG-->:PrefacturaServiceImpl-enviarCorreoAnulacionPrefactura:");
                    logger.error("<--MC_LOG-->:PrefacturaServiceImpl-enviarCorreoAnulacionPrefactura:"+proveedor.getEmail());
                    proveedorUsuario.setEmail(proveedor.getEmail());
                } else {
                    List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(prefactura.getProveedorRuc());
                    if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                        proveedorUsuario = posibleProveedorList.get(0);
                }
                /*List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(prefactura.getProveedorRuc());
                if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                    proveedorUsuario = posibleProveedorList.get(0);*/

                logger.error("enviarCorreoAnulacionPrefactura_prefactura :: " + prefactura);
                logger.error("enviarCorreoAnulacionPrefactura_prefactura_1 :: " + prefactura.getMotivoRechazo());
                if (proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty()) {
                    respuestaBasica = proveedorRegistradaRechazadaPFNotificacion.enviar(prefactura, proveedorUsuario, aprobador);
                    //logger.error("enviarCorreoAnulacionPrefactura_prefactura :: " + prefactura);
                    // logger.error("enviarCorreoAnulacionPrefactura_prefactura_1 :: " + prefactura.getMotivoRechazo());

                    logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.CORREO_ENVIAR.toString(),
                            RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                            "EXITO", respuestaBasica, false,token);

                    return respuestaBasica;
                } else {
                    respuestaBasica = "Error al obtener los datos del proveedor para envio de correo de anulacion de prefactura.";
                }
            } else if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.REGISTRADA.getId())) {
                respuestaBasica = String.format(respuestaErrorBase, "esta en estado REGISTRADA");
            } else if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.DESCARTADA.getId())) {
                respuestaBasica = String.format(respuestaErrorBase, "esta en estado DESCARTADA");
            } else if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.RECHAZADA.getId())) {
                respuestaBasica = String.format(respuestaErrorBase, "esta en estado RECHAZADA");
            } else if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.ENVIADA.getId())) {
                respuestaBasica = String.format(respuestaErrorBase, "esta en estado ENVIADA");
            }

            logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.CORREO_ENVIAR.toString(),
                    RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                    "ERROR", respuestaBasica, false,token);
        } else {
            respuestaBasica = String.format(respuestaErrorBase, "no existe");
            logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.CORREO_ENVIAR.toString(),
                    RegistroLogTipoEnum.PREFACTURA.toString(), null, idPrefactura,
                    "ERROR", respuestaBasica, false,token);
        }

        return respuestaBasica;
    }


    @Override
    public PrefacturaRFCResponseDto registrarPrefacturaEnSap(Integer idPrefactura, Date fechaContabilizacion, Date fechaBase, String indicadorImpuesto,Token token) throws Exception {
        Optional<Prefactura> optionalPrefactura = prefacturaRepository.getOneById(idPrefactura);
        String respuestaBasica = "";
        PrefacturaRFCResponseDto responseDto = new PrefacturaRFCResponseDto();
        Gson gson = new Gson();

        if (optionalPrefactura.isPresent()) {
            Prefactura prefactura = optionalPrefactura.get();

            if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.ENVIADA.getId())) {
                PrefacturaRFCRequestDto prefacturaRequestDto = new PrefacturaRFCRequestDto();
//                String usuarioSapLogueado = systemLoggedUser.getUserSession().getSapUsername();
                String usuarioSapLogueado = null;
                Usuario usuarioRegistrador = usuarioRepository.getByCodigoUsuarioIdp(systemLoggedUser.getUserSession(token).getId());
                if (usuarioRegistrador != null)
                    usuarioSapLogueado = usuarioRegistrador.getCodigoSap();

                prefacturaRequestDto.setReferencia(this.getReferenciaNotacionSap(prefactura.getReferencia()));
                prefacturaRequestDto.setSociedad(prefactura.getCodigoSociedad());
                prefacturaRequestDto.setFechaEmision(DateUtils.utilDateToSapString(prefactura.getFechaEmision()));
                prefacturaRequestDto.setFechaContabilizacion(DateUtils.utilDateToSapString(fechaContabilizacion));
                prefacturaRequestDto.setFechaBase(DateUtils.utilDateToSapString(fechaBase));
                prefacturaRequestDto.setIndicadorImpuesto(indicadorImpuesto);
                prefacturaRequestDto.setBaseImponibleTotal(Optional.ofNullable(prefactura.getTotal()).orElse(BigDecimal.ZERO));
                prefacturaRequestDto.setIgvTotal(Optional.ofNullable(prefactura.getIgv()).orElse(BigDecimal.ZERO));
//                prefacturaRequestDto.setRetencionTotal(Optional.ofNullable(prefactura.getXXXX()).orElse(BigDecimal.ZERO));
                prefacturaRequestDto.setTextoCabecera(prefactura.getObservaciones());
                prefacturaRequestDto.setMoneda(prefactura.getCodigoMondeda());
                prefacturaRequestDto.setUsuarioRegistroSap(usuarioSapLogueado);

                List<DocumentoAceptacion> documentoAceptacionList = this.obtenerDocumentoAceptacionListByIdPrefactura(idPrefactura);
                List<PrefacturaRFCPosicionDto> posicionDtoList = new ArrayList<>();

                Map<String, List<DocumentoAceptacion>> entradaMercaderiaMap = documentoAceptacionList.stream()
                        .filter(da -> da.getIdTipoDocumentoAceptacion() == DocumentoAceptacionTipoEnum.ENTRADA_MERCADERIA.getId())
                        .collect(Collectors.groupingBy(DocumentoAceptacion::getNumeroOrdenCompra, Collectors.toList()));

                Map<String, List<DocumentoAceptacion>> hojaServicioMap = documentoAceptacionList.stream()
                        .filter(da -> da.getIdTipoDocumentoAceptacion() == DocumentoAceptacionTipoEnum.HOJA_ENTRADA_SERVICIO.getId())
                        .collect(Collectors.groupingBy(DocumentoAceptacion::getNumeroOrdenCompra, Collectors.toList()));

                entradaMercaderiaMap.forEach((numeroOrdenCompra, entradaMercaderiaList) -> {
                    List<DocumentoAceptacionDetalle> entradaMercaderiaDetalleList = new ArrayList<>();
                    List<InfoMessage> entradaMercaderiaYearEmisionMessageList = new ArrayList<>();

                    entradaMercaderiaList.forEach(em -> {
                        entradaMercaderiaDetalleList.addAll(em.getDocumentoAceptacionDetalleList());

                        String numeroEntradaMercaderia = em.getNumeroDocumentoAceptacion();
                        String yearEmision = DateUtils.utilDateToStringPattern(em.getFechaEmision(), "yyyy");
                        entradaMercaderiaYearEmisionMessageList.add(new InfoMessage(numeroEntradaMercaderia, yearEmision));
                    });

                    entradaMercaderiaDetalleList.stream()
                            .filter(emd -> emd.getIdEstadoDocumentoAceptacionDetalle() == 1)
                            .collect(Collectors.groupingBy(DocumentoAceptacionDetalle::getPosicionOrdenCompra, Collectors.toList()))
                            .forEach((posicionOrdenCompra, entradaMercaderiaDetallePorPosicionList) -> {
                                entradaMercaderiaDetallePorPosicionList.forEach(emItem -> {
                                    PrefacturaRFCPosicionDto posicionDto = new PrefacturaRFCPosicionDto();

                                    posicionDto.setNumeroOrdenCompra(numeroOrdenCompra);
                                    posicionDto.setNumeroPosicion(posicionOrdenCompra);
                                    posicionDto.setUnidadMedida(emItem.getUnidadMedida());
                                    posicionDto.setCantidadFacturada(emItem.getCantidadAceptadaCliente());
                                    posicionDto.setValorFacturado(emItem.getValorRecibido());
                                    posicionDto.setTipoDocumentoAceptacion("M");
                                    posicionDto.setNumeroDocumentoAceptacion(emItem.getNumeroDocumentoAceptacion());
                                    posicionDto.setNumeroItem(String.format("%04d", emItem.getNumeroItem()));

                                    entradaMercaderiaYearEmisionMessageList.forEach(message -> {
                                        if (emItem.getNumeroDocumentoAceptacion().equals(message.getMessageCode())) {
                                            posicionDto.setYearEmision(message.getMessageText1());
                                        }
                                    });

                                    posicionDtoList.add(posicionDto);
                                });
                            });
                });

                hojaServicioMap.forEach((numeroOrdenCompra, hojaServicioList) -> {
                    hojaServicioList.stream()
                            .collect(Collectors.groupingBy(DocumentoAceptacion::getPosicionOrdenCompra, Collectors.toList()))
                            .forEach((posicionOrdenCompra, hojaServicioPorPosicionList) -> {
                                hojaServicioPorPosicionList.forEach(hes -> {
                                    DocumentoAceptacionDetalle hesItem = hes.getDocumentoAceptacionDetalleList().get(0);
                                    PrefacturaRFCPosicionDto posicionDto = new PrefacturaRFCPosicionDto();

                                    posicionDto.setNumeroOrdenCompra(numeroOrdenCompra);
                                    posicionDto.setNumeroPosicion(posicionOrdenCompra);
                                    posicionDto.setUnidadMedida(hesItem.getUnidadMedida());
                                    posicionDto.setCantidadFacturada(hesItem.getCantidadAceptadaCliente());
                                    posicionDto.setValorFacturado(hesItem.getValorRecibido());
                                    posicionDto.setTipoDocumentoAceptacion("S");
                                    posicionDto.setNumeroDocumentoAceptacion(hes.getNumeroDocumentoAceptacion());
                                    posicionDto.setNumeroItem(String.format("%010d", 10)); // "10" porque siempre es un unico item para HES

                                    posicionDtoList.add(posicionDto);
                                });
                            });
                });

                prefacturaRequestDto.setPrefacturaRFCPosicionDtoList(posicionDtoList);
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.SAP_ENVIAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), prefacturaRequestDto.toString(), idPrefactura,
                        "DTO", "Request DTO enviado a SAP", false,token);

                responseDto = jcoPrefacturaService.registrarPrefacturaRFC(prefacturaRequestDto);
                List<SapLog> sapMessageList = responseDto.getSapMessageList();

                if (sapMessageList.size() > 0) {
                    Optional<SapLog> opSapSuccessMessage = sapMessageList.stream()
                            .filter(r -> r.getCode().equalsIgnoreCase("S"))
                            .findFirst();

                    if (opSapSuccessMessage.isPresent() && !responseDto.getCodigoDocumentoSap().isEmpty()) {
                        respuestaBasica = "La prefactura ha sido registrada exitosamente";
                        logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.SAP_ENVIAR.toString(),
                                RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                                "EXITO", respuestaBasica, false,token);

                        for (SapLog sapLog : sapMessageList) {
                            logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.SAP_ENVIAR.toString(),
                                    RegistroLogTipoEnum.PREFACTURA.toString(), null, idPrefactura,
                                    sapLog.getCode(), sapLog.getMesaj(), false,token);
                        }

                        prefactura.setIdEstadoPrefactura(PrefacturaEstadoEnum.REGISTRADA.getId());
                        prefactura.setFechaRegistroSap(DateUtils.getCurrentTimestamp());
                        prefactura.setFechaContabilizacion(fechaContabilizacion);
                        prefactura.setFechaBase(fechaBase);
                        prefactura.setIndicadorImpuesto(indicadorImpuesto);
                        prefactura.setCodigoSap(responseDto.getCodigoDocumentoSap());
                        prefactura.setEjercicio(responseDto.getEjercicio());
                        prefactura.setNumeroDocumentoContable(responseDto.getNumeroDocumentoContable());
                        prefactura.setUsuarioRegistroSap(usuarioSapLogueado);
                        prefactura = prefacturaRepository.save(prefactura);
                        logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                                RegistroLogTipoEnum.PREFACTURA.toString(), null, idPrefactura,
                                "EXITO", "La Prefactura con Id: " + idPrefactura + " paso a estado REGISTRADA", false,token);

                        documentoAceptacionList.forEach(da -> {
                            if (da.getIdEstadoDocumentoAceptacion().compareTo(DocumentoAceptacionEstadoEnum.PREFACTURADO.getId()) == 0) {
                                da.setIdEstadoDocumentoAceptacion(DocumentoAceptacionEstadoEnum.REGISTRADO.getId());
                                documentoAceptacionRepository.save(da);
                                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                                        RegistroLogTipoEnum.DOC_ACEPTACION.toString(), null, da.getId(),
                                        "EXITO", "El Documento Aceptacion con Id: " + da.getId() + " y Numero: " + da.getNumeroDocumentoAceptacion() + " paso a estado REGISTRADO", false,token);
                            }
                        });

                        /*Enviando correo*/
                        Proveedor proveedor = proveedorService.getProveedorByRuc(prefactura.getProveedorRuc());
                        Usuario proveedorUsuario = null;
                        SapLog correoLog = new SapLog();
                        correoLog.setCode("CORREO");

                        if (proveedor != null && proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()) {
                            proveedorUsuario = new Usuario();
                            logger.error("<--MC_LOG-->:PrefacturaServiceImpl-registrarPrefacturaEnSap:");
                            logger.error("<--MC_LOG-->:PrefacturaServiceImpl-registrarPrefacturaEnSap:"+proveedor.getEmail());
                            proveedorUsuario.setEmail(proveedor.getEmail());
                        } else {
                            List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(prefactura.getProveedorRuc());
                            if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                                proveedorUsuario = posibleProveedorList.get(0);
                        }

                        /*List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(prefactura.getProveedorRuc());
                        if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                            proveedorUsuario = posibleProveedorList.get(0);*/

                        if (proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                            correoLog.setMesaj(proveedorRegistradaRechazadaPFNotificacion.enviar(prefactura, proveedorUsuario, null));
                        else
                            correoLog.setMesaj("Error al obtener los datos del proveedor para envio de correo de registro de prefactura.");

                        logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.CORREO_ENVIAR.toString(),
                                RegistroLogTipoEnum.PREFACTURA.toString(), null, idPrefactura,
                                correoLog.getCode(), correoLog.getMesaj(), false,token);

                        sapMessageList.add(correoLog);
                        responseDto.setSapMessageList(sapMessageList);
                    } else {
                        respuestaBasica = "Hubo un error al registrar la prefactura en SAP. Revisar mensajes de respuesta.";
                        logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.SAP_ENVIAR.toString(),
                                RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                                "ERROR", respuestaBasica, false,token);

                        for (SapLog sapLog : sapMessageList) {
                            logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.SAP_ENVIAR.toString(),
                                    RegistroLogTipoEnum.PREFACTURA.toString(), null, idPrefactura,
                                    sapLog.getCode(), sapLog.getMesaj(), false,token);
                        }
                    }

                    responseDto.setRespuestaBasica(respuestaBasica);
                    return responseDto;
                } else {
                    respuestaBasica = "Hubo un error al registrar la prefactura en SAP. No respondio con ningun mensaje.";
                    logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.SAP_ENVIAR.toString(),
                            RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                            "ERROR", respuestaBasica, false,token);
                }
            } else if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.DESCARTADA.getId())) {
                respuestaBasica = String.format(PREFACTURA_MENSAJE_GENERICO, "registrada", "ha sido descartada");
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.SAP_ENVIAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                        "ERROR", respuestaBasica, false,token);
            } else if (prefactura.getIdEstadoPrefactura().equals(PrefacturaEstadoEnum.RECHAZADA.getId())) {
                respuestaBasica = String.format(PREFACTURA_MENSAJE_GENERICO, "registrada", "ha sido rechazada");
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.SAP_ENVIAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                        "ERROR", respuestaBasica, false,token);
            } else {
                respuestaBasica = String.format(PREFACTURA_MENSAJE_GENERICO, "registrada", "ya ha sido registrada");
                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.SAP_ENVIAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                        "ERROR", respuestaBasica, false,token);
            }
        } else {
            respuestaBasica = String.format(PREFACTURA_MENSAJE_GENERICO, "registrada", "no existe");
            logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.SAP_ENVIAR.toString(),
                    RegistroLogTipoEnum.PREFACTURA.toString(), null, idPrefactura,
                    "ERROR", respuestaBasica, false,token);
        }

        List<SapLog> sapMessageList = new ArrayList<>();
        SapLog errorLog = new SapLog();
        errorLog.setCode("ERROR");
        errorLog.setMesaj(respuestaBasica);
        sapMessageList.add(errorLog);

        responseDto.setRespuestaBasica(respuestaBasica);
        responseDto.setSapMessageList(sapMessageList);
        return responseDto;
    }

    @Override
    @Transactional
    public String updatePathAdjuntoPrefacturaCf(Integer idPrefactura, String cmisFileUrl, String idCmis, String type) {
        try {
            Optional<Prefactura> prefacturaOptional = this.prefacturaRepository.getOneById(idPrefactura);
//            type = type.toLowerCase();

            if (prefacturaOptional.isPresent()) {
                if (type.contains("pdf") || type.contains("xml")) {
                    if (type.contains("pdf")) {
                        prefacturaOptional.get().setPdfEcmPathCf(cmisFileUrl);
                        prefacturaOptional.get().setArchivoIdPdfCf(idCmis);
                    } else if (type.contains("xml")) {
                        prefacturaOptional.get().setXmlEcmPathCf(cmisFileUrl);
                        prefacturaOptional.get().setArchivoIdXMLCf(idCmis);
                    }
                    this.prefacturaRepository.save(prefacturaOptional.get());
                    return "Se actualizo correctamente la prefactura con numero: " + prefacturaOptional.get().getReferencia();
                } else {
                    return "La extensión " + type + " no es correcta.";
                }
            }
            return "No se encontro una prefactura con Id: " + idPrefactura;
        } catch (NullPointerException e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Override
    @Transactional
    public String updatePathAdjuntoPrefactura(Integer idPrefactura, String cmisFileUrl, String type) {
        try {
            Optional<Prefactura> prefacturaOptional = this.prefacturaRepository.getOneById(idPrefactura);
//            type = type.toLowerCase();

            if (prefacturaOptional.isPresent()) {
                if (type.contains("pdf") || type.contains("xml")) {
                    if (type.contains("pdf")) {
                        prefacturaOptional.get().setPdfEcmPath(cmisFileUrl);
                    } else if (type.contains("xml")) {
                        prefacturaOptional.get().setXmlEcmPath(cmisFileUrl);
                    }
                    this.prefacturaRepository.save(prefacturaOptional.get());
                    return "Se actualizo correctamente la prefactura con numero: " + prefacturaOptional.get().getReferencia();
                } else {
                    return "La extensión " + type + " no es correcta.";
                }
            }
            return "No se encontro una prefactura con Id: " + idPrefactura;
        } catch (NullPointerException e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Override
    public String getFileEcmPath(Integer idPrefactura, String fileType) {
        try {
            Optional<Prefactura> prefacturaOptional = this.prefacturaRepository.getOneById(idPrefactura);
            String response = "";
            if (prefacturaOptional.isPresent()) {
                if (fileType.equals("pdf")) { // Type PDF
                    response = prefacturaOptional.get().getPdfEcmPath();
                } else { // Type XML
                    response = prefacturaOptional.get().getXmlEcmPath();
                }
            }
            return response;
        } catch (NullPointerException e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @Override
    public String getPrefacturaPdfContent(Prefactura prefactura) throws Exception {
        PrefacturaPdfDto prefacturaPdfDto = new PrefacturaPdfDto();
        prefacturaPdfDto.setClienteRuc(prefactura.getSociedad().getRuc());
        prefacturaPdfDto.setClienteRazonSocial(prefactura.getSociedad().getRazonSocial());
        prefacturaPdfDto.setProveedorRuc(prefactura.getProveedorRuc());
        prefacturaPdfDto.setProveedorRazonSocial(prefactura.getProveedorRazonSocial());
        prefacturaPdfDto.setPrefacturaReferencia(prefactura.getReferencia());
        prefacturaPdfDto.setPrefacturaEstado(prefactura.getEstadoPrefactura().getDescripcion());
        prefacturaPdfDto.setPrefacturaOrdenes(prefactura.getCadenaNumerosOrdenCompra());
        prefacturaPdfDto.setPrefacturaGuias(prefactura.getCadenaNumerosGuia());
        prefacturaPdfDto.setPrefacturaFechaEmision(DateUtils.utilDateToStringPattern(prefactura.getFechaEmision(), DateUtils.STANDARD_DATE_FORMAT));
        prefacturaPdfDto.setPrefacturaFechaRegistro(DateUtils.utilDateToStringPattern(new Date(prefactura.getFechaRecepcion().getTime()), DateUtils.STANDARD_DATE_FORMAT));
        prefacturaPdfDto.setPrefacturaIndicadorImpuesto(prefactura.getIndicadorImpuesto());
        prefacturaPdfDto.setPrefacturaMoneda(prefactura.getCodigoMondeda());
        prefacturaPdfDto.setPrefacturaObservaciones(prefactura.getObservaciones());
        prefacturaPdfDto.setMontoSubtotal(prefactura.getSubTotal());
        prefacturaPdfDto.setMontoIgv(prefactura.getIgv());
        prefacturaPdfDto.setMontoTotal(prefactura.getTotal());

        List<DocumentoAceptacion> documentoAceptacionList = this.obtenerDocumentoAceptacionListByIdPrefactura(prefactura.getId());
        List<PrefacturaItemPdfDto> itemPdfDtoList = new ArrayList<>();

        Map<String, List<DocumentoAceptacion>> entradaMercaderiaMap = documentoAceptacionList.stream()
                .filter(da -> da.getIdTipoDocumentoAceptacion() == DocumentoAceptacionTipoEnum.ENTRADA_MERCADERIA.getId())
                .collect(Collectors.groupingBy(DocumentoAceptacion::getNumeroOrdenCompra, Collectors.toList()));

        Map<String, List<DocumentoAceptacion>> hojaServicioMap = documentoAceptacionList.stream()
                .filter(da -> da.getIdTipoDocumentoAceptacion() == DocumentoAceptacionTipoEnum.HOJA_ENTRADA_SERVICIO.getId())
                .collect(Collectors.groupingBy(DocumentoAceptacion::getNumeroOrdenCompra, Collectors.toList()));

        entradaMercaderiaMap.forEach((numeroOrdenCompra, entradaMercaderiaList) -> {
            List<DocumentoAceptacionDetalle> entradaMercaderiaDetalleList = new ArrayList<>();

            entradaMercaderiaList.forEach(em -> {
                entradaMercaderiaDetalleList.addAll(em.getDocumentoAceptacionDetalleList());
            });

            entradaMercaderiaDetalleList.stream()
                    .filter(emd -> emd.getIdEstadoDocumentoAceptacionDetalle() == 1)
                    .collect(Collectors.groupingBy(DocumentoAceptacionDetalle::getPosicionOrdenCompra, Collectors.toList()))
                    .forEach((posicionOrdenCompra, entradaMercaderiaDetallePorPosicionList) -> {
                        entradaMercaderiaDetallePorPosicionList.forEach(emItem -> {

                            PrefacturaItemPdfDto itemPdfDto = new PrefacturaItemPdfDto();

                            itemPdfDto.setNumeroDocumento(emItem.getNumeroDocumentoAceptacion());
                            itemPdfDto.setNumeroOrden(numeroOrdenCompra);
                            if (posicionOrdenCompra != null && !posicionOrdenCompra.isEmpty())
                                itemPdfDto.setPosicion(String.valueOf(Integer.parseInt(posicionOrdenCompra)));
                            if (emItem.getCodigoSapBienServicio() != null && !emItem.getCodigoSapBienServicio().isEmpty())
                                itemPdfDto.setCodigo(String.valueOf(Integer.parseInt(emItem.getCodigoSapBienServicio())));
                            itemPdfDto.setDescripcion(emItem.getDescripcionBienServicio());
                            itemPdfDto.setCantidad(emItem.getCantidadAceptadaCliente());
                            itemPdfDto.setPrecioUnitario(emItem.getPrecioUnitario());
                            itemPdfDto.setImporte(emItem.getValorRecibido());

                            itemPdfDtoList.add(itemPdfDto);
                        });
                    });
        });

        hojaServicioMap.forEach((numeroOrdenCompra, hojaServicioList) -> {
            hojaServicioList.stream()
                    .collect(Collectors.groupingBy(DocumentoAceptacion::getPosicionOrdenCompra, Collectors.toList()))
                    .forEach((posicionOrdenCompra, hojaServicioPorPosicionList) -> {
                        hojaServicioPorPosicionList.forEach(hes -> {
                            DocumentoAceptacionDetalle hesItem = hes.getDocumentoAceptacionDetalleList().get(0);
                            String numeroHES = hes.getNumeroDocumentoAceptacion();
                            List<HojaServicioSubPosicionDto> subPosicionDtoList = new ArrayList<>();

                            try {
                                subPosicionDtoList = jcoHojaServicioService.extraerHojaServicioSubPosicionListRFC(numeroHES);
                            } catch (Exception e) {
                                String error = Utils.obtieneMensajeErrorException(e);
                                throw new RuntimeException(error);
                            }

                            subPosicionDtoList.forEach(subPos -> {
                                PrefacturaItemPdfDto itemPdfDto = new PrefacturaItemPdfDto();

                                itemPdfDto.setNumeroDocumento(numeroHES);
                                itemPdfDto.setNumeroOrden(numeroOrdenCompra);
                                if (posicionOrdenCompra != null && !posicionOrdenCompra.isEmpty())
                                    itemPdfDto.setPosicion(String.valueOf(Integer.parseInt(posicionOrdenCompra)));
                                itemPdfDto.setDescripcion(hesItem.getDescripcionBienServicio());
                                if (subPos.getCodigoServicio() != null && !subPos.getCodigoServicio().isEmpty())
                                    itemPdfDto.setCodigoServicio(String.valueOf(Integer.parseInt(subPos.getCodigoServicio())));
                                itemPdfDto.setDescripcionServicio(subPos.getDescripcionServicio());
                                itemPdfDto.setIndicadorDetraccion(subPos.getIndicadorDetraccion());
                                itemPdfDto.setCantidad(subPos.getCantidad());
                                itemPdfDto.setPrecioUnitario(subPos.getPrecioUnitario());
                                itemPdfDto.setImporte(subPos.getImporte());

                                itemPdfDtoList.add(itemPdfDto);
                            });
                        });
                    });
        });

        int[] counterArray = new int[]{0};
        itemPdfDtoList.forEach(item -> {
            counterArray[0]++;
            item.setItem(String.valueOf(counterArray[0]));
        });

        prefacturaPdfDto.setPrefacturaItemPdfDtoList(itemPdfDtoList);

        byte[] generatePrefacturaPdfBytes = PdfGeneratorFactory.getJasperGenerator().generatePrefacturaPdfBytes(prefacturaPdfDto);
        return Base64.getEncoder().encodeToString(generatePrefacturaPdfBytes);
    }


    @Override
    public PrefacturaAnuladaRespuestaDto actualizarMasivoPrefacturasAnuladasPorRangoFechas(Date fechaInicio, Date fechaFin, Token token) {
        LocalDate currentlyExtractLocalDate = DateUtils.utilDateToLocalDate(fechaInicio);
        LocalDate finalLocalDate = DateUtils.utilDateToLocalDate(fechaFin);
        logger.error("ACTUALIZACION MASIVA PREFACTURAS ANULADAS - FECHA INICIO: " + currentlyExtractLocalDate.toString());
        logger.error("ACTUALIZACION MASIVA PREFACTURAS ANULADAS - FECHA FIN: " + finalLocalDate.toString());

        List<PrefacturaAnuladaDto> prefacturaAnuladaSapDtoFullList = new ArrayList<>();
        List<Prefactura> prefacturaModificadaFullList = new ArrayList<>();
        PrefacturaAnuladaRespuestaDto respuestaFullDto = new PrefacturaAnuladaRespuestaDto();

        while (currentlyExtractLocalDate.isBefore(finalLocalDate.plusDays(1))) {
            try {
                String currentDateAsSapString = DateUtils.localDateToSapString(currentlyExtractLocalDate);

                PrefacturaAnuladaRespuestaDto respuestaDto = this.actualizarPrefacturasAnuladasPorRangoFechas(currentDateAsSapString, currentDateAsSapString, true,token);
                prefacturaAnuladaSapDtoFullList.addAll(respuestaDto.getPrefacturaAnuladaSapDtoList());
                prefacturaModificadaFullList.addAll(respuestaDto.getPrefacturaModificadaList());

                currentlyExtractLocalDate = currentlyExtractLocalDate.plusDays(1);
            } catch (Exception e) {
                String error = Utils.obtieneMensajeErrorException(e);
                logger.error("ERROR al actualizar prefacturas anuladas de la fecha " + DateUtils.localDateToString(currentlyExtractLocalDate) + " : " + error);
                currentlyExtractLocalDate = currentlyExtractLocalDate.plusDays(1);
            }
        }

        respuestaFullDto.setPrefacturaAnuladaSapDtoList(prefacturaAnuladaSapDtoFullList);
        respuestaFullDto.setPrefacturaModificadaList(prefacturaModificadaFullList);

        return respuestaFullDto;
    }


    @Override
    public PrefacturaAnuladaRespuestaDto actualizarPrefacturasAnuladasPorRangoFechas(String fechaInicioSapString, String fechaFinSapString, boolean actualizacionManual, Token token) {
        String header = "FACTURAS ANULADAS RFC // RANGO : " + fechaInicioSapString + " - " + fechaFinSapString;
        List<PrefacturaAnuladaDto> prefacturaAnuladaDtoList;
        List<Prefactura> prefacturaModificadaList = new ArrayList<>();
        PrefacturaAnuladaRespuestaDto respuestaDto = new PrefacturaAnuladaRespuestaDto();

        try {
            prefacturaAnuladaDtoList = jcoPrefacturaService.obtenerPrefacturaAnuladaListRFC(fechaInicioSapString, fechaFinSapString);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }

        int counterMod = 0;

        for (PrefacturaAnuladaDto prefacturaAnuladaDto : prefacturaAnuladaDtoList) {
            List<Integer> idEstadoPrefacturaList = Arrays.asList(PrefacturaEstadoEnum.REGISTRADA.getId(), PrefacturaEstadoEnum.REGISTRO_MIRO.getId());
            Prefactura prefactura = null;

            try {
//                Optional<Prefactura> optionalPrefactura = prefacturaRepository.getPrefacturaByIdEstadoListAndCodigoSapAndEjercicio
//                        (idEstadoPrefacturaList, prefacturaAnuladaDto.getCodigoDocumentoContable(), prefacturaAnuladaDto.getEjercicio());
                prefactura = prefacturaRepository.findPrefacturaByIdEstadoListAndCodigoSapAndEjercicio(idEstadoPrefacturaList, prefacturaAnuladaDto.getCodigoDocumentoContable(), prefacturaAnuladaDto.getEjercicio());
            } catch (Exception e) {
                String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
                logger.error("ERROR al actualizar la prefactura en iProvider correspondiente a la factura anulada en SAP: " + prefacturaAnuladaDto.toString() + " : " + error);
            }

//            if (optionalPrefactura.isPresent()) {
            if (prefactura != null) {
                Gson gson = new Gson();
//                Prefactura prefactura = optionalPrefactura.get();
                Integer idPrefactura = prefactura.getId();

                prefactura.setIdEstadoPrefactura(PrefacturaEstadoEnum.ANULADA.getId());
                prefactura.setFechaDescarte(new Timestamp(prefacturaAnuladaDto.getFechaAnulacion().getTime()));
                prefacturaRepository.save(prefactura);
                counterMod++;

                if (actualizacionManual) {
                    prefacturaModificadaList.add(prefactura);
                }

                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), gson.toJson(prefactura), idPrefactura,
                        "EXITO", "La Prefactura con Id: " + idPrefactura + " paso a estado ANULADA", true,token);

                List<PrefacturaDocumentoAceptacion> prefacturaDocumentoAceptacionList = prefacturaDocumentoAceptacionRepository.getAllAssociatedByIdPrefactura(idPrefactura);

                prefacturaDocumentoAceptacionList.forEach(pda -> {
                    pda.setIsActive(OpcionGenericaEnum.NO.getCodigo());
                    prefacturaDocumentoAceptacionRepository.save(pda);
                    documentoAceptacionRepository.updateEstadoDocumentoAceptacionById(DocumentoAceptacionEstadoEnum.ACTIVO.getId(), pda.getIdDocumentoAceptacion());
                    logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                            RegistroLogTipoEnum.DOC_ACEPTACION.toString(), null, pda.getIdDocumentoAceptacion(),
                            "EXITO", "El Documento Aceptacion con Id: " + pda.getIdDocumentoAceptacion() + " paso a estado ACTIVO", true,token);
                });
            }
        }

//        String header = "FACTURAS ANULADAS RFC // RANGO : " + fechaInicioSapString + " - " + fechaFinSapString;
        logger.error(header + " // Facturas Anuladas encontradas en SAP: " + prefacturaAnuladaDtoList.size());
        logger.error(header + " // Facturas Anuladas modificadas en BD: " + counterMod);

        if (actualizacionManual) {
            respuestaDto.setPrefacturaAnuladaSapDtoList(prefacturaAnuladaDtoList);
            respuestaDto.setPrefacturaModificadaList(prefacturaModificadaList);
        }

        return respuestaDto;
    }


    @Override
    public List<PrefacturaActualizarDto> actualizarPrefacturasRegistradasEnSap(List<Integer> idPrefacturaActualizarList,Token token) {
        List<PrefacturaActualizarDto> prefacturaActualizarDtoParametroList = new ArrayList<>();
        List<PrefacturaActualizarDto> prefacturaActualizarDtoRespuestaList = new ArrayList<>();
        List<PrefacturaRegistradaSapDto> prefacturaRegistradaSapDtoList;
        String header = "REGULARIZACION MIRO / " + DateUtils.getCurrentTimestamp().toString();

        idPrefacturaActualizarList.forEach(id -> {
            Optional<Prefactura> opPrefactura = prefacturaRepository.getOneById(id);
            PrefacturaActualizarDto prefacturaActualizarDto = new PrefacturaActualizarDto();
            prefacturaActualizarDto.setIdPrefactura(id);

            if (opPrefactura.isPresent()) {
                Prefactura prefactura = opPrefactura.get();
                prefacturaActualizarDto.setReferencia(prefactura.getReferencia());
                prefacturaActualizarDto.setRucProveedor(prefactura.getProveedorRuc());

                if (prefactura.getIdEstadoPrefactura().compareTo(PrefacturaEstadoEnum.ENVIADA.getId()) == 0) {
                    prefacturaActualizarDto.setSociedad(prefactura.getCodigoSociedad());
                    prefacturaActualizarDto.setImporte(prefactura.getTotal());
                    prefacturaActualizarDto.setReferenciaSap(this.getReferenciaNotacionSap(prefactura.getReferencia()));
                    logger.error(header + " // " + prefacturaActualizarDto.toString() + " [por consultar en SAP]");
                    prefacturaActualizarDto.setMensaje("Los datos de la prefactura '" + prefacturaActualizarDto.getReferencia() + "' con RUC '" + prefacturaActualizarDto.getRucProveedor() + "' no fueron encontrados entre las facturas registradas en SAP"); // mensaje por defecto, se actualizara despues si es que SI fue encontrada en SAP
                    prefacturaActualizarDto.setPrefactura(prefactura);
                    prefacturaActualizarDtoParametroList.add(prefacturaActualizarDto);
                } else {
                    prefacturaActualizarDto.setMensaje("La prefactura '" + prefacturaActualizarDto.getReferencia() + "' con RUC '" + prefacturaActualizarDto.getRucProveedor() + "' no esta en estado 'ENVIADA'");
                    logger.error(header + " // " + prefacturaActualizarDto.toString());
                    prefacturaActualizarDtoRespuestaList.add(prefacturaActualizarDto);
                }
            } else {
                prefacturaActualizarDto.setMensaje("La prefactura con Id: " + id + " no existe");
                logger.error(header + " // " + prefacturaActualizarDto.toString());
                prefacturaActualizarDtoRespuestaList.add(prefacturaActualizarDto);
            }
        });

        try {
            prefacturaRegistradaSapDtoList = jcoPrefacturaService.obtenerPrefacturaRegistradaSapListRFC(prefacturaActualizarDtoParametroList);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }

        prefacturaRegistradaSapDtoList.forEach(pfEncontrada -> {
            logger.error(header + " // FACTURA ENCONTRADA EN SAP: " + pfEncontrada.toString());
        });

        prefacturaActualizarDtoParametroList.forEach(pfActualizar -> {
            Optional<PrefacturaRegistradaSapDto> opPrefacturaRegistrada = prefacturaRegistradaSapDtoList.stream()
                    .filter(pfRegistrada -> pfRegistrada.getReferencia().equals(pfActualizar.getReferenciaSap()) &&
                            pfRegistrada.getRucProveedor().equals(pfActualizar.getRucProveedor()) &&
                            pfRegistrada.getSociedad().equals(pfActualizar.getSociedad()) &&
                            pfRegistrada.getImporte().compareTo(pfActualizar.getImporte()) == 0 &&
                            pfRegistrada.getNumeroDocumentoFacturaLogistica() != null &&
                            !pfRegistrada.getNumeroDocumentoFacturaLogistica().isEmpty())
                    .findFirst();

            if (opPrefacturaRegistrada.isPresent()) {
                PrefacturaRegistradaSapDto prefacturaRegistrada = opPrefacturaRegistrada.get();
                Prefactura prefactura = pfActualizar.getPrefactura();

                prefactura.setCodigoSap(prefacturaRegistrada.getNumeroDocumentoFacturaLogistica());
                prefactura.setNumeroDocumentoContable(prefacturaRegistrada.getNumeroDocumentoContable());
                prefactura.setEjercicio(prefacturaRegistrada.getEjercicio());
                prefactura.setUsuarioRegistroSap(prefacturaRegistrada.getUsuarioSapRegistrador());
                prefactura.setFechaRegistroSap(Timestamp.valueOf(DateUtils.getLocalDateTimeFromDateAndTime(prefacturaRegistrada.getFechaRegistro(), prefacturaRegistrada.getHoraRegistro())));
                prefactura.setFechaBase(prefacturaRegistrada.getFechaBase());
                prefactura.setFechaContabilizacion(prefacturaRegistrada.getFechaContabilizacion());
                prefactura.setIndicadorImpuesto(prefacturaRegistrada.getIndicadorImpuesto());
                prefactura.setIdEstadoPrefactura(PrefacturaEstadoEnum.REGISTRO_MIRO.getId());

                prefacturaRepository.save(prefactura);

                String mensaje = "Se actualizaron exitosamente los datos de la prefactura '" + prefactura.getReferencia() + "' con RUC '" + prefactura.getProveedorRuc() + "'";
                logger.error(header + " // RESPUESTA: " + pfActualizar.toString());
                pfActualizar.setMensaje(mensaje);
//                prefacturaActualizarDtoRespuestaList.add(pfActualizar);

                logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                        RegistroLogTipoEnum.PREFACTURA.toString(), new Gson().toJson(prefactura), prefactura.getId(),
                        "EXITO", "La Prefactura con Id: " + prefactura.getId() + ", referencia: '" + prefactura.getReferencia() + "' y RUC '" + prefactura.getProveedorRuc() + "' paso a estado REGISTRO MIRO", false,token);

                List<PrefacturaDocumentoAceptacion> prefacturaDocumentoAceptacionList = prefacturaDocumentoAceptacionRepository.getAllAssociatedByIdPrefactura(prefactura.getId());

                prefacturaDocumentoAceptacionList.forEach(pda -> {
                    documentoAceptacionRepository.updateEstadoDocumentoAceptacionById(DocumentoAceptacionEstadoEnum.REGISTRADO.getId(), pda.getIdDocumentoAceptacion());
                    logTransaccionService.grabarNuevaLineaLogTransaccion(TransaccionLogTipoEnum.BD_MODIFICAR.toString(),
                            RegistroLogTipoEnum.DOC_ACEPTACION.toString(), null, pda.getIdDocumentoAceptacion(),
                            "EXITO", "El Documento Aceptacion con Id: " + pda.getIdDocumentoAceptacion() + " paso a estado REGISTRADO", false,token);
                });
            }
        });

        prefacturaActualizarDtoRespuestaList.addAll(prefacturaActualizarDtoParametroList);

        return prefacturaActualizarDtoRespuestaList;
    }


    public SXSSFWorkbook descargarListaPrefacturaExcelSXLSX(Date fechaEmisionInicio, Date fechaEmisionFin,
                                                            Date fechaEntradaInicio, Date fechaEntradaFin,
                                                            String ruc,
                                                            String referencia,
                                                            String comprador,
                                                            String centro,
                                                            Integer idEstado) {
        logger.error("PrefacturaExcel request parameters"
                + " // FechaEmisionInicio: " + Optional.ofNullable(DateUtils.utilDateToString(fechaEmisionFin)).orElse("-")
                + " // FechaEmisionFin: " + Optional.ofNullable(DateUtils.utilDateToString(fechaEmisionFin)).orElse("-")
                + " // FechaEntradaInicio: " + Optional.ofNullable(DateUtils.utilDateToString(fechaEntradaInicio)).orElse("-")
                + " // FechaEntradaFin: " + Optional.ofNullable(DateUtils.utilDateToString(fechaEntradaFin)).orElse("-")
                + " // RUC: " + Optional.ofNullable(ruc).orElse("-")
                + " // Referencia: " + Optional.ofNullable(referencia).orElse("-")
                + " // Comprador: " + Optional.ofNullable(comprador).orElse("-")
                + " // Centro: " + Optional.ofNullable(centro).orElse("-")
                + " // IdEstado: " + (idEstado != null ? String.valueOf(idEstado) : "-"));

        if (comprador != null)
            comprador = "%" + comprador + "%";

        if (centro != null)
            centro = "%" + centro + "%";

        List<Prefactura> prefacturaList = prefacturaRepository.getPrefacturaListForExcel(fechaEmisionInicio, fechaEmisionFin, fechaEntradaInicio, DateUtils.utilDateAtEndOfDay(fechaEntradaFin), ruc, referencia, comprador, centro, idEstado);

        SXSSFWorkbook book = new SXSSFWorkbook(100);
        XSSFWorkbook xbook = book.getXSSFWorkbook();
        SXSSFSheet sheet = book.createSheet();
        sheet.trackAllColumnsForAutoSizing();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, "Prefacturas");
        int nroColumnas = ExcelDefault.createTitleAndWidth(xbook, sheet, "com/incloud/hcp/excel/PrefacturaListado.xml", "Listado de Prefacturas", null);
        for (int i = 0; i < nroColumnas; i++) {
            sheet.autoSizeColumn(i, true);
        }
        sheet.untrackAllColumnsForAutoSizing();

        XSSFCellStyle cellStyle01 = ExcelDefault.devuelveCellStyle(xbook, new Color(0, 0, 1), new Color(226, 239, 218), false, (short) 10);
        XSSFCellStyle cellStyle02 = ExcelDefault.devuelveCellStyle(xbook, new Color(0, 0, 192), new Color(255, 255, 255), false, (short) 10);
        List<CellStyle> cellStyleList = null;
        List<CellStyle> cellStyleList01 = ExcelDefault.generarCellStyle(xbook, cellStyle01);
        List<CellStyle> cellStyleList02 = ExcelDefault.generarCellStyle(xbook, cellStyle02);
        boolean filaImpar = true;

        for (Prefactura prefacturaData : prefacturaList) {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            Row dataRow = sheet.createRow(i + 1);
            int contador = 0;
            if (filaImpar) {
                cellStyleList = cellStyleList01;
            } else {
                cellStyleList = cellStyleList02;
            }
            filaImpar = !filaImpar;

            String ultimoUser="";
            String condicionPago="";
            Date ultimoDate= new Date();
            switch (prefacturaData.getIdEstadoPrefactura()){
                case 2:
                    ultimoDate  = prefacturaData.getFechaRegistroSap();
                    if(prefacturaData.getFechaRegistroSap()!=null)
                        ultimoUser=prefacturaData.getUsuarioRegistroSap();
                    break;
                case 3:
                    ultimoDate  = prefacturaData.getFechaDescarte();
                    ultimoUser=logTransaccionService.getUsuarioTransaccionBD("BD_MODIFICAR","PREFACTURA",prefacturaData.getId());
                    break;
                case 5:
                    ultimoDate  = prefacturaData.getFechaDescarte();
                    ultimoUser=prefacturaData.getUsuarioRegistroSap();
                    break;
                default:
                    ultimoDate  = prefacturaData.getFechaRegistroSap();
                    ultimoUser=prefacturaData.getUsuarioRegistroSap();
                    break;
            }

            //Mejora 3 :: Item 6
            OrdenCompra oc = ordenCompraService.getOrdenCompraByNOCompra(prefacturaData.getCadenaNumerosOrdenCompra());
            if(oc != null){
                condicionPago = oc.getCondicionPago()+"-"+oc.getCondicionPagoDescripcion();
            }

            ExcelDefault.setValueCell(prefacturaData.getReferencia(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getSociedad().getRazonSocial(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getProveedorRuc(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getUsuarioComprador(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getCentro(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getProveedorRazonSocial(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getEstadoPrefactura().getDescripcion(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell((prefacturaData.getNumeroDocumentoContable() == null ? "" : prefacturaData.getNumeroDocumentoContable()), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getFechaEmision(), dataRow.createCell(contador), "D", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(new Date(prefacturaData.getFechaRecepcion().getTime()), dataRow.createCell(contador), "D", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getTotal().toString(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getIndicadorImpuesto(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(ultimoUser, dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(ultimoDate, dataRow.createCell(contador), "D", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(condicionPago, dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getMotivoRechazo(), dataRow.createCell(contador), "S", cellStyleList);
        }
        return book;
    }


    public SXSSFWorkbook descargarListaPrefacturaExcelSXLSXconFiltros(PrefacturaExcelRequestDto requestDto) {
        logger.error("PrefacturaExcelRequestDto : " + requestDto.toString());

        String comprador = requestDto.getComprador();
        if (comprador != null)
            comprador = "%" + comprador + "%";

        String centro = requestDto.getCentro();
        if (centro != null)
            centro = "%" + centro + "%";

        List<Prefactura> prefacturaList = prefacturaRepository.getPrefacturaListForExcel(requestDto.getFechaEmisionInicio(), requestDto.getFechaEmisionFin(), requestDto.getFechaEntradaInicio(), DateUtils.utilDateAtEndOfDay(requestDto.getFechaEntradaFin()), requestDto.getRuc(), requestDto.getReferencia(), comprador, centro, requestDto.getIdEstado());

        Comparator<Prefactura> prefacturaComparator;
        boolean reversed = requestDto.getSortReversed() != null && requestDto.getSortReversed().equals("1");
        String sortCampo = requestDto.getSortCampo() != null ? requestDto.getSortCampo() : "";

        switch (sortCampo) {
            case "filtroCentro":
                if (!reversed)
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getCentro));
                else
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getCentro, Comparator.reverseOrder()));
                break;
            case "filtroComprador":
                if (!reversed)
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getUsuarioComprador));
                else
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getUsuarioComprador, Comparator.reverseOrder()));
                break;
            case "filtroDocumentoErp":
                if (!reversed)
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getNumeroDocumentoContable));
                else
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getNumeroDocumentoContable, Comparator.reverseOrder()));
                break;
            case "filtroEstado":
                if (!reversed)
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(p -> p.getEstadoPrefactura().getDescripcion()));
                else
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(p -> p.getEstadoPrefactura().getDescripcion(), Comparator.reverseOrder()));
                break;
            case "filtroFechaEmision":
                if (!reversed)
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getFechaEmision));
                else
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getFechaEmision, Comparator.reverseOrder()));
                break;
            case "filtroFechaEntrada":
                if (!reversed)
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getFechaRecepcion));
                else
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getFechaRecepcion, Comparator.reverseOrder()));
                break;
            case "filtroImporte":
                if (!reversed)
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getTotal));
                else
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getTotal, Comparator.reverseOrder()));
                break;
            case "filtroIndicador":
                if (!reversed)
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getIndicadorImpuesto));
                else
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getIndicadorImpuesto, Comparator.reverseOrder()));
                break;
            case "filtroRazonSocial":
                if (!reversed)
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getProveedorRazonSocial));
                else
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getProveedorRazonSocial, Comparator.reverseOrder()));
                break;
            case "filtroReferencia":
                if (!reversed)
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getReferencia));
                else
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getReferencia, Comparator.reverseOrder()));
                break;
            case "filtroRuc":
                if (!reversed)
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getProveedorRuc));
                else
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getProveedorRuc, Comparator.reverseOrder()));
                break;
            case "filtroSociedad":
                if (!reversed)
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(p -> p.getSociedad().getRazonSocial()));
                else
                    prefacturaComparator = Comparator.nullsLast(Comparator.comparing(p -> p.getSociedad().getRazonSocial(), Comparator.reverseOrder()));
                break;
            default:
                prefacturaComparator = Comparator.nullsLast(Comparator.comparing(Prefactura::getFechaRecepcion, Comparator.reverseOrder()));
        }

        List<Prefactura> filteredPefacturaList = prefacturaList.stream()
                .filter(requestDto.getFiltroCentro() == null ? p -> true : p -> p.getCentro() != null && p.getCentro().toLowerCase().contains(requestDto.getFiltroCentro().toLowerCase()))
                .filter(requestDto.getFiltroComprador() == null ? p -> true : p -> p.getUsuarioComprador() != null && p.getUsuarioComprador().toLowerCase().contains(requestDto.getFiltroComprador().toLowerCase()))
                .filter(requestDto.getFiltroDocumentoErp() == null ? p -> true : p -> p.getNumeroDocumentoContable() != null && p.getNumeroDocumentoContable().toLowerCase().contains(requestDto.getFiltroDocumentoErp().toLowerCase()))
                .filter(requestDto.getFiltroEstado() == null ? p -> true : p -> p.getIdEstadoPrefactura() != null && p.getEstadoPrefactura() != null && p.getEstadoPrefactura().getDescripcion().toLowerCase().contains(requestDto.getFiltroEstado().toLowerCase()))
                .filter(requestDto.getFiltroFechaEmision() == null ? p -> true : p -> p.getFechaEmision() != null && p.getFechaEmision().compareTo(requestDto.getFiltroFechaEmision()) == 0)
                .filter(requestDto.getFiltroFechaEntrada() == null ? p -> true : p -> p.getFechaRecepcion() != null && DateUtils.timestampToUtilDateAtStartOfDay(p.getFechaRecepcion()).compareTo(requestDto.getFiltroFechaEntrada()) == 0)
                .filter(requestDto.getFiltroImporte() == null ? p -> true : p -> p.getTotal() != null && p.getTotal().toString().contains(requestDto.getFiltroImporte().toLowerCase()) || p.getCodigoMondeda().toLowerCase().contains(requestDto.getFiltroImporte().toLowerCase()))
                .filter(requestDto.getFiltroIndicador() == null ? p -> true : p -> p.getIndicadorImpuesto() != null && p.getIndicadorImpuesto().toLowerCase().contains(requestDto.getFiltroIndicador().toLowerCase()))
                .filter(requestDto.getFiltroRazonSocial() == null ? p -> true : p -> p.getProveedorRazonSocial() != null && p.getProveedorRazonSocial().toLowerCase().contains(requestDto.getFiltroRazonSocial().toLowerCase()))
                .filter(requestDto.getFiltroReferencia() == null ? p -> true : p -> p.getReferencia() != null && p.getReferencia().toLowerCase().contains(requestDto.getFiltroReferencia().toLowerCase()))
                .filter(requestDto.getFiltroRuc() == null ? p -> true : p -> p.getProveedorRuc() != null && p.getProveedorRuc().toLowerCase().contains(requestDto.getFiltroRuc().toLowerCase()))
                .filter(requestDto.getFiltroSociedad() == null ? p -> true : p -> p.getCodigoSociedad() != null && p.getSociedad() != null && p.getSociedad().getRazonSocial().toLowerCase().contains(requestDto.getFiltroSociedad().toLowerCase()))
                .sorted(prefacturaComparator)
                .collect(Collectors.toList());


        SXSSFWorkbook book = new SXSSFWorkbook(100);
        XSSFWorkbook xbook = book.getXSSFWorkbook();
        SXSSFSheet sheet = book.createSheet();
        sheet.trackAllColumnsForAutoSizing();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, "Prefacturas");
        int nroColumnas = ExcelDefault.createTitleAndWidth(xbook, sheet, "com/incloud/hcp/excel/PrefacturaListado.xml", "Listado de Prefacturas", null);
        for (int i = 0; i < nroColumnas; i++) {
            sheet.autoSizeColumn(i, true);
        }
        sheet.untrackAllColumnsForAutoSizing();

        XSSFCellStyle cellStyle01 = ExcelDefault.devuelveCellStyle(xbook, new Color(0, 0, 1), new Color(226, 239, 218), false, (short) 10);
        XSSFCellStyle cellStyle02 = ExcelDefault.devuelveCellStyle(xbook, new Color(0, 0, 192), new Color(255, 255, 255), false, (short) 10);
        List<CellStyle> cellStyleList = null;
        List<CellStyle> cellStyleList01 = ExcelDefault.generarCellStyle(xbook, cellStyle01);
        List<CellStyle> cellStyleList02 = ExcelDefault.generarCellStyle(xbook, cellStyle02);
        boolean filaImpar = true;

        for (Prefactura prefacturaData : filteredPefacturaList) {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            Row dataRow = sheet.createRow(i + 1);
            int contador = 0;
            if (filaImpar) {
                cellStyleList = cellStyleList01;
            } else {
                cellStyleList = cellStyleList02;
            }
            filaImpar = !filaImpar;

            String ultimoUser="";
            String condicionPago="";
            Date ultimoDate= new Date();
            switch (prefacturaData.getIdEstadoPrefactura()){
                case 2:
                    ultimoDate  = prefacturaData.getFechaRegistroSap();
                    if(prefacturaData.getFechaRegistroSap()!=null)
                        ultimoUser=prefacturaData.getUsuarioRegistroSap();
                    break;
                case 3:
                    ultimoDate  = prefacturaData.getFechaDescarte();
                    ultimoUser=logTransaccionService.getUsuarioTransaccionBD("BD_MODIFICAR","PREFACTURA",prefacturaData.getId());
                    break;
                case 5:
                    ultimoDate  = prefacturaData.getFechaDescarte();
                    ultimoUser=prefacturaData.getUsuarioRegistroSap();
                    break;
                default:
                    ultimoDate  = prefacturaData.getFechaRegistroSap();
                    ultimoUser=prefacturaData.getUsuarioRegistroSap();
                    break;
            }

            //Mejora 3 :: Item 6
            OrdenCompra oc = ordenCompraService.getOrdenCompraByNOCompra(prefacturaData.getCadenaNumerosOrdenCompra());
            if(oc != null){
                condicionPago = oc.getCondicionPago()+"-"+oc.getCondicionPagoDescripcion();
            }

            ExcelDefault.setValueCell(prefacturaData.getReferencia(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getSociedad().getRazonSocial(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getProveedorRuc(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getUsuarioComprador(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getCentro(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getProveedorRazonSocial(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getEstadoPrefactura().getDescripcion(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell((prefacturaData.getNumeroDocumentoContable() == null ? "" : prefacturaData.getNumeroDocumentoContable()), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getFechaEmision(), dataRow.createCell(contador), "D", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(new Date(prefacturaData.getFechaRecepcion().getTime()), dataRow.createCell(contador), "D", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getTotal().toString(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getIndicadorImpuesto(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(ultimoUser, dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(ultimoDate, dataRow.createCell(contador), "D", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(condicionPago, dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(prefacturaData.getMotivoRechazo(), dataRow.createCell(contador), "S", cellStyleList);
        }
        return book;
    }


    private String completarReferenciaNotacionSunat(String referencia) {
        String[] arrayReferencia = referencia.split("-");
        String serie = arrayReferencia[0].trim();
        String correlativo = arrayReferencia[1].trim();

        if (correlativo.length() < 8)
            correlativo = String.format("%08d", Integer.parseInt(correlativo));

        return serie + "-" + correlativo;
    }


    private String getReferenciaNotacionSap(String referencia) {
        String[] arrayReferencia = referencia.split("-");
        String serie;
        String correlativo;

        if (arrayReferencia[0].equals("01")) {
            serie = arrayReferencia[1].trim();
            correlativo = arrayReferencia[2].trim();
        } else {
            serie = arrayReferencia[0].trim();
            correlativo = arrayReferencia[1].trim();
        }

        if (serie.length() == 4)
            serie = "0".concat(serie);

        if (correlativo.length() == 8)
            correlativo = correlativo.substring(1);
        else if (correlativo.length() < 7)
            correlativo = String.format("%07d", Integer.parseInt(correlativo));

        return "01-" + serie + "-" + correlativo;
    }

    @Override
    public String actualizarPrefacturasFechaPagoVencimiento(String sFechaRegistroIni, String sFechaRegistroFin, boolean actualizacionManual) throws Exception {

        logger.error("<--LOG_MC-->:0sFechaRegistroIni:"+sFechaRegistroIni);
        logger.error("<--LOG_MC-->:0sFechaRegistroFin:"+sFechaRegistroFin);
        String dssFechaRegistroIni = sFechaRegistroIni.substring(0,4)+"-"+ sFechaRegistroIni.substring(4,6)+"-"+ sFechaRegistroIni.substring(6,8);
        String dssFechaRegistroFin = sFechaRegistroFin.substring(0,4)+"-"+ sFechaRegistroFin.substring(4,6)+"-"+ sFechaRegistroFin.substring(6,8);
        logger.error("<--LOG_MC-->:dssFechaRegistroIni:"+dssFechaRegistroIni);
        logger.error("<--LOG_MC-->:dssFechaRegistroFin:"+dssFechaRegistroFin);

//        Date dFechaRegistroIni = new Date();
//        Date dFechaRegistroFin = new Date();

//        dFechaRegistroIni = DateUtils.convertStringToDate(dssFechaRegistroIni);
        Date dFechaRegistroIni=new SimpleDateFormat("yyyy-MM-dd").parse(dssFechaRegistroIni);
        logger.error("<--LOG_MC-->:dFechaRegistroIni:"+dssFechaRegistroIni);
//        dFechaRegistroFin = DateUtils.convertStringToDate(dssFechaRegistroFin);
        Date dFechaRegistroFin=new SimpleDateFormat("yyyy-MM-dd").parse(dssFechaRegistroFin);
        logger.error("<--LOG_MC-->:dFechaRegistroFin:"+dssFechaRegistroFin);

        List<Prefactura> prefacturaList = prefacturaRepository.getPrefacturaByFechaRegistroBetween(dFechaRegistroIni, dFechaRegistroFin);
        logger.error("<--LOG_MC-->:01:");
        List<ComprobantePagoDto> comprobantePagoDtoList = new ArrayList<>();
        logger.error("<--LOG_MC-->:02:");
        comprobantePagoDtoList = jcoComprobantePagoService.extraerComprobantePagoListRFC(sFechaRegistroIni, sFechaRegistroFin, "", "");
        logger.error("<--LOG_MC-->:03-prefacturaList:"+prefacturaList.size());
        logger.error("<--LOG_MC-->:03-prefacturaList:"+prefacturaList.toString());
        logger.error("<--LOG_MC-->:03-comprobantePagoDtoList:"+comprobantePagoDtoList.size());
        logger.error("<--LOG_MC-->:03-comprobantePagoDtoList:"+comprobantePagoDtoList.toString());
        for(Prefactura it:prefacturaList) {
            if(it.getFechaVencimiento() == null || it.getFechaPago() == null) {
                for (ComprobantePagoDto itx : comprobantePagoDtoList) {
                    if (itx.getNumeroDocumentoContable() != null) {
                        if (!itx.getNumeroDocumentoContable().equals("")) {
                            if (it.getNumeroDocumentoContable() != null && it.getNumeroDocumentoContable().equals(itx.getNumeroDocumentoContable())&& it.getEjercicio().equals(itx.getEjercicio())&& it.getCodigoSociedad().equals(itx.getSociedad().getCodigoSociedad())) {
                                it.setFechaPago(itx.getFechaPagoNuevo());
                                it.setFechaVencimiento(itx.getFechaVencimiento());
                                logger.error("<--LOG_MC-->:03.1:");
                                prefacturaRepository.save(it);
                                logger.error("<--LOG_MC-->:03.2:");
                            }
                        }
                    }
                }
            }
        }
        logger.error("<--LOG_MC-->:04:");
        return "Ok";
    }

    @Override
    public String rechazarPrefacturasSinAdjuntos(Date fechaRecepcion,Token token) {
        Integer count = 0;
        String message = "";
        Date fechaRecepcionInicio = DateUtils.sumarRestarDias(fechaRecepcion, -1);
        logger.error("fechaRecepcionInicio- " + fechaRecepcionInicio);
        Date fechaRecepcionFin =  DateUtils.sumarRestarMinutos(fechaRecepcion, -5);
        logger.error("fechaRecepcionFin- " + fechaRecepcionFin);
        List<Prefactura> prefacturas = this.prefacturaRepository.getPrefacturasByFechaAndEstado(fechaRecepcionInicio, fechaRecepcionFin, 1);
        List<Prefactura> prefacturasE = prefacturas.stream().filter(x -> !Character.isDigit(x.getReferencia().charAt(0))).collect(Collectors.toList());
        for (Prefactura p : prefacturasE){
            boolean faltanAdjuntos  = this.validarAdjuntosPrefactura(p.getId());
            if(faltanAdjuntos ){
                this.rechazarPrefactura(p.getId(), "No se subieron correctamente los adjuntos. [AUTOMÁTICO]",token);
                message = message + String.format("Se rechazó la factura %s \n", p.getReferencia());
            }
        }
        return message;
    }
    @Override
    public Boolean validarAdjuntosPrefactura(Integer idPrefactura) {
        Optional<Prefactura> optionalPrefactura = prefacturaRepository.getOneById(idPrefactura);
        Prefactura prefactura = optionalPrefactura.get();
        //if (StringUtils.isBlank(prefactura.getXmlEcmPath()) || StringUtils.isBlank(prefactura.getPdfEcmPath())){
        if (StringUtils.isBlank(prefactura.getXmlEcmPathCf()) || StringUtils.isBlank(prefactura.getPdfEcmPathCf())){
            logger.error("validarAdjuntosPrefactura " + idPrefactura);
            LogTransaccion logRechazoJob = new LogTransaccion();
            logRechazoJob.setLogFecha(DateUtils.getCurrentTimestamp());
            logRechazoJob.setTipoRegistro("RechazarPrefacturasSinAdjuntos - scheduleRechazarPrefacturasSinAdjuntos");
            logRechazoJob.setEnvioTrama("Rechazo facturaJob id: " + idPrefactura + ", xmlPath: " + prefactura.getXmlEcmPath() + ", pdfPath: " + prefactura.getPdfEcmPath() + ", xmlPathCF: " + prefactura.getXmlEcmPathCf() + ", pdfPathCF" + prefactura.getPdfEcmPathCf());
            this.logTransaccionRepository.save(logRechazoJob);
            return true;
        }
        return false;
    }
}
