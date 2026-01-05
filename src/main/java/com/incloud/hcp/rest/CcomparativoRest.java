package com.incloud.hcp.rest;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.CcomparativoProveedor;
import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.CComparativoAdjudicarDto;
import com.incloud.hcp.repository.CcomparativoProveedorRepository;
import com.incloud.hcp.repository.LicitacionRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.CcomparativoService;
import com.incloud.hcp.util.Constant;
import com.incloud.hcp.util.Utils;
import com.sap.cloud.security.xsuaa.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by USER on 18/09/2017.
 */
@RestController
@RequestMapping(value = "/api/ccomparativo")
public class CcomparativoRest extends AppRest {

    private static Logger logger = LoggerFactory.getLogger(CcomparativoRest.class);

    @Autowired
    private CcomparativoService ccomparativoService;

    @Autowired
    private LicitacionRepository licitacionRepository;

    @Autowired
    private CcomparativoProveedorRepository ccomparativoProveedorRepository;


    @RequestMapping(value = "/adjudicar", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<CComparativoAdjudicarDto> adjudicar(
            @RequestBody CComparativoAdjudicarDto dto,@AuthenticationPrincipal Token token) throws Exception  {

        try {
            UserSession userSession = this.getUserSession(token);
            dto.setUserSession(userSession);
            logger.error("adjudicar REST - CComparativoAdjudicarDto: " + dto.toString());
            CComparativoAdjudicarDto result = this.ccomparativoService.adjudicar(dto);

            /* Enviar mail */
            if (result.isExito()) {
                this.ccomparativoService.enviarCuadroComparativo(dto.getLicitacion(), dto.getListaProveedor());
            }

            /* Enviar mail de participación */
            Integer idLicitacion = dto.getIdLicitacion();
            Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
            this.ccomparativoService.enviarCorreoAgradecimiento(licitacion);

            return Optional.of(result).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }


    }

    @RequestMapping(value = "/adjudicarSAPErrores", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<CcomparativoProveedor>> adjudicarSAPErrores(
            @RequestBody CComparativoAdjudicarDto dto,@AuthenticationPrincipal Token token) throws Exception  {

        try {
            UserSession userSession = this.getUserSession(token);
            dto.setUserSession(userSession);
            logger.error("REST adjudicarSAPErrores: " + dto.toString());

            List<CcomparativoProveedor> result = this.ccomparativoService.adjudicarSAPErrores(dto);
            Integer idLicitacion = dto.getIdLicitacion();
            Licitacion licitacion = this.licitacionRepository.getOne(idLicitacion);
            String indicadorSap = licitacion.getIndEjecucionSapOk();

            /* Enviar mail de Adjudicacion */
            if (indicadorSap.equals(Constant.UNO)) {
                List<Proveedor> proveedorList = this.ccomparativoProveedorRepository.findByLicitacionProveedor(idLicitacion);
                this.ccomparativoService.enviarCuadroComparativo(licitacion, proveedorList);

            }

            return Optional.of(result).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }





}
