package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.TileDynamic;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.myibatis.mapper.HomologacionMapper;
import com.incloud.hcp.myibatis.mapper.LicitacionMapper;
import com.incloud.hcp.repository.LicitacionProveedorRepository;
import com.incloud.hcp.repository.LicitacionRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.service.TileDynamicService;
import com.incloud.hcp.util.constant.LicitacionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by Administrador on 24/10/2017.
 */
@Service
public class TileDynamicServiceImpl implements TileDynamicService {
    @Autowired
    private HomologacionMapper homologacionMapper;

    @Autowired
    LicitacionProveedorRepository licitacionProveedorRepository;

    @Autowired
    LicitacionRepository licitacionRepository;

    @Autowired
    private LicitacionMapper licitacionMapper;

    @Autowired
    private ProveedorRepository proveedorRepository;


    @Override
    public TileDynamic getTileToAvanceHomologacion(Proveedor proveedor) {

        //Anteriormente se mostraba el avance de llenado de informacion
        //Se cambia para mostrar Nota de Evaluacion
        BigDecimal nota = Optional.ofNullable(proveedor)
                .map(p -> {
                    //homologacionMapper.getAvanceHomologacionByIdProveedor(p);
                    return p.getEvaluacionHomologacion();
                }).orElse(BigDecimal.ZERO);

        String state = "Negative";
        if(nota.doubleValue() <= 80){
            state = "Critical";
        }

        if(nota.doubleValue() >= 80){
            state = "Positive";
        }

        TileDynamic tile = new TileDynamic();
        tile.setIcon("sap-icon://Fiori2/F0381");
        tile.setInfo("");
        tile.setInfoState(state);
        tile.setNumber(nota);
        tile.setNumberDigits(1);
        tile.setNumberFactor("%");
        tile.setNumberState(state);
        tile.setNumberUnit("");
        tile.setStateArrow("UP");
        tile.setSubtitle("");
        tile.setTitle("Cuestionario Proveedor");

        return tile;
    }


    public TileDynamic getTileLicitacionesPorResponder(Proveedor proveedor) {
        TileDynamic tile = new TileDynamic();
        String estadoLicitacion = LicitacionConstant.ESTADO_PUBLICADA;
        Integer contador = this.licitacionProveedorRepository.countByProveedorAndEstadoLicitacion(proveedor, estadoLicitacion);

        tile.setIcon("sap-icon://sales-quote");
        if (contador.intValue() <= 0) {
            tile.setInfo("Licitaciones");
        }
        else {
            if (contador.intValue() == 1) {
                tile.setInfo("Licitación");
            }
            else {
                tile.setInfo("Licitaciones");
            }
        }
        tile.setInfoState("Critical");
        tile.setNumber(new BigDecimal(contador));
        tile.setNumberDigits(1);
        tile.setNumberFactor("");
        tile.setNumberState("Critical");
        tile.setNumberUnit("");
        tile.setStateArrow("UP");
        tile.setSubtitle("(Cotización)");
        tile.setTitle("Respuesta de Licitación");

        return tile;
    }

    public TileDynamic getTileHistoricoLicitacionesPorResponder(Proveedor proveedor) {
        TileDynamic tile = new TileDynamic();
        String estadoLicitacion = LicitacionConstant.ESTADO_PUBLICADA;
        Integer contador = this.licitacionMapper.countByFiltroBusquedaAdjudicadoProveedor(proveedor.getIdProveedor());

        tile.setIcon("sap-icon://sales-quote");
        if (contador.intValue() <= 0) {
            tile.setInfo("Licitaciones");
        }
        else {
            if (contador.intValue() == 1) {
                tile.setInfo("Licitación");
            }
            else {
                tile.setInfo("Licitaciones");
            }
        }

        tile.setInfoState("Positive");
        tile.setNumber(new BigDecimal(contador));
        tile.setNumberDigits(1);
        tile.setNumberFactor("");
        tile.setNumberState("Positive");
        tile.setNumberUnit("");
        tile.setStateArrow("UP");
        tile.setSubtitle("(Cotización)");
        tile.setTitle("Histórico Respuesta de Licitación");

        return tile;
    }

    public TileDynamic getTileCuadroComparativo() {
        TileDynamic tile = new TileDynamic();
        String estadoLicitacion = LicitacionConstant.ESTADO_POR_EVALUAR;
        Integer contador = this.licitacionRepository.countByEstadoLicitacion(estadoLicitacion);

        tile.setIcon("sap-icon://Fiori2/F0251");
        if (contador.intValue() <= 0) {
            tile.setInfo("Licitaciones");
        }
        else {
            if (contador.intValue() == 1) {
                tile.setInfo("Licitación");
            }
            else {
                tile.setInfo("Licitaciones");
            }
        }
        tile.setInfoState("Critical");
        tile.setNumber(new BigDecimal(contador));
        tile.setNumberDigits(1);
        tile.setNumberFactor("");
        tile.setNumberState("Critical");
        tile.setNumberUnit("");
        tile.setStateArrow("UP");
        tile.setSubtitle("");
        tile.setTitle("Cuadro Comparativo");

        return tile;
    }


    public TileDynamic getTileHistoricoCuadroComparativo() {
        TileDynamic tile = new TileDynamic();
        Integer contador = this.licitacionRepository.countByEstadoLicitacionEstadoTerminal();

        tile.setIcon("sap-icon://bbyd-active-sales");
        if (contador.intValue() <= 0) {
            tile.setInfo("Licitaciones");
        }
        else {
            if (contador.intValue() == 1) {
                tile.setInfo("Licitación");
            }
            else {
                tile.setInfo("Licitaciones");
            }
        }
        tile.setInfoState("Neutral");
        tile.setNumber(new BigDecimal(contador));
        tile.setNumberDigits(1);
        tile.setNumberFactor("");
        tile.setNumberState("Neutral");
        tile.setNumberUnit("");
        tile.setStateArrow("UP");
        tile.setSubtitle("");
        tile.setTitle("Histórico Cuadro Comparativo");

        return tile;
    }

}
