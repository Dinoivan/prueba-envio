package com.incloud.hcp.rest;

import com.incloud.hcp.domain.LicitacionProveedorTipoCuestionario;
import com.incloud.hcp.dto.*;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.LicitacionProveedorTipoCuestionarioService;
import com.incloud.hcp.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by USER on 21/09/2017.
 */
@RestController
@RequestMapping(value = "/api/licitacionProveedorTipoCuestionario")
public class LicitacionProveedorTipoCuestionarioRest extends AppRest {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LicitacionProveedorTipoCuestionarioService licitacionProveedorTipoCuestionarioService;


    @GetMapping(value = "/graficaPrecioFinalMoneda/{idLicitacion}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GraficoMiniSalidaDto>> graficaPrecioFinalMoneda(@PathVariable Integer idLicitacion) {
        try {
            return Optional.ofNullable(this.licitacionProveedorTipoCuestionarioService.graficaPrecioFinalMoneda(idLicitacion))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/graficaPorcentajeObtenido/{idLicitacion}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GraficoMiniSalidaDto>> graficaPorcentajeObtenido(@PathVariable Integer idLicitacion) {
        try {
            return Optional.ofNullable(this.licitacionProveedorTipoCuestionarioService.graficaPorcentajeObtenido(idLicitacion))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @GetMapping(value = "/obtenerLicitacionProveedorTipoCuestionario/{idLicitacion}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LicitacionProveedorTipoCuestionario>> obtenerLicitacionProveedorTipoCuestionario(@PathVariable Integer idLicitacion) {
        try {
            return Optional.ofNullable(this.licitacionProveedorTipoCuestionarioService.obtenerLicitacionProveedorTipoCuestionario(idLicitacion))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/obtenerTipoLicitacion/{idLicitacion}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GanadorLicitacionSalidaTipoLicitacionDto> obtenerTipoLicitacion(@PathVariable Integer idLicitacion) {
        try {
            return Optional.ofNullable(this.licitacionProveedorTipoCuestionarioService.obtenerTipoLicitacion(idLicitacion))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/obtenerEvaluacionTecnica/{idLicitacion}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GanadorLicitacionEntradaDto> obtenerEvaluacionTecnica(@PathVariable Integer idLicitacion) {
        try {
            return Optional.ofNullable(this.licitacionProveedorTipoCuestionarioService.obtenerEvaluacionTecnica(idLicitacion))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/obtenerLicitacionProveedorDetalleCuadroEvaluacion/{idLicitacion}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LicitacionProveedorDetalleEvaluacionDto>> obtenerLicitacionProveedorDetalleCuadroEvaluacion(
            @PathVariable Integer idLicitacion) {
        try {
            return Optional.ofNullable(this.licitacionProveedorTipoCuestionarioService.obtenerLicitacionProveedorDetalleCuadroEvaluacion(idLicitacion))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }



    @PostMapping(value = "/obtenerGanadorTipoCuestionario", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GanadorLicitacionSalidaDto> obtenerGanadorTipoCuestionario
            (@RequestBody GanadorLicitacionEntradaDto bean)   {
        try {
            GanadorLicitacionSalidaDto result =
                    this.licitacionProveedorTipoCuestionarioService.obtenerGanadorTipoCuestionario(bean);

            return Optional.ofNullable(result)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }

    }

    @PostMapping(value = "/obtenerGanadorDetalleTipoCuestionario", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GanadorLicitacionSalidaDto> obtenerGanadorDetalleTipoCuestionario
            (@RequestBody GanadorLicitacionEntradaDto bean)   {
        try {
            GanadorLicitacionSalidaDto result =
                    this.licitacionProveedorTipoCuestionarioService.obtenerGanadorDetalleTipoCuestionario(bean);

            return Optional.ofNullable(result)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }

    }

    @Operation(summary = "Genera Excel XLSX de registros del Detalle de Ganadores")
    @GetMapping(value = "/downloadGanadorDetalleCuadro/{idLicitacion}", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity<?> downloadGanadorDetalleCuadro(
            @PathVariable Integer idLicitacion,
            HttpServletResponse response) {

       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh_mm_ss");
        String nombreArchivo = "GanadorDetalleCuadro";
        String excelFileName = nombreArchivo + "_" + formatter.format(LocalDateTime.now()) + ".xlsx";

        try {
            SXSSFWorkbook book = this.licitacionProveedorTipoCuestionarioService.downloadGanadorDetalleCuadro(idLicitacion);
            ByteArrayOutputStream outByteStream;
            byte[] outArray;
            outByteStream = new ByteArrayOutputStream();
            book.write(outByteStream);
            outArray = outByteStream.toByteArray();
            response.setContentLength(outArray.length);
            response.setHeader("Expires:", "0"); // eliminates browser caching
            response.setHeader("Content-Disposition", "attachment; filename=" + excelFileName);
            OutputStream outStream = response.getOutputStream();
            outStream.write(outArray);

            book.dispose();
            book.close();

            outStream.flush();
        } catch (FileNotFoundException e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            e.printStackTrace();
            throw new RuntimeException(error);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }



}


