package com.incloud.hcp.jco.tipoCambio.service.impl;

import com.incloud.hcp.domain.Moneda;
import com.incloud.hcp.domain.TasaCambio;
import com.incloud.hcp.jco.tipoCambio.dto.TipoCambioRFCDTO;
import com.incloud.hcp.jco.tipoCambio.dto.TipoCambioRFCParameterBuilder;
import com.incloud.hcp.jco.tipoCambio.dto.TipoCambioRFCResponseDto;
import com.incloud.hcp.jco.tipoCambio.service.JCOTipoCambioService;
import com.incloud.hcp.repository.MonedaRepository;
import com.incloud.hcp.repository.TasaCambioRepository;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.util.DateUtils;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOTipoCambioServiceImpl implements JCOTipoCambioService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;

//    private final String FUNCION_RFC = "ZMMRFC_CONSULTA_TIPO_CAMBIO";
    private final String FUNCION_RFC = "ZPE_MM_CONSULTA_TIPO_CAMBIO";
    private final String NOMBRE_TABLA_RFC = "TO_TIP_CAMBIO";

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;



    @Autowired
    private MonedaRepository monedaRepository;

    @Autowired
    private TasaCambioRepository tasaCambioRepository;

    @Override
    public TipoCambioRFCResponseDto actualizarTipoCambio(String fecha) throws Exception {
        TipoCambioRFCResponseDto tipoCambioRFCResponseDto = new TipoCambioRFCResponseDto();

        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - getTipoCambio");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01tB - getTipoCambio");

        logger.error("parametro ingresado");
        TipoCambioRFCParameterBuilder.build(
                jCoFunction,
                fecha
        );
        logger.error("01C - getTipoCambio");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - getTipoCambio - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 - getTipoCambio - FIN RFC");
        JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
        JCoParameterList result = jCoFunction.getExportParameterList();
        SapLog sapLog = new SapLog();
        String codigoSap = result.getString("PO_CODE");
        String message = result.getString("PO_MSJE");
        sapLog.setCode(codigoSap);
        sapLog.setMesaj(message);
        tipoCambioRFCResponseDto.setSapLog(sapLog);
        logger.error("02b - getTipoCambio - sapLog: " + sapLog.toString());

        /* Recorriendo valores obtenidos del RFC */

        List<TipoCambioRFCDTO> lisTasaCambio = new ArrayList<TipoCambioRFCDTO>();
        //lista de monedas actual
        List<Moneda> listMoneda = this.monedaRepository.findAll();

        JCoTable table = tableParameterList.getTable(NOMBRE_TABLA_RFC);
        if (table != null && !table.isEmpty()) {

            do {

                //logger.error("A - getDevuelveValores TABLE JCO: " + table.toString());
                TipoCambioRFCDTO tipoCambioRFCDTO = new TipoCambioRFCDTO();
                tipoCambioRFCDTO.setCodigoMonedaOrigen(table.getString("LOCAL_CURRENCY"));
                tipoCambioRFCDTO.setCodigoMonedaDestino(table.getString("FOREIGN_CURRENCY"));
                tipoCambioRFCDTO.setValor(table.getBigDecimal("EXCHANGE_RATE"));
                tipoCambioRFCDTO.setFecha(table.getString("PI_DATE"));

//                String monedaDestino=tipoCambioRFCDTO.getCodigoMonedaDestino();
//                Moneda monedaSearch=this.monedaRepository.getByCodigoMoneda(monedaDestino);
//
//                if (!Optional.ofNullable(monedaSearch).isPresent()) {
//                    Moneda newmoneda = new Moneda();
//                    newmoneda.setSigla(tipoCambioRFCDTO.getCodigoMonedaDestino());
//                    newmoneda.setCodigoMoneda(tipoCambioRFCDTO.getCodigoMonedaDestino());
//                    newmoneda.setTextoBreve(tipoCambioRFCDTO.getCodigoMonedaDestino());
//                    newmoneda.setTextoExplicativo(tipoCambioRFCDTO.getCodigoMonedaDestino());
//                    newmoneda.setTasaMoneda(new BigDecimal(0.0));
//                    logger.error(newmoneda.toString());
//                    this.monedaRepository.save(newmoneda);
//                }

                lisTasaCambio.add(tipoCambioRFCDTO);

                //logger.error("bean" + tempRubroBien);
            } while (table.nextRow());
        }


       for(TipoCambioRFCDTO tipoCambioRFCDTO1:lisTasaCambio){
            Moneda monedaLocal= monedaRepository.getByCodigoMoneda(tipoCambioRFCDTO1.getCodigoMonedaOrigen());
            Moneda monedaDestino= monedaRepository.getByCodigoMoneda(tipoCambioRFCDTO1.getCodigoMonedaDestino());
            Date fechaTasa=DateUtils.convertStringToDate("yyyyMMdd",tipoCambioRFCDTO1.getFecha());

            if (monedaLocal != null && monedaDestino != null) {
                TasaCambio tasaCambioExistente = tasaCambioRepository.getByFechaTasaAndIdMonedaOrigenAndIdMonedaDestino(fechaTasa, monedaLocal, monedaDestino);

                if (!Optional.ofNullable(tasaCambioExistente).isPresent()) {
                    logger.error("Nueva tasa Cambio");
                    TasaCambio tasaCambio = new TasaCambio();
                    tasaCambio.setFechaTasa(fechaTasa);
                    tasaCambio.setValor(tipoCambioRFCDTO1.getValor().setScale(4, BigDecimal.ROUND_HALF_UP));
                    tasaCambio.setIdMonedaOrigen(monedaLocal);
                    tasaCambio.setIdMonedaDestino(monedaDestino);
                    logger.error("Nueva tasa Cambio tasaCambio: " + tasaCambio.toString());
                    tasaCambioRepository.save(tasaCambio);
                } else {
                    logger.error("ActualizandoValor");
                    tasaCambioExistente.setFechaTasa(fechaTasa);
                    tasaCambioExistente.setValor(tipoCambioRFCDTO1.getValor().setScale(4, BigDecimal.ROUND_HALF_UP));
                    logger.error("ActualizandoValor tasa Cambio tasaCambio: " + tasaCambioExistente.toString());
                    tasaCambioRepository.save(tasaCambioExistente);
                }
            }
        }

        tipoCambioRFCResponseDto.setListaTasaCambio(lisTasaCambio);
        logger.error("Lista Tasa Cambios" +lisTasaCambio.toString() );
        return tipoCambioRFCResponseDto;
    }
}
