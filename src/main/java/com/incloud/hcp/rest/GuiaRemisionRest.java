package com.incloud.hcp.rest;

import com.incloud.hcp.config.excel.ExcelType;
import com.incloud.hcp.domain.balanza.GuiaRemision;
import com.incloud.hcp.dto.DatosBLZProveedorDTO;
import com.incloud.hcp.dto.FiltroProveedorDTO;
import com.incloud.hcp.dto.GuiaRemisionDTO;
import com.incloud.hcp.dto.GuiaRemisionSapDTO;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.repository.DetalleTicketRepository;
import com.incloud.hcp.repository.GuiaRemisionRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.service.GuiaRemisionDetalleService;
import com.incloud.hcp.service.GuiaRemisionService;
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
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/guiaRemision")
public class GuiaRemisionRest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GuiaRemisionService guiaRemisionService;

    @Autowired
    private GuiaRemisionDetalleService guiaRemisionDetalleService;

    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private DetalleTicketRepository detalleTicketRepository;
    @Autowired
    private GuiaRemisionRepository guiaRemisionRepository;


    @RequestMapping(value = "/id/{idGuiaRemision}", method = RequestMethod.GET, produces = {
                                                        MediaType.APPLICATION_JSON_VALUE,
                                                        MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getGuiaRemisionById(@PathVariable("idGuiaRemision") Integer idGuiaRemision) throws PortalException {
        GuiaRemision guiaRemision = this.guiaRemisionService.getGuiaRemisionById(idGuiaRemision);
        if (guiaRemision == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro TicketPesaje con ese id");
        }
        return new ResponseEntity<>(guiaRemision, HttpStatus.OK);
    }

    @RequestMapping(value = "Listar", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<GuiaRemision>> gelAllGuiaRemision() {
        return Optional.ofNullable(guiaRemisionService.getAllGuiaRemision())
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @RequestMapping(value = "RegistrarGRE", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public GuiaRemisionSapDTO RegistrarGRE(@RequestBody GuiaRemisionDTO guiaRemisionDTO) throws Exception {
        return this.guiaRemisionService.RegistrarGRE(guiaRemisionDTO);

    }
    @RequestMapping(value = "obtenerPdf", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String obtenerPdf(@RequestBody GuiaRemisionDTO guiaRemisionDTO) throws Exception {
        return this.guiaRemisionService.obtenerPdf(guiaRemisionDTO);

    }

    @RequestMapping(value = "anularGuia", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public GuiaRemisionSapDTO anularGuia(@RequestBody GuiaRemisionDTO guiaRemisionDTO) throws Exception {
        return this.guiaRemisionService.anularGuia(guiaRemisionDTO);

    }

    @RequestMapping(value = "guardar", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public GuiaRemisionSapDTO save(@RequestBody GuiaRemisionDTO guiaRemisionDTO) throws Exception {


        /*List<GuiaRemisionDetalle> guiaDetalle = guiaRemisionDTO.getGuiaRemisionDetalleList();
        for(GuiaRemisionDetalle item: guiaDetalle){
            DetalleTicket ticketDetalle = this.detalleTicketRepository.findByDocMaterial(item.getDocMaterial());
            ticketDetalle.setTipo_peso(item.getUnidadMedida());
            this.detalleTicketRepository.save(ticketDetalle);
        }*/

        //try {
            return this.guiaRemisionService.save(guiaRemisionDTO);

        /*} catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }

    /*@RequestMapping(value = "buscarProveedores", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Proveedor>> getProveedorByProveedorandRucandSocialanddireccionandemail(@RequestParam(value = "ruc", required = false) String ruc,
                                                                                                      @RequestParam(value = "razonSocial", required = false) String razonSocial)  {

        DatosBLZProveedorDTO dto = new DatosBLZProveedorDTO();
        dto.setRuc(ruc);
        dto.setRazonSocial(razonSocial);

        try{

            List<Proveedor> proveedorList = guiaRemisionService.getProveedorByRucandRazonSocial(dto);

            if(proveedorList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(proveedorList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }




/*
        try {
            return Optional.ofNullable(this.proveedorRepository.getProveedorByProveedorandRucandSocialanddireccionandemail(ruc,razonSocial))
                    .map(ccomparativoProveedor -> new ResponseEntity<>(ccomparativoProveedor, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }*/


   @RequestMapping(value = "buscarProveedor", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<DatosBLZProveedorDTO>> buscar(@RequestBody FiltroProveedorDTO filtro) {

        List<DatosBLZProveedorDTO> proveedor = new ArrayList<>();

        if (filtro != null) {
            if (filtro.getRuc() != null && filtro.getRazonSocial()!= null && filtro.getAcreedorCodigoSap()!= null) {
                proveedor = guiaRemisionService.getProveedorByProveedorandRucandSocialanddireccionandemail(filtro);
            } else if (filtro.getRuc() != null && filtro.getRazonSocial()== null && filtro.getAcreedorCodigoSap()== null) {
                proveedor = guiaRemisionService.getProveedorDtoByRuc(filtro.getRuc());
            } else if (filtro.getRuc() == null && filtro.getRazonSocial()!= null && filtro.getAcreedorCodigoSap() == null) {
                proveedor = guiaRemisionService.getProveedorDtoByRazonSocial(filtro.getRazonSocial());
            } else if (filtro.getRuc() == null && filtro.getRazonSocial()== null && filtro.getAcreedorCodigoSap() != null) {
                proveedor = guiaRemisionService.getProveedorDtoByAcreedorCodigoSap(filtro.getAcreedorCodigoSap());
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrese Datos del proveedor");
            }
        }


        return new ResponseEntity<List<DatosBLZProveedorDTO>>(proveedor, HttpStatus.OK);
    }

    @Operation(summary = "Genera Excel XLSX de constancia de Peso")
    @GetMapping(value = "/genera-constancia-peso/{id}", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity<?> generaConstanciaPeso(HttpServletResponse response,@PathVariable("id") Integer id) {
        logger.error("[generaConstanciaPeso]:Inicio");
        logger.error("[generaConstanciaPeso]:id:{}", id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        GuiaRemision guia = guiaRemisionRepository.getOne(id);
        logger.error("[generaConstanciaPeso]:guia:{}",guia);
        String excelFileName = "ConstanciaPeso_" + formatter.format(LocalDateTime.now()) +"_"+
                guia.getIdTicketPesaje()+".xlsx";

        SXSSFWorkbook book = this.guiaRemisionService.generaConstanciaPeso(id);

        try {
            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            book.write(outByteStream);
            byte[] outArray = outByteStream.toByteArray();
            response.setContentType(ExcelType.XLSX.getExtension());
            response.setContentLength(outArray.length);
            response.setHeader("Content-Disposition", "attachment; filename=" + excelFileName);
            OutputStream outStream = response.getOutputStream();
            outStream.write(outArray);
            outStream.flush();
            book.dispose();
            book.close();

        } catch (FileNotFoundException e) {
            String error = Utils.obtieneMensajeErrorException(e);
            e.printStackTrace();
            throw new RuntimeException(error);
        } catch (IOException e) {
            String error = Utils.obtieneMensajeErrorException(e);
            e.printStackTrace();
            throw new RuntimeException(error);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
