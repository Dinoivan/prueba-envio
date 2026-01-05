package com.incloud.hcp.sap.ordenCompra;

import com.incloud.hcp.domain.CcomparativoAdjudicado;
import com.incloud.hcp.repository.CotizacionDetalleRepository;
import com.incloud.hcp.repository.ParametroRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by USER on 29/09/2017.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrdenCompraWebServiceImpl implements OrdenCompraWebService {

    private static Logger logger = LoggerFactory.getLogger(OrdenCompraWebServiceImpl.class);

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    CotizacionDetalleRepository cotizacionDetalleRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    public OrdenCompraResponse grabarOrdenCompraSAP(List<CcomparativoAdjudicado> listaAdjudicacion,
                                                    String claseDocumentoSAP) {

        logger.error("01 Ingresando grabarOrdenCompraSAP");
        OrdenCompraResponse ordenCompraResponse = new OrdenCompraResponse();
        ordenCompraResponse.setTieneError(false);
//        List<OrdenCompraBeanSAP> listaOrdenCompraBeanSAP = new ArrayList<OrdenCompraBeanSAP>();
//        logger.error("02 Ingresando grabarOrdenCompraSAP");
//
//        ordenCompraResponse.setListaOrdenCompraBeanSAP(listaOrdenCompraBeanSAP);
//        SapLog sapLogRetorno = new SapLog();
//        logger.error("03 Ingresando grabarOrdenCompraSAP");
//
//        Parametro wsdlParam =
//                this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
//                        WebServiceConstant.URL_WSDL_ORDEN_COMPRA);
//        if (wsdlParam == null) {
//            String msj = "url_wsdl not found";
//            sapLogRetorno.setCode("-1");
//            sapLogRetorno.setMesaj(msj);
//            ordenCompraResponse.setSapLogPrincipal(sapLogRetorno);
//            logger.error(msj);
//            ordenCompraResponse.setTieneError(true);
//            return ordenCompraResponse;
//        }
//        logger.error("04 Ingresando grabarOrdenCompraSAP");
//
//        Parametro userParam = this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
//                WebServiceConstant.SAP_USERNAME);
//        if (userParam == null) {
//            String msj = "sap_user not found";
//            sapLogRetorno.setCode("-2");
//            sapLogRetorno.setMesaj(msj);
//            ordenCompraResponse.setSapLogPrincipal(sapLogRetorno);
//            ordenCompraResponse.setTieneError(true);
//            logger.error(msj);
//            return ordenCompraResponse;
//        }
//        logger.error("05 Ingresando grabarOrdenCompraSAP");
//
//        Parametro passParam = this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
//                WebServiceConstant.SAP_PASSWORD);
//        if (passParam == null) {
//            String msj = "sap_pass not found";
//            sapLogRetorno.setCode("-3");
//            sapLogRetorno.setMesaj(msj);
//            ordenCompraResponse.setSapLogPrincipal(sapLogRetorno);
//            ordenCompraResponse.setTieneError(true);
//            logger.error(msj);
//            return ordenCompraResponse;
//        }
//        logger.error("06 Ingresando grabarOrdenCompraSAP");
//        logger.error("ORDEN COMPRA WebServiceConstant.PATH_WSDL: " + WebServiceConstant.PATH_WSDL);
//        logger.error("ORDEN COMPRA wsdlParam.getValor(): " + wsdlParam.getValor());
//
//        String urlWsdl = "";
//        try {
//            urlWsdl = Class.class.getResource(wsdlParam.getValor()).toString();
//        }
//        catch(Exception e) {
//            logger.error("ORDEN COMPRA entro Excepcion");
//            urlWsdl = OrdenCompraWebService.class.getClassLoader().getResource( wsdlParam.getValor()).toString();
//        }
//
//        logger.error("07 Ingresando grabarOrdenCompraSAP");
//        DTV2P031CrearOrdenDeCompraRes response=null;
//        SIV2P031CrearOrdenDeCompraSyncReqOut port = portInit(urlWsdl);
//        sapLogRetorno.setCode(WebServiceConstant.RESPUESTA_OK);
//        sapLogRetorno.setMesaj(WebServiceConstant.MENSAJE_VACIO);
//        logger.error("08 Ingresando grabarOrdenCompraSAP");
//
//        //BASIC authenticaction
//        Map requestContext = ((BindingProvider) port).getRequestContext();
//        requestContext.put(BindingProvider.USERNAME_PROPERTY, userParam.getValor());
//        requestContext.put(BindingProvider.PASSWORD_PROPERTY, passParam.getValor());
//        logger.error("09 Ingresando grabarOrdenCompraSAP");
//        logger.error("09a Ingresando grabarOrdenCompraSAP - listaAdjudicacion: " + listaAdjudicacion.size());
//        logger.error("09b Ingresando grabarOrdenCompraSAP - listaAdjudicacion: " + listaAdjudicacion.toString());
//
//        List<CcomparativoAdjudicado> listaSolicitudPedido = new ArrayList<CcomparativoAdjudicado>();
//        for(CcomparativoAdjudicado bean:listaAdjudicacion) {
//            if (StringUtils.isBlank(bean.getCotizacionDetalle().getSolicitudPedido())) {
//                continue;
//            }
//            if (StringUtils.isBlank(bean.getCotizacionDetalle().getLicitacionDetalle().getPosicionSolicitudPedido())) {
//                continue;
//            }
//            Integer idCotizacionDetalle = bean.getCotizacionDetalle().getIdCotizacionDetalle();
//            if (idCotizacionDetalle.intValue() == 0) {
//                continue;
//            }
//            bean.setCotizacionDetalle(this.cotizacionDetalleRepository.getOne(idCotizacionDetalle));
//            listaSolicitudPedido.add(bean);
//        }
//        logger.error("10 Ingresando grabarOrdenCompraSAP");
//
//        /* Ordenando Lista por Proveedor */
//        Comparator<CcomparativoAdjudicado> comparatorProveedor = (CcomparativoAdjudicado a, CcomparativoAdjudicado b) -> {
//            return b.getProveedor().getIdProveedor().compareTo(a.getProveedor().getIdProveedor());
//        };
//        Collections.sort(listaSolicitudPedido, comparatorProveedor);
//        logger.error("11 Ingresando grabarOrdenCompraSAP");
//
//        /* Generando Ordenes de Compra */
//        int idProveedor = -1;
//        boolean grabarOrdenCompra = false;
//        DTV2P031CrearOrdenDeCompraReq objRequest = new DTV2P031CrearOrdenDeCompraReq();
//        List<DTV2P031CrearOrdenDeCompraReq.Detalle> listaDetalle = new ArrayList<DTV2P031CrearOrdenDeCompraReq.Detalle>();
//        logger.error("12 Ingresando grabarOrdenCompraSAP");
//        logger.error("12a Ingresando grabarOrdenCompraSAP - listaSolicitudPedido: " + listaSolicitudPedido.size());
//        logger.error("12b Ingresando grabarOrdenCompraSAP - listaSolicitudPedido: " + listaSolicitudPedido.toString());
//
//        List<OrdenCompraEnviarSAPDTO> ordenCompraEnviarSAPDTOList = new ArrayList<OrdenCompraEnviarSAPDTO>();
//        OrdenCompraEnviarSAPDTO ordenCompraEnviarSAPDTO = new OrdenCompraEnviarSAPDTO();
//        for(CcomparativoAdjudicado bean:listaSolicitudPedido) {
//            if (bean.getProveedor().getIdProveedor().intValue() != idProveedor) {
//                if (idProveedor != -1) {
//                    ordenCompraEnviarSAPDTO.setListaDetalle(listaDetalle);
//                    ordenCompraEnviarSAPDTOList.add(ordenCompraEnviarSAPDTO);
//                }
//
//                idProveedor = bean.getProveedor().getIdProveedor().intValue();
//                ordenCompraEnviarSAPDTO = new OrdenCompraEnviarSAPDTO();
//                objRequest = new DTV2P031CrearOrdenDeCompraReq();
//                CcomparativoAdjudicado beanCcomparativo = new CcomparativoAdjudicado();
//                listaDetalle = new ArrayList<DTV2P031CrearOrdenDeCompraReq.Detalle>();
//                DTV2P031CrearOrdenDeCompraReq.Cabecera cabecera = new DTV2P031CrearOrdenDeCompraReq.Cabecera();
//
//                Proveedor proveedor = this.proveedorRepository.getOne(bean.getProveedor().getIdProveedor());
//                beanCcomparativo.setProveedor(proveedor);
//                cabecera.setBSART(claseDocumentoSAP);
//                cabecera.setLIFNR(bean.getProveedor().getAcreedorCodigoSap().trim());
//                objRequest.setCabecera(cabecera);
//
//                ordenCompraEnviarSAPDTO.setBean(bean);
//                ordenCompraEnviarSAPDTO.setObjRequest(objRequest);
//                ordenCompraEnviarSAPDTO.setBeanCcomparativo(beanCcomparativo);
//                grabarOrdenCompra = true;
//            }
//            DTV2P031CrearOrdenDeCompraReq.Detalle detalle = new DTV2P031CrearOrdenDeCompraReq.Detalle();
//            detalle.setBANFN(bean.getCotizacionDetalle().getSolicitudPedido().trim());
//            detalle.setPOSNR(bean.getCotizacionDetalle().getLicitacionDetalle().getPosicionSolicitudPedido().trim());
//            detalle.setMENGE(bean.getCantidadReal().toString().trim());
//            detalle.setKBETR(bean.getPrecioUnitario().toString().trim());
//            listaDetalle.add(detalle);
//        }
//        ordenCompraEnviarSAPDTO.setListaDetalle(listaDetalle);
//        ordenCompraEnviarSAPDTOList.add(ordenCompraEnviarSAPDTO);
//
//
//        logger.error("13a Ingresando grabarOrdenCompraSAP - ordenCompraEnviarSAPDTOList: " + ordenCompraEnviarSAPDTOList.size());
//        logger.error("13b Ingresando grabarOrdenCompraSAP - ordenCompraEnviarSAPDTOList: " + ordenCompraEnviarSAPDTOList.toString());
//        for(OrdenCompraEnviarSAPDTO bean:ordenCompraEnviarSAPDTOList) {
//            logger.error("13c Ingresando grabarOrdenCompraSAP - bean: " + bean.toString());
//            this.grabarSAP(sapLogRetorno, port, grabarOrdenCompra,
//                    bean.getObjRequest(), bean.getListaDetalle(), ordenCompraResponse,
//                    bean.getBeanCcomparativo());
//        }
//        logger.error("14 Ingresando grabarOrdenCompraSAP FIN ");


//        CcomparativoAdjudicado beanCcomparativo = new CcomparativoAdjudicado();
//        for(CcomparativoAdjudicado bean:listaSolicitudPedido) {
//            logger.error("12c Ingresando grabarOrdenCompraSAP - bean: " + bean.toString());
//            if (bean.getProveedor().getIdProveedor().intValue() != idProveedor) {
//
//                this.grabarSAP(sapLogRetorno, port, grabarOrdenCompra,
//                        objRequest, listaDetalle, ordenCompraResponse,
//                        beanCcomparativo);
//
//                beanCcomparativo = new CcomparativoAdjudicado();
//                Proveedor proveedor = this.proveedorRepository.getOne(bean.getProveedor().getIdProveedor());
//                beanCcomparativo.setProveedor(proveedor);
//
//                idProveedor = bean.getProveedor().getIdProveedor().intValue();
//                objRequest = new DTV2P031CrearOrdenDeCompraReq();
//                listaDetalle = new ArrayList<DTV2P031CrearOrdenDeCompraReq.Detalle>();
//                DTV2P031CrearOrdenDeCompraReq.Cabecera cabecera = new DTV2P031CrearOrdenDeCompraReq.Cabecera();
//                cabecera.setBSART(claseDocumentoSAP);
//                cabecera.setLIFNR(bean.getProveedor().getAcreedorCodigoSap().trim());
//                objRequest.setCabecera(cabecera);
//                grabarOrdenCompra = true;
//            }
//            DTV2P031CrearOrdenDeCompraReq.Detalle detalle = new DTV2P031CrearOrdenDeCompraReq.Detalle();
//            detalle.setBANFN(bean.getCotizacionDetalle().getSolicitudPedido().trim());
//            detalle.setPOSNR(bean.getCotizacionDetalle().getLicitacionDetalle().getPosicionSolicitudPedido().trim());
//            detalle.setMENGE(bean.getCantidadReal().toString().trim());
//            detalle.setKBETR(bean.getPrecioUnitario().toString().trim());
//            listaDetalle.add(detalle);
//        }
//        logger.error("13 Ingresando grabarOrdenCompraSAP " + listaSolicitudPedido.toString());
//
//        this.grabarSAP(sapLogRetorno, port, grabarOrdenCompra,
//                objRequest, listaDetalle, ordenCompraResponse,
//                beanCcomparativo);
//        logger.error("14 Ingresando grabarOrdenCompraSAP " + listaSolicitudPedido.toString());
        return ordenCompraResponse;
    }

    /*private void grabarSAP(SapLog sapLogRetorno,
                           SIV2P031CrearOrdenDeCompraSyncReqOut port,
                           boolean grabarOrdenCompra,
                           DTV2P031CrearOrdenDeCompraReq objRequest,
                           List<DTV2P031CrearOrdenDeCompraReq.Detalle> listaDetalle,
                           OrdenCompraResponse ordenCompraResponse,
                           CcomparativoAdjudicado bean) {

        DTV2P031CrearOrdenDeCompraRes response = new DTV2P031CrearOrdenDeCompraRes();
        OrdenCompraBeanSAP ordenCompraBeanSAP = new OrdenCompraBeanSAP();
        List<OrdenCompraBeanSAP> listaOrdenCompraBeanSAP = ordenCompraResponse.getListaOrdenCompraBeanSAP();
        OrdenCompraBeanSAP beanRespuesta = new OrdenCompraBeanSAP();

        if (grabarOrdenCompra) {
            objRequest.setDetalle(listaDetalle);
            logger.error("ORDEN COMPRA objRequest VALORES: " + objRequest.toString());

            *//* Grabando en SAP *//*
            try {
                response = port.siP031CrearOrdenDeCompraSyncReqIn(objRequest);
                logger.error("ORDEN COMPRA ejecutado OK RESPONSE: " + response.toString());
                logger.error("ORDEN COMPRA LOG MESAJ: " + response.getLog().getMESAJ());
                logger.error("ORDEN COMPRA LOG CODE: " + response.getLog().getCODE());
            }
            catch(Exception e){
                logger.error("grabarSAP Orden Compra Exception: " + e.getMessage());
                if (response.getLog() == null) {
                    DTV2P031CrearOrdenDeCompraRes.Log log = new DTV2P031CrearOrdenDeCompraRes.Log();
                    response.setLog(log);
                }
                response.getLog().setCODE("-1");
                response.getLog().setMESAJ(e.getMessage());
                ordenCompraResponse.setTieneError(true);
            }

            *//* Respuesta SAP *//*
            if (StringUtils.isBlank(response.getLog().getCODE()) ||  WebServiceConstant.RESPUESTA_OK.equals(response.getLog().getCODE())) {
                if (Optional.ofNullable(response.getResponse().getEBELN()).isPresent()) {
                    logger.error("grabarSAP Orden Compra getEBELN: " + response.getResponse().getEBELN());
                    beanRespuesta.setNumeroOrdenCompra(response.getResponse().getEBELN());
                }
                else {
                    logger.error("grabarSAP Orden Compra Error: " + response.getLog().getMESAJ());
                    response.getLog().setCODE("-1");
                    response.getLog().setMESAJ(response.getLog().getMESAJ() + " - Campo EBELN devuelto de SAP no tiene valor" );
                    ordenCompraResponse.setTieneError(true);
                }
            }
            else {
                logger.error("grabarSAP Orden Compra Error: " + response.getLog().getMESAJ());
                ordenCompraResponse.setTieneError(true);
            }
//            beanRespuesta.setProveedorSAP(bean.getProveedor());

            sapLogRetorno.setCode(response.getLog().getCODE());
            sapLogRetorno.setMesaj(response.getLog().getMESAJ());
            beanRespuesta.setSapLogOrdenCompra(sapLogRetorno);
            listaOrdenCompraBeanSAP.add(beanRespuesta);


            ordenCompraResponse.setListaOrdenCompraBeanSAP(listaOrdenCompraBeanSAP);
            ordenCompraResponse.setSapLogPrincipal(sapLogRetorno);
            logger.error("grabarSAP Orden Compra OK" );

        }
    }


    private SIV2P031CrearOrdenDeCompraSyncReqOut portInit(String strUrl){
        SIV2P031CrearOrdenDeCompraSyncReqOut port = null;
        try {
            URL url=new URL(strUrl);
            SIV2P031CrearOrdenDeCompraSyncReqOutService consulta =
                    new SIV2P031CrearOrdenDeCompraSyncReqOutService(url);
            port = consulta.getHTTPPort();
        }catch (MalformedURLException e){
            e.printStackTrace();
            System.out.println(e.toString() + e);
        }
        return port;
    }*/
}
