package com.incloud.hcp.jco.contratoMarco.dto;

import com.incloud.hcp.domain.*;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class ContratoMarcoServicioRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ContratoMarcoServicioRFCParameterBuilder.class);
    private static final String DOC_TYPE = "WK";
    private static final String COMP_CODE = "SFER";
    private static final String VALOR_X = "X";

    private static final String TAX_CODE = "P0";
    private static final String ITEM_CAT = "9";
    private static final String ACCTASSCAT = "U";
    private static final String PURCH_ORG = "1000";

    private ContratoMarcoServicioRFCParameterBuilder() {

    }

    public static void build(JCoFunction jCoFunction,
                             String claseDocumento,
                             String fechaIniContrato,
                             String fechaFinContrato,
                             BigDecimal valorAcumulado,
                             String fechaActual,
                             Proveedor proveedor,
                             Usuario usuario,
                             Licitacion licitacion,
                             List<CcomparativoAdjudicado> ccomparativoAdjudicadoList) {
        Cotizacion cotizacion = ccomparativoAdjudicadoList.get(0).getCotizacionDetalle().getCotizacion();

        JCoStructure jCoTableInputHeader = jCoFunction.getImportParameterList().getStructure("PI_POHEADER");
        jCoTableInputHeader.setValue("COMP_CODE", COMP_CODE);
        if (StringUtils.isBlank(claseDocumento)) {
            jCoTableInputHeader.setValue("DOC_TYPE", DOC_TYPE);
        }
        else {
            jCoTableInputHeader.setValue("DOC_TYPE", claseDocumento);
        }
        jCoTableInputHeader.setValue("CREAT_DATE", fechaActual);
        jCoTableInputHeader.setValue("PURCH_ORG", PURCH_ORG);
        jCoTableInputHeader.setValue("PUR_GROUP", proveedor.getCodigoGrupoCompra());
        jCoTableInputHeader.setValue("VENDOR", proveedor.getAcreedorCodigoSap());
        jCoTableInputHeader.setValue("CURRENCY", cotizacion.getMoneda().getCodigoMoneda());
        jCoTableInputHeader.setValue("VPER_START", fechaIniContrato);
        jCoTableInputHeader.setValue("VPER_END", fechaFinContrato);
        jCoTableInputHeader.setValue("ACUM_VALUE", valorAcumulado);
        jCoTableInputHeader.setValue("CREATED_BY", usuario.getCodigoSap());

        Integer annoLicitacion = licitacion.getAnioLicitacion();
        Integer nroLicitacion = licitacion.getNroLicitacion();
        String nroLicitacionString = annoLicitacion.toString().trim() + StringUtils.leftPad(nroLicitacion.toString().trim(), 6, '0');
        jCoTableInputHeader.setValue("REF_1", nroLicitacionString);
        logger.error("ContratoMarcoServicioRFCParameterBuilder Mapeando tabla input PI_POHEADER " + jCoTableInputHeader.toString());


        JCoTable jCoTableInputPoItem = jCoFunction.getTableParameterList().getTable("TO_POITEM");
        logger.error("ContratoMarcoServicioRFCParameterBuilder ccomparativoAdjudicadoList " + ccomparativoAdjudicadoList.size());

        for (int i = 0; i < ccomparativoAdjudicadoList.size(); i++) {
            CcomparativoAdjudicado ccomparativoAdjudicado = ccomparativoAdjudicadoList.get(i);
            CotizacionDetalle cotizacionDetalle = ccomparativoAdjudicado.getCotizacionDetalle();
            logger.error("ContratoMarcoServicioRFCParameterBuilder ccomparativoAdjudicadoList " + ccomparativoAdjudicadoList.toString());
            logger.error("ContratoMarcoServicioRFCParameterBuilder cotizacionDetalle " + cotizacionDetalle.toString());
            jCoTableInputPoItem.appendRow();
            jCoTableInputPoItem.setRow(i);
            Integer contador = new Integer(10 * (i + 1));
            String scontador = StringUtils.leftPad(contador.toString().trim(), 5, '0');
            jCoTableInputPoItem.setValue("ITEM_NO", scontador);
            jCoTableInputPoItem.setValue("SHORT_TEXT", cotizacionDetalle.getDescripcion());
            jCoTableInputPoItem.setValue("MATL_GROUP", cotizacionDetalle.getBienServicio().getRubroBien().getCodigoSap());
            jCoTableInputPoItem.setValue("TARGET_QTY", ccomparativoAdjudicado.getCantidadReal());
            jCoTableInputPoItem.setValue("PO_UNIT", cotizacionDetalle.getUnidadMedida().getCodigoSap());
            jCoTableInputPoItem.setValue("NET_PRICE", cotizacionDetalle.getPrecioUnitario());
            jCoTableInputPoItem.setValue("TAX_CODE", TAX_CODE);
            jCoTableInputPoItem.setValue("ITEM_CAT", ITEM_CAT);
            jCoTableInputPoItem.setValue("ACCTASSCAT", ACCTASSCAT);
            jCoTableInputPoItem.setValue("EMATERIAL", cotizacionDetalle.getBienServicio().getCodigoSap());

        }
        logger.error("ContratoMarcoServicioRFCParameterBuilder Mapeando tabla input TO_POITEM " + jCoTableInputPoItem.toString());


    }


}
