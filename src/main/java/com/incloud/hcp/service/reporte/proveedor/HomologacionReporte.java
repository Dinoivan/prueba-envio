package com.incloud.hcp.service.reporte.proveedor;

import com.incloud.hcp.config.excel.ExcelDefault;
import com.incloud.hcp.domain.Homologacion;
import com.incloud.hcp.domain.HomologacionRespuesta;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Optional;

public class HomologacionReporte  {
    private final String NAME_SHEET = "HOMOLOGACION";
    private final String CONFIG_TITLE = "com/incloud/hcp/excel/Homologacion.xml";

    private HSSFWorkbook book;

    public HomologacionReporte(HSSFWorkbook book) {
        this.book = book;
    }

    public void agregar(List<HomologacionRespuesta> lista) {
        Optional<List<HomologacionRespuesta>> oList = Optional.ofNullable(lista);

        if (!oList.isPresent()) {
            return;
        }
        HSSFSheet sheet = book.createSheet();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, NAME_SHEET);
        ExcelDefault.createTitle(sheet, CONFIG_TITLE, book.createCellStyle(), book.createFont());

        lista.forEach(bean -> {
            Homologacion homologacion = bean.getHomologacion();
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            HSSFRow dataRow = sheet.createRow(i + 1);

            Optional<String> oTipoAdjunto = Optional.ofNullable(homologacion.getIndAdjunto()).map(tp -> {
                switch (tp) {
                    case "0":
                        return "NO";
                    case "1":
                        return "SI";
                    default:
                        return "NO";
                }
            });

            dataRow.createCell(0).setCellValue(homologacion.getLineaComercial().getDescripcion());
            dataRow.createCell(1).setCellValue(homologacion.getPregunta());
            if (Optional.ofNullable(homologacion.getPeso()).isPresent()) {
                dataRow.createCell(2).setCellValue(homologacion.getPeso().floatValue());
            }
            else {
                dataRow.createCell(2).setCellValue("");
            }
            dataRow.createCell(3).setCellValue(oTipoAdjunto.get());
            dataRow.createCell(4).setCellValue(bean.getRespuesta());
            if (Optional.ofNullable(bean.getPuntaje()).isPresent()) {
                dataRow.createCell(5).setCellValue(bean.getPuntaje().floatValue());
            }
            else {
                dataRow.createCell(5).setCellValue("");
            }

        });
        int totalColumn = sheet.getRow(0).getLastCellNum();
        for(int i=0; i < totalColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }
    }
}
