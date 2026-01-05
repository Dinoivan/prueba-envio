package com.incloud.hcp.rest;

import com.incloud.hcp.bean.MensajeBean;
import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.dto.UsuarioBtpDto;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.repository.UsuarioRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.UsuarioService;
import com.sap.cloud.security.xsuaa.token.Token;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by MARCELO on 22/09/2017.
 */
@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioRest extends AppRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Value("${ias.api.url}")
    private String url;

    @Value("${ias.api.user}")
    private String user;

    @Value("${ias.api.pass}")
    private String pass;

    @RequestMapping(value = "", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<java.util.List<Usuario>> gelAllUsuario() {
        return Optional.ofNullable(usuarioService.getAllUsuario())
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> save(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> edit(@RequestBody Usuario user) {
        return usuarioService.update(user);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> delete(@RequestBody Usuario user) {
        return usuarioService.delete(user);
    }

    @PostMapping(path = "/actualizarDatosUsuario")
    public ResponseEntity<MensajeBean> actualizarDatosUsuario(@RequestBody UsuarioBtpDto dto,@AuthenticationPrincipal Token token) {
        UserSession userSession = this.getUserSessionEmail(dto.getEmail());
        if(dto.getUsuariop().equalsIgnoreCase(userSession.getId())) {
            this.actualizaUsuarioIDP(dto.getUsuariop());
            logger.error("actualizarDatosUsuario_ " + dto);
            //Tabla proveedores
            if (StringUtils.isNotBlank(dto.getRuc())) {
                logger.error("actualizarDatosUsuario_1 ");
                Optional<Proveedor> optProveedor = this.proveedorRepository.findByRuc(dto.getRuc());
                if (optProveedor.isPresent()) {
                    logger.error("<--MC_LOG-->:UsuarioRest:");
                    logger.error("<--MC_LOG-->:UsuarioRest:" + dto.getEmail());
                    optProveedor.get().setEmail(dto.getEmail());
                    logger.error("<--MC_LOG-->:UsuarioRest-PreSave:");
                    this.proveedorRepository.save(optProveedor.get());
                }
            } else if (StringUtils.isNotBlank(dto.getUsuariop())) {
                logger.error("actualizarDatosUsuario_2 ");
                List<Proveedor> listaProveedor = this.proveedorRepository.getProveedorByIdp(dto.getUsuariop());
                logger.error("actualizarDatosUsuario_3 " + listaProveedor);
                if (listaProveedor != null && listaProveedor.size() > 0) {
                    logger.error("<--MC_LOG-->:UsuarioRest(0):");
                    logger.error("<--MC_LOG-->:UsuarioRest(0):" + dto.getEmail());
                    listaProveedor.get(0).setEmail(dto.getEmail());
                    logger.error("<--MC_LOG-->:UsuarioRest-PreSave(0):");
                    this.proveedorRepository.save(listaProveedor.get(0));
                }
                logger.error("actualizarDatosUsuario_ok_proveedor_4 ");
            }
            logger.error("actualizarDatosUsuario_5 ");
            //Tabla usuarios
            if (StringUtils.isNotBlank(dto.getUsuariop())) {
                logger.error("actualizarDatosUsuario_6 ");
                Usuario usuario = this.usuarioRepository.getByCodigoUsuarioIdp(dto.getUsuariop());
                logger.error("actualizarDatosUsuario_7 " + usuario);
                if (usuario != null) {
                    logger.error("<--MC_LOG-->:UsuarioRest(X):");
                    logger.error("<--MC_LOG-->:UsuarioRest(X):" + dto.getEmail());
                    usuario.setEmail(dto.getEmail());
                    logger.error("<--MC_LOG-->:UsuarioRest-PreSave(X):");
                    this.usuarioRepository.save(usuario);
                }
                logger.error("actualizarDatosUsuario_ok_usuario_8 ");
            }
            logger.error("actualizarDatosUsuario_9 ");
            MensajeBean msg = new MensajeBean();
            msg.setType("S");
            msg.setMensaje("El Usuario fue correctamente actualizado");


            if (msg != null) {

                return new ResponseEntity<MensajeBean>(msg, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private void actualizaUsuarioIDP(String usuarioP){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/scim+json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "{\"id\":\""+usuarioP+"\",\"emails\":[{\"value\":\"cbazalar@csticorp.biz\"}]}");
        Request request = new Request.Builder()
                .url(url+"service/scim/Users/"+usuarioP)
                .method("PUT", body)
                .addHeader("Content-Type", "application/scim+json")
                .addHeader("Authorization", getAuthorizationHeader(user,pass))
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAuthorizationHeader(String usuario, String clave) {
        String temp = new StringBuilder(usuario).append(":")
                .append(clave).toString();
        String result = "Basic "
                + new String(Base64.encodeBase64(temp.getBytes()));

        return result;
    }
}
