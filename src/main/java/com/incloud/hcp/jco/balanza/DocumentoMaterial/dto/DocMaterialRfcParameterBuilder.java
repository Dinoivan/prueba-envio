package com.incloud.hcp.jco.balanza.DocumentoMaterial.dto;

import com.incloud.hcp.jco.balanza.DocumentoMaterial.service.JCODocumentoMaterialService;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;

import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class DocMaterialRfcParameterBuilder {

    @Autowired
    private JCODocumentoMaterialService jcoDocumentoMaterialService;
    private static final Logger logger = LoggerFactory.getLogger(DocMaterialRfcParameterBuilder.class);

    public static void buildTraslado(JCoFunction jCoFunction, List<DocMaterialGenerarSAPDto> docDto) {

        //Date myDate = new Date();
        Calendar calendarActual = Calendar.getInstance();
        calendarActual.setTime(new Date());
        calendarActual.add(Calendar.HOUR, -5);
        Date myDate = new Date(calendarActual.getTime().getTime());
        logger.error("INICIO BUILDTRASLADO");

        JCoTable jCoTableInputHeader = jCoFunction.getTableParameterList().getTable("TI_CABECERA");
        DocMaterialGenerarSAPDto item = docDto.get(0);
        String numTicket = item.getTicketPesaje().split("-")[1];
        logger.error(".:TRASLADO LOG:. - Enviando header...");
        logger.error(".:TRASLADO LOG:. - item: " + item);
        jCoTableInputHeader.appendRow();
        jCoTableInputHeader.setValue("PSTNG_DATE", myDate);
        jCoTableInputHeader.setValue("DOC_DATE", myDate);
        jCoTableInputHeader.setValue("N_SERIE_GUIA", item.getSerieGuia());
        jCoTableInputHeader.setValue("N_GUIA",  item.getNroGuia());
        jCoTableInputHeader.setValue("N_TICKET", String.format("TK-%06d", Integer.parseInt(numTicket)));
        jCoTableInputHeader.setValue("HEADER_TXT", item.getReferenciaTE());

        // GM_CODE = 01 INGRESO
        // GM_CODE = 04 SALIDA
        logger.error(".:TRASLADO LOG:. - validacion de MOVE_PLANT: " + item.getExportaMoveplant());
        logger.error(".:TRASLADO LOG:. - validacion de MOVE_STLOC: " + item.getExportaMovestloc());
        logger.error(".:TRASLADO LOG:. - validacion de MOVE_BATCH: " + item.getLote());
        if(item.getTipoPesaje().equals("101")) {
            logger.error(".:TRASLADO LOG:. - Proceso de ingreso");
            jCoTableInputHeader.setValue("GM_CODE", "01");
        }
        if(item.getTipoPesaje().equals("351")) {
            logger.error(".:TRASLADO LOG:. - Proceso de salida");
            jCoTableInputHeader.setValue("GM_CODE", "04");
        }

        jCoTableInputHeader.setValue("PO_ITEM",item.getPosicion());
        jCoTableInputHeader.setValue("PO_NUMBER", item.getDocumentoTraslado());
        logger.error(".:TRASLADO LOG:. - Se envio el header");
        logger.error(".:TRASLADO LOG:. - header: " + jCoTableInputHeader);


        logger.error(".:TRASLADO LOG:. - Inicio de envio de detalles");
        JCoTable inputTable = jCoFunction.getTableParameterList().getTable("TI_MATERIAL");
        for(int i = 0; i < docDto.size(); i++){
            DocMaterialGenerarSAPDto itemDetalle = docDto.get(i);
            logger.error(".:TRASLADO LOG:. - itemDetalle: " + itemDetalle);
            inputTable.appendRow();
            inputTable.setRow(i);
            inputTable.setValue("MATERIAL", itemDetalle.getExportaMaterial());
            inputTable.setValue("PLANT", itemDetalle.getCentro());
            inputTable.setValue("STGE_LOC", itemDetalle.getAlmacen());
            inputTable.setValue("BATCH", itemDetalle.getLote());
            inputTable.setValue("MOVE_TYPE", itemDetalle.getTipoPesaje());
            inputTable.setValue("ENTRY_QNT", itemDetalle.getCantidad());
            inputTable.setValue("PO_ITEM",itemDetalle.getPosicion());
            inputTable.setValue("PO_NUMBER", itemDetalle.getDocumentoTraslado());
            inputTable.setValue("INDEX", i);
            inputTable.setValue("ENTRY_UOM", itemDetalle.getUnidadMedida());
            if(itemDetalle.getTipoPesaje().equals("351")){
                logger.error(".:TRASLADO LOG:. - Proceso de salida");
                logger.error(".:TRASLADO LOG:. - Tipo de pesaje 351");
                inputTable.setValue("MOVE_PLANT", itemDetalle.getExportaMoveplant());
                inputTable.setValue("MOVE_STLOC", itemDetalle.getExportaMovestloc());
                inputTable.setValue("MOVE_BATCH", itemDetalle.getLote());
            }
            if (itemDetalle.getTipoPesaje().equals("101")) {
                logger.error(".:TRASLADO LOG:. - Tipo de pesaje 101");
                Date date =new Date();
                int year = date.getYear() + 1900;
                logger.error(".:TRASLADO LOG:. - year: " + year);
                inputTable.setValue("REF_DOC_YR", year);
                inputTable.setValue("REF_DOC", itemDetalle.getDocumentoTraslado());
                inputTable.setValue("REF_DOC_IT", itemDetalle.getPosicion());
                inputTable.setValue("MVT_IND", "B");
            }
        }
        logger.error(".:TRASLADO LOG:. - Fin de envio de detalles");
        logger.error(".:TRASLADO LOG:. - inputTable: " + inputTable);
        logger.error("FIN BUILDTRASLADO");
    }

    public static void buildExportacion(JCoFunction jCoFunction, List<DocMaterialGenerarSAPDto> docDto) {


        Calendar calendarActual = Calendar.getInstance();
        calendarActual.setTime(new Date());
        calendarActual.add(Calendar.HOUR, -5);
        Date myDate = new Date(calendarActual.getTime().getTime());

        logger.error("INICIO BUILDEXPORTACION "+myDate);
        JCoTable jCoTableInputHeader = jCoFunction.getTableParameterList().getTable("TI_CABECERA");
        DocMaterialGenerarSAPDto item = docDto.get(0);
        String numTicket = item.getTicketPesaje().split("-")[1];
        logger.error(".:EXPORTACION LOG:. - Enviando header...");
        logger.error(".:EXPORTACION LOG:. - item: " + item);
        jCoTableInputHeader.appendRow();
        jCoTableInputHeader.setValue("PSTNG_DATE", myDate);
        jCoTableInputHeader.setValue("DOC_DATE", myDate);
        jCoTableInputHeader.setValue("GM_CODE", "04");
        jCoTableInputHeader.setValue("N_SERIE_GUIA", item.getSerieGuia());
        jCoTableInputHeader.setValue("N_GUIA", item.getNroGuia());
        jCoTableInputHeader.setValue("N_TICKET", String.format("TK-%06d", Integer.parseInt(numTicket)));
        jCoTableInputHeader.setValue("HEADER_TXT", item.getReferenciaTE());
        logger.error(".:EXPORTACION LOG:. - Se envio el header");
        logger.error(".:EXPORTACION LOG:. - header: " + jCoTableInputHeader);


        logger.error(".:EXPORTACION LOG:. - Inicio de envio de detalles");
        JCoTable inputTable = jCoFunction.getTableParameterList().getTable("TI_MATERIAL");
        for(int i = 0; i < docDto.size(); i++){
            DocMaterialGenerarSAPDto itemDetalle = docDto.get(i);
            logger.error(".:EXPORTACION LOG:. - itemDetalle: " + itemDetalle);
            inputTable.appendRow();
            inputTable.setRow(i);
            inputTable.setValue("MATERIAL", itemDetalle.getExportaMaterial());
            inputTable.setValue("PLANT", itemDetalle.getCentro());
            inputTable.setValue("STGE_LOC", itemDetalle.getAlmacen());
            inputTable.setValue("BATCH", itemDetalle.getLote());
            inputTable.setValue("MOVE_TYPE", itemDetalle.getTipoPesaje());
            inputTable.setValue("ENTRY_UOM", itemDetalle.getUnidadMedida());
            inputTable.setValue("ENTRY_QNT", itemDetalle.getCantidad());
            inputTable.setValue("MOVE_PLANT", itemDetalle.getExportaMoveplant());
            inputTable.setValue("MOVE_STLOC", itemDetalle.getExportaMovestloc());
            inputTable.setValue("MOVE_BATCH", itemDetalle.getLote());
        }
        logger.error(".:EXPORTACION LOG:. - Fin de envio de detalles");
        logger.error(".:EXPORTACION LOG:. - inputTable: " + inputTable);
        logger.error("FIN BUILDEXPORTACION");
    }
    public static void buildNacional(JCoFunction jCoFunction, List<DocMaterialGenerarSAPDto> docDto) throws Exception {

        //Date myDate = new Date();
        Calendar calendarActual = Calendar.getInstance();
        calendarActual.setTime(new Date());
        calendarActual.add(Calendar.HOUR, -5);
        Date myDate = new Date(calendarActual.getTime().getTime());

        logger.error("INICIO BUILDNACIONAL");
        List<DocMaterialGenerarSAPDto> docDtoHeaders = docsTrasladoAgrupados(docDto);
        logger.error(".:NACIONAL/VENTAS LOG:. - Resultado de agrupacion: " + docDtoHeaders);
        JCoTable jCoTableInputHeader = jCoFunction.getTableParameterList().getTable("TI_CABECERA");
        logger.error(".:NACIONAL/VENTAS LOG:. - Enviando headers");
        for(int i = 0; i < docDtoHeaders.size(); i++){
            DocMaterialGenerarSAPDto item = docDtoHeaders.get(i);
            String numTicket = item.getTicketPesaje().split("-")[1];
            logger.error(".:NACIONAL/VENTAS LOG:. - item: " + item);
            jCoTableInputHeader.appendRow();
            jCoTableInputHeader.setRow(i);
            jCoTableInputHeader.setValue("SHIP_POINT", item.getCentro());
            jCoTableInputHeader.setValue("DUE_DATE", myDate);
            jCoTableInputHeader.setValue("N_SERIE_GUIA", item.getSerieGuia());
            jCoTableInputHeader.setValue("REF_DOC", item.getNumeroPedido());
            jCoTableInputHeader.setValue("N_GUIA", item.getNroGuia());
            jCoTableInputHeader.setValue("N_TICKET", String.format("TK-%06d", Integer.parseInt(numTicket)));
            jCoTableInputHeader.setValue("BKTXT", item.getReferenciaNacional());
        }
        logger.error(".:NACIONAL/VENTAS LOG:. - Se enviaron los headers");
        logger.error(".:NACIONAL/VENTAS LOG:. - headers" + jCoTableInputHeader);


        logger.error(".:NACIONAL/VENTAS LOG:. - Inicio de envio de detalles");
        JCoTable inputTable = jCoFunction.getTableParameterList().getTable("TI_POSICIONES");
        for(int i = 0; i < docDto.size();i++) {
            DocMaterialGenerarSAPDto item = docDto.get(i);
            logger.error(".:NACIONAL/VENTAS LOG:. - item: " + item);
            inputTable.appendRow();
            inputTable.setRow(i);
            inputTable.setValue("REF_DOC", item.getNumeroPedido());
            inputTable.setValue("REF_ITEM", item.getPosicion());
            inputTable.setValue("DLV_QTY", item.getCantidad());
            inputTable.setValue("SALES_UNIT", item.getUnidadMedida());
            inputTable.setValue("SALES_UNIT_ISO", item.getNacionalSalesunitiso());
            inputTable.setValue("DELIV_NUMB", item.getNacionalDelivnumb());
            inputTable.setValue("RETURNS_DELIV_NUMB", item.getNacionalReturnsdelivnumb());
            inputTable.setValue("RETURNS_DELIV_ITEM", item.getNacionalReturnsdelivitem());
        }


        logger.error(".:NACIONAL/VENTAS LOG:. - Fin de envio de detalles");
        logger.error(".:NACIONAL/VENTAS LOG:. - inputTable: " + inputTable);
        logger.error("FIN BUILDNACIONAL");
    }

    public static void buildConsulta(JCoFunction jCoFunction, String docMaterial, String ejercicio) {

        logger.error("DocumentoMaterialRfcParameterBuilder: Inicio build");

        JCoParameterList input = jCoFunction.getImportParameterList();
        input.setValue("PI_MBLNR", docMaterial);
        input.setValue("PI_MJAHR", ejercicio);

        logger.error("DocumentoMaterialRfcParameterBuilder Mapeando tabla input T {}" + input);
    }


    public static List<DocMaterialGenerarSAPDto> docsTrasladoAgrupados(List<DocMaterialGenerarSAPDto> response) throws Exception {
        logger.error("INICIO DE AGRUPACION POR DOCTRASLADO");
        Map<Object, List<DocMaterialGenerarSAPDto>> result = response.stream()
                .collect(Collectors.groupingBy(x -> x.getNumeroPedido()));
        logger.error("DocMaterialGenerarSAPDto agrupado: " + result);

        List<String> docTrasladoList = new ArrayList<>();

        result.forEach((key, value) -> docTrasladoList.add((String) key));
        logger.error("Documentos Traslado encontrados: " + docTrasladoList);

        List<DocMaterialGenerarSAPDto> docMaterialGenerarSAPDtoList = new ArrayList<>();
        for(int i = 0; i < docTrasladoList.size(); i++){
            List<DocMaterialGenerarSAPDto> listItemAux = result.get(docTrasladoList.get(i));
            logger.error("Lista de DocMaterial por docTraslado: " + listItemAux);
            DocMaterialGenerarSAPDto itemAux = listItemAux.get(0);
            logger.error("DocMaterial a guardar: " + itemAux);
            docMaterialGenerarSAPDtoList.add(itemAux);
        }
        logger.error("Lista de docMaterial agrupados: " + docMaterialGenerarSAPDtoList);
        logger.error("FIN DE AGRUPACION POR DOCTRASLADO");
        return docMaterialGenerarSAPDtoList;
    }

    public static List<String> getDocsTrasladoLists(List<DocMaterialGenerarSAPDto> response) throws Exception {
        logger.error("INICIO DE EXTRACCION DE DOCTRASLADO ");
        Map<Object, List<DocMaterialGenerarSAPDto>> result = response.stream()
                .collect(Collectors.groupingBy(x -> x.getNumeroPedido()));
        logger.error("-ex- DocMaterialGenerarSAPDto agrupado: -ex-" + result);

        List<String> docTrasladoList = new ArrayList<>();

        result.forEach((key, value) -> docTrasladoList.add((String) key));
        logger.error("-ex- Documentos Traslado encontrados: -ex- " + docTrasladoList);

        return docTrasladoList;
    }
}
