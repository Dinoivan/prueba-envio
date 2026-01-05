package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.SEQUENCE;


/**
 * The persistent class for the licitacion_proveedor database table.
 *
 */
@Entity
@Table(name="licitacion_proveedor_tipo_cuestionario")
public class LicitacionProveedorTipoCuestionario extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "id_licitacion_proveedor_tipo_cuestionario", precision = 10)
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_licitacion_proveedor_tipo_cuestionario")
    @Id
    @SequenceGenerator(name = "seq_licitacion_proveedor_tipo_cuestionario", sequenceName = "seq_licitacion_proveedor_tipo_cuestionario", allocationSize = 1)
    private Integer id;


    // Raw attributes
    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "peso_condicion", nullable = false, precision = 15, scale = 2)
    private BigDecimal pesoCondicion;

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "peso_moneda", nullable = false, precision = 15, scale = 2)
    private BigDecimal pesoMoneda;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "peso_evaluacion_tecnica", nullable = false, precision = 15, scale = 2)
    private BigDecimal pesoEvaluacionTecnica;

    @Digits(integer = 10, fraction = 0)
    @NotNull
    @Column(name = "factor_denominador_tasa", nullable = false, precision = 10)
    private Integer factorDenominadorTasa;

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "tasa_moneda", nullable = false, precision = 15, scale = 2)
    private BigDecimal tasaMoneda;


    @Digits(integer = 13, fraction = 2)
    @Column(name = "precio_suma", nullable = false, precision = 15, scale = 2)
    private BigDecimal precioSuma;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "tasa_interes_moneda", nullable = false, precision = 15, scale = 2)
    private BigDecimal tasaInteresMoneda;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "costo_financiero_moneda", nullable = false, precision = 15, scale = 2)
    private BigDecimal costoFinancieroMoneda;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "precio_final_moneda", nullable = false, precision = 15, scale = 2)
    private BigDecimal precioFinalMoneda;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "precio_final_minimo_moneda", nullable = false, precision = 15, scale = 2)
    private BigDecimal precioFinalMinimoMoneda;


    @Digits(integer = 13, fraction = 2)
    @Column(name = "puntaje_condicion", nullable = false, precision = 15, scale = 2)
    private BigDecimal puntajeCondicion;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "puntaje_maximo_condicion", nullable = false, precision = 15, scale = 2)
    private BigDecimal puntajeMaximoCondicion;


    @Digits(integer = 13, fraction = 2)
    @Column(name = "puntaje_evaluacion_tecnica", nullable = false, precision = 15, scale = 2)
    private BigDecimal puntajeEvaluacionTecnica;


    @Digits(integer = 13, fraction = 2)
    @Column(name = "porcentaje_precio_obtenido", nullable = false, precision = 15, scale = 2)
    private BigDecimal porcentajePrecioObtenido;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "porcentaje_condicion_obtenido", nullable = false, precision = 15, scale = 2)
    private BigDecimal porcentajeCondicionObtenido;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "porcentaje_evaluacion_tecnica_obtenido", nullable = false, precision = 15, scale = 2)
    private BigDecimal porcentajeEvaluacionTecnicaObtenido;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "porcentaje_total_obtenido", nullable = false, precision = 15, scale = 2)
    private BigDecimal porcentajeTotalObtenido;

//    @NotNull
//    @Column(name = "fecha_tasa_cambio", nullable = false, length = 29)
//    @Temporal(TIMESTAMP)
//    private Date fechaTasaCambio;

    @Digits(integer = 14, fraction = 4)
    @NotNull
    @Column(name = "tasa_cambio", nullable = false, precision = 18, scale = 4)
    private BigDecimal tasaCambio;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "cantidad_total_cotizacion", nullable = false, precision = 15, scale = 2)
    private BigDecimal cantidadTotalCotizacion;


    @Digits(integer = 13, fraction = 2)
    @Column(name = "cantidad_total_licitacion", nullable = false, precision = 15, scale = 2)
    private BigDecimal cantidadTotalLicitacion;



    //uni-directional many-to-one association to Licitacion
    @ManyToOne
    @JoinColumn(name="id_licitacion", nullable=false)
    private Licitacion licitacion;

    //uni-directional many-to-one association to Proveedor
    @ManyToOne
    @JoinColumn(name="id_proveedor", nullable=false)
    private Proveedor proveedor;


    //uni-directional many-to-one association to TipoLicitacion
    @ManyToOne
    @JoinColumn(name="id_tipo_licitacion", nullable=false)
    private TipoLicitacion tipoLicitacion;

    //uni-directional many-to-one association to TipoLicitacion
    @ManyToOne
    @JoinColumn(name="id_tipo_cuestionario", nullable=false)
    private TipoLicitacion tipoCuestionario;

    @ManyToOne
    @JoinColumn(name = "id_condicion_pago", nullable=false)
    private CondicionPago condicionPago;

    @ManyToOne
    @JoinColumn(name = "id_moneda", nullable=false)
    private Moneda moneda;




    public LicitacionProveedorTipoCuestionario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrecioSuma() {
        return precioSuma;
    }

    public void setPrecioSuma(BigDecimal precioSuma) {
        this.precioSuma = precioSuma;
    }

    public BigDecimal getPesoCondicion() {
        return pesoCondicion;
    }

    public void setPesoCondicion(BigDecimal pesoCondicion) {
        this.pesoCondicion = pesoCondicion;
    }

    public BigDecimal getPesoMoneda() {
        return pesoMoneda;
    }

    public void setPesoMoneda(BigDecimal pesoMoneda) {
        this.pesoMoneda = pesoMoneda;
    }

    public BigDecimal getPesoEvaluacionTecnica() {
        return pesoEvaluacionTecnica;
    }

    public void setPesoEvaluacionTecnica(BigDecimal pesoEvaluacionTecnica) {
        this.pesoEvaluacionTecnica = pesoEvaluacionTecnica;
    }

    public BigDecimal getTasaInteresMoneda() {
        return tasaInteresMoneda;
    }

    public void setTasaInteresMoneda(BigDecimal tasaInteresMoneda) {
        this.tasaInteresMoneda = tasaInteresMoneda;
    }

    public BigDecimal getCostoFinancieroMoneda() {
        return costoFinancieroMoneda;
    }

    public void setCostoFinancieroMoneda(BigDecimal costoFinancieroMoneda) {
        this.costoFinancieroMoneda = costoFinancieroMoneda;
    }

    public BigDecimal getPrecioFinalMoneda() {
        return precioFinalMoneda;
    }

    public void setPrecioFinalMoneda(BigDecimal precioFinalMoneda) {
        this.precioFinalMoneda = precioFinalMoneda;
    }

    public BigDecimal getPrecioFinalMinimoMoneda() {
        return precioFinalMinimoMoneda;
    }

    public void setPrecioFinalMinimoMoneda(BigDecimal precioFinalMinimoMoneda) {
        this.precioFinalMinimoMoneda = precioFinalMinimoMoneda;
    }

    public BigDecimal getPuntajeCondicion() {
        return puntajeCondicion;
    }

    public void setPuntajeCondicion(BigDecimal puntajeCondicion) {
        this.puntajeCondicion = puntajeCondicion;
    }

    public BigDecimal getPuntajeMaximoCondicion() {
        return puntajeMaximoCondicion;
    }

    public void setPuntajeMaximoCondicion(BigDecimal puntajeMaximoCondicion) {
        this.puntajeMaximoCondicion = puntajeMaximoCondicion;
    }

    public BigDecimal getPuntajeEvaluacionTecnica() {
        return puntajeEvaluacionTecnica;
    }

    public void setPuntajeEvaluacionTecnica(BigDecimal puntajeEvaluacionTecnica) {
        this.puntajeEvaluacionTecnica = puntajeEvaluacionTecnica;
    }

    public BigDecimal getPorcentajePrecioObtenido() {
        return porcentajePrecioObtenido;
    }

    public void setPorcentajePrecioObtenido(BigDecimal porcentajePrecioObtenido) {
        this.porcentajePrecioObtenido = porcentajePrecioObtenido;
    }

    public BigDecimal getPorcentajeCondicionObtenido() {
        return porcentajeCondicionObtenido;
    }

    public void setPorcentajeCondicionObtenido(BigDecimal porcentajeCondicionObtenido) {
        this.porcentajeCondicionObtenido = porcentajeCondicionObtenido;
    }

    public BigDecimal getPorcentajeEvaluacionTecnicaObtenido() {
        return porcentajeEvaluacionTecnicaObtenido;
    }

    public void setPorcentajeEvaluacionTecnicaObtenido(BigDecimal porcentajeEvaluacionTecnicaObtenido) {
        this.porcentajeEvaluacionTecnicaObtenido = porcentajeEvaluacionTecnicaObtenido;
    }

    public BigDecimal getPorcentajeTotalObtenido() {
        return porcentajeTotalObtenido;
    }

    public void setPorcentajeTotalObtenido(BigDecimal porcentajeTotalObtenido) {
        this.porcentajeTotalObtenido = porcentajeTotalObtenido;
    }

    public Licitacion getLicitacion() {
        return licitacion;
    }

    public void setLicitacion(Licitacion licitacion) {
        this.licitacion = licitacion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public TipoLicitacion getTipoLicitacion() {
        return tipoLicitacion;
    }

    public void setTipoLicitacion(TipoLicitacion tipoLicitacion) {
        this.tipoLicitacion = tipoLicitacion;
    }

    public TipoLicitacion getTipoCuestionario() {
        return tipoCuestionario;
    }

    public void setTipoCuestionario(TipoLicitacion tipoCuestionario) {
        this.tipoCuestionario = tipoCuestionario;
    }

    public CondicionPago getCondicionPago() {
        return condicionPago;
    }

    public void setCondicionPago(CondicionPago condicionPago) {
        this.condicionPago = condicionPago;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public Integer getFactorDenominadorTasa() {
        return factorDenominadorTasa;
    }

    public void setFactorDenominadorTasa(Integer factorDenominadorTasa) {
        this.factorDenominadorTasa = factorDenominadorTasa;
    }

    public BigDecimal getTasaMoneda() {
        return tasaMoneda;
    }

    public void setTasaMoneda(BigDecimal tasaMoneda) {
        this.tasaMoneda = tasaMoneda;
    }

//    public Date getFechaTasaCambio() {
//        return fechaTasaCambio;
//    }
//
//    public void setFechaTasaCambio(Date fechaTasaCambio) {
//        this.fechaTasaCambio = fechaTasaCambio;
//    }

    public BigDecimal getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(BigDecimal tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    public BigDecimal getCantidadTotalCotizacion() {
        return cantidadTotalCotizacion;
    }

    public void setCantidadTotalCotizacion(BigDecimal cantidadTotalCotizacion) {
        this.cantidadTotalCotizacion = cantidadTotalCotizacion;
    }

    public BigDecimal getCantidadTotalLicitacion() {
        return cantidadTotalLicitacion;
    }

    public void setCantidadTotalLicitacion(BigDecimal cantidadTotalLicitacion) {
        this.cantidadTotalLicitacion = cantidadTotalLicitacion;
    }

    @Override
    public String toString() {
        return "LicitacionProveedorTipoCuestionario{" +
                "id=" + id +
                ", pesoCondicion=" + pesoCondicion +
                ", pesoMoneda=" + pesoMoneda +
                ", pesoEvaluacionTecnica=" + pesoEvaluacionTecnica +
                ", factorDenominadorTasa=" + factorDenominadorTasa +
                ", tasaMoneda=" + tasaMoneda +
                ", precioSuma=" + precioSuma +
                ", tasaInteresMoneda=" + tasaInteresMoneda +
                ", costoFinancieroMoneda=" + costoFinancieroMoneda +
                ", precioFinalMoneda=" + precioFinalMoneda +
                ", precioFinalMinimoMoneda=" + precioFinalMinimoMoneda +
                ", puntajeCondicion=" + puntajeCondicion +
                ", puntajeMaximoCondicion=" + puntajeMaximoCondicion +
                ", puntajeEvaluacionTecnica=" + puntajeEvaluacionTecnica +
                ", porcentajePrecioObtenido=" + porcentajePrecioObtenido +
                ", porcentajeCondicionObtenido=" + porcentajeCondicionObtenido +
                ", porcentajeEvaluacionTecnicaObtenido=" + porcentajeEvaluacionTecnicaObtenido +
                ", porcentajeTotalObtenido=" + porcentajeTotalObtenido +
                ", tasaCambio=" + tasaCambio +
                ", cantidadTotalCotizacion=" + cantidadTotalCotizacion +
                ", cantidadTotalLicitacion=" + cantidadTotalLicitacion +
                ", licitacion=" + licitacion +
                ", proveedor=" + proveedor +
                ", tipoLicitacion=" + tipoLicitacion +
                ", tipoCuestionario=" + tipoCuestionario +
                ", condicionPago=" + condicionPago +
                ", moneda=" + moneda +
                '}';
    }

    public String toString02() {
        return "LicitacionProveedorTipoCuestionario{" +
                "id=" + id +
                ", pesoCondicion=" + pesoCondicion +
                ", pesoMoneda=" + pesoMoneda +
                ", pesoEvaluacionTecnica=" + pesoEvaluacionTecnica +
                ", factorDenominadorTasa=" + factorDenominadorTasa +
                ", tasaMoneda=" + tasaMoneda +
                ", precioSuma=" + precioSuma +
                ", tasaInteresMoneda=" + tasaInteresMoneda +
                ", costoFinancieroMoneda=" + costoFinancieroMoneda +
                ", precioFinalMoneda=" + precioFinalMoneda +
                ", precioFinalMinimoMoneda=" + precioFinalMinimoMoneda +
                ", puntajeCondicion=" + puntajeCondicion +
                ", puntajeMaximoCondicion=" + puntajeMaximoCondicion +
                ", puntajeEvaluacionTecnica=" + puntajeEvaluacionTecnica +
                ", porcentajePrecioObtenido=" + porcentajePrecioObtenido +
                ", porcentajeCondicionObtenido=" + porcentajeCondicionObtenido +
                ", porcentajeEvaluacionTecnicaObtenido=" + porcentajeEvaluacionTecnicaObtenido +
                ", porcentajeTotalObtenido=" + porcentajeTotalObtenido +
//                ", fechaTasaCambio=" + fechaTasaCambio +
                ", tasaCambio=" + tasaCambio +
                ", cantidadTotalCotizacion=" + cantidadTotalCotizacion +
                ", cantidadTotalLicitacion=" + cantidadTotalLicitacion +
                ", licitacion=" + licitacion.getIdLicitacion() +
                ", proveedor=" + proveedor.getIdProveedor() +
                ", tipoLicitacion=" + tipoLicitacion.getIdTipoLicitacion() +
                ", tipoCuestionario=" + tipoCuestionario.getIdTipoLicitacion() +
                ", condicionPago=" + condicionPago.getIdCondicionPago() +
                ", moneda=" + moneda.getIdMoneda() +
                '}';
    }
}