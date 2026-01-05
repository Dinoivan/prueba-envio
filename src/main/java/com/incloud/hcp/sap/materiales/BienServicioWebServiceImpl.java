package com.incloud.hcp.sap.materiales;

import com.incloud.hcp.domain.BienServicio;
import com.incloud.hcp.domain.Parametro;
import com.incloud.hcp.domain.RubroBien;
import com.incloud.hcp.domain.UnidadMedida;
import com.incloud.hcp.myibatis.mapper.ProductoMapper;
import com.incloud.hcp.repository.BienServicioRepository;
import com.incloud.hcp.repository.ParametroRepository;
import com.incloud.hcp.repository.RubroBienRepository;
import com.incloud.hcp.repository.UnidadMedidaRepository;
import com.incloud.hcp.util.constant.BienServicioConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by USER on 22/09/2017.
 */
@Service
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class BienServicioWebServiceImpl implements BienServicioWebService {

    private static Logger logger = LoggerFactory.getLogger(BienServicioWebServiceImpl.class);

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private RubroBienRepository rubroBienRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private BienServicioRepository bienServicioRepository;

    @Autowired
    private BienServiceWebServiceNewRequired bienServiceWebServiceNewRequired;

    @Autowired
    private ProductoMapper productoMapper;


    @Override
    public BienServicioResponse sincronizarMateriales(String fechaIni, String url, String user, String pass) throws Exception {
        logger.error("Ingresando sincronizarMateriales 01");
        Parametro prmRubroDefault = parametroRepository.getParametroByModuloAndTipo(BienServicioConstant.KEY_BIEN_SERVICIO,
                BienServicioConstant.KEY_RUBRO_DEFAULT);

        Parametro prmUMDefault = parametroRepository.getParametroByModuloAndTipo(BienServicioConstant.KEY_BIEN_SERVICIO,
                BienServicioConstant.KEY_UM_DEFAULT);

        BienServicioResponse bienServicioResponse = new BienServicioResponse();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        /*DTP027MaestroMaterialesRes response = null;
        SIP027MaestroMaterialesReqSyncOut port = portInit(url);

        XMLGregorianCalendar fechaInput = convertToGregorianCalendar(fechaIni);

        DTP027MaestroMaterialesReq objRequest = new DTP027MaestroMaterialesReq();
        // objRequest.setFECHA(fechaInput);
        objRequest.setFECHA(fechaIni);

        //BASIC authenticaction
        Map requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.USERNAME_PROPERTY, user);
        requestContext.put(BindingProvider.PASSWORD_PROPERTY, pass);
        requestContext.put("com.sun.xml.internal.ws.request.timeout", 10000);
        requestContext.put("com.sun.xml.internal.ws.connect.timeout", 10000);

        if (port != null) {
            logger.error("Ingresando sincronizarMateriales 02 fechaIni: " + fechaIni);
            logger.error("Ingresando sincronizarMateriales 03 objRequest: " + objRequest);

            response = port.siP027MaestroMaterialesReqSyncOut(objRequest);
            if (response != null) {
                if (("0").equals(response.getLOG().getCODE())) {
                    Integer countItemRead = response.getResponse().size();
                    if (countItemRead > 0) {
                        Integer countItemInsert = 0;
                        Integer countItemUpdate = 0;

                        //Variables locales por cada iteración
                        UnidadMedida unidadMedida;
                        RubroBien rubroBien;
                        logger.error("Ingresando sincronizarMateriales LONGITUD: " + response.getResponse().size());

                        int contador = 0;
                        List<BienServicio> bienServicioListSave = new ArrayList<BienServicio>();
                        List<String> listSaveFail = new ArrayList<String>();
                        for (DTP027MaestroMaterialesRes.Response obj : response.getResponse()) {
                            contador++;
                            //logger.error("contador " + contador + " - findBienServicio: 00 " + obj.toString());
                            String codigoSap = obj.getMATNR();
                            //Obteniendo Descripción de Bien/Servicio
                            String descripcionBien = "-";
                            descripcionBien = obj.getMAKTX();
                            String tipoBien = "D";
                            if (("MAT").equals(obj.getMTART())) {
                                tipoBien = "M";
                            } else if (("SRV").equals(obj.getMTART())) {
                                tipoBien = "S";
                            }

                            boolean grabar = true;
                            if (!Optional.ofNullable(codigoSap).isPresent()) {
                                grabar = false;
                            }
                            if (!Optional.ofNullable(obj.getMAKTX()).isPresent()) {
                                grabar = false;
                            }
                            if (!Optional.ofNullable(obj.getMTART()).isPresent()) {
                                grabar = false;
                            }
                            if (!Optional.ofNullable(obj.getMATKL()).isPresent()) {
                                grabar = false;
                            }
                            if (!Optional.ofNullable(obj.getMEINS()).isPresent()) {
                                grabar = false;
                            }

                            //Obteniendo Grupo de Articulo del Bien/Servicio
                            rubroBien = this.getRubroBien(Optional.ofNullable(obj.getMATKL()).orElse(prmRubroDefault.getValor()));
                            if (!Optional.ofNullable(rubroBien).isPresent()) {
                                grabar = false;
                            }

                            //Obteniendo UnidadMedida del Bien/Servicio
                            unidadMedida = this.getUnidadMedida(Optional.ofNullable(obj.getMEINS()).orElse(prmUMDefault.getValor()));
                            if (!Optional.ofNullable(unidadMedida).isPresent()) {
                                grabar = false;
                            }

                            if (!grabar) {
                                continue;
                            }

                            BienServicio findBienServicio = this.getBienServicio(obj.getMATNR());
                            findBienServicio.setCodigoSap(codigoSap);
                            findBienServicio.setRubroBien(rubroBien);
                            findBienServicio.setUnidadMedida(unidadMedida);
                            findBienServicio.setTipoItem(tipoBien);
                            findBienServicio.setNumeroParte(obj.getMFRPN());
                            findBienServicio.setDescripcion(descripcionBien);

                            try {
                                this.bienServiceWebServiceNewRequired.saveNewRequired(findBienServicio);
                                //logger.error("findBienServicio save 08 OK" );
                            } catch (Exception e) {
                                listSaveFail.add(findBienServicio.getCodigoSap());
                                logger.error("findBienServicio save 08 ERROR findBienServicio: " + findBienServicio.toString());
                            }

                            //bienServicioListSave.add(findBienServicio);
                        }

                        bienServicioResponse.setCountItemRead(countItemRead);
                        bienServicioResponse.setCountItemInsert(countItemInsert);
                        bienServicioResponse.setCountItemUpdate(countItemUpdate);
                        bienServicioResponse.setCountSaveFail(listSaveFail.size());
                        bienServicioResponse.setListSaveFail(listSaveFail);
                        logger.error("findBienServicio 10");
                        if (listSaveFail.size() == 0) {
                            String dateLastSync = formatter.format(new Date()).toString();
                            this.parametroRepository.updateValueParameter(dateLastSync, BienServicioConstant.KEY_BIEN_SERVICIO,
                                    BienServicioConstant.KEY_DATE_SYNC);
                            bienServicioResponse.setSapLog(new SapLog(WebServiceConstant.CODE_SYNC_COMPLETE,
                                    WebServiceConstant.MSJ_SYNC_COMPLETE));
                        } else {
                            bienServicioResponse.setSapLog(new SapLog(WebServiceConstant.CODE_SYNC_INCOMPLETE,
                                    WebServiceConstant.MSJ_SYNC_INCOMPLETE));
                        }
                        //logger.error("findBienServicio 11");
                    } else {
                        logger.debug(WebServiceConstant.MSJ_RESPONSE_EMPTY);
                        bienServicioResponse.setSapLog(new SapLog(WebServiceConstant.CODE_RESPONSE_EMPTY,
                                WebServiceConstant.MSJ_RESPONSE_EMPTY));
                    }
                } else {
                    if (response.getLOG() != null) {
                        bienServicioResponse.setSapLog(new SapLog(response.getLOG().getCODE(), response.getLOG().getMESAJ()));
                    }
                }
            }
        }
        logger.error("Ingresando sincronizarMateriales FIN");*/
        return bienServicioResponse;
    }

    /*private SIP027MaestroMaterialesReqSyncOut portInit(String strUrl) {
        SIP027MaestroMaterialesReqSyncOut port = null;
        try {
            URL url = new URL(strUrl);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty( "Content-Type", "text/xml" );

            SIP027MaestroMaterialesReqSyncOutService consulta = new SIP027MaestroMaterialesReqSyncOutService(url);
            port = consulta.getHTTPPort();
        } catch (Exception e) {

            logger.error(e.getMessage());
        }
        return port;
    }*/


    /*private DTP027MaestroMaterialesRes ejecutarOkHttpClient(DTP027MaestroMaterialesReq mtP027MaestroMaterialesReqOut) throws  Exception{
        DTP027MaestroMaterialesRes dtp027MaestroMaterialesRes = new DTP027MaestroMaterialesRes();
        OkHttpClient client = new OkHttpClient();
        logger.error("ejecutarOkHttpClient 01");

        MediaType mediaType = MediaType.parse("text/xml;charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sanmartinperu.pe:portal027:MaestroMateriales\">\r\n   <soapenv:Header/>\r\n   <soapenv:Body>\r\n      <urn:MT_P027_MaestroMateriales_Req_Out>\r\n" +
                "         <FECHA>" +
                mtP027MaestroMaterialesReqOut.getFECHA() +
                "</FECHA>\r\n      </urn:MT_P027_MaestroMateriales_Req_Out>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>");
        Request request = new Request.Builder()
                .url("http://prdpo.sanmartinperu.pe:4500/XISOAPAdapter/MessageServlet?senderParty=&senderService=BS_HCP_PRD&receiverParty=&receiverService=&interface=SI_P027_MaestroMateriales_Req_Sync_Out&interfaceNamespace=urn%3Asanmartinperu.pe%3Aportal027%3AMaestroMateriales")
                .post(body)
                .addHeader("content-type", "text/xml;charset=UTF-8")
                .addHeader("authorization", "Basic U0FOTUFSVElOX1dTOmluaWNpbzAx")
                .addHeader("cache-control", "no-cache")
                //.addHeader("postman-token", "75e049fd-29c0-02fa-17d5-ffc0f2e31eb1")
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();

        DOMParserMateriales domParser = new DOMParserMateriales();
        domParser.parse(responseString);

        logger.error("ejecutarOkHttpClient 08");
        domParser.printAllElements();
        List<DTP027MaestroMaterialesRes.Response> listaMateriales = domParser.getMateriales();
        DTP027MaestroMaterialesRes.LOG log = new DTP027MaestroMaterialesRes.LOG();
        log.setCODE("0");
        log.setMESAJ("OK");
        dtp027MaestroMaterialesRes.setLOG(log);
        dtp027MaestroMaterialesRes.setResponse(listaMateriales);

//        for (DTP027MaestroMaterialesRes.Response beanResponse : listaMateriales) {
//            logger.error("ejecutarOkHttpClient 08 beanResponse: " + beanResponse.toString());
//        }
        return dtp027MaestroMaterialesRes;
    }*/



    private XMLGregorianCalendar convertToGregorianCalendar(String fecha) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        XMLGregorianCalendar xmlGregCal = null;
        try {
            Date date = format.parse(fecha);
            xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date));

        } catch (ParseException pe) {
            logger.error(pe.getMessage());
        } catch (DatatypeConfigurationException dce) {
            logger.error(dce.getMessage());
        }
        return xmlGregCal;
    }

    private BienServicio getBienServicio(String codSap) {
        BienServicio objServicio = new BienServicio();
        if (codSap != null && !codSap.isEmpty()) {
            List<BienServicio> bienServicioList = this.productoMapper.getBienServicioCodigoSap(codSap);
            //List<BienServicio> bienServicioList = this.bienServicioRepository.findByCodigoSap(codSap);
            if (bienServicioList != null && bienServicioList.size() > 0) {
                objServicio = bienServicioList.get(0);
            }
        }
        return objServicio;
    }

    private RubroBien getRubroBien(String codRubroBien) {
        RubroBien objRubro = null;
        if (codRubroBien != null && !codRubroBien.isEmpty()) {
            objRubro = rubroBienRepository.getByCodigoSap(codRubroBien);
        }
        return objRubro;
    }


    private UnidadMedida getUnidadMedida(String codUnidadMedida) {

        UnidadMedida objUm = null;

        if (codUnidadMedida != null && !codUnidadMedida.isEmpty()) {
            objUm = unidadMedidaRepository.getByCodigoSap(codUnidadMedida);
        }
        return objUm;
    }


}