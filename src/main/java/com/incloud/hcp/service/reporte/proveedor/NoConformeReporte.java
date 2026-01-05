package com.incloud.hcp.service.reporte.proveedor;

import com.incloud.hcp.config.excel.ExcelDefault;
import com.incloud.hcp.domain.AprobadorSolicitud;
import com.incloud.hcp.domain.SolicitudBlacklist;
import com.incloud.hcp.enums.EstadoBlackListEnum;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by USER on 01/12/2017.
 */
public class NoConformeReporte {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String NAME_SHEET = "NO_CONFORMES";
    private final String CONFIG_TITLE = "com/incloud/hcp/excel/NoConformes.xml";
    private HSSFWorkbook book;

    public NoConformeReporte(HSSFWorkbook book) {
        this.book = book;
    }

    public void agregar(List<SolicitudBlacklist> listSolicitudBlacklist) {
        Optional<List<SolicitudBlacklist>> oList = Optional.ofNullable(listSolicitudBlacklist);

        if (!oList.isPresent()) {
            return;
        }

        Integer maxAprobador = listSolicitudBlacklist.stream().map(l -> l.getListAprobador()).map(l -> l.size()).max(Integer::compare).get();

        HSSFSheet sheet = book.createSheet();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, NAME_SHEET);
        ExcelDefault.createTitle(sheet, CONFIG_TITLE, book.createCellStyle(), book.createFont());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        //Adicionar cabeceras dinámicas
        List<String> header = new ArrayList<String>();
        for (int x=1; x <= maxAprobador; x++){
            header.add("APROBADOR" + x);
        }
        ExcelDefault.addTitleDynamic(sheet, header, book.createCellStyle(), book.createFont());

        listSolicitudBlacklist.forEach(solicitud -> {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            HSSFRow dataRow = sheet.createRow(i + 1);

            dataRow.createCell(0).setCellValue(solicitud.getProveedor().getAcreedorCodigoSap());
            dataRow.createCell(1).setCellValue(solicitud.getProveedor().getRuc());
            dataRow.createCell(2).setCellValue(solicitud.getProveedor().getRazonSocial());
            dataRow.createCell(3).setCellValue(solicitud.getCriteriosBlacklist().getTipoSolicitudBlacklist().getDescripcion());
            dataRow.createCell(4).setCellValue(solicitud.getCodigoSolicitud());
            dataRow.createCell(5).setCellValue(solicitud.getMotivo());
            dataRow.createCell(6).setCellValue(formatter.format(solicitud.getFechaCreacion()));
            dataRow.createCell(7).setCellValue(this.getEstadoSolicitudString(solicitud.getEstadoSolicitud()));
            short j = dataRow.getLastCellNum();
            for(AprobadorSolicitud aprobador : solicitud.getListAprobador()){
                dataRow.createCell(j).setCellValue(aprobador.getUsuario().getNombre() + " " + aprobador.getUsuario().getApellido());
                j++;
            }
        });

        int totalColumn = sheet.getRow(0).getLastCellNum();
        for(int i=0; i < totalColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }
    }

    public String getEstadoSolicitudString(String codigoEstado){

        String estado = "";
        if (codigoEstado.equals(EstadoBlackListEnum.GENERADA.getCodigo())){
            estado = EstadoBlackListEnum.GENERADA.getTexto();
        }else if (codigoEstado.equals(EstadoBlackListEnum.PENDIENTE_APROBACION.getCodigo())){
            estado = EstadoBlackListEnum.PENDIENTE_APROBACION.getTexto();
        }else if (codigoEstado.equals(EstadoBlackListEnum.APROBADA.getCodigo())){
            estado = EstadoBlackListEnum.APROBADA.getTexto();
        }else if (codigoEstado.equals(EstadoBlackListEnum.RECHAZADA.getCodigo())){
            estado = EstadoBlackListEnum.RECHAZADA.getTexto();
        }else if (codigoEstado.equals(EstadoBlackListEnum.RECHAZADA_ADMIN.getCodigo())){
            estado = EstadoBlackListEnum.RECHAZADA_ADMIN.getTexto();
        }

        return estado;
    }


}
