package com.incloud.hcp.service.reporte.proveedor;

import com.incloud.hcp.config.excel.ExcelDefault;
import com.incloud.hcp.dto.reporte.CuentaBancoDtoExcel;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Optional;

/**
 * Created by Administrador on 30/11/2017.
 */
public class CuentaBancoReporte  {
    private final String NAME_SHEET = "CUENTA BANCARIA";
    private final String CONFIG_TITLE = "com/incloud/hcp/excel/CuentaBanco.xml";
    private HSSFWorkbook book;

    public CuentaBancoReporte(HSSFWorkbook book) {
        this.book = book;
    }

    public void agregar(List<CuentaBancoDtoExcel> list) {
        Optional<List<CuentaBancoDtoExcel>> oList = Optional.ofNullable(list);

        if (!oList.isPresent()) {
            return;
        }

        HSSFSheet sheet = book.createSheet();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, NAME_SHEET);

        ExcelDefault.createTitle(sheet, CONFIG_TITLE, book.createCellStyle(), book.createFont());

        list.forEach(sucursal -> {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            HSSFRow dataRow = sheet.createRow(i + 1);

            dataRow.createCell(0).setCellValue(sucursal.getCodigoSap());
            dataRow.createCell(1).setCellValue(sucursal.getRuc());
            dataRow.createCell(2).setCellValue(sucursal.getRazonSocial());
            dataRow.createCell(3).setCellValue(sucursal.getBanco());
            dataRow.createCell(4).setCellValue(sucursal.getTipoCuenta());
            dataRow.createCell(5).setCellValue(sucursal.getNumeroCuenta());
            dataRow.createCell(6).setCellValue(sucursal.getCodigoCci());
            dataRow.createCell(7).setCellValue(sucursal.getMoneda());
            dataRow.createCell(8).setCellValue(sucursal.getContacto());
        });

        int totalColumn = sheet.getRow(0).getLastCellNum();
        for(int i=0; i < totalColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }
    }
}
