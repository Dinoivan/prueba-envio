package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by MARCELO on 07/11/2017.
 */
public interface CargoRepository extends JpaRepository<Cargo, Integer> {

    public List<Cargo> findAllByOrderByNombreCargoAsc();


}
