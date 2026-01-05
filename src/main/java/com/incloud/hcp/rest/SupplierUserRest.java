package com.incloud.hcp.rest;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.config.SystemLoggedUser;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.dto.SupplierUserDto;
import com.incloud.hcp.supplieruser.facade.SupplierUserFacade;
import com.incloud.hcp.domain.SupplierUser;
import com.incloud.hcp.supplieruser.services.interfaces.ISupplierUserService;
import com.incloud.hcp.myibatis.mapper.ISupplierUserMapper;
import com.incloud.hcp.service.ProveedorService;
import com.sap.cloud.security.xsuaa.token.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping( "api/supplier-user" )
@RequiredArgsConstructor
public class SupplierUserRest {
    private final ProveedorService proveedorService;
    private final SystemLoggedUser systemLoggedUser;
    private final SupplierUserFacade supplierUserFacade;
    private final ISupplierUserService iSupplierUserService;
    private final ISupplierUserMapper iSupplierUserMapper;

    @GetMapping( "/find-by-supplier/{supplierId}" )
    public ResponseEntity<?> findBySupplier(@PathVariable("supplierId") Integer supplierId) {
        try {
            List<SupplierUser> supplierUsers = iSupplierUserService.findBySupplierId(supplierId);
            return ResponseEntity.ok(supplierUsers);
        } catch (Exception ex) {
            log.error("[/find-by-supplier] Error: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar supplier users: " + ex.getMessage());
        }
    }

    @PostMapping( "/find-by-id/{supplierId}" )
    public ResponseEntity<SupplierUser> findById( @PathVariable( "supplierId" ) Integer id ){

        SupplierUser supplierUser ;

        try{

            supplierUser = iSupplierUserService.findById( id );

        }catch ( Exception ex ){

            log.error( "[/find-by-id] Detalle del problema: {}", ex.getMessage() );
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).build();

        }

        return ResponseEntity.ok( supplierUser );

    }

    @PostMapping("/save-and-create-user-idp")
    public ResponseEntity<?> save(@RequestBody SupplierUser input) {
        try {
            Object result = supplierUserFacade.saveSupplierUserAndCreateInIdp(input);
            return ResponseEntity.ok(result);

        } catch (PortalException ex) {
            log.error("[/save-and-create-user-idp] {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)   // <--- código 400
                    .body(ex.getMessage());                       // <--- mensaje claro al frontend
        } catch (Exception ex) {
            log.error("[/save-and-create-user-idp] {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar usuario: " + ex.getMessage());
        }
    }




    @DeleteMapping( "/delete-by-id/{supplierId}" )
    public ResponseEntity<?> deleteById(@PathVariable("supplierId") Integer id) {
        try {
            Object response = supplierUserFacade.deleteUserAndRemoveSupplierRole(id);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            log.error("[/delete-by-id] {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar usuario: " + ex.getMessage());
        }
    }

    @GetMapping( "/find-supplier-by-email/{email}" )
    public ResponseEntity<?> findSupplierByEmail(@PathVariable("email") String email) {
        try {
            Proveedor supplier = supplierUserFacade.findSupplierByUserEmail(email);
            return ResponseEntity.ok(supplier);

        } catch (Exception ex) {
            log.error("[/find-supplier-by-email] {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar proveedor: " + ex.getMessage());
        }
    }

    @GetMapping( "/find-supplier-by-token" )
    public ResponseEntity<?> findSupplierByToken(@AuthenticationPrincipal Token token) {
        try {
            UserSession userSession = systemLoggedUser.getUserSessionByToken(token);

            return ResponseEntity.ok(userSession);

        } catch (Exception ex) {
            log.error("[/find-supplier-by-token] {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar proveedor: " + ex.getMessage());
        }
    }

    @GetMapping( "/get-all-suppliers" )
    public ResponseEntity<?> getAllSuppliers(@AuthenticationPrincipal Token token) {
        try {
            List<SupplierUserDto> listSupplier = this.iSupplierUserMapper.getAllSuppliers();

            return ResponseEntity.ok(listSupplier);

        } catch (Exception ex) {
            log.error("[/find-supplier-by-token] {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar proveedor: " + ex.getMessage());
        }
    }

}