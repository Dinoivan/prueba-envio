package com.incloud.hcp.pdf.test;

import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPdfDto;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPosicionClaseCondicionPdfDto;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPosicionDataAdicionalPdfDto;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPosicionPdfDto;
import com.incloud.hcp.pdf.bean.PrefacturaItemPdfDto;
import com.incloud.hcp.pdf.bean.PrefacturaPdfDto;
import com.incloud.hcp.pdf.util.ResourceUtil;
import com.incloud.hcp.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Mock {

    private String photoBase64Content = null;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static Mock getInstance(){ return new Mock(); }

    private Mock(){
        try{
            Properties properties;
            try(InputStream resourceStream = ResourceUtil.getResourceStream(Mock.class, "logo.properties")){
                properties =new Properties();
                properties.load(resourceStream);
            }
            photoBase64Content = properties.getProperty("logo.photo");
        } catch (IOException e){
            logger.error(e.getMessage(), e.getCause());
        }
    }

    public OrdenCompraPdfDto getMockOrdenCompraPdfDto() {
        OrdenCompraPdfDto mockOrdenCompraPdfDto = new OrdenCompraPdfDto();
        List<OrdenCompraPosicionPdfDto> mockPosicionPdfDtoList = new ArrayList<>();

        mockOrdenCompraPdfDto.setOrdenCompraNumero("4500239665");
        mockOrdenCompraPdfDto.setOrdenCompraTipo("Material");
        mockOrdenCompraPdfDto.setOrdenCompraVersion("1");
        mockOrdenCompraPdfDto.setOrdenCompraFechaCreacion(DateUtils.stringToUtilDate("2020-02-16"));
        mockOrdenCompraPdfDto.setOrdenCompraPersonaContacto("TANIA TICONA / Of_Princ_Lima");
        mockOrdenCompraPdfDto.setOrdenCompraFormaPago("PE Factura a 30 Días");
        mockOrdenCompraPdfDto.setOrdenCompraMoneda("USD");
        mockOrdenCompraPdfDto.setOrdenCompraAutorizador("TTICONA / 16.02.2020");
        mockOrdenCompraPdfDto.setClienteRuc("20224748711");
        mockOrdenCompraPdfDto.setClienteTelefono("2134000");
        mockOrdenCompraPdfDto.setClienteDireccion("Av. Manuel Olguín N° 327, Piso 15 - Santiago de Surco, Lima - - Lima y Callao");
        mockOrdenCompraPdfDto.setClienteRazonSocial("CORPORACION PESQUERA INCA S.A.");
        mockOrdenCompraPdfDto.setProveedorRazonSocial("PORT LOGISTICS S.A.C.");
        mockOrdenCompraPdfDto.setProveedorRuc("20511271453");
        mockOrdenCompraPdfDto.setProveedorDireccion("AV. MANUEL OLGUIN NRO. 211 INT. 401- URB. LOS GRANADOS LIMA - SANTIAGO DE SURCO - - Lima");
        mockOrdenCompraPdfDto.setProveedorContactoNombre("Luis Fernando Almonte");
        mockOrdenCompraPdfDto.setProveedorContactoTelefono("2154800");
        mockOrdenCompraPdfDto.setProveedorNumero("102582");
        mockOrdenCompraPdfDto.setLugarEntrega("Carretera Sechura - Bayovar Km 57+800 S/N Sechura Piura");
        mockOrdenCompraPdfDto.setAlmacenMaterialEmail("");
        mockOrdenCompraPdfDto.setAlmacenMaterialTelefono1("");
        mockOrdenCompraPdfDto.setAlmacenMaterialTelefono2("");
        mockOrdenCompraPdfDto.setMontoSubtotal(new BigDecimal("600.00"));
        mockOrdenCompraPdfDto.setMontoDescuento(new BigDecimal("0.00"));
        mockOrdenCompraPdfDto.setMontoIgv(new BigDecimal("108.00"));
        mockOrdenCompraPdfDto.setMontoImporteTotal(new BigDecimal("708.00"));
        mockOrdenCompraPdfDto.setTextoCabecera("FORMATO XXXXXX1<br>FORMATO XXXXXX2");
        mockOrdenCompraPdfDto.setTextoCabeceraObservaciones("FORMATO XXXXXX3 <br> FORMATO XXXXXX4 <br> FORMATO XXXXXX5");
        mockOrdenCompraPdfDto.setTextoFecha("");
        mockOrdenCompraPdfDto.setTextoNotasImportantes("");
        mockOrdenCompraPdfDto.setTextoNotasProveedor("PRUEBA DE DATOS DE NOTA DE PROVEEDOR 1 <br> PRUEBA DE DATOS DE NOTA DE PROVEEDOR 2");
        mockOrdenCompraPdfDto.setTextoNotasClausula("LINEA DE COMUNICACIÓN ANONIMA <br> Por esta via usted puede comunicar en forma anónima conductas antiéticas <br> que involucran a colaboradores de la compañía o terceros relacionados <br> (Proveedores, clientes, etc).Para más información ingrese a: <br> www.copeinca.com.pe y de click en la secci+on de LINEA DE COMUNICACIÓN <br> ANÓNIMA.");

        OrdenCompraPosicionPdfDto mockPosicion1PdfDto = new OrdenCompraPosicionPdfDto();

//        mockPosicion1PdfDto.setNumeroOrdenCompra("4500239665");
        mockPosicion1PdfDto.setPosicion("10");
        mockPosicion1PdfDto.setCentro("Bayovar");
        mockPosicion1PdfDto.setMaterial("12090010");
        mockPosicion1PdfDto.setCodigoProv("");
        mockPosicion1PdfDto.setDescripcion("BANDEJA T/ESCALERA FIBRA 100 X 100 MM X 3MT BANDEJA T/ESCALERA FIBRA 100 X 100 MM X 3MT BANDEJA T/ESCALERA FIBRA 100 X 100 MM X 3MT BANDEJA T/ESCALERA FIBRA 100 X 100 MM X 3MT");
        mockPosicion1PdfDto.setCantidad(new BigDecimal("3.0000"));
        mockPosicion1PdfDto.setUnidad("UN");
        mockPosicion1PdfDto.setFechaEntrega(DateUtils.stringToUtilDate("2020-02-23"));
        mockPosicion1PdfDto.setPrecioUnitario(new BigDecimal("60.0000"));
        mockPosicion1PdfDto.setImporte(new BigDecimal("180.00"));

        OrdenCompraPosicionDataAdicionalPdfDto mockDataAdicional1PdfDto = new OrdenCompraPosicionDataAdicionalPdfDto();
        List<OrdenCompraPosicionClaseCondicionPdfDto> claseCondicion1PdfDtoList = new ArrayList<>();

        mockDataAdicional1PdfDto.setTextoRegistroInfo("XXDDDDEFEFEFEFRF XXDDDDEFEFEFEFRF XDDDDEFEFEFEFRF XXDDDDEFEFEFEFRF XXDDDDEFEFEFEFRF XXDDDDEFEFEFEFRF XDDDDEFEFEFEFRF XXDDDDEFEFEFEFRF XXDDDDEFEFEFEFRF XXDDDDEFEFEFEFRF XDDDDEFEFEFEFRF XXDDDDEFEFEFEFRF");
        mockDataAdicional1PdfDto.setTextoPosicion("TEXTO POR POR POSICION 1 TEXTO POR POR POSICION 2 TEXTO POR POR POSICION 3 TEXTO POR POR POSICION 1 TEXTO POR POR POSICION 2 TEXTO POR POR POSICION 3 TEXTO POR POR POSICION 1 TEXTO POR POR POSICION 2 TEXTO POR POR POSICION 3");

        OrdenCompraPosicionClaseCondicionPdfDto claseCondicion1PdfDto = new OrdenCompraPosicionClaseCondicionPdfDto();
        claseCondicion1PdfDto.setClaseCondicionDescripcion("Waru waru pechu pechu");
        claseCondicion1PdfDto.setClaseCondicionPrecioUnitario(new BigDecimal("-1.5000"));
        claseCondicion1PdfDto.setClaseCondicionImporte(new BigDecimal("-4.50"));
        claseCondicion1PdfDtoList.add(claseCondicion1PdfDto);

        OrdenCompraPosicionClaseCondicionPdfDto claseCondicion2PdfDto = new OrdenCompraPosicionClaseCondicionPdfDto();
        claseCondicion2PdfDto.setClaseCondicionDescripcion("Pechu pechu waru waru ");
        claseCondicion2PdfDto.setClaseCondicionPrecioUnitario(new BigDecimal("9.2000"));
        claseCondicion2PdfDto.setClaseCondicionImporte(new BigDecimal("18.60"));
        claseCondicion1PdfDtoList.add(claseCondicion2PdfDto);

        OrdenCompraPosicionClaseCondicionPdfDto claseCondicion3PdfDto = new OrdenCompraPosicionClaseCondicionPdfDto();
        claseCondicion3PdfDto.setClaseCondicionDescripcion("Waka waka chuta chuta");
        claseCondicion3PdfDto.setClaseCondicionPrecioUnitario(new BigDecimal("-3.9000"));
        claseCondicion3PdfDto.setClaseCondicionImporte(new BigDecimal("-11.70"));
        claseCondicion1PdfDtoList.add(claseCondicion3PdfDto);

        mockDataAdicional1PdfDto.setOrdenCompraPosicionClaseCondicionPdfDtoList(claseCondicion1PdfDtoList);
        mockPosicion1PdfDto.setOrdenCompraPosicionDataAdicionalPdfDto(mockDataAdicional1PdfDto);
        mockPosicionPdfDtoList.add(mockPosicion1PdfDto);


        OrdenCompraPosicionPdfDto mockPosicion2PdfDto = new OrdenCompraPosicionPdfDto();

//        mockPosicion2PdfDto.setNumeroOrdenCompra("4500239665");
        mockPosicion2PdfDto.setPosicion("20");
        mockPosicion2PdfDto.setCentro("Bayovar");
        mockPosicion2PdfDto.setMaterial("12090011");
        mockPosicion2PdfDto.setCodigoProv("");
        mockPosicion2PdfDto.setDescripcion("BANDEJA T/ESCALERA FIBRA 200X150MMX3MT");
        mockPosicion2PdfDto.setCantidad(new BigDecimal("3.0000"));
        mockPosicion2PdfDto.setUnidad("UN");
        mockPosicion2PdfDto.setFechaEntrega(DateUtils.stringToUtilDate("2020-02-23"));
        mockPosicion2PdfDto.setPrecioUnitario(new BigDecimal("60.0000"));
        mockPosicion2PdfDto.setImporte(new BigDecimal("180.00"));

        OrdenCompraPosicionDataAdicionalPdfDto mockDataAdicional2PdfDto = new OrdenCompraPosicionDataAdicionalPdfDto();
        List<OrdenCompraPosicionClaseCondicionPdfDto> claseCondicion2PdfDtoList = new ArrayList<>();

        mockDataAdicional2PdfDto.setTextoRegistroInfo("TEXTO REGISTRO INFO 1 TEXTO REGISTRO INFO 2 TEXTO REGISTRO INFO 3");
        mockDataAdicional2PdfDto.setTextoPosicion("XXDDDDEFEFEFEFRF XXDDDDEFEFEFEFRF XDDDDEFEFEFEFRF XXDDDDEFEFEFEFRF");

        OrdenCompraPosicionClaseCondicionPdfDto claseCondicion4PdfDto = new OrdenCompraPosicionClaseCondicionPdfDto();
        claseCondicion4PdfDto.setClaseCondicionDescripcion("Waru waru pechu pechu");
        claseCondicion4PdfDto.setClaseCondicionPrecioUnitario(new BigDecimal("-1.5000"));
        claseCondicion4PdfDto.setClaseCondicionImporte(new BigDecimal("-4.50"));
        claseCondicion2PdfDtoList.add(claseCondicion4PdfDto);

        OrdenCompraPosicionClaseCondicionPdfDto claseCondicion5PdfDto = new OrdenCompraPosicionClaseCondicionPdfDto();
        claseCondicion5PdfDto.setClaseCondicionDescripcion("Pechu pechu waru waru ");
        claseCondicion5PdfDto.setClaseCondicionPrecioUnitario(new BigDecimal("9.2000"));
        claseCondicion5PdfDto.setClaseCondicionImporte(new BigDecimal("18.60"));
        claseCondicion2PdfDtoList.add(claseCondicion5PdfDto);

        OrdenCompraPosicionClaseCondicionPdfDto claseCondicion6PdfDto = new OrdenCompraPosicionClaseCondicionPdfDto();
        claseCondicion6PdfDto.setClaseCondicionDescripcion("Waka waka chuta chuta");
        claseCondicion6PdfDto.setClaseCondicionPrecioUnitario(new BigDecimal("-3.9000"));
        claseCondicion6PdfDto.setClaseCondicionImporte(new BigDecimal("-11.70"));
        claseCondicion2PdfDtoList.add(claseCondicion6PdfDto);

        OrdenCompraPosicionClaseCondicionPdfDto claseCondicion7PdfDto = new OrdenCompraPosicionClaseCondicionPdfDto();
        claseCondicion7PdfDto.setClaseCondicionDescripcion("Chuta Chuta Waka waka ");
        claseCondicion7PdfDto.setClaseCondicionPrecioUnitario(new BigDecimal("0.8000"));
        claseCondicion7PdfDto.setClaseCondicionImporte(new BigDecimal("2.40"));
        claseCondicion2PdfDtoList.add(claseCondicion7PdfDto);

        mockDataAdicional2PdfDto.setOrdenCompraPosicionClaseCondicionPdfDtoList(claseCondicion2PdfDtoList);
        mockPosicion2PdfDto.setOrdenCompraPosicionDataAdicionalPdfDto(mockDataAdicional2PdfDto);
        mockPosicionPdfDtoList.add(mockPosicion2PdfDto);


        OrdenCompraPosicionPdfDto mockPosicion3PdfDto = new OrdenCompraPosicionPdfDto();

//        mockPosicion3PdfDto.setNumeroOrdenCompra("4500239665");
        mockPosicion3PdfDto.setPosicion("30");
        mockPosicion3PdfDto.setCentro("Bayovar");
        mockPosicion3PdfDto.setMaterial("12090012");
        mockPosicion3PdfDto.setCodigoProv("");
        mockPosicion3PdfDto.setDescripcion("BANDEJA T/ESCALERA FIBRA 300X150MMX3MT");
        mockPosicion3PdfDto.setCantidad(new BigDecimal("3.0000"));
        mockPosicion3PdfDto.setUnidad("UN");
        mockPosicion3PdfDto.setFechaEntrega(DateUtils.stringToUtilDate("2020-02-23"));
        mockPosicion3PdfDto.setPrecioUnitario(new BigDecimal("60.0000"));
        mockPosicion3PdfDto.setImporte(new BigDecimal("180.00"));

        OrdenCompraPosicionDataAdicionalPdfDto mockDataAdicional3PdfDto = new OrdenCompraPosicionDataAdicionalPdfDto();

        mockDataAdicional3PdfDto.setTextoRegistroInfo("TEXTO REGISTRO INFO 4 TEXTO REGISTRO INFO 5 TEXTO REGISTRO INFO 6");
        mockDataAdicional3PdfDto.setTextoPosicion("dededededede wertwertwertwert wertwertwertwertwertwertwert");

        mockPosicion3PdfDto.setOrdenCompraPosicionDataAdicionalPdfDto(mockDataAdicional3PdfDto);
        mockPosicionPdfDtoList.add(mockPosicion3PdfDto);


        OrdenCompraPosicionPdfDto mockPosicion4PdfDto = new OrdenCompraPosicionPdfDto();

//        mockPosicion4PdfDto.setNumeroOrdenCompra("4500239665");
        mockPosicion4PdfDto.setPosicion("40");
        mockPosicion4PdfDto.setCentro("Bayovar");
        mockPosicion4PdfDto.setMaterial("12090013");
        mockPosicion4PdfDto.setCodigoProv("");
        mockPosicion4PdfDto.setDescripcion("BANDEJA T/ESCALERA FIBRA 400X150MMX3MT");
        mockPosicion4PdfDto.setCantidad(new BigDecimal("3.0000"));
        mockPosicion4PdfDto.setUnidad("UN");
        mockPosicion4PdfDto.setFechaEntrega(DateUtils.stringToUtilDate("2020-02-23"));
        mockPosicion4PdfDto.setPrecioUnitario(new BigDecimal("20.0000"));
        mockPosicion4PdfDto.setImporte(new BigDecimal("60.00"));

        OrdenCompraPosicionDataAdicionalPdfDto mockDataAdicional4PdfDto = new OrdenCompraPosicionDataAdicionalPdfDto();
        List<OrdenCompraPosicionClaseCondicionPdfDto> claseCondicion3PdfDtoList = new ArrayList<>();

        mockDataAdicional4PdfDto.setTextoRegistroInfo("TEXTO REGISTRO INFO 7 TEXTO REGISTRO INFO 8 TEXTO REGISTRO INFO 9");
        mockDataAdicional4PdfDto.setTextoPosicion("dfghdfgkkgfkkgghjk wer twefghj rtfgghfghjhkwertwert sdfgsdfgsdfg rtwertjjfghjfg wertwe rtwersdfgsdfgtwert");

        OrdenCompraPosicionClaseCondicionPdfDto claseCondicion8PdfDto = new OrdenCompraPosicionClaseCondicionPdfDto();
        claseCondicion8PdfDto.setClaseCondicionDescripcion("Waru waru pechu pechu");
        claseCondicion8PdfDto.setClaseCondicionPrecioUnitario(new BigDecimal("-1.5000"));
        claseCondicion8PdfDto.setClaseCondicionImporte(new BigDecimal("-4.50"));
        claseCondicion3PdfDtoList.add(claseCondicion8PdfDto);

        mockDataAdicional4PdfDto.setOrdenCompraPosicionClaseCondicionPdfDtoList(claseCondicion3PdfDtoList);
        mockPosicion4PdfDto.setOrdenCompraPosicionDataAdicionalPdfDto(mockDataAdicional4PdfDto);
        mockPosicionPdfDtoList.add(mockPosicion4PdfDto);

        mockOrdenCompraPdfDto.setOrdenCompraPosicionPdfDtoList(mockPosicionPdfDtoList);
        return mockOrdenCompraPdfDto;
    }


    public OrdenCompraPdfDto getMockContratoMarcoPdfDto() {
        OrdenCompraPdfDto mockOrdenCompraPdfDto = new OrdenCompraPdfDto();
        List<OrdenCompraPosicionPdfDto> mockPosicionPdfDtoList = new ArrayList<>();

        mockOrdenCompraPdfDto.setOrdenCompraNumero("4500239665");
        mockOrdenCompraPdfDto.setOrdenCompraTipo("Material");
        mockOrdenCompraPdfDto.setOrdenCompraVersion("1");
        mockOrdenCompraPdfDto.setOrdenCompraFechaCreacion(DateUtils.stringToUtilDate("2020-02-16"));
//        mockOrdenCompraPdfDto.setOrdenCompraPersonaContacto("TANIA TICONA / Of_Princ_Lima");
        mockOrdenCompraPdfDto.setOrdenCompraFormaPago("PE Factura a 30 Días");
        mockOrdenCompraPdfDto.setOrdenCompraMoneda("USD");
        mockOrdenCompraPdfDto.setOrdenCompraAutorizador("TTICONA / 16.02.2020");
        mockOrdenCompraPdfDto.setClienteRuc("20224748711");
        mockOrdenCompraPdfDto.setClienteTelefono("2134000");
        mockOrdenCompraPdfDto.setClienteDireccion("Av. Manuel Olguín N° 327, Piso 15 - Santiago de Surco, Lima - - Lima y Callao");
        mockOrdenCompraPdfDto.setClienteRazonSocial("CORPORACION PESQUERA INCA S.A.");
        mockOrdenCompraPdfDto.setProveedorRazonSocial("PORT LOGISTICS S.A.C.");
        mockOrdenCompraPdfDto.setProveedorRuc("20511271453");
//        mockOrdenCompraPdfDto.setProveedorDireccion("AV. MANUEL OLGUIN NRO. 211 INT. 401- URB. LOS GRANADOS LIMA - SANTIAGO DE SURCO - - Lima");
//        mockOrdenCompraPdfDto.setProveedorContactoNombre("Luis Fernando Almonte");
//        mockOrdenCompraPdfDto.setProveedorContactoTelefono("2154800");
        mockOrdenCompraPdfDto.setProveedorNumero("102582");
//        mockOrdenCompraPdfDto.setLugarEntrega("Carretera Sechura - Bayovar Km 57+800 S/N Sechura Piura");
//        mockOrdenCompraPdfDto.setAlmacenMaterialEmail("");
//        mockOrdenCompraPdfDto.setAlmacenMaterialTelefono1("");
//        mockOrdenCompraPdfDto.setAlmacenMaterialTelefono2("");
//        mockOrdenCompraPdfDto.setMontoSubtotal(new BigDecimal("600.00"));
//        mockOrdenCompraPdfDto.setMontoDescuento(new BigDecimal("0.00"));
//        mockOrdenCompraPdfDto.setMontoIgv(new BigDecimal("108.00"));
        mockOrdenCompraPdfDto.setMontoImporteTotal(new BigDecimal("708.00"));


        OrdenCompraPosicionPdfDto mockPosicion1PdfDto = new OrdenCompraPosicionPdfDto();

//        mockPosicion1PdfDto.setNumeroOrdenCompra("4500239665");
        mockPosicion1PdfDto.setPosicion("10");
        mockPosicion1PdfDto.setCentro("Bayovar");
        mockPosicion1PdfDto.setMaterial("12090010");
        mockPosicion1PdfDto.setCodigoProv("");
        mockPosicion1PdfDto.setDescripcion("BANDEJA T/ESCALERA FIBRA 100 X 100 MM X 3MT BANDEJA T/ESCALERA FIBRA 100 X 100 MM X 3MT BANDEJA T/ESCALERA FIBRA 100 X 100 MM X 3MT BANDEJA T/ESCALERA FIBRA 100 X 100 MM X 3MT");
        mockPosicion1PdfDto.setCantidad(new BigDecimal("3.0000"));
        mockPosicion1PdfDto.setUnidad("UN");
        mockPosicion1PdfDto.setFechaEntrega(DateUtils.stringToUtilDate("2020-02-23"));
        mockPosicion1PdfDto.setPrecioUnitario(new BigDecimal("60.0000"));
        mockPosicion1PdfDto.setImporte(new BigDecimal("180.00"));

        mockPosicionPdfDtoList.add(mockPosicion1PdfDto);


        OrdenCompraPosicionPdfDto mockPosicion2PdfDto = new OrdenCompraPosicionPdfDto();

//        mockPosicion2PdfDto.setNumeroOrdenCompra("4500239665");
        mockPosicion2PdfDto.setPosicion("20");
        mockPosicion2PdfDto.setCentro("Bayovar");
        mockPosicion2PdfDto.setMaterial("12090011");
        mockPosicion2PdfDto.setCodigoProv("");
        mockPosicion2PdfDto.setDescripcion("BANDEJA T/ESCALERA FIBRA 200X150MMX3MT");
        mockPosicion2PdfDto.setCantidad(new BigDecimal("3.0000"));
        mockPosicion2PdfDto.setUnidad("UN");
        mockPosicion2PdfDto.setFechaEntrega(DateUtils.stringToUtilDate("2020-02-23"));
        mockPosicion2PdfDto.setPrecioUnitario(new BigDecimal("60.0000"));
        mockPosicion2PdfDto.setImporte(new BigDecimal("180.00"));

        mockPosicionPdfDtoList.add(mockPosicion2PdfDto);


        OrdenCompraPosicionPdfDto mockPosicion3PdfDto = new OrdenCompraPosicionPdfDto();

//        mockPosicion3PdfDto.setNumeroOrdenCompra("4500239665");
        mockPosicion3PdfDto.setPosicion("30");
        mockPosicion3PdfDto.setCentro("Bayovar");
        mockPosicion3PdfDto.setMaterial("12090012");
        mockPosicion3PdfDto.setCodigoProv("");
        mockPosicion3PdfDto.setDescripcion("BANDEJA T/ESCALERA FIBRA 300X150MMX3MT");
        mockPosicion3PdfDto.setCantidad(new BigDecimal("3.0000"));
        mockPosicion3PdfDto.setUnidad("UN");
        mockPosicion3PdfDto.setFechaEntrega(DateUtils.stringToUtilDate("2020-02-23"));
        mockPosicion3PdfDto.setPrecioUnitario(new BigDecimal("60.0000"));
        mockPosicion3PdfDto.setImporte(new BigDecimal("180.00"));

        mockPosicionPdfDtoList.add(mockPosicion3PdfDto);


        OrdenCompraPosicionPdfDto mockPosicion4PdfDto = new OrdenCompraPosicionPdfDto();

//        mockPosicion4PdfDto.setNumeroOrdenCompra("4500239665");
        mockPosicion4PdfDto.setPosicion("40");
        mockPosicion4PdfDto.setCentro("Bayovar");
        mockPosicion4PdfDto.setMaterial("12090013");
        mockPosicion4PdfDto.setCodigoProv("");
        mockPosicion4PdfDto.setDescripcion("BANDEJA T/ESCALERA FIBRA 400X150MMX3MT");
        mockPosicion4PdfDto.setCantidad(new BigDecimal("3.0000"));
        mockPosicion4PdfDto.setUnidad("UN");
        mockPosicion4PdfDto.setFechaEntrega(DateUtils.stringToUtilDate("2020-02-23"));
        mockPosicion4PdfDto.setPrecioUnitario(new BigDecimal("20.0000"));
        mockPosicion4PdfDto.setImporte(new BigDecimal("60.00"));

        mockPosicionPdfDtoList.add(mockPosicion4PdfDto);



        mockOrdenCompraPdfDto.setOrdenCompraPosicionPdfDtoList(mockPosicionPdfDtoList);
        return mockOrdenCompraPdfDto;
    }


    public PrefacturaPdfDto getMockPrefacturaPdfDto() {
        PrefacturaPdfDto mockPrefacturaPdfDto = new PrefacturaPdfDto();
        List<PrefacturaItemPdfDto> mockItemPdfDtoList = new ArrayList<>();

        mockPrefacturaPdfDto.setClienteRuc("20224748711");
        mockPrefacturaPdfDto.setClienteRazonSocial("CORPORACION PESQUERA INCA S.A.");
        mockPrefacturaPdfDto.setProveedorRuc("20511271453");
        mockPrefacturaPdfDto.setProveedorRazonSocial("PORT LOGISTICS S.A.C.");
        mockPrefacturaPdfDto.setPrefacturaReferencia("F001-00512898");
        mockPrefacturaPdfDto.setPrefacturaEstado("Enviada");
        mockPrefacturaPdfDto.setPrefacturaOrdenes("4500126532-4500659887-4500968574-4500966352-4500325498-4500951276-4500967425-4500357951-4500785621-4500846253-4500862148");
        mockPrefacturaPdfDto.setPrefacturaGuias("123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789");
        mockPrefacturaPdfDto.setPrefacturaFechaEmision("16/02/2020");
        mockPrefacturaPdfDto.setPrefacturaFechaRegistro("27/02/2020");
        mockPrefacturaPdfDto.setPrefacturaMoneda("USD");
        mockPrefacturaPdfDto.setPrefacturaObservaciones("TEXTO REGISTRO INFO TEXTO REGISTRO INFO+");
        mockPrefacturaPdfDto.setMontoSubtotal(new BigDecimal("1600.00"));
        mockPrefacturaPdfDto.setMontoIgv(new BigDecimal("108.00"));
        mockPrefacturaPdfDto.setMontoTotal(new BigDecimal("1708.00"));

        // ITEM 1
        PrefacturaItemPdfDto mockItem1PdfDto = new PrefacturaItemPdfDto();
        mockItem1PdfDto.setItem("1");
        mockItem1PdfDto.setNumeroDocumento("5000568956");
        mockItem1PdfDto.setNumeroOrden("4500239665");
        mockItem1PdfDto.setPosicion("10");
        mockItem1PdfDto.setCodigo("12090010");
        mockItem1PdfDto.setDescripcion("BANDEJA T/ESCALERA FIBRA 100 X 100 MM X 3MT BANDEJA T/ESCALERA FIBRA 100 X 100 MM X 3MT BANDEJA T/ESCALERA FIBRA 100 X 100 MM X 3MT");
//        mockItem1PdfDto.setCodigoServicio("");
//        mockItem1PdfDto.setDescripcionServicio("");
//        mockItem1PdfDto.setIndicadorDetraccion("D1");
        mockItem1PdfDto.setCantidad(new BigDecimal("3.00"));
        mockItem1PdfDto.setPrecioUnitario(new BigDecimal("60.00"));
        mockItem1PdfDto.setImporte(new BigDecimal("180.00"));
        mockItemPdfDtoList.add(mockItem1PdfDto);


        // ITEM 2
        PrefacturaItemPdfDto mockItem2PdfDto = new PrefacturaItemPdfDto();
        mockItem2PdfDto.setItem("2");
        mockItem2PdfDto.setNumeroDocumento("5000985465");
        mockItem2PdfDto.setNumeroOrden("4500239665");
        mockItem2PdfDto.setPosicion("20");
        mockItem2PdfDto.setCodigo("18951654");
        mockItem2PdfDto.setDescripcion("BANDEJA T/ESCALERA FIBRA 200X150MMX3MT");
//        mockItem2PdfDto.setCodigoServicio("");
//        mockItem2PdfDto.setDescripcionServicio("");
//        mockItem2PdfDto.setIndicadorDetraccion("D0");
        mockItem2PdfDto.setCantidad(new BigDecimal("3.00"));
        mockItem2PdfDto.setPrecioUnitario(new BigDecimal("60.00"));
        mockItem2PdfDto.setImporte(new BigDecimal("180.00"));
        mockItemPdfDtoList.add(mockItem2PdfDto);

        // ITEM 3
        PrefacturaItemPdfDto mockItem3PdfDto = new PrefacturaItemPdfDto();
        mockItem3PdfDto.setItem("3");
        mockItem3PdfDto.setNumeroDocumento("1000856248");
        mockItem3PdfDto.setNumeroOrden("4500956284");
        mockItem3PdfDto.setPosicion("10");
//        mockItem3PdfDto.setCodigo("");
        mockItem3PdfDto.setDescripcion("SERVICIO DE LAVADO Y PLANCHADO DE ROPA SUCIA - SERVICIO DE LAVADO Y PLANCHADO DE ROPA SUCIA");
        mockItem3PdfDto.setCodigoServicio("95628411");
        mockItem3PdfDto.setDescripcionServicio("LAVADO DE POLOS Y CAMISAS");
        mockItem3PdfDto.setIndicadorDetraccion("D0");
        mockItem3PdfDto.setCantidad(new BigDecimal("10.00"));
        mockItem3PdfDto.setPrecioUnitario(new BigDecimal("50.00"));
        mockItem3PdfDto.setImporte(new BigDecimal("500.00"));
        mockItemPdfDtoList.add(mockItem3PdfDto);

        // ITEM 4
        PrefacturaItemPdfDto mockItem4PdfDto = new PrefacturaItemPdfDto();
        mockItem4PdfDto.setItem("4");
        mockItem4PdfDto.setNumeroDocumento("1000856248");
        mockItem4PdfDto.setNumeroOrden("4500956284");
        mockItem4PdfDto.setPosicion("10");
//        mockItem4PdfDto.setCodigo("");
        mockItem4PdfDto.setDescripcion("SERVICIO DE LAVADO Y PLANCHADO DE ROPA SUCIA - SERVICIO DE LAVADO Y PLANCHADO DE ROPA SUCIA");
        mockItem4PdfDto.setCodigoServicio("86291736");
        mockItem4PdfDto.setDescripcionServicio("LAVADO DE PANTALONES Y SHORTS");
        mockItem4PdfDto.setIndicadorDetraccion("D1");
        mockItem4PdfDto.setCantidad(new BigDecimal("15.00"));
        mockItem4PdfDto.setPrecioUnitario(new BigDecimal("100.00"));
        mockItem4PdfDto.setImporte(new BigDecimal("1500.00"));
        mockItemPdfDtoList.add(mockItem4PdfDto);

        mockPrefacturaPdfDto.setPrefacturaItemPdfDtoList(mockItemPdfDtoList);
        return mockPrefacturaPdfDto;
    }
    
}
