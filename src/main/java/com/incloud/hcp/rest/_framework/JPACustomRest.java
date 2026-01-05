package com.incloud.hcp.rest._framework;

import com.incloud.hcp.config.BindingErrorsResponse;
import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.domain._framework.BaseResponseDomain;
import com.incloud.hcp.service._framework.JPACustomService;
import com.incloud.hcp.service.support.PageResponse;
import com.incloud.hcp.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public abstract class JPACustomRest<R extends BaseResponseDomain, T extends BaseDomain, I> extends AppRest {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected final String NOMBRE_CLASE = this.getClass().getSimpleName();


    @Value("${prefijo.sistema.web}")
    protected String prefijoSistemaWeb;

    @Autowired
    protected JPACustomService<R, T> mainService;


    /*****************************/
    /* Metodos de Busqueda       */
    /*****************************/

    @Operation(summary = "Devuelve lista de registros de tipo <T> en base a los parámetros y condiciones ingresados")
    @PostMapping(value = "/findCondicion", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<T>> findCondicion(@RequestBody R bean, BindingResult bindingResult) throws URISyntaxException {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors()) {
            String errorDevuelve = this.devuelveErrorHeaders(bindingResult, errors);
            throw new RuntimeException(errorDevuelve);
        }
        log.debug(this.NOMBRE_CLASE + " - Ingresando findCondicion by:" + bean.toString());
        try {
            return Optional.ofNullable(this.mainService.findCondicion(bean)).map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }
        catch (Exception e) {
              String error = Utils.obtieneMensajeErrorException(e);
              throw new RuntimeException(error);

        }
    }

    @Operation(summary = "Devuelve lista de registros de tipo <T> en base a los parámetros y condiciones ingresados en forma Paginada")
    @PostMapping(value = "/findCondicionPaginated", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<T>> findCondicionPaginated(@RequestBody R bean, BindingResult bindingResult) throws URISyntaxException {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors()) {
            String errorDevuelve = this.devuelveErrorHeaders(bindingResult, errors);
            throw new RuntimeException(errorDevuelve);

        }
        if (!Optional.ofNullable(bean.getPageRequest()).isPresent()) {
            String errorDevuelve = "Debe ingresar Bean de Paginacion";
            throw new RuntimeException(errorDevuelve);

        }
        log.debug(this.NOMBRE_CLASE + " - Ingresando findCondicionPaginated by:" + bean.toString());
        try {
            return Optional.ofNullable(this.mainService.findCondicionPaginated(bean, bean.getPageRequest())).map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }
        catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);

        }
    }



    /************************/
    /* Instancia de Bean    */
    /************************/
    protected abstract T createInstance();


}
