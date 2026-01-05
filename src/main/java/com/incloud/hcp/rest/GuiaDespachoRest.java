package com.incloud.hcp.rest;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.almacen.GuiaDespacho;
import com.incloud.hcp.dto.GuiaDespachoDto;
import com.incloud.hcp.dto.RechazoDto;
import com.incloud.hcp.repository.GuiaDespachoRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.*;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import com.sap.cloud.security.xsuaa.token.Token;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/guia-despacho")
public class GuiaDespachoRest extends AppRest {

    private GuiaDespachoService guiaDespachoService;
    private GuiaDespachoRepository guiaDespachoRepository;
    private ProveedorRepository proveedorRepository;

    @Autowired
    public GuiaDespachoRest(GuiaDespachoService guiaDespachoService,
                            GuiaDespachoRepository guiaDespachoRepository,
                            ProveedorRepository proveedorRepository) {
        this.guiaDespachoService = guiaDespachoService;
        this.guiaDespachoRepository = guiaDespachoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @PostMapping(value = "/nuevaGuiaDespacho")
    public ResponseEntity<Integer> ingresarNuevaGuiaDespacho(@RequestBody GuiaDespachoDto guiaDespachoDto, @AuthenticationPrincipal Token token) {
        try {
            UserSession userSession = this.getUserSession(token);
            Integer idGuiaDespacho = guiaDespachoService.ingresarNuevaGuiaDespacho(guiaDespachoDto, userSession.getRuc());
            if (idGuiaDespacho.compareTo(-422) == 0) {
                return new ResponseEntity<>(idGuiaDespacho, HttpStatus.UNPROCESSABLE_ENTITY);
            }

            return new ResponseEntity<>(idGuiaDespacho, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/getAllDespachos/{FechaInicio}/{FechaFin}")
    public ResponseEntity<List<GuiaDespacho> > getAllDespachos( @PathVariable("FechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                                                @PathVariable("FechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                                                @AuthenticationPrincipal Token token) {
        try {
            UserSession userSession = this.getUserSession(token);
            Optional<Proveedor> proveedor = this.proveedorRepository.findByRuc(userSession.getRuc());
            List<GuiaDespacho> guiasDespacho = new ArrayList<>();

            LocalDate localDateFechaInicio = DateUtils.utilDateToLocalDate(fechaInicio);
            LocalDate localDateFechaFin = DateUtils.utilDateToLocalDate(fechaFin);

            ZoneId zoneId = ZoneId.systemDefault();
            Date fechaInicioDate = Date.from(
                    localDateFechaInicio.atStartOfDay(zoneId).toInstant()
            );
            Date fechaFinDate = Date.from(
                    localDateFechaFin.plusDays(1)
                            .atStartOfDay(zoneId)
                            .toInstant()
            );

            if(proveedor.isPresent()) {
                Proveedor pro = proveedor.get();
                guiasDespacho = guiaDespachoRepository.getAllDespachosRuc(fechaInicioDate, fechaFinDate, pro.getRuc());
            } else {
                guiasDespacho = guiaDespachoRepository.getAllDespachos(fechaInicioDate, fechaFinDate);
            }
            return Optional.of(guiasDespacho)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/getDespachosRegistrados/{FechaInicio}/{FechaFin}")
    public ResponseEntity<List<GuiaDespacho> > getDespachosRegistrados( @PathVariable("FechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                                                @PathVariable("FechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                                                @AuthenticationPrincipal Token token) {
        try {
            UserSession userSession = this.getUserSession(token);
            Optional<Proveedor> proveedor = this.proveedorRepository.findByRuc(userSession.getRuc());
            List<GuiaDespacho> guiasDespacho = new ArrayList<>();
            LocalDate localDateFechaInicio = DateUtils.utilDateToLocalDate(fechaInicio);
            LocalDate localDateFechaFin = DateUtils.utilDateToLocalDate(fechaFin);

            ZoneId zoneId = ZoneId.systemDefault();
            Date fechaInicioDate = Date.from(
                    localDateFechaInicio.atStartOfDay(zoneId).toInstant()
            );
            Date fechaFinDate = Date.from(
                    localDateFechaFin.plusDays(1)
                            .atStartOfDay(zoneId)
                            .toInstant()
            );
            if(proveedor.isPresent()) {
                Proveedor pro = proveedor.get();
                guiasDespacho = guiaDespachoRepository.getADespachosRegistradosRuc(fechaInicioDate, fechaFinDate, pro.getRuc());
            } else {
                guiasDespacho = guiaDespachoRepository.getDespachosRegistrados(fechaInicioDate, fechaFinDate);
            }
            return Optional.of(guiasDespacho)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "/descartarGuiaDespacho/{idDespacho}")
    public ResponseEntity<Integer> descartarGuiaDespacho(@PathVariable("idDespacho") Integer idDespacho, @AuthenticationPrincipal Token token) {
        try {
            UserSession userSession = this.getUserSession(token);
            Integer idGuiaDespacho = guiaDespachoService.descartarGuiaDespacho(idDespacho, userSession.getRuc());
            if (idGuiaDespacho.compareTo(-422) == 0) {
                return new ResponseEntity<>(idGuiaDespacho, HttpStatus.UNPROCESSABLE_ENTITY);
            }

            return new ResponseEntity<>(idGuiaDespacho, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "/rechazarGuiaDespacho/{idDespacho}")
    public ResponseEntity<Integer> rechazarGuiaDespacho(@PathVariable("idDespacho") Integer idDespacho,
                                                        @RequestBody RechazoDto rechazo,
                                                        @AuthenticationPrincipal Token token) {
        try {
            UserSession userSession = this.getUserSession(token);
            Integer idGuiaDespacho = guiaDespachoService.rechazarGuiaDespacho(idDespacho, rechazo.getMotivo(), userSession.getRuc());
            if (idGuiaDespacho.compareTo(-422) == 0) {
                return new ResponseEntity<>(idGuiaDespacho, HttpStatus.UNPROCESSABLE_ENTITY);
            }

            return new ResponseEntity<>(idGuiaDespacho, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "/aprobarGuiaDespacho/{idDespacho}")
    public ResponseEntity<String> aprobarGuiaDespacho(@PathVariable("idDespacho") Integer idDespacho,
                                                      @RequestBody GuiaDespachoDto guiaDespachoDto,
                                                        @AuthenticationPrincipal Token token) {
        try {
            UserSession userSession = this.getUserSession(token);
            String documentoSap = guiaDespachoService.aprobarGuiaDespacho(idDespacho, guiaDespachoDto, userSession.getRuc());
            if (documentoSap == null || documentoSap.isEmpty()) {
                return new ResponseEntity<>("No se generó documento SAP", HttpStatus.UNPROCESSABLE_ENTITY);
            }

            return new ResponseEntity<>(documentoSap, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
