package com.incloud.hcp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incloud.hcp.bean.FileBase64;
import com.incloud.hcp.bean.MensajePrefactura;
import com.incloud.hcp.bean.Response;
import com.incloud.hcp.config.excel.ExcelType;
import com.incloud.hcp.domain.DocumentoAceptacion;
import com.incloud.hcp.domain.InformacionNoticia;
import com.incloud.hcp.domain.Prefactura;
import com.incloud.hcp.domain.StorageDocument;
import com.incloud.hcp.dto.*;
import com.incloud.hcp.enums.PrefacturaOpcionEnum;
import com.incloud.hcp.enums.TipoArchivoEnum;
import com.incloud.hcp.exception.InvalidOptionException;
import com.incloud.hcp.jco.prefactura.dto.PrefacturaRFCResponseDto;
import com.incloud.hcp.repository.InformacionNoticiaRepository;
import com.incloud.hcp.repository.PrefacturaRepository;
import com.incloud.hcp.service.InformacionNoticiaService;
import com.incloud.hcp.service.PrefacturaNeoService;
import com.incloud.hcp.service.PrefacturaService;
import com.incloud.hcp.service.StorageDocumentService;
import com.incloud.hcp.service.cmiscf.CmisBaseService;
import com.incloud.hcp.service.cmiscf.bean.CmisFile;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import com.sap.cloud.security.xsuaa.token.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/api/prefactura")
public class PrefacturaRest {

    private PrefacturaService prefacturaService;
    private PrefacturaRepository prefacturaRepository;
    private StorageDocumentService storageDocumentService;
    private PrefacturaNeoService prefacturaNeoService;
    private CmisBaseService cmisService;
    private InformacionNoticiaService informacionNoticiaService;
    private InformacionNoticiaRepository informacionNoticiaRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String OPCION_INVALIDA = "'%s' no es una opción valida. Las opciones aceptadas son '%s' y '%s'.";

    @Value("${cfg.folder.ecm}")
    private String folderName;

    @Value("${url.cmis.migracion}")
    private String urlcmismigracion;

    @Autowired
    public PrefacturaRest(PrefacturaService prefacturaService,
                          PrefacturaRepository prefacturaRepository,
                          StorageDocumentService storageDocumentService,
                          CmisBaseService cmisService,
                          PrefacturaNeoService prefacturaNeoService,
                          InformacionNoticiaService informacionNoticiaService,
                          InformacionNoticiaRepository informacionNoticiaRepository) {
        this.prefacturaService = prefacturaService;
        this.prefacturaRepository = prefacturaRepository;
        this.storageDocumentService = storageDocumentService;
        this.cmisService = cmisService;
        this.prefacturaNeoService = prefacturaNeoService;
        this.informacionNoticiaService = informacionNoticiaService;
        this.informacionNoticiaRepository = informacionNoticiaRepository;
    }

    @GetMapping(value = "/getPrefacturaById/{idPrefactura}")
    public ResponseEntity<Prefactura> getPrefacturaById(@PathVariable("idPrefactura") Integer idPrefactura) {
        try {
            Optional<Prefactura> opPrefactura = prefacturaRepository.getOneById(idPrefactura);
            if (opPrefactura.isPresent()) {
                return new ResponseEntity<>(opPrefactura.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/getPrefacturaListByRucAndReferencia/{ruc}/{referencia}")
    public ResponseEntity<List<Prefactura>> getPrefacturaListByRucAndReferencia(@PathVariable("ruc") String ruc,
                                                                                @PathVariable("referencia") String referencia) {
        try {
            List<Prefactura> prefacturaList = prefacturaRepository.getPrefacturaListByRucAndReferencia(ruc, referencia);

            if (prefacturaList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(prefacturaList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/getPrefacturaList/{FechaInicio}/{FechaFin}")
    public ResponseEntity<List<Prefactura>> getPrefacturaList(
            @PathVariable("FechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @PathVariable("FechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
            @RequestParam(value = "ruc", required = false) String ruc) {
        try {
            List<Prefactura> prefacturaList = prefacturaService.getPrefacturaListPorFechasAndRuc(fechaInicio, fechaFin, ruc);

            if (prefacturaList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(prefacturaList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/getPrefacturaListByAnyDate")
    public ResponseEntity<List<Prefactura>> getPrefacturaListByAnyDate(
            @RequestParam(value = "fechaEmisionInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEmisionInicio,
            @RequestParam(value = "fechaEmisionFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEmisionFin,
            @RequestParam(value = "fechaEntradaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEntradaInicio,
            @RequestParam(value = "fechaEntradaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEntradaFin,
            @RequestParam(value = "ruc", required = false) String ruc,
            @RequestParam(value = "referencia", required = false) String referencia) {
        try {
            List<Prefactura> prefacturaList = prefacturaService.getPrefacturaListByFechasEmisionAndFechaEntradaAndRuc(fechaEmisionInicio, fechaEmisionFin, fechaEntradaInicio, fechaEntradaFin, ruc, referencia);

            if (prefacturaList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(prefacturaList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/getPrefacturaListByAllFilters")
    public ResponseEntity<List<PrefacturaAprobacionDTO>> getPrefacturaListByAllFilters(
            @RequestParam(value = "fechaEmisionInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEmisionInicio,
            @RequestParam(value = "fechaEmisionFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEmisionFin,
            @RequestParam(value = "fechaEntradaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEntradaInicio,
            @RequestParam(value = "fechaEntradaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEntradaFin,
            @RequestParam(value = "ruc", required = false) String ruc,
            @RequestParam(value = "referencia", required = false) String referencia,
            @RequestParam(value = "comprador", required = false) String comprador,
            @RequestParam(value = "centro", required = false) String centro,
            @RequestParam(value = "idEstado", required = false) Integer idEstado) {
        try {
//            List<Prefactura> prefacturaList = prefacturaService.getPrefacturaList(fechaEmisionInicio, fechaEmisionFin, fechaEntradaInicio, fechaEntradaFin, ruc, referencia, comprador, centro, idEstado);
            List<PrefacturaAprobacionDTO> prefacturaList = prefacturaService.getPrefacturaList(fechaEmisionInicio, fechaEmisionFin, fechaEntradaInicio, fechaEntradaFin, ruc, referencia, comprador, centro, idEstado);

            if (prefacturaList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(prefacturaList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }



    @GetMapping(value = "/getDocumentoAceptacionListByIdPrefactura/{idPrefactura}")
    public ResponseEntity<List<DocumentoAceptacion>> getDocumentoAceptacionListDePrefactura(@PathVariable("idPrefactura") Integer idPrefactura) {
        try {
            List<DocumentoAceptacion> documentoAceptacionList = prefacturaService.obtenerDocumentoAceptacionListByIdPrefactura(idPrefactura);
            if (documentoAceptacionList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(documentoAceptacionList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "/nuevaPrefactura")
    public ResponseEntity<Integer> ingresarNuevaPrefactura(@RequestBody PrefacturaDto prefacturaDto,@AuthenticationPrincipal Token token) {
        try {
            Integer idPrefactura = prefacturaService.ingresarNuevaPrefactura(prefacturaDto,token);
            if (idPrefactura.compareTo(-422) == 0){
                return new ResponseEntity<>(idPrefactura, HttpStatus.UNPROCESSABLE_ENTITY);
            }

            return new ResponseEntity<>(idPrefactura, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PutMapping(value = "/descartarPrefactura/{idPrefactura}")
    public ResponseEntity<String> descartarPrefacturaById(@PathVariable("idPrefactura") Integer idPrefactura,
                                                          @AuthenticationPrincipal Token token) {
        try {
            String respuesta = prefacturaService.descartarPrefactura(idPrefactura,token);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
    @PostMapping(value = "/rechazarPrefactura/{idPrefactura}/{textoRechazo}")
    public ResponseEntity<PrefacturaRFCResponseDto> rechazarPrefactura(
            @PathVariable("idPrefactura") Integer idPrefactura,
            @PathVariable("textoRechazo") String textoRechazo,
            @AuthenticationPrincipal Token token) {
        try {
            return new ResponseEntity<>(prefacturaService.rechazarPrefactura(idPrefactura, textoRechazo,token), HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
    @PutMapping(value = "/registrarRechazarPrefactura/{idPrefactura}/{prefacturaOpcion}")
    public ResponseEntity<PrefacturaRFCResponseDto> registrarRechazarPrefacturaById(
            @PathVariable("idPrefactura") Integer idPrefactura,
            @PathVariable(value = "prefacturaOpcion") PrefacturaOpcionEnum prefacturaOpcion,
            @RequestParam(value = "fechaContabilizacion", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaContabilizacion,
            @RequestParam(value = "fechaBase", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaBase,
            @RequestParam(value = "indicadorImpuesto", required = false) String indicadorImpuesto,
//            @RequestParam(value = "textoRechazo", required = false) String textoRechazo) {
            @RequestBody(required = false) String textoRechazo, @AuthenticationPrincipal Token token){

        String opcion = prefacturaOpcion.toString().trim().toUpperCase();

        if (!opcion.equals(PrefacturaOpcionEnum.REGISTRAR.toString()) && !opcion.equals(PrefacturaOpcionEnum.RECHAZAR.toString()))
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcion, PrefacturaOpcionEnum.REGISTRAR.toString(), PrefacturaOpcionEnum.RECHAZAR.toString()));

        try {
            PrefacturaRFCResponseDto responseDto = new PrefacturaRFCResponseDto();

            if (opcion.equals(PrefacturaOpcionEnum.REGISTRAR.toString())) {
                if (fechaContabilizacion == null || fechaBase == null || indicadorImpuesto == null)
                    throw new InvalidOptionException("Los campos fechaContabilizacion, fechaBase y/o indicadorImpuesto no pueden ser null al " + PrefacturaOpcionEnum.REGISTRAR.toString());

                logger.error("<--MC_LOG-->:PrefacturaRest/registrarRechazarPrefactura:");
                logger.error("<--MC_LOG-->:PrefacturaRest/registrarRechazarPrefactura-REGISTRAR:"+idPrefactura);
                responseDto = prefacturaService.registrarPrefacturaEnSap(idPrefactura, fechaContabilizacion, fechaBase, indicadorImpuesto,token);
            } else { // opcion = RECHAZAR
                if (textoRechazo == null || textoRechazo.isEmpty())
                    throw new InvalidOptionException("El campo de motivo de rechazo es obligatorio al " + PrefacturaOpcionEnum.RECHAZAR.toString());

                logger.error("<--MC_LOG-->:PrefacturaRest/registrarRechazarPrefactura:");
                logger.error("<--MC_LOG-->:PrefacturaRest/registrarRechazarPrefactura-RECHAZAR:"+idPrefactura);
                responseDto = prefacturaService.rechazarPrefactura(idPrefactura, textoRechazo,token);
            }

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @PutMapping(value = "/enviarCorreoAnulacionPrefactura/{idPrefactura}")
    public ResponseEntity<String> enviarCorreoAnulacionPrefacturaById(
            @PathVariable("idPrefactura") Integer idPrefactura,
            @RequestBody(required = false) String textoAnulacion,
            @AuthenticationPrincipal Token token){
        logger.error("enviarCorreoAnulacionPrefactura__ " + textoAnulacion);
           // @RequestParam(value = "textoAnulacion") String textoAnulacion) {
        try {
            logger.error("<--MC_LOG-->:PrefacturaRest/enviarCorreoAnulacionPrefactura:");
            logger.error("<--MC_LOG-->:PrefacturaRest/enviarCorreoAnulacionPrefactura:"+idPrefactura);
            return new ResponseEntity<>(prefacturaService.enviarCorreoAnulacionPrefactura(idPrefactura, textoAnulacion,token), HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "/guardarArchivosPrefactura/{idPrefactura}")
    public ResponseEntity<MensajePrefactura> guardarArchivoPrefactura(
            @PathVariable(value = "idPrefactura") Integer idPrefactura,
            @RequestParam(value = "file") MultipartFile multipartFile) {
        MensajePrefactura mensajePrefactura = new MensajePrefactura();
        String newFolder = folderName;
        logger.error("guardarArchivoPrefactura::001:: " + newFolder);
        CmisFile cmisFile;

        try {
            if (multipartFile != null) {
                String folderId = cmisService.createFolder(newFolder).getNameFolder();//Id();
                logger.error("guardarArchivoPrefactura::002:: " + newFolder);
                cmisFile = cmisService.createDocumento(folderId, multipartFile);
                //String messageSave = prefacturaService.updatePathAdjuntoPrefactura(idPrefactura, cmisFile.getUrl(), cmisFile.getType());
                String messageSave = prefacturaService.updatePathAdjuntoPrefacturaCf(idPrefactura, cmisFile.getUrl(),cmisFile.getId(), cmisFile.getType());
                //  logger.error("Verificar grabado: " + cmisFile.getUrl() + " " + cmisFile.getType());
                mensajePrefactura.setMensajeEcm("Se guardo correctamente el archivo " + cmisFile.getName());
                mensajePrefactura.setMensajeSaveEcm(messageSave);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }

        return new ResponseEntity<>(mensajePrefactura, HttpStatus.OK);
    }

    @PostMapping(path = "/findDocumentByIdPrefactura/{idPrefactura}/{tipoArchivo}")
    public ResponseEntity<StorageDocument> getDocumentByIdPrefacturaAndTipoArchivo(@PathVariable(value = "idPrefactura") Integer idPrefactura,
                                                                     @PathVariable(value = "tipoArchivo") TipoArchivoEnum tipoArchivoEnum) {
        String pathScpEcm = prefacturaService.getFileEcmPath(idPrefactura, tipoArchivoEnum.getFileType());
        if (!pathScpEcm.isEmpty()) {
            StorageDocument storageDocument = storageDocumentService.getDocumentByPath(pathScpEcm, true);
            return new ResponseEntity<>(storageDocument, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(path = "/list-document")
    public ResponseEntity<List<StorageDocument>> getAll(@RequestParam(value = "path") String path) {
        Session session = null;//cmisService.getSession();
        CmisObject objectByPath = session.getObjectByPath(path);
        List<StorageDocument> docs = new ArrayList<>();

        if (objectByPath instanceof Folder) {
            ItemIterable<CmisObject> children = ((Folder) objectByPath).getChildren();
            for (CmisObject cmisObject : children) {
                StorageDocument storageDocument = new StorageDocument();
                storageDocument.setId(cmisObject.getId());
                storageDocument.setName(cmisObject.getName());
                docs.add(storageDocument);
            }
        }
        if (objectByPath instanceof Document) {
            Document document = (Document) objectByPath;
            StorageDocument storageDocument = new StorageDocument();
            storageDocument.setId(document.getId());
            storageDocument.setName(document.getName());
            storageDocument.setMimeType(document.getContentStreamMimeType());
            docs.add(storageDocument);
        }
        return new ResponseEntity<>(docs, HttpStatus.OK);
    }


    @GetMapping(path = "/listDocumentByMaxLenghAndType/{maxLength}/{fileTypeEnum}")
    public ResponseEntity<List<StorageDocument>> getAllByMaxSizeAndType(@PathVariable("maxLength") Long maxLength,
                                                                        @PathVariable(value = "fileTypeEnum") TipoArchivoEnum fileTypeEnum) {
        String fileType = fileTypeEnum.getFileType();
        String folderPath = "/".concat(folderName);
        Session session = null;//cmisService.getSession();
        CmisObject objectByPath = session.getObjectByPath(folderPath);
        List<StorageDocument> docs = new ArrayList<>();

        if (objectByPath instanceof Folder) {
            ItemIterable<CmisObject> children = ((Folder) objectByPath).getChildren();
            for (CmisObject cmisObject : children) {
                if (cmisObject instanceof Document) {
                    String filename = cmisObject.getName();
                    if (filename.substring(filename.length()-3).equalsIgnoreCase(fileType)) {
                        ContentStream contentStream = ((Document) cmisObject).getContentStream();
                        long length = contentStream.getLength();

                        if(length <= maxLength) {
                            StorageDocument storageDocument = new StorageDocument();
                            storageDocument.setId(cmisObject.getId());
                            storageDocument.setName(filename);
                            storageDocument.setLength(String.valueOf(length));
                            docs.add(storageDocument);
                        }
                    }
                }
            }
        }

        return new ResponseEntity<>(docs, HttpStatus.OK);
    }


    @GetMapping(value = "/getPrefacturaPdfById/{idPrefactura}")
    public ResponseEntity<String> getPrefacturaPdfById(@PathVariable("idPrefactura") Integer idPrefactura){
        if (idPrefactura == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        try{
            Optional<Prefactura> opPrefactura = prefacturaRepository.getOneById(idPrefactura);

            if(opPrefactura.isPresent()){
                return new ResponseEntity<>(prefacturaService.getPrefacturaPdfContent(opPrefactura.get()),HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "actualizarPrefacturasAnuladasEnSapByRangoFechas/{fechaInicio}/{fechaFin}")
    public ResponseEntity<PrefacturaAnuladaRespuestaDto> actualizarPrefacturasAnuladas(@PathVariable(value = "fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                                                                       @PathVariable(value = "fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                                                                       @AuthenticationPrincipal Token token) {
        try {
            PrefacturaAnuladaRespuestaDto respuestaDto = prefacturaService.actualizarMasivoPrefacturasAnuladasPorRangoFechas(fechaInicio, fechaFin,token);
            return new ResponseEntity<>(respuestaDto, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "actualizarPrefacturasRegistradasManualmenteEnSapByIdList")
    public ResponseEntity<List<PrefacturaActualizarDto>> actualizarPrefacturasRegistradas(@RequestBody GenericRequestDTO genericRequestDTO,@AuthenticationPrincipal Token token) {
        List<Integer> idList = genericRequestDTO.getIntegerList1();

        if(idList == null || idList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        try {
            List<PrefacturaActualizarDto> prefacturaActualizarDtoRespuestaList = prefacturaService.actualizarPrefacturasRegistradasEnSap(idList,token);
            prefacturaActualizarDtoRespuestaList.forEach(pa -> {
                pa.setPrefactura(null);
            });

            return new ResponseEntity<>(prefacturaActualizarDtoRespuestaList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @GetMapping(value = "/descargarPrefacturaListExcel", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity<?> descargarPrefacturaListExcel(@RequestParam(value = "fechaEmisionInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEmisionInicio,
                                                          @RequestParam(value = "fechaEmisionFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEmisionFin,
                                                          @RequestParam(value = "fechaEntradaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEntradaInicio,
                                                          @RequestParam(value = "fechaEntradaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEntradaFin,
                                                          @RequestParam(value = "ruc", required = false) String ruc,
                                                          @RequestParam(value = "referencia", required = false) String referencia,
                                                          @RequestParam(value = "comprador", required = false) String comprador,
                                                          @RequestParam(value = "centro", required = false) String centro,
                                                          @RequestParam(value = "idEstado", required = false) Integer idEstado,
                                                          HttpServletResponse response) {
        String excelFileName = "ListadoPrefacturas_" + DateUtils.getFechaActualAsStringPattern("yyyy-MM-dd_hh_mm_ss") + ".xlsx";
        SXSSFWorkbook book = this.prefacturaService.descargarListaPrefacturaExcelSXLSX(fechaEmisionInicio, fechaEmisionFin, fechaEntradaInicio, fechaEntradaFin, ruc, referencia, comprador, centro, idEstado);

        try {
            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            book.write(outByteStream);
            byte[] outArray = outByteStream.toByteArray();
            response.setContentType(ExcelType.XLSX.getExtension());
            response.setContentLength(outArray.length);
            response.setHeader("Expires:", "0"); // eliminates browser caching
            response.setHeader("Content-Disposition", "attachment; filename=" + excelFileName);
            OutputStream outStream = response.getOutputStream();
            outStream.write(outArray);
            outStream.flush();

            book.dispose();
            book.close();
        } catch (FileNotFoundException e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        } catch (IOException e) {
            String error = Utils.obtieneMensajeErrorException(e);
            e.printStackTrace();
            throw new RuntimeException(error);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "/descargarPrefacturaListExcelconFiltros", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity<?> descargarPrefacturaListExcelconFiltros(@RequestParam(value = "fechaEmisionInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEmisionInicio,
                                                                    @RequestParam(value = "fechaEmisionFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEmisionFin,
                                                                    @RequestParam(value = "fechaEntradaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEntradaInicio,
                                                                    @RequestParam(value = "fechaEntradaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaEntradaFin,
                                                                    @RequestParam(value = "ruc", required = false) String ruc,
                                                                    @RequestParam(value = "referencia", required = false) String referencia,
                                                                    @RequestParam(value = "comprador", required = false) String comprador,
                                                                    @RequestParam(value = "centro", required = false) String centro,
                                                                    @RequestParam(value = "idEstado", required = false) Integer idEstado,
                                                                    @RequestParam(value = "filtroFechaEmision", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date filtroFechaEmision,
                                                                    @RequestParam(value = "filtroFechaEntrada", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date filtroFechaEntrada,
                                                                    @RequestParam(value = "filtroRuc", required = false) String filtroRuc,
                                                                    @RequestParam(value = "filtroReferencia", required = false) String filtroReferencia,
                                                                    @RequestParam(value = "filtroComprador", required = false) String filtroComprador,
                                                                    @RequestParam(value = "filtroCentro", required = false) String filtroCentro,
                                                                    @RequestParam(value = "filtroEstado", required = false) String filtroEstado,
                                                                    @RequestParam(value = "filtroSociedad", required = false) String filtroSociedad,
                                                                    @RequestParam(value = "filtroRazonSocial", required = false) String filtroRazonSocial,
                                                                    @RequestParam(value = "filtroDocumentoErp", required = false) String filtroDocumentoErp,
                                                                    @RequestParam(value = "filtroImporte", required = false) String filtroImporte,
                                                                    @RequestParam(value = "filtroIndicador", required = false) String filtroIndicador,
                                                                    @RequestParam(value = "sortCampo", required = false) String sortCampo,
                                                                    @RequestParam(value = "sortReversed", required = false) String sortReversed,
                                                                    HttpServletResponse response) {
        PrefacturaExcelRequestDto excelRequestDto = new PrefacturaExcelRequestDto();
        excelRequestDto.setFechaEmisionInicio(fechaEmisionInicio);
        excelRequestDto.setFechaEmisionFin(fechaEmisionFin);
        excelRequestDto.setFechaEntradaInicio(fechaEntradaInicio);
        excelRequestDto.setFechaEntradaFin(fechaEntradaFin);
        excelRequestDto.setRuc(ruc);
        excelRequestDto.setReferencia(referencia);
        excelRequestDto.setComprador(comprador);
        excelRequestDto.setCentro(centro);
        excelRequestDto.setIdEstado(idEstado);
        excelRequestDto.setFiltroFechaEmision(filtroFechaEmision);
        excelRequestDto.setFiltroFechaEntrada(filtroFechaEntrada);
        excelRequestDto.setFiltroRuc(filtroRuc);
        excelRequestDto.setFiltroReferencia(filtroReferencia);
        excelRequestDto.setFiltroComprador(filtroComprador);
        excelRequestDto.setFiltroCentro(filtroCentro);
        excelRequestDto.setFiltroEstado(filtroEstado);
        excelRequestDto.setFiltroSociedad(filtroSociedad);
        excelRequestDto.setFiltroRazonSocial(filtroRazonSocial);
        excelRequestDto.setFiltroDocumentoErp(filtroDocumentoErp);
        excelRequestDto.setFiltroImporte(filtroImporte);
        excelRequestDto.setFiltroIndicador(filtroIndicador);
        excelRequestDto.setSortCampo(sortCampo);
        excelRequestDto.setSortReversed(sortReversed);

        SXSSFWorkbook book = this.prefacturaService.descargarListaPrefacturaExcelSXLSXconFiltros(excelRequestDto);
        String excelFileName = "ListadoPrefacturas_" + DateUtils.getFechaActualAsStringPattern("yyyy-MM-dd__hh_mm_ss") + ".xlsx";

        try {
            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            book.write(outByteStream);
            byte[] outArray = outByteStream.toByteArray();
            response.setContentType(ExcelType.XLSX.getExtension());
            response.setContentLength(outArray.length);
            response.setHeader("Expires:", "0"); // eliminates browser caching
            response.setHeader("Content-Disposition", "attachment; filename=" + excelFileName);
            OutputStream outStream = response.getOutputStream();
            outStream.write(outArray);
            outStream.flush();

            book.dispose();
            book.close();
        } catch (FileNotFoundException e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        } catch (IOException e) {
            String error = Utils.obtieneMensajeErrorException(e);
            e.printStackTrace();
            throw new RuntimeException(error);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/getPrefacturaListByAllFiltersPag")
    public ResponseEntity<PrefacturaAprobacionOutDTO> getPrefacturaListByAllFiltersPag(@RequestBody FiltroDocumentoDto filtro,
                                                                                        HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            logger.error("<--MC_LOGGER--> :filtroDocumentoDto1X:"+filtro);
            PrefacturaAprobacionOutDTO out = new PrefacturaAprobacionOutDTO();
            filtro.getFechaEntradaInicio().setHours(0);
            filtro.getFechaEntradaInicio().setMinutes(0);
            filtro.getFechaEntradaInicio().setSeconds(1);
            logger.error("<--MC_LOG--> filterKpiDTO.getDateStart():4.1:"+filtro.getFechaEntradaInicio());

            filtro.getFechaEntradaFin().setHours(23);
            filtro.getFechaEntradaFin().setMinutes(59);
            filtro.getFechaEntradaFin().setSeconds(59);
            logger.error("<--MC_LOG--> filterKpiDTO.getDateStart():4.1:"+filtro.getFechaEntradaFin());
            logger.error("<--MC_LOGGER--> :filtroDocumentoDto2X:");
            List<PrefacturaAprobacionDTO> listaAux = prefacturaNeoService.getPrefacturaListPg(
                    filtro.getFechaEmisionInicio()
                    , filtro.getFechaEmisionFin()
                    , filtro.getFechaEntradaInicio()
                    , filtro.getFechaEntradaFin()
                    , filtro.getRuc()
                    , filtro.getReferencia()
                    , filtro.getComprador()
                    , filtro.getCentro()
                    , filtro.getIdEstado()
                    , filtro.getNroRegistros()
                    , filtro.getPaginaMostrar()
                    , null
            );

            logger.error("<--MC_LOGGER--> :filtroDocumentoDto3X:"+listaAux.size());
            logger.error("<--MC_LOGGER--> :filtroDocumentoDto4X:"+listaAux);
            Integer totalElementos = 0;
            Integer totalPaginas = 0;

            logger.error("<--MC_LOGGER--> :filtroDocumentoDto5X:");
            if (listaAux != null && listaAux.size() > 0) {
                logger.error("<--MC_LOGGER--> :filtroDocumentoDto5X:vacio");
                totalElementos = listaAux.size();
            }
            logger.error("<--MC_LOGGER--> :filtroDocumentoDto6X:");
            if (totalElementos > 0) {
                logger.error("docacp_2X ");
                if (totalElementos > filtro.getNroRegistros()) {
                    logger.error("docacp_3X ");
                    totalPaginas = totalElementos / filtro.getNroRegistros();
                    if(totalElementos%filtro.getNroRegistros()>0)
                        totalPaginas++;
                } else {
                    logger.error("docacp_4X ");
                    totalPaginas = 1;
                }


            }
            logger.error("<--MC_LOGGER--> :filtroDocumentoDto7X:");
            Integer numeroPaginaMostrar = (filtro.getPaginaMostrar() - 1) * filtro.getNroRegistros();
            logger.error("docap__5X " + numeroPaginaMostrar);

            List<PrefacturaAprobacionDTO> lista = prefacturaNeoService.getPrefacturaListPg(
                    filtro.getFechaEmisionInicio()
                    , filtro.getFechaEmisionFin()
                    , filtro.getFechaEntradaInicio()
                    , filtro.getFechaEntradaFin()
                    , filtro.getRuc()
                    , filtro.getReferencia()
                    , filtro.getComprador()
                    , filtro.getCentro()
                    , filtro.getIdEstado()
                    , filtro.getNroRegistros()
                    , numeroPaginaMostrar
                    , "X"
            );

            logger.error("listaFacturaPendiente__6X " + listaAux.size());
            logger.error("listaFacturaPendiente__7X " + lista.size());
            logger.error("listaFacturaPendiente__8X " + totalElementos);
            logger.error("listaFacturaPendiente__9X " + totalPaginas);

            logger.error("listaFacturaPendiente__12X ");
            logger.error("listaFacturaPendiente__13X ");

            out.setTotalElementos(totalElementos);
            out.setTotalPaginas(totalPaginas);
            out.setLista(lista);

            logger.error("<--MC_LOGGER--> :documentoAceptacionListX:"+out);
            return new ResponseEntity<>(out, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
    @PostMapping(value = "/rechazarPrefacturasSinAdjuntos/")
    public ResponseEntity<String> rechazarPrefacturasSinAdjuntos(
            @RequestParam(value = "fechaRecepcion", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaRecepcion,
            @AuthenticationPrincipal Token token) {
        try {
            logger.error("FechaEmisionRechazar: " + fechaRecepcion);
            return new ResponseEntity<>(prefacturaService.rechazarPrefacturasSinAdjuntos(fechaRecepcion,token), HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/getMigracionDocumentos/{tabla}/{tipoArchivo}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response<String>> getMigracionDocumentos(
            @PathVariable("tabla") String tabla,
            @PathVariable("tipoArchivo") String tipoArchivo,
            @RequestParam(value = "idAdjunto", required = false) Integer idAdjunto
    ) {
        Response<String> response = new Response<>();
        try {

            int cont = 0;
            if (tabla.equalsIgnoreCase("PREFACTURA")) {
                Optional<Prefactura> obj = prefacturaRepository.getOneById(idAdjunto);

                if (obj.isPresent()) {

                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .connectTimeout(10000, TimeUnit.SECONDS)
                            .writeTimeout(10000, TimeUnit.SECONDS)
                            .readTimeout(10000, TimeUnit.SECONDS)
                            .build();
                    okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
                    String pathArchivo = tipoArchivo.equalsIgnoreCase("PDF") ? obj.get().getPdfEcmPath() : obj.get().getXmlEcmPath();
                    pathArchivo = pathArchivo.replaceAll("/b9f6eb201790708cd5aaf0e5/root","");
                    logger.error("Migracion archivo"+pathArchivo);
                    okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "\""+pathArchivo+"\"");
                    Request request = new Request.Builder()
                            .url(urlcmismigracion)//"https://migracioncopeincaprof9iyf5gkk6.eu2.hana.ondemand.com/copeincaproviderapp/api/repositorio/getContentDocumentByIdCmis")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "Basic amVzY3VkZXJvQGNzdGljb3JwLmJpejpKZXNjdWRlcm8wMiQ=")
                            .build();
                    okhttp3.Response respuesta = client.newCall(request).execute();

                    String jsonResponse = respuesta.body().string();
                    logger.error("Migracion archivo "+jsonResponse);
                    Map<String, Object> resultMap = new ObjectMapper().readValue(jsonResponse, HashMap.class);
                    if (String.valueOf(resultMap.get("message")).equals("ok")) {
                        cont++;
                        //logger.error("***********"+String.valueOf(resultMap.get("ok")));
                        FileBase64 fileBase64 = new FileBase64();
                        fileBase64.setBase64(String.valueOf(resultMap.get("result")));
                        String[] auxNombre = pathArchivo.split("/");
                        String nombreArchivo = auxNombre[auxNombre.length - 1];
                        logger.error("migracion archivo nombreArchivo "+nombreArchivo);
                        String[] auxTipo = nombreArchivo.split("\\.");
                        String tipo = auxTipo[auxTipo.length - 1];
                        fileBase64.setTipo("." + tipo);//obj.get().getArchivoTipo());
                        fileBase64.setNombreDocumento(nombreArchivo);
                        // String[] aux = obj.get().getArchivoNombre().split("[.]", 0);
                        fileBase64.setExtension(tipo);//aux[aux.length-1]);

                        //Proveedor proveedor = proveedorService.getProveedorById(obj.get().getProveedor().getIdProveedor())
                        String nameFolder = folderName;//"ArchivosSunat";//Optional.ofNullable(obj.get().getUsuario()).isPresent()?obj.get().getUsuario():obj.get().getProveedor().getIdHcp();
                        CmisFile cmisFile = cmisService.createDocumentoByBase64(nameFolder, fileBase64);
                        Prefactura x = obj.get();
                        if (tipoArchivo.equalsIgnoreCase("PDF")) {
                            x.setArchivoIdPdfCf(cmisFile.getId());
                            x.setPdfEcmPathCf(cmisFile.getUrl());
                            prefacturaRepository.updateMigraDocPDF(obj.get().getId(), x.getPdfEcmPathCf(), x.getArchivoIdPdfCf());
                        } else {
                            x.setArchivoIdXMLCf(cmisFile.getId());
                            x.setXmlEcmPathCf(cmisFile.getUrl());
                            prefacturaRepository.updateMigraDocXML(obj.get().getId(), x.getXmlEcmPathCf(), x.getArchivoIdXMLCf());
                        }


                        //proveedorCatalogoRepository.updateMigraDoc(x.getIdProveedorCatalogo(),x.getRutaCatalogo(),x.getArchivoId(),"1");
                        /*iLegAdjuntoRepo.updateLegAdjuntoById(x.getIdAdjunto(),x.getCmisArchivoRoot(),
                                x.getMigraArchivo(),x.getCmisFolderId(), x.getCmisArchivoId());*/

                    }
                }
                response.ok(true, "lista migrados: " + cont);

            }
        } catch (Exception ex) {
            logger.error("Error getResumenContratoSolicitud: " + ex.getMessage());
            response.ok(ex);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping(value = "postMigracionDocumentos/{tabla}/{carpeta}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response<String>> postMigracionDocumentos(
            @PathVariable("tabla") String tabla,
            @PathVariable("carpeta") String carpeta,
            @RequestParam(value = "idAdjunto") Integer idAdjunto
    ) {
        Response<String> response = new Response<>();
        int cont = 0;

        //Proveedor proveedor = proveedorService.getProveedorById(obj.get().getProveedor().getIdProveedor())
        String nameFolder = carpeta;//folderName;//"ArchivosSunat";//Optional.ofNullable(obj.get().getUsuario()).isPresent()?obj.get().getUsuario():obj.get().getProveedor().getIdHcp();

        if (tabla.equalsIgnoreCase("PREFACTURA")) {
            Optional<Prefactura> obj = prefacturaRepository.getOneById(idAdjunto);
            if (obj.isPresent()) {
               // Map<String, Object> resultMap = ;
                //if (String.valueOf(resultMap.get("message")).equals("ok")) {
                try {

                    //logger.error("***********"+String.valueOf(resultMap.get("ok")));


                    //Prefactura x = obj.get();
                   if(!Optional.ofNullable(obj.get().getArchivoIdPdfCf()).isPresent() && !obj.get().getPdfEcmPath().isEmpty()) {
                       CmisFile cmisFilePDF = cmisService.createDocumentoByBase64(nameFolder, getFile(obj, "PDF"));
                       prefacturaRepository.updateMigraDocPDF(obj.get().getId(), cmisFilePDF.getUrl(), cmisFilePDF.getId());
                       cont++;
                   }
                    if( !Optional.ofNullable(obj.get().getArchivoIdXMLCf()).isPresent() && !obj.get().getXmlEcmPath().isEmpty()) {
                        CmisFile cmisFileXML = cmisService.createDocumentoByBase64(nameFolder, getFile(obj, "XML"));
                        prefacturaRepository.updateMigraDocXML(obj.get().getId(), cmisFileXML.getUrl(), cmisFileXML.getId());
                        cont++;
                    }
                       /* } else {

                        }*/


                    //proveedorCatalogoRepository.updateMigraDoc(x.getIdProveedorCatalogo(),x.getRutaCatalogo(),x.getArchivoId(),"1");
                        /*iLegAdjuntoRepo.updateLegAdjuntoById(x.getIdAdjunto(),x.getCmisArchivoRoot(),
                                x.getMigraArchivo(),x.getCmisFolderId(), x.getCmisArchivoId());*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
               // }
            }
        }
        else if(tabla.equalsIgnoreCase("INFORMACION_NOTICIA")){
            Optional<InformacionNoticia> oInfo = Optional.ofNullable(informacionNoticiaRepository.getOne(idAdjunto));
            if (oInfo.isPresent()) {
                try {
                    if(oInfo.get().getArchivoIdCf().isEmpty() && !oInfo.get().getRutaAdjunto().isEmpty()) {
                        CmisFile cmisFilePDF = cmisService.createDocumentoByBase64(nameFolder, getFileInfoNoticias(oInfo.get().getRutaAdjunto(), "PDF"));
                        informacionNoticiaRepository.updateMigraDoc(oInfo.get().getIdInformacionNoticia(),cmisFilePDF.getId(),cmisFilePDF.getUrl());
                        cont++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // }
            }
        }
        response.ok(true, "lista migrados: " + cont);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "postMigracionDocumentosMasivo/{tabla}/{carpeta}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response<String>> postMigracionDocumentosMasivo(
            @PathVariable("tabla") String tabla,
            @PathVariable("carpeta") String carpeta
    ) {
        Response<String> response = new Response<>();
        int cont = 0;

        //Proveedor proveedor = proveedorService.getProveedorById(obj.get().getProveedor().getIdProveedor())
        String nameFolder = carpeta;//folderName;//"ArchivosSunat";//Optional.ofNullable(obj.get().getUsuario()).isPresent()?obj.get().getUsuario():obj.get().getProveedor().getIdHcp();
        List<Integer> list = prefacturaRepository.getPrefacturaMigrar();
        if (tabla.equalsIgnoreCase("PREFACTURA")) {
            for (Integer x: list) {
                Optional<Prefactura> obj = prefacturaRepository.findById(x);
                // Map<String, Object> resultMap = ;
                //if (String.valueOf(resultMap.get("message")).equals("ok")) {
                try {

                    //logger.error("***********"+String.valueOf(resultMap.get("ok")));


                    //Prefactura x = obj.get();
                    if(!Optional.ofNullable(obj.get().getArchivoIdPdfCf()).isPresent() && !obj.get().getPdfEcmPath().isEmpty()) {
                        CmisFile cmisFilePDF = cmisService.createDocumentoByBase64(nameFolder, getFile(obj, "PDF"));
                        prefacturaRepository.updateMigraDocPDF(obj.get().getId(), cmisFilePDF.getUrl(), cmisFilePDF.getId());
                        cont++;
                    }
                    if( !Optional.ofNullable(obj.get().getArchivoIdXMLCf()).isPresent() && !obj.get().getXmlEcmPath().isEmpty()) {
                        CmisFile cmisFileXML = cmisService.createDocumentoByBase64(nameFolder, getFile(obj, "XML"));
                        prefacturaRepository.updateMigraDocXML(obj.get().getId(), cmisFileXML.getUrl(), cmisFileXML.getId());
                        cont++;
                    }
                       /* } else {

                        }*/


                    //proveedorCatalogoRepository.updateMigraDoc(x.getIdProveedorCatalogo(),x.getRutaCatalogo(),x.getArchivoId(),"1");
                        /*iLegAdjuntoRepo.updateLegAdjuntoById(x.getIdAdjunto(),x.getCmisArchivoRoot(),
                                x.getMigraArchivo(),x.getCmisFolderId(), x.getCmisArchivoId());*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // }
            }
        }
        else if(tabla.equalsIgnoreCase("INFORMACION_NOTICIA")){
            Optional<InformacionNoticia> oInfo = Optional.ofNullable(informacionNoticiaService.getInformacionNoticiaById(1));//idAdjunto));
            if (oInfo.isPresent()) {
                try {
                    if(!oInfo.get().getArchivoIdCf().isEmpty() && !oInfo.get().getRutaAdjunto().isEmpty()) {
                        CmisFile cmisFilePDF = cmisService.createDocumentoByBase64(nameFolder, getFileInfoNoticias(oInfo.get().getRutaAdjunto(), "PDF"));
                        informacionNoticiaRepository.updateMigraDoc(oInfo.get().getIdInformacionNoticia(),cmisFilePDF.getId(),cmisFilePDF.getUrl());
                        cont++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // }
            }
        }
        response.ok(true, "lista migrados: " + cont);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "postMigracionDocumentosMasivoDesc/{tabla}/{carpeta}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response<String>> postMigracionDocumentosMasivoDesc(
            @PathVariable("tabla") String tabla,
            @PathVariable("carpeta") String carpeta
    ) {
        Response<String> response = new Response<>();
        int cont = 0;

        //Proveedor proveedor = proveedorService.getProveedorById(obj.get().getProveedor().getIdProveedor())
        String nameFolder = carpeta;//folderName;//"ArchivosSunat";//Optional.ofNullable(obj.get().getUsuario()).isPresent()?obj.get().getUsuario():obj.get().getProveedor().getIdHcp();
        List<Integer> list = prefacturaRepository.getPrefacturaMigrarDesc();
        if (tabla.equalsIgnoreCase("PREFACTURA")) {
            for (Integer x: list) {
                Optional<Prefactura> obj = prefacturaRepository.findById(x);//Optional.ofNullable(x);
                // Map<String, Object> resultMap = ;
                //if (String.valueOf(resultMap.get("message")).equals("ok")) {
                try {

                    //logger.error("***********"+String.valueOf(resultMap.get("ok")));


                    //Prefactura x = obj.get();
                    if(!Optional.ofNullable(obj.get().getArchivoIdPdfCf()).isPresent() && !obj.get().getPdfEcmPath().isEmpty()) {
                        CmisFile cmisFilePDF = cmisService.createDocumentoByBase64(nameFolder, getFile(obj, "PDF"));
                        prefacturaRepository.updateMigraDocPDF(obj.get().getId(), cmisFilePDF.getUrl(), cmisFilePDF.getId());
                        cont++;
                    }
                    if( !Optional.ofNullable(obj.get().getArchivoIdXMLCf()).isPresent() && !obj.get().getXmlEcmPath().isEmpty()) {
                        CmisFile cmisFileXML = cmisService.createDocumentoByBase64(nameFolder, getFile(obj, "XML"));
                        prefacturaRepository.updateMigraDocXML(obj.get().getId(), cmisFileXML.getUrl(), cmisFileXML.getId());
                        cont++;
                    }
                       /* } else {

                        }*/


                    //proveedorCatalogoRepository.updateMigraDoc(x.getIdProveedorCatalogo(),x.getRutaCatalogo(),x.getArchivoId(),"1");
                        /*iLegAdjuntoRepo.updateLegAdjuntoById(x.getIdAdjunto(),x.getCmisArchivoRoot(),
                                x.getMigraArchivo(),x.getCmisFolderId(), x.getCmisArchivoId());*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // }
            }
        }
        else if(tabla.equalsIgnoreCase("INFORMACION_NOTICIA")){
            Optional<InformacionNoticia> oInfo = Optional.ofNullable(informacionNoticiaService.getInformacionNoticiaById(1));//idAdjunto));
            if (oInfo.isPresent()) {
                try {
                    if(!oInfo.get().getArchivoIdCf().isEmpty() && !oInfo.get().getRutaAdjunto().isEmpty()) {
                        CmisFile cmisFilePDF = cmisService.createDocumentoByBase64(nameFolder, getFileInfoNoticias(oInfo.get().getRutaAdjunto(), "PDF"));
                        informacionNoticiaRepository.updateMigraDoc(oInfo.get().getIdInformacionNoticia(),cmisFilePDF.getId(),cmisFilePDF.getUrl());
                        cont++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // }
            }
        }
        response.ok(true, "lista migrados: " + cont);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    private FileBase64 getFile(Optional<Prefactura> obj,String tipoDocu){



        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .writeTimeout(10000, TimeUnit.SECONDS)
                    .readTimeout(10000, TimeUnit.SECONDS)
                    .build();
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
            String pathArchivo = tipoDocu.equalsIgnoreCase("PDF") ? obj.get().getPdfEcmPath() : obj.get().getXmlEcmPath();
            //qas pathArchivo = pathArchivo.replaceAll("/b9f6eb201790708cd5aaf0e5/root", "");
            pathArchivo = pathArchivo.replaceAll("/af3e5a586190708c009865e5/root", "");
            logger.error("Migracion archivo" + pathArchivo);
            okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "\"" + pathArchivo + "\"");
            Request request = new Request.Builder()
                    .url(urlcmismigracion)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Basic amVzY3VkZXJvQGNzdGljb3JwLmJpejpKZXNjdWRlcm8wMiQ=")
                    .build();
            okhttp3.Response respuesta = client.newCall(request).execute();

            String jsonResponse = respuesta.body().string();
            logger.error("Migracion archivo " + jsonResponse);
            Map<String, Object> resultMap = new ObjectMapper().readValue(jsonResponse, HashMap.class);
            FileBase64 fileBase64 = new FileBase64();
            fileBase64.setBase64(String.valueOf(resultMap.get("result")));
            String[] auxNombre = pathArchivo.split("/");//obj.get().getPdfEcmPath().split("/");
            String nombreArchivo = auxNombre[auxNombre.length - 1];
            logger.error("migracion archivo nombreArchivo " + nombreArchivo);
            String[] auxTipo = nombreArchivo.split("\\.");
            String tipo = auxTipo[auxTipo.length - 1];
            fileBase64.setTipo("." + tipo);//obj.get().getArchivoTipo());
            fileBase64.setNombreDocumento(nombreArchivo);
            // String[] aux = obj.get().getArchivoNombre().split("[.]", 0);
            fileBase64.setExtension(tipo);//aux[aux.length-1]);
            return fileBase64;
        } catch (Exception ex) {
            logger.error("Error getResumenContratoSolicitud: " + ex.getMessage());
            return null;
        }

    }
    private FileBase64 getFileInfoNoticias(String url,String tipoDocu){



        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .writeTimeout(10000, TimeUnit.SECONDS)
                    .readTimeout(10000, TimeUnit.SECONDS)
                    .build();
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
            String pathArchivo = url;
            pathArchivo = pathArchivo.replaceAll("/af3e5a586190708c009865e5/root", "");
            logger.error("Migracion archivo" + pathArchivo);
            okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "\"" + pathArchivo + "\"");
            Request request = new Request.Builder()
                    .url("https://migracioncopeincaprof9iyf5gkk6.eu2.hana.ondemand.com/copeincaproviderapp/api/repositorio/getContentDocumentByIdCmis")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Basic amVzY3VkZXJvQGNzdGljb3JwLmJpejpKZXNjdWRlcm8wMiQ=")
                    .build();
            okhttp3.Response respuesta = client.newCall(request).execute();

            String jsonResponse = respuesta.body().string();
            logger.error("Migracion archivo " + jsonResponse);
            Map<String, Object> resultMap = new ObjectMapper().readValue(jsonResponse, HashMap.class);
            FileBase64 fileBase64 = new FileBase64();
            fileBase64.setBase64(String.valueOf(resultMap.get("result")));
            String[] auxNombre = url.split("/");
            String nombreArchivo = auxNombre[auxNombre.length - 1];
            logger.error("migracion archivo nombreArchivo " + nombreArchivo);
            String[] auxTipo = nombreArchivo.split("\\.");
            String tipo = auxTipo[auxTipo.length - 1];
            fileBase64.setTipo("." + tipo);//obj.get().getArchivoTipo());
            fileBase64.setNombreDocumento(nombreArchivo);
            // String[] aux = obj.get().getArchivoNombre().split("[.]", 0);
            fileBase64.setExtension(tipo);//aux[aux.length-1]);
            return fileBase64;
        } catch (Exception ex) {
            logger.error("Error getResumenContratoSolicitud: " + ex.getMessage());
            return null;
        }

    }
}
