package com.incloud.hcp.repository;

import com.incloud.hcp.domain.balanza.Chofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChoferRepository extends JpaRepository<Chofer, Integer> {

    public Chofer findByLicencia(String licencia);

    public Chofer findByDni(String dni);
    @Query("SELECT c FROM Chofer c where c.dni = ?1 and c.licencia =?2")
    List<Chofer> getChoferByDniAndLicencia(String dni, String licencia);

    @Query("SELECT c FROM Chofer c where c.dni = ?1")
    List<Chofer> getChoferByDniList(String dni);

    @Query("SELECT c.id FROM Chofer c where c.dni = ?1 group by c.dni, c.id")
    List<Integer> getIdChoferByDni(String dni);

    @Query("SELECT c.id FROM Chofer c where c.dni = ?1 and c.licencia =?2")
    List<Integer> getIdChoferByDniLicenciaIntegers(String dni, String licencia);

    @Query("SELECT c FROM Chofer c where c.dni = ?1 and c.licencia =?2")
    List<Chofer> getIdChoferByDniLicencia(String dni, String licencia);
}
