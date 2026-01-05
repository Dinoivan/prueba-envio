package com.incloud.hcp.pdf.service.impl;

import com.incloud.hcp.pdf.bean.FieldConformidadServicioPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterConformidadServicioPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterEntradaMercaderiaPdfDTO;

import java.util.*;

public class ConformidadServicioMapBuilder {

    private ParameterConformidadServicioPdfDTO parameterConformidadServicioPdfDTO;

    private static final String DASH = "-";
    private static final String rucCopeinca = "20224748711";
    private static final String rucCfgInvestment = "20512868046";

    static ConformidadServicioMapBuilder newConformidadServicioMapBuilder(ParameterConformidadServicioPdfDTO parameterConformidadServicioPdf) {
        return new ConformidadServicioMapBuilder(parameterConformidadServicioPdf);
    }

    public ConformidadServicioMapBuilder(ParameterConformidadServicioPdfDTO parameterConformidadServicioPdf) {
        this.parameterConformidadServicioPdfDTO = parameterConformidadServicioPdf;
    }

    Map<String, Object> buildParams() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ubicacionCliente", Optional.ofNullable(parameterConformidadServicioPdfDTO.getUbicacionCliente()).orElse(DASH));
        parameters.put("descripcionCliente", Optional.ofNullable(parameterConformidadServicioPdfDTO.getDescripcionCliente()).orElse(DASH));
        parameters.put("telefonoCliente", Optional.ofNullable(parameterConformidadServicioPdfDTO.getTelefonoCliente()).orElse(DASH));
        parameters.put("nroConformidadServicio", Optional.ofNullable(parameterConformidadServicioPdfDTO.getNroConformidadServicio()).orElse(DASH));
        parameters.put("rucProveedor", Optional.ofNullable(parameterConformidadServicioPdfDTO.getRucProveedor()).orElse(DASH));
        parameters.put("fechaEmision", Optional.ofNullable(parameterConformidadServicioPdfDTO.getFechaEmision()).orElse(DASH));
        parameters.put("razonSocialProveedor", Optional.ofNullable(parameterConformidadServicioPdfDTO.getRazonSocialProveedor()).orElse(DASH));
        parameters.put("tipoMoneda", Optional.ofNullable(parameterConformidadServicioPdfDTO.getTipoMoneda()).orElse(DASH));
        parameters.put("recepcionPersona", Optional.ofNullable(parameterConformidadServicioPdfDTO.getRecepcionPersona()).orElse(DASH));
        parameters.put("autorPersona", Optional.ofNullable(parameterConformidadServicioPdfDTO.getAutorPersona()).orElse(DASH));
        parameters.put("fechaAcept", Optional.ofNullable(parameterConformidadServicioPdfDTO.getFechaAcept()).orElse(DASH));
        /*if (Optional.ofNullable(parameterConformidadServicioPdfDTO.getRucCliente()).isPresent()) {
            parameters.put("pathLogo", rucCopeinca.equals(parameterConformidadServicioPdfDTO.getRucCliente())  ? "logoCopeinca/COPEINCA.png" : "logoCopeinca/CFGInvestment.png");
        } else {
            parameters.put("pathLogo", "logoCopeinca/CFGInvestment.png");
        }*/
        parameters.put("pathLogo", "logoCopeinca/CFG.png");
        return parameters;
}

    List<Map<String, Object>> buildDetails() {
        List<Map<String, Object>> details = new ArrayList<>();

        List<FieldConformidadServicioPdfDTO> fieldConformidadServicioPdfList = parameterConformidadServicioPdfDTO.getFieldConformidadServicioPdfDTOList();

        for (FieldConformidadServicioPdfDTO conformidadServicioPdf : fieldConformidadServicioPdfList) {
            Map<String, Object> map = new HashMap<>();
            map.put("nroItem", Optional.ofNullable(conformidadServicioPdf.getNroItem()).orElse(""));
            map.put("nroOrdenServicio", Optional.ofNullable(conformidadServicioPdf.getNroOrdenServicio()).orElse(""));
            map.put("nroItemOrdenServicio", Optional.ofNullable(conformidadServicioPdf.getNroItemOrdenServicio()).orElse(""));
            map.put("descripcionServicio", Optional.ofNullable(conformidadServicioPdf.getDescripcionServicio()).orElse(""));
            map.put("cantidad", Optional.ofNullable(conformidadServicioPdf.getCantidad()).orElse(""));
            map.put("unidad", Optional.ofNullable(conformidadServicioPdf.getUnidad()).orElse(""));
            map.put("valorRecibido", Optional.ofNullable(conformidadServicioPdf.getValorRecibido()).orElse(""));
            details.add(map);
        }
        return details;
    }
}
