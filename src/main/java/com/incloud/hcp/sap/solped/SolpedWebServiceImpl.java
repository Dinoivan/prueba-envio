package com.incloud.hcp.sap.solped;

import com.incloud.hcp.bean.LicitacionResponse;
import com.incloud.hcp.domain.BienServicio;
import com.incloud.hcp.domain.CentroAlmacen;
import com.incloud.hcp.domain.ClaseDocumento;
import com.incloud.hcp.myibatis.mapper.SolicitudPedidoMapper;
import com.incloud.hcp.repository.BienServicioRepository;
import com.incloud.hcp.repository.CentroAlmacenRepository;
import com.incloud.hcp.repository.ClaseDocumentoRepository;
import com.incloud.hcp.repository.UnidadMedidaRepository;
import com.incloud.hcp.util.constant.SolicitudPedidoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;
/**
 * Created by USER on 12/09/2017.
 */
@Component
public class SolpedWebServiceImpl implements SolpedWebService {

    private static Logger logger = LoggerFactory.getLogger(SolpedWebServiceImpl.class);

    @Autowired
    private ClaseDocumentoRepository claseDocumentoRepository;
    @Autowired
    private BienServicioRepository bienServicioRepository;
    @Autowired
    private CentroAlmacenRepository centroAlmacenRepository;
    @Autowired
    private SolicitudPedidoMapper solicitudPedidoMapper;
    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    private List<String> estados;

    /**
     *
     * @param numeroSolped
     * @param url
     * @param strUser
     * @param strPass
     * @return
     */
    @Override
    public SolpedResponse getSolpedResponseByCodigo(String numeroSolped, String url, String strUser, String strPass) {
        ClaseDocumento claseDocumento;
        BienServicio bienServicio;
        ClaseDocumento claseDoc;
        CentroAlmacen centro;
        CentroAlmacen almacen;
        SolpedResponse solpedResponse = new SolpedResponse();
        String codigoSapMaterialNoEncontrado = "";
        //this.estados = Arrays.asList("GE", "PE", "EV");


        /*DTP026ConsultaSOLPEDRes response=null;
        SIP026ConsultaSOLPEDReqSyncOut port = portInit(url);

        DTP026ConsultaSOLPEDReq objRequest = new DTP026ConsultaSOLPEDReq();
        DTP026ConsultaSOLPEDReq.Request objSolped = new DTP026ConsultaSOLPEDReq.Request();
        objSolped.setBANFN(numeroSolped);
        objRequest.setRequest(objSolped);

        //BASIC authenticaction
        Map requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.USERNAME_PROPERTY, strUser);
        requestContext.put(BindingProvider.PASSWORD_PROPERTY, strPass);

        if (numeroSolped.length() > 10){
            logger.debug(WebServiceConstant.MSJ_EXCEED_SIZE);
            solpedResponse.setSapLog(new SapLog(WebServiceConstant.CODE_EXCEED_SIZE,
                    WebServiceConstant.MSJ_EXCEED_SIZE));
            return solpedResponse;
        }
        logger.error("Ingresando getSolpedResponseByCodigo");


        if(port!=null){
            response=port.siP026ConsultaSOLPEDReqSyncOut(objRequest);
            logger.error("Ingresando getSolpedResponseByCodigo 01 ");
            if (response.getLog() != null) {
                logger.error("Ingresando getSolpedResponseByCodigo 02 ");
                if (("0").equals(response.getLog().getCODE())){
                    logger.error("Ingresando getSolpedResponseByCodigo 03 ");
                    Integer idClaseDocumento=0;
                    Integer idCentro=0;
                    Integer idAlmacen=0;
                    int countNoFound=0;
                    logger.error("Ingresando getSolpedResponseByCodigo 04 response detalle: " + response.getDetalle());

                    List<SolicitudPedido> listaSolicitudPedido = new ArrayList<SolicitudPedido>();
                    for(DTP026ConsultaSOLPEDRes.Detalle obj:response.getDetalle()){
                        logger.error("Ingresando getSolpedResponseByCodigo 05 detalle " + obj);
                        int idClaseDoc=0;
                        String descripClaseDoc="";

                        claseDocumento = this.getClaseDocumento(obj.getBSART());
                        if (claseDocumento != null){
                            idClaseDoc = claseDocumento.getIdClaseDocumento();
                            descripClaseDoc = claseDocumento.getDescripcion();
                        }
                        logger.error("Ingresando getSolpedResponseByCodigo 06 claseDocumento " + claseDocumento);
                        Long codigoSapMaterialNoCeros = Long.valueOf(obj.getMATNR());
                        bienServicio = this.getBienServicio(String.valueOf(codigoSapMaterialNoCeros));

                        if (bienServicio != null){
                            logger.error("Ingresando getSolpedResponseByCodigo 07 bienServicio " + bienServicio);
                            String estadoPosicion = getEstadoPosicion(obj.getBANFN(), bienServicio.getIdBienServicio());
                            Long codigoSolped = Long.valueOf(obj.getBANFN());

                            UnidadMedida um = this.unidadMedidaRepository.findByCodigoSap(obj.getMEINS());
                            if (um != null){
                                bienServicio.setUnidadMedida(um);
                            }
                            logger.error("Ingresando getSolpedResponseByCodigo 08 um " + um);

                            listaSolicitudPedido.add(new SolicitudPedido(
                                    obj.getBNFPO(),
                                    String.valueOf(codigoSolped),
                                    idClaseDoc,
                                    descripClaseDoc,
                                    bienServicio,
                                    obj.getMENGE(),
                                    estadoPosicion
                            ));
                        }else{
                            countNoFound ++;
                            codigoSapMaterialNoEncontrado = obj.getMATNR();
                        }
                    }
                    logger.error("Ingresando getSolpedResponseByCodigo 09");

                    if (countNoFound > 0){
                        logger.error("Ingresando getSolpedResponseByCodigo 09 error bienServicio No encontrado: " + codigoSapMaterialNoEncontrado);
                        solpedResponse.setSapLog(new SapLog(WebServiceConstant.CODE_NOT_FOUND, WebServiceConstant.MSJ_NOT_FOUND));
                    }else{
                        logger.error("Ingresando getSolpedResponseByCodigo 10");
                        claseDoc = this.getClaseDocumento(response.getDetalle().get(0).getBSART());
                        centro = this.getCentroAlmacen(response.getDetalle().get(0).getWERKS(),1);
                        almacen = this.getCentroAlmacen(response.getDetalle().get(0).getLGORT(),2);
                        logger.error("Ingresando getSolpedResponseByCodigo 11 clasedoc "+ claseDoc + " centro " + centro + " almacen : " + almacen);

                        if (claseDoc != null){
                            idClaseDocumento=claseDoc.getIdClaseDocumento();
                        }

                        if (centro != null){
                            idCentro=centro.getIdCentroAlmacen();
                        }

                        if (almacen != null){
                            idAlmacen=almacen.getIdCentroAlmacen();
                        }
                        logger.error("Ingresando getSolpedResponseByCodigo 11");
                        solpedResponse.setIdClaseDocumento(idClaseDocumento);
                        solpedResponse.setIdCentro(idCentro);
                        solpedResponse.setIdAlmacen(idAlmacen);
                        solpedResponse.setFechaEntrega(this.convertXMLCalendarIntoDate(response.getDetalle().get(0).getLFDAT()));
                        solpedResponse.setListaSolped(listaSolicitudPedido);
                        solpedResponse.setSapLog(new SapLog(response.getLog().getCODE(),response.getLog().getMESAJ()));
                        logger.error("Ingresando getSolpedResponseByCodigo 12");
                        logger.debug("Petición Satisfactoria ...");
                    }
                } else{
                    logger.debug(response.getLog().getMESAJ());
                    solpedResponse.setSapLog(new SapLog(response.getLog().getCODE(),response.getLog().getMESAJ()));
                }
            } else {
                logger.error(WebServiceConstant.MSJ_ERR_RESPONSE);
                solpedResponse.setSapLog(new SapLog(WebServiceConstant.CODE_RESPONSE_NULL,
                        WebServiceConstant.MSJ_ERR_RESPONSE));
            }
        }else{
            logger.error(WebServiceConstant.MSJ_ERR_RESPONSE);
            solpedResponse.setSapLog(new SapLog(WebServiceConstant.CODE_ERR_PORT,
                    WebServiceConstant.MSJ_ERR_RESPONSE));
        }
        logger.error("Ingresando getSolpedResponseByCodigo 13");*/
        return solpedResponse;
    }

    /*private SIP026ConsultaSOLPEDReqSyncOut portInit(String strUrl){
        SIP026ConsultaSOLPEDReqSyncOut port = null;
        try {
            URL url=new URL(strUrl);
            SIP026ConsultaSOLPEDReqSyncOutService consultaSolped = new SIP026ConsultaSOLPEDReqSyncOutService(url);
            port = consultaSolped.getHTTPPort();
        }catch (MalformedURLException e){
            e.printStackTrace();
            System.out.println(e.toString() + e);
        }
        return port;
    }*/

    public Date convertXMLCalendarIntoDate(XMLGregorianCalendar calendar){
        return calendar.toGregorianCalendar().getTime();
    }

    public ClaseDocumento getClaseDocumento(String codigoClaseDocumento){
        if (codigoClaseDocumento != null && !codigoClaseDocumento.isEmpty()){
            ClaseDocumento claseDoc = new ClaseDocumento();
            claseDoc = this.claseDocumentoRepository.findByCodigoClaseDocumentoAndNivel(codigoClaseDocumento, 1);
            return claseDoc;
        }else{
            return null;
        }
    }

    public BienServicio getBienServicio(String codigoSap){
        if (codigoSap != null && !codigoSap.isEmpty()) {
            BienServicio bienServ = this.bienServicioRepository.getByCodigoSap(codigoSap);
            return bienServ;
        }else{
            return null;
        }
    }

    public CentroAlmacen getCentroAlmacen(String codigoSap, int nivel){

        CentroAlmacen obj = null;
        try{
            if (codigoSap != null) {
                obj = centroAlmacenRepository.findByCodigoSapAndNivel(codigoSap, nivel);
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }

        return obj;
    }

    /**
     *
     * @param codSolped = Codigo Solped
     * @param codBienServicio = Codigo de Material o Servicio
     * @return idLicitacion si la consulta devuelve algo, de lo contrario retorna null;
     */
    public String getEstadoPosicion(String codSolped, int codBienServicio){
        List<LicitacionResponse> listaLicitacion = this.solicitudPedidoMapper.getLicitacionBySolpedAndBien(codSolped, codBienServicio);

        String estado = "";

        if (listaLicitacion.size() > 0){
            String estadoObj = listaLicitacion.get(0).getEstadoLicitacion();

            switch (estadoObj){
                case "GE":
                    estado = SolicitudPedidoConstant.ESTADO_EN_PROCESO;
                    break;
                case "PE":
                    estado = SolicitudPedidoConstant.ESTADO_EN_PROCESO;
                    break;
                case "EV":
                    estado = SolicitudPedidoConstant.ESTADO_EN_PROCESO;
                    break;
                case "AD":
                    estado = SolicitudPedidoConstant.ESTADO_ADJUDICADO;
                    break;
                case "AN":
                    estado = SolicitudPedidoConstant.ESTADO_POR_LICITAR;
                    break;
                case "ES":
                    estado = SolicitudPedidoConstant.ESTADO_ADJUDICADO;
                    break;
                default:
                    estado = SolicitudPedidoConstant.ESTADO_POR_LICITAR;
            }
        }else{
            estado = SolicitudPedidoConstant.ESTADO_POR_LICITAR;
        }

        return estado;
    }
}