package com.incloud.hcp.rest;

import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.DescargaService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrador on 30/11/2017.
 */
@RestController
@RequestMapping(value = "/api/descarga")
public class DescargaRest extends AppRest {

    @Autowired
    private DescargaService descargaService;

    @RequestMapping(value = "/proveedor/{selection}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_OCTET_STREAM_VALUE
    })
    public ResponseEntity<?> descargarProveedores(HttpServletResponse response,
                                                  @PathVariable("selection") String selection) {
        OutputStream out = null;
        try {
            HSSFWorkbook book = descargaService.getProveedores(selection);

            out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            book.write(out);
            response.setHeader ("Content-Disposition", "attachment; filename=reporte.xls");
            response.setHeader("Content-Length","200");
            book.close();
            return new ResponseEntity<>( HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            //throw new PortalException("Error al descargar el archivo");
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
