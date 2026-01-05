package com.incloud.hcp.service.reporte.licitacion;

import com.incloud.hcp.bean.LicitacionRequest;
import com.incloud.hcp.config.excel.ExcelDefault;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class EstadoReporte {

    private final String NAME_SHEET = "ESTADO";
    private final String CONFIG_TITLE = "com/incloud/hcp/excel/licitacion/EstadoExcel.xml";
    private static final String STRING_HYPHEN = "-";
    private HSSFWorkbook book;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    public EstadoReporte(HSSFWorkbook book) {
        this.book = book;
    }

    public void agregar(LicitacionRequest licitacionRequest) {
        LicitacionRequest licitacionRequestData = licitacionRequest;

     /*   if (!licitacionRequestData.isPresent()) {
            return;
        }*/

        HSSFSheet sheet = book.createSheet();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, NAME_SHEET);
        logger.error("EXCEL DATA"+licitacionRequest.toString());
        ExcelDefault.createTitle(sheet, CONFIG_TITLE, book.createCellStyle(), book.createFont());

            int lastRow = sheet.getLastRowNum();
            logger.error("LAST ROW" + lastRow);
            //int i = lastRow < 0 ? 0 : lastRow;
            HSSFRow dataRow = sheet.createRow(lastRow + 1);

            dataRow.createCell(0).setCellValue(Optional.ofNullable(licitacionRequestData.getNroLicitacionString()).orElse(STRING_HYPHEN));

            dataRow.createCell(1).setCellValue(Optional.ofNullable(licitacionRequestData.getFechaRecepcionOferta()).orElse(STRING_HYPHEN));

            dataRow.createCell(2).setCellValue(Optional.ofNullable(licitacionRequestData.getFechaUltimaRenegociacion()).orElse(STRING_HYPHEN));
            String estado=Optional.ofNullable(licitacionRequestData.getEstadoLicitacion())
                .map(codigo -> {
                    switch (licitacionRequestData.getEstadoLicitacion()) {
                        case "PE":
                            return "PENDIENTE DE RESPUESTA";
                        case "GE":
                            return "GENERADA";
                        case "EV":
                            return "EN EVALUACION";
                        case "AD":
                            return "ADJUDICADA";
                        case "AN":
                            return "ANULADA";
                        default:
                            return "-";
                    }
                }).orElse(STRING_HYPHEN);

            dataRow.createCell(3).setCellValue(estado);
            dataRow.createCell(4).setCellValue(Optional.ofNullable(licitacionRequestData.getFechaPublicacion()).orElse(STRING_HYPHEN));


       int totalColumn = sheet.getRow(0).getLastCellNum();
       logger.error("total Columna" + totalColumn);
        for(int j=0; j < totalColumn; j++) {
            sheet.autoSizeColumn(j, true);
        }
    }

}
