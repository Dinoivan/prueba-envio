package com.incloud.hcp.sap.homologacion;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.repository.ParametroRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.sap.proveedor.ProveedorBeanSAP;
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
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class HomologacionWebServiceImpl implements HomologacionWebService{

    private static Logger logger = LoggerFactory.getLogger(HomologacionWebServiceImpl.class);
    private static String VALOR_HOMOL = "X";

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;


    public HomologacionResponse actualizarHomologacionSAP(List<Proveedor> listaProveedorPotencial)  {
        logger.debug("Ingresando actualizarHomologacionSAP");

        HomologacionResponse beanRetorno = new HomologacionResponse();
        beanRetorno.setTieneError(false);
        SapLog sapLogRetorno = new SapLog();
        List<ProveedorBeanSAP> listaProveedorSAPResult = new ArrayList<ProveedorBeanSAP>();

        /*Parametro wsdlParam =
                this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
                WebServiceConstant.URL_WSDL_HOMOLOGACION);
        if (wsdlParam == null) {
            String msj = "url_wsdl not found";
            sapLogRetorno.setCode("-1");
            sapLogRetorno.setMesaj(msj);
            beanRetorno.setSapLog(sapLogRetorno);
            beanRetorno.setTieneError(true);
            logger.error(msj);
            return beanRetorno;
        }
        Parametro userParam = this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
                WebServiceConstant.SAP_USERNAME);
        if (userParam == null) {
            String msj = "sap_user not found";
            sapLogRetorno.setCode("-2");
            sapLogRetorno.setMesaj(msj);
            beanRetorno.setSapLog(sapLogRetorno);
            beanRetorno.setTieneError(true);
            logger.error(msj);
            return beanRetorno;
        }
        Parametro passParam = this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
                WebServiceConstant.SAP_PASSWORD);
        if (passParam == null) {
            String msj = "sap_pass not found";
            sapLogRetorno.setCode("-3");
            sapLogRetorno.setMesaj(msj);
            beanRetorno.setSapLog(sapLogRetorno);
            beanRetorno.setTieneError(true);
            logger.error(msj);
            return beanRetorno;
        }


        logger.debug("HOMOLOGACION WebServiceConstant.PATH_WSDL: " + WebServiceConstant.PATH_WSDL);
        logger.debug("HOMOLOGACION wsdlParam.getValor(): " + wsdlParam.getValor());
        String urlWsdl = "";
        try {
            urlWsdl = Class.class.getResource(wsdlParam.getValor()).toString();
        }
        catch(Exception e) {
            urlWsdl = HomologacionWebService.class.getClassLoader().getResource(wsdlParam.getValor()).toString();
        }
        //DTP030ActualizarHomologacionRes response=new DTP030ActualizarHomologacionRes();
        SIP030ActualizarHomologacionSyncReqOut port = portInit(urlWsdl);


        //BASIC authenticaction
        Map requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.USERNAME_PROPERTY, userParam.getValor());
        requestContext.put(BindingProvider.PASSWORD_PROPERTY, passParam.getValor());

        *//* Grabando informacion *//*
        DTP028MaestroProveedoresReq.MODO modo = new DTP028MaestroProveedoresReq.MODO();
        modo.setTPROC(WebServiceConstant.TIPO_PROCESAMIENTO_PROVEEDOR);

        sapLogRetorno.setCode(WebServiceConstant.RESPUESTA_OK);
        sapLogRetorno.setMesaj(WebServiceConstant.MENSAJE_VACIO);

        for(Proveedor beanProveedor: listaProveedorPotencial){
            DTP030ActualizarHomologacionReq objRequest = new DTP030ActualizarHomologacionReq();
            Date fechaExpiracion = new Date();
            fechaExpiracion = DateUtils.sumarRestarAnno(fechaExpiracion, 1);
            XMLGregorianCalendar fechaExpiracionXML = DateUtils.dateToXml(fechaExpiracion);
            objRequest.setFEEXP(fechaExpiracionXML);
            objRequest.setHOMOL(VALOR_HOMOL);
            objRequest.setLIFNR(beanProveedor.getAcreedorCodigoSap());
            objRequest.setNOTA(beanProveedor.getEvaluacionHomologacion().toString());


            *//* Grabando en SAP *//*
            SapLog sapLogProveedor = new SapLog();
            logger.debug("HOMOLOGACION objRequest VALORES: " + objRequest.toString());
            try {
                port.siP030ActualizarHomologacionSyncReqOut(objRequest);
                sapLogProveedor.setCode(WebServiceConstant.RESPUESTA_OK);
                sapLogProveedor.setMesaj(WebServiceConstant.MENSAJE_VACIO);

            }
            catch(Exception e) {
                logger.debug("GrabarSAP HOMOLOGACION Exception: " + e.getMessage());
                sapLogProveedor.setCode("-1");
                sapLogProveedor.setMesaj(e.getMessage());
                beanRetorno.setTieneError(true);
            }


            ProveedorBeanSAP proveedorBeanSAP = new ProveedorBeanSAP();
            proveedorBeanSAP.setProveedorSAP(beanProveedor);
            proveedorBeanSAP.setSapLogProveedor(sapLogProveedor);
            listaProveedorSAPResult.add(proveedorBeanSAP);
        }

        beanRetorno.setSapLog(sapLogRetorno);
        beanRetorno.setListaProveedorSAPResult(listaProveedorSAPResult);*/
        return beanRetorno;

    }

    /*private SIP030ActualizarHomologacionSyncReqOut portInit(String strUrl){
        SIP030ActualizarHomologacionSyncReqOut port = null;
        try {
            URL url=new URL(strUrl);
            SIP030ActualizarHomologacionSyncReqOutService consultaProveedor =
                    new SIP030ActualizarHomologacionSyncReqOutService(url);
            port = consultaProveedor.getHTTPPort();
        }catch (MalformedURLException e){
            e.printStackTrace();
            System.out.println(e.toString() + e);
        }
        return port;
    }*/




}
