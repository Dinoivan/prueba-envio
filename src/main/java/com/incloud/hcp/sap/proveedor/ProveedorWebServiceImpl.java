package com.incloud.hcp.sap.proveedor;

import com.incloud.hcp.domain.Parametro;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.repository.ParametroRepository;
import com.incloud.hcp.repository.ProveedorCuentaBancoRepository;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.service.ParametroService;
import com.incloud.hcp.util.constant.WebServiceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 27/09/2017.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProveedorWebServiceImpl implements ProveedorWebService {

    private static Logger logger = LoggerFactory.getLogger(ProveedorWebServiceImpl.class);

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    ProveedorCuentaBancoRepository proveedorCuentaBancoRepository;

    @Autowired
    private ParametroService parametroService;

    public ProveedorResponse grabarProveedorSAP(List<Proveedor> listaProveedorPotencial) {
        logger.error("Ingresando grabarProveedorSAP - DEV");

        ProveedorResponse beanRetorno = new ProveedorResponse();
        beanRetorno.setTieneError(false);
        SapLog sapLogRetorno = new SapLog();
        List<ProveedorBeanSAP> listaProveedorSAPResult = new ArrayList<ProveedorBeanSAP>();
        logger.error("Ingresando grabarProveedorSAP DEV 01");

        /*SapSetting sapSetting = parametroService.getSapSetting();
        logger.error("Ingresando grabarProveedorSAP DEV 02 - sapSetting: " + sapSetting.toString());
        if (sapSetting == null) {
            String msj = "Error al cargar los parametros de configuración";
            sapLogRetorno.setCode("-1");
            sapLogRetorno.setMesaj(msj);
            beanRetorno.setSapLog(sapLogRetorno);
            beanRetorno.setTieneError(true);
            logger.error(msj);
            return beanRetorno;
        }

        logger.error("PROVEEDOR wsdlParam.getValor(): " + sapSetting.getWsdlProveedor());
        String urlWsdl = "";
        try {
            urlWsdl = ProveedorWebService.class.getClassLoader().getResource(sapSetting.getWsdlProveedor()).toString();
            logger.error("Ingresando grabarProveedorSAP DEV 03 - urlWsdl: " + urlWsdl);
        } catch (Exception e) {
            String msj = "Error al cargar el archivo WSDL";
            logger.error("Ingresando grabarProveedorSAP DEV 03 - error: " );
            e.printStackTrace();
            sapLogRetorno.setCode("-2");
            sapLogRetorno.setMesaj(msj);
            beanRetorno.setSapLog(sapLogRetorno);
            beanRetorno.setTieneError(true);
            logger.error(msj, e);
            return beanRetorno;
        }

        DTP028MaestroProveedoresRes response = new DTP028MaestroProveedoresRes();
        SIP028MaestroProveedoresSyncReqOut port = portInit(urlWsdl);
        logger.error("Ingresando grabarProveedorSAP DEV 04 - port: " + port);

        //BASIC authenticaction
        Map requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.USERNAME_PROPERTY, sapSetting.getUser());
        requestContext.put(BindingProvider.PASSWORD_PROPERTY, sapSetting.getPassword());

        *//* Grabando informacion *//*

        sapLogRetorno.setCode(WebServiceConstant.RESPUESTA_OK);
        sapLogRetorno.setMesaj(WebServiceConstant.MENSAJE_VACIO);
        logger.error("Ingresando grabarProveedorSAP DEV 05 ");
        for (Proveedor beanProveedor : listaProveedorPotencial) {
            DTP028MaestroProveedoresReq objRequest = new DTP028MaestroProveedoresReq();
            DTP028MaestroProveedoresReq.MODO modo = new DTP028MaestroProveedoresReq.MODO();

            DTP028MaestroProveedoresReq.GENERALES generales = new DTP028MaestroProveedoresReq.GENERALES();

            Optional<String> oCodigoSap = Optional.ofNullable(beanProveedor.getAcreedorCodigoSap())
                    .filter(codigo -> !codigo.isEmpty());
            if (oCodigoSap.isPresent()) {
                modo.setTPROC(WebServiceConstant.MODIFICAR);
                generales.setLIFNR(beanProveedor.getAcreedorCodigoSap());
            } else {
                modo.setTPROC(WebServiceConstant.CREAR);
            }
            objRequest.setMODO(modo);

            *//* Datos Generales *//*
            String razonSocial = beanProveedor.getRazonSocial();
            if (razonSocial.length() <= 35) {
                generales.setNAME1(razonSocial);
                generales.setNAME2("");
                generales.setNAME3("");
            } else {
                if (razonSocial.length() > 35 && razonSocial.length() <= 70) {
                    String razonSocial01 = razonSocial.substring(0, 35);
                    String razonSocial02 = razonSocial.substring(35, razonSocial.length());
                    generales.setNAME1(razonSocial01);
                    generales.setNAME2(razonSocial02);
                } else {
                    String razonSocial01 = razonSocial.substring(0, 35);
                    String razonSocial02 = razonSocial.substring(35, 70);
                    String razonSocial03 = razonSocial.substring(70, razonSocial.length());
                    generales.setNAME1(razonSocial01);
                    generales.setNAME2(razonSocial02);
                    generales.setNAME2(razonSocial03);
                }
            }
            Optional.ofNullable(beanProveedor.getRuc())
                    .map(r -> r.substring(0, 10))
                    .ifPresent(generales::setSORTL);

            Optional.ofNullable(beanProveedor.getPais())
                    .map(p -> p.getCodigoUbigeoSap())
                    .map(c -> c.trim())
                    .ifPresent(generales::setLAND1);
            Optional.ofNullable(beanProveedor.getRegion())
                    .map(p -> p.getCodigoUbigeoSap())
                    .map(c -> c.trim())
                    .ifPresent(generales::setREGIO);
            Optional.ofNullable(beanProveedor.getProvincia())
                    .map(p -> p.getDescripcion())
                    .map(c -> c.trim())
                    .ifPresent(generales::setORT01);
            Optional.ofNullable(beanProveedor.getDistrito())
                    .map(p -> p.getDescripcion())
                    .map(c -> c.trim())
                    .ifPresent(generales::setORT02);
            if (Optional.ofNullable(beanProveedor.getDireccionFiscal()).isPresent()) {
                int longitud = beanProveedor.getDireccionFiscal().length();
                if (longitud > 35) {
                    String direccion = StringUtils.left(beanProveedor.getDireccionFiscal(), 35);
                    beanProveedor.setDireccionFiscal(direccion);
                }
            }
            generales.setSTRAS(beanProveedor.getDireccionFiscal());
            generales.setSTCD1(beanProveedor.getRuc());
            Optional.ofNullable(beanProveedor.getTipoProveedor())
                    .map(tp -> tp.getCodigoSap())
                    .map(c -> c.trim())
                    .ifPresent(generales::setKTOKK);
            generales.setSTCDT(WebServiceConstant.TIPO_RUC);
            generales.setTELF1(beanProveedor.getTelefono());
            Optional.ofNullable(beanProveedor.getPais())
                    .map(p -> p.getCodigoUbigeoSap())
                    .map(codigo -> codigo.trim())
                    .ifPresent(codigo -> {
                        switch (codigo) {
                            case "PE":
                                generales.setPSTLZ(beanProveedor.getDistrito().getCodigoUbigeoSap());
                                break;
                            case "US":
                                generales.setPSTLZ("00000");
                                break;
                            default:
                                generales.setPSTLZ("0000");
                        }
                    });

            objRequest.setGENERALES(generales);


            *//* Datos de Sociedad *//*
            DTP028MaestroProveedoresReq.SOCIEDAD sociedad = new DTP028MaestroProveedoresReq.SOCIEDAD();
            Optional.ofNullable(beanProveedor.getCondicionPago())
                    .map(cp -> cp.getCodigoSap())
                    .map(c -> c.trim())
                    .ifPresent(sociedad::setZTERM);
            *//**
             * 1 => Facturas (4212000001)
             * 2 => 4ta Recibo por honorario (4240000001)
             *
             * Esta información es evaluada en SAP
             *//*
            Optional.ofNullable(beanProveedor.getTipoComprobante())
                    .map(tc -> tc.getIdTipoComprobante())
                    .map(id -> id.toString())
                    .map(id -> id.trim())
                    .ifPresent(sociedad::setAKONT);
            objRequest.setSOCIEDAD(sociedad);

            *//* Datos de Compra *//*
            DTP028MaestroProveedoresReq.COMPRAS compras = new DTP028MaestroProveedoresReq.COMPRAS();
            Optional.ofNullable(beanProveedor.getMoneda())
                    .map(m -> m.getCodigoMoneda())
                    .map(c -> c.trim())
                    .ifPresent(compras::setWAERS);
            objRequest.setCOMPRAS(compras);

            *//* Datos de Email *//*
            List<DTP028MaestroProveedoresReq.EMAIL> listaEmail = new ArrayList<DTP028MaestroProveedoresReq.EMAIL>();

            DTP028MaestroProveedoresReq.EMAIL
                    email = new DTP028MaestroProveedoresReq.EMAIL();
            email.setSMTPADDR(beanProveedor.getEmail());
            email.setREMARK("ECRE");
            listaEmail.add(email);

            email = new DTP028MaestroProveedoresReq.EMAIL();
            email.setSMTPADDR(beanProveedor.getEmail());
            email.setREMARK("PRINC");
            listaEmail.add(email);

            objRequest.setEMAIL(listaEmail);

            *//* DATOS DE RETENCIÓN *//*
            Optional.ofNullable(objRequest.getGENERALES())
                    .map(general -> general.getLAND1())
                    .ifPresent(codigoPais -> {
                        List<DTP028MaestroProveedoresReq.RETENCION> listRetencion = new ArrayList<>();
                        if (codigoPais.equals("PE")) {
                            *//* Proveedor Nacional *//*
                            if (beanProveedor.getTipoComprobante().getIdTipoComprobante() == 1) {
                                *//** Factura *//*
                                DTP028MaestroProveedoresReq.RETENCION
                                        retencion = new DTP028MaestroProveedoresReq.RETENCION();
                                retencion.setWITHT("DE");
                                retencion.setWTWITHCD("X");
                                listRetencion.add(retencion);

                                retencion = new DTP028MaestroProveedoresReq.RETENCION();
                                retencion.setWITHT("FG");
                                retencion.setWTWITHCD("X");
                                listRetencion.add(retencion);

                                retencion = new DTP028MaestroProveedoresReq.RETENCION();
                                retencion.setWITHT("RP");
                                retencion.setWTWITHCD("X");
                                listRetencion.add(retencion);
                            } else {
                                *//* Recibo por honorario *//*
                                DTP028MaestroProveedoresReq.RETENCION
                                        retencion = new DTP028MaestroProveedoresReq.RETENCION();
                                retencion.setWITHT("RE");
                                retencion.setWTWITHCD("X");
                                listRetencion.add(retencion);
                            }
                        } else {
                            *//* Proveedor extranjero *//*
                            DTP028MaestroProveedoresReq.RETENCION
                                    retencion = new DTP028MaestroProveedoresReq.RETENCION();
                            retencion.setWITHT("RX");
                            retencion.setWTWITHCD("X");
                            listRetencion.add(retencion);
                        }
                        objRequest.setRETENCION(listRetencion);
                    });

            *//* Datos Cuenta Bancaria *//*
            List<ProveedorCuentaBancaria> listaProveedorCuentaBancaria = this.proveedorCuentaBancoRepository
                    .getListCuentaBancariaByIdProveedor(beanProveedor.getIdProveedor());

            DTP028MaestroProveedoresReq.BANCO bancos = new DTP028MaestroProveedoresReq.BANCO();
            List<DTP028MaestroProveedoresReq.BANCO.BANCOS> listaBancos = new ArrayList<DTP028MaestroProveedoresReq.BANCO.BANCOS>();

            Optional.ofNullable(listaProveedorCuentaBancaria)
                    .filter(list -> !list.isEmpty())
                    .ifPresent(list -> {
                        list.stream().map(cb -> {
                            DTP028MaestroProveedoresReq.BANCO.BANCOS banco = new DTP028MaestroProveedoresReq.BANCO.BANCOS();
                            banco.setBANKS(beanProveedor.getPais().getCodigoUbigeoSap());
                            banco.setBANKL(cb.getBanco().getClaveBanco());
                            banco.setBANKN(cb.getNumeroCuenta());
                            banco.setBKONT(cb.getClaveControlBanco());
                            banco.setBKREF(cb.getNumeroCuentaCci());
                            return banco;
                        }).forEach(listaBancos::add);
                        bancos.setBANCOS(listaBancos);
                        objRequest.setBANCO(bancos);
                    });

            *//* Grabando en SAP *//*
            logger.error("Ingresando grabarProveedorSAP DEV 06 ");
            logger.error("PROVEEDOR objRequest VALORES: " + objRequest.toString());
            if (objRequest.getGENERALES() != null)
                logger.error("PROVEEDOR objRequest.GENERALES VALORES: " + objRequest.getGENERALES().toString());
            if (objRequest.getMODO() != null)
                logger.error("PROVEEDOR objRequest.MODO VALORES: " + objRequest.getMODO().toString());
            if (objRequest.getSOCIEDAD() != null)
                logger.error("PROVEEDOR objRequest.SOCIEDAD VALORES: " + objRequest.getSOCIEDAD().toString());
            if (objRequest.getBANCO() != null)
                logger.error("PROVEEDOR objRequest.BANCO VALORES: " + objRequest.getBANCO().getBANCOS().toString());
            if (objRequest.getCOMPRAS() != null)
                logger.error("PROVEEDOR objRequest.COMPRAS VALORES: " + objRequest.getCOMPRAS().toString());
            if (objRequest.getEMAIL() != null)
                logger.error("PROVEEDOR objRequest.MAIL VALORES: " + objRequest.getEMAIL().toString());
            if (objRequest.getRETENCION() != null)
                logger.error("PROVEEDOR objRequest.RETENCION VALORES: " + objRequest.getRETENCION().toString());
            try {
                logger.error("Ingresando grabarProveedorSAP DEV 07 ");
                response = port.siP028MaestroProveedoresSyncReqOut(objRequest);
                logger.error("Ingresando grabarProveedorSAP DEV 08 ");
                logger.error("Ingresando grabarProveedorSAP DEV 09 response: " + response);

                *//* Respuesta SAP *//*
                if (StringUtils.isBlank(response.getCODE()) || WebServiceConstant.RESPUESTA_OK.equals(response.getCODE())) {
                    beanProveedor.setAcreedorCodigoSap(response.getLIFNR());
                    logger.error("GrabarSAP PROVEEDOR EXITO CODIGO SAP: " + response.getLIFNR());
                    logger.error("GrabarSAP PROVEEDOR EXITO code: " + response.getCODE());
                    logger.error("GrabarSAP PROVEEDOR EXITO mensaj: " + response.getMESAJ());
                } else {
                    //sapLogRetorno.setCode(response.getCODE());
                    //sapLogRetorno.setMesaj(response.getMESAJ());
                    beanRetorno.setTieneError(true);

                    logger.error("GrabarSAP PROVEEDOR ERROR: " + response.getMESAJ());
                }
            } catch (Exception e) {
                logger.error("Ingresando grabarProveedorSAP DEV 10 error: " );
                logger.error("GrabarSAP PROVEEDOR Exception: " + e.getMessage());
                e.printStackTrace();
                response.setCODE("-1");
                response.setMESAJ(e.getMessage());
                beanRetorno.setTieneError(true);
            }


            ProveedorBeanSAP proveedorBeanSAP = new ProveedorBeanSAP();
            proveedorBeanSAP.setProveedorSAP(beanProveedor);

            SapLog sapLogProveedor = new SapLog();
            sapLogProveedor.setCode(response.getCODE());
            sapLogProveedor.setMesaj(response.getMESAJ());
            proveedorBeanSAP.setSapLogProveedor(sapLogProveedor);

            listaProveedorSAPResult.add(proveedorBeanSAP);
        }

        beanRetorno.setSapLog(sapLogRetorno);
        beanRetorno.setListaProveedorSAPResult(listaProveedorSAPResult);
        logger.error("Ingresando grabarProveedorSAP DEV FIN: ");*/
        return beanRetorno;
    }

    @Override
    public SapLog bloquearProveedorSAP(Proveedor proveedor) {

        logger.debug("Ingresando bloquearProveedorSAP");
        SapLog sapLogRetorno = new SapLog();

        Parametro wsdlParam =
                this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
                        WebServiceConstant.URL_WSDL_PROVEEDOR);
        if (wsdlParam == null) {
            String msj = "url_wsdl not found";
            sapLogRetorno.setCode("-1");
            sapLogRetorno.setMesaj(msj);
            logger.error(msj);
            return sapLogRetorno;
        }
        Parametro userParam = this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
                WebServiceConstant.SAP_USERNAME);
        if (userParam == null) {
            String msj = "sap_user not found";
            sapLogRetorno.setCode("-2");
            sapLogRetorno.setMesaj(msj);
            logger.error(msj);
            return sapLogRetorno;
        }
        Parametro passParam = this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
                WebServiceConstant.SAP_PASSWORD);
        if (passParam == null) {
            String msj = "sap_pass not found";
            sapLogRetorno.setCode("-3");
            sapLogRetorno.setMesaj(msj);
            logger.error(msj);
            return sapLogRetorno;
        }

        /*logger.debug("PROVEEDOR wsdlParam.getValor(): " + wsdlParam.getValor());
        String urlWsdl = "";
        try {
            urlWsdl = Class.class.getResource(wsdlParam.getValor()).toString();
        } catch (Exception e) {
            urlWsdl = ProveedorWebService.class.getClassLoader().getResource(wsdlParam.getValor()).toString();
        }
        DTP028MaestroProveedoresRes response = new DTP028MaestroProveedoresRes();
        SIP028MaestroProveedoresSyncReqOut port = portInit(urlWsdl);

        //BASIC authenticaction
        Map requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.USERNAME_PROPERTY, userParam.getValor());
        requestContext.put(BindingProvider.PASSWORD_PROPERTY, passParam.getValor());


        *//* Grabando informacion *//*
        DTP028MaestroProveedoresReq.MODO modo = new DTP028MaestroProveedoresReq.MODO();
        modo.setTPROC(WebServiceConstant.TIPO_PROCESAMIENTO_BLOQUEO_PROVEEDOR);

        sapLogRetorno.setCode(WebServiceConstant.RESPUESTA_OK);
        sapLogRetorno.setMesaj(WebServiceConstant.MENSAJE_VACIO);

        DTP028MaestroProveedoresReq objRequest = new DTP028MaestroProveedoresReq();
        objRequest.setMODO(modo);

        *//* Datos Generales *//*
        DTP028MaestroProveedoresReq.GENERALES generales = new DTP028MaestroProveedoresReq.GENERALES();
        generales.setLIFNR(proveedor.getAcreedorCodigoSap());
        objRequest.setGENERALES(generales);

        *//* Grabando en SAP *//*
        logger.debug("PROVEEDOR objRequest VALORES: " + objRequest.toString());
        if (objRequest.getGENERALES() != null) {
            logger.debug("PROVEEDOR objRequest.GENERALES VALORES: " + objRequest.getGENERALES().toString());
        }

        try {
            response = port.siP028MaestroProveedoresSyncReqOut(objRequest);

            *//* Respuesta SAP *//*
            if (response.getCODE() != null && !response.getCODE().isEmpty()) {
                sapLogRetorno.setCode(response.getCODE());
                sapLogRetorno.setMesaj(response.getMESAJ());
            }
        } catch (Exception e) {
            logger.error("BloquearSAP PROVEEDOR Exception: " + e.getMessage());
            sapLogRetorno.setCode("-1");
            sapLogRetorno.setMesaj(e.getMessage());
        }*/

        return sapLogRetorno;

    }

    /*private SIP028MaestroProveedoresSyncReqOut portInit(String strUrl) {
        SIP028MaestroProveedoresSyncReqOut port = null;
        try {
            URL url = new URL(strUrl);
            SIP028MaestroProveedoresSyncReqOutService consultaProveedor =
                    new SIP028MaestroProveedoresSyncReqOutService(url);
            port = consultaProveedor.getHTTPPort();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println(e.toString() + e);
        }
        return port;
    }*/


}
