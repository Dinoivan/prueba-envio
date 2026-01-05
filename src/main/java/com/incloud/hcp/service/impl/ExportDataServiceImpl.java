package com.incloud.hcp.service.impl;

import com.incloud.hcp.service.ExportDataService;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ExportDataServiceImpl implements ExportDataService {

    private final int ROW_NUMBER = 12;
    private final int ROW_NUMBERDOCLiq = 6;
    private int totalFilter1 = 0;
    private int totalFilter2 = 0;

    @Override
    public Workbook mapToWorkbook(Map map) {
        final String fileName = (String) map.get("fileName");
        List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
        List<Map<String, Object>> filter1 = (List<Map<String, Object>>) map.get("filter1");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = ((XSSFWorkbook) workbook).createSheet(fileName);

        if (data.isEmpty()) {
            return workbook;
        }

        // style cell
        CellStyle cellStyle = getDefaultXSSFCellStyle(workbook);
        // style header
        CellStyle styleHeader = getDefaultXSSFCellStyleHeader(workbook, (XSSFCellStyle) cellStyle);
        // filter
        if (filter1 != null) {
            addFilter((XSSFSheet) sheet, filter1.get(0), (XSSFCellStyle) cellStyle, 0);
            addFilter((XSSFSheet) sheet, filter1.get(1), (XSSFCellStyle) cellStyle, 3);
        }
        totalFilter1 = filter1 == null ? 0 : filter1.get(0).size();
        totalFilter2 = filter1 == null ? 0 : filter1.get(1).size();

        addHeaderExcel((XSSFSheet) sheet, data.get(0), (XSSFCellStyle) styleHeader);

        for (Map<String, Object> entity : data) {
            addRow((XSSFSheet) sheet, entity, (XSSFCellStyle) cellStyle);
        }

        autoAjustColumn(sheet, data.get(0));

        return workbook;
    }

    private void addRowWithKey(XSSFSheet sheet, Map<String, Object> map, XSSFCellStyle cellStyle) {
        Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
        int positionCell = 0;

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Cell cellKey = row.createCell(positionCell);
            Cell cellValue;
            Object value = null;

            setValueCelll(entry.getKey(), cellKey);
            cellKey.setCellStyle(cellStyle);
            positionCell++;

            cellValue = row.createCell(positionCell);
            if (entry.getValue() != null) {
                value = entry.getValue();
            }

            setValueCelll(value, cellValue);
            cellValue.setCellStyle(cellStyle);
            positionCell++;
        }
    }

    private void addRowEmpty(Workbook workbook, XSSFSheet sheet, int position) {
        Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
        int positionCell = position;
        Cell cellKey = row.createCell(positionCell);
        XSSFCellStyle cellStyle = ((XSSFWorkbook) workbook).createCellStyle();
        cellStyle.setWrapText(true);
        cellKey.setCellValue("");
        cellKey.setCellStyle(cellStyle);
    }

    private void addTituloExcel2(XSSFSheet sheet, String titulo, XSSFCellStyle cellStyle, int fila) {
        Row headerRow = sheet.createRow(fila);

        sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));
        headerRow.setHeightInPoints(25);
        int position = 0;
        Cell nameHeaderCell = headerRow.createCell(position);
        nameHeaderCell.setCellValue(titulo);
        nameHeaderCell.setCellStyle(cellStyle);

    }

    public Workbook mapToWorkbook(String titulo, List<Map<String, Object>> cabecera, Map map) {
        final String fileName = (String) map.get("fileName");
        List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = ((XSSFWorkbook) workbook).createSheet(fileName);
        int filaHeader = 0;

        if (data.isEmpty()) {
            return workbook;
        }

        // style cell
        CellStyle cellStyle = getDefaultXSSFCellStyle(workbook);

        // style Titulo

        //XSSFCellStyle cellStyleTitulo = ((XSSFWorkbook) workbook).createCellStyle();
        //CellStyle styleTitulo = this.getDefaultXSSFCellStyleTitulo(workbook, (XSSFCellStyle) cellStyleTitulo);
        //addTituloExcel((XSSFSheet) sheet, titulo, (XSSFCellStyle) styleTitulo, filaHeader);
       /* filaHeader++;
        addTituloExcel2((XSSFSheet) sheet, titulo, (XSSFCellStyle) styleTitulo,filaHeader);*/
        //addRowS((XSSFSheet) sheet, titulo2, (XSSFCellStyle) cellStyle,filaHeader);

        //addRowEmpty(workbook,(XSSFSheet) sheet,0);
        //cabeceras
     /*   CellStyle styleCabecera = this.getDefaultXSSFCellStyleCabecera(workbook);
        for (Map<String, Object> entity : cabecera) {
            addRowCabeceraDocliq((XSSFSheet) sheet, entity, (XSSFCellStyle) styleCabecera, 0);
            ///addRow((XSSFSheet) sheet, entity, (XSSFCellStyle) cellStyle, 0);

            filaHeader++;
        }*/

        //addRowEmpty(workbook,(XSSFSheet) sheet,0);
        // style header
       // filaHeader++;
        CellStyle styleHeader = getDefaultXSSFCellStyleHeader(workbook, (XSSFCellStyle) cellStyle);
        addHeaderExcel((XSSFSheet) sheet, data.get(0), (XSSFCellStyle) styleHeader, filaHeader);

        for (Map<String, Object> entity : data) {
            addRow((XSSFSheet) sheet, entity, (XSSFCellStyle) cellStyle, 0);
        }

        autoAjustColumn(sheet, data.get(0));

        return workbook;
    }

    public Workbook mapToWorkbook(String titulo, Map map) {
        final String fileName = (String) map.get("fileName");
        List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = ((XSSFWorkbook) workbook).createSheet(fileName);
        int filaHeader = 0;

        if (data.isEmpty()) {
            return workbook;
        }

        // style cell
        CellStyle cellStyle = getDefaultXSSFCellStyle(workbook);


        // style Titulo
        XSSFCellStyle cellStyleTitulo = ((XSSFWorkbook) workbook).createCellStyle();
        CellStyle styleTitulo = this.getDefaultXSSFCellStyleTitulo(workbook, (XSSFCellStyle) cellStyleTitulo);
        addTituloExcel((XSSFSheet) sheet, titulo, (XSSFCellStyle) styleTitulo, filaHeader);
        filaHeader++;
        //addTituloExcel2((XSSFSheet) sheet, titulo, (XSSFCellStyle) styleTitulo,filaHeader);
        //addRowS((XSSFSheet) sheet, titulo2, (XSSFCellStyle) cellStyle,filaHeader);

        addRowEmpty(workbook,(XSSFSheet) sheet,0);
        //cabeceras
     /*   CellStyle styleCabecera = this.getDefaultXSSFCellStyleCabecera(workbook);
        for (Map<String, Object> entity : cabecera) {
            addRowCabeceraDocliq((XSSFSheet) sheet, entity, (XSSFCellStyle) styleCabecera, 0);
            ///addRow((XSSFSheet) sheet, entity, (XSSFCellStyle) cellStyle, 0);

            filaHeader++;
        }*/

        //addRowEmpty(workbook,(XSSFSheet) sheet,0);
        // style header
        // filaHeader++;
        CellStyle styleHeader = getDefaultXSSFCellStyleHeader(workbook, (XSSFCellStyle) cellStyle);
        addHeaderExcel((XSSFSheet) sheet, data.get(0), (XSSFCellStyle) styleHeader, filaHeader);

        for (Map<String, Object> entity : data) {
            addRow((XSSFSheet) sheet, entity, (XSSFCellStyle) cellStyle, 0);
        }

        autoAjustColumn(sheet, data.get(0));

        return workbook;
    }


    public void addSheet(Workbook workbook,Map map,String titulo) {
        final String fileName = (String) map.get("fileName");
        List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");

        Sheet sheet = ((XSSFWorkbook) workbook).createSheet(fileName);

        int filaHeader = 0;
        XSSFCellStyle cellStyleTitulo = ((XSSFWorkbook) workbook).createCellStyle();
        CellStyle styleTitulo = this.getDefaultXSSFCellStyleTitulo(workbook, (XSSFCellStyle) cellStyleTitulo);
        addTituloExcel2((XSSFSheet) sheet, titulo, (XSSFCellStyle) styleTitulo,filaHeader);
        filaHeader++;
        addRowEmpty(workbook,(XSSFSheet) sheet,0);
        if (data.isEmpty()) {
            return;
        }

        // style cell
        CellStyle cellStyle = getDefaultXSSFCellStyle(workbook);

        CellStyle styleHeader = getDefaultXSSFCellStyleHeader(workbook, (XSSFCellStyle) cellStyle);
        addHeaderExcel((XSSFSheet) sheet, data.get(0), (XSSFCellStyle) styleHeader, filaHeader);

        for (Map<String, Object> entity : data) {
            addRow((XSSFSheet) sheet, entity, (XSSFCellStyle) cellStyle, 0);
        }

        autoAjustColumn(sheet, data.get(0));


    }


    private void autoAjustColumn(Sheet sheet, Map<String, Object> rowMap) {
        int size = rowMap.size();
        for (int i = 0; i < size; i++) {
            sheet.autoSizeColumn((short) i);
        }
    }

    private void addTituloExcel(XSSFSheet sheet, String titulo, XSSFCellStyle cellStyle, int fila) {
        Row headerRow = sheet.createRow(fila);
        headerRow.setHeightInPoints(25);
        int position = 2;

        for(int i=0;i<14;i++){
            Cell nameHeaderCell = headerRow.createCell(i);
            //nameHeaderCell.setCellValue(titulo);
            nameHeaderCell.setCellStyle(cellStyle);
            if(i==2){
                nameHeaderCell.setCellValue(titulo);
            }
        }

    }

    private void addHeaderExcel(XSSFSheet sheet, Map<String, Object> map, XSSFCellStyle cellStyle) {
        Row headerRow = sheet.createRow(ROW_NUMBERDOCLiq);
        headerRow.setHeightInPoints(25);
        int position = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Cell nameHeaderCell = headerRow.createCell(position);
            String nameHeader = (entry.getKey().indexOf('_') != -1) ? split(entry.getKey(), "_") : splitCamelCase(entry.getKey());
            nameHeaderCell.setCellValue(nameHeader.toUpperCase());
            nameHeaderCell.setCellStyle(cellStyle);
            position++;
        }
        sheet.createFreezePane(0, ROW_NUMBERDOCLiq + 1);
    }

    private void addHeaderExcel(XSSFSheet sheet, Map<String, Object> map, XSSFCellStyle cellStyle, int filaInicio) {
        Row headerRow = sheet.createRow(sheet.getPhysicalNumberOfRows());
        headerRow.setHeightInPoints(25);
        int position = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Cell nameHeaderCell = headerRow.createCell(position);
            String nameHeader = (entry.getKey().indexOf('_') != -1) ? split(entry.getKey(), "_") : splitCamelCase(entry.getKey());
            nameHeaderCell.setCellValue(nameHeader.toUpperCase());
            nameHeaderCell.setCellStyle(cellStyle);
            position++;
        }
        sheet.createFreezePane(0, sheet.getPhysicalNumberOfRows());
    }

    private void addRowCabecera(XSSFSheet sheet, Map<String, Object> map, XSSFCellStyle cellStyle, int row) {
        Row headerRow = sheet.createRow(sheet.getPhysicalNumberOfRows() + row);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            int position = 0;
            Cell cell = headerRow.createCell(position);
            Object titulo = null;
            if (entry.getKey() != null) {
                titulo = entry.getKey();
            }
            setValueCelll(titulo, cell);
            cell.setCellStyle(cellStyle);
            position++;

            cell = headerRow.createCell(position);
            Object value = null;
            if (entry.getValue() != null) {
                value = entry.getValue();
            }

            setValueCelll(value, cell);
            cell.setCellStyle(cellStyle);
        }
    }
    private void addRowCabeceraDocliq(XSSFSheet sheet, Map<String, Object> map, XSSFCellStyle cellStyle, int row) {




        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Row headerRow = sheet.createRow(sheet.getPhysicalNumberOfRows() + row);
            int position = 0;
            Cell cell = headerRow.createCell(position);
            Object titulo = null;
            if (entry.getKey() != null) {
                titulo = entry.getKey();
            }
            setValueCelll(titulo, cell);
            cell.setCellStyle(cellStyle);
            position++;

            cell = headerRow.createCell(position);
            Object value = null;
            if (entry.getValue() != null) {
                value = entry.getValue();
            }

            setValueCelll(value, cell);
            //cell.setCellStyle(cellStyle); //sin style
            //row++;
        }

    }

    private void addRow(XSSFSheet sheet, Map<String, Object> map, XSSFCellStyle cellStyle) {
        Row headerRow = sheet.createRow(sheet.getPhysicalNumberOfRows() + ROW_NUMBERDOCLiq - (totalFilter1 > totalFilter2 ? totalFilter1 : totalFilter2));
        int position = 0;

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Cell cell = headerRow.createCell(position);
            Object value = null;
            if (entry.getValue() != null) {
                value = entry.getValue();
            }

            setValueCelll(value, cell);
            cell.setCellStyle(cellStyle);
            position++;
        }
    }

    private void addRow(XSSFSheet sheet, Map<String, Object> map, XSSFCellStyle cellStyle, int row) {
        Row headerRow = sheet.createRow(sheet.getPhysicalNumberOfRows() + row);
        int position = 0;

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Cell cell = headerRow.createCell(position);
            Object value = null;
            if (entry.getValue() != null) {
                value = entry.getValue();
            }

            setValueCelll(value, cell);
            cell.setCellStyle(cellStyle);
            position++;
        }
    }
    private void addRowS(XSSFSheet sheet, String value, XSSFCellStyle cellStyle, int row) {
        Row headerRow = sheet.createRow(sheet.getPhysicalNumberOfRows() + row);
        int position = 0;


            Cell cell = headerRow.createCell(position);
            headerRow.setHeightInPoints(25);


            setValueCelll(value, cell);
            cell.setCellStyle(cellStyle);
            position++;

    }

    private void addFilter(XSSFSheet sheet, Map<String, Object> map, XSSFCellStyle cellStyle, int position) {
        int cont = 1;
        Row headerRow;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            headerRow = sheet.getRow((cont)) == null ? sheet.createRow((cont)) : sheet.getRow((cont));
            // field
            Cell nameFilterCell = headerRow.createCell(position);
            String nameFilter = (entry.getKey().indexOf('_') != -1) ? split(entry.getKey(), "_") : splitCamelCase(entry.getKey());
            nameFilterCell.setCellValue(nameFilter.toUpperCase());
            nameFilterCell.setCellStyle(cellStyle);
            // value
            Cell cell = headerRow.createCell((position + 1));
            Object value = null;
            if (entry.getValue() != null) {
                value = entry.getValue();
            }

            setValueCelll(value, cell);
            cell.setCellStyle(cellStyle);
            cont++;
        }
    }

    private XSSFCellStyle getDefaultXSSFCellStyle(Workbook workbook) {
        XSSFCellStyle cellStyle = ((XSSFWorkbook) workbook).createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private XSSFCellStyle getDefaultXSSFCellStyleCabecera(Workbook workbook) {
        XSSFCellStyle cellStyle = ((XSSFWorkbook) workbook).createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.NONE);
        cellStyle.setBorderTop(BorderStyle.NONE);
        cellStyle.setBorderRight(BorderStyle.NONE);
        cellStyle.setBorderLeft(BorderStyle.NONE);
        cellStyle.setWrapText(true);

        ///fuente en negrita
        Font font = workbook.createFont();
        font.setBold(true);
        //font.setColor(HSSFColor.BLACK.index);
        cellStyle.setFont(font);
        return cellStyle;
    }

    private XSSFCellStyle getDefaultXSSFCellStyleHeader(Workbook workbook, XSSFCellStyle cellStyle) {
        if (cellStyle == null) {
            cellStyle = getDefaultXSSFCellStyle(workbook);
        }

        XSSFCellStyle styleHeader = ((XSSFWorkbook) workbook).createCellStyle();
        styleHeader.cloneStyleFrom(cellStyle);
        //java.awt.Color colorFondo = new java.awt.Color(bean.getFondoColor01(), bean.getFondoColor02(), bean.getFondoColor03());
        styleHeader.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());


        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleHeader.setAlignment(HorizontalAlignment.CENTER);

        Font font = workbook.createFont();
        font.setBold(true);

        font.setColor(IndexedColors.BLUE.getIndex());

        styleHeader.setFont(font);
        return styleHeader;
    }

    private XSSFCellStyle getDefaultXSSFCellStyleTitulo(Workbook workbook, XSSFCellStyle cellStyle) {
        if (cellStyle == null) {
            cellStyle = getDefaultXSSFCellStyle(workbook);
        }
        cellStyle.setBorderBottom(BorderStyle.NONE);
        cellStyle.setBorderTop(BorderStyle.NONE);
        cellStyle.setBorderRight(BorderStyle.NONE);
        cellStyle.setBorderLeft(BorderStyle.NONE);
        cellStyle.setWrapText(false);

        XSSFCellStyle styleHeader = ((XSSFWorkbook) workbook).createCellStyle();
        styleHeader.cloneStyleFrom(cellStyle);
        //styleHeader.setFillForegroundColor();
        //styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleHeader.setAlignment(HorizontalAlignment.LEFT);

        Font font = workbook.createFont();
        font.setBold(true);
        //font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 14);
        styleHeader.setFont(font);
        return styleHeader;
    }

    public static String splitCamelCase(@NotNull String wordCamelCase) {
        if (wordCamelCase == null || wordCamelCase.isEmpty()) {
            return "";
        }
        StringBuilder text = new StringBuilder();
        String[] wordArray = wordCamelCase.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
        for (String word : wordArray) {
            text.append(word).append(" ");
        }
        return text.toString().trim();
    }

    public static String split(@NotNull String wordCamelCase, @NotNull String separater) {
        if (wordCamelCase == null || wordCamelCase.isEmpty() || separater == null || separater.isEmpty()) {
            return "";
        }
        StringBuilder text = new StringBuilder();
        String[] wordArray = wordCamelCase.split(separater);
        for (String word : wordArray) {
            text.append(word).append(" ");
        }
        return text.toString().trim();
    }

    private void setValueCelll(Object row, Cell cell) {
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
                cell.setCellValue(Double.parseDouble(NumberUtils.isNumber(value.toString()) ? value.toString() : "0.000"));
            } else {
                cell.setCellValue(value.toString());
            }
        } catch (Exception e) {
            cell.setCellValue(value.toString());
        }
    }
}
