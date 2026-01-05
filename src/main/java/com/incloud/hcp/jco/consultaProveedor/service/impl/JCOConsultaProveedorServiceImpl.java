package com.incloud.hcp.jco.consultaProveedor.service.impl;

import com.incloud.hcp.domain.*;
import com.incloud.hcp.enums.EstadoProveedorEnum;
import com.incloud.hcp.jco.consultaProveedor.dto.ConsultaProveedorRFCParameterBuilder;
import com.incloud.hcp.jco.consultaProveedor.dto.ConsultaProveedorRFCResponseDto;
import com.incloud.hcp.jco.consultaProveedor.service.JCOConsultaProveedorService;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.util.DateUtils;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOConsultaProveedorServiceImpl implements JCOConsultaProveedorService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;
//    private final String FUNCION_RFC = "ZMMRFC_CONSULTA_PROVEEDOR";
    private final String FUNCION_RFC = "ZPE_MM_CONSULTA_PROVEEDOR";
    private final String NOMBRE_TABLA_RFC_GENERAL = "ITAB_DETALL_PROV";
    private final String NOMBRE_TABLA_RFC_CUENTAS="ITAB_LFBK";
    //private final String NOMBRE_TABLA_RFC_ORGCOMPRAS="ITAB_LFM1";
    private final String NOMBRE_TABLA_RFC_CONTACTO ="ITAB_KNVK";

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UbigeoRepository ubigeoRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private CondicionPagoReposity condicionPagoReposity;

    @Autowired
    private EstadoProveedorRepository estadoProveedorRepository;

    @Autowired
    private ProveedorCuentaBancoRepository proveedorCuentaBancoRepository;

    @Autowired
    private TipoProveedorRepository tipoProveedorRepository;

    @Autowired
    private MonedaRepository monedaRepository;

    @Autowired
    private TipoComprobanteRepository tipoComprobanteRepository;

    @Override
    public ConsultaProveedorRFCResponseDto listaProveedorByRFC(String nroAcreedor, String fechaInicio, String fechaFin, String email, String tipoPersona) throws Exception {

        ConsultaProveedorRFCResponseDto proveedorRFCResponseDto = new ConsultaProveedorRFCResponseDto();

        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A listaProveedorByRFC - CONSULTA PROVEEDOR");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01B listaProveedorByRFC - CONSULTA PROVEEDOR");


        logger.error("parametro ingresado");
        ConsultaProveedorRFCParameterBuilder.build(
                jCoFunction,
                nroAcreedor,
                fechaInicio,
                fechaFin
        );
        logger.error("01C listaProveedorByRFC - CONSULTA PROVEEDOR");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca listaProveedorByRFC - CONSULTA PROVEEDOR - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 listaProveedorByRFC - CONSULTA PROVEEDOR - FIN RFC");
        JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
        JCoParameterList result = jCoFunction.getExportParameterList();
        SapLog sapLog = new SapLog();
        String codigoSap = result.getString("PO_CODE");
        String message = result.getString("PO_MSJE");
        sapLog.setCode(codigoSap);
        sapLog.setMesaj(message);
        proveedorRFCResponseDto.setSapLog(sapLog);
        logger.error("02b listaProveedorByRFC - ConsultaProveedor - sapLog: " + sapLog.toString());

        /* Recorriendo valores obtenidos del RFC datos generales */

        List<Proveedor> listaProveedores = new ArrayList<Proveedor>();
        JCoTable table = tableParameterList.getTable(NOMBRE_TABLA_RFC_GENERAL);
        JCoTable tableContacto = tableParameterList.getTable(NOMBRE_TABLA_RFC_CONTACTO);

        logger.error("02b 01 listaProveedorByRFC - ConsultaProveedor - sapLog: " + sapLog.toString());
        if (table != null && !table.isEmpty()) {

            do {
                String acreedorSap=table.getString("LIFNR");
                if(!Optional.ofNullable(this.proveedorRepository.getProveedorByAcreedorCodigoSap(acreedorSap)).isPresent()) {
                    logger.error("02bA listaProveedorByRFC - acreedorSap: " + acreedorSap);
                    Proveedor proveedor = new Proveedor();
                    proveedor.setAcreedorCodigoSap(acreedorSap);
                    proveedor.setRazonSocial(table.getString("NAME1") + table.getString("NAME2"));
                    proveedor.setDireccionFiscal(table.getString("STRAS"));
                    proveedor.setRuc(table.getString("STCD1"));
                    //proveedor.setn
                    proveedor.setTelefonoMovil(table.getString("TEL_MOV") );//Telefono movil
                    proveedor.setCelular(table.getString("TEL_MOV"));//celular
                    //Campos de datos de contacto
                    if (tableContacto != null && !tableContacto.isEmpty()) {
                        for(int j = 0; j < tableContacto.getNumRows(); j++) {
                            tableContacto.setRow(j);
                            if (tableContacto.getString("ABTNR") != null &&  tableContacto.getString("ABTNR").equalsIgnoreCase("0010") &&
                                    tableContacto.getString("PAFKT") != null &&  tableContacto.getString("PAFKT").equalsIgnoreCase("51") ) {
                                proveedor.setNombreRepresentanteLegal(tableContacto.getString("NAME1") + " " + tableContacto.getString("NAMEV"));
                                //proveedor.setCargoRepresentanteLegal();
                                proveedor.setNroDocumRepresentanteLegal(tableContacto.getString("SORTL"));
                                proveedor.setEmailRepresentanteLegal(tableContacto.getString("SMTP_ADDR"));
                            }

                            if (tableContacto.getString("ABTNR") != null &&  tableContacto.getString("ABTNR").equalsIgnoreCase("0009") &&
                                    tableContacto.getString("PAFKT") != null &&  tableContacto.getString("PAFKT").equalsIgnoreCase("44") ) {
                                proveedor.setNombrePersonaCreditoCobranza(tableContacto.getString("NAME1") + " " + tableContacto.getString("NAMEV"));
                                //proveedor.setCargoPersonaCreditoCobranza();
                                proveedor.setNroDocumPersonaCreditoCobranza(tableContacto.getString("SORTL"));
                                proveedor.setEmailPersonaCreditoCobranza(tableContacto.getString("SMTP_ADDR"));
                            }

                        }
                        //tableContacto.setr
                    }

//                    String tipoPersona = table.getString("FITYP").toUpperCase();
//                    if (tipoPersona != "") {
//                        proveedor.setTipoPersona(tipoPersona.substring(1));
//                    }
                    if (tipoPersona != "" && tipoPersona != null) {
                        proveedor.setTipoPersona(tipoPersona);
                    } else {
                        proveedor.setTipoPersona("J");
                    }

                    String clavePaisErp = table.getString("LAND1");
                    logger.error("PAIS" + clavePaisErp);
                    Ubigeo pais = this.ubigeoRepository.findByCodigoUbigeoSapErpAndNivel(clavePaisErp, 0);


                    if (Optional.ofNullable(pais).isPresent()) {
                        proveedor.setPais(pais);
                        String claveRegionErp = table.getString("REGION");
                        Ubigeo region = this.ubigeoRepository.findByCodigoUbigeoSapErpAndNivelAndIdPadre(claveRegionErp, 1, pais.getIdUbigeo());
                        if (Optional.ofNullable(region).isPresent()) {
                            proveedor.setRegion(region);
                            String clavePoblacionErp = table.getString("CITY_CODE");
                            logger.error(clavePoblacionErp);
                            Ubigeo poblacion=null;
                            if(clavePoblacionErp!=null){
                                poblacion = this.ubigeoRepository.findByCodigoUbigeoSapErpAndNivelAndIdPadre(clavePoblacionErp, 2, region.getIdUbigeo());
                                if (Optional.ofNullable(poblacion).isPresent()) {
                                    proveedor.setProvincia(poblacion);
                                    logger.error("03 listaProveedorByRFC ConsultaProveedorRFCResponseDto: entrando distrito code" );
                                    String claveDistritoErp = table.getString("CITYP_CODE");
                                    logger.error("04 listaProveedorByRFC ConsultaProveedorRFCResponseDto:  distrito code :: " +  claveDistritoErp );
                                    //claveDistritoErp=claveDistritoErp.substring(2,8);

                                    if (StringUtils.isNotBlank(claveDistritoErp)){
                                    //if(claveDistritoErp!=null){
                                        Long aux = Long.parseLong(claveDistritoErp);
                                        claveDistritoErp = aux.longValue() + "";
                                        logger.error("05 listaProveedorByRFC ConsultaProveedorRFCResponseDto:  distrito code2 :: " );
                                        Ubigeo distrito = this.ubigeoRepository.findByCodigoUbigeoSapErpAndNivelAndIdPadre(claveDistritoErp, 3, poblacion.getIdUbigeo());
                                        logger.error("ConsultaProveedorRFCResponseDto:  distrito code3 :: "  + distrito);
                                        if (Optional.ofNullable(distrito).isPresent()) {
                                            logger.error("06 listaProveedorByRFC ConsultaProveedorRFCResponseDto:  distrito entro_2 :: " );
                                            proveedor.setDistrito(distrito);
                                        }
                                    }
                                }
                            }
                        }


                    }
                    //proveedor.setDistrito(table.getString("CITY2"));//PPO

                    proveedor.setTelefono(table.getString("TELNR_LONG"));
                    proveedor.setCodigoPostal(table.getString("PSTLZ"));
                    String tipoProveedorErp = table.getString("KTOKK");
                    TipoProveedor tipoProveedor = this.tipoProveedorRepository.findByCodigoSap(tipoProveedorErp);
                    if (Optional.ofNullable(tipoProveedor).isPresent()) {
                        proveedor.setTipoProveedor(tipoProveedor);
                    } else {
                        continue;
                    }

                    logger.error("07 listaProveedorByRFC TiPO PROVEEDOR " + tipoProveedorErp);


                    proveedor.setDireccionFiscal(table.getString("STREET") + table.getString("STR_SUPPL1") + table.getString("STR_SUPPL2") + table.getString("STR_SUPPL3"));

                    //Correo enviada del Excel validar
                    if(StringUtils.isBlank(email)){
                        logger.error("<--MC_LOG-->:JCOConsultaProveedorServiceImpl:");
                        logger.error("<--MC_LOG-->:JCOConsultaProveedorServiceImpl-SMTP_ADDR:"+table.getString("SMTP_ADDR"));
                        proveedor.setEmail(table.getString("SMTP_ADDR"));
                    }else{
                        logger.error("<--MC_LOG-->:JCOConsultaProveedorServiceImpl:");
                        logger.error("<--MC_LOG-->:JCOConsultaProveedorServiceImpl-isNotBlanck:"+email);
                        proveedor.setEmail(email);
                    }
//                    if (StringUtils.isBlank(table.getString("SMTP_ADDR"))) {
//                        proveedor.setEmail(email);
//                    }else {
//                        proveedor.setEmail(table.getString("SMTP_ADDR"));
//                    }



                    ////Datos Compras

                    proveedor.setCodigoGrupoCompra(table.getString("EKGRP"));
                    String condPago = table.getString("ZTERM");
                    CondicionPago condicionPago = this.condicionPagoReposity.findByCodigoSapOrderByIdCondicionPago(condPago);

                    if (Optional.ofNullable(condicionPago).isPresent()) {
                        proveedor.setCondicionPago(condicionPago);
                    }
                    logger.error("08 listaProveedorByRFC CONDICION PAGO " + condPago);
                    String monedaFactErp = table.getString("WAERS");
                    Moneda monedaFact = this.monedaRepository.getByCodigoMoneda(monedaFactErp);

                    if (Optional.ofNullable(monedaFact).isPresent()) {
                        proveedor.setMoneda(monedaFact);
                    }
                    proveedor.setCodigoGrupoTesoreria(table.getString("FDGRV"));
                    logger.error("09 listaProveedorByRFC MONEDA" + monedaFact.toString());


                    String cuentaAsociada = table.getString("AKONT");
                    String tipoComprobanteErp = "FA"; // cliente solo usa factura
//                    if (cuentaAsociada.equalsIgnoreCase("0042120001")) {
//                        tipoComprobanteErp = "FA";
//                    } else if (cuentaAsociada.equalsIgnoreCase("0042400001")) {
//                        tipoComprobanteErp = "RH";
//                    }
                    TipoComprobante tipoComprobante = this.tipoComprobanteRepository.findByCodigoTipoComprobante(tipoComprobanteErp);
                    logger.error("10 listaProveedorByRFC TIPOCOMPROBANTEERP " + cuentaAsociada);

                    if (Optional.ofNullable(tipoComprobante).isPresent()) {
                        proveedor.setTipoComprobante(tipoComprobante);
                    }
                    EstadoProveedor estadoProveedor = this.estadoProveedorRepository.
                            getByCodigoEstadoProveedor(EstadoProveedorEnum.MIGRADO_DE_SAP.getCodigo());

                    proveedor.setIdEstadoProveedor(estadoProveedor);
                    proveedor.setIndMigradoSap("1");
                    String fechaCreacion=table.getString("ERDAT");
                    if(StringUtils.isNotBlank(fechaCreacion)){
                        proveedor.setFechaCreacion(DateUtils.convertStringToDate("yyyyMMdd",fechaCreacion));
                    }

                    proveedor.setIndProveedorComunidad("0");
                    proveedor.setEvaluacionDesempeno(new BigDecimal(0));
                    proveedor.setEvaluacionHomologacion(new BigDecimal(0));
                    proveedor.setIndBlackList("0");
                    proveedor.setIndBloqueadoSap("0");
                    proveedor.setIndHomologado("0");
                    proveedor.setIndSujetoRetencion("0");
                    proveedor.setUsuarioCreacion(0);
                    proveedor.setActivo("1");
                    listaProveedores.add(proveedor);

                    logger.error("11 listaProveedorByRFC proveedor " + proveedor.toString());
                    logger.error("<--MC_LOG-->:JCOConsultaProveedorServiceImpl2-PreSave:"+proveedor.getEmail());
                    this.proveedorRepository.saveAndFlush(proveedor);
                    logger.error("12 listaProVeedorByRFC proveedor OK Save " + proveedor.getRuc() + " - " + proveedor.getRazonSocial());

                }
            } while (table.nextRow());
        }
        logger.error("FIN");

        //this.proveedorRepository.saveAll(listaProveedores);
        List<ProveedorCuentaBancaria> listaProveedorCuentaBancaria = new ArrayList<ProveedorCuentaBancaria>();
        JCoTable tableCuentaBancariaProveedor = tableParameterList.getTable(NOMBRE_TABLA_RFC_CUENTAS);

        logger.error("12 listaProveedorByRFC CUENTA BANCARIA ");
        logger.error("12b listaProveedorByRFC 01 - ConsultaProveedor - sapLog: " + sapLog.toString());
        if (tableCuentaBancariaProveedor != null && !tableCuentaBancariaProveedor.isEmpty()) {

            do {
                String codigoMoneda=tableCuentaBancariaProveedor.getString("BVTYP");
                if (codigoMoneda == null || codigoMoneda.isEmpty()) // si la cuenta bancaria no tienen moneda entonces no se tomara en cuenta
                    continue;
                ///Encontrar Proveedor
                String codigoProveedor =tableCuentaBancariaProveedor.getString("LIFNR");
                Proveedor proveedorEncontrado=this.proveedorRepository.getProveedorByAcreedorCodigoSap(codigoProveedor);
                if (Optional.ofNullable(proveedorEncontrado).isPresent()) {
//                    List<ProveedorCuentaBancaria> listaproveedorCuentaBancaria=this.proveedorCuentaBancoRepository.getListCuentaBancariaByIdProveedor(proveedorEncontrado.getIdProveedor());
//                    if(listaproveedorCuentaBancaria.size()==0) {

                    String claveBanco = tableCuentaBancariaProveedor.getString("BANKL");
                    String numeroCuenta = tableCuentaBancariaProveedor.getString("BANKN");

                    Optional<ProveedorCuentaBancaria> opProveedorCuentaBancaria
                            = proveedorCuentaBancoRepository.getProveedorCuentaBancariaByIdProveedorAndClaveBancoAndNumeroCuenta(proveedorEncontrado.getIdProveedor(), claveBanco, numeroCuenta);

                    ProveedorCuentaBancaria proveedorCuentaBancaria = new ProveedorCuentaBancaria();
                    if(opProveedorCuentaBancaria.isPresent()){
                        proveedorCuentaBancaria = opProveedorCuentaBancaria.get();
                    }
                    else{
                        proveedorCuentaBancaria.setNumeroCuenta(numeroCuenta);
                    }

                    proveedorCuentaBancaria.setClaveControlBanco(tableCuentaBancariaProveedor.getString("BKONT"));
//                    proveedorCuentaBancaria.setNumeroCuentaCci(tableCuentaBancariaProveedor.getString("BKREF"));
//                    proveedorCuentaBancaria.setContacto(tableCuentaBancariaProveedor.getString("KOINH"));
                    proveedorCuentaBancaria.setNumeroCuentaCci(tableCuentaBancariaProveedor.getString("KOINH"));


                    ///Encontrar Banco

//                    String codigoMoneda=claveBanco.substring(3);
                    logger.error("13.0 listaProveedorByRFC codigo moneda original SAP: " + codigoMoneda);
                    if(codigoMoneda.length() > 3) {
                        codigoMoneda = codigoMoneda.substring(0, 3);
                        logger.error("13.1 listaProveedorByRFC codigo moneda trimmed: " + codigoMoneda);
                    }
//                    Moneda monedaCuenta=null;
//                    if(codigoMoneda.equalsIgnoreCase("S")){
//                        monedaCuenta=this.monedaRepository.getByCodigoMoneda("PEN");
//                    }else{
//                        monedaCuenta=this.monedaRepository.getByCodigoMoneda("USD");
//                    }
                    Moneda monedaCuenta=this.monedaRepository.getByCodigoMoneda(codigoMoneda);

                    Banco bancoEncontrado = this.bancoRepository.getByClaveBanco(claveBanco);
                    if (Optional.ofNullable(bancoEncontrado).isPresent()) {
                        proveedorCuentaBancaria.setBanco(bancoEncontrado);
                        proveedorCuentaBancaria.setMoneda(monedaCuenta);

                        logger.error("13.5 listaProveedorByRFC MONEDA CUENTA BANCARIA: " + (monedaCuenta != null ? monedaCuenta.toString() : "--"));

                        proveedorCuentaBancaria.setProveedor(proveedorEncontrado);
                        listaProveedorCuentaBancaria.add(proveedorCuentaBancaria);

                        logger.error("14 listaProveedorByRFC proveedorCuentaBancaria " + proveedorCuentaBancaria.toString());
                        proveedorCuentaBancaria = this.proveedorCuentaBancoRepository.saveAndFlush(proveedorCuentaBancaria);

                        logger.error("15 listaProVeedorByRFC proveedorCuentaBancaria OK Save " + proveedorCuentaBancaria.toString());
                    }
//                    }
                }
            } while (tableCuentaBancariaProveedor.nextRow());
        }

        //this.proveedorCuentaBancoRepository.saveAll(listaProveedorCuentaBancaria);
        proveedorRFCResponseDto.setDatosGenerales(listaProveedores);
        proveedorRFCResponseDto.setListaCuentaBancaria(listaProveedorCuentaBancaria);
        return proveedorRFCResponseDto;
    }
}
