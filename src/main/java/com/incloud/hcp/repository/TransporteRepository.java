package com.incloud.hcp.repository;


import com.incloud.hcp.domain.balanza.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface  TransporteRepository extends JpaRepository<Transporte, Integer> {
    Transporte findByPlaca(String placa);
    Transporte findByModelo(String modelo);

    @Query("SELECT t FROM Transporte t where t.placa = ?1")
    List<Transporte> findByPlacaList(String placa);
    @Query("SELECT t.id FROM Transporte t where t.placa = ?1 group by t.id, t.placa")
    List<Integer> getIdTransporteByPlaca(String placa);

    @Query("SELECT t FROM Transporte t where t.remolque = ?1 AND t.estado != 'X'")
    List<Transporte> findRemolque(String remolque);

    @Query("SELECT t FROM Transporte t where t.estado != 'X'")
    List<Transporte> findTransporteActivo();
}
