package com.incloud.hcp.sap.materiales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class DOMParserMateriales {

    private static Logger logger = LoggerFactory.getLogger(DOMParserMateriales.class);

    private DocumentBuilder docBuilder;
    private Element root;

    //private List<DTP027MaestroMaterialesRes.Response> materiales = new ArrayList<DTP027MaestroMaterialesRes.Response>();

    public DOMParserMateriales() throws Exception {
        logger.error("ejecutarOkHttpClient DOMParserMateriales 01");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        docBuilder = dbf.newDocumentBuilder();
        logger.error("ejecutarOkHttpClient DOMParserMateriales 02");
    }

    public void parse(String responseString) throws Exception {
        logger.error("ejecutarOkHttpClient DOMParserMateriales 03");
        Document doc = docBuilder.parse(new
                ByteArrayInputStream(responseString.getBytes()));
        //logger.error("ejecutarOkHttpClient DOMParserMateriales 04");
        root = doc.getDocumentElement();
        //logger.error("ejecutarOkHttpClient DOMParserMateriales 05");
    }

    /*public void printAllElements() throws Exception {
        //logger.error("ejecutarOkHttpClient 08a");
        printElement(root);
        //logger.error("ejecutarOkHttpClient 08aa");
    }*/

    /*public void printElement(Node node) {
        logger.error("ejecutarOkHttpClient 08b");
        if (node.getNodeType() != Node.TEXT_NODE) {
            Node child = node.getFirstChild();
            while (child != null) {
                //logger.error("ejecutarOkHttpClient 08c child: " + child.getNodeName());
                if ("Response".equals(child.getNodeName())) {
                    logger.error("ejecutarOkHttpClient 08c");
                    NodeList station = child.getChildNodes();

                    Map<String, String> map = new HashMap<String, String>();
                    for (int i = 0; i < station.getLength(); i++) {
                        Node s = station.item(i);
                        if (s.getNodeType() == Node.ELEMENT_NODE) {
                            map.put(s.getNodeName(), s.getChildNodes().item(0).getNodeValue());
                        }
                    }
                    //logger.error("ejecutarOkHttpClient 08d map: " + map);
                    DTP027MaestroMaterialesRes.Response airportStation = new DTP027MaestroMaterialesRes.Response();
                    //logger.error("ejecutarOkHttpClient 08e");
                    try {
                        BeanUtils.populate(airportStation, map);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    //logger.error("ejecutarOkHttpClient 08f bean: " + airportStation);
                    materiales.add(airportStation);
                }
                printElement(child);
                child = child.getNextSibling();
            }
        }
    }*/

    /*public List<DTP027MaestroMaterialesRes.Response> getMateriales() {
        return materiales;
    }
*/
}