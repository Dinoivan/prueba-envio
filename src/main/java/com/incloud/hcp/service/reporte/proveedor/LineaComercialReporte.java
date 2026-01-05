package com.incloud.hcp.service.reporte.proveedor;

import com.incloud.hcp.config.excel.ExcelDefault;
import com.incloud.hcp.dto.reporte.LineaComercialDtoExcel;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Optional;

/**
 * Created by Administrador on 30/11/2017.
 */
public class LineaComercialReporte {
    private final String NAME_SHEET = "LINEA COMERCIAL";
    private final String CONFIG_TITLE = "com/incloud/hcp/excel/LineaComercial.xml";
    private HSSFWorkbook book;

    public LineaComercialReporte(HSSFWorkbook book) {
        this.book = book;
    }

    public void agregar(List<LineaComercialDtoExcel> list) {
        Optional<List<LineaComercialDtoExcel>> oList = Optional.ofNullable(list);

        if (!oList.isPresent()) {
            return;
        }

        HSSFSheet sheet = book.createSheet();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, NAME_SHEET);

        ExcelDefault.createTitle(sheet, CONFIG_TITLE, book.createCellStyle(), book.createFont());

        list.forEach(linea -> {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            HSSFRow dataRow = sheet.createRow(i + 1);

            dataRow.createCell(0).setCellValue(linea.getCodigoSap());
            dataRow.createCell(1).setCellValue(linea.getRuc());
            dataRow.createCell(2).setCellValue(linea.getRazonSocial());
            dataRow.createCell(3).setCellValue(linea.getLineaComercial());
            dataRow.createCell(4).setCellValue(linea.getFamilia());
            dataRow.createCell(5).setCellValue(linea.getSubFamilia());
            dataRow.createCell(6).setCellValue(linea.getOtraFamilia());
        });

        int totalColumn = sheet.getRow(0).getLastCellNum();
        for(int i=0; i < totalColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }
    }
}
