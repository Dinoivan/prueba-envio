package com.incloud.hcp.repository;

import com.incloud.hcp.domain.RubroBien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 20/09/2017.
 */
public interface RubroBienRepository extends JpaRepository<RubroBien, Integer> {

        public RubroBien findByCodigoSap(String codigoSap);

        public RubroBien getByCodigoSap(String codigoSap);

        @Query("select r from RubroBien r where r.tipoLicitacion.idTipoLicitacion = ?1 or r.tipoCuestionario.idTipoLicitacion=?1")
        public List<RubroBien> findByIdTipoLicitacionOrTipoCuestionario(Integer id);

        @Query(nativeQuery = true,
                value="SELECT trb.codigo_sap,trb.descripcion,trb.nivel from temp_rubro_bien trb where not exists (select * from rubro_bien r where trb.codigo_sap = r.codigo_sap)")
        public List<RubroBien> listNewRubroBien();

        @Query(nativeQuery = true,value ="SELECT (select rb.id_rubro from rubro_bien rb where rb.codigo_sap=trb.codigo_sap) as ID_RUBRO"
                +",trb.codigo_sap,trb.descripcion,trb.nivel" +
                "        from temp_rubro_bien trb" +
                "        where  exists (select * from rubro_bien r" +
                "                where trb.codigo_sap = r.codigo_sap and" +
                "                trb.descripcion <> r.descripcion)")
        public List<RubroBien> listUpdateRubroBien();


}
