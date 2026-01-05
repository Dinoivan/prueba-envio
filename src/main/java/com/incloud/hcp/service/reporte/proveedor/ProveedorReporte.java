package com.incloud.hcp.service.reporte.proveedor;

import com.incloud.hcp.config.excel.ExcelDefault;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.Ubigeo;
import com.incloud.hcp.rest.bean.ProveedorDatosGeneralesDTO;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Optional;

/**
 * Created by Administrador on 30/11/2017.
 */
public class ProveedorReporte {
    private final String NAME_SHEET = "PROVEEDORES";
    private final String CONFIG_TITLE = "com/incloud/hcp/excel/Documento.xml";
    //private final int NRO_COLUMNAS = 27;
    private HSSFWorkbook book;

    public ProveedorReporte(HSSFWorkbook book) {
        this.book = book;
    }

    public void agregar(List<ProveedorDatosGeneralesDTO> listProveedor) {
        Optional<List<ProveedorDatosGeneralesDTO>> oList = Optional.ofNullable(listProveedor);

        if (!oList.isPresent()) {
            return;
        }
        HSSFSheet sheet = book.createSheet();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, NAME_SHEET);
        ExcelDefault.createTitle(sheet, CONFIG_TITLE, book.createCellStyle(), book.createFont());

        listProveedor.forEach(beanProveedor -> {
            Proveedor proveedor = beanProveedor.getProveedor();
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            HSSFRow dataRow = sheet.createRow(i + 1);

            Optional<String> oTipoPersona = Optional.ofNullable(proveedor.getTipoPersona()).map(tp -> {
                switch (tp) {
                    case "J":
                        return "Jurídica";
                    case "N":
                        return "Nacional";
                    default:
                        return "-";
                }
            });
            Optional<String> oComunidad = Optional.ofNullable(proveedor.getIndProveedorComunidad())
                    .map(indicador -> {
                        switch (indicador) {
                            case "0":
                                return "No";
                            case "1":
                                return "Si";
                            default:
                                return "-";
                        }
                    });
            dataRow.createCell(0).setCellValue(proveedor.getRuc());
            dataRow.createCell(1).setCellValue(proveedor.getRazonSocial());
            dataRow.createCell(2).setCellValue(proveedor.getAcreedorCodigoSap());
            dataRow.createCell(3).setCellValue(Optional.ofNullable(proveedor.getTipoProveedor()).map(tp -> tp.getDescripcion()).orElse("-"));
            dataRow.createCell(4).setCellValue(oTipoPersona.orElse("-"));
            dataRow.createCell(5).setCellValue(proveedor.getEmail());
            dataRow.createCell(6).setCellValue(proveedor.getContacto());
            dataRow.createCell(7).setCellValue(proveedor.getTelefono());
            dataRow.createCell(8).setCellValue(oComunidad.orElse("-"));
            dataRow.createCell(9).setCellValue(Optional.ofNullable(proveedor.getPais()).map(Ubigeo::getDescripcion).orElse("-"));
            dataRow.createCell(10).setCellValue(Optional.ofNullable(proveedor.getRegion()).map(Ubigeo::getDescripcion).orElse("-"));
            dataRow.createCell(11).setCellValue(Optional.ofNullable(proveedor.getProvincia()).map(Ubigeo::getDescripcion).orElse("-"));
            dataRow.createCell(12).setCellValue(Optional.ofNullable(proveedor.getDistrito()).map(Ubigeo::getDescripcion).orElse("-"));
            dataRow.createCell(13).setCellValue(Optional.ofNullable(proveedor.getDistrito()).map(Ubigeo::getCodigoUbigeoSap).orElse("-"));
            dataRow.createCell(14).setCellValue(proveedor.getDireccionFiscal());
            dataRow.createCell(15).setCellValue(Optional.ofNullable(proveedor.getCondicionPago()).map(cp -> cp.getDescripcion()).orElse("-"));
            dataRow.createCell(16).setCellValue(Optional.ofNullable(proveedor.getMoneda()).map(m -> m.getTextoBreve()).orElse("-"));
            dataRow.createCell(17).setCellValue(Optional.ofNullable(proveedor.getTipoComprobante()).map(t -> t.getDescripcion()).orElse("-"));
            //dataRow.createCell(18).setCellValue(Optional.ofNullable(proveedor.getSectorTrabajo()).map(s -> s.getDescripcion()).orElse("-"));
            dataRow.createCell(19).setCellValue(Optional.ofNullable(proveedor.getIndHomologado()).map(ind -> "1".equals(ind)).orElse(Boolean.FALSE));
            dataRow.createCell(20).setCellValue(Optional.ofNullable(proveedor.getEvaluacionDesempeno()).map(eval -> eval.doubleValue()).orElse(0d));
            dataRow.createCell(21).setCellValue(Optional.ofNullable(proveedor.getEvaluacionHomologacion()).map(eval -> eval.doubleValue()).orElse(0d));
            dataRow.createCell(22).setCellValue(beanProveedor.getNoConformesRegistrados());
            dataRow.createCell(23).setCellValue(Optional.ofNullable(proveedor.getIndBlackList()).map(ind -> "1".equals(ind)).orElse(Boolean.FALSE));
            dataRow.createCell(24).setCellValue(beanProveedor.getLicitacionesGanadas());
            dataRow.createCell(25).setCellValue(beanProveedor.getLicitacionesPerdidas());
            dataRow.createCell(26).setCellValue(beanProveedor.getLicitacionesParticipo());
            dataRow.createCell(27).setCellValue(beanProveedor.getProveedor().getEmailRetencion());

        });
        int totalColumn = sheet.getRow(0).getLastCellNum();
        for(int i=0; i < totalColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }
    }
}
