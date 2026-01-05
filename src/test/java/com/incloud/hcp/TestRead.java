package com.incloud.hcp;

import com.incloud.hcp.domain.Sociedad;
import com.incloud.hcp.dto.PrefacturaDto;
import com.incloud.hcp.util.FunctionsXML;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.NumberUtils;
import com.incloud.hcp.util.Utils;
import org.apache.commons.lang.StringUtils;
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

public class TestRead {
    public static void main(String[] args) {
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
//            File sunatXml = FunctionsXML.convert(archivoSunat);
            File sunatXml = new File("D:\\WORK\\COPEINCA\\20382072023-01-F010-75058 (2).xml");
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
                System.out.println("LeerComprobante 0 :: " + isStandar);
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
                System.out.println("LeerComprobante 1 :: " + isStandar);
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
                    System.out.println("LeerComprobante 2 :: " + isStandar);
                    //String[] auxDate = fechaEmisionString.split(" ");
                    //DateFormat fmt = new SimpleDateFormat("MMMMM-dd-yyyy");
                    //fechaEmision = fmt.parse(auxDate[2] + "-" + auxDate[0] + "-" + auxDate[4]);
                    String[] auxDate = fechaEmisionString.split(" ");
                    DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                    //fechaEmision  = fmt.parse(auxDate[2] + "-" + auxDate[0] + "-" + auxDate[4]);
                    fechaEmision  = fmt.parse(auxDate[0] + "/" + getNumberMonth(auxDate[2]) + "/" + auxDate[4]);

                }
            }

//            Sociedad sociedad = sociedadService.getOneByRucCliente(rucCliente);
            Sociedad sociedad = new Sociedad();
            System.out.println("LeerComprobante  sociedad  :: " + sociedad.getCodigoSociedad() );
            System.out.println("LeerComprobante  rucProveedor  :: " + rucProveedor );
            System.out.println("LeerComprobante  fechaEmision  :: " + fechaEmision );
            System.out.println("LeerComprobante   referenciaFactura :: " +  referenciaFactura);
            System.out.println("LeerComprobante  Tipo Comprobante  :: " +  tipoComprobante);
            System.out.println("LeerComprobante  codigoMoneda  :: " +  codigoMoneda);
            System.out.println("LeerComprobante  igv  :: " +  igv);
            System.out.println("LeerComprobante  subTotal  :: " +  subTotal);
            System.out.println("LeerComprobante  montoTotal  :: " +  montoTotal);
            prefactura.setSociedad(sociedad != null ? sociedad.getCodigoSociedad() : "");
            prefactura.setProveedorRuc(rucProveedor);
            prefactura.setFechaEmision(fechaEmision);
            prefactura.setReferencia(referenciaFactura);
            prefactura.setCodigoMoneda(codigoMoneda);
            prefactura.setIgv(igv);
            prefactura.setSubTotal(subTotal);
            prefactura.setTotal(montoTotal);
            prefactura.setObservaciones("RUC Cliente: " + rucCliente + " // Tipo Comprobante: " + tipoComprobante);

//            if (sunatXml.exists()) {
//                sunatXml.delete();
//            }

//            return new ResponseEntity<>(prefactura, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    public static String getNumberMonth(String nameMonth) {
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
}
