package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.File64;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.repository.BienServicioRepository;
import com.incloud.hcp.repository.CentroAlmacenRepository;
import com.incloud.hcp.repository.ParametroRepository;
import com.incloud.hcp.repository.RubroBienRepository;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.sap.materiales.BienServicioResponse;
import com.incloud.hcp.sap.materiales.BienServicioWebService;
import com.incloud.hcp.service.BienServicioService;
import com.incloud.hcp.util.constant.BienServicioConstant;
import com.incloud.hcp.util.constant.WebServiceConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Created by USER on 21/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class BienServicioServiceImpl implements BienServicioService {

    private static Logger logger = LoggerFactory.getLogger(BienServicioServiceImpl.class);


    @Autowired
    private BienServicioRepository bienServicioRepository;

    @Autowired
    private RubroBienRepository rubroBienRepository;

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private BienServicioWebService bienServicioWebService;

    @Autowired
    private CentroAlmacenRepository centroAlmacenRepository;

    @Override
    public List<BienServicio> getListBienServicioByCodigoSap(String codigoSap) {

        List<BienServicio> lista = new ArrayList<BienServicio>();
        if (codigoSap.length() > 18){
            return lista;
        }
        //String codigoSapFormateado = String.format("%018d", Integer.valueOf(codigoSap));

        if (codigoSap != null) {
            //String texto = ("000000000000000000" + codigoSap);
            //String codigoSapFormateado = texto.substring(texto.length() - 18, texto.length());
            lista = this.bienServicioRepository.findAllByCodigoSap(codigoSap);
        }
        return lista;
    }


    @Override
    public List<BienServicio> getListBienServicioByNroParte(String nroParte) {

        List<BienServicio> lista = new ArrayList<BienServicio>();
        if ((BienServicioConstant.VALUE_NOT_ALLOW_1).equals(nroParte.trim())){
            return lista;
        }else {
            lista = this.bienServicioRepository.findAllByNumeroParte(nroParte);
        }
        return lista;
    }

    @Override
    public Map<String, Object> getListBienServicioByExcel(File64 file64) {

        List<LicitacionDetalle> lista = new ArrayList<LicitacionDetalle>();
        List<String> listaError = new ArrayList<String>();
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            byte[] buffer = Base64.getDecoder().decode(file64.getContentBase64().getBytes("UTF-8"));

            DataFormatter dataFormatter = new DataFormatter(new Locale("en", "US"));
            File excelFile = new File("detalle_licitacion.xlsx");
            OutputStream outStream = new FileOutputStream(excelFile);
            outStream.write(buffer);
            InputStream targetStream = new FileInputStream(excelFile);

            Workbook workbook = new XSSFWorkbook(targetStream);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            int countRow = 0;
            int countCell = 0;
            //boolean flagRow = true;
            //boolean flagCell = true;

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (countRow > 0) {//Obviar cabecera de excel
                    LicitacionDetalle detalle = new LicitacionDetalle();
                    BienServicio bienServicio = new BienServicio();
                    CentroAlmacen centro = new CentroAlmacen();
                    CentroAlmacen almacen = new CentroAlmacen();
                    Iterator<Cell> cellIterator = currentRow.iterator();
                    List<BienServicio> listaBs = new ArrayList<BienServicio>();
                    List<CentroAlmacen> listaCentro = new ArrayList<CentroAlmacen>();
                    List<CentroAlmacen> listaAlmacen  = new ArrayList<CentroAlmacen>();

                    countCell = 0;

                    String valueCodigoSap = "";
                    String valueCantidad = "";
                    String codCentro = "";
                    String codAlmacen = "";

                    int codCentroAux = 0;
                    int codAlmacenAux = 0;
                    int idCentro=0;


                    while (cellIterator.hasNext()) {

                        Cell currentCell = cellIterator.next();
                        countCell++;
                        if (countCell == 1) {//Codigo sap
                            //valueCodigoSap = currentCell.getStringCellValue();
                            if (currentCell.getCellType() == CellType.STRING) {
                                valueCodigoSap = currentCell.getStringCellValue();
                            } else if (currentCell.getCellType() == CellType.NUMERIC) {
//                                valueCodigoSap = currentCell.getNumericCellValue() + "";
                                valueCodigoSap = dataFormatter.formatCellValue(currentCell);
                            }

                        }else if (countCell == 2){//Cantidad
                            //valueCantidad = currentCell.getNumericCellValue();
                            if (currentCell.getCellType() == CellType.STRING) {
                                valueCantidad = currentCell.getStringCellValue();
                            } else if (currentCell.getCellType() == CellType.NUMERIC) {
                                valueCantidad = currentCell.getNumericCellValue() + "";
                            }

                        }else if (countCell == 3){//centro

                            //Validacion centro
                            if (currentCell.getCellType() == CellType.STRING) {
                                codCentro = currentCell.getStringCellValue();
                            } else if (currentCell.getCellType() == CellType.NUMERIC) {
                                codCentro = currentCell.getNumericCellValue() + "";
                            }

                        }
//                        else if (countCell == 4){//almacen
//                            //Validacion almacen
//                            if (currentCell.getCellType() == CellType.STRING) {
//                                codAlmacen = currentCell.getStringCellValue();
//                            } else if (currentCell.getCellType() == CellType.NUMERIC) {
//                                codAlmacen = currentCell.getNumericCellValue() + "";
//                            }
//                        }
                    }

                    //Validacion codigo sap=========================================
                    //boolean flagBs = true;
                    boolean flagValidate = true;

                    if (!this.isNumeric(valueCodigoSap)) {
                        //Map<String, String> map = new HashMap<String, String>();
                       // map.put("E" + countRow , "Linea " + countRow + " : " +  "El codigo SAP " + valueCodigoSap + " tiene que ser numérico");
                        listaError.add("Linea " + countRow + " : " +  "El codigo SAP " + valueCodigoSap + " tiene que ser numérico");
                        flagValidate = false;
                    }

                    if (flagValidate) {
                        logger.error(valueCodigoSap);
//                        valueCodigoSap = valueCodigoSap.split("\\.", 2)[0];//Quitar puntos decimales
                        //String codigoSapFormateado = String.format("%18s", valueCodigoSap).replace(' ','0');
//                        String codigoSapFormateado = String.format("%018d", new Long(valueCodigoSap));
                        String codigoSapFormateado = StringUtils.leftPad(valueCodigoSap.trim(), 18, '0');
                        logger.error(codigoSapFormateado);
                        listaBs = this.getListBienServicioByCodigoSap(codigoSapFormateado);
                        logger.error(listaBs.toString());
                        //bienServicio = listaBs.get(0);

                        if (listaBs == null || listaBs.size() == 0) {
                           // Map<String, String> map = new HashMap<String, String>();
                            //map.put("E" + countRow, "Linea " + countRow + " : " +  "El codigo SAP " + valueCodigoSap + " no existe");
                            listaError.add("Linea " + countRow + " : " +  "El codigo SAP " + valueCodigoSap + " no existe");
                            flagValidate = false;
                        }else {
                            bienServicio = listaBs.get(0);
                            //flagBs = true;
                        }
                    }
                    //Validacion cantidad ===========================================================
                    if (flagValidate) {
                        if (!this.isNumeric(valueCantidad + "")) {
                            //Map<String, String> map = new HashMap<String, String>();
                           // map.put("E" + countRow , "Linea " + countRow + " : " +  "La cantidad " + valueCantidad + " tiene que ser numérico");
                            listaError.add("Linea " + countRow + " : " +  "La cantidad " + valueCantidad + " tiene que ser numérico");
                            flagValidate = false;
                        }else {
                            detalle.setCantidadSolicitada(new BigDecimal(valueCantidad));
                        }
                    }
                    //Validacion Centro ======================================================
//                    if (flagValidate){
//                        if (!this.isNumeric(codCentro)) {
//                            //Map<String, String> map = new HashMap<String, String>();
//                            //map.put("E" + countRow, "Linea " + (countRow - 1) +  "El Centro SAP " + codCentro + " tiene que ser numérico");
//                            listaError.add("Linea " + (countRow - 1) +  "El Centro SAP " + codCentro + " tiene que ser numérico");
//                            flagValidate = false;
//                        }
//                    }
                    boolean flagCentro = false;
                    if (flagValidate) {
//                        codCentroAux = (int)Float.parseFloat(codCentro);
                        listaCentro = this.centroAlmacenRepository.findByNivelOrderByDenominacion(1);

                        if (listaCentro != null) {
                            for(int i = 0; i < listaCentro.size(); i++) {
                                centro = listaCentro.get(i);
//                                if (parseInt(centro.getCodigoSap())== codCentroAux) {
//                                    idCentro=centro.getIdCentroAlmacen().intValue();
//                                    flagCentro = true;
//                                    break;
                                if (centro.getCodigoSap().equalsIgnoreCase(codCentro)) {
                                    idCentro = centro.getIdCentroAlmacen();
                                    flagCentro = true;
                                    break;
                                }
                            }
                        }

//                        if (!flagCentro) {
//                           // Map<String, String> map = new HashMap<String, String>();
//                            //map.put("E", "Linea " + countRow + " : " +  "El Centro " + codCentro + " no existe");
//                            listaError.add("Linea " + countRow + " : " +  "El Centro " + codCentroAux + " no existe");
//                            flagValidate = false;
//                        }
                    }
                    //Validacion almacen ==================================================================
//                    if (flagValidate) {
//                        if (!this.isNumeric(codAlmacen)) {
//                           // Map<String, String> map = new HashMap<String, String>();
//                           // map.put("E" + countRow, "Linea " + countRow + " : " +  "El Almacén SAP " + codCentro + " tiene que ser numérico");
//                            listaError.add("Linea " + countRow + " : " +  "El Almacén SAP " + codCentro + " tiene que ser numérico");
//                            flagValidate = false;
//                        }
//                    }
//                    if (flagValidate) {
//                        boolean flagAlmacen = false;
//                        codAlmacenAux = (int)Float.parseFloat(codAlmacen);
//
//
//                        listaAlmacen = this.centroAlmacenRepository.findByIdPadreOrderByDenominacion(idCentro);
//                        if (listaAlmacen != null) {
//                            for(int i = 0; i < listaAlmacen.size(); i++) {
//                                almacen = listaAlmacen.get(i);
//                                if (parseInt(almacen.getCodigoSap()) == codAlmacenAux) {
//                                    flagAlmacen = true;
//                                    break;
//                                }
//
//                            }
//                        }
//                        if (!flagAlmacen) {
//                           // Map<String, String> map = new HashMap<String, String>();
//                           // map.put("E" + countRow , "Linea " + countRow + " : " +  "El Almacén " + codAlmacen + " no pertenece al centro " + codCentro);
//                            listaError.add("Linea " + countRow + " : " +  "El Almacén " + codAlmacenAux + " no pertenece al centro " + codCentroAux);
//                            flagValidate = false;
//                        }
//                    }



                    //detalle.setg

                    if (flagValidate) {
                        detalle.setBienServicio(bienServicio);
                        if(flagCentro)
                            detalle.setIdCentro(centro);
//                        detalle.setIdAlmacen(almacen);
                        detalle.setDescripcion(bienServicio.getDescripcion());
                        detalle.setNumeroParte(bienServicio.getNumeroParte());
                        detalle.setUnidadMedida(bienServicio.getUnidadMedida());
                        if (bienServicio.getRubroBien() != null)
                            detalle.setGrupoArticulo(bienServicio.getRubroBien().getDescripcion());
                        lista.add(detalle);
                    }


                }
                countRow++;
            }

            //System.out.println("ok");
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException Error Carga masiva excel : " + e.toString());
            e.printStackTrace();
        }catch (IOException e ) {
            logger.error("IOException Error Carga masiva excel : " + e.toString());
            e.printStackTrace();

        }
        map.put("lista",lista );
        map.put("listaError",listaError );
        return map;
    }
    public boolean isNumeric(String strNum) {
        try {

            BigDecimal d = new BigDecimal(strNum + "");
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    @Override
    public List<BienServicio> getListBienServicioByGrupoArticulo(int idGrupoArt) {
        List<BienServicio> lista = new ArrayList<BienServicio>();
        RubroBien rubroBien = new RubroBien();
        rubroBien.setIdRubro(idGrupoArt);

        return this.bienServicioRepository.findAllByRubroBien(rubroBien);
    }

    @Override
    public BienServicioResponse sincronizarBienServicioByLastDate() throws Exception {

        Parametro syncParam = parametroRepository.getParametroByModuloAndTipo(BienServicioConstant.KEY_BIEN_SERVICIO, BienServicioConstant.KEY_DATE_SYNC);
        String dateLastSync = syncParam.getValor();

        Parametro wsdlParam = this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
                WebServiceConstant.URL_WSDL_MATERIAL);

        if (syncParam == null){
            logger.error("date_sync not found");
        }
        if (wsdlParam == null){
            logger.error("url_wsdl not found");
        }
        Parametro userParam = this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
                WebServiceConstant.SAP_USERNAME);
        if (userParam == null){
            logger.error("sap_user not found");
        }
        Parametro passParam = this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
                WebServiceConstant.SAP_PASSWORD);
        if (passParam == null){
            logger.error("sap_pass not found");
        }

        if (syncParam == null || wsdlParam == null || userParam == null || passParam == null){
            BienServicioResponse objResponse = new BienServicioResponse();

            logger.error(WebServiceConstant.MSJ_PARAM_NOEXIST);
            objResponse.setSapLog(new SapLog(WebServiceConstant.CODE_PARAM_NOEXIST,
                    WebServiceConstant.MSJ_PARAM_NOEXIST));

            return objResponse;
        }

        String urlWsdl = BienServicioService.class.getClassLoader().getResource(wsdlParam.getValor()).toString();
        return bienServicioWebService.sincronizarMateriales(
            dateLastSync,
            urlWsdl,
            userParam.getValor(),
            passParam.getValor()
        );


    }



}
