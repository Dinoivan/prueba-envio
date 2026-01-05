package com.incloud.hcp.rest;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.HomologacionDto;
import com.incloud.hcp.dto.ProveedorVerNotaDto;
import com.incloud.hcp.dto.homologacion.LineaComercialHomologacionDto;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.facade.HomologacionFacade;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.HomologacionService;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.util.Utils;
import com.incloud.hcp.validation.Validator;
import com.sap.cloud.security.xsuaa.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Administrador on 28/09/2017.
 */
@RestController
@RequestMapping(value = "/api/homologacion")
public class HomologacionRest extends AppRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HomologacionService homologacionService;
    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorRepository proveedorRepository;

    private HomologacionFacade homologacionFacade;
    private Validator<HomologacionDto> validator;

    @Autowired
    public void setHomologacionFacade(HomologacionFacade homologacionFacade) {
        this.homologacionFacade = homologacionFacade;
    }

    @Autowired
    @Qualifier(value ="homologacionValidatorImpl")
    public void setValidator(Validator<HomologacionDto> validator) {
        this.validator = validator;
    }

    public Validator<HomologacionDto> getValidator() {
        return validator;
    }

    @RequestMapping(value = "/proveedor",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> guardarHomologacion(HttpServletRequest request,
                                                 @RequestBody List<LineaComercialHomologacionDto> list,
                                                 @AuthenticationPrincipal Token token) throws PortalException {
        UserSession userSession = this.getUserSession(token);
        if (!Optional.ofNullable(userSession.getRuc()).isPresent()) {
            throw new PortalException("El usuario no esta registrado como proveedor");
        }

        Proveedor p = this.proveedorService.getProveedorByRuc(userSession.getRuc());
        return this.processObject(homologacionService.guardarHomologacion(p, list));
    }

    @RequestMapping(value = "/proveedor",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getListHomologacionByIdProveedor(HttpServletRequest request,@AuthenticationPrincipal Token token) throws PortalException {
        UserSession userSession = this.getUserSession(token);
        if (!Optional.ofNullable(userSession.getRuc()).isPresent()) {
            throw new PortalException("El usuario no esta registrado como proveedor");
        }
        Proveedor p = this.proveedorService.getProveedorByRuc(userSession.getRuc());
        if (p !=null)
            return this.processObject(homologacionService.getListHomologacionByIdProveedor(p.getIdProveedor()));
        else
            return this.processObject(null);
    }

    @RequestMapping(value = "/proveedor/responder",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getListHomologacionByIdProveedorResponder(HttpServletRequest request,@AuthenticationPrincipal Token token) throws PortalException {
        UserSession userSession = this.getUserSession(token);
        if (!Optional.ofNullable(userSession.getRuc()).isPresent()) {
            throw new PortalException("El usuario no esta registrado como proveedor");
        }
        Proveedor p = this.proveedorService.getProveedorByRuc(userSession.getRuc());
        if (p !=null)
            return this.processObject(homologacionService.getListHomologacionByIdProveedorResponder(p.getIdProveedor()));
        else
            return this.processObject(null);
    }
    //Inicio Prueba
    @RequestMapping(value = "/proveedor/pruebita/ppo",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPruebita(HttpServletRequest request,@AuthenticationPrincipal Token token) throws PortalException {
        UserSession userSession = this.getUserSession(token);
        if (userSession !=null)
            return this.processObject(userSession);
        else
            return this.processObject(null);
    }
    @RequestMapping(value = "/proveedor/pruebita/responder",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPruebita2(HttpServletRequest request) throws PortalException {

        Proveedor p = this.proveedorService.getProveedorByRuc("10420686442");
        if (p !=null)
            return this.processObject(homologacionService.getListHomologacionByIdProveedorResponder(p.getIdProveedor()));
        else
            return this.processObject(null);

    }
    //Fin prueba

    @RequestMapping(value = "/proveedor/{idProveedor}/verNota",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ProveedorVerNotaDto> verNota(@PathVariable("idProveedor") Integer idProveedor) throws PortalException {
        try {
            Proveedor p = proveedorRepository.getOne(idProveedor);
            if (!Optional.ofNullable(idProveedor).isPresent()) {
                throw new PortalException("El proveedor no existe");
            }
            logger.error("Ingresando verNota proveedor 01 p: " + p.toString());
            ProveedorVerNotaDto proveedorVerNotaDto = this.homologacionService.verNota(idProveedor);
            logger.error("Ingresando verNota proveedor 02 proveedorVerNotaDto: " + proveedorVerNotaDto.toString());
            logger.error("Ingresando verNota proveedor 03 p: " + p.toString());
            this.proveedorRepository.save(p);

            return Optional.ofNullable(proveedorVerNotaDto)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @RequestMapping(value = "/proveedor/{idProveedor}/evaluar",
            method = RequestMethod.PATCH, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> evaluarProveedor(@PathVariable("idProveedor") Integer idProveedor,
                                              @RequestBody Map<String, Object> data) throws Exception {
        Boolean comunidad = Optional.ofNullable(data)
                .map(d -> d.get("comunidad"))
                .filter(h -> h instanceof Boolean)
                .map(h -> (Boolean) h)
                .orElse(false);

        return this.processObject(homologacionService.evaluarProveedorDto(idProveedor, comunidad));
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getAllHomologacion() {
        return this.processList(homologacionFacade.getListAll());
    }

    @RequestMapping(value = "/{idHomologacion}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getHomologacionById(@PathVariable("idHomologacion") Integer id) {
        return this.processObject(homologacionFacade.getHomologacionDto(id));
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> save(@Valid @RequestBody HomologacionDto dto, BindingResult result) {

        getValidator().validate(dto, result);
        dto.setEstado("1");
        if (result.hasErrors()) {
            PortalException ex = new PortalException("Información inválida");
            result.getFieldErrors().stream().forEach(f -> ex.addDetail(f.getField(), f.getDefaultMessage()));
            throw ex;
        }
        return this.processObject(this.homologacionFacade.guardar(dto));
    }

    @RequestMapping(value = "/desactivarPregunta/{idHomologacion}",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public String desactivarPregunta(HttpServletRequest request,
                                     @PathVariable("idHomologacion") int idHomologacion) throws PortalException {
        homologacionService.updateHomologacion(idHomologacion);

        return "Succes";
    }

    @RequestMapping(value = "/activarPregunta/{idHomologacion}",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public String activarPregunta(HttpServletRequest request,
                                     @PathVariable("idHomologacion") int idHomologacion) throws PortalException {
        homologacionService.updateHomologacionActivar(idHomologacion);

        return "Succes";
    }

}
