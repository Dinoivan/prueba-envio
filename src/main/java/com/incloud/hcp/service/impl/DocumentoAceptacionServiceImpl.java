package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.DocumentoAceptacion;
import com.incloud.hcp.domain.EstadoDocumentoAceptacion;
import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.dto.DocumentoAceptacionDto;
import com.incloud.hcp.jco.documentoAceptacion.service.JCODocumentoAceptacionService;
import com.incloud.hcp.myibatis.mapper.DocumentoAceptacionNeoMapper;
import com.incloud.hcp.pdf.PdfGeneratorFactory;
import com.incloud.hcp.pdf.bean.ParameterConformidadServicioPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterEntradaMercaderiaPdfDTO;
import com.incloud.hcp.repository.DocumentoAceptacionRepository;
import com.incloud.hcp.repository.EstadoDocumentoAceptacionRepository;
import com.incloud.hcp.repository.OrdenCompraRepository;
import com.incloud.hcp.service.DocumentoAceptacionService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DocumentoAceptacionServiceImpl implements DocumentoAceptacionService {

    private DocumentoAceptacionRepository documentoAceptacionRepository;
    private JCODocumentoAceptacionService jcoDocumentoAceptacionService;
    private OrdenCompraRepository ordenCompraRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private EstadoDocumentoAceptacionRepository estadoDocumentoAceptacionRepository;

    @Autowired
    private DocumentoAceptacionNeoMapper documentoAceptacionNeoMapper;

    @Autowired
    public DocumentoAceptacionServiceImpl(DocumentoAceptacionRepository documentoAceptacionRepository,
                                          JCODocumentoAceptacionService jcoDocumentoAceptacionService,
                                          EstadoDocumentoAceptacionRepository estadoDocumentoAceptacionRepository,
                                          OrdenCompraRepository ordenCompraRepository) {
        this.documentoAceptacionRepository = documentoAceptacionRepository;
        this.jcoDocumentoAceptacionService = jcoDocumentoAceptacionService;
        this.estadoDocumentoAceptacionRepository = estadoDocumentoAceptacionRepository;
        this.ordenCompraRepository = ordenCompraRepository;
    }

    @Override
    public List<DocumentoAceptacion> getAllDocumentoAceptacion() {
        return documentoAceptacionRepository.findAll();
    }

    @Override
    public DocumentoAceptacion getDocumentoAceptacionbyId(Integer idTipoDocumentoAceptacion, Integer idEntregaMercaderia) {
        return documentoAceptacionRepository.getDocumentoAceptacionById(idTipoDocumentoAceptacion, idEntregaMercaderia);
    }

    @Override
    public List<DocumentoAceptacion> getDocumentoAceptacionPorFechasAndRuc(Date fechaInicio, Date fechaFin, String ruc) {
        List<DocumentoAceptacion> documentoAceptacionList = new ArrayList<>();

        if (ruc == null || ruc.isEmpty()) {
            documentoAceptacionList = documentoAceptacionRepository.getDocumentoAceptacionByFechaRegistroBetween(fechaInicio, fechaFin);
        }
        else {
            documentoAceptacionList = documentoAceptacionRepository.getDocumentoAceptacionByFechaRegistroBetweenAndProveedorRuc(fechaInicio, fechaFin, ruc);
        }

        return documentoAceptacionList.stream()
                .filter(da-> da.getIdOrdenCompra() != null) // si el Id de OC es null significa que el numero de OC asociado no se encontro entre las OC liberadas y porlo tanto el doc de aceptacion no es facturable y no debe mostrarse
                .collect(Collectors.toList());
    }

    @Override
    public void extraerDocumentoAceptacionMasivoByRangoFechas(LocalDate fechaInicio, LocalDate fechaFin, boolean aprobarOrdenCompra, boolean enviarCorreoAprobacion){
        logger.error("EXTRACCION DOC_ACEP MASIVA - INICIO: " + fechaInicio.toString());
        logger.error("EXTRACCION DOC_ACEP MASIVA - FIN: " + fechaFin.toString());

        while (fechaInicio.isBefore(fechaFin.plusDays(1))){
            try {
                String currentDateAsSapString = DateUtils.localDateToSapString(fechaInicio);
                jcoDocumentoAceptacionService.extraerDocumentoAceptacionListRFC(currentDateAsSapString, currentDateAsSapString, false, aprobarOrdenCompra, enviarCorreoAprobacion);
                fechaInicio = fechaInicio.plusDays(1);
            }
            catch(Exception e){
                String error = Utils.obtieneMensajeErrorException(e);
                logger.error("ERROR al extraer Documentos de Aceptacion de la fecha " + DateUtils.localDateToString(fechaInicio) + " : " + error);
                fechaInicio = fechaInicio.plusDays(1);
            }
        }
    }

    @Override
    public void extraerGuiasAnuladasDespacho(LocalDate fechaInicio, LocalDate fechaFin, boolean aprobarOrdenCompra, boolean enviarCorreoAprobacion){
        logger.error("EXTRACCION DOC_ACEP MASIVA - INICIO: " + fechaInicio.toString());
        logger.error("EXTRACCION DOC_ACEP MASIVA - FIN: " + fechaFin.toString());

        while (fechaInicio.isBefore(fechaFin.plusDays(1))){
            try {
                String currentDateAsSapString = DateUtils.localDateToSapString(fechaInicio);
                jcoDocumentoAceptacionService.extraerDespachosAnuladosListRFC(currentDateAsSapString, currentDateAsSapString, false, aprobarOrdenCompra, enviarCorreoAprobacion);
                fechaInicio = fechaInicio.plusDays(1);
            }
            catch(Exception e){
                String error = Utils.obtieneMensajeErrorException(e);
                logger.error("ERROR al extraer Documentos de Aceptacion de la fecha " + DateUtils.localDateToString(fechaInicio) + " : " + error);
                fechaInicio = fechaInicio.plusDays(1);
            }
        }
    }

    @Override
    public String extraerDocumentoAceptacionByNumOrdenCompraAndNumDocAceptacion(String numeroOrdenCompra, String numeroDocumentoAceptacion, boolean aprobarOrdenCompra, boolean enviarCorreoAprobacion){
        String header = "EXTRACCION DOC_ACEP POR NUMERO_OC Y NUMERO_DA: " + numeroOrdenCompra + " / " + numeroDocumentoAceptacion;
        String respuesta = "";
        logger.error(header + " // " + DateUtils.getCurrentTimestamp().toString());

        try {
            Optional<OrdenCompra> optionalOrdenCompra = ordenCompraRepository.getOrdenCompraActivaByNumero(numeroOrdenCompra);
            if (optionalOrdenCompra.isPresent()) {
                Optional<DocumentoAceptacion> opDocumentoAceptacion = documentoAceptacionRepository.findByNumeroDocumentoAceptacion(numeroDocumentoAceptacion);
                if (opDocumentoAceptacion.isPresent()) {
                    DocumentoAceptacion documentoAceptacion = opDocumentoAceptacion.get();
                    respuesta = "El documento ya fue publicado previamente (asociado a Orden de Compra '" + documentoAceptacion.getNumeroOrdenCompra() + "') y actualmente esta en estado '" + documentoAceptacion.getEstadoDocumentoAceptacion().getDescripcion() + "'";
                    logger.error(header + " // " + respuesta);
                } else {
                    jcoDocumentoAceptacionService.extraerDocumentoAceptacionListRFC(numeroOrdenCompra, numeroDocumentoAceptacion, true, aprobarOrdenCompra, enviarCorreoAprobacion);
                    respuesta = "El documento no fue publicado previamente y los datos de busqueda fueron enviados a SAP, verificar si hubo publicacion exitosa";
                    logger.error(header + " // " + respuesta);
                }
            } else {
                respuesta = "El documento no fue publicado porque la OC no existe en Iprovider.";
                logger.error(header + " // " + respuesta);
            }
        }
        catch(Exception e){
            String error = Utils.obtieneMensajeErrorException(e);
            logger.error(header + " // EXCEPCION: " + error);
            throw new RuntimeException("EXCEPCION al extraer Documento de Aceptacion '" + numeroDocumentoAceptacion + "' asociado a la Orden de Compra '" + numeroOrdenCompra + "' : " + error);
        }
        return respuesta;
    }

    @Override
    public String getEntregaMercaderiaGenerateContent(ParameterEntradaMercaderiaPdfDTO parameterEntradaMercaderiaPdfDTO){
        byte[] generateEntradaMercaderia =PdfGeneratorFactory.getJasperGenerator().generateEntradaMercaderia(parameterEntradaMercaderiaPdfDTO);
        return Base64.getEncoder().encodeToString(generateEntradaMercaderia);
    }

    @Override
    public String getConformidadServicioGenerateContent(ParameterConformidadServicioPdfDTO parameterConformidadServicioPdfDTO){
        byte[] generateConformidadServicio = PdfGeneratorFactory.getJasperGenerator().generateConformidadServicio(parameterConformidadServicioPdfDTO);
        return Base64.getEncoder().encodeToString(generateConformidadServicio);
    }

    @Override
    public List<DocumentoAceptacion> getDocumentoAceptacionPorFechasAndRucPag(Date fechaInicio, Date fechaFin, String ruc, String nroOC, String nroGP, Integer nroRegistros, Integer paginaMostrar, Integer tipoDocumento) {
        List<DocumentoAceptacion> documentoAceptacionList = new ArrayList<>();
        logger.error("<--MC_LOGGER--> :fechaInicio:"+fechaInicio);
        logger.error("<--MC_LOGGER--> :fechaFin:"+fechaFin);
        logger.error("<--MC_LOGGER--> :ruc:"+ruc);
        logger.error("<--MC_LOGGER--> :nroOC:"+nroOC);
        logger.error("<--MC_LOGGER--> :nroGP:"+nroGP);
        logger.error("<--MC_LOGGER--> :nroRegistros:"+nroRegistros);
        logger.error("<--MC_LOGGER--> :paginaMostrar:"+paginaMostrar);
        logger.error("<--MC_LOGGER--> :tipoDocumento:"+tipoDocumento);



        if (ruc == null || ruc.isEmpty()) {
            if (nroGP == null || nroGP.isEmpty()){
                documentoAceptacionList = documentoAceptacionRepository.getDocumentoAceptacionByFechaRegistroBetweenPAgb(fechaInicio, fechaFin, nroOC, nroRegistros, paginaMostrar, tipoDocumento);
            }else{
                documentoAceptacionList = documentoAceptacionRepository.getDocumentoAceptacionByFechaRegistroBetweenPAga(fechaInicio, fechaFin, nroOC, nroGP, nroRegistros, paginaMostrar, tipoDocumento);
            }
        }
        else {
            if (nroGP == null || nroGP.isEmpty()){
                documentoAceptacionList = documentoAceptacionRepository.getDocumentoAceptacionByFechaRegistroBetweenAndProveedorRucPagb(fechaInicio, fechaFin, ruc, nroOC, nroRegistros, paginaMostrar, tipoDocumento);
            }else{
                documentoAceptacionList = documentoAceptacionRepository.getDocumentoAceptacionByFechaRegistroBetweenAndProveedorRucPaga(fechaInicio, fechaFin, ruc, nroOC, nroGP,nroRegistros, paginaMostrar, tipoDocumento);
            }
        }

        return documentoAceptacionList.stream()
                .filter(da-> da.getIdOrdenCompra() != null) // si el Id de OC es null significa que el numero de OC asociado no se encontro entre las OC liberadas y porlo tanto el doc de aceptacion no es facturable y no debe mostrarse
                .collect(Collectors.toList());
    }

    @Override
    public String updateDocumentoAceptacionStatus(String numDocumentoAceptacion, String numOrdenCompra, String descripcionEstado) {
        DocumentoAceptacion documentoAceptacion = this.documentoAceptacionRepository.getDocumentoAceptacionByNumeroDocumentoAceptacionAndNumeroOrdenCompra(numDocumentoAceptacion, numOrdenCompra);
        EstadoDocumentoAceptacion estadoDocumentoAceptacion = this.estadoDocumentoAceptacionRepository.getByDescripcion(descripcionEstado);
        documentoAceptacion.setIdEstadoDocumentoAceptacion(estadoDocumentoAceptacion.getId());
        this.documentoAceptacionRepository.save(documentoAceptacion);
        return String.format("Documento aceptación %s actualizado a estado %s",
                documentoAceptacion.getNumeroDocumentoAceptacion(),
                estadoDocumentoAceptacion.getDescripcion());
    }
    @Override
    public List<DocumentoAceptacionDto> getDocumentoAceptacionFiltro(Date fechaInicio, Date fechaFin, String ruc, String numeroDocumentoAceptacion, String numeroOrdenCompra, String numeroGuiaProveedor, Date fechaContabilizacionDesde, Date fechaContabilizacionHasta) {
        logger.error("fechaInicio_ " + fechaInicio +
                " fechaFin_ " + fechaFin +
                " fRuc_ " + ruc +
                " fNumeroDocumentoAceptacion_ " + numeroDocumentoAceptacion +
                " fNumeroOrdenCompra_ " + numeroOrdenCompra +
                " fNumeroGuiaProveedor_ " + numeroGuiaProveedor +
                " nfechaContabilizacionDesde_ " + fechaContabilizacionDesde +
                " nfechaContabilizacionHasta_ " + fechaContabilizacionHasta);
        String fRuc = ruc != null ? ruc : "";
        String fNumeroDocumentoAceptacion = numeroDocumentoAceptacion != null ? numeroDocumentoAceptacion : "";
        String fNumeroOrdenCompra = numeroOrdenCompra != null ? numeroOrdenCompra : "";
        String fNumeroGuiaProveedor = numeroGuiaProveedor != null ? numeroGuiaProveedor : "";
        Date nfechaContabilizacionDesde = fechaContabilizacionDesde != null ? fechaContabilizacionDesde : null;
        Date nfechaContabilizacionHasta = fechaContabilizacionHasta != null ? fechaContabilizacionHasta : null;
        return this.documentoAceptacionNeoMapper.getDocuAcepPorFecPrefactura(
                fechaInicio,
                fechaFin,
                fRuc,
                fNumeroDocumentoAceptacion,
                fNumeroOrdenCompra,
                fNumeroGuiaProveedor,
                nfechaContabilizacionDesde,
                nfechaContabilizacionHasta);
    }
}
