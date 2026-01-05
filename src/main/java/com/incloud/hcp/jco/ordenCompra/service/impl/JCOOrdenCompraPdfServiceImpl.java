package com.incloud.hcp.jco.ordenCompra.service.impl;

import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.enums.OrdenCompraEstadoEnum;
import com.incloud.hcp.jco.ordenCompra.dto.*;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenCompraPdfService;
import com.incloud.hcp.repository.OrdenCompraRepository;
import com.incloud.hcp.util.DateUtils;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JCOOrdenCompraPdfServiceImpl implements JCOOrdenCompraPdfService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    public JCOOrdenCompraPdfServiceImpl(OrdenCompraRepository ordenCompraRepository) {
        this.ordenCompraRepository = ordenCompraRepository;
    }

    @Override
    public OrdenCompraPdfDto extraerBloquesOrdenCompraDtoRFC(String numeroOrdenCompra) throws  Exception{
        OrdenCompraPdfDto dto = new OrdenCompraPdfDto();
        try{
            String FUNCION_RFC = "ZPE_MM_INFO_PDF";
            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();
            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            this.mapFilters(jCoFunction, numeroOrdenCompra);

            //Ejecutar RFC
            logger.error("Ejecutando RFC: {}", destination);
            jCoFunction.execute(destination);
            String texto1 = jCoFunction.getExportParameterList().getString("T_TEXTO_1");
            dto.setLugarEntregaDireccion(texto1);
            JCoTable textoTable = jCoFunction.getTableParameterList().getTable("T_TEXTO_2");
            if (textoTable != null && textoTable.getNumRows() > 0) {
                StringBuilder sb = new StringBuilder();
                textoTable.firstRow();
                int rowIndex = 0;
                do {
                    String linea = textoTable.getString("TDLINE");
                    // Solo en la primera línea, quitar <H> y </>
                    if (rowIndex == 0) {
                        linea = linea.replace("<H>", "").replace("</>", "");
                    }
                    sb.append(linea).append("\n");
                    rowIndex++;
                } while (textoTable.nextRow());
                dto.setLugarEntregaDescription(sb.toString().trim());
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e.getCause());
            throw new Exception("Error al extraer bloques desde SAP: " + e.getMessage(), e);
        }
        logger.error("Datos del dto: {}", dto);
        return dto;
    }

    @Override
    public OrdenCompraPdfDto extraerOrdenCompraPdfDtoRFC(String numeroOrdenCompra) throws Exception {
        try {
            OrdenCompraPdfDto dto = extraerBloquesOrdenCompraDtoRFC(numeroOrdenCompra);
            String FUNCION_RFC = "ZPE_MM_COMPRAS_DETAIL_PDF";

            JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
            JCoRepository repository = destination.getRepository();
            JCoFunction jCoFunction = repository.getFunction(FUNCION_RFC);
            this.mapFilters(jCoFunction, numeroOrdenCompra);
            jCoFunction.execute(destination);

            JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
            JCoParameterList exportParameterList = jCoFunction.getExportParameterList();

            BigDecimal montoSubtotal = Optional.ofNullable(exportParameterList.getBigDecimal("PO_SUBTO")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
            BigDecimal montoDescuento = Optional.ofNullable(exportParameterList.getBigDecimal("PO_TOT_COND")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
            BigDecimal montoIgv = Optional.ofNullable(exportParameterList.getBigDecimal("PO_IGV")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
            BigDecimal montoImporteTotal = Optional.ofNullable(exportParameterList.getBigDecimal("PO_TOTAL")).orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);

            OrdenCompraPdfExtractorMapper ordenCompraPdfExtractorMapper = OrdenCompraPdfExtractorMapper.newMapper(tableParameterList);

            List<OrdenCompraPdfDto> ordenCompraPdfDtoList = ordenCompraPdfExtractorMapper.getOrdenCompraPdfDtoList();
            List<SociedadDto> sociedadDtoList = ordenCompraPdfExtractorMapper.getSociedadDtoList();
            List<OrdenCompraPosicionPdfDto> ordenCompraPosicionPdfDtoList = ordenCompraPdfExtractorMapper.getOrdenCompraPosicionPdfDtoList();
            List<OrdenCompraPosicionClaseCondicionPdfDto> claseCondicionPdfDtoList = ordenCompraPdfExtractorMapper.getClaseCondicionPdfDtoList();
            List<TextoPosicionDto> textoPosicionDtoList = ordenCompraPdfExtractorMapper.getTextoPosicionList("PO_TEXTO_POS");
            List<TextoPosicionDto> textoRegistroInfoDtoList = ordenCompraPdfExtractorMapper.getTextoPosicionList("PO_TEXTO_REG_POS");
            List<TextoPosicionDto> textoAmpliadoMaterialDtoList = ordenCompraPdfExtractorMapper.getTextoPosicionList("PO_TEXTO_AMPL_MAT");

            List<String> textoCabeceraList = ordenCompraPdfExtractorMapper.getTextoList("PO_TEXTO_CABECERA");
            List<String> textoCabeceraObservacionesList = ordenCompraPdfExtractorMapper.getTextoList("PO_TEXTO_CABECERA_OBS");
            List<String> textoFechaList = ordenCompraPdfExtractorMapper.getTextoList("PO_FECHA");
            List<String> textoNotasImportantesList = ordenCompraPdfExtractorMapper.getTextoList("PO_NOTAS_IM");
            List<String> textoNotasProveedorList = ordenCompraPdfExtractorMapper.getTextoList("PO_NOTAS_PRO");
            List<String> textoNotasClausulaList = ordenCompraPdfExtractorMapper.getTextoList("PO_CLAUSULAS");

            String header1 = "INI: " + DateUtils.getCurrentTimestamp().toString() + " -- EXTR PDF OC: " + numeroOrdenCompra;
            logger.error(header1 + " // Extraccion PDF Orden de Compra");

            OrdenCompraPdfDto ordenCompraPdfDto = new OrdenCompraPdfDto();

            if (ordenCompraPdfDtoList.size() == 1){
                ordenCompraPdfDto = ordenCompraPdfDtoList.get(0);

                if (sociedadDtoList.size() == 1) {
                    SociedadDto sociedadDto = sociedadDtoList.get(0);

                    ordenCompraPdfDto.setClienteRazonSocial(sociedadDto.getRazonSocial());
                    ordenCompraPdfDto.setClienteRuc(sociedadDto.getRuc());
                    ordenCompraPdfDto.setClienteTelefono(sociedadDto.getTelefono());
                    ordenCompraPdfDto.setClienteDireccion(sociedadDto.getCalle() + " " + sociedadDto.getNumero() + (!sociedadDto.getPoblacion().equals("") ? (" - " + sociedadDto.getPoblacion()) : "") + (!sociedadDto.getDistrito().equals("") ? (" - " + sociedadDto.getDistrito()) : ""));
                }

                if(ordenCompraPdfDto.getProveedorNumero() != null && !ordenCompraPdfDto.getProveedorNumero().isEmpty()) {
                    ordenCompraPdfDto.setProveedorNumero(String.valueOf(Integer.parseInt(ordenCompraPdfDto.getProveedorNumero())));
                }

                ordenCompraPdfDto.setMontoSubtotal(montoSubtotal);
                ordenCompraPdfDto.setMontoDescuento(montoDescuento);
                ordenCompraPdfDto.setMontoIgv(montoIgv);
                ordenCompraPdfDto.setMontoImporteTotal(montoImporteTotal);

                // Log del tipo de orden de compra antes de la validación
                logger.error("Tipo de Orden de Compra recibido: {}", ordenCompraPdfDto.getOrdenCompraTipo());

                if ("Servicio".equals(ordenCompraPdfDto.getOrdenCompraTipo())) {
                    // Es un servicio (ZC03 o ZC18) → no se imprime lugar de entrega
                    //ordenCompraPdfDto.setLugarEntrega(ordenCompraPdfDto.getLugarEntrega());
                    ordenCompraPdfDto.setLugarEntrega("");
                } else {
                    // Es material → se imprime con la lógica existente
                    ordenCompraPdfDto.setLugarEntrega(dto.getLugarEntregaDireccion() + "\n\n" + dto.getLugarEntregaDescription());
                }

                logger.error(header1 + " // FOUND ONE OC: " + ordenCompraPdfDto.toString()); // MUESTRA SIEMPRE LOS DATOS DE LA OC QUE LLEGA DE SAP

                Optional<OrdenCompra> optionalOrdenCompra = ordenCompraRepository.getOrdenCompraActivaByNumero(numeroOrdenCompra);

                if (optionalOrdenCompra.isPresent()){ // OC ya existe en HANA
                    OrdenCompra ordenCompraExistente = optionalOrdenCompra.get();

                    if(ordenCompraExistente.getIdEstadoOrdenCompra().compareTo(OrdenCompraEstadoEnum.ANULADA.getId()) != 0) {
                        ordenCompraPdfDto.setOrdenCompraVersion(String.valueOf(ordenCompraExistente.getVersion()));

                        //&nbsp;
                        StringBuilder txtBuilder1 = new StringBuilder();
                        int[] txtCounter1 = new int[]{0};
                        textoCabeceraList.forEach(txt -> {
                            if(txtCounter1[0] == 0)
                                txtBuilder1.append(txt);
                            else
                                txtBuilder1.append(" <br> ".concat(txt));

                            txtCounter1[0]++;
                        });
                        ordenCompraPdfDto.setTextoCabecera(txtBuilder1.toString());

                        StringBuilder txtBuilder2 = new StringBuilder();
                        int[] txtCounter2 = new int[]{0};
                        textoCabeceraObservacionesList.forEach(txt -> {
                            if(txtCounter2[0] == 0)
                                txtBuilder2.append(txt);
                            else
                                txtBuilder2.append(" <br> ".concat(txt));

                            txtCounter2[0]++;
                        });
                        ordenCompraPdfDto.setTextoCabeceraObservaciones(txtBuilder2.toString());

                        StringBuilder txtBuilder3 = new StringBuilder();
                        int[] txtCounter3 = new int[]{0};
                        textoFechaList.forEach(txt -> {
                            if(txtCounter3[0] == 0)
                                txtBuilder3.append(txt);
                            else
                                txtBuilder3.append(" <br> ".concat(txt));

                            txtCounter3[0]++;
                        });
                        ordenCompraPdfDto.setTextoFecha(txtBuilder3.toString());

                        StringBuilder txtBuilder4 = new StringBuilder();
                        int[] txtCounter4 = new int[]{0};
                        textoNotasImportantesList.forEach(txt -> {
                            if(txtCounter4[0] == 0)
                                txtBuilder4.append(txt);
                            else
                                txtBuilder4.append(" <br> ".concat(txt));

                            txtCounter4[0]++;
                        });
                        ordenCompraPdfDto.setTextoNotasImportantes(txtBuilder4.toString());

                        StringBuilder txtBuilder5 = new StringBuilder();
                        int[] txtCounter5 = new int[]{0};
                        textoNotasProveedorList.forEach(txt -> {
                            if(txtCounter5[0] == 0)
                                txtBuilder5.append(txt);
                            else
                                txtBuilder5.append(" <br> ".concat(txt));

                            txtCounter5[0]++;
                        });
                        ordenCompraPdfDto.setTextoNotasProveedor(txtBuilder5.toString());

                        StringBuilder txtBuilder6 = new StringBuilder();
                        int[] txtCounter6 = new int[]{0};
                        textoNotasClausulaList.forEach(txt -> {
                            if(txtCounter6[0] == 0)
                                txtBuilder6.append(txt);
                            else
                                txtBuilder6.append(" <br> ".concat(txt));

                            txtCounter6[0]++;
                        });
                        ordenCompraPdfDto.setTextoNotasClausula(txtBuilder6.toString());

                        logger.error(header1 + " // MODDED ONE OC: " + ordenCompraPdfDto.toString()); // MUESTRA SIEMPRE LOS DATOS DE LA OC QUE LLEGA DE SAP

                        int[] counterArray = new int[]{0};
                        ordenCompraPosicionPdfDtoList.forEach(ocp -> {
                            counterArray[0]++;
                            logger.error(header1 + " // FOUND POS " + counterArray[0] + ": " + ocp.toString()); // MUESTRA SIEMPRE LOS DATOS DE LAS POSICIONES QUE LLEGAN DE SAP

                            if(ocp.getMaterial() != null && !ocp.getMaterial().isEmpty()) {
                                ocp.setMaterial(String.valueOf(Integer.parseInt(ocp.getMaterial())));
                            }

                            BigDecimal precioUnitarioBase = ocp.getPrecioUnitario();
                            BigDecimal precioUnitario = precioUnitarioBase.divide(ocp.getCantidadBase(), 4, RoundingMode.HALF_UP);

                            ocp.setPrecioUnitario(precioUnitario);
                            ocp.setImporte(ocp.getCantidad().multiply(precioUnitario).setScale(4, RoundingMode.HALF_UP));

                            List<TextoPosicionDto> filteredTextoAmpliadoMaterialDtoList = textoAmpliadoMaterialDtoList.stream()
                                    .filter(tp -> tp.getPosicion().equals(ocp.getPosicion()))
                                    .collect(Collectors.toList());

                            if(filteredTextoAmpliadoMaterialDtoList.size() > 0) {
                                StringBuilder textoAmpliadoMaterialBuilder = new StringBuilder();
                                int[] tamCounter = new int[]{0};
                                filteredTextoAmpliadoMaterialDtoList.forEach(tam -> {
                                    if (tamCounter[0] == 0)
                                        textoAmpliadoMaterialBuilder.append(tam.getLinea());
                                    else
                                        textoAmpliadoMaterialBuilder.append(" ".concat(tam.getLinea()));

                                    tamCounter[0]++;
                                });
                                ocp.setDescripcion(textoAmpliadoMaterialBuilder.toString());
                            }

                            OrdenCompraPosicionDataAdicionalPdfDto posicionDataAdicionalPdfDto = new OrdenCompraPosicionDataAdicionalPdfDto();

                            List <OrdenCompraPosicionClaseCondicionPdfDto> filteredClaseCondicionList = claseCondicionPdfDtoList.stream()
                                    .filter(cc -> cc.getPosicion().equals(ocp.getPosicion()))
                                    .collect(Collectors.toList());

                            posicionDataAdicionalPdfDto.setOrdenCompraPosicionClaseCondicionPdfDtoList(filteredClaseCondicionList);

                            StringBuilder textoPosicionBuilder = new StringBuilder();
                            int[] tpCounter = new int[]{0};
                            textoPosicionDtoList.stream()
                                    .filter(tp -> tp.getPosicion().equals(ocp.getPosicion()))
                                    .forEach(tp -> {
                                        if(tpCounter[0] == 0)
                                            textoPosicionBuilder.append(tp.getLinea());
                                        else
                                            textoPosicionBuilder.append(" ".concat(tp.getLinea()));

                                        tpCounter[0]++;
                                    });
                            posicionDataAdicionalPdfDto.setTextoPosicion(textoPosicionBuilder.toString());

                            StringBuilder textoRegistroInfoBuilder = new StringBuilder();
                            int[] triCounter = new int[]{0};
                            textoRegistroInfoDtoList.stream()
                                    .filter(tri -> tri.getPosicion().equals(ocp.getPosicion()))
                                    .forEach(tri -> {
                                        if(triCounter[0] == 0)
                                            textoRegistroInfoBuilder.append(tri.getLinea());
                                        else
                                            textoRegistroInfoBuilder.append(" ".concat(tri.getLinea()));

                                        triCounter[0]++;
                                    });
                            posicionDataAdicionalPdfDto.setTextoRegistroInfo(textoRegistroInfoBuilder.toString());

                            ocp.setOrdenCompraPosicionDataAdicionalPdfDto(posicionDataAdicionalPdfDto);

                            if(ocp.getPosicion() != null && !ocp.getPosicion().isEmpty()) {
                                ocp.setPosicion(String.valueOf(Integer.parseInt(ocp.getPosicion())));
                            }

                            logger.error(header1 + " // MODDED POS " + counterArray[0] + ": " + ocp.toString()); // MUESTRA SIEMPRE LOS DATOS DE LAS POSICIONES YA MODIFICADOS
                        });

                        ordenCompraPdfDto.setOrdenCompraPosicionPdfDtoList(ordenCompraPosicionPdfDtoList);
                    }
                }
            }
            logger.error(header1 + " // FULL OC: " + ordenCompraPdfDto.toString());

            return ordenCompraPdfDto;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            throw new Exception(e);
        }
    }

    private void mapFilters(JCoFunction function, String numeroOrdenCompra) {
        JCoParameterList paramList = function.getImportParameterList();

        JCoTable jcoTableEBELN = paramList.getTable("I_EBELN");

        if (numeroOrdenCompra != null && !numeroOrdenCompra.isEmpty()) {
            jcoTableEBELN.appendRow();
            jcoTableEBELN.setRow(0);
            jcoTableEBELN.setValue("SIGN", "I");
            jcoTableEBELN.setValue("OPTION", "EQ");
            jcoTableEBELN.setValue("LOW", numeroOrdenCompra);
            jcoTableEBELN.setValue("HIGH", "");
        }
    }

}