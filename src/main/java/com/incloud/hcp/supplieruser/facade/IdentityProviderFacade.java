package com.incloud.hcp.supplieruser.facade;

import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.domain.SupplierUser;
import com.incloud.hcp.ws.ias.bean.IASResponse;
import com.incloud.hcp.ws.ias.bean.IASUserInfoResponse;
import com.incloud.hcp.ws.ias.dto.IASUserDto;
import com.incloud.hcp.ws.ias.service.IUserIASService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class IdentityProviderFacade {

    private final IUserIASService userIASService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public IASUserInfoResponse.Resource saveAndAssignRoleToUserClean(SupplierUser supplierUser, String groupId) {
        logger.error("supplierUser_saveAndAssignRoleToUserClean" + supplierUser);

        IASResponse responseIdp = userIASService.getUserByEmail(supplierUser.getEmail());
        logger.error("responseIdp-saveAnd " + responseIdp);

        boolean userExistsInIdp = responseIdp.getResult().getTotalResults() > 0;
        logger.error("userExistsInIdp " + userExistsInIdp);

        IASUserInfoResponse.Resource userIdp;
        String userId;

        if (userExistsInIdp) {
            String mensaje = "El usuario " + supplierUser.getEmail() + " ya existe en IAS.";
            throw new PortalException(mensaje);
        } else {
            IASUserDto requestUser = new IASUserDto();

            IASUserDto.IASName name = new IASUserDto.IASName();
            name.setGivenName(supplierUser.getFirstName());
            name.setFamilyName(supplierUser.getLastName());
            requestUser.setName(name);

            requestUser.setDisplayName(supplierUser.getDisplayName());
            requestUser.setUserName(supplierUser.getUserName());
            requestUser.setSendMail("true");

            IASUserDto.IASValue emailValue = new IASUserDto.IASValue(supplierUser.getEmail());
            requestUser.setEmails(Collections.singletonList(emailValue));

            IASResponse createResponse = userIASService.createUserProveedor(requestUser);
            userIdp = createResponse.getResult().getResources().get(0);
        }

        return userIdp;
    }

}