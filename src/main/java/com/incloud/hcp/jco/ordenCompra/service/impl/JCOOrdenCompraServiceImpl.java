package com.incloud.hcp.jco.ordenCompra.service.impl;

import com.incloud.hcp.domain.*;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraRFCParameterBuilder;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraResponseDto;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraServicioRFCParameterBuilder;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenCompraService;
import com.incloud.hcp.repository.CotizacionDetalleRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.util.DateUtils;
import com.sap.conn.jco.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOOrdenCompraServiceImpl implements JCOOrdenCompraService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;
    private final int NIVEL = 1;
//    private final String FUNCION_RFC = "ZMM_GENERAR_OC";
    private final String FUNCION_RFC = "ZPE_MM_GENERAR_OC";
    private final String NOMBRE_TABLA_RPTA_RFC = "TO_RETURN";
    private final String TIPO_ITEM_MATERIAL = "MATERIAL";

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CotizacionDetalleRepository cotizacionDetalleRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;



    public OrdenCompraResponseDto grabarOrdenCompra(
            String claseDocumento,
            Proveedor proveedor,
            Usuario usuario,
            List<CcomparativoAdjudicado> ccomparativoAdjudicadoList) throws Exception {
        OrdenCompraResponseDto ordenCompraResponseDto = new OrdenCompraResponseDto();

        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - grabarOrdenCompra");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01B - grabarOrdenCompra");

        /* Pasando los datos de parametria */
        Date fechaActual = DateUtils.obtenerFechaActual();
        String sfechaActual = DateUtils.convertDateToString("yyyyMMdd", fechaActual);
        CotizacionDetalle cotizacionDetalle = ccomparativoAdjudicadoList.get(0).getCotizacionDetalle();
        cotizacionDetalle = this.cotizacionDetalleRepository.getOne(cotizacionDetalle.getIdCotizacionDetalle());
        String tipoItem = cotizacionDetalle.getBienServicio().getTipoItem();
        logger.error("01B - grabarOrdenCompra tipoItem: " + tipoItem);
        Licitacion licitacion = cotizacionDetalle.getCotizacion().getLicitacion();

        if (tipoItem.equals(TIPO_ITEM_MATERIAL)) {
            OrdenCompraRFCParameterBuilder.build(
                    jCoFunction,
                    claseDocumento,
                    sfechaActual,
                    proveedor,
                    usuario,
                    licitacion,
                    ccomparativoAdjudicadoList
            );
        }
        else  {
            OrdenCompraServicioRFCParameterBuilder.build(
                    jCoFunction,
                    claseDocumento,
                    sfechaActual,
                    proveedor,
                    usuario,
                    licitacion,
                    ccomparativoAdjudicadoList
            );
        }

        logger.error("01C - GET grabarOrdenCompra");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - grabarOrdenCompra - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 - GET grabarOrdenCompra - FIN RFC");
        JCoParameterList result = jCoFunction.getExportParameterList();
        List<SapLog> listSapLog = new ArrayList<>();
        String nroOrdenCompra = result.getString("PO_EBELN");
        logger.error("03 - GET grabarOrdenCompra - FIN RFC nroOrdenCompra: " + nroOrdenCompra);

        ordenCompraResponseDto.setCodigoAcreedorSap(proveedor.getAcreedorCodigoSap());
        ordenCompraResponseDto.setProveedorSAP(proveedor);
        ordenCompraResponseDto.setNumeroOrdenCompra(nroOrdenCompra);
        logger.error("03 - GET grabarOrdenCompra - FIN ordenCompraResponseDto: " + ordenCompraResponseDto.toString());

        JCoTable table = jCoFunction.getTableParameterList().getTable(NOMBRE_TABLA_RPTA_RFC);
        if (table != null && !table.isEmpty()) {
            do {
                SapLog sapLog = new SapLog();
                sapLog.setTipo(table.getString("TYPE"));
                sapLog.setCode(table.getString("NUMBER"));
                sapLog.setMesaj(table.getString("MESSAGE"));
                sapLog.setParameter(table.getString("PARAMETER"));
                sapLog.setRow(table.getString("ROW"));
                sapLog.setField(table.getString("FIELD"));
                sapLog.setSystem(table.getString("SYSTEM"));
                logger.error("grabarOrdenCompra sapLog" + sapLog.toString());
                listSapLog.add(sapLog);
            } while (table.nextRow());
        }
        ordenCompraResponseDto.setSapLogList(listSapLog);
        ordenCompraResponseDto.setExito(true);
//        if (listSapLog != null && listSapLog.size() > 0) {
//            for (SapLog beanLog : listSapLog) {
//                if (!beanLog.getTipo().equals("W")) {
//                    ordenCompraResponseDto.setExito(false);
//                    ordenCompraResponseDto.setNumeroOrdenCompra("");
//                }
//            }
//        }
        if (StringUtils.isBlank(nroOrdenCompra)) {
            ordenCompraResponseDto.setExito(false);
            ordenCompraResponseDto.setNumeroOrdenCompra("");
            SapLog sapLog = new SapLog();
            sapLog.setTipo("E");
            sapLog.setMesaj("SAP no devolvio Nro de Orden de Compra respectivo");
            listSapLog.add(sapLog);
            ordenCompraResponseDto.setSapLogList(listSapLog);
        }
        logger.error("04 - GET grabarOrdenCompra - FIN ordenCompraResponseDto: " + ordenCompraResponseDto.toString());
        return ordenCompraResponseDto;
    }
}
