package com.incloud.hcp.pdf.service.impl;

import com.incloud.hcp.pdf.bean.FieldEntradaMercaderiaPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterEntradaMercaderiaPdfDTO;

import javax.swing.text.html.Option;
import java.lang.reflect.Parameter;
import java.util.*;

class EntregaMercaderiaMapBuilder {

    private ParameterEntradaMercaderiaPdfDTO parameterEntradaMercaderiaPdfDTO;

    private static final String DASH = "-";
    private static final String CODE_VALUE_FORMAT ="%s -%s";

    static EntregaMercaderiaMapBuilder newEntradaMercaderiaMapBuilder(ParameterEntradaMercaderiaPdfDTO parameterEntradaMercaderia){
        return new EntregaMercaderiaMapBuilder(parameterEntradaMercaderia);
    }

    public EntregaMercaderiaMapBuilder(ParameterEntradaMercaderiaPdfDTO parameterEntradaMercaderia) {
        this.parameterEntradaMercaderiaPdfDTO = parameterEntradaMercaderia;
    }

    Map<String, Object> buildParams(){
        Map<String, Object> parameters= new HashMap<>();
        parameters.put("nroRUC", Optional.ofNullable(parameterEntradaMercaderiaPdfDTO.getNroRuc()).orElse(DASH));
        parameters.put("nroGUIA", Optional.ofNullable(parameterEntradaMercaderiaPdfDTO.getNroGuia()).orElse(DASH));
        parameters.put("rucCliente", Optional.ofNullable(parameterEntradaMercaderiaPdfDTO.getRucCliente()).orElse(DASH));
        parameters.put("fechaEmision", Optional.ofNullable(parameterEntradaMercaderiaPdfDTO.getFechaEmision()).orElse(DASH));
        parameters.put("razonSocialCliente", Optional.ofNullable(parameterEntradaMercaderiaPdfDTO.getRazonSocialCliente()).orElse(DASH));
        parameters.put("documentoMaterial", Optional.ofNullable(parameterEntradaMercaderiaPdfDTO.getDocumentoMaterial()).orElse(DASH));
        parameters.put("descripcionProveedor", Optional.ofNullable(parameterEntradaMercaderiaPdfDTO.getDescripcionProveedor()).orElse(DASH));
        parameters.put("ubicacionProveedor", Optional.ofNullable(parameterEntradaMercaderiaPdfDTO.getUbicacionProveedor()).orElse(DASH));
        if(Optional.ofNullable(parameterEntradaMercaderiaPdfDTO.getTelefonoProveedor()).isPresent()){
            parameters.put("telefonoProveedor", String.format(CODE_VALUE_FORMAT, Optional.ofNullable(parameterEntradaMercaderiaPdfDTO.getTelefonoProveedor()).orElse(""),  Optional.ofNullable(parameterEntradaMercaderiaPdfDTO.getTelefonoProveedor()).orElse("")));
        }

        return parameters;
    }

    List<Map<String,Object>> buildDetails(){
        List<Map<String, Object>>details = new ArrayList<>();

        List<FieldEntradaMercaderiaPdfDTO> parameterEntradaMercaderiaList = parameterEntradaMercaderiaPdfDTO.getFieldEntradaMercaderiaPdfDTOList();

        for(FieldEntradaMercaderiaPdfDTO parameterEntradaMercaderia: parameterEntradaMercaderiaList){
            Map<String, Object> map =new HashMap<>();

            map.put("nroItem", Optional.ofNullable(parameterEntradaMercaderia.getNroItem()).orElse(""));
            map.put("nroOC", Optional.ofNullable(parameterEntradaMercaderia.getNroOC()).orElse(""));
            map.put("nroItemOC", Optional.ofNullable(parameterEntradaMercaderia.getNroItemOC()).orElse(""));
            map.put("codigoProducto", Optional.ofNullable(parameterEntradaMercaderia.getCodigoProducto()).orElse(""));
            map.put("descripcionProducto", Optional.ofNullable(parameterEntradaMercaderia.getDescripcionProducto()).orElse(""));
            map.put("cantAceptableCliente", Optional.ofNullable(parameterEntradaMercaderia.getCantAceptableCliente()).orElse(""));
            map.put("undMedida", Optional.ofNullable(parameterEntradaMercaderia.getUndMedida()).orElse(""));
            map.put("cantPendientePedido", Optional.ofNullable(parameterEntradaMercaderia.getCantPedientePedido()).orElse(""));
            map.put("undMedidaPedido", Optional.ofNullable(parameterEntradaMercaderia.getUndMedida()).orElse(""));

            details.add(map);
        }

        return details;
    }
}
