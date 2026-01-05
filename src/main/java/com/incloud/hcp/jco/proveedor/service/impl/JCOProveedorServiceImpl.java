package com.incloud.hcp.jco.proveedor.service.impl;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.ProveedorCuentaBancaria;
import com.incloud.hcp.exception.ServiceException;
import com.incloud.hcp.jco.proveedor.dto.ProveedorRFCParameterBuilder;
import com.incloud.hcp.jco.proveedor.dto.ProveedorRFCResponseDto;
import com.incloud.hcp.jco.proveedor.dto.ProveedorResponseRFC;
import com.incloud.hcp.jco.proveedor.service.JCOProveedorService;
import com.incloud.hcp.repository.ProveedorCuentaBancoRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.util.StrUtils;
import com.incloud.hcp.util.constant.WebServiceConstant;
import com.sap.conn.jco.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOProveedorServiceImpl implements JCOProveedorService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;
//    private final String FUNCION_RFC = "ZMM_CREA_PROVEEDOR";
    private final String FUNCION_RFC = "ZPE_MM_CREA_PROVEEDOR";
    private final String NOMBRE_TABLA_RFC = "OT_MENSAJES";

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorCuentaBancoRepository proveedorCuentaBancoRepository;

    @Override
    public ProveedorRFCResponseDto grabarProveedor(Integer idProveedor,String usuarioSap) throws Exception {
        ProveedorRFCResponseDto proveedorRFCResponseDto = new ProveedorRFCResponseDto();


        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - GRABAR PROVEEDOR");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01B - GET PROVEEDOR");

        ///Obtener Bean Proveedor
        Proveedor proveedorParam = this.proveedorRepository.getProveedorByIdProveedor(idProveedor);
        List<ProveedorCuentaBancaria> listaCuentaBancaria= this.
                proveedorCuentaBancoRepository.getListCuentaBancariaByIdProveedor(idProveedor);

        logger.error("parametro ingresado Proveedor" + proveedorParam.toString());
        logger.error("parametro ingresado Cuenta Bancaria" + listaCuentaBancaria.toString());
        ProveedorRFCParameterBuilder.build(
                jCoFunction,
                proveedorParam,
                listaCuentaBancaria,
                usuarioSap
        );
        logger.error("01C - GET PROVEEDOR");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - PROVEEDOR - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 - GET PROVEEDOR - FIN RFC");
        JCoParameterList result = jCoFunction.getExportParameterList();

        List<SapLog> listSapLog = new ArrayList<>();
        String acreedorSap = result.getString("O_COD_PROVEEDOR");

        logger.error("02b - GET PROVEEDOR - ACREEDOR SAP: " + acreedorSap);
        /* Recorriendo valores obtenidos del RFC */
        JCoTable table = result.getTable(NOMBRE_TABLA_RFC);
        if (table != null && !table.isEmpty()) {
            do {

                SapLog sapLog = new SapLog();
                sapLog.setCode(table.getString("NUMERO"));
                sapLog.setMesaj(table.getString("TEXTO"));
                sapLog.setClase(table.getString("CLASE"));
                sapLog.setTipo(table.getString("TIPO"));
                listSapLog.add(sapLog);
            } while (table.nextRow());
        }
        proveedorRFCResponseDto.setNroAcreedor(acreedorSap);
        proveedorRFCResponseDto.setListasapLog(listSapLog);

        return proveedorRFCResponseDto;
    }

    @Override
    public ProveedorResponseRFC grabarListaProveedorSAP(List<Proveedor> listaProveedorPotencial,String usuarioSap) throws Exception {

        ProveedorResponseRFC beanRetorno = new ProveedorResponseRFC();
        beanRetorno.setTieneError(false);
        SapLog sapLogRetorno = new SapLog();
        List<ProveedorRFCResponseDto> listaProveedorSAPResult = new ArrayList<ProveedorRFCResponseDto>();

        /////GRABAR
        sapLogRetorno.setCode(WebServiceConstant.RESPUESTA_OK);
        sapLogRetorno.setMesaj(WebServiceConstant.MENSAJE_VACIO);
        for (Proveedor beanProveedor : listaProveedorPotencial) {

            Optional<String> oCodigoSap = Optional.ofNullable(beanProveedor.getAcreedorCodigoSap())
                    .filter(codigo -> !codigo.isEmpty());
            if (!oCodigoSap.isPresent()) {
                logger.error("INGRESANDO A CREAR");
                ProveedorRFCResponseDto proveedorRFCResponseDtoResult = new ProveedorRFCResponseDto();
                try {
                    logger.error("<--MC_LOG-->:JCOProveedorServiceImpl-grabarListaProveedorSAP-beanProveedor:"+beanProveedor.getEmail());
                    proveedorRFCResponseDtoResult = this.grabarProveedor(beanProveedor.getIdProveedor(),usuarioSap);
                    logger.error("PROVEEDOR ACREEDOR"+proveedorRFCResponseDtoResult.getNroAcreedor());

                    /* Respuesta SAP */
                    if (StringUtils.isNotBlank(proveedorRFCResponseDtoResult.getNroAcreedor())) {
                        logger.error("CREANDO");
                        beanProveedor.setAcreedorCodigoSap(proveedorRFCResponseDtoResult.getNroAcreedor());
                          this.proveedorRepository.updateAcreedorCodigoSAP(
                                    proveedorRFCResponseDtoResult.getNroAcreedor(),
                                   beanProveedor.getIdProveedor());
                        logger.error("GrabarSAP PROVEEDOR EXITO CODIGO SAP: " + proveedorRFCResponseDtoResult.getNroAcreedor());
                    } else {
                        beanRetorno.setTieneError(true);
                        logger.error("GrabarSAP PROVEEDOR ERROR: "  );
                    }
                } catch (Exception e) {
                    logger.error("Ingresando grabarProveedorSAP  error: " );
                    logger.error("GrabarSAP PROVEEDOR Exception: " + e.getMessage());
                    beanRetorno.setTieneError(true);
                }
                logger.error("BEAN PROVEEDOR SAP" + beanProveedor.getAcreedorCodigoSap());
                proveedorRFCResponseDtoResult.setProveedorSap(beanProveedor);
                listaProveedorSAPResult.add(proveedorRFCResponseDtoResult);
            }
        }
        beanRetorno.setSapLog(sapLogRetorno);
        beanRetorno.setListaProveedorSAPResult(listaProveedorSAPResult);
        logger.error("Ingresando grabarProveedorSAP DEV FIN: ");
        return beanRetorno;
    }

    @Override
    public ProveedorRFCResponseDto grabarUnicoProveedorSAP(Proveedor proveedorPotencial,String usuarioSap) throws Exception {
        ProveedorRFCResponseDto proveedorRFCResponseDtoResult = new ProveedorRFCResponseDto();
        List<SapLog> sapLogList = new ArrayList<>();
        Optional<String> oCodigoSap = Optional.ofNullable(proveedorPotencial.getAcreedorCodigoSap())
                .filter(codigo -> !codigo.isEmpty());

        if (!oCodigoSap.isPresent()) {
            logger.error("INGRESANDO A CREAR");
            try {
                logger.error("<--MC_LOG-->:JCOProveedorServiceImpl-grabarUnicoProveedorSAP-proveedorPotencial:"+proveedorPotencial.getEmail());
                proveedorRFCResponseDtoResult = this.grabarProveedor(proveedorPotencial.getIdProveedor(),usuarioSap);
                String numeroAcreedor = Optional.ofNullable(proveedorRFCResponseDtoResult.getNroAcreedor()).orElse("");

                logger.error("PROVEEDOR ACREEDOR: " + (!numeroAcreedor.isEmpty() ? numeroAcreedor : "--"));

                    /* Respuesta SAP */
                if (StringUtils.isNotBlank(numeroAcreedor)) {
                    logger.error("ACTUALIZANDO");
                    proveedorPotencial.setAcreedorCodigoSap(numeroAcreedor);
                    this.proveedorRepository.updateAcreedorCodigoSAP(
                            proveedorRFCResponseDtoResult.getNroAcreedor(),
                            proveedorPotencial.getIdProveedor());
                    logger.error("GrabarSAP PROVEEDOR EXITO CODIGO SAP: " + numeroAcreedor);
                }
                else {
                    logger.error("GrabarSAP PROVEEDOR ERROR");
                }

                sapLogList = proveedorRFCResponseDtoResult.getListasapLog();
                logger.error("GrabarSAP LISTA RESPUESTA RFC: " + (sapLogList != null ? sapLogList.toString() : "--"));
            } catch (Exception e) {
                String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
                logger.error("GrabarSAP PROVEEDOR Exception: " + error);
                throw new Exception(error);
            }
            logger.error("BEAN PROVEEDOR SAP" + proveedorPotencial.getAcreedorCodigoSap());
//            proveedorRFCResponseDtoResult.setProveedorSap(proveedorPotencial);
        }
        else{
            String errorMessage = "Proveedor con RUC '" + proveedorPotencial.getRuc() + "' ya tiene numeroAcreedor: " + proveedorPotencial.getAcreedorCodigoSap();
            logger.error("ERROR PARA CREAR PROVEEDOR SAP: " + errorMessage);
            SapLog errorLog = new SapLog("ERROR", errorMessage);
            sapLogList.add(errorLog);
            proveedorRFCResponseDtoResult.setListasapLog(sapLogList);
        }

        return proveedorRFCResponseDtoResult;
    }
}
