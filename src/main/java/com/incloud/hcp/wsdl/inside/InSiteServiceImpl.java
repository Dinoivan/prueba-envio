package com.incloud.hcp.wsdl.inside;

import com.incloud.hcp.ws.insite.bean.InsiteInput;
import com.incloud.hcp.ws.insite.service.IConsultaInsiteService;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Administrador on 09/11/2017.
 */
@Service
public class InSiteServiceImpl implements InSiteService, InSiteConstant {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private IConsultaInsiteService iConsultaInsiteService;

    @Autowired
    public InSiteServiceImpl(IConsultaInsiteService iConsultaInsiteService) {
        this.iConsultaInsiteService = iConsultaInsiteService;
    }

    @Override
    public InSiteResponse getConsultaRuc(String ruc) throws InSiteException {
//        String header = "getConsultaRuc - RUC " + ruc + " // ";
        try {
            String data = iConsultaInsiteService.consultaRUC(new InsiteInput().builder()
                    .ruc(ruc)
                    .username(USER_DEFAULT)
                    .hash(LICENSE_DEFAULT)
                    .tracking(FORMAT_DEFAULT)
                    .build());
//            logger.error(header + "respuesta JSON String: " + data);

            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(data);
            Map<String, String> map = new HashMap<>();
//            logger.error(header + "respuesta JSON Array toString: " + array.toString());
//            logger.error(header + "respuesta JSON Array toJSONString: " + array.toJSONString());

            for (int i = 0; i < array.size(); i++) {
//                logger.error(header + "respuesta JSON Array Item " + i + ": " + array.get(i));
                Map<String, String> values = (Map) array.get(i);
                Set<String> keys = values.keySet();
                keys.stream().forEach(key -> {
                    //map.put(key, values.get(key));
                    map.put(key, String.valueOf(values.get(key)));
                });
            }

            InSiteResponse response = new InSiteResponse();

            response.setRuc(Optional.ofNullable(map.get(RUC)).orElse(""));
            response.setCondicion(Optional.ofNullable(map.get(CONDICION))
                    .map(s -> s.equals("HABIDO"))
                    .orElse(Boolean.FALSE));
            response.setEstado(Optional.ofNullable(map.get(ACTIVO))
                    .map(s -> s.equals("ACTIVO"))
                    .orElse(Boolean.FALSE));
            response.setUbigeo(Optional.ofNullable(map.get(UBIGEO)).orElse(""));
            response.setDireccion(Optional.ofNullable(map.get(DIRECCION_02)).orElse(""));
            if (StringUtils.isBlank(response.getDireccion())) {
                response.setDireccion(Optional.ofNullable(map.get(DIRECCION)).orElse(""));
            }


            response.setRazonSocial(Optional.ofNullable(map.get(RAZON_SOCIAL)).orElse(""));
            response.setRegion(Optional.ofNullable(map.get(REGION)).orElse(""));
            response.setProvincia(Optional.ofNullable(map.get(PROVINCIA)).orElse(""));
            response.setDistrito(Optional.ofNullable(map.get(DISTRITO)).orElse(""));

            response.setActividadEconomica(Optional.ofNullable(map.get(ACTIVIDAD_ECONOMICA)).orElse(""));
            response.setCodigoSistemaEmisionElect(Optional.ofNullable(map.get(SISTEMA_EMISION_ELECTRONICA)).orElse(""));
            response.setCodigoComprobantePago(Optional.ofNullable(map.get(COMPROBANTE_PAGO)).orElse(""));
            response.setCodigoPadron(Optional.ofNullable(map.get(PADRON)).orElse(""));
            response.setFechaInicioActiSunat(Optional.ofNullable(map.get(FECHA_INICIO_ACTIVIDAD)).orElse(""));

            if (response.getRuc().isEmpty()) {
                throw new InSiteException("El RUC consultado no existe");
            }
            return response;
        } catch (Exception ex) {
            logger.error("Error al consultar el numero de ruc en el 'Servicio de Consulta'", ex);
            throw new InSiteException(ex.getMessage());
        }
    }
}
