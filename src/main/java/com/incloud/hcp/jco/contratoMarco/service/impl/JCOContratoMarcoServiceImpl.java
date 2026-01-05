package com.incloud.hcp.jco.contratoMarco.service.impl;

import com.incloud.hcp.domain.*;
import com.incloud.hcp.jco.contratoMarco.dto.ContratoMarcoRFCParameterBuilder;
import com.incloud.hcp.jco.contratoMarco.dto.ContratoMarcoResponseDto;
import com.incloud.hcp.jco.contratoMarco.dto.ContratoMarcoServicioRFCParameterBuilder;
import com.incloud.hcp.jco.contratoMarco.service.JCOContratoMarcoService;
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
public class JCOContratoMarcoServiceImpl implements JCOContratoMarcoService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;
    private final int NIVEL = 1;
//    private final String FUNCION_RFC = "ZMM_GENERAR_CM";
    private final String FUNCION_RFC = "ZPE_MM_GENERAR_CM";
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

    public ContratoMarcoResponseDto grabarContratoMarco(
            CcomparativoProveedor ccomparativoProveedor,
            Proveedor proveedor,
            Usuario usuario,
            List<CcomparativoAdjudicado> ccomparativoAdjudicadoList) throws Exception {
        ContratoMarcoResponseDto contratoMarcoResponseDto = new ContratoMarcoResponseDto();

        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - grabarContratoMarco");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01B - grabarContratoMarco");

        /* Pasando los datos de parametria */
        Date fechaActual = DateUtils.obtenerFechaActual();
        String sfechaActual = DateUtils.convertDateToString("yyyyMMdd", fechaActual);
        CotizacionDetalle cotizacionDetalle = ccomparativoAdjudicadoList.get(0).getCotizacionDetalle();
        cotizacionDetalle = this.cotizacionDetalleRepository.getOne(cotizacionDetalle.getIdCotizacionDetalle());
        String tipoItem = cotizacionDetalle.getBienServicio().getTipoItem();

        String fechaIni = DateUtils.convertDateToString("yyyyMMdd", ccomparativoProveedor.getFechaInicioValidez());
        String fechaFin = DateUtils.convertDateToString("yyyyMMdd", ccomparativoProveedor.getFechaFinValidez());
        Licitacion licitacion = cotizacionDetalle.getCotizacion().getLicitacion();

        logger.error("01B - grabarContratoMarco tipoItem: " + tipoItem);
        if (tipoItem.equals(TIPO_ITEM_MATERIAL)) {
            ContratoMarcoRFCParameterBuilder.build(
                    jCoFunction,
                    ccomparativoProveedor.getIdClaseDocumento().getCodigoClaseDocumento(),
                    fechaIni,
                    fechaFin,
                    ccomparativoProveedor.getValorAcumulado(),
                    sfechaActual,
                    proveedor,
                    usuario,
                    licitacion,
                    ccomparativoAdjudicadoList
            );
        }
        else {
            ContratoMarcoServicioRFCParameterBuilder.build(
                    jCoFunction,
                    ccomparativoProveedor.getIdClaseDocumento().getCodigoClaseDocumento(),
                    fechaIni,
                    fechaFin,
                    ccomparativoProveedor.getValorAcumulado(),
                    sfechaActual,
                    proveedor,
                    usuario,
                    licitacion,
                    ccomparativoAdjudicadoList
            );
        }


        logger.error("01C - GET grabarContratoMarco");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - grabarContratoMarco - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 - GET grabarContratoMarco - FIN RFC");
        JCoParameterList result = jCoFunction.getExportParameterList();
        List<SapLog> listSapLog = new ArrayList<>();
        String nroDocumento = result.getString("I_DOCUMENT");
        logger.error("03 - GET grabarContratoMarco - FIN RFC nroDocumento: " + nroDocumento);

        contratoMarcoResponseDto.setCodigoAcreedorSap(proveedor.getAcreedorCodigoSap());
        contratoMarcoResponseDto.setProveedorSAP(proveedor);
        contratoMarcoResponseDto.setNumeroContratoMarco(nroDocumento);

        logger.error("03 - GET grabarContratoMarco - FIN contratoMarcoResponseDto: " + contratoMarcoResponseDto.toString());
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
                logger.error("03A - grabarContratoMarco sapLog" + sapLog.toString());
                listSapLog.add(sapLog);
            } while (table.nextRow());
        }
        contratoMarcoResponseDto.setSapLogList(listSapLog);
        contratoMarcoResponseDto.setExito(true);
//        if (listSapLog != null && listSapLog.size() > 0) {
//            for (SapLog beanLog : listSapLog) {
//                if (!beanLog.getTipo().equals("W")) {
//                    contratoMarcoResponseDto.setExito(false);
//                    contratoMarcoResponseDto.setNumeroContratoMarco("");
//                }
//            }
//        }
        if (StringUtils.isBlank(nroDocumento)) {
            contratoMarcoResponseDto.setExito(false);
            contratoMarcoResponseDto.setNumeroContratoMarco("");
            SapLog sapLog = new SapLog();
            sapLog.setTipo("E");
            sapLog.setMesaj("SAP no devolvio Nro de Contrato Marco respectivo");
            listSapLog.add(sapLog);
            contratoMarcoResponseDto.setSapLogList(listSapLog);
        }

        logger.error("04 - GET grabarContratoMarco - FIN contratoMarcoResponseDto: " + contratoMarcoResponseDto.toString());
        return contratoMarcoResponseDto;
    }
}
