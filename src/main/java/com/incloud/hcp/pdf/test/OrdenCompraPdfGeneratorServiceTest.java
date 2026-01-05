package com.incloud.hcp.pdf.test;

import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPdfDto;
import com.incloud.hcp.util.DateUtils;
import org.junit.Ignore;
import org.junit.Test;
import com.incloud.hcp.pdf.PdfGeneratorFactory;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Ignore
public class OrdenCompraPdfGeneratorServiceTest {

    @Test
    public void generatePdf() throws IOException {
        OrdenCompraPdfDto ordenCompraPdfDto = Mock.getInstance().getMockOrdenCompraPdfDto();

        long l = System.currentTimeMillis();
        byte[] bytes = PdfGeneratorFactory.getJasperGenerator().generateOrdenCompraPdfBytes(ordenCompraPdfDto);
        System.out.println("Total generation time: " + (System.currentTimeMillis() - l));

        String pdfPath = "C:\\Users\\Admin\\Desktop\\MockOrdenCompraPdf_" + ordenCompraPdfDto.getOrdenCompraNumero() + ".pdf";

        Files.write(Paths.get(pdfPath), bytes);
    }
}
