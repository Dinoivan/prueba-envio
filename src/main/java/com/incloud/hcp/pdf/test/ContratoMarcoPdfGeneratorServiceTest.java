package com.incloud.hcp.pdf.test;

import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPdfDto;
import com.incloud.hcp.pdf.PdfGeneratorFactory;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Ignore
public class ContratoMarcoPdfGeneratorServiceTest {

    @Test
    public void generatePdf() throws IOException {
        OrdenCompraPdfDto contratoMarcoPdfDto = Mock.getInstance().getMockContratoMarcoPdfDto();

        long l = System.currentTimeMillis();
        byte[] bytes = PdfGeneratorFactory.getJasperGenerator().generateContratoMarcoPdfBytes(contratoMarcoPdfDto);
        System.out.println("Total generation time: " + (System.currentTimeMillis() - l));

        String pdfPath = "C:\\Users\\Admin\\Desktop\\MockContratoMarcoPdf_" + contratoMarcoPdfDto.getOrdenCompraNumero() + ".pdf";

        Files.write(Paths.get(pdfPath), bytes);
    }
}
