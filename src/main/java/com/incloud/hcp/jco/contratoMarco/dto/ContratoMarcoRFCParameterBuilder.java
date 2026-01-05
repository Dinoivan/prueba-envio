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

public class ContratoMarcoRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ContratoMarcoRFCParameterBuilder.class);
    private static final String DOC_TYPE = "ZCMI";
    private static final String COMP_CODE = "SFER";
    private static final String VALOR_X = "X";
    private static final String TAX_CODE = "P0";
    private static final String PURCH_ORG = "1000";

    private ContratoMarcoRFCParameterBuilder() {

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
        jCoTableInputHeader.setValue("PUR_GROUP", proveedor.getCodigoGrupoCompra());  //ya no debe enviar dicho valor
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
        logger.error("ContratoMarcoRFCParameterBuilder Mapeando tabla input PI_POHEADER " + jCoTableInputHeader.toString());

        JCoTable jCoTableInputPoItem = jCoFunction.getTableParameterList().getTable("TO_POITEM");
        for (int i = 0; i < ccomparativoAdjudicadoList.size(); i++) {
            CcomparativoAdjudicado ccomparativoAdjudicado = ccomparativoAdjudicadoList.get(i);
            CotizacionDetalle cotizacionDetalle = ccomparativoAdjudicado.getCotizacionDetalle();
            logger.error("ContratoMarcoRFCParameterBuilder ccomparativoAdjudicado " + ccomparativoAdjudicado.toString());

            jCoTableInputPoItem.appendRow();
            jCoTableInputPoItem.setRow(i);
            Integer contador = new Integer(10 * (i + 1));
            String scontador = StringUtils.leftPad(contador.toString().trim(), 5, '0');
            jCoTableInputPoItem.setValue("ITEM_NO", scontador);
            jCoTableInputPoItem.setValue("MATERIAL", cotizacionDetalle.getBienServicio().getCodigoSap());
            jCoTableInputPoItem.setValue("PLANT", cotizacionDetalle.getLicitacionDetalle().getIdCentro().getCodigoSap());
            jCoTableInputPoItem.setValue("STGE_LOC", cotizacionDetalle.getLicitacionDetalle().getIdAlmacen().getCodigoSap());
            jCoTableInputPoItem.setValue("TARGET_QTY", ccomparativoAdjudicado.getCantidadReal());
            jCoTableInputPoItem.setValue("NET_PRICE", ccomparativoAdjudicado.getPrecioUnitario());
            jCoTableInputPoItem.setValue("TAX_CODE", TAX_CODE);

        }
        logger.error("ContratoMarcoRFCParameterBuilder Mapeando tabla input TO_POITEM " + jCoTableInputPoItem.toString());


    }


}
