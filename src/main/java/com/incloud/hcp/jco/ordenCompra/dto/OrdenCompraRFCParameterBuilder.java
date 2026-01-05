package com.incloud.hcp.jco.ordenCompra.dto;

import com.incloud.hcp.domain.*;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrdenCompraRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(OrdenCompraRFCParameterBuilder.class);
    private static final String PURCH_ORG = "1000";
    private static final String STATUS = "9";
    private static final String DOC_TYPE = "ZCMA";
    private static final String COMP_CODE = "SFER";
    private static final String VALOR_X = "X";


    private OrdenCompraRFCParameterBuilder() {
    }

    public static void build(JCoFunction jCoFunction,
                             String claseDocumento,
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
        jCoTableInputHeader.setValue("PUR_GROUP", proveedor.getCodigoGrupoCompra());
        jCoTableInputHeader.setValue("STATUS", STATUS);
        jCoTableInputHeader.setValue("CREAT_DATE", fechaActual);
        jCoTableInputHeader.setValue("VENDOR", proveedor.getAcreedorCodigoSap());
        jCoTableInputHeader.setValue("PURCH_ORG", PURCH_ORG);
        jCoTableInputHeader.setValue("CURRENCY", cotizacion.getMoneda().getCodigoMoneda());
        jCoTableInputHeader.setValue("CREATED_BY", usuario.getCodigoSap());
        Integer annoLicitacion = licitacion.getAnioLicitacion();
        Integer nroLicitacion = licitacion.getNroLicitacion();
        String nroLicitacionString = annoLicitacion.toString().trim() + StringUtils.leftPad(nroLicitacion.toString().trim(), 6, '0');
        jCoTableInputHeader.setValue("COLLECT_NO", nroLicitacionString);

        logger.error("OrdenCompraRFCParameterBuilder Mapeando tabla input PI_POHEADER " + jCoTableInputHeader.toString());

        JCoTable jCoTableInputPoItem = jCoFunction.getTableParameterList().getTable("TO_POITEM");
        logger.error("OrdenCompraRFCParameterBuildeR INICIO PRUEBA");
        for (int i = 0; i < ccomparativoAdjudicadoList.size(); i++) {
            CcomparativoAdjudicado ccomparativoAdjudicado = ccomparativoAdjudicadoList.get(i);
            CotizacionDetalle cotizacionDetalle = ccomparativoAdjudicado.getCotizacionDetalle();
            jCoTableInputPoItem.appendRow();
            jCoTableInputPoItem.setRow(i);
            Integer contador = new Integer(10 * (i + 1));
            String scontador = StringUtils.leftPad(contador.toString().trim(), 5, '0');
            jCoTableInputPoItem.setValue("PO_ITEM", scontador);
            jCoTableInputPoItem.setValue("MATERIAL", cotizacionDetalle.getBienServicio().getCodigoSap());
            jCoTableInputPoItem.setValue("PLANT", cotizacionDetalle.getLicitacionDetalle().getIdCentro().getCodigoSap());
            jCoTableInputPoItem.setValue("QUANTITY", ccomparativoAdjudicado.getCantidadReal());
            jCoTableInputPoItem.setValue("PO_UNIT", cotizacionDetalle.getUnidadMedida().getCodigoSap());
            jCoTableInputPoItem.setValue("NET_PRICE", ccomparativoAdjudicado.getPrecioUnitario());
            jCoTableInputPoItem.setValue("PRICE_UNIT", new Integer(1));
            jCoTableInputPoItem.setValue("PREQ_NO", cotizacionDetalle.getSolicitudPedido());
            jCoTableInputPoItem.setValue("PREQ_ITEM", cotizacionDetalle.getLicitacionDetalle().getPosicionSolicitudPedido());
            logger.error("OrdenCompraRFCParameterBuilder ccomparativoAdjudicado: " + ccomparativoAdjudicado.toString());

        }
        logger.error("OrdenCompraRFCParameterBuilder Mapeando tabla input TO_POITEM " + jCoTableInputPoItem.toString());

    }
}
