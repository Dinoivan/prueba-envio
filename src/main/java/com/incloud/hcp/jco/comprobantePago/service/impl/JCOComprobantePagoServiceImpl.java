package com.incloud.hcp.jco.comprobantePago.service.impl;

import com.incloud.hcp.domain.Sociedad;
import com.incloud.hcp.enums.ComprobantePagoEstadoEnum;
import com.incloud.hcp.jco.comprobantePago.dto.ComprobantePagoDto;
import com.incloud.hcp.jco.comprobantePago.dto.ComprobantePagoItemDto;
import com.incloud.hcp.jco.comprobantePago.service.JCOComprobantePagoService;
import com.incloud.hcp.repository.SociedadRepository;
import com.incloud.hcp.util.DateUtils;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class JCOComprobantePagoServiceImpl implements JCOComprobantePagoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SociedadRepository sociedadRepository;

    @Value("${destination.rfc.profit}")
    private String destinationProfit;


    @Autowired
    public JCOComprobantePagoServiceImpl(SociedadRepository sociedadRepository) {
        this.sociedadRepository = sociedadRepository;
    }

    @Override
    public List<ComprobantePagoDto> extraerComprobantePagoListRFC(String fechaInicio, String fechaFin, String numeroComprobantePago, String ruc) throws Exception {
        try {
            String FUNCION_RFC = "ZPE_MM_COMPROBANTE_PAGO_TILE";

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();

            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            this.mapFilters(jCoFunction, fechaInicio, fechaFin, numeroComprobantePago, ruc);
            jCoFunction.execute(destination);

            JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
            ComprobantePagoExtractorMapper comprobantePagoExtractorMapper = ComprobantePagoExtractorMapper.newMapper(exportParameterList);

            List<ComprobantePagoDto> comprobantePagoDtoList = Optional.ofNullable(comprobantePagoExtractorMapper.getComprobantePagoDtoList()).orElse(new ArrayList<>());
            List<ComprobantePagoItemDto> comprobantePagoItemDtoList = Optional.ofNullable(comprobantePagoExtractorMapper.getComprobantePagoItemDtoList()).orElse(new ArrayList<>());

            logger.error("BUSQUEDA COMPROBANTES DE PAGO EN SAP [ENCONTRADOS]: comprobantes = " + comprobantePagoDtoList.size());
            logger.error("BUSQUEDA COMPROBANTES DE PAGO EN SAP [ENCONTRADOS]: items = " + comprobantePagoItemDtoList.size());

            List<Sociedad> sociedadList = sociedadRepository.findAll();

            comprobantePagoDtoList.forEach(cp -> {
                for (Sociedad sociedad : sociedadList) {
                    if (sociedad.getCodigoSociedad().equalsIgnoreCase(cp.getCodigoSociedad())) {
                        cp.setSociedad(sociedad);
                        cp.setCodigoSociedad(null);
                        break;
                    }
                }

                /* **************** REAJUSTE NUMERO COMPROBANTE ************************** */
                String[] arrayNumCompPago = cp.getNumeroComprobantePago().split("-");

//                String tipo = "";
                String serie;
                String correlativo;

                if(arrayNumCompPago[0].length() == 2){
//                    tipo = arrayNumCompPago[0];
                    serie = arrayNumCompPago[1].trim();
                    correlativo = arrayNumCompPago[2].trim();
                }
                else{
                    serie = arrayNumCompPago[0].trim();
                    correlativo = arrayNumCompPago[1].trim();
                }

                if (serie.length() == 5)
                    serie = serie.substring(1);

                if (correlativo.length() == 7)
                    correlativo = "0".concat(correlativo);

//                if (!tipo.isEmpty()){
//                    cp.setNumeroComprobantePago(tipo + "-" + serie + "-" + correlativo);
//                }
//                else{
                    cp.setNumeroComprobantePago(serie + "-" + correlativo);
//                }
                /* *********************************************************************** */

//                /* **************** CALCULO FECHA VENCIMIENTO **************************** */
//                Date fechaVencimiento = DateUtils.localDateToUtilDate(DateUtils.utilDateToLocalDate(cp.getFechaBase()).plusDays(cp.getFormaPago()));
//                cp.setFechaVencimiento(fechaVencimiento);
//                /* *********************************************************************** */

                String estado = cp.getEstado();
                if (estado.equalsIgnoreCase(ComprobantePagoEstadoEnum.PUBLICADO.getCodigo()))
                    cp.setEstado(ComprobantePagoEstadoEnum.PUBLICADO.getDescripcion());
                else if (estado.equalsIgnoreCase(ComprobantePagoEstadoEnum.PREREGISTRADO.getCodigo()))
                    cp.setEstado(ComprobantePagoEstadoEnum.PREREGISTRADO.getDescripcion());
                else if (estado.equalsIgnoreCase(ComprobantePagoEstadoEnum.PAGADO.getCodigo()))
                    cp.setEstado(ComprobantePagoEstadoEnum.PAGADO.getDescripcion());
                else if (estado.equalsIgnoreCase(ComprobantePagoEstadoEnum.ANULADO.getCodigo()))
                    cp.setEstado(ComprobantePagoEstadoEnum.ANULADO.getDescripcion());

                cp.setComprobantePagoItemDtoList(comprobantePagoItemDtoList.stream()
                        .filter(cpi -> cpi.getCodigoDocumentoSap().equals(cp.getCodigoDocumentoSap()) && cpi.getEjercicio().equals(cp.getEjercicio()))
                        .collect(Collectors.toList())
                );
            });

            return comprobantePagoDtoList;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }


    private void mapFilters(JCoFunction function, String fechaInicio, String fechaFin, String numeroComprobantePago, String ruc) {
        JCoParameterList paramList = function.getImportParameterList();

        JCoTable jcoTableBLART = paramList.getTable("IT_RGE_BLART");
        jcoTableBLART.appendRow();
        jcoTableBLART.setRow(0);
        jcoTableBLART.setValue("SIGN", "I");
        jcoTableBLART.setValue("OPTION", "EQ");
        jcoTableBLART.setValue("LOW", "01");
//        jcoTableBLART.setValue("HIGH", xxxxx);

        if(!ruc.isEmpty()){
            JCoTable jcoTableSTCD1 = paramList.getTable("IT_STCD1");
            jcoTableSTCD1.appendRow();
            jcoTableSTCD1.setRow(0);
            jcoTableSTCD1.setValue("STCD1", ruc);
        }

        if (!numeroComprobantePago.isEmpty()) {
            paramList.setValue("I_XBLNR", numeroComprobantePago);
        }
        else if (!fechaInicio.isEmpty() && !fechaFin.isEmpty()){
            JCoTable jcoTableCPUDT = paramList.getTable("IT_RGE_BLDAT");
            jcoTableCPUDT.appendRow();
            jcoTableCPUDT.setRow(0);
            jcoTableCPUDT.setValue("SIGN", "I");
            jcoTableCPUDT.setValue("LOW", fechaInicio);

            if (fechaInicio.equals(fechaFin)) {
                jcoTableCPUDT.setValue("OPTION", "EQ");
            } else {
                jcoTableCPUDT.setValue("OPTION", "BT");
                jcoTableCPUDT.setValue("HIGH", fechaFin);
            }
        }
    }
}