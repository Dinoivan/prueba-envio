package com.incloud.hcp.rest;

import com.incloud.hcp.bean.MensajePrefactura;
import com.incloud.hcp.domain.Sociedad;
import com.incloud.hcp.dto.PrefacturaDto;
import com.incloud.hcp.service.SociedadService;
import com.incloud.hcp.util.FunctionsXML;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.NumberUtils;
import com.incloud.hcp.util.Utils;
import com.incloud.hcp.ws.sunat.bean.GetStatusInput;
import com.incloud.hcp.ws.sunat.bean.GetStatusOutput;
import com.incloud.hcp.ws.sunat.service.IStatusComprobante;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/api/Sunat")
public class ServicioSunatRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SociedadService sociedadService;
    private IStatusComprobante iStatusComprobante;

    @Autowired
    public ServicioSunatRest(SociedadService sociedadService, IStatusComprobante iStatusComprobante) {
        this.sociedadService = sociedadService;
        this.iStatusComprobante = iStatusComprobante;
    }

    @PostMapping(value = "/ValidarComprobante", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<MensajePrefactura> VerificarComprobanteRest(@RequestParam(value = "file") MultipartFile archivoSunat) {
        MensajePrefactura bean = new MensajePrefactura();
        String codess;
        String messages;
        String serieComprobante = "";
        String nroComprobante = "";
        PrefacturaDto prefactura = new PrefacturaDto();
        //@add ppo 18.01.2021
        String rucProveedor = "";
        String rucCliente = "";
        String tipoComprobante = "";
        String montoTotal = "";
        String igv = "";
        String subTotal = "";
        String referenciaFactura = "";
        String fechaEmisionString = "";
        String codigoMoneda = "";
        Date fechaEmision = null;
        NodeList facturaNodeList = null;
        //Fin
        try {
            File sunatXml = FunctionsXML.convert(archivoSunat);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(sunatXml);
            //@add ppo 18.01.2020
            boolean isStandar = true;
            NodeList facturaNodeListAux = doc.getElementsByTagName("Invoice");
            if (facturaNodeListAux.getLength() == 0) {
                facturaNodeListAux = doc.getElementsByTagName("tns:Invoice");
            }

            if (facturaNodeListAux.getLength() == 0) {
                isStandar = false;
            }
            //Fin
            if (isStandar) {//Como siempre estubo funcionando
                logger.error("VerificarComprobanteRest 0 :: " + isStandar);
                XPath xPath = XPathFactory.newInstance().newXPath();
                referenciaFactura = xPath.compile("/Invoice/ID").evaluate(doc);
                String montoImpuestos = xPath.compile("/Invoice/TaxTotal/TaxAmount").evaluate(doc);
                String rucProveedorAlt = xPath.compile("/Invoice/AccountingSupplierParty/Party/PartyIdentification/ID").evaluate(doc);
                String rucClienteAlt = xPath.compile("/Invoice/AccountingCustomerParty/Party/PartyIdentification/ID").evaluate(doc);

                doc.getDocumentElement().normalize();

                NodeList proveedorNodeList = doc.getElementsByTagName("cac:AccountingSupplierParty");
                if (proveedorNodeList.getLength() == 0)
                    proveedorNodeList = doc.getElementsByTagName("n5:AccountingSupplierParty");

                NodeList clienteNodeList = doc.getElementsByTagName("cac:AccountingCustomerParty");
                if (clienteNodeList.getLength() == 0)
                    clienteNodeList = doc.getElementsByTagName("n5:AccountingCustomerParty");

                facturaNodeList = doc.getElementsByTagName("Invoice");
                if (facturaNodeList.getLength() == 0)
                    facturaNodeList = doc.getElementsByTagName("tns:Invoice");

                NodeList taxSubTotalNodeList = doc.getElementsByTagName("cac:TaxTotal");
                if (taxSubTotalNodeList.getLength() == 0)
                    taxSubTotalNodeList = doc.getElementsByTagName("n5:TaxTotal");
                if (taxSubTotalNodeList.getLength() == 0)
                    taxSubTotalNodeList = doc.getElementsByTagName("n1:TaxTotal");

                NodeList taxTotalNodeList = doc.getElementsByTagName("cac:LegalMonetaryTotal");
                if (taxTotalNodeList.getLength() == 0)
                    taxTotalNodeList = doc.getElementsByTagName("n5:LegalMonetaryTotal");
                if (taxTotalNodeList.getLength() == 0)
                    taxTotalNodeList = doc.getElementsByTagName("n1:LegalMonetaryTotal");

                rucProveedor = FunctionsXML.getTagValueHTML(proveedorNodeList, "cbc:ID");
                if (rucProveedor == null)
                    rucProveedor = FunctionsXML.getTagValueHTML(proveedorNodeList, "n4:ID");

                rucCliente = FunctionsXML.getTagValueHTML(clienteNodeList, "cbc:ID");
                if (rucCliente == null)
                    rucCliente = FunctionsXML.getTagValueHTML(clienteNodeList, "n4:ID");

                if (rucProveedor == null || (!NumberUtils.stringIsLong(rucProveedor) && rucProveedor.length() != 11))
                    rucProveedor = rucProveedorAlt;

                if (rucCliente == null || (!NumberUtils.stringIsLong(rucCliente) && rucCliente.length() != 11))
                    rucCliente = rucClienteAlt;

                tipoComprobante = FunctionsXML.getTagValueHTML(facturaNodeList, "cbc:InvoiceTypeCode");
                if (tipoComprobante == null)
                    tipoComprobante = FunctionsXML.getTagValueHTML(facturaNodeList, "n4:InvoiceTypeCode");
                if (tipoComprobante == null)
                    tipoComprobante = FunctionsXML.getTagValueHTML(facturaNodeList, "n2:InvoiceTypeCode");

                montoTotal = FunctionsXML.getTagValueHTML(taxTotalNodeList, "cbc:PayableAmount");
                if (montoTotal == null)
                    montoTotal = FunctionsXML.getTagValueHTML(taxTotalNodeList, "n4:PayableAmount");
                if (montoTotal == null)
                    montoTotal = FunctionsXML.getTagValueHTML(taxTotalNodeList, "n2:PayableAmount");

                igv = "";
                List<String> igvList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "cbc:Name", "IGV", "cbc:TaxAmount");
                if (igvList.isEmpty())
                    igvList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "n4:Name", "IGV", "n4:TaxAmount");
                if (!igvList.isEmpty())
                    igv = igvList.get(0);
                if (igv == null || !NumberUtils.stringIsBigDecimal(igv))
                    igv = montoImpuestos;

                subTotal = "";
                List<String> subTotalList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "cbc:Name", "IGV", "cbc:TaxableAmount");
                if (subTotalList.isEmpty())
                    subTotalList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "n4:Name", "IGV", "n4:TaxableAmount");
                if (!subTotalList.isEmpty())
                    subTotal = subTotalList.get(0);
                if (subTotal == null || !NumberUtils.stringIsBigDecimal(subTotal) || ((new BigDecimal(subTotal).compareTo(BigDecimal.ZERO)) == 0)) {
                    String subTotalAlt = FunctionsXML.getTagValueHTML(taxTotalNodeList, "cbc:LineExtensionAmount");
                    if (subTotalAlt == null)
                        subTotalAlt = FunctionsXML.getTagValueHTML(taxTotalNodeList, "n4:LineExtensionAmount");
                    if (subTotalAlt != null && NumberUtils.stringIsBigDecimal(subTotalAlt) && (new BigDecimal(subTotalAlt).compareTo(BigDecimal.ZERO) > 0))
                        subTotal = subTotalAlt;
                    else
                        subTotal = (new BigDecimal(montoTotal).subtract(new BigDecimal(igv))).toString();
                }
            } else { //Nuevo Formato
                logger.error("VerificarComprobanteRest 1 :: " + isStandar);
                tipoComprobante = "01";
                NodeList generalNodeListTextSpan = doc.getElementsByTagName("text:span");
                for (int i = 0; i < generalNodeListTextSpan.getLength(); i++) {
                    Node item = generalNodeListTextSpan.item(i);
                    //String texto = item.getTextContent();
                    if (i == 0) { //Ruc Proveedor
                        rucProveedor = item.getTextContent();
                    } else if (i == 6) {//Referencia Factura F000-234
                        referenciaFactura = item.getTextContent();
                    } else if (i == 15) { // Ruc Cliente
                        rucCliente = item.getTextContent();
                    } else if (i == 23) {//FechaEmision
                        fechaEmisionString = item.getTextContent();
                    } else if (i == 39) {//Sub Total
                        subTotal = item.getTextContent();
                    } else if (i == 40) { //Igv
                        igv = item.getTextContent();
                    } else if (i == 41) {//Monto Total
                        montoTotal = item.getTextContent();
                    } else if (i == 42) {//Moneda
                        if (item.getTextContent() != null && item.getTextContent().equalsIgnoreCase("S/")) {
                            codigoMoneda = "PEN";
                            logger.error("VerificarComprobanteRest 2 :: " + isStandar);
                        }
                    }


                }
            }

            String[] refFacturaStrings = referenciaFactura.split("-");

            if (refFacturaStrings.length > 1) {
                serieComprobante = refFacturaStrings[0];
                nroComprobante = refFacturaStrings[1];

                GetStatusOutput outputResponse = iStatusComprobante.getStatusComprobante(new GetStatusInput().builder()
                        .rucCliente(rucCliente)
                        .rucComprobante(rucProveedor)
                        .tipoComprobante(tipoComprobante)
                        .serieComprobante(serieComprobante)
                        .numeroComprobante(Integer.parseInt(nroComprobante + ""))
                        .build());
                codess = outputResponse.getCodigo();
                messages = outputResponse.getMensaje();
                if (codess.equalsIgnoreCase("0001") || codess.equalsIgnoreCase("0009") || codess.equalsIgnoreCase("0010")) {
                    bean.setType("S");
                    bean.setMensaje(messages);
                } else {
                    bean.setType("E");
                    bean.setMensaje(messages);
                }
                if (isStandar) {//Como siempre estubo funcionando
                    logger.error("VerificarComprobanteRest 3 :: " + isStandar);
                    fechaEmisionString = FunctionsXML.getTagValueHTML(facturaNodeList, "cbc:IssueDate");
                    if (fechaEmisionString == null)
                        fechaEmisionString = FunctionsXML.getTagValueHTML(facturaNodeList, "n4:IssueDate");
                    if (fechaEmisionString == null)
                        fechaEmisionString = FunctionsXML.getTagValueHTML(facturaNodeList, "n2:IssueDate");

                    codigoMoneda = FunctionsXML.getTagValueHTML(facturaNodeList, "cbc:DocumentCurrencyCode");
                    if (codigoMoneda == null)
                        codigoMoneda = FunctionsXML.getTagValueHTML(facturaNodeList, "n4:DocumentCurrencyCode");
                    if (codigoMoneda == null)
                        codigoMoneda = FunctionsXML.getTagValueHTML(facturaNodeList, "n2:DocumentCurrencyCode");

                    fechaEmision = DateUtils.stringToUtilDate(fechaEmisionString);
                } else {
                    logger.error("VerificarComprobanteRest 4 :: " + isStandar);
                    if (StringUtils.isNotBlank(fechaEmisionString)) {
                        logger.error("VerificarComprobanteRest 5 :: " + isStandar);
                        // String[] auxDate = fechaEmisionString.split(" ");
                        //DateFormat fmt = new SimpleDateFormat("MMMMM-dd-yyyy");
                        //fechaEmision = fmt.parse(auxDate[2] + "-" + auxDate[0] + "-" + auxDate[4]);
                        String[] auxDate = fechaEmisionString.split(" ");
                        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                        //fechaEmision  = fmt.parse(auxDate[2] + "-" + auxDate[0] + "-" + auxDate[4]);
                        fechaEmision  = fmt.parse(auxDate[0] + "/" + getNumberMonth(auxDate[2]) + "/" + auxDate[4]);

                    }
                }

                Sociedad sociedad = sociedadService.getOneByRucCliente(rucCliente);
                logger.error("VerificarComprobanteRest  sociedad  :: " + sociedad.getCodigoSociedad() );
                logger.error("VerificarComprobanteRest  rucProveedor  :: " + rucProveedor );
                logger.error("VerificarComprobanteRest  fechaEmision  :: " + fechaEmision );
                logger.error("VerificarComprobanteRest   referenciaFactura :: " +  referenciaFactura);
                logger.error("LeerComprobante  Tipo Comprobante  :: " +  tipoComprobante);
                logger.error("VerificarComprobanteRest  codigoMoneda  :: " +  codigoMoneda);
                logger.error("VerificarComprobanteRest  igv  :: " +  igv);
                logger.error("VerificarComprobanteRest  subTotal  :: " +  subTotal);
                logger.error("VerificarComprobanteRest  montoTotal  :: " +  montoTotal);
                prefactura.setSociedad(sociedad != null ? sociedad.getCodigoSociedad() : "");
                prefactura.setProveedorRuc(rucProveedor);
                prefactura.setFechaEmision(fechaEmision);
                prefactura.setReferencia(referenciaFactura);
                prefactura.setCodigoMoneda(codigoMoneda);
                prefactura.setIgv(igv);
                prefactura.setSubTotal(subTotal);
                prefactura.setTotal(montoTotal);
                // prefactura.setObservaciones(    );

                bean.setPrefactura(prefactura);
            } else {
                bean.setType("EX");
                bean.setMensaje("La referencia de factura: '" + referenciaFactura + "' encontrada en el archivo XML no es valida");
            }

            if (sunatXml.exists()) {
                sunatXml.delete();
            }

        } catch (IOException e) {
            messages = e.toString();
            bean.setType("EX");
            if (messages.equalsIgnoreCase("El Usuario ingresado no existe")) { // si el RUC del receptor no es uno valido, el usuario de autenticacion generado usando dicho RUC no es correcto
                messages = "El receptor no es el correcto";
            }
            bean.setMensaje(messages);
//            logger.error(e.toString());
        } catch (Exception e) {
            // TODO: handle exception
            messages = e.toString();
            bean.setType("EL");
            bean.setMensaje(messages);
//            logger.error(e.toString());
        }

        return new ResponseEntity<>(bean, HttpStatus.OK);
    }

    @PostMapping(value = "/LeerComprobante", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PrefacturaDto> LeerComprobante(@RequestParam(value = "file") MultipartFile archivoSunat) {
        PrefacturaDto prefactura = new PrefacturaDto();
        //@add ppo 18.01.2021
        String rucProveedor = "";
        String rucCliente = "";
        String tipoComprobante = "";
        String montoTotal = "";
        String igv = "";
        String subTotal = "";
        String referenciaFactura = "";
        String fechaEmisionString = "";
        String codigoMoneda = "";
        Date fechaEmision = null;
        NodeList facturaNodeList = null;
        //Fin
        try {
            File sunatXml = FunctionsXML.convert(archivoSunat);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(sunatXml);
            //@add ppo 18.01.2020
            boolean isStandar = true;
            NodeList facturaNodeListAux = doc.getElementsByTagName("Invoice");
            if (facturaNodeListAux.getLength() == 0) {
                facturaNodeListAux = doc.getElementsByTagName("tns:Invoice");
            }

            if (facturaNodeListAux.getLength() == 0) {
                isStandar = false;
            }
            //Fin
            if (isStandar) {//Forma normal
                logger.error("LeerComprobante 0 :: " + isStandar);
                XPath xPath = XPathFactory.newInstance().newXPath();
                referenciaFactura = xPath.compile("/Invoice/ID").evaluate(doc);
                String montoImpuestos = xPath.compile("/Invoice/TaxTotal/TaxAmount").evaluate(doc);
                String rucProveedorAlt = xPath.compile("/Invoice/AccountingSupplierParty/Party/PartyIdentification/ID").evaluate(doc);
                String rucClienteAlt = xPath.compile("/Invoice/AccountingCustomerParty/Party/PartyIdentification/ID").evaluate(doc);

                doc.getDocumentElement().normalize();

                NodeList proveedorNodeList = doc.getElementsByTagName("cac:AccountingSupplierParty");
                if (proveedorNodeList.getLength() == 0)
                    proveedorNodeList = doc.getElementsByTagName("n5:AccountingSupplierParty");

                NodeList clienteNodeList = doc.getElementsByTagName("cac:AccountingCustomerParty");
                if (clienteNodeList.getLength() == 0)
                    clienteNodeList = doc.getElementsByTagName("n5:AccountingCustomerParty");

                facturaNodeList = doc.getElementsByTagName("Invoice");
                if (facturaNodeList.getLength() == 0)
                    facturaNodeList = doc.getElementsByTagName("tns:Invoice");

                NodeList taxSubTotalNodeList = doc.getElementsByTagName("cac:TaxTotal");
                if (taxSubTotalNodeList.getLength() == 0)
                    taxSubTotalNodeList = doc.getElementsByTagName("n5:TaxTotal");
                if (taxSubTotalNodeList.getLength() == 0)
                    taxSubTotalNodeList = doc.getElementsByTagName("n1:TaxTotal");

                NodeList taxTotalNodeList = doc.getElementsByTagName("cac:LegalMonetaryTotal");
                if (taxTotalNodeList.getLength() == 0)
                    taxTotalNodeList = doc.getElementsByTagName("n5:LegalMonetaryTotal");
                if (taxTotalNodeList.getLength() == 0)
                    taxTotalNodeList = doc.getElementsByTagName("n1:LegalMonetaryTotal");

                rucProveedor = FunctionsXML.getTagValueHTML(proveedorNodeList, "cbc:ID");
                if (rucProveedor == null)
                    rucProveedor = FunctionsXML.getTagValueHTML(proveedorNodeList, "n4:ID");

                rucCliente = FunctionsXML.getTagValueHTML(clienteNodeList, "cbc:ID");
                if (rucCliente == null)
                    rucCliente = FunctionsXML.getTagValueHTML(clienteNodeList, "n4:ID");

                if (rucProveedor == null || (!NumberUtils.stringIsLong(rucProveedor) && rucProveedor.length() != 11))
                    rucProveedor = rucProveedorAlt;

                if (rucCliente == null || (!NumberUtils.stringIsLong(rucCliente) && rucCliente.length() != 11))
                    rucCliente = rucClienteAlt;

                tipoComprobante = FunctionsXML.getTagValueHTML(facturaNodeList, "cbc:InvoiceTypeCode");
                if (tipoComprobante == null)
                    tipoComprobante = FunctionsXML.getTagValueHTML(facturaNodeList, "n4:InvoiceTypeCode");
                if (tipoComprobante == null)
                    tipoComprobante = FunctionsXML.getTagValueHTML(facturaNodeList, "n2:InvoiceTypeCode");

                montoTotal = FunctionsXML.getTagValueHTML(taxTotalNodeList, "cbc:PayableAmount");
                if (montoTotal == null)
                    montoTotal = FunctionsXML.getTagValueHTML(taxTotalNodeList, "n4:PayableAmount");
                if (montoTotal == null)
                    montoTotal = FunctionsXML.getTagValueHTML(taxTotalNodeList, "n2:PayableAmount");

                igv = "";
                List<String> igvList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "cbc:Name", "IGV", "cbc:TaxAmount");
                if (igvList.isEmpty())
                    igvList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "n4:Name", "IGV", "n4:TaxAmount");
                if (!igvList.isEmpty())
                    igv = igvList.get(0);
                if (igv == null || !NumberUtils.stringIsBigDecimal(igv))
                    igv = montoImpuestos;

                subTotal = "";
                List<String> subTotalList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "cbc:Name", "IGV", "cbc:TaxableAmount");
                if (subTotalList.isEmpty())
                    subTotalList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "n4:Name", "IGV", "n4:TaxableAmount");
                if (!subTotalList.isEmpty())
                    subTotal = subTotalList.get(0);
                if (subTotal == null || !NumberUtils.stringIsBigDecimal(subTotal) || ((new BigDecimal(subTotal).compareTo(BigDecimal.ZERO)) == 0)) {
                    String subTotalAlt = FunctionsXML.getTagValueHTML(taxTotalNodeList, "cbc:LineExtensionAmount");
                    if (subTotalAlt == null)
                        subTotalAlt = FunctionsXML.getTagValueHTML(taxTotalNodeList, "n4:LineExtensionAmount");
                    if (subTotalAlt != null && NumberUtils.stringIsBigDecimal(subTotalAlt) && (new BigDecimal(subTotalAlt).compareTo(BigDecimal.ZERO) > 0))
                        subTotal = subTotalAlt;
                    else
                        subTotal = (new BigDecimal(montoTotal).subtract(new BigDecimal(igv))).toString();
                }

                fechaEmisionString = FunctionsXML.getTagValueHTML(facturaNodeList, "cbc:IssueDate");
                if (fechaEmisionString == null)
                    fechaEmisionString = FunctionsXML.getTagValueHTML(facturaNodeList, "n4:IssueDate");
                if (fechaEmisionString == null)
                    fechaEmisionString = FunctionsXML.getTagValueHTML(facturaNodeList, "n2:IssueDate");

                codigoMoneda = FunctionsXML.getTagValueHTML(facturaNodeList, "cbc:DocumentCurrencyCode");
                if (codigoMoneda == null)
                    codigoMoneda = FunctionsXML.getTagValueHTML(facturaNodeList, "n4:DocumentCurrencyCode");
                if (codigoMoneda == null)
                    codigoMoneda = FunctionsXML.getTagValueHTML(facturaNodeList, "n2:DocumentCurrencyCode");

                fechaEmision = DateUtils.stringToUtilDate(fechaEmisionString);
            } else {
                logger.error("LeerComprobante 1 :: " + isStandar);
                tipoComprobante = "01";
                NodeList generalNodeListTextSpan = doc.getElementsByTagName("text:span");
                for (int i = 0; i < generalNodeListTextSpan.getLength(); i++) {
                    Node item = generalNodeListTextSpan.item(i);
                    //String texto = item.getTextContent();
                    if (i == 0) { //Ruc Proveedor
                        rucProveedor = item.getTextContent();
                    } else if (i == 6) {//Referencia Factura F000-234
                        referenciaFactura = item.getTextContent();
                    } else if (i == 15) { // Ruc Cliente
                        rucCliente = item.getTextContent();
                    } else if (i == 23) {//FechaEmision
                        fechaEmisionString = item.getTextContent();
                    } else if (i == 39) {//Sub Total
                        subTotal = item.getTextContent();
                    } else if (i == 40) { //Igv
                        igv = item.getTextContent();
                    } else if (i == 41) {//Monto Total
                        montoTotal = item.getTextContent();
                    } else if (i == 42) {//Moneda
                        if (item.getTextContent() != null && item.getTextContent().equalsIgnoreCase("S/")) {
                            codigoMoneda = "PEN";
                        }
                    }


                }
                if (StringUtils.isNotBlank(fechaEmisionString)) {
                    logger.error("LeerComprobante 2 :: " + isStandar);
                    //String[] auxDate = fechaEmisionString.split(" ");
                    //DateFormat fmt = new SimpleDateFormat("MMMMM-dd-yyyy");
                    //fechaEmision = fmt.parse(auxDate[2] + "-" + auxDate[0] + "-" + auxDate[4]);
                    String[] auxDate = fechaEmisionString.split(" ");
                    DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                    //fechaEmision  = fmt.parse(auxDate[2] + "-" + auxDate[0] + "-" + auxDate[4]);
                    fechaEmision  = fmt.parse(auxDate[0] + "/" + getNumberMonth(auxDate[2]) + "/" + auxDate[4]);

                }
            }

            Sociedad sociedad = sociedadService.getOneByRucCliente(rucCliente);
            logger.error("LeerComprobante  sociedad  :: " + sociedad.getCodigoSociedad() );
            logger.error("LeerComprobante  rucProveedor  :: " + rucProveedor );
            logger.error("LeerComprobante  fechaEmision  :: " + fechaEmision );
            logger.error("LeerComprobante   referenciaFactura :: " +  referenciaFactura);
            logger.error("LeerComprobante  Tipo Comprobante  :: " +  tipoComprobante);
            logger.error("LeerComprobante  codigoMoneda  :: " +  codigoMoneda);
            logger.error("LeerComprobante  igv  :: " +  igv);
            logger.error("LeerComprobante  subTotal  :: " +  subTotal);
            logger.error("LeerComprobante  montoTotal  :: " +  montoTotal);
            prefactura.setSociedad(sociedad != null ? sociedad.getCodigoSociedad() : "");
            prefactura.setProveedorRuc(rucProveedor);
            prefactura.setFechaEmision(fechaEmision);
            prefactura.setReferencia(referenciaFactura);
            prefactura.setCodigoMoneda(codigoMoneda);
            prefactura.setIgv(igv);
            prefactura.setSubTotal(subTotal);
            prefactura.setTotal(montoTotal);
            prefactura.setObservaciones("RUC Cliente: " + rucCliente + " // Tipo Comprobante: " + tipoComprobante);

            if (sunatXml.exists()) {
                sunatXml.delete();
            }

            return new ResponseEntity<>(prefactura, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    public  String getNumberMonth(String nameMonth) {
        String[] monthStringEn = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] monthStringEs = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        int numMes = 0;
        for(int i = 0 ; i < monthStringEs.length ; i++) {
            if(monthStringEs[i].equalsIgnoreCase(nameMonth)) {
                numMes = i + 1;
                break;
            }
            if(monthStringEn[i].equalsIgnoreCase(nameMonth)) {
                numMes = i + 1;
                break;
            }
        }
        return String.format("%02d", numMes);
    }
    //Backup
    /*
    @PostMapping(value = "/ValidarComprobante",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<MensajePrefactura> VerificarComprobanteRest(@RequestParam(value = "file") MultipartFile archivoSunat){
        MensajePrefactura bean = new MensajePrefactura();
        String codess;
        String messages;
        String serieComprobante = "";
        String nroComprobante = "";
        PrefacturaDto prefactura = new PrefacturaDto();

        try {
            File sunatXml = FunctionsXML.convert(archivoSunat);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(sunatXml);

            XPath xPath = XPathFactory.newInstance().newXPath();
            String referenciaFactura = xPath.compile("/Invoice/ID").evaluate(doc);
            String montoImpuestos = xPath.compile("/Invoice/TaxTotal/TaxAmount").evaluate(doc);
            String rucProveedorAlt = xPath.compile("/Invoice/AccountingSupplierParty/Party/PartyIdentification/ID").evaluate(doc);
            String rucClienteAlt = xPath.compile("/Invoice/AccountingCustomerParty/Party/PartyIdentification/ID").evaluate(doc);

            doc.getDocumentElement().normalize();

            NodeList proveedorNodeList = doc.getElementsByTagName("cac:AccountingSupplierParty");
            if(proveedorNodeList.getLength() == 0)
                proveedorNodeList = doc.getElementsByTagName("n5:AccountingSupplierParty");

            NodeList clienteNodeList = doc.getElementsByTagName("cac:AccountingCustomerParty");
            if(clienteNodeList.getLength() == 0)
                clienteNodeList = doc.getElementsByTagName("n5:AccountingCustomerParty");

            NodeList facturaNodeList = doc.getElementsByTagName("Invoice");
            if(facturaNodeList.getLength() == 0)
                facturaNodeList = doc.getElementsByTagName("tns:Invoice");

            NodeList taxSubTotalNodeList = doc.getElementsByTagName("cac:TaxTotal");
            if(taxSubTotalNodeList.getLength() == 0)
                taxSubTotalNodeList = doc.getElementsByTagName("n5:TaxTotal");

            NodeList taxTotalNodeList = doc.getElementsByTagName("cac:LegalMonetaryTotal");
            if(taxTotalNodeList.getLength() == 0)
                taxTotalNodeList = doc.getElementsByTagName("n5:LegalMonetaryTotal");

            String rucProveedor = FunctionsXML.getTagValueHTML(proveedorNodeList, "cbc:ID");
            if(rucProveedor == null)
                rucProveedor = FunctionsXML.getTagValueHTML(proveedorNodeList, "n4:ID");

            String rucCliente = FunctionsXML.getTagValueHTML(clienteNodeList, "cbc:ID");
            if(rucCliente == null)
                rucCliente = FunctionsXML.getTagValueHTML(clienteNodeList, "n4:ID");

            if(rucProveedor == null || (!NumberUtils.stringIsLong(rucProveedor) && rucProveedor.length() != 11))
                rucProveedor = rucProveedorAlt;

            if(rucCliente == null || (!NumberUtils.stringIsLong(rucCliente) && rucCliente.length() != 11))
                rucCliente = rucClienteAlt;

            String tipoComprobante = FunctionsXML.getTagValueHTML(facturaNodeList, "cbc:InvoiceTypeCode");
            if(tipoComprobante == null)
                tipoComprobante = FunctionsXML.getTagValueHTML(facturaNodeList, "n4:InvoiceTypeCode");

            String montoTotal = FunctionsXML.getTagValueHTML(taxTotalNodeList, "cbc:PayableAmount");
            if(montoTotal == null)
                montoTotal = FunctionsXML.getTagValueHTML(taxTotalNodeList, "n4:PayableAmount");

            String igv = "";
            List<String> igvList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "cbc:Name", "IGV", "cbc:TaxAmount");
            if(igvList.isEmpty())
                igvList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "n4:Name", "IGV", "n4:TaxAmount");
            if(!igvList.isEmpty())
                igv = igvList.get(0);
            if(igv == null || !NumberUtils.stringIsBigDecimal(igv))
                igv = montoImpuestos;

            String subTotal = "";
            List<String> subTotalList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "cbc:Name", "IGV", "cbc:TaxableAmount" );
            if(subTotalList.isEmpty())
                subTotalList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "n4:Name", "IGV", "n4:TaxableAmount" );
            if(!subTotalList.isEmpty())
                subTotal = subTotalList.get(0);
            if(subTotal == null || !NumberUtils.stringIsBigDecimal(subTotal) || ((new BigDecimal(subTotal).compareTo(BigDecimal.ZERO)) == 0)){
                String subTotalAlt = FunctionsXML.getTagValueHTML(taxTotalNodeList, "cbc:LineExtensionAmount");
                if(subTotalAlt == null)
                    subTotalAlt = FunctionsXML.getTagValueHTML(taxTotalNodeList, "n4:LineExtensionAmount");
                if (subTotalAlt != null && NumberUtils.stringIsBigDecimal(subTotalAlt) && (new BigDecimal(subTotalAlt).compareTo(BigDecimal.ZERO) > 0))
                    subTotal = subTotalAlt;
                else
                    subTotal =(new BigDecimal(montoTotal).subtract(new BigDecimal(igv))).toString();
            }

            String[] refFacturaStrings = referenciaFactura.split("-");

            if (refFacturaStrings.length > 1) {
                serieComprobante = refFacturaStrings[0];
                nroComprobante = refFacturaStrings[1];

                BillConsultPortBidingServiceLocator locator = new BillConsultPortBidingServiceLocator();
                locator.setBillConsultServicePortEndpointAddress("https://ww1.sunat.gob.pe/ol-it-wsconscpegem/billConsultService");//
                BillConsultService port = locator.getBillConsultServicePort();
                Stub stub = ((Stub) port);
                int nroComprobanteInteger = Integer.parseInt(nroComprobante + "");
                SOAPHeaderElement wsseSecurity = new SOAPHeaderElement(new PrefixedQName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security", "wsse"));
                MessageElement usernameToken = new MessageElement("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:UsernameToken");
                MessageElement username = new MessageElement("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:Username");
                MessageElement password = new MessageElement("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:Password");

                username.setObjectValue(rucCliente.concat(repositoryUser));
                usernameToken.addChild(username);
                password.setObjectValue(repositoryPass);
                usernameToken.addChild(password);
                wsseSecurity.addChild(usernameToken);
                stub.setHeader(wsseSecurity);
                StatusResponse ff = port.getStatus(rucProveedor, tipoComprobante, serieComprobante, nroComprobanteInteger);

                codess = ff.getStatusCode();
                messages = ff.getStatusMessage();
                if (codess.equalsIgnoreCase("0001") || codess.equalsIgnoreCase("0009") || codess.equalsIgnoreCase("0010")) {
                    bean.setType("S");
                    bean.setMensaje(messages);
                } else {
                    bean.setType("E");
                    bean.setMensaje(messages);
                }

                String fechaEmisionString = FunctionsXML.getTagValueHTML(facturaNodeList, "cbc:IssueDate");
                if(fechaEmisionString == null)
                    fechaEmisionString = FunctionsXML.getTagValueHTML(facturaNodeList, "n4:IssueDate");

                String codigoMoneda = FunctionsXML.getTagValueHTML(facturaNodeList, "cbc:DocumentCurrencyCode");
                if(codigoMoneda == null)
                    codigoMoneda = FunctionsXML.getTagValueHTML(facturaNodeList, "n4:DocumentCurrencyCode");

                Date fechaEmision = DateUtils.stringToUtilDate(fechaEmisionString);
                Sociedad sociedad = sociedadService.getOneByRucCliente(rucCliente);

                prefactura.setSociedad(sociedad != null ? sociedad.getCodigoSociedad() : "");
                prefactura.setProveedorRuc(rucProveedor);
                prefactura.setFechaEmision(fechaEmision);
                prefactura.setReferencia(referenciaFactura);
                prefactura.setCodigoMoneda(codigoMoneda);
                prefactura.setIgv(igv);
                prefactura.setSubTotal(subTotal);
                prefactura.setTotal(montoTotal);
                // prefactura.setObservaciones(    );

                bean.setPrefactura(prefactura);
            }
            else{
                bean.setType("EX");
                bean.setMensaje("La referencia de factura: '" + referenciaFactura + "' encontrada en el archivo XML no es valida");
            }

            if (sunatXml.exists()) {
                sunatXml.delete();
            }

        } catch (IOException e) {
            messages = e.toString();
            bean.setType("EX");
            if(messages.equalsIgnoreCase("El Usuario ingresado no existe")) { // si el RUC del receptor no es uno valido, el usuario de autenticacion generado usando dicho RUC no es correcto
                messages = "El receptor no es el correcto";
            }
            bean.setMensaje(messages);
//            logger.error(e.toString());
        }catch (Exception e) {
            // TODO: handle exception
            messages = e.toString();
            bean.setType("EL");
            bean.setMensaje(messages);
//            logger.error(e.toString());
        }

        return new ResponseEntity<>(bean, HttpStatus.OK);
    }

    @PostMapping(value = "/LeerComprobante",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PrefacturaDto> LeerComprobante(@RequestParam(value = "file") MultipartFile archivoSunat){
        PrefacturaDto prefactura = new PrefacturaDto();

        try {
            File sunatXml = FunctionsXML.convert(archivoSunat);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(sunatXml);

            XPath xPath = XPathFactory.newInstance().newXPath();
            String referenciaFactura = xPath.compile("/Invoice/ID").evaluate(doc);
            String montoImpuestos = xPath.compile("/Invoice/TaxTotal/TaxAmount").evaluate(doc);
            String rucProveedorAlt = xPath.compile("/Invoice/AccountingSupplierParty/Party/PartyIdentification/ID").evaluate(doc);
            String rucClienteAlt = xPath.compile("/Invoice/AccountingCustomerParty/Party/PartyIdentification/ID").evaluate(doc);

            doc.getDocumentElement().normalize();

            NodeList proveedorNodeList = doc.getElementsByTagName("cac:AccountingSupplierParty");
            if(proveedorNodeList.getLength() == 0)
                proveedorNodeList = doc.getElementsByTagName("n5:AccountingSupplierParty");

            NodeList clienteNodeList = doc.getElementsByTagName("cac:AccountingCustomerParty");
            if(clienteNodeList.getLength() == 0)
                clienteNodeList = doc.getElementsByTagName("n5:AccountingCustomerParty");

            NodeList facturaNodeList = doc.getElementsByTagName("Invoice");
            if(facturaNodeList.getLength() == 0)
                facturaNodeList = doc.getElementsByTagName("tns:Invoice");

            NodeList taxSubTotalNodeList = doc.getElementsByTagName("cac:TaxTotal");
            if(taxSubTotalNodeList.getLength() == 0)
                taxSubTotalNodeList = doc.getElementsByTagName("n5:TaxTotal");

            NodeList taxTotalNodeList = doc.getElementsByTagName("cac:LegalMonetaryTotal");
            if(taxTotalNodeList.getLength() == 0)
                taxTotalNodeList = doc.getElementsByTagName("n5:LegalMonetaryTotal");

            String rucProveedor = FunctionsXML.getTagValueHTML(proveedorNodeList, "cbc:ID");
            if(rucProveedor == null)
                rucProveedor = FunctionsXML.getTagValueHTML(proveedorNodeList, "n4:ID");

            String rucCliente = FunctionsXML.getTagValueHTML(clienteNodeList, "cbc:ID");
            if(rucCliente == null)
                rucCliente = FunctionsXML.getTagValueHTML(clienteNodeList, "n4:ID");

            if(rucProveedor == null || (!NumberUtils.stringIsLong(rucProveedor) && rucProveedor.length() != 11))
                rucProveedor = rucProveedorAlt;

            if(rucCliente == null || (!NumberUtils.stringIsLong(rucCliente) && rucCliente.length() != 11))
                rucCliente = rucClienteAlt;

            String tipoComprobante = FunctionsXML.getTagValueHTML(facturaNodeList, "cbc:InvoiceTypeCode");
            if(tipoComprobante == null)
                tipoComprobante = FunctionsXML.getTagValueHTML(facturaNodeList, "n4:InvoiceTypeCode");

            String montoTotal = FunctionsXML.getTagValueHTML(taxTotalNodeList, "cbc:PayableAmount");
            if(montoTotal == null)
                montoTotal = FunctionsXML.getTagValueHTML(taxTotalNodeList, "n4:PayableAmount");

            String igv = "";
            List<String> igvList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "cbc:Name", "IGV", "cbc:TaxAmount");
            if(igvList.isEmpty())
                igvList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "n4:Name", "IGV", "n4:TaxAmount");
            if(!igvList.isEmpty())
                igv = igvList.get(0);
            if(igv == null || !NumberUtils.stringIsBigDecimal(igv))
                igv = montoImpuestos;

            String subTotal = "";
            List<String> subTotalList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "cbc:Name", "IGV", "cbc:TaxableAmount" );
            if(subTotalList.isEmpty())
                subTotalList = FunctionsXML.getTagValueIntoTagHTML(taxSubTotalNodeList, "n4:Name", "IGV", "n4:TaxableAmount" );
            if(!subTotalList.isEmpty())
                subTotal = subTotalList.get(0);
            if(subTotal == null || !NumberUtils.stringIsBigDecimal(subTotal) || ((new BigDecimal(subTotal).compareTo(BigDecimal.ZERO)) == 0)){
                String subTotalAlt = FunctionsXML.getTagValueHTML(taxTotalNodeList, "cbc:LineExtensionAmount");
                if(subTotalAlt == null)
                    subTotalAlt = FunctionsXML.getTagValueHTML(taxTotalNodeList, "n4:LineExtensionAmount");
                if (subTotalAlt != null && NumberUtils.stringIsBigDecimal(subTotalAlt) && (new BigDecimal(subTotalAlt).compareTo(BigDecimal.ZERO) > 0))
                    subTotal = subTotalAlt;
                else
                    subTotal =(new BigDecimal(montoTotal).subtract(new BigDecimal(igv))).toString();
            }

            String fechaEmisionString = FunctionsXML.getTagValueHTML(facturaNodeList, "cbc:IssueDate");
            if(fechaEmisionString == null)
                fechaEmisionString = FunctionsXML.getTagValueHTML(facturaNodeList, "n4:IssueDate");

            String codigoMoneda = FunctionsXML.getTagValueHTML(facturaNodeList, "cbc:DocumentCurrencyCode");
            if(codigoMoneda == null)
                codigoMoneda = FunctionsXML.getTagValueHTML(facturaNodeList, "n4:DocumentCurrencyCode");

            Date fechaEmision = DateUtils.stringToUtilDate (fechaEmisionString);
            Sociedad sociedad = sociedadService.getOneByRucCliente(rucCliente);

            prefactura.setSociedad(sociedad != null ? sociedad.getCodigoSociedad() : "");
            prefactura.setProveedorRuc(rucProveedor);
            prefactura.setFechaEmision(fechaEmision);
            prefactura.setReferencia(referenciaFactura);
            prefactura.setCodigoMoneda(codigoMoneda);
            prefactura.setIgv(igv);
            prefactura.setSubTotal(subTotal);
            prefactura.setTotal(montoTotal);
            prefactura.setObservaciones("RUC Cliente: " + rucCliente + " // Tipo Comprobante: " + tipoComprobante);

            if (sunatXml.exists()) {
                sunatXml.delete();
            }

            return new ResponseEntity<>(prefactura, HttpStatus.OK);
        }
        catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
     */
}
