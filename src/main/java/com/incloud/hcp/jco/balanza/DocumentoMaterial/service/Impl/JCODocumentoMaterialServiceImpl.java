package com.incloud.hcp.jco.balanza.DocumentoMaterial.service.Impl;

import com.incloud.hcp.jco.balanza.DocumentoMaterial.dto.*;
import com.incloud.hcp.jco.balanza.DocumentoMaterial.service.JCODocumentoMaterialService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCODocumentoMaterialServiceImpl implements JCODocumentoMaterialService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String FUNCION_RFC_CONSULTA_EXP = "ZFPE_MM_CONSULTA_DOCMAT_EXP";
    private final String FUNCION_RFC_CONSULTA_PNAC = "ZFPE_MM_CONSULTA_DOCMAT_PNAC";
    private final String FUNCION_RFC_CONSULTA_PTRA = "ZFPE_MM_CONSULTA_DOCMAT_PTRA";
    private final String NOMBRE_TABLA_RPTA_RFC_CONSULTA = "TO_MSEG";

    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;

    @Override
    public List<DocumentoMaterialResponseDto> generarDocumentoMaterialRfc(List<DocMaterialGenerarSAPDto> docMaterialGenerarDto)
            throws Exception {

        logger.error("GENERAR DOCUMENTO MATERIAL RFC - docMaterialGenerarDTO: " + docMaterialGenerarDto);
        List<DocumentoMaterialResponseDto> documentoMaterialResponseDtoList = new ArrayList<>();
        DocumentoMaterialResponseDto returnDto = new DocumentoMaterialResponseDto();

        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("generarDocumentoMaterialRfc Inicio ");

        for (DocMaterialGenerarSAPDto i : docMaterialGenerarDto){
            if(i.getTipoPesaje()!=null){
                logger.error("generarDocumentoMaterialRfc tipopesaje:{}", i.getTipoPesaje());
            }else{
                logger.error("generarDocumentoMaterialRfc tipopesaje: null ");
            }
        }
//        String numTicket = docMaterialGenerarDto.get(0).getTicketPesaje().split("-")[1];
//        docMaterialGenerarDto.get(0).setTicketPesaje();
        Integer numPosiciones = docMaterialGenerarDto.size();
        logger.error("numPosiciones " + numPosiciones);
        //PEDIDO DE TRASLADO
        if(docMaterialGenerarDto.get(0).getTipoPesaje().equals("351") || docMaterialGenerarDto.get(0).getTipoPesaje().equals("101")){

            JCoRepository repo = destination.getRepository();
            logger.error("01A - generandoDocumentoMaterial-TRASLADO - INICIO");
            JCoFunction jCoFunction = repo.getFunction("ZFPE_SD_PEDIDO_TRANSPOR");
            logger.error("TRASLADO jCoFunction: " + jCoFunction);

            DocMaterialRfcParameterBuilder.buildTraslado(
                    jCoFunction,
                    docMaterialGenerarDto
            );

            jCoFunction.execute(destination);
            logger.error("01C - GET generarDocumentoDto");
            logger.error("generarDocumentoMaterialRfc jcoFunction{}",jCoFunction);

            JCoTable outputTableToMat = jCoFunction.getTableParameterList().getTable("TO_MAT");

            JCoTable outputTableTiMaterial = jCoFunction.getTableParameterList().getTable("TI_MATERIAL");
            logger.error("outputTableToMat - TRASLADO 001: " + outputTableToMat);
            logger.error("outputTableTiMaterial - TRASLADO 002: " + outputTableTiMaterial);
            if(outputTableToMat.isEmpty()){
                logger.error("outputTableToMat vacio");
            } else {
                if(outputTableTiMaterial != null && !outputTableTiMaterial.isEmpty()){
                    Integer index = 0;
                    do {
                        DocumentoMaterialResponseDto item = new DocumentoMaterialResponseDto();
                        item.setDocumentoMaterial(outputTableToMat.getString("PO_EXPPURCHASEORDER"));
                        item.setEjercio(outputTableToMat.getString("PO_EXPHEADER"));
                        item.setZeile(outputTableTiMaterial.getString("ZEILE"));
                        item.setPoNumber(outputTableToMat.getString("PO_NUMBER"));
                        item.setPoItem(outputTableToMat.getString("PO_ITEM"));
                        item.setPoMenge(outputTableTiMaterial.getString("MENGE"));
                        item.setPoMeins(outputTableTiMaterial.getString("MEINS"));
                        item.setMensajeError("Documento Material creado");
                        item.setSubticket(docMaterialGenerarDto.get(index).getTicketPesaje());
                        documentoMaterialResponseDtoList.add(item);
                        index++;
                    } while (outputTableTiMaterial.nextRow());
                }
                logger.error("DocumentoMaterialResponseDto item:  - EXPORTACION" + returnDto);
            }
            logger.error("outputTableToMat - TRASLADO 002: " + outputTableToMat);

            JCoTable outputTable = jCoFunction.getTableParameterList().getTable("TO_RETURN");

            if (outputTable.isEmpty()){
                logger.error("outputTable vacio - TRASLADO");
            }else {
                logger.error("ERROR AL CREAR DOCUMENTO MATERIA - TRASLADO");
                documentoMaterialResponseDtoList.clear();
                logger.error("outputTable - TRASLADO:{} ", outputTable);
                returnDto.setEjercio("");
                returnDto.setNumDelivery("");
                returnDto.setDocumentoMaterial("");
                returnDto.setZeile("");
                for (int i = 0; i < numPosiciones; i++) {
                    returnDto.setMensajeError(outputTable.getString("MESSAGE"));
                    documentoMaterialResponseDtoList.add(returnDto);
                }
            }
            logger.error("LISTA DOCUMENTOS MATERIAL - TRASLADO: " + documentoMaterialResponseDtoList);
            logger.error("01A - generandoDocumentoMaterial-TRASLADO - FIN");
        // Pedido exportacion
        }else if(docMaterialGenerarDto.get(0).getTipoPesaje().equals("301")){

            JCoRepository repo = destination.getRepository();
            logger.error("01A - DESTINATION:{}", destination);
            logger.error("01A - generandoDocumentoMaterial-EXPORTACION");
            JCoFunction jCoFunction = repo.getFunction("ZFPE_SD_PEDIDO_EXPORTACION");
            logger.error("EXPORTACION jCoFunction: " + jCoFunction);
            logger.error("EXPORTACION BUILDEXPORTACION - INICIO");
            DocMaterialRfcParameterBuilder.buildExportacion(
                    jCoFunction,
                    docMaterialGenerarDto
            );
            logger.error("EXPORTACION BUILDEXPORTACION - FIN");

            logger.error("EXPORTACION EXECUTE - INICIO");
            jCoFunction.execute(destination);
            logger.error("EXPORTACION EXECUTE - FIN");

            logger.error("generarDocumentoMaterialRfc jcoFunction{}",jCoFunction);


            JCoTable outputTableToMat = jCoFunction.getTableParameterList().getTable("TO_MAT");

            JCoTable outputTableTiMaterial = jCoFunction.getTableParameterList().getTable("TI_MATERIAL");
            logger.error("outputTableToMat - EXPORTACION 001: " + outputTableToMat);
            logger.error("outputTableTiMaterial - EXPORTACION 002: " + outputTableTiMaterial);
            if(outputTableToMat.isEmpty()){
                logger.error("outputTableToMat vacio");
            } else {
                if(outputTableTiMaterial != null && !outputTableTiMaterial.isEmpty()){
                    Integer index = 0;
                    do {
                        DocumentoMaterialResponseDto item = new DocumentoMaterialResponseDto();
                        item.setDocumentoMaterial(outputTableToMat.getString("PO_DOCUMENTO_MAT"));
                        item.setEjercio(outputTableToMat.getString("PO_DOCUMENTO_ANO"));
                        item.setZeile(outputTableTiMaterial.getString("ZEILE"));
                        item.setPoMenge(outputTableTiMaterial.getString("MENGE"));
                        item.setPoMeins(outputTableTiMaterial.getString("MEINS"));
                        item.setMensajeError("Documento Material creado");
                        item.setSubticket(docMaterialGenerarDto.get(index).getTicketPesaje());
                        documentoMaterialResponseDtoList.add(item);
                        index++;
                    } while (outputTableTiMaterial.nextRow());
                }
                logger.error("DocumentoMaterialResponseDto item:  - EXPORTACION" + returnDto);
            }

            logger.error("DocumentoMaterialResponseDto item: - EXPORTACION" + returnDto);

            JCoTable outputTable = jCoFunction.getTableParameterList().getTable("TO_RETURN");
            if (outputTable.isEmpty()){
                logger.error("outputTable vacio - EXPORTACION");
                logger.error("DocMaterialCreado - EXPORTACION: " + documentoMaterialResponseDtoList);
            }else{
                logger.error("ERROR AL CREAR UN DOCUMENTO MATERIAL - EXPORTACION");
                documentoMaterialResponseDtoList.clear();
                logger.error("outputTable:{}", outputTable);
                returnDto.setEjercio("");
                returnDto.setNumDelivery("");
                returnDto.setDocumentoMaterial("");
                returnDto.setZeile("");
                for (int i = 0; i < numPosiciones; i++) {
                    returnDto.setMensajeError(outputTable.getString("MESSAGE"));
                    documentoMaterialResponseDtoList.add(returnDto);
                }
            }
            logger.error("LISTA DOCUMENTOS MATERIAL - EXPORTACION: " + documentoMaterialResponseDtoList);
        }
        else if(docMaterialGenerarDto.get(0).getTipoPesaje().equals("927")){
            //ventas o nacional
            JCoRepository repo = destination.getRepository();
            logger.error("01A - generandoDocumentoMaterial-NAC");
            JCoFunction jCoFunction = repo.getFunction("ZFPE_SD_PEDIDO_NACIONAL");
            logger.error("NACIONAL jCoFunction: " + jCoFunction);
            DocMaterialRfcParameterBuilder.buildNacional(
                    jCoFunction,
                    docMaterialGenerarDto
            );

            jCoFunction.execute(destination);
            logger.error("01C - GET generarDocumentoDto - NACIONAL");
            logger.error("generarDocumentoMaterialRfc jcoFunction{}",jCoFunction);
            JCoTable outputTableToMat = jCoFunction.getTableParameterList().getTable("TO_MAT");
            logger.error("generarDocumentoMaterialRfc  - NACIONAL/VENTAS input: {}",outputTableToMat);
            List<String> docTraslados = DocMaterialRfcParameterBuilder.getDocsTrasladoLists(docMaterialGenerarDto);
            logger.error("docsTraslados agrupados -b: " + docTraslados);
            for(int i = 0; i<docMaterialGenerarDto.size(); i++){
                DocumentoMaterialResponseDto item = new DocumentoMaterialResponseDto();
                outputTableToMat.appendRow();
                outputTableToMat.setRow(i);
                item.setNumDelivery(outputTableToMat.getString("PO_DELIVERY"));
                item.setEjercio(outputTableToMat.getString("PO_MAT_YEAR"));
                item.setDocumentoMaterial(outputTableToMat.getString("PO_MAT_DOC"));
                item.setPoMeins(outputTableToMat.getString("PO_MEINS"));
                item.setPoMenge(outputTableToMat.getString("PO_MENGE"));
                item.setZeile(outputTableToMat.getString("PO_ZEILE"));
                item.setPedidoVenta(docMaterialGenerarDto.get(i).getNumeroPedido());
                documentoMaterialResponseDtoList.add(item);
            }
            logger.error("DocumentoMaterialResponseDto outputTableToMat: " + outputTableToMat);
            JCoTable outputTable = jCoFunction.getTableParameterList().getTable("TO_RETURN");
            if (outputTable.isEmpty()){
                logger.error("DOCUMENTO MATERIAL CREADO - NACIONAL/VENTAS");
                for(int i = 0; i<docMaterialGenerarDto.size();i++){
                    documentoMaterialResponseDtoList.get(i).setMensajeError("Documento Material creado");
                    documentoMaterialResponseDtoList.get(i).setSubticket(docMaterialGenerarDto.get(i).getTicketPesaje());
                }
                logger.error("DocMaterialCreado - NACIONAL/VENTAS: " + documentoMaterialResponseDtoList);
            }else {
                logger.error("DOCUMENTO MATERIAL ERROR - NACIONAL/VENTAS");
                logger.error("NACIONAL/VENTAS outputTable: " + outputTable);
                documentoMaterialResponseDtoList.clear();
                for(int i = 0; i<docMaterialGenerarDto.size(); i++){
                    outputTable.appendRow();
                    outputTable.setRow(i);
                    DocumentoMaterialResponseDto item = new DocumentoMaterialResponseDto();
                    item.setMensajeError(outputTable.getString("MESSAGE"));
                    item.setEjercio("");
                    item.setNumDelivery("");
                    item.setDocumentoMaterial("");
                    item.setPedidoVenta(docMaterialGenerarDto.get(i).getNumeroPedido());
                    documentoMaterialResponseDtoList.add(item);

                }

            }
            logger.error("LISTA DOCUMENTOS MATERIAL - NACIONAL/VENTAS: " + documentoMaterialResponseDtoList);
        }else if(docMaterialGenerarDto.get(0).getTipoPesaje().equals("100")){
            returnDto.setMensajeError("Tipo Pesaje libre");
            returnDto.setNumDelivery("");
            returnDto.setEjercio("");
            returnDto.setDocumentoMaterial("");
            returnDto.setZeile("");
            returnDto.setSubticket(docMaterialGenerarDto.get(0).getTicketPesaje());
            documentoMaterialResponseDtoList.add(returnDto);
        }

        logger.error("RETURN documentoMaterialResponseDtoList: " + documentoMaterialResponseDtoList);
        return documentoMaterialResponseDtoList;
    }

    @Override
    public List<DocMaterialConsultaResponse> consultaDocumentoMaterialRFC(DocMaterialConsultaDto docMaterial)
            throws Exception{

        logger.error("01A - consultaDocumentoMaterial : Inicio");
        if(docMaterial!=null){
            logger.error("01A - consultaDocumentoMaterial : documentoMaterial:{}, ejercicio:{}, tipoPesaje:{}"
                    ,docMaterial.getDocMaterial(), docMaterial.getEjercicio(), docMaterial.getTipoPesaje());
        }
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - consultaDocumentoMaterial");
        String funcionRFC="";

        switch(Objects.requireNonNull(docMaterial).getTipoPesaje()){
            case 1:
                funcionRFC = FUNCION_RFC_CONSULTA_EXP;
                break;
            case 2:
                funcionRFC = FUNCION_RFC_CONSULTA_PNAC;
                break;
            case 3:
                funcionRFC = FUNCION_RFC_CONSULTA_PTRA;
                break;
            default:
                funcionRFC = FUNCION_RFC_CONSULTA_EXP;
        }

        JCoFunction jCoFunction = repo.getFunction(funcionRFC);

        List<DocMaterialConsultaResponse> listDocMaterial = new ArrayList<DocMaterialConsultaResponse>();

        DocMaterialRfcParameterBuilder.buildConsulta(
                jCoFunction,
                docMaterial.getDocMaterial(),
                docMaterial.getEjercicio()
        );

        logger.error("01C - GET consultaDocumentoMaterial ejecuta jcoFunction");
        jCoFunction.execute(destination);

        JCoTable table = jCoFunction.getTableParameterList().getTable(NOMBRE_TABLA_RPTA_RFC_CONSULTA);
        if (table != null && !table.isEmpty()) {
            do {
                try{
                    DocMaterialConsultaResponse doc = new DocMaterialConsultaResponse();
                    doc.setPos(table.getString("POS"));
                    doc.setProduct(table.getString("PRODUCT"));
                    doc.setSupplier(table.getString("SUPPLIER"));
                    doc.setDimension(table.getString("DIMENSIONS"));
                    doc.setWeight(table.getString("WEIGHT"));
                    doc.setPrice(table.getString("PRICE"));
                    doc.setCurrency(table.getString("CURRENCY"));
                    logger.error("DocumentoMaterial ingresado: {}", doc);
                    listDocMaterial.add(doc);

                }catch (Exception e){
                    return listDocMaterial;
                }
            }while (table.nextRow());
        }
        return listDocMaterial;
    }


}
