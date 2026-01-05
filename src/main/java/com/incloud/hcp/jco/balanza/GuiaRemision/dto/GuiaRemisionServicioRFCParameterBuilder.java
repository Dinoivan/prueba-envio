package com.incloud.hcp.jco.balanza.GuiaRemision.dto;

import com.incloud.hcp.repository.TipoProductoRepository;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GuiaRemisionServicioRFCParameterBuilder {
    private static final Logger logger = LoggerFactory.getLogger(GuiaRemisionServicioRFCParameterBuilder.class);

    public static void build(JCoFunction jCoFunction, GuiaRemisionResponseDTO guiaRemision,
                             List<GuiaRemisionPosResponseDTO> guiaRemisionPos, String dniChofer, String licencia) {

        logger.error("grabarGuiaRemision builder - guiaRemision: " + guiaRemision);
        logger.error("grabarGuiaRemision builder - guiaRemisionPos: " + guiaRemisionPos);
        logger.error("grabarGuiaRemision builder - dniChofer: " + dniChofer);
//        JCoStructure jCoTableInputHeader = jCoFunction.getImportParameterList().getStructure("ZPE_MM_GUIAREMISION");
        JCoStructure jCoTableInputHeader = jCoFunction.getImportParameterList().getStructure("PI_CABECERA");
        String ticket = String.format("TK-%06d", Integer.parseInt(guiaRemision.getTicketPesaje()));
        logger.error("Inicio de seteo de valores");
        jCoTableInputHeader.setValue("SERIE_GUIA", guiaRemision.getSerieGuia());
        jCoTableInputHeader.setValue("NRO_GUIA", guiaRemision.getNroGuia());
//        jCoTableInputHeader.setValue("TIPO_GR", guiaRemision.getTipoGuia().charAt(1));
        jCoTableInputHeader.setValue("TIPO_GR", guiaRemision.getTipoGuia());
        if (guiaRemision.getTipoMovimiento() != null || guiaRemision.getTipoMovimiento() != "") {
            jCoTableInputHeader.setValue("TIPO_GUIA", "GR");
        } else {
            jCoTableInputHeader.setValue("TIPO_GUIA", "");
        }
        jCoTableInputHeader.setValue("MOT_TRASLADO", guiaRemision.getMotTraslado());
        jCoTableInputHeader.setValue("TEXTO_GUIA", guiaRemision.getTextoGuia());
        jCoTableInputHeader.setValue("TICKET_PESAJE", guiaRemision.getTicketPesaje());
        jCoTableInputHeader.setValue("RAZON_SOCIAL", guiaRemision.getRazonSocial());
        jCoTableInputHeader.setValue("RUC", guiaRemision.getRuc());
        jCoTableInputHeader.setValue("ORI_CENTRO", guiaRemision.getOriCentro());
        jCoTableInputHeader.setValue("ORI_ALMACEN", guiaRemision.getOriAlmacen());
        jCoTableInputHeader.setValue("ORI_PROVEEDOR", guiaRemision.getOriProveedor());
        jCoTableInputHeader.setValue("ORI_DIR_STREET", guiaRemision.getOriDirStreet());
        jCoTableInputHeader.setValue("ORI_DIR_HOUSE_NUM1", guiaRemision.getOriDirHouseNum1());
        jCoTableInputHeader.setValue("ORI_DIR_HOUSE_NUM2", guiaRemision.getOriDirHouseNum2());
        jCoTableInputHeader.setValue("ORI_DIR_STR_SUPPL2", guiaRemision.getOriDirStrSuppl2());
        jCoTableInputHeader.setValue("ORI_DIR_REGION", guiaRemision.getOriDirRegion());
        jCoTableInputHeader.setValue("ORI_DIR_BEZEI", guiaRemision.getOriDirBezei());
        jCoTableInputHeader.setValue("ORI_DIR_CITY1", guiaRemision.getOriDirCity1());
        jCoTableInputHeader.setValue("ORI_DIR_CITY2", guiaRemision.getOriDirCity2());
        jCoTableInputHeader.setValue("DES_CENTRO", guiaRemision.getDesCentro());
        jCoTableInputHeader.setValue("DES_ALMACEN", guiaRemision.getDesAlmacen());
        jCoTableInputHeader.setValue("DES_CLIENTE", guiaRemision.getDesCliente());
        jCoTableInputHeader.setValue("DES_PROVEEDOR", guiaRemision.getDesProveedor());
        jCoTableInputHeader.setValue("ZINDEST", guiaRemision.getTipoDestino());
        jCoTableInputHeader.setValue("LFDNR", guiaRemision.getLfdnr());
        jCoTableInputHeader.setValue("LAND1", guiaRemision.getLand1());
        jCoTableInputHeader.setValue("ZINDORI", guiaRemision.getTipoOrigen());
        jCoTableInputHeader.setValue("TRANSPORT", guiaRemision.getTransport());
        jCoTableInputHeader.setValue("DES_DIR_STREET", guiaRemision.getDesDirStreet());
        jCoTableInputHeader.setValue("DES_DIR_HOUSE_NUM1", guiaRemision.getDesDirHouseNum1());
        jCoTableInputHeader.setValue("DES_DIR_HOUSE_NUM2", guiaRemision.getDesDirHouseNum2());
        jCoTableInputHeader.setValue("DES_DIR_STR_SUPPL2", guiaRemision.getDesDirStrSuppl2());
        jCoTableInputHeader.setValue("DES_DIR_REGION", guiaRemision.getDesDirRegion());
        jCoTableInputHeader.setValue("DES_DIR_BEZEI", guiaRemision.getDesDirBezei());
        jCoTableInputHeader.setValue("DES_DIR_CITY1", guiaRemision.getDesDirCity1());
        jCoTableInputHeader.setValue("DES_DIR_CITY2", guiaRemision.getDesDirCity2());
        jCoTableInputHeader.setValue("PLACA_TRANSPORTE", guiaRemision.getPlacaTransporte());
        jCoTableInputHeader.setValue("PLACA_CARRETA", guiaRemision.getPlacaCarreta());
        jCoTableInputHeader.setValue("ZBALANZA", ticket);
        jCoTableInputHeader.setValue("ZPROCESO", guiaRemision.getzProceso());
        jCoTableInputHeader.setValue("ZREFERENCIA", guiaRemision.getzReferencia());
        jCoTableInputHeader.setValue("ZPRODUCTO", guiaRemision.getzProducto());
        jCoTableInputHeader.setValue("ZFETRAS", guiaRemision.getFechaTraslado());
        jCoTableInputHeader.setValue("CR_NAME", guiaRemision.getUsuarioCreador());
        jCoTableInputHeader.setValue("CR_DATE", guiaRemision.getFechaCreacion());
        jCoTableInputHeader.setValue("CR_TIME", guiaRemision.getHoraCreacion());
        jCoTableInputHeader.setValue("ZMOTRAS", guiaRemision.getIdmotTraslado());
        jCoTableInputHeader.setValue("GRTXT", guiaRemision.getIdmotOtro());
        jCoTableInputHeader.setValue("MODTRANSP", guiaRemision.getModoTransporte());
        jCoTableInputHeader.setValue("WTARA", guiaRemision.getPesoTara());
        jCoTableInputHeader.setValue("WNETTO", guiaRemision.getPesoNeto());
        jCoTableInputHeader.setValue("WGROSS", guiaRemision.getPesoBruto());
        jCoTableInputHeader.setValue("UM", guiaRemision.getUnidadMedida());
        jCoTableInputHeader.setValue("NROBULTOS", guiaRemision.getNumeroBulto());
        jCoTableInputHeader.setValue("INDSERVICIO", guiaRemision.getIndicadorServicio());
        jCoTableInputHeader.setValue("TIPOMOV", guiaRemision.getTipoMovimiento());
        jCoTableInputHeader.setValue("TIPOCONV", guiaRemision.getTipoTransaccion());

        jCoTableInputHeader.setValue("PRECINTO", guiaRemision.getPresintoAduanero());
        jCoTableInputHeader.setValue("NROCONTEN", guiaRemision.getNumContenedor());
        jCoTableInputHeader.setValue("CODDAM", guiaRemision.getCodigoDam());
        jCoTableInputHeader.setValue("PESO_SELECCION", guiaRemision.getPesoSeleccion());
        jCoTableInputHeader.setValue("TIPOLOCAL", guiaRemision.getTipoLocacion());
        jCoTableInputHeader.setValue("ID_EX_PTOLLEGADA", guiaRemision.getPuertoLlegada());
        jCoTableInputHeader.setValue("CODLOCAL", guiaRemision.getAeropuerto());
        jCoTableInputHeader.setValue("SUSTENTO_DIFEREN", guiaRemision.getSustentoDiferencia());
        jCoTableInputHeader.setValue("CONSTDETRAC", guiaRemision.getConstanciaDetraccion());
        jCoTableInputHeader.setValue("BUKRS", guiaRemision.getSociedad());



        jCoTableInputHeader.setValue("CHOFER_DNI", dniChofer);
        jCoTableInputHeader.setValue("DR_LICENSE", licencia);

        logger.error("Fin de seteo de valores");
        logger.error("GuiaRemisionServicioRFCParameterBuilder Mapeando tabla input ZPE_MM_GUIAREMISON " + jCoTableInputHeader);

//        JCoTable jCoTableInputPoItem = jCoFunction.getTableParameterList().getTable("ZPE_MM_GUIAREMISON_POS");
        JCoTable jCoTableInputPoItem = jCoFunction.getTableParameterList().getTable("TI_POSICIONES");

        for (int i = 0; i < guiaRemisionPos.size(); i++) {

            GuiaRemisionPosResponseDTO guiaRemisionDetalle = guiaRemisionPos.get(i);
            logger.error("guiaRemisionDetalle " + i + " :");
            logger.error("GuiaRemisionServicioRFCParameterBuilder guiaRemisionPos " + guiaRemisionPos.toString());
            jCoTableInputPoItem.appendRow();
            jCoTableInputPoItem.setRow(i);

            Integer contador = new Integer(10 * (i + 1));
            String scontador = StringUtils.leftPad(contador.toString().trim(), 5, '0');

            jCoTableInputPoItem.setValue("NUM_POS", scontador);
            jCoTableInputPoItem.setValue("DOC_MATERIAL", guiaRemisionDetalle.getDocMaterial());
            jCoTableInputPoItem.setValue("EJERCICIO", guiaRemisionDetalle.getEjercicio());
            //jCoTableInputPoItem.setValue("POSICION", guiaRemisionDetalle.getPosicion());
            jCoTableInputPoItem.setValue("POSICION", guiaRemisionDetalle.getPosicionDocumento());
            jCoTableInputPoItem.setValue("MATERIAL", guiaRemisionDetalle.getMaterial());
            jCoTableInputPoItem.setValue("DESCRIPCION", guiaRemisionDetalle.getCodigoTipoProducto());
            jCoTableInputPoItem.setValue("LOTE", guiaRemisionDetalle.getLote());
            jCoTableInputPoItem.setValue("CANT_DISPONIBLE", guiaRemisionDetalle.getCantDisponible());
            jCoTableInputPoItem.setValue("UM", guiaRemisionDetalle.getUm());
            jCoTableInputPoItem.setValue("ZSUBTICKET", guiaRemisionDetalle.getSubticket());
            jCoTableInputPoItem.setValue("FEC_PROD", guiaRemisionDetalle.getFechaProduccion());
            jCoTableInputPoItem.setValue("CODDAM", guiaRemisionDetalle.getDam());
        }
        logger.error("GuiaRemisionServicioRFCParameterBuilder Mapeando tabla input TO_POITEM " + jCoTableInputPoItem.toString());
    }
}
