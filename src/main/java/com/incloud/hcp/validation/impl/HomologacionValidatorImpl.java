package com.incloud.hcp.validation.impl;

import com.incloud.hcp.domain.LineaComercial;
import com.incloud.hcp.dto.HomologacionDto;
import com.incloud.hcp.enums.TipoHomologacionEnum;
import com.incloud.hcp.service.LineaComercialService;
import com.incloud.hcp.util.constant.LineaComercialConstant;
import com.incloud.hcp.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;

@Component
public class HomologacionValidatorImpl implements Validator<HomologacionDto> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String LINEA_COMERCIAL_INVALIDA = "Seleccione una linea comercial";
    private final String TIPO_HOMOLOGACION_INVALIDA = "Los tipos de homologación esta incompleto";
    private LineaComercialService lineaComercialService;

    @Override
    public void validate(final HomologacionDto dto, final BindingResult errors) {

        logger.debug("Homologacion validate start");
        Optional.ofNullable(dto.getIdLineaComercial())
                .filter(id -> !Optional.ofNullable(lineaComercialService.getByIdLineaComercial(id))
                        .map(LineaComercial::getNivel)
                        .filter(nivel -> nivel == LineaComercialConstant.LINEA_COMERCIAL)
                        .isPresent())
                .map(t -> new FieldError("homologacion", "idLineaComercial", LINEA_COMERCIAL_INVALIDA))
                .ifPresent(errors::addError);

        Optional.ofNullable(dto.getTipo())
                .filter(tipo -> TipoHomologacionEnum.values().length != tipo.size())
                .map(t -> new FieldError("homologacion","tipo", TIPO_HOMOLOGACION_INVALIDA))
                .ifPresent(errors::addError);
    }

    @Autowired
    public void setLineaComercialService(LineaComercialService lineaComercialService) {
        this.lineaComercialService = lineaComercialService;
    }
}
