package com.incloud.hcp.supplieruser.services;

import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.domain.SupplierUser;
import com.incloud.hcp.repository.ISupplierUserRepository;
import com.incloud.hcp.supplieruser.services.interfaces.ISupplierUserService;
import com.incloud.hcp.util.UtilString;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierUserService implements ISupplierUserService {

    private final ISupplierUserRepository iSupplierUserRepository;

    @Transactional(readOnly = true)
    @Override
    public List<SupplierUser> findBySupplierId(Integer supplierId) {

        UtilString.validaCampoObligatorio(supplierId, "supplierId");

        SupplierUser filter = SupplierUser.builder()
                .supplierId(supplierId)
                .build();

        return iSupplierUserRepository.findAll(Example.of(filter));

    }

    @Transactional(readOnly = true)
    @Override
    public SupplierUser findById(Integer id) {

        UtilString.validaCampoObligatorio(id, "id");

        return iSupplierUserRepository.findById(id)
                .orElseThrow(() -> new PortalException("No se encontró ningún registro de tipo " + SupplierUser.entityName + " con el identificador " + id));

    }

    @Transactional(readOnly = true)
    @Override
    public SupplierUser findByEmail(String email) {

        UtilString.validaCampoObligatorio(email, "email");

        return iSupplierUserRepository.findByEmail(email)
                .orElseThrow(() -> new PortalException("No se encontró ningún registro de tipo SupplierUser con el identificador " + email));

    }

    @Transactional(readOnly = true)
    @Override
    public Optional<String> findRucByEmail(String email) {

        UtilString.validaCampoObligatorio(email, "email");

        return iSupplierUserRepository.findRucByEmail(email);

    }

    @Transactional
    @Override
    public SupplierUser deleteById(Integer id) {

        if (id == null) {
            throw new RuntimeException("Debe indicar el ID del usuario a eliminar.");
        }

        SupplierUser user = iSupplierUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario no existe."));

        iSupplierUserRepository.deleteById(id);

        return user;
    }


    @Transactional
    @Override
    public SupplierUser save(SupplierUser input) {

        boolean isNew = input.getId() == null;

        SupplierUser supplierUser;

        // Crear nuevo
        supplierUser = SupplierUser.builder()
                .email(input.getEmail())
                .supplierId(input.getSupplierId())
                .creationDate(new Date())
                .build();

        // Campos comunes
        supplierUser.setFirstName(input.getFirstName());
        supplierUser.setLastName(input.getLastName());
        supplierUser.setDisplayName(input.getDisplayName());
        supplierUser.setUserName(input.getUserName());

        supplierUser = iSupplierUserRepository.save(supplierUser);

        // Persistir
        return supplierUser;
    }


}