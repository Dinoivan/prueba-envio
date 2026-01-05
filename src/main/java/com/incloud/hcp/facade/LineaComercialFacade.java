package com.incloud.hcp.facade;

import com.incloud.hcp.bean.Linea;
import com.incloud.hcp.bean.LineaFamilia;
import com.incloud.hcp.bean.LineaSubFamilia;

import java.util.List;

public interface LineaComercialFacade {
    /**
     * Retorna la lista de lineas comerciales. Sin incluir las lineas comerciales Generales.
     * @return
     */
    List<Linea> getListLineaWithoutIndGeneral();

    /**
     * Retorna la lista de lineas comerciales.
     * @return
     */
    List<Linea> getListLinea();

    /**
     * Retorna la familia de linea.
     * @param idLinea
     * @return
     */
    List<LineaFamilia> getLineaFamilia(Integer idLinea);



    /**
     * Retorna la sub familia de familia.
     * @param idFamilia
     * @return
     */
    List<LineaSubFamilia> getLineaSubFamilia(Integer idFamilia);
}
