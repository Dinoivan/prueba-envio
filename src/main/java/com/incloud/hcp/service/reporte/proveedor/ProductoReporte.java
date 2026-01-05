package com.incloud.hcp.service.reporte.proveedor;

import com.incloud.hcp.config.excel.ExcelDefault;
import com.incloud.hcp.dto.reporte.ProductoDtoExcel;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Optional;

/**
 * Created by Administrador on 30/11/2017.
 */
public class ProductoReporte  {
    private final String NAME_SHEET = "PRODUCTOS";
    private final String CONFIG_TITLE = "com/incloud/hcp/excel/Producto.xml";
    private HSSFWorkbook book;

    public ProductoReporte(HSSFWorkbook book) {
        this.book = book;
    }

    public void agregar(List<ProductoDtoExcel> list) {
        Optional<List<ProductoDtoExcel>> oList = Optional.ofNullable(list);

        if (!oList.isPresent()) {
            return;
        }

        HSSFSheet sheet = book.createSheet();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, NAME_SHEET);

        ExcelDefault.createTitle(sheet, CONFIG_TITLE, book.createCellStyle(), book.createFont());

        list.forEach(producto -> {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            HSSFRow dataRow = sheet.createRow(i + 1);

            dataRow.createCell(0).setCellValue(producto.getCodigoSap());
            dataRow.createCell(1).setCellValue(producto.getRuc());
            dataRow.createCell(2).setCellValue(producto.getRazonSocial());
            dataRow.createCell(3).setCellValue(producto.getMarca());
            dataRow.createCell(4).setCellValue(producto.getProductoServicio());
            dataRow.createCell(5).setCellValue(producto.getDescripcionAdicional());
        });

        int totalColumn = sheet.getRow(0).getLastCellNum();
        for(int i=0; i < totalColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }
    }
}
