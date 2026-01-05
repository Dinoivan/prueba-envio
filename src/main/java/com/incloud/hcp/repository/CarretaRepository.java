package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.Carreta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarretaRepository extends JpaRepository<Carreta, Integer> {
    public Carreta findByDescripcion(String descripcion);

    public Carreta findByCodigo(String codigo);
    @Query("SELECT t FROM Carreta t where t.placa = ?1")
    List<Carreta> findByPlacaList(String placa);
    @Query("SELECT t.id FROM Carreta t where t.placa = ?1 group by t.id, t.placa")
    List<Integer> getIdCarretaByPlaca(String placa);
}
