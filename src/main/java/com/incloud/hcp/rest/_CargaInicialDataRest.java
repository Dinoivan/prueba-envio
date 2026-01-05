package com.incloud.hcp.rest;

import com.incloud.hcp.domain.*;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.util.CsvUtils;
import com.incloud.hcp.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/_cargaInicialDataRest")
public class _CargaInicialDataRest extends AppRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String RUTA_DATA_INICIAL_CSV = "data_inicial/csv/";
    private final String SUFIJO_CSV = ".csv";

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private MonedaRepository monedaRepository;

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private UbigeoRepository ubigeoRepository;

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private CondicionPagoReposity condicionPagoReposity;

    @Autowired
    private TipoProveedorRepository tipoProveedorRepository;

    @Autowired
    private LineaComercialRepository lineaComercialRepository;

    @Autowired
    private SectorTrabajoRepository sectorTrabajoRepository;

    @Autowired
    private TipoComprobanteRepository tipoComprobanteRepository;

    @Autowired
    private EstadoProveedorRepository estadoProveedorRepository;

    @Autowired
    private PreguntaInformacionRepository preguntaInformacionRepository;

    @Autowired
    private CentroAlmacenRepository centroAlmacenRepository;

    @Autowired
    private TipoLicitacionRepository tipoLicitacionRepository;

    @Autowired
    private CondicionLicRepository condicionLicRepository;

    @Autowired
    private CondicionLicRespuestaRepository condicionLicRespuestaRepository;

    @Autowired
    private ClaseDocumentoRepository claseDocumentoRepository;

    @Autowired
    private SubetapaRepository subetapaRepository;

    @Autowired
    private EstadoOrdenCompraRepository estadoOrdenCompraRepository;

    @Autowired
    private TipoOrdenCompraRepository tipoOrdenCompraRepository;

    @Autowired
    private TipoDocumentoAceptacionRepository tipoDocumentoAceptacionRepository;

    @Autowired
    private EstadoDocumentoAceptacionRepository estadoDocumentoAceptacionRepository;

    @Autowired
    private EstadoPrefacturaRepository estadoPrefacturaRepository;

    @Autowired
    private SociedadRepository sociedadRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private TipoDocumentoSapRepository tipoDocumentoSapRepository;


    @Operation(summary = "Carga inicial de Data (Tabla Cargo)")
    @PostMapping(value = "/_cargo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cargo(@RequestParam("file") MultipartFile file) throws URISyntaxException {
        log.debug("Find by id _cargo : {}");
        try {
            this.cargoRepository.deleteAll();
            this.cargoRepository.saveAll(CsvUtils.read(Cargo.class, file.getInputStream()));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Cargo)")
    @PostMapping(value = "/_010_cargo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _010_cargo() throws URISyntaxException {
        log.debug("Find by id _10_cargo : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "10_cargo" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.cargoRepository.deleteAll();
            this.cargoRepository.saveAll(CsvUtils.read(Cargo.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Moneda)")
    @PostMapping(value = "/_020_moneda", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _020_moneda() throws URISyntaxException {
        log.debug("Find by id _20_moneda : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "20_moneda" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.monedaRepository.deleteAll();
            this.monedaRepository.saveAll(CsvUtils.read(Moneda.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Parametro)")
    @PostMapping(value = "/_030_parametro", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _030_parametro() throws URISyntaxException {
        log.debug("Find by id _30_parametro : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "30_parametro" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.parametroRepository.deleteAll();
            this.parametroRepository.saveAll(CsvUtils.read(Parametro.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Parametro)")
    @PostMapping(value = "/_035_parametro_grupo_tesoreria", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _035_parametro() throws URISyntaxException {
        log.debug("Find by id _035_parametro_grupo_tesoreria : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "35_parametro_grupo_tesoreria" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            //this.parametroRepository.deleteByTipo("GRUPO_TESORERIA");
            this.parametroRepository.saveAll(CsvUtils.read(Parametro.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Parametro)")
    @PostMapping(value = "/_036_parametro_grupo_compra", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _036_parametro() throws URISyntaxException {
        log.debug("Find by id _036_parametro_grupo_compra : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "36_parametro_grupo_compra" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            //this.parametroRepository.deleteByTipo("GRUPO_COMPRA");
            this.parametroRepository.saveAll(CsvUtils.read(Parametro.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Ubigeo) - DESACTIVAR SECUENCIA!!!")
    @PostMapping(value = "/_040_ubigeo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _040_ubigeo() throws URISyntaxException {
        log.debug("Find by id _40_ubigeo : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "40_ubigeo" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.ubigeoRepository.deleteAll();
            List<Ubigeo> lista = CsvUtils.read(Ubigeo.class, inputStream);
            for(Ubigeo bean : lista) {
                this.ubigeoRepository.save(bean);
            }
            //this.ubigeoRepository.saveAll();
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Ubigeo LISTA) - DESACTIVAR SECUENCIA!!!")
    @PostMapping(value = "/_041_ubigeo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _041_ubigeo() throws URISyntaxException {
        log.debug("Find by id _41_ubigeo : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "40_ubigeo" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.ubigeoRepository.deleteAll();
            List<Ubigeo> lista = CsvUtils.read(Ubigeo.class, inputStream);
            this.ubigeoRepository.saveAll(lista);
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Banco)")
    @PostMapping(value = "/_050_banco", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _050_banco() throws URISyntaxException {
        log.debug("Find by id _50_Banco : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "50_banco" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.bancoRepository.deleteAll();
            this.bancoRepository.saveAll(CsvUtils.read(Banco.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Condicion Pago)")
    @PostMapping(value = "/_060_condicion_pago", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _060_condicion_pago() throws URISyntaxException {
        log.debug("Find by id _60_Condicion_pago : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "60_condicion_pago" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.condicionPagoReposity.deleteAll();
            this.condicionPagoReposity.saveAll(CsvUtils.read(CondicionPago.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Tipo_Proveedor)")
    @PostMapping(value = "/_070_tipo_proveedor", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _070_tipo_proveedor() throws URISyntaxException {
        log.debug("Find by id _70_tipo_proveedor : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "70_tipo_proveedor" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.tipoProveedorRepository.deleteAll();
            this.tipoProveedorRepository.saveAll(CsvUtils.read(TipoProveedor.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Linea_Comercial)")
    @PostMapping(value = "/_080_linea_comercial", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _080_linea_comercial() throws URISyntaxException {
        log.debug("Find by id _80_linea_comercial : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "80_linea_comercial" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.lineaComercialRepository.deleteAll();
            this.lineaComercialRepository.saveAll(CsvUtils.read(LineaComercial.class, inputStream));


            String nombreCSVfamilia = RUTA_DATA_INICIAL_CSV + "85_linea_comercial" + SUFIJO_CSV;
            ClassPathResource resourceCSVfamilia = new ClassPathResource(nombreCSVfamilia);
            InputStream inputStreamfamilia = resourceCSVfamilia.getInputStream();

            List<LineaComercial> lineaComercialFamiliaList = CsvUtils.read(LineaComercial.class, inputStreamfamilia);
            int contador = 0;
            for(LineaComercial bean : lineaComercialFamiliaList) {
                try {
                    LineaComercial lineaComercialPadre = this.lineaComercialRepository.getByDescripcion(bean.getDescripcionPadre());
                    bean.setIdPadre(lineaComercialPadre.getIdLineaComercial());
                    this.lineaComercialRepository.save(bean);
                }
                catch (Exception e) {
                    log.error("FASE FAMILIA BEAN: " + bean.toString());
                    contador++;
                }
            }

            String nombreCSVsfamilia = RUTA_DATA_INICIAL_CSV + "86_linea_comercial" + SUFIJO_CSV;
            ClassPathResource resourceCSVsfamilia = new ClassPathResource(nombreCSVsfamilia);
            InputStream inputStreamsfamilia = resourceCSVsfamilia.getInputStream();

            List<LineaComercial> lineaComercialsFamiliaList = CsvUtils.read(LineaComercial.class, inputStreamsfamilia);
            for(LineaComercial bean : lineaComercialsFamiliaList) {
                try {
                    LineaComercial lineaComercialPadre = this.lineaComercialRepository.
                            getByCodigoGrupoComercial(bean.getCodigoGrupoComercialPadre());
                    bean.setIdPadre(lineaComercialPadre.getIdLineaComercial());
                    this.lineaComercialRepository.save(bean);
                }
                catch (Exception e) {
                    log.error("FASE subFAMILIA BEAN: " + bean.toString());
                    contador++;
                }
            }

            return new ResponseEntity<>("OK + Errores: " + contador, HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Sector Trabajo)")
    @PostMapping(value = "/_090_sector_trabajo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _090_sector_trabajo() throws URISyntaxException {
        log.debug("Find by id _90_sector_trabajo : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "90_sector_trabajo" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.sectorTrabajoRepository.deleteAll();
            this.sectorTrabajoRepository.saveAll(CsvUtils.read(SectorTrabajo.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Tipo Comprobante)")
    @PostMapping(value = "/_100_tipo_comprobante", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _100_tipo_comprobante() throws URISyntaxException {
        log.debug("Find by id _100_tipo_comprobante : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "100_tipo_comprobante" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.tipoComprobanteRepository.deleteAll();
            this.tipoComprobanteRepository.saveAll(CsvUtils.read(TipoComprobante.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Estado Proveedor) DESACTIVAR SECUENCIAS!!!")
    @PostMapping(value = "/_110_estado_proveedor", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _110_estado_proveedor() throws URISyntaxException {
        log.debug("Find by id _110_estado_proveedor : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "110_estado_proveedor" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.estadoProveedorRepository.deleteAll();
            this.estadoProveedorRepository.saveAll(CsvUtils.read(EstadoProveedor.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Pregunta Informacion)")
    @PostMapping(value = "/_120_pregunta_informacion", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _120_pregunta_informacion() throws URISyntaxException {
        log.debug("Find by id _120_pregunta_informacion : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "120_pregunta_informacion" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.preguntaInformacionRepository.deleteAll();
            this.preguntaInformacionRepository.saveAll(CsvUtils.read(PreguntaInformacion.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Centro)")
    @PostMapping(value = "/_140_centro", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _140_centro() throws URISyntaxException {
        log.debug("Find by id _140_centro : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "140_centro" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.centroAlmacenRepository.deleteAll();
            this.centroAlmacenRepository.saveAll(CsvUtils.read(CentroAlmacen.class, inputStream));

            String nombreCSVAlmacen = RUTA_DATA_INICIAL_CSV + "145_almacen" + SUFIJO_CSV;
            ClassPathResource resourceCSVAlmacen = new ClassPathResource(nombreCSVAlmacen);
            InputStream inputStreamAlmacen = resourceCSVAlmacen.getInputStream();

            List<CentroAlmacen> centroAlmacenList = CsvUtils.read(CentroAlmacen.class, inputStreamAlmacen);
            log.error("centroAlmacenList size(): " + centroAlmacenList.size());
            int contador = 1;
            for(CentroAlmacen bean : centroAlmacenList) {
                try {
                    //log.error("contador bean: " + contador + " - " +bean.toString());
                    CentroAlmacen centroAlmacenPadre = this.centroAlmacenRepository.
                            getByCodigoSap(bean.getCodigoSapPadre());
                    //log.error("centroAlmacenPadre: " + centroAlmacenPadre.toString());
                    bean.setIdPadre(centroAlmacenPadre.getIdCentroAlmacen());
                    this.centroAlmacenRepository.save(bean);
                    contador++;
                }
                catch (Exception e) {
                    log.error("Exception: " + e.getMessage());

                }
            }

            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Tipo Licitacion)")
    @PostMapping(value = "/_150_tipo_licitacion", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _150_tipo_licitacion() throws URISyntaxException {
        log.debug("Find by id _150_tipo_licitacion : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "150_tipo_licitacion" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.tipoLicitacionRepository.deleteAll();
            this.tipoLicitacionRepository.saveAll(CsvUtils.read(TipoLicitacion.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Condicion Lic)")
    @PostMapping(value = "/_160_condicion_lic", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _160_condicion_lic() throws URISyntaxException {
        log.debug("Find by id _160_condicion_lic : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "160_condicion_lic" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.condicionLicRepository.deleteAll();

//            List<CondicionLic> condicionLicList = CsvUtils.read(CondicionLic.class, inputStream);
//            log.error("condicionLicList size(): " + condicionLicList.size());
//            int contador = 1;
//            java.util.Date utilDate = new java.util.Date();
//            System.out.println("java.util.Date time    : " + utilDate);
//            java.sql.Timestamp sqlTS = new java.sql.Timestamp(utilDate.getTime());
//            for(CondicionLic bean : condicionLicList) {
//                try {
//                    TipoLicitacion tipoLicitacion = this.tipoLicitacionRepository.
//                            getByDescripcion(bean.getDescripcionTipoLicitacion());
//                    TipoLicitacion tipoCuestionario = this.tipoLicitacionRepository.
//                            getByDescripcionAndIdPadre(
//                                    bean.getDescripcionTipoCuestionario(),
//                                    tipoLicitacion.getIdTipoLicitacion());
//                    bean.setTipoLicitacion1(tipoLicitacion);
//                    bean.setTipoLicitacion2(tipoCuestionario);
//                    bean.setFechaCreacion(sqlTS);
//                    bean.setUsuarioCreacion("1");
//                    this.condicionLicRepository.save(bean);
//                    contador++;
//                }
//                catch (Exception e) {
//                    log.error("Exception: " + e.getMessage());
//
//                }
//            }
            this.condicionLicRepository.saveAll(CsvUtils.read(CondicionLic.class, inputStream));

            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Condicion Lic Respuesta)")
    @PostMapping(value = "/_170_condicion_lic_respuesta", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _170_condicion_lic_respuesta() throws URISyntaxException {
        log.debug("Find by id _170_condicion_lic_respuesta : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "170_condicion_lic_respuesta" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.condicionLicRespuestaRepository.deleteAll();

//            List<CondicionLicRespuesta> condicionLicList = CsvUtils.read(CondicionLicRespuesta.class, inputStream);
//            log.error("condicionLicList size(): " + condicionLicList.size());
//            int contador = 1;
//            java.util.Date utilDate = new java.util.Date();
//            System.out.println("java.util.Date time    : " + utilDate);
//            java.sql.Timestamp sqlTS = new java.sql.Timestamp(utilDate.getTime());
//            for(CondicionLicRespuesta bean : condicionLicList) {
//                try {
//                    CondicionLic condicionLic = this.condicionLicRepository.getOne(bean.getIdCondicionLic());
//                    bean.setCondicionLic(condicionLic);
//                    bean.setFechaCreacion(sqlTS);
//                    bean.setUsuarioCreacion("1");
//                    this.condicionLicRespuestaRepository.save(bean);
//                    contador++;
//                }
//                catch (Exception e) {
//                    log.error("Exception: " + e.getMessage());
//
//                }
//            }
            this.condicionLicRespuestaRepository.saveAll(CsvUtils.read(CondicionLicRespuesta.class, inputStream));

            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Clase Documento)")
    @PostMapping(value = "/_180_clase_documento", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _180_clase_documento() throws URISyntaxException {
        log.debug("Find by id _180_clase_documento : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "180_clase_documento" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.claseDocumentoRepository.deleteAll();
            this.claseDocumentoRepository.saveAll(CsvUtils.read(ClaseDocumento.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Subetapa)")
    @PostMapping(value = "/_190_subetapa", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _190_subetapa() throws URISyntaxException {
        log.debug("Find by id _190_subetapa : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "190_subetapa" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.subetapaRepository.deleteAll();
            this.subetapaRepository.saveAll(CsvUtils.read(Subetapa.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Unidad Medida)")
    @PostMapping(value = "/_200_unidad_medida", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _200_unidad_medida() throws URISyntaxException {
        log.debug("Find by id unidad_medida : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "200_unidad_medida" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.unidadMedidaRepository.deleteAll();
            this.unidadMedidaRepository.saveAll(CsvUtils.read(UnidadMedida.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @Operation(summary = "Carga inicial de Data (Tabla Tipo Documento Sap)")
    @PostMapping(value = "/_210_tipo_documento_sap", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _210_tipo_documento_sap() throws URISyntaxException {
        log.debug("Find by id unidad_medida : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "210_tipo_documento_sap" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.tipoDocumentoSapRepository.deleteAll();
            this.tipoDocumentoSapRepository.saveAll(CsvUtils.read(TipoDocumentoSap.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }



    /* *********************** MODULO 1 ******************************************************************************* */

    @Operation(summary = "Carga inicial de Data (Tabla Estado Orden Compra)")
    @PostMapping(value = "/_310_estado_orden_compra", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _310_estado_orden_compra() throws URISyntaxException {
        log.debug("Find by id _310_estado_orden_compra : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "310_estado_orden_compra" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.estadoOrdenCompraRepository.deleteAll();
            this.estadoOrdenCompraRepository.saveAll(CsvUtils.read(EstadoOrdenCompra.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Tipo Orden Compra)")
    @PostMapping(value = "/_320_tipo_orden_compra", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _320_tipo_orden_compra() throws URISyntaxException {
        log.debug("Find by id _320_tipo_orden_compra : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "320_tipo_orden_compra" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.tipoOrdenCompraRepository.deleteAll();
            this.tipoOrdenCompraRepository.saveAll(CsvUtils.read(TipoOrdenCompra.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Tipo Documento Aceptacion)")
    @PostMapping(value = "/_330_tipo_documento_aceptacion", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _330_tipo_documento_aceptacion() throws URISyntaxException {
        log.debug("Find by id _330_tipo_documento_aceptacion : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "330_tipo_documento_aceptacion" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.tipoDocumentoAceptacionRepository.deleteAll();
            this.tipoDocumentoAceptacionRepository.saveAll(CsvUtils.read(TipoDocumentoAceptacion.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Estado Documento Aceptacion)")
    @PostMapping(value = "/_340_estado_documento_aceptacion", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _340_estado_documento_aceptacion() throws URISyntaxException {
        log.debug("Find by id _340_estado_documento_aceptacion : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "340_estado_documento_aceptacion" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.estadoDocumentoAceptacionRepository.deleteAll();
            this.estadoDocumentoAceptacionRepository.saveAll(CsvUtils.read(EstadoDocumentoAceptacion.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Carga inicial de Data (Tabla Estado Prefactura)")
    @PostMapping(value = "/_350_estado_prefactura", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> _350_estado_prefactura() throws URISyntaxException {
        log.debug("Find by id _350_estado_prefactura : {}");
        try {
            String nombreCSV = RUTA_DATA_INICIAL_CSV + "350_estado_prefactura" + SUFIJO_CSV;
            ClassPathResource resourceCSV = new ClassPathResource(nombreCSV);
            InputStream inputStream = resourceCSV.getInputStream();
            this.estadoPrefacturaRepository.deleteAll();
            this.estadoPrefacturaRepository.saveAll(CsvUtils.read(EstadoPrefactura.class, inputStream));
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
