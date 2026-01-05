package com.incloud.hcp.rest.bean;

import com.incloud.hcp.domain.GrupoCondicionLicRespuesta;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by USER on 31/08/2017.
 */
public class CondicionCotizacionDTO {

    private String tipoLicitacion;
    private String tipoCuestionario;
    private String pregunta;
    private String respuestaPrdeterminada;
    private Integer idLicitacionGrupoCondicion;
    private String bloqueadoProveedor;
    private boolean estaNoBloqueadoProveedor;

    private String tipoCampo;
    private boolean esLista;
    private boolean esTexto;
    private BigDecimal pesoPregunta;
    private List<GrupoCondicionLicRespuesta> listaRespuestas;

    private Boolean visible01;
    private String nombreProveedor01;
    private String respuesta01;
    private Integer idCondicionRespuesta01;
    private String textoRespuestaLibre01;


    private Boolean visible02;
    private String nombreProveedor02;
    private String respuesta02;
    private Integer idCondicionRespuesta02;
    private String textoRespuestaLibre02;

    private Boolean visible03;
    private String nombreProveedor03;
    private String respuesta03;
    private Integer idCondicionRespuesta03;
    private String textoRespuestaLibre03;

    private Boolean visible04;
    private String nombreProveedor04;
    private String respuesta04;
    private Integer idCondicionRespuesta04;
    private String textoRespuestaLibre04;

    private Boolean visible05;
    private String nombreProveedor05;
    private String respuesta05;
    private Integer idCondicionRespuesta05;
    private String textoRespuestaLibre05;

    private Boolean visible06;
    private String nombreProveedor06;
    private String respuesta06;
    private Integer idCondicionRespuesta06;
    private String textoRespuestaLibre06;

    private Boolean visible07;
    private String nombreProveedor07;
    private String respuesta07;
    private Integer idCondicionRespuesta07;
    private String textoRespuestaLibre07;

    private Boolean visible08;
    private String nombreProveedor08;
    private String respuesta08;
    private Integer idCondicionRespuesta08;
    private String textoRespuestaLibre08;

    private Boolean visible09;
    private String nombreProveedor09;
    private String respuesta09;
    private Integer idCondicionRespuesta09;
    private String textoRespuestaLibre09;

    private Boolean visible10;
    private String nombreProveedor10;
    private String respuesta10;
    private Integer idCondicionRespuesta10;
    private String textoRespuestaLibre10;


    public String getTipoLicitacion() {
        return tipoLicitacion;
    }

    public void setTipoLicitacion(String tipoLicitacion) {
        this.tipoLicitacion = tipoLicitacion;
    }

    public String getTipoCuestionario() {
        return tipoCuestionario;
    }

    public void setTipoCuestionario(String tipoCuestionario) {
        this.tipoCuestionario = tipoCuestionario;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuestaPrdeterminada() {
        return respuestaPrdeterminada;
    }

    public void setRespuestaPrdeterminada(String respuestaPrdeterminada) {
        this.respuestaPrdeterminada = respuestaPrdeterminada;
    }

    public Boolean getVisible01() {
        return visible01;
    }

    public void setVisible01(Boolean visible01) {
        this.visible01 = visible01;
    }

    public String getNombreProveedor01() {
        return nombreProveedor01;
    }

    public void setNombreProveedor01(String nombreProveedor01) {
        this.nombreProveedor01 = nombreProveedor01;
    }

    public String getRespuesta01() {
        return respuesta01;
    }

    public void setRespuesta01(String respuesta01) {
        this.respuesta01 = respuesta01;
    }

    public Boolean getVisible02() {
        return visible02;
    }

    public void setVisible02(Boolean visible02) {
        this.visible02 = visible02;
    }

    public String getNombreProveedor02() {
        return nombreProveedor02;
    }

    public void setNombreProveedor02(String nombreProveedor02) {
        this.nombreProveedor02 = nombreProveedor02;
    }

    public String getRespuesta02() {
        return respuesta02;
    }

    public void setRespuesta02(String respuesta02) {
        this.respuesta02 = respuesta02;
    }

    public Boolean getVisible03() {
        return visible03;
    }

    public void setVisible03(Boolean visible03) {
        this.visible03 = visible03;
    }

    public String getNombreProveedor03() {
        return nombreProveedor03;
    }

    public void setNombreProveedor03(String nombreProveedor03) {
        this.nombreProveedor03 = nombreProveedor03;
    }

    public String getRespuesta03() {
        return respuesta03;
    }

    public void setRespuesta03(String respuesta03) {
        this.respuesta03 = respuesta03;
    }

    public Boolean getVisible04() {
        return visible04;
    }

    public void setVisible04(Boolean visible04) {
        this.visible04 = visible04;
    }

    public String getNombreProveedor04() {
        return nombreProveedor04;
    }

    public void setNombreProveedor04(String nombreProveedor04) {
        this.nombreProveedor04 = nombreProveedor04;
    }

    public String getRespuesta04() {
        return respuesta04;
    }

    public void setRespuesta04(String respuesta04) {
        this.respuesta04 = respuesta04;
    }

    public Boolean getVisible05() {
        return visible05;
    }

    public void setVisible05(Boolean visible05) {
        this.visible05 = visible05;
    }

    public String getNombreProveedor05() {
        return nombreProveedor05;
    }

    public void setNombreProveedor05(String nombreProveedor05) {
        this.nombreProveedor05 = nombreProveedor05;
    }

    public String getRespuesta05() {
        return respuesta05;
    }

    public void setRespuesta05(String respuesta05) {
        this.respuesta05 = respuesta05;
    }

    public Boolean getVisible06() {
        return visible06;
    }

    public void setVisible06(Boolean visible06) {
        this.visible06 = visible06;
    }

    public String getNombreProveedor06() {
        return nombreProveedor06;
    }

    public void setNombreProveedor06(String nombreProveedor06) {
        this.nombreProveedor06 = nombreProveedor06;
    }

    public String getRespuesta06() {
        return respuesta06;
    }

    public void setRespuesta06(String respuesta06) {
        this.respuesta06 = respuesta06;
    }

    public Boolean getVisible07() {
        return visible07;
    }

    public void setVisible07(Boolean visible07) {
        this.visible07 = visible07;
    }

    public String getNombreProveedor07() {
        return nombreProveedor07;
    }

    public void setNombreProveedor07(String nombreProveedor07) {
        this.nombreProveedor07 = nombreProveedor07;
    }

    public String getRespuesta07() {
        return respuesta07;
    }

    public void setRespuesta07(String respuesta07) {
        this.respuesta07 = respuesta07;
    }

    public Boolean getVisible08() {
        return visible08;
    }

    public void setVisible08(Boolean visible08) {
        this.visible08 = visible08;
    }

    public String getNombreProveedor08() {
        return nombreProveedor08;
    }

    public void setNombreProveedor08(String nombreProveedor08) {
        this.nombreProveedor08 = nombreProveedor08;
    }

    public String getRespuesta08() {
        return respuesta08;
    }

    public void setRespuesta08(String respuesta08) {
        this.respuesta08 = respuesta08;
    }

    public Boolean getVisible09() {
        return visible09;
    }

    public void setVisible09(Boolean visible09) {
        this.visible09 = visible09;
    }

    public String getNombreProveedor09() {
        return nombreProveedor09;
    }

    public void setNombreProveedor09(String nombreProveedor09) {
        this.nombreProveedor09 = nombreProveedor09;
    }

    public String getRespuesta09() {
        return respuesta09;
    }

    public void setRespuesta09(String respuesta09) {
        this.respuesta09 = respuesta09;
    }

    public Boolean getVisible10() {
        return visible10;
    }

    public void setVisible10(Boolean visible10) {
        this.visible10 = visible10;
    }

    public String getNombreProveedor10() {
        return nombreProveedor10;
    }

    public void setNombreProveedor10(String nombreProveedor10) {
        this.nombreProveedor10 = nombreProveedor10;
    }

    public String getRespuesta10() {
        return respuesta10;
    }

    public void setRespuesta10(String respuesta10) {
        this.respuesta10 = respuesta10;
    }

    public String getTipoCampo() {
        return tipoCampo;
    }

    public void setTipoCampo(String tipoCampo) {
        this.tipoCampo = tipoCampo;
    }

    public BigDecimal getPesoPregunta() {
        return pesoPregunta;
    }

    public void setPesoPregunta(BigDecimal pesoPregunta) {
        this.pesoPregunta = pesoPregunta;
    }

    public List<GrupoCondicionLicRespuesta> getListaRespuestas() {
        return listaRespuestas;
    }

    public void setListaRespuestas(List<GrupoCondicionLicRespuesta> listaRespuestas) {
        this.listaRespuestas = listaRespuestas;
    }

    public boolean isEsLista() {
        if (this.tipoCampo.equals("T"))
            return false;
        return true;
    }

    public void setEsLista(boolean esLista) {
        this.esLista = esLista;
    }

    public boolean isEsTexto() {
        if (this.tipoCampo.equals("L"))
            return false;
        return true;
    }

    public void setEsTexto(boolean esTexto) {
        this.esTexto = esTexto;
    }

    public Integer getIdLicitacionGrupoCondicion() {
        return idLicitacionGrupoCondicion;
    }

    public void setIdLicitacionGrupoCondicion(Integer idLicitacionGrupoCondicion) {
        this.idLicitacionGrupoCondicion = idLicitacionGrupoCondicion;
    }


    public Integer getIdCondicionRespuesta01() {
        return idCondicionRespuesta01;
    }

    public void setIdCondicionRespuesta01(Integer idCondicionRespuesta01) {
        this.idCondicionRespuesta01 = idCondicionRespuesta01;
    }

    public String getTextoRespuestaLibre01() {
        return textoRespuestaLibre01;
    }

    public void setTextoRespuestaLibre01(String textoRespuestaLibre01) {
        this.textoRespuestaLibre01 = textoRespuestaLibre01;
    }

    public Integer getIdCondicionRespuesta02() {
        return idCondicionRespuesta02;
    }

    public void setIdCondicionRespuesta02(Integer idCondicionRespuesta02) {
        this.idCondicionRespuesta02 = idCondicionRespuesta02;
    }

    public String getTextoRespuestaLibre02() {
        return textoRespuestaLibre02;
    }

    public void setTextoRespuestaLibre02(String textoRespuestaLibre02) {
        this.textoRespuestaLibre02 = textoRespuestaLibre02;
    }

    public Integer getIdCondicionRespuesta03() {
        return idCondicionRespuesta03;
    }

    public void setIdCondicionRespuesta03(Integer idCondicionRespuesta03) {
        this.idCondicionRespuesta03 = idCondicionRespuesta03;
    }

    public String getTextoRespuestaLibre03() {
        return textoRespuestaLibre03;
    }

    public void setTextoRespuestaLibre03(String textoRespuestaLibre03) {
        this.textoRespuestaLibre03 = textoRespuestaLibre03;
    }

    public Integer getIdCondicionRespuesta04() {
        return idCondicionRespuesta04;
    }

    public void setIdCondicionRespuesta04(Integer idCondicionRespuesta04) {
        this.idCondicionRespuesta04 = idCondicionRespuesta04;
    }

    public String getTextoRespuestaLibre04() {
        return textoRespuestaLibre04;
    }

    public void setTextoRespuestaLibre04(String textoRespuestaLibre04) {
        this.textoRespuestaLibre04 = textoRespuestaLibre04;
    }

    public Integer getIdCondicionRespuesta05() {
        return idCondicionRespuesta05;
    }

    public void setIdCondicionRespuesta05(Integer idCondicionRespuesta05) {
        this.idCondicionRespuesta05 = idCondicionRespuesta05;
    }

    public String getTextoRespuestaLibre05() {
        return textoRespuestaLibre05;
    }

    public void setTextoRespuestaLibre05(String textoRespuestaLibre05) {
        this.textoRespuestaLibre05 = textoRespuestaLibre05;
    }

    public Integer getIdCondicionRespuesta06() {
        return idCondicionRespuesta06;
    }

    public void setIdCondicionRespuesta06(Integer idCondicionRespuesta06) {
        this.idCondicionRespuesta06 = idCondicionRespuesta06;
    }

    public String getTextoRespuestaLibre06() {
        return textoRespuestaLibre06;
    }

    public void setTextoRespuestaLibre06(String textoRespuestaLibre06) {
        this.textoRespuestaLibre06 = textoRespuestaLibre06;
    }

    public Integer getIdCondicionRespuesta07() {
        return idCondicionRespuesta07;
    }

    public void setIdCondicionRespuesta07(Integer idCondicionRespuesta07) {
        this.idCondicionRespuesta07 = idCondicionRespuesta07;
    }

    public String getTextoRespuestaLibre07() {
        return textoRespuestaLibre07;
    }

    public void setTextoRespuestaLibre07(String textoRespuestaLibre07) {
        this.textoRespuestaLibre07 = textoRespuestaLibre07;
    }

    public Integer getIdCondicionRespuesta08() {
        return idCondicionRespuesta08;
    }

    public void setIdCondicionRespuesta08(Integer idCondicionRespuesta08) {
        this.idCondicionRespuesta08 = idCondicionRespuesta08;
    }

    public String getTextoRespuestaLibre08() {
        return textoRespuestaLibre08;
    }

    public void setTextoRespuestaLibre08(String textoRespuestaLibre08) {
        this.textoRespuestaLibre08 = textoRespuestaLibre08;
    }

    public Integer getIdCondicionRespuesta09() {
        return idCondicionRespuesta09;
    }

    public void setIdCondicionRespuesta09(Integer idCondicionRespuesta09) {
        this.idCondicionRespuesta09 = idCondicionRespuesta09;
    }

    public String getTextoRespuestaLibre09() {
        return textoRespuestaLibre09;
    }

    public void setTextoRespuestaLibre09(String textoRespuestaLibre09) {
        this.textoRespuestaLibre09 = textoRespuestaLibre09;
    }

    public Integer getIdCondicionRespuesta10() {
        return idCondicionRespuesta10;
    }

    public void setIdCondicionRespuesta10(Integer idCondicionRespuesta10) {
        this.idCondicionRespuesta10 = idCondicionRespuesta10;
    }

    public String getTextoRespuestaLibre10() {
        return textoRespuestaLibre10;
    }

    public void setTextoRespuestaLibre10(String textoRespuestaLibre10) {
        this.textoRespuestaLibre10 = textoRespuestaLibre10;
    }

    public String getBloqueadoProveedor() {
        return bloqueadoProveedor;
    }

    public void setBloqueadoProveedor(String bloqueadoProveedor) {
        this.bloqueadoProveedor = bloqueadoProveedor;
    }

    public boolean isEstaNoBloqueadoProveedor() {
        return estaNoBloqueadoProveedor;
    }

    public void setEstaNoBloqueadoProveedor(boolean estaNoBloqueadoProveedor) {
        this.estaNoBloqueadoProveedor = estaNoBloqueadoProveedor;
    }
}
