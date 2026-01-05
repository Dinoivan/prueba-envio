package com.incloud.hcp.rest;

import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.DescargaService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 30/11/2017.
 */
@RestController
@RequestMapping(value = "/api/descarga")
public class DescargaRest extends AppRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DescargaService descargaService;

    @RequestMapping(
            value = "/proveedor/{selection}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, String>> descargarProveedores(@PathVariable("selection") String selection) {
        logger.error(">>> Iniciando descarga con selection=" + selection);

        try (HSSFWorkbook book = descargaService.getProveedores(selection);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            if (book == null) {
                logger.error("El objeto HSSFWorkbook es NULL");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            book.write(bos);
            byte[] excelBytes = bos.toByteArray();

            String base64Excel = Base64.getEncoder().encodeToString(excelBytes);

            Map<String, String> response = new HashMap<>();
            response.put("file", base64Excel);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            logger.error("Error al generar el Excel", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/homologacion",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_OCTET_STREAM_VALUE
    })
    public ResponseEntity<?> descargarHomologacion(HttpServletResponse response) {
        OutputStream out = null;
        try {
            HSSFWorkbook book = descargaService.getHomologacion();

            out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            book.write(out);
            response.setHeader ("Content-Disposition", "attachment; filename=reporte.xls");
            response.setHeader("Content-Length","200");
            book.close();
            return new ResponseEntity<>( HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ioe) {
                    //  ioe.printStackTrace();
                }
            }
        }

    }






}
