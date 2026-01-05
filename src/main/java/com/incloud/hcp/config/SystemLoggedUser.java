package com.incloud.hcp.config;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.exception.ServiceException;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.repository.UsuarioRepository;
import com.incloud.hcp.service.UsuarioAutoService;
import com.incloud.hcp.ws.ias.bean.IASResponse;
import com.incloud.hcp.ws.ias.bean.IASUserInfoResponse;
import com.incloud.hcp.ws.ias.service.IUserIASService;
import com.sap.cloud.security.xsuaa.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("systemLoggedUser")
public class SystemLoggedUser {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${sm.portal.nameId}")
    private String nameIdScp;

    @Value("${sm.portal.dev}")
    private Boolean isDev;

    @Value("${sm.portal.nameDisplay}")
    private String nameDisplay;

    @Value("${sm.portal.email}")
    private String email;

    private ProveedorRepository proveedorRepository;
    private UsuarioRepository usuarioRepository;
    private UsuarioAutoService usuarioAutoService;
    private IUserIASService userIASService;

    @Autowired
    public SystemLoggedUser(ProveedorRepository proveedorRepository,
                            UsuarioRepository usuarioRepository,
                            UsuarioAutoService usuarioAutoService,
                            IUserIASService userIASService
                            ) throws PortalException {
        this.proveedorRepository = proveedorRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioAutoService = usuarioAutoService;
        this.userIASService = userIASService;
    }

    /*public User getUserSCP(HttpServletRequest request) {
        String keyAttribute = "com.sap.security.auth.login.User." + request.getUserPrincipal().getName().toLowerCase();
        return (User) request.getSession().getAttribute(keyAttribute);
    }*/

    public UserSession getUserSession(Token token) throws ServiceException {
        /*try {

            UserSession session = new UserSession();
            session.setRuc("");
            session.setMail("");
            session.setDisplayName("");
            session.setId("");
            logger.error("getUserSession session: " + session.toString());
            return session;
        } catch (Exception ex) {
            logger.error("Error al obtener el usuario de la sesión", ex);
            throw new ServiceException("Error al obtener el usuario de la sesión");
        }*/
        try {
            logger.error("token_user :: " + token);
            logger.error("getUserSession_info :: " + token.getEmail());
            logger.error("getUserSession_error :: " + token.getUsername());


            IASResponse response = userIASService.getUserByEmail(token.getEmail());

            if (response.getStatus().equals("200")) {
                IASUserInfoResponse.Resource resource = response.getResult().getResources().get(0);
                UserSession session = new UserSession();
                //session.setDisplayName(token.getGivenName() + " " + token.getFamilyName());
                session.setDisplayName(resource.getName().getGivenName() + " " + resource.getName().getFamilyName());
                session.setId(resource.getId());
                session.setMail(token.getEmail());
                session.setRuc(resource.getUserName()); // se considera en IAS el userName como el RUC
                session.setUserName(resource.getDisplayName());
                session.setFirstName(resource.getName().getGivenName());
                session.setLastName(resource.getName().getFamilyName());
                //session.setBody(response.getBody());
                    /*session.setFirstName(token.getGivenName());
                    session.setLastName(token.getFamilyName());*/
                if(response.getResult()!= null && response.getResult().getGroups() != null) {
                    session.setListaGrupos(response.getResult().getGroups());
                }
                String ruc = session.getRuc();
                if (ruc != null && !ruc.isEmpty() && (ruc.startsWith("1") || ruc.startsWith("2"))) { // loginName es un RUC
                    this.guardarUsuarioIdpDeProveedor(session);
                } else { // internal user
                    List listSociedad = new ArrayList<String>();
                    listSociedad.add("0001");
                    //session.setCodigoSociedades(Optional.ofNullable(usuarioSociedadRepository.findByUsuarioIAS(session.getId())).orElse(new ArrayList<>()));
                    session.setCodigoSociedades(listSociedad);
                }

                logger.info("ZUSERINFO -> {}", session);
                return session;

            } else {
                throw new PortalException("Usuario no habilitado " + token.getGivenName() + " " + token.getFamilyName() + ", " + token.getEmail());
            }
        } catch (Exception ex) {
            logger.error("Error al obtener el usuario de la sesión", ex);
            throw new PortalException("Error al obtener el usuario de la sesión");
        }
    }

    public UserSession getUserSessionByEmail(String email) throws ServiceException {
        try {
            logger.error("getUserSessionByEmail_info :: " + email);

            IASResponse response = userIASService.getUserByEmail(email);

            if (response.getStatus().equals("200")) {
                IASUserInfoResponse.Resource resource = response.getResult().getResources().get(0);
                UserSession session = new UserSession();
                session.setDisplayName(resource.getName().getGivenName() + " " + resource.getName().getFamilyName());
                session.setId(resource.getId());
                session.setMail(email);
                session.setRuc(resource.getUserName());
                session.setUserName(resource.getDisplayName());
                session.setFirstName(resource.getName().getGivenName());
                session.setLastName(resource.getName().getFamilyName());

                if (response.getResult() != null && response.getResult().getGroups() != null) {
                    session.setListaGrupos(response.getResult().getGroups());
                }

                String ruc = session.getRuc();
                if (ruc != null && !ruc.isEmpty() && (ruc.startsWith("1") || ruc.startsWith("2"))) {
                    this.guardarUsuarioIdpDeProveedor(session);
                } else {
                    List<String> listSociedad = new ArrayList<>();
                    listSociedad.add("0001");
                    session.setCodigoSociedades(listSociedad);
                }

                logger.info("ZUSERINFO -> {}", session);
                return session;
            } else {
                throw new PortalException("Usuario no habilitado con email " + email);
            }
        } catch (Exception ex) {
            logger.error("Error al obtener el usuario de la sesión", ex);
            throw new PortalException("Error al obtener el usuario de la sesión");
        }
    }

    /*public UserSession getUserSession() throws PortalException {
        try {
            if (isDev) {
                UserSession session = new UserSession();
                session.setDisplayName(this.nameDisplay);
                session.setId(this.nameIdScp);
                session.setMail(this.email);
                session.setRuc(this.nameIdScp);
                return session;
            }
            InitialContext ctx = new InitialContext();
            UserProvider userProvider;
            userProvider = (UserProvider) ctx.lookup("java:comp/env/user/Provider");

            User u = userProvider.getCurrentUser();
            logger.error("Usuario u: " + u.toString());
            UserSession session = new UserSession();
            session.setUserName(u.getName());
            session.setId(this.getAttributeOfUser(u, "id"));
            session.setFirstName(this.getAttributeOfUser(u,"first_name"));
            session.setLastName(this.getAttributeOfUser(u,"last_name"));
            session.setDisplayName(this.getAttributeOfUser(u, "display_name"));
            session.setMail(this.getAttributeOfUser(u, "email"));
            session.setMail(this.getAttributeOfUser(u, "email"));
            logger.error("Usuario session: " + session.toString());
            String loginName = this.getAttributeOfUser(u, "login_name");
            session.setRuc(loginName);
            if (loginName != null && !loginName.isEmpty() && (loginName.startsWith("1") || loginName.startsWith("2"))) { // loginName es un RUC
                this.guardarEditar(session); // temporalmente activo mientras sigan creando proveedores sin el MODULO 2
                this.guardarUsuarioIdpDeProveedor(session);
            }

//            if (loginName != null && !this.stringIsLong(loginName)) // loginName no es null ni es un RUC
//                session.setSapUsername(loginName.toUpperCase());

            session.setGroupList(new ArrayList<>(u.getGroups()));
            session.setRoleList(new ArrayList<>(u.getRoles()));
            //logger.error("Usuario session: " + session.toString());

//            if (loginName != null)
//                this.guardarEditar(session);

            return session;
        } catch (Exception ex) {
            logger.error("Error al obtener el usuario de la sesión", ex);
            throw new PortalException("Error al obtener el usuario de la sesión");
        }
    }*/

    private boolean stringIsLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
/*
    //// ****************** temporalmente activo mientras sigan creando proveedores sin el MODULO 2 ********************
    private void guardarEditar(UserSession userSession) {
        String nombreLogin = userSession.getRuc();
        String correo = userSession.getMail().toLowerCase();
        String nombre = Optional.ofNullable(userSession.getFirstName()).orElse("");
        String apellido = Optional.ofNullable(userSession.getLastName()).orElse("");
//        String codigoSap = Optional.ofNullable(userSession.getSapUsername()).orElse("");

//        Usuario usuarioExistente = usuarioRepository.findByCodigoSap(nombreLogin.toUpperCase());
        Usuario usuarioExistente = null;
        boolean correoEsUnico;
        List<Usuario> usuarioExistenteList = usuarioRepository.findByCodigoUsuarioIdp(nombreLogin);
        if (usuarioExistenteList.size() == 1)
            usuarioExistente = usuarioExistenteList.get(0);

        try{
            if (usuarioExistente == null){ // NO existe usuario en la tabla con ese mismo login_name
                logger.error("USUARIO NUEVO: " + userSession.toString());
                Usuario usuarioConMismoCorreo = usuarioRepository.findByEmail(correo);
                correoEsUnico = usuarioConMismoCorreo == null;

                if(correoEsUnico) { // NO existe usuario en la tabla con ese mismo correo
                    logger.error("USUARIO NUEVO Y CORREO ES NUEVO: " + userSession.toString());
                }
                else{ // SI existe usuario en la tabla con ese mismo correo
                    logger.error("USUARIO NUEVO Y CORREO EXISTE CON OTRO USUARIO: " + userSession.toString());
                    logger.error("USUARIO EXISTENTE CON MISMO CORREO (A BORRAR): " + usuarioConMismoCorreo.toString());
                    usuarioAutoService.delete(usuarioConMismoCorreo);
                }

                Usuario usuarioNuevo = new Usuario();
//                Cargo defaultCargo = new Cargo();
//                defaultCargo.setIdCargo(0);

                usuarioNuevo.setCodigoUsuarioIdp(nombreLogin);
                usuarioNuevo.setEmail(correo);
//                usuarioNuevo.setCargo(defaultCargo);
                usuarioNuevo.setApellido(apellido);
                usuarioNuevo.setNombre(nombre);

//                if (!codigoSap.isEmpty())
//                    usuarioNuevo.setCodigoSap(codigoSap);

                usuarioNuevo = usuarioAutoService.save(usuarioNuevo);

                if (correoEsUnico)
                    logger.error("USUARIO NUEVO Y CORREO ES NUEVO Y SE INSERTO EN BD CON: " + usuarioNuevo.toString());
                else
                    logger.error("USUARIO NUEVO Y CORREO AHORA ES UNICO Y SE INSERTO EN BD CON: " + usuarioNuevo.toString());
            }
            else { // SI existe usuario en la tabla con ese mismo login_name
                if(!usuarioExistente.getEmail().equals(correo)
                        || !usuarioExistente.getNombre().equals(nombre)
                        || !usuarioExistente.getApellido().equals(apellido)
//                        || !(usuarioExistente.getCodigoSap() != null ? usuarioExistente.getCodigoSap() : "").equals(codigoSap)
                        ){
                    logger.error("USUARIO EXISTE Y FUE MODIFICADO: " + usuarioExistente.toString());
//                    List<Usuario> otroUsuarioCodigoSapList = usuarioRepository.getUsuarioCodigoSapDistingbyId(usuarioExistente.getIdUsuario(), codigoSap);
                    List<Usuario> otroUsuarioCodigoSapList = new ArrayList<>();

                    if (!(otroUsuarioCodigoSapList != null && !otroUsuarioCodigoSapList.isEmpty())) {
                        logger.error("USUARIO EXISTE Y FUE MODIFICADO Y CODIGO_SAP ES UNICO: " + usuarioExistente.toString());
                        List<Usuario> otroUsuarioCorreoList = usuarioRepository.getUsuarioCorreoDistingbyId(usuarioExistente.getIdUsuario(), correo);
                        correoEsUnico = !(otroUsuarioCorreoList != null && !otroUsuarioCorreoList.isEmpty());

                        if (correoEsUnico) {
                            logger.error("USUARIO EXISTE Y FUE MODIFICADO Y CODIGO_SAP ES UNICO Y CORREO ES UNICO: " + usuarioExistente.toString());
                        }
                        else{
                            logger.error("USUARIO EXISTE Y FUE MODIFICADO Y CODIGO_SAP ES UNICO Y CORREO EXISTE CON OTRO(S) USUARIO(S): " + userSession.toString());
                            for(Usuario usuarioConMismoCorreo : otroUsuarioCorreoList) {
                                logger.error("USUARIO EXISTENTE CON MISMO CORREO (A BORRAR): " + usuarioConMismoCorreo.toString());
                                usuarioAutoService.delete(usuarioConMismoCorreo);
                            }
                        }

                        usuarioExistente.setEmail(correo);
                        usuarioExistente.setNombre(nombre);
                        usuarioExistente.setApellido(apellido);

//                        if(!codigoSap.isEmpty())
//                            usuarioExistente.setCodigoSap(codigoSap);

                        usuarioExistente = usuarioAutoService.update(usuarioExistente);

                        if (correoEsUnico)
                            logger.error("USUARIO EXISTE Y FUE MODIFICADO Y CODIGO_SAP ES UNICO Y CORREO ES UNICO Y SE ACTUALIZO EN BD CON: " + usuarioExistente.toString());
                        else
                            logger.error("USUARIO EXISTE Y FUE MODIFICADO Y CODIGO_SAP ES UNICO Y CORREO AHORA ES UNICO Y SE ACTUALIZO EN BD CON: " + usuarioExistente.toString());
                    }
                }
            }
        }catch (Exception e){
            logger.error("Excepción al guardar/editar usuario " + nombreLogin + ": " + e.getClass().getName() + " -- " + e.getMessage());
        }
    }
    //// ***************************************************************************************************************

    private void guardarUsuarioIdpDeProveedor(UserSession userSession) {
        String ruc = userSession.getRuc();
        String usuarioIdp = userSession.getId();

        try{
//            Optional<String> opCurrentUsuarioIdp = proveedorRepository.getUsuarioIdpProveedorByRuc(ruc);
            Optional<ProveedorCustom> opProveedorCustom = proveedorRepository.getProveedorIdHcpByRuc(ruc);

            if(opProveedorCustom.isPresent() && opProveedorCustom.get().getIdHCP() == null) {
                List<Usuario> posibleUsuarioList = usuarioRepository.findByCodigoUsuarioIdp(usuarioIdp); // busca en la tabla USUARIO con el "usuarioIdp"

                if(posibleUsuarioList == null || posibleUsuarioList.isEmpty()) { // para que nunca se actualice el IdHCP si el usuario logueado es un usuario interno
                    logger.error("ACTUALIZANDO UsuarioIdp DE PROVEEDOR // ruc: " + ruc + " / usuarioIdp: " + usuarioIdp);
                    proveedorRepository.updateIdHCP(usuarioIdp, ruc);
                }
            }
        }catch (Exception e){
            logger.error("EXCEPCION AL VERIFICAR/GUARDAR UsuarioIdp DE PROVEEDOR / ruc: " + ruc + " / usuarioIdp: " + usuarioIdp + " // " + e.getClass().getName() + " -- " + e.getMessage());
        }
    }

    private String getAttributeOfUser(User user, String key) {
        try {
            if (user == null) {
                return null;
            }
            //logger.error("Usuario getAttributeOfUser: " + user.toString());
            //logger.error("Usuario getAttributeOfUser key: " + key);
            String atributo = user.getAttribute(key);
            //logger.error("Usuario getAttributeOfUser atributo: " + atributo);
            return atributo;
        } catch (UnsupportedUserAttributeException ex) {
            logger.error("Error al leer el atributo " + key + " de la sesión", ex);
        }
        return null;
    }*/

    private void guardarUsuarioIdpDeProveedor(UserSession userSession) {
        String ruc = userSession.getRuc();
        String usuarioIdp = userSession.getId();

        try {
            //Optional<ProveedorCustom> opProveedorCustom = proveedorRepository.getProveedorIdHcpByRuc(ruc);
            Proveedor opProveedorCustom = proveedorRepository.getProveedorByRuc(ruc);
            //if (opProveedorCustom.isPresent() && opProveedorCustom.get().getIdHCP() == null) {
            if (opProveedorCustom != null && opProveedorCustom.getIdHcp() == null) {
                logger.error("ACTUALIZANDO UsuarioIdp DE PROVEEDOR // ruc: " + ruc + " / usuarioIdp: " + usuarioIdp);
                //proveedorRepository.updateIdHCP(usuarioIdp, ruc);
                //proveedorRepository.update(usuarioIdp, ruc);
            }
        } catch (Exception e) {
            logger.error("EXCEPCION AL VERIFICAR/GUARDAR UsuarioIdp DE PROVEEDOR / ruc: " + ruc + " / usuarioIdp: " + usuarioIdp + " // " + e.getClass().getName() + " -- " + e.getMessage());
        }
    }
}
