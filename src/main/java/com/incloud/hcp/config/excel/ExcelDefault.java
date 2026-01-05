package com.incloud.hcp.config.excel;

import com.incloud.hcp.util.DateUtils;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.*;

/**
 * Created by Administrador on 30/11/2017.
 */
public class ExcelDefault {

    private static final Logger log = LoggerFactory.getLogger(ExcelDefault.class);

    private static String[] getTitulos(String configTitle){
        try{
            XMLConfiguration catag = new XMLConfiguration(configTitle);
            return catag.getStringArray("header.title");
        } catch (Exception ex) {
            return null;
        }
    }

    public static void createTitle(HSSFSheet sheet, String configTitle, CellStyle headerStyle, HSSFFont font){
        List<String> titulos = Arrays.asList(getTitulos(configTitle));
        font.setBold(true);
        //font.setFontHeight((short) 200);
        //font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        headerStyle.setFont(font);
        HSSFRow headerRow = sheet.createRow(0);
        titulos.forEach(title -> {
            int lastCell = headerRow.getLastCellNum();
            int i = lastCell < 0 ? 0 : lastCell;
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(title);
        });
    }

    public static void createTitle(XSSFSheet sheet, String configTitle, CellStyle headerStyle, XSSFFont font){
        List<String> titulos = Arrays.asList(getTitulos(configTitle));
        font.setBold(true);
        headerStyle.setFont(font);

        XSSFRow headerRow = sheet.createRow(0);
        titulos.forEach(title -> {
            int lastCell = headerRow.getLastCellNum();
            int i = lastCell < 0 ? 0 : lastCell;
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(title);
        });
    }

    public static void createTitle(Sheet sheet, String configTitle, CellStyle headerStyle, Font font){
        List<String> titulos = Arrays.asList(getTitulos(configTitle));
        font.setBold(true);
        //font.setFontHeight((short) 200);
        //font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerStyle.setFont(font);

        Row headerRow = sheet.createRow(0);
        titulos.forEach(title -> {
            int lastCell = headerRow.getLastCellNum();
            int i = lastCell < 0 ? 0 : lastCell;
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(title);
        });
    }

    public static void addTitleDynamic(HSSFSheet sheet, List<String> configTitle, CellStyle headerStyle, HSSFFont font){
        font.setBold(true);
        //font.setFontHeight((short) 200);
        //font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        headerStyle.setFont(font);

        HSSFRow headerRow = sheet.getRow(0);
        configTitle.forEach(title -> {
            int lastCell = headerRow.getLastCellNum();
            int i = lastCell < 0 ? 0 : lastCell;
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(title);
        });
    }

    public static void setValueCell(Object row, Cell cell) {
        Object value = row;
        if (value == null)
            value = "";

        try {
            Class type = value.getClass();
            if (type == Integer.class) {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(Integer.parseInt(NumberUtils.isNumber(value.toString()) ? value.toString() : "0"));
            } else if (type == Double.class) {
                cell.setCellType(CellType.NUMERIC);
                String cadena = NumberUtils.isNumber(value.toString()) ? value.toString() : "0.00";
                BigDecimal valor01 = new BigDecimal(cadena);
                BigDecimal valor02 = valor01.setScale(2, BigDecimal.ROUND_HALF_UP);
                cell.setCellValue(valor02.doubleValue());
            } else if (type == BigDecimal.class) {
                cell.setCellType(CellType.NUMERIC);
                String cadena = NumberUtils.isNumber(value.toString()) ? value.toString() : "0.00";
                BigDecimal valor01 = new BigDecimal(cadena);
                BigDecimal valor02 = valor01.setScale(2, BigDecimal.ROUND_HALF_UP);
                cell.setCellValue(valor02.doubleValue());

            } else if (type == Float.class) {
                cell.setCellType(CellType.NUMERIC);
                String cadena = NumberUtils.isNumber(value.toString()) ? value.toString() : "0.00";
                BigDecimal valor01 = new BigDecimal(cadena);
                BigDecimal valor02 = valor01.setScale(2, BigDecimal.ROUND_HALF_UP);
                cell.setCellValue(valor02.doubleValue());

            } else {
                cell.setCellValue(value.toString());
            }
        } catch (Exception e) {
            cell.setCellValue(value.toString());
        }
    }

    public static void setValueCell(Object row, Cell cell, CellStyle cellStyle, DataFormat dataFormat) {
        Object value = row;

        boolean esNumericoDecimal = false;
        if (value == null)
            value = "";

        try {
            Class type = value.getClass();
            if (type == Integer.class) {
                esNumericoDecimal =false;
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(Integer.parseInt(NumberUtils.isNumber(value.toString()) ? value.toString() : "0"));
            } else if (type == Double.class) {
                cell.setCellType(CellType.NUMERIC);
                esNumericoDecimal = true;
                String cadena = NumberUtils.isNumber(value.toString()) ? value.toString() : "0.00";
                BigDecimal valor01 = new BigDecimal(cadena);
                BigDecimal valor02 = valor01.setScale(2, BigDecimal.ROUND_HALF_UP);
                cell.setCellValue(valor02.doubleValue());

            } else if (type == BigDecimal.class) {
                cell.setCellType(CellType.NUMERIC);
                esNumericoDecimal = true;
                String cadena = NumberUtils.isNumber(value.toString()) ? value.toString() : "0.00";
                BigDecimal valor01 = new BigDecimal(cadena);
                BigDecimal valor02 = valor01.setScale(2, BigDecimal.ROUND_HALF_UP);
                cell.setCellValue(valor02.doubleValue());

            } else if (type == Float.class) {
                cell.setCellType(CellType.NUMERIC);
                esNumericoDecimal = true;
                String cadena = NumberUtils.isNumber(value.toString()) ? value.toString() : "0.00";
                BigDecimal valor01 = new BigDecimal(cadena);
                BigDecimal valor02 = valor01.setScale(2, BigDecimal.ROUND_HALF_UP);
                cell.setCellValue(valor02.doubleValue());

            } else if (type == Timestamp.class) {
                esNumericoDecimal =false;
                Date fecha = DateUtils.toDateTime(value);
                cell.setCellValue(fecha);

            } else if (type == java.sql.Date.class) {
                esNumericoDecimal =false;
                java.sql.Date fecha = DateUtils.toDateSQL(value);
                cell.setCellValue(fecha);

            } else {
                esNumericoDecimal =false;
                cell.setCellValue(value.toString());
            }

            if (esNumericoDecimal) {
                cellStyle.setDataFormat(dataFormat.getFormat("###,###,###,##0.00"));
            }
            else {
                cellStyle.setDataFormat(dataFormat.getFormat(""));
            }
            cell.setCellStyle(cellStyle);

        } catch (Exception e) {
            cell.setCellValue(value.toString());
        }
    }


    /*************************************************/
    public static List<CellStyle> generarCellStyle(XSSFWorkbook xbook, CellStyle cellStyleBase) {
        List<CellStyle> cellStyleList = new ArrayList<CellStyle>();

        CellStyle cellStyleBaseVacio00 = xbook.createCellStyle();
        DataFormat dataFormat00 = xbook.createDataFormat();
        cellStyleBaseVacio00.cloneStyleFrom(cellStyleBase);
        cellStyleBaseVacio00.setDataFormat(dataFormat00.getFormat("############0"));
        cellStyleList.add(cellStyleBaseVacio00);

        CellStyle cellStyleBaseVacio01 = xbook.createCellStyle();
        DataFormat dataFormat01 = xbook.createDataFormat();
        cellStyleBaseVacio01.cloneStyleFrom(cellStyleBase);
        cellStyleBaseVacio01.setDataFormat(dataFormat01.getFormat("############0"));
        cellStyleList.add(cellStyleBaseVacio01);

        CellStyle cellStyleBaseVacio02 = xbook.createCellStyle();
        DataFormat dataFormat02 = xbook.createDataFormat();
        cellStyleBaseVacio02.cloneStyleFrom(cellStyleBase);
        cellStyleBaseVacio02.setDataFormat(dataFormat02.getFormat("###,###,###,##0.00"));
        cellStyleList.add(cellStyleBaseVacio02);

        CellStyle cellStyleBaseVacio03 = xbook.createCellStyle();
        DataFormat dataFormat03 = xbook.createDataFormat();
        cellStyleBaseVacio03.cloneStyleFrom(cellStyleBase);
        cellStyleBaseVacio03.setDataFormat(dataFormat03.getFormat(""));
        cellStyleList.add(cellStyleBaseVacio03);

        CellStyle cellStyleBaseVacio04 = xbook.createCellStyle();
        DataFormat dataFormat04 = xbook.createDataFormat();
        cellStyleBaseVacio04.cloneStyleFrom(cellStyleBase);
        cellStyleBaseVacio04.setDataFormat(dataFormat04.getFormat("###,###,###,##0"));
        cellStyleList.add(cellStyleBaseVacio04);

        CellStyle cellStyleBaseVacio05 = xbook.createCellStyle();
        DataFormat dataFormat05 = xbook.createDataFormat();
        cellStyleBaseVacio05.cloneStyleFrom(cellStyleBase);
        cellStyleBaseVacio05.setDataFormat(dataFormat05.getFormat("###,###,###,##0.0"));
        cellStyleList.add(cellStyleBaseVacio05);

        CellStyle cellStyleBaseVacio06 = xbook.createCellStyle();
        DataFormat dataFormat06 = xbook.createDataFormat();
        cellStyleBaseVacio06.cloneStyleFrom(cellStyleBase);
        cellStyleBaseVacio06.setDataFormat(dataFormat06.getFormat("###,###,###,##0.000"));
        cellStyleList.add(cellStyleBaseVacio06);

        CellStyle cellStyleBaseVacio07 = xbook.createCellStyle();
        DataFormat dataFormat07 = xbook.createDataFormat();
        cellStyleBaseVacio07.cloneStyleFrom(cellStyleBase);
        cellStyleBaseVacio07.setDataFormat(dataFormat07.getFormat("###,###,###,##0.0000"));
        cellStyleList.add(cellStyleBaseVacio07);

        CellStyle cellStyleBaseVacio08 = xbook.createCellStyle();
        DataFormat dataFormat08 = xbook.createDataFormat();
        cellStyleBaseVacio08.cloneStyleFrom(cellStyleBase);
        cellStyleBaseVacio08.setDataFormat(dataFormat08.getFormat("###,###,###,##0.00000"));
        cellStyleList.add(cellStyleBaseVacio08);

        CellStyle cellStyleBaseVacio09 = xbook.createCellStyle();
        DataFormat dataFormat09 = xbook.createDataFormat();
        cellStyleBaseVacio09.cloneStyleFrom(cellStyleBase);
        cellStyleBaseVacio09.setDataFormat(dataFormat09.getFormat("dd/MM/yyyy HH:mm:ss"));
        cellStyleList.add(cellStyleBaseVacio09);

        CellStyle cellStyleBaseVacio10 = xbook.createCellStyle();
        DataFormat dataFormat10 = xbook.createDataFormat();
        cellStyleBaseVacio10.cloneStyleFrom(cellStyleBase);
        cellStyleBaseVacio10.setDataFormat(dataFormat08.getFormat("dd/MM/yyyy"));
        cellStyleList.add(cellStyleBaseVacio10);

        CellStyle cellStyleBaseVacio11 = xbook.createCellStyle();
        DataFormat dataFormat11 = xbook.createDataFormat();
        cellStyleBaseVacio11.cloneStyleFrom(cellStyleBase);
        cellStyleBaseVacio11.setDataFormat(dataFormat09.getFormat("HH:mm:ss"));
        cellStyleList.add(cellStyleBaseVacio11);


        return cellStyleList;
    }

    public static void setValueCell(Object row, Cell cell, List<CellStyle> cellStyle) {
        Object value = row;

        if (value == null)
            value = "";

        try {
            Class type = value.getClass();
            if (type == Integer.class) {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(Integer.parseInt(NumberUtils.isNumber(value.toString()) ? value.toString() : "0"));
                cell.setCellStyle(cellStyle.get(1));
            } else if (type == Double.class) {
                cell.setCellType(CellType.NUMERIC);
                String cadena = NumberUtils.isNumber(value.toString()) ? value.toString() : "0.00";
                BigDecimal valor01 = new BigDecimal(cadena);
                BigDecimal valor02 = valor01.setScale(2, BigDecimal.ROUND_HALF_UP);
                cell.setCellValue(valor02.doubleValue());
                cell.setCellStyle(cellStyle.get(2));

            } else if (type == BigDecimal.class) {
                cell.setCellType(CellType.NUMERIC);
                String cadena = NumberUtils.isNumber(value.toString()) ? value.toString() : "0.00";
                BigDecimal valor01 = new BigDecimal(cadena);
                BigDecimal valor02 = valor01.setScale(2, BigDecimal.ROUND_HALF_UP);
                cell.setCellValue(valor02.doubleValue());
                cell.setCellStyle(cellStyle.get(2));

            } else if (type == Float.class) {
                cell.setCellType(CellType.NUMERIC);
                String cadena = NumberUtils.isNumber(value.toString()) ? value.toString() : "0.00";
                BigDecimal valor01 = new BigDecimal(cadena);
                BigDecimal valor02 = valor01.setScale(2, BigDecimal.ROUND_HALF_UP);
                cell.setCellValue(valor02.doubleValue());
                cell.setCellStyle(cellStyle.get(2));

            } else if (type == Timestamp.class) {
                Date fecha = DateUtils.toDateTime(value);
                cell.setCellValue(fecha);
                cell.setCellStyle(cellStyle.get(11));

            } else if (type == java.sql.Date.class) {
                java.sql.Date fecha = DateUtils.toDateSQL(value);
                cell.setCellValue(fecha);
                cell.setCellStyle(cellStyle.get(9));

            } else {
                cell.setCellValue(value.toString());
                cell.setCellStyle(cellStyle.get(3));
            }

        } catch (Exception e) {
            cell.setCellValue(value.toString());
        }
    }

    public static int setValueCell(Object row, Cell cell, String tipoCampo, List<CellStyle> cellStyle, String atributo, List<String> listaColumnasVisibles, int contador) {
        boolean existe = false;
        for(String columnaVisible : listaColumnasVisibles) {
            log.debug("Ingresando setValueCell columnaVisible: " + columnaVisible + " atributo: " + atributo);
            if (columnaVisible.equals(atributo)) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            return contador;
        }
        setValueCell(row, cell, tipoCampo, cellStyle);
        contador++;
        log.debug("Ingresando setValueCell contador: " + contador);
        return contador;
    }

    public static void setValueCell(Object row, Cell cell, String tipoCampo, List<CellStyle> cellStyle) {
        Object value = row;

        if (value == null)
            value = "";
        try {
            switch (tipoCampo) {
                case "S":
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(value.toString());
                    cell.setCellStyle(cellStyle.get(3));
                    break;
                case "I":
                    cell.setCellType(CellType.NUMERIC);
                    Integer valorInteger;
                    try {
                        valorInteger = new Integer(value.toString());
                    }
                    catch(Exception e) {
                        valorInteger = new Integer(0);
                    }
                    cell.setCellValue(valorInteger);
                    cell.setCellStyle(cellStyle.get(1));
                    break;
                case "N":
                    cell.setCellType(CellType.NUMERIC);
                    Double valorDouble;
                    try {
                        valorDouble = new Double(value.toString());
                    }
                    catch(Exception e) {
                        valorDouble = new Double(0);
                    }
                    cell.setCellValue(valorDouble);
                    cell.setCellStyle(cellStyle.get(2));
                    break;
                case "N0":
                    cell.setCellType(CellType.NUMERIC);
                    Double valorDouble0;
                    try {
                        valorDouble0 = new Double(value.toString());
                    }
                    catch(Exception e) {
                        valorDouble0 = new Double(0);
                    }
                    cell.setCellValue(valorDouble0);
                    cell.setCellStyle(cellStyle.get(4));
                    break;
                case "N1":
                    cell.setCellType(CellType.NUMERIC);
                    Double valorDouble1 ;
                    try {
                        valorDouble1 = new Double(value.toString());
                    }
                    catch(Exception e) {
                        valorDouble1 = new Double(0);
                    }
                    cell.setCellValue(valorDouble1);
                    cell.setCellStyle(cellStyle.get(5));
                    break;
                case "N3":
                    cell.setCellType(CellType.NUMERIC);
                    Double valorDouble3 ;
                    try {
                        valorDouble3 = new Double(value.toString());
                    }
                    catch(Exception e) {
                        valorDouble3 = new Double(0);
                    }
                    cell.setCellValue(valorDouble3);
                    cell.setCellStyle(cellStyle.get(6));
                    break;
                case "N4":
                    cell.setCellType(CellType.NUMERIC);
                    Double valorDouble4;
                    try {
                        valorDouble4 = new Double(value.toString());
                    }
                    catch(Exception e) {
                        valorDouble4 = new Double(0);
                    }
                    cell.setCellValue(valorDouble4);
                    cell.setCellStyle(cellStyle.get(7));
                    break;
                case "N5":
                    cell.setCellType(CellType.NUMERIC);
                    Double valorDouble5 ;
                    try {
                        valorDouble5 = new Double(value.toString());
                    }
                    catch(Exception e) {
                        valorDouble5 = new Double(0);
                    }
                    cell.setCellValue(valorDouble5);
                    cell.setCellStyle(cellStyle.get(8));
                    break;

                case "DT":
                    Date fecha = DateUtils.toDateTime(value);
                    cell.setCellValue(fecha);
                    cell.setCellStyle(cellStyle.get(9));
                    break;
                case "D":
                    Date fecha02 = DateUtils.toDateTime(value);
                    cell.setCellValue(fecha02);
                    cell.setCellStyle(cellStyle.get(10));
                    break;
                case "T":
                    Date fechaT = DateUtils.toDateTime(value);
                    cell.setCellValue(fechaT);
                    cell.setCellStyle(cellStyle.get(11));
                    break;
                default:
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(value.toString());
                    cell.setCellStyle(cellStyle.get(3));
                    break;
            }

        } catch (Exception e) {
            cell.setCellValue(value.toString());
        }
    }

    public static XSSFCellStyle devuelveCellStyle(
            XSSFWorkbook book,
            Color colorLetra,
            Color colorFondo,
            boolean negrita,
            short tamannoLetra) {

        XSSFFont fontEstrategia = book.createFont();
        XSSFCellStyle cellStyleEstrategia = book.createCellStyle();
        fontEstrategia.setBold(negrita);
        fontEstrategia.setFontHeightInPoints(tamannoLetra);
        fontEstrategia.setColor(new XSSFColor(colorLetra, new DefaultIndexedColorMap()));
        cellStyleEstrategia.setFont(fontEstrategia);

        cellStyleEstrategia.setFillForegroundColor(new XSSFColor(colorFondo, new DefaultIndexedColorMap()));
        cellStyleEstrategia.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyleEstrategia;
    }

    private static List<CabeceraExcel> getTitulosObject(String configTitle){
        try{
            XMLConfiguration catag = new XMLConfiguration(configTitle);
            String title[] = catag.getStringArray("header.title");
            String fondoColor01[] = catag.getStringArray("header.fondoColor01");
            String fondoColor02[] = catag.getStringArray("header.fondoColor02");
            String fondoColor03[] = catag.getStringArray("header.fondoColor03");
            String letraColor01[] = catag.getStringArray("header.letraColor01");
            String letraColor02[] = catag.getStringArray("header.letraColor02");
            String letraColor03[] = catag.getStringArray("header.letraColor03");
            String comentario[] = catag.getStringArray("header.comentario");

            List<String> listaTitle = Arrays.asList(title);
            List<String> listaFondoColor01 = Arrays.asList(fondoColor01);
            List<String> listaFondoColor02 = Arrays.asList(fondoColor02);
            List<String> listaFondoColor03 = Arrays.asList(fondoColor03);
            List<String> listaLetraColor01 = Arrays.asList(letraColor01);
            List<String> listaLetraColor02 = Arrays.asList(letraColor02);
            List<String> listaLetraColor03 = Arrays.asList(letraColor03);
            List<String> listaComentario = Arrays.asList(comentario);

            List<CabeceraExcel> cabeceraExcelList = new ArrayList<CabeceraExcel>();
            for(int i=0; i < listaTitle.size(); i++) {
                CabeceraExcel cabeceraExcel = new CabeceraExcel();
                cabeceraExcel.setTitle(listaTitle.get(i));
                cabeceraExcel.setFondoColor01(new Integer(listaFondoColor01.get(i)));
                cabeceraExcel.setFondoColor02(new Integer(listaFondoColor02.get(i)));
                cabeceraExcel.setFondoColor03(new Integer(listaFondoColor03.get(i)));
                cabeceraExcel.setLetraColor01(new Integer(listaLetraColor01.get(i)));
                cabeceraExcel.setLetraColor02(new Integer(listaLetraColor02.get(i)));
                cabeceraExcel.setLetraColor03(new Integer(listaLetraColor03.get(i)));
                cabeceraExcel.setComentario(listaComentario.get(i));
                cabeceraExcelList.add(cabeceraExcel);
            }
            return cabeceraExcelList;
        } catch (Exception ex) {
            return null;
        }
    }

    private static List<CabeceraExcel> getTitulosObjectVersion02(String configTitle){
        try{
            XMLConfiguration catag = new XMLConfiguration(configTitle);
            String title[] = catag.getStringArray("header.title");
            String fondoColor01[] = catag.getStringArray("header.fondoColor01");
            String fondoColor02[] = catag.getStringArray("header.fondoColor02");
            String fondoColor03[] = catag.getStringArray("header.fondoColor03");
            String letraColor01[] = catag.getStringArray("header.letraColor01");
            String letraColor02[] = catag.getStringArray("header.letraColor02");
            String letraColor03[] = catag.getStringArray("header.letraColor03");
            String comentario[] = catag.getStringArray("header.comentario");
            String id[] = catag.getStringArray("header.id");
            String ancho[] = catag.getStringArray("header.ancho");

            List<String> listaTitle = Arrays.asList(title);
            List<String> listaFondoColor01 = Arrays.asList(fondoColor01);
            List<String> listaFondoColor02 = Arrays.asList(fondoColor02);
            List<String> listaFondoColor03 = Arrays.asList(fondoColor03);
            List<String> listaLetraColor01 = Arrays.asList(letraColor01);
            List<String> listaLetraColor02 = Arrays.asList(letraColor02);
            List<String> listaLetraColor03 = Arrays.asList(letraColor03);
            List<String> listaComentario = Arrays.asList(comentario);
            List<String> listaId = Arrays.asList(id);
            List<String> listaAncho = Arrays.asList(ancho);

            List<CabeceraExcel> cabeceraExcelList = new ArrayList<CabeceraExcel>();
            for(int i=0; i < listaTitle.size(); i++) {
                CabeceraExcel cabeceraExcel = new CabeceraExcel();
                cabeceraExcel.setTitle(listaTitle.get(i));
                cabeceraExcel.setFondoColor01(new Integer(listaFondoColor01.get(i)));
                cabeceraExcel.setFondoColor02(new Integer(listaFondoColor02.get(i)));
                cabeceraExcel.setFondoColor03(new Integer(listaFondoColor03.get(i)));
                cabeceraExcel.setLetraColor01(new Integer(listaLetraColor01.get(i)));
                cabeceraExcel.setLetraColor02(new Integer(listaLetraColor02.get(i)));
                cabeceraExcel.setLetraColor03(new Integer(listaLetraColor03.get(i)));
                cabeceraExcel.setComentario(listaComentario.get(i));
                cabeceraExcel.setId(listaId.get(i));
                cabeceraExcel.setAncho(new Integer(listaAncho.get(i)));
                cabeceraExcelList.add(cabeceraExcel);
            }
            return cabeceraExcelList;
        } catch (Exception ex) {
            return null;
        }
    }

    public static int createTitle(XSSFWorkbook book, XSSFSheet sheet, String configTitle){
        List<CabeceraExcel> titulosObject = getTitulosObject(configTitle);
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(25);

        for(int i=0; i< titulosObject.size(); i++) {
            CabeceraExcel bean = titulosObject.get(i);
            XSSFCellStyle headerStyle = book.createCellStyle();
            XSSFFont font =  book.createFont();

            Color colorLetra = new Color(bean.getLetraColor01(), bean.getLetraColor02(), bean.getLetraColor03());
            font.setBold(true);
            font.setFontHeightInPoints((short)9);
            font.setColor(new XSSFColor(colorLetra, new DefaultIndexedColorMap()));
            headerStyle.setFont(font);
            headerStyle.setWrapText(true);

            Color colorFondo = new Color(bean.getFondoColor01(), bean.getFondoColor02(), bean.getFondoColor03());
            headerStyle.setFillForegroundColor(new XSSFColor(colorFondo, new DefaultIndexedColorMap()));
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            int lastCell = headerRow.getLastCellNum();
            int x = lastCell < 0 ? 0 : lastCell;
            XSSFCell cell = headerRow.createCell(x);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(bean.getTitle());

            String comentario = bean.getComentario();
            if (StringUtils.isNotBlank(comentario)) {
                XSSFCreationHelper richTextFactory = book.getCreationHelper();
                XSSFDrawing drawing = sheet.createDrawingPatriarch();
                XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 2, 6, 8);
                XSSFComment comment = drawing.createCellComment(anchor);
                XSSFRichTextString rtf = richTextFactory.createRichTextString(comentario);

                XSSFFont commentFormatter = book.createFont();
                /* Specify a custom font for comment text in Apache POI */
                commentFormatter.setFontName("Verdana");

                /* Specify a custom font height */
                commentFormatter.setFontHeightInPoints((short)12);

                /* Set a custom comment color */
                commentFormatter.setColor(IndexedColors.BLUE.getIndex());
                /* Apply all the formatting options to the rich text string */
                rtf.applyFont(commentFormatter);
                /* Add comment string */
                comment.setString(rtf);
                /* Set author */
                comment.setAuthor("Ayuda");

                cell.setCellComment(comment);
            }
        }
        sheet.createFreezePane(0, 1);
        return titulosObject.size();
    }

    public static int createTitle(XSSFWorkbook book, Sheet sheet, String configTitle){
        List<CabeceraExcel> titulosObject = getTitulosObject(configTitle);
        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(25);

        for(int i=0; i< titulosObject.size(); i++) {
            CabeceraExcel bean = titulosObject.get(i);
            XSSFCellStyle headerStyle = book.createCellStyle();
            XSSFFont font =  book.createFont();

            Color colorLetra = new Color(bean.getLetraColor01(), bean.getLetraColor02(), bean.getLetraColor03());
            font.setBold(true);
            font.setFontHeightInPoints((short)9);
            font.setColor(new XSSFColor(colorLetra, new DefaultIndexedColorMap()));
            headerStyle.setFont(font);
            headerStyle.setWrapText(true);


            Color colorFondo = new Color(bean.getFondoColor01(), bean.getFondoColor02(), bean.getFondoColor03());
            headerStyle.setFillForegroundColor(new XSSFColor(colorFondo, new DefaultIndexedColorMap()));
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            int lastCell = headerRow.getLastCellNum();
            int x = lastCell < 0 ? 0 : lastCell;
            Cell cell = headerRow.createCell(x);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(bean.getTitle());

            String comentario = bean.getComentario();
            if (StringUtils.isNotBlank(comentario)) {
                CreationHelper richTextFactory = book.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = richTextFactory.createClientAnchor();
                anchor.setCol1(i);
                anchor.setCol2(i + 4);
                anchor.setRow1(0);
                anchor.setRow2(10);
                anchor.setDx1(0);
                anchor.setDx2(0);
                anchor.setDy1(0);
                anchor.setDy2(0);
                Comment comment = drawing.createCellComment(anchor);
                RichTextString rtf = richTextFactory.createRichTextString(comentario);

                XSSFFont commentFormatter = book.createFont();
                /* Specify a custom font for comment text in Apache POI */
                commentFormatter.setFontName("Verdana");

                /* Specify a custom font height */
                commentFormatter.setFontHeightInPoints((short)8);

                /* Set a custom comment color */
                commentFormatter.setColor(IndexedColors.DARK_BLUE.getIndex());

                /* Apply all the formatting options to the rich text string */
                rtf.applyFont(commentFormatter);

                /* Add comment string */
                comment.setString(rtf);
                /* Set author */
                comment.setAuthor("Ayuda");

                //cell.setCellComment(comment);
                comment.setColumn(i);
                comment.setRow(0);
            }
        }
        sheet.createFreezePane(0, 1);
        return titulosObject.size();
    }


    public static int createTitle(XSSFWorkbook book, Sheet sheet, String configTitle, List<String> listaColumnasVisibles){
        List<CabeceraExcel> titulosObject = getTitulosObjectVersion02(configTitle);
        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(25);
        int contador =0;
        for(int i=0; i< titulosObject.size(); i++) {
            CabeceraExcel bean = titulosObject.get(i);
            boolean existe = false;
            for(String columnaVisible : listaColumnasVisibles) {
                if (columnaVisible.equals(bean.getId())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                continue;
            }

            XSSFCellStyle headerStyle = book.createCellStyle();
            XSSFFont font =  book.createFont();

            Color colorLetra = new Color(bean.getLetraColor01(), bean.getLetraColor02(), bean.getLetraColor03());
            font.setBold(true);
            font.setFontHeightInPoints((short)9);
            font.setColor(new XSSFColor(colorLetra, new DefaultIndexedColorMap()));
            headerStyle.setFont(font);
            headerStyle.setWrapText(true);


            Color colorFondo = new Color(bean.getFondoColor01(), bean.getFondoColor02(), bean.getFondoColor03());
            headerStyle.setFillForegroundColor(new XSSFColor(colorFondo, new DefaultIndexedColorMap()));
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            contador++;
            int lastCell = headerRow.getLastCellNum();
            int x = lastCell < 0 ? 0 : lastCell;
            Cell cell = headerRow.createCell(x);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(bean.getTitle());
            int tamanno = bean.getAncho() * 100;
            if (tamanno > 255 * 100) {
                tamanno = 25500;
            }
            sheet.setColumnWidth(i, tamanno);

            String comentario = bean.getComentario();
            if (StringUtils.isNotBlank(comentario)) {
                CreationHelper richTextFactory = book.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = richTextFactory.createClientAnchor();
                anchor.setCol1(i);
                anchor.setCol2(i + 4);
                anchor.setRow1(0);
                anchor.setRow2(10);
                anchor.setDx1(0);
                anchor.setDx2(0);
                anchor.setDy1(0);
                anchor.setDy2(0);
                Comment comment = drawing.createCellComment(anchor);
                RichTextString rtf = richTextFactory.createRichTextString(comentario);

                XSSFFont commentFormatter = book.createFont();
                /* Specify a custom font for comment text in Apache POI */
                commentFormatter.setFontName("Verdana");

                /* Specify a custom font height */
                commentFormatter.setFontHeightInPoints((short) 8);

                /* Set a custom comment color */
                commentFormatter.setColor(IndexedColors.DARK_BLUE.getIndex());

                /* Apply all the formatting options to the rich text string */
                rtf.applyFont(commentFormatter);

                /* Add comment string */
                comment.setString(rtf);
                /* Set author */
                comment.setAuthor("Help");

                //cell.setCellComment(comment);
                comment.setColumn(i);
                comment.setRow(0);
            }

        }
        sheet.createFreezePane(0, 1);
        return contador;
    }


    public static int createTitleAndWidth(
            XSSFWorkbook book,
            Sheet sheet,
            String configTitle,
            String tituloSheet,
            List<CabeceraExcel> titulosAdicionales){
        List<CabeceraExcel> titulosObject = new ArrayList<CabeceraExcel>();
        List<CabeceraExcel> titulosObjectArchivoxx = getTitulosObject(configTitle);
        List<CabeceraExcel> titulosObjectArchivo = getTitulosObjectVersion02(configTitle);
        titulosObject.addAll(titulosObjectArchivo);
        Optional<List<CabeceraExcel>> oTitulosAdicionales = Optional.ofNullable(titulosAdicionales);
        if (oTitulosAdicionales.isPresent()) {
            titulosObject.addAll(titulosAdicionales);
        }

        Row headerRow = sheet.createRow(0);
        int filasHeader = 1;

        if (StringUtils.isNotBlank(tituloSheet)) {
            filasHeader++;
            headerRow.setHeightInPoints(30);
            XSSFCellStyle headerStyle = book.createCellStyle();
            XSSFFont font =  book.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short)12);
            font.setColor((short)0);
            headerStyle.setFont(font);
            headerStyle.setWrapText(false);

            int lastCell = headerRow.getLastCellNum();
            int x = lastCell < 0 ? 0 : lastCell;
            Cell cell = headerRow.createCell(x);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(tituloSheet);
            headerRow = sheet.createRow(1);
        }

        headerRow.setHeightInPoints(25);

        for(int i=0; i< titulosObject.size(); i++) {
            CabeceraExcel bean = titulosObject.get(i);
            XSSFCellStyle headerStyle = book.createCellStyle();
            XSSFFont font =  book.createFont();

            Color colorLetra = new Color(bean.getLetraColor01(), bean.getLetraColor02(), bean.getLetraColor03());
            font.setBold(true);
            font.setFontHeightInPoints((short)9);
            font.setColor(new XSSFColor(colorLetra, new DefaultIndexedColorMap()));
            headerStyle.setFont(font);
            headerStyle.setWrapText(true);


            Color colorFondo = new Color(bean.getFondoColor01(), bean.getFondoColor02(), bean.getFondoColor03());
            headerStyle.setFillForegroundColor(new XSSFColor(colorFondo, new DefaultIndexedColorMap()));
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            int lastCell = headerRow.getLastCellNum();
            int x = lastCell < 0 ? 0 : lastCell;
            Cell cell = headerRow.createCell(x);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(bean.getTitle());
            int tamanno = bean.getAncho() * 100;
            if (tamanno > 255 * 100) {
                tamanno = 25500;
            }
            sheet.setColumnWidth(i, tamanno);

            String comentario = bean.getComentario();
            if (StringUtils.isNotBlank(comentario)) {
                CreationHelper richTextFactory = book.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = richTextFactory.createClientAnchor();
                anchor.setCol1(i);
                anchor.setCol2(i + 4);
                anchor.setRow1(0);
                anchor.setRow2(10);
                anchor.setDx1(0);
                anchor.setDx2(0);
                anchor.setDy1(0);
                anchor.setDy2(0);
                Comment comment = drawing.createCellComment(anchor);
                RichTextString rtf = richTextFactory.createRichTextString(comentario);

                XSSFFont commentFormatter = book.createFont();
                /* Specify a custom font for comment text in Apache POI */
                commentFormatter.setFontName("Verdana");

                /* Specify a custom font height */
                commentFormatter.setFontHeightInPoints((short)8);

                /* Set a custom comment color */
                commentFormatter.setColor(IndexedColors.DARK_BLUE.getIndex());

                /* Apply all the formatting options to the rich text string */
                rtf.applyFont(commentFormatter);

                /* Add comment string */
                comment.setString(rtf);
                /* Set author */
                comment.setAuthor("Help");

                //cell.setCellComment(comment);
                comment.setColumn(i);
                comment.setRow(0);
            }
        }
        sheet.setAutoFilter(new CellRangeAddress(filasHeader - 1, filasHeader - 1, 0, titulosObject.size() - 1));
        sheet.createFreezePane(0, filasHeader);
        return titulosObject.size();
    }


    public static int createAgrupadoTitleAndWidth(
            XSSFWorkbook book,
            Sheet sheet,
            String configTitleAgrupado,
            String configTitle,
            String tituloSheet,
            List<CabeceraExcel> titulosAdicionales){
        List<CabeceraExcel> titulosObject = new ArrayList<CabeceraExcel>();
        List<CabeceraExcel> titulosObjectArchivo = getTitulosObjectVersion02(configTitle);
        titulosObject.addAll(titulosObjectArchivo);
        Optional<List<CabeceraExcel>> oTitulosAdicionales = Optional.ofNullable(titulosAdicionales);
        if (oTitulosAdicionales.isPresent()) {
            titulosObject.addAll(titulosAdicionales);
        }
        List<CabeceraExcel> titulosObjectArchivoAgrupado = getTitulosObjectVersion02(configTitleAgrupado);


        int filasHeader = 0;
        Row headerRow = sheet.createRow(filasHeader);

        if (StringUtils.isNotBlank(tituloSheet)) {
            headerRow.setHeightInPoints(25);
            filasHeader++;
            XSSFCellStyle headerStyle = book.createCellStyle();
            XSSFFont font =  book.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short)12);
            font.setColor((short)0);
            headerStyle.setFont(font);
            headerStyle.setWrapText(false);

            int lastCell = headerRow.getLastCellNum();
            int x = lastCell < 0 ? 0 : lastCell;
            Cell cell = headerRow.createCell(x);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(tituloSheet);
            headerRow = sheet.createRow(filasHeader);
        }

        headerRow.setHeightInPoints(15);
        for(int i=0; i< titulosObjectArchivoAgrupado.size(); i++) {

            CabeceraExcel bean = titulosObjectArchivoAgrupado.get(i);
            XSSFCellStyle headerStyle = book.createCellStyle();
            XSSFFont font =  book.createFont();

            Color colorLetra = new Color(bean.getLetraColor01(), bean.getLetraColor02(), bean.getLetraColor03());
            font.setBold(true);
            font.setFontHeightInPoints((short)9);
            font.setColor(new XSSFColor(colorLetra, new DefaultIndexedColorMap()));
            headerStyle.setFont(font);
            headerStyle.setWrapText(true);


            Color colorFondo = new Color(bean.getFondoColor01(), bean.getFondoColor02(), bean.getFondoColor03());
            headerStyle.setFillForegroundColor(new XSSFColor(colorFondo, new DefaultIndexedColorMap()));
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            int lastCell = headerRow.getLastCellNum();
            int x = lastCell < 0 ? 0 : lastCell;
            Cell cell = headerRow.createCell(x);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(bean.getTitle());
            int tamanno = bean.getAncho() * 100;
            if (tamanno > 255 * 100) {
                tamanno = 25500;
            }
            sheet.setColumnWidth(i, tamanno);

            String comentario = bean.getComentario();
            if (StringUtils.isNotBlank(comentario)) {
                CreationHelper richTextFactory = book.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = richTextFactory.createClientAnchor();
                anchor.setCol1(i);
                anchor.setCol2(i + 4);
                anchor.setRow1(0);
                anchor.setRow2(10);
                anchor.setDx1(0);
                anchor.setDx2(0);
                anchor.setDy1(0);
                anchor.setDy2(0);
                Comment comment = drawing.createCellComment(anchor);
                RichTextString rtf = richTextFactory.createRichTextString(comentario);

                XSSFFont commentFormatter = book.createFont();
                /* Specify a custom font for comment text in Apache POI */
                commentFormatter.setFontName("Verdana");

                /* Specify a custom font height */
                commentFormatter.setFontHeightInPoints((short)8);

                /* Set a custom comment color */
                commentFormatter.setColor(IndexedColors.DARK_BLUE.getIndex());

                /* Apply all the formatting options to the rich text string */
                rtf.applyFont(commentFormatter);

                /* Add comment string */
                comment.setString(rtf);
                /* Set author */
                comment.setAuthor("Help");

                //cell.setCellComment(comment);
                comment.setColumn(i);
                comment.setRow(0);
            }
        }

        filasHeader++;
        headerRow = sheet.createRow(filasHeader);
        headerRow.setHeightInPoints(25);

        for(int i=0; i< titulosObject.size(); i++) {
            CabeceraExcel bean = titulosObject.get(i);
            XSSFCellStyle headerStyle = book.createCellStyle();
            XSSFFont font =  book.createFont();

            Color colorLetra = new Color(bean.getLetraColor01(), bean.getLetraColor02(), bean.getLetraColor03());
            font.setBold(true);
            font.setFontHeightInPoints((short)9);
            font.setColor(new XSSFColor(colorLetra, new DefaultIndexedColorMap()));
            headerStyle.setFont(font);
            headerStyle.setWrapText(true);


            Color colorFondo = new Color(bean.getFondoColor01(), bean.getFondoColor02(), bean.getFondoColor03());
            headerStyle.setFillForegroundColor(new XSSFColor(colorFondo, new DefaultIndexedColorMap()));
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            int lastCell = headerRow.getLastCellNum();
            int x = lastCell < 0 ? 0 : lastCell;
            Cell cell = headerRow.createCell(x);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(bean.getTitle());
            sheet.setColumnWidth(i, bean.getAncho() * 255);

            String comentario = bean.getComentario();
            if (StringUtils.isNotBlank(comentario)) {
                CreationHelper richTextFactory = book.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = richTextFactory.createClientAnchor();
                anchor.setCol1(i);
                anchor.setCol2(i + 4);
                anchor.setRow1(0);
                anchor.setRow2(10);
                anchor.setDx1(0);
                anchor.setDx2(0);
                anchor.setDy1(0);
                anchor.setDy2(0);
                Comment comment = drawing.createCellComment(anchor);
                RichTextString rtf = richTextFactory.createRichTextString(comentario);

                XSSFFont commentFormatter = book.createFont();
                /* Specify a custom font for comment text in Apache POI */
                commentFormatter.setFontName("Verdana");

                /* Specify a custom font height */
                commentFormatter.setFontHeightInPoints((short)8);

                /* Set a custom comment color */
                commentFormatter.setColor(IndexedColors.DARK_BLUE.getIndex());

                /* Apply all the formatting options to the rich text string */
                rtf.applyFont(commentFormatter);

                /* Add comment string */
                comment.setString(rtf);
                /* Set author */
                comment.setAuthor("Help");

                //cell.setCellComment(comment);
                comment.setColumn(i);
                comment.setRow(0);
            }
        }
        sheet.setAutoFilter(new CellRangeAddress(filasHeader , filasHeader , 0, titulosObject.size() - 1));
        sheet.createFreezePane(0, filasHeader + 1);
        return titulosObject.size();
    }


    public static int MIN_COL_WIDTH = 6 << 8;
    public static int MAX_COL_WIDTH = 120 << 8;

    public static void autosizeColumns(Sheet sheet, int numColumns) {
        for (int i = 0; i < numColumns; i++) {
            sheet.autoSizeColumn(i);
            int cw = (int) (sheet.getColumnWidth(i) * 0.8);
            // increase width to accommodate drop-down arrow in the header
            sheet.setColumnWidth(i, Math.max(Math.min(cw, MAX_COL_WIDTH), MIN_COL_WIDTH));
        }
    }




}
