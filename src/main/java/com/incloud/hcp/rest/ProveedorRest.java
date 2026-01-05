package com.incloud.hcp.rest;

import com.incloud.hcp.bean.ProveedorCustom;
import com.incloud.hcp.bean.ProveedorFiltro;
import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.EstadoProveedor;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.ListProveedorHCP;
import com.incloud.hcp.dto.PreRegistroProveedorDto;
import com.incloud.hcp.dto.ProveedorDto;
import com.incloud.hcp.dto.ProveedorXLSXDTO;
import com.incloud.hcp.enums.EstadoProveedorEnum;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.facade.PreRegistroFacade;
import com.incloud.hcp.jco.consultaProveedor.service.JCOConsultaProveedorService;
import com.incloud.hcp.repository.EstadoProveedorRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.repository.TipoProveedorRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.rest.bean.ProveedorDatosGeneralesDTO;
import com.incloud.hcp.service.PreRegistroProveedorService;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.UbigeoService;
import com.incloud.hcp.util.StrUtils;
import com.incloud.hcp.util.Utils;
import com.incloud.hcp.wsdl.inside.InSiteResponse;
import com.incloud.hcp.wsdl.inside.InSiteService;
import com.sap.cloud.security.xsuaa.token.Token;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Administrador on 29/08/2017.
 */
@RestController
@RequestMapping(value = "/api/proveedor")
public class ProveedorRest extends AppRest {

    private final int NRO_EJECUCIONES = 10;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InSiteService inSiteService;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private PreRegistroProveedorService preRegistroProveedorService;
    @Autowired
    private JCOConsultaProveedorService jcoConsultaProveedorService;
    @Autowired
    private TipoProveedorRepository tipoProveedorRepository;

    @Autowired
    private UbigeoService ubigeoService;

    @Autowired
    private PreRegistroFacade preRegistroFacade;

    @Autowired
    private EstadoProveedorRepository estadoProveedorRepository;


    @RequestMapping(value = "/sap",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> enviarInformacionASap(@RequestBody Map<String, Integer> json) throws PortalException {
        logger.error("Ingresando enviarInformacionASap");
        Integer idProveedor = json.get("idProveedor");
        ProveedorDto dto = proveedorService.sendToSap(idProveedor);
        logger.error("finalizando enviarInformacionASap: " + dto);
        return this.processObject(dto);
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getProveedor(HttpServletRequest request,@AuthenticationPrincipal Token token) throws PortalException {
        logger.debug("[rest] /proveedor");
        logger.error("Ingresando GET PROVEEDOR");

        UserSession userSession = this.getUserSession(token);
        if (!Optional.ofNullable(userSession.getRuc()).isPresent()) {
            return this.processObject(new ProveedorDto());
        }
        Optional<ProveedorDto> oProveedor = Optional.ofNullable(this.proveedorService.getProveedorDtoByRuc(userSession.getRuc()));

        if (oProveedor.isPresent()) {
            oProveedor.get().setIdHcp(userSession.getId());
            //Actualizar Datos del Ubigeo para los Migrados
            if(oProveedor.get().getIdEstadoProveedor().getCodigoEstadoProveedor().equals(EstadoProveedorEnum.MIGRADO_DE_SAP.getCodigo())){
                logger.error("INGRESANDO MIGRADO");
                Optional<PreRegistroProveedorDto> preRegistro = Optional.ofNullable(preRegistroFacade.getPreRegistroByIdHcp(userSession.getId()));
                //PreRegistroProveedorDto preRegistro = preRegistroFacade.getPreRegistroByIdHcp(userSession.getId());
                if(preRegistro.isPresent()){
                    logger.error("EXISTE EN PRE REGISTRO");
                   // ProveedorDto temp = new ProveedorDto();
                    //BeanUtils.copyProperties(preRegistro, temp);
                    oProveedor.get().setDireccionFiscal(preRegistro.get().getDireccion());
                    oProveedor.get().setActivo(preRegistro.get().getActivo());
                    oProveedor.get().setHabido(preRegistro.get().getHabido());
                    oProveedor.get().setLineasComercial(preRegistro.get().getLineasComercial());
                    oProveedor.get().setCodigoSistemaEmisionElect(preRegistro.get().getCodigoSistemaEmisionElect());
                    oProveedor.get().setCodigoComprobantePago(preRegistro.get().getCodigoComprobantePago());
                    oProveedor.get().setCodigoPadron(preRegistro.get().getCodigoPadron());
                    oProveedor.get().setFechaInicioActiSunat(preRegistro.get().getFechaInicioActiSunat());

                    Optional.ofNullable(preRegistro.get().getIdTipoProveedor())
                            .map(id -> tipoProveedorRepository.getOne(id))
                            .ifPresent(tipoProveedor -> {
                                if (tipoProveedor.getDescripcion().toUpperCase().equals("NACIONAL")) {
                                    Optional.ofNullable(this.ubigeoService.getUbigeoByCodigo("PE"))
                                            .ifPresent(ubigeo -> oProveedor.get().setCodigoPais(ubigeo.getIdUbigeo()));
                                    if (preRegistro.get().getUbigeo().length() > 6) {
                                        Integer nUbigeo = new Integer(preRegistro.get().getUbigeo());
                                        String sUbigeo = nUbigeo.toString();
                                        preRegistro.get().setUbigeo(sUbigeo);
                                    }
                                    Optional.ofNullable(preRegistro.get().getUbigeo())
                                            .filter(u -> !u.isEmpty())
                                            .filter(u -> u.length() == 6)
                                            .ifPresent(codigo -> {
                                                Optional.ofNullable(this.ubigeoService.getUbigeoByCodigo(codigo))
                                                        .ifPresent(ubigeo -> oProveedor.get().setCodigoDistrito(ubigeo.getIdUbigeo()));

                                                Optional.ofNullable(this.ubigeoService.getUbigeoByCodigo(codigo.substring(0, 4)))
                                                        .ifPresent(ubigeo -> oProveedor.get().setCodigoProvincia(ubigeo.getIdUbigeo()));

                                                Optional.ofNullable(this.ubigeoService.getUbigeoByCodigo(codigo.substring(0, 2)))
                                                        .ifPresent(ubigeo -> oProveedor.get().setCodigoRegion(ubigeo.getIdUbigeo()));
                                            });
                                }
                            });

                }

                this.obtenerDatosSunat(userSession, oProveedor);
            }
            return this.processObject(oProveedor.get());
        } else {
            PreRegistroProveedorDto preRegistro = preRegistroFacade.getPreRegistroByIdHcp(userSession.getId());
            ProveedorDto temp = new ProveedorDto();
            EstadoProveedor estadoProveedor = this.estadoProveedorRepository.
                    getByCodigoEstadoProveedor(EstadoProveedorEnum.REGISTRADO.getCodigo());
            temp.setIdEstadoProveedor(estadoProveedor);


            BeanUtils.copyProperties(preRegistro, temp);
            temp.setDireccionFiscal(preRegistro.getDireccion());
            temp.setCelular(preRegistro.getTelefono());
            temp.setTipoPersona("J");
            temp.setEvaluacionHomologacion(new BigDecimal(0.0));



            Optional.ofNullable(preRegistro.getIdTipoProveedor())
                    .map(id -> tipoProveedorRepository.getOne(id))
                    .ifPresent(tipoProveedor -> {
                        if (tipoProveedor.getDescripcion().toUpperCase().equals("NACIONAL")) {
                            Optional.ofNullable(this.ubigeoService.getUbigeoByCodigo("PE"))
                                    .ifPresent(ubigeo -> temp.setCodigoPais(ubigeo.getIdUbigeo()));

                            Optional.ofNullable(preRegistro.getUbigeo())
                                    .filter(u -> !u.isEmpty())
                                    .filter(u -> u.length() == 6)
                                    .ifPresent(codigo -> {
                                        Optional.ofNullable(this.ubigeoService.getUbigeoByCodigo(codigo))
                                                .ifPresent(ubigeo -> temp.setCodigoDistrito(ubigeo.getIdUbigeo()));

                                        Optional.ofNullable(this.ubigeoService.getUbigeoByCodigo(codigo.substring(0, 4)))
                                                .ifPresent(ubigeo -> temp.setCodigoProvincia(ubigeo.getIdUbigeo()));

                                        Optional.ofNullable(this.ubigeoService.getUbigeoByCodigo(codigo.substring(0, 2)))
                                                .ifPresent(ubigeo -> temp.setCodigoRegion(ubigeo.getIdUbigeo()));
                                    });
                        }
                    });

            this.obtenerDatosSunat(userSession, temp);
            return this.processObject(temp);
        }

    }

    @RequestMapping(value = "/dummy",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getProveedorDummy(HttpServletRequest request) throws PortalException {
        logger.debug("[rest] /proveedor");
        logger.error("Ingresando GET PROVEEDOR");
        return this.processObject(new ProveedorDto());
    }


    @RequestMapping(value = "devuelveProveedor",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> devuelveProveedor(HttpServletRequest request,@AuthenticationPrincipal Token token) throws PortalException {
        logger.debug("[rest] /proveedor");

        UserSession userSession = this.getUserSession(token);
        if (!Optional.ofNullable(userSession.getRuc()).isPresent()) {
            return this.processObject(new ProveedorDto());
        }

        Optional<ProveedorDto> oProveedor = Optional.ofNullable(this.proveedorService.getProveedorDtoByRuc(userSession.getRuc()));
        if (oProveedor.isPresent()) {
            oProveedor.get().setIdHcp(userSession.getId());
            this.obtenerDatosSunat(userSession, oProveedor);
            return this.processObject(oProveedor.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "devuelveProveedorNew",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> devuelveProveedorNew(HttpServletRequest request,@AuthenticationPrincipal Token token) throws PortalException {
        logger.debug("[rest] /proveedor");

        UserSession userSession = this.getUserSession(token);
        if (!Optional.ofNullable(userSession.getRuc()).isPresent()) {
            return this.processObject(new ProveedorDto());
        }

        Optional<ProveedorDto> oProveedor = Optional.ofNullable(this.proveedorService.getProveedorDtoByRuc(userSession.getRuc()));
        if (oProveedor.isPresent()) {
            oProveedor.get().setIdHcp(userSession.getId());
            this.obtenerDatosSunat(userSession, oProveedor);
            return this.processObject(oProveedor.get());
        } else {
            ProveedorDto proveedorDto = new ProveedorDto();
            this.obtenerDatosSunat(userSession, proveedorDto);
            return this.processObject(proveedorDto);
        }
    }

    @RequestMapping(value = "/devuelveProveedorResponder",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> devuelveProveedorResponder(HttpServletRequest request,@AuthenticationPrincipal Token token) throws PortalException {
        logger.debug("[rest] /devuelveProveedorResponder");

        UserSession userSession = this.getUserSession(token);
        if (!Optional.ofNullable(userSession.getRuc()).isPresent()) {
            return this.processObject(new ProveedorDto());
        }

        Optional<ProveedorDto> oProveedor = Optional.ofNullable(this.proveedorService.getProveedorDtoByRucResponder(userSession.getRuc()));
        if (oProveedor.isPresent()) {
            oProveedor.get().setIdHcp(userSession.getId());
            this.obtenerDatosSunat(userSession, oProveedor);
            return this.processObject(oProveedor.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void obtenerDatosSunat(UserSession userSession, Optional<ProveedorDto> oProveedorDto) {
        InSiteResponse response = null;
        for (int contador = 0; contador < NRO_EJECUCIONES; contador++) {
            try {
                response = inSiteService.getConsultaRuc(userSession.getRuc());
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES - 1) {
                }
            }
        }
        if (Optional.ofNullable(response).isPresent()) {
            oProveedorDto.get().setActivo(response.getEstado());
            oProveedorDto.get().setHabido(response.getCondicion());
            oProveedorDto.get().setCodigoPadron(response.getCodigoPadron());
            oProveedorDto.get().setCodigoComprobantePago(response.getCodigoComprobantePago());
            oProveedorDto.get().setFechaInicioActiSunat(response.getFechaInicioActiSunat());
            oProveedorDto.get().setCodigoActividadEconomica(response.getActividadEconomica());
        }

    }

    private void obtenerDatosSunat(UserSession userSession, ProveedorDto proveedorDto) {
        InSiteResponse response = null;
        for (int contador = 0; contador < NRO_EJECUCIONES; contador++) {
            try {
                response = inSiteService.getConsultaRuc(userSession.getRuc());
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES - 1) {
                }
            }
        }
        if (Optional.ofNullable(response).isPresent()) {
            proveedorDto.setActivo(response.getEstado());
            proveedorDto.setHabido(response.getCondicion());
            proveedorDto.setCodigoPadron(response.getCodigoPadron());
            proveedorDto.setCodigoComprobantePago(response.getCodigoComprobantePago());
            proveedorDto.setFechaInicioActiSunat(response.getFechaInicioActiSunat());
            proveedorDto.setCodigoActividadEconomica(response.getActividadEconomica());
        }

    }

    @RequestMapping(value = "/{idProveedor}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getProveedorByIdProveedor(@PathVariable("idProveedor") Integer idProveedor) throws PortalException {
        logger.error("Ingresando getProveedorByIdProveedor 00");
        return this.processObject(proveedorService.getProveedorDtoById(idProveedor));
    }

    @RequestMapping(value = "/{idProveedor}/lineas-comerciales",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getListLineaComercialByIdProveedor(@PathVariable("idProveedor") Integer idProveedor) throws PortalException {
        return this.processList(proveedorService.getListLineaComercialByIdProveedor(idProveedor));
    }

    @RequestMapping(value = "/ruc/{rucProveedor}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getProveedorByRucProveedor(@PathVariable("rucProveedor") String ruc) throws PortalException {
        return this.processList(this.proveedorService.getListProveedorByRuc(ruc));
    }


    @RequestMapping(value = "/obtenerProveedor/{rucProveedor}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> obtenerProveedorByRuc(@PathVariable("rucProveedor") String ruc) throws PortalException {
        Proveedor proveedor = this.proveedorRepository.getProveedorByRuc(ruc);
        return ResponseEntity.ok().body(proveedor);
    }


    @RequestMapping(value = "/filtro", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> devuelveListaLicitacionByFiltro(@RequestBody ProveedorFiltro filtro) {
        return this.processList(this.proveedorService.getListProveedorByFiltro(filtro));
    }

    @RequestMapping(value = "/filtroPaginado", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> devuelveListaLicitacionByFiltroPaginado(@RequestBody ProveedorFiltro filtro) {
        return this.processList(this.proveedorService.getListProveedorByFiltroPaginado(filtro));
    }

    @RequestMapping(value = "/filtroDataMaestra", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> filtroDataMaestra(@RequestBody ProveedorFiltro filtro) {
        filtro.setEstadoProveedor(EstadoProveedorEnum.REGISTRADO.getCodigo());
        return this.processList(this.proveedorService.getListProveedorByFiltro(filtro));
    }

    @RequestMapping(value = "/filtroValidacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> filtroValidacion(@RequestBody ProveedorFiltro filtro,@AuthenticationPrincipal Token token) {
        UserSession userSession = this.getUserSession(token);
        return this.processList(this.proveedorService.getListProveedorByFiltroValidacion(userSession, filtro));
    }

    @RequestMapping(value = "/filtroLicitacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> filtroLicitacion(@RequestBody ProveedorFiltro filtro,@AuthenticationPrincipal Token token) {
        UserSession userSession = this.getUserSession(token);
        return this.processList(this.proveedorService.getListProveedorByFiltroLicitacion(filtro));
    }

    @RequestMapping(value = "/filtroLicitacionPaginado", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> filtroLicitacionPaginado(@RequestBody ProveedorFiltro filtro,@AuthenticationPrincipal Token token) {
        UserSession userSession = this.getUserSession(token);
        return this.processList(this.proveedorService.getListProveedorByFiltroLicitacionPaginado(filtro));
    }


    @RequestMapping(value = "/validarDataMaestra/{idProveedor}",
            method = RequestMethod.PATCH, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> validarDataMaestra(@PathVariable("idProveedor") Integer idProveedor) throws PortalException {
        this.proveedorService.evaluarDataMaestra(idProveedor);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping(value = "/rechazarDataMaestra/{idProveedor}",
            method = RequestMethod.PATCH, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> rechazarDataMaestra(@PathVariable("idProveedor") Integer idProveedor) throws PortalException {
        this.proveedorService.rechazarDataMaestra(idProveedor);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


    @Operation(summary = "Elimina todos los datos del proveedor")
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminaDatosProveedor(@PathVariable Integer id) throws URISyntaxException {
        logger.debug("Delete by id eliminaDatosProveedor : {}", id);
        try {
            this.proveedorService.eliminarDatosProveedor(id);
            return ResponseEntity.ok().build();
        } catch (Exception x) {
            // todo: dig exception, most likely
            String error = Utils.obtieneMensajeErrorException(x);
            throw new RuntimeException(error);
        }
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> save(@RequestBody ProveedorDto proveedorDto,
                                    BindingResult result)  {
        logger.error("Ingresando GRABAR PROVEEDOR: " + proveedorDto);

        try {
            return this.processObject(proveedorService.saveDto(proveedorDto));
        } catch (Exception ex) {
            ex.printStackTrace();
            PortalException pex = new PortalException(Utils.obtieneMensajeErrorException(ex));
            throw pex;
        }
    }


    @RequestMapping(value = "/id/{idProveedor}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Proveedor> getProveedorById(@PathVariable("idProveedor") Integer idProveedor) throws PortalException {
        Proveedor proveedor = this.proveedorRepository.getOne(idProveedor);
        return ResponseEntity.ok().body(proveedor);
    }

    @RequestMapping(value = "/getProveedorDatosGenerales",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ProveedorDatosGeneralesDTO>> getProveedorDatosGenerales(
            @RequestBody Map<String, Object> json) throws PortalException {
        String fechaCreacionIni = (String) json.get("fechaCreacionIni");
        String fechaCreacionFin = (String) json.get("fechaCreacionFin");
        List<ProveedorDatosGeneralesDTO> listaProveedorDatosGenerales = this.proveedorService.getProveedorDatosGenerales(fechaCreacionIni, fechaCreacionFin);

        return ResponseEntity.ok().body(listaProveedorDatosGenerales);
    }

    @RequestMapping(value = "/listaProveedorSinHCPID",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ProveedorCustom>> getListaProveedoresSinHcpID() throws PortalException {

        List<ProveedorCustom> listaProveedores = this.proveedorService.getListProveedorSinHcpID();

        return ResponseEntity.ok().body(listaProveedores);
    }

    @RequestMapping(value = "/actualizarIDHCPProveedor", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<Integer> devuelveCountByEstadoLicitacion(@RequestBody ListProveedorHCP listProveedorHCP)  {
        Integer nroProveedoresActualizados = this.proveedorService.updateProveedorIDHCP(listProveedorHCP);
        return ResponseEntity.ok().body(nroProveedoresActualizados);
    }


    @RequestMapping(value = "/actualizarEmailProveedor",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> saveEmail(@RequestParam(value = "ruc") String ruc,
                                            @RequestParam(value = "email") String email)  {
        logger.error("Ingresando MODIFICAR EMAIL PROVEEDOR: RUC = " + ruc + ", nuevoEmail = " + email);
        try {
            logger.error("<--MC_LOG-->:ProveedorRest/actualizarEmailProveedor:");
            logger.error("<--MC_LOG-->:ProveedorRest/actualizarEmailProveedor:"+ruc+" - "+email);
            String respuesta = proveedorService.saveEmail(ruc, email);
            logger.error("Respuesta MODIFICAR EMAIL PROVEEDOR: " + respuesta);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception ex) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(ex);
            logger.error("Error MODIFICAR EMAIL PROVEEDOR: " + error);
            throw new PortalException(error);
        }
    }

    @PostMapping("/xlsx/file/")
    public ResponseEntity<List<ProveedorXLSXDTO>> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            InputStream in = file.getInputStream();
            logger.error("<--MC_LOG-->:ProveedorRest/xlsx/:");
            List<ProveedorXLSXDTO> result = this.proveedorService.uploadExcel(in);
            for (ProveedorXLSXDTO beanUpload : result) {
                if (beanUpload.isError()) {
                    continue;
                }
                ProveedorDto bean = beanUpload.getProveedor();
                try {
                    logger.error("Ingresando bean :" + bean.toString());
                    logger.error("<--MC_LOG-->:ProveedorRest-listaProveedorByRFC-xlsx/file:");
                    this.jcoConsultaProveedorService.listaProveedorByRFC(bean.getAcreedorCodigoSap(),"","",bean.getEmail(),bean.getTipoPersona());
                    beanUpload.setError(false);
                    beanUpload.setMensaje("SE AGREGO EL PROVEEDOR A LA BD " + bean.getAcreedorCodigoSap());
                    beanUpload.setProveedor(null);
                } catch (Exception e) {
                    beanUpload.setError(true);
                    beanUpload.setMensaje(Utils.obtieneMensajeErrorException(e));

                    logger.error("Ingresando bean error uploadExcel:" + e.getMessage());
                    e.printStackTrace();
                }
            }
            return Optional.of(result).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping(value = "getAllProveedores")
    public List<Proveedor> consultaTransportistas() {
        try{
            return this.proveedorRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
