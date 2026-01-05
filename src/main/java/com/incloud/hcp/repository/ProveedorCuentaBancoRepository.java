package com.incloud.hcp.repository;

import com.incloud.hcp.domain.ProveedorCuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by Administrador on 29/08/2017.
 */
public interface ProveedorCuentaBancoRepository extends JpaRepository<ProveedorCuentaBancaria, Integer> {

    @Query("SELECT b FROM ProveedorCuentaBancaria b WHERE b.proveedor.idProveedor= ?1")
    List<ProveedorCuentaBancaria> getListCuentaBancariaByIdProveedor(Integer idProveedor);

    @Modifying
    @Query("DELETE FROM ProveedorCuentaBancaria p WHERE p.proveedor.idProveedor= ?1")
    void deleteContactoByIdProveedor(Integer idProveedor);

    @Query("SELECT b FROM ProveedorCuentaBancaria b WHERE b.proveedor.idProveedor= ?1 and b.banco.claveBanco = ?2 and b.numeroCuenta =?3")
    Optional<ProveedorCuentaBancaria> getProveedorCuentaBancariaByIdProveedorAndClaveBancoAndNumeroCuenta(Integer idProveedor, String claveBanco, String numeroCuenta);
}
