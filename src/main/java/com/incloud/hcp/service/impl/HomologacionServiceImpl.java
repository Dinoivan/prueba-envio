package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.EstadoProveedor;
import com.incloud.hcp.domain.Homologacion;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.ProveedorHomologacion;
import com.incloud.hcp.dto.ProveedorVerNotaDto;
import com.incloud.hcp.dto.homologacion.LineaComercialHomologacionDto;
import com.incloud.hcp.enums.EstadoProveedorEnum;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.jco.proveedor.dto.ProveedorRFCResponseDto;
import com.incloud.hcp.jco.proveedor.service.JCOProveedorService;
import com.incloud.hcp.myibatis.mapper.HomologacionMapper;
import com.incloud.hcp.myibatis.mapper.ParametroMapper;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.service.HomologacionService;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.cmiscf.CmisBaseService;
import com.incloud.hcp.service.notificacion.ActualizacionDeDatosNotificacion;
import com.incloud.hcp.service.notificacion.HomologacionAprobadaNotificacion;
import com.incloud.hcp.service.notificacion.HomologacionDesaprobadaNotificacion;
import com.incloud.hcp.util.Constant;
import com.incloud.hcp.util.constant.ParametroConstant;
import com.incloud.hcp.util.constant.ParametroTipoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Administrador on 28/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class HomologacionServiceImpl implements HomologacionService {

    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private HomologacionMapper homologacionMapper;
    //@Autowired
    private ProveedorService proveedorService;
    @Autowired
    private ProveedorHomologacionRepository proveedorHomologacionRepository;
    @Autowired
    private HomologacionRespuestaRepository homologacionRespuestaRepository;
    //@Autowired
    //private LineaComercialRepository lineaComercialReposity;
    @Autowired
    private HomologacionRepository homologacionRepository;
    @Autowired
    private CmisBaseService cmisService;
    @Autowired
    private HomologacionAprobadaNotificacion homologacionAprobadaNotificacion;
    @Autowired
    private HomologacionDesaprobadaNotificacion homologacionDesaprobadaNotificacion;
    @Autowired
    private ParametroMapper parametroMapper;
    @Autowired
    private ActualizacionDeDatosNotificacion actualizacionDeDatosNotificacion;

    @Autowired
    private EstadoProveedorRepository estadoProveedorRepository;

    @Autowired
    private JCOProveedorService jcoProveedorService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Proveedor evaluarProveedor(Integer idProveedor) throws PortalException {
        Proveedor p = Optional.ofNullable(idProveedor)
                .map(proveedorRepository::getOne)
                .orElseThrow(() -> new PortalException("El proveedor no existe"));
        homologacionMapper.evaluarProveedor(p);
        if (p.getEvaluacionHomologacion() == null) {
            throw new PortalException("Evaluación de homologación es NULL");
        }

        if (p.getIndHomologado() == null) {
            throw new PortalException("El indicador de homologación es NULL");
        }
        return proveedorService.save(p);
    }


    public ProveedorVerNotaDto verNota(Integer idProveedor) throws PortalException {
        Proveedor p = proveedorRepository.getOne(idProveedor);
        if (!Optional.ofNullable(idProveedor).isPresent()) {
            throw new PortalException("El proveedor no existe");
        }
        String indHomologado = p.getIndHomologado();
        BigDecimal evaluacionHomologacion = p.getEvaluacionHomologacion();
        ProveedorVerNotaDto proveedorVerNotaDto = new ProveedorVerNotaDto();
        proveedorVerNotaDto.setIdProveedor(idProveedor);
        proveedorVerNotaDto.setIndHomologado(indHomologado);
        proveedorVerNotaDto.setEvaluacionHomologacion(evaluacionHomologacion);
        homologacionMapper.evaluarProveedorVerNota(proveedorVerNotaDto);

        logger.error("Ingresando Service verNota proveedorVerNotaDto: " + proveedorVerNotaDto.toString());
        return proveedorVerNotaDto;
    }


    @Override
    public ProveedorRFCResponseDto evaluarProveedorDto(Integer idProveedor, Boolean comunidad) throws Exception {
        Proveedor p = Optional.ofNullable(idProveedor)
                .map(proveedorRepository::getOne)
                .orElseThrow(() -> new PortalException("El proveedor no existe"));
        logger.error("Ingresando evaluarProveedorDto 00 p: " + p.toString());
        homologacionMapper.evaluarProveedor(p);
        logger.error("Ingresando evaluarProveedorDto 01 p: " + p.toString());

        if (p.getEvaluacionHomologacion() == null) {
            throw new PortalException("Evaluación de homologación es NULL");
        }

        if (p.getIndHomologado() == null) {
            throw new PortalException("El indicador de homologación es NULL");
        }
        p.setIndProveedorComunidad(comunidad ? "1" : "0");

        EstadoProveedor previoEstadoProveedor = p.getIdEstadoProveedor();
        EstadoProveedor estadoProveedor = this.estadoProveedorRepository.
                getByCodigoEstadoProveedor(EstadoProveedorEnum.HOMOLOGADO.getCodigo());
        p.setIdEstadoProveedor(estadoProveedor);
        p = proveedorRepository.save(p);

        ProveedorRFCResponseDto proveedorResponse = new ProveedorRFCResponseDto();
        if(p.getIndHomologado().equals("1")) {
        /* Agregando Proveedor Potencial para envio a crear en SAP */
//        String codigoEmpleadoSap = usuario.getCodigoEmpleadoSap();
            String codigoEmpleadoSap = "";
            proveedorResponse = this.jcoProveedorService.grabarUnicoProveedorSAP(p, codigoEmpleadoSap);
            if(proveedorResponse.getNroAcreedor() != null && !proveedorResponse.getNroAcreedor().isEmpty()){ // si se creo el proveedor en SAP -> envia correo de aprobacion al proveedor
                this.homologacionAprobadaNotificacion.enviar(this.parametroMapper.getMailSetting(), p);
            }
            else { // si no se creo el proveedor en SAP -> cambia el indicador de homologado a "0", regresa a estado anteior y no envia ningun correo
                p.setIndHomologado("0");
                p.setIdEstadoProveedor(previoEstadoProveedor);
                p = proveedorRepository.save(p);
            }
        } else {
            this.homologacionDesaprobadaNotificacion.enviar(this.parametroMapper.getMailSetting(), p);
        }

        proveedorResponse.setProveedorDto(proveedorService.getProveedorDtoById(p.getIdProveedor()));

        return proveedorResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineaComercialHomologacionDto> getListHomologacionByIdProveedor(Integer idProveedor) throws PortalException {
        return homologacionMapper.getListHomologacionByIdProveedor(idProveedor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineaComercialHomologacionDto> getListHomologacionByIdProveedorResponder(Integer idProveedor) throws PortalException {
        return homologacionMapper.getListHomologacionByIdProveedorResponder(idProveedor);
    }



    @Override
    public List<LineaComercialHomologacionDto> guardarHomologacion(Proveedor proveedor, List<LineaComercialHomologacionDto> lineasComercialHomologacion) throws PortalException {

        Optional<List<LineaComercialHomologacionDto>> oLineas = Optional.ofNullable(lineasComercialHomologacion);

        if (!oLineas.isPresent()) {
            throw new PortalException("La list de lineas comerciales esta vacía");
        }
        logger.error("Ingresando guardarHomologacion lineasComercialHomologacion: " + lineasComercialHomologacion.toString());
        proveedorHomologacionRepository.deleteRespuestaByIdProveedor(proveedor.getIdProveedor());
        homologacionMapper.getListHomologacionByIdProveedor(proveedor.getIdProveedor())
                .stream()
                .map(linea -> linea.getPreguntas())
                .filter(pregunta -> pregunta != null)
                .forEach(preguntas -> {
                    List<String> listId = preguntas.stream()
                            .filter(pregunta -> pregunta.getIndicadorAdjunto())
                            .map(pregunta -> pregunta.getRespuestaProveedor())
                            .filter(respuesta -> respuesta != null)
                            .map(respuesta -> respuesta.getArchivoId())
                            .filter(fileId -> fileId != null)
                            .collect(Collectors.toList());
                    cmisService.deleteFiles(listId,proveedor.getRuc());
                });

        /**
         * Actualización de la veersión de los archivos adjuntos
         */
        oLineas.get().stream()
                .map(linea -> linea.getPreguntas())
                .filter(preguntas -> preguntas != null)
                .filter(preguntas -> !preguntas.isEmpty())
                .forEach(preguntas -> {
                    List<String> listId = preguntas
                            .stream()
                            .filter(pregunta -> pregunta.getIndicadorAdjunto())
                            .map(pregunta -> pregunta.getRespuestaProveedor())
                            .filter(respuesta -> respuesta != null)
                            .map(respuesta -> respuesta.getArchivoId())
                            .filter(fileId -> fileId != null)
                            .collect(Collectors.toList());
                    cmisService.updateFileLastVersionTrue(listId,proveedor.getRuc());
                });

        /**
         * Guarda la respuesta
         */
        oLineas.get().stream()
                .map(linea -> linea.getPreguntas())
                .forEach(preguntas -> preguntas.stream().forEach(pregunta -> {
                    Optional.ofNullable(pregunta.getRespuestaProveedor()).ifPresent(respuesta -> {
                        Optional.ofNullable(respuesta.getIdHomologacionRespuesta())
                                .map(homologacionRespuestaRepository::getOne)
                                .ifPresent(hr -> {
                                    logger.error("Ingresando guardarHomologacion pregunta: " + pregunta.toString());
                                    ProveedorHomologacion ph = new ProveedorHomologacion();
                                    ph.setHomologacion(hr.getHomologacion());
                                    ph.setArchivoNombre(respuesta.getNombreArchivo());
                                    ph.setArchivoId(respuesta.getArchivoId());
                                    ph.setRutaAdjunto(respuesta.getRutaAdjunto());
                                    ph.setValorRespuestaLibre(pregunta.getValorRespuestaLibre());
                                    ph.setProveedor(proveedor);
                                    ph.setHomologacionRespuesta(hr);
                                    proveedorHomologacionRepository.save(ph);
                                });
                    });
                }));

        Boolean evaluationIsZero = Optional.ofNullable(proveedor.getEvaluacionHomologacion())
                .map(evaluacion -> evaluacion.compareTo(BigDecimal.ZERO) == 0)
                .orElse(Boolean.TRUE);

        if(!evaluationIsZero){
            /**
             * Envia una notificación al administrador
             */
            Optional.ofNullable(parametroMapper.getListParametro(ParametroConstant.ADMINISTRACION, ParametroTipoConstant.EMAIL))
                    .filter(l -> !l.isEmpty())
                    .map(l -> l.get(0))
                    .map(parametro -> parametro.getValor())
                    .ifPresent(emailAdministrador -> {
                        actualizacionDeDatosNotificacion.enviar(parametroMapper.getMailSetting(), proveedor, emailAdministrador);
                    });
        }

        return homologacionMapper.getListHomologacionByIdProveedor(proveedor.getIdProveedor());
    }

    @Override
    public List<Homologacion> getAll() throws PortalException {
        return homologacionRepository.findAll();
    }

    @Override
    public Homologacion getHomologacionById(Integer id) throws PortalException {
        return homologacionRepository.getOne(id);
    }

    @Override
    public Homologacion guardar(Homologacion homologacion) {
        return this.homologacionRepository.save(homologacion);
    }

    /*
    @Override
    public ResponseEntity<Map> delete(Homologacion homologacion) {
        ResponseEntity response = null;
        Map map = null;
        try {
            List<ProveedorHomologacion> ph = proveedorHomologacionRepository.findByHomologacion(homologacion);
            map = new HashMap<>();
            if (ph != null && ph.size() > 0) {
                map.put("message", "No se puede Editar. La pregunta ya es utilizada en la homologación de otro proveedor");
                response = new ResponseEntity(map, HttpStatus.OK);
            } else {
                homologacionRespuestaRepository.deleteHomologacionRespuestaByHomologacion(homologacion.getIdHomologacion());
                homologacionRepository.delete(homologacion);

                map.put("data", homologacion);
                response = new ResponseEntity(map, HttpStatus.OK);
            }
        } catch (Exception e) {
            map = new HashMap<>();
            map.put("message", e.getMessage());
            response = new ResponseEntity(map, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public Homologacion save(HomologacionCustom custom) {
        //String response = "exito";
        //ResponseEntity response;
        //Map map;
        //try {
            Homologacion homologacion = custom.getHomologacion();
            logger.error("CUSTOM.getHomologacion: " + custom.getHomologacion());
            homologacion.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
            homologacion.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
            homologacion.setUsuarioCreacion(1);
            homologacion.setUsuarioModificacion(1);
            LineaComercial lineaComercial = lineaComercialReposity.getLineaComercialById(homologacion.getLineaComercial().getIdLineaComercial());
            logger.error("LINEA_COMERCIAL: " + lineaComercial.getDescripcion());
            homologacion.setLineaComercial(lineaComercial);
            //map = new HashMap<>();
            //map.put("data", homologacionRepository.save(homologacion));
            Homologacion obj = homologacionRepository.save(homologacion);

            List<HomologacionRespuesta> respuestas = custom.getHomologacionRespuestas();
            logger.error("RESPUESTAS: " + respuestas.size());
            int i = 1;
            for (HomologacionRespuesta hr : respuestas) {
                hr.setHomologacion(homologacion);
                hr.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
                hr.setUsuarioCreacion(1);
                hr.setUsuarioModificacion(1);
                hr.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
                hr.setNroOrden("" + i);
                i++;
                homologacionRespuestaRepository.save(hr);
            }
            logger.error("TERMINO FOR RESPUESTAS: ");
            //response = new ResponseEntity(map, HttpStatus.OK);
        //} catch (Exception e) {
            //logger.error(e.getMessage());
            //map = new HashMap<>();
            //map.put("message", e.getMessage());
            //response = new ResponseEntity(HttpStatus.NOT_FOUND);
        //}
        return obj;
    }

    @Override
    public Homologacion update(HomologacionCustom custom) {
        Homologacion obj = new Homologacion();

        Homologacion homologacion = custom.getHomologacion();
//        List<ProveedorHomologacion> ph = proveedorHomologacionRepository.findByHomologacion(homologacion);
//        if (ph != null) {
//           throw new PortalException("No se puede Editar. La pregunta ya es utilizada en la homologación de otro proveedor");
//
//        } else {
//            LineaComercial lineaComercial = lineaComercialReposity.getLineaComercialById(homologacion.getLineaComercial().getIdLineaComercial());
//            homologacion.setLineaComercial(lineaComercial);
//            //map.put("data", homologacionRepository.save(homologacion));
//            obj = homologacionRepository.save(homologacion);
//
//            homologacionRespuestaRepository.deleteHomologacionRespuestaByHomologacion(homologacion.getIdHomologacion());
//
//            List<HomologacionRespuesta> respuestas = custom.getHomologacionRespuestas();
//            int i = 1;
//            for (HomologacionRespuesta hr : respuestas) {
//                hr.setHomologacion(homologacion);
//                hr.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
//                hr.setUsuarioCreacion(1);
//                hr.setUsuarioModificacion(1);
//                hr.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
//                hr.setNroOrden("" + i);
//                i++;
//                homologacionRespuestaRepository.save(hr);
//            }
//
//        }

        LineaComercial lineaComercial = lineaComercialReposity.getLineaComercialById(homologacion.getLineaComercial().getIdLineaComercial());
        homologacion.setLineaComercial(lineaComercial);
        obj = homologacionRepository.save(homologacion);

        homologacionRespuestaRepository.deleteHomologacionRespuestaByHomologacion(homologacion.getIdHomologacion());

        List<HomologacionRespuesta> respuestas = custom.getHomologacionRespuestas();
        int i = 1;
        for (HomologacionRespuesta hr : respuestas) {
            hr.setHomologacion(homologacion);
            hr.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
            hr.setUsuarioCreacion(1);
            hr.setUsuarioModificacion(1);
            hr.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
            hr.setNroOrden("" + i);
            i++;
            homologacionRespuestaRepository.save(hr);
        }

        List<ProveedorHomologacion> ph = proveedorHomologacionRepository.findByHomologacion(homologacion);
        if (ph != null) {
            for(ProveedorHomologacion p: ph) {
                String comunidad = p.getProveedor().getIndProveedorComunidad();
                Boolean bcomunidad = false;
                if (comunidad.equals(Constant.UNO))
                    bcomunidad = true;
                this.evaluarProveedorDto(p.getProveedor().getIdProveedor(),bcomunidad);
            }

        }
        return obj;
    }
    */

    private void evaluarProveedorHomologacion(Proveedor p) throws PortalException {
        if (p.getIndHomologado()!= null && p.getIndHomologado().equals(Constant.UNO) ) {
            homologacionMapper.evaluarProveedor(p);
            proveedorRepository.save(p);
        }
        return;
    }
    @Override
    public void updateHomologacion(Integer idHomologacion) throws PortalException {
        homologacionRepository.updateHomologacion("0",idHomologacion);
    };

    @Override
    public void updateHomologacionActivar(Integer idHomologacion) throws PortalException{
        homologacionRepository.updateHomologacion("1",idHomologacion);
    };
}
