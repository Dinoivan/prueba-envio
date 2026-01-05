package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.ProveedorCustom;
import com.incloud.hcp.bean.SolicitudPedido;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.dto.CComparativoAdjudicarDto;
import com.incloud.hcp.jco.contratoMarco.service.JCOContratoMarcoService;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenCompraService;
import com.incloud.hcp.jco.peticionOferta.dto.PeticionOfertaRFCDto;
import com.incloud.hcp.jco.peticionOferta.dto.PeticionOfertaRFCRequestDto;
import com.incloud.hcp.jco.peticionOferta.dto.PeticionOfertaRFCResponseDto;
import com.incloud.hcp.jco.peticionOferta.service.JCOPeticionOfertaService;
import com.incloud.hcp.jco.proveedor.dto.ProveedorRFCResponseDto;
import com.incloud.hcp.jco.proveedor.dto.ProveedorResponseRFC;
import com.incloud.hcp.jco.proveedor.service.JCOProveedorService;
import com.incloud.hcp.myibatis.mapper.ParametroMapper;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.rest.bean.CuadroComparativoGrabarDTO;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.sap.homologacion.HomologacionWebService;
import com.incloud.hcp.sap.ordenCompra.OrdenCompraWebService;
import com.incloud.hcp.sap.proveedor.ProveedorWebService;
import com.incloud.hcp.service.CcomparativoService;
import com.incloud.hcp.service.CcomparativoServiceNew;
import com.incloud.hcp.service._framework.BaseServiceImpl;
import com.incloud.hcp.service.notificacion.EnviarAdjudicacionCComparativoNotificacion;
import com.incloud.hcp.service.notificacion.EnviarAgradecimientoNoAdjudicadoNotificacion;
import com.incloud.hcp.util.Constant;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.constant.ClaseDocumentoConstant;
import com.incloud.hcp.util.constant.LicitacionConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by USER on 18/09/2017.
 */
@Service
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class CcomparativoServiceImpl  extends BaseServiceImpl implements CcomparativoService {

    private static Logger logger = LoggerFactory.getLogger(CcomparativoServiceImpl.class);

    @Autowired
    CcomparativoRepository ccomparativoRepository;

    @Autowired
    CcomparativoAdjudicadoRepository ccomparativoAdjudicadoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorWebService proveedorWebService;

    @Autowired
    private JCOProveedorService jcoProveedorService;

    @Autowired
    private JCOPeticionOfertaService jcoPeticionOfertaService;

    @Autowired
    private OrdenCompraWebService ordenCompraWebService;

    @Autowired
    private HomologacionWebService homologacionWebService;

    @Autowired
    private CotizacionDetalleRepository cotizacionDetalleRepository;

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private LicitacionRepository licitacionRepository;

    @Autowired
    private EnviarAdjudicacionCComparativoNotificacion enviarAdjudicacionCComparativoNotificacion;

    @Autowired
    private EnviarAgradecimientoNoAdjudicadoNotificacion enviarAgradecimientoNoAdjudicadoNotificacion;

    @Autowired
    private ParametroMapper parametroMapper;

    @Autowired
    private CcomparativoProveedorRepository ccomparativoProveedorRepository;

    @Autowired
    private TipoDocumentoSapRepository tipoDocumentoSapRepository;

    @Autowired
    private JCOOrdenCompraService jcoOrdenCompraService;

    @Autowired
    private JCOContratoMarcoService jcoContratoMarcoService;

    @Autowired
    private ClaseDocumentoRepository claseDocumentoRepository;

    @Autowired
    private CcomparativoServiceNew ccomparativoServiceNew;

    @Autowired
    private LicitacionProveedorRepository licitacionProveedorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<CcomparativoProveedor> adjudicarSAPErrores(CComparativoAdjudicarDto dto) throws Exception {
        List<CcomparativoProveedor> ccomparativoProveedorList = dto.getCcomparativoProveedorList();
        if (ccomparativoProveedorList == null || ccomparativoProveedorList.size() <= 0) {
            throw new Exception("No hay Lista de Proveedores a Adjudicar");
        }
        Usuario usuario = this.usuarioRepository.getByCodigoUsuarioIdp(dto.getUserSession().getId());
        if (Optional.ofNullable(usuario).isPresent()) {
            dto.setUsuario(usuario);
            if (StringUtils.isBlank(usuario.getCodigoSap())) {
                throw new Exception("Debe ingresar Código Usuario SAP en el Mantenimiento de Usuarios para el Usuario: " + usuario.getEmail() );
            }
//            if (StringUtils.isBlank(usuario.getCodigoEmpleadoSap())) {
//                throw new Exception("Debe ingresar Código Empleado SAP en el Mantenimiento de Usuarios para el Usuario: " + usuario.getEmail() );
//            }
        }
        else {
            throw new Exception("No se encontró Usuario con Código IDP: " + dto.getUserSession().getId() );
        }

        for (CcomparativoProveedor bean : ccomparativoProveedorList) {
            logger.error("adjudicarSAPErrores bean: " + bean.toString());
            bean.setObservacionesSap(null);
            bean = this.ccomparativoProveedorRepository.save(bean);
        }
        Integer idLicitacion = dto.getIdLicitacion();
        return this.adjudicarSAPErrores(idLicitacion, dto);
    }

    public List<CcomparativoProveedor> adjudicarSAPErrores(Integer idLicitacion, CComparativoAdjudicarDto dto) throws Exception {
        Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
        if (!Optional.ofNullable(licitacion).isPresent()) {
            throw new Exception("No se encontró Licitación");
        }

        List<Ccomparativo> cComparativoList = this.ccomparativoRepository.findByLicitacion(idLicitacion);
        Ccomparativo ccomparativo = new Ccomparativo();
        if (cComparativoList == null || cComparativoList.size() <= 0) {
            throw new Exception("No se encontró Cuadro Comparativo relacionado a la licitación");
        }
        ccomparativo = cComparativoList.get(0);

        List<CcomparativoProveedor> ccomparativoProveedorList = this.ccomparativoProveedorRepository.findByLicitacion(idLicitacion);
        if (ccomparativoProveedorList == null || ccomparativoProveedorList.size() <= 0) {
            return ccomparativoProveedorList;
        }
        logger.error("adjudicarSAPErrores ccomparativoProveedorList: " + ccomparativoProveedorList.toString());

        /* Obteniendo Proveedores con Error de Creacion del CComparativo */
        List<Proveedor> listaProveedorPotencial = new ArrayList<Proveedor>();
        for (CcomparativoProveedor bean : ccomparativoProveedorList) {
            Proveedor beanProveedor = bean.getIdProveedor();
            String indicadorProveedor = bean.getIndCreacionProveedor();
            if (StringUtils.isNotBlank(indicadorProveedor)) {
               if (indicadorProveedor.equals(Constant.DOS)) {
                   listaProveedorPotencial.add(beanProveedor);
               }
            }
        }

        /* Creando Proveedor de nuevo en SAP */
        boolean exito = true;
        boolean existeProveedor = false;
        boolean exitoProveedor = true;
        ProveedorResponseRFC proveedorResponse = new ProveedorResponseRFC();
        proveedorResponse.setTieneError(false);

        Usuario usuario = dto.getUsuario();
//        String codigoEmpleadoSap = usuario.getCodigoEmpleadoSap();
        String codigoEmpleadoSap = "";

        proveedorResponse = this.jcoProveedorService.grabarListaProveedorSAP(listaProveedorPotencial, codigoEmpleadoSap);
        List<ProveedorRFCResponseDto> listaProveedorSAPResult = proveedorResponse.getListaProveedorSAPResult();
        for (ProveedorRFCResponseDto bean : listaProveedorSAPResult) {
            existeProveedor = true;
            Proveedor proveedor = bean.getProveedorSap();
            CcomparativoProveedor ccomparativoProveedor = this.ccomparativoProveedorRepository.
                    getByIdCcomparativoAndIdProveedor(ccomparativo, proveedor);
            if (StringUtils.isBlank(proveedor.getAcreedorCodigoSap())) {
                exitoProveedor = false;
                exito = false;
                ccomparativoProveedor.setIndCreacionProveedor(Constant.DOS);
                ccomparativoProveedor.setIndCreacionProveedorSapOk(Constant.CERO);
                List<SapLog> listasapLog = bean.getListasapLog();
                String observaciones = "PROVEEDOR: " + System.lineSeparator();
                for(SapLog beanSaplog: listasapLog) {
                    observaciones += "(" + beanSaplog.getTipo() + ") - " + beanSaplog.getMesaj() + System.lineSeparator();
                }
                ccomparativoProveedor.setObservacionesSap(observaciones);
            }
            else {
                ccomparativoProveedor.setIndCreacionProveedor(Constant.UNO);
                ccomparativoProveedor.setIndCreacionProveedorSapOk(Constant.UNO);
                ccomparativoProveedor.setCodigoAcreedorSap(proveedor.getAcreedorCodigoSap());
            }
            this.ccomparativoProveedorRepository.save(ccomparativoProveedor);
        }


//        /* Creando Contrato Marco de nuevo */
//        boolean existeCM = false;
//        boolean exitoCM = true;
//        for (CcomparativoProveedor bean : ccomparativoProveedorList) {
//            String indicadorCM = bean.getIndCreacionCm();
//            if (StringUtils.isBlank(indicadorCM)) {
//                continue;
//            }
//            if (indicadorCM.equals(Constant.DOS)) {
//                existeCM = true;
//                List<CcomparativoAdjudicado> ccomparativoAdjudicadoListProveedor =
//                        this.ccomparativoAdjudicadoRepository.findByLicitacionProveedor(
//                                licitacion.getIdLicitacion(),
//                                bean.getIdProveedor().getIdProveedor());
//
//                ContratoMarcoResponseDto grabarContratoMarco = this.jcoContratoMarcoService.
//                        grabarContratoMarco(
//                                bean,
//                                bean.getIdProveedor(),
//                                usuario,
//                                ccomparativoAdjudicadoListProveedor);
//
//                if (grabarContratoMarco.isExito()) {
//                    bean.setIndCreacionCm(Constant.UNO);
//                    bean.setIndCreacionCmSapOk(Constant.UNO);
//                    bean.setNumeroDocumentoSap(grabarContratoMarco.getNumeroContratoMarco());
//                }
//                else {
//                    exitoCM = false;
//                    exito = false;
//                    bean.setIndCreacionCm(Constant.DOS);
//                    bean.setIndCreacionCmSapOk(Constant.CERO);
//
//                    List<SapLog> listasapLog = grabarContratoMarco.getSapLogList();
//                    String observaciones = "";
//                    if (Optional.ofNullable(bean.getObservacionesSap()).isPresent()) {
//                        observaciones = bean.getObservacionesSap() + System.lineSeparator();
//                    }
//                    observaciones += "CONTRATO MARCO: " + System.lineSeparator();
//                    for(SapLog beanSaplog: listasapLog) {
//                        observaciones += "(" + beanSaplog.getTipo() + ") - " + beanSaplog.getMesaj() + System.lineSeparator();
//                    }
//                    bean.setObservacionesSap(observaciones);
//                }
//                this.ccomparativoProveedorRepository.save(bean);
//            }
//
//        }
//
//        /* Creando Orden de Compra de nuevo */
//        boolean existeOC = false;
//        boolean exitoOC = true;
//        for (CcomparativoProveedor bean : ccomparativoProveedorList) {
//            String indicadorOC = bean.getIndCreacionOc();
//            if (indicadorOC.equals(Constant.DOS)) {
//                existeOC = true;
//                List<CcomparativoAdjudicado> ccomparativoAdjudicadoListProveedor =
//                        this.ccomparativoAdjudicadoRepository.findByLicitacionProveedor(
//                                licitacion.getIdLicitacion(),
//                                bean.getIdProveedor().getIdProveedor());
//                OrdenCompraResponseDto ordenCompraResponseDto = this.jcoOrdenCompraService.
//                        grabarOrdenCompra(
//                                bean.getIdClaseDocumento().getCodigoClaseDocumento(),
//                                bean.getIdProveedor(),
//                                usuario,
//                                ccomparativoAdjudicadoListProveedor);
//                if (ordenCompraResponseDto.isExito()) {
//                    bean.setIndCreacionOc(Constant.UNO);
//                    bean.setIndCreacionOcSapOk(Constant.UNO);
//                    bean.setNumeroDocumentoSap(ordenCompraResponseDto.getNumeroOrdenCompra());
//                }
//                else {
//                    exitoOC = false;
//                    exito = false;
//                    bean.setIndCreacionOc(Constant.DOS);
//                    bean.setIndCreacionOcSapOk(Constant.CERO);
//
//                    List<SapLog> listasapLog = ordenCompraResponseDto.getSapLogList();
//                    String observaciones = "";
//                    if (Optional.ofNullable(bean.getObservacionesSap()).isPresent()) {
//                        observaciones = bean.getObservacionesSap() + System.lineSeparator();
//                    }
//                    observaciones += "ORDEN DE COMPRA: " + System.lineSeparator();
//                    for(SapLog beanSaplog: listasapLog) {
//                        observaciones += "(" + beanSaplog.getTipo() + ") - " + beanSaplog.getMesaj() + System.lineSeparator();
//                    }
//                    bean.setObservacionesSap(observaciones);
//                }
//                this.ccomparativoProveedorRepository.save(bean);
//            }
//
//        }

        /* Actualizando Estado e Indicadores de Licitación */
        Date fechaActual = DateUtils.obtenerFechaHoraActual();
        licitacion = this.licitacionRepository.getOne(idLicitacion);
        licitacion.setIndEjecucionSapOk(Constant.CERO);
        licitacion.setIndCreacionProveedorSapOk(Constant.CERO);
        licitacion.setIndCreacionOcSapOk(Constant.CERO);
        licitacion.setIndCreacionCmSapOk(Constant.CERO);
        licitacion.setFechaModificacion(new Timestamp(fechaActual.getTime()));
        if (exito) {
            licitacion.setIndEjecucionSapOk(Constant.UNO);
        }
        else {
            licitacion.setIndEjecucionSapOk(Constant.DOS);
        }
        if (existeProveedor) {
            if (exitoProveedor) {
                licitacion.setIndCreacionProveedorSapOk(Constant.UNO);
            }
            else {
                licitacion.setIndCreacionProveedorSapOk(Constant.DOS);
            }
        }
//        if (existeCM) {
//            if (exitoCM) {
//                licitacion.setIndCreacionCmSapOk(Constant.UNO);
//            }
//            else {
//                licitacion.setIndCreacionCmSapOk(Constant.DOS);
//            }
//        }
//        if (existeOC) {
//            if (exitoOC) {
//                licitacion.setIndCreacionOcSapOk(Constant.UNO);
//            }
//            else {
//                licitacion.setIndCreacionOcSapOk(Constant.DOS);
//            }
//        }
        this.licitacionRepository.save(licitacion);


        /* Devolviendo informacion */
        ccomparativoProveedorList = this.ccomparativoProveedorRepository.findByLicitacion(idLicitacion);
        return ccomparativoProveedorList;
    }

    public CComparativoAdjudicarDto adjudicar(CComparativoAdjudicarDto dto) throws Exception {
        if (dto == null) {
            throw new Exception("CComparativoAdjudicarDto ingresado tiene Valor Nulo");
        }
        if (dto.getIdLicitacion() == null) {
            throw new Exception("Id Licitacion ingresado tiene valor Nulo");
        }
        List<CcomparativoProveedor> ccomparativoProveedorList = dto.getCcomparativoProveedorList();
        if (ccomparativoProveedorList == null || ccomparativoProveedorList.size() <= 0) {
            throw new Exception("No hay Lista de Proveedores a Adjudicar");
        }
        List<CcomparativoAdjudicado> ccomparativoAdjudicadoList = dto.getCcomparativoAdjudicadoList();
        if (ccomparativoAdjudicadoList == null || ccomparativoAdjudicadoList.size() <= 0) {
            throw new Exception("No hay Lista Detalle de la Adjudicación");
        }

        Licitacion licitacion = this.licitacionRepository.getOne(dto.getIdLicitacion());
        if (!Optional.ofNullable(licitacion).isPresent()) {
            throw new Exception("No se encontró Licitación");
        }

        boolean okSunat = this.ccomparativoServiceNew.verificarDataSunat(ccomparativoProveedorList);
        if (!okSunat) {
            throw new Exception("Existen Proveedores con Estado FALSO en Activo / Habido de los datos obtenidos por SUNAT");
        }
        logger.error("ccomparativoProveedorList: " + ccomparativoProveedorList.toString());

        Usuario usuario = this.usuarioRepository.getByCodigoUsuarioIdp(dto.getUserSession().getId());
        if (Optional.ofNullable(usuario).isPresent()) {
            dto.setUsuario(usuario);
            if (StringUtils.isBlank(usuario.getCodigoSap())) {
                throw new Exception("Debe ingresar Código Usuario SAP en el Mantenimiento de Usuarios para el Usuario: " + usuario.getEmail() );
            }
//            if (StringUtils.isBlank(usuario.getCodigoEmpleadoSap())) {
//                throw new Exception("Debe ingresar Código Empleado SAP en el Mantenimiento de Usuarios para el Usuario: " + usuario.getEmail() );
//            }
        }
        else {
            throw new Exception("No se encontró Usuario con Código IDP: " + dto.getUserSession().getId() );
        }

        String numeroPeticionOfertaLicitacionSap = licitacion.getNumeroPeticionOfertaLicitacionSap();
        boolean existeNumPeticionOfertaLicitacionSap = StringUtils.isNotBlank(numeroPeticionOfertaLicitacionSap);
        boolean exitoActualizacionPeticionOferta = true;

        if (existeNumPeticionOfertaLicitacionSap) {
            PeticionOfertaRFCResponseDto peticionOfertaRFCResponseDto = this.jcoPeticionOfertaService.getPeticionOfertaResponseByCodigo(numeroPeticionOfertaLicitacionSap, true);
            logger.error("peticionOfertaRFCResponseDto: " + peticionOfertaRFCResponseDto.toString());
            List<ProveedorCustom> proveedorCustomList = peticionOfertaRFCResponseDto.getListaProveedorSeleccionado();
            List<SolicitudPedido> posicionPeticionOfertaDtoList = peticionOfertaRFCResponseDto.getListaSolped();
            List<PeticionOfertaRFCDto> peticionOfertaRFCDtoList = new ArrayList<>();
            PeticionOfertaRFCRequestDto peticionOfertaRFCRequestDto = new PeticionOfertaRFCRequestDto();

            logger.error("proveedorCustomList: " + proveedorCustomList.toString());
            logger.error("idLicitacion: " + licitacion.getIdLicitacion());
            for(ProveedorCustom proveedor : proveedorCustomList){
                logger.error("Busqueda cotizacion // idLicitacion = " + licitacion.getIdLicitacion() + " / idProveedor = "+ proveedor.getIdProveedor());
                Cotizacion cotizacion = cotizacionRepository.findByLicitacionAndProveedor(licitacion.getIdLicitacion(),proveedor.getIdProveedor());

                logger.error("Busqueda cotizacion // cotizacion encontrada = " + (cotizacion != null ? cotizacion.toString() : "--"));
                if(cotizacion != null){
                    List<CotizacionDetalle> cotizacionDetalleList = cotizacionDetalleRepository.findByCotizacion(cotizacion);

                    logger.error("Busqueda cotizacion // cotizacionDetalleList = " + cotizacionDetalleList.toString());
                    for(CotizacionDetalle cotizacionDetalle : cotizacionDetalleList){
                        logger.error("Busqueda cotizacion // cotizacionDetalle actual = " + cotizacionDetalle.toString());
                        if(cotizacionDetalle.getBienServicio() != null) {
                            String posicionCotizacionDetalle = cotizacionDetalle.getLicitacionDetalle().getPosicionSolicitudPedido();
                            Optional<SolicitudPedido> opPosicionPeticionOfertaDto = posicionPeticionOfertaDtoList.stream()
                                    .filter(pos -> pos.getRucProveedor().equals(cotizacion.getProveedor().getRuc())
                                            && pos.getPosicion().equals(posicionCotizacionDetalle)
                                            && pos.getBienServicio().getIdBienServicio().equals(cotizacionDetalle.getBienServicio().getIdBienServicio()))
                                    .findFirst();

                            if (opPosicionPeticionOfertaDto.isPresent()){
                                SolicitudPedido posicionPeticionOfertaDto = opPosicionPeticionOfertaDto.get();
                                String customPosicion = StringUtils.leftPad(String.valueOf(Integer.parseInt(posicionCotizacionDetalle) / 10),2,"0");

                                PeticionOfertaRFCDto peticionOfertaRFCDto = new PeticionOfertaRFCDto();
                                peticionOfertaRFCDto.setNumeroPeticionOferta(posicionPeticionOfertaDto.getSolicitudPedido());
                                peticionOfertaRFCDto.setPosicionPeticionOferta(customPosicion);
                                peticionOfertaRFCDto.setCodigoMaterial(cotizacionDetalle.getBienServicio().getCodigoSap());
                                peticionOfertaRFCDto.setPrecioUnitario(cotizacionDetalle.getPrecioUnitario());
                                peticionOfertaRFCDto.setCodigoMoneda(cotizacion.getMoneda().getCodigoMoneda());
                                peticionOfertaRFCDtoList.add(peticionOfertaRFCDto);
                            }
                        }
                    }
                }
            }
            logger.error("peticionOfertaRFCDtoList: " + peticionOfertaRFCDtoList.toString());

            List<SapLog> modPeticionOfertaSapLogList = new ArrayList<>();

            if(peticionOfertaRFCDtoList.size() > 0){
                peticionOfertaRFCRequestDto.setNumeroLicitacion(this.getNroLicitacionString(licitacion.getNroLicitacion(),licitacion.getAnioLicitacion()));
                peticionOfertaRFCRequestDto.setPeticionOfertaRFCDtoList(peticionOfertaRFCDtoList);

                modPeticionOfertaSapLogList = jcoPeticionOfertaService.modificarPeticionOferta(peticionOfertaRFCRequestDto);
            }
            else{
                SapLog sapLog = new SapLog();
                sapLog.setCode("EMPTY");
                sapLog.setMesaj("No hubo Peticiones de Oferta por actualizar");
                modPeticionOfertaSapLogList.add(sapLog);
            }

            for(SapLog log : modPeticionOfertaSapLogList){
                if(log.getCode().equals("E")) {
                    exitoActualizacionPeticionOferta = false;
                    break;
                }
            }

            dto.setModPeticionOfertaSapLogList(modPeticionOfertaSapLogList);
            logger.error("modPeticionOfertaSapLogList: " + modPeticionOfertaSapLogList.toString());
        }

        if(exitoActualizacionPeticionOferta) {
        /* Eliminando Cuadro Comparativo Anteriores */
            List<CcomparativoAdjudicado> ccomparativoAdjudicadosDelete = this.ccomparativoAdjudicadoRepository.findByLicitacion(licitacion);
            if (ccomparativoAdjudicadosDelete != null && ccomparativoAdjudicadosDelete.size() > 0) {
                for (CcomparativoAdjudicado bean : ccomparativoAdjudicadosDelete) {
                    this.ccomparativoAdjudicadoRepository.delete(bean);
                }
            }

            List<CcomparativoProveedor> ccomparativoProveedorsDelete = this.ccomparativoProveedorRepository.
                    findByLicitacion(dto.getIdLicitacion());
            if (ccomparativoProveedorsDelete != null && ccomparativoProveedorsDelete.size() > 0) {
                for (CcomparativoProveedor bean : ccomparativoProveedorsDelete) {
                    this.ccomparativoProveedorRepository.delete(bean);
                }
            }

            List<Ccomparativo> ccomparativosDelete = this.ccomparativoRepository.findByLicitacion(dto.getIdLicitacion());
            if (ccomparativosDelete != null && ccomparativosDelete.size() > 0) {
                for (Ccomparativo bean : ccomparativosDelete) {
                    this.ccomparativoRepository.delete(bean);
                }
            }

        /* Grabando datos en Cuadro Comparativo */
            Ccomparativo nuevoCuadroComparativo = new Ccomparativo();
            nuevoCuadroComparativo.setLicitacion(licitacion);
            nuevoCuadroComparativo.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
            nuevoCuadroComparativo.setUsuarioCreacion(dto.getIdUsuario());
            nuevoCuadroComparativo = this.ccomparativoRepository.save(nuevoCuadroComparativo);
            logger.error("adjudicar parte 2");

        /* Grabando datos en Cuadro Comparativo Proveedor */
            List<Proveedor> listaProveedor = new ArrayList<Proveedor>();
            List<Proveedor> listaProveedorPotencial = new ArrayList<Proveedor>();

            for (CcomparativoProveedor bean : ccomparativoProveedorList) {
                CcomparativoProveedor bean1 = new CcomparativoProveedor();
                BeanUtils.copyProperties(bean, bean1);
                logger.error("adjudicar CcomparativoProveedor bean inicial: " + bean1.toString());
                bean.setIdCcomparativo(nuevoCuadroComparativo);
                bean.setIndCreacionOc(Constant.CERO);
                bean.setIndCreacionCm(Constant.CERO);
                bean.setIndCreacionProveedor(Constant.CERO);
                bean.setIndCreacionProveedorSapOk(null);
                bean.setIndCreacionOcSapOk(null);
                bean.setIndCreacionCmSapOk(null);
                Proveedor proveedor = this.proveedorRepository.getOne(bean.getIdProveedor().getIdProveedor());
                bean.setIdProveedor(proveedor);
                String codigoAcreedorSap = proveedor.getAcreedorCodigoSap();
                if (StringUtils.isBlank(codigoAcreedorSap)) {
                    bean.setIndCreacionProveedor(Constant.UNO);
                    bean.setIndCreacionProveedorSapOk(Constant.CERO);
                    listaProveedorPotencial.add(proveedor);
                }

                listaProveedor.add(proveedor);

                Cotizacion cotizacion = this.cotizacionRepository.getByLicitacionAndProveedor(licitacion, proveedor);
                Moneda monedaCotizacion = cotizacion.getMoneda();
                bean.setIdMoneda(monedaCotizacion);
                bean.setSubtotal(new BigDecimal(0.00));

                List<CotizacionDetalle> cotizacionDetalleList = this.cotizacionDetalleRepository.findByCotizacion(cotizacion);
                CotizacionDetalle cotizacionDetalle = cotizacionDetalleList.get(0);
                LicitacionDetalle licitacionDetalle = cotizacionDetalle.getLicitacionDetalle();
//            if (StringUtils.isNotBlank(licitacionDetalle.getSolicitudPedido())) {
//                TipoDocumentoSap tipoDocumentoSap = this.tipoDocumentoSapRepository.getBySiglaTipoDocumentoSap(
//                        ClaseDocumentoConstant.TIPO_DOCUMENTO_OC);
//                if (Optional.ofNullable(tipoDocumentoSap).isPresent()) {
//                    bean.setIdTipoDocumentoSap(tipoDocumentoSap);
//                    bean.setIndCreacionOc(Constant.UNO);
//                    bean.setIndCreacionOcSapOk(Constant.CERO);
//                }
//            }
//            if (StringUtils.isBlank(licitacionDetalle.getSolicitudPedido())) {
//                if (Optional.ofNullable(licitacionDetalle.getBienServicio()).isPresent()) {
//                    TipoDocumentoSap tipoDocumentoSap = this.tipoDocumentoSapRepository.getBySiglaTipoDocumentoSap(
//                            ClaseDocumentoConstant.TIPO_DOCUMENTO_CM);
//                    if (Optional.ofNullable(tipoDocumentoSap).isPresent()) {
//                        bean.setIdTipoDocumentoSap(tipoDocumentoSap);
//                        bean.setIndCreacionCm(Constant.UNO);
//                        bean.setIndCreacionCmSapOk(Constant.CERO);
//                    }
//                }
//            }

                if (existeNumPeticionOfertaLicitacionSap) {
                    if (Optional.ofNullable(licitacionDetalle.getBienServicio()).isPresent()) {
                        TipoDocumentoSap tipoDocumentoSap = this.tipoDocumentoSapRepository.getBySiglaTipoDocumentoSap(
                                ClaseDocumentoConstant.TIPO_DOCUMENTO_PO);
                        if (Optional.ofNullable(tipoDocumentoSap).isPresent()) {
                            bean.setIdTipoDocumentoSap(tipoDocumentoSap);
//                        bean.setIndCreacionCm(Constant.UNO);
//                        bean.setIndCreacionCmSapOk(Constant.CERO);
                        }
                    }
                }
                CcomparativoProveedor bean2 = new CcomparativoProveedor();
                BeanUtils.copyProperties(bean, bean2);
                logger.error("adjudicar CcomparativoProveedor bean final: " + bean2.toString());
                this.ccomparativoProveedorRepository.save(bean);
            }

        /* Grabando datos en Cuadro Comparativo Adjudicado  */
            for (CcomparativoAdjudicado bean : ccomparativoAdjudicadoList) {

                CotizacionDetalle cotizacionDetalle = bean.getCotizacionDetalle();
                cotizacionDetalle = this.cotizacionDetalleRepository.getOne(cotizacionDetalle.getIdCotizacionDetalle());
                bean.setPrecioUnitario(cotizacionDetalle.getPrecioUnitario());

                Proveedor proveedor = cotizacionDetalle.getCotizacion().getProveedor();
                CcomparativoProveedor ccomparativoProveedor = this.ccomparativoProveedorRepository.
                        getByIdCcomparativoAndIdProveedor(nuevoCuadroComparativo, proveedor);
                bean.setCcomparativoProveedor(ccomparativoProveedor);

                logger.error("adjudicar CcomparativoAdjudicado bean: " + bean.toString());
                bean = this.ccomparativoAdjudicadoRepository.save(bean);
            }


        /* Actualizando subtotales */
            for (CcomparativoProveedor bean : ccomparativoProveedorList) {

                BigDecimal sumatoria = this.ccomparativoAdjudicadoRepository.
                        getSumatoriaMontoByLicitacionAndProveedor(
                                licitacion.getIdLicitacion(),
                                bean.getIdProveedor().getIdProveedor());
                if (!Optional.ofNullable(sumatoria).isPresent()) {
                    sumatoria = new BigDecimal(0.00);
                }
                sumatoria = new BigDecimal(sumatoria.floatValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                bean.setSubtotal(sumatoria);
                logger.error("adjudicar CcomparativoProveedor bean sumatoria: " + bean.toString());
                bean = this.ccomparativoProveedorRepository.save(bean);
            }
            logger.error("adjudicar parte 5");


        /* Verificando Proveedores Potenciales en SAP */
            boolean exito = true;
            boolean existeProveedor = false;
            boolean exitoProveedor = true;

////        String codigoEmpleadoSap = usuario.getCodigoEmpleadoSap();
//        String codigoEmpleadoSap = "";
//        if (listaProveedorPotencial != null && listaProveedorPotencial.size() > 0) {
//            existeProveedor = true;
//            ProveedorResponseRFC proveedorResponse = new ProveedorResponseRFC();
//            proveedorResponse.setTieneError(false);
//            SapLog sapLog = new SapLog();
//            sapLog.setCode(WebServiceConstant.RESPUESTA_OK);
//            sapLog.setMesaj(WebServiceConstant.MENSAJE_VACIO);
//            proveedorResponse = this.jcoProveedorService.grabarListaProveedorSAP(listaProveedorPotencial, codigoEmpleadoSap);
//            if (proveedorResponse.isTieneError()) {
//                exitoProveedor = false;
//                exito = false;
//            }
//            List<ProveedorRFCResponseDto> listaProveedorSAPResult = proveedorResponse.getListaProveedorSAPResult();
//            for (ProveedorRFCResponseDto bean : listaProveedorSAPResult) {
//                Proveedor proveedor = bean.getProveedorSap();
//                CcomparativoProveedor ccomparativoProveedor = this.ccomparativoProveedorRepository.
//                        getByIdCcomparativoAndIdProveedor(nuevoCuadroComparativo, proveedor);
//                if (StringUtils.isBlank(proveedor.getAcreedorCodigoSap())) {
//                    ccomparativoProveedor.setIndCreacionProveedor(Constant.DOS);
//                    ccomparativoProveedor.setIndCreacionProveedorSapOk(Constant.CERO);
//                    List<SapLog> listasapLog = bean.getListasapLog();
//                    String observaciones = "PROVEEDOR: " + System.lineSeparator();
//                    for (SapLog beanSaplog : listasapLog) {
//                        observaciones += "(" + beanSaplog.getTipo() + ") - " + beanSaplog.getMesaj() + System.lineSeparator();
//                    }
//                    ccomparativoProveedor.setObservacionesSap(observaciones);
//                } else {
//                    ccomparativoProveedor.setIndCreacionProveedorSapOk(Constant.UNO);
//                    ccomparativoProveedor.setCodigoAcreedorSap(proveedor.getAcreedorCodigoSap());
//                }
//                this.ccomparativoProveedorRepository.save(ccomparativoProveedor);
//            }
//        }


//        /* Actualizando en caso hubiera Contrato Marco */
//        String indCMGeneral = Constant.CERO;
//        boolean existeCM = false;
//        boolean exitoCM = true;
//
//        for (CcomparativoProveedor bean : ccomparativoProveedorList) {
//            String indicadorCM = bean.getIndCreacionCm();
//
//            if (indicadorCM.equals(Constant.UNO)) {
//                existeCM = true;
//
//                Date fechaIni = bean.getFechaInicioValidez();
//                Date fechaFin = bean.getFechaFinValidez();
//                BigDecimal valorAcumulado = bean.getValorAcumulado();
//                if (!Optional.ofNullable(fechaIni).isPresent()) {
//                    throw new Exception("Debe ingresar Fecha Inicio Validez para el Contrato Marco del Proveedor: "+ bean.getIdProveedor().getRazonSocial());
//                }
//                if (!Optional.ofNullable(fechaFin).isPresent()) {
//                    throw new Exception("Debe ingresar Fecha Fin Validez para el Contrato Marco del Proveedor: "+ bean.getIdProveedor().getRazonSocial());
//                }
//                if (!Optional.ofNullable(valorAcumulado).isPresent()) {
//                    throw new Exception("Debe ingresar Valor Acumulado para el Contrato Marco del Proveedor: "+ bean.getIdProveedor().getRazonSocial());
//                }
//
//                ClaseDocumento claseDocumento = bean.getIdClaseDocumento();
//                claseDocumento = this.claseDocumentoRepository.getOne(claseDocumento.getIdClaseDocumento());
//                bean.setIdClaseDocumento(claseDocumento);
//
//                List<CcomparativoAdjudicado> ccomparativoAdjudicadoListProveedor = this.ccomparativoAdjudicadoRepository.
//                        findByLicitacionProveedor(
//                                licitacion.getIdLicitacion(),
//                                bean.getIdProveedor().getIdProveedor());
//                BigDecimal sumaPrecio = new BigDecimal(0.00);
//                for (int i=0; i < ccomparativoAdjudicadoListProveedor.size(); i++) {
//                    CcomparativoAdjudicado ccomparativoAdjudicado = ccomparativoAdjudicadoListProveedor.get(i);
//                    CotizacionDetalle cotizacionDetalle = ccomparativoAdjudicado.getCotizacionDetalle();
//                    cotizacionDetalle = this.cotizacionDetalleRepository.getOne(cotizacionDetalle.getIdCotizacionDetalle());
//                    ccomparativoAdjudicado.setCotizacionDetalle(cotizacionDetalle);
//                    ccomparativoAdjudicadoListProveedor.set(i,ccomparativoAdjudicado);
//
//                    sumaPrecio = new BigDecimal(
//                            sumaPrecio.floatValue() +
//                                ccomparativoAdjudicado.getCantidadReal().floatValue() * ccomparativoAdjudicado.getPrecioUnitario().floatValue()
//                    );
//                }
//                if (valorAcumulado.floatValue() < sumaPrecio.floatValue()) {
//                    throw new Exception("Valor Acumulado: "+ valorAcumulado.floatValue() + " debe ser un Monto Mayor Igual a la suma del Precio Total: "+ sumaPrecio.floatValue());
//                }
//                ContratoMarcoResponseDto grabarContratoMarco = this.jcoContratoMarcoService.
//                        grabarContratoMarco(
//                                bean,
//                                bean.getIdProveedor(),
//                                usuario,
//                                ccomparativoAdjudicadoListProveedor);
//                if (grabarContratoMarco.isExito()) {
//                    bean.setIndCreacionCmSapOk(Constant.UNO);
//                    bean.setNumeroDocumentoSap(grabarContratoMarco.getNumeroContratoMarco());
//                }
//                else {
//                    exito = false;
//                    exitoCM = false;
//                    bean.setIndCreacionCm(Constant.DOS);
//                    bean.setIndCreacionCmSapOk(Constant.CERO);
//
//                    List<SapLog> listasapLog = grabarContratoMarco.getSapLogList();
//                    String observaciones = "";
//                    if (Optional.ofNullable(bean.getObservacionesSap()).isPresent()) {
//                        observaciones = bean.getObservacionesSap() + System.lineSeparator();
//                    }
//                    observaciones += "CONTRATO MARCO: " + System.lineSeparator();
//                    for(SapLog beanSaplog: listasapLog) {
//                        observaciones += "(" + beanSaplog.getTipo() + ") - " + beanSaplog.getMesaj() + System.lineSeparator();
//                    }
//                    bean.setObservacionesSap(observaciones);
//                }
//                this.ccomparativoProveedorRepository.save(bean);
//            }
//        }
//
//        /* Actualizando en caso hubiera Orden de Compra */
//        boolean existeOC = false;
//        boolean exitoOC = true;
//        //logger.error("adjudicar parte 8");
//        for (CcomparativoProveedor bean : ccomparativoProveedorList) {
//            String indicadorOC = bean.getIndCreacionOc();
//            if (indicadorOC.equals(Constant.UNO)) {
//                ClaseDocumento claseDocumento = bean.getIdClaseDocumento();
//                claseDocumento = this.claseDocumentoRepository.getOne(claseDocumento.getIdClaseDocumento());
//                bean.setIdClaseDocumento(claseDocumento);
//
//                existeOC = true;
//                List<CcomparativoAdjudicado> ccomparativoAdjudicadoListProveedor = this.ccomparativoAdjudicadoRepository.
//                        findByLicitacionProveedor(
//                                licitacion.getIdLicitacion(),
//                                bean.getIdProveedor().getIdProveedor());
//                for (int i=0; i < ccomparativoAdjudicadoListProveedor.size(); i++) {
//                    CcomparativoAdjudicado ccomparativoAdjudicado = ccomparativoAdjudicadoListProveedor.get(i);
//                    CotizacionDetalle cotizacionDetalle = ccomparativoAdjudicado.getCotizacionDetalle();
//                    cotizacionDetalle = this.cotizacionDetalleRepository.getOne(cotizacionDetalle.getIdCotizacionDetalle());
//                    ccomparativoAdjudicado.setCotizacionDetalle(cotizacionDetalle);
//                    ccomparativoAdjudicadoListProveedor.set(i,ccomparativoAdjudicado);
//                }
//
//                OrdenCompraResponseDto ordenCompraResponseDto = this.jcoOrdenCompraService.
//                        grabarOrdenCompra(
//                                bean.getIdClaseDocumento().getCodigoClaseDocumento(),
//                                bean.getIdProveedor(),
//                                usuario,
//                                ccomparativoAdjudicadoListProveedor);
//                if (ordenCompraResponseDto.isExito()) {
//                    bean.setIndCreacionOcSapOk(Constant.UNO);
//                    bean.setNumeroDocumentoSap(ordenCompraResponseDto.getNumeroOrdenCompra());
//                }
//                else {
//                    exitoOC = false;
//                    exito = false;
//                    bean.setIndCreacionOc(Constant.DOS);
//                    bean.setIndCreacionOcSapOk(Constant.CERO);
//
//                    List<SapLog> listasapLog = ordenCompraResponseDto.getSapLogList();
//                    String observaciones = "";
//                    if (Optional.ofNullable(bean.getObservacionesSap()).isPresent()) {
//                        observaciones = bean.getObservacionesSap() + System.lineSeparator();
//                    }
//                    observaciones += "ORDEN DE COMPRA: " + System.lineSeparator();
//                    for(SapLog beanSaplog: listasapLog) {
//                        observaciones += "(" + beanSaplog.getTipo() + ") - " + beanSaplog.getMesaj() + System.lineSeparator();
//                    }
//                    bean.setObservacionesSap(observaciones);
//                }
//                this.ccomparativoProveedorRepository.save(bean);
//            }
//
//        }

        /* Actualizando Estado e Indicadores de Licitación */
            Date fechaActual = DateUtils.obtenerFechaHoraActual();
            licitacion = this.licitacionRepository.getOne(dto.getIdLicitacion());
            licitacion.setIndEjecucionSapOk(Constant.CERO);
            licitacion.setIndCreacionProveedorSapOk(Constant.CERO);
            licitacion.setIndCreacionOcSapOk(Constant.CERO);
            licitacion.setIndCreacionCmSapOk(Constant.CERO);
            licitacion.setFechaModificacion(new Timestamp(fechaActual.getTime()));
            licitacion.setEstadoLicitacion(LicitacionConstant.ESTADO_ADJUDICADA);

            if (exito) {
                licitacion.setIndEjecucionSapOk(Constant.UNO);
            } else {
                licitacion.setIndEjecucionSapOk(Constant.DOS);
            }
//        if (existeProveedor) {
//            if (exitoProveedor) {
//                licitacion.setIndCreacionProveedorSapOk(Constant.UNO);
//            }
//            else {
//                licitacion.setIndCreacionProveedorSapOk(Constant.DOS);
//            }
//        }
//        if (existeCM) {
//            if (exitoCM) {
//                licitacion.setIndCreacionCmSapOk(Constant.UNO);
//            }
//            else {
//                licitacion.setIndCreacionCmSapOk(Constant.DOS);
//            }
//        }
//        if (existeOC) {
//            if (exitoOC) {
//                licitacion.setIndCreacionOcSapOk(Constant.UNO);
//            }
//            else {
//                licitacion.setIndCreacionOcSapOk(Constant.DOS);
//            }
//        }
            this.licitacionRepository.save(licitacion);

            dto.setExito(exito);
            dto.setListaProveedor(listaProveedor);
            dto.setLicitacion(licitacion);

            logger.error("adjudicar parte FIN");
        }
        else{
            dto.setExito(exitoActualizacionPeticionOferta);
        }

        return dto;
    }

    public void enviarCuadroComparativo(Licitacion licitacion, List<Proveedor> listaProveedor)   {
        for(Proveedor item : listaProveedor) {
            Proveedor proveedor = item;
            this.enviarAdjudicacionCComparativoNotificacion.enviar(
                    this.parametroMapper.getMailSetting(),
                    licitacion,
                    proveedor
            );
        }
    }

    public void enviarCorreoAgradecimiento(Licitacion licitacion) throws Exception {
        List<CcomparativoProveedor> ccomparativoProveedorList =
                this.ccomparativoProveedorRepository.findByLicitacion(licitacion.getIdLicitacion());
        if (ccomparativoProveedorList == null || ccomparativoProveedorList.isEmpty()) {
            return;
        }

        List<LicitacionProveedor> licitacionProveedorList = this.licitacionProveedorRepository.
                findByLicitacionSiParticipa(licitacion, Constant.S);
        Integer idLicitacion = licitacion.getIdLicitacion();
        for (LicitacionProveedor bean : licitacionProveedorList) {
            Proveedor proveedor = bean.getProveedor();
            Proveedor proveedorAdjudicado = this.ccomparativoProveedorRepository.
                    getByLicitacionAndProveedor(idLicitacion, proveedor.getIdProveedor());
            if (!Optional.ofNullable(proveedorAdjudicado).isPresent()) {
                this.enviarAgradecimientoNoAdjudicadoNotificacion.enviar(
                        this.parametroMapper.getMailSetting(),
                        licitacion,
                        proveedor
                );
            }
        }
    }


    public void enviarCuadroComparativo(CuadroComparativoGrabarDTO dto, List<Proveedor> listaProveedor)   {
        Licitacion licitacion = this.licitacionRepository.getOne(dto.getIdLicitacion());
        List<CcomparativoAdjudicado> listaAdjudicados =  dto.getListaAdjudicacion();

        Map<String, Object> mapSmtp = new HashMap<String, Object>();
        mapSmtp = this.getConfigSmtp();
        Map<String, Object> mapTemplate;
        for(Proveedor item : listaProveedor) {

            Proveedor proveedor = this.proveedorRepository.getOne(item.getIdProveedor());
            this.enviarAdjudicacionCComparativoNotificacion.enviar(
                    this.parametroMapper.getMailSetting(),
                    licitacion,
                    proveedor
            );
         }
    }

    private String getNroLicitacionString(Integer numero, Integer annio){
        String texto = ("00000000" + numero);
        String nroLicitacionString = annio + texto.substring(texto.length() - 8, texto.length());

        return nroLicitacionString;
    }
}
