package com.incloud.hcp.repository;

import com.incloud.hcp.domain.almacen.GuiaDespacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface GuiaDespachoRepository extends JpaRepository<GuiaDespacho, Integer> {

    @Query("SELECT p FROM GuiaDespacho p WHERE p.id = ?1")
    GuiaDespacho findByIdGuiaDespacho(Integer id);

    @Query("SELECT p FROM GuiaDespacho p WHERE p.documentoMaterial = ?1")
    Optional<GuiaDespacho> findByDocumentoMaterial(String documento);

    @Query("SELECT p FROM GuiaDespacho p where p.fechaCreacion between ?1 and ?2")
    List<GuiaDespacho> getAllDespachos(Date fechaInicio, Date fechaFin);

    @Query("SELECT p FROM GuiaDespacho p where p.fechaCreacion between ?1 and ?2 and p.proveedorRuc = ?3")
    List<GuiaDespacho> getAllDespachosRuc(Date fechaInicio, Date fechaFin, String ruc);

    @Query("SELECT p FROM GuiaDespacho p where p.fechaCreacion between ?1 and ?2 and p.estadoDespacho.id = 1")
    List<GuiaDespacho> getDespachosRegistrados(Date fechaInicio, Date fechaFin);

    @Query("SELECT p FROM GuiaDespacho p where p.fechaCreacion between ?1 and ?2 and p.proveedorRuc = ?3 and p.estadoDespacho.id = 1")
    List<GuiaDespacho> getADespachosRegistradosRuc(Date fechaInicio, Date fechaFin, String ruc);
}
