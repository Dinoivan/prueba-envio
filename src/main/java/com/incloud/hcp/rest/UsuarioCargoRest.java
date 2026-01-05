package com.incloud.hcp.rest;

import com.incloud.hcp.domain.UsuarioCargo;
import com.incloud.hcp.dto.UsuarioCargoDto;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.UsuarioCargoService;
import com.incloud.hcp.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/usuarioCargo")
public class UsuarioCargoRest extends AppRest {
    private final Logger log = LoggerFactory.getLogger(UsuarioCargoRest.class);

    @Autowired
    protected UsuarioCargoService usuarioCargoService;

    @Operation(summary = "Busca registro de tipo UsuarioCargo en base al id del Usuario")
    @GetMapping(value = "/findByIdUsuario/{idUsuario}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UsuarioCargo>> findByIdUsuario(@PathVariable Integer idUsuario) throws URISyntaxException {
        log.debug("Find by id UsuarioCargo : {}", idUsuario);
        try {
            return Optional.ofNullable(this.usuarioCargoService.findbyIdUsuario(idUsuario))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @Operation(summary = "Modifica o crea registro de tipo UsuarioCargo en base a una Lista")
    @PostMapping(value = "/grabarLista", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UsuarioCargo>> grabarLista(
            @RequestBody UsuarioCargoDto usuarioCargoDto)
            throws URISyntaxException {

        log.debug("Ingresando grabarLista UsuarioCargoRest : {}", usuarioCargoDto.toString());
        try {
            List<UsuarioCargo> result = this.usuarioCargoService.grabarLista(usuarioCargoDto);
            return Optional.of(result).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

}