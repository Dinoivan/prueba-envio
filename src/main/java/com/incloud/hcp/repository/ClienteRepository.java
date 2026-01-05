package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.Cliente;
import com.incloud.hcp.domain.balanza.ProveedorBLZ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c where c.ruc = ?1 and c.numDeudor=?2")
    List<Cliente> getClienteByByRucDeudor(String ruc,String deudor);

    @Query("SELECT c FROM Cliente c WHERE c.razonSocial = ?1")
    Cliente getClienteByRazonSocial(String razonSocial);
    @Query("SELECT c FROM Cliente c where c.id = ?1")
    Cliente getClienteByID(Long id);

    @Query("SELECT c FROM Cliente c WHERE c.ruc = ?1")
    Cliente getClienteByRUC(String ruc);

    @Query("SELECT c FROM Cliente c WHERE c.numDeudor = ?1")
    Cliente getClienteByDeudor(String numDeudor);
}
