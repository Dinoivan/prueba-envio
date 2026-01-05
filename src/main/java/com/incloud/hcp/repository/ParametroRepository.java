package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by USER on 23/08/2017.
 */
public interface ParametroRepository extends JpaRepository<Parametro,Integer> {

    List<Parametro> findByModuloAndTipo(String modulo, String tipo);

    List<Parametro> findByModuloAndTipoOrderByValor(String modulo, String tipo);

    List<Parametro> findByModuloAndTipoAndCodigo(String modulo, String tipo, String codigo);

    List<Parametro> findByModuloAndTipoAndValorAsociado(String modulo, String tipo, String valorAsociado);

    @Query("SELECT p FROM Parametro p  WHERE p.modulo=:modulo and p.tipo=:tipo")
    Parametro getParametroByModuloAndTipo(@Param("modulo") String modulo,
                                                 @Param("tipo") String tipo);

    @Query("SELECT p FROM Parametro p  WHERE p.modulo=:modulo and p.tipo=:tipo and p.codigo=:codigo")
    Parametro getParametroByModuloAndTipoAndCodigo(@Param("modulo") String modulo,
                                          @Param("tipo") String tipo,
                                          @Param("codigo") String codigo);

    @Modifying
    @Query("UPDATE Parametro t SET t.valor=?1 WHERE t.modulo=?2 and t.tipo=?3")
    public void updateValueParameter(String value, String module, String type);

    @Modifying
    @Query("DELETE FROM Parametro p WHERE p.tipo = ?1")
    void deleteByTipo(String tipo);

    Parametro getByModuloAndTipoAndCodigo(String modulo, String tipo, String codigo);

}
