package com.incloud.hcp.jco.prefactura.service.impl;

import com.incloud.hcp.dto.PrefacturaActualizarDto;
import com.incloud.hcp.jco.prefactura.dto.*;
import com.incloud.hcp.jco.prefactura.service.JCOPrefacturaService;
import com.incloud.hcp.sap.SapLog;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JCOPrefacturaServiceImpl implements JCOPrefacturaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Override
    public PrefacturaRFCResponseDto registrarPrefacturaRFC(PrefacturaRFCRequestDto prefacturaRFCRequestDto) throws Exception {
        try {
            logger.error("PREFACTURA RFC /// REQUEST: " + prefacturaRFCRequestDto.toString());
            String FUNCION_RFC = "ZPE_MM_PREREG_FACTS";
            PrefacturaRFCResponseDto responseDto = new PrefacturaRFCResponseDto();
            List<SapLog> sapMessageList = new ArrayList<>();

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            this.mapFilters(jCoFunction, prefacturaRFCRequestDto);
            jCoFunction.execute(destination);

            JCoParameterList exportParameterList = jCoFunction.getExportParameterList();
            String codigoDocumentoSap = exportParameterList.getString("E_BELNR");
            String ejercicio = exportParameterList.getString("E_GJAHR");
            String numeroDocumentoContable = exportParameterList.getString("E_BKPFBELNR");
            logger.error("PREFACTURA RFC /// codigoDocumentoSap: " + codigoDocumentoSap);
            logger.error("PREFACTURA RFC /// ejercicio: " + ejercicio);
            logger.error("PREFACTURA RFC /// numeroDocumentoContable: " + numeroDocumentoContable);
            responseDto.setCodigoDocumentoSap(codigoDocumentoSap);
            responseDto.setEjercicio(ejercicio);
            responseDto.setNumeroDocumentoContable(numeroDocumentoContable);

            JCoParameterList exportTableList = jCoFunction.getTableParameterList();
            JCoTable table = exportTableList.getTable("OT_RETURN");

            if (table != null && !table.isEmpty()) {
                do {
                    SapLog sapMessage = new SapLog();

                    sapMessage.setCode(table.getString("TYPE"));
                    sapMessage.setMesaj(table.getString("MESSAGE"));

                    sapMessageList.add(sapMessage);
                } while (table.nextRow());
            }

            responseDto.setSapMessageList(sapMessageList);
            logger.error("PREFACTURA RFC /// RESPONSE: " + responseDto.toString());
            return responseDto;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }


    @Override
    public List<PrefacturaAnuladaDto> obtenerPrefacturaAnuladaListRFC(String fechaInicio, String fechaFin) throws Exception {
        try {
            String FUNCION_RFC = "ZPE_MM_FACTURAS_ANULADAS";

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();
            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);

            this.mapFacturasAnuladasFilters(jCoFunction, fechaInicio, fechaFin);
            jCoFunction.execute(destination);

            JCoParameterList exportTableList = jCoFunction.getTableParameterList();
            GenericDtoExtractorMapper genericDtoExtractorMapper = GenericDtoExtractorMapper.newMapper(exportTableList);

            return genericDtoExtractorMapper.getPrefacturaAnuladaDtoList();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }


    @Override
    public List<PrefacturaRegistradaSapDto> obtenerPrefacturaRegistradaSapListRFC(List<PrefacturaActualizarDto> prefacturaActualizarDtoList) throws Exception {
        try {
            String FUNCION_RFC = "ZPE_MM_CARGA_FACTURAS";

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();
            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);

            JCoParameterList paramList = jCoFunction.getImportParameterList();
            JCoTable inputJCoTable = paramList.getTable("IT_MM_RUC_XBLNR");

            for (int i = 0; i < prefacturaActualizarDtoList.size(); i++) {
                PrefacturaActualizarDto prefacturaActualizar = prefacturaActualizarDtoList.get(i);
                inputJCoTable.appendRow();
                inputJCoTable.setRow(i);
                inputJCoTable.setValue("STCD1", prefacturaActualizar.getRucProveedor());
                inputJCoTable.setValue("XBLNR", prefacturaActualizar.getReferenciaSap());
            }

            jCoFunction.execute(destination);

            JCoParameterList exportTableList = jCoFunction.getTableParameterList();
            GenericDtoExtractorMapper genericDtoExtractorMapper = GenericDtoExtractorMapper.newMapper(exportTableList);

            return genericDtoExtractorMapper.getPrefacturaRegistradaSapDtoList();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }



    private void mapFilters(JCoFunction function, PrefacturaRFCRequestDto prefacturaRFCRequestDto) {
        JCoParameterList paramList = function.getImportParameterList();

//        paramList.setValue("I_DOC_TYPE", "01"); // tipo de documento (01=facttura)

        String sociedad = prefacturaRFCRequestDto.getSociedad();
        if (sociedad != null && !sociedad.isEmpty())
            paramList.setValue("I_COMP_CODE", sociedad);

        String referencia = prefacturaRFCRequestDto.getReferencia();
        if (referencia != null && !referencia.isEmpty())
            paramList.setValue("I_REF_DOC_NO", referencia);

        String fechaEmision = prefacturaRFCRequestDto.getFechaEmision();
        if (fechaEmision != null && !fechaEmision.isEmpty())
            paramList.setValue("I_DOC_DATE", fechaEmision);

        String fechaContabilizacion = prefacturaRFCRequestDto.getFechaContabilizacion();
        if (fechaContabilizacion != null && !fechaContabilizacion.isEmpty())
            paramList.setValue("I_PSTNG_DATE", fechaContabilizacion);

        String fechaBase = prefacturaRFCRequestDto.getFechaBase();
        if (fechaBase != null && !fechaBase.isEmpty())
            paramList.setValue("I_BLINE_DATE", fechaBase);

        String indicadorImpuesto = prefacturaRFCRequestDto.getIndicadorImpuesto();
        if (indicadorImpuesto != null && !indicadorImpuesto.isEmpty())
            paramList.setValue("I_TAX_CODE", indicadorImpuesto);

        BigDecimal baseImponibleTotal = prefacturaRFCRequestDto.getBaseImponibleTotal();
        if (baseImponibleTotal != null)
            paramList.setValue("I_GROSS_AMOUNT", baseImponibleTotal);

//        BigDecimal igvTotal = prefacturaRFCRequestDto.getIgvTotal();
//        if (igvTotal != null)
//            paramList.setValue("I_EXCH_RATE", igvTotal);

//        BigDecimal retencionTotal = prefacturaRFCRequestDto.getRetencionTotal();
//        if (retencionTotal != null)
//            paramList.setValue("I_XXXXX", retencionTotal);

        String textoCabecera = prefacturaRFCRequestDto.getTextoCabecera();
        if (textoCabecera != null && !textoCabecera.isEmpty())
            paramList.setValue("I_HEADER_TXT", textoCabecera);

        String usuarioRegistroSap = prefacturaRFCRequestDto.getUsuarioRegistroSap();
        if (usuarioRegistroSap != null && !usuarioRegistroSap.isEmpty())
            paramList.setValue("I_USNAM", usuarioRegistroSap);

//        String moneda = prefacturaRFCRequestDto.getMoneda();
//        if (moneda != null && !moneda.isEmpty())
//            paramList.setValue("I_XXXXX", moneda);

        JCoTable ocPosicionJCoTable = paramList.getTable("IT_ITEMS");
        List<PrefacturaRFCPosicionDto> posicionDtoList = prefacturaRFCRequestDto.getPrefacturaRFCPosicionDtoList();

        for (int i = 0; i < posicionDtoList.size(); i++) {
            PrefacturaRFCPosicionDto posicionDto = posicionDtoList.get(i);

            ocPosicionJCoTable.appendRow();
            ocPosicionJCoTable.setRow(i);
            ocPosicionJCoTable.setValue("INVOICE_DOC_ITEM", String.format("%06d", (i+1)*10));
            ocPosicionJCoTable.setValue("PO_NUMBER", posicionDto.getNumeroOrdenCompra());
            ocPosicionJCoTable.setValue("PO_ITEM", posicionDto.getNumeroPosicion());
            ocPosicionJCoTable.setValue("QUANTITY", posicionDto.getCantidadFacturada());
            ocPosicionJCoTable.setValue("PO_UNIT", posicionDto.getUnidadMedida());
            ocPosicionJCoTable.setValue("ITEM_AMOUNT", posicionDto.getValorFacturado());

            if(posicionDto.getTipoDocumentoAceptacion().equals("M")){ // --> EM
                ocPosicionJCoTable.setValue("REF_DOC", posicionDto.getNumeroDocumentoAceptacion());
                ocPosicionJCoTable.setValue("REF_DOC_IT", posicionDto.getNumeroItem());
                ocPosicionJCoTable.setValue("REF_DOC_YEAR", posicionDto.getYearEmision());
            }
            else{ // tipo "S" --> HES
                ocPosicionJCoTable.setValue("SHEET_NO", posicionDto.getNumeroDocumentoAceptacion());
                ocPosicionJCoTable.setValue("SHEET_ITEM", posicionDto.getNumeroItem());
            }
        }
    }


    private void mapFacturasAnuladasFilters(JCoFunction function, String fechaInicio, String fechaFin) {
        JCoParameterList paramList = function.getImportParameterList();

        if (!fechaInicio.isEmpty() && !fechaFin.isEmpty()){
            JCoTable jcoTableBUDAT = paramList.getTable("IP_BUDAT");
            jcoTableBUDAT.appendRow();
            jcoTableBUDAT.setRow(0);
            jcoTableBUDAT.setValue("SIGN", "I");
            jcoTableBUDAT.setValue("LOW", fechaInicio);

            if (fechaInicio.equals(fechaFin)) {
                jcoTableBUDAT.setValue("OPTION", "EQ");
            } else {
                jcoTableBUDAT.setValue("OPTION", "BT");
                jcoTableBUDAT.setValue("HIGH", fechaFin);
            }
        }
    }
}