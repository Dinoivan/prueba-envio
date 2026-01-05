package com.incloud.hcp.jco.proveedor.dto;

import com.incloud.hcp.config.util.AppUtil;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.ProveedorCuentaBancaria;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ProveedorRFCParameterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ProveedorRFCParameterBuilder.class);
//    private static final String I_SOCIEDAD = "SFER";
    private static final String I_SOCIEDAD = "CO02";
//    private static final String I_ORG_COMPRAS = "1000";
    private static final String I_ORG_COMPRAS = "PE20"; // PE21 para servicios
//    private static final String RAMO = "Y002";
    private static final String RAMO = "Y002";
    private static final String VALOR_DEFAULT="X";
//    private static final String VIAS_PAGO_N="M"; // vias_pago nacional
//    private static final String VIAS_PAGO_N="T";
    private static final String VIAS_PAGO_N="CGPSTW";
//    private static final String VIAS_PAGO_E="";// vias_pago extranjero
    private static final String VIAS_PAGO_E="CGPSTW";
//    private static final String CCI="CC";
    private static final String CCI="CC";
//    private static final String CASOCIADA_FACTURA="42120001"; // cuenta asociada factura
//    private static final String CASOCIADA_FACTURA="48820033 "; // 1910348820033 (13 DIGITOS), clave banco 002
    private static final String CASOCIADA_FACTURA="42121001";
//    private static final String CASOCIADA_RH="42400001"; // cuenta asociada recibo por honorarios
//    private static final String CASOCIADA_RH="48820033"; // 1910348820033 (13 DIGITOS), clave banco 002
    private static final String CASOCIADA_RH="42121001";

    //Debe de jalar el proveedor cuenta bancaria

    private ProveedorRFCParameterBuilder() {
    }

    public static void build(
            JCoFunction jCoFunction,
            Proveedor beanProveedor,
            List<ProveedorCuentaBancaria> listaCuentaBancaria,
            String usuarioSap) {
        logger.error("Mapeando tabla");

        JCoParameterList input = jCoFunction.getImportParameterList();
        input.setValue("I_SOCIEDAD", I_SOCIEDAD);
//        input.setValue("I_ORG_COMPRAS", I_ORG_COMPRAS);
        input.setValue("I_GRUPO_CTAS", beanProveedor.getTipoProveedor().getCodigoSap());


        /* enviando organizaciones de compras en base a indicadores de bienes y servicios */
        JCoTable jcoTablaOrgCompras = jCoFunction.getImportParameterList().getTable("IT_ORG_COMPRAS");
        logger.error("INGRESANDO LA(S) ORGANIZACION(ES) DE COMPRA(S)");

        Boolean ventaBien = beanProveedor.getIndTipoVentaBien() != null && beanProveedor.getIndTipoVentaBien().trim().equals("1");
        Boolean ventaServicio = beanProveedor.getIndTipoVentaServicio() != null && beanProveedor.getIndTipoVentaServicio().trim().equals("1");
        int contadorVenta = 0;

        if(ventaBien) {
            jcoTablaOrgCompras.appendRow();
            jcoTablaOrgCompras.setRow(contadorVenta);
            jcoTablaOrgCompras.setValue("SIGN", "I");
            jcoTablaOrgCompras.setValue("OPTION", "EQ");
            jcoTablaOrgCompras.setValue("LOW", "PE20");

            contadorVenta++;
        }

        if(ventaServicio) {
            jcoTablaOrgCompras.appendRow();
            jcoTablaOrgCompras.setRow(contadorVenta);
            jcoTablaOrgCompras.setValue("SIGN", "I");
            jcoTablaOrgCompras.setValue("OPTION", "EQ");
            jcoTablaOrgCompras.setValue("LOW", "PE21");
        }


        JCoStructure jcoEstructuraGen = jCoFunction.getImportParameterList().getStructure("I_DATOS_GENERALES");

        jcoEstructuraGen.setValue("CONCEP_BUSQUED",beanProveedor.getRuc());

        String razonSocial = beanProveedor.getRazonSocial().trim().toUpperCase();
        razonSocial = AppUtil.reemplazarCaracteresEspeciales(razonSocial);

        if (razonSocial.length() <= 40) {
            jcoEstructuraGen.setValue("NOMBRE", razonSocial.toUpperCase());
        }
        else {
            if (razonSocial.length() > 40 && razonSocial.length() <= 80) {
                String razonSocial01 = razonSocial.substring(0, 39);
                String razonSocial02 = razonSocial.substring(40, razonSocial.length());
                jcoEstructuraGen.setValue("NOMBRE",razonSocial01.toUpperCase());
                jcoEstructuraGen.setValue("NOMBRE2",razonSocial02.toUpperCase());
            } else {
                String razonSocial01 = razonSocial.substring(0, 39);
                String razonSocial02 = razonSocial.substring(40, 80);
                String razonSocial03 = razonSocial.substring(80, razonSocial.length());
                jcoEstructuraGen.setValue("NOMBRE",razonSocial01.toUpperCase());
                jcoEstructuraGen.setValue("NOMBRE2",razonSocial02.toUpperCase());
                //jcoEstructuraGen.setValue("NOMBRE3",razonSocial03.toUpperCase());
            }
        }

        String datosPersonaNatural=separarCadenas(razonSocial,"/");
        String nombres=datosPersonaNatural.split(",")[0];
        String apellidos=datosPersonaNatural.split(",")[1];

        Boolean indAgenteRetencion = beanProveedor.getIndAgenteRetencion() != null && beanProveedor.getIndAgenteRetencion().trim().equals("1");


        logger.error("RAZON SOCIAL: "+razonSocial);
        String pais = Optional.ofNullable(beanProveedor.getPais())
                .map(tp -> tp.getCodigoUbigeoSapErp())
                .map(c -> c.trim()).get();
        String region = Optional.ofNullable(beanProveedor.getRegion())
                .map(tp -> Optional.ofNullable(tp.getCodigoUbigeoSapErp()).orElse(""))
                .map(c -> c.trim()).get();
        logger.error("REGION"+region.toString());
        String provincia = Optional.ofNullable(beanProveedor.getProvincia())
                .map(tp -> Optional.ofNullable(tp.getDescripcion()).orElse(""))
                .map(c -> c.trim()).get();
        String distrito = Optional.ofNullable(beanProveedor.getDistrito())
                .map(tp -> Optional.ofNullable(tp.getDescripcion()).orElse(""))
                .map(c -> c.trim()).get();
        jcoEstructuraGen.setValue("PAIS", pais.toUpperCase());
        jcoEstructuraGen.setValue("REGION", region.toUpperCase());
        jcoEstructuraGen.setValue("POBLACION", provincia.toUpperCase());
        jcoEstructuraGen.setValue("DISTRITO", distrito.toUpperCase());
        jcoEstructuraGen.setValue("COD_POSTAL",Optional.ofNullable(beanProveedor.getCodigoPostal()).orElse("").toUpperCase().trim());
        String direccion = "";
        String direccion1="";
        String direccion2="";
        String direccion3="";
        String direccion4="";
        if (Optional.ofNullable(beanProveedor.getDireccionFiscal()).isPresent()) {
            direccion = beanProveedor.getDireccionFiscal().toUpperCase();
            direccion = AppUtil.reemplazarCaracteresEspeciales(direccion);
            int longitud = direccion.length();
            if(longitud<=60){
                direccion1=direccion.substring(0,direccion.length());
            }
            else if (longitud > 60 && longitud <= 120) {
                logger.error("DIRECCION 2");
                direccion1=direccion.substring(0,60);
                direccion2=direccion.substring(60,direccion.length());
            }else if(longitud > 120 && longitud <= 180){
                logger.error("DIRECCION 3");
                direccion1=direccion.substring(0,60);
                direccion2=direccion.substring(60,120);
                direccion3=direccion.substring(120,direccion.length());
            }else{
                logger.error("DIRECCION 4");
                direccion1=direccion.substring(0,60);
                direccion2=direccion.substring(60,120);
                direccion3=direccion.substring(120,180);
                direccion4=direccion.substring(180,direccion.length());
            }
        }
        logger.error("DIRECION: " + direccion);
        logger.error("DIRECION: " + direccion2);
        String grupoEsquemaProveedor="";
        String viasPago="";
        String tipoNif="";
        String claseImpuesto="";
        String ctaAsociada="";


        String tipoProveedor=beanProveedor.getTipoProveedor().getCodigoSap().toUpperCase();
        if(tipoProveedor.equalsIgnoreCase("ZNAC")) {
            viasPago=VIAS_PAGO_N;
//            grupoEsquemaProveedor="Z1";
            grupoEsquemaProveedor="01";
//            tipoNif="P6";
            tipoNif="80";
            claseImpuesto=Optional.ofNullable(beanProveedor.getTipoPersona().toUpperCase())
                    .map(codigo -> {
                        switch (beanProveedor.getTipoPersona()) {
                            case "J":
                                return "PJ";
                            case "N":
                                return "PN";
                            default:
                                return "";
                        }
                    }).orElse("");

            if(claseImpuesto=="PJ"){

                ctaAsociada=CASOCIADA_FACTURA;
                jcoEstructuraGen.setValue("TRATAMIENTO","EMPRESA");
            }else{
                jcoEstructuraGen.setValue("NUM_DNI",beanProveedor.getRuc().substring(2,10));
                jcoEstructuraGen.setValue("TRATAMIENTO","Señor");
                jcoEstructuraGen.setValue("NOMBRE3",apellidos);
                jcoEstructuraGen.setValue("NOMBRE4",nombres);
                if(beanProveedor.getTipoComprobante().getCodigoTipoComprobante().equalsIgnoreCase("FA")){
                    ctaAsociada=CASOCIADA_FACTURA;
                }else{
                    ctaAsociada=CASOCIADA_RH;
                }

            }

            String datosContacto1=separarCadenas(beanProveedor.getNombreRepresentanteLegal().trim().toUpperCase()," ");
            String nombresContacto1=datosContacto1.split(",")[0].toUpperCase();
            String apellidosContacto1=datosContacto1.split(",")[1].toUpperCase();

            String datosContacto2=separarCadenas(beanProveedor.getNombrePersonaCreditoCobranza().trim().toUpperCase()," ");
            String nombresContacto2=datosContacto2.split(",")[0].toUpperCase();
            String apellidosContacto2=datosContacto2.split(",")[1].toUpperCase();

            jcoEstructuraGen.setValue("NOMB_CONTACTO",longitudCadenaContactos(nombresContacto1,35));
            jcoEstructuraGen.setValue("NOMB_PILA", longitudCadenaContactos(apellidosContacto1,35));
            jcoEstructuraGen.setValue("TELEFONO_CONTACTO", "");
            jcoEstructuraGen.setValue("MAIL_CONTACTO",beanProveedor.getEmailRepresentanteLegal().trim());
            jcoEstructuraGen.setValue("DNI_CONTACTO",beanProveedor.getNroDocumRepresentanteLegal().trim());
            jcoEstructuraGen.setValue("DEP_CONTACTO","0010");
            jcoEstructuraGen.setValue("FUNC_CONTACTO","51");

            jcoEstructuraGen.setValue("NOMB_PILA2",longitudCadenaContactos(apellidosContacto2,35));
            jcoEstructuraGen.setValue("NOMB_CONTACTO2",longitudCadenaContactos(nombresContacto2,35));
            jcoEstructuraGen.setValue("TELEFONO_CONTACTO2","");
            jcoEstructuraGen.setValue("MAIL_CONTACTO2",beanProveedor.getEmailPersonaCreditoCobranza().trim());
            jcoEstructuraGen.setValue("DNI_CONTACTO2",beanProveedor.getNroDocumPersonaCreditoCobranza().trim());
            jcoEstructuraGen.setValue("DEP_CONTACTO2", "0009");
            jcoEstructuraGen.setValue("FUNC_CONTACTO2", "44");
        }
        else {
//            grupoEsquemaProveedor="Z4";
            grupoEsquemaProveedor="01";
            viasPago=VIAS_PAGO_E;
//            tipoNif="P0";
            tipoNif="80";
            claseImpuesto="PE";
            if(beanProveedor.getTipoComprobante().getCodigoTipoComprobante().equalsIgnoreCase("FA")){
                ctaAsociada=CASOCIADA_FACTURA;
            }else{
                ctaAsociada=CASOCIADA_RH;
            }
        }


        jcoEstructuraGen.setValue("DIRECCION", direccion1.toUpperCase());
        jcoEstructuraGen.setValue("DIRECCION2", direccion2.toUpperCase());
        jcoEstructuraGen.setValue("DIRECCION3", direccion3.toUpperCase());
        jcoEstructuraGen.setValue("DIRECCION4", direccion4.toUpperCase());
        jcoEstructuraGen.setValue("TELEFONO", beanProveedor.getTelefono().toUpperCase());
        jcoEstructuraGen.setValue("EMAIL", beanProveedor.getEmail().trim().toUpperCase());
        jcoEstructuraGen.setValue("NUM_IDENT_FISC", beanProveedor.getRuc().toUpperCase());
        jcoEstructuraGen.setValue("TIPO_NIF", tipoNif);


        ///Datos de Intelocutor CodigoSap Usuario 1100000045
//        jcoEstructuraGen.setValue("FUNC_INTER","ZJ");
        jcoEstructuraGen.setValue("FUNC_INTER","");
        jcoEstructuraGen.setValue("COD_INTER",usuarioSap.trim());
        //////fin//////
        jcoEstructuraGen.setValue("CLASE_IMPUESTO", claseImpuesto);
        jcoEstructuraGen.setValue("RECARGO_EQUIV", "");
        jcoEstructuraGen.setValue("PERSONA_FISICA", "");
//        jcoEstructuraGen.setValue("RAMO", RAMO);
        jcoEstructuraGen.setValue("RAMO", "");






        JCoStructure jcoEstructuraSociedad = jCoFunction.getImportParameterList().getStructure("I_DATOS_SOCIEDAD");

        jcoEstructuraSociedad.setValue("CTA_ASOCIADA",ctaAsociada);
//        jcoEstructuraSociedad.setValue("GRUPO_TESOR",beanProveedor.getCodigoGrupoTesoreria());
        jcoEstructuraSociedad.setValue("COND_PAGO",beanProveedor.getCondicionPago().getCodigoSap());
        jcoEstructuraSociedad.setValue("GRUPO_TOLER","");
        jcoEstructuraSociedad.setValue("VERIF_FRA_DOB",VALOR_DEFAULT);
        jcoEstructuraSociedad.setValue("VIA_PAGO",viasPago);


        JCoStructure jcoEstructuraOrgCompra = jCoFunction.getImportParameterList().getStructure("I_DATOS_ORG_COMP");
        jcoEstructuraOrgCompra.setValue("MONEDA_PEDIDO",beanProveedor.getMoneda().getCodigoMoneda().trim());
        jcoEstructuraOrgCompra.setValue("COND_PAGO",beanProveedor.getCondicionPago().getCodigoSap());
        jcoEstructuraOrgCompra.setValue("GRP_ESQUEMA_PROV",grupoEsquemaProveedor);
        jcoEstructuraOrgCompra.setValue("VERIF_FACT_EM",VALOR_DEFAULT);
        jcoEstructuraOrgCompra.setValue("PED_AUTOMATICO","");
        jcoEstructuraOrgCompra.setValue("VER_FACT_REL_SER",VALOR_DEFAULT);
        jcoEstructuraOrgCompra.setValue("CONC_BONIF_ESPE","");
        jcoEstructuraOrgCompra.setValue("CONT_CONFIRMA","");
        jcoEstructuraOrgCompra.setValue("GRUPO_COMPRA",beanProveedor.getCodigoGrupoCompra());


        JCoStructure jcoEstructuraEstProveedor = jCoFunction.getImportParameterList().getStructure("I_ESTADO_PROVEEDOR");
        jcoEstructuraEstProveedor.setValue("INICIO_BLOQUEO","");
        jcoEstructuraEstProveedor.setValue("FIN_BLOQUEO","");
        jcoEstructuraEstProveedor.setValue("HOMOLOGACION","");
        jcoEstructuraEstProveedor.setValue("CHECK01","");
        jcoEstructuraEstProveedor.setValue("CHECK02","");
        jcoEstructuraEstProveedor.setValue("CHECK03","");
        jcoEstructuraEstProveedor.setValue("CHECK04","");
        jcoEstructuraEstProveedor.setValue("CHECK05","");
        jcoEstructuraEstProveedor.setValue("CHECK06","");
        jcoEstructuraEstProveedor.setValue("CHECK07","");
        jcoEstructuraEstProveedor.setValue("CHECK08","");
        jcoEstructuraEstProveedor.setValue("CHECK09","");
        jcoEstructuraEstProveedor.setValue("CHECK10","");
        jcoEstructuraEstProveedor.setValue("SENSIBLE","");


        ///Cuenta Bancaria
        logger.error("INGRESANDO A LAS CUENTAS BANCARIAS DEL PROVEEDOR");
        logger.error("INGRESANDO A LAS CUENTAS BANCARIAS DEL PROVEEDOR - listaCuentaBancaria size: " + listaCuentaBancaria.size());
        logger.error("INGRESANDO A LAS CUENTAS BANCARIAS DEL PROVEEDOR - listaCuentaBancaria: " + listaCuentaBancaria.toString());

        JCoTable jcoTableCtaBancaria = jCoFunction.getImportParameterList().getTable("IT_CUENTA_BANCARIA");
        if(tipoProveedor.equalsIgnoreCase("ZNAC")) {
            for (int i = 0; i < listaCuentaBancaria.size(); i++) {
                ProveedorCuentaBancaria proveedorCuentaBancaria = listaCuentaBancaria.get(i);
                logger.error("INGRESANDO A LAS CUENTAS BANCARIAS DEL PROVEEDOR proveedorCuentaBancaria: " + proveedorCuentaBancaria.toString());

                jcoTableCtaBancaria.appendRow();
                jcoTableCtaBancaria.setRow(i);
                jcoTableCtaBancaria.setValue("PAIS", beanProveedor.getPais().getCodigoUbigeoSapErp());
                jcoTableCtaBancaria.setValue("ENTIDAD_BANCARIA", proveedorCuentaBancaria.getBanco().getClaveBanco());
                jcoTableCtaBancaria.setValue("NRO_CUENTA", proveedorCuentaBancaria.getNumeroCuenta().replace("-",""));
                jcoTableCtaBancaria.setValue("CCI", proveedorCuentaBancaria.getClaveControlBanco()); //

                if (proveedorCuentaBancaria.getBanco().getClaveBanco().equals("018") && proveedorCuentaBancaria.getClaveControlBanco().equals("03")) // si es una cuenta de detraccion del Banco de la Nacion
                    jcoTableCtaBancaria.setValue("MONEDA", "PEN1");
                else
                    jcoTableCtaBancaria.setValue("MONEDA", proveedorCuentaBancaria.getMoneda().getCodigoMoneda());

                /*JRAMOS - UPDATE*/
                jcoTableCtaBancaria.setValue("REFERENCIA", "");

                if(proveedorCuentaBancaria.getNumeroCuentaCci() != null)
                    jcoTableCtaBancaria.setValue("NOMBRE_TITULAR", proveedorCuentaBancaria.getNumeroCuentaCci().trim());
                /*if (razonSocial.length() <= 35) {
                    jcoTableCtaBancaria.setValue("NOMBRE_TITULAR", razonSocial.toUpperCase());
                } else {
                    if (razonSocial.length() > 35 && razonSocial.length() <= 60) {
                        String razonSocial01 = razonSocial.substring(0, razonSocial.length());
                        jcoTableCtaBancaria.setValue("NOMBRE_TITULAR", razonSocial01.toUpperCase());

                    } else {
                        String razonSocial01 = razonSocial.substring(0, 59);
                        jcoTableCtaBancaria.setValue("NOMBRE_TITULAR", razonSocial01.toUpperCase());

                    }
                }*/
                jcoTableCtaBancaria.setValue("DIRECC_TITULAR", "");
                jcoTableCtaBancaria.setValue("NOMBRE_BANCO", "");
                jcoTableCtaBancaria.setValue("DIRECC_BANCO", "");
                jcoTableCtaBancaria.setValue("CODIGO_ABA", "");
                jcoTableCtaBancaria.setValue("CODIGO_SWIFT", "");
                jcoTableCtaBancaria.setValue("CODIGO_IBAN", "");
            }

        }
        JCoTable jcoTableRetencion = jCoFunction.getImportParameterList().getTable("IT_RETENCIONES");
        if(tipoProveedor.equalsIgnoreCase("ZNAC")) {

            if(claseImpuesto=="PJ"){
//                String tipoRetencionI="";
//                if(beanProveedor.getCodigoPadron().equalsIgnoreCase("NINGUNO")){
//                    tipoRetencionI="I3";
//                }else{
//                    tipoRetencionI="I0";
//                }
                jcoTableRetencion.appendRow();
                jcoTableRetencion.setRow(0);
//                jcoTableRetencion.setValue("TIPO_RETEN", "DB");
                jcoTableRetencion.setValue("TIPO_RETEN", "RE");
//                jcoTableRetencion.setValue("INDIC_RETEN", "00");
                jcoTableRetencion.setValue("INDIC_RETEN", "R1");

                if(!indAgenteRetencion){
                    jcoTableRetencion.setValue("WT_SUBJCT", VALOR_DEFAULT);
                    logger.error("PADRON" + beanProveedor.getCodigoPadron());
                }else{
                    jcoTableRetencion.setValue("WT_SUBJCT", "");
                }

                jcoTableRetencion.appendRow();
                jcoTableRetencion.setRow(1);
//                jcoTableRetencion.setValue("TIPO_RETEN", "I6");
                jcoTableRetencion.setValue("TIPO_RETEN", "D1");
//                jcoTableRetencion.setValue("INDIC_RETEN", tipoRetencionI);
                jcoTableRetencion.setValue("INDIC_RETEN", "D1");
                jcoTableRetencion.setValue("WT_SUBJCT", VALOR_DEFAULT);
            }else{

                /*Denisse Indico que cuando se el proveedor es nacional debe de nacer con RE y R1*/

                jcoTableRetencion.appendRow();
                jcoTableRetencion.setRow(0);
                jcoTableRetencion.setValue("TIPO_RETEN", "RE");
                jcoTableRetencion.setValue("INDIC_RETEN", "R1");
                if(!indAgenteRetencion){
                    jcoTableRetencion.setValue("WT_SUBJCT", VALOR_DEFAULT);
                    logger.error("PADRON" + beanProveedor.getCodigoPadron());
                }else{
                    jcoTableRetencion.setValue("WT_SUBJCT", "");
                }


                /*if(beanProveedor.getTipoComprobante().getCodigoTipoComprobante().equalsIgnoreCase("FA")){
                    jcoTableRetencion.appendRow();
                    jcoTableRetencion.setRow(0);
//                    jcoTableRetencion.setValue("TIPO_RETEN", "DB");
                    jcoTableRetencion.setValue("TIPO_RETEN", "Q1");
//                    jcoTableRetencion.setValue("INDIC_RETEN", "00");
                    jcoTableRetencion.setValue("INDIC_RETEN", "Q1");
                    jcoTableRetencion.setValue("WT_SUBJCT", VALOR_DEFAULT);
//                    logger.error("PADRON" + beanProveedor.getCodigoPadron());
//                    String tipoRetencionI="";
//                    if(beanProveedor.getCodigoPadron().equalsIgnoreCase("NINGUNO")){
//                        tipoRetencionI="I3";
//                    }else{
//                        tipoRetencionI="I0";
//                    }
//                    jcoTableRetencion.appendRow();
//                    jcoTableRetencion.setRow(1);
//                    jcoTableRetencion.setValue("TIPO_RETEN", "I6");
//                    jcoTableRetencion.setValue("INDIC_RETEN", tipoRetencionI);
//                    jcoTableRetencion.setValue("WT_SUBJCT", VALOR_DEFAULT);
                }else{
                    jcoTableRetencion.appendRow();
                    jcoTableRetencion.setRow(0);
//                    jcoTableRetencion.setValue("TIPO_RETEN", "R4");
                    jcoTableRetencion.setValue("TIPO_RETEN", "Q1");
//                    jcoTableRetencion.setValue("INDIC_RETEN", "R0");
                    jcoTableRetencion.setValue("INDIC_RETEN", "Q1");
                    jcoTableRetencion.setValue("WT_SUBJCT", VALOR_DEFAULT);
                }*/
            }
        }else{
            jcoTableRetencion.appendRow();
            jcoTableRetencion.setRow(0);
            jcoTableRetencion.setValue("TIPO_RETEN", "ND");
            jcoTableRetencion.setValue("INDIC_RETEN", "N0");
            jcoTableRetencion.setValue("WT_SUBJCT", VALOR_DEFAULT);
            jcoTableRetencion.appendRow();
            jcoTableRetencion.setRow(1);
            jcoTableRetencion.setValue("TIPO_RETEN", "NI");
            jcoTableRetencion.setValue("INDIC_RETEN", "N0");
            jcoTableRetencion.setValue("WT_SUBJCT", VALOR_DEFAULT);
        }

        /**JRAMOS - UPDATE*/
        logger.error("<<<<<<<<<<<<<<<<<<<<<<<<<<IT_PERSONA_CONTACTO>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        List<ProveedorContactoRowDTO> listProveedorContacto = new ArrayList<>();

        //----------------------------------------------------------------------------
        ProveedorContactoRowDTO perRepresentanteLegal = new ProveedorContactoRowDTO();
        perRepresentanteLegal.setTratamiento("");
        perRepresentanteLegal.setNombre(beanProveedor.getNombreRepresentanteLegal());
        perRepresentanteLegal.setApellido("");
        perRepresentanteLegal.setFuncion(beanProveedor.getCargoRepresentanteLegal());
        perRepresentanteLegal.setTelefono("");
        perRepresentanteLegal.setTelefonoMov("");
        logger.error("<--MC_LOG-->:ProveedorRFCParameterBuilder-build:");
        logger.error("<--MC_LOG-->:ProveedorRFCParameterBuilder-beanProveedor.getEmailRepresentanteLegal():"+beanProveedor.getEmailRepresentanteLegal());
        perRepresentanteLegal.setEmail(beanProveedor.getEmailRepresentanteLegal());
        listProveedorContacto.add(perRepresentanteLegal);
        //-----------------------------------------------------------------------------
        ProveedorContactoRowDTO perTesoreria = new ProveedorContactoRowDTO();
        perTesoreria.setTratamiento("");
        perTesoreria.setNombre(beanProveedor.getNombrePersonaTesoreria());
        perTesoreria.setApellido("");
        perTesoreria.setFuncion(beanProveedor.getCargoPersonaTesoreria());
        perTesoreria.setTelefono("");
        perTesoreria.setTelefonoMov("");
        logger.error("<--MC_LOG-->:ProveedorRFCParameterBuilder-build:");
        logger.error("<--MC_LOG-->:ProveedorRFCParameterBuilder-beanProveedor.getEmailPersonaTesoreria():"+beanProveedor.getEmailPersonaTesoreria());
        perTesoreria.setEmail(beanProveedor.getEmailPersonaTesoreria());
        listProveedorContacto.add(perTesoreria);
        //------------------------------------------------------------------------------
        ProveedorContactoRowDTO perCobranza = new ProveedorContactoRowDTO();
        perCobranza.setTratamiento("");
        perCobranza.setNombre(beanProveedor.getNombrePersonaCreditoCobranza());
        perCobranza.setApellido("");
        perCobranza.setFuncion(beanProveedor.getCargoPersonaCreditoCobranza());
        perCobranza.setTelefono("");
        perCobranza.setTelefonoMov("");
        logger.error("<--MC_LOG-->:ProveedorRFCParameterBuilder-build:");
        logger.error("<--MC_LOG-->:ProveedorRFCParameterBuilder-beanProveedor.getEmailPersonaCreditoCobranza():"+beanProveedor.getEmailPersonaCreditoCobranza());
        perCobranza.setEmail(beanProveedor.getEmailPersonaCreditoCobranza());
        listProveedorContacto.add(perCobranza);
        //------------------------------------------------------------------------------
        ProveedorContactoRowDTO perCompras = new ProveedorContactoRowDTO();
        perCompras.setTratamiento("");
        perCompras.setNombre(beanProveedor.getNombrePersonaCompra());
        perCompras.setApellido("");
        perCompras.setFuncion(beanProveedor.getCargoPersonaCompra());
        perCompras.setTelefono("");
        perCompras.setTelefonoMov(beanProveedor.getCelularPersonaCompra());
        logger.error("<--MC_LOG-->:ProveedorRFCParameterBuilder-build:");
        logger.error("<--MC_LOG-->:ProveedorRFCParameterBuilder-beanProveedor.getEmailPersonaCompra():"+beanProveedor.getEmailPersonaCompra());
        perCompras.setEmail(beanProveedor.getEmailPersonaCompra());
        listProveedorContacto.add(perCompras);
        //------------------------------------------------------------------------------

        JCoTable zTablePersonaContacto = jCoFunction.getImportParameterList().getTable("IT_PERSONA_CONTACTO");
        AtomicReference<Integer> provContactoCont = new AtomicReference<>(0);

        listProveedorContacto.forEach( o -> {

            zTablePersonaContacto.appendRow();
            zTablePersonaContacto.setRow(provContactoCont.get());

            zTablePersonaContacto.setValue("TRATAMIENTO", o.getTratamiento());
            zTablePersonaContacto.setValue("NOMBRE", o.getNombre());
            //zTablePersonaContacto.setValue("APELLIDO", o.getApellido());
            zTablePersonaContacto.setValue("APELLIDO", o.getNombre()); //Esto se hace porque en el ERP el campo apellido es obligatorio.
            zTablePersonaContacto.setValue("FUNCION", o.getFuncion());
            zTablePersonaContacto.setValue("TELEFONO", o.getTelefono());
            zTablePersonaContacto.setValue("TELEFONO_MOV", o.getTelefonoMov());
            zTablePersonaContacto.setValue("EMAIL", o.getEmail());

            provContactoCont.getAndSet(provContactoCont.get() + 1);

        });


        logger.error("FIN MAPEO PROVEEDOR");

    }



    public static String separarCadenas(String cadena,String aux){
        String [] razonPartida=cadena.split(" ");
        int longRazonSocial=razonPartida.length;
        String nombres="";
        String apellidos="";
        if(longRazonSocial<=2){
            nombres =razonPartida[0];
            apellidos=razonPartida[longRazonSocial-1];
        }else if(longRazonSocial==3){
            nombres =razonPartida[0];
            apellidos=razonPartida[1]+aux+razonPartida[2];
        }else{
            if(longRazonSocial==4){
                nombres =razonPartida[0]+aux+razonPartida[1];
                apellidos=razonPartida[2]+aux+razonPartida[3];
            }else{
                nombres =razonPartida[0]+aux+razonPartida[1]+aux+razonPartida[2];
                apellidos=razonPartida[3]+aux+razonPartida[4];
            }
        }
        return nombres+","+apellidos;
    }

    public static String longitudCadenaContactos(String cadena,int l){

        String cadenaReturn="";
        if(cadena.length()<=l){
            cadenaReturn=cadena;
        }else{
            if(cadena.length()>l){
                cadenaReturn=cadena.substring(0,34);
            }
        }


        return cadenaReturn;
    }


}
