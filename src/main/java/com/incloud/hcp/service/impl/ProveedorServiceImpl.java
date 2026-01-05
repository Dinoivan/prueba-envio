package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.ProveedorCustom;
import com.incloud.hcp.bean.ProveedorFiltro;
import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.dto.*;
import com.incloud.hcp.dto.blacklist.SolicitudBlackListDTOMapper;
import com.incloud.hcp.dto.mapper.*;
import com.incloud.hcp.enums.EstadoBlackListEnum;
import com.incloud.hcp.enums.EstadoProveedorEnum;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.exception.ServiceException;
import com.incloud.hcp.jco.consultaProveedor.service.JCOConsultaProveedorService;
import com.incloud.hcp.myibatis.mapper.*;
import com.incloud.hcp.populate.Populater;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.rest.bean.ProveedorDatosGeneralesDTO;
import com.incloud.hcp.sap.proveedor.ProveedorBeanSAP;
import com.incloud.hcp.sap.proveedor.ProveedorResponse;
import com.incloud.hcp.sap.proveedor.ProveedorWebService;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.cmiscf.CmisBaseService;
import com.incloud.hcp.service.cmiscf.bean.CmisFile;
import com.incloud.hcp.service.delta.UsuarioLineaComercialDeltaService;
import com.incloud.hcp.service.notificacion.ProveedorDataMaestraNotificacion;
import com.incloud.hcp.util.Constant;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.StrUtils;
import com.incloud.hcp.util.Utils;
import com.incloud.hcp.util.constant.LicitacionConstant;
import com.incloud.hcp.util.constant.ParametroConstant;
import com.incloud.hcp.util.constant.ParametroTipoConstant;
import com.incloud.hcp.util.constant.TipoSolicitudConstant;
import com.incloud.hcp.wsdl.inside.InSiteResponse;
import com.incloud.hcp.wsdl.inside.InSiteService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrador on 29/08/2017.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProveedorServiceImpl implements ProveedorService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int NRO_EJECUCIONES = 10;

    @Value("${sm.portal.dev}")
    private Boolean isDev;

    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ProveedorCanalContactoRepository proveedorCanalContactoRepository;

    @Autowired
    private ProveedorAdjuntoSunatRepository proveedorAdjuntoSunatRepository;

    @Autowired
    private ProveedorCuentaBancoRepository proveedorCuentaBancoRepository;
    @Autowired
    private ProveedorLineaComercialRepository proveedorLineaComercialRepository;
    @Autowired
    private ProveedorProductoRepository proveedorProductoRepository;
    @Autowired
    private ProveedorEvaluacionRepository proveedorEvaluacionRepository;
    @Autowired
    private SolicitudBlackListRepository solicitudBlackListRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProveedorHomologacionMapper proveedorHomologacionMapper;
    @Autowired
    private JCOConsultaProveedorService jcoConsultaProveedorService;

    @Autowired
    private ContactoMapper contactoMapper;
    @Autowired
    private CuentaBancariaMapper cuentaBancariaMapper;

    @Autowired
    private BancoRepository bancoRepository;
    @Autowired
    private MonedaRepository monedaRepository;
    @Autowired
    protected MessageSource messageSource;
    @Autowired
    private UbigeoMapper ubigeoMapper;

    @Autowired
    private ProveedorMapper proveedorMapperMybatis;
    @Autowired
    private CondicionPagoReposity condicionPagoReposity;
    @Autowired
    private SectorTrabajoRepository sectorTrabajoRepository;
    @Autowired
    private TipoComprobanteRepository tipoComprobanteRepository;
    @Autowired
    private TipoProveedorRepository tipoProveedorRepository;
    @Autowired
    private LineaComercialRepository lineaComercialRepository;
    @Autowired
    private ProveedorCatalogoRepository proveedorCatalogoRepository;
    @Autowired
    private UbigeoRepository ubigeoRepository;
    @Autowired
    private PreRegistroProveedorRepository preRegistroProveedorRepository;

    @Autowired
    private LicitacionProveedorRepository licitacionProveedorRepository;

    @Autowired
    private LicitacionProveedorMapper licitacionProveedorMapper;

    @Autowired
    private BlackListMapper blackListMapper;

    @Autowired
    private ProveedorWebService proveedorWebService;

    @Autowired
    private HomologacionMapper homologacionMapper;


    @Autowired
    private ParametroMapper parametroMapper;

    @Autowired
    private ProveedorSectorTrabajoRepository proveedorSectorTrabajoRepository;

    @Autowired
    private EstadoProveedorRepository estadoProveedorRepository;

    @Autowired
    private ProveedorInstalacionRepository proveedorInstalacionRepository;

    @Autowired
    private ProveedorFuncionarioRepository proveedorFuncionarioRepository;

    @Autowired
    private ProveedorPreguntaInformacionRepository proveedorPreguntaInformacionRepository;

    @Autowired
    private ProveedorClienteRepository proveedorClienteRepository;

    @Autowired
    private ProveedorPermisoRepository proveedorPermisoRepository;

    @Autowired
    private PreguntaInformacionRepository preguntaInformacionRepository;

    @Autowired
    private ProveedorHomologacionRepository proveedorHomologacionRepository;

    @Autowired
    private ProveedorInstalacionMapper proveedorInstalacionMapper;

    @Autowired
    private ProveedorPermisoMapper proveedorPermisoMapper;

    @Autowired
    private ProveedorPreguntaInformacionMapper proveedorPreguntaInformacionMapper;

    @Autowired
    private ProveedorClienteMapper proveedorClienteMapper;

    @Autowired
    private ProveedorFuncionarioMapper proveedorFuncionarioMapper;

    @Autowired
    private UsuarioLineaComercialDeltaService usuarioLineaComercialDeltaService;

    @Autowired
    private ProveedorDataMaestraNotificacion proveedorDataMaestraNotificacion;

    @Autowired
    private CmisBaseService cmisService;

    @Autowired
    private InSiteService inSiteService;


    private Populater<SectorTrabajo, SectorTrabajoDto> sectorTrabajoPopulater;

    public ProveedorServiceImpl() {
    }

    @Autowired
    @Qualifier(value = "sectorTrabajoPopulate")
    public void setSectorTrabajoPopulater(Populater<SectorTrabajo, SectorTrabajoDto> sectorTrabajoPopulater) {
        this.sectorTrabajoPopulater = sectorTrabajoPopulater;
    }

    @Autowired
    public void setProveedorSectorTrabajoRepository(ProveedorSectorTrabajoRepository proveedorSectorTrabajoRepository) {
        this.proveedorSectorTrabajoRepository = proveedorSectorTrabajoRepository;
    }

    private Proveedor getOne(Integer idProveedor) {
        Proveedor proveedor = this.proveedorRepository.getOne(idProveedor);
        if (Optional.ofNullable(proveedor).isPresent()) {
            if (proveedor.getTipoComprobante().getCodigoTipoComprobante().equalsIgnoreCase(Constant.SAP_TIPO_PROVEEDOR_NACIONAL)) {
                if (Optional.ofNullable(proveedor.getRuc()).isPresent()) {

                    InSiteResponse response = null;
                    for (int contador = 0; contador < NRO_EJECUCIONES; contador++) {
                        try {
                            response = inSiteService.getConsultaRuc(proveedor.getRuc());
                            break;
                        } catch (Exception e) {
                            if (contador == NRO_EJECUCIONES - 1) {
                                throw new PortalException(e.getMessage());
                            }
                        }
                    }
                    if (Optional.ofNullable(response).isPresent()) {
                        BeanUtils.copyProperties(response, proveedor);
                        Optional.ofNullable(response.isEstado()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndActivoSunat);
                        Optional.ofNullable(response.isCondicion()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndHabidoSunat);
                    }

//                try {
//                    InSiteResponse response = inSiteService.getConsultaRuc(proveedor.getRuc());
//                    if (response != null) {
//                        BeanUtils.copyProperties(response, proveedor);
////                        proveedor.setFechaInicioActiSunat(response.getFechaInicioActiSunat());
////                        proveedor.setCodigoComprobantePago(response.getCodigoComprobantePago());
////                        proveedor.setCodigoSistemaEmisionElect(response.getCodigoSistemaEmisionElect());
////                        proveedor.setCodigoPadron(response.getCodigoPadron());
//                        Optional.ofNullable(response.isEstado()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndActivoSunat);
//                        Optional.ofNullable(response.isCondicion()).map(v -> v ? "1" : "0").ifPresent(proveedor::setIndHabidoSunat);
//                    }
//                } catch (InSiteException ex) {
//
//                }
                }
            }
        }
        return proveedor;
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorDto getProveedorDtoById(Integer idProveedor) throws PortalException {
        logger.error("Ingresando getProveedorDtoById 00" + idProveedor);
        return Optional.ofNullable(this)
                .map(r -> r.getOne(idProveedor))
                .map(this::toDto)
                .orElse(new ProveedorDto());
    }

    @Override
    @Transactional(readOnly = true)
    public Proveedor getProveedorById(Integer idProveedor) throws PortalException {
        return Optional.ofNullable(proveedorRepository)
                .map(r -> r.getOne(idProveedor))
                .orElse(null);
    }

    public void eliminarDatosProveedor(Integer idProveedor) {
        Proveedor p = this.getProveedorById(idProveedor);
        if (!Optional.ofNullable(p).isPresent()) {
            return ;
        }
        this.proveedorHomologacionRepository.deleteRespuestaByIdProveedor(p.getIdProveedor());
        this.proveedorCanalContactoRepository.deleteCanalContactoByIdProveedor(p.getIdProveedor());
        this.proveedorCuentaBancoRepository.deleteContactoByIdProveedor(p.getIdProveedor());
        this.proveedorLineaComercialRepository.deleteLineaComercialByIdProveedor(p.getIdProveedor());
        this.proveedorProductoRepository.deleteProductoByIdProveedor(p.getIdProveedor());
        this.proveedorSectorTrabajoRepository.deleteSectorTrabajoByIdProveedor(p.getIdProveedor());

        this.proveedorInstalacionRepository.deleteInstalacionByIdProveedor(p.getIdProveedor());
        this.proveedorFuncionarioRepository.deleteFuncionarioByIdProveedor(p.getIdProveedor());
        this.proveedorPreguntaInformacionRepository.deletePreguntaInformacionByIdProveedor(p.getIdProveedor());
        this.proveedorClienteRepository.deleteClienteByIdProveedor(p.getIdProveedor());
        this.proveedorPermisoRepository.deletePermisoByIdProveedor(p.getIdProveedor());
        this.proveedorRepository.deleteById(p.getIdProveedor());
        return ;

    }


    @Override
    public Proveedor save(Proveedor documento) {
        return proveedorRepository.save(documento);
    }

    @Override
    public ProveedorDto saveDto(ProveedorDto dto) throws Exception {
        if (dto == null) {
            throw new Exception("ProveedorDto ingresado es NULL");
        }
        if (dto.getIdProveedor() == null || dto.getIdProveedor() == 0) {
            Proveedor proveedorRuc = this.proveedorRepository.getProveedorByRuc(dto.getRuc());
            if (proveedorRuc != null && proveedorRuc.getIdProveedor() != null) {
                dto.setIdProveedor(proveedorRuc.getIdProveedor());
            }
        }
        logger.error("Ingresando GRABAR PROVEEDOR Service 1");
        ProveedorDTOMapper proveedorDTOMapper = new ProveedorDTOMapper(
                this.condicionPagoReposity,
                this.ubigeoMapper,
                this.monedaRepository,
                this.tipoComprobanteRepository,
                this.tipoProveedorRepository);
        Optional<Proveedor> encontrado = Optional.ofNullable(dto.getIdProveedor())
                .map(this.proveedorRepository::getOne);
        Proveedor p;
        Proveedor data = proveedorDTOMapper.toEntity(dto);
        logger.error("Ingresando GRABAR PROVEEDOR Service 4 data: " + data.toString());
        EstadoProveedor estadoProveedor = this.estadoProveedorRepository.
                getByCodigoEstadoProveedor(EstadoProveedorEnum.REGISTRADO.getCodigo());

        if (encontrado.isPresent()) {
            logger.error("Ingresando GRABAR PROVEEDOR Service 5");
            p = encontrado.get();
            BeanUtils.copyProperties(data, p);
            p.setUsuarioCreacion(0);
            p.setFechaModificacion(new Date());
            //p.setIndHomologado("0");
            //p.setEvaluacionHomologacion(new BigDecimal(0));

            this.proveedorCanalContactoRepository.deleteCanalContactoByIdProveedor(p.getIdProveedor());
            this.proveedorCuentaBancoRepository.deleteContactoByIdProveedor(p.getIdProveedor());
            this.proveedorLineaComercialRepository.deleteLineaComercialByIdProveedor(p.getIdProveedor());
            this.proveedorProductoRepository.deleteProductoByIdProveedor(p.getIdProveedor());
            this.proveedorSectorTrabajoRepository.deleteSectorTrabajoByIdProveedor(p.getIdProveedor());

            this.proveedorInstalacionRepository.deleteInstalacionByIdProveedor(p.getIdProveedor());
            this.proveedorFuncionarioRepository.deleteFuncionarioByIdProveedor(p.getIdProveedor());
            this.proveedorPreguntaInformacionRepository.deletePreguntaInformacionByIdProveedor(p.getIdProveedor());
            this.proveedorClienteRepository.deleteClienteByIdProveedor(p.getIdProveedor());
            this.proveedorPermisoRepository.deletePermisoByIdProveedor(p.getIdProveedor());
            logger.error("Ingresando GRABAR PROVEEDOR Service 6");

            EstadoProveedor estadoProveedorActual = p.getIdEstadoProveedor();
            if (estadoProveedorActual.getCodigoEstadoProveedor().equals(EstadoProveedorEnum.RECHAZADO_DATA_MAESTRA.getCodigo())) {
                p.setIdEstadoProveedor(estadoProveedor);
            }
            if (estadoProveedorActual.getCodigoEstadoProveedor().equals(EstadoProveedorEnum.MIGRADO_DE_SAP.getCodigo())) {
                p.setIdEstadoProveedor(estadoProveedor);

            }
        } else {
            logger.error("Ingresando GRABAR PROVEEDOR Service 7");


            p = new Proveedor();
            BeanUtils.copyProperties(data, p);
            p.setIdEstadoProveedor(estadoProveedor);
            p.setFechaCreacion(new Date());
            p.setIndProveedorComunidad("0");
            p.setEvaluacionDesempeno(new BigDecimal(0));
            p.setEvaluacionHomologacion(new BigDecimal(0));
            p.setIndBlackList("0");
            p.setIndBloqueadoSap("0");
            p.setIndHomologado("0");
            p.setIndSujetoRetencion("0");
            p.setUsuarioCreacion(0);
            p.setFechaCreacion(new Date());
            logger.error("Ingresando GRABAR PROVEEDOR Service 8");
        }

        logger.error("Ingresando GRABAR PROVEEDOR Service 9 p: " + p.toString());
        final Proveedor proveedor = this.proveedorRepository.save(p);
        logger.error("Ingresando GRABAR PROVEEDOR Service 10");

        /**
         * Contactos por canales de distribución
         */
        CanalContactoDTOMapper canalContactoDTOMapper = new CanalContactoDTOMapper(this.ubigeoMapper);
        Optional.ofNullable(dto.getCanales())
                .ifPresent(l -> l.stream()
                        .map(canalContactoDTOMapper::toEntity)
                        .forEach(c -> {
                            c.setProveedor(proveedor);
                            this.proveedorCanalContactoRepository.save(c);
                        }));

        /**
         * Cuentas de Banco
         */
        CuentaBancoDTOMapper bancoDTOMapper = new CuentaBancoDTOMapper(this.bancoRepository, this.monedaRepository);
        Optional.ofNullable(dto.getCuentasBanco())
                .ifPresent(l -> l.stream()
                        .map(bancoDTOMapper::toEntity)
                        .forEach(b -> {
                            ProveedorCuentaBancaria pcb = new ProveedorCuentaBancaria();
                            pcb.setIdCuenta(null);
                            Optional.ofNullable(b.getTipoCuenta())
                                    .map(t -> t.getCodigo()).ifPresent(pcb::setClaveControlBanco);
                            pcb.setContacto(b.getContacto());
                            pcb.setBanco(b.getBanco());
                            pcb.setMoneda(b.getMoneda());
                            pcb.setNumeroCuenta(b.getNumeroCuenta());
                            pcb.setNumeroCuentaCci(b.getNumeroCuentaCci());
                            pcb.setArchivoId(b.getArchivoId());
                            pcb.setArchivoNombre(b.getArchivoNombre());
                            pcb.setArchivoTipo(b.getArchivoTipo());
                            pcb.setRutaAdjunto(b.getRutaAdjunto());
                            pcb.setProveedor(proveedor);
                            this.proveedorCuentaBancoRepository.save(pcb);
                        }));
        /**
         * Lineas Comerciales
         */
        ProveedorLineaComercialDTOMapper lineaDtoMapper = new ProveedorLineaComercialDTOMapper(this.lineaComercialRepository);
        Optional.ofNullable(dto.getLineasComercial())
                .ifPresent(l -> l.stream()
                        .map(lineaDtoMapper::toEntity)
                        .forEach(n -> {
                            n.setProveedor(proveedor);
                            this.proveedorLineaComercialRepository.save(n);
                        }));
        /**
         * Productos
         */
        ProveedorProductoDTOMapper productoDTOMapper = new ProveedorProductoDTOMapper();
        Optional.ofNullable(dto.getProductos())
                .ifPresent(l -> l.stream()
                        .map(productoDTOMapper::toEntity)
                        .forEach(pr -> {
                            pr.setProveedor(proveedor);
                            this.proveedorProductoRepository.save(pr);
                        }));

        /**
         * Instalaciones
         */
        Proveedor proveedorPadre = new Proveedor();
        proveedorPadre.setIdProveedor(proveedor.getIdProveedor());
        List<ProveedorInstalacion> proveedorInstalacionList = dto.getInstalaciones();
        if (Optional.ofNullable(proveedorInstalacionList).isPresent()) {
            for (ProveedorInstalacion bean : proveedorInstalacionList) {
                bean.setIdProveedor(proveedorPadre);
                logger.error("Instalaciones bean instalacion: " + bean.toString());
            }
            logger.error("Instalaciones proveedorInstalacionList: " + proveedorInstalacionList.toString());
            this.proveedorInstalacionRepository.saveAll(proveedorInstalacionList);
        }

        /**
         * Permisos
         */
        List<ProveedorPermiso> proveedorPermisoList = dto.getPermisos();
        if (Optional.ofNullable(proveedorPermisoList).isPresent()) {
            for (ProveedorPermiso bean : proveedorPermisoList) {
                bean.setIdProveedor(proveedorPadre);
            }
            this.proveedorPermisoRepository.saveAll(proveedorPermisoList);
        }

        /**
         * Principales
         */
        List<ProveedorCliente> proveedorClienteList = dto.getPrincipales();
        if (Optional.ofNullable(proveedorClienteList).isPresent()) {
            for (ProveedorCliente bean : proveedorClienteList) {
                bean.setIdProveedor(proveedorPadre);
            }
            this.proveedorClienteRepository.saveAll(proveedorClienteList);
        }

        /**
         * Adicionales
         */
        List<ProveedorFuncionario> proveedorFuncionarioList = dto.getAdicionales();
        if (Optional.ofNullable(proveedorFuncionarioList).isPresent()) {
            for (ProveedorFuncionario bean : proveedorFuncionarioList) {
                bean.setIdProveedor(proveedorPadre);
            }
            this.proveedorFuncionarioRepository.saveAll(proveedorFuncionarioList);
        }

        /**
         * Pregunta Informacion
         */
        List<ProveedorPreguntaInformacion> proveedorPreguntaInformacionList = dto.getPreguntaInformacion();
        if (Optional.ofNullable(proveedorPreguntaInformacionList).isPresent()) {
            for (ProveedorPreguntaInformacion bean : proveedorPreguntaInformacionList) {
                bean.setIdProveedor(proveedorPadre);
                PreguntaInformacion preguntaInformacion = bean.getIdPreguntaInformacion();
                if (preguntaInformacion.isRespuestaSiNo()) {
                    bean.setRespuesta(Constant.N);
                    if (bean.isRespuestaSiNo()) {
                        bean.setRespuesta(Constant.S);
                    }
                }
            }
            this.proveedorPreguntaInformacionRepository.saveAll(proveedorPreguntaInformacionList);
        }

        /**
         *  Sector de trabajo
         */
        Optional.ofNullable(dto.getSectorTrabajos())
                .ifPresent(list -> list.stream()
                        .map(SectorTrabajoDto::getIdSectorTrabajo)
                        .map(id -> this.sectorTrabajoRepository.getOne(id))
                        .forEach(st -> {
                            final ProveedorSectorTrabajo pst = new ProveedorSectorTrabajo();
                            pst.setSectorTrabajo(st);
                            pst.setProveedor(proveedor);
                            this.proveedorSectorTrabajoRepository.save(pst);
                        }));

        logger.error("Finalizando GRABAR Sector trabajo");
        /////guardar AdjuntoSunat

        logger.error("proveedor_adjuntos " + proveedor);
        List<ProveedorAdjuntoSunat> listAdjuntosSunat = this.guardarAdjuntoSunat(proveedor, dto.getAdjuntosSunat(), null);
        logger.error("Finalizando GRABAR AdjuntoSunat");


        ////guardar Catalogos
        List<ProveedorCatalogo> listCatalogo=this.guardarAdjuntoCatalogo(proveedor,dto.getCatalogos(),null);
        logger.error("Finalizando GRABAR catalogos");


        dto.setIdProveedor(proveedor.getIdProveedor());
        //dto.setAdjuntosSunat(listAdjuntosSunat);
        logger.error("Finalizando GRABAR PROVEEDOR Service");
        return dto;
    }

    @Override
    public List<Proveedor> getListProveedor() {
        return proveedorRepository.findAll();
    }

    @Override
    public List<ProveedorCustom> getListProveedorByFiltro(ProveedorFiltro proveedorFiltro) {

        return proveedorMapperMybatis.getListProveedorByFiltro(
                proveedorFiltro.getIdsPais(),
                proveedorFiltro.getRazonSocial(),
                proveedorFiltro.getIdsRegion(),
                proveedorFiltro.getIdsProvincia(),
                proveedorFiltro.getNroRuc(),
                proveedorFiltro.getTipoProveedor(),
                proveedorFiltro.getTipoPersona(),
                proveedorFiltro.getIndHomologado(),
                proveedorFiltro.getMarca(),
                proveedorFiltro.getProducto(),
                proveedorFiltro.getDescripcionAdicional(),
                proveedorFiltro.getIdsLinea(),
                proveedorFiltro.getIdsFamilia(),
                proveedorFiltro.getIdsSubFamilia(),
                proveedorFiltro.getEstadoProveedor()
        );
    }

    @Override
    public List<ProveedorCustom> getListProveedorByFiltroPaginado(ProveedorFiltro proveedorFiltro) {
        Integer paginaMostrar = new Integer((proveedorFiltro.getPaginaMostrar().intValue() - 1) * proveedorFiltro.getNroRegistros());
        proveedorFiltro.setPaginaMostrar(paginaMostrar);
        return proveedorMapperMybatis.getListProveedorByFiltroPaginado(
                proveedorFiltro.getIdsPais(),
                proveedorFiltro.getRazonSocial(),
                proveedorFiltro.getIdsRegion(),
                proveedorFiltro.getIdsProvincia(),
                proveedorFiltro.getNroRuc(),
                proveedorFiltro.getTipoProveedor(),
                proveedorFiltro.getTipoPersona(),
                proveedorFiltro.getIndHomologado(),
                proveedorFiltro.getMarca(),
                proveedorFiltro.getProducto(),
                proveedorFiltro.getDescripcionAdicional(),
                proveedorFiltro.getIdsLinea(),
                proveedorFiltro.getIdsFamilia(),
                proveedorFiltro.getIdsSubFamilia(),
                proveedorFiltro.getEstadoProveedor(),
                proveedorFiltro.getNroRegistros(),
                proveedorFiltro.getPaginaMostrar()
        );
    }

    @Override
    public List<ProveedorCustom> getListProveedorByFiltroValidacion(UserSession userSession, ProveedorFiltro proveedorFiltro) {
        List<LineaComercial> lineaComercialList = this.usuarioLineaComercialDeltaService.
                devuelveLineaComercial(userSession);
        if (lineaComercialList == null || lineaComercialList.size() <=0) {
            return null;
        }
        ArrayList<String> idsFamilia = new ArrayList<String>();
        for(LineaComercial bean : lineaComercialList) {
            String idLinea = bean.getIdLineaComercial().toString();
            idsFamilia.add(idLinea);
        }
        proveedorFiltro.setIdsFamilia(idsFamilia);

        return proveedorMapperMybatis.getListProveedorByFiltroValidacion(
                proveedorFiltro.getIdsPais(),
                proveedorFiltro.getIdsRegion(),
                proveedorFiltro.getIdsProvincia(),
                proveedorFiltro.getNroRuc(),
                proveedorFiltro.getRazonSocial(),
                proveedorFiltro.getTipoProveedor(),
                proveedorFiltro.getTipoPersona(),
                proveedorFiltro.getIndHomologado(),
                proveedorFiltro.getMarca(),
                proveedorFiltro.getProducto(),
                proveedorFiltro.getDescripcionAdicional(),
                proveedorFiltro.getIdsLinea(),
                proveedorFiltro.getIdsFamilia(),
                proveedorFiltro.getIdsSubFamilia()
        );
    }

    @Override
    public List<ProveedorCustom> getListProveedorByFiltroLicitacion(ProveedorFiltro proveedorFiltro) {
        return proveedorMapperMybatis.getListProveedorByFiltroLicitacion(
                proveedorFiltro.getIdsPais(),
                proveedorFiltro.getRazonSocial(),
                proveedorFiltro.getIdsRegion(),
                proveedorFiltro.getIdsProvincia(),
                proveedorFiltro.getNroRuc(),
                proveedorFiltro.getTipoProveedor(),
                proveedorFiltro.getTipoPersona(),
                proveedorFiltro.getIndHomologado(),
                proveedorFiltro.getMarca(),
                proveedorFiltro.getProducto(),
                proveedorFiltro.getDescripcionAdicional(),
                proveedorFiltro.getIdsLinea(),
                proveedorFiltro.getIdsFamilia(),
                proveedorFiltro.getIdsSubFamilia()
        );
    }

    @Override
    public List<ProveedorCustom> getListProveedorByFiltroLicitacionPaginado(ProveedorFiltro proveedorFiltro) {
        Integer paginaMostrar = new Integer((proveedorFiltro.getPaginaMostrar().intValue() - 1) * proveedorFiltro.getNroRegistros());
        proveedorFiltro.setPaginaMostrar(paginaMostrar);
        return proveedorMapperMybatis.getListProveedorByFiltroLicitacionPaginado(
                proveedorFiltro.getIdsPais(),
                proveedorFiltro.getRazonSocial(),
                proveedorFiltro.getIdsRegion(),
                proveedorFiltro.getIdsProvincia(),
                proveedorFiltro.getNroRuc(),
                proveedorFiltro.getTipoProveedor(),
                proveedorFiltro.getTipoPersona(),
                proveedorFiltro.getIndHomologado(),
                proveedorFiltro.getMarca(),
                proveedorFiltro.getProducto(),
                proveedorFiltro.getDescripcionAdicional(),
                proveedorFiltro.getIdsLinea(),
                proveedorFiltro.getIdsFamilia(),
                proveedorFiltro.getIdsSubFamilia(),
                proveedorFiltro.getNroRegistros(),
                proveedorFiltro.getPaginaMostrar()
        );
    }

    @Override
    public List<ProveedorCustom> getListProveedorByRuc(String ruc) {
        return proveedorMapperMybatis.getListProveedorByRuc(ruc);
}



    @Override
    public List<ProveedorCustom> getListProveedorSinHcpID() {
        return proveedorMapperMybatis.getListProveedorMigrados();
    }

    @Override
    public List<LineaComercialDto> getListLineaComercialByIdProveedor(Integer idProveedor) {
        return Optional.ofNullable(homologacionMapper.getListHomologacionByIdProveedor(idProveedor))
                .map(l -> {
                    List<LineaComercialDto> list = new ArrayList<>();
                    l.stream().map(linea -> {
                        LineaComercialDto dto = new LineaComercialDto();
                        dto.setIdLinea(linea.getIdLinea());
                        dto.setLinea(linea.getLinea());
                        return dto;
                    }).forEach(list::add);
                    return list;
                })
                .orElse(new ArrayList<>());
    }

    @Override
    public List<ProveedorCatalogoDto> getListCatalogosByIdProveedor(Integer idProveedor) {
        ProveedorCatalogoDTOMapper catalogoMapper = new ProveedorCatalogoDTOMapper();

        return Optional.ofNullable(proveedorCatalogoRepository)
                .map(r -> r.getProveedorCatalogoByIdProveedor(idProveedor))
                .map(l -> {
                    List<ProveedorCatalogoDto> list = new ArrayList<>();
                    l.stream().map(catalogoMapper::toDto).forEach(list::add);
                    return list;
                })
                .orElse(new ArrayList<>());
    }

    public ProveedorDto toDtoResponder(Proveedor proveedor) {
        /**
         * Información del proveedor
         */
        ProveedorDTOMapper proveedorDtoMapper = new ProveedorDTOMapper(this.condicionPagoReposity,
                this.ubigeoMapper,
                this.monedaRepository,
                this.tipoComprobanteRepository,
                this.tipoProveedorRepository);
        Optional<ProveedorDto> oDto = Optional.ofNullable(proveedor)
                .map(proveedorDtoMapper::toDto)
                .map(dto -> {
                    if (dto.getIdProveedor() == null) {
                        return dto;
                    }

                    int idProveedor = dto.getIdProveedor();

                    /**
                     *  Contactos por canal de distribución
                     */
                    Optional.ofNullable(this.contactoMapper)
                            .map(r -> {
                                List<CanalContactoDto> list = r.getListContactoByIdProveedor(idProveedor);
                                return list;
                            }).ifPresent(l -> l.stream().forEach(dto::addCanalContacto));

                    /**
                     *  Cuentas de Banco
                     */

                    CuentaBancoDTOMapper bancoDTOMapper = new CuentaBancoDTOMapper(this.bancoRepository,
                            this.monedaRepository);
                    Optional.ofNullable(this.cuentaBancariaMapper)
                            .map(r -> r.getListCuentaByIdProveedor(ParametroConstant.CUENTA_BANCO,
                                    ParametroTipoConstant.TIPO,
                                    dto.getIdProveedor()))
                            .ifPresent(l -> l.stream()
                                    .map(bancoDTOMapper::toDto)
                                    .forEach(dto::addCuentaBanco));
                    /**
                     * Líneas comerciales
                     */
                    ProveedorLineaComercialDTOMapper lineaDtoMapper = new ProveedorLineaComercialDTOMapper(this.lineaComercialRepository);
                    Optional.ofNullable(this.proveedorLineaComercialRepository)
                            .map(r -> r.getListLineaComercialByIdProveedor(idProveedor))
                            .ifPresent(l -> l.stream()
                                    .map(lineaDtoMapper::toDto)
                                    .forEach(dto::addLineaComercial));

                    /**
                     * Evaluacion de Homologación
                     */

                    Optional.ofNullable(homologacionMapper)
                            .map(r -> r.getListHomologacionByIdProveedorResponder(idProveedor))
                            .ifPresent(l -> {
                                l.stream().forEach(lch -> {
                                    lch.getPreguntas().forEach(p -> {
                                        final ProveedorHomologacionDto data = new ProveedorHomologacionDto();
                                        data.setIdLineaComercial(lch.getIdLinea());
                                        data.setLineaComercial(lch.getLinea());
                                        data.setIdHomologacion(p.getIdHomologacion());
                                        data.setPregunta(p.getPregunta());
                                        data.setIntAdjunto(p.getIndicadorAdjunto());
                                        data.setEstado(p.getEstado());
                                        data.setIndEstado(false);
                                        data.setValorRespuestaLibre(p.getValorRespuestaLibre());
                                        if (data.getEstado().equals("1")) {
                                            data.setIndEstado(true);
                                        }
                                        Optional.ofNullable(p.getRespuestaProveedor())
                                                .ifPresent(resp -> {
                                                    data.setRutaAdjunto(resp.getRutaAdjunto());
                                                    data.setArchivoId(resp.getArchivoId());
                                                    data.setArchivoNombre(resp.getNombreArchivo());
                                                    p.getOpciones().stream()
                                                            .filter(opt -> opt.getIdHomologacionRespuesta().equals(resp.getIdHomologacionRespuesta()))
                                                            .findFirst()
                                                            .map(f -> f.getRespuesta())
                                                            .ifPresent(data::setRespuesta);
                                                });
                                        dto.addRespuestaHomologacion(data);
                                    });
                                });
                            });

                    /**
                     * Productos
                     */
                    ProveedorProductoDTOMapper productoDtoMapper = new ProveedorProductoDTOMapper();
                    Optional.ofNullable(proveedorProductoRepository)
                            .map(r -> r.getListProductoByIdProveedor(idProveedor))
                            .ifPresent(l -> l.stream()
                                    .map(productoDtoMapper::toDto)
                                    .forEach(dto::addProducto));

                    /**
                     * Evaluación de desempeño
                     */
                    EvaluacionDTOMapper evaluacionDtoMapper = new EvaluacionDTOMapper();
                    Optional.ofNullable(proveedorEvaluacionRepository)
                            .map(r -> r.getProveedorEvaluacionByIdProveedor(idProveedor))
                            .ifPresent(l -> l.stream()
                                    .map(evaluacionDtoMapper::toDto)
                                    .forEach(dto::addEvaluacionDesempenio));
                    /**
                     * No conformes
                     */

                    SolicitudBlackListDTOMapper solicitudDtoMapper = new SolicitudBlackListDTOMapper();
                    Optional.ofNullable(solicitudBlackListRepository)
                            .map(r -> r.getListNoConformeByIdProveedorAndEstado(idProveedor,
                                    TipoSolicitudConstant.REGISTRO_NO_CONFORME.getId(),
                                    EstadoBlackListEnum.APROBADA.getCodigo()))
                            .ifPresent(l -> l.stream().map(solicitudDtoMapper::toDto).forEach(s -> {
                                dto.addNoConforme(s);
                            }));

                    /**
                     * Catálogos
                     */

                    ProveedorCatalogoDTOMapper catalogoMapper = new ProveedorCatalogoDTOMapper();
                    Optional.ofNullable(proveedorCatalogoRepository)
                            .map(r -> r.getProveedorCatalogoByIdProveedor(idProveedor))
                            .ifPresent(l -> l.stream().map(catalogoMapper::toDto)
                                    .forEach(dto::addCatalogo));


                    ///adjuntoSunat
                    ProveedorAdjuntoSunatDTOMapper adjuntoSunatDTOMapper = new ProveedorAdjuntoSunatDTOMapper();
                    Optional.ofNullable(proveedorAdjuntoSunatRepository)
                            .map(r -> r.getProveedorAdjuntoSunatByIdProveedor(idProveedor))
                            .ifPresent(l -> l.stream().map(adjuntoSunatDTOMapper::toDto)
                                    .forEach(dto::addAdjuntoSunat));

                  /*  List<ProveedorAdjuntoSunat> proveedorAdjuntoSunatList =this.proveedorAdjuntoSunatRepository.getProveedorAdjuntoSunatByIdProveedor(idProveedor);
                    dto.setAdjuntosSunat(proveedorAdjuntoSunatList);*/
                    /**



                     * Instalacion
                     */
                    ProveedorInstalacion proveedorInstalacion = new ProveedorInstalacion();
                    proveedorInstalacion.setIdBuscarProveedor(idProveedor);
                    List<ProveedorInstalacion> proveedorInstalacionList =
                            this.proveedorInstalacionMapper.getProveedorInstalacion(proveedorInstalacion);

                    dto.setInstalaciones(proveedorInstalacionList);

                    /**
                     * Permisos
                     */
                    ProveedorPermiso proveedorPermiso = new ProveedorPermiso();
                    proveedorPermiso.setIdBuscarProveedor(idProveedor);
                    List<ProveedorPermiso> proveedorPermisoList =
                            this.proveedorPermisoMapper.getProveedorPermiso(proveedorPermiso);
                    dto.setPermisos(proveedorPermisoList);

                    /**
                     * Principales
                     */
                    ProveedorCliente proveedorCliente = new ProveedorCliente();
                    proveedorCliente.setIdBuscarProveedor(idProveedor);
                    List<ProveedorCliente> proveedorClienteList =
                            this.proveedorClienteMapper.getProveedorCliente(proveedorCliente);
                    dto.setPrincipales(proveedorClienteList);

                    /**
                     * Adicionales
                     */
                    ProveedorFuncionario proveedorFuncionario = new ProveedorFuncionario();
                    proveedorFuncionario.setIdBuscarProveedor(idProveedor);
                    List<ProveedorFuncionario> proveedorFuncionarioList =
                            this.proveedorFuncionarioMapper.getProveedorFuncionario(proveedorFuncionario);
                    dto.setAdicionales(proveedorFuncionarioList);
                    logger.error("toDto 05 Adicionales " + proveedorFuncionarioList.toString());

                    /**
                     * Pregunta Informacion
                     */
                    List<PreguntaInformacion> preguntaInformacionList =
                            this.preguntaInformacionRepository.findAll(Sort.by("orden"));
                    List<ProveedorPreguntaInformacion> proveedorPreguntaInformacionList = new ArrayList<ProveedorPreguntaInformacion>();
                    for (PreguntaInformacion bean : preguntaInformacionList) {
                        ProveedorPreguntaInformacion beanBuscar = new ProveedorPreguntaInformacion();
                        beanBuscar.setIdBuscarProveedor(idProveedor);
                        beanBuscar.setIdBuscarPreguntaInformacion(bean.getId());
                        List<ProveedorPreguntaInformacion> lista =
                                this.proveedorPreguntaInformacionMapper.getProveedorPreguntaInformacion(beanBuscar);

                        ProveedorPreguntaInformacion proveedorPreguntaInformacion = new ProveedorPreguntaInformacion();
                        proveedorPreguntaInformacion.setRespuestaSiNo(false);
                        if (lista != null && lista.size() > 0) {
                            proveedorPreguntaInformacion = lista.get(0);
                        }
                        if (Optional.ofNullable(proveedorPreguntaInformacion.getRespuesta()).isPresent()) {
                            if (proveedorPreguntaInformacion.getRespuesta().equals(Constant.S)) {
                                proveedorPreguntaInformacion.setRespuestaSiNo(true);
                            }
                        }

                        proveedorPreguntaInformacion.setIdPreguntaInformacion(bean);
                        proveedorPreguntaInformacionList.add(proveedorPreguntaInformacion);
                    }
                    dto.setPreguntaInformacion(proveedorPreguntaInformacionList);

                    /**
                     * Sector de trabajo
                     */
                    Optional.ofNullable(proveedorSectorTrabajoRepository)
                            .map(r -> r.getListSectorTrabajoByIdProveedor(idProveedor))
                            .ifPresent(l -> {
                                List<SectorTrabajoDto> listDto = new ArrayList<>();
                                l.stream()
                                        .map(ProveedorSectorTrabajo::getSectorTrabajo)
                                        .map(sectorTrabajo -> sectorTrabajoPopulater.toDto(sectorTrabajo))
                                        .forEach(listDto::add);
                                dto.setSectorTrabajos(listDto);
                            });



                    return dto;
                });

        return oDto.isPresent() ? oDto.get() : null;
    }

    public ProveedorDto toDto(Proveedor proveedor) {
        /**
         * Información del proveedor
         */
        ProveedorDTOMapper proveedorDtoMapper = new ProveedorDTOMapper(this.condicionPagoReposity,
                this.ubigeoMapper,
                this.monedaRepository,
                this.tipoComprobanteRepository,
                this.tipoProveedorRepository);
        Optional<ProveedorDto> oDto = Optional.ofNullable(proveedor)
                .map(proveedorDtoMapper::toDto)
                .map(dto -> {
                    if (dto.getIdProveedor() == null) {
                        return dto;
                    }

                    int idProveedor = dto.getIdProveedor();
                    logger.error("toDto 01 idProveedor: " + idProveedor);

                    /**
                     *  Contactos por canal de distribución
                     */
                    Optional.ofNullable(this.contactoMapper)
                            .map(r -> {
                                List<CanalContactoDto> list = r.getListContactoByIdProveedor(idProveedor);
                                return list;
                            }).ifPresent(l -> l.stream().forEach(dto::addCanalContacto));

                    /**
                     *  Cuentas de Banco
                     */

                    CuentaBancoDTOMapper bancoDTOMapper = new CuentaBancoDTOMapper(this.bancoRepository,
                            this.monedaRepository);
                    Optional.ofNullable(this.cuentaBancariaMapper)
                            .map(r -> r.getListCuentaByIdProveedor(ParametroConstant.CUENTA_BANCO,
                                    ParametroTipoConstant.TIPO,
                                    dto.getIdProveedor()))
                            .ifPresent(l -> l.stream()
                                    .map(bancoDTOMapper::toDto)
                                    .forEach(dto::addCuentaBanco));
                    /**
                     * Líneas comerciales
                     */
                    ProveedorLineaComercialDTOMapper lineaDtoMapper = new ProveedorLineaComercialDTOMapper(this.lineaComercialRepository);
                    Optional.ofNullable(this.proveedorLineaComercialRepository)
                            .map(r -> r.getListLineaComercialByIdProveedor(idProveedor))
                            .ifPresent(l -> l.stream()
                                    .map(lineaDtoMapper::toDto)
                                    .forEach(dto::addLineaComercial));

                    /**
                     * Evaluacion de Homologación
                     */
                    Optional.ofNullable(homologacionMapper)
                            .map(r -> r.getListHomologacionByIdProveedor(idProveedor))
                            .ifPresent(l -> {
                                l.stream().forEach(lch -> {
                                    lch.getPreguntas().forEach(p -> {
                                        final ProveedorHomologacionDto data = new ProveedorHomologacionDto();
                                        data.setIdLineaComercial(lch.getIdLinea());
                                        data.setLineaComercial(lch.getLinea());
                                        data.setIdHomologacion(p.getIdHomologacion());
                                        data.setPregunta(p.getPregunta());
                                        data.setPeso(p.getPeso());
                                        data.setIntAdjunto(p.getIndicadorAdjunto());
                                        data.setEstado(p.getEstado());
                                        data.setIndEstado(false);
                                        data.setValorRespuestaLibre(p.getValorRespuestaLibre());
                                        if (data.getEstado().equals("1")) {
                                            data.setIndEstado(true);
                                        }
                                        Optional.ofNullable(p.getRespuestaProveedor())
                                                .ifPresent(resp -> {
                                                    data.setRutaAdjunto(resp.getRutaAdjunto());
                                                    data.setArchivoId(resp.getArchivoId());
                                                    data.setArchivoNombre(resp.getNombreArchivo());
                                                    p.getOpciones().stream()
                                                            .filter(opt -> opt.getIdHomologacionRespuesta().equals(resp.getIdHomologacionRespuesta()))
                                                            .findFirst()
                                                            .map(f -> f.getRespuesta())
                                                            .ifPresent(data::setRespuesta);
                                                });
                                        dto.addRespuestaHomologacion(data);
                                    });
                                });
                            });
                    /**
                     * Productos
                     */
                    ProveedorProductoDTOMapper productoDtoMapper = new ProveedorProductoDTOMapper();
                    Optional.ofNullable(proveedorProductoRepository)
                            .map(r -> r.getListProductoByIdProveedor(idProveedor))
                            .ifPresent(l -> l.stream()
                                    .map(productoDtoMapper::toDto)
                                    .forEach(dto::addProducto));

                    /**
                     * Evaluación de desempeño
                     */
                    EvaluacionDTOMapper evaluacionDtoMapper = new EvaluacionDTOMapper();
                    Optional.ofNullable(proveedorEvaluacionRepository)
                            .map(r -> r.getProveedorEvaluacionByIdProveedor(idProveedor))
                            .ifPresent(l -> l.stream()
                                    .map(evaluacionDtoMapper::toDto)
                                    .forEach(dto::addEvaluacionDesempenio));
                    /**
                     * No conformes
                     */

                    SolicitudBlackListDTOMapper solicitudDtoMapper = new SolicitudBlackListDTOMapper();
                    Optional.ofNullable(solicitudBlackListRepository)
                            .map(r -> r.getListNoConformeByIdProveedorAndEstado(idProveedor,
                                    TipoSolicitudConstant.REGISTRO_NO_CONFORME.getId(),
                                    EstadoBlackListEnum.APROBADA.getCodigo()))
                            .ifPresent(l -> l.stream().map(solicitudDtoMapper::toDto).forEach(s -> {
                                dto.addNoConforme(s);
                            }));

                    /**
                     * Catálogos
                     */

                    ProveedorCatalogoDTOMapper catalogoMapper = new ProveedorCatalogoDTOMapper();
                    Optional.ofNullable(proveedorCatalogoRepository)
                            .map(r -> r.getProveedorCatalogoByIdProveedor(idProveedor))
                            .ifPresent(l -> l.stream().map(catalogoMapper::toDto)
                                    .forEach(dto::addCatalogo));

                    logger.error("toDto 02 ");

                    //adjuntoSunat

                    ProveedorAdjuntoSunatDTOMapper adjuntoSunatDTOMapper = new ProveedorAdjuntoSunatDTOMapper();
                    Optional.ofNullable(proveedorAdjuntoSunatRepository)
                            .map(r -> r.getProveedorAdjuntoSunatByIdProveedor(idProveedor))
                            .ifPresent(l -> l.stream().map(adjuntoSunatDTOMapper::toDto)
                                    .forEach(dto::addAdjuntoSunat));

                    /*
                    List<ProveedorAdjuntoSunat> adjuntoSunatList= this.proveedorAdjuntoSunatRepository.getProveedorAdjuntoSunatByIdProveedor(idProveedor);
                    dto.setAdjuntosSunat(adjuntoSunatList);*/
                    /**
                     * Instalacion
                     */
                    ProveedorInstalacion proveedorInstalacion = new ProveedorInstalacion();
                    proveedorInstalacion.setIdBuscarProveedor(idProveedor);
                    List<ProveedorInstalacion> proveedorInstalacionList =
                            this.proveedorInstalacionMapper.getProveedorInstalacion(proveedorInstalacion);

                    dto.setInstalaciones(proveedorInstalacionList);
                    logger.error("toDto 03 Instalacion " + proveedorInstalacionList.toString());

                    /**
                     * Permisos
                     */
                    ProveedorPermiso proveedorPermiso = new ProveedorPermiso();
                    proveedorPermiso.setIdBuscarProveedor(idProveedor);
                    List<ProveedorPermiso> proveedorPermisoList =
                            this.proveedorPermisoMapper.getProveedorPermiso(proveedorPermiso);
                    dto.setPermisos(proveedorPermisoList);
                    logger.error("toDto 03 Permisos " + proveedorPermisoList.toString());

                    /**
                     * Principales
                     */
                    ProveedorCliente proveedorCliente = new ProveedorCliente();
                    proveedorCliente.setIdBuscarProveedor(idProveedor);
                    List<ProveedorCliente> proveedorClienteList =
                            this.proveedorClienteMapper.getProveedorCliente(proveedorCliente);
                    dto.setPrincipales(proveedorClienteList);
                    logger.error("toDto 04 Principales " + proveedorClienteList.toString());

                    /**
                     * Adicionales
                     */
                    ProveedorFuncionario proveedorFuncionario = new ProveedorFuncionario();
                    proveedorFuncionario.setIdBuscarProveedor(idProveedor);
                    List<ProveedorFuncionario> proveedorFuncionarioList =
                            this.proveedorFuncionarioMapper.getProveedorFuncionario(proveedorFuncionario);
                    dto.setAdicionales(proveedorFuncionarioList);
                    logger.error("toDto 05 Adicionales " + proveedorFuncionarioList.toString());

                    /**
                     * Pregunta Informacion
                     */
                    List<PreguntaInformacion> preguntaInformacionList =
                            this.preguntaInformacionRepository.findAll(Sort.by("orden"));
                    List<ProveedorPreguntaInformacion> proveedorPreguntaInformacionList = new ArrayList<ProveedorPreguntaInformacion>();
                    for (PreguntaInformacion bean : preguntaInformacionList) {
                        ProveedorPreguntaInformacion beanBuscar = new ProveedorPreguntaInformacion();
                        beanBuscar.setIdBuscarProveedor(idProveedor);
                        beanBuscar.setIdBuscarPreguntaInformacion(bean.getId());
                        List<ProveedorPreguntaInformacion> lista =
                                this.proveedorPreguntaInformacionMapper.getProveedorPreguntaInformacion(beanBuscar);

                        ProveedorPreguntaInformacion proveedorPreguntaInformacion = new ProveedorPreguntaInformacion();
                        proveedorPreguntaInformacion.setRespuestaSiNo(false);
                        if (lista != null && lista.size() > 0) {
                            proveedorPreguntaInformacion = lista.get(0);
                        }
                        if (Optional.ofNullable(proveedorPreguntaInformacion.getRespuesta()).isPresent()) {
                            if (proveedorPreguntaInformacion.getRespuesta().equals(Constant.S)) {
                                proveedorPreguntaInformacion.setRespuestaSiNo(true);
                            }
                        }

                        proveedorPreguntaInformacion.setIdPreguntaInformacion(bean);
                        proveedorPreguntaInformacionList.add(proveedorPreguntaInformacion);
                    }
                    dto.setPreguntaInformacion(proveedorPreguntaInformacionList);
                    logger.error("toDto 06 Pregunta " + proveedorPreguntaInformacionList.toString());

                    /**
                     * Sector de trabajo
                     */
                    Optional.ofNullable(proveedorSectorTrabajoRepository)
                            .map(r -> r.getListSectorTrabajoByIdProveedor(idProveedor))
                            .ifPresent(l -> {
                                List<SectorTrabajoDto> listDto = new ArrayList<>();
                                l.stream()
                                        .map(ProveedorSectorTrabajo::getSectorTrabajo)
                                        .map(sectorTrabajo -> sectorTrabajoPopulater.toDto(sectorTrabajo))
                                        .forEach(listDto::add);
                                dto.setSectorTrabajos(listDto);
                            });

                    logger.error("toDto 07 dto: " + dto.toString());
                    return dto;
                });

        return oDto.isPresent() ? oDto.get() : null;
    }

    @Override
    @Transactional(readOnly = true)
    public Proveedor getProveedorByRuc(String ruc) {
        return this.proveedorRepository.getProveedorByRuc(ruc);
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorDto getProveedorDtoByIdHcp(String idHcp) throws PortalException {
        return Optional.ofNullable(proveedorRepository)
                .map(r -> r.getProveedorByIdHcp(idHcp))
                .map(this::toDto)
                .orElse(new ProveedorDto());
    }

    @Override
    @Transactional(readOnly = true)
    public Proveedor getProveedorByIdHcp(String idHcp) throws PortalException {
        return Optional.ofNullable(proveedorRepository)
                .map(r -> r.getProveedorByIdHcp(idHcp))
                .orElse(new Proveedor());
    }

    @Override
    public ProveedorDto getProveedorDtoByRuc(String ruc) {
        return Optional.ofNullable(proveedorRepository)
                .map(r -> r.getProveedorByRuc(ruc))
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public ProveedorDto getProveedorDtoByRucResponder(String ruc) {
        return Optional.ofNullable(proveedorRepository)
                .map(r -> r.getProveedorByRuc(ruc))
                .map(this::toDtoResponder)
                .orElse(null);
    }


    public List<ProveedorDatosGeneralesDTO> getProveedorDatosGenerales(
            String fechaCreacionIni, String fechaCreacionFin) throws PortalException {
        Date dFechaCreacionIni = new Date();
        Date dFechaCreacionFin = new Date();
        int indicador = 0;

        List<Proveedor> listaProveedor = new ArrayList<Proveedor>();
        if (StringUtils.isNotBlank(fechaCreacionIni)) {
            indicador = 1;
            if (StringUtils.isNotBlank(fechaCreacionFin))
                indicador = 2;
        }

        try {
            switch (indicador) {
                case 1:
                    dFechaCreacionIni = DateUtils.convertStringToDate(fechaCreacionIni);
                    break;
                case 2:
                    dFechaCreacionIni = DateUtils.convertStringToDate(fechaCreacionIni);
                    dFechaCreacionFin = DateUtils.convertStringToDate(fechaCreacionFin);
                    break;
            }
        } catch (ParseException e) {
            indicador = 0;
        }


        switch (indicador) {
            case 0:
                listaProveedor = this.proveedorRepository.findAllByOrderByRuc();
                break;
            case 1:
                listaProveedor = this.proveedorRepository.findByFechaCreacionGreaterThanEqualOrderByRuc(dFechaCreacionIni);
                break;
            case 2:
                listaProveedor = this.proveedorRepository.findByFechaCreacionBetweenOrderByRuc(dFechaCreacionIni, dFechaCreacionFin);
                break;
        }

        List<ProveedorDatosGeneralesDTO> listaProveedorDatosGenerales = new ArrayList<ProveedorDatosGeneralesDTO>();
        for (Proveedor item : listaProveedor) {
            ProveedorDatosGeneralesDTO bean = new ProveedorDatosGeneralesDTO();
            bean.setProveedor(item);

            Integer licitacionesParticipo = this.licitacionProveedorRepository.
                    countByProveedorAndEstadoLicitacionLicitada(item, LicitacionConstant.ESTADO_ADJUDICADA, LicitacionConstant.ESTADO_ENVIADO_SAP);
            Integer licitacionesGanadas = this.licitacionProveedorMapper.countByLicitacionesGanadas(item.getIdProveedor());
            Integer licitacionesPerdidas = licitacionesParticipo.intValue() - licitacionesGanadas.intValue();
            Integer noConformesRegistrados = this.blackListMapper.countByBlackListRegistrados(
                    item.getIdProveedor(), Constant.CODIGO_TIPO_SOLICITUD_NO_CONFORME);
            bean.setLicitacionesParticipo(licitacionesParticipo);
            bean.setLicitacionesGanadas(licitacionesGanadas);
            bean.setLicitacionesPerdidas(licitacionesPerdidas);
            bean.setNoConformesRegistrados(noConformesRegistrados);
            listaProveedorDatosGenerales.add(bean);
        }

        return listaProveedorDatosGenerales;
    }

    @Override
    public ProveedorDto sendToSap(Integer idProveedor) throws PortalException {
        Optional<Proveedor> oProveedor = Optional.ofNullable(proveedorRepository)
                .map(r -> r.getOne(idProveedor));

        if (!oProveedor.isPresent()) {
            throw new PortalException("El proveedor no existe");
        }
        Proveedor proveedor = oProveedor.get();
        if (Optional.ofNullable(proveedor.getAcreedorCodigoSap()).isPresent()) {
            throw new PortalException("El proveedor posee código SAP: " + proveedor.getAcreedorCodigoSap() + " por lo que no se ha enviado a SAP");
        }

        logger.error("Ingresando sendToSap: " + oProveedor.toString());
        ProveedorResponse response = proveedorWebService.grabarProveedorSAP(Arrays.asList(oProveedor.get()));
        logger.error("Ingresando sendToSap 01" );
        Optional<ProveedorBeanSAP> oProveedorResponse = Optional.ofNullable(response.getListaProveedorSAPResult())
                .filter(list -> list.size() == 1)
                .map(list -> list.get(0));
        logger.error("Ingresando sendToSap 02" );
        if (!oProveedorResponse.isPresent()) {
            throw new PortalException("La respuesta de la sincronización a SAP es nula");
        }
        logger.error("Ingresando sendToSap 03" );
        if (response.isTieneError()) {
            String error = oProveedorResponse.map(r -> r.getSapLogProveedor()).map(log -> log.getMesaj()).orElse("-");
            throw new PortalException(error);
        }

        return toDto(oProveedorResponse.get().getProveedorSAP());
    }

    public void evaluarDataMaestra(Integer idProveedor) throws PortalException {
        Optional<Proveedor> oProveedor = Optional.ofNullable(proveedorRepository)
                .map(r -> r.getOne(idProveedor));

        if (!oProveedor.isPresent()) {
            throw new PortalException("El proveedor no existe");
        }
        Proveedor proveedorActual = oProveedor.get();
        EstadoProveedor estadoProveedorActual = proveedorActual.getIdEstadoProveedor();
        if (estadoProveedorActual.getCodigoEstadoProveedor().equals(EstadoProveedorEnum.RECHAZADO_DATA_MAESTRA.getCodigo())) {
            throw new PortalException("No es posible Aprobar Data Maestra, debido que dicho Proveedor actualmente esta RECHAZADO x DATA MAESTRA");
        }

        EstadoProveedor estadoProveedor = this.estadoProveedorRepository.
                getByCodigoEstadoProveedor(EstadoProveedorEnum.APROBADO_DATA_MAESTRA.getCodigo());
        this.proveedorMapperMybatis.updateEstadoProveedor(
                oProveedor.get().getIdProveedor(),
                estadoProveedor.getId());

        //enviar correo

        proveedorDataMaestraNotificacion.enviar(this.parametroMapper.getMailSetting(), oProveedor.get(),"APR");


    }

    public void rechazarDataMaestra(Integer idProveedor) throws PortalException {
        Optional<Proveedor> oProveedor = Optional.ofNullable(proveedorRepository)
                .map(r -> r.getOne(idProveedor));

        if (!oProveedor.isPresent()) {
            throw new PortalException("El proveedor no existe");
        }
        Proveedor proveedorActual = oProveedor.get();
        EstadoProveedor estadoProveedorActual = proveedorActual.getIdEstadoProveedor();
        if (estadoProveedorActual.getCodigoEstadoProveedor().equals(EstadoProveedorEnum.APROBADO_DATA_MAESTRA.getCodigo())) {
            throw new PortalException("No es posible Rechazar Data Maestra, debido que dicho Proveedor actualmente esta APROBADO x DATA MAESTRA");
        }

        EstadoProveedor estadoProveedor = this.estadoProveedorRepository.
                getByCodigoEstadoProveedor(EstadoProveedorEnum.RECHAZADO_DATA_MAESTRA.getCodigo());
        this.proveedorMapperMybatis.updateEstadoProveedor(
                oProveedor.get().getIdProveedor(),
                estadoProveedor.getId());
        //correo

        proveedorDataMaestraNotificacion.enviar(this.parametroMapper.getMailSetting(), oProveedor.get(),"REC");
    }

    @Override
    public Integer updateProveedorIDHCP(ListProveedorHCP listProveedorHCP) {
        List<ProveedorCustom> listaProveedoresSinHCP=listProveedorHCP.getListaProveedorsinIDHCP();
        Integer nroProveedor=listaProveedoresSinHCP.size();
        if(listaProveedoresSinHCP.size()>0){
            for (ProveedorCustom obj : listaProveedoresSinHCP){
               this.proveedorRepository.updateIdHCP(obj.getIdHCP(),obj.getRuc());
            }
        }
        return nroProveedor;
    }
    protected boolean depurarUploadExcel(ProveedorDto dto) {
        if (StringUtils.isBlank(dto.getAcreedorCodigoSap())) {
            return false;
        }
//        if(StringUtils.isBlank(dto.getEmail())){
//            return false;
//        }
        if(StringUtils.isBlank(dto.getTipoPersona())){
            return false;
        }
        return true;
    }

    protected String validacionesPrevias(ProveedorDto dto) {
        String mensaje = "";

        if (!Optional.ofNullable(dto.getAcreedorCodigoSap()).isPresent()) {
            String msj = this.messageSource.getMessage("message.AcreedorSap.noIngresado", null,
                    LocaleContextHolder.getLocale());
            mensaje += "* " + msj + " ";
        }
        if (!Optional.ofNullable(dto.getEmail()).isPresent()) {
            String msj = this.messageSource.getMessage("message.Email.noIngresado", null,
                    LocaleContextHolder.getLocale());
            mensaje += "* " + msj + " ";
        }
        if (!Optional.ofNullable(dto.getTipoPersona()).isPresent()) {
            String msj = this.messageSource.getMessage("message.TipoPersona.noIngresado", null,
                    LocaleContextHolder.getLocale());
            mensaje += "* " + msj + " ";
        }

        return mensaje;
    }

    protected ProveedorDto setUploadExcel(Cell currentCell, ProveedorDto proveedorDtoParam, int contador, DataFormatter dataFormatter) {
        Double valorNumerico = new Double(0);
        BigDecimal valorDecimal = new BigDecimal(0);
        String valorCadena = "";
        switch (contador) {
            case 1:
                try {
                    if (currentCell.getCellType() == CellType.STRING) {
                        valorCadena = currentCell.getStringCellValue().trim();
                    } else if (currentCell.getCellType() == CellType.NUMERIC) {
                        valorCadena = dataFormatter.formatCellValue(currentCell);
                    }
//                    valorCadena = currentCell.getStringCellValue();
                    if (valorCadena.length() > 10) {
                        throw new ServiceException("Valor Campo Acreedor contiene mas de 10 caracter(es)");
                    }
                    proveedorDtoParam.setAcreedorCodigoSap(valorCadena);
                } catch (Exception e) {
//                    throw new ServiceException("Valor Campo Acreedor está en formato incorrecto");
                    String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
                    logger.error("plantilla Excel migracion proveedores SAP / codigoAcreedorSap: " + currentCell.getStringCellValue() + " / EXCEPTION: " + error);
                    throw new ServiceException(error);
                }
                break;
            case 2:
                try {
                    valorCadena = currentCell.getStringCellValue();

                    if (!Utils.validarEmail(valorCadena)){
                        throw new ServiceException("Valor Campo Email tiene formato incorrecto");
                    }
                    logger.error("<--MC_LOG-->:ProveedorServiceImpl-setUploadExcel:");
                    logger.error("<--MC_LOG-->:ProveedorServiceImpl-setUploadExcel:"+valorCadena);
                    proveedorDtoParam.setEmail(valorCadena);
                } catch (Exception e) {
//                    throw new ServiceException("Valor Campo Email está en formato incorrecto");
                    String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
                    logger.error("plantilla Excel migracion proveedores SAP / email: " + currentCell.getStringCellValue() + " / EXCEPTION: " + error);
                    throw new ServiceException(error);
                }
                break;
            case 3:
                try {
                    valorCadena = currentCell.getStringCellValue();

                    if (valorCadena.length() != 1 || (!valorCadena.equals("J") && !valorCadena.equals("N"))){
                        throw new ServiceException("Valor Campo Tipo Persona tiene formato incorrecto");
                    }
                    proveedorDtoParam.setTipoPersona(valorCadena);
                } catch (Exception e) {
//                    throw new ServiceException("Valor Tipo Persona está en formato incorrecto");
                    String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
                    logger.error("plantilla Excel migracion proveedores SAP / tipoPersona: " + currentCell.getStringCellValue() + " / EXCEPTION: " + error);
                    throw new ServiceException(error);
                }
                break;
            default:
                break;
        }
        return proveedorDtoParam;
    }

    public List<ProveedorXLSXDTO> uploadExcelData(InputStream in) {
        logger.debug("Ingresando uploadExcel");
        List<ProveedorXLSXDTO> mapaProveedorParamMasivoDTOArrayList = new ArrayList<ProveedorXLSXDTO>();
        DataFormatter dataFormatter = new DataFormatter(new Locale("en", "US"));
        int inicioRegistroData = 2;
        /*AppParametria appParametriaData = this.appParametriaDeltaRepository.getByModuloAndLabelAndStatus(AppParametriaModuloEnum.CARGA_EXCEL.getEstado(),
                AppParametriaLabelEnum.INICIO_REGISTRO_DATA.getEstado(), Constant.UNO);
        if (Optional.of(appParametriaData).isPresent()) {
            inicioRegistroData = new Integer(appParametriaData.getValue1()).intValue();
        }*/
        try {
            Workbook workbook = new XSSFWorkbook(in);
            try {
                Sheet datatypeSheet = workbook.getSheetAt(0);
                Iterator<Row> iterator = datatypeSheet.iterator();
                int contadorRegistro = 1;
                while (iterator.hasNext()) {
                    if (contadorRegistro < inicioRegistroData) {
                        contadorRegistro++;
                        Row currentRow = iterator.next();
                        continue;
                    }
                    ProveedorXLSXDTO proveedorXLSXDTO = new ProveedorXLSXDTO();
                    ProveedorDto proveedorParam = new ProveedorDto();
                    proveedorParam.setIdProveedor(null);
                    Row currentRow = iterator.next();
                    Iterator<Cell> cellIterator = currentRow.iterator();
                    boolean error = false;
                    try {
                        while (cellIterator.hasNext()) {
                            Cell currentCell = cellIterator.next();
                            logger.error("<--MC_LOG-->:ProveedorServiceImpl-uploadExcelData:");
                            logger.error("<--MC_LOG-->:ProveedorServiceImpl-uploadExcelData:"+proveedorParam.getEmail());
                            proveedorParam = this.setUploadExcel(currentCell, proveedorParam, currentCell.getColumnIndex() + 1, dataFormatter);
                        }
                        String mensaje = this.validacionesPrevias(proveedorParam);
                        if (StringUtils.isNotBlank(mensaje)) {
                            throw new ServiceException(mensaje);
                        }
                        if (this.depurarUploadExcel(proveedorParam)) {
                            proveedorXLSXDTO.setProveedor(proveedorParam);
                            proveedorXLSXDTO.setMensaje("");
                            proveedorXLSXDTO.setError(false);
                        }
                    } catch (Exception e) {
                        proveedorXLSXDTO.setProveedor(proveedorParam);
                        proveedorXLSXDTO.setMensaje(Utils.obtieneMensajeErrorException(e));
                        proveedorXLSXDTO.setError(true);
                    }
                    mapaProveedorParamMasivoDTOArrayList.add(proveedorXLSXDTO);
                }
            } catch (Exception exw) {

            } finally {
                workbook.close();
            }
        } catch (Exception ex) {

        }
        return mapaProveedorParamMasivoDTOArrayList;
    }
    @Override
    public List<ProveedorXLSXDTO> uploadExcel(InputStream in) {
        logger.error("Ingresando uploadExcelDelta ");
        logger.error("<--MC_LOG-->:ProveedorServiceImpl-uploadExcel:");
        List<ProveedorXLSXDTO> listaUpload = this.uploadExcelData(in);
        logger.error("Ingresando after uploadExcel: ");
        if (listaUpload == null || listaUpload.size() <= 0) {
            return null;
        }

        logger.error("Fin uploadExcelDelta ");
        return listaUpload;
    }

    public List<ProveedorAdjuntoSunat> guardarAdjuntoSunat(Proveedor proveedor, List<ProveedorAdjuntoSunatDto> listAdjunto, String operacion) throws Exception {

        if (isDev) {
            return null;
        }
        logger.error("iniciando GRABAR AdjuntoSunat");
        //Creo una segunda lista con los adjuntos no guardados
        List<CmisFile> listAdjuntoNew = new ArrayList<CmisFile>();
        if (listAdjunto.size() > 0) {
            listAdjunto.forEach(item -> {
                if (item.getId() == null) {
                    listAdjuntoNew.add(new CmisFile(
                            item.getArchivoId(),
                            item.getArchivoNombre(),
                            item.getRutaAdjunto(),
                            item.getArchivoTipo(),
                            item.getArchivoSize(),
                            item.getArchivoNombreFinal(),
                            item.getArchivoExtension(),
                            item.getArchivoCarpetaId(),
                            item.getArchivoNombreFolder(),
                            item.getArchivoParentPath()
                    ));
                    logger.error("Creo una segunda lista con los adjuntos no guardados");
                }
            });
        }

        //Se crea folder destino -> Nro de licitacion
        String newFolder = proveedor.getRuc();
        logger.error("newFolder_sunat " + newFolder);

        String folderId = cmisService.createFolder(newFolder).getNameFolder();//Id();
        logger.error("folderId_sunat: " + folderId);

        //Se mueven los adjuntos al folder destino y se obtiene la lista de los mismos con su nuevo URL
        Optional<List<CmisFile>> listAdjuntoMove = Optional.ofNullable(listAdjuntoNew)
                .map(list -> {
                    logger.error("Actualizando la version de los archivos adjuntos sunat");
                    return cmisService.updateFileAndMoveVerificar(listAdjuntoNew, folderId);
                });

        //Se guardan en la base de datos los adjuntos movidos
        if(listAdjuntoMove.isPresent()){
            List<CmisFile> list = listAdjuntoMove.get();
            list.forEach(file->{
                ProveedorAdjuntoSunat obj = this.proveedorAdjuntoSunatRepository.save(new ProveedorAdjuntoSunat(proveedor, file.getUrl(), file.getId(), file.getName(), file.getType()));
            });
            logger.debug("Se guardan en la base de datos los adjuntos movidos");
        }

        //Eliminando adjuntos ausentes en el request
        List<ProveedorAdjuntoSunat> listaAdjuntosActual = this.proveedorAdjuntoSunatRepository.getProveedorAdjuntoSunatByIdProveedor(proveedor.getIdProveedor());
        for (ProveedorAdjuntoSunat obj1 : listaAdjuntosActual){
            Boolean encontrado = false;
            for (ProveedorAdjuntoSunatDto obj2 : listAdjunto){
                if ((obj1.getArchivoId()).equals(obj2.getArchivoId())){
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado){
                this.proveedorAdjuntoSunatRepository.deleteProveedorAdjuntoSunatByLicitacionandAndArchivoId(proveedor.getIdProveedor(), obj1.getArchivoId());
                logger.debug("Eliminando adjuntos ausentes en el request");
                continue;
            }
        }

        //Se obtiene lista de adjuntos final
        List<ProveedorAdjuntoSunat> listaAdjuntosSunatResult = this.proveedorAdjuntoSunatRepository.getProveedorAdjuntoSunatByIdProveedor(proveedor.getIdProveedor());

        return listaAdjuntosSunatResult;

    }

    public List<ProveedorCatalogo> guardarAdjuntoCatalogo(Proveedor proveedor, List<ProveedorCatalogoDto> catalogosList, String operacion) throws Exception {

        if (isDev) {
            return null;
        }
        logger.error("iniciando GRABAR catalogos");
        //Creo una segunda lista con los adjuntos no guardados
        List<CmisFile> listAdjuntoNew = new ArrayList<CmisFile>();
        if (catalogosList.size() > 0) {
            catalogosList.forEach(item -> {
                if (item.getId() == null) {
                    listAdjuntoNew.add(new CmisFile(
                            item.getArchivoId(),
                            item.getArchivoNombre(),
                            item.getRutaCatalogo(),
                            item.getArchivoTipo(),
                            item.getArchivoSize(),
                            item.getArchivoNombreFinal(),
                            item.getArchivoExtension(),
                            item.getArchivoCarpetaId(),
                            item.getArchivoNombreFolder(),
                            item.getArchivoParentPath()
                    ));

                    logger.error("Creo una segunda lista con los adjuntos no catalogos");
                }
            });
        }


        String newFolder = proveedor.getRuc() + "-catalogos";
        logger.error("newFolder_catalogo: " + newFolder);

        String folderId = cmisService.createFolder(newFolder).getNameFolder();//Id();
        logger.error("FOLDER_DESTINO: " + folderId);

        //Se mueven los adjuntos al folder destino y se obtiene la lista de los mismos con su nuevo URL
        Optional<List<CmisFile>> listAdjuntoMove = Optional.ofNullable(listAdjuntoNew)
                .map(list -> {
                    logger.error("Actualizando la version de los archivos catalogos");
                    return cmisService.updateFileAndMoveVerificar(listAdjuntoNew, folderId);
                });

        //Se guardan en la base de datos los adjuntos movidos
        if(listAdjuntoMove.isPresent()){
            List<CmisFile> list = listAdjuntoMove.get();
            list.forEach(file->{
                ProveedorCatalogo obj = this.proveedorCatalogoRepository.save(new ProveedorCatalogo(proveedor, file.getUrl(), file.getId(), file.getName(), file.getType()));
            });
            logger.error("Se guardan en la base de datos los catalogos movidos");
        }

        //Eliminando adjuntos ausentes en el request
        logger.error(proveedor.getIdProveedor().toString());
        List<ProveedorCatalogo> listaAdjuntosActual = this.proveedorCatalogoRepository.getProveedorCatalogoByIdProveedor(proveedor.getIdProveedor());
        for (ProveedorCatalogo obj1 : listaAdjuntosActual){
            Boolean encontrado = false;
            for (ProveedorCatalogoDto obj2 : catalogosList){
                if ((obj1.getArchivoId()).equals(obj2.getArchivoId())){
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado){
                this.proveedorCatalogoRepository.deleteCatalogoByIdProveedorCatalogoById(proveedor.getIdProveedor(), obj1.getArchivoId());
                logger.debug("Eliminando adjuntos ausentes en el request");
                continue;
            }
        }

        //Se obtiene lista de adjuntos final
        List<ProveedorCatalogo> listaCatalogos = this.proveedorCatalogoRepository.getProveedorCatalogoByIdProveedor(proveedor.getIdProveedor());

        return listaCatalogos;

    }

    @Override
    public String saveEmail(String ruc, String email) {
        String respuesta = "";

        if(!email.isEmpty()) {
            Proveedor proveedor = proveedorRepository.getProveedorByRuc(ruc);
            if (proveedor != null) {
                logger.error("<--MC_LOG-->:ProveedorServiceImpl-saveEmail:");
                logger.error("<--MC_LOG-->:ProveedorServiceImpl-saveEmail:"+email);
                proveedor.setEmail(email);
                proveedor = proveedorRepository.save(proveedor);
                respuesta = "Se modificó correctamente la dirección de correo a: " + proveedor.getEmail();
            } else {
                respuesta = "No se encontró proveedor con RUC: " + ruc;
            }

            List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(ruc);
            if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1) {
                Usuario usuarioProveedor = posibleProveedorList.get(0);
                usuarioProveedor.setEmail(email);
                usuarioRepository.save(usuarioProveedor);
            }
        }
        else{
            respuesta = "La dirección de correo enviada está vacía";
        }

        return respuesta;
    }
}
