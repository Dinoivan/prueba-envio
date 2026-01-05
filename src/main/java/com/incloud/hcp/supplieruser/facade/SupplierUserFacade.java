package com.incloud.hcp.supplieruser.facade;

import com.google.gson.Gson;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.domain.SupplierUser;
import com.incloud.hcp.repository.ISupplierUserRepository;
import com.incloud.hcp.supplieruser.services.interfaces.ISupplierUserService;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.service.notificacion.ProveedorAfiliadoNotificacion;
import com.incloud.hcp.util.UtilString;
import com.incloud.hcp.ws.ias.bean.IASResponse;
import com.incloud.hcp.ws.ias.bean.IASUserInfoResponse;
import com.incloud.hcp.ws.ias.service.IUserIASService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SupplierUserFacade {

    private String SUPPLIER_ROLE = "9822d302-3a44-449e-a463-d84a514c097d";

    private final IUserIASService userIASService;
    private final ProveedorRepository proveedorRepository;
    private final ISupplierUserService iSupplierUserService;
    private final IdentityProviderFacade identityProviderFacade;
    private final ISupplierUserRepository iSupplierUserRepository;
    private final ProveedorAfiliadoNotificacion proveedorAfiliadoNotificacion;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public SupplierUser saveSupplierUserAndCreateInIdp(SupplierUser input) throws Exception {

        boolean isNew = input.getId() == null;

        // --- VALIDACIONES PARA NUEVO ---
        if (isNew) {

            if (UtilString.isEmpty(input.getEmail())) {
                throw new RuntimeException("El campo email es obligatorio.");
            }

            if (input.getSupplierId() == null) {
                throw new RuntimeException("El campo supplierId es obligatorio.");
            }

            boolean emailUsed = iSupplierUserRepository.countByEmail(input.getEmail()) > 0;
            if (emailUsed) {
                throw new RuntimeException(
                        "El correo electrónico " + input.getEmail() + " ya está siendo utilizado por otro usuario: "
                );
            }
        }

        // --- VALIDACIONES COMUNES ---
        if (UtilString.isEmpty(input.getFirstName())) {
            throw new RuntimeException("El campo firstName es obligatorio.");
        }

        if (UtilString.isEmpty(input.getLastName())) {
            throw new RuntimeException("El campo lastName es obligatorio.");
        }

        if (UtilString.isEmpty(input.getDisplayName())) {
            throw new RuntimeException("El campo displayName es obligatorio.");
        }

        // --- CREAR/ACTUALIZAR USUARIO EN IDP ---
        if (isNew) {

            Gson gson = new Gson();
            logger.error("input_user " + gson.toJson(input));
            IASUserInfoResponse.Resource userIdp =
                    identityProviderFacade.saveAndAssignRoleToUserClean(input, SUPPLIER_ROLE);

            logger.error("userIdp_create_fin " + userIdp);
            input.setUserName(userIdp.getUserName());
            input.setIdentityProviderCode(userIdp.getId());
        }

        // --- GUARDAR EN BD ---
        SupplierUser saved = iSupplierUserService.save(input);

        Proveedor proveedorEmail = this.proveedorRepository.getProveedorByIdProveedor(input.getSupplierId());

        // --- NOTIFICAR  ---
        this.proveedorAfiliadoNotificacion.enviar(saved, proveedorEmail);

        return saved;
    }

    public Proveedor findSupplierByUserEmail( String email ){

        UtilString.validaCampoObligatorio( email, "email" );

        //--- Obtenemos el usuario proveedor según el email
        SupplierUser supplierUser = iSupplierUserService.findByEmail( email );

        return proveedorRepository.findById( supplierUser.getSupplierId() )
                .orElseThrow( () -> new PortalException( "Proveedor" +supplierUser.getSupplierId() ) );

    }

    @Transactional
    public SupplierUser deleteUserAndRemoveSupplierRole(Integer id) throws Exception {

        if (id == null) {
            throw new RuntimeException("Debe seleccionar un usuario para eliminar.");
        }

        SupplierUser supplierUser = iSupplierUserService.findById(id);

        IASResponse responseIdp = userIASService.getUserByEmail(supplierUser.getEmail());
        boolean userExistsInIdp = responseIdp.getResult().getTotalResults() > 0;

        if (userExistsInIdp) {
            IASUserInfoResponse.Resource informationUser = responseIdp.getResult().getResources().get(0);
            String userId = informationUser.getId();
            userIASService.deleteUsuarioIas(userId);
        }

        iSupplierUserService.deleteById(id);

        return supplierUser;
    }


}